import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { DistributionUtils } from '../../utils/screens/distribution/DistributionUtils';
import { generateEAN13 } from '../../utils/ean13';
import { expectErrorToast } from '../../utils/common';

//
// Several handling units of ONE article stand at one source locator and the operator picks a series of
// single-unit orders off them — the recorded customer situation, under the setting every customer runs
// (MobileUI_UserProfile_DD.IsAllowPickingAnyHU = 'Y', so the backend pre-allocates no move plan and
// the operator chooses the handling unit themselves). Once an order takes a handling unit's whole
// content, that handling unit leaves the source locator, and the NEXT order cannot be served from it.
//
// The auto-advance carries the operator's handling unit into the next order whenever it holds that
// order's article (postDistributionPickFromThunk) — and it does still hold it, in transit. Only the
// backend can say that it may no longer be picked from, and it says so when the pick is posted. With a
// handling unit applied, the pick-from screen renders NO handling-unit input at all: it goes straight
// to the article-code scan (ScanHUAndGetQtyComponent). So the refusal has to hand the operator the
// handling-unit prompt back, on the same screen, or every article-code scan they make is refused for
// the same reason and the order can only be left.
//
// The scenario therefore drives three single-unit orders off two handling units:
//   DD1  picked whole off HU_A, which leaves the source locator -> auto-advance to DD2 with HU_A
//        carried, so DD2 asks only for the article code.
//   DD2  the article-code scan is refused because HU_A can no longer be picked from. The operator
//        must be told why AND get the handling-unit prompt back, identify HU_B, and finish DD2.
//   DD3  auto-advanced with HU_B carried — HU_B still stands at the source locator with stock left,
//        so the sweep continues on the article code alone. This is what pins that recovering from the
//        refusal does not cost the operator the carry-forward for the orders that follow.
//
// Sibling coverage: sweep_scan_HU_after_autoAdvance_anyHU.spec.js covers the plain carry-forward (the
// handling unit keeps serving order after order), and navigateToJobsListAfterPickFromComplete.spec.js
// the case the frontend can rule out up front — the next order asks for an article the handling unit
// does not hold, so nothing is carried and the prompt is there from the start.
//
// RESIDUAL GAP, stated rather than implied: the refusal covered here is the one the operator meets at
// the article-code prompt, where the pick is posted directly because a single unit is left to pick. A
// multi-unit order opens the quantity dialog first; the same refusal then arrives while that dialog is
// on screen, and no spec covers it.
//

// One unit per order — the customer's orders ask for 1-2 Stk. At one unit remaining the pick is
// posted straight from the article-code scan, with no quantity dialog in between.
const QTY_PER_ORDER = 1;

// HU_A holds exactly one order's worth, so DD1 takes it whole and it leaves the source locator.
const HU_A_QTY = QTY_PER_ORDER;
// HU_B holds two orders' worth, so it survives DD2 and is still available for DD3.
const HU_B_QTY = QTY_PER_ORDER * 2;

// The run language is en_US (set by createMasterdata), so this is the en text of
// activities.distribution.cannotPickFromSelectedHU, up to the backend's reason it interpolates.
const EXPECTED_MESSAGE = 'Cannot pick from the selected HU:';

const createMasterdata = async ({ huABarcode, huBBarcode }) =>
    await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "carriedHUWorkplace" } },
            mobileConfig: {
                distribution: {
                    // Auto-advance to the next order's pick-from screen once an order is fully picked
                    // — the moment the handling unit is carried across.
                    navigateToJobsListAfterPickFromComplete: true,
                    completeJobAutomatically: true,
                    requireScanningProductCode: true,
                    allowStartNextJobOnly: true,
                    // allowPickingAnyHU is a sticky, global config row the masterdata API leaves
                    // untouched when omitted (e2e/mobile-webui/CLAUDE.md § "Debugging Flaky Tests"
                    // rule 3), so it is always set explicitly. It is the setting every customer runs,
                    // and the one under which the operator picks the handling unit themselves.
                    allowPickingAnyHU: true,
                },
            },
            workplaces: { carriedHUWorkplace: { warehouse: 'wh', pickFromLocator: 'packingTable' } },
            resources: { "plantId": { type: "PT" } },
            products: { "P": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh": {
                    locators: {
                        // The ground locator both handling units stand at.
                        LZ: { isGroundLocator: true, priorityNo: 10 },
                        // The non-ground target every order drops to.
                        packingTable: { isGroundLocator: false, priorityNo: 999 },
                    },
                },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // Both identified by an external barcode, the way the customer's handling units are
                // labelled.
                HU_A: { product: "P", warehouse: "wh", locator: "LZ", qty: HU_A_QTY, externalBarcode: huABarcode },
                HU_B: { product: "P", warehouse: "wh", locator: "LZ", qty: HU_B_QTY, externalBarcode: huBBarcode },
            },
            distributionOrders: {
                DD1: buildOrder({ seqNo: 10 }),
                DD2: buildOrder({ seqNo: 20 }),
                DD3: buildOrder({ seqNo: 30 }),
            },
        },
    });

