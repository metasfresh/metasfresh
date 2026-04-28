import { test } from '../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from './common';

/**
 * DocumentReferences - Utility for working with Alt+6 related documents panel.
 *
 * The Alt+6 panel shows document references (e.g., from Purchase Order to Vendor Invoice).
 * References are loaded via Server-Sent Events (SSE) and rendered with data-cy attributes.
 *
 * IMPORTANT: Always use data-cy selectors (language-independent) instead of text-based selectors.
 *
 * Common data-cy patterns (from AD_RelationType.InternalName):
 * - reference-C_Order_to_C_Invoice_PO  - Purchase Order → Vendor Invoice
 * - reference-C_Order_to_M_InOut_PO    - Purchase Order → Material Receipt
 * - reference-C_Order_to_C_Invoice_SO  - Sales Order → Customer Invoice
 * - reference-C_Order_to_M_InOut_SO    - Sales Order → Shipment
 *
 * Usage:
 *   import { openRelatedDocument, waitForReferences } from '../utils/DocumentReferences';
 *
 *   await openRelatedDocument({
 *     dataCy: 'reference-C_Order_to_C_Invoice_PO',
 *     stepName: 'Open related Vendor Invoice',
 *     navigateToDetail: true,
 *   });
 */

/**
 * Wait for SSE references to load in the Alt+6 panel.
 * This handles the case where spinners disappear before SSE finishes streaming.
 *
 * @param {Object} options - Configuration options
 * @param {number} options.timeout - Maximum time to wait for references (default: 8000ms)
 * @returns {Promise<boolean>} True if references loaded, false if timeout
 */
export async function waitForReferences({ timeout = 8000 } = {}) {
  const page = getPage();

  try {
    await page
      .locator('[data-cy^="reference-"]')
      .first()
      .waitFor({
        state: 'visible',
        timeout,
      });
    return true;
  } catch {
    return false;
  }
}

/**
 * Open the Alt+6 references panel and wait for it to load.
 *
 * @param {Object} options - Configuration options
 * @param {number} options.timeout - Maximum time to wait for panel (default: 5000ms)
 * @returns {Promise<boolean>} True if panel opened successfully
 */
export async function openReferencesPanel({ timeout = 5000 } = {}) {
  const page = getPage();

  // Close any existing Alt+6 panel by pressing Escape
  await page.keyboard.press('Escape');
  await page.waitForTimeout(200);

  // Click on the page body to ensure it has focus
  await page.locator('body').click();
  await page.waitForTimeout(100);

  // Press Alt+6 to open related documents panel (tab 1 = Referenced Documents)
  await page.keyboard.press('Alt+6');

  // Wait for the side panel to become visible (class 'order-list-panel-open')
  try {
    await page.locator('.order-list-panel-open').waitFor({
      state: 'visible',
      timeout,
    });
    return true;
  } catch {
    return false;
  }
}

/**
 * Wait for spinners to disappear in the references panel.
 *
 * @param {Object} options - Configuration options
 * @param {number} options.timeout - Maximum time to wait (default: 10000ms)
 */
export async function waitForSpinnersToDisappear({ timeout = 10000 } = {}) {
  const page = getPage();

  await page
    .locator('.rotating, .spinner, .indicator-pending')
    .waitFor({
      state: 'detached',
      timeout,
    })
    .catch(() => {
      // No spinners present
    });
}

/**
 * Get all visible references with their data-cy attributes for debugging.
 *
 * @returns {Promise<Array<{dataCy: string, text: string}>>} Array of reference info
 */
export async function getVisibleReferences() {
  const page = getPage();

  const allRefs = await page.locator('[data-cy^="reference-"]').all();
  return Promise.all(
    allRefs.map(async (el) => ({
      dataCy: await el.getAttribute('data-cy'),
      text: await el.textContent().catch(() => 'N/A'),
    }))
  );
}

/**
 * Open a related document via Alt+6 panel using language-independent data-cy selector.
 *
 * This method:
 * 1. Opens the Alt+6 panel (with retries and page refresh if needed)
 * 2. Waits for SSE references to load
 * 3. Clicks the reference link identified by data-cy attribute
 * 4. Optionally navigates to detail view if landing on list view
 *
 * @param {Object} options - Configuration options
 * @param {string} options.dataCy - The data-cy attribute value (e.g., 'reference-C_Order_to_C_Invoice_PO')
 * @param {string} options.stepName - Test step name for logging (e.g., 'Open related Vendor Invoice')
 * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
 * @param {number} options.retryDelay - Delay between retries in ms (default: 3000)
 * @param {boolean} options.navigateToDetail - Double-click table row if landing on list view (default: true)
 * @param {boolean} options.refreshOnRetry - Refresh page (F5) before each retry (default: false)
 * @param {Object} options.sseDebug - Enable SSE response debugging (default: null, set to {} to enable)
 */
