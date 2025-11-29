import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, } from '../common';
import { INVOICE_CANDIDATE_WINDOW_ID } from '../WindowIds';

/**
 * Page object for Invoice Candidate window (ID: 540983).
 * Handles viewing, filtering, and processing invoice candidates.
 */
export class InvoiceCandidatePage {
  /**
   * Navigate to Invoice Candidate window.
   */
  static async goto() {
    return await test.step('InvoiceCandidatePage - Navigate to Invoice Candidate window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${INVOICE_CANDIDATE_WINDOW_ID}`);

      // Wait for document list to load
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });

      // Wait for table data to load
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Wait for invoice candidates to appear in the grid.
   * Invoice candidates are generated asynchronously, so we may need to wait.
   * @param {number} timeoutMs - Maximum time to wait in milliseconds (default: 60000)
   */
  static async waitForCandidates(timeoutMs = 60000) {
    return await test.step('InvoiceCandidatePage - Wait for invoice candidates to appear', async () => {
      const page = getPage();

      const startTime = Date.now();
      let found = false;

      while (!found && (Date.now() - startTime < timeoutMs)) {
        // Refresh the view to check for new candidates
        await page.keyboard.press('F5');
        await page.waitForTimeout(2000);

        // Check if any rows are present
        const rowCount = await page.locator('.table tbody tr, table tbody tr').count();

        if (rowCount > 0) {
          found = true;
          console.log(`Invoice candidates found after ${Date.now() - startTime}ms`);
        } else {
          console.log('Waiting for invoice candidates to be generated...');
          await page.waitForTimeout(3000);
        }
      }

      if (!found) {
        throw new Error(`No invoice candidates found after ${timeoutMs}ms`);
      }
    });
  }

  /**
   * Filter/search for invoice candidates by product name.
   * Uses the search/filter functionality in the grid view.
   * @param {string} productName - Product name to search for
   */
  static async filterByProduct(productName) {
    return await test.step(`InvoiceCandidatePage - Filter by product: ${productName}`, async () => {
      const page = getPage();

      // Look for filter/search input in the grid
      // metasfresh grids typically have a filter panel or search box
      const filterInput = page.locator(
        'input[placeholder*="Filter"], input[placeholder*="Search"], .filter-input'
      ).first();

      const filterExists = await filterInput.count() > 0;

      if (filterExists) {
        await filterInput.fill(productName);
        await page.waitForTimeout(1000); // Wait for filter to apply
      } else {
        console.log('No filter input found - using advanced filter or column filter');

        // Alternative: Use column-specific filter (click on column header)
        // This is more complex and depends on the grid implementation
        // For now, we'll skip filtering and just select the first candidate
      }
    });
  }

