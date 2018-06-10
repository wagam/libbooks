import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { BookMetadatas } from './book-metadatas.model';
import { BookMetadatasService } from './book-metadatas.service';

@Injectable()
export class BookMetadatasPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private bookMetadatasService: BookMetadatasService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.bookMetadatasService.find(id)
                    .subscribe((bookMetadatasResponse: HttpResponse<BookMetadatas>) => {
                        const bookMetadatas: BookMetadatas = bookMetadatasResponse.body;
                        this.ngbModalRef = this.bookMetadatasModalRef(component, bookMetadatas);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.bookMetadatasModalRef(component, new BookMetadatas());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    bookMetadatasModalRef(component: Component, bookMetadatas: BookMetadatas): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bookMetadatas = bookMetadatas;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
