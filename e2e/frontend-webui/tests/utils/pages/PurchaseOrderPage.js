import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, } from '../common';
import { PURCHASE_ORDER_WINDOW_ID } from '../WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from '../WebAPIValidation';
import { openRelatedDocument, REFERENCE_DATA_CY } from '../DocumentReferences';

/**
 * Page object for Purchase Order window (ID: 181).
 * Handles creating and completing purchase orders in metasfresh.
 */
export class PurchaseOrderPage {
  /**
   * Navigate to Purchase Order window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('PurchaseOrderPage - Navigate to Purchase Order window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${PURCHASE_ORDER_WINDOW_ID}`);

      // Wait for document list to load
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });

      // Wait for network to settle
      await page
        .waitForLoadState('networkidle', {
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore timeout
        });

      // Wait for any loading spinners to disappear
      await page
        .locator('.rotating, .panel-spaced-lg')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Give the window a moment to be ready for keyboard input
      await page.waitForTimeout(500);
    });
  }

  /**
   * Create a new purchase order using Alt+N keyboard shortcut.
   * Waits for the new document detail view to load.
   */
  static async clickNew() {
    return await test.step('PurchaseOrderPage - Create new order (Alt+N)', async () => {
      const page = getPage();

      // Click on the page body to ensure it has focus
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Use keyboard shortcut Alt+N to create new document
      await page.keyboard.press('Alt+N');

      // Wait for navigation to new document (URL pattern: /window/181/{documentId})
      await page.waitForURL(/\/window\/181\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the document to fully load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore timeout - some pages may not reach networkidle
      });

      // Wait for loading spinners to disappear
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Give the form time to initialize
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Get the record ID from the current URL.
   * @returns {string} Record ID
   */
  static getRecordId() {
    const page = getPage();
    const url = page.url();
    const recordId = url.split('/').pop();
    return recordId;
  }

  /**
   * Select a business partner using the lookup widget.
   * This method waits for the record to be saved after business partner selection.
   * Language-independent: Uses database field name C_BPartner_ID
   * @param {string} partnerName - Name of the business partner to search for
   * @returns {Promise<string>} Record ID
   */
  static async selectBusinessPartner(partnerName) {
    return await test.step(`PurchaseOrderPage - Select business partner: ${partnerName}`, async () => {
      const page = getPage();

      // Language-independent selector: use lookup ID based on database column name
      const bpartnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
      await bpartnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await bpartnerInput.click();

      // Wait for initial loading spinner to disappear (vendors list being loaded)
      await page.locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore if no spinner exists
      });

      // Small delay to ensure dropdown is ready
      await page.waitForTimeout(300);

      // Fill the business partner name
      await bpartnerInput.fill(partnerName);

      // Wait for debounce/input processing (autocomplete might have debounce)
      await page.waitForTimeout(500);

      // Wait for any search spinner to disappear (after typing, might trigger another search)
      await page.locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore if no spinner exists
      });

      // Wait for dropdown to populate
      await page.waitForTimeout(300);

      // Click the option by text - finds element containing the partner name
      await page.locator('.input-dropdown-list-option').getByText(partnerName).first().click();

      // Wait for dropdown to close
      await page.locator('.input-dropdown-list').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Trigger blur by pressing Tab to move to next field (this triggers the save)
      await page.keyboard.press('Tab');

      // Wait for save operation to start (spinner appears)
      await page.waitForTimeout(500);

      // Wait for save operations to complete (spinner disappears)
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Additional wait for auto-fill to complete
      await page.waitForTimeout(3000);

      // Get record ID from URL
      const recordId = this.getRecordId();

      // Wait for record to be saved (auto-fill completes and all mandatory fields are valid)
      await waitForRecordSaved(PURCHASE_ORDER_WINDOW_ID, recordId, {
        maxRetries: 20,
        retryDelayMs: 1000,
      });

      console.log(`Purchase Order ${recordId} saved successfully`);

      return recordId;
    });
  }

  /**
   * Set the order date by clicking on the date picker.
   * Language-independent: Uses database field name DateOrdered
   * @param {number} day - Day of month to select (e.g., 30)
   */
  static async setOrderDate(day = 30) {
    return await test.step(`PurchaseOrderPage - Set order date: day ${day}`, async () => {
      const page = getPage();

      // Language-independent selector: use form-field class with database column name
      const dateField = page.locator('.form-field-DateOrdered .form-control').first();

      await dateField.click();

      // Wait for date picker to open
      await page.waitForTimeout(300);

      // Click on the specified day
      await page.getByRole('cell', { name: day.toString() }).nth(1).click();

      // Wait for date to be set
      await page.waitForTimeout(500);
    });
  }

  /**
   * Switch to the Order Line tab (if needed).
   * Note: Batch entry button may be accessible from main screen, so this might not be needed.
   */
  static async goToOrderLineTab() {
    return await test.step('PurchaseOrderPage - Go to Order Line tab', async () => {
      const page = getPage();

      // Check if batch entry button is already visible (meaning we're on the right tab or it's accessible)
      // Language-independent: Use data-testid
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      const isVisible = await batchEntryButton.isVisible().catch(() => false);

      if (isVisible) {
        console.log('Batch entry button already visible - skipping tab switch');
        return;
      }

      // Click on "PO Line" tab
      // Language-independent: Use existing ID or data-testid
      await page.locator('#tab_POLine, [data-testid="tab-po-line"]').click();

      // Wait for network to settle after tab switch
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Wait for batch entry button to be visible (proves tab is ready)
      await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * Add an order line using batch entry.
   * IMPORTANT: Parent record must be saved before calling this method.
   * Use waitForTabAllowsNew() or call selectBusinessPartner() first which waits for save.
   *
   * Language-independent: Uses database field names M_Product_ID, QtyEntered
   * @param {Object} lineData - Order line data
   * @param {string} lineData.product - Product name to search for
   * @param {number} lineData.quantity - Quantity to order
   * @param {string} lineData.recordId - Optional record ID (will extract from URL if not provided)
   */
  static async addOrderLine({ product, quantity, recordId, maxAttempts = 3 }) {
    return await test.step(`PurchaseOrderPage - Add order line: ${product} x ${quantity}`, async () => {
      const page = getPage();

      // Get record ID if not provided
      const effectiveRecordId = recordId || this.getRecordId();

      // Wait for tab to allow creating new records
      // Tab ID for Purchase Order Lines: AD_Tab-293 (internalName: C_OrderLine)
      await waitForTabAllowsNew(PURCHASE_ORDER_WINDOW_ID, effectiveRecordId, 'AD_Tab-293', {
        maxRetries: 15,
        retryDelayMs: 1000,
      });

      console.log(`Purchase Order Lines tab ready for record ${effectiveRecordId}`);

      for (let attempt = 1; attempt <= maxAttempts; attempt++) {
        console.log(`addOrderLine attempt ${attempt}/${maxAttempts}`);

        const batchEntryButton = page.getByTestId('batch-entry-toggle');
        await batchEntryButton.scrollIntoViewIfNeeded();
        await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await batchEntryButton.click();
        await page.waitForTimeout(500);

        const quickInputVisible = await page
          .locator('.quick-input-container')
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (!quickInputVisible) {
          console.log(`Quick input container not visible on attempt ${attempt}, retrying...`);
          await page.keyboard.press('Escape');
          await page.waitForTimeout(1000);
          continue;
        }

        const productInput = page.locator('#lookup_M_Product_ID input.input-field');
        await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await productInput.click();
        await page.waitForTimeout(300);
        await productInput.fill(product);
        await page.waitForTimeout(1000);

        await page
          .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(500);

        const dropdownOption = page.locator('.input-dropdown-list-option').getByText(product).first();
        const optionVisible = await dropdownOption
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (!optionVisible) {
          console.log(`Product dropdown option not visible on attempt ${attempt}, retrying...`);
          await page.keyboard.press('Escape');
          await page.waitForTimeout(500);
          await batchEntryButton.click().catch(() => {});
          await page.waitForTimeout(1000);
          await page.keyboard.press('F5');
          await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
          await page.waitForTimeout(2000);
          continue;
        }

        await dropdownOption.click();
        await page.waitForTimeout(500);

        // Fill quantity — scope to .quick-input-container to avoid matching other spinbuttons
        const quantityInput = page.locator('.quick-input-container').getByRole('spinbutton');
        await quantityInput.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
        await quantityInput.click();
        await quantityInput.fill(quantity.toString());
        await page.waitForTimeout(300);

        await page.keyboard.press('Enter');
        await page.waitForTimeout(2000);

        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        // Close batch entry (only if still open)
        const isStillOpen = await page.locator('.quick-input-container').isVisible().catch(() => false);
        if (isStillOpen) {
          await page.getByTestId('batch-entry-toggle').click();
          await page.waitForTimeout(1000);
        }

        // Verify that at least one order line was added
        const gridRows = page.locator('table tbody tr');
        const rowCount = await gridRows.count();
        if (rowCount > 0) {
          console.log(`Order line added successfully on attempt ${attempt} (${rowCount} row(s))`);
          return;
        }

        console.log(`No order lines found after attempt ${attempt}, reloading page...`);
        await page.keyboard.press('F5');
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(2000);
      }

      throw new Error(`Failed to add order line after ${maxAttempts} attempts`);
    });
  }

  /**
   * Complete the purchase order using the document action.
   * Retries up to maxAttempts times, using language-independent data-testid selectors
   * to detect whether the Complete (CO) action is still available.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxAttempts - Maximum retry attempts (default: 3)
   */
  static async complete({ maxAttempts = 3 } = {}) {
    return await test.step('PurchaseOrderPage - Complete order', async () => {
      const page = getPage();

      for (let attempt = 1; attempt <= maxAttempts; attempt++) {
        const coOption = page.getByTestId('status-CO');

        // Check if dropdown is already open from a previous failed attempt
        const isAlreadyOpen = await coOption.isVisible().catch(() => false);
        if (!isAlreadyOpen) {
          await page.getByTestId('status-button').click();
          await page.waitForTimeout(500);
        }

        // Language-independent: check if Complete action is available
        const coVisible = await coOption
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (!coVisible) {
          console.log(`Complete action (status-CO) not available — document likely already completed`);
          await page.keyboard.press('Escape');
          return;
        }

        console.log(`Complete attempt ${attempt}/${maxAttempts}`);

        await coOption.click();
        await page.waitForTimeout(3000);

        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        // Language-independent verification: open dropdown and check if CO is still there
        await page.getByTestId('status-button').click();
        await page.waitForTimeout(500);
        const stillHasCO = await coOption.isVisible().catch(() => false);
        await page.keyboard.press('Escape');

        if (!stillHasCO) {
          console.log(`Order completed successfully on attempt ${attempt}`);
          return;
        }

        console.log(`Order still shows CO action after attempt ${attempt}, retrying...`);
        await page.waitForTimeout(2000);
      }

      throw new Error(`Failed to complete order after ${maxAttempts} attempts`);
    });
  }

  /**
   * Get the document number of the current order.
   * Language-independent: Uses database field name DocumentNo
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('PurchaseOrderPage - Get document number', async () => {
      const page = getPage();

      // Language-independent selector: use form-field class with database column name
      const documentNoInput = page.locator('.form-field-DocumentNo input, .form-field-DocumentNo .form-control');

      const docNo = await documentNoInput.inputValue();

      expect(docNo).toBeTruthy();
      expect(docNo.length).toBeGreaterThan(0);

      return docNo;
    });
  }

  /**
   * Wait for the order detail view to load after clicking New or opening an existing order.
   */
  static async waitForDetailView() {
    return await test.step('PurchaseOrderPage - Wait for detail view', async () => {
      const page = getPage();

      // Wait for URL to match detail view pattern
      await page.waitForURL(/\/window\/181\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the form to be visible
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore timeout
      });
    });
  }

  /**
   * Open the related Material Receipt Candidates window using ALT-6.
   * This navigates directly to the receipt candidate(s) created for this purchase order.
   *
   * IMPORTANT: Use this method instead of navigating to window 540196 and searching,
   * because it ensures we select the CORRECT receipt candidate for this specific PO.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Initial delay between retries in ms (default: 2000)
   */
  static async openRelatedReceiptCandidate({ maxRetries = 5, retryDelay = 2000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.PO_TO_RECEIPT_CANDIDATES,
      stepName: 'PurchaseOrderPage - Open related receipt candidate (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Receipt candidates opens as list view
    });
  }

  /**
   * Open the related Invoice Candidate for the current purchase order using Alt+6.
   *
   * IMPORTANT: Use this method instead of navigating to window 540983 and searching,
   * because it ensures we select the CORRECT invoice candidate for this specific PO.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 2000)
   */
  static async openRelatedInvoiceCandidate({ maxRetries = 5, retryDelay = 2000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.PO_TO_INVOICE_CANDIDATES,
      stepName: 'PurchaseOrderPage - Open related invoice candidate (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Invoice candidates opens as list view
    });
  }

  /**
   * Reactivate the purchase order using the document action.
   * Follows the same pattern as complete() but clicks status-RE instead of status-CO.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxAttempts - Maximum retry attempts (default: 3)
   */
  static async reactivate({ maxAttempts = 3 } = {}) {
    return await test.step('PurchaseOrderPage - Reactivate order', async () => {
      const page = getPage();

      for (let attempt = 1; attempt <= maxAttempts; attempt++) {
        const reOption = page.getByTestId('status-RE');

        // Check if dropdown is already open from a previous failed attempt
        const isAlreadyOpen = await reOption.isVisible().catch(() => false);
        if (!isAlreadyOpen) {
          await page.getByTestId('status-button').click();
          await page.waitForTimeout(500);
        }

        // Language-independent: check if Reactivate action is available
        const reVisible = await reOption
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (!reVisible) {
          console.log('Reactivate action (status-RE) not available');
          await page.keyboard.press('Escape');
          throw new Error('Reactivate action not available — is PO_AllowReactivationIfReceiptsCreated=Y set?');
        }

        console.log(`Reactivate attempt ${attempt}/${maxAttempts}`);

        await reOption.click();
        await page.waitForTimeout(3000);

        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        // Verification: open dropdown and check if CO is now available (document back to Drafted)
        await page.getByTestId('status-button').click();
        await page.waitForTimeout(500);
        const hasCO = await page.getByTestId('status-CO').isVisible().catch(() => false);
        await page.keyboard.press('Escape');

        if (hasCO) {
          console.log(`Order reactivated successfully on attempt ${attempt}`);
          return;
        }

        console.log(`Order still does not show CO action after attempt ${attempt}, retrying...`);
        await page.waitForTimeout(2000);
      }

      throw new Error(`Failed to reactivate order after ${maxAttempts} attempts`);
    });
  }

  /**
   * Open the related Material Receipt (M_InOut) for the current purchase order using Alt+6.
   *
   * Use this method after creating a material receipt via Receipt Candidates workflow.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Initial delay between retries in ms (default: 2000)
   */
  static async openRelatedMaterialReceipt({ maxRetries = 5, retryDelay = 2000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.PO_TO_MATERIAL_RECEIPT,
      stepName: 'PurchaseOrderPage - Open related Material Receipt (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: true, // Auto-navigate to detail view if landing on list
    });
  }

  /**
   * Open the related Vendor Invoice (C_Invoice) for the current purchase order using Alt+6.
   *
   * Use this method after creating a vendor invoice via Invoice Candidates workflow.
   *
   * IMPORTANT: Vendor invoice creation is asynchronous and can take 40-60 seconds.
   * This method uses polling with page refresh to wait for the invoice to appear.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 20 for ~60s total wait)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 3000)
   */
  static async openRelatedVendorInvoice({ maxRetries = 20, retryDelay = 3000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.PO_TO_VENDOR_INVOICE,
      stepName: 'PurchaseOrderPage - Open related Vendor Invoice (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Vendor invoice opens as list view
      refreshOnRetry: true, // Refresh page before each retry (async invoice generation)
      sseDebug: {}, // Enable SSE debugging for invoice timing issues
    });
  }
}
