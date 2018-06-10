import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserMetadatas } from './user-metadatas.model';
import { UserMetadatasService } from './user-metadatas.service';

@Component({
    selector: 'jhi-user-metadatas-detail',
    templateUrl: './user-metadatas-detail.component.html'
})
export class UserMetadatasDetailComponent implements OnInit, OnDestroy {

    userMetadatas: UserMetadatas;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userMetadatasService: UserMetadatasService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserMetadatas();
    }

    load(id) {
        this.userMetadatasService.find(id)
            .subscribe((userMetadatasResponse: HttpResponse<UserMetadatas>) => {
                this.userMetadatas = userMetadatasResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserMetadatas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userMetadatasListModification',
            (response) => this.load(this.userMetadatas.id)
        );
    }
}
