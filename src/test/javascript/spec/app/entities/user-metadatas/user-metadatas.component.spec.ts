/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LibBooksTestModule } from '../../../test.module';
import { UserMetadatasComponent } from '../../../../../../main/webapp/app/entities/user-metadatas/user-metadatas.component';
import { UserMetadatasService } from '../../../../../../main/webapp/app/entities/user-metadatas/user-metadatas.service';
import { UserMetadatas } from '../../../../../../main/webapp/app/entities/user-metadatas/user-metadatas.model';

describe('Component Tests', () => {

    describe('UserMetadatas Management Component', () => {
        let comp: UserMetadatasComponent;
        let fixture: ComponentFixture<UserMetadatasComponent>;
        let service: UserMetadatasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [UserMetadatasComponent],
                providers: [
                    UserMetadatasService
                ]
            })
            .overrideTemplate(UserMetadatasComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMetadatasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMetadatasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserMetadatas(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userMetadatas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
