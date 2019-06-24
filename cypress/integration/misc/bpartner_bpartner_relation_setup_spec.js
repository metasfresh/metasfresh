import { BPartner } from '../../support/utils/bpartner';

describe('Create test: bpartner relation, https://github.com/metasfresh/metasfresh-e2e/issues/42', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const customer1Name = `Customer1 ${timestamp}`;
  const customer2Name = `Customer2 ${timestamp}`;

  //create customer2
  it('Create customer2', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customer2Name)
        .clearContacts()
        .apply();
    });
  });

  //create customer1
  it('Create customer1', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customer1Name)
        .clearContacts()
        .apply();
    });
  });

  //create bpartner relation
  it('Create a bpartner relation', function() {
    cy.executeHeaderActionWithDialog('CreateBPRelationFromDocument');
    cy.selectInListField('C_BPartner_Location_ID', 'Address1', true /*modal*/, '/rest/api/process/.*' /*rewriteUrl*/);
    cy.writeIntoLookupListField(
      'C_BPartnerRelation_ID',
      customer2Name,
      customer2Name,
      false /*typeList*/,
      true /*modal*/,
      '/rest/api/process/.*' /*rewriteUrl*/
    );
    cy.selectInListField(
      'C_BPartnerRelation_Location_ID',
      'Address1',
      true /*modal*/,
      '/rest/api/process/.*' /*rewriteUrl*/
    );

    cy.getCheckboxValue('IsBillTo');
    cy.clickOnCheckBox('IsShipTo', 'checked', true /*modal*/, '/rest/api/process/.*' /*rewriteUrl*/);
    cy.pressStartButton();
  });
});
