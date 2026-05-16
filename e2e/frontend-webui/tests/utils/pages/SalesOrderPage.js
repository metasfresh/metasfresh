import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { SALES_ORDER_WINDOW_ID } from '../WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from '../WebAPIValidation';
import { PdfDownloader } from '../PdfDownloader';
import { openRelatedDocument, REFERENCE_DATA_CY } from '../DocumentReferences';

/**
 * Page object for Sales Order window (ID: 143).
 * Handles creating and completing sales orders in metasfresh.
 */
export class SalesOrderPage {
  /**
   * Navigate to Sales Order window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('SalesOrderPage - Navigate to Sales Order window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}`);

      // Wait for document list to load
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });

      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.locator('.rotating, .panel-spaced-lg').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(500);
    });
  }

  /**
   * Create a new sales order using Alt+N keyboard shortcut.
   */
  static async clickNew() {
    return await test.step('SalesOrderPage - Create new order (Alt+N)', async () => {
      const page = getPage();

      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+N');

      // Use flexible window ID pattern - custom projects may override window 143
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

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
   * Select a customer using the lookup widget.
   * This method waits for the record to be saved after customer selection.
   * @param {string} partnerName - Name of the customer to search for
   * @returns {Promise<string>} Record ID
   */
  static async selectCustomer(partnerName) {
    return await test.step(`SalesOrderPage - Select customer: ${partnerName}`, async () => {
      const page = getPage();

      const bpartnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
      await bpartnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await bpartnerInput.click();

      // Wait for initial loading spinner to disappear (customer list being loaded)
      await page
        .locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if no spinner exists
        });

      // Small delay to ensure dropdown is ready
      await page.waitForTimeout(300);

      // Fill the customer name
      await bpartnerInput.fill(partnerName);

      // Wait for debounce/input processing (autocomplete might have debounce)
      await page.waitForTimeout(500);

      // Wait for any search spinner to disappear (after typing, might trigger another search)
      await page
        .locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if no spinner exists
        });

      // Wait for dropdown to populate
      await page.waitForTimeout(300);

      // Click the option by text - finds element containing the customer name
      // This avoids clicking on "Search for more..." or other non-record options
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
      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, {
        maxRetries: 20,
        retryDelayMs: 1000,
      });

      console.log(`Sales Order ${recordId} saved successfully`);

      return recordId;
    });
  }

  /**
   * Navigate to the Order Line tab.
   */
  static async goToOrderLineTab() {
    return await test.step('SalesOrderPage - Go to Order Line tab', async () => {
      const page = getPage();

      // Check if batch entry button is already visible
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      const isVisible = await batchEntryButton.isVisible().catch(() => false);

      if (isVisible) {
        console.log('Batch entry button already visible - skipping tab switch');
        return;
      }

      // Click on SOLine tab (tab for C_OrderLine)
      // Language-independent: Use AD tab ID selector
      // Tab ID from window layout: AD_Tab-187 (internalName: C_OrderLine)
      await page.locator('#AD_Tab-187').click();

      // Wait for network to settle after tab switch
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Wait for batch entry button to be visible (proves tab is ready)
      await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * Add an order line using batch entry.
   * IMPORTANT: Parent record must be saved before calling this method.
   * Use waitForTabAllowsNew() or call selectCustomer() first which waits for save.
   *
   * @param {Object} params - Order line parameters
   * @param {string} params.product - Product code or name
   * @param {string|number} params.quantity - Quantity to order
   * @param {string} params.recordId - Optional record ID (will extract from URL if not provided)
   */
  static async addOrderLine({ product, quantity, recordId, maxAttempts = 3 }) {
    return await test.step(`SalesOrderPage - Add order line: ${product} x ${quantity}`, async () => {
      const page = getPage();

      // Get record ID if not provided
      const effectiveRecordId = recordId || this.getRecordId();

      // Wait for tab to allow creating new records
      // Tab ID for Sales Order Lines: AD_Tab-187
      await waitForTabAllowsNew(SALES_ORDER_WINDOW_ID, effectiveRecordId, 'AD_Tab-187', {
        maxRetries: 15,
        retryDelayMs: 1000,
      });

      console.log(`Sales Order Lines tab ready for record ${effectiveRecordId}`);

      for (let attempt = 1; attempt <= maxAttempts; attempt++) {
        console.log(`addOrderLine attempt ${attempt}/${maxAttempts}`);

        // Scroll to batch entry button (may be below the fold in single-section layout)
        const batchEntryButton = page.getByTestId('batch-entry-toggle');
        await batchEntryButton.scrollIntoViewIfNeeded();
        await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        // Click batch entry toggle
        await batchEntryButton.click();
        await page.waitForTimeout(500);

        // Wait for batch entry form
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

        // Wait for initial loading spinner to disappear (product list being loaded)
        await page
          .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        await page.waitForTimeout(300);

        // Fill the product code/name
        await productInput.fill(product);
        await page.waitForTimeout(1000);

        // Wait for any search spinner to disappear
        await page
          .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        await page.waitForTimeout(500);

        // Click the option by text - check it's visible first
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

        // Press Enter to add the line (as instructed by the UI: "Press 'Enter' to add")
        await page.keyboard.press('Enter');
        await page.waitForTimeout(2000);

        // Wait for spinners
        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        // Close the batch entry modal (only if still open)
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
   * Complete the sales order.
   * Retries up to maxAttempts times, using language-independent data-testid selectors
   * to detect whether the Complete (CO) action is still available.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxAttempts - Maximum retry attempts (default: 3)
   */
  static async complete({ maxAttempts = 3 } = {}) {
    return await test.step('SalesOrderPage - Complete order', async () => {
      const page = getPage();

      for (let attempt = 1; attempt <= maxAttempts; attempt++) {
        const coOption = page.getByTestId('status-CO');

        // Check if dropdown is already open from a previous failed attempt
        const isAlreadyOpen = await coOption.isVisible().catch(() => false);
        if (!isAlreadyOpen) {
          // Click the document status button to open the action dropdown
          await page.getByTestId('status-button').click();
          await page.waitForTimeout(500);
        }

        // Language-independent: check if Complete action is available
        const coVisible = await coOption
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (!coVisible) {
          // No CO action means document is already completed (or non-completable)
          console.log(`Complete action (status-CO) not available — document likely already completed`);
          await page.keyboard.press('Escape');
          return;
        }

        console.log(`Complete attempt ${attempt}/${maxAttempts}`);

        // Click "Complete" action (CO = Complete document action key)
        await coOption.click();

        // Wait for the completion process
        await page.waitForTimeout(3000);

        // Wait for any processing indicators to disappear
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
   * Get the document number of the current sales order.
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('SalesOrderPage - Get document number', async () => {
      const page = getPage();

      const docNoField = page.locator('.form-field-DocumentNo input, input[name="DocumentNo"]').first();
      await docNoField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const docNo = await docNoField.inputValue();
      return docNo;
    });
  }

  /**
   * Open the related Shipment Candidate (Shipment Schedule) using Alt+6.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 2000)
   */
  static async openRelatedShipmentCandidate({ maxRetries = 5, retryDelay = 2000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.SO_TO_SHIPMENT_SCHEDULE,
      stepName: 'SalesOrderPage - Open related shipment candidate (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Opens as list view
    });
  }

  /**
   * Open the related Shipment using Alt+6.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 2000)
   */
  static async openRelatedShipment({ maxRetries = 5, retryDelay = 2000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.SO_TO_SHIPMENT,
      stepName: 'SalesOrderPage - Open related shipment (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Stay on list view - test will call ShipmentPage.openDetailView() explicitly
    });
  }

  /**
   * Open the related Invoice Candidate using Alt+6.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 2000)
   */
  static async openRelatedInvoiceCandidate({ maxRetries = 5, retryDelay = 2000, refreshOnRetry = false } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.SO_TO_INVOICE_CANDIDATES,
      stepName: 'SalesOrderPage - Open related invoice candidate (Alt+6)',
      maxRetries,
      retryDelay,
      refreshOnRetry,
      navigateToDetail: false, // Opens as list view
    });
  }

  /**
   * Open the print modal using Alt+P keyboard shortcut.
   * This opens the PDF generation modal for the sales order.
   */
  static async openPrintModal() {
    return await PdfDownloader.openPrintModal('SalesOrderPage');
  }

  /**
   * Download the PDF by clicking the print button in the modal.
   * Handles both download and popup (new tab) scenarios.
   * @returns {Promise<Download>} Playwright download object
   */
  static async downloadPDF() {
    return await PdfDownloader.downloadPdf('sales-order', 'SalesOrderPage');
  }

  /**
   * Validate the content of the downloaded PDF.
   * @param {Download} download - Playwright download object
   * @param {Object} expectedData - Expected data to validate
   * @param {string} expectedData.documentNo - Sales order document number
   * @param {string} expectedData.customerName - Customer name or code
   * @param {string} expectedData.productCode - Product code
   * @param {string} expectedData.quantity - Quantity (e.g., '10')
   * @param {string} expectedData.language - Language code (en_US, de_DE)
   */
  /**
   * Validate PDF content and layout
   * @param {Download} download - Playwright Download object
   * @param {Object} expectedData - Expected data to validate
   * @param {string} expectedData.documentNo - Document number
   * @param {string} expectedData.customerName - Customer name/code
   * @param {string} expectedData.productCode - Product code
   * @param {string} expectedData.quantity - Quantity
   * @param {string} expectedData.language - Language (e.g., 'en_US', 'de_DE')
   * @returns {Promise<void>}
   */
  static async validatePdfContent(download, expectedData) {
    return await test.step('SalesOrderPage - Validate PDF content', async () => {
      const { PdfValidator } = require('../PdfValidator');

      // Use unified PDF validator (includes text + layout validation)
      await PdfValidator.validate(download, {
        documentNo: expectedData.documentNo,
        customerName: expectedData.customerName,
        productCode: expectedData.productCode,
        quantity: expectedData.quantity,
        language: expectedData.language,
        checkOverlaps: true,         // Enabled - detects true 2D overlaps
        checkMargins: false,         // Disabled - not needed yet
      });
    });
  }

  /**
   * Close the print modal by pressing Escape.
   */
  static async closePrintModal() {
    return await test.step('SalesOrderPage - Close print modal', async () => {
      const page = getPage();

      // Press Escape to close modal
      await page.keyboard.press('Escape');

      // Wait for modal to disappear
      await page
        .locator('.modal-content, .modal, .panel-modal')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if already closed
          console.log('Modal already closed or not found');
        });

      console.log('Print modal closed');
    });
  }

  /**
   * Open the related Invoice (Customer Invoice) using Alt+6.
   * This navigates to the invoice created from the invoice candidates.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 5)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 2000)
   */
  static async openRelatedInvoice({ maxRetries = 5, retryDelay = 2000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.SO_TO_CUSTOMER_INVOICE,
      stepName: 'SalesOrderPage - Open related invoice (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Stay on list view - test will call InvoicePage.openDetailView() explicitly
    });
  }
}
