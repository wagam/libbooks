import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LibBooksCollectionModule } from './collection/collection.module';
import { LibBooksBookModule } from './book/book.module';
import { LibBooksAuthorModule } from './author/author.module';
import { LibBooksUserBookModule } from './user-book/user-book.module';
import { LibBooksUserMetadatasModule } from './user-metadatas/user-metadatas.module';
import { LibBooksBookMetadatasModule } from './book-metadatas/book-metadatas.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LibBooksCollectionModule,
        LibBooksBookModule,
        LibBooksAuthorModule,
        LibBooksUserBookModule,
        LibBooksUserMetadatasModule,
        LibBooksBookMetadatasModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LibBooksEntityModule {}
