import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibBooksSharedModule } from '../../shared';
import {
    BookMetadatasService,
    BookMetadatasPopupService,
    BookMetadatasComponent,
    BookMetadatasDetailComponent,
    BookMetadatasDialogComponent,
    BookMetadatasPopupComponent,
    BookMetadatasDeletePopupComponent,
    BookMetadatasDeleteDialogComponent,
    bookMetadatasRoute,
    bookMetadatasPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bookMetadatasRoute,
    ...bookMetadatasPopupRoute,
];

@NgModule({
    imports: [
        LibBooksSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BookMetadatasComponent,
        BookMetadatasDetailComponent,
        BookMetadatasDialogComponent,
        BookMetadatasDeleteDialogComponent,
        BookMetadatasPopupComponent,
        BookMetadatasDeletePopupComponent,
    ],
    entryComponents: [
        BookMetadatasComponent,
        BookMetadatasDialogComponent,
        BookMetadatasPopupComponent,
        BookMetadatasDeleteDialogComponent,
        BookMetadatasDeletePopupComponent,
    ],
    providers: [
        BookMetadatasService,
        BookMetadatasPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibBooksBookMetadatasModule {}
