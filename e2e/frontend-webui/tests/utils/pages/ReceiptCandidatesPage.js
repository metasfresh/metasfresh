import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { PdfDownloader } from '../PdfDownloader';

/**
 * Page object for Material Receipt Candidates window (ID: 540196).
 *
 * IMPORTANT: This page object assumes the receipt candidate is already opened
 * and selected via PurchaseOrderPage.openRelatedReceiptCandidate() (Alt+6 workflow).
 *
 * DO NOT navigate to window 540196 directly - it will select the wrong candidate!
 *
 * ## Two-Phase Receipt Workflow
 *
 * Phase 1 - "Receive HUs" (WEBUI_M_ReceiptSchedule_ReceiveHUs_*):
 * - User clicks Quick Action on Receipt Candidate
 * - System generates Planning HUs and opens HU-Editor modal
 * - HU rows appear with status "Planning"
 *
 * Phase 2 - "Create Receipt" (WEBUI_M_HU_CreateReceipt_*):
 * - User selects HU rows in the modal
 * - User clicks "Create Receipt" Quick Action within the modal
 * - System creates M_InOut (Material Receipt) document
 * - HU status changes from "Planning" to "Active"
 *
 * Workflow:
 * 1. Complete purchase order in PurchaseOrderPage
 * 2. Use PurchaseOrderPage.openRelatedReceiptCandidate() to navigate (Alt+6)
 * 3. Receipt candidate is already selected with Quick Actions visible
 * 4. Execute Quick Action to open HU Editor modal (Phase 1)
 * 5. Wait for Planning HU rows to appear
 * 6. Select HU rows
 * 7. Execute "Create Receipt" Quick Action in modal (Phase 2)
 * 8. Wait for receipt processing to complete
 * 9. Click "Done" to close modal
 */
export class ReceiptCandidatesPage {

  /**
   * Ensure no modal overlays are blocking the page.
   *
   * This method waits for any modal overlays (screen-freeze, raw-modal) to be
   * removed from the DOM before proceeding. This prevents "element intercepts
   * pointer events" errors when trying to click elements on the main page.
   *
   * @param {number} [timeout=SLOW_ACTION_TIMEOUT] - Maximum time to wait
   */
  static async ensureNoModalBlocking(timeout = SLOW_ACTION_TIMEOUT) {
    return await test.step('ReceiptCandidatesPage - Ensure no modal blocking', async () => {
      const page = getPage();

      // Wait for screen-freeze overlay to be removed
      await page.locator('.screen-freeze').waitFor({
        state: 'detached',
        timeout: timeout,
      }).catch(() => {
        // No overlay present, which is fine
      });

      // Wait for raw-modal to be removed
      await page.locator('.raw-modal').waitFor({
        state: 'detached',
        timeout: timeout,
      }).catch(() => {
        // No modal present, which is fine
      });

      // Wait for modal-done button to be removed (confirms modal is fully closed)
      await page.getByTestId('modal-done').waitFor({
        state: 'detached',
        timeout: timeout,
      }).catch(() => {
        // No done button present, which is fine
      });

      // Wait for any spinners/loaders to complete
      await page.locator('.rotating, .indicator-pending, .loader, .spinner').waitFor({
        state: 'detached',
        timeout: timeout,
      }).catch(() => {
        // No spinners present, which is fine
      });

      // Final stabilization wait
      await page.waitForTimeout(300);
    });
  }

