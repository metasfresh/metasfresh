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
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customer1Name)
        .setBank(bankName)
        .clearContacts() // save time by not creating locations and contacts which are not of this spec's concern right now
        .clearLocations()
        .apply();
    });
  });
});
