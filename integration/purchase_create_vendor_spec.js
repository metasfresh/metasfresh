describe('purchase order Test', function() {
    before(function() {
        // login before each test
        cy.loginByForm();
    });

    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const vendorValue = `${timestamp} (Cypress Test)`;

    it('Create a vendor with two contacts', function() {

            cy.visit('/window/123/NEW');
            cy.writeIntoStringField('Value', `{selectall}{backspace}${vendorValue}`);
            cy.writeIntoStringField('CompanyName', 'CompanyName');

            cy.selectTab('Vendor');
            cy.selectSingleTabRow();

            cy.openAdvancedEdit();
            cy.clickOnCheckBox('IsVendor');
            cy.pressDoneButton();

            // note that the partner needs an address to be eligible in the purchase order without causing an error
            cy.selectTab('C_BPartner_Location');
            cy.pressAddNewButton();
            cy.writeIntoStringField('Name', 'Address');
            
            // TODO: extract into a command
            cy.get(`.form-field-C_Location_ID`).find('button').click();

            cy.writeIntoLookupListField('C_Country_ID', 'Deu', 'Deutschland')
                .writeIntoStringField('City', 'Cologne')

            /*
            Here the test fails for me; i get
CypressError: Timed out retrying: cy.click() failed because this element is not visible:

<button tabindex="0" class="btn btn-block tag tag-lg tag-block tag-secondary pointer tag-disabled">Edit</button>

This element '<button.btn.btn-block.tag.tag-lg.tag-block.tag-secondary.pointer.tag-disabled>' is not visible because it has CSS property: 'position: fixed' and its being covered by another element:

<div class="attributes attributes-in-table">...</div>

Fix this problem, or use {force: true} to disable error checking.

https://on.cypress.io/element-cannot-be-interacted-with

            I also tried "force", but that didn't work eiter ("do you really want to leave?")
            In real life, i can just click on the "Edit"-button once more an that's it.
            */
            .get(`.form-field-C_Location_ID`).find('button').click(); 

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
    });
});
