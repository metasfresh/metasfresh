import { BPartner } from '../../support/utils/bpartner';

describe('Create new bpartner group, https://github.com/metasfresh/metasfresh-e2e/issues/70', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const GroupTestPartnerName = `GroupTestPartnerName ${timestamp}`;
  const GroupName = `TestGroup ${timestamp}`;

  //create bpartnergroup
  it('Create bpartnergroup', function() {
    cy.visitWindow('192', 'NEW');
    cy.writeIntoStringField('Name', `TestGroup ${timestamp}`, false);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', `TestGroup ${timestamp}`, false);
  });

  //create bpartner
  it('Create Testpartner', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(GroupTestPartnerName)
        .clearContacts()
        .apply();
      cy.selectInListField('C_BP_Group_ID', GroupName, false /*modal*/, '/rest/api/window/.*' /*rewriteUrl*/);
    });
  });

  //check bpartnergroup
  it('Check bpartnergroup', function() {
    cy.visitWindow('192');
    cy.get('.cell-text-wrapper.text-cell')
      .contains(GroupName)
      .dblclick();
    cy.selectTab('C_BPartner');
    cy.get('.cell-text-wrapper.text-cell').contains(GroupTestPartnerName);
  });
});
