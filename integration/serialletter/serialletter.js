/// <reference types="Cypress" />

before(function() {
    // login before each test
    cy.loginByForm();
});

it('Serial letter', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const printFormatName = `${timestamp} Serial letter printformat`;
    const marketingPlatformName = `${timestamp} Serial letter marketing platform`;
    const boilerPlateName = `${timestamp}_Serial_letter_text_snippet`;
    
    const marketingCampaignName = `${timestamp} Serial letter marketing campaign`;
    const marketingContactName = `${timestamp} Serial letter marketing contact`;

//    describe('Create print format and doc outbound log', function() {
            cy.visit('/window/240/NEW');
            cy.writeIntoStringField('Name', printFormatName);
            cy.writeIntoLookupField('AD_Table_ID', 'Letter', 'Letter');
            cy.writeIntoLookupField('JasperProcess_ID', 'Serial Letter', 'Serial Letter');

            cy.visit('/window/540173/NEW');
            cy.writeIntoLookupField('AD_Table_ID', 'Letter', 'Letter');
            cy.writeIntoLookupField('AD_PrintFormat_ID', printFormatName, printFormatName);
            cy.clickOnCheckBox('IsCreatePrintJob');
            cy.wait(500);
//    });

//    describe('create Marketing platform', function() {
        cy.visit('/window/540437/NEW');
        cy.writeIntoStringField('Name', marketingPlatformName);
        cy.clickOnCheckBox('IsRequiredLocation');
        cy.wait(500);
//    })

    // create boiler plate (text-snippet)
    cy.visit('/window/504410/NEW');
    cy.writeIntoStringField('Name', boilerPlateName);
    cy.writeIntoStringField('Subject', 'Subject');
    cy.writeIntoTextField('TextSnippet', 'TextSnippet');
    cy.selectTab('AD_BoilerPlate_Ref'); // need to click somewhere outside of the text field

    // create contact person
    cy.visit('/window/540435/NEW');
    cy.writeIntoStringField('Name', marketingContactName);
    cy.writeIntoLookupField('MKTG_Platform_ID', marketingPlatformName, marketingPlatformName);
    cy.editAddress('C_Location_ID', function () {
        cy.writeIntoStringField('City', 'Cologne')
        cy.writeIntoLookupField('C_Country_ID', 'Deu', 'Deutschland');
    });
    
    // create marketing campaign
    cy.visit('/window/540434/NEW');
    cy.writeIntoStringField('Name', marketingCampaignName);
    cy.writeIntoLookupField('MKTG_Platform_ID', marketingPlatformName, marketingPlatformName);
    cy.writeIntoLookupField('AD_BoilerPlate_ID', boilerPlateName, boilerPlateName);

    // add contact person to campaign
    cy.selectTab('MKTG_Campaign_ContactPerson');
    cy.pressAddNewButton();
    cy.writeIntoLookupField('MKTG_ContactPerson_ID', marketingContactName, marketingContactName);
    cy.pressDoneButton();

    // needs https://github.com/metasfresh/metasfresh-webui-frontend/issues/1978#issuecomment-445274540
    // cy.executeProcess('C_Letter_CreateFrom_MKTG_ContactPerson');
});
