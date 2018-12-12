describe('purchase order Test', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const vendorValue = `${timestamp} (Cypress Test)`;

  it('Create a vendor with two contacts', function() {
    cy.visit('/window/123/NEW');
    // cy.writeIntoStringField('Value', `{selectall}{backspace}${vendorValue}`);
    cy.writeIntoStringField('CompanyName', vendorValue);
    cy.writeIntoStringField('Name2', 'CompanyName');

    cy.selectTab('Vendor');
    cy.selectSingleTabRow();

    cy.openAdvancedEdit();
    cy.clickOnCheckBox('IsVendor');
    cy.pressDoneButton();

    // note that the partner needs an address to be eligible in the purchase order without causing an error
    // TODO: Use proper tab name when rolled out - Kuba
    cy.selectTab('C_BPartner_Location');
    cy.pressAddNewButton();
    cy.writeIntoStringField('Name', 'Address1');
    
    cy.editAddress('C_Location_ID', function a() {
        cy.writeIntoStringField('City', 'Cologne')
        cy.writeIntoLookupField('C_Country_ID', 'Deu', 'Deutschland');
        // cy.get('.panel-modal-header').click();
    });
    cy.get('.form-field-Address')
        .should('contain', 'Cologne');
    cy.pressDoneButton();  

      cy.selectTab('AD_User');
      cy.pressAddNewButton();
      cy.writeIntoStringField('Firstname', 'Default');
      cy.writeIntoStringField('Lastname', 'Contact');
      cy.clickOnCheckBox('IsDefaultContact');
      cy.pressDoneButton();

      cy.selectTab('AD_User');
      cy.pressAddNewButton();
      cy.writeIntoStringField('Firstname', 'Secondary');
      cy.writeIntoStringField('Lastname', 'Contact');
      cy.pressDoneButton();

      cy.get('table tbody tr')
        .should('have.length', 2);
  });
});
