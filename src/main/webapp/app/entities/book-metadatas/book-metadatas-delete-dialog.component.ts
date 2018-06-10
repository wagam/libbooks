import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BookMetadatas } from './book-metadatas.model';
import { BookMetadatasPopupService } from './book-metadatas-popup.service';
import { BookMetadatasService } from './book-metadatas.service';

@Component({
    selector: 'jhi-book-metadatas-delete-dialog',
    templateUrl: './book-metadatas-delete-dialog.component.html'
})
export class BookMetadatasDeleteDialogComponent {

    bookMetadatas: BookMetadatas;

    constructor(
        private bookMetadatasService: BookMetadatasService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bookMetadatasService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bookMetadatasListModification',
                content: 'Deleted an bookMetadatas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-book-metadatas-delete-popup',
    template: ''
})
export class BookMetadatasDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookMetadatasPopupService: BookMetadatasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bookMetadatasPopupService
                .open(BookMetadatasDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
