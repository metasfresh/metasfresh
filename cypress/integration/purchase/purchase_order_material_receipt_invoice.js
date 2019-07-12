import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { purchaseOrders } from '../../page_objects/purchase_orders';
import { Builder } from '../../support/utils/builder';

describe('Create Purchase order - material receipt - invoice', function() {
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
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);
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
      .clear()
      .type('1{enter}');
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
      .clear()
      .type('1{enter}');
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
    /**Select the first product */
    cy.get('tbody tr')
      .eq('0')
      .click();
    cy.wait(8000);
    cy.get('.quick-actions-tag.pointer').click({ force: true });

    cy.wait(8000);
    /**Create material receipt */
    cy.contains('Create material receipt').click();
    cy.wait(2000);
    cy.pressDoneButton();
    /**Select the second product */
    cy.get('tbody tr')
      .eq('1')
      .click();
    cy.wait(8000);
    cy.get('.quick-actions-tag.pointer').click({ force: true });

    cy.wait(8000);
    /**Create material receipt */
    cy.contains('Create material receipt').click();
    cy.wait(2000);
    cy.pressDoneButton();
    /**Navigate back in the purchase order */
    cy.go('back');
    /**Go to 'Material receipt' */
    cy.wait(4000);
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    cy.contains('Material Receipt (#').click();
    /**Navigate back in the purchase order */
    cy.wait(2000);
    cy.go('back');
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
    cy.wait(5000);//look into
    cy.get('.pagination-link.pointer').click({ force: true });
    cy.contains('generate invoices').click();
    cy.wait(5000);
    cy.pressStartButton();
    cy.wait(8000);
    /**Open notifications */
    cy.get('.header-item-badge.icon-lg i').click();
    cy.get('.inbox-item-unread .inbox-item-title')
      .filter(':contains("' + vendorName + '")')
      .first()
      .click();
    cy.wait(3000);
    cy.openAdvancedEdit();
    /**because should('have.value',grandTotal) evaluates using a null grand total */
    cy.get('.form-field-GrandTotal input').should(el => {
      expect(el).to.have.value(grandTotal);
    });
  });
});
