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

  it('Create customer', function() {
    let bpartnerID = null;

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customer1Name })
        .setCustomer(true)
        .clearLocations()
        .clearContacts()
        .setBank(bankName);

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;

        cy.visitWindow('123', bpartnerID);
      });
    });
  });
});
