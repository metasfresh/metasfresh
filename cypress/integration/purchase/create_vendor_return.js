import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { DocumentStatusKey } from '../../support/utils/constants';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

describe('Create Purchase order - complete - change - complete', function() {
  const date = humanReadableNow();
  const productName1 = `ProductTest ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
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
  });
  it('Create product to be used in purchase order', function() {
    Builder.createBasicProductEntities(
      productCategoryName,
      productCategoryName,
      priceListName,
      productName1,
      productName1,
      productType
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
  it('Create a purchase order and complete it', function() {
    new PurchaseOrder()
      .setBPartner(vendorName)
      .setPriceSystem(priceSystemName)
      .setPoReference('test')
      .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(1))
      .apply();
    cy.completeDocument();
    cy.waitUntilProcessIsFinished();
  });
  it('Open Material receipt candidates and create material receipt', function() {
    cy.openReferencedDocuments('M_ReceiptSchedule');
    cy.selectNthRow(0).click();
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveCUs', false);
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', false, true);
    cy.pressDoneButton();
  });
  it('Go to Material receipt', function() {
    cy.visitWindow('184');
    cy.selectNthRow(0).dblclick();
  });
  it('Go to Handling Unit Editor and return product to vendor', function() {
    cy.visitWindow('540189');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
    applyFilters();
    cy.selectNthRow(0).click();
    cy.executeQuickAction('WEBUI_M_HU_ReturnToVendor', false, false, false);
  });
  it('Check document type, bpartner, product and notification in vendor returns', function() {
    cy.visitWindow('53098');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('C_BPartner_ID', vendorName, vendorName, false, false, null, true);
    applyFilters();
    cy.selectNthRow(0).dblclick();
    cy.get('.header-breadcrumb-sitename').should('contain', vendorName);
    cy.getStringFieldValue('C_DocType_ID').then(el => {
      expect(el).to.equals('Lieferantenrückgabe');
    });
    /*cy.getStringFieldValue('C_DocType_ID').should('equal', 'Lieferantenrückgabe');*/
    cy.selectTab('M_InOutLine');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_Product_ID').then(el => {
      expect(el).to.contain(productName1);
    });
    cy.getStringFieldValue('QtyEntered').then(el => {
      expect(el).to.contain(1);
    });
    cy.pressDoneButton();
    cy.openInboxNotificationWithText(vendorName);
  });
});
