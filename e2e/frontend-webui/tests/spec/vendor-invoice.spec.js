import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { VendorInvoicePage } from '../utils/pages/VendorInvoicePage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PURCHASE_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Vendor Invoice Workflow E2E test suite.
 *
 * Features tested (from Google Sheets):
 * - F00600: Purchase Order (Epic: Purchasing)
 * - F65010: Material Receipt Candidates (Epic: Material Receipt)
 * - F00700: Invoice Candidate (Purchase) (Epic: Purchasing)
 * - F00710: Vendor Invoice (Epic: Purchasing)
 *
 * Tests the complete purchase-to-invoice workflow:
 *
 * 1. Create a purchase order with qty=10
 * 2. Complete the purchase order
 * 3. Navigate to Receipt Candidates (Alt+6)
 * 4. Create material receipt using "Receive CUs with Qty"
 * 5. Navigate back to PO and open Invoice Candidates (Alt+6)
 * 6. Create vendor invoice via Quick Action
 * 7. Navigate to Vendor Invoice and validate PDF
 *
 * This mirrors the sales flow (shipment-schedule.spec.js):
 * Sales: SO → Shipment → Invoice
 * Purchase: PO → Receipt → Invoice
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Vendor Invoice Workflow (${label})`, () => {
    // Increase timeout for this complex multi-step workflow
    test.setTimeout(180000); // 3 minutes

    test(`Complete PO → Receipt → Invoice workflow (${label} UI)`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0140: Purchasing');
      allure.tag('F00600: Purchase Order');
      allure.tag('F00600');
      allure.tag('F65010: Material Receipt Candidates');
      allure.tag('F65010');
      allure.tag('F00700: Invoice Candidate (Purchase)');
      allure.tag('F00700');
      allure.tag('F00710: Vendor Invoice');
      allure.tag('F00710');
      allure.story('Complete purchase-to-invoice workflow');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.parameter('UI Label', label);
      allure.tag(language);

      allure.description(`
## E0140: Purchasing

## F00600: Purchase Order
## F65010: Material Receipt Candidates
## F00700: Invoice Candidate (Purchase)
## F00710: Vendor Invoice

### Test Scenario: Vendor Invoice Workflow
This test validates the complete purchase-to-invoice workflow:

1. **Create Purchase Order** - New PO with vendor and product line (qty=10)
2. **Complete Order** - Mark as completed to trigger downstream processes
3. **Create Material Receipt** - Receive all units via Receipt Candidates
4. **Navigate to Invoice Candidates** - Use Alt+6 to open related invoice candidates
5. **Create Vendor Invoice** - Execute enqueue action to generate invoice
6. **Navigate to Vendor Invoice** - Use Alt+6 to open the generated invoice
7. **Validate Invoice PDF** - Verify document number, vendor, product, and quantity

## Features Tested
- **F00600**: Purchase Order
- **F65010**: Material Receipt Candidates
- **F00700**: Invoice Candidate (Purchase)
- **F00710**: Vendor Invoice

## Quick Actions Used
- WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam (Receive CUs with Qty)
- C_Invoice_Candidate_EnqueueSelectionForInvoicing (Create Invoice)

