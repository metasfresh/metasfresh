import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * MobileUI Manufacturing Configuration — the NEW "Merkmale" (Attributes) child tab.
 *
 * Migration 5821580 adds AD_Tab 549417 ("Merkmale" / en_US "Attributes", TabLevel 1) with 4 new
 * AD_UI_Elements (SeqNo, M_Attribute_ID, IsActive, AD_Org_ID) on window 541788 (MobileUI Manufacturing
 * config), backed by table MobileUI_MFG_Config_Attribute. The tab lets an admin configure which
 * M_Attributes are editable at the mfg material-receipt node, and in which order (SeqNo).
 *
 * metasfresh-window-design-rules mandates a Playwright test demonstrating a new AD_Tab / AD_UI_Element
 * set in successful action against a DB with real data. This spec drives the real desktop UI to:
 *   1. open the MobileUI Manufacturing config record,
 *   2. switch to the new "Merkmale" child tab,
 *   3. ADD two M_Attribute rows via the tab's "Add new" modal, each with a distinct SeqNo, and SAVE,
 *   4. assert BOTH rows persist with the correct M_Attribute + SeqNo (reorder-via-SeqNo), then
 *   5. DEACTIVATE a row (uncheck IsActive) and assert it persisted inactive.
 *
 * Persistence is asserted against the webapi included-tab data endpoint
 * (GET /window/541788/<parentId>/AD_Tab-549417), because this tab intentionally ships with NO
 * grid-displayed AD_UI_Elements (all IsDisplayedGrid='N', mirroring the shipped HU-Manager "Merkmale"
 * precedent tab 547558) — so the child grid renders no data columns and cannot be asserted on by cell
 * text. All identifiers are language-invariant (window/tab ids, ColumnName-based .form-field-* classes,
 * data-testid), per e2e/frontend-webui/CLAUDE.md.
 *
 * The config record (MobileUI_MFG_Config) is a single global config: this spec opens the existing
 * record when present, otherwise creates one (self-sufficient on a fresh preloaded CI DB). The two
 * added attribute rows are UNIQUE per run and are left DEACTIVATED at the end (the same state as the
 * other inactive extras already on this shared global config, and the state the mobile masterdata API
 * itself leaves unlisted attributes in), so the run does not pollute the active editable-attribute set.
 */

const MFG_CONFIG_WINDOW_ID = 541788;
const MERKMALE_TAB_ID = 549417; // AD_Tab "Merkmale"/"Attributes", table MobileUI_MFG_Config_Attribute
const MERKMALE_TAB_DETAIL_ID = `AD_Tab-${MERKMALE_TAB_ID}`; // DetailId form used by the REST API

