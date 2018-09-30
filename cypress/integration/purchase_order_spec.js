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

            cy.get('.input-dropdown-container .raw-lookup-wrapper')
                .first()
                .find('input')
                .clear()
                .type('G0001');
            // these two shall work as of issue https://github.com/metasfresh/metasfresh-webui-frontend/issues/1981
            // cy.writeIntoLookupListField('C_BPartner_ID', 'Test L', 'Test Lieferant');
            // cy.selectInListField('C_BPartner_Location_ID', 'Testaddresse');

            cy.clickOnCheckBox('IsDropShip');
    });

});
