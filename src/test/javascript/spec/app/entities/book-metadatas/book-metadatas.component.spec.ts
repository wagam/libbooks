/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LibBooksTestModule } from '../../../test.module';
import { BookMetadatasComponent } from '../../../../../../main/webapp/app/entities/book-metadatas/book-metadatas.component';
import { BookMetadatasService } from '../../../../../../main/webapp/app/entities/book-metadatas/book-metadatas.service';
import { BookMetadatas } from '../../../../../../main/webapp/app/entities/book-metadatas/book-metadatas.model';

describe('Component Tests', () => {

    describe('BookMetadatas Management Component', () => {
        let comp: BookMetadatasComponent;
        let fixture: ComponentFixture<BookMetadatasComponent>;
        let service: BookMetadatasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [BookMetadatasComponent],
                providers: [
                    BookMetadatasService
                ]
            })
            .overrideTemplate(BookMetadatasComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookMetadatasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookMetadatasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BookMetadatas(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bookMetadatas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
