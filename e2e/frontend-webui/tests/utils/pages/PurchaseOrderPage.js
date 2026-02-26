import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, } from '../common';
import { PURCHASE_ORDER_WINDOW_ID } from '../WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from '../WebAPIValidation';

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
  static async addOrderLine({ product, quantity, recordId }) {
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

      // Scroll to batch entry button (may be below the fold in single-section layout)
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      await batchEntryButton.scrollIntoViewIfNeeded();
      await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Click batch entry toggle
      await batchEntryButton.click();

      // Wait for batch entry form container to appear
      // The form has class 'quick-input-container' from TableQuickInput.js
      await page.locator('.quick-input-container').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the product input field to be visible and ready
      const productInput = page.locator('#lookup_M_Product_ID input.input-field');
      await productInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Click to focus the product input field
      await productInput.click();
      await page.waitForTimeout(300);

      await productInput.fill(product);

      // Wait for debounce/input processing
      await page.waitForTimeout(500);

      // Wait for any search spinner to disappear (after typing)
      await page.locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore if no spinner exists
      });

      // Wait for dropdown to populate and click the matching option
      // Note: Dropdown options use data-test-id attributes, not CSS classes
      // The dropdown displays names doubled (e.g., "Product123_Product123")
      // so we use partial match instead of exact match
      await page.waitForTimeout(300);

      // Click the option by text - finds element containing the product name
      // Use more specific selector to ensure we're clicking dropdown option
      await page.locator('.input-dropdown-list-option').getByText(product).first().click();

      // Wait for product to be selected
      await page.waitForTimeout(500);

      // Fill quantity using spinbutton role (language-independent)
      await page.getByRole('spinbutton').fill(quantity.toString());

      // Press Enter to add the line (as instructed by the UI: "Press 'Enter' to add")
      await page.keyboard.press('Enter');

      // Wait for the line to be added
      await page.waitForTimeout(500);

      // Close the batch entry modal
      // Language-independent: Use data-testid from TableFilter.js
      const closeButton = page.getByTestId('batch-entry-toggle');
      await closeButton.click();

      // Wait for the modal to close
      await page.waitForTimeout(500);
    });
  }

  /**
   * Complete the purchase order using the document action.
   * Clicks the action button and selects "Complete" from the dropdown.
   */
  static async complete() {
    return await test.step('PurchaseOrderPage - Complete order', async () => {
      const page = getPage();

      // Click the document status button (e.g., "Drafted") in the upper right header
      // This opens the document action dropdown with options like Complete, Close, Void, etc.
      // Language-independent: Use data-testid
      await page.getByTestId('status-button').click();

      // Wait for the action dropdown to appear
      await page.waitForTimeout(500);

      // Click "Complete" action in the dropdown
      // Language-independent: Use data-testid (CO = Complete document action key)
      await page.getByTestId('status-CO').click();

      // Wait for the completion process (can take a few seconds)
      await page.waitForTimeout(3000);

      // Wait for any processing indicators to disappear
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if indicator doesn't exist
        });
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
   * @param {number} waitTime - Time to wait for receipt candidates to be created (default: 5000ms)
   */
  static async openRelatedReceiptCandidate(waitTime = 5000) {
    return await test.step('PurchaseOrderPage - Open related receipt candidate (Alt+6)', async () => {
      const page = getPage();

      // Wait for receipt candidates to be created asynchronously by backend
      // Receipt candidates are created by the app server after PO completion
      await page.waitForTimeout(waitTime);

      // Click on the page body to ensure it has focus
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Press Alt+6 to open related documents panel
      await page.keyboard.press('Alt+6');

      // Wait for the related documents panel to appear
      // The panel typically contains links like "Material Receipt Candidates (#1)"
      await page.waitForTimeout(1000);

      // Wait for any loading spinners in the related documents panel to disappear
      await page
        .locator('.rotating, .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Click on "Material Receipt Candidates" link (or "Wareneingangsdisposition" in German)
      // This navigates to window 540196 with the correct candidate already selected
      // Language-independent: Use regex to match English or German text with count indicator
      // English: "Material Receipt Candidates (#1)"
      // German: "Wareneingangsdisposition (#1)"
      await page.getByText(/(?:Material Receipt Candidates|Wareneingangsdisposition).*\(#\d+\)/).first().click();

      // Wait for navigation to Receipt Candidates window with pre-selected candidate
      // URL pattern: /window/540196?page=1&refDocumentId={poId}&refType=181&...
      await page.waitForURL(/\/window\/540196/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the window to fully load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
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

      // Give the window a moment to be ready
      await page.waitForTimeout(500);
    });
  }

  /**
   * Open the related Invoice Candidate for the current purchase order using Alt+6.
   *
   * This method:
   * 1. Waits for invoice candidates to be created (asynchronously by backend)
   * 2. Opens the related documents panel (Alt+6)
   * 3. Clicks on the "Invoice Candidates" link
   * 4. Navigates to the Invoice Candidate window with the candidate already selected
   *
   * IMPORTANT: Use this method instead of navigating to window 540983 and searching,
   * because it ensures we select the CORRECT invoice candidate for this specific PO.
   *
   * NOTE: Invoice candidates are created when the PO is completed (one IC per PO line).
   * Additional ICs may be created if the material receipt includes items not in the PO
   * (e.g., a pallet that's not part of the PO).
   *
   * @param {number} waitTime - Time to wait for invoice candidates to be created (default: 5000ms)
   */
  static async openRelatedInvoiceCandidate(waitTime = 5000) {
    return await test.step('PurchaseOrderPage - Open related invoice candidate (Alt+6)', async () => {
      const page = getPage();

      // Wait for invoice candidates to be created asynchronously by backend
      // ICs are created when PO is completed (one IC per PO line)
      // Additional ICs may be created after material receipt for non-PO items
      await page.waitForTimeout(waitTime);

      // Click on the page body to ensure it has focus
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Press Alt+6 to open related documents panel
      await page.keyboard.press('Alt+6');

      // Wait for the related documents panel to appear
      // The panel typically contains links like "Invoice Candidates (#1)"
      await page.waitForTimeout(1000);

      // Wait for any loading spinners in the related documents panel to disappear
      await page
        .locator('.rotating, .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Click on "Invoice Candidates" link (or "Rechnungsdisposition" in German)
      // This navigates to window 540983 with the correct candidate already selected
      // Language-independent: Use regex to match English or German text with count indicator
      // English: "Invoice Candidates (#1)"
      // German: "Rechnungsdisposition (#1)"
      await page.getByText(/(?:Invoice Candidates|Rechnungsdisposition).*\(#\d+\)/).first().click();

      // Wait for navigation to Invoice Candidates window with pre-selected candidate
      // URL pattern: /window/540983?page=1&refDocumentId={poId}&refType=181&...
      await page.waitForURL(/\/window\/540983/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the window to fully load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
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

      // Give the window a moment to be ready
      await page.waitForTimeout(500);
    });
  }
}