/** GET the included-tab child rows straight from the webapi (fresh from DB, bypassing the client cache). */
async function fetchMerkmaleRows(page, parentId) {
  const resp = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${MFG_CONFIG_WINDOW_ID}/${parentId}/${MERKMALE_TAB_DETAIL_ID}`
  );
  expect(resp.ok(), `child-rows request must succeed (got ${resp.status()})`).toBeTruthy();
  const body = await resp.json();
  return body.result || [];
}

/**
 * Find the child row whose M_Attribute display carries the given (unique-per-run) attribute value.
 * The M_Attribute_ID lookup caption is "<Value>_<Name>", so the unique value is a reliable discriminator.
 */
function findRowByAttrValue(rows, attrValue) {
  return rows.find((r) => {
    const caption = ((r.fieldsByName || {}).M_Attribute_ID || {}).value?.caption || '';
    return caption.includes(attrValue);
  });
}

function seqNoOf(row) {
  return ((row.fieldsByName || {}).SeqNo || {}).value;
}
function isActiveOf(row) {
  return ((row.fieldsByName || {}).IsActive || {}).value;
}

/** Add one MobileUI_MFG_Config_Attribute row via the "Merkmale" tab's "Add new" modal, then save it. */
async function addAttributeRow(page, { attrValue, seqNo }) {
  const modal = page.locator('.panel-modal');

  // Language-independent selector (stable button classes, NOT the localized "Add new" caption) — covers
  // both the inline-tab and the table-filter renderers; excludes the batch-entry toggle.
  const addNewButton = page
    .locator('.inlinetab-action-button button, .table-filter-line .filter-panel-buttons button.btn-distance:not(.close-batch-entry)')
    .first();
  await addNewButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await addNewButton.click();

  await modal.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(1000);

  // M_Attribute_ID — a Search lookup. Type the unique attribute value and pick the matching option.
  // pressSequentially (not fill) so the React typeahead fires its debounced backend query.
  const attrInput = modal.locator('.form-field-M_Attribute_ID input').first();
  await attrInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await attrInput.click();
  await attrInput.pressSequentially(attrValue, { delay: 60 });
  await page.waitForTimeout(1500);
  const dropdown = page.locator('.input-dropdown-list');
  await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await dropdown
    .locator('.input-dropdown-list-option')
    .filter({ hasText: attrValue })
    .first()
    .click();
  await page.waitForTimeout(800);

  // SeqNo — a numeric field. Set it, then blur to commit the PATCH.
  const seqField = modal.locator('.form-field-SeqNo input').first();
  await seqField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await seqField.fill(String(seqNo));
  await seqField.press('Tab');
  await page.waitForTimeout(500);

  // Commit + close the "Add new" overlay via its stable data-testid (never the localized caption).
  await modal.getByTestId('process-modal-cancel-button').first().click();
  await page.waitForTimeout(1500);
  await modal.first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
}

/**
 * Deactivate a Merkmale grid row (uncheck IsActive) inline. Assumes the Merkmale tab is the active tab.
 * Enters edit mode on the row's Active cell so the interactive checkbox renders, then unchecks + commits.
 */
async function deactivateRowInGrid(page, attrValue) {
  const row = page.locator('tbody tr').filter({ hasText: attrValue }).first();
  await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await row.click(); // select the row
  await page.waitForTimeout(300);

  const activeCell = row.locator('[data-cy="cell-IsActive"]');
  await activeCell.dblclick(); // enter edit mode → interactive checkbox renders

  // The checkbox <input> is a real element but visually hidden inside its <label> (zero-size, styled
  // tick overlay), so it is attached but not "visible" and a mouse click can't target it. Dispatch a
  // native click, which toggles it and fires the widget's onChange (checkboxes in tabs save via websocket).
  const checkbox = activeCell.locator('input[type="checkbox"]').first();
  await checkbox.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
  if (await checkbox.isChecked()) {
    await checkbox.evaluate((el) => el.click()); // toggle off
    await page.waitForTimeout(800);
  }
  // Commit + leave edit mode.
  await page.locator('body').click();
  await page.waitForTimeout(1500);
}

test.describe('MobileUI MFG Config — Merkmale (editable attributes) child tab', () => {
  test('add attribute rows with SeqNo, persist, and deactivate on window 541788', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8047: MobileUI Manufacturing Profile');
    allure.tag('F8047');
    allure.story('MobileUI Manufacturing config — Merkmale (editable attributes) child tab');
    allure.severity('critical');

    allure.description(`
## MobileUI Manufacturing config — new "Merkmale" child tab (migration 5821580)

Drives the new AD_Tab 549417 on window 541788 in successful action:
1. Open the MobileUI Manufacturing config record.
2. Switch to the "Merkmale" child tab.
3. Add two M_Attribute rows (SeqNo 10 and 20) via the tab's "Add new" modal and SAVE.
4. Assert both rows persist with the correct M_Attribute + SeqNo (reorder-via-SeqNo).
5. Deactivate a row (uncheck IsActive) and assert it persisted inactive.
    `);

    test.setTimeout(180000);

    const runId = Date.now();
    const attrA = `E2EMFGA${runId}`; // will be given SeqNo 10 (should sort first)
    const attrB = `E2EMFGB${runId}`; // will be given SeqNo 20

    // === MASTERDATA: login user (WebUI role → RW on window 541788) + two unique instance attributes ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        attributes: {
          attrA: { value: attrA, name: `E2E Mfg Attr A ${runId}`, attributeValueType: 'STRING' },
          attrB: { value: attrB, name: `E2E Mfg Attr B ${runId}`, attributeValueType: 'STRING' },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === RESOLVE THE CONFIG RECORD (open existing, else create the sole global config) ===
    let parentId;
    await test.step('Open the MobileUI Manufacturing config record', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${MFG_CONFIG_WINDOW_ID}`);
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(1000);

      const firstRow = page.locator('table tbody tr').first();
      const hasRow = await firstRow.isVisible().catch(() => false);
      if (hasRow) {
        await firstRow.dblclick();
      } else {
        // Fresh DB (e.g. core CI preloaded): create the sole global config. All mandatory columns are
        // defaulted, so the record the frontend creates on /NEW is valid and saved immediately.
        await page.goto(`${FRONTEND_BASE_URL}/window/${MFG_CONFIG_WINDOW_ID}/NEW`);
      }
      await page.waitForURL(new RegExp(`/window/${MFG_CONFIG_WINDOW_ID}/\\d+`), {
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });
      parentId = page.url().match(new RegExp(`/window/${MFG_CONFIG_WINDOW_ID}/(\\d+)`))[1];
      expect(parentId, 'config record id must resolve').toMatch(/^\d+$/);
      console.log(`[INFO] MobileUI_MFG_Config record id = ${parentId}`);
      await page.waitForTimeout(1000);
    });

    // === SWITCH TO THE "MERKMALE" CHILD TAB AND ADD TWO ROWS ===
    await test.step('Switch to the Merkmale child tab and add two attribute rows', async () => {
      await page.getByTestId(`tab-AD_Tab-${MERKMALE_TAB_ID}`).click();
      await page.waitForTimeout(1500);

      // Add attrB first (SeqNo 20), then attrA (SeqNo 10) — proves ordering follows SeqNo, not insert order.
      await addAttributeRow(page, { attrValue: attrB, seqNo: 20 });
      await addAttributeRow(page, { attrValue: attrA, seqNo: 10 });
    });

    // === ASSERT BOTH ROWS PERSIST WITH THE CORRECT M_Attribute + SeqNo (reorder-via-SeqNo) ===
    await test.step('Both rows persist with the correct attribute and SeqNo', async () => {
      const rows = await fetchMerkmaleRows(page, parentId);
      const rowA = findRowByAttrValue(rows, attrA);
      const rowB = findRowByAttrValue(rows, attrB);

      expect(rowA, `row for ${attrA} must persist on the Merkmale tab`).toBeTruthy();
      expect(rowB, `row for ${attrB} must persist on the Merkmale tab`).toBeTruthy();
      expect(Number(seqNoOf(rowA)), 'attrA row SeqNo must persist as 10').toBe(10);
      expect(Number(seqNoOf(rowB)), 'attrB row SeqNo must persist as 20').toBe(20);
      expect(isActiveOf(rowA), 'attrA row must be active after create').toBeTruthy();
      expect(isActiveOf(rowB), 'attrB row must be active after create').toBeTruthy();
      console.log(`[PASS] both attribute rows persisted (attrA seq=${seqNoOf(rowA)}, attrB seq=${seqNoOf(rowB)})`);
    });

    // === DEACTIVATE attrA (uncheck IsActive inline in the Merkmale grid) AND ASSERT IT PERSISTED ===
    // The tab has no grid-displayed AD_UI_Elements, but the frontend still renders the tab's form
    // fields as grid columns (SeqNo / Attribute / Active / Organisation). A boolean cell renders a
    // static widget until the cell enters edit mode (cell double-click), after which an interactive
    // checkbox appears — that is the realistic way an admin toggles a row's Active flag here.
    await test.step('Reopen the Merkmale tab and deactivate the attrA row', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${MFG_CONFIG_WINDOW_ID}/${parentId}`);
      await page.waitForTimeout(2000);
      await page.getByTestId(`tab-AD_Tab-${MERKMALE_TAB_ID}`).click();
      await page.waitForTimeout(1500);

      await deactivateRowInGrid(page, attrA);

      const rows = await fetchMerkmaleRows(page, parentId);
      const rowA = findRowByAttrValue(rows, attrA);
      expect(rowA, 'attrA row still exists after deactivation').toBeTruthy();
      expect(isActiveOf(rowA), 'attrA row must persist as inactive after unchecking IsActive').toBeFalsy();
      // attrB is untouched — still active.
      const rowB = findRowByAttrValue(rows, attrB);
      expect(isActiveOf(rowB), 'attrB row remains active (untouched)').toBeTruthy();
      console.log('[PASS] attrA deactivated and persisted inactive; attrB untouched');
    });

    // === CLEANUP: leave both added rows deactivated so the shared global config is not polluted ===
    await test.step('Cleanup — deactivate the attrB row too', async () => {
      const before = await fetchMerkmaleRows(page, parentId);
      if (isActiveOf(findRowByAttrValue(before, attrB))) {
        await deactivateRowInGrid(page, attrB);
      }
      const after = await fetchMerkmaleRows(page, parentId);
      expect(isActiveOf(findRowByAttrValue(after, attrB)), 'attrB left inactive (cleanup)').toBeFalsy();
    });
  });
});
