import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../WindowIds';
import { assertRecordIsValid } from '../WebAPIValidation';

// Named timing constants for UI interactions
const TAB_SWITCH_DELAY = 500;
const EDIT_MODE_DELAY = 300;
const DROPDOWN_OPEN_DELAY = 300;
const SAVE_CONFIRMATION_DELAY = 500;

// Column field names - these are database column names used in data-testid attributes
// Format in HTML: data-testid="column-{fieldName}"
const COLUMN_FIELDS = {
  PO_PAYMENT_TERM: 'PO_PaymentTerm_ID',
};

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
   * Set the CompanyName field on the current business partner.
   * This is a workaround for Docker images that don't have the fix to set CompanyName
   * automatically when creating BPartners via the frontendTesting API.
   *
   * The CompanyName field is mandatory for vendors - if it's not set, the record is
   * invalid and any changes made via the UI won't be saved.
   *
   * @param {string} companyName - The company name to set
   */
  static async setCompanyName(companyName) {
    return await test.step(`BusinessPartnerPage - Set Company Name: ${companyName}`, async () => {
      const page = getPage();

      // Find the CompanyName field
      const companyNameInput = page.locator('.form-field-CompanyName input');
      await companyNameInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Clear and fill the value
      await companyNameInput.click();
      await companyNameInput.fill(companyName);

      // Tab out to trigger save
      await page.keyboard.press('Tab');
      await page.waitForTimeout(SAVE_CONFIRMATION_DELAY);

      // Wait for save to complete
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      console.log(`Company Name set to: ${companyName}`);
    });
  }

  /**
   * Fix the CompanyName for a business partner (workaround for Docker images without the fix).
   * Navigates to the record, sets CompanyName if missing, then returns.
   *
   * @param {string|number} bpartnerId - The C_BPartner_ID
   * @param {string} companyName - The company name to set
   */
  static async fixCompanyName(bpartnerId, companyName) {
    return await test.step(`BusinessPartnerPage - Fix Company Name for ${bpartnerId}`, async () => {
      await this.gotoRecord(bpartnerId);
      await this.setCompanyName(companyName);
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

      // CRITICAL: Validate the record before modifying
      // If valid=false, changes made in the UI WILL NOT BE SAVED!
      const recordId = BusinessPartnerPage.getRecordId();
      await assertRecordIsValid(BUSINESS_PARTNER_WINDOW_ID.toString(), recordId, 'before setting PO_PaymentTerm_ID');

      // Step 1: Click on the Vendor tab (clickTab already waits for content to load)
      await this.clickTab(TAB_IDS.VENDOR);

      // Step 2: Find the PO Payment Term column using language-independent data-testid
      // The frontend renders column headers with data-testid="column-{fieldName}"
      const columnHeader = page.locator(`th[data-testid="column-${COLUMN_FIELDS.PO_PAYMENT_TERM}"]`);
      const headerExists = await columnHeader.count() > 0;

      if (!headerExists) {
        // Collect available columns for error message
        const allHeaders = page.locator('th[data-testid^="column-"]');
        const headerCount = await allHeaders.count();
        const availableColumns = [];
        for (let i = 0; i < headerCount; i++) {
          const testId = await allHeaders.nth(i).getAttribute('data-testid');
          const text = await allHeaders.nth(i).textContent();
          availableColumns.push(`${testId} ("${text?.trim()}")`);
        }
        throw new Error(
          `PO Payment Term column not found. Looking for data-testid="column-${COLUMN_FIELDS.PO_PAYMENT_TERM}". ` +
          `Available columns: ${availableColumns.join(', ')}`
        );
      }

      // Step 3: Find the column index and corresponding cell
      // Get all headers to determine column position
      const allHeaders = page.locator('th');
      const headerCount = await allHeaders.count();
      let columnIndex = -1;
      for (let i = 0; i < headerCount; i++) {
        const testId = await allHeaders.nth(i).getAttribute('data-testid');
        if (testId === `column-${COLUMN_FIELDS.PO_PAYMENT_TERM}`) {
          columnIndex = i;
          break;
        }
      }

      // Find the data row and corresponding cell
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
      console.log(`Clicked payment term option: ${paymentTermName}`);

      // Step 7: Press Tab to confirm selection and trigger save (NOT Escape, which cancels!)
      await page.waitForTimeout(DROPDOWN_OPEN_DELAY);
      await page.keyboard.press('Tab');
      await page.waitForTimeout(SAVE_CONFIRMATION_DELAY);

      // Wait for save to complete
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Give more time for the save to propagate to the UI
      await page.waitForTimeout(1000);

      // Step 8: Verify the value was saved by checking the table content
      // The table should now show the payment term name
      const tableText = await page.locator('table').textContent().catch(() => '');
      console.log(`Table content (first 600 chars): ${tableText?.substring(0, 600)}`);

      // Check if the payment term name appears in the table content
      const paymentTermSaved = tableText.includes(paymentTermName);

      if (!paymentTermSaved) {
        // Try a shorter match (first 20 chars) in case of truncation
        const shortName = paymentTermName.substring(0, 20);
        const shortMatch = tableText.includes(shortName);

        if (!shortMatch) {
          throw new Error(
            `PO Payment Term "${paymentTermName}" was NOT saved. ` +
            `The value does not appear in the Vendor tab table. ` +
            `Check if the dropdown selection and Tab key triggered a save.`
          );
        }
        console.log(`PO Payment Term verified (partial match: ${shortName})`);
      } else {
        console.log(`PO Payment Term verified in table: ${paymentTermName}`);
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
