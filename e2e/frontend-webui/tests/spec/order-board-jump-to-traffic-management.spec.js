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
 * that selecting one or more board rows and running the jump quick action opens Traffic Management
 * scoped to exactly the schedules the board row covers that are still WAITING FOR AN ASSIGNMENT.
 *
 * Two independent mechanisms shape what the jump shows, and every test below turns on one of them:
 *
 *   - HARD, in the relation's target where-clause: only schedules the BOARD itself admits. The board's
 *     inclusion test is `isassigned='Y' OR qtyonhand>0`, and the where-clause repeats it on the target
 *     row, so a schedule sharing the board row's grouping tuple but invisible on the board (an
 *     unassigned one with no stock) never comes through. The user cannot switch this off.
 *
 *   - SOFT, as the target window's own DEFAULT FILTER: only UNASSIGNED schedules. Traffic Management's
 *     `IsAssigned` column carries FilterDefaultValue='N', and AD_Process.IsUseAutoFilters='Y' makes the
 *     overlay apply that default. So the jump OPENS on the schedules that still need a workplace -- but
 *     the restriction arrives as a filter the operator can see and clear in the overlay, not as SQL.
 *     Clearing it brings the already-assigned schedules back into view.
 *
 * The soft half is deliberate: this is a first draft for customer feedback, so "only unassigned" has to
 * be the opening state, not a decision baked into the relation.
 *
 * Everything is asserted through data-testid / data-cy / REST JSON field names -- never localized UI
 * text (e2e/frontend-webui/CLAUDE.md "Specs MUST be language-independent").
 */

const ORDER_BOARD_WINDOW_ID = 542168; // AD_Window "Auftrags-Board", Uebersicht tab (root), table M_Picking_OrderBoard_Overview_v (542626)
const TRAFFIC_MANAGEMENT_WINDOW_ID = 541929; // AD_Window "Traffic Management", table M_Picking_Job_Schedule_view (542514)

// AD_Process.Value (set by migration 5822100), which is what makes this data-testid stable.
// QuickActionsDropdown.js renders data-testid="quick-action-" + (action.internalName || action.processId).
const JUMP_ACTION_TESTID = 'quick-action-M_Picking_OrderBoard_Overview_v_to_TrafficManagement';

/**
 * Poll a grid view (board or Traffic Management) until a row matching `predicate` appears.
 *
 * The board's Uebersicht tab has no server-side filter PARAMETER to narrow the fetch to one product
 * (verified against the running stack: no AD_Field on that tab is marked IsFilterField='Y'), so --
 * unlike the Material Cockpit v2 / forecast specs -- a single unfiltered fetch (pageLength=500) is used
 * to LOCATE the row via REST. Traffic Management does have such a parameter, but is polled the same way
 * here for one predicate shape across both windows.
 *
 * This only RESOLVES the row's id; the UI never navigates the view created here. selectBoardRow() /
 * selectScheduleRow() re-scope to a view holding just the row(s) in question, so where a row would have
 * landed in the unfiltered grid's paging is irrelevant.
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

    const row = view?.result?.find(predicate);
    if (row) {
      return { rowId: row.id, row };
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
 * Distinguish two Traffic Management schedule rows for the SAME product by their QtyOrdered.
 * Needed when one board row aggregates more than one schedule for one product (the mixed-tuple
 * case: an assigned line and an unassigned, unstocked sibling line for the same product/UOM/date/
 * country) and each schedule must be resolved individually -- e.g. to assign only ONE of the two
 * same-product lines to a workplace. QtyOrdered is present in fieldsByName even though
 * IsDisplayed='N' on this tab (verified against the running stack, AD_Field 752539) -- the JSON view
 * payload is not limited to rendered fields; see waitForBoardRow()'s use of M_Product_ID (also
 * IsDisplayed='N' on this tab, AD_Field 752536) for the same pattern already relied on above.
 */
const byProductAndQty = (productId, qty) => (r) =>
  byProduct(productId)(r) && Number(r.fieldsByName?.QtyOrdered?.value) === qty;

/**
 * Poll Traffic Management until the schedule for `productId` carrying exactly `qty` has its carrier
 * resolved -- see waitForScheduleCarrierResolved() below for why this wait is needed; this variant
 * disambiguates by QtyOrdered when more than one schedule row exists for the same product.
 */
async function waitForScheduleCarrierResolvedByQty(page, productId, qty, opts) {
  return waitForViewRow(
    page,
    TRAFFIC_MANAGEMENT_WINDOW_ID,
    (r) => byProductAndQty(productId, qty)(r) && !!r.fieldsByName?.Carrier_Product_ID?.value?.key,
    opts
  );
}

