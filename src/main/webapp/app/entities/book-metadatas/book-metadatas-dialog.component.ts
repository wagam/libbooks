import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BookMetadatas } from './book-metadatas.model';
import { BookMetadatasPopupService } from './book-metadatas-popup.service';
import { BookMetadatasService } from './book-metadatas.service';

@Component({
    selector: 'jhi-book-metadatas-dialog',
    templateUrl: './book-metadatas-dialog.component.html'
})
export class BookMetadatasDialogComponent implements OnInit {

    bookMetadatas: BookMetadatas;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bookMetadatasService: BookMetadatasService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bookMetadatas.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bookMetadatasService.update(this.bookMetadatas));
        } else {
            this.subscribeToSaveResponse(
                this.bookMetadatasService.create(this.bookMetadatas));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BookMetadatas>>) {
        result.subscribe((res: HttpResponse<BookMetadatas>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BookMetadatas) {
        this.eventManager.broadcast({ name: 'bookMetadatasListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-book-metadatas-popup',
    template: ''
})
export class BookMetadatasPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookMetadatasPopupService: BookMetadatasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bookMetadatasPopupService
                    .open(BookMetadatasDialogComponent as Component, params['id']);
            } else {
                this.bookMetadatasPopupService
                    .open(BookMetadatasDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
