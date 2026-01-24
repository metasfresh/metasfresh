import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../WindowIds';

// Named timing constants for UI interactions
const TAB_SWITCH_DELAY = 500;
const EDIT_MODE_DELAY = 300;
const DROPDOWN_OPEN_DELAY = 300;
const SAVE_CONFIRMATION_DELAY = 500;

// Language-independent patterns for column headers
// These match both English and German translations
const PO_PAYMENT_TERM_PATTERN = /PO Payment Term|Zahlungskondition.*Einkauf|Zahlungsbedingung/i;

// Tab IDs - language-independent, verified via browser inspection
// These are stable across languages and work in window overrides
const TAB_IDS = {
  VENDOR: 'tab_Vendor',
  CUSTOMER: 'tab_Customer',
  LOCATION: 'tab_C_BPartner_Location',
  CONTACT: 'tab_AD_User',
  BANK_ACCOUNT: 'tab_C_BP_BankAccount',
};

/**
 * Page object for Business Partner window (ID: 123).
 * Handles navigating to business partners and setting purchase payment terms.
 */
export class BusinessPartnerPage {
  /**
   * Navigate to Business Partner window and wait for it to fully load.
   */
  static async goto() {
    return await test.step('BusinessPartnerPage - Navigate to Business Partner window', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);

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

      // Give the window a moment to be ready
      await page.waitForTimeout(500);
    });
  }

  /**
   * Navigate directly to a specific business partner record by ID.
   * @param {string|number} bpartnerId - The C_BPartner_ID
   */
  static async gotoRecord(bpartnerId) {
    return await test.step(`BusinessPartnerPage - Navigate to record ${bpartnerId}`, async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${bpartnerId}`);

      // Wait for document detail view to load
      // Use flexible pattern - window ID may differ in overridden windows
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
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
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      // Give the form time to be ready
      await page.waitForTimeout(1000);
    });
  }

  /**
   * Click on a tab in the Business Partner window by its ID.
   * Uses the language-independent 'id' attribute which is stable across languages and window overrides.
   *
   * @param {string} tabId - The tab ID (use TAB_IDS constants, e.g., TAB_IDS.VENDOR)
   */
  static async clickTab(tabId) {
    return await test.step(`BusinessPartnerPage - Click tab: ${tabId}`, async () => {
      const page = getPage();

      const tab = page.locator(`li.nav-item#${tabId}`);
      const tabExists = await tab.count() > 0;

      if (!tabExists) {
        const allTabs = page.locator('li.nav-item[id^="tab_"]');
        const tabCount = await allTabs.count();
        const availableTabs = [];
        for (let i = 0; i < tabCount; i++) {
          const id = await allTabs.nth(i).getAttribute('id');
          const text = await allTabs.nth(i).textContent();
          availableTabs.push(`${id} ("${text?.trim()}")`);
        }
        throw new Error(
          `Tab not found. Looking for id="${tabId}". ` +
          `Available tabs: ${availableTabs.join(', ')}`
        );
      }

      await tab.click();
      await page.waitForTimeout(TAB_SWITCH_DELAY);
    });
  }

  /**
   * Navigate to a business partner by ID and set PO Payment Term.
   * This is a convenience method that combines gotoRecord and setPOPaymentTerm.
   *
   * @param {string|number} bpartnerId - The C_BPartner_ID
   * @param {string} paymentTermName - Name of the payment term to set (e.g., "30 days net")
   */
  static async setVendorPaymentTerm(bpartnerId, paymentTermName) {
    return await test.step(`BusinessPartnerPage - Set PO payment term for vendor ${bpartnerId}`, async () => {
      await this.gotoRecord(bpartnerId);
      await this.setPOPaymentTerm(paymentTermName);
    });
  }

  /**
   * Search for a business partner by name/code and open the record.
   * @param {string} searchText - Text to search for (name or code)
   */
  static async searchAndOpen(searchText) {
    return await test.step(`BusinessPartnerPage - Search and open: ${searchText}`, async () => {
      const page = getPage();

      // Navigate to the window first
      await this.goto();

      // Use the global search (Ctrl+/ or click search icon)
      // Wait for search input or trigger search
      const searchInput = page.locator('.filter-widget input, .document-filter input').first();
      await searchInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await searchInput.click();
      await searchInput.fill(searchText);

      // Wait for search results
      await page.waitForTimeout(1000);

      // Press Enter to search
      await page.keyboard.press('Enter');

      // Wait for results to load
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore if spinner doesn't exist
        });

      await page.waitForTimeout(500);

      // Double-click the first result row to open
      const firstRow = page.locator('.table tbody tr, table tbody tr').first();
      await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await firstRow.dblclick();

      // Wait for detail view to load
      // Use flexible pattern - window ID may differ in overridden windows
      await page.waitForURL(/\/window\/\d+\/\d+/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Wait for network to settle
      await page
        .waitForLoadState('networkidle', {
          timeout: SLOW_ACTION_TIMEOUT,
        })
        .catch(() => {
          // Ignore timeout
        });

      await page.waitForTimeout(500);
    });
  }

  /**
   * Set the Purchase Payment Term (PO_PaymentTerm_ID) on the current business partner.
   * Uses pure UI interaction: clicks on Vendor tab, double-clicks the cell, selects from dropdown.
   *
   * @param {string} paymentTermName - Name of the payment term to select (e.g., "30 days net")
   */
  static async setPOPaymentTerm(paymentTermName) {
    return await test.step(`BusinessPartnerPage - Set PO Payment Term: ${paymentTermName}`, async () => {
      const page = getPage();

      // Step 1: Click on the Vendor tab (clickTab already waits for content to load)
      await this.clickTab(TAB_IDS.VENDOR);

      // Step 2: Find the PO Payment Term column using language-independent pattern
      const headers = page.locator('th, [role="columnheader"]');
      const headerCount = await headers.count();
      let columnIndex = -1;
      const availableHeaders = [];

      for (let i = 0; i < headerCount; i++) {
        const headerText = await headers.nth(i).textContent();
        availableHeaders.push(headerText);
        if (headerText && PO_PAYMENT_TERM_PATTERN.test(headerText)) {
          columnIndex = i;
          break;
        }
      }

      if (columnIndex === -1) {
        throw new Error(
          `PO Payment Term column not found in Vendor tab. ` +
          `Available headers: ${availableHeaders.join(', ')}`
        );
      }

      // Step 3: Find the data row and the corresponding cell
      // The Vendor tab typically has only one row for the vendor record
      const dataRow = page.locator('tbody tr, [role="rowgroup"] [role="row"]').first();
      await dataRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const cells = dataRow.locator('td, [role="cell"]');
      const poPaymentTermCell = cells.nth(columnIndex);

      // Step 4: Double-click to enter edit mode
      await poPaymentTermCell.dblclick();
      await page.waitForTimeout(EDIT_MODE_DELAY);

      // Verify edit mode by checking for input or dropdown element
      const editInput = poPaymentTermCell.locator('input, select, [role="combobox"], [role="textbox"]');
      await editInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {
          throw new Error('Failed to enter edit mode on PO Payment Term cell');
        });

      // Step 5: Press ArrowDown to open the dropdown
      await page.keyboard.press('ArrowDown');
      await page.waitForTimeout(DROPDOWN_OPEN_DELAY);

      // Verify dropdown is open by checking for listbox or dropdown menu
      const dropdownList = page.locator('[role="listbox"], .input-dropdown-list');
      await dropdownList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {
          throw new Error('Failed to open payment term dropdown');
        });

      // Step 6: Find and click the payment term option
      // Use simple filter by text - more robust than complex attribute selectors
      const paymentTermOption = page.locator('[role="option"], .input-dropdown-list-option')
        .filter({ hasText: paymentTermName });

      const optionCount = await paymentTermOption.count();
      if (optionCount === 0) {
        const allOptions = await page.locator('[role="option"], .input-dropdown-list-option').allTextContents();
        throw new Error(
          `Payment term "${paymentTermName}" not found in dropdown. ` +
          `Available options: ${allOptions.join(', ')}`
        );
      }

      await paymentTermOption.first().click();

      // Step 7: Press Escape to close any remaining dropdown and confirm selection
      await page.keyboard.press('Escape');
      await page.waitForTimeout(SAVE_CONFIRMATION_DELAY);

      // Wait for save to complete
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Verify the value was set by checking the cell text
      const cellText = await poPaymentTermCell.textContent();
      if (!cellText || !cellText.includes(paymentTermName)) {
        console.warn(`Warning: Cell text "${cellText}" does not contain expected value "${paymentTermName}"`);
      }

      console.log(`PO Payment Term set to: ${paymentTermName}`);
    });
  }

  /**
   * Get the current value of the PO Payment Term field.
   * @returns {Promise<string>} The payment term name
   */
  static async getPOPaymentTerm() {
    return await test.step('BusinessPartnerPage - Get PO Payment Term', async () => {
      const page = getPage();

      const poPaymentTermInput = page.locator('#lookup_PO_PaymentTerm_ID input.input-field');
      await poPaymentTermInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const value = await poPaymentTermInput.inputValue();
      return value;
    });
  }

  /**
   * Get the business partner name from the current record.
   * @returns {Promise<string>} The business partner name
   */
  static async getName() {
    return await test.step('BusinessPartnerPage - Get name', async () => {
      const page = getPage();

      // Try CompanyName first (for companies), then Name
      const companyNameInput = page.locator('.form-field-CompanyName input');
      const isCompanyNameVisible = await companyNameInput.isVisible().catch(() => false);

      if (isCompanyNameVisible) {
        const value = await companyNameInput.inputValue();
        if (value) return value;
      }

      // Fall back to Name field
      const nameInput = page.locator('.form-field-Name input');
      await nameInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const value = await nameInput.inputValue();

      expect(value).toBeTruthy();
      return value;
    });
  }

  /**
   * Get the record ID from the current URL.
   * @returns {string} Record ID (C_BPartner_ID)
   */
  static getRecordId() {
    const page = getPage();
    const url = page.url();
    const recordId = url.split('/').pop();
    return recordId;
  }

  /**
   * Tab IDs for use with clickTab().
   * These are language-independent and stable across window overrides.
   */
  static TAB_IDS = TAB_IDS;
}
