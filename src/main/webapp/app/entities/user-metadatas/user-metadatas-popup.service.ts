import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { UserMetadatas } from './user-metadatas.model';
import { UserMetadatasService } from './user-metadatas.service';

@Injectable()
export class UserMetadatasPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userMetadatasService: UserMetadatasService

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
                this.userMetadatasService.find(id)
                    .subscribe((userMetadatasResponse: HttpResponse<UserMetadatas>) => {
                        const userMetadatas: UserMetadatas = userMetadatasResponse.body;
                        this.ngbModalRef = this.userMetadatasModalRef(component, userMetadatas);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userMetadatasModalRef(component, new UserMetadatas());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userMetadatasModalRef(component: Component, userMetadatas: UserMetadatas): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userMetadatas = userMetadatas;
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
