import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

let groupTestPartnerName;
let groupName;
let groupDocumentId;
let bpartnerID;

it('Read fixture and prepare the names', function () {
  cy.fixture('masterdata/bpartner_group_spec.json').then((f) => {
    groupTestPartnerName = appendHumanReadableNow(f['groupTestPartnerName']);
    groupName = appendHumanReadableNow(f['groupName']);
  });
});
describe('Create new bpartner group', function () {
  it('Create bpartner', function () {
    cy.fixture('sales/simple_customer.json').then((customerJson) => {
      const bpartner = new BPartner({ ...customerJson, name: groupTestPartnerName }).clearLocations().clearContacts();
      bpartner.apply().then((bpartner) => {
        bpartnerID = bpartner.id;
      });
    });
  });

  //create bpartnergroup
  it('Create bpartnergroup', function () {
    cy.visitWindow('192', 'NEW', 'newGroupId');
    cy.writeIntoStringField('Name', groupName, false);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', groupName, false);

    cy.getCurrentWindowRecordId().then((id) => (groupDocumentId = id));
  });

  //create bpartner
  it('Create Testpartner', function () {
    cy.visitWindow('123', bpartnerID);
    cy.selectInListField('C_BP_Group_ID', groupName, false);
  });

  //check bpartnergroup
  it('Check bpartnergroup', function () {
    cy.visitWindow('192', groupDocumentId);

    cy.selectTab('C_BPartner');
    cy.get('.cell-text-wrapper.text-cell').contains(groupTestPartnerName);
  });
});
