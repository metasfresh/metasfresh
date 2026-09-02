import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID } from '../utils/WindowIds';

/**
 * View Invalidation on Change admin window E2E test.
 *
 * Verifies the new minimal System-Administration admin window over the
 * WEBUI_ViewInvalidateOnChange config table (AD_Window_ID=542178,
 * "View Invalidation on Change" / element 585139).
 *
 * This spec satisfies the metasfresh-window-design-rules § Verification mandate:
 * every new AD_Window ships with a Playwright test in the SAME PR proving it
 * renders and persists.
 *
 * Scenario:
 * 1. Log in and open window 542178 in NEW mode.
 * 2. Assert the two mandatory lookup fields (AD_Window_ID, AD_Table_ID) render.
 * 3. Set both via the WebAPI PATCH endpoint to valid ids.
 * 4. Wait until the record becomes valid (validStatus.valid=true, i.e. saved).
 * 5. Reload the page and assert both values persisted via the WebAPI record
 *    (fieldsByName.<Column>.value.key), language-independent.
 *
 * Assertions use only language-invariant identifiers (.form-field-<Column>,
 * WebAPI fieldsByName.<Column>.value) — no getByText / localized text.
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

// Valid ids to set on the two mandatory TableDir lookups.
// AD_Table_ID: M_ReceiptSchedule (queried from AD_Table on localhost:21432).
// AD_Window_ID: Forecast window (Prognose, 328) — any existing window is valid.
const RECEIPT_SCHEDULE_TABLE_ID = 540524; // AD_Table.TableName='M_ReceiptSchedule'
const FORECAST_WINDOW_ID_VALUE = 328; // AD_Window Prognose

/**
 * Poll the WebAPI until the record becomes valid (all mandatory fields filled and saved).
 * Returns the validStatus object.
 */
async function waitForRecordValid(page, windowId, recordId, { timeout = 30000 } = {}) {
  const start = Date.now();
  let lastStatus = null;
  while (Date.now() - start < timeout) {
    lastStatus = await page.evaluate(
      async ({ windowId, recordId }) => {
        const resp = await fetch(`/rest/api/window/${windowId}/${recordId}`, {
          credentials: 'include',
          headers: { Accept: 'application/json' },
        });
        if (!resp.ok) return { valid: false, reason: `HTTP ${resp.status}` };
        const data = await resp.json();
        return data[0]?.validStatus || { valid: false, reason: 'no validStatus' };
      },
      { windowId, recordId }
    );
    if (lastStatus?.valid) return lastStatus;
    await page.waitForTimeout(500);
  }
  return lastStatus;
}

/**
 * Set a field value via the WebAPI PATCH endpoint (language-independent, reliable
 * for lookups). Lookup values are passed as { key, caption } objects.
 */
async function patchField(page, windowId, recordId, fieldName, value) {
  return page.evaluate(
    async ({ windowId, recordId, fieldName, value }) => {
      const resp = await fetch(`/rest/api/window/${windowId}/${recordId}`, {
        method: 'PATCH',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify([{ op: 'replace', path: fieldName, value }]),
      });
      if (!resp.ok) {
        const text = await resp.text();
        throw new Error(`PATCH ${fieldName} failed: ${resp.status} ${text}`);
      }
      return resp.json();
    },
    { windowId, recordId, fieldName, value }
  );
}

/**
 * Delete a record via the WebUI REST DELETE endpoint. Best-effort cleanup so
 * the partial-unique index (ad_window_id, ad_table_id) WHERE isactive='Y' does
 * not block re-runs. Swallows errors (e.g. NEW draft never persisted).
 */
async function deleteRecord(page, windowId, recordId) {
  if (!recordId || recordId === 'NEW') return;
  return page.evaluate(
    async ({ windowId, recordId }) => {
      try {
        await fetch(`/rest/api/window/${windowId}/${recordId}`, {
          method: 'DELETE',
          credentials: 'include',
          headers: { Accept: 'application/json' },
        });
      } catch (e) {
        // ignore — best-effort cleanup
      }
    },
    { windowId, recordId }
  );
}

/**
 * Read the raw WebAPI record document (element [0]) for assertions on
 * fieldsByName.<Column>.value.
 */
async function readRecord(page, windowId, recordId) {
  return page.evaluate(
    async ({ windowId, recordId }) => {
      const resp = await fetch(`/rest/api/window/${windowId}/${recordId}`, {
        credentials: 'include',
        headers: { Accept: 'application/json' },
      });
      if (!resp.ok) throw new Error(`GET record failed: HTTP ${resp.status}`);
      const data = await resp.json();
      return data[0];
    },
    { windowId, recordId }
  );
}