/**
 * Poll Traffic Management's OWN default view (its "not assigned" filter -- see the last test -- already
 * shows a freshly-created, not-yet-assigned schedule) until the row for `productId` has a resolved
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

/**
 * Create a NEW board view scoped to exactly `rowIds`, via the generic `filterOnlyIds` mechanism
 * (JSONCreateViewRequest.filterOnlyIds -> SqlViewFactory: a server-side sticky `keyColumn IN (...)`
 * filter -- de.metas.ui.web.base SqlViewFactory.java "if (!request.getFilterOnlyIds().isEmpty())";
 * already used the same way by picking-terminal.spec.js).
 *
 * Every board selection this spec makes goes through such a view. The Uebersicht tab exposes no
 * AD_Field filter parameter to narrow by product (see waitForViewRow()), so its unfiltered view
 * accumulates every row this stack has ever had, and a freshly-seeded row lands on an arbitrary grid
 * page. A view containing only `rowIds` puts them all on page 1, which removes grid paging from this
 * spec entirely -- and, for the two-row case, guarantees the page co-location a ctrl-click needs.
 *
 * This is a server-side filter on the SAME underlying table/rows, not a different data set --
 * RelationTypeInOverlayProcess#getSelectedSourceRecordRefs() resolves the selected records from the
 * AD_PInstance selection where-clause, not from the view's own filters, so selecting from this filtered
 * view is equivalent to selecting from the unfiltered one.
 *
 * `filterOnlyIds` is board-only on purpose: it deserializes to Integer, and Traffic Management's rows
 * carry the COMPOSED key `<M_ShipmentSchedule_ID>$<M_Picking_Job_Schedule_ID>` (verified against the
 * running stack -- passing one back yields HTTP 400 "Cannot deserialize value of type Integer").
 * createScheduleViewForProduct() narrows that window instead.
 */
async function createBoardViewScopedTo(page, rowIds) {
  return page.evaluate(async ({ windowId, rowIds }) => {
    const apiUrl = config.API_URL; // see waitForViewRow() for why an absolute base URL is required
    const created = await fetch(`${apiUrl}/documentView/${windowId}`, {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
      body: JSON.stringify({ windowId: String(windowId), viewType: 'grid', filterOnlyIds: rowIds }),
    });
    if (!created.ok) throw new Error(`create scoped board view failed: ${created.status}`);
    const { viewId } = await created.json();
    return viewId;
  }, { windowId: ORDER_BOARD_WINDOW_ID, rowIds });
}

/**
 * Create a Traffic Management view narrowed to one product, so a row this spec seeded is always on grid
 * page 1 no matter how many unrelated schedules the window holds. Unlike the board, Traffic Management
 * DOES expose filter parameters (its `default` filter carries an M_Product_ID Lookup -- verified against
 * the running stack's view layout), which is what makes this the workable narrowing here given that
 * filterOnlyIds cannot address its composed row key (see createBoardViewScopedTo()).
 *
 * Note this REPLACES the window's own default filter for this view, so the result contains the product's
 * schedules whether assigned or not. That is what the callers want: this is used only to reach a row and
 * act on it (assigning a workplace), never to assert what the JUMP returns -- those assertions always run
 * against the view the jump itself opened.
 */
async function createScheduleViewForProduct(page, productId) {
  return page.evaluate(async ({ windowId, productId }) => {
    const apiUrl = config.API_URL; // see waitForViewRow() for why an absolute base URL is required
    const created = await fetch(`${apiUrl}/documentView/${windowId}`, {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
      body: JSON.stringify({
        windowId: String(windowId),
        viewType: 'grid',
        filters: [{ filterId: 'default', parameters: [{ parameterName: 'M_Product_ID', value: productId }] }],
      }),
    });
    if (!created.ok) throw new Error(`create product-scoped Traffic Management view failed: ${created.status}`);
    const { viewId } = await created.json();
    return viewId;
  }, { windowId: TRAFFIC_MANAGEMENT_WINDOW_ID, productId });
}

/**
 * Navigate to an already-created view and select `rowId` in it (mirrors
 * material-cockpit-v2-jump-preconditions.spec.js).
 */
