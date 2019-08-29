import { Bank } from '../../support/utils/bank';
import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create Bank', function() {
  let bankName;
  let customerName;
  let BLZ;

  it('Read the fixture', function() {
    cy.fixture('settings/create_bank_and_set_it_to_bpartner.json').then(f => {
      bankName = appendHumanReadableNow(f['bankName']);
      customerName = appendHumanReadableNow(f['customerName']);
      BLZ = f['BLZ'];
    });
  });

  it('Create Bank', function() {
    cy.fixture('finance/bank.json').then(productJson => {
      Object.assign(new Bank(), productJson)
        .setName(bankName)
        .setBLZ(BLZ)
        .apply();
    });
  });

  let bpartnerID = null;
  it('Create customer', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customerName })
        .clearLocations()
        .clearContacts()
        .setBank(bankName);

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  it('Verfify customer has bank', function() {
    cy.visitWindow('123', bpartnerID);

    cy.selectTab('C_BP_BankAccount')
      .selectSingleTabRow()
      .openAdvancedEdit()
      .getStringFieldValue('C_Bank_ID', true)
      .then(bankNameFieldValue => {
        expect(bankNameFieldValue).to.eq(`${bankName}_${BLZ}`);
      });
  });
});
