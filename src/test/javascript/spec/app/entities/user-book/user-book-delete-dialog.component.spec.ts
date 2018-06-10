/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LibBooksTestModule } from '../../../test.module';
import { UserBookDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-book/user-book-delete-dialog.component';
import { UserBookService } from '../../../../../../main/webapp/app/entities/user-book/user-book.service';

describe('Component Tests', () => {

    describe('UserBook Management Delete Component', () => {
        let comp: UserBookDeleteDialogComponent;
        let fixture: ComponentFixture<UserBookDeleteDialogComponent>;
        let service: UserBookService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [UserBookDeleteDialogComponent],
                providers: [
                    UserBookService
                ]
            })
            .overrideTemplate(UserBookDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserBookDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserBookService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
