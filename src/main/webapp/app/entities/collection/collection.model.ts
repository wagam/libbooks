import { BaseEntity } from './../../shared';

export class Collection implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public imageContentType?: string,
        public image?: any,
        public booksNumber?: number,
    ) {
    }
}
