import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserBookComponent } from './user-book.component';
import { UserBookDetailComponent } from './user-book-detail.component';
import { UserBookPopupComponent } from './user-book-dialog.component';
import { UserBookDeletePopupComponent } from './user-book-delete-dialog.component';

export const userBookRoute: Routes = [
    {
        path: 'user-book',
        component: UserBookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-book/:id',
        component: UserBookDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userBook.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userBookPopupRoute: Routes = [
    {
        path: 'user-book-new',
        component: UserBookPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userBook.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-book/:id/edit',
        component: UserBookPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userBook.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-book/:id/delete',
        component: UserBookDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'libBooksApp.userBook.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
