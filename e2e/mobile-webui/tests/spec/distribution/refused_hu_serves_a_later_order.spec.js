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
// A refusal to pick from a handling unit says something about the ORDER it was refused on, not about
// the handling unit for the rest of the shift. The same unit can serve a later order of the same
// sweep, and when the auto-advance carries it there the screen must ask only for the article code —
// AC1 holds "at the first auto-advance and at every subsequent one".
//
// Two source ground locators of the same article, which is why a refusal here is recoverable at all:
// an order draws from one locator (DD_OrderLine.M_Locator_ID), and a handling unit standing at the
// other is refused with DISTRIBUTION_HU_NOT_AT_TARGET (DistributionHUService.assertHUCanBePickedFrom)
// while staying exactly where it was, still full, still pickable for an order that draws from ITS
// locator. Set under IsAllowPickingAnyHU='Y', the setting every customer runs, so the backend
// pre-allocates no move plan and the operator chooses the handling unit themselves.
//
// The sweep, four single-unit orders:
//   DD1 (from LZ1)  the operator scans HU_P_LZ2 on the job screen — it stands at the other locator,
//                   so the pick is refused and the handling-unit prompt comes back. They identify
//                   HU_P_LZ1 and finish DD1.
//   DD2 (from LZ1, article Q)  nothing is carried across: HU_P_LZ1 holds P, not Q
//                   (postDistributionPickFromThunk gates the carry on isHUServingWholeOrder). The
//                   operator identifies HU_Q_LZ1 and finishes DD2.
//   DD3 (from LZ2)  again nothing is carried: HU_Q_LZ1 holds Q, not P. The operator identifies
//                   HU_P_LZ2 — the unit refused back on DD1 — and it picks, because DD3 draws from
//                   the locator it stands at.
//   DD4 (from LZ2)  auto-advanced with HU_P_LZ2 carried across, and it can serve DD4. The screen must
//                   ask only for the article code.
//
// Sibling coverage: carried_hu_cannot_serve_next_order.spec.js covers the refusal itself (the message
// and the handling-unit prompt coming back), sweep_scan_HU_after_autoAdvance_anyHU.spec.js the plain
// carry-forward, navigateToJobsListAfterPickFromComplete.spec.js the case the app rules out up front.
//

// One unit per order — the customer's orders ask for 1-2 Stk. At one unit remaining the pick is
// posted straight from the article-code scan, with no quantity dialog in between.
const QTY_PER_ORDER = 1;

// HU_P_LZ2 serves DD3 and DD4 and still has stock left afterwards, so nothing about the end state
// depends on it being emptied.
const HU_P_LZ2_QTY = QTY_PER_ORDER * 3;

// The run language is en_US (set by createMasterdata), so this is the en text of
// activities.distribution.cannotPickFromSelectedHU, up to the backend's reason it interpolates.
const EXPECTED_REFUSAL_MESSAGE = 'Cannot pick from the selected HU:';

const createMasterdata = async ({ huPLZ1Barcode, huPLZ2Barcode, huQLZ1Barcode }) =>
    await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "refusedHUWorkplace" } },
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
            workplaces: { refusedHUWorkplace: { warehouse: 'wh', pickFromLocator: 'packingTable' } },
            resources: { "plantId": { type: "PT" } },
            products: { "P": { gtin: generateEAN13().ean13 }, "Q": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh": {
                    locators: {
                        // Two source ground locators of the same article. An order names one of them,
                        // and a handling unit at the other cannot be picked from for that order.
                        LZ1: { isGroundLocator: true, priorityNo: 10 },
                        LZ2: { isGroundLocator: true, priorityNo: 20 },
                        // The non-ground target every order drops to.
                        packingTable: { isGroundLocator: false, priorityNo: 999 },
                    },
                },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // All identified by an external barcode, the way the customer's handling units are
                // labelled.
                HU_P_LZ1: { product: "P", warehouse: "wh", locator: "LZ1", qty: QTY_PER_ORDER, externalBarcode: huPLZ1Barcode },
                HU_P_LZ2: { product: "P", warehouse: "wh", locator: "LZ2", qty: HU_P_LZ2_QTY, externalBarcode: huPLZ2Barcode },
                HU_Q_LZ1: { product: "Q", warehouse: "wh", locator: "LZ1", qty: QTY_PER_ORDER, externalBarcode: huQLZ1Barcode },
            },
            distributionOrders: {
                DD1: buildOrder({ seqNo: 10, product: "P", locatorFrom: "LZ1" }),
                DD2: buildOrder({ seqNo: 20, product: "Q", locatorFrom: "LZ1" }),
                DD3: buildOrder({ seqNo: 30, product: "P", locatorFrom: "LZ2" }),
                DD4: buildOrder({ seqNo: 40, product: "P", locatorFrom: "LZ2" }),
            },
        },
    });

// Single-line orders: the auto-advance fires only once an order is FULLY picked, which a single-line
// order reaches in one pick.
const buildOrder = ({ seqNo, product, locatorFrom }) => ({
    seqNo,
    warehouseFrom: "wh",
    warehouseTo: "wh",
    warehouseInTransit: "whInTransit",
    plant: "plantId",
    lines: [{ product, qtyEntered: QTY_PER_ORDER, locatorFrom, locatorTo: "packingTable" }],
});

