import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';

/**
 * Partial Receipt Workflow E2E test suite.
 *
 * Features tested (from Google Sheets):
 * - F00600: Purchase Order (Epic: Purchasing)
 * - F65010: Material Receipt Candidates (Epic: Material Receipt)
 *
 * Tests the partial receipt workflow where a single purchase order
 * is fulfilled through multiple receipt operations:
 *
 * 1. Create a purchase order with qty=10
 * 2. Complete the purchase order
 * 3. Navigate to Receipt Candidates (Alt+6)
 * 4. Create first partial receipt with qty=6 using "Receive CUs with Qty"
 * 5. Create second partial receipt with qty=4 using "Receive CUs with Qty"
 * 6. Validate both partial receipt PDFs
 *
 * This validates:
 * - Parameterized Quick Action (WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam)
 * - Parameter dialog input for quantity
 * - Multiple receipts from single PO line
 * - PDF validation for each partial receipt
 *
 * ## Video Recording Behavior
 *
 * NOTE: This test opens new browser tabs for Material Receipt navigation
 * (via "Zoom Into" context menu). Playwright records each tab separately,
 * which may result in multiple video files in the test results:
 * - Main video: Shows the primary workflow on the main page
 * - Additional videos (video-1.webm, etc.): Show Material Receipt tab content
 *
 * This is expected behavior due to Playwright's per-page video recording.
 * All videos together document the complete workflow.
 *
 * ## PDF Attachments
 *
 * Downloaded PDFs are automatically attached to the Allure report after validation.
 * This allows verification of PDF content without accessing the CI artifacts directly.
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Partial Receipt Workflow (${label})`, () => {
    // Increase timeout for this complex multi-receipt workflow
    test.setTimeout(120000);

    test(`Create partial receipts: PO qty=10 → Receipt1 qty=6 → Receipt2 qty=4 (${label} UI)`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0140: Purchasing');
      allure.tag('F00600: Purchase Order');
      allure.tag('F00600');
      allure.tag('F65010: Material Receipt Candidates');
      allure.tag('F65010');
      allure.story('Partial receipt workflow with multiple receipts');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.parameter('UI Label', label);
      allure.tag(language);

      allure.description(`
## E0140: Purchasing

## F00600: Purchase Order
## F65010: Material Receipt Candidates

### Test Scenario: Partial Receipt Workflow
This test validates the partial receipt workflow where a purchase order
is fulfilled through multiple receipt operations:

1. **Create Purchase Order** - New PO with vendor and product line (qty=10)
2. **Complete Order** - Mark as completed to trigger downstream processes
3. **Navigate to Receipt Candidates** - Use Alt+6 to open related receipt candidates
4. **Create First Partial Receipt** - Receive 6 units using parameterized action
5. **Create Second Partial Receipt** - Receive remaining 4 units
6. **Validate First Receipt PDF** - Verify qty=6 in PDF
7. **Validate Second Receipt PDF** - Verify qty=4 in PDF

## Features Tested
- **F00600**: Purchase Order
- **F65010**: Material Receipt Candidates

## Quick Action Used
- WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam (Receive CUs with Qty)

## Business Value
Ensures partial receipt workflows work correctly, allowing vendors to deliver
goods in multiple shipments while maintaining accurate inventory and documentation.
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
              name: `Partial Receipt Product (${language})`,
              type: 'Item',
              prices: [
                {
                  price: 15.0,
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

      // Step 1: Create a purchase order with qty=10
      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
      await PurchaseOrderPage.goToOrderLineTab();
      await PurchaseOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: '10' });
      await PurchaseOrderPage.complete();

      // Get the purchase order document number and URL
      const poDocumentNo = await PurchaseOrderPage.getDocumentNo();
      const poUrl = page.url(); // Save the PO URL for later navigation

      // Verify document number was assigned
      expect(poDocumentNo).toBeTruthy();
      expect(poDocumentNo.length).toBeGreaterThan(0);

      console.log(`[${language}] Purchase Order created: ${poDocumentNo}`);

      // Step 2: Open related receipt candidate using Alt+6
      await PurchaseOrderPage.openRelatedReceiptCandidate();
      await ReceiptCandidatesPage.expectQuickActionsVisible();

      // Save the receipt candidate URL for later navigation
      const receiptCandidateUrl = page.url();
      console.log(`[${language}] Receipt Candidate opened for PO ${poDocumentNo}`);
      console.log(`[${language}] Receipt Candidate URL: ${receiptCandidateUrl}`);

      // Step 3: Create first partial receipt with qty=6
      await ReceiptCandidatesPage.receiveCUsWithQuantity(6);

      console.log(`[${language}] First partial receipt created (qty=6)`);

      // Step 4: Navigate to the created Material Receipt and validate PDF
      // IMPORTANT: navigateToMaterialReceiptViaTab opens a NEW TAB and switches global.currentPage
      // We need to track pages and switch back after PDF validation
      await ReceiptCandidatesPage.navigateToMaterialReceiptViaTab();

      // Get and save first receipt document number
      const receipt1DocNo = await ReceiptCandidatesPage.getDocumentNo();
      console.log(`[${language}] First Material Receipt: ${receipt1DocNo}`);

      // Validate first receipt PDF (qty=6)
      await ReceiptCandidatesPage.downloadAndValidatePdf({
        documentNo: receipt1DocNo,
        vendorName: masterdata.bpartners.VENDOR1.bpartnerCode,
        productCode: masterdata.products.Product1.productCode,
        quantity: '6',
        language,
      });

      console.log(`[${language}] First receipt PDF validated (qty=6)`);

      // Step 5: Navigate back to PO and use Alt+6 to find remaining receipt candidate
      // After a partial receipt, the SAME receipt candidate may be updated to show remaining qty,
      // OR a NEW receipt candidate may be created. Either way, Alt+6 from PO will show the correct one.
      const materialReceiptPage = global.currentPage;
      await materialReceiptPage.close();
      console.log(`[${language}] Closed Material Receipt tab`);

      // Switch to remaining page and navigate to PO
      const allPages = page.context().pages();
      const remainingPage = allPages[0];
      global.currentPage = remainingPage;
      await remainingPage.bringToFront();

      // Navigate to Purchase Order
      await remainingPage.goto(poUrl);
      await remainingPage.waitForURL(/\/window\/181\/\d+/, { timeout: 15000 });
      await remainingPage.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});

      // Wait for page to be fully loaded (spinners to disappear)
      await remainingPage.locator('.rotating, .indicator-pending, .loader')
        .waitFor({ state: 'detached', timeout: 15000 }).catch(() => {});

      console.log(`[${language}] Navigated back to PO: ${poUrl}`);

      // Use Alt+6 to open related receipt candidate
      // This will show the receipt candidate with remaining qty (4)
      await PurchaseOrderPage.openRelatedReceiptCandidate();
      await ReceiptCandidatesPage.expectQuickActionsVisible();

      console.log(`[${language}] Receipt Candidate ready for second partial receipt (via Alt+6)`);

      // Step 6: Create second partial receipt with qty=4
      await ReceiptCandidatesPage.receiveCUsWithQuantity(4);

      console.log(`[${language}] Second partial receipt created (qty=4)`);

      // Step 7: Navigate to the second Material Receipt and validate PDF
      // IMPORTANT: Allocation table is sorted oldest-first (by ID ascending)
      // After second receipt: row 0 = first receipt, row 1 (last) = second receipt
      await ReceiptCandidatesPage.navigateToMaterialReceiptViaTab({ rowIndex: 1 });

      // Get second receipt document number
      const receipt2DocNo = await ReceiptCandidatesPage.getDocumentNo();
      console.log(`[${language}] Second Material Receipt: ${receipt2DocNo}`);

      // Verify it's a different receipt than the first one
      expect(receipt2DocNo).not.toEqual(receipt1DocNo);

      // Validate second receipt PDF (qty=4)
      // Note: For partial receipts, the PDF may show "1000012 ff." instead of the actual doc number
      // because metasfresh uses "ff." (and following) notation for subsequent receipts
      // So we skip document number validation for the second receipt
      await ReceiptCandidatesPage.downloadAndValidatePdf({
        documentNo: receipt2DocNo,
        vendorName: masterdata.bpartners.VENDOR1.bpartnerCode,
        productCode: masterdata.products.Product1.productCode,
        quantity: '4',
        language,
        skipDocNumberValidation: true, // PDF shows "first_doc ff." format for partial receipts
      });

      console.log(`[${language}] Second receipt PDF validated (qty=4)`);

      // Final verification: Both partial receipts created and validated
      console.log(`[${language}] Partial receipt workflow complete:`);
      console.log(`  - PO: ${poDocumentNo} (qty=10)`);
      console.log(`  - Receipt 1: ${receipt1DocNo} (qty=6)`);
      console.log(`  - Receipt 2: ${receipt2DocNo} (qty=4)`);
    });
  });
});
