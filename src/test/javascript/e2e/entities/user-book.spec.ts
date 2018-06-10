import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserBook e2e test', () => {

    let navBarPage: NavBarPage;
    let userBookDialogPage: UserBookDialogPage;
    let userBookComponentsPage: UserBookComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserBooks', () => {
        navBarPage.goToEntity('user-book');
        userBookComponentsPage = new UserBookComponentsPage();
        expect(userBookComponentsPage.getTitle())
            .toMatch(/libBooksApp.userBook.home.title/);

    });

    it('should load create UserBook dialog', () => {
        userBookComponentsPage.clickOnCreateButton();
        userBookDialogPage = new UserBookDialogPage();
        expect(userBookDialogPage.getModalTitle())
            .toMatch(/libBooksApp.userBook.home.createOrEditLabel/);
        userBookDialogPage.close();
    });

    it('should create and save UserBooks', () => {
        userBookComponentsPage.clickOnCreateButton();
        userBookDialogPage.statusSelectLastOption();
        userBookDialogPage.setCommentInput('comment');
        expect(userBookDialogPage.getCommentInput()).toMatch('comment');
        userBookDialogPage.getIsLikedInput().isSelected().then((selected) => {
            if (selected) {
                userBookDialogPage.getIsLikedInput().click();
                expect(userBookDialogPage.getIsLikedInput().isSelected()).toBeFalsy();
            } else {
                userBookDialogPage.getIsLikedInput().click();
                expect(userBookDialogPage.getIsLikedInput().isSelected()).toBeTruthy();
            }
        });
        userBookDialogPage.userSelectLastOption();
        userBookDialogPage.booksSelectLastOption();
        userBookDialogPage.save();
        expect(userBookDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserBookComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-book div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserBookDialogPage {
    modalTitle = element(by.css('h4#myUserBookLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    statusSelect = element(by.css('select#field_status'));
    commentInput = element(by.css('input#field_comment'));
    isLikedInput = element(by.css('input#field_isLiked'));
    userSelect = element(by.css('select#field_user'));
    booksSelect = element(by.css('select#field_books'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setStatusSelect = function(status) {
        this.statusSelect.sendKeys(status);
    };

    getStatusSelect = function() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    };

    statusSelectLastOption = function() {
        this.statusSelect.all(by.tagName('option')).last().click();
    };
    setCommentInput = function(comment) {
        this.commentInput.sendKeys(comment);
    };

    getCommentInput = function() {
        return this.commentInput.getAttribute('value');
    };

    getIsLikedInput = function() {
        return this.isLikedInput;
    };
    userSelectLastOption = function() {
        this.userSelect.all(by.tagName('option')).last().click();
    };

    userSelectOption = function(option) {
        this.userSelect.sendKeys(option);
    };

    getUserSelect = function() {
        return this.userSelect;
    };

    getUserSelectedOption = function() {
        return this.userSelect.element(by.css('option:checked')).getText();
    };

    booksSelectLastOption = function() {
        this.booksSelect.all(by.tagName('option')).last().click();
    };

    booksSelectOption = function(option) {
        this.booksSelect.sendKeys(option);
    };

    getBooksSelect = function() {
        return this.booksSelect;
    };

    getBooksSelectedOption = function() {
        return this.booksSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
