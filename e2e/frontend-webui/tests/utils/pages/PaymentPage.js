import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { PAYMENT_WINDOW_ID } from '../WindowIds';

/**
 * Page object for Payment window (ID: 195).
 * Handles creating vendor payments with discount amounts in metasfresh.
 */
export class PaymentPage {
  /**
   * Navigate to Payment window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('PaymentPage - Navigate to Payment window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${PAYMENT_WINDOW_ID}`);

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
   * Create a new payment using Alt+N keyboard shortcut.
   * Waits for the new document detail view to load.
   */
  static async clickNew() {
    return await test.step('PaymentPage - Create new payment (Alt+N)', async () => {
      const page = getPage();

      // Click on the page body to ensure it has focus
      await page.locator('body').click();
      await page.waitForTimeout(200);

      // Use keyboard shortcut Alt+N to create new document
      await page.keyboard.press('Alt+N');

      // Wait for navigation to new document (URL pattern: /window/195/{documentId})
      await page.waitForURL(/\/window\/195\/\d+/, {
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
   * Select the business partner (vendor) for the payment.
   * Language-independent: Uses database column name C_BPartner_ID.
   * @param {string} partnerName - Name of the business partner to search for
   */
  static async selectBusinessPartner(partnerName) {
    return await test.step(`PaymentPage - Select business partner: ${partnerName}`, async () => {
      const page = getPage();

      const bpartnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
      await bpartnerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await bpartnerInput.click();

      // Wait for initial loading spinner to disappear
      await page.locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {
        // Ignore if no spinner exists
      });

      await page.waitForTimeout(300);
      await bpartnerInput.fill(partnerName);
      await page.waitForTimeout(500);

      // Wait for dropdown to populate
      await page.locator('#lookup_C_BPartner_ID .rotating, #lookup_C_BPartner_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(300);

      // Click the option by text
      await page.locator('.input-dropdown-list-option').getByText(partnerName).first().click();

      // Wait for dropdown to close
      await page.locator('.input-dropdown-list').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Trigger blur by pressing Tab
      await page.keyboard.press('Tab');

      // Wait for save operations to complete
      await page.waitForTimeout(500);
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(1000);
    });
  }

  /**
   * Set the payment amount.
   * Language-independent: Uses database column name PayAmt.
   * @param {number} amount - Payment amount
   */
  static async setPaymentAmount(amount) {
    return await test.step(`PaymentPage - Set payment amount: ${amount}`, async () => {
      const page = getPage();

      const payAmtInput = page.locator('.form-field-PayAmt input');
      await payAmtInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await payAmtInput.click();
      await payAmtInput.fill(amount.toString());
      await page.keyboard.press('Tab');

      // Wait for save
      await page.waitForTimeout(500);
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});
    });
  }

  /**
   * Select the invoice to pay.
   * Language-independent: Uses database column name C_Invoice_ID.
   * @param {string} invoiceDocNo - Invoice document number to search for
   */
  static async selectInvoice(invoiceDocNo) {
    return await test.step(`PaymentPage - Select invoice: ${invoiceDocNo}`, async () => {
      const page = getPage();

      const invoiceInput = page.locator('#lookup_C_Invoice_ID input.input-field');
      await invoiceInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await invoiceInput.click();

      // Wait for initial loading spinner to disappear
      await page.locator('#lookup_C_Invoice_ID .rotating, #lookup_C_Invoice_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(300);
      await invoiceInput.fill(invoiceDocNo);
      await page.waitForTimeout(500);

      // Wait for dropdown to populate
      await page.locator('#lookup_C_Invoice_ID .rotating, #lookup_C_Invoice_ID .spinner').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(300);

      // Click the option by text
      await page.locator('.input-dropdown-list-option').getByText(invoiceDocNo).first().click();

      // Wait for dropdown to close
      await page.locator('.input-dropdown-list').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Trigger blur by pressing Tab
      await page.keyboard.press('Tab');

      // Wait for save operations to complete
      await page.waitForTimeout(500);
      await page.locator('.rotating, .indicator-pending').waitFor({
        state: 'detached',
        timeout: SLOW_ACTION_TIMEOUT,
      }).catch(() => {});

      await page.waitForTimeout(1000);
    });
  }

  /**
   * Complete the payment using the document action.
   * Clicks the status button and selects "Complete" from the dropdown.
   */
  static async complete() {
    return await test.step('PaymentPage - Complete payment', async () => {
      const page = getPage();

      // Click the document status button (e.g., "Drafted")
      await page.getByTestId('status-button').click();

      // Wait for the action dropdown to appear
      await page.waitForTimeout(500);

      // Click "Complete" action (CO = Complete document action key)
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
        .catch(() => {});
    });
  }

  /**
   * Get the document number of the current payment.
   * @returns {Promise<string>} The document number
   */
  static async getDocumentNo() {
    return await test.step('PaymentPage - Get document number', async () => {
      const page = getPage();

      const documentNoInput = page.locator('.form-field-DocumentNo input, .form-field-DocumentNo .form-control');
      const docNo = await documentNoInput.inputValue();

      expect(docNo).toBeTruthy();
      return docNo;
    });
  }

  /**
   * Select the document type for the payment.
   * For vendor payments, this should be set to "AP Payment" or equivalent.
   * @param {string} [docTypeName] - Optional specific document type name
   */
  static async selectDocumentType(docTypeName) {
    return await test.step(`PaymentPage - Select document type`, async () => {
      const page = getPage();

      // Document Type field is a combobox/dropdown (not a lookup field)
      // Try multiple selector patterns for the dropdown
      const docTypeSelectors = [
        // Combobox pattern with data-field-name
        '[data-field-name="C_DocType_ID"]',
        '.form-field-C_DocType_ID',
        // List widget pattern
        '#list_C_DocType_ID',
        // Generic dropdown pattern
        '[id*="C_DocType"] .input-dropdown',
        '[id*="DocType"] .input-dropdown',
      ];

      let docTypeField = null;
      for (const selector of docTypeSelectors) {
        const field = page.locator(selector);
        const count = await field.count();
        if (count > 0) {
          console.log(`Found Document Type field with selector: ${selector}`);
          docTypeField = field;
          break;
        }
      }

      if (!docTypeField) {
        // Try clicking on the "none" text which is the current value
        const noneText = page.locator('.form-group:has-text("Document Type") .input-dropdown, .form-group:has-text("Document Type") select, .input-dropdown:has-text("none")').first();
        const noneExists = await noneText.count() > 0;
        if (noneExists) {
          console.log('Found Document Type via none text');
          docTypeField = noneText;
        }
      }

      if (docTypeField) {
        // Click the dropdown to open it
        await docTypeField.click();
        await page.waitForTimeout(500);

        // Wait for dropdown options to appear
        await page.locator('.input-dropdown-list, .select-dropdown, select option').waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        }).catch(() => {});

        // Click the first available option (AP Payment for vendor)
        const optionSelectors = [
          '.input-dropdown-list-option',
          '.select-option',
          'option',
        ];

        for (const optSelector of optionSelectors) {
          const options = page.locator(optSelector);
          const optCount = await options.count();
          if (optCount > 0) {
            // Click the first option
            await options.first().click();
            await page.waitForTimeout(500);
            console.log(`Selected document type using ${optSelector}`);
            break;
          }
        }

        // Wait for save
        await page.locator('.rotating, .indicator-pending').waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        }).catch(() => {});
      } else {
        console.log('Document Type field not found with any selector');
        // List all form fields for debugging
        const formFields = await page.locator('.form-group, [class*="form-field"]').evaluateAll(
          elements => elements.slice(0, 10).map(e => ({
            classes: e.className,
            id: e.id,
            text: e.innerText?.substring(0, 50)
          }))
        );
        console.log('Form fields found:', JSON.stringify(formFields, null, 2));
      }
    });
  }

