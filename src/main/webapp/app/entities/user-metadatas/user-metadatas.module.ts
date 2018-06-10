import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibBooksSharedModule } from '../../shared';
import { LibBooksAdminModule } from '../../admin/admin.module';
import {
    UserMetadatasService,
    UserMetadatasPopupService,
    UserMetadatasComponent,
    UserMetadatasDetailComponent,
    UserMetadatasDialogComponent,
    UserMetadatasPopupComponent,
    UserMetadatasDeletePopupComponent,
    UserMetadatasDeleteDialogComponent,
    userMetadatasRoute,
    userMetadatasPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userMetadatasRoute,
    ...userMetadatasPopupRoute,
];

@NgModule({
    imports: [
        LibBooksSharedModule,
        LibBooksAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserMetadatasComponent,
        UserMetadatasDetailComponent,
        UserMetadatasDialogComponent,
        UserMetadatasDeleteDialogComponent,
        UserMetadatasPopupComponent,
        UserMetadatasDeletePopupComponent,
    ],
    entryComponents: [
        UserMetadatasComponent,
        UserMetadatasDialogComponent,
        UserMetadatasPopupComponent,
        UserMetadatasDeleteDialogComponent,
        UserMetadatasDeletePopupComponent,
    ],
    providers: [
        UserMetadatasService,
        UserMetadatasPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibBooksUserMetadatasModule {}
