import { BaseEntity } from './../../shared';

export class UserMetadatas implements BaseEntity {
    constructor(
        public id?: number,
        public numberOfReadBooks?: number,
        public numberOfComments?: number,
        public numberOfLikes?: number,
        public userId?: number,
    ) {
    }
}
