import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { FRONTEND_BASE_URL } from '../utils/common';

/**
 * Confirm the dashboard rendered, without gating on networkidle: the dashboard's STOMP/websocket +
 * KPI polling keep the network permanently active, so DashboardPage.expectVisible() (which awaits
 * page.waitForLoadState('networkidle')) never settles here and times out -- see the playwright-run
 * skill's "Writing steps that don't flake" section. A deterministic DOM signal is used instead.
 */
async function expectDashboardVisible(page) {
  await page.locator('.app-content, .dashboard').waitFor({ state: 'visible', timeout: 20000 });
}

/**
 * Auftrags-Board -> Traffic Management jump.
 *
 * The Auftrags-Board's Uebersicht tab aggregates M_Picking_Job_Schedule_view rows by product / UOM /
 * delivery date / delivery country / client / org (M_Picking_OrderBoard_Overview_v). This spec proves
 * that selecting one or more board rows and running the (not-yet-existing) jump quick action opens
 * Traffic Management scoped to exactly those rows' schedules -- including rows that are already fully
 * assigned to a workplace, which is exactly the case the customer originally reported as an empty grid.
 *
 * RED-by-construction: the AD_Process / AD_RelationType / AD_Table_Process wiring that offers the jump
 * on the board does not exist yet (a follow-up migration script adds it). Tests 1-4 are therefore
 * expected to FAIL because the Aktionen dropdown never offers
 * [data-testid="quick-action-M_Picking_OrderBoard_Overview_v_to_TrafficManagement"]. Test 5 does not
 * depend on that action at all -- it guards the Traffic Management window's OWN default filter and must
 * pass from the start.
 *
 * Everything is asserted through data-testid / data-cy / REST JSON field names -- never localized UI
 * text (e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent").
 */

const ORDER_BOARD_WINDOW_ID = 542168; // AD_Window "Auftrags-Board", Uebersicht tab (root), table M_Picking_OrderBoard_Overview_v (542626)
const TRAFFIC_MANAGEMENT_WINDOW_ID = 541929; // AD_Window "Traffic Management", table M_Picking_Job_Schedule_view (542514)

// AD_Process.Value the follow-up migration is required to use so this data-testid is stable.
// QuickActionsDropdown.js renders data-testid="quick-action-" + (action.internalName || action.processId).
const JUMP_ACTION_TESTID = 'quick-action-M_Picking_OrderBoard_Overview_v_to_TrafficManagement';

// frontend/src/utils/documentListHelper.js DEFAULT_PAGE_LENGTH -- the grid page size a window falls
// back to when its own layout does not set one (neither the board nor Traffic Management does here).
const GRID_DEFAULT_PAGE_LENGTH = 15;

/**
 * Poll a grid view (board or Traffic Management) until a row matching `predicate` appears.
 *
 * Neither window has a server-side "default" filter PARAMETER to narrow the fetch to one product
 * (verified against the running stack: the board's Uebersicht tab has no AD_Field marked
 * IsFilterField='Y'), so -- unlike the Material Cockpit v2 / forecast specs -- a single unfiltered
 * fetch (pageLength=500) is used to LOCATE the row via REST. That fetch's order matches what the grid
 * renders (verified: a requested `orderBy` override on the create-view call is silently ignored -- the
 * response always echoes back M_Picking_OrderBoard_Overview_v_ID ascending), so the row's index in it
 * also tells us which GRID PAGE (GRID_DEFAULT_PAGE_LENGTH per page) the UI will show it on -- see
 * `pageNumber` below and selectRow(). Both views can and do accumulate more than one page's worth of
 * rows over a spec run (each test seeds its own uniquely-named product), so this must not assume page 1.
 */
