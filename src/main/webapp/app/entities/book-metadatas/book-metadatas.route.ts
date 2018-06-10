import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BookMetadatasComponent } from './book-metadatas.component';
import { BookMetadatasDetailComponent } from './book-metadatas-detail.component';
import { BookMetadatasPopupComponent } from './book-metadatas-dialog.component';
import { BookMetadatasDeletePopupComponent } from './book-metadatas-delete-dialog.component';

export const bookMetadatasRoute: Routes = [
    {
        path: 'book-metadatas',
        component: BookMetadatasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.bookMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'book-metadatas/:id',
        component: BookMetadatasDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.bookMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bookMetadatasPopupRoute: Routes = [
    {
        path: 'book-metadatas-new',
        component: BookMetadatasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.bookMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'book-metadatas/:id/edit',
        component: BookMetadatasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.bookMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'book-metadatas/:id/delete',
        component: BookMetadatasDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.bookMetadatas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
