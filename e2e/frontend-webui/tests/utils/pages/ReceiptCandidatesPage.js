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
 * Workflow:
 * 1. Complete purchase order in PurchaseOrderPage
 * 2. Use PurchaseOrderPage.openRelatedReceiptCandidate() to navigate (Alt+6)
 * 3. Receipt candidate is already selected with Quick Actions visible
 * 4. Execute Quick Action to open HU Editor modal
 * 5. Wait for receipt creation (HU rows appear)
 * 6. Click "Done" to close modal
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
   * Wait for receipt to be created in the HU Editor modal.
   *
   * After clicking "Create", the button greys out and it takes time for the receipt
   * to be created. When complete, HU rows appear greyed out in the modal.
   */
  static async waitForReceiptCreation() {
    return await test.step('ReceiptCandidatesPage - Wait for receipt creation', async () => {
      const page = getPage();

      // Wait for the processing to complete
      // The HU rows will appear in the modal when receipt is created
      // They will have a greyed-out appearance

      // Option 1: Wait for a specific indicator (e.g., no more spinners)
      await page
        .locator('.rotating, .indicator-pending, .loader')
        .waitFor({
          state: 'detached',
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Might not have visible spinners
        });

      // Option 2: Wait for rows to appear in the HU Editor
      // HU rows typically appear in a table or grid within the modal
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
   * Complete workflow: Create a material receipt from the already-selected candidate (simple approach).
   *
   * Uses the default Quick Action to create the receipt.
   * IMPORTANT: Assumes receipt candidate is already selected via PurchaseOrderPage.openRelatedReceiptCandidate().
   */
  static async createReceipt() {
    return await test.step('ReceiptCandidatesPage - Create receipt from selected candidate', async () => {
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
      await this.selectQuickAction(actionInternalName);
      await this.waitForReceiptCreation();
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