async function waitForViewRow(page, windowId, predicate, { timeout = 60000 } = {}) {
  const deadline = Date.now() + timeout;
  while (Date.now() < deadline) {
    const view = await page.evaluate(async ({ windowId }) => {
      // config.API_URL is the SAME base URL the app's own redux/api layer uses (set by
      // config.js) -- a bare relative "/rest/api/..." only works when the frontend origin proxies
      // that path (e.g. the webpack dev server); a statically-served production build does not, and
      // silently answers with index.html (SyntaxError: Unexpected token '<' when parsed as JSON).
      const apiUrl = config.API_URL;
      const created = await fetch(`${apiUrl}/documentView/${windowId}`, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify({ windowId: String(windowId), viewType: 'grid' }),
      });
      if (!created.ok) return null;
      const { viewId } = await created.json();
      const rows = await fetch(`${apiUrl}/documentView/${windowId}/${viewId}?firstRow=0&pageLength=500`, {
        credentials: 'include',
        headers: { Accept: 'application/json' },
      });
      if (!rows.ok) return null;
      const body = await rows.json();
      return { viewId, result: body.result };
    }, { windowId });

    const rowIndex = view?.result?.findIndex(predicate) ?? -1;
    if (rowIndex >= 0) {
      const row = view.result[rowIndex];
      // The grid renders GRID_DEFAULT_PAGE_LENGTH rows per page (frontend/src/utils/documentListHelper.js
      // DEFAULT_PAGE_LENGTH); the Overview/Traffic Management views accept no client-requested `orderBy`
      // override (verified against the running stack -- the create-view response always echoes back
      // M_Picking_OrderBoard_Overview_v_ID ascending regardless of what is requested), so a freshly-seeded
      // row can land on any page once this window accumulates more than one page's worth of rows. Compute
      // which page it is on so selectRow() can navigate there instead of assuming page 1.
      const pageNumber = Math.floor(rowIndex / GRID_DEFAULT_PAGE_LENGTH) + 1;
      return { viewId: view.viewId, rowId: row.id, row, pageNumber };
    }
    await page.waitForTimeout(2000);
  }
  throw new Error(`No row matched within ${timeout}ms on window ${windowId}`);
}

const byProduct = (productId) => (r) => String(r.fieldsByName?.M_Product_ID?.value?.key) === String(productId);

/** Poll the Auftrags-Board Uebersicht view until a row for `productId` appears. */
async function waitForBoardRow(page, productId, opts) {
  return waitForViewRow(page, ORDER_BOARD_WINDOW_ID, byProduct(productId), opts);
}

/**
 * Create a NEW board view scoped to exactly `rowIds`, via the generic `filterOnlyIds` mechanism
 * (JSONCreateViewRequest.filterOnlyIds -> SqlViewFactory: a server-side sticky `keyColumn IN (...)`
 * filter -- de.metas.ui.web.base SqlViewFactory.java "if (!request.getFilterOnlyIds().isEmpty())";
 * already used the same way by picking-terminal.spec.js). Needed because the Uebersicht tab exposes
 * no AD_Field filter parameter to narrow by product (see waitForViewRow()), so two freshly-seeded rows
 * can otherwise land on different grid pages once the board accumulates more than
 * GRID_DEFAULT_PAGE_LENGTH rows from earlier runs. A view containing only `rowIds` makes their page
 * co-location deterministic (both always fit on page 1) regardless of how many unrelated rows exist
 * in the unfiltered board view. This is a server-side filter on the SAME underlying table/rows, not a
 * different data set -- RelationTypeInOverlayProcess#getSelectedSourceRecordRefs() resolves the
 * selected records from the AD_PInstance selection where-clause, not from the view's own filters, so
 * selecting from this filtered view is equivalent to selecting from the unfiltered one.
 */
async function createFilteredBoardView(page, rowIds) {
  return page.evaluate(async ({ windowId, rowIds }) => {
    const apiUrl = config.API_URL; // see waitForViewRow() for why an absolute base URL is required
    const created = await fetch(`${apiUrl}/documentView/${windowId}`, {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
      body: JSON.stringify({ windowId: String(windowId), viewType: 'grid', filterOnlyIds: rowIds }),
    });
    if (!created.ok) throw new Error(`create filtered board view failed: ${created.status}`);
    const { viewId } = await created.json();
    return viewId;
  }, { windowId: ORDER_BOARD_WINDOW_ID, rowIds });
}

/**
 * Poll Traffic Management's OWN default view (its "not assigned" filter -- see test 5 -- already shows
 * a freshly-created, not-yet-assigned schedule) until the row for `productId` has a resolved
 * Carrier_Product_ID. Needed before the "Schedule" quick action can run: assigning a workplace requires
 * the schedule's carrier already resolved (CreateOrUpdatePickingJobSchedulesCommand.assumeCarrierProductSet,
 * gated by AD_SysConfig de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet='Y' on this
 * stack) -- and that resolution runs on a separate async workpackage
 * (AdviseDeliveryOrderWorkpackageProcessor), so it is NOT guaranteed to have completed by the time the
 * seeding request returns (verified: reliably present for a single-line order, but a race for a
 * multi-line one -- this poll removes the race instead of depending on incidental timing).
 */
async function waitForScheduleCarrierResolved(page, productId, opts) {
  return waitForViewRow(
    page,
    TRAFFIC_MANAGEMENT_WINDOW_ID,
    (r) => byProduct(productId)(r) && !!r.fieldsByName?.Carrier_Product_ID?.value?.key,
    opts
  );
}

