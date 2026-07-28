/**
 * TL;DR — Regression guard for the mobile-workflow "bounce-to-home" race at workflow start.
 *
 * The bug: starting a workflow from a launcher could drop the operator back on the home menu
 * (ApplicationsListScreen) mid-start instead of landing on the job (WF-process) screen. Two
 * independent races produced it:
 *   - Race A (reducer): a stale POPULATE_LAUNCHERS_COMPLETE snapshot, fetched before the start
 *     resolved, pruned the just-started wfProcess from the store.
 *   - Race B (ApplicationLayout redirect-home guard): on React 17 / connected-react-router the
 *     store update and the route change are not batched, so ApplicationLayout can mount on the job
 *     route one render pass before the store selector observes the just-dispatched process; the
 *     guard used to fire goHome() synchronously on that first pass. The guard now defers goHome()
 *     one macrotask (setTimeout(...,0)) and cancels it in cleanup if the process becomes loaded
 *     within the tick — so a real start no longer bounces, while a genuinely-absent process still
 *     redirects.
 *
 * The scenarios below:
 *   1. No-bounce on workflow start — the observable contract. Starting from a picking launcher and
 *      from a distribution launcher lands on the WF-process screen and does NOT bounce to the home
 *      menu. NOTE: this does NOT reliably reproduce the Race-B ordering hazard on a fast local stack
 *      (verified: reverting the ApplicationLayout goHome-deferral alone did NOT turn this scenario
 *      RED — that store-vs-router timing does not manifest in a plain launcher-start under test). So
 *      scenario 1 is a contract guard, not a deterministic Race-B guard; the deterministic race
 *      guard is scenario 4 (Race A). Race B's preserved behaviour is covered by scenarios 2 and 3(b).
 *   2. Dead deep-link (cold navigate to a WF-process URL whose process is not in the store) still
 *      redirects home — proves the deferred goHome still fires for a genuinely-absent process.
 *   3. Reload while a job is open. Two real behaviours, one test:
 *      (a) a PLAIN reload KEEPS the operator in the job — the redux store is persisted to
 *          localStorage and re-hydrated on boot (intentional F5-resilience, mirroring the auth
 *          token which is kept in a cookie), so the process is still loaded and the guard does not
 *          fire; and
 *      (b) when the re-hydrated store does NOT contain the process (localStorage evicted / private
 *          mode / a fresh browser opening a bookmarked deep-link — the auth cookie still carries the
 *          session), the boot-time guard still redirects home rather than hanging on a blank job
 *          frame. This is the same dead-deep-link class as scenario 2, reached via the cold-boot path.
 *   4. Stale launchers refresh after start (Race A, deterministic) — reproduces the PRIMARY production
 *      trigger with real network timing: a launchers-query issued before the start (so its snapshot
 *      does not list the just-started process) whose response is delivered AFTER the start. Without
 *      Race A's fix that stale snapshot prunes the just-started wfProcess and the operator bounces
 *      home; with the fix the process is kept (its local update is newer than the request that fetched
 *      the snapshot). The pre-start request timing is forced deterministically by holding the query's
 *      response (Playwright route interception) — a faithful model of a slow/reordered response, not a
 *      fabricated state. This is the RED-provable guard of the reducer fix (revert it → RED).
 *
 * NOTE on 3(a): a plain reload does NOT redirect home in this app (verified) because wfProcesses is
 * persisted+re-hydrated. Forcing a redirect on a plain reload would require fabricating a state the
 * real reload cannot produce; instead 3(b) clears the persisted store to model the genuine
 * absent-process boot the guard actually protects.
 *
 * No assertion here is to be weakened: scenario 1 must land on the WF-process screen AND not bounce
 * to the home menu; scenarios 2 & 3(b) must redirect home; scenario 4 must keep the process on the
 * job screen after the stale snapshot arrives.
 */

import { test } from "../../playwright.config";
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from "../utils/screens/Backend";
import { LoginScreen } from "../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../utils/screens/picking/PickingJobScreen";
import { PickingJobsListFiltersScreen } from "../utils/screens/picking/PickingJobsListFiltersScreen";
import { DistributionJobsListScreen } from "../utils/screens/distribution/DistributionJobsListScreen";
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from "../utils/common";

