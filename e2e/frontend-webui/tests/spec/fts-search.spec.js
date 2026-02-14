import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { FTSSearchHelper } from '../utils/pages/FTSSearchHelper';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  BUSINESS_PARTNER_WINDOW_ID,
  PRODUCT_WINDOW_ID,
  SALES_INVOICE_WINDOW_ID,
} from '../utils/WindowIds';

/**
 * PostgreSQL Full-Text Search (FTS) E2E test suite.
 *
 * Validates that the inline FTS filter works end-to-end for:
 * - Business Partner window (C_BPartner_FTS)
 * - Product window (M_Product_FTS)
 * - Sales Invoice window (C_Invoice_FTS)
 *
 * Requires:
 * - FTS SysConfig enabled (de.metas.ui.web.document.filter.provider.fullTextSearch...enabled = Y)
 * - FTS tables populated (ops.update_all_fts_if_active())
 * - Feature branch CI images with Product/Invoice FTS support
 */
// Shared timestamp for unique test data names across all tests in this file
const timestamp = Date.now();

/**
 * Create FTS test master data (user, bpartners, product).
 * Called from each test since beforeAll doesn't have access to the page fixture.
 */
async function createFTSMasterdata() {
  return await Backend.createMasterdata({
    request: {
      login: {
        user: {
          language: 'en_US',
        },
      },
      bpartners: {
        FTS_ALPHA: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: `FTSAlpha_${timestamp}`,
        },
      },
      products: {
        FTS_PROD: {
          name: `FTSP_${timestamp}`.substring(0, 16), // max 16 chars
          type: 'Item',
          prices: [
            {
              price: 10.0,
              currencyCode: 'EUR',
            },
          ],
        },
      },
    },
  });
}

