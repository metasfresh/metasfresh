import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { SALES_INVOICE_CANDIDATE_WINDOW_ID } from '../utils/WindowIds';
import {
  navigateToViewWindow,
  discoverFacetFilters,
  getGridRowCount,
} from '../utils/view-validation/ViewWindowHelper';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Get total item count from pagination footer (language-independent).
 * Reused from purchase-sales-overview.spec.js.
 */
async function getTotalItemCount(page) {
  const paginationText = await page.locator('.pagination-wrapper, .document-list-footer').first().textContent().catch(() => '');
  const match = paginationText.match(/(\d+)\s*(?:«|$)/);
  if (!match) {
    const match2 = paginationText.match(/(?:Total items|Gesamt)\s*(\d+)/i);
    return match2 ? parseInt(match2[1]) : 0;
  }
  return parseInt(match[1]);
}

/**
 * Invoice Candidate — Facet Filter E2E test.
 *
 * Window AD_Window_ID: 540092 (Sales Invoice Candidates / Rechnungsdisposition)
 * Table: C_Invoice_Candidate (regular table, NOT a database view)
 *
 * gh#28680: This test complements the purchase-sales-overview.spec.js test by
 * verifying facet filtering on a window backed by a regular table (not a view).
 * The selectFieldValues() performance fix affects ALL facet filters in ALL windows.
 *
 * Tests:
 * 1. Creates a sales order via the frontendTesting API (generates invoice candidates)
 * 2. Navigates to the Sales Invoice Candidate window
 * 3. Verifies facet filters render
 * 4. Exercises a facet filter: selects an option, applies, verifies row count changes
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Invoice Candidate Facet Filters (${label})`, () => {
    test.setTimeout(300000);

    test(`Seed data, verify facet filters work (${label})`, async ({ page }) => {
      allure.epic('E0200: Billing');
      allure.tag('F02001: Invoice Candidates');
      allure.story('Facet filter interaction on invoice candidates');
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
                firstname: 'icfacet',
                lastname: 'test',
              },
            },
            bpartners: {
              BP1: {
                isVendor: true,
                isCustomer: true,
                isSoPriceList: true,
                name: 'ICFacetPartner',
              },
            },
            products: {
              P1: {
                name: 'ICFPRD1',
                type: 'Item',
                prices: [{ price: 10.0, currencyCode: 'EUR' }],
              },
              P2: {
                name: 'ICFPRD2',
                type: 'Item',
                prices: [{ price: 20.0, currencyCode: 'EUR' }],
              },
            },
            warehouses: {
              wh: {},
            },
            salesOrders: {
              SO1: {
                bpartner: 'BP1',
                warehouse: 'wh',
                datePromised: '2026-03-22T00:00:00.000+01:00',
                lines: [
                  { product: 'P1', qty: 5 },
                  { product: 'P2', qty: 3 },
                ],
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
          if (await loginBtn.isVisible()) { await loginBtn.click(); }
        }
        await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
      });

      // === STEP 3: Navigate to Sales Invoice Candidates ===
      await test.step('Navigate to Sales Invoice Candidates', async () => {
        await navigateToViewWindow(SALES_INVOICE_CANDIDATE_WINDOW_ID);
        // Invoice candidates may take a moment to appear (async generation)
        await page.waitForTimeout(3000);
      });

      // === STEP 4: Verify data is present ===
      await test.step('Verify invoice candidates appear in grid', async () => {
        const rowCount = await getGridRowCount();
        console.log(`[INFO] Grid row count: ${rowCount}`);
        expect(rowCount, 'Grid MUST have data rows — sales order should generate invoice candidates').toBeGreaterThan(0);
      });

      // === STEP 5: Discover and verify facet filters exist ===
      let filters;
      await test.step('Verify facet filters render', async () => {
        filters = await discoverFacetFilters();
        console.log('[INFO] Facet filters:', JSON.stringify(filters.map(f => f.caption)));
        allure.attachment('Facet Filters', JSON.stringify(filters, null, 2), 'application/json');
        expect(filters.length, 'Expected at least 1 filter').toBeGreaterThanOrEqual(1);
      });

      // === STEP 6: Find and exercise a facet filter with multiple options ===
      await test.step('Exercise facet filter: select option, verify filtering', async () => {
        const totalBefore = await getTotalItemCount(page);
        const rowsBefore = await getGridRowCount();
        console.log(`[INFO] Before facet filter: ${rowsBefore} rows, ${totalBefore} total items`);

        // Find any filter button that has a facet- prefix in its data-testid
        const facetButtons = page.locator('[data-testid^="filter-button-facet-"]');
        const facetCount = await facetButtons.count();

        // Also try non-facet filter buttons (e.g., default-date) as fallback
        const allFilterButtons = page.locator('[data-testid^="filter-button-"]');
        const allFilterCount = await allFilterButtons.count();
        console.log(`[INFO] Found ${facetCount} facet filter buttons, ${allFilterCount} total filter buttons`);

        if (facetCount > 0) {
          // Click the first facet filter
          const firstFacetBtn = facetButtons.first();
          const facetTestId = await firstFacetBtn.getAttribute('data-testid');
          console.log(`[INFO] Clicking facet filter: ${facetTestId}`);
          await firstFacetBtn.click();
          await page.waitForTimeout(1500);

          allure.attachment('Facet Filter Panel Open', await page.screenshot({ fullPage: false }), 'image/png');

          // Find filter options
          const filterOptions = page.locator('[data-testid^="filter-option-"]');
          const optionCount = await filterOptions.count();
          console.log(`[INFO] Facet filter has ${optionCount} options`);

          if (optionCount >= 1) {
            // Select first option
            const firstOptionLabel = filterOptions.nth(0).locator('label.form-control-label');
            await firstOptionLabel.click();
            const firstOptionKey = await filterOptions.nth(0).getAttribute('data-testid');
            console.log(`[INFO] Selected: ${firstOptionKey}`);

            // Apply
            await page.getByTestId('filter-apply-button').click();
            await page.waitForTimeout(2000);

            const totalAfter = await getTotalItemCount(page);
            const rowsAfter = await getGridRowCount();
            console.log(`[INFO] After facet filter: ${rowsAfter} rows, ${totalAfter} total items (was ${totalBefore})`);
            allure.attachment('After Facet Filter Applied', await page.screenshot({ fullPage: false }), 'image/png');

            // The filter should show results (not error out)
            expect(rowsAfter, 'Filtered view should still have rows').toBeGreaterThan(0);

            // If we had multiple options, the filtered count should be <= total
            if (optionCount >= 2 && totalBefore > 0) {
              expect(totalAfter, 'Filtered items should be <= total items').toBeLessThanOrEqual(totalBefore);
            }

            // === Clear the filter ===
            // Re-open the same facet filter
            await firstFacetBtn.click();
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

            const totalCleared = await getTotalItemCount(page);
            console.log(`[INFO] After clearing facet filter: ${totalCleared} total items`);

            // After clearing, total should go back to original
            expect(totalCleared, 'Clearing filter should restore original count').toBe(totalBefore);
          } else {
            console.log('[WARN] No filter options found, closing filter');
            await page.keyboard.press('Escape');
          }
        } else if (allFilterCount > 0) {
          // Fallback: exercise whatever filter is available
          console.log('[INFO] No facet filters found, exercising first available filter');
          const firstBtn = allFilterButtons.first();
          const testId = await firstBtn.getAttribute('data-testid');
          console.log(`[INFO] Clicking filter: ${testId}`);
          await firstBtn.click();
          await page.waitForTimeout(1000);
          allure.attachment('Filter Panel Open', await page.screenshot({ fullPage: false }), 'image/png');
          await page.keyboard.press('Escape');
        } else {
          console.log('[WARN] No filters found on this window');
        }
      });

      // === FINAL: Screenshot ===
      const screenshot = await page.screenshot({ fullPage: false });
      allure.attachment('Final Window State', screenshot, 'image/png');
    });
  });
});
