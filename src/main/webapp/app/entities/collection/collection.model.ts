import { BaseEntity } from './../../shared';

export class Collection implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public coverContentType?: string,
        public cover?: any,
        public bookNumber?: number,
        public books?: BaseEntity[],
    ) {
    }
}
