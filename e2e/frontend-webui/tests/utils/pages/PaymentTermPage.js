import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { PAYMENT_TERM_WINDOW_ID } from '../WindowIds';

/**
 * Page object for Payment Term window (ID: 141).
 * Handles creating payment terms with discount configuration in metasfresh.
 */
export class PaymentTermPage {
  /**
   * Navigate to Payment Term window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('PaymentTermPage - Navigate to Payment Term window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${PAYMENT_TERM_WINDOW_ID}`);

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
   * Create a new payment term using Alt+N keyboard shortcut.
   * Waits for the new document detail view to load.
   */
  static async clickNew() {
    return await test.step('PaymentTermPage - Create new payment term (Alt+N)', async () => {
      const page = getPage();

      // Click on the page body to ensure it has focus
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Use keyboard shortcut Alt+N to create new document
      await page.keyboard.press('Alt+N');

      // Wait for navigation to new document (URL pattern: /window/141/{documentId})
      await page.waitForURL(/\/window\/141\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for the document to fully load
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore timeout - some pages may not reach networkidle
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
   * Fill in the payment term fields.
   * Language-independent: Uses database column names for selectors.
   *
   * @param {Object} data - Payment term data
   * @param {string} data.value - Search key (unique identifier)
   * @param {string} data.name - Display name
   * @param {number} data.discount - Discount percentage (e.g., 5 for 5%)
   * @param {number} data.discountDays - Days within which discount applies (e.g., 10)
   */
  static async fillPaymentTerm({ value, name, discount, discountDays }) {
    return await test.step(`PaymentTermPage - Fill payment term: ${name}`, async () => {
      const page = getPage();

      // Fill Value (Search Key) field
      // Language-independent selector: use form-field class with database column name
      const valueInput = page.locator('.form-field-Value input');
      await valueInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await valueInput.click();
      await valueInput.fill(value);
      await page.keyboard.press('Tab');
      await page.waitForTimeout(300);

      // Fill Name field
      const nameInput = page.locator('.form-field-Name input');
      await nameInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await nameInput.click();
      await nameInput.fill(name);
      await page.keyboard.press('Tab');
      await page.waitForTimeout(300);

      // Fill Discount field (percentage)
      const discountInput = page.locator('.form-field-Discount input');
      await discountInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await discountInput.click();
      await discountInput.fill(discount.toString());
      await page.keyboard.press('Tab');
      await page.waitForTimeout(300);

      // Fill DiscountDays field
      const discountDaysInput = page.locator('.form-field-DiscountDays input');
      await discountDaysInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await discountDaysInput.click();
      await discountDaysInput.fill(discountDays.toString());
      await page.keyboard.press('Tab');
      await page.waitForTimeout(300);

      // Wait for auto-save (metasfresh saves on field blur)
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Wait for save to complete
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Create a payment term with early payment discount.
   * Combines navigation, new record creation, and field filling.
   *
   * @param {Object} data - Payment term data
   * @param {string} data.name - Display name (will be used for both Value and Name)
   * @param {number} data.discount - Discount percentage (default: 5)
   * @param {number} data.discountDays - Discount days (default: 10)
   * @returns {Promise<Object>} Object with paymentTermId and name
   */
  static async createPaymentTerm({ name, discount = 5, discountDays = 10 }) {
    return await test.step(`PaymentTermPage - Create payment term: ${name}`, async () => {
      const page = getPage();

      // Navigate to Payment Term window
      await this.goto();

      // Create new record
      await this.clickNew();

      // Fill in the fields
      await this.fillPaymentTerm({
        value: name, // Use name as value for uniqueness
        name: name,
        discount: discount,
        discountDays: discountDays,
      });

      // Get the record ID
      const recordId = this.getRecordId();

      console.log(`Payment Term created: ${name} (ID: ${recordId})`);

      return {
        paymentTermId: recordId,
        name: name,
        discount: discount,
        discountDays: discountDays,
      };
    });
  }

  /**
   * Get the name of the current payment term.
   * Language-independent: Uses database field name.
   * @returns {Promise<string>} The payment term name
   */
  static async getName() {
    return await test.step('PaymentTermPage - Get payment term name', async () => {
      const page = getPage();

      const nameInput = page.locator('.form-field-Name input');
      const name = await nameInput.inputValue();

      expect(name).toBeTruthy();
      return name;
    });
  }
}