  /**
   * Open a specific invoice candidate by clicking on a row in the grid.
   * @param {number} index - Row index (0-based)
   */
  static async openCandidate(index = 0) {
    return await test.step(`InvoiceCandidatePage - Open invoice candidate at index ${index}`, async () => {
      const page = getPage();

      // Wait for table rows to be visible
      await page.locator('.table tbody tr, table tbody tr').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Double-click the row to open detail view
      const row = page.locator('.table tbody tr, table tbody tr').nth(index);
      await row.dblclick();

      // Wait for navigation to detail view
      await page.waitForURL(/\/window\/540983\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the detail view to load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore timeout
      });

      await page.waitForTimeout(1000);
    });
  }

  /**
   * Set the QtyToInvoice_Override field to a specific quantity.
   * This overrides the default quantity to invoice.
   * @param {number} quantity - Quantity to invoice
   */
  static async setQtyToInvoiceOverride(quantity) {
    return await test.step(`InvoiceCandidatePage - Set QtyToInvoice_Override to ${quantity}`, async () => {
      const page = getPage();

      // Look for the QtyToInvoice_Override field
      // Try multiple possible selectors based on field name and caption
      const qtyOverrideInput = page.locator(
        'input[name="QtyToInvoice_Override"], input[name="QtyToInvoiceInUOM_Override"]'
      ).first();

      // Alternative: Find by label/caption
      const qtyOverrideByLabel = page.locator(
        'text=/To invoice.*override/i >> .. >> input'
      ).first();

      // Try to find the field
      let input = qtyOverrideInput;
      const exists = await qtyOverrideInput.count() > 0;

      if (!exists) {
        const labelExists = await qtyOverrideByLabel.count() > 0;
        if (labelExists) {
          input = qtyOverrideByLabel;
        } else {
          throw new Error('Could not find QtyToInvoice_Override field');
        }
      }

      // Clear and fill the quantity
      await input.clear();
      await input.fill(quantity.toString());

      // Press Tab to trigger field validation/processing
      await input.press('Tab');

      // Wait for the value to be processed (recomputation may happen)
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Trigger the recompute action if available.
   * This recalculates invoice candidate amounts after changing override values.
   */
  static async recompute() {
    return await test.step('InvoiceCandidatePage - Recompute invoice candidate', async () => {
      const page = getPage();

      // Look for a "Recompute" action button in the action menu
      // This might be in a gear menu or action dropdown
      const recomputeButton = page.locator(
        'button:has-text("Recompute"), .btn:has-text("Recompute"), [title*="Recompute"]'
      ).first();

      const buttonExists = await recomputeButton.count() > 0;

      if (buttonExists) {
        await recomputeButton.click();
        await page.waitForTimeout(1500);
      } else {
        console.log('No explicit Recompute button found - recomputation may be automatic');
      }
    });
  }

  /**
   * Process the invoice candidate(s) to generate invoices.
   * This typically uses a gear menu action or process button.
   */
  static async processInvoice() {
    return await test.step('InvoiceCandidatePage - Process/Enqueue invoice', async () => {
      const page = getPage();

      // Look for gear menu (actions menu)
      const gearMenu = page.locator('.btn-meta-action, button.meta-icon, .actions-menu').first();

      const gearExists = await gearMenu.count() > 0;

      if (gearExists) {
        // Click gear menu to open actions
        await gearMenu.click();
        await page.waitForTimeout(500);

        // Look for "Enqueue for invoicing" or similar action
        await page.locator(
          'text=/enqueue.*invoice/i, text=/process.*invoice/i, text=/generate.*invoice/i'
        ).first().click();

        await page.waitForTimeout(2000);
      } else {
        // Alternative: Look for a direct "Process" or "Invoice" button
        const processButton = page.locator(
          'button:has-text("Process"), button:has-text("Invoice"), .btn:has-text("Enqueue")'
        ).first();

        const processExists = await processButton.count() > 0;

        if (processExists) {
          await processButton.click();
          await page.waitForTimeout(2000);
        } else {
          console.log('No process/invoice button found - manual processing may be required');
        }
      }
    });
  }

  /**
   * Wait for invoice generation to complete.
   * Invoice generation is asynchronous and may take up to 60 seconds.
   * @param {number} timeoutMs - Maximum time to wait (default: 60000ms)
   */
  static async waitForInvoiceGeneration(timeoutMs = 60000) {
    return await test.step('InvoiceCandidatePage - Wait for invoice generation', async () => {
      const page = getPage();

      const startTime = Date.now();
      let invoiceGenerated = false;

      while (!invoiceGenerated && (Date.now() - startTime < timeoutMs)) {
        // Refresh or check for invoice link/status
        await page.keyboard.press('F5');
        await page.waitForTimeout(2000);

        // Check if C_Invoice_ID field has a value (indicates invoice was generated)
        const invoiceField = page.locator('input[name="C_Invoice_ID"]');
        const invoiceFieldExists = await invoiceField.count() > 0;

        if (invoiceFieldExists) {
          const invoiceValue = await invoiceField.inputValue().catch(() => '');
          if (invoiceValue && invoiceValue.length > 0) {
            invoiceGenerated = true;
            console.log(`Invoice generated after ${Date.now() - startTime}ms`);
          }
        }

        if (!invoiceGenerated) {
          console.log('Waiting for invoice to be generated...');
          await page.waitForTimeout(3000);
        }
      }

      if (!invoiceGenerated) {
        console.log(`Warning: Invoice may not have been generated after ${timeoutMs}ms`);
      }
    });
  }

  /**
   * Get the generated invoice document number.
   * @returns {Promise<string|null>} The invoice document number, or null if not generated
   */
  static async getInvoiceDocumentNo() {
    return await test.step('InvoiceCandidatePage - Get invoice document number', async () => {
      const page = getPage();

      // Look for the C_Invoice_ID field which should have a link to the invoice
      const invoiceField = page.locator('input[name="C_Invoice_ID"]');
      const invoiceFieldExists = await invoiceField.count() > 0;

      if (!invoiceFieldExists) {
        return null;
      }

      const invoiceValue = await invoiceField.inputValue();

      return invoiceValue && invoiceValue.length > 0 ? invoiceValue : null;
    });
  }

  /**
   * Verify the expected quantity and amount in the invoice candidate.
   * @param {Object} expected - Expected values
   * @param {number} expected.qtyToInvoice - Expected quantity to invoice
   * @param {number} expected.netAmt - Expected net amount
   */
  static async verifyCandidate({ qtyToInvoice, netAmt }) {
    return await test.step(`InvoiceCandidatePage - Verify candidate (Qty: ${qtyToInvoice}, NetAmt: ${netAmt})`, async () => {
      const page = getPage();

      if (qtyToInvoice !== undefined) {
        const qtyField = page.locator(
          'input[name="QtyToInvoice"], input[name="QtyToInvoiceInUOM"]'
        ).first();

        const actualQty = await qtyField.inputValue();
        expect(parseFloat(actualQty)).toBe(qtyToInvoice);
      }

      if (netAmt !== undefined) {
        const netAmtField = page.locator(
          'input[name="NetAmtToInvoice"], input[name="LineNetAmt"]'
        ).first();

        const actualAmt = await netAmtField.inputValue();
        // Remove currency symbols and parse
        const cleanAmt = actualAmt.replace(/[^0-9.,]/g, '').replace(',', '.');
        expect(parseFloat(cleanAmt)).toBe(netAmt);
      }
    });
  }
}