/** Navigate to a window/view and select a single row (mirrors material-cockpit-v2-jump-preconditions.spec.js). */
async function selectRow(page, windowId, viewId, rowId, { pageNumber = 1 } = {}) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}?viewId=${viewId}`);
  if (pageNumber > 1) {
    await goToGridPage(page, pageNumber);
  }
  const row = page.locator(`tbody tr[data-testid="table-row-${rowId}"]`);
  await row.waitFor({ state: 'visible', timeout: 30000 });
  // A click right after the grid renders does not always land as a selection -- assert it, retrying.
  await expect(async () => {
    await row.click();
    await expect(row).toHaveClass(/row-selected/, { timeout: 2000 });
  }).toPass({ timeout: 30000 });
  return row;
}

/** Board-scoped convenience wrapper around selectRow(). */
async function selectBoardRow(page, viewId, rowId, opts) {
  return selectRow(page, ORDER_BOARD_WINDOW_ID, viewId, rowId, opts);
}

/**
 * Navigate the currently-loaded grid to `pageNumber` (1-based). Needed because a freshly-seeded row can
 * land beyond page 1 once a view accumulates more than GRID_DEFAULT_PAGE_LENGTH rows -- see
 * waitForViewRow(). TablePagination.js renders every page number directly (no "..." compression) for
 * up to 7 pages (`pages < 8`); this deliberately does not handle the compressed/goToPage case, since 7
 * pages (105 rows) is well beyond what this spec's own seeding accumulates in the window's lifetime.
 */
async function goToGridPage(page, pageNumber) {
  const pageLink = page.locator('.pagination-wrapper li.page-item a').filter({ hasText: new RegExp(`^${pageNumber}$`) });
  await pageLink.first().click({ timeout: 15000 });
}

/**
 * Navigate to the board and select TWO rows via ctrl-click (Table.js:191 `e.metaKey || e.ctrlKey` ->
 * "select more"). No precedent for multi-row selection exists anywhere in e2e/frontend-webui (both
 * material-cockpit-v2-jump-preconditions.spec.js and forecast-overlay-qty.spec.js select a single row);
 * this gesture was derived from frontend/src/components/table/Table.js and is verified by this test run
 * itself (both rows must show the row-selected class, independent of whether the jump action exists).
 */
async function selectTwoBoardRows(page, viewId, rowIdFirst, rowIdSecond, { pageNumberFirst = 1, pageNumberSecond = 1 } = {}) {
  // Both rows must render on the SAME grid page for a ctrl-click to add the second to the first's
  // selection -- selection state is not verified to survive a page-to-page navigation in this suite
  // (no precedent), so a cross-page pair is a genuine limitation of this helper, surfaced explicitly
  // rather than silently attempted. See waitForViewRow() for why a row's page can vary at all.
  if (pageNumberFirst !== pageNumberSecond) {
    throw new Error(
      `Cannot multi-select: row ${rowIdFirst} is on grid page ${pageNumberFirst} but row ${rowIdSecond} is on page ${pageNumberSecond}. ` +
      'This helper only supports ctrl-clicking two rows on the same page.'
    );
  }

  await page.goto(`${FRONTEND_BASE_URL}/window/${ORDER_BOARD_WINDOW_ID}?viewId=${viewId}`);
  if (pageNumberFirst > 1) {
    await goToGridPage(page, pageNumberFirst);
  }

  const firstRow = page.locator(`tbody tr[data-testid="table-row-${rowIdFirst}"]`);
  await firstRow.waitFor({ state: 'visible', timeout: 30000 });
  await expect(async () => {
    await firstRow.click();
    await expect(firstRow).toHaveClass(/row-selected/, { timeout: 2000 });
  }).toPass({ timeout: 30000 });

  const secondRow = page.locator(`tbody tr[data-testid="table-row-${rowIdSecond}"]`);
  await secondRow.waitFor({ state: 'visible', timeout: 30000 });
  await expect(async () => {
    await secondRow.click({ modifiers: ['Control'] });
    await expect(secondRow).toHaveClass(/row-selected/, { timeout: 2000 });
  }).toPass({ timeout: 30000 });

  // The ctrl-click must ADD to the selection, not replace it -- assert the first row is still selected.
  await expect(firstRow, 'ctrl-click on a second row must keep the first row selected too').toHaveClass(/row-selected/);

  return { firstRow, secondRow };
}

/** Open the Aktionen (quick-actions) dropdown for the currently selected row(s). */
async function openQuickActionsDropdown(page) {
  // The whole quick-actions bar (including this toggle) renders only when actions.length > 0
  // (frontend/src/components/app/QuickActions.js:155). The board currently has ZERO AD_Table_Process
  // rows for table 542626 (verified against the running stack), so before the jump is wired the toggle
  // never appears at all -- an explicit, short timeout here turns that into a fast, clear failure
  // instead of hanging until the enclosing test's own (120s) timeout.
  await page.locator('[data-testid="quick-action-dropdown-toggle"]').first().click({ timeout: 10000 });
  const dropdown = page.locator('.quick-actions-dropdown');
  await dropdown.waitFor({ state: 'visible', timeout: 15000 });
  return dropdown;
}

// AD_Process.Value of Traffic Management's OWN, pre-existing "Schedule" quick action
// (PickingJobScheduleView_Schedule, AD_Process_ID 585493, AD_Table_Process on table 542514) -- unrelated
// to the jump this spec is testing; used only as test SETUP, to reach a genuinely assigned schedule
// exactly the way a real Traffic Management user would (rather than via the frontend-testing masterdata
// API's `workplace` shortcut, whose own createSchedules() call races the same async carrier-advise
// workpackage this setup already waits out -- see waitForScheduleCarrierResolved()).
const SCHEDULE_ACTION_TESTID = 'quick-action-PickingJobScheduleView_Schedule';

/**
 * Assign the given Traffic Management row to `workplaceName` via the window's own "Schedule" quick
 * action -- a NEW pattern in this suite (no existing spec fills a process-parameter dialog). Process
 * parameters render through the same WidgetWrapper machinery as window fields
 * (frontend/src/components/Process.js -> dataSource="process"), so the lookup fill/select gesture is
 * identical to a window field (`#lookup_<Column> input` + pick the first `.input-dropdown-list-option`,
 * as compensation-group-bundle.spec.js already does for a window's own product lookup); the modal's
 * start button carries a stable data-testid (frontend/src/components/app/Modal.js:743).
 */