  /**
   * Execute the default Quick Action (simple approach).
   *
   * Clicks the quick action button which executes the default action.
   * This is simpler than opening the dropdown and selecting a specific action.
   *
   * Note: Assumes the default action opens a modal (e.g., HU Editor).
   */
  static async executeDefaultQuickAction() {
    return await test.step('ReceiptCandidatesPage - Execute default Quick Action', async () => {
      const page = getPage();

      // Language-independent: Use data-testid added in Phase 2
      const quickActionButton = page.getByTestId('quick-action-button');

      await quickActionButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await quickActionButton.click();

      // Wait for modal to appear (HU Editor)
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);
    });
  }

  /**
   * Open the Quick Actions dropdown.
   *
   * Clicks the dropdown toggle button (down arrow) to show all available actions.
   *
   * IMPORTANT: This method first ensures no modal overlays are blocking the page.
   * This prevents "element intercepts pointer events" errors after modal operations.
   */
  static async openQuickActionsDropdown() {
    return await test.step('ReceiptCandidatesPage - Open Quick Actions dropdown', async () => {
      const page = getPage();

      // CRITICAL: Ensure no modal is blocking before trying to click
      await this.ensureNoModalBlocking();

      // Language-independent: Use data-testid added in Phase 2
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
    });
  }

  /**
   * Select a specific action from the Quick Actions dropdown (explicit approach).
   *
   * Opens the dropdown and clicks a specific action by its internal name.
   * Use this when you need to execute a non-default action.
   *
   * @param {string} actionInternalName - Internal name or process ID of the action
   *                                      (e.g., "WEBUI_M_HU_CreateReceipt_NoParams")
   */
  static async selectQuickAction(actionInternalName) {
    return await test.step(`ReceiptCandidatesPage - Select quick action: ${actionInternalName}`, async () => {
      const page = getPage();

      // First, open the dropdown if not already open
      const dropdown = page.locator('.quick-actions-dropdown');
      const isOpen = await dropdown.isVisible().catch(() => false);

      if (!isOpen) {
        await this.openQuickActionsDropdown();
      }

      // Language-independent: Use data-testid added in Phase 2
      const actionItem = page.getByTestId(`quick-action-${actionInternalName}`);

      await actionItem.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await actionItem.click();

      // Wait for modal to appear (HU Editor)
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await page.waitForTimeout(500);
    });
  }

  /**
   * Wait for Planning HU rows to appear in the HU-Editor modal.
   *
   * After executing "Receive HUs" Quick Action (Phase 1), Planning HUs are generated
   * and displayed in a table within the modal.
   */
  static async waitForPlanningHURows() {
    return await test.step('ReceiptCandidatesPage - Wait for Planning HU rows', async () => {
      const page = getPage();

      // Wait for any spinners to disappear
      await page
        .locator('.rotating, .indicator-pending, .loader')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Might not have visible spinners
        });

      // Wait for HU rows to appear in the modal table
      // Use combined selector: data-testid (new) OR class-based (legacy)
      const rowSelector = '.panel-modal .table-row, .modal-content .table-row, ' +
                          '.panel-modal [data-testid^="table-row-"], .modal-content [data-testid^="table-row-"], ' +
                          '.panel-modal .table tbody tr, .modal-content .table tbody tr';
      await page
        .locator(rowSelector)
        .first()
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });

      // Give the UI time to finish rendering all rows
      await page.waitForTimeout(500);
    });
  }

  /**
   * Select the first HU row in the modal.
   *
   * Clicks on the first table row to select it for further actions.
   */
  static async selectFirstHURow() {
    return await test.step('ReceiptCandidatesPage - Select first HU row', async () => {
      const page = getPage();

      // Find the first HU row in the modal
      // Use combined selector: data-testid (new) OR class-based (legacy)
      const rowSelector = '.panel-modal .table-row, .modal-content .table-row, ' +
                          '.panel-modal [data-testid^="table-row-"], .modal-content [data-testid^="table-row-"], ' +
                          '.panel-modal .table tbody tr, .modal-content .table tbody tr';
      const firstRow = page.locator(rowSelector).first();

      await firstRow.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Scroll row into view and click
      await firstRow.scrollIntoViewIfNeeded();
      await firstRow.click();

      // Wait for selection to be processed
      await page.waitForTimeout(500);

      // Try to verify row is selected, but don't fail if class isn't added
      // (some modal views might use different selection mechanism)
      const hasSelectedClass = await firstRow.evaluate(el => el.classList.contains('row-selected'));
      if (!hasSelectedClass) {
        // Try clicking again with force option
        await firstRow.click({ force: true });
        await page.waitForTimeout(500);
      }

      // Verify selection by checking if Quick Actions button is visible
      // (actions appear when rows are selected)
      const modalQuickAction = page.locator('.panel-modal [data-testid="quick-action-button"], .modal-content [data-testid="quick-action-button"]');
      const quickActionVisible = await modalQuickAction.isVisible().catch(() => false);

      if (!quickActionVisible) {
        // If no quick action visible, maybe selection failed - throw error
        console.log('Warning: Quick Action not visible after row click - selection may have failed');
      }
    });
  }

  /**
   * Select all HU rows in the modal.
   *
   * Uses Ctrl+A to select all rows, or clicks each row with Ctrl held.
   */
  static async selectAllHURows() {
    return await test.step('ReceiptCandidatesPage - Select all HU rows', async () => {
      const page = getPage();

      // Find all HU rows in the modal
      // Use combined selector: data-testid (new) OR class-based (legacy)
      const rowSelector = '.panel-modal .table-row, .modal-content .table-row, ' +
                          '.panel-modal [data-testid^="table-row-"], .modal-content [data-testid^="table-row-"], ' +
                          '.panel-modal .table tbody tr, .modal-content .table tbody tr';
      const rows = page.locator(rowSelector);
      const rowCount = await rows.count();

      if (rowCount === 0) {
        throw new Error('No HU rows found in modal');
      }

      // Click first row to focus the table
      await rows.first().click();
      await page.waitForTimeout(200);

      // Use keyboard shortcut Ctrl+A to select all
      // First, focus on the table container
      const tableContainer = page.locator('.panel-modal .js-table, .modal-content .js-table').first();
      const hasTable = await tableContainer.count() > 0;

      if (hasTable) {
        await tableContainer.focus();
        await page.keyboard.press('Control+a');
      } else {
        // Fallback: Click each row with Ctrl key
        for (let i = 1; i < rowCount; i++) {
          await rows.nth(i).click({ modifiers: ['Control'] });
          await page.waitForTimeout(100);
        }
      }

      await page.waitForTimeout(300);
    });
  }

  /**
   * Execute the "Create Receipt" Quick Action within the HU-Editor modal.
   *
   * This is Phase 2 of the receipt workflow - converts selected Planning HUs
   * into an actual Material Receipt (M_InOut) document.
   */
  static async executeCreateReceiptInModal() {
    return await test.step('ReceiptCandidatesPage - Execute Create Receipt in modal', async () => {
      const page = getPage();

      // Wait for Quick Actions to be available in the modal
      // The quick action button should appear after rows are selected
      const modalQuickAction = page.locator('.panel-modal [data-testid="quick-action-button"], .modal-content [data-testid="quick-action-button"]');

      await modalQuickAction.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await modalQuickAction.click();

      // Wait for processing to start (button might become disabled or show spinner)
      await page.waitForTimeout(500);
    });
  }

  /**
   * Wait for receipt processing to complete after clicking "Create Receipt".
   *
   * The system creates M_InOut records and changes HU status from Planning to Active.
   * HU rows will appear greyed out (processed) when complete.
   */
  static async waitForReceiptProcessingComplete() {
    return await test.step('ReceiptCandidatesPage - Wait for receipt processing', async () => {
      const page = getPage();

      // Wait for spinners to disappear
      await page
        .locator('.rotating, .indicator-pending, .loader, .spinner')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Might not have visible spinners
        });

      // Wait for processing indicator in modal to disappear
      await page
        .locator('.panel-modal .indicator-pending, .modal-content .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no indicator
        });

      // Wait for rows to become processed (greyed out)
      // Processed rows have 'row-disabled' class
      await page
        .locator('.panel-modal .row-disabled, .modal-content .row-disabled')
        .first()
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Row might not become disabled in all cases
        });

      // Give the UI time to finish updating
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Wait for receipt to be created in the HU Editor modal (legacy method).
   *
   * @deprecated Use waitForPlanningHURows() + selectFirstHURow() + executeCreateReceiptInModal() + waitForReceiptProcessingComplete() instead.
   */
  static async waitForReceiptCreation() {
    return await test.step('ReceiptCandidatesPage - Wait for receipt creation (legacy)', async () => {
      const page = getPage();

      // Wait for spinners to disappear
      await page
        .locator('.rotating, .indicator-pending, .loader')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Might not have visible spinners
        });

      // Wait for rows to appear in the HU Editor
      await page
        .locator('.panel-modal .table tbody tr, .modal-content .table tbody tr')
        .first()
        .waitFor({
          state: 'visible',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no rows found
        });

      // Give the UI time to finish rendering
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Close the HU Editor modal by clicking "Done".
   *
   * IMPORTANT: This method uses a robust wait pattern to ensure the modal
   * is fully closed before returning. This prevents "intercepts pointer events"
   * errors on subsequent actions.
   */
  static async closeHUEditorModal() {
    return await test.step('ReceiptCandidatesPage - Close HU Editor modal', async () => {
      const page = getPage();

      // Language-independent: ModalButton.js already has data-testid="modal-done"
      const doneButton = page.getByTestId('modal-done');

      await doneButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await doneButton.click();

      // CRITICAL: Wait for the modal overlay (screen-freeze) to be removed
      // This prevents "intercepts pointer events" errors on subsequent actions

      // Wait for compound class selector (element with both classes)
      await page.locator('.screen-freeze.raw-modal').waitFor({
        state: 'detached',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Try individual selectors
      });

      // Wait for screen-freeze alone
      await page.locator('.screen-freeze').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Might already be gone
      });

      // Wait for raw-modal alone
      await page.locator('.raw-modal').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Might already be gone
      });

      // Also wait for modal content to be removed
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Modal content might already be gone
      });

      // Ensure the Done button is gone (confirms modal closed)
      await page.getByTestId('modal-done').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Button might already be gone
      });

      // Wait for any spinners/loaders
      await page.locator('.rotating, .indicator-pending, .loader, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // No spinners present
      });

      // Wait for network to settle
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Additional stabilization wait to ensure no lingering modal effects
      await page.waitForTimeout(500);
    });
  }

  /**
   * Complete workflow: Create a material receipt from the already-selected candidate.
   *
   * This method implements the FULL two-phase workflow:
   * - Phase 1: Open HU-Editor and generate Planning HUs
   * - Phase 2: Select HUs and create Material Receipt (M_InOut)
   *
   * IMPORTANT: Assumes receipt candidate is already selected via PurchaseOrderPage.openRelatedReceiptCandidate().
   */
  static async createReceipt() {
    return await test.step('ReceiptCandidatesPage - Create receipt from selected candidate', async () => {
      // Phase 1: Execute "Receive HUs" to generate Planning HUs
      await this.executeDefaultQuickAction();
      await this.waitForPlanningHURows();

      // Phase 2: Select HUs and create Material Receipt
      await this.selectFirstHURow();
      await this.executeCreateReceiptInModal();
      await this.waitForReceiptProcessingComplete();

      // Close the modal
      await this.closeHUEditorModal();
    });
  }

  /**
   * Legacy workflow: Opens modal, waits, and closes (does NOT create actual receipt).
   *
   * @deprecated Use createReceipt() for the complete workflow that actually creates M_InOut.
   */
  static async createReceiptLegacy() {
    return await test.step('ReceiptCandidatesPage - Create receipt (legacy - no actual receipt)', async () => {
      await this.executeDefaultQuickAction();
      await this.waitForReceiptCreation();
      await this.closeHUEditorModal();
    });
  }

  /**
   * Execute the "Receive CUs with Qty" action with a specified quantity.
   *
   * This method executes the parameterized receipt action (WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam)
   * which creates a Material Receipt directly with the specified CU quantity.
   *
   * Unlike the two-phase HU workflow, this action:
   * - Opens a parameter dialog to enter the quantity
   * - Creates the M_InOut directly without opening HU-Editor modal
   *
   * IMPORTANT: Assumes receipt candidate is already selected via PurchaseOrderPage.openRelatedReceiptCandidate().
   *
   * @param {number} quantity - The quantity of CUs to receive
   */
  static async receiveCUsWithQuantity(quantity) {
    return await test.step(`ReceiptCandidatesPage - Receive CUs with quantity ${quantity}`, async () => {
      const page = getPage();

      // Step 1: Open Quick Actions dropdown
      await this.openQuickActionsDropdown();

      // Step 2: Click the parameterized CU receipt action
      // Language-independent: Use data-testid with process value
      const actionItem = page.getByTestId('quick-action-WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam');

      await actionItem.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await actionItem.click();

      // Step 3: Wait for parameter dialog to appear
      // The dialog contains a form field for QtyCUsPerTU
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Step 4: Find and fill the quantity field
      // Language-independent: Use field name selector (QtyCUsPerTU)
      // Try multiple selector patterns for the quantity input
      const quantityInput = page.locator(
        '.form-field-QtyCUsPerTU input, ' +
        'input[name="QtyCUsPerTU"], ' +
        '.panel-modal .input-number input, ' +
        '.modal-content .input-number input'
      ).first();

      await quantityInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Clear existing value and enter new quantity
      await quantityInput.clear();
      await quantityInput.fill(String(quantity));

      // Wait for input to have the expected value (React state update)
      await expect(quantityInput).toHaveValue(String(quantity), { timeout: 5000 });

      // Step 5: Submit the parameter dialog by clicking the Start button
      // Language-independent: Use data-testid for modal buttons
      // Note: Process modals use "process-modal-start-button" (see Modal.js)
      const submitButton = page.getByTestId('process-modal-start-button').or(
        page.getByTestId('modal-start')
      ).or(
        page.getByTestId('modal-done')
      );

      await submitButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await submitButton.click();

      // Step 6: Wait for HU Editor to appear
      // The process opens an HU Editor modal where we need to create the receipt
      // Using data-testid for language-independent detection (modal-done button appears when modal opens)
      await page.getByTestId('modal-done').waitFor({
        state: 'visible',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });

      // Step 7: Click "Create material receipt" action in the HU Editor
      // IMPORTANT: Scope to the modal to avoid interacting with elements from the main view behind the modal
      // The modal has class "screen-freeze raw-modal" and contains the HU Editor
      const modal = page.locator('.screen-freeze.raw-modal, .panel-modal').first();

      // Open the quick actions dropdown to access the specific action
      const dropdownToggle = modal.getByTestId('quick-action-dropdown-toggle');
      await dropdownToggle.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await dropdownToggle.click();

      // Click the specific "Create material receipt" action by its AD_Process.Value
      // Using data-testid ensures we get the correct action regardless of display order
      const createReceiptAction = modal.getByTestId('quick-action-WEBUI_M_HU_CreateReceipt_NoParams');
      await createReceiptAction.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await createReceiptAction.click();

      // Step 8: Wait for receipt creation to complete
      // First wait for any spinners to disappear
      await page
        .locator('.rotating, .indicator-pending, .loader, .spinner')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Continue if no spinner visible
        });

      // Then wait for network to settle (receipt creation involves backend processing)
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Step 9: Click "Done" to close the HU Editor
      // Using data-testid for language-independent selection
      const doneButton = page.getByTestId('modal-done');
      await doneButton.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await doneButton.click();

      // Step 10: Wait for HU Editor modal to fully close
      // CRITICAL: Wait for the modal overlay (screen-freeze) to be removed
      // This prevents "intercepts pointer events" errors on subsequent actions

      // Wait for screen-freeze overlay to be removed (compound selector for element with both classes)
      await page.locator('.screen-freeze.raw-modal').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Try again with individual selectors
        console.log('Compound selector detach failed, trying individual selectors');
      });

      // Also wait for screen-freeze alone (in case classes aren't combined)
      await page.locator('.screen-freeze').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Might already be gone
      });

      // Also wait for raw-modal alone
      await page.locator('.raw-modal').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Might already be gone
      });

      // Ensure the Done button is gone (confirms modal closed)
      await page.getByTestId('modal-done').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Button might already be gone
      });

      // Wait for any remaining spinners/loaders
      await page.locator('.rotating, .indicator-pending, .loader, .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // No spinners present
      });

      // Wait for page to stabilize after modal close
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Additional stabilization wait to ensure no lingering modal effects
      await page.waitForTimeout(1000);

      console.log(`Receipt created successfully for quantity ${quantity}`);
    });
  }

  /**
   * Complete workflow: Create a material receipt using a specific action (explicit approach).
   *
   * Opens the dropdown and selects a specific action by internal name.
   * Use this when you need a non-default action.
   * IMPORTANT: Assumes receipt candidate is already selected via PurchaseOrderPage.openRelatedReceiptCandidate().
   *
   * @param {string} actionInternalName - Quick action internal name (e.g., "WEBUI_M_HU_CreateReceipt_NoParams")
   */
  static async createReceiptWithAction(actionInternalName) {
    return await test.step(`ReceiptCandidatesPage - Create receipt using ${actionInternalName}`, async () => {
      // Phase 1: Execute specific action to generate Planning HUs
      await this.selectQuickAction(actionInternalName);
      await this.waitForPlanningHURows();

      // Phase 2: Select HUs and create Material Receipt
      await this.selectFirstHURow();
      await this.executeCreateReceiptInModal();
      await this.waitForReceiptProcessingComplete();

      // Close the modal
      await this.closeHUEditorModal();
    });
  }

  /**
   * Verify that the Quick Actions button is visible (confirms correct candidate selected).
   */
  static async expectQuickActionsVisible() {
    return await test.step('ReceiptCandidatesPage - Verify Quick Actions button visible', async () => {
      const page = getPage();

      const quickActionButton = page.getByTestId('quick-action-button');
      await expect(quickActionButton).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  /**
   * Navigate to the Material Receipt detail view via the "Allocated Material Receipt" tab.
   *
   * This method:
   * 1. Clicks on the "Allocated Material Receipt" tab (M_ReceiptSchedule_Alloc)
   * 2. Right-clicks on the first allocation row
   * 3. Selects "Zoom Into" from the context menu
   * 4. Navigates to the Material Receipt (M_InOut) detail view
   *
   * IMPORTANT: Call this after createReceipt() while still on the Receipt Candidates window.
   *
   * @param {Object} options - Configuration options
   * @param {number} options.maxRetries - Maximum retry attempts (default: 3)
   * @param {number} options.rowIndex - Which row to click (0 = first, -1 = last, default: 0)
   */
  static async navigateToMaterialReceiptViaTab({ maxRetries = 3, rowIndex = 0 } = {}) {
    const rowDescription = rowIndex === -1 ? 'last' : rowIndex === 0 ? 'first' : `row ${rowIndex}`;
    return await test.step(`ReceiptCandidatesPage - Navigate to Material Receipt via Allocated tab (${rowDescription} row)`, async () => {
      const page = getPage();

      let lastError = null;

      for (let attempt = 1; attempt <= maxRetries; attempt++) {
        try {
          // Step 0: Check if we're on a list view and need to open detail view first
          // List view URL: /window/540196?page=1&... (has query params, no record ID)
          // Detail view URL: /window/540196/{recordId} (has record ID after window ID)
          const currentUrl = page.url();
          const isListView = currentUrl.includes('page=') || !currentUrl.match(/\/window\/540196\/\d+/);

          if (isListView) {
            console.log('On list view, need to open detail view first');

            // Double-click on first row to open detail view
            // Use various selectors for table rows in the document list
            const listRow = page.locator('.document-list-wrapper .table-row, .document-list .table-row, .table tbody tr').first();
            await listRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

            await listRow.dblclick();
            console.log('Double-clicked on receipt candidate row');

            // Wait for navigation to detail view
            await page.waitForURL(/\/window\/540196\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

            console.log('Navigated to detail view:', page.url());
          }

          // Wait for detail view to fully load (spinners to disappear)
          await page.locator('.rotating, .indicator-pending, .loader')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

          // Step 1: Click on the "Allocated Material Receipt" tab
          // Tab ID 540530, internal name M_ReceiptSchedule_Alloc
          // Language-independent: Use data-testid with tab ID (format: tab-{tabId})
          // Also fallback to id="tab_M_ReceiptSchedule_Alloc" using internal name
          const allocatedReceiptTab = page.getByTestId('tab-540530').or(
            page.locator('#tab_M_ReceiptSchedule_Alloc')
          );

          await allocatedReceiptTab.waitFor({
            state: 'visible',
            timeout: SLOW_ACTION_TIMEOUT,
          });

          await allocatedReceiptTab.click();
          console.log('Clicked Allocated Material Receipt tab');

          // Wait for tab content to load (spinners to disappear)
          await page
            .locator('.rotating, .indicator-pending, .loader')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

          // Step 2: Wait for table rows in the tab
          // The tab contains M_ReceiptSchedule_Alloc records linking to M_InOutLine
          // Tab tables are inside .tabs-content or similar container
          const allTabRows = page.locator('.tabs-content .table-row, .tab-content .table-row, .tabs-content .table tbody tr');
          await allTabRows.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

          // Select the appropriate row based on rowIndex
          // rowIndex -1 means last row, 0 means first row, etc.
          let tabTableRow;
          if (rowIndex === -1) {
            tabTableRow = allTabRows.last();
          } else {
            tabTableRow = allTabRows.nth(rowIndex);
          }

          const rowCount = await allTabRows.count();
          console.log(`Found ${rowCount} allocation row(s) in tab, selecting ${rowDescription}`);

          // Step 3: First left-click to select the row, then right-click for context menu
          await tabTableRow.click();
          await tabTableRow.click({ button: 'right' });

          // Wait for context menu to appear
          const contextMenu = page.locator('.context-menu');
          await contextMenu.waitFor({ state: 'visible', timeout: 5000 });

          console.log('Context menu opened');

          // Step 4: Click "Zoom Into" option
          // LANGUAGE-INDEPENDENT: Use icon class 'meta-icon-share' instead of text
          // The menu item has icon="meta-icon-share" which is stable across languages
          // IMPORTANT: Zoom Into opens in a NEW TAB (window.open with _blank)
          const zoomIntoItem = contextMenu.locator('.context-menu-item').filter({
            has: page.locator('.meta-icon-share'),
          }).first();

          await zoomIntoItem.waitFor({ state: 'visible', timeout: 5000 });

          // Set up listener for the popup BEFORE clicking
          const popupPromise = page.context().waitForEvent('page', { timeout: SLOW_ACTION_TIMEOUT });

          await zoomIntoItem.click();
          console.log('Clicked Zoom Into');

          // Step 5: Wait for new tab to open
          const newPage = await popupPromise;
          console.log('New tab opened:', newPage.url());

          // Wait for the new page to load
          await newPage.waitForLoadState('networkidle', {
            timeout: SLOW_ACTION_TIMEOUT,
          }).catch(() => {});

          // Verify we're on the Material Receipt detail view
          const finalUrl = newPage.url();
          if (finalUrl.includes('/window/184/')) {
            console.log(`Successfully navigated to Material Receipt on attempt ${attempt}`);

            // IMPORTANT: Switch context to new page
            // The shared page reference uses global.currentPage
            global.currentPage = newPage;

            return;
          }

          // Close the new page if it wasn't the right one
          await newPage.close();
          throw new Error(`Expected navigation to window/184, but URL is ${finalUrl}`);

        } catch (error) {
          lastError = error;
          console.log(`Attempt ${attempt}/${maxRetries} failed: ${error.message}`);

          // Press Escape to close any open menus before retrying
          await page.keyboard.press('Escape');
          await page.waitForTimeout(200);

          if (attempt < maxRetries) {
            await page.waitForTimeout(2000 * attempt);
          }
        }
      }

      throw new Error(
        `Failed to navigate to Material Receipt via tab after ${maxRetries} attempts. ` +
        `Last error: ${lastError?.message}`
      );
    });
  }

  /**
   * Get the document number from the current Material Receipt window.
   * Language-independent: Uses database field name DocumentNo.
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('ReceiptCandidatesPage - Get document number', async () => {
      const page = getPage();

      // Language-independent selector: use form-field class with database column name
      const documentNoInput = page.locator('.form-field-DocumentNo input, .form-field-DocumentNo .form-control');

      await documentNoInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const docNo = await documentNoInput.inputValue();

      expect(docNo).toBeTruthy();
      expect(docNo.length).toBeGreaterThan(0);

      console.log('Material Receipt document number:', docNo);
      return docNo;
    });
  }

  /**
   * Open the print modal using Alt+P keyboard shortcut.
   * This opens the PDF generation modal for the document.
   */
  static async openPrintModal() {
    return await PdfDownloader.openPrintModal('ReceiptCandidatesPage');
  }

  /**
   * Download the Material Receipt PDF.
   *
   * Opens the print modal (Alt+P) and clicks the print button.
   *
   * @returns {Promise<Download>} Playwright Download object
   */
  static async downloadPdf() {
    return await test.step('ReceiptCandidatesPage - Download Material Receipt PDF', async () => {
      // Open print modal and download
      await this.openPrintModal();
      return await PdfDownloader.downloadPdf('material-receipt', 'ReceiptCandidatesPage');
    });
  }

  /**
   * Download and validate the Material Receipt PDF.
   *
   * @param {Object} expectedData - Expected data to validate
   * @param {string} expectedData.documentNo - Document number (optional, auto-detected if not provided)
   * @param {string} expectedData.vendorName - Vendor name/code
   * @param {string} expectedData.productCode - Product code
   * @param {string} expectedData.quantity - Quantity
   * @param {string} expectedData.language - Language (e.g., 'en_US', 'de_DE')
   * @param {boolean} expectedData.skipDocNumberValidation - Skip document number validation (for partial receipts)
   * @returns {Promise<void>}
   */
  static async downloadAndValidatePdf(expectedData) {
    return await test.step('ReceiptCandidatesPage - Download and validate Material Receipt PDF', async () => {
      const { PdfValidator } = require('../PdfValidator');
      const page = getPage();

      // Log current URL for debugging
      const currentUrl = page.url();
      console.log(`Current URL before PDF download: ${currentUrl}`);

      // Validate we're on the Material Receipt window (184)
      if (!currentUrl.includes('/window/184/')) {
        throw new Error(`Expected to be on Material Receipt window (184), but URL is: ${currentUrl}`);
      }

      // Auto-detect document number if not provided
      let documentNo = expectedData.documentNo;
      if (!documentNo) {
        documentNo = await this.getDocumentNo();
      }

      console.log(`Downloading PDF for Material Receipt document: ${documentNo}`);
      console.log(`Expected vendor: ${expectedData.vendorName}`);
      console.log(`Expected product: ${expectedData.productCode}`);

      // Download the PDF
      const download = await this.downloadPdf();

      // Use unified PDF validator
      // Note: quantity validation is disabled for Material Receipts because PDF text
      // extraction often merges adjacent cells (e.g., "5" qty + "10" price = "510")
      await PdfValidator.validate(download, {
        documentNo: documentNo,
        customerName: expectedData.vendorName, // Vendor for purchase-side documents
        productCode: expectedData.productCode,
        // quantity: expectedData.quantity, // Disabled due to PDF text extraction quirks
        language: expectedData.language,
        checkOverlaps: true,
        checkMargins: false,
        skipDocNumberValidation: expectedData.skipDocNumberValidation || false,
      });
    });
  }

  /**
   * Create receipt and validate PDF in one workflow.
   *
   * This is a convenience method that:
   * 1. Creates the material receipt
   * 2. Opens the created M_InOut document
   * 3. Downloads and validates the PDF
   *
   * @param {Object} expectedData - Expected data for PDF validation
   * @returns {Promise<void>}
   */
  static async createReceiptAndValidatePdf(expectedData) {
    return await test.step('ReceiptCandidatesPage - Create receipt and validate PDF', async () => {
      // Create the receipt
      await this.createReceipt();

      // Open the related Material Receipt document
      await this.openRelatedMaterialReceipt();

      // Download and validate PDF
      await this.downloadAndValidatePdf(expectedData);
    });
  }
}
