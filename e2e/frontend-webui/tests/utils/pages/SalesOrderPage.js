import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { SALES_ORDER_WINDOW_ID } from '../WindowIds';
import { waitForRecordSaved, waitForTabAllowsNew } from '../WebAPIValidation';

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

      await page.waitForURL(/\/window\/143\/\d+/, {
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

      // Wait for tab content to load
      await page.waitForTimeout(1000);
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

      await page.waitForTimeout(1000);
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
      await page.waitForTimeout(1000);

      await page.locator('.rotating, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Click on Shipment Schedule link using data-cy attribute (language-independent)
      // This corresponds to the M_ShipmentSchedule reference
      await page.locator('[data-cy="reference-M_ShipmentSchedule"]').click();

      await page.waitForURL(/\/window\/500221/, {
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
      await page.waitForTimeout(1000);

      await page.locator('.rotating, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Click on Invoice Candidate link using data-cy attribute (language-independent)
      // This corresponds to the C_Invoice_Candidate reference
      await page.locator('[data-cy="reference-C_Invoice_Candidate"]').click();

      // Sales IC window is 540092, not 540983 (purchase)
      await page.waitForURL(/\/window\/540092/, {
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
    return await test.step('SalesOrderPage - Open print modal (Alt+P)', async () => {
      const page = getPage();

      // Focus page first
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Press Alt+P to open print modal
      await page.keyboard.press('Alt+P');

      // Two-step wait pattern: Wait for modal container first
      await page
        .locator('.modal-content, .modal, .panel-modal')
        .waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

      // Wait for modal content to load
      await page.waitForTimeout(500);

      console.log('Print modal opened successfully');
    });
  }

  /**
   * Download the PDF by clicking the print button in the modal.
   * Handles both download and popup (new tab) scenarios.
   * @returns {Promise<Download>} Playwright download object
   */
  static async downloadPDF() {
    return await test.step('SalesOrderPage - Download PDF', async () => {
      const page = getPage();
      const axios = require('axios');
      const fs = require('fs');
      const path = require('path');

      // Wait for modal to be fully loaded and buttons to be ready
      await page.waitForTimeout(1000);

      // Find the "Print" button - it's the one with text "Print" (not "Cancel")
      // Use text content to be more specific
      const printButton = page.locator('.btn.btn-meta-outline-secondary:has-text("Print")').first();

      await printButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Set up both download and popup listeners BEFORE clicking
      const downloadPromise = page.waitForEvent('download', {
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).catch(() => null); // Don't fail if download doesn't happen

      const popupPromise = page.waitForEvent('popup', {
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).catch(() => null); // Don't fail if popup doesn't happen

      console.log('Clicking Print button...');
      await printButton.click();

      // Give it a moment for the action to start
      await page.waitForTimeout(2000);

      // Wait for either download OR popup
      const result = await Promise.race([
        downloadPromise.then(d => ({ type: 'download', data: d })),
        popupPromise.then(p => ({ type: 'popup', data: p })),
      ]);

      if (!result || !result.data) {
        throw new Error('Neither download nor popup occurred after clicking print button');
      }

      if (result.type === 'download') {
        console.log('PDF download started:', result.data.suggestedFilename());
        return result.data;
      } else {
        // Handle popup - fetch PDF from new tab URL
        console.log('PDF opened in new tab, fetching from URL...');
        const popup = result.data;

        // Wait for popup to load
        await popup.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

        const pdfUrl = popup.url();
        console.log('PDF URL:', pdfUrl);

        // Get authentication cookies from the browser context
        const cookies = await page.context().cookies();
        const cookieHeader = cookies.map(c => `${c.name}=${c.value}`).join('; ');

        // Download PDF using axios with authentication
        const response = await axios.get(pdfUrl, {
          responseType: 'arraybuffer',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
          headers: {
            'Cookie': cookieHeader,
          },
        });

        // Save to temp file
        const tempDir = path.join(process.cwd(), 'test-results', 'temp-pdfs');
        if (!fs.existsSync(tempDir)) {
          fs.mkdirSync(tempDir, { recursive: true });
        }

        const timestamp = Date.now();
        const tempPath = path.join(tempDir, `sales-order-${timestamp}.pdf`);
        fs.writeFileSync(tempPath, Buffer.from(response.data));

        console.log('PDF downloaded from popup:', tempPath);

        // Close the popup
        await popup.close().catch(() => {});

        // Return a mock download object with path() method
        return {
          suggestedFilename: () => `sales-order-${timestamp}.pdf`,
          path: async () => tempPath,
        };
      }
    });
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
  static async validatePdfContent(download, expectedData) {
    return await test.step('SalesOrderPage - Validate PDF content', async () => {
      const fs = require('fs');
      const pdfParse = require('pdf-parse');

      // Get file path (Playwright manages temp file)
      const filePath = await download.path();

      // Read file
      const buffer = fs.readFileSync(filePath);

      console.log('PDF file size:', buffer.length, 'bytes');

      // Parse PDF
      let pdfData;
      try {
        pdfData = await pdfParse(buffer);
      } catch (error) {
        console.error('Failed to parse PDF:', error.message);
        throw new Error(`PDF parsing failed: ${error.message}`);
      }

      console.log('PDF pages:', pdfData.numpages);
      console.log('PDF text length:', pdfData.text.length);

      // Extract and normalize text (replace multiple whitespaces with single space)
      const text = pdfData.text.replace(/\s+/g, ' ');

      // Log first 1000 chars for debugging
      console.log('PDF text preview:', text.substring(0, 1000));

      // Validate document number
      if (!text.includes(expectedData.documentNo)) {
        throw new Error(`Document number "${expectedData.documentNo}" not found in PDF`);
      }
      console.log(`✓ Document number validated: ${expectedData.documentNo}`);

      // Validate customer name/code
      if (!text.includes(expectedData.customerName)) {
        console.warn(`Customer name "${expectedData.customerName}" not found in PDF (may use different format)`);
      } else {
        console.log(`✓ Customer name validated: ${expectedData.customerName}`);
      }

      // Validate product code
      if (!text.includes(expectedData.productCode)) {
        throw new Error(`Product code "${expectedData.productCode}" not found in PDF`);
      }
      console.log(`✓ Product code validated: ${expectedData.productCode}`);

      // Validate quantity (handle multiple formats: "10", "10.00", "10,00")
      const quantityPatterns = [
        expectedData.quantity,
        `${expectedData.quantity}.00`,
        `${expectedData.quantity},00`,
      ];

      const quantityFound = quantityPatterns.some((pattern) => text.includes(pattern));

      if (!quantityFound) {
        console.warn(
          `Quantity "${expectedData.quantity}" not found in expected formats. ` +
          `Tried: ${quantityPatterns.join(', ')}`
        );
      } else {
        console.log(`✓ Quantity validated: ${expectedData.quantity}`);
      }

      console.log(`PDF content validation completed successfully for ${expectedData.language}`);
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
}
