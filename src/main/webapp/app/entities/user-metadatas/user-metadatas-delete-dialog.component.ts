import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserMetadatas } from './user-metadatas.model';
import { UserMetadatasPopupService } from './user-metadatas-popup.service';
import { UserMetadatasService } from './user-metadatas.service';

@Component({
    selector: 'jhi-user-metadatas-delete-dialog',
    templateUrl: './user-metadatas-delete-dialog.component.html'
})
export class UserMetadatasDeleteDialogComponent {

    userMetadatas: UserMetadatas;

    constructor(
        private userMetadatasService: UserMetadatasService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userMetadatasService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userMetadatasListModification',
                content: 'Deleted an userMetadatas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-metadatas-delete-popup',
    template: ''
})
export class UserMetadatasDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMetadatasPopupService: UserMetadatasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userMetadatasPopupService
                .open(UserMetadatasDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
