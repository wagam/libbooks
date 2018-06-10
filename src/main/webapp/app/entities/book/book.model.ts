import { BaseEntity } from './../../shared';

export class Book implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public firstYearEdition?: number,
        public hasNext?: boolean,
        public summary?: string,
        public coverContentType?: string,
        public cover?: any,
        public numberOfPages?: number,
        public metadatasId?: number,
        public collectionId?: number,
        public authorId?: number,
    ) {
        this.hasNext = false;
    }
}
