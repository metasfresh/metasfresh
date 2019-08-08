import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create new bpartner group, https://github.com/metasfresh/metasfresh-e2e/issues/70', function() {
  const date = humanReadableNow();
  const GroupTestPartnerName = `GroupTestPartnerName ${date}`;
  const GroupName = `TestGroup ${date}`;
  let groupDocumentId;
  let bpartnerID = null;

  before(function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: GroupTestPartnerName })
        .setCustomer(true)
        .clearLocations()
        .clearContacts()
        .setBank(undefined);

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  //create bpartnergroup
  it('Create bpartnergroup', function() {
    cy.visitWindow('192', 'NEW', 'newGroupId');
    cy.writeIntoStringField('Name', `TestGroup ${date}`, false);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', `TestGroup ${date}`, false);
    cy.get('@newGroupId').then(groupId => {
      groupDocumentId = groupId.documentId;
    });
  });

  //create bpartner
  it('Create Testpartner', function() {
    cy.visitWindow('123', bpartnerID);
    cy.selectInListField('C_BP_Group_ID', GroupName, false /*modal*/, '/rest/api/window/.*' /*rewriteUrl*/);
  });

  //check bpartnergroup
  it('Check bpartnergroup', function() {
    cy.visitWindow('192', groupDocumentId);

    cy.selectTab('C_BPartner');
    cy.get('.cell-text-wrapper.text-cell').contains(GroupTestPartnerName);
  });
});
