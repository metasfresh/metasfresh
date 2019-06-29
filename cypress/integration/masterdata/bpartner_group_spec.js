import { BPartner } from '../../support/utils/bpartner';

describe('Create new bpartner group, https://github.com/metasfresh/metasfresh-e2e/issues/70', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const GroupTestPartnerName = `GroupTestPartnerName ${timestamp}`;
  const GroupName = `TestGroup ${timestamp}`;
  let groupDocumentId;

  //create bpartnergroup
  it('Create bpartnergroup', function() {
    cy.visitWindow('192', 'NEW', 'newGroupId');
    cy.writeIntoStringField('Name', `TestGroup ${timestamp}`, false);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', `TestGroup ${timestamp}`, false);
    cy.get('@newGroupId').then(groupId => {
      groupDocumentId = groupId.documentId;
    });
  });

  //create bpartner
  it('Create Testpartner', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(GroupTestPartnerName)
        .clearContacts() // we don't care about contract, location or bank, so let's save some time..
        .clearLocations()
        .setBank(undefined)
        .apply();
      cy.selectInListField('C_BP_Group_ID', GroupName, false /*modal*/, '/rest/api/window/.*' /*rewriteUrl*/);
    });
  });

  //check bpartnergroup
  it('Check bpartnergroup', function() {
    cy.visitWindow('192', groupDocumentId);

    cy.selectTab('C_BPartner');
    cy.get('.cell-text-wrapper.text-cell').contains(GroupTestPartnerName);
  });
});
