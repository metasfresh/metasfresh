import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Pricesystem } from '../../support/utils/pricesystem';
import { PriceList, PriceListVersion } from '../../support/utils/pricelist';
import { purchaseOrders } from '../../page_objects/purchase_orders';
import { Builder } from '../../support/utils/builder';

describe('Create Sales order', function() {
  const timestamp = new Date().getTime();
  const productForPackingMaterial = `ProductPackingMaterial ${timestamp}`;
  const productPMValue = `purchase_order_testPM ${timestamp}`;
  const packingMaterialName = `ProductPackingMaterial ${timestamp}`;
  const packingInstructionsName = `ProductPackingInstrutions ${timestamp}`;
  const productName1 = `ProductTest ${timestamp}`;
  const productValue1 = `purchase_order_test ${timestamp}`;
  const productName2 = `ProductTest ${timestamp}`;
  const productValue2 = `purchase_order_test ${timestamp}`;
  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductCategoryValue ${timestamp}`;
  const discountSchemaName = `DiscountSchemaTest ${timestamp}`;
  const priceSystemName = `PriceSystem ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const priceListVersionName = `PriceListVersion ${timestamp}`;
  const productType = 'Item';
  const vendorName = `Vendor ${timestamp}`;

  before(function() {
    // cy.fixture('price/pricesystem.json').then(priceSystemJson => {
    //   Object.assign(
    //     new Pricesystem(/* useless to set anything here since it's replaced by the fixture */),
    //     priceSystemJson
    //   )
    //     .setName(priceSystemName)
    //     .apply();
    // });

    // let priceListVersion;
    // cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
    //   priceListVersion = Object.assign(
    //     new PriceListVersion(/* useless to set anything here since it's replaced by the fixture */),
    //     priceListVersionJson
    //   ).setName(priceListVersionName);
    // });

    // cy.fixture('price/pricelist.json').then(pricelistJson => {
    //   Object.assign(new PriceList(/* useless to set anything here since it's replaced by the fixture */), pricelistJson)
    //     .setName(priceListName)
    //     .setPriceSystem(priceSystemName)
    //     .setSalesPriceList(false)
    //     .addPriceListVersion(priceListVersion)
    //     .apply();
    // });
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    // cy.fixture('product/simple_product.json').then(productJson => {
    //   Object.assign(new Product(), productJson)
    //     .setName(productForPackingMaterial)
    //     .setValue(productPMValue)
    //     .setProductType(productType)
    //     .setProductCategory('24_Gebinde')
    //     .apply();
    // });
    Builder.createBasicProductEntitiesWithPrice(priceListName, productForPackingMaterial, productPMValue, productType);
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(packingMaterialName)
        .setProduct(productForPackingMaterial)
        .apply();
    });
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsName)
        .apply();
    });
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsName)
        .setPackingInstructions(packingInstructionsName)
        .setPackingMaterial(packingMaterialName)
        .apply();
    });
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
    // let productPrice;
    // cy.fixture('product/product_price.json').then(productPriceJson => {
    //   productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    // });
    // cy.fixture('product/simple_product.json').then(productJson => {
    //   Object.assign(new Product(), productJson)
    //     .setName(productName1)
    //     .setValue(productValue1)
    //     .setProductCategory(productCategoryValue + '_' + productCategoryName)
    //     .setStocked(true)
    //     .setPurchased(true)
    //     .setSold(true)
    //     .addProductPrice(priceListName)
    //     .setCUTUAllocation(packingInstructionsName)
    //     .apply();
    // });

    // cy.fixture('product/simple_product.json').then(productJson => {
    //   Object.assign(new Product(), productJson)
    //     .setName(productName2)
    //     .setValue(productValue2)
    //     .setProductCategory(productCategoryValue + '_' + productCategoryName)
    //     .setStocked(true)
    //     .setPurchased(true)
    //     .setSold(true)
    //     .addProductPrice(productPrice)
    //     .setCUTUAllocation(packingInstructionsName)
    //     .apply();
    // });
    Builder.createBasicProductEntitiesWithCUTUAllocation(
      productCategoryName,
      productCategoryValue,
      priceListName,
      productName1,
      productValue1,
      productType,
      packingInstructionsName
    );
    Builder.createBasicProductEntitiesWithCUTUAllocation(
      productCategoryName,
      productCategoryValue,
      priceListName,
      productName2,
      productValue2,
      productType,
      packingInstructionsName
    );
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
    cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName1);

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
    cy.wait(8000);

    cy.get('.quick-input-container .form-group').should('exist');
    cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName2);

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
    cy.wait(8000);
    /**Complete purchase order */
    cy.get('.form-field-DocAction ul')
      .click({ force: true })
      .get('li')
      .eq('1')
      .click({ force: true });
    cy.wait(8000);
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    /** Go to Material Receipt Candidates*/
    cy.get('.reference_M_ReceiptSchedule').click();
    cy.get('tbody tr')
      .eq('0')
      .click();
  });
});
