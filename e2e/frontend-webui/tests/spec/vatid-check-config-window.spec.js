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
import { assertRecordIsValid, getFieldData } from '../utils/WebAPIValidation';

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

  test('The config window is reachable by browsing the main menu tree (Finanzen -> Einstellungen)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VATaxID_Config window (542182) — menu-tree reachability');
    allure.severity('critical');
    allure.tag('VATaxID_Config');
    allure.description(`
## VATaxID_Config menu placement (AD_Menu_ID 542356)

### Why this test exists
A prior fix added an AD_Menu/AD_TreeNodeMM entry so the window is reachable from the
menu at all, but the entry was mis-parented under a developer/schema-maintenance
branch ("Application-Dictionary") where the configuring (accounting/admin) user would
never look. A follow-up migration re-parented the entry to the business-facing
"Finanzen -> Einstellungen" folder, alongside the adjacent Steuersatz/Steuerkategorie
tax settings. This test proves reachability THROUGH THE MENU TREE (not a direct URL),
so a future re-mis-parenting (or a dropped menu entry) is caught here rather than only
by the direct-URL test above, which would still pass either way.

### What it proves
The configuring user can open the main menu, drill into Finance -> Settings, and click
"VAT-ID Check Configuration" to land on window 542182 — i.e. the menu entry both EXISTS
and is parented under the correct business folder.

### Note on selectors
Menu items (\`MenuOverlayItem.js\`) carry no \`data-testid\`/id-bearing DOM attribute —
only the rendered caption text is selectable. This test therefore pins the login
language to en_US (matching this spec's other test and the suite default) and selects
on the English captions, scoped within \`.menu-overlay .js-menu-item\` to avoid matching
unrelated text elsewhere on the page.
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

    await test.step('Drill into Finance -> Settings', async () => {
      const financeItem = page.locator('.menu-overlay .js-menu-item', { hasText: 'Finance' }).first();
      await financeItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await financeItem.click();

      const settingsItem = page.locator('.menu-overlay .js-menu-item', { hasText: 'Settings' }).first();
      await settingsItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await settingsItem.click();
    });

    await test.step('Click "VAT-ID Check Configuration" and assert the window opens', async () => {
      const configItem = page.locator('.menu-overlay .js-menu-item', { hasText: 'VAT-ID Check Configuration' }).first();
      await configItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await configItem.click();

      await page.waitForURL(new RegExp(`/window/${VATID_CONFIG_WINDOW_ID}(/|$)`), { timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('.document-list-wrapper, .document-list').waitFor({
        state: 'visible',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });

      expect(page.url()).toContain(`/window/${VATID_CONFIG_WINDOW_ID}`);
      console.log(`[PASS] Reached VATaxID_Config window (${VATID_CONFIG_WINDOW_ID}) by browsing Finance -> Settings in the main menu`);
    });
  });
});

/**
 * Delete a record via the WebUI REST DELETE endpoint. Best-effort cleanup so
 * the partial-unique index (AD_Org_ID) WHERE IsActive='Y' does not block
 * re-runs. Swallows errors (e.g. record was never actually persisted).
 * Same pattern as view-invalidate-config-window.spec.js.
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