## Business Value
Ensures the complete purchase-to-invoice flow works correctly, allowing
proper vendor invoice generation after goods receipt.
      `);

      // Create test data with specified language
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
            },
          },
          bpartners: {
            VENDOR1: {
              isVendor: true,
              isCustomer: false,
              isSoPriceList: false, // Purchase price list
            },
          },
          products: {
            Product1: {
              name: `Vendor Invoice Product (${language})`,
              type: 'Item',
              prices: [
                {
                  price: 25.0,
                  currencyCode: 'EUR',
                },
              ],
            },
          },
        },
      });

      // Login with the user (UI will be in specified language)
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // === STEP 1: Create and complete purchase order ===
      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
      await PurchaseOrderPage.goToOrderLineTab();
      await PurchaseOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: '10' });
      await PurchaseOrderPage.complete();

      // Get the purchase order document number and record ID
      const poDocumentNo = await PurchaseOrderPage.getDocumentNo();
      const poRecordId = PurchaseOrderPage.getRecordId();

      // Verify document number was assigned
      expect(poDocumentNo).toBeTruthy();
      expect(poDocumentNo.length).toBeGreaterThan(0);

      console.log(`[${language}] Purchase Order created: ${poDocumentNo} (ID: ${poRecordId})`);

      // === STEP 2: Create material receipt ===
      await PurchaseOrderPage.openRelatedReceiptCandidate();
      await ReceiptCandidatesPage.expectQuickActionsVisible();

      console.log(`[${language}] Receipt Candidate opened for PO ${poDocumentNo}`);

      // Create receipt for all 10 units
      await ReceiptCandidatesPage.receiveCUsWithQuantity(10);

      console.log(`[${language}] Material Receipt created (qty=10)`);

      // Navigate to the created Material Receipt to verify it was created
      await ReceiptCandidatesPage.navigateToMaterialReceiptViaTab();
      const receiptDocNo = await ReceiptCandidatesPage.getDocumentNo();
      console.log(`[${language}] Material Receipt document: ${receiptDocNo}`);

      // Close the Material Receipt tab and go back to PO
      const materialReceiptPage = global.currentPage;
      await materialReceiptPage.close();

      // Switch to remaining page and navigate to PO
      const allPages = page.context().pages();
      const remainingPage = allPages[0];
      global.currentPage = remainingPage;
      await remainingPage.bringToFront();

      // === STEP 3: Navigate back to PO ===
      await remainingPage.goto(`${FRONTEND_BASE_URL}/window/${PURCHASE_ORDER_WINDOW_ID}/${poRecordId}`);
      await remainingPage.waitForURL(/\/window\/181\/\d+/, { timeout: 15000 });
      await remainingPage.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

      // Wait for page to be fully loaded
      await remainingPage.locator('.rotating, .indicator-pending, .loader')
        .waitFor({ state: 'detached', timeout: 15000 }).catch(() => {});

      console.log(`[${language}] Navigated back to PO: ${poDocumentNo}`);

      // === STEP 4: Navigate to Invoice Candidates ===
      // Invoice candidates are created when PO is completed
      // Wait a bit for async invoice candidate creation
      await remainingPage.waitForTimeout(2000);

      await PurchaseOrderPage.openRelatedInvoiceCandidate(5000);
      await InvoiceCandidatePage.expectVisibleForPurchaseOrder();

      console.log(`[${language}] Invoice Candidate opened for PO ${poDocumentNo}`);

      // === STEP 5: Create vendor invoice ===
      await InvoiceCandidatePage.createInvoiceForPurchaseOrder();

      console.log(`[${language}] Vendor Invoice creation triggered`);

      // Wait for invoice to be fully created (async process)
      // Invoice generation is async and can take 10-15 seconds
      await remainingPage.waitForTimeout(10000);

      // === STEP 6: Navigate back to PO and then to Vendor Invoice ===
      await remainingPage.goto(`${FRONTEND_BASE_URL}/window/${PURCHASE_ORDER_WINDOW_ID}/${poRecordId}`);
      await remainingPage.waitForURL(/\/window\/181\/\d+/, { timeout: 15000 });
      await remainingPage.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

      // Wait for page to be fully loaded
      await remainingPage.locator('.rotating, .indicator-pending, .loader')
        .waitFor({ state: 'detached', timeout: 15000 }).catch(() => {});

      console.log(`[${language}] Navigated back to PO for invoice navigation`);

      // Navigate to Vendor Invoice via Alt+6
      // Use higher retries since invoice creation is async
      await PurchaseOrderPage.openRelatedVendorInvoice({ maxRetries: 8, retryDelay: 3000 });
      await VendorInvoicePage.expectVisible();

      // Get vendor invoice document number
      const invoiceDocNo = await VendorInvoicePage.getDocumentNo();
      expect(invoiceDocNo).toBeTruthy();
      expect(invoiceDocNo.length).toBeGreaterThan(0);

      console.log(`[${language}] Vendor Invoice: ${invoiceDocNo}`);

      // === STEP 7: Open detail view and validate PDF ===
      await VendorInvoicePage.openDetailView();
      await VendorInvoicePage.openPrintModal();

      const invoiceDownload = await VendorInvoicePage.downloadPDF();
      console.log(`[${language}] Vendor Invoice PDF downloaded: ${invoiceDownload.suggestedFilename()}`);

      // Validate PDF content
      await VendorInvoicePage.validatePdfContent(invoiceDownload, {
        documentNo: invoiceDocNo,
        vendorName: masterdata.bpartners.VENDOR1.bpartnerCode,
        productCode: masterdata.products.Product1.productCode,
        quantity: '10',
        language,
      });

      console.log(`[${language}] Vendor Invoice PDF validated successfully`);

      await VendorInvoicePage.closePrintModal().catch(() => {});

      // === Final verification ===
      console.log(`[${language}] Vendor Invoice workflow complete:`);
      console.log(`  - PO: ${poDocumentNo} (qty=10)`);
      console.log(`  - Receipt: ${receiptDocNo}`);
      console.log(`  - Invoice: ${invoiceDocNo}`);
    });
  });
});
