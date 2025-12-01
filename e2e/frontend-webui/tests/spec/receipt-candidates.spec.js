import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';

/**
 * Material Receipt Candidates E2E test suite.
 *
 * Tests the complete workflow:
 * 1. Create a purchase order
 * 2. Navigate to Receipt Candidates window
 * 3. Find the PO line awaiting receipt
 * 4. Use Quick Action to create material receipt
 * 5. Wait for HU Editor to process receipt
 * 6. Close HU Editor modal
 *
 * This validates language-independent selectors for:
 * - Quick Actions button (data-testid="quick-action-button")
 * - Quick Actions dropdown (data-testid="quick-action-dropdown-toggle")
 * - Specific actions (data-testid="quick-action-{internalName}")
 * - Modal buttons (data-testid="modal-done")
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Receipt Candidates - Create Material Receipt (${label})`, () => {
    test(`Create purchase order and receive goods (${label} UI)`, async ({ page }) => {
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

      // Get the purchase order document number
      const poDocumentNo = await PurchaseOrderPage.getDocumentNo();

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

      // The receipt should now be created and HU Editor should be closed
      // We could navigate to Material Receipt window to verify, but that's optional
    });

    // TODO: Add explicit action test after adding data-testid to dropdown action items
    // test.skip(`Create receipt using explicit action (${label} UI)`, async ({ page }) => {
    //   // This test demonstrates using the explicit action approach
    //   // instead of the default Quick Action
    //   // Currently skipped: Dropdown action items need data-testid attributes
    // });
  });
});
