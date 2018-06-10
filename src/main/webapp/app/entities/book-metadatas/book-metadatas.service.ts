import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BookMetadatas } from './book-metadatas.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BookMetadatas>;

@Injectable()
export class BookMetadatasService {

    private resourceUrl =  SERVER_API_URL + 'api/book-metadatas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/book-metadatas';

    constructor(private http: HttpClient) { }

    create(bookMetadatas: BookMetadatas): Observable<EntityResponseType> {
        const copy = this.convert(bookMetadatas);
        return this.http.post<BookMetadatas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bookMetadatas: BookMetadatas): Observable<EntityResponseType> {
        const copy = this.convert(bookMetadatas);
        return this.http.put<BookMetadatas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BookMetadatas>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BookMetadatas[]>> {
        const options = createRequestOption(req);
        return this.http.get<BookMetadatas[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BookMetadatas[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<BookMetadatas[]>> {
        const options = createRequestOption(req);
        return this.http.get<BookMetadatas[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BookMetadatas[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BookMetadatas = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BookMetadatas[]>): HttpResponse<BookMetadatas[]> {
        const jsonResponse: BookMetadatas[] = res.body;
        const body: BookMetadatas[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BookMetadatas.
     */
    private convertItemFromServer(bookMetadatas: BookMetadatas): BookMetadatas {
        const copy: BookMetadatas = Object.assign({}, bookMetadatas);
        return copy;
    }

    /**
     * Convert a BookMetadatas to a JSON which can be sent to the server.
     */
    private convert(bookMetadatas: BookMetadatas): BookMetadatas {
        const copy: BookMetadatas = Object.assign({}, bookMetadatas);
        return copy;
    }
}
