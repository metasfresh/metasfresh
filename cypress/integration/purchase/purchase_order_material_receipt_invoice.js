import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { humanReadableNow } from '../../support/utils/utils';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { purchaseOrders } from '../../page_objects/purchase_orders';

// task: https://github.com/metasfresh/metasfresh-e2e/issues/161

const date = humanReadableNow();
const productForPackingMaterial = `ProductPackingMaterial_${date}`;
const packingInstructionsName = `ProductPackingInstructions_${date}`;
const productName1 = `Product1_${date}`;
const productName2 = `Product2_${date}`;
const productCategoryName = `ProductCategoryName_${date}`;
const discountSchemaName = `DiscountSchema_${date}`;
const priceSystemName = `PriceSystem_${date}`;
const priceListName = `PriceList_${date}`;
const priceListVersionName = `PriceListVersion_${date}`;
const productType = 'Item';
const vendorName = `Vendor_${date}`;
const generateInvoicesNotificationModalText =
  'Fakturlauf mit 1 Rechnungen eingeplant. Es sind bereits 0 zu erstellende Rechnungen in der Warteschlange, die vorher verarbeitet werden.';

// test
let purchaseOrderRecordId;
let grandTotal;

describe('Create test data', function() {
  it('Create price entities', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
  });
  it('Create packing related entities', function() {
    // eslint-disable-next-line
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterial, productForPackingMaterial, productType, "24_Gebinde");
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(productForPackingMaterial)
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
        .setPackingMaterial(productForPackingMaterial)
        .apply();
    });
  });

  it('Create category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create product1', function() {
    Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(
      productCategoryName,
      productCategoryName,
      priceListName,
      productName1,
      productName1,
      productType,
      packingInstructionsName
    );
  });

  it('Create product2', function() {
    // these are split into multiple "it" blocks as maybe this fixes some stupid ` Cannot read property 'body' of null` error
    Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(
      productCategoryName,
      productCategoryName,
      priceListName,
      productName2,
      productName2,
      productType,
      packingInstructionsName
    );
  });
  it('Create vendor', function() {
    new BPartner({ name: vendorName })
      .setVendor(true)
      .setVendorPricingSystem(priceSystemName)
      .setVendorDiscountSchema(discountSchemaName)
      .setPaymentTerm('30 days net')
      .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
      .apply();

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
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults', false);
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', false, true, false);
    cy.pressDoneButton();
  });

  it('Create Material Receipt 2', function() {
    cy.selectNthRow(1).click();
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults', false);
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', false, true, false);
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

    cy.expectNumberOfRows(4); // i am not sure how many rows to expect. In video there are 6, but in manual testing i have found only 4!
  });

  it('Select all candidates and run action "Generate Invoices"', function() {
    cy.get('body').type('{alt}a'); // select all on this page
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
    cy.url().should('contain', '/183/');

    // hope this is enough for the whole window to load
    cy.waitForSaveIndicator();
    cy.getStringFieldValue('C_BPartner_ID').should('contain', vendorName);

    cy.openAdvancedEdit();
    cy.getStringFieldValue('GrandTotal').then(el => {
      expect(parseFloat(el)).to.equals(grandTotal);
    });
  });
});
