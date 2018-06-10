import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Collection } from './collection.model';
import { CollectionService } from './collection.service';

@Component({
    selector: 'jhi-collection-detail',
    templateUrl: './collection-detail.component.html'
})
export class CollectionDetailComponent implements OnInit, OnDestroy {

    collection: Collection;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private collectionService: CollectionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollections();
    }

    load(id) {
        this.collectionService.find(id)
            .subscribe((collectionResponse: HttpResponse<Collection>) => {
                this.collection = collectionResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollections() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collectionListModification',
            (response) => this.load(this.collection.id)
        );
    }
}
