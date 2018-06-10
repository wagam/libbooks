import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Collection } from './collection.model';
import { CollectionPopupService } from './collection-popup.service';
import { CollectionService } from './collection.service';

@Component({
    selector: 'jhi-collection-dialog',
    templateUrl: './collection-dialog.component.html'
})
export class CollectionDialogComponent implements OnInit {

    collection: Collection;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private collectionService: CollectionService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
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
        this.dataUtils.clearInputImage(this.collection, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collectionService.update(this.collection));
        } else {
            this.subscribeToSaveResponse(
                this.collectionService.create(this.collection));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Collection>>) {
        result.subscribe((res: HttpResponse<Collection>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Collection) {
        this.eventManager.broadcast({ name: 'collectionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-collection-popup',
    template: ''
})
export class CollectionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collectionPopupService: CollectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.collectionPopupService
                    .open(CollectionDialogComponent as Component, params['id']);
            } else {
                this.collectionPopupService
                    .open(CollectionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
