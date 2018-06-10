import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserMetadatas } from './user-metadatas.model';
import { UserMetadatasPopupService } from './user-metadatas-popup.service';
import { UserMetadatasService } from './user-metadatas.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-user-metadatas-dialog',
    templateUrl: './user-metadatas-dialog.component.html'
})
export class UserMetadatasDialogComponent implements OnInit {

    userMetadatas: UserMetadatas;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userMetadatasService: UserMetadatasService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userMetadatas.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userMetadatasService.update(this.userMetadatas));
        } else {
            this.subscribeToSaveResponse(
                this.userMetadatasService.create(this.userMetadatas));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserMetadatas>>) {
        result.subscribe((res: HttpResponse<UserMetadatas>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserMetadatas) {
        this.eventManager.broadcast({ name: 'userMetadatasListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-user-metadatas-popup',
    template: ''
})
export class UserMetadatasPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMetadatasPopupService: UserMetadatasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userMetadatasPopupService
                    .open(UserMetadatasDialogComponent as Component, params['id']);
            } else {
                this.userMetadatasPopupService
                    .open(UserMetadatasDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