export async function openRelatedDocument({
  dataCy,
  stepName,
  maxRetries = 5,
  retryDelay = 3000,
  navigateToDetail = true,
  refreshOnRetry = false,
  sseDebug = null,
}) {
  return await test.step(stepName || `Open related document (${dataCy})`, async () => {
    const page = getPage();

    const referenceLink = page.locator(`[data-cy="${dataCy}"]`);

    let lastError = null;
    let sseReferences = []; // Collect references from SSE responses for debugging

    // Set up SSE response listener if debugging is enabled
    const sseResponseHandler = sseDebug
      ? async (response) => {
          const url = response.url();
          if (url.includes('/references/sse')) {
            try {
              const text = await response.text().catch(() => '');
              const lines = text.split('\n').filter((line) => line.startsWith('data:'));
              for (const line of lines) {
                try {
                  const data = JSON.parse(line.substring(5).trim());
                  if (data.type === 'PARTIAL_RESULT' && data.partialGroup?.references) {
                    sseReferences.push(...data.partialGroup.references);
                    console.log(
                      `[SSE] Received ${data.partialGroup.references.length} references from group "${data.partialGroup.caption}":`,
                      data.partialGroup.references.map((r) => `${r.internalName}: "${r.caption}"`)
                    );
                  }
                } catch {
                  // Ignore parse errors for partial chunks
                }
              }
            } catch {
              // Ignore response read errors
            }
          }
        }
      : null;

    if (sseResponseHandler) {
      page.on('response', sseResponseHandler);
    }

    console.log(
      `Waiting for reference link (data-cy="${dataCy}") (max ${(maxRetries * retryDelay) / 1000}s)...`
    );

    for (let attempt = 1; attempt <= maxRetries; attempt++) {
      try {
        // Refresh the page on retry if enabled (useful for async document creation)
        if (attempt > 1 && refreshOnRetry) {
          console.log(`[Attempt ${attempt}/${maxRetries}] Refreshing page...`);
          await page.keyboard.press('F5');
          await page.waitForLoadState('networkidle', { timeout: 15000 }).catch(() => {});
          await page.waitForTimeout(1000);
        }

        // Open the references panel
        const panelOpened = await openReferencesPanel();
        console.log(`[Attempt ${attempt}] Panel opened: ${panelOpened}`);

        // Wait for spinners to disappear
        await waitForSpinnersToDisappear();

        // Wait for SSE references to load
        const referencesLoaded = await waitForReferences();
        console.log(`[Attempt ${attempt}] References loaded: ${referencesLoaded}`);

        // Check if the reference link exists
        const linkVisible = await referenceLink.isVisible().catch(() => false);

        if (!linkVisible) {
          // Debug: Log all visible references
          const refs = await getVisibleReferences();
          console.log(`[Attempt ${attempt}] Found ${refs.length} references with data-cy:`);
          refs.forEach((ref) => console.log(`  - ${ref.dataCy}: "${ref.text}"`));

          throw new Error(`Reference link (data-cy="${dataCy}") not yet visible`);
        }

        // Link is visible - click it
        await referenceLink.click();

        // Wait for navigation to start
        await page.waitForTimeout(1000);

        // Wait for table row OR detail view elements
        const tableRow = page.locator('.table-row, [class*="table-row"]').first();
        const printButton = page.getByTestId('action-Print');

        // Try waiting for either element (list view table row or detail view print button)
        await Promise.race([
          tableRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT }),
          printButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT }),
        ]).catch(async () => {
          // If neither appears, wait for any content to load
          await page.waitForLoadState('networkidle', { timeout: 10000 }).catch(() => {});
        });

        // Wait for loading spinners to disappear
        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({
            state: 'detached',
            timeout: SLOW_ACTION_TIMEOUT,
          })
          .catch(() => {
            // No spinners present
          });

        // If requested, navigate to detail view if we landed on list view
        if (navigateToDetail) {
          const hasTableRow = await tableRow.isVisible().catch(() => false);
          const hasPrintButton = await printButton.isVisible().catch(() => false);

          if (hasTableRow && !hasPrintButton) {
            // We're on list view - DOUBLE-click the first row to navigate to detail view
            console.log('On list view, double-clicking first row to open detail view...');
            await tableRow.dblclick();

            // Wait for detail view to load (indicated by Print button or other detail elements)
            await printButton
              .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
              .catch(() => {
                // Print button may not always be present in detail view
              });

            // Wait for any loading to complete
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

            await page
              .locator('.rotating, .indicator-pending')
              .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
              .catch(() => {});
          }
        }

        // Success - exit the retry loop
        console.log(
          `Reference link clicked on attempt ${attempt} (after ~${((attempt - 1) * retryDelay) / 1000}s wait)`
        );

        // Clean up SSE handler if enabled
        if (sseResponseHandler) {
          page.off('response', sseResponseHandler);
        }

        return;
      } catch (error) {
        lastError = error;

        if (attempt < maxRetries) {
          console.log(`[Attempt ${attempt}/${maxRetries}] ${error.message}. Waiting ${retryDelay}ms...`);
          await page.waitForTimeout(retryDelay);
        }
      }
    }

    // All retries exhausted - clean up and report
    if (sseResponseHandler) {
      page.off('response', sseResponseHandler);

      // Log SSE summary for debugging
      if (sseReferences.length > 0) {
        console.log(`[SSE SUMMARY] Total references collected from SSE during all attempts:`);
        const uniqueRefs = [...new Map(sseReferences.map((r) => [r.internalName, r])).values()];
        uniqueRefs.forEach((r) =>
          console.log(`  - ${r.internalName}: "${r.caption}" (window: ${r.targetWindowId})`)
        );
        const found = uniqueRefs.some(
          (r) => `reference-${r.internalName}` === dataCy || r.internalName === dataCy
        );
        console.log(`[SSE SUMMARY] Looking for "${dataCy}", found in SSE: ${found ? 'YES' : 'NO'}`);
      } else {
        console.log(`[SSE SUMMARY] No references received via SSE - panel may not have opened or loaded`);
      }
    }

    const totalWaitTime = (maxRetries * retryDelay) / 1000;
    throw new Error(
      `Failed to find reference link (data-cy="${dataCy}") after ${maxRetries} attempts (~${totalWaitTime}s). ` +
        `The async document creation may have failed or taken longer than expected. ` +
        `Last error: ${lastError?.message}`
    );
  });
}

