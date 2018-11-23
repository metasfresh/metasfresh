describe('purchase order Test', function() {
    before(function() {
        // login before each test
        cy.loginByForm();
    });

    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const poReference = `${timestamp} (Cypress Test)`;

    it('Create a purchase order record', function() {
        
            cy.visit('/window/181/NEW');

            cy.writeIntoStringField('POReference', poReference);

            cy.writeIntoCompositeLookupField('C_BPartner_ID','G0002', 'Test Lieferant');

            // TODO: I don't see a reason why we would like to fill this field, if it's pre-filled
            // automatically. But maybe I'm missing something here.  - Kuba
            // cy.writeIntoCompositeLookupField('C_BPartner_Location_ID', 'Testaddresse');
            cy.writeIntoCompositeLookupField('AD_User_ID', 'Testensen', 'Testensen');

            cy.clickOnCheckBox('IsDropShip');
    });

});
