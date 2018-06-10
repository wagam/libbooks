import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserBook } from './user-book.model';
import { UserBookPopupService } from './user-book-popup.service';
import { UserBookService } from './user-book.service';
import { User, UserService } from '../../shared';
import { Book, BookService } from '../book';

@Component({
    selector: 'jhi-user-book-dialog',
    templateUrl: './user-book-dialog.component.html'
})
export class UserBookDialogComponent implements OnInit {

    userBook: UserBook;
    isSaving: boolean;

    users: User[];

    books: Book[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userBookService: UserBookService,
        private userService: UserService,
        private bookService: BookService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.bookService.query()
            .subscribe((res: HttpResponse<Book[]>) => { this.books = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userBook.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userBookService.update(this.userBook));
        } else {
            this.subscribeToSaveResponse(
                this.userBookService.create(this.userBook));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserBook>>) {
        result.subscribe((res: HttpResponse<UserBook>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserBook) {
        this.eventManager.broadcast({ name: 'userBookListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackBookById(index: number, item: Book) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-book-popup',
    template: ''
})
export class UserBookPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userBookPopupService: UserBookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userBookPopupService
                    .open(UserBookDialogComponent as Component, params['id']);
            } else {
                this.userBookPopupService
                    .open(UserBookDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
