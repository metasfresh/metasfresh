const fillFormInModal = (productFromList, quantity) => {
  cy.pressAddNewButton();
  cy.writeIntoLookupListField('M_Product_ID', 'prod', productFromList, true);

  cy.writeIntoStringField('QtyCount', quantity, true);
  cy.selectInListField('PP_Plant_ID', 'test', true);
  cy.pressDoneButton();
};

describe('create new stock control purchase', function() {
  before(function() {
    cy.visit('/window/540253');
  });

  it('create process', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Description', 'test');
  });

  it('first product', function() {
    fillFormInModal('ProductName 1560426460548_1000003', '100');
  });

  it('second product', function() {
    fillFormInModal('ProductName 1560502091488_1000012', '200');
  });

  it('third product', function() {
    fillFormInModal('ProductName 1560502118834_1000013', '50');
  });

  it('header actions', function() {
    cy.executeHeaderAction('Fresh_QtyOnHand_UpdateSeqNo');
  });

  it('processed activate', function() {
    cy.clickOnCheckBox('Processed');
  });
});
