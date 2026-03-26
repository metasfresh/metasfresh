/**
 * Common PDF Download Utility
 *
 * Provides shared functionality for downloading PDFs via the print modal.
 * Handles both direct download and popup (new tab) scenarios.
 *
 * Usage:
 *   const { PdfDownloader } = require('../utils/PdfDownloader');
 *   await PdfDownloader.openPrintModal();
 *   const download = await PdfDownloader.downloadPdf('invoice');
 */

const { test } = require('../../playwright.config');
const { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } = require('./common');

class PdfDownloader {
  /**
   * Open the print modal using Alt+P keyboard shortcut.
   * This opens the PDF generation modal for the current document.
   *
   * @param {string} [stepName='Document'] - Name for the test step (e.g., 'ShipmentPage')
   */
  static async openPrintModal(stepName = 'Document') {
    return await test.step(`${stepName} - Open print modal (Alt+P)`, async () => {
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
   *
   * IMPORTANT: Uses Playwright's built-in request API instead of axios
   * to ensure cookies are properly handled in CI environments.
   *
   * @param {string} [documentType='document'] - Type for temp file naming (e.g., 'shipment', 'invoice')
   * @param {string} [stepName='Document'] - Name for the test step
   * @returns {Promise<Download>} Playwright Download object (or mock with path() method)
   */
  static async downloadPdf(documentType = 'document', stepName = 'Document') {
    return await test.step(`${stepName} - Download PDF`, async () => {
      const page = getPage();
      const fs = require('fs');
      const path = require('path');

      // Wait for modal to be fully loaded and buttons to be ready
      await page.waitForTimeout(1000);

      // Find the Print button using data-testid (language-independent)
      const printButton = page.getByTestId('print-modal-button');

      await printButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Set up both download and popup listeners BEFORE clicking
      const downloadPromise = page
        .waitForEvent('download', { timeout: VERY_SLOW_ACTION_TIMEOUT })
        .catch(() => null);

      const popupPromise = page
        .waitForEvent('popup', { timeout: VERY_SLOW_ACTION_TIMEOUT })
        .catch(() => null);

      console.log('Clicking Print button in modal...');
      await printButton.click();

      // Wait for either download OR popup
      const result = await Promise.race([
        downloadPromise.then((d) => (d ? { type: 'download', data: d } : null)),
        popupPromise.then((p) => (p ? { type: 'popup', data: p } : null)),
      ]);

      if (!result || !result.data) {
        throw new Error('Neither download nor popup occurred after clicking print button');
      }

      if (result.type === 'download') {
        console.log('PDF download started:', result.data.suggestedFilename());
        return result.data;
      } else {
        // Handle popup - PDF opened in new tab
        console.log('PDF opened in new tab');
        const popup = result.data;

        // Wait for popup to load with proper error handling
        try {
          await popup.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT });
        } catch (loadError) {
          console.log('Warning: popup networkidle timeout, continuing anyway');
        }

        const pdfUrl = popup.url();
        console.log('PDF URL:', pdfUrl);

        // Validate URL is not blank or error page
        if (!pdfUrl || pdfUrl === 'about:blank' || !pdfUrl.includes('/print/')) {
          throw new Error(`Invalid PDF popup URL: ${pdfUrl}`);
        }

        // Use Playwright's built-in request API instead of axios
        // This automatically handles cookies and session from the browser context
        const context = popup.context();
        const response = await context.request.get(pdfUrl);

        if (!response.ok()) {
          throw new Error(`Failed to download PDF: ${response.status()} ${response.statusText()}`);
        }

        const buffer = await response.body();
        console.log('PDF downloaded, size:', buffer.length, 'bytes');

        // Validate PDF content (should start with %PDF-)
        const pdfHeader = buffer.slice(0, 5).toString();
        if (pdfHeader !== '%PDF-') {
          console.error('Invalid PDF content, first 100 bytes:', buffer.slice(0, 100).toString());
          throw new Error(`Downloaded file is not a valid PDF. Header: ${pdfHeader}`);
        }

        // Save to temp file
        const tempDir = path.join(process.cwd(), 'test-results', 'temp-pdfs');
        if (!fs.existsSync(tempDir)) {
          fs.mkdirSync(tempDir, { recursive: true });
        }

        const timestamp = Date.now();
        const tempPath = path.join(tempDir, `${documentType}-${timestamp}.pdf`);
        fs.writeFileSync(tempPath, buffer);

        console.log('PDF saved to:', tempPath);

        await popup.close().catch(() => {});

        // Return a mock download object with path() method
        return {
          suggestedFilename: () => `${documentType}-${timestamp}.pdf`,
          path: async () => tempPath,
        };
      }
    });
  }

  /**
   * Open print modal and download PDF in one step.
   *
   * @param {string} [documentType='document'] - Type for temp file naming
   * @param {string} [stepName='Document'] - Name for test steps
   * @returns {Promise<Download>} Playwright Download object
   */
  static async openAndDownload(documentType = 'document', stepName = 'Document') {
    await this.openPrintModal(stepName);
    return await this.downloadPdf(documentType, stepName);
  }
}

module.exports = { PdfDownloader };