// Single-line orders: the auto-advance fires only once an order is FULLY picked, which a single-line
// order reaches in one pick.
const buildOrder = ({ seqNo }) => ({
    seqNo,
    warehouseFrom: "wh",
    warehouseTo: "wh",
    warehouseInTransit: "whInTransit",
    plant: "plantId",
    lines: [{ product: "P", qtyEntered: QTY_PER_ORDER, locatorFrom: "LZ", locatorTo: "packingTable" }],
});

// noinspection JSUnusedLocalSymbols
test('A carried handling unit that can no longer be picked from hands the handling-unit prompt back', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.tag('F5114');
    allure.story('When the handling unit carried across the auto-advance cannot serve the next order, the operator is told why and identifies another one without leaving the screen');
    allure.severity('critical');

    const huABarcode = `EXT-CARRIED-A-${Date.now()}`;
    const huBBarcode = `EXT-CARRIED-B-${Date.now()}`;
    const masterdata = await createMasterdata({ huABarcode, huBBarcode });
    const articleCode = masterdata.products.P.gtin;

    await test.step('Open the Distribution app and start DD1', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD1.jobId });
    });

    await test.step('Pick DD1 off HU_A (identify HU_A, scan the article code) → auto-advance to DD2 pick-from', async () => {
        await DistributionJobScreen.scanHUToMove({
            huQRCode: huABarcode,
            productScannedCode: articleCode,
            // One unit to pick: the pick is posted straight from the article-code scan.
            expectQuantityDialog: false,
            expectNextScreen: 'DistributionLinePickFromScreen',
        });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId });
    });

    // PRECONDITIONS, all asserted before the target assertions so a fixture/config drift fails HERE
    // and is never mistaken for the behaviour under test.
    await test.step('Backend: DD2 was started in pick-any-HU mode, without a pre-allocated move plan', async () => {
        await DistributionUtils.expectPickAnyHUJobWithoutMovePlan({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });
    });

    await test.step('Backend: DD1 took HU_A whole, so HU_A is in transit and no longer at the source locator', async () => {
        await Backend.expect({
            title: 'DD1 picked HU_A whole',
            hus: {
                HU_A: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${HU_A_QTY} PCE` } },
            },
        });
    });

    await test.step('On the auto-advanced DD2 the screen asks only for the article code (HU_A was carried across)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // HU_A still holds the article DD2 asks for, so the app had no way to rule it out up front — the
    // backend refuses the pick because HU_A has left the source locator. The operator must be told, in
    // words they can act on, both that the handling unit they are on cannot be picked from and why.
    await expectErrorToast(
        'Scan the article code while the carried HU_A can no longer be picked from',
        async () => await DistributionLinePickFromScreen.typeProductCode(articleCode),
        ({ textContent }) => {
            expect(textContent).toContain(EXPECTED_MESSAGE);
            // The message carries the backend's reason, which is the "why" half of it. Asserted as
            // "the placeholder was filled" rather than against the backend's wording, so this stays a
            // check on the app and not on an AD_Message text.
            expect(textContent).not.toContain('%(reason)s');
        }
    );

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // ... and the message alone would leave them stranded: with a handling unit applied this screen
    // renders no handling-unit input, so every further article-code scan is refused for the same
    // reason. The prompt must be the handling-unit one again, on this same screen.
    await test.step('The screen asks for the handling unit again', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Identify HU_B instead and scan the article code → DD2 is picked and auto-advances to DD3', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(huBBarcode);
        await DistributionLinePickFromScreen.typeProductCode(articleCode);
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
    });

    await test.step('On the auto-advanced DD3 the screen asks only for the article code (HU_B was carried across)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await test.step('Scan the article code for DD3 → the last order is picked and the jobs list is shown', async () => {
        await DistributionLinePickFromScreen.typeProductCode(articleCode);
        await DistributionJobsListScreen.waitForScreen();
    });

    await test.step('Backend: all three orders are picked — one unit in transit per order', async () => {
        // DD2 was served by a unit split off HU_B, which exists only from the pick on, so it can only
        // be named through the QR code DD2's job step reports.
        const dd2PickedHUQRCode = await DistributionUtils.getPickedHUQRCode({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });
        await Backend.expect({
            title: 'DD1, DD2 and DD3 each moved one unit of P into transit',
            hus: {
                HU_A: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
                [dd2PickedHUQRCode]: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
                HU_B: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
            },
        });
    });
});