  /**
   * Create a complete vendor payment with discount.
   * Combines all steps: navigate, create, fill, and complete.
   *
   * @param {Object} data - Payment data
   * @param {string} data.vendorName - Name of the vendor
   * @param {string} data.invoiceDocNo - Invoice document number
   * @param {number} data.paymentAmount - Payment amount (invoice total - discount)
   * @returns {Promise<Object>} Object with paymentId and documentNo
   */
  static async createVendorPayment({ vendorName, invoiceDocNo, paymentAmount }) {
    return await test.step(`PaymentPage - Create vendor payment: ${paymentAmount} for ${invoiceDocNo}`, async () => {
      // Navigate to Payment window
      await this.goto();

      // Create new payment
      await this.clickNew();

      // Select document type first (required for AP payments)
      await this.selectDocumentType();

      // Select vendor
      await this.selectBusinessPartner(vendorName);

      // Select invoice
      await this.selectInvoice(invoiceDocNo);

      // Set payment amount
      await this.setPaymentAmount(paymentAmount);

      // Complete the payment
      await this.complete();

      // Get the record ID and document number
      const paymentId = this.getRecordId();
      const documentNo = await this.getDocumentNo();

      console.log(`Vendor Payment created: ${documentNo} (ID: ${paymentId}), Amount: ${paymentAmount}`);

      return {
        paymentId: paymentId,
        documentNo: documentNo,
        paymentAmount: paymentAmount,
      };
    });
  }
}