async function selectRowInView(page, windowId, viewId, rowId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}?viewId=${viewId}`);
  const row = page.locator(`tbody tr[data-testid="table-row-${rowId}"]`);
  await row.waitFor({ state: 'visible', timeout: 30000 });
  // A click right after the grid renders does not always land as a selection -- assert it, retrying.
  await expect(async () => {
    await row.click();
    await expect(row).toHaveClass(/row-selected/, { timeout: 2000 });
  }).toPass({ timeout: 30000 });
  return row;
}

/** Select one board row, in a view scoped to just that row. */
async function selectBoardRow(page, rowId) {
  const viewId = await createBoardViewScopedTo(page, [rowId]);
  return selectRowInView(page, ORDER_BOARD_WINDOW_ID, viewId, rowId);
}

/** Select one Traffic Management row, in a view narrowed to its product. */
async function selectScheduleRow(page, productId, rowId) {
  const viewId = await createScheduleViewForProduct(page, productId);
  return selectRowInView(page, TRAFFIC_MANAGEMENT_WINDOW_ID, viewId, rowId);
}


/**
 * Navigate to the board and select TWO rows via ctrl-click (Table.js:191 `e.metaKey || e.ctrlKey` ->
 * "select more"). No precedent for multi-row selection exists anywhere in e2e/frontend-webui (both
 * material-cockpit-v2-jump-preconditions.spec.js and forecast-overlay-qty.spec.js select a single row);
 * this gesture was derived from frontend/src/components/table/Table.js and is verified by this test run
 * itself (both rows must show the row-selected class, independent of whether the jump action exists).
 */
async function selectTwoBoardRows(page, rowIdFirst, rowIdSecond) {
  // A ctrl-click can only ADD to a selection when both rows render on the same grid page, so the pair
  // gets its own two-row view (see createBoardViewScopedTo()) -- page co-location is then guaranteed
  // rather than depending on how many unrelated rows the board has accumulated.
  const viewId = await createBoardViewScopedTo(page, [rowIdFirst, rowIdSecond]);
  await page.goto(`${FRONTEND_BASE_URL}/window/${ORDER_BOARD_WINDOW_ID}?viewId=${viewId}`);

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
async function assignWorkplaceViaScheduleAction(page, productId, rowId, workplaceName) {
  await selectScheduleRow(page, productId, rowId);
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
 * Assert that one row the jump returned satisfies the jump's own restriction, in full.
 *
 * Asserts both halves at once for a row in the jump's DEFAULT (unfiltered-by-the-user) state: it must
 * be unassigned (the window's IsAssigned default filter, applied because AD_Process.IsUseAutoFilters='Y')
 * and in stock (the where-clause's board-visibility invariant). Checking both on every returned row means
 * a regression that drops either one is caught by every test that inspects rows -- not only by the two
 * mixed-tuple tests that seed the boundary directly.
 *
 * `IsAssigned` is a boolean in the view JSON, `QtyOnHand` a numeric string (verified against the
 * running stack: `{"field":"IsAssigned","value":false}`, `{"field":"QtyOnHand","value":"1000"}`);
 * both are present in fieldsByName even though QtyOnHand carries IsDisplayed='N' on this tab -- the
 * same payload property waitForBoardRow() already relies on for M_Product_ID.
 */
function expectRowIsUnassignedAndStocked(targetRow, productId) {
  expect(
    String(targetRow.fieldsByName?.M_Product_ID?.value?.key),
    'every row opened by the jump must carry the selected board row\'s product'
  ).toBe(String(productId));
  expect(
    targetRow.fieldsByName?.IsAssigned?.value,
    'the jump must OPEN on unassigned schedules only (the IsAssigned default filter)'
  ).toBe(false);
  expect(
    Number(targetRow.fieldsByName?.QtyOnHand?.value),
    'an unassigned schedule is on the board only when it has stock -- the jump must not return one without'
  ).toBeGreaterThan(0);
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
3. Traffic Management opens; every visible row carries that product, is unassigned and in stock, and
   the row count equals the board row's OrderLineCount (here the two coincide: the row's only schedule
   is the unassigned one).

Proves AC1 (the action is offered) and AC2 (single row -> exactly its schedules that still need one).
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

    const { rowId, row } = await waitForBoardRow(page, productId);
    const expectedOrderLineCount = row.fieldsByName.OrderLineCount.value;

    await selectBoardRow(page, rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    await expect(entry, `${JUMP_ACTION_TESTID} must be offered for a valid single-row selection`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndCaptureTargetView(page, entry);
    expect(targetWindowId, 'the jump must open a window other than the board itself').not.toBe(String(ORDER_BOARD_WINDOW_ID));

    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(targetView.result.length, 'row count must equal the board row\'s OrderLineCount').toBe(expectedOrderLineCount);
    for (const targetRow of targetView.result) {
      expectRowIsUnassignedAndStocked(targetRow, productId);
    }
  });

  test('fully-assigned board row: none of its assigned schedules come through the jump', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('Jump from a fully-assigned board row returns nothing to schedule');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed a sales-order line assigned to a workplace, so its schedule is IsAssigned='Y' and QtyWaiting=0
   (the board's "In Kommissionierung" bucket).
2. Select that board row and run the jump.
3. Traffic Management opens -- cleanly, on the Traffic Management window, with no error -- and opens on
   NONE of that row's schedules, because every one of them is already assigned and the overlay applies
   the window's IsAssigned default filter (AD_Process.IsUseAutoFilters='Y').

Proves the "opens on unassigned only" half for the extreme case where the board row has nothing left to
schedule -- and, just as importantly, that this case does NOT surface as an error: an empty overlay is
the correct answer here, not a failure. The empty grid is not vacuous: the test first asserts that the
board row genuinely covers at least one schedule (OrderLineCount > 0) and that every one of them is
assigned (QtyWaiting = 0), so the emptiness is the filter at work, not a missing schedule or a broken
jump. The operator can still see those schedules by clearing the filter -- that affordance is covered by
the mixed-tuple-with-stock test, which has both an assigned and an unassigned row to tell apart.
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
    await assignWorkplaceViaScheduleAction(page, productId, scheduleRow.rowId, workplaceName);

    const { rowId, row } = await waitForBoardRow(page, productId);
    expect(row.fieldsByName.QtyWaiting.value, 'the seeded row must be entirely assigned (QtyWaiting=0)').toBe('0');
    // Non-vacuity: the board row DOES cover at least one schedule (it is built from them), and every
    // one of those is assigned -- so an empty jump result below can only be the restriction at work.
    expect(
      row.fieldsByName.OrderLineCount.value,
      'the board row must genuinely cover at least one (assigned) schedule -- otherwise an empty jump result proves nothing'
    ).toBeGreaterThan(0);

    await selectBoardRow(page, rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    await expect(entry, `${JUMP_ACTION_TESTID} must be offered for a valid single-row selection`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    // The jump must still OPEN Traffic Management cleanly (no error toast, no 5xx) -- it simply has
    // nothing to offer, because every schedule under this board row is already assigned.
    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndExpectNoError(page, entry);
    expect(targetWindowId, 'the jump must open the Traffic Management window').toBe(String(TRAFFIC_MANAGEMENT_WINDOW_ID));

    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(
      targetView.result.length,
      'an entirely-assigned board row has nothing left to schedule -- the jump must return no rows at all'
    ).toBe(0);
  });

  test('two board rows selected: one grid holds the union, nothing from a third product', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('Multi-row selection opens one combined grid');
    allure.severity('normal');
    allure.description(`
