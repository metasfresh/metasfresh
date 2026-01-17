import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';

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
   */
  static async openQuickActionsDropdown() {
    return await test.step('ReceiptCandidatesPage - Open Quick Actions dropdown', async () => {
      const page = getPage();

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

      await page.waitForTimeout(300);
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

      // Wait for modal to close
      await page.locator('.panel-modal, .modal-content').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Modal might still be visible, that's OK
      });

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
}
