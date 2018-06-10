/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LibBooksTestModule } from '../../../test.module';
import { UserBookDetailComponent } from '../../../../../../main/webapp/app/entities/user-book/user-book-detail.component';
import { UserBookService } from '../../../../../../main/webapp/app/entities/user-book/user-book.service';
import { UserBook } from '../../../../../../main/webapp/app/entities/user-book/user-book.model';

describe('Component Tests', () => {

    describe('UserBook Management Detail Component', () => {
        let comp: UserBookDetailComponent;
        let fixture: ComponentFixture<UserBookDetailComponent>;
        let service: UserBookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [UserBookDetailComponent],
                providers: [
                    UserBookService
                ]
            })
            .overrideTemplate(UserBookDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserBookDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserBookService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserBook(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userBook).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
