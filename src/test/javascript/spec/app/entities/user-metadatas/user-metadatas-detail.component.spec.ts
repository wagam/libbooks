/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LibBooksTestModule } from '../../../test.module';
import { UserMetadatasDetailComponent } from '../../../../../../main/webapp/app/entities/user-metadatas/user-metadatas-detail.component';
import { UserMetadatasService } from '../../../../../../main/webapp/app/entities/user-metadatas/user-metadatas.service';
import { UserMetadatas } from '../../../../../../main/webapp/app/entities/user-metadatas/user-metadatas.model';

describe('Component Tests', () => {

    describe('UserMetadatas Management Detail Component', () => {
        let comp: UserMetadatasDetailComponent;
        let fixture: ComponentFixture<UserMetadatasDetailComponent>;
        let service: UserMetadatasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [UserMetadatasDetailComponent],
                providers: [
                    UserMetadatasService
                ]
            })
            .overrideTemplate(UserMetadatasDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMetadatasDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMetadatasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserMetadatas(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userMetadatas).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
