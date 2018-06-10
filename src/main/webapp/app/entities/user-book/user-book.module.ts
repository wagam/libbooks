import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibBooksSharedModule } from '../../shared';
import { LibBooksAdminModule } from '../../admin/admin.module';
import {
    UserBookService,
    UserBookPopupService,
    UserBookComponent,
    UserBookDetailComponent,
    UserBookDialogComponent,
    UserBookPopupComponent,
    UserBookDeletePopupComponent,
    UserBookDeleteDialogComponent,
    userBookRoute,
    userBookPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userBookRoute,
    ...userBookPopupRoute,
];

@NgModule({
    imports: [
        LibBooksSharedModule,
        LibBooksAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserBookComponent,
        UserBookDetailComponent,
        UserBookDialogComponent,
        UserBookDeleteDialogComponent,
        UserBookPopupComponent,
        UserBookDeletePopupComponent,
    ],
    entryComponents: [
        UserBookComponent,
        UserBookDialogComponent,
        UserBookPopupComponent,
        UserBookDeleteDialogComponent,
        UserBookDeletePopupComponent,
    ],
    providers: [
        UserBookService,
        UserBookPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibBooksUserBookModule {}
