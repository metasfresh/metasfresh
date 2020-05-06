import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { purchaseOrders } from '../../page_objects/purchase_orders';
import { RewriteURL } from '../../support/utils/constants';
import { appendHumanReadableNow } from '../../support/utils/utils';

let productForPackingMaterial;
let packingInstructionsName;
let productName1;
let productName2;
let productCategoryName;
let discountSchemaName;
let priceSystemName;
let priceListName;
let priceListVersionName;
let vendorName;
let generateInvoicesNotificationModalText;

// test
let purchaseOrderRecordId;
let grandTotal;

describe('Create test data', function() {
  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_order_material_receipt_invoice.json').then(f => {
      productForPackingMaterial = appendHumanReadableNow(f['productForPackingMaterial']);
      packingInstructionsName = appendHumanReadableNow(f['packingInstructionsName']);
      productName1 = appendHumanReadableNow(f['productName1']);
      productName2 = appendHumanReadableNow(f['productName2']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
      vendorName = appendHumanReadableNow(f['vendorName']);
      generateInvoicesNotificationModalText = f['generateInvoicesNotificationModalText'];
    });
  });

  it('Create price entities', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
  });
  it('Create packing related entities', function() {
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterial, productForPackingMaterial, null, '24_Gebinde');
    Builder.createPackingMaterial(productForPackingMaterial, packingInstructionsName);
  });

  it('Create category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create products', function() {
    Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(productCategoryName, productCategoryName, priceListName, productName1, productName1, null, packingInstructionsName);
    Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(productCategoryName, productCategoryName, priceListName, productName2, productName2, null, packingInstructionsName);
  });

  it('Create vendor', function() {
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendorPricingSystem(priceSystemName)
        .setVendorDiscountSchema(discountSchemaName)
        .apply();
    });

    cy.readAllNotifications();
  });
});

describe('Create a purchase order and Material Receipts', function() {
  it('Create a purchase order and visit Material Receipt Candidates', function() {
    new PurchaseOrder()
      .setBPartner(vendorName)
      .setPriceSystem(priceSystemName)
      .setPoReference('test')
      .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(1))
      .addLine(new PurchaseOrderLine().setProduct(productName2).setQuantity(1))
      .apply();
    cy.completeDocument();
  });

  it('Save values needed for the next steps', function() {
    cy.getCurrentWindowRecordId().then(recordId => {
      purchaseOrderRecordId = recordId;
    });

    cy.openAdvancedEdit();
    cy.getStringFieldValue('GrandTotal').then(gt => {
      grandTotal = parseFloat(gt);
    });
    cy.pressDoneButton();
  });

  it('Visit referenced Material Receipt Candidates', function() {
    cy.openReferencedDocuments('M_ReceiptSchedule');
    cy.expectNumberOfRows(3);
  });

  it('Create Material Receipt 1', function() {
    cy.selectNthRow(0).click();
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults');
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
    cy.pressDoneButton();
  });

  it('Create Material Receipt 2', function() {
    cy.selectNthRow(1).click();
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults', false);
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
    cy.pressDoneButton();
  });

  it('Go to the referenced Material Receipt and expect 2 rows/records', function() {
    cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);
    cy.openReferencedDocuments('184');

    cy.expectNumberOfRows(2);
  });

  it('Go to the referenced Invoice Disposition (Billing Candidates) and expect 4 rows/records', function() {
    cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);
    cy.openReferencedDocuments('C_Invoice_Candidate');

    cy.expectNumberOfRows(4); // todo @dh i am not sure how many rows to expect. In video there are 6, but in manual testing i have found only 4!
  });

  it('Select all candidates and run action "Generate Invoices"', function() {
    cy.selectAllRowsOnCurrentPage();
    cy.readAllNotifications();
    cy.executeHeaderActionWithDialog('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
    // no parameters are changed for this process
    cy.pressStartButton();

    cy.getNotificationModal(generateInvoicesNotificationModalText);
    cy.expectNumberOfDOMNotifications(1);
  });

  it('Open Purchase Invoice from notifications bell and check GrandTotal', function() {
    cy.openInboxNotificationWithText(vendorName);
    cy.waitForSaveIndicator();

    // wait until current window is PurchaseInvoice
    cy.url().should('matches', RewriteURL.ExactSingleView(183));

    // hope this is enough for the whole window to load
    cy.waitForSaveIndicator();
    cy.getStringFieldValue('C_BPartner_ID').should('contain', vendorName);

    cy.openAdvancedEdit();
    cy.getStringFieldValue('GrandTotal').then(el => {
      expect(parseFloat(el)).to.be.closeTo(grandTotal, 0.1);
    });
  });
});
