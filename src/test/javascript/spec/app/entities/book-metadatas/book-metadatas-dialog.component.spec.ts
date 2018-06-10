/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LibBooksTestModule } from '../../../test.module';
import { BookMetadatasDialogComponent } from '../../../../../../main/webapp/app/entities/book-metadatas/book-metadatas-dialog.component';
import { BookMetadatasService } from '../../../../../../main/webapp/app/entities/book-metadatas/book-metadatas.service';
import { BookMetadatas } from '../../../../../../main/webapp/app/entities/book-metadatas/book-metadatas.model';

describe('Component Tests', () => {

    describe('BookMetadatas Management Dialog Component', () => {
        let comp: BookMetadatasDialogComponent;
        let fixture: ComponentFixture<BookMetadatasDialogComponent>;
        let service: BookMetadatasService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LibBooksTestModule],
                declarations: [BookMetadatasDialogComponent],
                providers: [
                    BookMetadatasService
                ]
            })
            .overrideTemplate(BookMetadatasDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookMetadatasDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookMetadatasService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BookMetadatas(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.bookMetadatas = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bookMetadatasListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BookMetadatas();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.bookMetadatas = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bookMetadatasListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