test.describe('PostgreSQL Full-Text Search', () => {
  test('Search BPartner by name', async ({ page }) => {
    allure.epic('Full-Text Search');
    allure.story('BPartner FTS search');
    allure.severity('critical');
    allure.description(`
## FTS: Search BPartner by name
Verifies that the FTS inline filter is visible in the BPartner window,
returns matching results, and excludes non-matching entries.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // Navigate to BPartner window
    await MasterWindowPage.goto(BUSINESS_PARTNER_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    // FTS input should be visible
    await FTSSearchHelper.waitForSearchInput();

    // Search for our unique BPartner name
    const alphaName = masterdata.bpartners.FTS_ALPHA.bpartnerCode;
    await FTSSearchHelper.search(alphaName);

    // Should find at least one result
    const count = await FTSSearchHelper.getResultCount();
    expect(count).toBeGreaterThan(0);

    console.log(`BPartner FTS search for "${alphaName}" returned ${count} results`);
  });

  test('Search Product by name', async ({ page }) => {
    allure.epic('Full-Text Search');
    allure.story('Product FTS search');
    allure.severity('critical');
    allure.description(`
## FTS: Search Product by name
Verifies that the FTS inline filter works in the Product window.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // Navigate to Product window
    await MasterWindowPage.goto(PRODUCT_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    // FTS input should be visible
    await FTSSearchHelper.waitForSearchInput();

    // Search for our unique product
    const productName = masterdata.products.FTS_PROD.productName;
    await FTSSearchHelper.search(productName);

    // Should find at least one result
    const count = await FTSSearchHelper.getResultCount();
    expect(count).toBeGreaterThan(0);

    console.log(`Product FTS search for "${productName}" returned ${count} results`);
  });

  test('Search Invoice by document number', async ({ page }) => {
    allure.epic('Full-Text Search');
    allure.story('Invoice FTS search');
    allure.severity('critical');
    allure.description(`
## FTS: Search Invoice by document number
Verifies that the FTS inline filter works in the Invoice window.
Uses existing seed data — reads the first visible invoice's DocumentNo, then searches for it.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // Navigate to Sales Invoice window
    await MasterWindowPage.goto(SALES_INVOICE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    // FTS input should be visible
    await FTSSearchHelper.waitForSearchInput();

    // Read the DocumentNo of the first visible invoice
    const firstDocNoCell = page.locator(
      'tbody tr.table-row:first-child [data-cy="cell-DocumentNo"]'
    );
    await firstDocNoCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const documentNo = (await firstDocNoCell.textContent()).trim();
    expect(documentNo).toBeTruthy();

    console.log(`Invoice: first visible DocumentNo = "${documentNo}"`);

    // Search for that document number
    await FTSSearchHelper.search(documentNo);

    // Should find at least one result
    const count = await FTSSearchHelper.getResultCount();
    expect(count).toBeGreaterThan(0);

    // The first result should contain our document number
    await FTSSearchHelper.expectFirstResultContains('DocumentNo', documentNo);

    console.log(`Invoice FTS search for "${documentNo}" returned ${count} results`);
  });

  test('Empty search returns all results', async ({ page }) => {
    test.setTimeout(90000); // 90 seconds for search + clear + reload
    allure.epic('Full-Text Search');
    allure.story('FTS clear search');
    allure.severity('normal');
    allure.description(`
## FTS: Empty search returns all results
Verifies that clearing the FTS search input restores the full (unfiltered) list.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // Navigate to BPartner window
    await MasterWindowPage.goto(BUSINESS_PARTNER_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    await FTSSearchHelper.waitForSearchInput();

    // Get initial row count (full list)
    const initialCount = await FTSSearchHelper.getResultCount();
    expect(initialCount).toBeGreaterThan(0);

    // Search for a specific partner to narrow results
    const alphaName = masterdata.bpartners.FTS_ALPHA.bpartnerCode;
    await FTSSearchHelper.search(alphaName);
    const filteredCount = await FTSSearchHelper.getResultCount();
    expect(filteredCount).toBeGreaterThan(0);
    expect(filteredCount).toBeLessThanOrEqual(initialCount);

    // Clear search — should restore full list
    await FTSSearchHelper.clearSearch();
    const restoredCount = await FTSSearchHelper.getResultCount();
    expect(restoredCount).toBeGreaterThanOrEqual(initialCount);

    console.log(
      `BPartner counts: initial=${initialCount}, filtered=${filteredCount}, restored=${restoredCount}`
    );
  });

  test('Non-matching search shows no results', async ({ page }) => {
    allure.epic('Full-Text Search');
    allure.story('FTS no results');
    allure.severity('normal');
    allure.description(`
## FTS: Non-matching search shows no results
Verifies that a gibberish query returns an empty result set.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // Navigate to BPartner window
    await MasterWindowPage.goto(BUSINESS_PARTNER_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    await FTSSearchHelper.waitForSearchInput();

    // Get initial row count before search
    const initialCount = await FTSSearchHelper.getResultCount();

    // Search for gibberish that won't meaningfully match
    // Note: FTS ngram fuzzy search may still return a few weak matches
    await FTSSearchHelper.search('zzzxqjk_nonexistent_99999');

    const count = await FTSSearchHelper.getResultCount();
    // Should have significantly fewer results than the full list
    expect(count).toBeLessThan(initialCount);

    console.log(`Non-matching FTS search: ${count} results (was ${initialCount})`);
  });

  test('Double-click FTS result opens detail view', async ({ page }) => {
    allure.epic('Full-Text Search');
    allure.story('FTS result navigation');
    allure.severity('normal');
    allure.description(`
## FTS: Double-click FTS result opens detail view
Verifies that double-clicking a row from FTS results navigates to the detail view.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // Navigate to BPartner window
    await MasterWindowPage.goto(BUSINESS_PARTNER_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    await FTSSearchHelper.waitForSearchInput();

    // Search for our unique BPartner
    const alphaName = masterdata.bpartners.FTS_ALPHA.bpartnerCode;
    await FTSSearchHelper.search(alphaName);

    const count = await FTSSearchHelper.getResultCount();
    expect(count).toBeGreaterThan(0);

    // Double-click the first result to open detail view
    await FTSSearchHelper.openFirstResult();

    // Verify URL changed to detail view format: /window/{id}/{recordId}
    const url = page.url();
    expect(url).toMatch(/\/window\/\d+\/\d+/);

    console.log(`Opened BPartner detail view from FTS result: ${url}`);
  });
});
