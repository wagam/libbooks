import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Collection e2e test', () => {

    let navBarPage: NavBarPage;
    let collectionDialogPage: CollectionDialogPage;
    let collectionComponentsPage: CollectionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Collections', () => {
        navBarPage.goToEntity('collection');
        collectionComponentsPage = new CollectionComponentsPage();
        expect(collectionComponentsPage.getTitle())
            .toMatch(/libBooksApp.collection.home.title/);

    });

    it('should load create Collection dialog', () => {
        collectionComponentsPage.clickOnCreateButton();
        collectionDialogPage = new CollectionDialogPage();
        expect(collectionDialogPage.getModalTitle())
            .toMatch(/libBooksApp.collection.home.createOrEditLabel/);
        collectionDialogPage.close();
    });

    it('should create and save Collections', () => {
        collectionComponentsPage.clickOnCreateButton();
        collectionDialogPage.setNameInput('name');
        expect(collectionDialogPage.getNameInput()).toMatch('name');
        collectionDialogPage.setCoverInput(absolutePath);
        collectionDialogPage.setBookNumberInput('5');
        expect(collectionDialogPage.getBookNumberInput()).toMatch('5');
        collectionDialogPage.save();
        expect(collectionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CollectionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-collection div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CollectionDialogPage {
    modalTitle = element(by.css('h4#myCollectionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    coverInput = element(by.css('input#file_cover'));
    bookNumberInput = element(by.css('input#field_bookNumber'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setCoverInput = function(cover) {
        this.coverInput.sendKeys(cover);
    };

    getCoverInput = function() {
        return this.coverInput.getAttribute('value');
    };

    setBookNumberInput = function(bookNumber) {
        this.bookNumberInput.sendKeys(bookNumber);
    };

    getBookNumberInput = function() {
        return this.bookNumberInput.getAttribute('value');
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