/**
 * Common data-cy values for document references.
 * Use these constants instead of hardcoding strings.
 *
 * These come from AD_RelationType.InternalName in the database:
 * SELECT AD_RelationType_ID, InternalName, Name FROM AD_RelationType WHERE IsActive='Y';
 *
 * Note: If a relation doesn't have an InternalName, the data-cy will be:
 * reference-AD_RelationType_ID-{id}
 */
export const REFERENCE_DATA_CY = {
  // =====================================================================
  // Purchase Order relations (POTrx, IsSOTrx='N')
  // =====================================================================
  PO_TO_VENDOR_INVOICE: 'reference-C_Order_to_C_Invoice_PO',
  PO_TO_MATERIAL_RECEIPT: 'reference-Receipt',
  PO_TO_RECEIPT_CANDIDATES: 'reference-M_ReceiptSchedule',
  PO_TO_INVOICE_CANDIDATES: 'reference-C_Invoice_Candidate_Purchase',
  PO_TO_SALES_ORDER: 'reference-C_Order_PO_to_C_Order_SO',
  PO_TO_INVENTORY_LINE: 'reference-C_Order_PO_to_M_InventoryLine',
  PO_TO_PROJECT: 'reference-C_Order_PO_to_C_Project',
  PO_TO_FORECAST: 'reference-C_Order_to_M_Forecast',

  // =====================================================================
  // Sales Order relations (SOTrx, IsSOTrx='Y')
  // =====================================================================
  SO_TO_CUSTOMER_INVOICE: 'reference-C_Order_to_C_Invoice_SO',
  SO_TO_SHIPMENT: 'reference-C_Order_to_M_InOut_SO',
  SO_TO_SHIPMENT_SCHEDULE: 'reference-M_ShipmentSchedule',
  SO_TO_INVOICE_CANDIDATES: 'reference-C_Invoice_Candidate_Sales',
  SO_TO_PURCHASE_ORDER: 'reference-C_Order_SO_to_C_Order_PO',
  SO_TO_QUOTATION: 'reference-C_Order_SO_to_C_Order_Quotation',
  SO_TO_PROJECT: 'reference-C_Order_SO_to_C_Project',
  SO_TO_FLATRATE_TERM: 'reference-C_Order_to_C_FlatRate_Term',
  SO_TO_OLCAND: 'reference-C_Order_to_C_OLCand',
  SO_TO_PP_ORDER_CANDIDATE: 'reference-C_Order_SO_to_PP_Order_Candidate',

  // =====================================================================
  // Invoice Candidate relations
  // =====================================================================
  IC_TO_VENDOR_INVOICE: 'reference-C_Invoice_Candidate_to_C_Invoice_PO',
  IC_SALES_TO_INVOICE: 'reference-C_Invoice_Candidate_Sales_C_Invoice',
  IC_PURCHASE_TO_INVOICE: 'reference-C_Invoice_Candidate_Purchase_C_Invoice',

  // =====================================================================
  // Shipment Schedule relations
  // =====================================================================
  SHIPMENT_SCHEDULE_TO_SHIPMENT: 'reference-M_ShipmentSchedule_to_M_InOut',

  // =====================================================================
  // Shipment (M_InOut SOTrx) relations
  // =====================================================================
  SHIPMENT_TO_SALES_ORDER: 'reference-M_InOut_to_C_Order_SO',
  SHIPMENT_TO_CUSTOMER_INVOICE: 'reference-M_InOut_to_C_Invoice_SO',
  SHIPMENT_TO_SHIPMENT_SCHEDULE: 'reference-M_InOut_to_M_ShipmentSchedule',
  SHIPMENT_TO_PROJECT: 'reference-M_InOut_SO_to_C_Project',

  // =====================================================================
  // Material Receipt (M_InOut POTrx) relations
  // =====================================================================
  RECEIPT_TO_PURCHASE_ORDER: 'reference-M_InOut_to_C_Order_PO',
  RECEIPT_TO_VENDOR_INVOICE: 'reference-M_InOut_to_C_Invoice_PO',

  // =====================================================================
  // Sales Invoice (C_Invoice SOTrx) relations
  // =====================================================================
  SALES_INVOICE_TO_SALES_ORDER: 'reference-C_Invoice_to_C_Order_SO',
  SALES_INVOICE_TO_SHIPMENT: 'reference-C_Invoice_to_M_InOut_SO',
  SALES_INVOICE_TO_INVOICE_CANDIDATE: 'reference-C_Invoice_to_C_Invoice_Candidate_SO',
  SALES_INVOICE_TO_DUNNING: 'reference-C_Invoice-C_DunningDoc',
  SALES_INVOICE_TO_PAY_SELECTION: 'reference-C_Invoice_SO_to_C_PaySelection',
  SALES_INVOICE_TO_PROJECT: 'reference-C_Invoice_SO_to_C_Project',
  SALES_INVOICE_TO_REF_INVOICE: 'reference-C_Invoice_to_Ref_C_Invoice_SO',

  // =====================================================================
  // Vendor Invoice (C_Invoice POTrx) relations
  // =====================================================================
  VENDOR_INVOICE_TO_PURCHASE_ORDER: 'reference-C_Invoice_to_C_Order_PO',
  VENDOR_INVOICE_TO_RECEIPT: 'reference-C_Invoice_to_M_InOut_PO',
  VENDOR_INVOICE_TO_INVOICE_CANDIDATE: 'reference-C_Invoice_to_C_Invoice_Candidate_PO',
  VENDOR_INVOICE_TO_PAY_SELECTION: 'reference-C_Invoice_PO_to_C_PaySelection',
  VENDOR_INVOICE_TO_PROJECT: 'reference-C_Invoice_PO_to_C_Project',
  VENDOR_INVOICE_TO_REF_INVOICE: 'reference-C_Invoice_to_Ref_C_Invoice_PO',

  // =====================================================================
  // Payment relations
  // =====================================================================
  INVOICE_TO_PAYMENT: 'reference-C_Invoice_to_C_Payment',
  PAYMENT_TO_PAY_SELECTION: 'reference-C_Payment_to_C_PaySelection',
};
