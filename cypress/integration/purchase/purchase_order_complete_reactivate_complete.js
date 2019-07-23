import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';

describe('Create Purchase order - material receipt - invoice', function() {
  const date = humanReadableNow();
  const productForPackingMaterial = `ProductPackingMaterial ${date}`;
  const productPMValue = `purchase_order_testPM ${date}`;
  const packingMaterialName = `ProductPackingMaterial ${date}`;
  const packingInstructionsName = `ProductPackingInstrutions ${date}`;
  const productName1 = `ProductTest ${date}`;
  const productValue1 = `purchase_order_test ${date}`;
  const productName2 = `ProductTest ${date}`;
  const productValue2 = `purchase_order_test ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
  const productCategoryValue = `ProductCategoryValue ${date}`;
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;
  const productType = 'Item';
  const vendorName = `Vendor ${date}`;

  it('Create price and product entities to be used in purchase order', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    Builder.createBasicProductEntitiesWithPrice(priceListName, productForPackingMaterial, productPMValue, productType);
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(packingMaterialName)
        .setProduct(productForPackingMaterial)
        .apply();
    });
  });
  it('Create packing entities to be used in purchase order', function() {
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
  it('Create product entities and a vendor to be used in purchase order', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });

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
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ name: vendorName })
        .setVendor(true)
        .setVendorPricingSystem(priceSystemName)
        .setVendorDiscountSchema(discountSchemaName)
        .setPaymentTerm('30 days net')
        .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
        .apply();
    });
    cy.readAllNotifications();
  });
  it('Create a purchase order', function() {
    cy.visitWindow('181', 'NEW');
    cy.get('#lookup_C_BPartner_ID input')
      .type(vendorName)
      .type('\n');
    cy.contains('.input-dropdown-list-option', vendorName).click();

    cy.selectInListField('M_PricingSystem_ID', priceSystemName, false, null, true);
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
      .type('5{enter}');
    cy.waitUntilProcessIsFinished();
    // cy.get('#lookup_M_Product_ID .input-dropdown').should('not.have.class', 'input-block');
    /**Complete purchase order */
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, DocumentActionKey.Complete),
        getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed)
      );
    });
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
    /**check product quantity */
    cy.get('tbody tr')
      .eq('0')
      .find('.quantity-cell')
      .contains('5');
    /**purchase order should be completed */
    cy.log('purchase order should be completed');
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.get('.tag.tag-success').contains(getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed));
    });
  });
  /**Reactivate purchase order */
  it('Reactivate the purchase order', function() {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, DocumentActionKey.Reactivate),
        getLanguageSpecific(miscDictionary, DocumentStatusKey.InProgress)
      );
    });
  });
});
