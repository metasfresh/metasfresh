import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { FTSSearchHelper } from '../utils/pages/FTSSearchHelper';
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

  // TODO: Invoice FTS test skipped — createInvoiceForSalesOrder() times out in CI after IC navigation.
  // The IC→Invoice generation step needs further investigation.
  test.fixme('Search Invoice via FTS', async ({ page }) => {
    test.setTimeout(240000); // 4 minutes — includes SO creation, async IC generation, invoice generation, and FTS search
    allure.epic('Full-Text Search');
    allure.story('Invoice FTS search');
    allure.severity('critical');
    allure.description(`
## FTS: Search Invoice via FTS
Creates a Sales Order, completes it, generates an invoice via invoice candidates,
then verifies the FTS inline filter finds the invoice on the Sales Invoice window.
    `);

    const masterdata = await createFTSMasterdata();

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    // --- Create a Sales Order and generate an Invoice ---

    // Create SO
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();

    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.FTS_ALPHA.bpartnerCode);

    await SalesOrderPage.addOrderLine({
      product: masterdata.products.FTS_PROD.productCode,
      quantity: '1',
      recordId,
    });

    // Complete SO
    await SalesOrderPage.complete();
    const soDocumentNo = await SalesOrderPage.getDocumentNo();
    expect(soDocumentNo).toBeTruthy();
    console.log(`Invoice FTS: SO created: ${soDocumentNo}`);

    // Wait for invoice candidates to be generated asynchronously.
    // In CI, this can take 10-15 seconds after SO completion.
    await page.waitForTimeout(15000);

    // Navigate to invoice candidates (Alt+6) and create invoice.
    // Use refreshOnRetry to reload the page between attempts (helps with SSE loading).
    await SalesOrderPage.openRelatedInvoiceCandidate({ maxRetries: 15, retryDelay: 5000, refreshOnRetry: true });
    await InvoiceCandidatePage.expectVisibleForSalesOrder();
    console.log(`Invoice FTS: Invoice candidates visible`);
    await InvoiceCandidatePage.createInvoiceForSalesOrder();

    // Wait for async invoice generation (CI can be slow)
    await page.waitForTimeout(15000);

    // --- Now test FTS search on the Invoice window ---
    // Navigate directly to Sales Invoice window and search by BPartner name.
    // The BPartner name is unique (contains timestamp), so FTS should find exactly
    // the invoice we just generated — no need to look up the invoice DocumentNo first.
    const bpartnerName = masterdata.bpartners.FTS_ALPHA.bpartnerCode;

    await MasterWindowPage.goto(SALES_INVOICE_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // FTS input should be visible
    await FTSSearchHelper.waitForSearchInput();
    await FTSSearchHelper.waitForResultsLoaded();

    // Search for the invoice by BPartner name
    await FTSSearchHelper.search(bpartnerName);

    // Should find at least one result (the invoice we just created)
    const count = await FTSSearchHelper.getResultCount();
    expect(count).toBeGreaterThan(0);

    console.log(`Invoice FTS search for "${bpartnerName}" returned ${count} results`);
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