async function assignWorkplaceViaScheduleAction(page, viewId, rowId, workplaceName, { pageNumber = 1 } = {}) {
  await selectRow(page, TRAFFIC_MANAGEMENT_WINDOW_ID, viewId, rowId, { pageNumber });
  const dropdown = await openQuickActionsDropdown(page);
  const entry = dropdown.getByTestId(SCHEDULE_ACTION_TESTID);
  await expect(entry, `${SCHEDULE_ACTION_TESTID} must be offered on Traffic Management (pre-existing action)`).toBeVisible({ timeout: 10000 });
  await entry.click();

  const startButton = page.locator('[data-testid="process-modal-start-button"]');
  await startButton.waitFor({ state: 'visible', timeout: 15000 });

  const workplaceField = page.locator('#lookup_C_Workplace_ID input');
  await workplaceField.waitFor({ state: 'visible', timeout: 15000 });
  await workplaceField.fill(workplaceName);
  await page.waitForTimeout(800);
  await page.locator('.input-dropdown-list-option').first().click();
  await page.waitForTimeout(500);

  await startButton.click();
  await startButton.waitFor({ state: 'detached', timeout: 20000 });
}

/**
 * Fetch the rows of an already-created view via REST (used to inspect the Traffic Management grid the
 * jump opens, whose viewId we learn from the intercepted documentView response).
 */
async function fetchViewRows(page, windowId, viewId) {
  return page.evaluate(async ({ windowId, viewId }) => {
    const apiUrl = config.API_URL; // see waitForBoardRow() for why an absolute base URL is required
    const resp = await fetch(`${apiUrl}/documentView/${windowId}/${viewId}?firstRow=0&pageLength=500`, {
      credentials: 'include',
      headers: { Accept: 'application/json' },
    });
    if (!resp.ok) throw new Error(`fetch view rows failed: ${resp.status}`);
    return resp.json();
  }, { windowId, viewId });
}

/**
 * Click the jump entry and wait for the target grid's rows GET to fire, for any window other than the
 * board itself (the overlay is a same-tab modal, so page.url() never changes -- see
 * material-cockpit-v2-jump-preconditions.spec.js:152-171, whose race-and-retry shape this mirrors).
 * Returns the matched window id and view id parsed out of the intercepted URL.
 */
async function clickJumpAndCaptureTargetView(page, entry) {
  const targetViewResponsePromise = page.waitForResponse((response) => {
    if (response.request().method() !== 'GET') return false;
    const match = response.url().match(/\/documentView\/(\d+)\/([^/?]+)\?firstRow=/);
    return !!match && match[1] !== String(ORDER_BOARD_WINDOW_ID);
  }, { timeout: 30000 });

  await entry.click();
  const targetViewResponse = await targetViewResponsePromise;
  const match = targetViewResponse.url().match(/\/documentView\/(\d+)\/([^/?]+)\?firstRow=/);
  return { windowId: match[1], viewId: decodeURIComponent(match[2]) };
}

