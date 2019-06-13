import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';

describe('Create test: discount schema set to bpartner, https://github.com/metasfresh/metasfresh-e2e/issues/113', function() {
  //create bpartner
  it('Create bpartner', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setCustomer(true)
        .setCustomerDiscountSchema(
          it('Create discount schema', function() {
            cy.fixture('discount/discountschema.json').then(customerJson => {
              Object.assign(new DiscountSchema(), customerJson).apply();
            });
          })
        )
        .apply();
    });
    cy.log('Now going to verify that the discount schema was set correctly');
    cy.get('#tab_Customer').select(
      '.text-left td-lg List pulse-off.cell-text-wrapper list-cell',
      'DiscountSchemaTest',
      false /*modal*/
    );
  });
});
