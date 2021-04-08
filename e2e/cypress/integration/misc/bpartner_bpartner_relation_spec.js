import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';
import { SalesOrder } from '../../support/utils/sales_order_api';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('BPartner relations', function() {
  let customer1Name;
  let customer2Name;
  let salesOrder1Name;
  let bPartnerRelationName;
  let priceListName;
  let discountSchemaName;
  let relationID = null;

  it('Read fixture and prepare the names', function() {
    cy.fixture('misc/bpartner_bpartner_relation_spec.json').then(f => {
      customer1Name = appendHumanReadableNow(f['customer1Name']);
      customer2Name = appendHumanReadableNow(f['customer2Name']);
      salesOrder1Name = appendHumanReadableNow(f['salesOrder1Name']);
      bPartnerRelationName = appendHumanReadableNow(f['bPartnerRelationName']);
      priceListName = f['priceListName'];
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
    });
  });

  it('Create discount schema,customers and a sales order', function() {
    cy.fixture('discount/discountschema.json').then(discountschemaJson => {
      Object.assign(new DiscountSchema(), discountschemaJson)
        .setName(discountSchemaName)
        .apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customer1Name })
        .setCustomerDiscountSchema(discountSchemaName)
        .setCustomerPricingSystem(priceListName)
        .clearContacts()
        .apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customer2Name })
        .setCustomerDiscountSchema(discountSchemaName)
        .setCustomerPricingSystem(priceListName)
        .clearContacts()
        .apply();
    });

    cy.fixture('sales/sales_order.json').then(orderJson => {
      new SalesOrder({ ...orderJson, reference: salesOrder1Name })
        .setBPartner(customer1Name)
        .setInvoicePartner(customer2Name)
        .apply();
    });
  });

  it('Create a bpartner relation', function() {
    cy.visitWindow(313, 'NEW');
    cy.getCurrentWindowRecordId().then(id => (relationID = id));

    cy.writeIntoStringField(`Name`, bPartnerRelationName);
    cy.writeIntoLookupListField('C_BPartner_ID', customer1Name, customer1Name);
    cy.writeIntoLookupListField('C_BPartnerRelation_ID', customer2Name, customer2Name);
  });

  it('Set bpartner relation inactive', function() {
    cy.visitWindow(313, relationID);
    cy.clickOnIsActive();
    cy.get('.indicator-saved', { timeout: 10000 }).should('exist');
    cy.visitWindow('143', 'NEW');

    cy.writeIntoLookupListField('C_BPartner_ID', customer1Name, customer1Name);
    const path = `#lookup_Bill_BPartner_ID`;
    cy.get(path).within(el => {
      if (el.find('.lookup-widget-wrapper input').length) {
        return cy
          .get('input')
          .type('{selectall}')
          .type(customer2Name);
      }

      return cy.get('.lookup-dropdown').click();
    });

    cy.get('.input-dropdown-list').should('exist');
    cy.contains('.input-dropdown-list-option', customer2Name).should(`not.exist`);
  });
});
