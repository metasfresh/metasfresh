import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create test: discount schema set to vendor, https://github.com/metasfresh/metasfresh-e2e/issues/113', function() {
  let discountSchemaName;

  // test
  let bpartnerID;

  it('Read the fixture', function() {
    cy.fixture('masterdata/discount_schema_to_vendor_spec.json').then(f => {
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
    });
  });

  it('Create Discount Schema', function() {
    cy.fixture('discount/discountschema.json').then(discountschemaJson => {
      Object.assign(new DiscountSchema(), discountschemaJson)
        .setName(discountSchemaName)
        .apply();
    });
  });

  it('Set discount schema to vendor', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson })
        .setVendor(true)
        .setVendorDiscountSchema(discountSchemaName)
        .clearLocations()
        .clearContacts()
        .setBank(undefined);

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
        cy.visitWindow(123, bpartnerID);

        cy.selectTab('Vendor');
        cy.log('Now going to verify that the discount schema was set correctly');
        cy.get('.table tbody td').should('exist');

        cy.get('.table tbody').should(el => {
          expect(el[0].innerHTML).to.include(discountSchemaName);
        });
      });
    });
  });
});
