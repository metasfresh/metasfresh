describe('material tracking test', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering
    
    const lagerKonfName = `${timestamp} Lagerkonf (Cypress Test)`;
    const lot = `${timestamp} (Cypress Test)`;

    it('Create a Lager-Konferenz and material tracking record', function() {
    
        cy.visit('/window/540230/NEW');
    
        cy.writeIntoStringField('Name', `${lagerKonfName}{enter}`);
        cy.pressAddNewButton();
        cy.writeIntoStringField('ValidFrom','01/11/2018{enter}');
        cy.writeIntoStringField('ValidTo','01/11/2019{enter}');

        cy.writeIntoLookupListField('M_Product_Scrap_ID','P002749','P002749_Erdbesatz');
        cy.selectInListField('C_UOM_Scrap_ID', 'Kilogramm');

        cy.writeIntoLookupListField('M_Product_ProcessingFee_ID', 'P002750', 'P002750_Futterkarotten');

        cy.writeIntoLookupListField('M_Product_Witholding_ID', 'P002751', 'P002751_Akonto (Einbehalt)');

        cy.writeIntoLookupListField('M_Product_RegularPPOrder_ID', 'P002752', 'P002752_Produkt für Auslagerung');
        cy.pressDoneButton();
        
        cy.visit('/window/540226/NEW');

        cy.writeIntoStringField('Lot', lot);

        cy.writeIntoLookupListField('M_Product_ID', 'Convenience', 'Convenience Salat 250g_P002737');
        cy.writeIntoLookupListField('C_BPartner_ID', 'Test Liefer', 'Test Lieferant 1_G0002');

        cy.selectInListField('M_QualityInsp_LagerKonf_Version_ID', lagerKonfName);
    });

});
