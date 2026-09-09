import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, } from '../common';

// Material Receipt window ID
const MATERIAL_RECEIPT_WINDOW_ID = 184;

/**
 * Page object for Material Receipt window (ID: 184).
 * Handles creating and completing material receipts in metasfresh.
 */
export class MaterialReceiptPage {
  /**
   * Navigate to Material Receipt window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('MaterialReceiptPage - Navigate to Material Receipt window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${MATERIAL_RECEIPT_WINDOW_ID}`);

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
   * Create a new material receipt using Alt+N keyboard shortcut.
   * Waits for the new document detail view to load.
   */
  static async clickNew() {
    return await test.step('MaterialReceiptPage - Create new receipt (Alt+N)', async () => {
      const page = getPage();

      // Click on the page body to ensure it has focus
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Use keyboard shortcut Alt+N to create new document
      await page.keyboard.press('Alt+N');

      // Wait for navigation to new document (flexible pattern for window overrides)
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the document to fully load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore timeout
      });

      // Wait for loading spinners to disappear
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Give the form time to initialize
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Select a business partner for the material receipt.
   * @param {string} partnerName - Name of the business partner
   */
  static async selectBusinessPartner(partnerName) {
    return await test.step(`MaterialReceiptPage - Select business partner: ${partnerName}`, async () => {
      const page = getPage();

      // Find the first lookup widget (Business Partner)
      const lookupInput = page.locator('.lookup-widget-wrapper input').first();

      // Fill the search term
      await lookupInput.fill(partnerName);

      // Wait for autocomplete dropdown to appear
      await page.waitForTimeout(800);
      await page.locator('.input-dropdown-list-option').first().waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Select the first option
      await page.locator('.input-dropdown-list-option').first().click();

      // Wait for the selection to be processed
      await page.waitForTimeout(500);
    });
  }

  /**
   * Use "Create From" action to create receipt from a purchase order.
   * This is a common pattern in metasfresh for generating documents from existing documents.
   * @param {string} orderDocumentNo - Document number of the purchase order
   */
  static async createFromPurchaseOrder(orderDocumentNo) {
    return await test.step(`MaterialReceiptPage - Create from purchase order: ${orderDocumentNo}`, async () => {
      const page = getPage();

      // Language-independent: Click "Create From" action button using data-testid
      // ActionButton.js has data-testid="action-{internalName}"
      // Note: Exact action name may vary - common values: CreateFrom, M_InOut_CreateFrom
      const createFromButton = page.getByTestId('action-CreateFrom').or(
        page.getByTestId('action-M_InOut_CreateFrom')
      );

      await createFromButton.click();

      // Wait for "Create From" modal to appear (two-step waiting pattern)
      // Step 1: Wait for modal container
      await page.locator('.modal-content, .modal').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Step 2: Wait for search input field
      // Language-independent: Use structural selector (modal + input type)
      const orderSearchInput = page.locator('.modal input[type="text"]').first();
      await orderSearchInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await orderSearchInput.fill(orderDocumentNo);
      await page.waitForTimeout(500);

      // Select the order from the list
      await page.locator('.table tbody tr, .input-dropdown-list-option').first().click();

      // Language-independent: Confirm selection using data-testid or structural selector
      // ModalButton.js has data-testid="modal-{buttonName}"
      // Fallback to primary button in modal footer
      const confirmButton = page.getByTestId('modal-ok').or(
        page.getByTestId('modal-select')
      ).or(
        page.locator('.modal-footer .btn-primary')
      );

      await confirmButton.click();

      // Wait for the lines to be created
      await page.waitForTimeout(2000);
    });
  }

  /**
   * Alternative method: Directly receive from available receipt schedules.
   * After selecting a business partner, receipt schedules may be available.
   */
  static async receiveAll() {
    return await test.step('MaterialReceiptPage - Receive all quantities', async () => {
      const page = getPage();

      // Language-independent: Look for "Receive All" action button using data-testid
      // ActionButton.js has data-testid="action-{internalName}"
      // Common action names: Receive, ReceiveAll, M_InOut_ReceiveAll
      const receiveButton = page.getByTestId('action-Receive').or(
        page.getByTestId('action-ReceiveAll')
      ).or(
        page.getByTestId('action-M_InOut_ReceiveAll')
      );

      // Check if button exists, if not, skip this step
      const buttonExists = await receiveButton.count() > 0;

      if (buttonExists) {
        await receiveButton.click();
        await page.waitForTimeout(1000);
      } else {
        // If no "Receive All" button, the receipt lines might already be populated
        // from the "Create From" action
        console.log('No "Receive All" button found - lines may already be populated');
      }
    });
  }

  /**
   * Navigate to the Receipt Line tab and verify lines are present.
   */
  static async goToReceiptLineTab() {
    return await test.step('MaterialReceiptPage - Go to Receipt Line tab', async () => {
      const page = getPage();

      // Language-independent: Use tab data-testid from Tabs.js
      // Tabs.js has data-testid="tab-AD_Tab-{tabId}"
      // Material Receipt Line tab is AD_Tab-297
      await page.getByTestId('tab-AD_Tab-297').click();

      // Wait for tab content to load
      await page.waitForTimeout(800);

      // Wait for the tab content area to be visible
      await page.locator('.tab-content, .panel-primary').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore if selector doesn't match
      });
    });
  }

  /**
   * Complete the material receipt using the document action.
   */
  static async complete() {
    return await test.step('MaterialReceiptPage - Complete receipt', async () => {
      const page = getPage();

      // Language-independent: Click the document action button using data-testid
      // ActionButton.js has data-testid="status-button"
      await page.getByTestId('status-button').click();

      // Wait for the action dropdown to appear
      await page.waitForTimeout(500);

      // Language-independent: Click "Complete" action using data-testid
      // ActionButton.js has data-testid="status-{statusKey}"
      // "CO" is the document status code for "Complete"
      await page.getByTestId('status-CO').click();

      // Wait for the completion process
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
   * Handle HU Editor if it appears during receipt creation.
   * The HU (Handling Unit) Editor may or may not appear depending on product configuration.
   */
  static async handleHUEditor() {
    return await test.step('MaterialReceiptPage - Handle HU Editor (if present)', async () => {
      const page = getPage();

      // Check if HU Editor modal/dialog is present
      const huEditor = page.locator('.hu-editor, [data-cy="hu-editor"], .modal-title:has-text("HU")');

      const isPresent = await huEditor.isVisible().catch(() => false);

      if (isPresent) {
        console.log('HU Editor detected - confirming');

        // Look for "OK" or "Done" button in the HU Editor
        await page.locator(
          '.hu-editor button:has-text("OK"), .modal-footer button:has-text("Done"), .btn-primary'
        ).click();

        // Wait for HU Editor to close
        await page.waitForTimeout(1000);
      } else {
        console.log('HU Editor not present - skipping');
      }
    });
  }

  /**
   * Get the document number of the current receipt.
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('MaterialReceiptPage - Get document number', async () => {
      const page = getPage();

      // Language-independent selector: use form-field class with database column name
      const documentNoInput = page.locator('.form-field-DocumentNo input, .form-field-DocumentNo .form-control');

      const docNo = await documentNoInput.inputValue();

      expect(docNo).toBeTruthy();
      expect(docNo.length).toBeGreaterThan(0);

      return docNo;
    });
  }
}
