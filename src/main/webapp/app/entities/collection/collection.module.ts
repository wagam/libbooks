import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibBooksSharedModule } from '../../shared';
import {
    CollectionService,
    CollectionPopupService,
    CollectionComponent,
    CollectionDetailComponent,
    CollectionDialogComponent,
    CollectionPopupComponent,
    CollectionDeletePopupComponent,
    CollectionDeleteDialogComponent,
    collectionRoute,
    collectionPopupRoute,
    CollectionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...collectionRoute,
    ...collectionPopupRoute,
];

@NgModule({
    imports: [
        LibBooksSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CollectionComponent,
        CollectionDetailComponent,
        CollectionDialogComponent,
        CollectionDeleteDialogComponent,
        CollectionPopupComponent,
        CollectionDeletePopupComponent,
    ],
    entryComponents: [
        CollectionComponent,
        CollectionDialogComponent,
        CollectionPopupComponent,
        CollectionDeleteDialogComponent,
        CollectionDeletePopupComponent,
    ],
    providers: [
        CollectionService,
        CollectionPopupService,
        CollectionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibBooksCollectionModule {}
