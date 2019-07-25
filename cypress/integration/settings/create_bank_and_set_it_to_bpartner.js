import { Bank } from '../../support/utils/bank';
import { BPartner } from '../../support/utils/bpartner';

describe('Create Bank', function() {
  const timestamp = new Date().getTime();
  const bankName = `Bank ${timestamp}`;
  const BLZ = '80027';
  const customer1Name = `Customer ${timestamp}`;

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
      const bpartner = new BPartner({ ...customerJson, name: customer1Name })
        .setCustomer(true)
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
      .then(bankNameFieldvalue => {
        expect(bankNameFieldvalue).to.eq(`${bankName}_${BLZ}`);
      });
  });
});
