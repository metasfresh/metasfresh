import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, } from '../common';
import { INVOICE_CANDIDATE_WINDOW_ID, SALES_INVOICE_CANDIDATE_WINDOW_ID } from '../WindowIds';
import { openRelatedDocument, REFERENCE_DATA_CY } from '../DocumentReferences';

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

  /**
   * Verify that the Quick Actions button is visible.
   * This confirms that an invoice candidate is selected and ready for processing.
   */
  static async expectQuickActionsVisible() {
    return await test.step('InvoiceCandidatePage - Verify Quick Actions button is visible', async () => {
      const page = getPage();

      // Language-independent: Use data-testid for quick action button
      const quickActionButton = page.getByTestId('quick-action-button');

      await quickActionButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await expect(quickActionButton).toBeVisible();
    });
  }

  /**
   * Execute the default Quick Action to generate invoice.
   *
   * Workflow:
   * 1. Click the quick action button
   * 2. Wait for modal to appear
   * 3. Click "Start" button in modal
   * 4. Wait for invoice generation process to complete
   *
   * Note: Assumes the invoice candidate is already selected
   */
  static async generateInvoice() {
    return await test.step('InvoiceCandidatePage - Generate invoice via Quick Action', async () => {
      const page = getPage();

      // Click quick action button
      const quickActionButton = page.getByTestId('quick-action-button');

      await quickActionButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await quickActionButton.click();

      // Wait for modal to appear
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);

      // Click "Start" button in modal (language-independent: data-testid)
      // Try data-testid first, fall back to text-based selector
      const startButton = page.getByTestId('modal-start').or(
        page.locator('.panel-modal button, .modal-content button')
          .filter({ hasText: /^(Start|Starten)$/i })
      );

      await startButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await startButton.click();

      // Wait for modal to close OR page to navigate (invoice generation started)
      // Note: After clicking Start, the page might navigate away, so we handle both cases
      await Promise.race([
        page.locator('.panel-modal, .modal-content').waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        }),
        page.waitForURL(/.*/, { timeout: VERY_SLOW_ACTION_TIMEOUT }),
      ]).catch(() => {
        // Ignore if modal is already closed or page already navigated
      });

      // Give the page a moment to stabilize
      // Use a shorter timeout and catch the error if page was closed
      await page.waitForTimeout(500).catch(() => {
        // Page might have been closed - this is ok
      });
    });
  }

  /**
   * Verify sales invoice candidate window is visible and loaded.
   *
   * IMPORTANT: This method is for SALES invoice candidates accessed via Alt+6 from sales order.
   * Use this when navigating via SalesOrderPage.openRelatedInvoiceCandidate().
   * The invoice candidate should be automatically selected.
   */
  static async expectVisibleForSalesOrder() {
    return await test.step('InvoiceCandidatePage - Verify sales invoice candidate window is visible', async () => {
      const page = getPage();

      // Sales invoice candidate opens as a list view, not a detail window
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
   * Execute C_Invoice_Candidate_EnqueueSelectionForInvoicing action to create invoice from sales order.
   * This is a Quick Action on the sales invoice candidate list view.
   *
   * IMPORTANT: Use this method for SALES invoice candidates accessed via Alt+6 from sales order.
   *
   * The action opens a modal with configuration options.
   * After clicking "Start" in the modal, the invoice is created asynchronously.
   */
  static async createInvoiceForSalesOrder() {
    return await test.step('InvoiceCandidatePage - Create invoice for sales order', async () => {
      const page = getPage();

      // Step 1: Open the quick actions dropdown
      const dropdownToggle = page.getByTestId('quick-action-dropdown-toggle');
      await dropdownToggle.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await dropdownToggle.click();

      // Wait for dropdown to appear
      await page.locator('.quick-actions-dropdown').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(300);

      // Step 2: Click the specific C_Invoice_Candidate_EnqueueSelectionForInvoicing action
      const actionItem = page.getByTestId('quick-action-C_Invoice_Candidate_EnqueueSelectionForInvoicing');
      await actionItem.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await actionItem.click();

      // Step 3: Wait for modal to appear
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);

      // Step 4: Click the "Start" button in the modal
      // Try data-testid first, fall back to last button if not found
      const startButton = page.getByTestId('process-modal-start-button');
      const startButtonExists = await startButton.count();

      if (startButtonExists === 0) {
        // Fallback: Click last button in header (should be Start)
        const lastButton = page.locator('.panel-modal-header button').last();
        await lastButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await lastButton.click();
      } else {
        await startButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await startButton.click();
      }

      // Step 5: Wait for modal to close (indicates process completed)
      await page
        .locator('.panel-modal, .modal-content')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if modal doesn't close
        });

      // Wait for processing indicators to disappear
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no spinner found
        });

      // Additional wait for backend processing
      await page.waitForTimeout(3000);
    });
  }

  /**
   * Verify purchase invoice candidate window is visible and loaded.
   *
   * IMPORTANT: This method is for PURCHASE invoice candidates accessed via Alt+6 from purchase order.
   * Use this when navigating via PurchaseOrderPage.openRelatedInvoiceCandidate().
   * The invoice candidate should be automatically selected.
   *
   * Uses window 540983 (Invoice Candidate - Purchase).
   */
  static async expectVisibleForPurchaseOrder() {
    return await test.step('InvoiceCandidatePage - Verify purchase invoice candidate window is visible', async () => {
      const page = getPage();

      // Purchase invoice candidate opens as a list view, not a detail window
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
   * Execute C_Invoice_Candidate_EnqueueSelectionForInvoicing action to create invoice from purchase order.
   * This is a Quick Action on the purchase invoice candidate list view.
   *
   * IMPORTANT: Use this method for PURCHASE invoice candidates accessed via Alt+6 from purchase order.
   *
   * The action opens a modal with configuration options.
   * After clicking "Start" in the modal, the invoice is created asynchronously.
   *
   * Uses the same action as sales: C_Invoice_Candidate_EnqueueSelectionForInvoicing
   */
  static async createInvoiceForPurchaseOrder() {
    return await test.step('InvoiceCandidatePage - Create invoice for purchase order', async () => {
      const page = getPage();

      // Step 1: Open the quick actions dropdown
      const dropdownToggle = page.getByTestId('quick-action-dropdown-toggle');
      await dropdownToggle.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await dropdownToggle.click();

      // Wait for dropdown to appear
      await page.locator('.quick-actions-dropdown').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(300);

      // Step 2: Click the specific C_Invoice_Candidate_EnqueueSelectionForInvoicing action
      const actionItem = page.getByTestId('quick-action-C_Invoice_Candidate_EnqueueSelectionForInvoicing');
      await actionItem.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await actionItem.click();

      // Step 3: Wait for modal to appear
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);

      // Step 4: Click the "Start" button in the modal
      // Try data-testid first, fall back to last button if not found
      const startButton = page.getByTestId('process-modal-start-button');
      const startButtonExists = await startButton.count();

      if (startButtonExists === 0) {
        // Fallback: Click last button in header (should be Start)
        const lastButton = page.locator('.panel-modal-header button').last();
        await lastButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await lastButton.click();
      } else {
        await startButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await startButton.click();
      }

      // Step 5: Wait for modal to close (indicates process completed)
      await page
        .locator('.panel-modal, .modal-content')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if modal doesn't close
        });

      // Wait for processing indicators to disappear
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no spinner found
        });

      // Additional wait for backend processing
      await page.waitForTimeout(3000);
    });
  }

  /**
   * Open the related Vendor Invoice (C_Invoice) from the Invoice Candidate using Alt+6.
   *
   * Use this method after generating a vendor invoice from the invoice candidate.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 10)
   * @param {number} options.retryDelay - Delay between retries in ms (default: 3000)
   */
  static async openRelatedVendorInvoice({ maxRetries = 10, retryDelay = 3000 } = {}) {
    await openRelatedDocument({
      dataCy: REFERENCE_DATA_CY.IC_TO_VENDOR_INVOICE,
      stepName: 'InvoiceCandidatePage - Open related Vendor Invoice (Alt+6)',
      maxRetries,
      retryDelay,
      navigateToDetail: false, // Opens as list view
      refreshOnRetry: true, // Invoice might be created async
    });
  }
}