// noinspection JSUnusedLocalSymbols
test('A handling unit refused on one order is carried across the auto-advance into a later one it can serve', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.tag('F5114');
    allure.story('A refusal to pick from a handling unit is scoped to the order it happened on, so a later order of the sweep still gets it carried across the auto-advance');
    allure.severity('critical');

    const huPLZ1Barcode = `EXT-REFUSED-P-LZ1-${Date.now()}`;
    const huPLZ2Barcode = `EXT-REFUSED-P-LZ2-${Date.now()}`;
    const huQLZ1Barcode = `EXT-REFUSED-Q-LZ1-${Date.now()}`;
    const masterdata = await createMasterdata({ huPLZ1Barcode, huPLZ2Barcode, huQLZ1Barcode });
    const articleCodeP = masterdata.products.P.gtin;
    const articleCodeQ = masterdata.products.Q.gtin;

    await test.step('Open the Distribution app and start DD1', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD1.jobId });
    });

    await test.step('On DD1 the operator picks HU_P_LZ2, which stands at the other source locator', async () => {
        await DistributionJobScreen.scanHU({ huQRCode: huPLZ2Barcode });
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await expectErrorToast(
        'Scan the article code while HU_P_LZ2 is not at the locator DD1 draws from',
        async () => await DistributionLinePickFromScreen.typeProductCode(articleCodeP),
        ({ textContent }) => expect(textContent).toContain(EXPECTED_REFUSAL_MESSAGE)
    );

    await test.step('The screen asks for the handling unit again', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Identify HU_P_LZ1 and finish DD1 → auto-advance to DD2, which asks for article Q', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(huPLZ1Barcode);
        await DistributionLinePickFromScreen.typeProductCode(articleCodeP);
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId });
        // HU_P_LZ1 holds P, DD2 asks for Q, so nothing is carried across and the prompt is the
        // handling-unit one from the start.
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Pick DD2 off HU_Q_LZ1 → auto-advance to DD3, which asks for article P again', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(huQLZ1Barcode);
        await DistributionLinePickFromScreen.typeProductCode(articleCodeQ);
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
        // HU_Q_LZ1 holds Q, DD3 asks for P, so again nothing is carried across.
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Pick DD3 off HU_P_LZ2 — the handling unit DD1 refused — and auto-advance to DD4', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(huPLZ2Barcode);
        await DistributionLinePickFromScreen.typeProductCode(articleCodeP);
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD4.jobId });
    });

    // PRECONDITIONS, asserted before the target assertion so a fixture/config drift fails HERE and is
    // never mistaken for the behaviour under test.
    await test.step('Backend: DD4 was started in pick-any-HU mode, without a pre-allocated move plan', async () => {
        await DistributionUtils.expectPickAnyHUJobWithoutMovePlan({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD4.jobId}`,
        });
    });

    await test.step('Backend: HU_P_LZ2 still stands at LZ2 with stock, so carrying it into DD4 is legitimate', async () => {
        await Backend.expect({
            title: 'HU_P_LZ2 survived the DD3 pick at its own locator',
            hus: {
                HU_P_LZ2: {
                    warehouse: 'wh',
                    locator: 'LZ2',
                    huStatus: 'A',
                    storages: { P: `${HU_P_LZ2_QTY - QTY_PER_ORDER} PCE` },
                },
            },
        });
    });

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // DD4 draws from LZ2, HU_P_LZ2 stands there with stock left, and DD3 just picked off it — so the
    // auto-advance carries it across and the operator needs only the article code. The refusal DD1
    // met is spent: it belonged to DD1, an order that drew from the other locator.
    await test.step('On the auto-advanced DD4 the screen asks only for the article code (HU_P_LZ2 was carried across)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await test.step('Scan the article code for DD4 → the last order is picked and the jobs list is shown', async () => {
        await DistributionLinePickFromScreen.typeProductCode(articleCodeP);
        await DistributionJobsListScreen.waitForScreen();
    });

    await test.step('Backend: all four orders are picked — one unit in transit per order', async () => {
        // DD3 and DD4 were each served by a unit split off HU_P_LZ2, which exists only from the pick
        // on, so it can only be named through the QR code that order's job step reports.
        const dd3PickedHUQRCode = await DistributionUtils.getPickedHUQRCode({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD3.jobId}`,
        });
        const dd4PickedHUQRCode = await DistributionUtils.getPickedHUQRCode({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD4.jobId}`,
        });
        await Backend.expect({
            title: 'DD1..DD4 each moved one unit into transit, and HU_P_LZ2 kept the rest',
            hus: {
                HU_P_LZ1: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
                HU_Q_LZ1: { warehouse: 'whInTransit', huStatus: 'A', storages: { Q: `${QTY_PER_ORDER} PCE` } },
                [dd3PickedHUQRCode]: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
                [dd4PickedHUQRCode]: { warehouse: 'whInTransit', huStatus: 'A', storages: { P: `${QTY_PER_ORDER} PCE` } },
                HU_P_LZ2: {
                    warehouse: 'wh',
                    locator: 'LZ2',
                    huStatus: 'A',
                    storages: { P: `${HU_P_LZ2_QTY - 2 * QTY_PER_ORDER} PCE` },
                },
            },
        });
    });
});