const createPickingMasterdata = async () => Backend.createMasterdata({
    language: "en_US",
    request: {
        login: { user: { language: "en_US" } },
        mobileConfig: {
            picking: {
                aggregationType: "sales_order",
                allowPickingAnyCustomer: true,
                createShipmentPolicy: 'CL',
                allowPickingAnyHU: true,
                pickTo: ['LU_TU'],
                allowCompletingPartialPickingJob: false,
            }
        },
        bpartners: { "BP1": {} },
        warehouses: { "wh": {} },
        pickingSlots: { slot1: {} },
        products: { "P1": { prices: [{ price: 1 }] } },
        packingInstructions: {
            "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
        },
        handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' } },
        salesOrders: {
            "SO1": {
                bpartner: 'BP1',
                warehouse: 'wh',
                datePromised: '2025-03-01T00:00:00.000+02:00',
                lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
            }
        },
    }
});

const createDistributionMasterdata = async () => Backend.createMasterdata({
    language: "en_US",
    request: {
        login: { user: { language: "en_US" } },
        mobileConfig: { distribution: {} },
        resources: { "plantId": { type: "PT" } },
        products: { "P1": {} },
        warehouses: {
            "wh1": {},
            "wh2": {},
            "whInTransit": { inTransit: true },
        },
        packingInstructions: {
            "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
        },
        handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh1', qty: 100 } },
        distributionOrders: {
            "DD1": {
                warehouseFrom: "wh1",
                warehouseTo: "wh2",
                warehouseInTransit: "whInTransit",
                plant: "plantId",
                lines: [{ product: "P1", qtyEntered: 100 }],
            }
        },
    }
});

// noinspection JSUnusedLocalSymbols
test('Workflow start from a PICKING launcher lands on the job screen and does NOT bounce to home', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Workflow start does not bounce to the home menu (picking launcher)');
    allure.severity('critical');

    const masterdata = await createPickingMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);

    // startJob({documentNo}) is a single launcher tap + PickingJobScreen.waitForScreen() (no retry),
    // so a bounce-to-home surfaces as the job screen never arriving. It then leaves us on the job
    // screen; assert we did NOT bounce to the home menu.
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.expectVisible();
    await ApplicationsListScreen.expectNotDisplayed();
    expect(page.url()).toContain('/picking/wf/');
});

// noinspection JSUnusedLocalSymbols
test('Workflow start from a DISTRIBUTION launcher lands on the job screen and does NOT bounce to home', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Workflow start does not bounce to the home menu (distribution launcher)');
    allure.severity('critical');

    const masterdata = await createDistributionMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });

    // startJob({launcherTestId}) is a single launcher tap + DistributionJobScreen.waitForScreen()
    // (no retry) — same rationale as the picking scenario.
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await ApplicationsListScreen.expectNotDisplayed();
    expect(page.url()).toContain('/distribution/wf/');
});

// noinspection JSUnusedLocalSymbols
test('Dead deep-link to a WF-process URL (no loaded process) redirects to the home menu', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Redirect-home guard still fires for a genuinely-absent process (cold deep-link)');
    allure.severity('critical');

    // Log in first so the app is authenticated (the auth token is kept in a cookie, so it survives a
    // cold navigation) and the store has NO wfProcess for the id we deep-link to.
    const masterdata = await createPickingMasterdata();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Navigate COLD to a picking WF-process URL whose process id is not (and never was) in the store.
    const deadDeepLink = `${FRONTEND_BASE_URL}/picking/wf/NO-SUCH-WFPROCESS-999999999`;
    await page.goto(deadDeepLink, { waitUntil: 'load' });

    // The guard must redirect to the home menu (the deferred goHome still fires) — not hang on a blank frame.
    await ApplicationsListScreen.expectVisible();
    await PickingJobScreen.expectNotDisplayed();
});

