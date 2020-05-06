import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create test: discount schema set to customer', function() {
  let discountSchemaName;

  // test
  let bpartnerID;

  // it('WTF!??!!?', function() { // todo using an `it` block doesn't work on my machine, only with `before` the test is green. What The Actual Fuck?!?!?!
  before(function() {
    cy.fixture('masterdata/discount_schema_to_customer_spec.json').then(f => {
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
    });

    cy.fixture('discount/discountschema.json').then(discountschemaJson => {
      Object.assign(new DiscountSchema(), discountschemaJson)
        .setName(discountSchemaName)
        .apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson })
        .setCustomer(true)
        .setCustomerDiscountSchema(discountSchemaName)
        .clearLocations() // contacts&locations not needed in this test
        .clearContacts()
        .setBank(undefined); // no bank needed either

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  it('Check bpartner', function() {
    cy.visitWindow(123, bpartnerID);

    cy.selectTab('Customer');
    cy.log('Now going to verify that the discount schema was set correctly');
    cy.get('.table tbody td').should('exist');

    cy.get('.table tbody').should(el => {
      expect(el[0].innerHTML).to.include(discountSchemaName);
    });
  });
});
