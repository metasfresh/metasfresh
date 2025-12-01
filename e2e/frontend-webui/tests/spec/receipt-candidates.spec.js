import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';

/**
 * Purchase-to-Invoice E2E test suite.
 *
 * Tests the complete purchase-to-invoice workflow:
 * 1. Create a purchase order
 * 2. Complete the purchase order
 * 3. Navigate to Receipt Candidates (Alt+6)
 * 4. Create material receipt via Quick Action
 * 5. Navigate back to Purchase Order
 * 6. Navigate to Invoice Candidates (Alt+6)
 * 7. Generate invoice via Quick Action
 *
 * This validates language-independent selectors for:
 * - Quick Actions button (data-testid="quick-action-button")
 * - Quick Actions dropdown (data-testid="quick-action-dropdown-toggle")
 * - Specific actions (data-testid="quick-action-{internalName}")
 * - Modal buttons (data-testid="modal-done", data-testid="modal-start")
 * - Related documents navigation (Alt+6)
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Purchase-to-Invoice Workflow (${label})`, () => {
    test(`Complete purchase-to-invoice flow: PO → Receipt → Invoice (${label} UI)`, async ({ page }) => {
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
              value: `TEST-RECEIPT-${language}-001`,
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

      console.log(`✓ Purchase Order created: ${poDocumentNo} (${label} UI)`);

      // Step 2: Open related receipt candidate using Alt+6
      // This navigates directly to the correct receipt candidate for this PO
      // IMPORTANT: Do NOT navigate to window 540196 directly - it selects wrong candidate!
      await PurchaseOrderPage.openRelatedReceiptCandidate();

      // Verify Quick Actions button is visible (confirms correct candidate selected)
      await ReceiptCandidatesPage.expectQuickActionsVisible();

      // Step 3: Create material receipt using default Quick Action
      await ReceiptCandidatesPage.createReceipt();

      console.log(`✓ Material Receipt created from PO ${poDocumentNo} (${label} UI)`);

      // Step 4: Navigate back to the Purchase Order to access invoice candidates
      // Use the saved PO URL from step 1
      await page.goto(poUrl);

      // Wait for PO detail view to load
      await page.waitForURL(/\/window\/181\/\d+/, {
        timeout: 10000,
      });

      await page.waitForLoadState('networkidle', {
        timeout: 10000,
      }).catch(() => {
        // Ignore timeout
      });

      await page.waitForTimeout(1000);

      console.log(`✓ Navigated back to Purchase Order ${poDocumentNo} (${label} UI)`);

      // Step 5: Open related invoice candidate using Alt+6
      // This navigates directly to the correct invoice candidate for this PO
      // Note: Invoice candidates are created asynchronously after material receipt
      // We need to wait longer for them to be generated (up to 15 seconds)
      await PurchaseOrderPage.openRelatedInvoiceCandidate(15000);

      // Verify Quick Actions button is visible (confirms correct candidate selected)
      await InvoiceCandidatePage.expectQuickActionsVisible();

      console.log(`✓ Invoice Candidate opened for PO ${poDocumentNo} (${label} UI)`);

      // Step 6: Generate invoice using Quick Action
      await InvoiceCandidatePage.generateInvoice();

      console.log(`✓ Invoice generated from PO ${poDocumentNo} (${label} UI)`);

      // The invoice generation is now complete
      // We could verify the invoice document number, but that's optional
    });

    // TODO: Add explicit action test after adding data-testid to dropdown action items
    // test.skip(`Create receipt using explicit action (${label} UI)`, async ({ page }) => {
    //   // This test demonstrates using the explicit action approach
    //   // instead of the default Quick Action
    //   // Currently skipped: Dropdown action items need data-testid attributes
    // });
  });
});
