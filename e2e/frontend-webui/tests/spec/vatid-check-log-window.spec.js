import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { BusinessPartnerPage } from '../utils/pages/BusinessPartnerPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * VAT-ID check-log window (table VATaxID_CheckLog).
 *
 * AD_Window_ID 542183, AD_Tab_ID 549365. An evidence/audit log of VAT-ID checks,
 * deliberately locked down: AD_Tab.IsReadOnly='Y', AD_Tab.IsInsertRecord='N',
 * AD_Table.IsDeleteable='N', and every column IsUpdateable='N'.
 *
 * Purpose of this spec: no production trigger writes check-log rows yet (the
 * consumer that automatically performs a VAT-ID check on save arrives in a
 * later task), so the table has zero rows on every stack by default. Tests 1
 * and 2 below deliberately do NOT fabricate a row through a path the real
 * system would never use — that would only prove a state the system cannot
 * actually reach — and instead assert the properties that are true and
 * valuable on an empty table:
 *   1. Menu reachability (same overlay-search mechanism as the sibling
 *      VATaxID_Config spec, for the same permission-independent reason).
 *   2. The window opens and its tab renders, with the grid-displayed columns
 *      the feature depends on actually present.
 *   3. The read-only guarantee holds in the rendered UI: the list-view's own
 *      "add new" affordance is absent, AND a direct attempt to reach the tab's
 *      new-record route does not result in a created record — proving the
 *      guarantee at the point where a user would try to break it, not merely
 *      by reading AD_Tab metadata.
 * Test 3 below is different: it seeds one real row through
 * VATaxIDCheckRepository.writeRequestSent(...) — the actual production
 * writer, not raw SQL — via the `vatIdChecks` masterdata command, because
 * that write path already exists on this branch independent of the later
 * automatic-trigger task (see the KNOWN GAP paragraph below).
 *
 * NOT asserted here, and why: per-field editability inside an open record
 * (AD_Column.IsUpdateable / the tab-wide IsReadOnly rendering fields disabled)
 * cannot be exercised in the rendered UI without an existing row to open — and
 * with zero rows, and no legitimate path to create one, there is no route to
 * an editable form at all. That absence of any reachable editable form is
 * itself the stronger form of the same guarantee, and is what test 2 below
 * proves via the failed create-attempt.
 *
 * KNOWN GAP — reverse-zoom (Alt+6 Related-Documents) is proven here only for
 * C_BPartner, not for C_BPartner_Location ("the address"). The acceptance
 * criterion requires the check-log to be "reachable from the partner and
 * from the address" via the standard Related-Documents zoom mechanism,
 * enabled by flipping AD_Column.IsExcludeFromZoomTargets='N' on
 * VATaxID_CheckLog.C_BPartner_ID (593172) and .C_BPartner_Location_ID
 * (593173). The Alt+6 SSE endpoint gates every candidate on a real
 * `SELECT COUNT(1) FROM VATaxID_CheckLog WHERE C_BPartner_ID=...`
 * (DocumentReferencesRestController.evaluateAndPublishNow ->
 * WebuiDocumentReferenceCandidate.evaluateAndStream ->
 * RelatedDocumentsCandidateGroup.evaluateOrNull ->
 * GenericRelatedDocumentsCountSupplier.getRecordsCount()), so an empty table
 * can never satisfy that assertion — that is NOT a later-task limitation:
 * VATaxIDCheckRepository.writeRequestSent(...) already exists on this
 * branch, is unit-tested, and needs no VIES call, so a real check-log row
 * can be (and below, is) seeded today through the production writer via the
 * `vatIdChecks` masterdata command, exactly the way the eventual VAT-ID-check
 * consumer will create one. What genuinely remains unprovable here is the
 * address half: the desktop WebUI's Alt+6 shortcut (SideList.js ->
 * DocumentReferences.js) always queries at the HEADER document level
 * (windowId/docId from the page's own URL), never wiring a tabId/rowId into
 * the request, and C_BPartner_Location has no standalone window of its own
 * (verified: no AD_Window with a tab at tablevel=0 for that table) — so there
 * is no Alt+6-reachable page for "the address" as its own document. The
 * row-level SSE endpoint (`/{windowId}/{documentId}/{tabId}/{rowId}/references/sse`)
 * exists and is used, but only by the grid's right-click context menu
 * (TableContextMenu.js), a different UI surface than Alt+6, and is not
 * driven by this spec. Live DB confirms the metadata-level mechanism also
 * covers the address (`ad_table_related_windows_v` returns a row for
 * source_tablename='C_BPartner_Location' targeting window 542183, same as
 * for C_BPartner) — only the end-to-end UI proof is missing.
 *
 * Language independence: every assertion is on the DB ColumnName (via the grid
 * header's `data-testid="column-<Column>"`, set by TableHeader.js from the
 * field's language-invariant `field.field`), a structural CSS class, or the
 * language-invariant window id in the URL — never a caption. See
 * e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent".
 */

const VATID_CHECKLOG_WINDOW_ID = 542183;

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
 * this window's name match BOTH VATaxID_Config (542182) and VATaxID_CheckLog
 * (542183), with 542182 sorted first in the AD_Menu tree — so a `.first()`
 * click during any transiently-rendered ambiguous/partial result set would
 * silently land on the wrong window. Keying on the elementId the response
 * itself carries is correct regardless of how many entries render or in what
 * order — order-independent by construction, not by picking a longer search
 * string (which only narrows, never eliminates, the underlying ambiguity).
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
login language to en_US. The result item to click is located by its position
in the backend's own \`/menu/queryPaths\` response (matched on \`elementId\`),
never by DOM position (\`.first()\`) — "VAT-ID Check" and shorter prefixes of
this window's name also match the sibling VATaxID_Config window (542182),
sorted first in the AD_Menu tree, so a positional click is order-dependent.
The end result is asserted only on the language-invariant window id in the
URL and a structural DOM marker.
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

    await test.step('Search the overlay for the window by name and open the matching result', async () => {
      // Input value only (not an assertion) — the window's own display name.
      await clickMenuOverlaySearchResult(page, 'VAT-ID Check Log', VATID_CHECKLOG_WINDOW_ID);

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

  test('The check-log window is offered as a related-document zoom target when opening a Business Partner with a real check-log row', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.story('VATaxID_CheckLog window (542183) — reverse-zoom reachability from C_BPartner');
    allure.severity('critical');
    allure.tag('VATaxID_CheckLog');
    allure.description(`
## VATaxID_CheckLog reverse-zoom reachability (AD_Column_ID 593172, C_BPartner_ID)

### Why this test exists

The check-log's acceptance criterion requires every check attempt to be
"reachable from the partner and from the address" via the standard
Related-Documents (Alt+6) zoom mechanism, enabled by flipping
\`AD_Column.IsExcludeFromZoomTargets='N'\` on \`VATaxID_CheckLog.C_BPartner_ID\`
(593172) and \`VATaxID_CheckLog.C_BPartner_Location_ID\` (593173). The Alt+6
SSE endpoint only ever offers a candidate whose backing table actually has a
matching row (a live \`SELECT COUNT(1)\`), so this test seeds one through the
masterdata \`vatIdChecks\` command — which itself only wraps the real
production writer \`VATaxIDCheckRepository.writeRequestSent(...)\` (no VIES
call, no raw SQL) — before opening the partner.

### What it proves — and what it does not

Creates a Business Partner plus a check-log row for that partner, opens the
partner record, presses Alt+6 (the same shortcut and DOM surface
\`document-references.spec.js\` uses for order documents), captures the
backend's Server-Sent-Events response for that panel, and asserts that one of
the streamed reference entries carries \`targetWindowId: "542183"\` — i.e. the
VATaxID_CheckLog window is genuinely offered as a zoom target for a Business
Partner that actually has a check-log row.

**Only the partner half is covered here.** See the file-level "KNOWN GAP"
comment above: the desktop WebUI's Alt+6 shortcut never wires a row-level
address document into the request, so the address half of this
acceptance-criterion clause remains untested by Playwright.

### Language independence

The SSE payload asserted on is the raw JSON contract
(\`JSONDocumentReference.targetWindowId\`), never the rendered caption or the
\`data-cy\` (which — for this window specifically — would embed the window's
German \`AD_Window.Name\`, since no \`AD_Window.InternalName\` is set for
542183). No caption/label is read or asserted anywhere in this test.
    `);

    // This stack's bpartner masterdata creation has been observed taking
    // 60-90s+ on its own (see the app-server log's cache-reset gaps around
    // each Backend.createMasterdata bpartners call) — well above the
    // config-default 60s, so this test raises its own timeout per the
    // playwright-run skill's "multi-step spec must raise its own per-test
    // timeout" rule.
    test.setTimeout(180000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US', firstname: 'E2E', lastname: 'VatidZoomTarget' } },
        bpartners: {
          PARTNER1: { isVendor: false, isCustomer: true, name: 'E2E VatidZoomTarget Partner' },
        },
        vatIdChecks: {
          CHECK1: { bpartner: 'PARTNER1' },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const bpartnerId = masterdata.bpartners.PARTNER1.id;
    expect(bpartnerId, 'Masterdata must return the created BPartner id').toBeTruthy();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await LoginPage.expectLoggedIn();

    await BusinessPartnerPage.gotoRecord(bpartnerId);

    // The browser's own EventSource client closes the underlying connection once it has what it
    // needs, which the network stack reports as an aborted request — and an aborted request's body
    // is not retrievable via a `page.on('response')` listener's `.text()` (it resolves empty).
    // Route interception avoids the race: `route.fetch()` performs OUR OWN buffered fetch of the
    // exact same request (same session cookie, same URL) and hands the real, complete body back to
    // the page via `route.fulfill()`, so the UI behaves identically while we keep a reliable copy.
    let sseBody = '';
    let sseSeen = false;
    await page.route('**/references/sse', async (route) => {
      const response = await route.fetch();
      sseBody = await response.text();
      sseSeen = true;
      await route.fulfill({ response });
    });

    await test.step('Open the Related Documents panel (Alt+6) on the Business Partner record', async () => {
      await page.locator('body').click();
      await page.waitForTimeout(200);
      await page.keyboard.press('Alt+6');
      await page.locator('.order-list-panel-open').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Wait for the intercepted references SSE response to be captured', async () => {
      const deadline = Date.now() + VERY_SLOW_ACTION_TIMEOUT;
      while (!sseSeen && Date.now() < deadline) {
        await page.waitForTimeout(500);
      }
    });

    await page.unroute('**/references/sse');

    const checkLogTargetSeen = sseBody.includes(`"targetWindowId":"${VATID_CHECKLOG_WINDOW_ID}"`);

    expect(
      checkLogTargetSeen,
      `The Related-Documents SSE stream for Business Partner ${bpartnerId} must include a reference with targetWindowId="${VATID_CHECKLOG_WINDOW_ID}" (VATaxID_CheckLog)`
    ).toBe(true);
    console.log(`[PASS] VATaxID_CheckLog (${VATID_CHECKLOG_WINDOW_ID}) offered as a related-document zoom target for Business Partner ${bpartnerId}, backed by a real check-log row`);
  });
});
