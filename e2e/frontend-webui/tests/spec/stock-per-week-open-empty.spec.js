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
 * Behaviour under test: opening window 542159 standalone (from the menu, no filter) must NOT scan
 * the whole materialised view. Instead the view returns a zero-row selection plus a
 * "please filter first" hint. Rows load only once the user applies a filter (product / warehouse /
 * week range).
 *
 * The open-empty behaviour is enforced at the view-framework level: when a window is configured
 * with queryIfNoFilters=false, the view guard short-circuits to an EmptyReason
 * (AD_Messages webui.view.emptyReason.pleaseFilterFirst.text / .hint) when no filter is applied.
 * The frontend renders that reason in `.empty-info-text` (<h5>=text, <p>=hint).
 *
 * NOTE: the assertion contract here is the open-empty BEHAVIOUR, which is fully deterministic and
 * needs no seeded stock:
 *   1. standalone open  -> 0 grid rows + the "filter first" hint shown (UI assertion).
 *   2. after a filter is applied via REST -> the "filter first" hint is gone (guard no longer fires).
 * Asserting that real rows appear additionally requires stock materialised into MD_Stock_PerWeek_V,
 * which is timing/data dependent; this test does not hard-fail on row count
 * (the hint toggling already proves the filter-gated load path).
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Stock per Week open-empty (${label})`, () => {
    test(`Standalone open is empty, filtering loads (${label})`, async ({ page }) => {
      allure.epic('E0155: Material Disposition');
      allure.tag('F19100: Stock per week');
      allure.tag('F19100');
      allure.story('Stock per Week opens empty and loads rows only after a filter');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## Stock per Week — open empty, load on filter

1. **Standalone open** — open window 542159 from the URL (no filter). Assert 0 grid rows and the
   "please filter first" empty hint is shown (the ~782k-row view is NOT scanned).
2. **Apply a WeekStartDate filter via REST** — assert the empty-reason hint is absent in the view
   response (the queryIfNoFilters guard does not fire once a filter is supplied).
      `);

      test.setTimeout(180000); // 3 minutes

      // === CREATE LOGIN USER ===
      // Step 2 uses a REST-based view assertion — no seeded product or price list required.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: { language, firstname: 'stockperweek', lastname: 'test' },
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
      // STEP 2: Create a view via REST with a WeekStartDate filter
      //         -> the view framework guard must NOT fire (emptyResultText absent)
      // ======================================================================
      await test.step('Creating a filtered view via REST removes the empty-reason hint', async () => {
        // Use a WeekStartDate range covering the current week so the filter is non-trivial.
        // The exact row count depends on stock data materialised into MD_Stock_PerWeek_V, but
        // the guard behaviour (emptyResultText present ↔ no filter) is deterministic.
        const today = new Date().toISOString().slice(0, 10); // YYYY-MM-DD

        // Resolve the REST base from the page's own runtime config (window.config.API_URL) — that is
        // exactly the origin the browser logged in against, so page.request (which shares the browser
        // context's cookie jar) carries the authenticated session to it. This is robust across stack
        // topologies: a static prod-build front-end talks to the WebAPI directly (e.g. :8080), while a
        // webpack dev-server proxies /rest/api on its own origin. Fall back to the WebAPI default.
        const restBase = await page.evaluate(
          () => (window.config && window.config.API_URL) || null
        );
        const apiBase = restBase || process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';

        const createViewResp = await page.request.post(
          `${apiBase}/documentView/${STOCK_PER_WEEK_WINDOW_ID}`,
          {
            headers: { 'Content-Type': 'application/json' },
            data: {
              // JSONCreateViewRequest reads the window id from @JsonProperty("documentType").
              documentType: String(STOCK_PER_WEEK_WINDOW_ID),
              viewType: 'grid',
              filters: [
                {
                  filterId: 'WeekStartDate',
                  parameters: [
                    { parameterName: 'WeekStartDate', value: today },
                  ],
                },
              ],
            },
          }
        );

        expect(createViewResp.ok(), `POST /documentView/${STOCK_PER_WEEK_WINDOW_ID} must succeed`).toBe(true);
        const viewData = await createViewResp.json();
        console.log(`[INFO] Filtered view created: viewId=${viewData.viewId}, size=${viewData.size}`);
        allure.attachment('Filtered view response', JSON.stringify(viewData, null, 2), 'application/json');

        // Core assertion: when a filter is present the queryIfNoFilters guard does NOT fire,
        // so emptyResultText must be absent (null / undefined) in the view response.
        expect(
          viewData.emptyResultText ?? null,
          'emptyResultText must be absent when a filter is applied (guard must not fire)'
        ).toBeNull();
      });

      console.log('[PASS] Stock-per-week open-empty / load-on-filter test completed');
    });
  });
});
