import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, getPage } from '../utils/common';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { TextWidget } from '../utils/widgets/TextWidget';
import { NumericWidget } from '../utils/widgets/NumericWidget';
import { ListWidget } from '../utils/widgets/ListWidget';
import { assertRecordIsValid, getFieldData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * VAT-ID check configuration window (table VATaxID_Config).
 *
 * AD_Window_ID 542182, AD_Tab_ID 549363. One editable row per Org, holding the
 * settings the VAT-ID check feature reads at runtime (format check toggle,
 * VIES check toggle, REST endpoint + requester identity, recheck interval,
 * behaviour when the VIES service is unavailable).
 *
 * Purpose of this spec: every one of the 8 config columns must be reachable
 * through the RENDERED window, not merely declared in AD_Field metadata. A
 * column that exists on the table but is not wired into the AD_UI_Element
 * chain (or is hidden by DisplayLogic, or forced read-only) is a silent dead
 * end: the feature would read a value the user could never actually set. So
 * every field assertion below interacts with the real widget (visible +
 * not-readonly, then set + reload + read back) rather than reading the
 * layout endpoint.
 *
 * The OnServiceUnavailable dropdown is asserted to offer EXACTLY its two
 * intended AD_Ref_List values (ServiceUnavailable, Invalid) and nothing else
 * — this list was previously wired to the wrong (six-value) reference list;
 * this assertion is what stops that regressing.
 *
 * Language independence: every assertion is on the DB ColumnName or the
 * AD_Ref_List Value (via the widget's data-testid="option-<value>"), never a
 * caption — see e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent".
 */

const VATID_CONFIG_WINDOW_ID = 542182;

/**
 * Recursively collect the leaf nodes of a `/menu/queryPaths` response tree —
 * the same shape `flattenLastElem` (MenuActions.js) reduces to the rendered
 * `.js-menu-item` array, in the same order. A node with no `children` is a leaf.
 */
function flattenMenuOverlayLeaves(node) {
  if (node.children) {
    return node.children.flatMap(flattenMenuOverlayLeaves);
  }
  return [node];
}

/**
 * Fill the menu-overlay search box and click the result for `targetElementId`
 * — located by its position in the backend's OWN `/menu/queryPaths` response,
 * never by DOM position (`.first()`). "VAT-ID Check" and shorter prefixes of
 * this window's name also match the sibling VATaxID_CheckLog window (542183)
 * — 542182 happens to sort first in the AD_Menu tree, so a `.first()` click
 * here would have passed only by luck (see vatid-check-log-window.spec.js,
 * where the same pattern picks the wrong window because it sorts second).
 * Keying on the elementId the response itself carries is correct regardless
 * of how many entries render or in what order.
 */
async function clickMenuOverlaySearchResult(page, searchTerm, targetElementId) {
  const searchInput = page.locator('.menu-overlay-query input.input-field');
  await searchInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  const [response] = await Promise.all([
    page.waitForResponse((r) => r.url().includes('/menu/queryPaths'), { timeout: SLOW_ACTION_TIMEOUT }),
    searchInput.fill(searchTerm),
  ]);

  const leaves = flattenMenuOverlayLeaves(await response.json());
  const targetIndex = leaves.findIndex((leaf) => leaf.elementId === String(targetElementId));
  expect(targetIndex, `Menu overlay search for "${searchTerm}" must return a result for window ${targetElementId}`).toBeGreaterThanOrEqual(0);

  const resultItem = page.locator('.menu-overlay-query .js-menu-item').nth(targetIndex);
  await resultItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await resultItem.click();
}

// AD_Reference_ID 542126 — must contain EXACTLY these two values (Value column,
// language-invariant). Historically this was miswired to a six-value list.
const ON_SERVICE_UNAVAILABLE_EXPECTED_OPTIONS = ['ServiceUnavailable', 'Invalid'];

// The 8 config columns the feature depends on, each of which must be visible
// AND editable on the rendered window. `widgetType: 'List'` marks the one
// dropdown field — its rendered <input> is deliberately `readonly` (typing is
// blocked; selection happens via the dropdown), so editability for it is
// proven by `isDisabled()` only, not the generic readonly-attribute check
// (matches how ListWidget itself gates edit attempts — see ListWidget.js
// `setValue`/`setByValue`, which check `isDisabled()`, never `readonly`).
const CONFIG_FIELDS = [
  { fieldName: 'IsActive' },
  { fieldName: 'IsFormatCheckEnabled' },
  { fieldName: 'IsVIESCheckEnabled' },
  { fieldName: 'RestApiBaseURL' },
  { fieldName: 'RequesterMemberStateCode' },
  { fieldName: 'RequesterNumber' },
  { fieldName: 'RecheckAfterDays' },
  { fieldName: 'OnServiceUnavailable', widgetType: 'List' },
];

/**
 * Read the keys of every option rendered in a List widget's dropdown, via the
 * data-testid="option-<value>" attribute (the AD_Ref_List Value — never the
 * localized caption). Closes the dropdown without selecting.
 */
async function getListWidgetOptionKeys(fieldName) {
  const page = getPage();
  const container = WidgetCommon.getFieldContainer(fieldName);
  const dropdownTrigger = container.locator('.input-dropdown, input, [class*="dropdown"]').first();
  await dropdownTrigger.waitFor({ state: 'visible', timeout: WidgetCommon.WIDGET_TIMEOUT });
  await dropdownTrigger.click();
  await WidgetCommon.waitForDropdown();

  const dropdown = page.locator('.input-dropdown-list');
  const testIds = await dropdown.locator('[data-testid^="option-"]').evaluateAll(
    (nodes) => nodes.map((n) => n.getAttribute('data-testid'))
  );
  const keys = testIds.map((id) => id.replace(/^option-/, ''));

  await page.keyboard.press('Escape');
  await WidgetCommon.waitForDropdownClosed();

  return keys;
}

test.describe('VAT-ID check configuration window (542182)', () => {
  test('All 8 config fields are visible + editable on a fresh record, OnServiceUnavailable offers exactly 2 options, values persist', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VATaxID_Config window (542182) — field wiring + OnServiceUnavailable reference list');
    allure.severity('critical');
    allure.tag('VATaxID_Config');
    allure.description(`
## VATaxID_Config window (AD_Window_ID 542182 / AD_Tab_ID 549363)

### Why this test exists
Closes a mandatory Playwright-coverage gap for a newly created WebUI window
(metasfresh-window-design-rules § "Verification — MANDATORY Playwright test").

### What it proves
1. The window opens and the tab renders.
2. All 8 config columns (IsActive, IsFormatCheckEnabled, IsVIESCheckEnabled,
   RestApiBaseURL, RequesterMemberStateCode, RequesterNumber, RecheckAfterDays,
   OnServiceUnavailable) are present, VISIBLE and EDITABLE on the rendered
   window for the configuring user — asserted against the live DOM, not the
   layout/metadata endpoint.
3. The OnServiceUnavailable dropdown (AD_Reference_ID 542126) offers exactly
   the two intended values (ServiceUnavailable, Invalid) and no others.
4. A record can be created, its fields set, saved, and the saved values read
   back unchanged after a page reload.
    `);

    test.setTimeout(120000);

    // === STEP 1: Create test user + login ===
    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { firstname: 'E2E', lastname: 'VatidConfig' } } },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    // Use a deterministic URL check rather than DashboardPage.expectVisible()'s
    // networkidle wait — the dashboard's STOMP/KPI polling keeps the network
    // permanently active, so networkidle never settles (see
    // e2e/frontend-webui CLAUDE.md via the playwright-run skill).
    await LoginPage.expectLoggedIn();

    // The table permits only ONE active VATaxID_Config row per org (partial unique
    // index on AD_Org_ID WHERE IsActive='Y'). Wrap record creation + assertions in
    // try/finally so the created record is always deleted afterwards (best-effort,
    // robust even if an earlier step throws) — otherwise the next run's Alt+N record
    // collides with this run's leftover active row. Same pattern as
    // view-invalidate-config-window.spec.js's deleteRecord/try-finally.
    let recordId;
    try {
    // === STEP 2: Open the window and create a new record ===
    await page.goto(`${FRONTEND_BASE_URL}/window/${VATID_CONFIG_WINDOW_ID}`);
    await page.locator('.document-list-wrapper, .document-list').waitFor({
      state: 'visible',
      timeout: VERY_SLOW_ACTION_TIMEOUT,
    });
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    console.log('[INFO] VATaxID_Config window (542182) list view loaded');

    await page.locator('body').click();
    await page.waitForTimeout(200);
    await page.keyboard.press('Alt+N');
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(1000);

    recordId = page.url().split('/').pop();
    console.log(`[INFO] New VATaxID_Config record created: ${recordId}`);

    await assertRecordIsValid(VATID_CONFIG_WINDOW_ID, recordId, 'after create');

    // === STEP 3: Every one of the 8 config fields is visible + not-readonly ===
    for (const { fieldName, widgetType } of CONFIG_FIELDS) {
      await test.step(`Assert ${fieldName} is visible and editable`, async () => {
        const container = WidgetCommon.getFieldContainer(fieldName);
        await container.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(container, `${fieldName} must be visible on the rendered window`).toBeVisible();

        if (widgetType === 'List') {
          // List widgets render a deliberately `readonly` <input> (selection
          // happens via the dropdown, not typing) — `isDisabled()` is the
          // correct editability signal here, matching ListWidget.js itself.
          const input = container.locator('input').first();
          const isDisabled = await input.isDisabled().catch(() => false);
          expect(isDisabled, `${fieldName} (List widget) must NOT be disabled on the rendered window`).toBe(false);
        } else {
          const isReadonly = await WidgetCommon.isFieldReadonly(fieldName);
          expect(isReadonly, `${fieldName} must NOT be readonly on the rendered window`).toBe(false);
        }

        console.log(`[PASS] ${fieldName} — visible and editable`);
      });
    }

    // === STEP 4: OnServiceUnavailable offers EXACTLY its 2 intended options ===
    await test.step('Assert OnServiceUnavailable offers exactly 2 options (ServiceUnavailable, Invalid)', async () => {
      const optionKeys = await getListWidgetOptionKeys('OnServiceUnavailable');
      console.log(`[INFO] OnServiceUnavailable rendered options: ${JSON.stringify(optionKeys)}`);

      expect(optionKeys.sort()).toEqual([...ON_SERVICE_UNAVAILABLE_EXPECTED_OPTIONS].sort());
    });

    // === STEP 5: Set every field through its real widget ===
    const expected = {
      RestApiBaseURL: 'https://ec.europa.eu/taxation_customs/vies/rest-api/check-vat-test-service',
      RequesterMemberStateCode: 'DE',
      RequesterNumber: 'DE123456789',
      RecheckAfterDays: 45,
      OnServiceUnavailable: 'Invalid',
    };

    await test.step('Toggle IsActive off then on (proves the boolean widget is editable)', async () => {
      await BooleanWidget.setFalse('IsActive');
      expect(await BooleanWidget.getValue('IsActive')).toBe(false);
      await BooleanWidget.setTrue('IsActive');
      expect(await BooleanWidget.getValue('IsActive')).toBe(true);
    });

    await test.step('Set IsFormatCheckEnabled to false (default is true)', async () => {
      await BooleanWidget.setFalse('IsFormatCheckEnabled');
    });

    await test.step('Set IsVIESCheckEnabled to true (default is false)', async () => {
      await BooleanWidget.setTrue('IsVIESCheckEnabled');
    });

    await test.step(`Set RestApiBaseURL to "${expected.RestApiBaseURL}"`, async () => {
      await TextWidget.setValue('RestApiBaseURL', expected.RestApiBaseURL);
    });

    await test.step(`Set RequesterMemberStateCode to "${expected.RequesterMemberStateCode}"`, async () => {
      await TextWidget.setValue('RequesterMemberStateCode', expected.RequesterMemberStateCode);
    });

    await test.step(`Set RequesterNumber to "${expected.RequesterNumber}"`, async () => {
      await TextWidget.setValue('RequesterNumber', expected.RequesterNumber);
    });

    await test.step(`Set RecheckAfterDays to ${expected.RecheckAfterDays}`, async () => {
      await NumericWidget.setValue('RecheckAfterDays', expected.RecheckAfterDays);
    });

    await test.step(`Set OnServiceUnavailable to "${expected.OnServiceUnavailable}" (by AD_Ref_List key)`, async () => {
      await ListWidget.setByValue('OnServiceUnavailable', expected.OnServiceUnavailable);
    });

    await waitForRecordSavedNoError(VATID_CONFIG_WINDOW_ID, recordId);

    // === STEP 6: Reload and read back every value via the WebAPI (raw, language-independent) ===
    await test.step('Reload page and assert all saved values persisted', async () => {
      await page.reload();
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await WidgetCommon.getFieldContainer('OnServiceUnavailable').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const isActive = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'IsActive');
      expect(isActive).toBe(true);

      const isFormatCheckEnabled = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'IsFormatCheckEnabled');
      expect(isFormatCheckEnabled).toBe(false);

      const isViesCheckEnabled = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'IsVIESCheckEnabled');
      expect(isViesCheckEnabled).toBe(true);

      const restApiBaseUrl = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'RestApiBaseURL');
      expect(restApiBaseUrl).toBe(expected.RestApiBaseURL);

      const requesterMemberStateCode = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'RequesterMemberStateCode');
      expect(requesterMemberStateCode).toBe(expected.RequesterMemberStateCode);

      const requesterNumber = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'RequesterNumber');
      expect(requesterNumber).toBe(expected.RequesterNumber);

      const recheckAfterDays = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'RecheckAfterDays');
      expect(Number(recheckAfterDays)).toBe(expected.RecheckAfterDays);

      const onServiceUnavailable = await getRawFieldValue(VATID_CONFIG_WINDOW_ID, recordId, 'OnServiceUnavailable');
      expect(onServiceUnavailable).toBe(expected.OnServiceUnavailable);

      console.log('[PASS] All 8 config fields persisted correctly after save + reload');

      const screenshotBuffer = await page.screenshot({ fullPage: true });
      await allure.attachment('VATaxID_Config record after save & reload', screenshotBuffer, 'image/png');
    });

    console.log('[PASS] VATaxID_Config window (542182): all fields visible+editable, OnServiceUnavailable has exactly 2 options, record persists.');
    } finally {
      // Best-effort cleanup so the partial-unique index (AD_Org_ID WHERE IsActive='Y')
      // does not block the next run — runs regardless of pass/fail.
      await deleteRecord(page, VATID_CONFIG_WINDOW_ID, recordId);
      console.log(`[INFO] Cleanup: deleted record ${recordId}`);
    }
  });

  test('The config window is reachable via the main menu search (Alt+2 quick-nav)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VATaxID_Config window (542182) — menu-tree reachability');
    allure.severity('critical');
    allure.tag('VATaxID_Config');
    allure.description(`
## VATaxID_Config menu placement (AD_Menu_ID 542356)

### Why this test exists
A prior fix added an AD_Menu/AD_TreeNodeMM entry so the window is reachable from the
menu at all. This test proves reachability THROUGH THE MENU SYSTEM (not a direct URL),
so a dropped or mis-parented menu entry is caught here rather than only by the
direct-URL test above, which would still pass either way.

The quick-nav popup's own top-level breadcrumb (the folders shown immediately after
Alt+2) is hard-capped to the first 10 root branches (\`HOME_MENU_USER_MAX_ITEMS\` in
\`frontend/src/constants/Constants.js\`) — a pre-existing, permission-independent
product behaviour, unrelated to this feature, under which the VAT-ID window's parent
folder (root position 13 of 22) can never appear for any role. The popup's own search
box is NOT subject to that cap (backed by the unlimited \`queryPathsRequest\`), and is
the mechanism a real user relies on to find a window without scrolling the top-level
folders — so this test drives that surface instead of the capped breadcrumb.

### What it proves
The configuring user can open the main menu (Alt+2), type the window's name into the
overlay's own search box, and click the single matching result to land on window
542182 — i.e. the menu entry both EXISTS and is indexed/discoverable under its
registered name.

### Note on selectors
The search input and result item are matched structurally
(\`.menu-overlay-query input.input-field\` / \`.menu-overlay-query .js-menu-item\`) —
neither carries a \`data-testid\`, per \`MenuOverlayItem.js\`. The window's display name
is used as the search **input** (not an assertion), pinning the login language to
en_US as this spec's other test does. The result item to click is located by its
position in the backend's own \`/menu/queryPaths\` response (matched on \`elementId\`),
never by DOM position (\`.first()\`) — shorter prefixes of this window's name also
match the sibling VATaxID_CheckLog window (542183). The end result is asserted only
on the language-invariant window id in the URL and a structural DOM marker — see
e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent".
    `);

    test.setTimeout(60000);

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'VatidConfigMenu' } } },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await test.step('Open the main menu (Alt+2)', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);
      await page.keyboard.press('Alt+2');
      await page.locator('.menu-overlay').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Search the overlay for the window by name and open the matching result', async () => {
      // Input value only (not an assertion) — the window's own display name.
      await clickMenuOverlaySearchResult(page, 'VAT-ID Check Configuration', VATID_CONFIG_WINDOW_ID);

      await page.waitForURL(new RegExp(`/window/${VATID_CONFIG_WINDOW_ID}(/|$)`), { timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('.document-list-wrapper, .document-list').waitFor({
        state: 'visible',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });

      expect(page.url()).toContain(`/window/${VATID_CONFIG_WINDOW_ID}`);
      console.log(`[PASS] Reached VATaxID_Config window (${VATID_CONFIG_WINDOW_ID}) via the main menu overlay search`);
    });
  });
});

