/// <reference types="Cypress" />

describe('New boiler plate (textsnippet) test', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const boilerPlateName = `${timestamp}_Name_Cypress_Test`; // name needs to match regex ^[a-zA-Z0-9-_+]+?
    const boilerPlateSubject = `${timestamp} Subject (Cypress Test)`;
    const boilerPlateTextSnippet = `${timestamp} TextSnippet (Cypress Test)`;

    it('Create a new boiler plate record', function() {

        cy.log('Create a new boiler plate record')
        cy.visit('window/504410/NEW');

        cy.writeIntoStringField('Name', boilerPlateName);
        cy.writeIntoStringField('Subject', boilerPlateSubject);
        cy.writeIntoTextField('TextSnippet', boilerPlateTextSnippet);
        cy.screenshot();

        cy.log('There shall be no button, because lang-records are not inserted via UI')
        cy.selectTab('AD_BoilerPlate_Trl');
        cy.get('.btn').should('not.exist');

        const trlTextSnippet = `en_US trl of ${boilerPlateTextSnippet}`;
        cy.get('table').contains('td', trlTextSnippet).should('not.exist')

        cy.log('Select the "English (US)" translation trecord and provide a trl')     
        cy.get('table').contains('td', 'English (US)').click();
        cy.openAdvancedEdit()
        cy.writeIntoTextField('TextSnippet', `{selectall}{backspace}${trlTextSnippet}`, true/*modal*/)  // this fails because we now have two "TextSnippet" fields on the page
        cy.clickOnCheckBox('IsTranslated')
        cy.pressDoneButton();

        cy.get('table').contains('td', trlTextSnippet).should('exist')
   });
});