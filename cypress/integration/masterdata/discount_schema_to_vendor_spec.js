import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create test: discount schema set to vendor, https://github.com/metasfresh/metasfresh-e2e/issues/113', function() {
  const date = humanReadableNow();
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  let bpartnerID = null;

  before(function() {
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
        cy.visitWindow('123', bpartnerID);

        cy.selectTab('Vendor');
        cy.log('Now going to verify that the discount schema was set correctly');
        // Looking at the tab like this failed (sometimes?) in the docker image.
        // I believe that's because selectTab didn'T make sure to actually wait for the tab to be loaded.
        // Still even if that's fixed, a simple layout change would break this check.
        // Actually I would like a snapshot of the tab data, but since i'm now focussing on fixing what's there, i rather check only this field value
        // cy.get('table tr').eq(0).get('td').eq(5).should('contain', discountSchemaName);
        cy.get('.table tbody td').should('exist');

        cy.get('.table tbody').then(el => {
          expect(el[0].innerHTML).to.include(discountSchemaName);
        });
      });
    });
  });
});
