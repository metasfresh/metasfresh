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
  it('Create product,category and vendor entities to be used in purchase order', function() {
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
    new BPartner({ name: vendorName })
      .setVendor(true)
      .setVendorPricingSystem(priceSystemName)
      .setVendorDiscountSchema(discountSchemaName)
      .setPaymentTerm('30 days net')
      .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
      .apply();

    cy.readAllNotifications();
  });
  it('Create a purchase order', function() {
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
      .clear()
      .type('1{enter}');
    cy.waitUntilProcessIsFinished();

    cy.get('.quick-input-container .form-group').should('exist');
    cy.writeIntoLookupListField('M_Product_ID', productName2, productName2, false, false, null, true);

    cy.get('.form-field-Qty')
      .click()
      .find('.input-body-container.focused')
      .should('exist')
      .find('i')
      .eq(0)
      .click();

    cy.get('.form-field-Qty')
      .find('input')
      .clear()
      .type('1{enter}');
    cy.waitUntilProcessIsFinished();
    /**Complete purchase order */
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, DocumentActionKey.Complete),
        getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed)
      );
    });
    cy.waitUntilProcessIsFinished();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    /** Go to Material Receipt Candidates*/
    cy.get('.reference_M_ReceiptSchedule').click();
    /**Select the first product */
    cy.get('tbody tr')
      .eq('0')
      .click();
    cy.waitUntilProcessIsFinished();
    cy.get('.quick-actions-tag.pointer').click({ force: true });

    cy.waitUntilProcessIsFinished();
    /**Create material receipt */
    cy.contains('Create material receipt').click();
    cy.waitUntilProcessIsFinished();
    cy.pressDoneButton();
    /**Select the second product */
    cy.get('tbody tr')
      .eq('1')
      .click();
    cy.waitUntilProcessIsFinished();
    cy.get('.quick-actions-tag.pointer').click({ force: true });

    cy.waitUntilProcessIsFinished();
    /**Create material receipt */
    cy.contains('Create material receipt').click();
    cy.waitUntilProcessIsFinished();
    cy.pressDoneButton();
    /**Navigate back in the purchase order */
    cy.go('back');
    /**Go to 'Material receipt' */
    cy.waitUntilProcessIsFinished();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    cy.contains('Material Receipt (#').click();
    /**Navigate back in the purchase order */
    cy.go('back');
    cy.waitUntilProcessIsFinished();
    let grandTotal = null;
    cy.openAdvancedEdit()
      .get('.form-field-GrandTotal input')
      .then(el => {
        grandTotal = el.val();
      });
    /**Go to 'Invoice disposition' */
    cy.pressDoneButton();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    cy.get('.reference_C_Invoice_Candidate').click();
    cy.waitUntilProcessIsFinished();
    cy.get('.pagination-link.pointer').click({ force: true });
    cy.contains('generate invoices').click();
    cy.waitUntilProcessIsFinished();
    cy.pressStartButton();
    cy.waitUntilProcessIsFinished();
    /**Open notifications */
    cy.get('.header-item-badge.icon-lg i').click();
    cy.get('.inbox-item-unread .inbox-item-title')
      .filter(':contains("' + vendorName + '")')
      .first()
      .click();
    cy.waitUntilProcessIsFinished();
    cy.openAdvancedEdit();
    /**because should('have.value',grandTotal) evaluates using a null grand total */
    cy.get('.form-field-GrandTotal input').should(el => {
      expect(el).to.have.value(grandTotal);
    });
  });
});
