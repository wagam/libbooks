import { BaseEntity } from './../../shared';

export class BookMetadatas implements BaseEntity {
    constructor(
        public id?: number,
        public numberOfComments?: number,
        public numberOfLikes?: number,
        public notation?: number,
    ) {
    }
}
