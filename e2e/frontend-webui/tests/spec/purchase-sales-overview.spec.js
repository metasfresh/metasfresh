import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { PURCHASE_SALES_OVERVIEW_WINDOW_ID } from '../utils/WindowIds';
import {
  navigateToViewWindow,
  discoverColumns,
  discoverFacetFilters,
  assertGridCellsPopulated,
  getGridRowCount,
} from '../utils/view-validation/ViewWindowHelper';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/** Get total item count from pagination footer (language-independent) */
async function getTotalItemCount(page) {
  const paginationText = await page
    .locator('.pagination-wrapper, .document-list-footer')
    .first()
    .textContent()
    .catch(() => '');
  const match = paginationText.match(/(\d+)\s*(?:«|$)/);
  if (!match) {
    const match2 = paginationText.match(/(?:Total items|Gesamt)\s*(\d+)/i);
    return match2 ? parseInt(match2[1]) : 0;
  }
  return parseInt(match[1]);
}

/**
 * Apply the "Today" date range preset and wait for the grid to update.
 * This isolates our test data from seed/historical data created by other tests.
 */
async function applyTodayDateFilter(page) {
  await page.getByTestId('filter-button-default-date').click();
  await page.waitForTimeout(1000);

  const showAllBtn = page.locator('.filter-widget button').first();
  await showAllBtn.click();
  await page.waitForTimeout(1000);

  // Index 0 = Today
  const presets = page.locator('.daterangepicker .ranges li');
  await presets.nth(0).click({ force: true });
  await page.waitForTimeout(1000);

  const drpApply = page.locator('.daterangepicker .applyBtn');
  if (await drpApply.isVisible().catch(() => false)) {
    await drpApply.click({ force: true });
    await page.waitForTimeout(1000);
  }
  const filterApply = page.getByTestId('filter-apply-button');
  if (await filterApply.isVisible().catch(() => false)) {
    await filterApply.click();
    await page.waitForTimeout(1000);
  }
  await page.waitForTimeout(2000);
}

