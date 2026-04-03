import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { RECEIPT_CANDIDATES_WINDOW_ID } from '../utils/WindowIds';
import { getFieldData } from '../utils/WebAPIValidation';

/**
 * PO Reactivation with Receipt Schedule E2E test suite.
 *
 * Features tested:
 * - F00600: Purchase Order (Epic: Purchasing)
 * - F65010: Material Receipt Candidates (Epic: Material Receipt)
 *
 * Tests the receipt schedule close/reopen behavior on PO reactivation:
 * 1. Create PO with 1 line -> Complete
 * 2. Verify receipt schedule exists and is open (IsClosed unchecked)
 * 3. Create actual material receipt
 * 4. Reactivate PO (requires PO_AllowReactivationIfReceiptsCreated=Y)
 * 5. Verify receipt schedule is closed (IsClosed checked), NOT deleted
 * 6. Re-complete PO
 * 7. Verify receipt schedule is reopened (IsClosed unchecked)
 *
 * Precondition: PO_AllowReactivationIfReceiptsCreated=Y sysconfig must be set.
 * For E2E Docker stack: added as -D JVM property in compose.yml JAVA_TOOL_OPTIONS.
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

/**
 * Extract the record ID from the current page URL for a given window ID.
 * @param {import('@playwright/test').Page} page
 * @param {number} windowId
 * @returns {string|null}
 */
function extractRecordIdFromUrl(page, windowId) {
  const url = page.url();
  const match = url.match(new RegExp(`/window/${windowId}/(\\d+)`));
  return match ? match[1] : null;
}

testCases.forEach(({ language, label }) => {
  test.describe(`PO Reactivation — Receipt Schedule Close/Reopen (${label})`, () => {
    test.setTimeout(240000);

    test(`PO reactivation preserves receipt schedule after material receipt (${label} UI)`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0140: Purchasing');
      allure.tag('F00600: Purchase Order');
      allure.tag('F65010: Material Receipt Candidates');
      allure.story('PO reactivation closes receipt schedules instead of deleting them');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## PO Reactivation — Receipt Schedule Close/Reopen

### Test Scenario
Validates that reactivating a Purchase Order closes receipt schedules instead of deleting them,
and re-completing the PO reopens them.

1. **Create PO** with 1 order line
2. **Verify receipt schedule** is open (IsClosed=N)
3. **Create material receipt** via HU-Editor workflow
4. **Reactivate PO** (requires sysconfig PO_AllowReactivationIfReceiptsCreated=Y)
5. **Verify receipt schedule** is closed (IsClosed=Y), same record ID (not recreated)
6. **Re-complete PO**
7. **Verify receipt schedule** is reopened (IsClosed=N), same record ID
      `);

      // =====================================================
      // Step 1: Create test data
      // =====================================================
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
              isSoPriceList: false,
            },
          },
          products: {
            Product1: {
              name: `Test Product for PO Reactivation (${language})`,
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

      // =====================================================
      // Step 2: Login and create Purchase Order
      // =====================================================
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
      await PurchaseOrderPage.goToOrderLineTab();
      await PurchaseOrderPage.addOrderLine({
        product: masterdata.products.Product1.productCode,
        quantity: '10',
      });
      await PurchaseOrderPage.complete();

      const poDocumentNo = await PurchaseOrderPage.getDocumentNo();
      const poUrl = page.url();
      console.log(`[${language}] PO created and completed: ${poDocumentNo}`);

      // =====================================================
      // Step 3: Verify receipt schedule exists and is open
      // =====================================================
      await PurchaseOrderPage.openRelatedReceiptCandidate();

      // Double-click first row to open detail view
      const listRow = page.locator('.table-row, [class*="table-row"]').first();
      await listRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await listRow.dblclick();

      // Wait for detail view
      await page.waitForURL(new RegExp(`/window/${RECEIPT_CANDIDATES_WINDOW_ID}/\\d+`), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Extract receipt schedule record ID
      const rsRecordId = extractRecordIdFromUrl(page, RECEIPT_CANDIDATES_WINDOW_ID);
      expect(rsRecordId).toBeTruthy();
      console.log(`[${language}] Receipt Schedule record ID: ${rsRecordId}`);

      // Verify IsClosed is false via WebAPI
      const isClosedField = await getFieldData(RECEIPT_CANDIDATES_WINDOW_ID, rsRecordId, 'IsClosed');
      expect(isClosedField.value).toBe(false);
      console.log(`[${language}] Receipt Schedule IsClosed=${isClosedField.value} (expected: false)`);

      // =====================================================
      // Step 4: Navigate back to PO and create material receipt
      // =====================================================
      await page.goto(poUrl);
      await page.waitForURL(/\/window\/181\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      await PurchaseOrderPage.openRelatedReceiptCandidate();
      await ReceiptCandidatesPage.expectQuickActionsVisible();
      await ReceiptCandidatesPage.createReceipt();

      console.log(`[${language}] Material Receipt created`);

      // =====================================================
      // Step 5: Navigate back to PO and reactivate
      // =====================================================
      await page.goto(poUrl);
      await page.waitForURL(/\/window\/181\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      await PurchaseOrderPage.reactivate();
      console.log(`[${language}] PO reactivated`);

      // =====================================================
      // Step 6: Verify receipt schedule is closed (not deleted)
      // =====================================================
      // Use WebAPI directly — the closed receipt schedule (Processed=true) is hidden by
      // the default "Filter by: Not Processed" filter in the list view, so we verify via API.
      const isClosedField2 = await getFieldData(RECEIPT_CANDIDATES_WINDOW_ID, rsRecordId, 'IsClosed');
      expect(isClosedField2.value).toBe(true);
      console.log(`[${language}] Receipt Schedule ${rsRecordId} IsClosed=${isClosedField2.value} (expected: true)`);

      // =====================================================
      // Step 7: Navigate back to PO and re-complete
      // =====================================================
      await page.goto(poUrl);
      await page.waitForURL(/\/window\/181\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      await PurchaseOrderPage.complete();
      console.log(`[${language}] PO re-completed`);

      // =====================================================
      // Step 8: Verify receipt schedule is reopened
      // =====================================================
      // Use WebAPI directly to verify the same receipt schedule was reopened.
      const isClosedField3 = await getFieldData(RECEIPT_CANDIDATES_WINDOW_ID, rsRecordId, 'IsClosed');
      expect(isClosedField3.value).toBe(false);
      console.log(`[${language}] Receipt Schedule ${rsRecordId} IsClosed=${isClosedField3.value} (expected: false)`);

      console.log(`[${language}] ===================================`);
      console.log(`[${language}] PO Reactivation Receipt Schedule Test PASSED`);
      console.log(`[${language}] PO: ${poDocumentNo}`);
      console.log(`[${language}] Receipt Schedule ID: ${rsRecordId}`);
      console.log(`[${language}] Cycle: open -> closed -> reopened (same record preserved)`);
      console.log(`[${language}] ===================================`);
    });
  });
});
