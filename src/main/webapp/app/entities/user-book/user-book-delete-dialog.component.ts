import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserBook } from './user-book.model';
import { UserBookPopupService } from './user-book-popup.service';
import { UserBookService } from './user-book.service';

@Component({
    selector: 'jhi-user-book-delete-dialog',
    templateUrl: './user-book-delete-dialog.component.html'
})
export class UserBookDeleteDialogComponent {

    userBook: UserBook;

    constructor(
        private userBookService: UserBookService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userBookService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userBookListModification',
                content: 'Deleted an userBook'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-book-delete-popup',
    template: ''
})
export class UserBookDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userBookPopupService: UserBookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userBookPopupService
                .open(UserBookDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
