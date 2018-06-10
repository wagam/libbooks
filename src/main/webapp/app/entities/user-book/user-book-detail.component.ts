import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserBook } from './user-book.model';
import { UserBookService } from './user-book.service';

@Component({
    selector: 'jhi-user-book-detail',
    templateUrl: './user-book-detail.component.html'
})
export class UserBookDetailComponent implements OnInit, OnDestroy {

    userBook: UserBook;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userBookService: UserBookService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserBooks();
    }

    load(id) {
        this.userBookService.find(id)
            .subscribe((userBookResponse: HttpResponse<UserBook>) => {
                this.userBook = userBookResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserBooks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userBookListModification',
            (response) => this.load(this.userBook.id)
        );
    }
}
