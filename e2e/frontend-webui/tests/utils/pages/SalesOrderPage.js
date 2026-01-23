import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { SALES_ORDER_WINDOW_ID } from '../WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from '../WebAPIValidation';
import { PdfDownloader } from '../PdfDownloader';

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
  static async addOrderLine({ product, quantity, recordId }) {
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

      // Scroll to batch entry button (may be below the fold in single-section layout)
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      await batchEntryButton.scrollIntoViewIfNeeded();
      await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Click batch entry toggle
      await batchEntryButton.click();

      // Wait for batch entry form
      await page.locator('.quick-input-container').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      const productInput = page.locator('#lookup_M_Product_ID input.input-field');
      await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await productInput.click();

      // Wait for initial loading spinner to disappear (product list being loaded)
      await page
        .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if no spinner exists
        });

      // Small delay to ensure dropdown is ready
      await page.waitForTimeout(300);

      // Fill the product code/name
      await productInput.fill(product);

      // Wait for debounce/input processing
      await page.waitForTimeout(500);

      // Wait for any search spinner to disappear
      await page
        .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if no spinner exists
        });

      // Wait for dropdown to populate
      await page.waitForTimeout(300);

      // Click the option by text - finds element containing the product code
      // This avoids clicking on "Search for more..." or other non-record options
      await page.locator('.input-dropdown-list-option').getByText(product).first().click();

      // Wait for product to be selected and form to update
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
   * Complete the sales order.
   */
  static async complete() {
    return await test.step('SalesOrderPage - Complete order', async () => {
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
   * Open the related Shipment Candidate using Alt+6.
   * @param {number} waitTime - Time to wait for candidates to be created (default: 5000ms)
   */
  static async openRelatedShipmentCandidate(waitTime = 5000) {
    return await test.step('SalesOrderPage - Open related shipment candidate (Alt+6)', async () => {
      const page = getPage();

      await page.waitForTimeout(waitTime);

      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+6');

      // Wait for Alt+6 side panel to open
      // The panel has class 'order-list-panel-open' when visible
      await page.locator('.order-list-panel-open').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.locator('.rotating, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Click on Shipment Schedule link using data-cy attribute (language-independent)
      // Handle both InternalName format and fallback AD_RelationType_ID format
      // InternalName: M_ShipmentSchedule, Fallback: AD_RelationType_ID-540170 (C_Order_to_M_ShipmentSchedule)
      const link = page.locator(
        '[data-cy="reference-M_ShipmentSchedule"], [data-cy="reference-AD_RelationType_ID-540170"]'
      );
      await link.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await link.click();

      // Use flexible window ID pattern - custom projects may override windows
      await page.waitForURL(/\/window\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
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
   * Open the related Shipment using Alt+6.
   * @param {number} waitTime - Time to wait for shipment to be created (default: 5000ms)
   */
  static async openRelatedShipment(waitTime = 5000) {
    return await test.step('SalesOrderPage - Open related shipment (Alt+6)', async () => {
      const page = getPage();

      await page.waitForTimeout(waitTime);

      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+6');

      // Wait for Alt+6 side panel to open
      // The panel has class 'order-list-panel-open' when visible
      await page.locator('.order-list-panel-open').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.locator('.rotating, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Click on Shipment link using data-cy attribute (language-independent)
      // Handle both InternalName format and fallback AD_RelationType_ID format
      // InternalName: C_Order_to_M_InOut_SO, Fallback: AD_RelationType_ID-540159
      const link = page.locator(
        '[data-cy="reference-C_Order_to_M_InOut_SO"], [data-cy="reference-AD_RelationType_ID-540159"]'
      );
      await link.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await link.click();

      // Use flexible window ID pattern - custom projects may override window 169 (M_InOut)
      await page.waitForURL(/\/window\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
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
   * Open the related Invoice Candidate using Alt+6.
   * @param {number} waitTime - Time to wait for candidates to be created (default: 5000ms)
   */
  static async openRelatedInvoiceCandidate(waitTime = 5000) {
    return await test.step('SalesOrderPage - Open related invoice candidate (Alt+6)', async () => {
      const page = getPage();

      await page.waitForTimeout(waitTime);

      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+6');

      // Wait for Alt+6 side panel to open
      // The panel has class 'order-list-panel-open' when visible
      await page.locator('.order-list-panel-open').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.locator('.rotating, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Click on Invoice Candidate link using data-cy attribute (language-independent)
      // Handle both InternalName format and fallback AD_RelationType_ID format
      // InternalName: C_Invoice_Candidate_Sales, Fallback: AD_RelationType_ID-540119
      const link = page.locator(
        '[data-cy="reference-C_Invoice_Candidate_Sales"], [data-cy="reference-AD_RelationType_ID-540119"]'
      );
      await link.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await link.click();

      // Use flexible window ID pattern - custom projects may override windows
      await page.waitForURL(/\/window\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
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
   * Open the related Invoice using Alt+6.
   * This navigates to the invoice created from the invoice candidates.
   * @param {number} waitTime - Time to wait for invoice to be created (default: 5000ms)
   */
  static async openRelatedInvoice(waitTime = 5000) {
    return await test.step('SalesOrderPage - Open related invoice (Alt+6)', async () => {
      const page = getPage();

      await page.waitForTimeout(waitTime);

      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+6');

      // Wait for Alt+6 side panel to open
      // The panel has class 'order-list-panel-open' when visible
      await page.locator('.order-list-panel-open').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.locator('.rotating, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Click on Invoice link using data-cy attribute (language-independent)
      // Handle both InternalName format and fallback AD_RelationType_ID format
      // InternalName: C_Order_to_C_Invoice_SO, Fallback: AD_RelationType_ID-540160
      const invoiceLink = page.locator(
        '[data-cy="reference-C_Order_to_C_Invoice_SO"], [data-cy="reference-AD_RelationType_ID-540160"]'
      ).first();

      await invoiceLink.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await invoiceLink.click();

      // Use flexible window ID pattern - custom projects may override window 167 (C_Invoice)
      await page.waitForURL(/\/window\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
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
}
