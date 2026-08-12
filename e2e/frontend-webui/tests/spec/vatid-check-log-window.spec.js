import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * VAT-ID check-log window (table VATaxID_CheckLog).
 *
 * AD_Window_ID 542183, AD_Tab_ID 549365. An evidence/audit log of VAT-ID checks,
 * deliberately locked down: AD_Tab.IsReadOnly='Y', AD_Tab.IsInsertRecord='N',
 * AD_Table.IsDeleteable='N', and every column IsUpdateable='N'.
 *
 * Purpose of this spec: no production code writes check-log rows yet (the
 * consumer that performs the actual VAT-ID check arrives in a later task), so
 * the table has zero rows on every stack, including this one (verified via
 * `select count(*) from VATaxID_CheckLog` = 0 locally). This spec therefore does
 * NOT fabricate a row through a path the real system would never use — that
 * would only prove a state the system cannot actually reach. Instead it asserts
 * the properties that are true and valuable today:
 *   1. Menu reachability (same overlay-search mechanism as the sibling
 *      VATaxID_Config spec, for the same permission-independent reason).
 *   2. The window opens and its tab renders, with the grid-displayed columns
 *      the feature depends on actually present.
 *   3. The read-only guarantee holds in the rendered UI: the list-view's own
 *      "add new" affordance is absent, AND a direct attempt to reach the tab's
 *      new-record route does not result in a created record — proving the
 *      guarantee at the point where a user would try to break it, not merely
 *      by reading AD_Tab metadata.
 *
 * NOT asserted here, and why: per-field editability inside an open record
 * (AD_Column.IsUpdateable / the tab-wide IsReadOnly rendering fields disabled)
 * cannot be exercised in the rendered UI without an existing row to open — and
 * with zero rows, and no legitimate path to create one, there is no route to
 * an editable form at all. That absence of any reachable editable form is
 * itself the stronger form of the same guarantee, and is what test 2 below
 * proves via the failed create-attempt.
 *
 * KNOWN GAP — reverse-zoom (Alt+6 Related-Documents) reachability from
 * C_BPartner / C_BPartner_Location is NOT covered by any test in this file,
 * and cannot honestly be covered here. The acceptance criterion requires the
 * check-log to be "reachable from the partner and from the address" via the
 * standard Related-Documents zoom mechanism, enabled by flipping
 * AD_Column.IsExcludeFromZoomTargets='N' on VATaxID_CheckLog.C_BPartner_ID
 * (593172) and .C_BPartner_Location_ID (593173) — this part is metadata-only
 * verified today (confirmed by direct `psql` against `ad_table_related_windows_v`
 * returning a row for both source tables targeting window 542183; not
 * exercised by any automated test). It is NOT provable end-to-end via the
 * actual Alt+6 SSE surface: traced through
 * DocumentReferencesRestController.evaluateAndPublishNow ->
 * WebuiDocumentReferenceCandidate.evaluateAndStream ->
 * RelatedDocumentsCandidateGroup.evaluateOrNull, a candidate is dropped
 * whenever GenericRelatedDocumentsCountSupplier.getRecordsCount() (a real
 * `SELECT COUNT(1) FROM VATaxID_CheckLog WHERE C_BPartner_ID=...`) returns
 * <=0 — and the table has zero rows on every stack (see above), so that
 * count is always 0 and the candidate is always dropped before it can ever
 * reach the SSE stream. An earlier version of this file asserted
 * `targetWindowId":"542183"` would appear in that SSE stream; that assertion
 * was wrong — it cannot hold under any circumstances while the table is
 * empty, independent of any other defect — and has been removed rather than
 * kept as a permanently-red or fabricated-data test. Proving the UI-level
 * half of this acceptance criterion requires a real check-log row, which
 * requires the consumer that performs the actual VAT-ID check (a later
 * task, per the top-of-file note); the reverse-zoom E2E test belongs with
 * that task, once it can create a real row through the real system.
 *
 * Language independence: every assertion is on the DB ColumnName (via the grid
 * header's `data-testid="column-<Column>"`, set by TableHeader.js from the
 * field's language-invariant `field.field`), a structural CSS class, or the
 * language-invariant window id in the URL — never a caption. See
 * e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent".
 */

const VATID_CHECKLOG_WINDOW_ID = 542183;

// The complete set of AD_Field.IsDisplayedGrid='Y' columns on tab 549365,
// verified live against localhost:22532 (`select columnname from ad_field
// join ad_column ... where ad_tab_id=549365 and isdisplayedgrid='Y'`): all 8
// grid-displayed columns, including the two cornerstone fields (IsActive,
// AD_Org_ID) that a partial list would otherwise silently drop. These render
// as grid column headers regardless of whether any row exists. Every other
// column on the table (C_BPartner_Location_ID, ReturnedName, ReturnedAddress,
// RawResponse, AD_PInstance_ID, AD_Session_ID, TraderNameMatch,
// TraderAddressMatch, AD_Client_ID, Created/CreatedBy/Updated/UpdatedBy,
// VATaxID_CheckLog_ID) has IsDisplayedGrid='N' and is intentionally not
// asserted here: those only render inside an opened record, which does not
// exist yet.
const GRID_COLUMNS = [
  'IsActive',
  'C_BPartner_ID',
  'VATaxID',
  'VATaxIDStatus',
  'RequestDate',
  'ResponseDate',
  'RequestIdentifier',
  'AD_Org_ID',
];

test.describe('VAT-ID check-log window (542183)', () => {
  test('The check-log window is reachable via the main menu search (Alt+2 quick-nav)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VATaxID_CheckLog window (542183) — menu-tree reachability');
    allure.severity('critical');
    allure.tag('VATaxID_CheckLog');
    allure.description(`
## VATaxID_CheckLog menu placement (AD_Menu_ID 542357)

### Why this test exists

Proves the window is reachable THROUGH THE MENU SYSTEM (not only by direct URL),
so a dropped or mis-parented menu entry is caught here. AD_Menu_ID 542357 is a
direct child of the Finanzen folder (root position 13 of 22) — the quick-nav
popup's own top-level breadcrumb is hard-capped to the first 10 root branches
(\`HOME_MENU_USER_MAX_ITEMS\` in \`frontend/src/constants/Constants.js\`), a
pre-existing, permission-independent product behaviour under which this
window's parent folder can never appear for any role. The popup's own search
box is NOT subject to that cap (backed by the unlimited \`queryPathsRequest\`),
so this test drives that surface instead of the capped breadcrumb — the same
mechanism and reasoning as \`vatid-check-config-window.spec.js\`'s menu test.

### What it proves

The configuring user can open the main menu (Alt+2), type the window's name
into the overlay's own search box, and click the single matching result to
land on window 542183.

### Note on selectors

The search input and result item are matched structurally
(\`.menu-overlay-query input.input-field\` / \`.menu-overlay-query .js-menu-item\`)
— neither carries a \`data-testid\`, per \`MenuOverlayItem.js\`. The window's
display name is used as the search **input** (not an assertion), pinning the
login language to en_US. The end result is asserted only on the
language-invariant window id in the URL and a structural DOM marker.
    `);

    test.setTimeout(60000);

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'VatidCheckLogMenu' } } },
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

    await test.step('Search the overlay for the window by name and open the single result', async () => {
      const searchInput = page.locator('.menu-overlay-query input.input-field');
      await searchInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      // Input value only (not an assertion) — the window's own display name.
      await searchInput.fill('VAT-ID Check Log');

      const resultItem = page.locator('.menu-overlay-query .js-menu-item').first();
      await resultItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await resultItem.click();

      await page.waitForURL(new RegExp(`/window/${VATID_CHECKLOG_WINDOW_ID}(/|$)`), { timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('.document-list-wrapper, .document-list').waitFor({
        state: 'visible',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });

      expect(page.url()).toContain(`/window/${VATID_CHECKLOG_WINDOW_ID}`);
      console.log(`[PASS] Reached VATaxID_CheckLog window (${VATID_CHECKLOG_WINDOW_ID}) via the main menu overlay search`);
    });
  });

  test('The window opens with its expected grid columns, and the read-only/no-create guarantee holds', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VATaxID_CheckLog window (542183) — renders + read-only guarantee');
    allure.severity('critical');
    allure.tag('VATaxID_CheckLog');
    allure.description(`
## VATaxID_CheckLog window (AD_Window_ID 542183 / AD_Tab_ID 549365)

### Why this test exists

Closes a mandatory Playwright-coverage gap for a newly created WebUI window
(metasfresh-window-design-rules § "Verification — MANDATORY Playwright test").
The table is a deliberately locked-down evidence/audit log
(AD_Tab.IsReadOnly='Y', AD_Tab.IsInsertRecord='N', AD_Table.IsDeleteable='N',
every column IsUpdateable='N') and currently has zero rows on every stack (no
production code writes to it yet). This spec proves the properties that are
true today without fabricating a row the real system cannot yet produce.

### What it proves

1. The window opens and the tab (list view) renders.
2. Every grid-displayed column on the tab (IsActive, C_BPartner_ID, VATaxID,
   VATaxIDStatus, RequestDate, ResponseDate, RequestIdentifier, AD_Org_ID)
   is present as a rendered grid column header.
3. The read-only/no-insert guarantee holds in the rendered UI: the list-view's
   "add new" affordance (gated by \`allowCreateNew\`, itself sourced from
   AD_Tab.IsInsertRecord) is absent, AND a direct attempt to navigate to the
   tab's own new-record route does not result in a created record — proving
   the guarantee is enforced at the point of use, not only in AD metadata.
    `);

    test.setTimeout(90000);

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'VatidCheckLogWindow' } } },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    await test.step('Open the check-log window and assert the list view renders', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${VATID_CHECKLOG_WINDOW_ID}`);
      await page.locator('.document-list-wrapper, .document-list').waitFor({
        state: 'visible',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      expect(page.url()).toContain(`/window/${VATID_CHECKLOG_WINDOW_ID}`);
      console.log(`[PASS] VATaxID_CheckLog window (${VATID_CHECKLOG_WINDOW_ID}) list view loaded`);
    });

    await test.step('Assert every grid-displayed column is rendered as a column header', async () => {
      for (const columnName of GRID_COLUMNS) {
        const header = page.locator(`[data-testid="column-${columnName}"]`);
        await expect(header, `Grid column header for ${columnName} must be rendered`).toBeVisible({
          timeout: SLOW_ACTION_TIMEOUT,
        });
        console.log(`[PASS] Grid column header rendered: ${columnName}`);
      }
    });

    await test.step('Assert the list-view "add new" affordance is absent (AD_Tab.IsInsertRecord=N)', async () => {
      // TableFilter.js renders the "add new" button as
      // `<button class="btn btn-meta-outline-secondary btn-distance btn-sm">`
      // only when `allowCreateNew` is true; the sibling batch-entry button is
      // the only other `.btn-distance` in this toolbar and is distinguished by
      // its own `.close-batch-entry` class — so this selector isolates exactly
      // the create-new button, purely structurally (no caption/text involved).
      const addNewButton = page.locator('.filter-panel-buttons button.btn-distance:not(.close-batch-entry)');
      await expect(addNewButton, 'No "add new" button must be rendered for a no-insert tab').toHaveCount(0);
      console.log('[PASS] No "add new" affordance rendered in the list-view toolbar');
    });

    await test.step('Assert a direct attempt to reach the new-record route does not create a record', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${VATID_CHECKLOG_WINDOW_ID}/new`);
      // Give the app every chance to complete a create round-trip if it were
      // (incorrectly) allowed to — then assert it never landed on a
      // created-record URL (`/window/542183/<numeric id>`).
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(2000);

      const createdRecordUrl = new RegExp(`/window/${VATID_CHECKLOG_WINDOW_ID}/\\d+`);
      expect(page.url(), 'The new-record route must never settle on a created-record id for a no-insert tab').not.toMatch(
        createdRecordUrl
      );
      console.log(`[PASS] Navigating to the new-record route did not create a record (final URL: ${page.url()})`);
    });

    console.log('[PASS] VATaxID_CheckLog window (542183): renders with its grid columns, no create affordance, no-insert guarantee holds.');
  });

  // NOTE: There is intentionally no third test here asserting reverse-zoom
  // (Alt+6 Related-Documents) reachability from a Business Partner. See the
  // "KNOWN GAP" paragraph in the file-level comment above for why: the
  // Alt+6 SSE endpoint gates every candidate on a live
  // `SELECT COUNT(1) FROM VATaxID_CheckLog ...` and drops it when that count
  // is 0, which it always is on this branch, so no assertion against that
  // SSE stream can ever pass here — not a flake, not conditional on the
  // unrelated Backend.createMasterdata timing issue, but unsatisfiable by
  // construction while the table is empty. Proving this acceptance-criterion
  // clause end-to-end needs a real check-log row, which needs the consumer
  // that performs the actual VAT-ID check — that arrives in a later task,
  // and the reverse-zoom E2E test belongs there, not here.
});
