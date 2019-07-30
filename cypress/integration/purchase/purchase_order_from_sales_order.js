import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { ProductCategory, ProductPrice, Product } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';

describe('Create Purchase order from sales order', function() {
  const date = humanReadableNow();
  const productForPackingMaterial = `ProductPackingMaterial ${date}`;
  const productPMValue = `purchase_order_testPM ${date}`;
  const packingMaterialName = `ProductPackingMaterial ${date}`;
  const packingInstructionsName = `ProductPackingInstrutions ${date}`;
  const productName1 = `ProductTest ${date}`;
  const productValue1 = `purchase_order_test ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
  const productCategoryValue = `ProductCategoryValue ${date}`;
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  const purchasePriceSystem = `PurchasePriceSystem ${date}`;
  const purchasePriceList = `PurchasePriceList ${date}`;
  const purchasePriceListVersion = `PurchasePriceListVersion ${date}`;
  const salesPriceSystem = `SalesPriceSystem ${date}`;
  const salesPriceList = `SalesPriceList ${date}`;
  const salesPriceListVersion = `SalesPriceListVersion ${date}`;
  const productType = 'Item';
  const vendorName = `Vendor ${date}`;
  const customerName = `Customer ${date}`;

  it('Create price and product entities to be used in purchase order', function() {
    /**purchase price list */
    Builder.createBasicPriceEntities(purchasePriceSystem, purchasePriceListVersion, purchasePriceList, false);
    /**sales price list */
    Builder.createBasicPriceEntities(salesPriceSystem, salesPriceListVersion, salesPriceList, true);
    /**Create product for packing material which has both a purchase price list and a sales price list */
    let productPricePM1;
    let productPricePM2;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPricePM1 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(purchasePriceList);
    });
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPricePM2 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(salesPriceList);
    });
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productForPackingMaterial)
        .setValue(productPMValue)
        .setProductType(productType)
        .setProductCategory('24_Gebinde')
        .addProductPrice(productPricePM1)
        .addProductPrice(productPricePM2)
        .apply();
    });
  });
  it('Create packing related entities to be used in purchase order', function() {
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
  });
  it('Create product category, product, vendor and customer entities to be used in purchase order', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
    /**Create vendor to use in product - Business partner tab - current vendor */
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendor(true)
        .setVendorPricingSystem(purchasePriceSystem)
        .setVendorDiscountSchema(discountSchemaName)
        .setVendorPaymentTerm('30 days net')
        .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
        .apply();
    });
    /**Create customer for sales order */
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customerName })
        .setCustomer(true)
        .setCustomerPricingSystem(salesPriceSystem)
        .setCustomerDiscountSchema(discountSchemaName)
        .setPaymentTerm('30 days net')
        .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
        .apply();
    });

    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });

    let productPrice1;
    let productPrice2;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice1 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(purchasePriceList);
    });
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice2 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(salesPriceList);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName1)
        .setValue(productValue1)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice1)
        .addProductPrice(productPrice2)
        .setCUTUAllocation(packingInstructionsName)
        .setBusinessPartner(vendorName)
        .apply();
    });
  });
  it('Create a sales order', function() {
    cy.visitWindow('143', 'NEW');
    cy.get('#lookup_C_BPartner_ID input')
      .type(customerName)
      .type('\n');
    cy.contains('.input-dropdown-list-option', customerName).click();

    cy.selectInListField('M_PricingSystem_ID', salesPriceSystem, false, null, true);

    const addNewText = Cypress.messages.window.batchEntry.caption;
    cy.get('.tabs-wrapper .form-flex-align .btn')
      .contains(addNewText)
      .should('exist')
      .click();
    cy.get('.quick-input-container .form-group').should('exist');
    cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);

    cy.get('.form-field-Qty')
      .click()
      .find('.input-body-container.focused')
      .should('exist')
      .find('i')
      .eq(0)
      .click();

    cy.get('.form-field-Qty')
      .find('input')
      .should('have.value', '0.1')
      .clear()
      .type('1{enter}');
    // cy.get('#lookup_M_Product_ID .input-dropdown').should('not.have.class', 'input-block');
    cy.waitUntilProcessIsFinished();
    /**Complete sales order */
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, DocumentActionKey.Complete),
        getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed)
      );
    });
    /**Create purchase order from sales order */
    cy.executeHeaderActionWithDialog('C_Order_CreatePOFromSOs');
    cy.pressStartButton();
    cy.waitUntilProcessIsFinished();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    /**Go to purchase order */
    cy.get('.reference_AD_RelationType_ID-540164', { timeout: 10000 }).click();
    cy.get('tbody tr')
      .eq('0')
      .dblclick();
    /**check product name */
    cy.get('tbody tr')
      .eq('0')
      .find('.Lookup')
      .find('.lookup-cell')
      .contains(productName1);
    /**check price of product */
    cy.get('tbody tr')
      .eq('0')
      .find('.CostPrice')
      .find('.costprice-cell')
      .eq(0)
      .contains('1.23');
    /**check if vendor in purchase order is the current vendor set in product  */
    cy.get('tbody tr')
      .eq('0')
      .find('.list-cell')
      .contains(vendorName);
    cy.get('tbody tr')
      .eq('0')
      .find('.quantity-cell')
      .contains('1');
    /**purchase order should be drafted */
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.get('.tag.tag-primary').contains(getLanguageSpecific(miscDictionary, DocumentStatusKey.Drafted));
    });
  });
});