### Scenario
1. Seed three products on one order, each with on-hand stock and no workplace, so each gets its own
   board row whose schedule is unassigned and therefore genuinely reachable through the jump.
2. Select TWO of the three rows (ctrl-click) and run the jump.
3. Expected: one Traffic Management grid holding the union of the two selected rows' schedules -- every
   row unassigned and in stock -- and nothing belonging to the third, unselected product.

Proves AC3 (RelationTypeInOverlayProcess.createCombinedFilterView's OR-union path).
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump3' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-Multi-Partner' } },
        warehouses: { wh: {} },
        // See the "fully-assigned board row" test above for why a shipper with a local (no-gateway)
        // carrier advise is required before ANY line appears in M_Picking_Job_Schedule_view at all
        // (AD_SysConfig RequireCarrierProductSet='Y' on this stack).
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: {
          pA: { name: 'OrderBoardJump-Multi-A', prices: [{ price: 10 }] },
          pB: { name: 'OrderBoardJump-Multi-B', prices: [{ price: 10 }] },
          pC: { name: 'OrderBoardJump-Multi-C', prices: [{ price: 10 }] },
        },
        // Real on-hand stock for all three products: an unassigned schedule is on the board -- and
        // reachable through the jump -- only when qtyonhand>0. Product C is NOT selected for the jump
        // below, but must be a genuine, visible board row for "nothing from a third product" to prove
        // anything: an invisible row would satisfy that assertion without exercising the union filter.
        handlingUnits: {
          huA: { product: 'pA', warehouse: 'wh', qty: 50 },
          huB: { product: 'pB', warehouse: 'wh', qty: 50 },
          huC: { product: 'pC', warehouse: 'wh', qty: 50 },
        },
        salesOrders: {
          so3: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            // No workplace anywhere: all three schedules stay unassigned, which is exactly what the
            // jump now resolves.
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

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    // Each schedule only enters M_Picking_Job_Schedule_view once its carrier has been advised on the
    // async workpackage -- see waitForScheduleCarrierResolved().
    for (const productId of [productIdA, productIdB, productIdC]) {
      await waitForScheduleCarrierResolved(page, productId);
    }

    const rowA = await waitForBoardRow(page, productIdA);
    const rowB = await waitForBoardRow(page, productIdB);
    // Sanity: the third product's own row must also exist and be excludable -- otherwise "nothing from
    // a third product" would be true only because there is no third row to leak in the first place.
    await waitForBoardRow(page, productIdC);

    await selectTwoBoardRows(page, rowA.rowId, rowB.rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    await expect(entry, `${JUMP_ACTION_TESTID} must be offered for a valid multi-row selection`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid multi-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    // One combined grid, union of A+B, nothing from C -- and every row unassigned and in stock.
    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndCaptureTargetView(page, entry);
    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(targetView.result.length, 'the combined grid must not be empty').toBeGreaterThan(0);
    for (const targetRow of targetView.result) {
      expect(targetRow.fieldsByName?.IsAssigned?.value, 'the jump must return ONLY unassigned schedules').toBe(false);
      expect(
        Number(targetRow.fieldsByName?.QtyOnHand?.value),
        'an unassigned schedule is on the board only when it has stock -- the jump must not return one without'
      ).toBeGreaterThan(0);
    }

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
1. Seed a board row whose schedule is unassigned and in stock (i.e. genuinely reachable through the
   jump) and select it in the browser.
2. WITHOUT reloading the page, create a SECOND sales order for the same product / partner / delivery
   date via the Backend API (chained onto the same masterdata context) -- a concurrent update landing
   after the row was selected but before the jump is clicked. Because the board groups by
   (product, UOM, delivery date, country, client, org), that new order line falls under the very board
   row that is already selected: its aggregate changes underneath the selection, while the originally
   selected schedule stays untouched and still needs an assignment.
3. Run the jump on that selection.
4. Expected: the jump opens Traffic Management scoped to the row's product, with no error toast and no
   5xx response anywhere in-flight, and the originally selected schedule is still there.

A draft (not completed) shipment was tried here first and is NOT usable: generating one drives the
schedule's QtyToDeliver to 0 (verified on the running stack), and the view's unassigned branch exists
only \`WHERE qtytoscheduleforpicking > 0\` -- so the row the test needs to still find would be gone for
a reason that has nothing to do with the jump. That shape only ever worked while this test seeded an
ASSIGNED row, whose branch of M_Picking_Job_Schedule_view joins M_Picking_Job_Schedule instead and does
not depend on QtyToDeliver.

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
    const selectedQty = 6;      // the line that exists BEFORE the selection -- must survive the concurrent update
    const concurrentQty = 2;    // the line the concurrent update adds under the same board row
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump4' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-ConcurrentUpdate-Partner' } },
        warehouses: { wh: {} },
        // See the "fully-assigned board row" test above for why a shipper with a local (no-gateway)
        // carrier advise is required before the line appears in M_Picking_Job_Schedule_view at all.
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: { p4: { name: 'OrderBoardJump-ConcurrentUpdate-Product', prices: [{ price: 10 }] } },
        // On-hand stock so the still-unassigned schedule clears the board's own inclusion test and is
        // therefore genuinely reachable through the jump.
        handlingUnits: { hu4: { product: 'p4', warehouse: 'wh', qty: 50 } },
        salesOrders: {
          so4: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [{ product: 'p4', qty: selectedQty }], // no workplace -> IsAssigned='N', which is what the jump resolves
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.p4.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    await waitForScheduleCarrierResolved(page, productId);

    const { rowId } = await waitForBoardRow(page, productId);
    await selectBoardRow(page, rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    // Change the selected board row's own aggregate WITHOUT navigating away, between selecting the row
    // and clicking the jump -- exactly the race a real user can hit. Chains onto the FIRST call's
    // context (JsonCreateMasterdataRequest.context / MasterdataContext#putFromJson-#toJson round-trip)
    // so `bpartner: 'bp'` / `warehouse: 'wh'` / `shipper: 'ship1'` / `product: 'p4'` resolve to the
    // records already seeded -- an established, plain-JSON mechanism, though not previously exercised
    // by any spec in this suite. Same product + partner + delivery date means the new line lands under
    // the SAME board row that is already selected. See the description above for why a draft shipment
    // (the shape this test used while it seeded an assigned row) cannot be used here.
    await Backend.createMasterdata({
      request: {
        context: masterdata.context,
        salesOrders: {
          so4b: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [{ product: 'p4', qty: concurrentQty }],
          },
        },
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
      expectRowIsUnassignedAndStocked(targetRow, productId);
    }
    // The originally selected schedule specifically -- not just "some row" -- must have survived the
    // concurrent update. The second order's line may or may not have had its carrier advised by now
    // (that runs on an async workpackage), so its presence is deliberately not asserted either way.
    const returnedQtys = targetView.result.map((r) => Number(r.fieldsByName?.QtyOrdered?.value));
    expect(returnedQtys, 'the schedule that was selected before the concurrent update must still be returned').toContain(selectedQty);
  });

  test('mixed-tuple board row: the jump opens on the unassigned schedule only, and the operator can clear that', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('The jump opens filtered to unassigned -- as a filter the operator can clear');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed ONE sales order with TWO lines for the SAME product/UOM/delivery-date/country tuple (so both
   schedules fall under one Auftrags-Board row), plus real on-hand stock for that product. One line is
   assigned to a workplace via Traffic Management's own "Schedule" action; the other stays unassigned.
2. Both schedules therefore pass the board's own \`isassigned='Y' OR qtyonhand>0\` inclusion test, so
   the board row's OrderLineCount is 2 -- the board legitimately shows both.
3. Select that board row and run the jump.
4. DEFAULT STATE: the overlay holds EXACTLY ONE row -- the UNASSIGNED line (QtyOrdered = the unassigned
   line's qty, IsAssigned=false, no workplace) -- and shows the filter as ACTIVE, so the operator can
   see that a restriction is in force.
5. CONFIGURABILITY: clearing that filter in the overlay, the way an operator would (open the filter
   button, pick the default filter, hit "clear filter"), brings the assigned sibling back -- two rows.

### Why this is the case that pins the behaviour down
The jump exists so the operator lands on the schedules that still need a workplace; one that already has
one is noise on arrival. But this is a first draft for customer feedback, so "only unassigned" must be
the OPENING state, not a decision baked into the relation -- hence a default filter (the target window's
own \`IsAssigned\` FilterDefaultValue='N', applied because AD_Process.IsUseAutoFilters='Y') rather than a
where-clause term.

Both halves need this one seed, and only this seed: it is the only place in this file where an assigned
and an unassigned schedule share a single board row, so it is the only test that can tell "opens on the
unassigned rows" apart from "returns whatever the board shows" -- the two answers differ here (1 row vs
2) and nowhere else. That same gap is what makes step 5 meaningful: the row that reappears after
clearing is precisely the one the default filter had hidden.
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const assignedQty = 5;
    const unassignedQty = 3;
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump6' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-MixedTuple-Partner' } },
        warehouses: { wh: {} },
        workplaces: { wp1: { warehouse: 'wh' } },
        // See the "fully-assigned board row" test above for why a shipper with a local (no-gateway)
        // carrier advise is required before any line can carry a workplace -- and, on this stack, before
        // ANY line (assigned or not) even appears in M_Picking_Job_Schedule_view at all
        // (RequireCarrierProductSet='Y').
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        // ONE product for BOTH lines -- this is what makes them share the board's grouping tuple
        // (M_Product_ID, C_UOM_ID, DeliveryDate::date, C_Country_ID, AD_Client_ID, AD_Org_ID).
        products: { p6: { name: 'OrderBoardJump-MixedTuple-Product', prices: [{ price: 10 }] } },
        // Real on-hand stock: it is what puts the UNASSIGNED line on the board (and hence in the jump).
        // Stock is per product/warehouse, not per order line, so both lines share it.
        handlingUnits: { hu6: { product: 'p6', warehouse: 'wh', qty: 50 } },
        // Both lines are seeded WITHOUT a workplace (see the "fully-assigned board row" test above for
        // why the workplace shortcut races the async carrier advise for a multi-line order); only the
        // first line is assigned afterwards, via the real "Schedule" UI action.
        salesOrders: {
          so6: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [
              { product: 'p6', qty: assignedQty },   // assigned afterwards -> IsAssigned='Y', must NOT come through
              { product: 'p6', qty: unassignedQty }, // stays unassigned, in stock -> the ONLY row the jump may return
            ],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.p6.id;
    const workplaceName = masterdata.workplaces.wp1.name;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    // Resolve and assign ONLY the assignedQty line's schedule -- the unassignedQty sibling is left
    // exactly as seeded (no workplace).
    const assignedScheduleRow = await waitForScheduleCarrierResolvedByQty(page, productId, assignedQty);
    await assignWorkplaceViaScheduleAction(page, productId, assignedScheduleRow.rowId, workplaceName);
    // The unassigned sibling must genuinely exist and be carrier-resolved (it is the row the jump is
    // required to return, so its absence would make the assertions below meaningless).
    await waitForScheduleCarrierResolvedByQty(page, productId, unassignedQty);

    const { rowId, row } = await waitForBoardRow(page, productId);
    // The board itself shows BOTH schedules -- this is what makes the jump's narrower result meaningful
    // rather than a coincidence of the seeding.
    expect(
      row.fieldsByName.OrderLineCount.value,
      'both the assigned and the unassigned-but-stocked line must count towards the board row\'s OrderLineCount'
    ).toBe(2);

    await selectBoardRow(page, rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    await expect(entry, `${JUMP_ACTION_TESTID} must be offered for a valid single-row selection`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndCaptureTargetView(page, entry);
    expect(targetWindowId, 'the jump must open the Traffic Management window').toBe(String(TRAFFIC_MANAGEMENT_WINDOW_ID));

    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    expect(
      targetView.result.length,
      'the jump must OPEN on the unassigned line only -- one row, not the board row\'s two'
    ).toBe(1);

    const [onlyRow] = targetView.result;
    expectRowIsUnassignedAndStocked(onlyRow, productId);
    expect(
      Number(onlyRow.fieldsByName?.QtyOrdered?.value),
      'the row the jump opens on must be the UNASSIGNED line, not the assigned sibling'
    ).toBe(unassignedQty);
    expect(
      onlyRow.fieldsByName?.C_Workplace_ID?.value?.key,
      'the row the jump opens on must carry no workplace -- that is what "unassigned" means here'
    ).toBeFalsy();

    // --- CONFIGURABILITY: the restriction is a filter the operator can see and clear, not SQL. ---
    // Everything below is driven through the overlay's own UI, scoped to the modal so nothing can be
    // read off the board grid still rendered behind it (Modal.js: `panel-modal-content`).
    const overlay = page.locator('.panel-modal-content');
    const overlayRows = overlay.locator('tbody tr[data-testid^="table-row-"]');
    await expect(overlayRows, 'the overlay must render the one unassigned row the jump opened on').toHaveCount(1);

    // The filter must be VISIBLY in force -- FiltersIncluded.js marks the filter button `btn-active`
    // when one of its included filters is applied. Without AD_Process.IsUseAutoFilters='Y' the overlay
    // applies no default filter at all, so this button is never active and the operator is given no
    // hint that anything is being hidden.
    const filterButton = overlay.locator('.filters-not-frequent button.btn-filter');
    await expect(
      filterButton,
      'the overlay must show the IsAssigned default filter as active -- the operator has to see the restriction to be able to lift it'
    ).toHaveClass(/btn-active/, { timeout: 15000 });

    // Clear it exactly the way an operator would: open the filter, then hit its "clear filter" control
    // (FiltersItem.js `.filter-clear`, rendered only while the filter IS active -- so this click is
    // itself a second, independent proof that the default filter was applied). Both are class-based
    // selectors -- no localized caption is matched.
    //
    // The click lands straight on the filter's parameter panel, with no intermediate filter-picking
    // menu: FiltersIncluded.toggleDropdown() pre-selects the filter to open whenever one is already
    // active -- and, failing that, whenever the group holds exactly one filter, which is the case here
    // (Traffic Management's included-filter group contains only `default`).
    await filterButton.click();

    // Clearing the filter makes the frontend re-fetch the overlay's grid; catch that GET so the rows it
    // returns can be inspected by field. The rendered grid cannot answer this on its own -- C_Workplace_ID
    // carries IsDisplayed='N' on this tab (AD_Field, verified against the running stack), so it is in the
    // view JSON but never in the DOM. Same intercept shape as clickJumpAndCaptureTargetView().
    const clearedViewResponsePromise = page.waitForResponse(
      (response) =>
        response.request().method() === 'GET' &&
        new RegExp(`/documentView/${TRAFFIC_MANAGEMENT_WINDOW_ID}/[^/?]+\\?firstRow=`).test(response.url()),
      { timeout: 30000 }
    );
    await overlay.locator('.filter-clear').click();
    const clearedViewId = decodeURIComponent(
      (await clearedViewResponsePromise).url().match(/\/documentView\/\d+\/([^/?]+)\?firstRow=/)[1]
    );

    // With the filter gone, the assigned sibling comes back: the where-clause alone admits both rows.
    await expect(
      overlayRows,
      'clearing the IsAssigned filter must bring the assigned sibling back -- the restriction is a removable default, not a hard rule'
    ).toHaveCount(2, { timeout: 20000 });

    // ...and the row that came back must be the ASSIGNED sibling specifically -- a bare count of 2 would
    // also be satisfied by some unrelated row leaking in, which is exactly what the count cannot tell apart.
    const clearedView = await fetchViewRows(page, TRAFFIC_MANAGEMENT_WINDOW_ID, clearedViewId);
    const reappearedRow = clearedView.result.find((r) => Number(r.fieldsByName?.QtyOrdered?.value) === assignedQty);
    expect(reappearedRow, 'the row that reappears after clearing must be the assigned line').toBeTruthy();
    expect(
      reappearedRow.fieldsByName?.IsAssigned?.value,
      'the row that reappears must be the one the default filter had been hiding -- i.e. an ASSIGNED one'
    ).toBe(true);
    expect(
      reappearedRow.fieldsByName?.C_Workplace_ID?.value?.key,
      'the reappeared assigned row must carry the workplace it was assigned to'
    ).toBeTruthy();
  });

  test('mixed-tuple board row without stock: the unstocked, unassigned sibling never comes through the jump', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('The jump never returns a schedule the board itself does not show');
    allure.severity('critical');
    allure.description(`
### Scenario
1. Seed ONE sales order with TWO lines for the SAME product/UOM/delivery-date/country tuple and NO
   on-hand stock: one line is assigned to a workplace, the other stays unassigned.
2. The board admits only the assigned line (\`isassigned='Y' OR qtyonhand>0\`), so OrderLineCount is 1;
   the unassigned, unstocked line is invisible on the board -- yet it DOES share the exact grouping
   tuple the jump's EXISTS back-join matches on.
3. Select that board row and run the jump.
4. Expected: the unassigned, unstocked line is NOWHERE in the result. It is excluded by the relation's
   own where-clause, which is the HARD half of the jump's restriction and is not affected by this
   change.

### Why this exclusion needs its own test -- and why it is deliberately filter-agnostic
The jump's WhereClause is an EXISTS back-join to \`M_Picking_OrderBoard_Overview_v\` on the grouping
tuple, plus \`AND (IsAssigned='Y' OR QtyOnHand>0)\` on the TARGET row. The EXISTS alone only proves
"a board aggregate row with this id and this tuple exists" -- it says nothing about whether the specific
target row would itself have counted towards that aggregate. Drop that trailing term and this unstocked
sibling is returned by the jump even though the board does not show it. Only this test seeds an
unassigned schedule with zero stock, so only this test can catch that.

The primary assertion is therefore about the SIBLING'S ABSENCE, not about an exact grid size. That is on
purpose: the where-clause half is unchanged by the IsUseAutoFilters change, so this test must be -- and
stays -- green both before and after it, which is exactly what pins the two halves apart. Pinning the
count to an exact number would fold the soft half back in and lose that separation (the IsAssigned
default filter independently hides the assigned line, so the grid holds 1 row before the change and 0
after). The soft half has its own coverage: the "fully-assigned board row" and mixed-tuple-with-stock
tests.

It is accompanied by an UPPER BOUND -- the jump may never return more rows than the board row itself
covers (OrderLineCount = 1 here) -- which guards a DIFFERENT bug class: duplication / join fan-out in
the relation, which the containment check cannot see because every duplicated row still carries a
legitimate qty.

The two are not interchangeable, and the containment check is the one that matters here. Simulating the
regression on a running stack (dropping the where-clause's trailing term, then re-running this test)
showed the leak surfacing as EXACTLY ONE row -- the IsAssigned auto-filter removes the assigned line in
the same fetch -- so the observed array was \`[4]\`: containment fired, the bound (1 <= 1) did not. Do
not conclude from the bound's presence that the containment assertion is redundant; it is the only
assertion in this file that catches an unstocked sibling leaking past the where-clause.

Neither assertion is vacuous: the test first asserts that BOTH schedules genuinely exist (each
carrier-resolved and reachable in Traffic Management's own view before the assignment), that the board
row covers exactly one of them, and that the jump still opens Traffic Management cleanly.
    `);
    test.setTimeout(120000);

    const datePromised = new Date(Date.now() + 24 * 60 * 60 * 1000).toISOString();
    const assignedQty = 7;
    const unassignedQty = 4;
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'orderboard', lastname: 'jump7' } },
        bpartners: { bp: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'OrderBoardJump-MixedTupleNoStock-Partner' } },
        warehouses: { wh: {} },
        workplaces: { wp1: { warehouse: 'wh' } },
        shippers: { ship1: { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
        products: { p7: { name: 'OrderBoardJump-MixedTupleNoStock-Product', prices: [{ price: 10 }] } },
        // Deliberately NO handlingUnits -- the unassigned line must have qtyonhand=0, which is what
        // keeps it off the board and, therefore, out of the jump.
        salesOrders: {
          so7: {
            bpartner: 'bp',
            warehouse: 'wh',
            shipper: 'ship1',
            datePromised,
            lines: [
              { product: 'p7', qty: assignedQty },   // assigned afterwards -> the only line the board shows
              { product: 'p7', qty: unassignedQty }, // unassigned AND unstocked -> not on the board, and never in the jump
            ],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    const productId = masterdata.products.p7.id;
    const workplaceName = masterdata.workplaces.wp1.name;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await expectDashboardVisible(page);

    const assignedScheduleRow = await waitForScheduleCarrierResolvedByQty(page, productId, assignedQty);
    await assignWorkplaceViaScheduleAction(page, productId, assignedScheduleRow.rowId, workplaceName);
    // Sanity: the unassigned sibling's own schedule must genuinely exist (carrier-resolved, so it is not
    // simply "not created yet") -- otherwise "the sibling is absent from the jump" would be trivially
    // true because there is no sibling to leak in the first place.
    await waitForScheduleCarrierResolvedByQty(page, productId, unassignedQty);

    const { rowId, row } = await waitForBoardRow(page, productId);
    const expectedOrderLineCount = row.fieldsByName.OrderLineCount.value;
    expect(
      expectedOrderLineCount,
      'only the assigned line must count towards the board row\'s OrderLineCount -- the unstocked sibling is not on the board'
    ).toBe(1);

    await selectBoardRow(page, rowId);
    const dropdown = await openQuickActionsDropdown(page);
    const entry = dropdown.getByTestId(JUMP_ACTION_TESTID);

    await expect(entry, `${JUMP_ACTION_TESTID} must be offered for a valid single-row selection`).toBeVisible({ timeout: 10000 });
    await expect(entry, `${JUMP_ACTION_TESTID} must not be disabled for a valid single-row selection`).not.toHaveClass(/quick-actions-item-disabled/);

    const { windowId: targetWindowId, viewId: targetViewId } = await clickJumpAndExpectNoError(page, entry);
    expect(targetWindowId, 'the jump must open the Traffic Management window').toBe(String(TRAFFIC_MANAGEMENT_WINDOW_ID));

    const targetView = await fetchViewRows(page, targetWindowId, targetViewId);
    const returnedQtys = targetView.result.map((r) => Number(r.fieldsByName?.QtyOrdered?.value));
    expect(
      returnedQtys,
      'the unassigned line has no stock, so the board does not show it -- the jump\'s where-clause must not return it either'
    ).not.toContain(unassignedQty);
    // A SEPARATE bug class, not a second line of defence for the check above -- do not treat the
    // containment assertion as redundant because this one exists. Verified by simulating the
    // regression on a running stack (dropping the where-clause's trailing term, then re-running this
    // test): the leak surfaces as EXACTLY ONE row, because the IsAssigned auto-filter removes the
    // assigned line in the same fetch. The observed result array was `[4]` -- the containment check
    // fired; this bound (1 <= 1) did not. What the bound does catch is a row count the board itself
    // cannot justify: duplication / join fan-out in the relation, which containment cannot see
    // because every returned row would still carry a legitimate qty.
    expect(
      targetView.result.length,
      'the jump must never return more rows than the board row covers (OrderLineCount)'
    ).toBeLessThanOrEqual(expectedOrderLineCount);
  });

  test('Traffic Management opened from the menu still applies its default not-assigned filter', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00245: Traffic Management');
    allure.tag('F00245');
    allure.story('The window\'s own default not-assigned filter is what the jump now leans on');
    allure.severity('critical');
    allure.description(`
### Scenario
Open Traffic Management directly (as the menu / breadcrumb would), with no jump involved. Expected: the
view-creation response still carries the "not assigned" default filter (AD_Column 591490,
FilterDefaultValue='N') -- verified live against this window: creating a documentView for window 541929
returns \`filters: [{filterId:"default", parameters:[{parameterName:"IsAssigned", value:false}]}]\`.

This filter is what the jump's whole "opens on unassigned" behaviour rests on: with
AD_Process.IsUseAutoFilters='Y' the overlay applies this very default, so if the filter were ever
dropped from the window the jump would silently start showing everything. The check is deliberately
made through ORDINARY navigation (no jump involved), so it keeps guarding the filter's existence
independently of how the overlay is wired -- and it stays green across the IsUseAutoFilters change.
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
