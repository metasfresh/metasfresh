import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';

describe('Create test: discount schema set to bpartner, https://github.com/metasfresh/metasfresh-e2e/issues/113', function() {
  //create bpartner
  it('Create bpartner', function() {
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson).apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setCustomer(true)
        .setCustomerDiscountSchema('DiscountSchemaTest')
        .apply();
    });

    cy.log('Now going to verify that the discount schema was set correctly');
    cy.selectTab('Customer');
    cy.get('table tr')
      .eq(0)
      .get('td')
      .eq(7)
      //.debug();
      .should('contain', 'DiscountSchemaTest');
  });
});
