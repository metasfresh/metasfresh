import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';
import { SalesOrder } from '../../support/utils/sales_order_api';

describe('BPartner relations', function() {
  const timestamp = new Date().getTime();
  const customer1Name = `Customer1_${timestamp}`;
  const customer2Name = `Customer2_${timestamp}`;
  const salesOrder1Name = `SalesOrder1-${timestamp}`;
  const bPartnerRelationName = `BPartnerRelation_${timestamp}`;
  const priceListName = 'Testpreisliste Kunden';
  const discountSchemaName = `DiscountSchemaTest ${timestamp}`;
  let relationID = null;

  before(function() {
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
    cy.writeIntoLookupListField('C_BPartner_Location_ID', 'Address1', 'Address1', true);
    cy.writeIntoLookupListField('C_BPartnerRelation_ID', customer2Name, customer2Name);
    cy.writeIntoLookupListField('C_BPartnerRelation_Location_ID', 'Address1', 'Address1', true);
  });

  it('Set bpartner relation inactive', function() {
    cy.visitWindow(313, relationID);

    cy.clickOnIsActive();

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
