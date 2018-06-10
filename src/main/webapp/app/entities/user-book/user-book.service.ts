import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserBook } from './user-book.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserBook>;

@Injectable()
export class UserBookService {

    private resourceUrl =  SERVER_API_URL + 'api/user-books';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-books';

    constructor(private http: HttpClient) { }

    create(userBook: UserBook): Observable<EntityResponseType> {
        const copy = this.convert(userBook);
        return this.http.post<UserBook>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userBook: UserBook): Observable<EntityResponseType> {
        const copy = this.convert(userBook);
        return this.http.put<UserBook>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserBook>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserBook[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserBook[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserBook[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<UserBook[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserBook[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserBook[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserBook = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserBook[]>): HttpResponse<UserBook[]> {
        const jsonResponse: UserBook[] = res.body;
        const body: UserBook[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserBook.
     */
    private convertItemFromServer(userBook: UserBook): UserBook {
        const copy: UserBook = Object.assign({}, userBook);
        return copy;
    }

    /**
     * Convert a UserBook to a JSON which can be sent to the server.
     */
    private convert(userBook: UserBook): UserBook {
        const copy: UserBook = Object.assign({}, userBook);
        return copy;
    }
}
