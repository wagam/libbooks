/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LibBooksTestModule } from '../../../test.module';
import { UserBookComponent } from '../../../../../../main/webapp/app/entities/user-book/user-book.component';
import { UserBookService } from '../../../../../../main/webapp/app/entities/user-book/user-book.service';
import { UserBook } from '../../../../../../main/webapp/app/entities/user-book/user-book.model';

describe('Component Tests', () => {

    describe('UserBook Management Component', () => {
        let comp: UserBookComponent;
        let fixture: ComponentFixture<UserBookComponent>;
        let service: UserBookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [UserBookComponent],
                providers: [
                    UserBookService
                ]
            })
            .overrideTemplate(UserBookComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserBookComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserBookService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserBook(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userBooks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
