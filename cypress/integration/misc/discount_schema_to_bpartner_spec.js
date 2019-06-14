import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';

describe('Create test: discount schema set to bpartner, https://github.com/metasfresh/metasfresh-e2e/issues/113', function() {
  const timestamp = new Date().getTime();
  const discountSchemaName = `DiscountSchemaTest ${timestamp}`;
  it('Create discount schema and set it to bpartner', function() {
    cy.fixture('discount/discountschema.json')
      .then(discountschemaJson => {
        Object.assign(new DiscountSchema(), discountschemaJson)
          .setName(discountSchemaName)
          .apply();
      })
      .debug();
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setCustomer(true)
        .setCustomerDiscountSchema(discountSchemaName)
        .apply();
    });

    cy.selectTab('Customer');
    cy.log('Now going to verify that the discount schema was set correctly');
    cy.get('table tr')
      .eq(0)
      .get('td')
      .eq(7)
      .should('contain', discountSchemaName);
  });
});
