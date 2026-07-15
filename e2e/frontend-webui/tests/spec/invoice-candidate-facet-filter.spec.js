import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { SALES_INVOICE_CANDIDATE_WINDOW_ID } from '../utils/WindowIds';
import {
  navigateToViewWindow,
  discoverFacetFilters,
  getGridRowCount,
  getTotalItemCount,
} from '../utils/view-validation/ViewWindowHelper';
import { loginWithMasterdataUser } from '../utils/LoginHelper';

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
 * specific BPartner by matching the BPartner ID from the API response against
 * the facet option data-testid attributes.
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
        await loginWithMasterdataUser(page, masterdata.login.user);
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
        totalBefore = await getTotalItemCount();
        console.log(`[INFO] Grid row count: ${rowCount}, total items: ${totalBefore}`);
        expect(totalBefore, 'Must have at least 2 invoice candidates from our 2 orders').toBeGreaterThanOrEqual(2);
      });

      // === STEP 5: Discover facet filters ===
      await test.step('Verify facet filters render', async () => {
        const filters = await discoverFacetFilters();
        console.log('[INFO] Facet filters:', JSON.stringify(filters.map(f => f.caption)));
        allure.attachment('Facet Filters', JSON.stringify(filters, null, 2), 'application/json');
        expect(filters.length, 'Expected at least 1 filter').toBeGreaterThanOrEqual(1);
      });

      // === STEP 6: Filter by Bill Partner facet using our specific BPartner ID ===
      await test.step('Filter by Bill Partner facet — verify with specific BPartner', async () => {
        // Get the BPartner ID from the first sales order's masterdata response
        const bp1Id = masterdata.bpartners?.BP1?.bpartnerId;
        const so1Id = masterdata.salesOrders?.SO1?.id;
        console.log(`[INFO] BP1 ID: ${bp1Id}, SO1 ID: ${so1Id}`);

        // Try Bill Partner facet first, fall back to Order facet
        const bpartnerFacet = page.locator('[data-testid="filter-button-facet-Bill_BPartner_ID"]');
        const orderFacet = page.locator('[data-testid="filter-button-facet-C_Order_ID"]');
        let targetFacetBtn;
        let targetOptionId; // the ID to find in filter-option-{id}

        if (await bpartnerFacet.count() > 0 && bp1Id) {
          targetFacetBtn = bpartnerFacet;
          targetOptionId = String(bp1Id);
          console.log(`[INFO] Using Bill_BPartner_ID facet, looking for option with ID ${targetOptionId}`);
        } else if (await orderFacet.count() > 0 && so1Id) {
          targetFacetBtn = orderFacet;
          targetOptionId = String(so1Id);
          console.log(`[INFO] Using C_Order_ID facet, looking for option with ID ${targetOptionId}`);
        } else {
          // Last resort: use first available facet, pick first option
          targetFacetBtn = page.locator('[data-testid^="filter-button-facet-"]').first();
          expect(await targetFacetBtn.count(), 'Must have at least one facet filter').toBeGreaterThan(0);
          targetOptionId = null;
          console.log('[INFO] Using first available facet filter');
        }

        await targetFacetBtn.click();
        await page.waitForTimeout(1500);
        allure.attachment('Facet Filter Panel Open', await page.screenshot({ fullPage: false }), 'image/png');

        const filterOptions = page.locator('[data-testid^="filter-option-"]');
        const optionCount = await filterOptions.count();
        console.log(`[INFO] Facet filter has ${optionCount} options`);
        expect(optionCount, 'Facet must have options').toBeGreaterThanOrEqual(1);

        // Find and click our specific option by ID, or fall back to first option
        let selectedOption;
        if (targetOptionId) {
          selectedOption = page.locator(`[data-testid="filter-option-${targetOptionId}"]`);
          if (await selectedOption.count() === 0) {
            console.log(`[WARN] Option filter-option-${targetOptionId} not found, using first option`);
            selectedOption = filterOptions.nth(0);
          }
        } else {
          selectedOption = filterOptions.nth(0);
        }

        await selectedOption.locator('label.form-control-label').click();
        const selectedTestId = await selectedOption.getAttribute('data-testid');
        const selectedText = await selectedOption.textContent();
        console.log(`[INFO] Selected: ${selectedTestId} (${selectedText.trim()})`);

        // Apply
        await page.getByTestId('filter-apply-button').click();
        await page.waitForTimeout(2000);

        const totalAfterFilter = await getTotalItemCount();
        const rowsAfterFilter = await getGridRowCount();
        console.log(`[INFO] After filter: ${rowsAfterFilter} rows, ${totalAfterFilter} total (was ${totalBefore})`);
        allure.attachment('After Facet Filter Applied', await page.screenshot({ fullPage: false }), 'image/png');

        // HARD: filtering must reduce count and still show results
        expect(totalAfterFilter, 'Filtering must show fewer items').toBeLessThan(totalBefore);
        expect(totalAfterFilter, 'Filtered view must still have items').toBeGreaterThan(0);

        // === Clear filter and verify count restores ===
        await targetFacetBtn.click();
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

        const totalCleared = await getTotalItemCount();
        console.log(`[INFO] After clearing filter: ${totalCleared} total`);
        expect(totalCleared, 'Clearing filter must restore original count').toBe(totalBefore);
      });

      // === FINAL: Screenshot ===
      const screenshot = await page.screenshot({ fullPage: false });
      allure.attachment('Final Window State', screenshot, 'image/png');
    });
  });
});