/**
 * Delete a record via the WebAPI DELETE endpoint (absolute WEBAPI_BASE_URL, same
 * request path used by assertRecordIsValid/getFieldData above — a same-origin
 * relative fetch from the page only reaches the webapi when frontend+webapi share
 * an origin, which is not the case for a local split-port dev server). Best-effort
 * cleanup so the partial-unique index (AD_Org_ID WHERE IsActive='Y') does not block
 * re-runs. Swallows errors (e.g. record was never actually persisted).
 */
async function deleteRecord(page, windowId, recordId) {
  if (!recordId || recordId === 'NEW') return;
  try {
    await page.request.delete(`${WEBAPI_BASE_URL}/window/${windowId}/${recordId}`, {
      headers: { Accept: 'application/json' },
    });
  } catch (e) {
    // ignore — best-effort cleanup
  }
}

/**
 * Read a field's raw (language-invariant) value via the WebAPI.
 * List/Lookup fields return { key, caption }; other widgets return the raw
 * scalar. Always returns the raw key/scalar, never a caption.
 */
async function getRawFieldValue(windowId, recordId, fieldName) {
  const field = await getFieldData(windowId, recordId, fieldName);
  return typeof field.value === 'object' && field.value !== null ? field.value.key : field.value;
}

/**
 * Wait for the pending-save indicator to clear, then assert the record has no
 * save error (surfaces a validation failure immediately instead of letting it
 * hide behind the next assertion).
 */
async function waitForRecordSavedNoError(windowId, recordId) {
  await WidgetCommon.waitForSaveComplete();
  await assertRecordIsValid(windowId, recordId, 'after setting all config fields');
}
