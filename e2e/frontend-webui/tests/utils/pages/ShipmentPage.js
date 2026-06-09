import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { SHIPMENT_WINDOW_ID } from '../WindowIds';
import { PdfDownloader } from '../PdfDownloader';

/**
 * Page object for Shipment window (ID: 169).
 * Handles shipment document operations in metasfresh.
 */
export class ShipmentPage {
  /**
   * Navigate to Shipment window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('ShipmentPage - Navigate to Shipment window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${SHIPMENT_WINDOW_ID}`);

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
   * Verify shipment window is visible and loaded.
   *
   * Assumes navigation via SalesOrderPage.openRelatedShipment().
   * Waits for the shipment list view to be fully loaded.
   */
  static async expectVisible() {
    return await test.step('ShipmentPage - Verify window is visible', async () => {
      const page = getPage();

      // Shipment opens as a list view (grid), not a detail window
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
   * Get the document number of the current shipment from the grid.
   *
   * Since shipment opens as a filtered list (not a detail window),
   * we read the document number directly from the grid row using the language-independent
   * data-cy attribute on the cell.
   *
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('ShipmentPage - Get document number from grid', async () => {
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

      console.log(`DocumentNo from grid (via data-cy): ${documentNo}`);

      return documentNo.trim();
    });
  }

  /**
   * Open the shipment detail view by double-clicking on the first row in the grid.
   * Required before printing from list view.
   */
  static async openDetailView() {
    return await test.step('ShipmentPage - Open detail view', async () => {
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
   * This opens the PDF generation modal for the shipment.
   */
  static async openPrintModal() {
    return await PdfDownloader.openPrintModal('ShipmentPage');
  }

  /**
   * Download the PDF by clicking the print button in the modal.
   * Handles both download and popup (new tab) scenarios.
   * @returns {Promise<Download>} Playwright download object
   */
  static async downloadPDF() {
    return await PdfDownloader.downloadPdf('shipment', 'ShipmentPage');
  }

  /**
   * Validate PDF content and layout
   * @param {Download} download - Playwright Download object
   * @param {Object} expectedData - Expected data to validate
   * @param {string} expectedData.documentNo - Document number
   * @param {string} expectedData.customerName - Customer name/code (optional)
   * @param {string} expectedData.productCode - Product code
   * @param {string} expectedData.quantity - Quantity
   * @param {string} expectedData.language - Language (e.g., 'en_US', 'de_DE')
   * @returns {Promise<void>}
   */
  static async validatePdfContent(download, expectedData) {
    return await test.step('ShipmentPage - Validate PDF content', async () => {
      const { PdfValidator } = require('../PdfValidator');

      // Use unified PDF validator (includes text + layout validation)
      await PdfValidator.validate(download, {
        documentNo: expectedData.documentNo,
        customerName: expectedData.customerName,
        productCode: expectedData.productCode,
        quantity: expectedData.quantity,
        language: expectedData.language,
        checkOverlaps: true, // Enabled - detects true 2D overlaps
        checkMargins: false, // Disabled - not needed yet
      });
    });
  }

  /**
   * Close the print modal by pressing Escape.
   */
  static async closePrintModal() {
    return await test.step('ShipmentPage - Close print modal', async () => {
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