/**
 * Click the jump entry and prove the REACHABLE half of the "readable message, no Oops/500" promise for
 * a valid selection: it must open cleanly, with no error toast and no 5xx response anywhere in-flight.
 * Delegates the positive navigation proof to clickJumpAndCaptureTargetView() (a working jump is itself
 * the primary evidence -- a broken one hangs there for 30s instead of silently "passing"), then checks
 * the negative side-channels an error path would have left behind.
 */
async function clickJumpAndExpectNoError(page, entry) {
  const serverErrors = [];
  const onResponse = (response) => {
    if (response.status() >= 500) serverErrors.push(`${response.status()} ${response.url()}`);
  };
  page.on('response', onResponse);
  try {
    const target = await clickJumpAndCaptureTargetView(page, entry);
    const errorToastVisible = await page
      .locator('.Toastify div[role="alert"].Toastify__toast-body')
      .isVisible()
      .catch(() => false);
    expect(errorToastVisible, 'no error toast may be shown once the jump opened a target grid').toBe(false);
    expect(serverErrors, 'no 5xx response may occur while opening the jump target').toEqual([]);
    return target;
  } finally {
    page.off('response', onResponse);
  }
}

test.describe('Auftrags-Board -> Traffic Management jump', () => {
  test('single waiting board row: the jump lists exactly that row\'s schedules', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('Jump from a single, not-yet-assigned board row');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed a sales-order line for a fresh product, with on-hand stock (needed so the still-unassigned
   schedule row clears the board's own \`isassigned='Y' OR qtyonhand>0\` filter) but no workplace.
2. Select that Auftrags-Board row and run the jump.
3. Traffic Management opens; every visible row carries that product, and the row count equals the
   board row's OrderLineCount.

Proves AC1 (the action is offered) and AC2 (single row -> exactly its schedules).
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump1' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-Waiting-Partner' } },
        warehouses: { wh: {} },
        // M_Picking_Job_Schedule_view's OWN base_schedule CTE excludes every row -- assigned or not --
        // that has no Carrier_Product_ID, whenever AD_SysConfig RequireCarrierProductSet='Y' (verified
        // against the running stack: `WHERE s.carrier_product_id > 0 OR get_sysconfig_value(...) = 'N'`
        // in M_Picking_Job_Schedule_view.sql). Without a shipper this row would not just be hidden by the
        // board's own OR-filter -- it would not exist in the source view at all. See the
        // "fully-assigned board row" test for why IsApiCarrierAdvise='Y' (no gateway) is the reliable,
        // no-external-dependency way to resolve it.
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: { p1: { name: 'OrderBoardJump-Waiting-Product', prices: [{ price: 10 }] } },
        // Real on-hand stock (via a virtual-inventory-backed HU), independent of the sales order below --
        // an unassigned schedule row is dropped from the board entirely unless qtyonhand>0
        // (M_Picking_OrderBoard_Overview_v.sql WHERE clause).
        handlingUnits: { hu1: { product: 'p1', warehouse: 'wh', qty: 50 } },
        salesOrders: {
          so1: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [{ product: 'p1', qty: 5 }], // no workplace -> IsAssigned='N' ("waiting")
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.p1.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    const { viewId, rowId, row, pageNumber } = await waitForBoardRow(page, productId);
    const expectedOrderLineCount = row.fieldsByName.OrderLineCount.value;

    await selectBoardRow(page, viewId, rowId, { pageNumber });
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    // --- EXPECTED RED: the migration that wires the jump has not been applied yet, so the Aktionen menu
    // offers no jump entry at all. This is the assertion that must fail right now, for exactly this reason.
    await expect(entry, `${JUMP_ACTION_TESTID} must be offered once the jump is wired`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    // --- Once the jump is wired, the rest of this test proves AC1/AC2 without further changes.
    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndCaptureTargetView(page, entry);
    expect(targetWindowId, 'the jump must open a window other than the board itself').not.toBe(String(ORDER_BOARD_WINDOW_ID));

    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(targetView.result.length, 'row count must equal the board row\'s OrderLineCount').toBe(expectedOrderLineCount);
    for (const targetRow of targetView.result) {
      expect(
        String(targetRow.fieldsByName?.M_Product_ID?.value?.key),
        'every row opened by the jump must carry the selected board row\'s product'
      ).toBe(String(productId));
    }
  });

  test('fully-assigned board row: its rows stay visible after the jump (regression guard)', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('Jump from a fully-assigned board row must not land on an empty grid');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed a sales-order line assigned to a workplace, so its schedule is IsAssigned='Y' and QtyWaiting=0
   (the board's "In Kommissionierung" bucket).
2. Select that board row and run the jump.
3. Traffic Management opens WITH those rows visible -- not an empty grid, and not a grid whose only
   content is hidden behind the "not assigned" default filter.

This is the exact failure the customer was shown by the predecessor delivery: the
RelationTypeInOverlayProcess.setUseAutoFilters(true) default re-applied Traffic Management's own
"Filter nach: Not Zugeordnet" filter on top of an already-assigned selection, hiding every row. Proves
AC4, and is the regression guard for the AD_Process.IsUseAutoFilters='N' plumbing.
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump2' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-Assigned-Partner' } },
        warehouses: { wh: {} },
        workplaces: { wp1: { warehouse: 'wh' } },
        // A workplace assignment requires the schedule's Carrier_Product_ID already resolved
        // (CreateOrUpdatePickingJobSchedulesCommand.assumeCarrierProductSet, gated by AD_SysConfig
        // RequireCarrierProductSet='Y' on this stack) -- verified: a fresh order with no shipper never
        // even requests a carrier advise (Carrier_Advising_Status stays 'NR'), so any assignment attempt
        // throws CarrierProductNotSet. IsApiCarrierAdvise='Y' with no gateway makes CarrierAdviseCommand
        // synthesize the advise LOCALLY (carrier product = shipper name) -- no external shipper-gateway
        // dependency -- but it still runs on a separate async workpackage (see
        // waitForScheduleCarrierResolved()), so the line below is seeded WITHOUT a workplace and gets
        // assigned afterwards via the real "Schedule" UI action once the advise has settled.
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: { p2: { name: 'OrderBoardJump-Assigned-Product', prices: [{ price: 10 }] } },
        salesOrders: {
          so2: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [{ product: 'p2', qty: 8 }],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.p2.id;
    const workplaceName = masterdata.workplaces.wp1.name;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    const scheduleRow = await waitForScheduleCarrierResolved(page, productId);
    await assignWorkplaceViaScheduleAction(page, scheduleRow.viewId, scheduleRow.rowId, workplaceName, { pageNumber: scheduleRow.pageNumber });

    const { viewId, rowId, row, pageNumber } = await waitForBoardRow(page, productId);
    expect(row.fieldsByName.QtyWaiting.value, 'the seeded row must be entirely assigned (QtyWaiting=0)').toBe('0');

    await selectBoardRow(page, viewId, rowId, { pageNumber });
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    // --- EXPECTED RED: no jump entry yet.
    await expect(entry, `${JUMP_ACTION_TESTID} must be offered once the jump is wired`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    // --- Once the jump is wired: the grid must NOT be empty despite every schedule being assigned.
    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndCaptureTargetView(page, entry);
    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(targetView.result.length, 'the fully-assigned board row must not open an empty Traffic Management grid').toBeGreaterThan(0);
    for (const targetRow of targetView.result) {
      expect(String(targetRow.fieldsByName?.M_Product_ID?.value?.key)).toBe(String(productId));
    }
  });

  test('two board rows selected: one grid holds the union, nothing from a third product', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('Multi-row selection opens one combined grid');
    allure.severity('normal');
    allure.description(`
### Scenario
1. Seed three products on one order, each getting its own board row (grouping is per-product).
2. Select TWO of the three rows (ctrl-click) and run the jump.
3. Expected: one Traffic Management grid holding the union of the two selected rows' schedules, and
   nothing belonging to the third, unselected product.

Proves AC3 (RelationTypeInOverlayProcess.createCombinedFilterView's OR-union path).
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump3' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-Multi-Partner' } },
        warehouses: { wh: {} },
        workplaces: { wp1: { warehouse: 'wh' } },
        // See the "fully-assigned board row" test above for why a shipper with a local (no-gateway)
        // carrier advise is required before any line can carry a workplace.
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: {
          pA: { name: 'OrderBoardJump-Multi-A', prices: [{ price: 10 }] },
          pB: { name: 'OrderBoardJump-Multi-B', prices: [{ price: 10 }] },
          pC: { name: 'OrderBoardJump-Multi-C', prices: [{ price: 10 }] },
        },
        salesOrders: {
          so3: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            // No `workplace` here -- see the "fully-assigned board row" test for why the assignment is
            // done afterwards via the real "Schedule" UI action instead. Product C is NOT selected for
            // the jump below, but is assigned too: it must be a genuine, visible board row (assigned or
            // stocked) for "nothing from a third product" to prove anything -- an invisible row would
            // trivially satisfy that assertion without exercising the union filter at all.
            lines: [
              { product: 'pA', qty: 3 },
              { product: 'pB', qty: 4 },
              { product: 'pC', qty: 2 },
            ],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productIdA = masterdata.products.pA.id;
    const productIdB = masterdata.products.pB.id;
    const productIdC = masterdata.products.pC.id;
    const workplaceName = masterdata.workplaces.wp1.name;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    for (const productId of [productIdA, productIdB, productIdC]) {
      const scheduleRow = await waitForScheduleCarrierResolved(page, productId);
      await assignWorkplaceViaScheduleAction(page, scheduleRow.viewId, scheduleRow.rowId, workplaceName, { pageNumber: scheduleRow.pageNumber });
    }

    const rowA = await waitForBoardRow(page, productIdA);
    const rowB = await waitForBoardRow(page, productIdB);
    // Sanity: the third product's own row must also exist and be excludable -- otherwise "nothing from
    // a third product" would be true only because there is no third row to leak in the first place.
    await waitForBoardRow(page, productIdC);

    // Narrow to exactly rows A and B via a server-side filterOnlyIds view (see
    // createFilteredBoardView()) so both are guaranteed to land on the SAME grid page (page 1 -- only
    // two rows exist in this view), regardless of how many unrelated rows the unfiltered board view has
    // accumulated across this stack's test history.
    const filteredViewId = await createFilteredBoardView(page, [rowA.rowId, rowB.rowId]);
    await selectTwoBoardRows(page, filteredViewId, rowA.rowId, rowB.rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    // --- EXPECTED RED: no jump entry yet.
    await expect(entry, `${JUMP_ACTION_TESTID} must be offered once the jump is wired`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid multi-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    // --- Once the jump is wired: one combined grid, union of A+B, nothing from C.
    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndCaptureTargetView(page, entry);
    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(targetView.result.length, 'the combined grid must not be empty').toBeGreaterThan(0);

    const targetProductIds = new Set(targetView.result.map((r) => String(r.fieldsByName?.M_Product_ID?.value?.key)));
    expect(targetProductIds.has(String(productIdA)), 'the union must include product A').toBe(true);
    expect(targetProductIds.has(String(productIdB)), 'the union must include product B').toBe(true);
    expect(targetProductIds.has(String(productIdC)), 'the union must NOT include the unselected product C').toBe(false);
  });

  test('selection still valid after a concurrent update (no reload): the jump opens cleanly, no Oops/500', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('A valid selection that outlives a concurrent update still opens cleanly');
    allure.severity('normal');
    allure.description(`
### Scenario
1. Seed a fully-assigned board row and select it in the browser.
2. WITHOUT reloading the page, create a DRAFT (not completed) shipment against the sales order's
   schedule via the Backend API (chained onto the same masterdata context) -- a concurrent update
   landing after the row was selected but before the jump is clicked. A draft shipment binds the
   schedule's picked-quantity rows without completing the document, so the schedule stays open
   (QtyToDeliver > 0) and keeps its place in Traffic Management -- unlike completing/fully shipping it,
   which would legitimately (and correctly) remove it from Traffic Management's own grid, a different,
   unrelated behaviour this test must not conflate with an error.
3. Run the jump on that selection.
4. Expected: the jump opens Traffic Management scoped to the row's product, with no error toast and no
   5xx response anywhere in-flight, and the row is still there.

### Why this asserts success, not the original "stale selection -> readable error" idea
\`M_Picking_OrderBoard_Overview_v\` is built directly on \`m_picking_job_schedule_view\` and groups by the
same key \`(m_product_id, c_uom_id, deliverydate::date, c_country_id, ad_client_id, ad_org_id)\` that the
jump's own zoom-source lookup uses -- so a board row that is still live in the grid always has at least
one resolvable schedule; the two can never diverge for a selection that has not been reloaded away. That
makes the "selection whose schedules no longer resolve" case structurally unreachable through this UI
flow. The error path itself (\`RelationTypeInOverlayProcess\` surfacing \`MSG_NO_RELATED_DOCS_FOUND\`
instead of a raw failure when the relation resolves zero related documents) is covered where it IS
reachable: JUnit \`RelationTypeInOverlayProcessTest.DoIt#throws_whenNoRelatedDocumentsFound\` (single
selection) and \`RelationTypeInOverlayProcessTest.DoItMultiSelection#throws_whenNoRelatedDocsForAnySelectedRow\`
(combined selection). What THIS e2e test instead proves is the reachable half of the same promise: a
concurrent update landing between selection and click must never surface an error for a selection that
is (and remains) valid.
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump4' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-ConcurrentUpdate-Partner' } },
        warehouses: { wh: {} },
        workplaces: { wp1: { warehouse: 'wh' } },
        // See the "fully-assigned board row" test above for why a shipper with a local (no-gateway)
        // carrier advise is required before any line can carry a workplace.
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: { p4: { name: 'OrderBoardJump-ConcurrentUpdate-Product', prices: [{ price: 10 }] } },
        salesOrders: {
          so4: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [{ product: 'p4', qty: 6 }], // assigned afterwards via the "Schedule" UI action, not the masterdata shortcut
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.p4.id;
    const workplaceName = masterdata.workplaces.wp1.name;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    const scheduleRow = await waitForScheduleCarrierResolved(page, productId);
    await assignWorkplaceViaScheduleAction(page, scheduleRow.viewId, scheduleRow.rowId, workplaceName, { pageNumber: scheduleRow.pageNumber });

    const { viewId, rowId, pageNumber } = await waitForBoardRow(page, productId);
    await selectBoardRow(page, viewId, rowId, { pageNumber });
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    // Update the schedule WITHOUT navigating away, between selecting the row and clicking the jump --
    // exactly the race a real user can hit. Chains onto the FIRST call's context
    // (JsonCreateMasterdataRequest.context / MasterdataContext#putFromJson-#toJson round-trip) so
    // `salesOrder: 'so4'` resolves -- an established, plain-JSON mechanism, though not previously
    // exercised by any spec in this suite. `complete: false` leaves the shipment as DRAFT (not
    // completed) so the schedule stays open and visible in Traffic Management -- see the description
    // above for why completing it instead would be a different, unrelated (and correct) disappearance.
    await Backend.createMasterdata({
      request: {
        context: masterdata.context,
        shipments: { draftShip: { salesOrder: 'so4', complete: false } },
      },
    });

    await expect(entry, `${JUMP_ACTION_TESTID} must be offered for a valid single-row selection`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    // The selection is still valid (see the description above for why it structurally cannot go stale
    // this way) -- the jump must open Traffic Management scoped to the row's product, cleanly.
    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndExpectNoError(page, entry);
    expect(targetWindowId, 'the jump must open a window other than the board itself').not.toBe(String(ORDER_BOARD_WINDOW_ID));

    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(targetView.result.length, 'the jump target grid must not be empty for a still-valid selection').toBeGreaterThan(0);
    for (const targetRow of targetView.result) {
      expect(
        String(targetRow.fieldsByName?.M_Product_ID?.value?.key),
        'every row opened by the jump must carry the selected board row\'s product'
      ).toBe(String(productId));
    }
  });

  test('Traffic Management opened from the menu still applies its default not-assigned filter', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('Opening Traffic Management normally is unaffected by the jump\'s IsUseAutoFilters override');
    allure.severity('critical');
    allure.description(`
### Scenario
Open Traffic Management directly (as the menu / breadcrumb would), with no jump involved. Expected: the
view-creation response still carries the "not assigned" default filter (AD_Column 591490,
FilterDefaultValue='N') -- verified live against this window: creating a documentView for window 541929
returns \`filters: [{filterId:"default", parameters:[{parameterName:"IsAssigned", value:false}]}]\`.

This test must PASS from the start: it exercises the window's OWN default-filter plumbing, not the jump
action a follow-up migration adds, and it guards the IsUseAutoFilters routing against over-reach into
ordinary window navigation. Proves AC5.
    `);
    test.setTimeout(60000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump5' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    const viewCreateResponsePromise = page.waitForResponse(
      (response) => response.request().method() === 'POST' && new RegExp(`/documentView/${TRAFFIC_MANAGEMENT_WINDOW_ID}$`).test(response.url())
    );
    await page.goto(`${FRONTEND_BASE_URL}/window/${TRAFFIC_MANAGEMENT_WINDOW_ID}`);
    const viewCreateResponse = await viewCreateResponsePromise;
    const body = await viewCreateResponse.json();

    const isAssignedFilterParam = (body.filters || [])
      .flatMap((filter) => filter.parameters || [])
      .find((param) => param.parameterName === 'IsAssigned');

    expect(isAssignedFilterParam, 'Traffic Management opened from the menu must still carry its default IsAssigned filter parameter').toBeTruthy();
    expect(isAssignedFilterParam.value, 'the default filter must be "not assigned" (IsAssigned=false), unchanged').toBe(false);
  });
});