// noinspection JSUnusedLocalSymbols
test('Reload while a job is open: plain reload keeps the job; a re-hydrated store without the process redirects home', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Reload behaviour: F5 keeps the job; a genuinely-absent process on cold boot redirects home');
    allure.severity('critical');

    const masterdata = await createPickingMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.expectVisible();
    const jobUrl = page.url();
    expect(jobUrl).toContain('/picking/wf/');

    // 3(a) — a PLAIN reload keeps the operator in the job (F5-resilience): the redux store is
    // persisted to localStorage and re-hydrated on boot, so the process is still loaded and the guard
    // does not fire. Locking this in protects the operator against losing their job on an accidental
    // refresh.
    await page.reload({ waitUntil: 'load' });
    await PickingJobScreen.expectVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    await ApplicationsListScreen.expectNotDisplayed();
    expect(page.url()).toBe(jobUrl);

    // 3(b) — now model a cold boot where the re-hydrated store does NOT contain the process
    // (localStorage evicted / private mode / a fresh browser opening a bookmarked deep-link). The auth
    // token lives in a cookie, so the session survives; only the persisted redux store is gone. On the
    // next reload the process is not loaded and the guard's (deferred) goHome must still redirect home
    // — not hang on a blank job frame.
    await page.evaluate(() => window.localStorage.clear());
    await page.reload({ waitUntil: 'load' });
    await ApplicationsListScreen.expectVisible();
    await PickingJobScreen.expectNotDisplayed();
});

// noinspection JSUnusedLocalSymbols
test('Stale launchers refresh after start does not prune the just-started process nor bounce home', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Stale launchers snapshot (fetched before start, delivered after) does not delete the started process');
    allure.severity('critical');

    const masterdata = await createPickingMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // Arm a one-shot hold on the NEXT full launchers-list query (countOnly:false). We fetch its real
    // (stale) snapshot NOW — before the job is started, so the snapshot does not list the process —
    // and deliver it only AFTER the start. This is the exact production race (a launchers refresh
    // issued before the start resolving after it), forced deterministically via a delayed response
    // rather than left to chance. The filter screen's count query (countOnly:true) and the /facets
    // endpoint are left untouched so the filter UI still works.
    let armed = false;
    let intercepted = false;
    let markCaptured;
    const staleCaptured = new Promise((resolve) => { markCaptured = resolve; });
    let releaseHeld;
    const held = new Promise((resolve) => { releaseHeld = resolve; });

    await page.route('**/userWorkflows/launchers/query', async (route) => {
        let body = {};
        try { body = route.request().postDataJSON() ?? {}; } catch { /* no body */ }
        if (!armed || intercepted || body.countOnly === true) {
            return route.continue();
        }
        intercepted = true;
        const staleResponse = await route.fetch(); // snapshot computed now, before the start
        markCaptured();
        await held;                                 // wait until the job has been started
        await route.fulfill({ response: staleResponse });
    });

    // Trigger the held query by applying the document-number filter (the only sales order → the SO1
    // launcher stays visible from the initial query while this filtered query is held). Do NOT wait
    // for the list to settle (it cannot — the query is held on purpose).
    armed = true;
    await PickingJobsListScreen.clickFilterButton();
    await PickingJobsListFiltersScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await staleCaptured;

    // Start the job while the stale refresh is still in flight. A foreground query shows a `.loading`
    // overlay that would intercept a hit-tested tap, but that overlay is an artifact of using a filter
    // query as the stale-refresh trigger — the production race is driven by a background refresh with
    // no such overlay. The reducer path exercised (POPULATE_LAUNCHERS_COMPLETE pruning) is identical
    // either way, so a dispatched click reaches the exact documented state (job started while a stale
    // launchers snapshot is pending).
    await PickingJobsListScreen.startJobByDispatchClick({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // Now deliver the stale snapshot. Its POPULATE_LAUNCHERS_COMPLETE must NOT delete the just-started
    // process (its local update is newer than the request that fetched the snapshot), so we stay on
    // the job screen and do not bounce home. Without the reducer fix this prunes the process and the
    // redirect-home guard fires.
    releaseHeld();
    await PickingJobScreen.expectRemainsDisplayed();
    await ApplicationsListScreen.expectNotDisplayed();
    await page.unroute('**/userWorkflows/launchers/query');
});
