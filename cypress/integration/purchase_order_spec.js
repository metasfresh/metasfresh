describe('purchase order Test', function() {
    before(function() {
        // login before each test
        cy.loginByForm();
    });

    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const poReference = `${timestamp} (Cypress Test)`;
    const contactLastName = `${timestamp} (Cypress Test)`;

    const vendorValue = 'G0002'; // "Test Lieferant"


    it('Add another contact to Test-Vendor, then a purchase order', function() {

            cy.visit('/window/123/2156423');

            cy.selectTab('AD_User');
            cy.pressAddNewButton();
            cy.writeIntoStringField('Firstname', 'Other-Contact');
            cy.writeIntoStringField('Lastname', contactLastName);
            cy.pressDoneButton();

            cy.visit('/window/181/NEW');
            
            cy.writeIntoLookupField('C_BPartner_ID', vendorValue, vendorValue);
            // AD_User should be pre-filled with 'default contact';
            cy.writeIntoLookupField('AD_User_ID', contactLastName, contactLastName);
            
            cy.writeIntoStringField('POReference', poReference);
            cy.clickOnCheckBox('IsDropShip');
    });

});
