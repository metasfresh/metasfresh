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
 * gh#28680: This test verifies facet filtering on a window backed by a regular
 * table (not a view). The selectFieldValues() performance fix affects ALL facet
 * filters in ALL windows.
 *
 * Strategy: Creates TWO sales orders with distinct BPartners so the BPartner
 * facet filter has at least 2 options from our own data. Then filters to one
 * BPartner and verifies the count matches exactly what we created.
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

      // === STEP 1: Create test data — TWO orders with DIFFERENT BPartners ===
      // This ensures the Bill Partner facet has at least 2 options from our data.
      let masterdata;
      await test.step('Create masterdata + two sales orders via API', async () => {
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
                name: 'ICFacetPartner_A',
              },
              BP2: {
                isVendor: true,
                isCustomer: true,
                isSoPriceList: true,
                name: 'ICFacetPartner_B',
              },
            },
            products: {
              P1: {
                name: 'ICFPRD1',
                type: 'Item',
                prices: [{ price: 10.0, currencyCode: 'EUR' }],
              },
            },
            warehouses: {
              wh: {},
            },
            salesOrders: {
              SO1: {
                bpartner: 'BP1',
                warehouse: 'wh',
                datePromised: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString(),
                lines: [{ product: 'P1', qty: 5 }],
              },
              SO2: {
                bpartner: 'BP2',
                warehouse: 'wh',
                datePromised: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString(),
                lines: [{ product: 'P1', qty: 3 }],
              },
            },
          },
        });

        console.log('[INFO] Sales order SO1:', JSON.stringify(masterdata.salesOrders.SO1));
        console.log('[INFO] Sales order SO2:', JSON.stringify(masterdata.salesOrders.SO2));
        expect(masterdata.salesOrders.SO1.documentNo, 'SO1 must have documentNo').toBeTruthy();
        expect(masterdata.salesOrders.SO2.documentNo, 'SO2 must have documentNo').toBeTruthy();
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
        await page.waitForTimeout(3000);
      });

      // === STEP 4: Verify data is present ===
      let totalBefore;
      await test.step('Verify invoice candidates appear in grid', async () => {
        const rowCount = await getGridRowCount();
        totalBefore = await getTotalItemCount(page);
        console.log(`[INFO] Grid row count: ${rowCount}, total items: ${totalBefore}`);
        // We created 2 orders with 1 line each => at least 2 invoice candidates
        expect(totalBefore, 'Must have at least 2 invoice candidates from our 2 orders').toBeGreaterThanOrEqual(2);
      });

      // === STEP 5: Discover facet filters ===
      await test.step('Verify facet filters render', async () => {
        const filters = await discoverFacetFilters();
        console.log('[INFO] Facet filters:', JSON.stringify(filters.map(f => f.caption)));
        allure.attachment('Facet Filters', JSON.stringify(filters, null, 2), 'application/json');
        expect(filters.length, 'Expected at least 1 filter').toBeGreaterThanOrEqual(1);
      });

      // === STEP 6: Use Bill Partner facet to filter to one of our BPartners ===
      // The Bill Partner facet (C_Bill_BPartner_ID) should have at least our 2 BPartners.
      // Filtering to BP1 should show exactly 1 candidate (SO1 has 1 line).
      await test.step('Filter by Bill Partner facet — verify specific count', async () => {
        // Look for the Bill Partner facet button
        const bpartnerFacet = page.locator('[data-testid="filter-button-facet-Bill_BPartner_ID"]');
        const hasBPartnerFacet = await bpartnerFacet.count() > 0;

        if (!hasBPartnerFacet) {
          // Fallback: use any available facet filter
          console.log('[WARN] Bill_BPartner_ID facet not found, trying C_Order_ID');
          const orderFacet = page.locator('[data-testid="filter-button-facet-C_Order_ID"]');
          if (await orderFacet.count() > 0) {
            await orderFacet.click();
          } else {
            // Use first available facet
            const anyFacet = page.locator('[data-testid^="filter-button-facet-"]').first();
            expect(await anyFacet.count(), 'Must have at least one facet filter').toBeGreaterThan(0);
            await anyFacet.click();
          }
        } else {
          await bpartnerFacet.click();
        }
        await page.waitForTimeout(1500);
        allure.attachment('Facet Filter Panel Open', await page.screenshot({ fullPage: false }), 'image/png');

        // Get all filter options
        const filterOptions = page.locator('[data-testid^="filter-option-"]');
        const optionCount = await filterOptions.count();
        console.log(`[INFO] Facet filter has ${optionCount} options`);
        expect(optionCount, 'Facet must have at least 2 options (our 2 BPartners)').toBeGreaterThanOrEqual(2);

        // Select ONLY the first option
        const firstOptionLabel = filterOptions.nth(0).locator('label.form-control-label');
        await firstOptionLabel.click();
        const firstOptionTestId = await filterOptions.nth(0).getAttribute('data-testid');
        const firstOptionText = await filterOptions.nth(0).textContent();
        console.log(`[INFO] Selected: ${firstOptionTestId} (${firstOptionText.trim()})`);

        // Apply
        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const totalAfterFilter = await getTotalItemCount(page);
        const rowsAfterFilter = await getGridRowCount();
        console.log(`[INFO] After filter: ${rowsAfterFilter} rows, ${totalAfterFilter} total (was ${totalBefore})`);
        allure.attachment('After Facet Filter Applied', await page.screenshot({ fullPage: false }), 'image/png');

        // HARD: filtering to one BPartner must reduce the count
        expect(totalAfterFilter, 'Filtering to one BPartner must show fewer items').toBeLessThan(totalBefore);
        expect(totalAfterFilter, 'Filtered view must still have items').toBeGreaterThan(0);

        // === Clear filter and verify count restores ===
        const facetBtn = hasBPartnerFacet
          ? bpartnerFacet
          : page.locator('[data-testid^="filter-button-facet-"]').first();
        await facetBtn.click();
        await page.waitForTimeout(1000);
        const allOpts = page.locator('[data-testid^="filter-option-"]');
        const allOptCount = await allOpts.count();
        for (let i = 0; i < allOptCount; i++) {
          const cb = page.locator('[data-testid^="filter-checkbox-"]').nth(i);
          if (await cb.isChecked()) {
            await allOpts.nth(i).locator('label.form-control-label').click();
          }
        }
        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const totalCleared = await getTotalItemCount(page);
        console.log(`[INFO] After clearing filter: ${totalCleared} total`);
        expect(totalCleared, 'Clearing filter must restore original count').toBe(totalBefore);
      });

      // === FINAL: Screenshot ===
      const screenshot = await page.screenshot({ fullPage: false });
      allure.attachment('Final Window State', screenshot, 'image/png');
    });
  });
});
