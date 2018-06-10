import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BookMetadatas } from './book-metadatas.model';
import { BookMetadatasService } from './book-metadatas.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-book-metadatas',
    templateUrl: './book-metadatas.component.html'
})
export class BookMetadatasComponent implements OnInit, OnDestroy {
bookMetadatas: BookMetadatas[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private bookMetadatasService: BookMetadatasService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.bookMetadatasService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<BookMetadatas[]>) => this.bookMetadatas = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.bookMetadatasService.query().subscribe(
            (res: HttpResponse<BookMetadatas[]>) => {
                this.bookMetadatas = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBookMetadatas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BookMetadatas) {
        return item.id;
    }
    registerChangeInBookMetadatas() {
        this.eventSubscriber = this.eventManager.subscribe('bookMetadatasListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
