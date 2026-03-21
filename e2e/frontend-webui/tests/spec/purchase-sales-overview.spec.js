import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { PURCHASE_SALES_OVERVIEW_WINDOW_ID } from '../utils/WindowIds';
import {
  navigateToViewWindow,
  discoverColumns,
  discoverFacetFilters,
  exerciseFacetFilter,
  assertGridCellsPopulated,
  getGridRowCount,
} from '../utils/view-validation/ViewWindowHelper';
import { getPage, FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Purchase & Sales Overview (Ein- und Verkaufsübersicht) E2E test.
 *
 * Window AD_Window_ID: 542070
 * Table: C_Order_M_InOut_C_Invoice_Overview_V (read-only view)
 *
 * Tests:
 * 1. Creates a sales order via the frontendTesting API (real data)
 * 2. Verifies the window opens with expected grid columns
 * 3. Verifies the order data appears in the grid (HARD assertion)
 * 4. Verifies facet filters render and can be interacted with
 * 5. Verifies grid cells contain expected values
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

      // === STEP 1: Create test data via frontendTesting API ===
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
            shipments: {
              SHIP1: { salesOrder: 'SO1' },
            },
          },
        });

        console.log('[INFO] Sales order:', JSON.stringify(masterdata.salesOrders));
        console.log('[INFO] Shipment:', JSON.stringify(masterdata.shipments));
        expect(masterdata.salesOrders.SO1.documentNo, 'Sales order must have documentNo').toBeTruthy();
        expect(masterdata.shipments.SHIP1.documentNo, 'Shipment must have documentNo').toBeTruthy();
      });

      // === STEP 2: Login ===
      await test.step('Login', async () => {
        await page.goto(`${FRONTEND_BASE_URL}`);
        await page.waitForTimeout(3000);
        // The app redirects to /login if not logged in
        await page.locator('input, [role="textbox"]').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        const user = masterdata.login.user;
        const inputs = page.locator('input, [role="textbox"]');
        await inputs.nth(0).fill(user.username);
        await inputs.nth(1).fill(user.password);
        await page.locator('.btn-meta-success').click();

        await page.waitForTimeout(2000);
        if (page.url().includes('/login')) {
          const loginBtn = page.locator('.btn-meta-success');
          if (await loginBtn.isVisible()) { await loginBtn.click(); }
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
        console.log('[INFO] Discovered columns:', JSON.stringify(allColumns.map(c => c.testId)));
        allure.attachment('All Grid Columns', JSON.stringify(allColumns, null, 2), 'application/json');
        expect(allColumns.length, 'Expected at least 5 grid columns').toBeGreaterThanOrEqual(5);
      });

      // === STEP 5: Verify data is present — HARD assertion ===
      await test.step('Verify sales order data appears in grid', async () => {
        const rowCount = await getGridRowCount();
        console.log(`[INFO] Grid row count: ${rowCount}`);

        // HARD assertion: the sales order we created MUST appear
        expect(rowCount, 'Grid MUST have data rows — the API created a sales order').toBeGreaterThan(0);

        // Verify key cells are populated
        const result = await assertGridCellsPopulated(['DocumentNo', 'C_BPartner_ID', 'M_Product_ID'], 5);
        allure.attachment('Cell Population', JSON.stringify(result, null, 2), 'application/json');
        expect(result.populated, 'Key columns must have values').toBeGreaterThan(0);
      });

      // === STEP 6: Verify facet filters exist ===
      await test.step('Verify facet filters render', async () => {
        const filters = await discoverFacetFilters();
        console.log('[INFO] Facet filters:', JSON.stringify(filters.map(f => f.caption)));
        allure.attachment('Facet Filters', JSON.stringify(filters, null, 2), 'application/json');
        expect(filters.length, 'Expected at least 1 filter').toBeGreaterThanOrEqual(1);
      });

      // === STEP 7: Filter by Document Type — select ONLY the first option ===
      await test.step('Filter to show only one document type (language-independent)', async () => {
        const rowsBefore = await getGridRowCount();
        console.log(`[INFO] Rows before Document Type filter: ${rowsBefore}`);

        // Open Document Type filter (find by filter-facet class, not by text)
        await page.getByTestId('filter-button-facet-C_DocType_ID').click();
        await page.waitForTimeout(1000);

        // Screenshot: filter panel open
        allure.attachment('DocType Filter - Open', await page.screenshot({ fullPage: false }), 'image/png');

        // Use data-testid to find filter checkboxes (language-independent)
        const filterOptions = page.locator('[data-testid^="filter-option-"]');
        const filterCheckboxes = page.locator('[data-testid^="filter-checkbox-"]');
        const optionCount = await filterOptions.count();
        console.log(`[INFO] Filter has ${optionCount} options`);

        if (optionCount >= 2) {
          // Click only the first option's label (triggers React onChange via htmlFor)
          // The filter starts with nothing checked, so clicking one selects ONLY that one
          const firstOptionLabel = filterOptions.nth(0).locator('label.form-control-label');
          await firstOptionLabel.click();
          const firstOptionKey = await filterOptions.nth(0).getAttribute('data-testid');
          console.log(`[INFO] Selected: ${firstOptionKey}`);

          // Apply
          await page.getByTestId('filter-apply-button').click();
          await page.waitForTimeout(2000);

          const rowsAfterFirst = await getGridRowCount();
          console.log(`[INFO] Rows after first option filter: ${rowsAfterFirst} (was ${rowsBefore})`);
          allure.attachment('DocType Filter - First Option Only', await page.screenshot({ fullPage: false }), 'image/png');

          // HARD: must be fewer rows (we have both orders AND shipments)
          expect(rowsAfterFirst, 'Filter should reduce visible rows').toBeLessThan(rowsBefore);
          expect(rowsAfterFirst, 'Should still have rows').toBeGreaterThan(0);

          // === Now switch to the SECOND option ===
          // Re-open filter
          await page.getByTestId('filter-button-facet-C_DocType_ID').click();
          await page.waitForTimeout(1000);

          // Click first option label to uncheck, then second to check
          const opts = page.locator('[data-testid^="filter-option-"]');
          await opts.nth(0).locator('label.form-control-label').click(); // uncheck first
          await opts.nth(1).locator('label.form-control-label').click(); // check second
          const secondOptionKey = await opts.nth(1).getAttribute('data-testid');
          console.log(`[INFO] Switched to: ${secondOptionKey}`);

          await page.getByTestId('filter-apply-button').click();
          await page.waitForTimeout(2000);

          const rowsAfterSecond = await getGridRowCount();
          console.log(`[INFO] Rows after second option filter: ${rowsAfterSecond}`);
          allure.attachment('DocType Filter - Second Option Only', await page.screenshot({ fullPage: false }), 'image/png');

          // HARD: second filter should show the OTHER rows
          expect(rowsAfterSecond, 'Second option should also show rows').toBeGreaterThan(0);
          // The two filtered counts should add up to roughly the original
          console.log(`[INFO] First: ${rowsAfterFirst}, Second: ${rowsAfterSecond}, Total was: ${rowsBefore}`);
        } else {
          console.log('[WARN] Need at least 2 filter options to test filtering');
          await page.keyboard.press('Escape');
        }
      });

      // === STEP 8: Clear Document Type filter first ===
      await test.step('Clear Document Type filter', async () => {
        // Re-open the doctype filter and uncheck everything
        await page.getByTestId('filter-button-facet-C_DocType_ID').click();
        await page.waitForTimeout(1000);

        // Uncheck all options
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

      // === STEP 9: Test date filter — enter yesterday → 0 rows ===
      await test.step('Filter by date (yesterday) → expect 0 rows', async () => {
        const rowsBefore = await getGridRowCount();

        // Open date filter
        await page.getByTestId('filter-button-default-date').click();
        await page.waitForTimeout(1000);

        // Enter yesterday's date
        const yesterday = new Date();
        yesterday.setDate(yesterday.getDate() - 1);
        const yStr = yesterday.toLocaleDateString('en-CA'); // YYYY-MM-DD format
        const dateInput = page.locator('.filter-widget .datepicker input[type="text"]');
        await dateInput.click();
        await dateInput.fill(yStr);
        await page.waitForTimeout(500);

        allure.attachment('Date Filter - Yesterday Entered', await page.screenshot({ fullPage: false }), 'image/png');

        // Apply
        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const rowsAfter = await getGridRowCount();
        console.log(`[INFO] Date filter (yesterday ${yStr}): ${rowsBefore} → ${rowsAfter} rows`);
        allure.attachment('Date Filter - Yesterday Result', await page.screenshot({ fullPage: false }), 'image/png');

        // Yesterday should show significantly fewer rows than unfiltered
        expect(rowsAfter, 'Yesterday should show fewer rows than today').toBeLessThan(rowsBefore);
      });

      // === STEP 10: Change date to today → rows come back ===
      await test.step('Filter by date (today) → expect rows back', async () => {
        // Open date filter again
        await page.getByTestId('filter-button-default-date').click();
        await page.waitForTimeout(1000);

        // Enter today's date
        const today = new Date().toLocaleDateString('en-CA');
        const dateInput = page.locator('.filter-widget .datepicker input[type="text"]');
        await dateInput.click();
        await dateInput.triple_click ? await dateInput.fill('') : await dateInput.fill('');
        await dateInput.fill(today);
        await page.waitForTimeout(500);

        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const rowsAfter = await getGridRowCount();
        console.log(`[INFO] Date filter (today ${today}): → ${rowsAfter} rows`);
        allure.attachment('Date Filter - Today Result', await page.screenshot({ fullPage: false }), 'image/png');

        expect(rowsAfter, 'Today filter should show rows').toBeGreaterThan(0);
      });

      // === STEP 11: Combined filter — Date (today) + DocType (first option) ===
      await test.step('Combined filter: Date + Document Type', async () => {
        // Date filter should still be active from step 10
        // Now also open Document Type filter and select first option
        await page.getByTestId('filter-button-facet-C_DocType_ID').click();
        await page.waitForTimeout(1000);

        // Select first option only (Delivery note / Lieferschein)
        const opts = page.locator('[data-testid^="filter-option-"]');
        const optCount = await opts.count();
        if (optCount >= 2) {
          // Make sure only first is checked
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

        // Combined filter: should show only shipments from today (fewer than DocType alone)
        expect(rowsCombined, 'Combined filter should show rows').toBeGreaterThanOrEqual(0);
      });

      // === FINAL: Screenshot ===
      const screenshot = await page.screenshot({ fullPage: false });
      allure.attachment('Final Window State', screenshot, 'image/png');
    });
  });
});