testCases.forEach(({ language, label }) => {
  test.describe(`View Invalidation on Change window (${label})`, () => {
    test(`Admin window renders both lookup fields and persists them (${label} UI)`, async ({
      page,
    }) => {
      // === ALLURE METADATA ===
      allure.epic('E0200: WebUI Table Features');
      allure.tag('F00655: Einkaufs-Cockpit');
      allure.tag('F00655');
      allure.story('View Invalidation on Change: admin window renders and persists');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.parameter('UI Label', label);
      allure.tag(language);

      allure.description(`
## E0200: WebUI Table Features

## View Invalidation on Change (AD_Window_ID=542178)

### Test Scenario
Verifies the new minimal System-Administration admin window over the
WEBUI_ViewInvalidateOnChange config table:

1. **Login** — Login user via Backend API
2. **Open window NEW** — Navigate to /window/542178/NEW
3. **Assert fields render** — .form-field-AD_Window_ID and .form-field-AD_Table_ID present
4. **Set lookups via PATCH** — AD_Table_ID=M_ReceiptSchedule, AD_Window_ID=Prognose(328)
5. **Wait valid** — Poll WebAPI until validStatus.valid=true (saved)
6. **Reload + assert persisted** — fieldsByName.<Column>.value.key matches the set ids

### Business Value
Satisfies the window-design § Verification mandate: the new AD_Window is proven
to render and persist in the same PR that introduces it.
      `);

      test.setTimeout(120000);

      // Step 1: Login-only setup
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
              firstname: 'viewinvalidate',
              lastname: 'tester',
            },
          },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      console.log(`[${language}] Master data created (login user)`);

      // Step 2: Login
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      console.log(`[${language}] Logged in successfully`);

      // Step 3: Open window 542178 in NEW mode
      let recordId;
      try {
      await test.step('Open View Invalidation window in NEW mode', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID}/NEW`);
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(1000);

        recordId = page.url().split('/').pop();
        console.log(`[${language}] New record created in WebUI: ${recordId}`);
      });

      // Step 4: Assert both mandatory lookup fields are rendered in the form
      await test.step('Assert AD_Window_ID and AD_Table_ID fields render', async () => {
        const windowField = page.locator('.form-field-AD_Window_ID');
        const tableField = page.locator('.form-field-AD_Table_ID');

        await windowField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await tableField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        expect(await windowField.count()).toBeGreaterThan(0);
        expect(await tableField.count()).toBeGreaterThan(0);

        const screenshot = await page.screenshot();
        allure.attachment('Admin window NEW form', screenshot, 'image/png');
        console.log(`[${language}] Both lookup fields render in the form`);
      });

      // Step 5: Set both mandatory lookups via PATCH and wait until the record is valid
      await test.step('Set both lookups via PATCH and wait for valid', async () => {
        await patchField(page, VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID, recordId, 'AD_Table_ID', {
          key: RECEIPT_SCHEDULE_TABLE_ID,
          caption: 'M_ReceiptSchedule',
        });
        console.log(`[${language}] AD_Table_ID set: ${RECEIPT_SCHEDULE_TABLE_ID}`);

        await patchField(page, VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID, recordId, 'AD_Window_ID', {
          key: FORECAST_WINDOW_ID_VALUE,
          caption: 'Prognose',
        });
        console.log(`[${language}] AD_Window_ID set: ${FORECAST_WINDOW_ID_VALUE}`);

        const validStatus = await waitForRecordValid(
          page,
          VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID,
          recordId
        );
        if (!validStatus?.valid) {
          console.log(`[${language}] validStatus not valid: ${JSON.stringify(validStatus)}`);
        }
        expect(validStatus?.valid).toBe(true);
        console.log(`[${language}] Record is valid and saved`);
      });

      // Step 6: Reload and assert both values persisted via the WebAPI record
      await test.step('Reload and assert both lookups persisted', async () => {
        await page
          .reload({ waitUntil: 'networkidle', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(1000);

        const record = await readRecord(page, VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID, recordId);
        allure.attachment('Reloaded record', JSON.stringify(record, null, 2), 'application/json');

        const windowValue = record?.fieldsByName?.AD_Window_ID?.value;
        const tableValue = record?.fieldsByName?.AD_Table_ID?.value;

        // Lookup values are { key, caption } objects; key holds the id (as string).
        expect(Number(windowValue?.key)).toBe(FORECAST_WINDOW_ID_VALUE);
        expect(Number(tableValue?.key)).toBe(RECEIPT_SCHEDULE_TABLE_ID);

        console.log(
          `[${language}] Persisted: AD_Window_ID=${windowValue?.key}, AD_Table_ID=${tableValue?.key}`
        );
      });
      } finally {
        // Clean up the created config record so the partial-unique index does
        // not block re-runs (independent of pass/fail).
        await deleteRecord(page, VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID, recordId);
        console.log(`[${language}] Cleanup: deleted record ${recordId}`);
      }

      console.log(`[${language}] View Invalidation window test completed successfully`);
    });
  });
});
