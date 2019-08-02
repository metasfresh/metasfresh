import { DiscountSchema } from '../../support/utils/discountschema';
import { BPartner } from '../../support/utils/bpartner';

describe('BPartner relations', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const customer1Name = `Customer1_${timestamp}`;
  const customer2Name = `Customer2_${timestamp}`;
  const bPartnerRelationName = `BPartnerRelation_${timestamp}`;
  const priceListName = 'Testpreisliste Kunden';
  const discountSchemaName = `DiscountSchemaTest ${timestamp}`;

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
  });

  it('Create a bpartner relation', function() {
    cy.visitWindow('313', 'NEW');

    cy.writeIntoStringField(`Name`, bPartnerRelationName);
    cy.writeIntoLookupListField('C_BPartner_ID', customer1Name, customer1Name);
    cy.writeIntoLookupListField('C_BPartner_Location_ID',
      'Address1',
      'Address1',
      true
    );
    cy.writeIntoLookupListField(
      'C_BPartnerRelation_ID',
      customer2Name,
      customer2Name,
    );
    cy.writeIntoLookupListField(
      'C_BPartnerRelation_Location_ID',
      'Address1',
      'Address1',
      true
    );
  });
});
