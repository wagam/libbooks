import { BaseEntity } from './../../shared';

export class Author implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public firstName?: string,
        public books?: BaseEntity[],
    ) {
    }
}
