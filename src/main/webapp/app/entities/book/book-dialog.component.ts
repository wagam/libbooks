import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Book } from './book.model';
import { BookPopupService } from './book-popup.service';
import { BookService } from './book.service';
import { BookMetadatas, BookMetadatasService } from '../book-metadatas';
import { Collection, CollectionService } from '../collection';
import { Author, AuthorService } from '../author';

@Component({
    selector: 'jhi-book-dialog',
    templateUrl: './book-dialog.component.html'
})
export class BookDialogComponent implements OnInit {

    book: Book;
    isSaving: boolean;

    metadatas: BookMetadatas[];

    collections: Collection[];

    authors: Author[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private bookService: BookService,
        private bookMetadatasService: BookMetadatasService,
        private collectionService: CollectionService,
        private authorService: AuthorService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bookMetadatasService
            .query({filter: 'book-is-null'})
            .subscribe((res: HttpResponse<BookMetadatas[]>) => {
                if (!this.book.metadatasId) {
                    this.metadatas = res.body;
                } else {
                    this.bookMetadatasService
                        .find(this.book.metadatasId)
                        .subscribe((subRes: HttpResponse<BookMetadatas>) => {
                            this.metadatas = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.collectionService.query()
            .subscribe((res: HttpResponse<Collection[]>) => { this.collections = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.authorService.query()
            .subscribe((res: HttpResponse<Author[]>) => { this.authors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.book, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.book.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bookService.update(this.book));
        } else {
            this.subscribeToSaveResponse(
                this.bookService.create(this.book));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Book>>) {
        result.subscribe((res: HttpResponse<Book>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Book) {
        this.eventManager.broadcast({ name: 'bookListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBookMetadatasById(index: number, item: BookMetadatas) {
        return item.id;
    }

    trackCollectionById(index: number, item: Collection) {
        return item.id;
    }

    trackAuthorById(index: number, item: Author) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-book-popup',
    template: ''
})
export class BookPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookPopupService: BookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bookPopupService
                    .open(BookDialogComponent as Component, params['id']);
            } else {
                this.bookPopupService
                    .open(BookDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
