import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Book e2e test', () => {

    let navBarPage: NavBarPage;
    let bookDialogPage: BookDialogPage;
    let bookComponentsPage: BookComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Books', () => {
        navBarPage.goToEntity('book');
        bookComponentsPage = new BookComponentsPage();
        expect(bookComponentsPage.getTitle())
            .toMatch(/libBooksApp.book.home.title/);

    });

    it('should load create Book dialog', () => {
        bookComponentsPage.clickOnCreateButton();
        bookDialogPage = new BookDialogPage();
        expect(bookDialogPage.getModalTitle())
            .toMatch(/libBooksApp.book.home.createOrEditLabel/);
        bookDialogPage.close();
    });

    it('should create and save Books', () => {
        bookComponentsPage.clickOnCreateButton();
        bookDialogPage.setTitleInput('title');
        expect(bookDialogPage.getTitleInput()).toMatch('title');
        bookDialogPage.setFirstYearEditionInput('5');
        expect(bookDialogPage.getFirstYearEditionInput()).toMatch('5');
        bookDialogPage.getHasNextInput().isSelected().then((selected) => {
            if (selected) {
                bookDialogPage.getHasNextInput().click();
                expect(bookDialogPage.getHasNextInput().isSelected()).toBeFalsy();
            } else {
                bookDialogPage.getHasNextInput().click();
                expect(bookDialogPage.getHasNextInput().isSelected()).toBeTruthy();
            }
        });
        bookDialogPage.setSummaryInput('summary');
        expect(bookDialogPage.getSummaryInput()).toMatch('summary');
        bookDialogPage.setCoverInput(absolutePath);
        bookDialogPage.setNumberOfPagesInput('5');
        expect(bookDialogPage.getNumberOfPagesInput()).toMatch('5');
        bookDialogPage.metadatasSelectLastOption();
        bookDialogPage.collectionSelectLastOption();
        bookDialogPage.authorSelectLastOption();
        bookDialogPage.collectionSelectLastOption();
        bookDialogPage.authorSelectLastOption();
        bookDialogPage.save();
        expect(bookDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BookComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-book div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BookDialogPage {
    modalTitle = element(by.css('h4#myBookLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    firstYearEditionInput = element(by.css('input#field_firstYearEdition'));
    hasNextInput = element(by.css('input#field_hasNext'));
    summaryInput = element(by.css('input#field_summary'));
    coverInput = element(by.css('input#file_cover'));
    numberOfPagesInput = element(by.css('input#field_numberOfPages'));
    metadatasSelect = element(by.css('select#field_metadatas'));
    collectionSelect = element(by.css('select#field_collection'));
    authorSelect = element(by.css('select#field_author'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setFirstYearEditionInput = function(firstYearEdition) {
        this.firstYearEditionInput.sendKeys(firstYearEdition);
    };

    getFirstYearEditionInput = function() {
        return this.firstYearEditionInput.getAttribute('value');
    };

    getHasNextInput = function() {
        return this.hasNextInput;
    };
    setSummaryInput = function(summary) {
        this.summaryInput.sendKeys(summary);
    };

    getSummaryInput = function() {
        return this.summaryInput.getAttribute('value');
    };

    setCoverInput = function(cover) {
        this.coverInput.sendKeys(cover);
    };

    getCoverInput = function() {
        return this.coverInput.getAttribute('value');
    };

    setNumberOfPagesInput = function(numberOfPages) {
        this.numberOfPagesInput.sendKeys(numberOfPages);
    };

    getNumberOfPagesInput = function() {
        return this.numberOfPagesInput.getAttribute('value');
    };

    metadatasSelectLastOption = function() {
        this.metadatasSelect.all(by.tagName('option')).last().click();
    };

    metadatasSelectOption = function(option) {
        this.metadatasSelect.sendKeys(option);
    };

    getMetadatasSelect = function() {
        return this.metadatasSelect;
    };

    getMetadatasSelectedOption = function() {
        return this.metadatasSelect.element(by.css('option:checked')).getText();
    };

    collectionSelectLastOption = function() {
        this.collectionSelect.all(by.tagName('option')).last().click();
    };

    collectionSelectOption = function(option) {
        this.collectionSelect.sendKeys(option);
    };

    getCollectionSelect = function() {
        return this.collectionSelect;
    };

    getCollectionSelectedOption = function() {
        return this.collectionSelect.element(by.css('option:checked')).getText();
    };

    authorSelectLastOption = function() {
        this.authorSelect.all(by.tagName('option')).last().click();
    };

    authorSelectOption = function(option) {
        this.authorSelect.sendKeys(option);
    };

    getAuthorSelect = function() {
        return this.authorSelect;
    };

    getAuthorSelectedOption = function() {
        return this.authorSelect.element(by.css('option:checked')).getText();
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
