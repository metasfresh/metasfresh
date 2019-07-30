import { QualityNote } from '../../support/utils/qualityNote';
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { BPartner } from '../../support/utils/bpartner';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentActionKey, DocumentStatusKey } from '../../support/utils/constants';

describe('Create test: create material receipt with quality issue, https://github.com/metasfresh/metasfresh-e2e/issues/210', function() {
  const date = humanReadableNow();
  const qualityNoteName = `QualityNoteTest ${date}`;
  const qualityNoteValue = `QualityNoteValueTest ${date}`;
  const warehouseName = `TestWarehouseName ${date}`;
  const warehouseValue = `TestWarehouseValue ${date}`;
  const productName1 = `ProductTest ${date}`;
  const productValue1 = `purchase_order_test ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
  const productCategoryValue = `ProductCategoryValue ${date}`;
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;
  const productType = 'Item';
  const vendorName = `Vendor ${date}`;

  it('Create a quality note', function() {
    cy.fixture('material/quality_note.json').then(qualityNoteJson => {
      Object.assign(new QualityNote(), qualityNoteJson)
        .setValue(qualityNoteValue)
        .setName(qualityNoteName)
        .apply();
    });
  });
  it('Create quality issue warehouse', function() {
    /**filter after quality issue warehouse */
    cy.visitWindow('139');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.clickOnCheckBox('IsIssueWarehouse', true, false, null, true);
    applyFilters();
    cy.waitUntilProcessIsFinished();
    /**if found, deselect it */
    cy.get('tr').then(el => {
      if (el.length > 1) {
        debugger;
        cy.get(el.get(1))
          .find('td:nth-of-type(7)')
          .find('.meta-icon-checkbox-1')
          .dblclick();
        cy.setCheckBoxValue('IsIssueWarehouse', false);
        cy.waitUntilProcessIsFinished();
      }
    });
    /**create a new quality issue warehouse */
    cy.visitWindow('139', 'NEW')
      .writeIntoStringField('Name', warehouseName)
      .clearField('Value')
      .writeIntoStringField('Value', warehouseValue);
    cy.selectNthInListField('C_BPartner_Location_ID', 1, false);
    cy.setCheckBoxValue('IsIssueWarehouse', true);
    //create locator
    cy.get(`#tab_M_Locator`).click();
    cy.pressAddNewButton()
      .writeIntoStringField('X', '0')
      .writeIntoStringField('X1', '0')
      .writeIntoStringField('Z', '0')
      .writeIntoStringField('Y', '0')
      .pressDoneButton();
    //create warehouse routing
    cy.get(`#tab_M_Warehouse_Routing`).click();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', 'Sales Order', true)
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', 'Match PO', true)
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', 'Material Receipt', true)
      .pressDoneButton();
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
  });
  it('Create product and vendor', function() {
    Builder.createBasicProductEntities(
      productCategoryName,
      productCategoryValue,
      priceListName,
      productName1,
      productValue1,
      productType
    );
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendor(true)
        .setVendorPricingSystem(priceSystemName)
        .setVendorDiscountSchema(discountSchemaName)
        .setPaymentTerm('30 days net')
        .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
        .apply();
    });
    cy.readAllNotifications();
  });
  it('Create purchase order - material receipt with quality issue', function() {
    /**Create a purchase order */
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
      .type('10{enter}');
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
    /**Receive CUs */
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveCUs');
    /**this means that 50% of the products are altered. */
    cy.writeIntoStringField('QualityDiscountPercent', 50, true, null, true);
    cy.selectInListField('QualityNotice', qualityNoteName, true, null, true);
    cy.waitUntilProcessIsFinished();
    /**Create material receipt */
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams');
    cy.waitUntilProcessIsFinished();
    cy.pressDoneButton();
    cy.waitUntilProcessIsFinished();
    cy.go('back');
    cy.waitUntilProcessIsFinished();
    cy.get('.btn-header.side-panel-toggle').click({ force: true });
    cy.get('.order-list-nav .order-list-btn')
      .eq('1')
      .find('i')
      .click({ force: true });
    /** Go to Material Receipt Candidates*/
    cy.contains('Material Receipt (#').click();
    /**as the quantity for the product set in purchase order was 10 and 50% are altered
     * => there should be 2 items in material receipt -
     * 1 without quality note and quantity=5 and 1 with quality note and quantity=5 */
    cy.get('tbody tr')
      .eq('0')
      .dblclick();
    cy.get('tbody tr')
      .eq('0')
      .find('.quantity-cell')
      .eq(1)
      .contains('5');
    cy.get('tbody tr')
      .eq('0')
      .find('.Text')
      .find('.text-cell')
      .should('be.empty');
    cy.get('tbody tr')
      .eq('1')
      .find('.quantity-cell')
      .eq(1)
      .contains('5');
    cy.get('tbody tr')
      .eq('1')
      .find('.Text')
      .find('.text-cell')
      .contains(qualityNoteName);
  });
});
