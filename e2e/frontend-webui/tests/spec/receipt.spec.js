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

/**
 * Purchase-to-Invoice E2E test suite.
 *
 * Features tested (from Google Sheets):
 * - F00600: Purchase Order (Epic: Purchasing)
 * - F65010: Material Receipt Candidates (Epic: Material Receipt)
 * - F00700: Invoice Candidate (Purchase) (Epic: Purchasing)
 * - F00710: Vendor Invoice (Epic: Purchasing)
 *
 * Tests the complete purchase-to-pay workflow:
 * 1. Create a purchase order
 * 2. Complete the purchase order
 * 3. Navigate to Receipt Candidates (Alt+6)
 * 4. Create material receipt via Quick Action (HU-Editor workflow)
 * 5. Navigate back to Purchase Order
 * 6. Open Material Receipt via Alt+6 and validate PDF
 * 7. Navigate to Invoice Candidates (Alt+6)
 * 8. Generate invoice via Quick Action
 * 9. Navigate to Vendor Invoice (Alt+6)
 * 10. Validate Vendor Invoice PDF
 *
 * This validates language-independent selectors for:
 * - Quick Actions button (data-testid="quick-action-button")
 * - Quick Actions dropdown (data-testid="quick-action-dropdown-toggle")
 * - Specific actions (data-testid="quick-action-{internalName}")
 * - Modal buttons (data-testid="modal-done", data-testid="modal-start")
 * - Related documents navigation (Alt+6)
 * - PDF download and validation (Material Receipt + Vendor Invoice)
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Purchase-to-Invoice Workflow (${label})`, () => {
    // Increase timeout for this complete purchase-to-pay workflow
    // Vendor invoice creation is async and takes ~40-60s
    test.setTimeout(180000);

    test(`Complete purchase-to-invoice flow: PO → Receipt → Invoice (${label} UI)`, async ({ page }) => {
      // === ALLURE METADATA ===
      // Feature metadata - IDs for filtering, full names in description
      allure.epic('E0140: Purchasing');
      allure.tag('F00600: Purchase Order');
      allure.tag('F00600');  // Standalone tag for Tags section
      allure.tag('F65010: Material Receipt Candidates');
      allure.tag('F65010');  // Standalone tag for Tags section
      allure.tag('F00700: Invoice Candidate (Purchase)');
      allure.tag('F00700');  // Standalone tag for Tags section
      allure.tag('F00710: Vendor Invoice');
      allure.tag('F00710');  // Standalone tag for Tags section
      allure.story('Complete PO → Receipt → Invoice flow');
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

### Test Scenario
This test validates the complete purchase-to-pay workflow:

1. **Create Purchase Order** - New PO with vendor and product line
2. **Complete Order** - Mark as completed to trigger downstream processes
3. **Navigate to Receipt Candidates** - Use Alt+6 to open related receipt candidates
4. **Create Material Receipt** - Execute HU-Editor workflow to receive goods
5. **Navigate back to PO** - Return to PO for accessing related documents
6. **Validate Material Receipt PDF** - Open M_InOut via Alt+6 and verify PDF content
7. **Navigate to Invoice Candidates** - Use Alt+6 to open related invoice candidates
8. **Generate Invoice** - Execute quick action to create vendor invoice
9. **Navigate to Vendor Invoice** - Use Alt+6 to open generated invoice
10. **Validate Invoice PDF** - Download and verify invoice document content

## Features Tested
- **F00600**: Purchase Order
- **F65010**: Material Receipt Candidates
- **F00700**: Invoice Candidate (Purchase)
- **F00710**: Vendor Invoice

## Business Value
Ensures the complete purchase-to-pay flow works correctly across UI languages,
including material receipt and vendor invoice PDF validation.
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
              name: `Test Product for Receipt (${language})`,
              type: 'Item',
              prices: [
                {
                  price: 10.0,
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

      // Step 1: Create a purchase order
      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
      await PurchaseOrderPage.goToOrderLineTab();
      await PurchaseOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: '5' });
      await PurchaseOrderPage.complete();

      // Get the purchase order document number and URL
      const poDocumentNo = await PurchaseOrderPage.getDocumentNo();
      const poUrl = page.url(); // Save the PO URL for later navigation

      // Verify document number was assigned
      expect(poDocumentNo).toBeTruthy();
      expect(poDocumentNo.length).toBeGreaterThan(0);

      console.log(`[${language}] Purchase Order created: ${poDocumentNo}`);

      // Step 2: Open related receipt candidate using Alt+6
      // This navigates directly to the correct receipt candidate for this PO
      // IMPORTANT: Do NOT navigate to window 540196 directly - it selects wrong candidate!
      await PurchaseOrderPage.openRelatedReceiptCandidate();

      // Verify Quick Actions button is visible (confirms correct candidate selected)
      await ReceiptCandidatesPage.expectQuickActionsVisible();

      // Step 3: Create material receipt using default Quick Action
      await ReceiptCandidatesPage.createReceipt();

      console.log(`[${language}] Material Receipt created from PO ${poDocumentNo}`);

      // Step 4: Validate Material Receipt PDF
      // Navigate to Material Receipt via the "Allocated Material Receipt" tab,
      // then right-click -> Zoom Into to open the M_InOut detail view
      // IMPORTANT: This opens a NEW TAB and switches global.currentPage to it
      await ReceiptCandidatesPage.navigateToMaterialReceiptViaTab();

      // Capture the Material Receipt page (the new tab) for cleanup later
      const materialReceiptPage = global.currentPage;

      await ReceiptCandidatesPage.downloadAndValidatePdf({
        vendorName: masterdata.bpartners.VENDOR1.bpartnerCode,
        productCode: masterdata.products.Product1.productCode,
        quantity: '5',
        language,
      });
      console.log(`[${language}] Material Receipt PDF validated`);

      // IMPORTANT: Close the Material Receipt tab and restore global.currentPage
      // to the original Playwright page fixture, so page objects use the correct page
      await materialReceiptPage.close();
      global.currentPage = page;

      // Step 5: Navigate back to the Purchase Order for invoice creation
      await page.goto(poUrl);
      await page.waitForURL(/\/window\/181\/\d+/, { timeout: 10000 });
      await page.waitForLoadState('networkidle', { timeout: 10000 }).catch(() => {});

      console.log(`[${language}] Navigated back to Purchase Order ${poDocumentNo}`);

      // Step 6: Open related invoice candidate using Alt+6
      // This navigates directly to the correct invoice candidate for this PO
      // Note: Invoice candidates are created when PO is completed (one IC per PO line)
      // Additional ICs may be created after material receipt for non-PO items
      await PurchaseOrderPage.openRelatedInvoiceCandidate();

      // Verify Quick Actions button is visible (confirms correct candidate selected)
      await InvoiceCandidatePage.expectQuickActionsVisible();

      console.log(`[${language}] Invoice Candidate opened for PO ${poDocumentNo}`);

      // Step 7: Generate invoice using Quick Action
      await InvoiceCandidatePage.generateInvoice();

      console.log(`[${language}] Invoice generation triggered from PO ${poDocumentNo}`);

      // Step 8: Navigate back to Purchase Order for vendor invoice access
      // The invoice is created asynchronously (~40 seconds) so we need to navigate
      // back to PO and wait for it to appear in Alt+6 related documents
      await page.goto(poUrl);
      await page.waitForURL(/\/window\/181\/\d+/, { timeout: 15000 });
      await page.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

      // Wait for the PO detail view to be fully loaded
      await page.locator('.form-field-DocumentNo, .document-header').first()
        .waitFor({ state: 'visible', timeout: 15000 });
      await page.locator('.rotating, .indicator-pending, .loader')
        .waitFor({ state: 'detached', timeout: 10000 }).catch(() => {});

      // Give the page time to be ready for keyboard input
      await page.waitForTimeout(1000);

      console.log(`[${language}] Navigated back to Purchase Order for invoice access`);

      // Step 9: Open related Vendor Invoice using Alt+6 (with polling)
      // This method polls with page refresh until the invoice appears (~60s max wait)
      await PurchaseOrderPage.openRelatedVendorInvoice();

      console.log(`[${language}] Vendor Invoice opened for PO ${poDocumentNo}`);

      // Step 10: Validate Vendor Invoice PDF
      await VendorInvoicePage.downloadAndValidatePdf({
        vendorName: masterdata.bpartners.VENDOR1.bpartnerCode,
        productCode: masterdata.products.Product1.productCode,
        quantity: '5',
        language,
      });

      console.log(`[${language}] Vendor Invoice PDF validated`);
      console.log(`[${language}] Complete purchase-to-invoice workflow finished: PO ${poDocumentNo}`);
    });

    // TODO: Add explicit action test after adding data-testid to dropdown action items
    // test.skip(`Create receipt using explicit action (${label} UI)`, async ({ page }) => {
    //   // This test demonstrates using the explicit action approach
    //   // instead of the default Quick Action
    //   // Currently skipped: Dropdown action items need data-testid attributes
    // });
  });
});
