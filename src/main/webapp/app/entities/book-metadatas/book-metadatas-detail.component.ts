import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BookMetadatas } from './book-metadatas.model';
import { BookMetadatasService } from './book-metadatas.service';

@Component({
    selector: 'jhi-book-metadatas-detail',
    templateUrl: './book-metadatas-detail.component.html'
})
export class BookMetadatasDetailComponent implements OnInit, OnDestroy {

    bookMetadatas: BookMetadatas;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bookMetadatasService: BookMetadatasService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBookMetadatas();
    }

    load(id) {
        this.bookMetadatasService.find(id)
            .subscribe((bookMetadatasResponse: HttpResponse<BookMetadatas>) => {
                this.bookMetadatas = bookMetadatasResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBookMetadatas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bookMetadatasListModification',
            (response) => this.load(this.bookMetadatas.id)
        );
    }
}
