import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BookMetadatas e2e test', () => {

    let navBarPage: NavBarPage;
    let bookMetadatasDialogPage: BookMetadatasDialogPage;
    let bookMetadatasComponentsPage: BookMetadatasComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BookMetadatas', () => {
        navBarPage.goToEntity('book-metadatas');
        bookMetadatasComponentsPage = new BookMetadatasComponentsPage();
        expect(bookMetadatasComponentsPage.getTitle())
            .toMatch(/libBooksApp.bookMetadatas.home.title/);

    });

    it('should load create BookMetadatas dialog', () => {
        bookMetadatasComponentsPage.clickOnCreateButton();
        bookMetadatasDialogPage = new BookMetadatasDialogPage();
        expect(bookMetadatasDialogPage.getModalTitle())
            .toMatch(/libBooksApp.bookMetadatas.home.createOrEditLabel/);
        bookMetadatasDialogPage.close();
    });

    it('should create and save BookMetadatas', () => {
        bookMetadatasComponentsPage.clickOnCreateButton();
        bookMetadatasDialogPage.setNumberOfCommentsInput('5');
        expect(bookMetadatasDialogPage.getNumberOfCommentsInput()).toMatch('5');
        bookMetadatasDialogPage.setNumberOfLikesInput('5');
        expect(bookMetadatasDialogPage.getNumberOfLikesInput()).toMatch('5');
        bookMetadatasDialogPage.setNotationInput('5');
        expect(bookMetadatasDialogPage.getNotationInput()).toMatch('5');
        bookMetadatasDialogPage.save();
        expect(bookMetadatasDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BookMetadatasComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-book-metadatas div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BookMetadatasDialogPage {
    modalTitle = element(by.css('h4#myBookMetadatasLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    numberOfCommentsInput = element(by.css('input#field_numberOfComments'));
    numberOfLikesInput = element(by.css('input#field_numberOfLikes'));
    notationInput = element(by.css('input#field_notation'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNumberOfCommentsInput = function(numberOfComments) {
        this.numberOfCommentsInput.sendKeys(numberOfComments);
    };

    getNumberOfCommentsInput = function() {
        return this.numberOfCommentsInput.getAttribute('value');
    };

    setNumberOfLikesInput = function(numberOfLikes) {
        this.numberOfLikesInput.sendKeys(numberOfLikes);
    };

    getNumberOfLikesInput = function() {
        return this.numberOfLikesInput.getAttribute('value');
    };

    setNotationInput = function(notation) {
        this.notationInput.sendKeys(notation);
    };

    getNotationInput = function() {
        return this.notationInput.getAttribute('value');
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
