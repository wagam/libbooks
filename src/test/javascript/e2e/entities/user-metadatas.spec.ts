import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserMetadatas e2e test', () => {

    let navBarPage: NavBarPage;
    let userMetadatasDialogPage: UserMetadatasDialogPage;
    let userMetadatasComponentsPage: UserMetadatasComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserMetadatas', () => {
        navBarPage.goToEntity('user-metadatas');
        userMetadatasComponentsPage = new UserMetadatasComponentsPage();
        expect(userMetadatasComponentsPage.getTitle())
            .toMatch(/libBooksApp.userMetadatas.home.title/);

    });

    it('should load create UserMetadatas dialog', () => {
        userMetadatasComponentsPage.clickOnCreateButton();
        userMetadatasDialogPage = new UserMetadatasDialogPage();
        expect(userMetadatasDialogPage.getModalTitle())
            .toMatch(/libBooksApp.userMetadatas.home.createOrEditLabel/);
        userMetadatasDialogPage.close();
    });

    it('should create and save UserMetadatas', () => {
        userMetadatasComponentsPage.clickOnCreateButton();
        userMetadatasDialogPage.setNumberOfReadBooksInput('5');
        expect(userMetadatasDialogPage.getNumberOfReadBooksInput()).toMatch('5');
        userMetadatasDialogPage.setNumberOfCommentsInput('5');
        expect(userMetadatasDialogPage.getNumberOfCommentsInput()).toMatch('5');
        userMetadatasDialogPage.setNumberOfLikesInput('5');
        expect(userMetadatasDialogPage.getNumberOfLikesInput()).toMatch('5');
        userMetadatasDialogPage.userSelectLastOption();
        userMetadatasDialogPage.save();
        expect(userMetadatasDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserMetadatasComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-metadatas div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserMetadatasDialogPage {
    modalTitle = element(by.css('h4#myUserMetadatasLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    numberOfReadBooksInput = element(by.css('input#field_numberOfReadBooks'));
    numberOfCommentsInput = element(by.css('input#field_numberOfComments'));
    numberOfLikesInput = element(by.css('input#field_numberOfLikes'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNumberOfReadBooksInput = function(numberOfReadBooks) {
        this.numberOfReadBooksInput.sendKeys(numberOfReadBooks);
    };

    getNumberOfReadBooksInput = function() {
        return this.numberOfReadBooksInput.getAttribute('value');
    };

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
