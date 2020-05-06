import { QualityNote } from '../../support/utils/qualityNote';
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { BPartner } from '../../support/utils/bpartner';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';
import { Warehouse, WarehouseRoute } from '../../support/utils/warehouse';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { purchaseOrders } from '../../page_objects/purchase_orders';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create material receipt with quality issue', function() {
  let qualityNoteName;
  let qualityNoteValue;
  let qualityDiscountPercent;
  let expectedLineQty;

  let warehouseName;

  let productName;
  let productCategoryName;
  let discountSchemaName;
  let priceSystemName;
  let priceListName;
  let priceListVersionName;
  let vendorName;

  let purchaseOrderLineQuantity;

  // test
  let purchaseOrderRecordId;

  it('Read the fixture', function() {
    cy.fixture('materialReceipt/create_material_receipt_with_quality_issue.json').then(f => {
      qualityNoteName = appendHumanReadableNow(f['qualityNoteName']);
      qualityNoteValue = appendHumanReadableNow(f['qualityNoteValue']);
      qualityDiscountPercent = f['qualityDiscountPercent'];
      expectedLineQty = f['expectedLineQty'];

      warehouseName = appendHumanReadableNow(f['warehouseName']);

      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
      vendorName = appendHumanReadableNow(f['vendorName']);
      purchaseOrderLineQuantity = f['purchaseOrderLineQuantity'];
    });
  });

  it('Create a quality note', function() {
    cy.fixture('material/quality_note.json').then(qualityNoteJson => {
      Object.assign(new QualityNote(), qualityNoteJson)
        .setValue(qualityNoteValue)
        .setName(qualityNoteName)
        .apply();
    });
  });

  it('Disable all other quality issue warehouses', function() {
    cy.visitWindow('139');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.setCheckBoxValue('IsIssueWarehouse', true, false, null, true);
    applyFilters();

    cy.get('tr').then(el => {
      if (el.length > 1) {
        cy.selectNthRow(0).dblclick();
        cy.setCheckBoxValue('IsIssueWarehouse', false);
      }
    });
  });

  it('Ensure no quality issue warehouse exists', function() {
    cy.visitWindow('139');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.setCheckBoxValue('IsIssueWarehouse', true, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(0);
  });

  it('Create quality issue warehouse', function() {
    cy.fixture('misc/warehouse.json').then(warehouseJson => {
      Object.assign(new Warehouse(), warehouseJson)
        .setName(warehouseName)
        .setValue(warehouseName)
        .setIsQualityIssueWarehouse(true)
        .addRoute(new WarehouseRoute().setDocBaseType('Match PO'))
        .apply();
    });
  });

  it('Create Price', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
  });

  it('Create Product Category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create product and vendor', function() {
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productName, productName, null, productCategoryName);
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendorPricingSystem(priceSystemName)
        .setVendorDiscountSchema(discountSchemaName)
        .apply();
    });
    cy.readAllNotifications();
  });

  it('Create purchase order - material receipt with quality issue', function() {
    new PurchaseOrder()
      .setBPartner(vendorName)
      .setPriceSystem(priceSystemName)
      .addLine(new PurchaseOrderLine().setProduct(productName).setQuantity(purchaseOrderLineQuantity))
      .apply();

    cy.completeDocument();
  });

  it('Save values needed for the next steps', function() {
    cy.getCurrentWindowRecordId().then(recordId => {
      purchaseOrderRecordId = recordId;
    });
  });

  it('Visit referenced Material Receipt Candidates, expect 1 row', function() {
    cy.openReferencedDocuments('M_ReceiptSchedule');
    cy.expectNumberOfRows(1);
  });

  it('Select the row and Receive the CUs with 50% alteration', function() {
    cy.selectNthRow(0);
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveCUs');
    /**this means that 50% of the products are altered. */
    cy.writeIntoStringField('QualityDiscountPercent', qualityDiscountPercent, true, null, true);
    cy.selectInListField('QualityNotice', qualityNoteName, true, null, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
    cy.pressDoneButton();
  });

  it('Go to the referenced Material Receipt and expect 1 rows/records', function() {
    cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);
    cy.openReferencedDocuments('184');

    cy.expectNumberOfRows(1);
  });

  it('Expect 2 Material Receipt Lines', function() {
    /**
     * As the quantity for the product set in purchase order was 10 and 50% are altered
     * => there should be 2 items in material receipt -
     * 1 without quality note and quantity=5 and 1 with quality note and quantity=5
     * */

    cy.selectNthRow(0).dblclick();
    cy.selectTab('M_InOutLine');
    cy.expectNumberOfRows(2);
  });

  it('Check Material Receipt Line without quality issue', function() {
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.getStringFieldValue('QtyEntered', true).should('equal', expectedLineQty.toString());
    cy.getStringFieldValue('QualityNote', true).should('be.empty');
    cy.getStringFieldValue('QualityDiscountPercent', true).should('be', '0');
    cy.expectCheckboxValue('IsInDispute', false, true);
    cy.pressDoneButton();
  });

  it('Check Material Receipt Line with quality issue', function() {
    cy.selectNthRow(1);
    cy.openAdvancedEdit();
    cy.getStringFieldValue('QtyEntered', true).should('equal', expectedLineQty.toString());
    cy.getStringFieldValue('QualityNote', true).should('contain', qualityNoteName);
    cy.getStringFieldValue('QualityDiscountPercent', true).should('contain', qualityDiscountPercent);
    cy.expectCheckboxValue('IsInDispute', true, true);
    cy.pressDoneButton();
  });
});
