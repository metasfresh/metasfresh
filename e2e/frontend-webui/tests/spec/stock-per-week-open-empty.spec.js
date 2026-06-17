import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { STOCK_PER_WEEK_WINDOW_ID } from '../utils/WindowIds';

/**
 * Stock per Week (Bestand pro Woche) — open-empty / load-on-filter E2E test.
 *
 * Window AD_Window_ID: 542159, table MD_Stock_PerWeek_V (~782k rows on the customer instance).
 *
 * Behaviour under test (me03 #30457): opening the window standalone (from the menu, no filter)
 * must NOT scan the whole materialised view. Instead the view returns a zero-row selection plus a
 * "please filter first" hint. Rows load only once the user applies a filter (product / warehouse /
 * week range).
 *
 * Mechanism: StockPerWeekSqlViewBindingCustomizer sets queryIfNoFilters(false) on the SqlViewBinding
 * for this one windowId; the existing SqlViewRowIdsOrderedSelectionFactory guard then short-circuits
 * to an EmptyReason (AD_Messages webui.view.emptyReason.pleaseFilterFirst.text / .hint) when no filter
 * is applied. The frontend renders that reason in `.empty-info-text` (<h5>=text, <p>=hint).
 *
 * NOTE: the assertion contract here is the open-empty BEHAVIOUR, which is fully deterministic and needs
 * no seeded stock:
 *   1. standalone open  -> 0 grid rows + the "filter first" hint shown.
 *   2. after a filter is applied -> the "filter first" hint is gone (guard no longer fires).
 * Asserting that real rows appear additionally requires stock materialised into MD_Stock_PerWeek_V,
 * which is timing/data dependent; this test logs the row count opportunistically but does not hard-fail
 * on it (the hint toggling already proves the filter-gated load path).
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Stock per Week open-empty (${label})`, () => {
    test(`Standalone open is empty, filtering loads (${label})`, async ({ page }) => {
      allure.epic('E0193: User Interface');
      allure.tag('F14030: Filtering & Sorting');
      allure.tag('F14030');
      allure.story('Stock per Week opens empty and loads rows only after a filter');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## Stock per Week — open empty, load on filter

1. **Standalone open** — open window 542159 from the URL (no filter). Assert 0 grid rows and the
   "please filter first" empty hint is shown (the ~782k-row view is NOT scanned).
2. **Apply a product filter** — assert the empty hint disappears (the queryIfNoFilters guard is
   filter-gated). Row count after filtering is logged.
      `);

      test.setTimeout(180000); // 3 minutes

      // === CREATE TEST DATA (a product to filter by) + LOGIN USER ===
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: { language, firstname: 'stockperweek', lastname: 'test' },
          },
          products: {
            Product1: {
              name: 'SPW_PROD',
              type: 'Item',
              prices: [{ price: 10.0, currencyCode: 'EUR' }],
            },
          },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      // === LOGIN ===
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // ======================================================================
      // STEP 1: Standalone open -> empty + "filter first" hint
      // ======================================================================
      await test.step('Standalone open shows no rows + filter-first hint', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${STOCK_PER_WEEK_WINDOW_ID}`);
        await page
          .locator('.document-list-wrapper, .document-list')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page
          .locator('.indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        // Zero data rows.
        const rowCount = await page.locator('table tbody tr').count();
        console.log(`[INFO] Stock-per-week standalone open: grid rows = ${rowCount}`);

        // The "please filter first" empty reason is rendered in .empty-info-text
        // (h5 = emptyResultText, p = emptyResultHint).
        const emptyInfo = page.locator('.empty-info-text');
        await emptyInfo.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        const emptyText = (await emptyInfo.textContent().catch(() => '')).trim();
        console.log(`[INFO] Empty-info text shown: "${emptyText}"`);
        allure.attachment('Empty open screenshot', await page.screenshot(), 'image/png');

        expect(rowCount, 'standalone open must show no data rows').toBe(0);
        await expect(emptyInfo, 'the filter-first empty hint must be visible').toBeVisible();
        expect(emptyText.length, 'the empty hint must carry localized text').toBeGreaterThan(0);
      });

      // ======================================================================
      // STEP 2: Apply a product filter -> the filter-first hint disappears
      // ======================================================================
      await test.step('Applying a product filter removes the empty hint', async () => {
        const productId = masterdata.products?.Product1?.id ?? masterdata.products?.Product1;
        console.log(`[INFO] Filtering by M_Product_ID = ${JSON.stringify(productId)}`);

        // Open the first filter button in the filter bar and pick the product filter.
        const filterButton = page.locator('.filter-wrapper .btn-filter').first();
        await filterButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await filterButton.click();
        await page.waitForTimeout(500);

        // Select the product filter option if a filter menu opened, otherwise the widget is inline.
        const productOption = page
          .locator('.filter-menu li, .filter-option')
          .filter({ hasText: /Product|Produkt/i })
          .first();
        if (await productOption.isVisible().catch(() => false)) {
          await productOption.click();
          await page.waitForTimeout(500);
        }

        // Type into the product lookup widget and pick the seeded product.
        const productLookup = page.locator('#lookup_M_Product_ID input').first();
        if (await productLookup.isVisible().catch(() => false)) {
          await productLookup.click();
          await productLookup.fill('SPW_PROD');
          await page.waitForTimeout(1500);
          const firstSuggestion = page.locator('.input-dropdown-list-option').first();
          if (await firstSuggestion.isVisible().catch(() => false)) {
            await firstSuggestion.click();
          } else {
            await productLookup.press('Enter');
          }
          await page.waitForTimeout(500);
        }

        // Apply the filter (Apply button inside the filter widget, else Enter).
        const applyButton = page.locator('.filter-btn-apply, .btn-filter-apply, .applyBtn').first();
        if (await applyButton.isVisible().catch(() => false)) {
          await applyButton.click();
        } else {
          await page.keyboard.press('Enter');
        }

        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page
          .locator('.indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(1000);

        const rowsAfter = await page.locator('table tbody tr').count();
        const hintStillVisible = await page
          .locator('.empty-info-text')
          .isVisible()
          .catch(() => false);
        console.log(
          `[INFO] After product filter: grid rows = ${rowsAfter}, filter-first hint visible = ${hintStillVisible}`
        );
        allure.attachment('Filtered screenshot', await page.screenshot(), 'image/png');

        // Core assertion: once a filter is applied the queryIfNoFilters guard no longer fires,
        // so the "please filter first" hint is gone. (Whether actual rows appear depends on
        // stock being materialised into MD_Stock_PerWeek_V for the seeded product.)
        expect(hintStillVisible, 'the filter-first hint must disappear once a filter is applied').toBe(
          false
        );
      });

      console.log('[PASS] Stock-per-week open-empty / load-on-filter test completed');
    });
  });
});
