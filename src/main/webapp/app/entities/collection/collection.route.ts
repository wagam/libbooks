import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CollectionComponent } from './collection.component';
import { CollectionDetailComponent } from './collection-detail.component';
import { CollectionPopupComponent } from './collection-dialog.component';
import { CollectionDeletePopupComponent } from './collection-delete-dialog.component';

@Injectable()
export class CollectionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const collectionRoute: Routes = [
    {
        path: 'collection',
        component: CollectionComponent,
        resolve: {
            'pagingParams': CollectionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.collection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collection/:id',
        component: CollectionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.collection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collectionPopupRoute: Routes = [
    {
        path: 'collection-new',
        component: CollectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.collection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collection/:id/edit',
        component: CollectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.collection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collection/:id/delete',
        component: CollectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.collection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
