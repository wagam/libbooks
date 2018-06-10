import { BaseEntity } from './../../shared';

export const enum BookStatus {
    'IS_READ',
    'WAIT_FOR_READ',
    'IS_IN_PROGRESS'
}

export class UserBook implements BaseEntity {
    constructor(
        public id?: number,
        public status?: BookStatus,
        public comment?: string,
        public isLiked?: boolean,
        public userId?: number,
        public booksId?: number,
    ) {
        this.isLiked = false;
    }
}
