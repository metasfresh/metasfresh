import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Builder } from '../../support/utils/builder';
import { Pricesystem } from '../../support/utils/pricesystem';
import { PriceList, PriceListVersion } from '../../support/utils/pricelist';
import { purchaseOrders } from '../../page_objects/purchase_orders';

describe('Create Sales order', function() {
  const timestamp = new Date().getTime();
  const productName = `ProductTest ${timestamp}`;
  const productValue = `sales_order_test ${timestamp}`;
  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductCategoryValue ${timestamp}`;
  const discountSchemaName = `DiscountSchemaTest ${timestamp}`;
  const priceSystemName = `PriceSystem ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const priceListVersionName = `PriceListVersion ${timestamp}`;
  const productType = 'Item';
  const vendorName = `Vendor ${timestamp}`;

  before(function() {
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(
        new Pricesystem(/* useless to set anything here since it's replaced by the fixture */),
        priceSystemJson
      )
        .setName(priceSystemName)
        .apply();
    });

    let priceListVersion;
    cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
      priceListVersion = Object.assign(
        new PriceListVersion(/* useless to set anything here since it's replaced by the fixture */),
        priceListVersionJson
      ).setName(priceListVersionName);
    });

    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList(/* useless to set anything here since it's replaced by the fixture */), pricelistJson)
        .setName(priceListName)
        .setPriceSystem(priceSystemName)
        .setSalesPriceList(false)
        .addPriceListVersion(priceListVersion)
        .apply();
    });
    Builder.createBasicProductEntities(
      productCategoryName,
      productCategoryValue,
      priceListName,
      productName,
      productValue,
      productType
    );
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    new BPartner({ name: vendorName })
      .setVendor(true)
      .setVendorPricingSystem(priceSystemName)
      .setVendorDiscountSchema(discountSchemaName)
      .setPaymentTerm('30 days net')
      .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
      .apply();

    cy.readAllNotifications();
  });
  it('Create a sales order', function() {
    cy.visitWindow('181', 'NEW');
    cy.get('#lookup_C_BPartner_ID input')
      .type(vendorName)
      .type('\n');
    cy.contains('.input-dropdown-list-option', vendorName).click();

    cy.selectInListField('M_PricingSystem_ID', priceSystemName, false, null, true);
    cy.writeIntoStringField('POReference', 'test', false, null, true);
    const addNewText = Cypress.messages.window.batchEntry.caption;
    cy.get('.tabs-wrapper .form-flex-align .btn')
      .contains(addNewText)
      .should('exist')
      .click();
    // cy.wait(8000);
    cy.get('.quick-input-container .form-group').should('exist');
    cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName);

    cy.get('.form-field-Qty')
      .click()
      .find('.input-body-container.focused')
      .should('exist')
      .find('i')
      .eq(0)
      .click();
    cy.server();
    cy.route('POST', `/rest/api/window/${purchaseOrders.windowId}/*/${purchaseOrders.orderLineTabId}/quickInput`).as(
      'resetQuickInputFields'
    );
    cy.get('.form-field-Qty')
      .find('input')
      .should('have.value', '0.1')
      .type('15{enter}');
    cy.wait(3000);

    cy.get('.quick-input-container .form-group').should('exist');
    cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName);

    cy.get('.form-field-Qty')
      .click()
      .find('.input-body-container.focused')
      .should('exist')
      .find('i')
      .eq(0)
      .click();
    cy.server();
    cy.route('POST', `/rest/api/window/${purchaseOrders.windowId}/*/${purchaseOrders.orderLineTabId}/quickInput`).as(
      'resetQuickInputFields'
    );
    cy.get('.form-field-Qty')
      .find('input')
      .should('have.value', '0.1')
      .type('25{enter}');
    cy.wait(3000);
    /**Complete purchase order */
    cy.get('.form-field-DocAction ul')
      .click({ force: true })
      .get('li')
      .eq('1')
      .click({ force: true });

  });
});
