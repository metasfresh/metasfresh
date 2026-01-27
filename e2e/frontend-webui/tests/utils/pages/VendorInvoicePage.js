import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { VENDOR_INVOICE_WINDOW_ID } from '../WindowIds';
import { PdfDownloader } from '../PdfDownloader';

/**
 * Page object for Vendor Invoice window (ID: 183).
 * Handles vendor invoice document operations in metasfresh.
 *
 * This is the purchase-side equivalent of InvoicePage (sales invoice window 167).
 */
export class VendorInvoicePage {
  /**
   * Navigate to Vendor Invoice window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('VendorInvoicePage - Navigate to Vendor Invoice window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${VENDOR_INVOICE_WINDOW_ID}`);

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
   * Verify vendor invoice window is visible and loaded.
   *
   * Assumes navigation via PurchaseOrderPage.openRelatedVendorInvoice().
   * Waits for the invoice list view to be fully loaded.
   */
  static async expectVisible() {
    return await test.step('VendorInvoicePage - Verify window is visible', async () => {
      const page = getPage();

      // Vendor invoice opens as a list view (grid), not a detail window
      // Wait for document list container
      await page.locator('.document-list-wrapper, .document-list').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for spinners to disappear
      await page
        .locator('.rotating, .spinner')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no spinners found
        });

      await page.waitForTimeout(500);
    });
  }

  /**
   * Select and open the first invoice in the grid.
   * @deprecated Use selectInvoiceByVendor() instead for reliable selection
   */
  static async selectFirstInvoice() {
    return await test.step('VendorInvoicePage - Select first invoice in grid', async () => {
      const page = getPage();

      // Wait for grid table to be loaded
      await page.locator('.document-list-table, table').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for at least one table row
      await page.locator('tbody tr').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Single click to select the first row
      const firstRow = page.locator('tbody tr').first();
      await firstRow.click();
      await page.waitForTimeout(500);

      // Double click to open detail view
      await firstRow.dblclick();

      // Wait for navigation to detail view (flexible pattern for window overrides)
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for detail view to load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Wait for spinners to disappear
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(500);

      console.log('Opened first vendor invoice detail view');
    });
  }

  /**
   * Filter the Vendor Invoice grid by vendor code and select the matching invoice.
   * This is more reliable than selectFirstInvoice() because vendor codes are unique per test.
   *
   * @param {string} vendorCode - The unique vendor code to filter by
   */
  static async selectInvoiceByVendor(vendorCode) {
    return await test.step(`VendorInvoicePage - Select invoice for vendor ${vendorCode}`, async () => {
      const page = getPage();

      console.log(`Filtering vendor invoices by vendor: ${vendorCode}`);

      // Wait for grid table to be loaded
      await page.locator('.document-list-table, table').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for at least one table row
      await page.locator('tbody tr').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Use Ctrl+F to open the filter panel
      await page.keyboard.press('Control+f');
      await page.waitForTimeout(1000);

      // Wait for filter panel to appear
      await page.locator('.filter-wrapper, .filters-wrapper, .filter-panel').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        console.log('Filter panel locator not found, trying alternative...');
      });

      // Find the BPartner filter input field
      // In metasfresh, filters use field names like C_BPartner_ID
      const bpartnerFilter = page.locator('[data-cy="filter-C_BPartner_ID"] input, #lookup_C_BPartner_ID input, input[placeholder*="Business Partner"], input[placeholder*="Geschäftspartner"]').first();
      const filterExists = await bpartnerFilter.count() > 0;

      if (filterExists) {
        await bpartnerFilter.click();
        await page.waitForTimeout(300);
        await bpartnerFilter.fill(vendorCode);
        await page.waitForTimeout(500);

        // Press Enter to apply filter
        await page.keyboard.press('Enter');
        await page.waitForTimeout(2000);

        console.log(`Applied filter for vendor: ${vendorCode}`);
      } else {
        // Try typing in the search box at the top of the grid
        console.log('BPartner filter not found, trying global search...');
        const searchInput = page.locator('.search-input, input[type="search"], .document-list-header input').first();
        if (await searchInput.count() > 0) {
          await searchInput.click();
          await searchInput.fill(vendorCode);
          await page.keyboard.press('Enter');
          await page.waitForTimeout(2000);
        } else {
          console.log('No filter/search input found, proceeding with first row');
        }
      }

      // Wait for grid to refresh with filtered results
      await page.waitForTimeout(1000);

      // Now select and open the first row (should be the filtered result)
      const firstRow = page.locator('tbody tr').first();
      await firstRow.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await firstRow.click();
      await page.waitForTimeout(500);

      // Double click to open detail view
      await firstRow.dblclick();

      // Wait for navigation to detail view (flexible pattern for window overrides)
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for detail view to load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      // Wait for spinners to disappear
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(500);

      console.log(`Opened vendor invoice detail view for vendor: ${vendorCode}`);
    });
  }

  /**
   * Get the document number of the current vendor invoice from the grid.
   *
   * Since vendor invoice opens as a filtered list (not a detail window),
   * we read the document number directly from the grid row using the language-independent
   * data-cy attribute on the cell.
   *
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('VendorInvoicePage - Get document number from grid', async () => {
      const page = getPage();

      // Wait for grid table to be loaded
      await page.locator('.document-list-table').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for at least one table row (data row, not header)
      await page.locator('tbody tr').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Use language-independent data-cy attribute to find the DocumentNo cell
      const docNoCell = page.locator('[data-cy="cell-DocumentNo"]').first();
      await docNoCell.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      const documentNo = await docNoCell.textContent();

      console.log(`Vendor Invoice DocumentNo from grid (via data-cy): ${documentNo}`);

      return documentNo.trim();
    });
  }

  /**
   * Open the vendor invoice detail view by double-clicking on the first row in the grid.
   * Required before printing from list view.
   */
  static async openDetailView() {
    return await test.step('VendorInvoicePage - Open detail view', async () => {
      const page = getPage();

      // Double-click on first row to open detail view
      const firstRow = page.locator('tbody tr').first();
      await firstRow.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await firstRow.dblclick();

      // Wait for detail view to load
      await page.waitForTimeout(1000);

      // Wait for detail panel to appear
      await page.locator('.panel-modal, .window-wrapper, .header-wrapper').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);
    });
  }

  /**
   * Open the print modal using Alt+P keyboard shortcut.
   * This opens the PDF generation modal for the vendor invoice.
   */
  static async openPrintModal() {
    return await PdfDownloader.openPrintModal('VendorInvoicePage');
  }

  /**
   * Download the PDF by clicking the print button in the modal.
   * Handles both download and popup (new tab) scenarios.
   * @returns {Promise<Download>} Playwright download object
   */
  static async downloadPDF() {
    return await PdfDownloader.downloadPdf('vendor-invoice', 'VendorInvoicePage');
  }

  /**
   * Validate PDF content and layout
   * @param {Download} download - Playwright Download object
   * @param {Object} expectedData - Expected data to validate
   * @param {string} expectedData.documentNo - Document number
   * @param {string} expectedData.vendorName - Vendor name/code (optional)
   * @param {string} expectedData.productCode - Product code
   * @param {string} expectedData.quantity - Quantity
   * @param {string} expectedData.language - Language (e.g., 'en_US', 'de_DE')
   * @returns {Promise<void>}
   */
  static async validatePdfContent(download, expectedData) {
    return await test.step('VendorInvoicePage - Validate PDF content', async () => {
      const { PdfValidator } = require('../PdfValidator');

      // Use unified PDF validator (includes text + layout validation)
      // Note: For vendor invoices, we use vendorName instead of customerName
      await PdfValidator.validate(download, {
        documentNo: expectedData.documentNo,
        customerName: expectedData.vendorName, // Reuse customerName field for vendor
        productCode: expectedData.productCode,
        quantity: expectedData.quantity,
        language: expectedData.language,
        checkOverlaps: true, // Enabled - detects true 2D overlaps
        checkMargins: false, // Disabled - not needed yet
      });
    });
  }

  /**
   * Download and validate vendor invoice PDF in one combined operation.
   * This is a convenience method that combines openPrintModal, downloadPDF, and validatePdfContent.
   *
   * @param {Object} expectedData - Expected data to validate
   * @param {string} expectedData.vendorName - Vendor name/code
   * @param {string} expectedData.productCode - Product code
   * @param {string} expectedData.quantity - Quantity
   * @param {string} expectedData.language - Language (e.g., 'en_US', 'de_DE')
   * @returns {Promise<void>}
   */
  static async downloadAndValidatePdf(expectedData) {
    return await test.step('VendorInvoicePage - Download and validate PDF', async () => {
      const page = getPage();
      let url = page.url();

      // Check if we're on a list view (no document ID in URL)
      // List view: /window/{id}?filters...
      // Detail view: /window/{id}/12345
      const isListView = !url.match(/\/window\/\d+\/\d+/);

      if (isListView) {
        console.log('On list view, opening detail view by double-clicking first row');

        // Wait for table row to be visible
        const firstRow = page.locator('.table-row, [class*="table-row"], tbody tr').first();
        await firstRow.waitFor({ state: 'visible', timeout: 10000 });

        // Double-click to open detail view
        await firstRow.dblclick();

        // Wait for navigation to detail view (flexible pattern for window overrides)
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 15000 });
        await page.waitForLoadState('networkidle', { timeout: 10000 }).catch(() => {});

        // Update URL after navigation
        url = page.url();
        console.log(`Navigated to detail view: ${url}`);
      }

      // Get the document number from the page (not from URL - URL has document ID, not DocumentNo)
      // Language-independent selector: use form-field class with database column name
      const documentNoInput = page.locator('.form-field-DocumentNo input, .form-field-DocumentNo .form-control');

      // Wait for detail view to be loaded
      await page.locator('.rotating, .indicator-pending, .loader')
        .waitFor({ state: 'detached', timeout: 10000 }).catch(() => {});

      let documentNo;
      try {
        await documentNoInput.first().waitFor({ state: 'visible', timeout: 10000 });
        documentNo = await documentNoInput.first().inputValue();
      } catch {
        // Fallback: extract from URL (less reliable but better than nothing)
        const documentId = url.match(/\/window\/\d+\/(\d+)/)?.[1];
        console.log(`Warning: Could not get DocumentNo from page, using URL document ID: ${documentId}`);
        documentNo = documentId;
      }

      console.log(`Downloading PDF for Vendor Invoice document: ${documentNo}`);
      console.log(`Expected vendor: ${expectedData.vendorName}`);
      console.log(`Expected product: ${expectedData.productCode}`);

      // Open print modal
      await VendorInvoicePage.openPrintModal();

      // Download PDF
      const download = await VendorInvoicePage.downloadPDF();

      // Validate PDF content
      await VendorInvoicePage.validatePdfContent(download, {
        documentNo: documentNo,
        vendorName: expectedData.vendorName,
        productCode: expectedData.productCode,
        quantity: expectedData.quantity,
        language: expectedData.language,
      });

      console.log(`Vendor Invoice PDF validated successfully`);
    });
  }

  /**
   * Close the print modal by pressing Escape.
   */
  static async closePrintModal() {
    return await test.step('VendorInvoicePage - Close print modal', async () => {
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
