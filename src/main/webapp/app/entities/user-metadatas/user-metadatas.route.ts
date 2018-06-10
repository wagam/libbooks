import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserMetadatasComponent } from './user-metadatas.component';
import { UserMetadatasDetailComponent } from './user-metadatas-detail.component';
import { UserMetadatasPopupComponent } from './user-metadatas-dialog.component';
import { UserMetadatasDeletePopupComponent } from './user-metadatas-delete-dialog.component';

export const userMetadatasRoute: Routes = [
    {
        path: 'user-metadatas',
        component: UserMetadatasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-metadatas/:id',
        component: UserMetadatasDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userMetadatasPopupRoute: Routes = [
    {
        path: 'user-metadatas-new',
        component: UserMetadatasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-metadatas/:id/edit',
        component: UserMetadatasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-metadatas/:id/delete',
        component: UserMetadatasDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
