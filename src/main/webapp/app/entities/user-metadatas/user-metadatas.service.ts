import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserMetadatas } from './user-metadatas.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserMetadatas>;

@Injectable()
export class UserMetadatasService {

    private resourceUrl =  SERVER_API_URL + 'api/user-metadatas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-metadatas';

    constructor(private http: HttpClient) { }

    create(userMetadatas: UserMetadatas): Observable<EntityResponseType> {
        const copy = this.convert(userMetadatas);
        return this.http.post<UserMetadatas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userMetadatas: UserMetadatas): Observable<EntityResponseType> {
        const copy = this.convert(userMetadatas);
        return this.http.put<UserMetadatas>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserMetadatas>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserMetadatas[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserMetadatas[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserMetadatas[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<UserMetadatas[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserMetadatas[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserMetadatas[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserMetadatas = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserMetadatas[]>): HttpResponse<UserMetadatas[]> {
        const jsonResponse: UserMetadatas[] = res.body;
        const body: UserMetadatas[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserMetadatas.
     */
    private convertItemFromServer(userMetadatas: UserMetadatas): UserMetadatas {
        const copy: UserMetadatas = Object.assign({}, userMetadatas);
        return copy;
    }

    /**
     * Convert a UserMetadatas to a JSON which can be sent to the server.
     */
    private convert(userMetadatas: UserMetadatas): UserMetadatas {
        const copy: UserMetadatas = Object.assign({}, userMetadatas);
        return copy;
    }
}
