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
// RESIDUAL GAP, stated rather than implied: the refusal is met here at the article-code prompt,
// because a single unit left to pick posts the pick directly. A multi-unit order opens the quantity
// dialog first, so the operator meets the same refusal with that dialog on screen. It travels the same
// path (DistributionPickFromScreen.onResult), and this spec does NOT cover it.
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

//
// The second case on the same fixture: the pick fails for a reason that is NOT about the handling
// unit. The operator has HU_B applied — here by scanning it on the job screen, the other way this
// screen is opened with a handling unit already selected — and the server answers the pick with a
// workflow-state conflict. The handling unit is not what went wrong, so the operator must be told what
// did, and must keep it: repeating the same scan can succeed, and taking it away costs them a scan of
// a handling unit that was never the problem.
//
// The refusal above and this failure arrive on the same endpoint (/distribution/event) and both carry
// an HTTP response, so only the backend's error code tells them apart — the HU-specific rejections
// carry one (DISTRIBUTION_HU_NOT_AT_TARGET and its siblings, MobileQRCodeMessages), while every other
// rejection on this path is a plain-message AdempiereException with none at all.
//
// RESIDUAL GAP, stated rather than implied: this covers a rejection with NO error code, which is what
// every non-HU rejection reachable here produces today (DDOrderPickFromCommand's "Already picked",
// DistributionJob.assertCanEdit, movement generation, an infra 5xx). It therefore does NOT distinguish
// the named-code list the app matches on from a plainer "any error code at all" test — no reachable
// rejection on this path separates the two.
//

// What the app really receives when the pick fails for a reason that is not about the handling unit:
// RestResponseEntityExceptionHandler answers an AdempiereException with 422 and a JsonError body, and
// a plain-message one — DDOrderPickFromCommand.executeInTrx's "Already picked" workflow-state conflict,
// raised when the schedule was already picked from — carries no errorCode and is not user-friendly, so
// the app shows its own error.InternalError text.
const WORKFLOW_CONFLICT_RESPONSE = {
    status: 422,
    contentType: 'application/json',
    body: JSON.stringify({
        errors: [
            {
                message: 'Already picked',
                userFriendlyError: false,
                parameters: {},
                stackTrace: 'de.metas.distribution.ddorder.movement.schedule.commands.pick_from.DDOrderPickFromCommand.executeInTrx',
            },
        ],
    }),
};

// The en text of error.InternalError, which is what a rejection flagged not-user-friendly surfaces as.
const EXPECTED_INTERNAL_ERROR_MESSAGE = 'If the problem persists, contact support';

// Answer the NEXT pick with the workflow-state conflict above and let every later one reach the
// backend, so the operator's retry is a real pick against real stock.
const answerNextPickWithWorkflowConflict = async (page) => {
    let alreadyAnswered = false;
    await page.route('**/distribution/event', async (route) => {
        if (alreadyAnswered) {
            await route.continue();
            return;
        }
        alreadyAnswered = true;
        await route.fulfill(WORKFLOW_CONFLICT_RESPONSE);
    });
};

test('A pick refused for a reason that is not about the handling unit keeps it selected', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.tag('F5114');
    allure.story('A pick rejected for a reason that is not about the handling unit reports that reason and leaves the selected handling unit in place, so repeating the scan books the pick');
    allure.severity('critical');

    const huABarcode = `EXT-CONFLICT-A-${Date.now()}`;
    const huBBarcode = `EXT-CONFLICT-B-${Date.now()}`;
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

    await test.step('Identify HU_B on the job screen — it holds two orders\' worth and stands at the source locator', async () => {
        await DistributionJobScreen.scanHU({ huQRCode: huBBarcode });
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await answerNextPickWithWorkflowConflict(page);

    let refusalToastText;
    await expectErrorToast(
        'Scan the article code while the server answers the pick with a workflow-state conflict',
        async () => await DistributionLinePickFromScreen.typeProductCode(articleCode),
        ({ textContent }) => {
            refusalToastText = textContent;
        }
    );

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // Asserted before the message, because this is the half that strands the operator: HU_B is not
    // what the server rejected, so it stays applied and the screen keeps asking for the article code.
    // Dropping it would send them to re-scan a handling unit that was never the problem.
    await test.step('HU_B is still the selected handling unit, so the screen still asks for the article code', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // ... and the operator is told what actually went wrong. Naming HU_B as unusable would send them
    // looking for another handling unit for a failure that had nothing to do with this one.
    await test.step('The operator is shown the failure itself, not that the handling unit cannot be picked from', async () => {
        expect(refusalToastText).toContain(EXPECTED_INTERNAL_ERROR_MESSAGE);
        expect(refusalToastText).not.toContain(EXPECTED_MESSAGE);
    });

    await test.step('Scan the article code again → the pick books off HU_B and DD1 auto-advances to DD2', async () => {
        await DistributionLinePickFromScreen.typeProductCode(articleCode);
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId });
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await test.step('Backend: DD1 moved one unit into transit and HU_B kept the rest', async () => {
        // DD1 was served by a unit split off HU_B, which exists only from the pick on, so it can only
        // be named through the QR code DD1's job step reports.
        const dd1PickedHUQRCode = await DistributionUtils.getPickedHUQRCode({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD1.jobId}`,
        });
        await Backend.expect({
            title: 'DD1 picked one unit off HU_B after the retry',
            hus: {
                [dd1PickedHUQRCode]: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
                HU_B: { warehouse: 'wh', locator: 'LZ', huStatus: 'A', storages: { P: `${HU_B_QTY - QTY_PER_ORDER} PCE` } },
            },
        });
    });
});