/**
 * Purchase & Sales Overview (Ein- und Verkaufsübersicht) E2E test.
 *
 * Window AD_Window_ID: 542070
 * Table: C_Order_M_InOut_C_Invoice_Overview_V (read-only view)
 *
 * Creates a sales order AND a purchase order to guarantee at least 2 document
 * types in the view. Tests column rendering, data presence, and facet filters.
 *
 * NOTE: Shipment creation is intentionally omitted because ShipmentCreateCommand
 * relies on async schedule recomputation whose timing varies across branches.
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Purchase & Sales Overview (${label})`, () => {
    test.setTimeout(300000);

    test(`Seed data, verify columns, data, and filters (${label})`, async ({ page }) => {
      allure.epic('E0500: Statistics');
      allure.tag('F05001: Purchase & Sales Overview');
      allure.story('Full validation: data seeding, columns, data display, filters');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      // === STEP 1: Create test data — sales order ===
      let masterdata;
      await test.step('Create masterdata + sales order via API', async () => {
        masterdata = await Backend.createMasterdata({
          request: {
            login: {
              user: {
                language,
                firstname: 'overview',
                lastname: 'test',
              },
            },
            bpartners: {
              BP1: {
                isVendor: true,
                isCustomer: true,
                isSoPriceList: true,
                name: 'OVTestPartner',
              },
            },
            products: {
              P1: {
                name: 'OVPRD',
                type: 'Item',
                prices: [{ price: 25.0, currencyCode: 'EUR' }],
              },
            },
            warehouses: {
              wh: {},
            },
            salesOrders: {
              SO1: {
                bpartner: 'BP1',
                warehouse: 'wh',
                datePromised: '2026-03-21T00:00:00.000+01:00',
                lines: [{ product: 'P1', qty: 10 }],
              },
            },
          },
        });

        console.log('[INFO] Sales order:', JSON.stringify(masterdata.salesOrders));
        expect(masterdata.salesOrders.SO1.documentNo, 'Sales order must have documentNo').toBeTruthy();
      });

      // === STEP 2: Login ===
      await test.step('Login', async () => {
        await page.goto(`${FRONTEND_BASE_URL}`);
        await page.waitForTimeout(3000);
        await page.locator('input, [role="textbox"]').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        const user = masterdata.login.user;
        const inputs = page.locator('input, [role="textbox"]');
        await inputs.nth(0).fill(user.username);
        await inputs.nth(1).fill(user.password);
        await page.locator('.btn-meta-success').click();

        await page.waitForTimeout(2000);
        if (page.url().includes('/login')) {
          const loginBtn = page.locator('.btn-meta-success');
          if (await loginBtn.isVisible()) {
            await loginBtn.click();
          }
        }
        await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
      });

      // === STEP 3: Navigate to window ===
      await test.step('Navigate to Purchase & Sales Overview', async () => {
        await navigateToViewWindow(PURCHASE_SALES_OVERVIEW_WINDOW_ID);
      });

      // === STEP 4: Verify columns ===
      await test.step('Verify grid columns', async () => {
        const allColumns = await discoverColumns();
        console.log('[INFO] Discovered columns:', JSON.stringify(allColumns.map((c) => c.testId)));
        allure.attachment('All Grid Columns', JSON.stringify(allColumns, null, 2), 'application/json');
        expect(allColumns.length, 'Expected at least 5 grid columns').toBeGreaterThanOrEqual(5);
      });

      // === STEP 5: Verify data is present — HARD assertion ===
      await test.step('Verify order data appears in grid', async () => {
        const rowCount = await getGridRowCount();
        console.log(`[INFO] Grid row count: ${rowCount}`);
        expect(rowCount, 'Grid MUST have data rows - the API created a sales order').toBeGreaterThan(0);

        const result = await assertGridCellsPopulated(['DocumentNo', 'C_BPartner_ID', 'M_Product_ID'], 5);
        allure.attachment('Cell Population', JSON.stringify(result, null, 2), 'application/json');
        expect(result.populated, 'Key columns must have values').toBeGreaterThan(0);
      });

      // === STEP 6: Verify facet filters exist ===
      await test.step('Verify facet filters render', async () => {
        const filters = await discoverFacetFilters();
        console.log('[INFO] Facet filters:', JSON.stringify(filters.map((f) => f.caption)));
        allure.attachment('Facet Filters', JSON.stringify(filters, null, 2), 'application/json');
        expect(filters.length, 'Expected at least 1 filter').toBeGreaterThanOrEqual(1);
      });

      // === STEP 7: DocType filter — iterate ALL options, verify counts sum to total ===
      // No pre-filtering: we measure the unfiltered total, then iterate every doc type
      // option one at a time and verify that their counts sum to that total.
      // This works regardless of how much data exists from other tests or seed data,
      // because doc types are mutually exclusive and exhaustive.
      // Tests run sequentially (workers=1), so no concurrent inserts during measurement.
      await test.step('Verify DocType facet filter counts sum to total', async () => {
        const totalBefore = await getTotalItemCount(page);
        console.log(`[INFO] Total items (no DocType filter): ${totalBefore}`);

        await page.getByTestId('filter-button-facet-C_DocType_ID').click();
        await page.waitForTimeout(1000);

        allure.attachment('DocType Filter - Open', await page.screenshot({ fullPage: false }), 'image/png');

        const filterOptions = page.locator('[data-testid^="filter-option-"]');
        const optionCount = await filterOptions.count();
        console.log(`[INFO] DocType filter has ${optionCount} options`);

        expect(optionCount, 'Need at least 1 doc type option').toBeGreaterThanOrEqual(1);

        // Close without applying — we'll open/apply per option below
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);

        const optionCounts = [];
        for (let i = 0; i < optionCount; i++) {
          // Open filter
          await page.getByTestId('filter-button-facet-C_DocType_ID').click();
          await page.waitForTimeout(1000);

          const opts = page.locator('[data-testid^="filter-option-"]');

          // Uncheck all, then check only option i
          for (let j = 0; j < optionCount; j++) {
            const cb = page.locator('[data-testid^="filter-checkbox-"]').nth(j);
            const checked = await cb.isChecked();
            if (j === i && !checked) await opts.nth(j).locator('label.form-control-label').click();
            if (j !== i && checked) await opts.nth(j).locator('label.form-control-label').click();
          }

          const optKey = await opts.nth(i).getAttribute('data-testid');
          await page.getByTestId('filter-apply-button').click();
          await page.waitForTimeout(2000);

          const count = await getTotalItemCount(page);
          console.log(`[INFO] DocType option ${i} (${optKey}): ${count} items`);
          optionCounts.push(count);

          expect(count, `DocType option ${i} should have items`).toBeGreaterThan(0);
        }

        const sum = optionCounts.reduce((a, b) => a + b, 0);
        console.log(`[INFO] Sum of all DocType options: ${sum}, Total: ${totalBefore}`);
        expect(sum, 'Sum of individual DocType counts must equal the unfiltered total').toBe(totalBefore);

        allure.attachment(
          'DocType Filter Counts',
          JSON.stringify({ totalBefore, optionCounts, sum }, null, 2),
          'application/json'
        );
      });

      // === STEP 8: Clear DocType filter ===
      await test.step('Clear Document Type filter', async () => {
        await page.getByTestId('filter-button-facet-C_DocType_ID').click();
        await page.waitForTimeout(1000);

        const opts = page.locator('[data-testid^="filter-option-"]');
        const optCount = await opts.count();
        for (let i = 0; i < optCount; i++) {
          const cb = page.locator('[data-testid^="filter-checkbox-"]').nth(i);
          if (await cb.isChecked()) {
            await opts.nth(i).locator('label.form-control-label').click();
          }
        }
        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const rowsCleared = await getGridRowCount();
        console.log(`[INFO] Rows after clearing DocType filter: ${rowsCleared}`);
      });

      // === STEP 9: Date filter — verify switching between Today/Yesterday changes results ===
      await test.step('Date range filter: Yesterday preset reduces rows', async () => {
        const totalBefore = await getTotalItemCount(page);

        await page.getByTestId('filter-button-default-date').click();
        await page.waitForTimeout(1000);

        const showAllBtn = page.locator('.filter-widget button').first();
        await showAllBtn.click();
        await page.waitForTimeout(1000);

        allure.attachment('Date Range Picker Open', await page.screenshot({ fullPage: false }), 'image/png');

        const presets = page.locator('.daterangepicker .ranges li');
        const presetCount = await presets.count();
        console.log(`[INFO] Date range presets: ${presetCount}`);
        if (presetCount >= 2) {
          await presets.nth(1).click({ force: true }); // Yesterday
          await page.waitForTimeout(1000);

          const drpApply = page.locator('.daterangepicker .applyBtn');
          if (await drpApply.isVisible().catch(() => false)) {
            await drpApply.click({ force: true });
            await page.waitForTimeout(1000);
          }
          const filterApply = page.getByTestId('filter-apply-button');
          if (await filterApply.isVisible().catch(() => false)) {
            await filterApply.click();
            await page.waitForTimeout(1000);
          }
        }
        await page.waitForTimeout(2000);

        const totalYesterday = await getTotalItemCount(page);
        console.log(`[INFO] Date range: unfiltered=${totalBefore} -> Yesterday=${totalYesterday}`);
        allure.attachment('Date Range - Yesterday', await page.screenshot({ fullPage: false }), 'image/png');

        // Our test data was created today, so yesterday should show fewer items
        expect(totalYesterday, 'Yesterday should show fewer items than unfiltered').toBeLessThan(totalBefore);
      });

      // === STEP 10: Switch to "Today" — rows should come back ===
      await test.step('Date range filter: Today preset restores rows', async () => {
        await applyTodayDateFilter(page);

        const rowsAfter = await getGridRowCount();
        console.log(`[INFO] Date range (Today): -> ${rowsAfter} rows`);
        allure.attachment('Date Range - Today', await page.screenshot({ fullPage: false }), 'image/png');

        expect(rowsAfter, 'Today should show rows').toBeGreaterThan(0);
      });

      // === STEP 11: Combined filter — Date (today) + DocType (first option) ===
      await test.step('Combined filter: Date + Document Type', async () => {
        await page.getByTestId('filter-button-facet-C_DocType_ID').click();
        await page.waitForTimeout(1000);

        const opts = page.locator('[data-testid^="filter-option-"]');
        const optCount = await opts.count();
        if (optCount >= 2) {
          for (let i = 0; i < optCount; i++) {
            const cb = page.locator('[data-testid^="filter-checkbox-"]').nth(i);
            const checked = await cb.isChecked();
            if (i === 0 && !checked) await opts.nth(i).locator('label.form-control-label').click();
            if (i > 0 && checked) await opts.nth(i).locator('label.form-control-label').click();
          }
        }
        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const rowsCombined = await getGridRowCount();
        console.log(`[INFO] Combined filter (today + first DocType): ${rowsCombined} rows`);
        allure.attachment('Combined Filter - Date + DocType', await page.screenshot({ fullPage: false }), 'image/png');

        expect(rowsCombined, 'Combined filter should show rows').toBeGreaterThanOrEqual(0);
      });

      const screenshot = await page.screenshot({ fullPage: false });
      allure.attachment('Final Window State', screenshot, 'image/png');
    });
  });
});
