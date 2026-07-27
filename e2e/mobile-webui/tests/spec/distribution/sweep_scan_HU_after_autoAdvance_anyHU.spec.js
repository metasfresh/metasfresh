import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { DistributionUtils } from '../../utils/screens/distribution/DistributionUtils';
import { generateEAN13 } from '../../utils/ean13';

//
// Auto-advance carry-forward, the "pick any HU" case.
//
// When a distribution job is fully picked, the app auto-starts the next offered order and jumps
// straight to its Pick-From screen. Whether the just-scanned source HU is carried forward to that
// screen (skipping the HU scan) is decided by HU identity — see postDistributionPickFromThunk.js.
// Three cases:
//   1. The next order's pre-allocated move plan draws from the SAME physical HU -> carry forward,
//      the operator only scans the product (covered by sweep_scan_product_after_autoAdvance.spec.js).
//   2. The next order's plan draws from a DIFFERENT HU -> omit it, the operator re-scans the HU
//      (covered by packingTable_navigateToNextOrder.spec.js).
//   3. THIS SPEC: allowPickingAnyHU=true, so the next order has NO pre-allocated move plan at all
//      (the backend skips createPlan, see DistributionJobCreateCommand) -> the set of the next
//      order's source HUs is EMPTY, nothing can match, so no HU is carried forward and the operator
//      lands on the "Scan HU" prompt. Safe default: never assume it is the same HU.
//
// The fixture is deliberately IDENTICAL to sweep_scan_product_after_autoAdvance.spec.js — ONE staging
// LU at a ground locator that every DD order draws from — so the ONLY difference between the two
// specs is allowPickingAnyHU. That is what makes this test discriminating: with the flag false the
// very same data lands the operator on the PRODUCT scan (case 1); it is the flag, not the data, that
// produces the HU scan here. A fixture with a distinct HU per order would land on "Scan HU" too, but
// via case 2 — i.e. it would prove nothing about case 3.
//
// Mobile distribution profile:
//   navigateToJobsListAfterPickFromComplete: true — auto-advance to the next order after pick-from
//   requireScanningProductCode: true              — operator must scan the product GTIN
//   completeJobAutomatically: true                — auto-complete a job once fully moved
//   allowStartNextJobOnly: true                   — start jobs strictly in offered order
//   allowPickingAnyHU: true                       — THE case under test: no move plan is built
//   orderBys: 'Priority, LocatorPriority'         — offer orders sorted by priority, then source-locator priority
//

const ORDER_COUNT = 3;

// Plenty of qty on the staging LU so every DD order below is a small, partial pick off it.
const LU_QTY = 1000;

const createMasterdata = async () => {
    const luExternalBarcode = `EXT-SWEEP-ANYHU-${Date.now()}`;

    // Single-line DD orders: auto-advance only fires once an order is FULLY picked, which a
    // single-line order reaches in one pick.
    const distributionOrders = {};
    for (let i = 1; i <= ORDER_COUNT; i++) {
        distributionOrders[`DD${i}`] = {
            seqNo: i * 10,
            warehouseFrom: "wh",
            warehouseTo: "wh",
            warehouseInTransit: "whInTransit",
            plant: "plantId",
            lines: [{ product: "P", qtyEntered: i * 10, locatorFrom: "LZ", locatorTo: "packingTable" }],
        };
    }

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "sweepAnyHUWorkplace" } },
            mobileConfig: {
                distribution: {
                    navigateToJobsListAfterPickFromComplete: true,
                    completeJobAutomatically: true,
                    requireScanningProductCode: true,
                    allowStartNextJobOnly: true,
                    // THE flag under test. With it true the backend builds no pre-allocated move plan
                    // for a started order, so the auto-advanced order has no source HU to compare
                    // against and nothing can be carried forward. It must be set here explicitly for a
                    // second reason too: it is a sticky, global, unscoped config row
                    // (MobileConfigDistributionCommand keeps the previous value when omitted, unlike
                    // its siblings which reset to false), so relying on whatever an earlier spec left
                    // behind would make this scenario order-dependent.
                    allowPickingAnyHU: true,
                    orderBys: 'Priority, LocatorPriority',
                    // Job-level caption (asserted nowhere here, but kept consistent with the mirror
                    // spec's convention so the launcher/job header renders a meaningful caption).
                    captionFormat: 'LocatorFrom,LocatorTo,ProductValueAndName,Qty',
                },
            },
            // The workplace is the packing table: a single warehouse, pick-from = packingTable.
            workplaces: { sweepAnyHUWorkplace: { warehouse: 'wh', pickFromLocator: 'packingTable' } },
            resources: { "plantId": { type: "PT" } },
            products: { "P": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh": {
                    locators: {
                        // The ground locator where the ONE staging LU sits.
                        LZ: { isGroundLocator: true, priorityNo: 10 },
                        // The non-ground target every DD order drops to.
                        packingTable: { isGroundLocator: false, priorityNo: 999 },
                    },
                },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // The single staging LU, scannable via its external barcode, holding plenty of P.
                LU: { product: "P", warehouse: "wh", locator: "LZ", qty: LU_QTY, externalBarcode: luExternalBarcode },
            },
            distributionOrders,
        },
    });

    masterdata.luExternalBarcode = luExternalBarcode;
    return masterdata;
};

// noinspection JSUnusedLocalSymbols
test('Pick-any-HU: after auto-advance, the operator is asked to scan the HU (nothing is carried forward)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('With allowPickingAnyHU, the auto-advanced order has no move plan, so the operator scans the source HU again');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await test.step('Open the Distribution app, start DD1', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD1.jobId });
    });

    await test.step('Pick DD1 off the staging LU (scan the LU + product P, confirm qty 10) → auto-advance to DD2 pick-from', async () => {
        await DistributionJobScreen.scanHUToMove({
            huQRCode: masterdata.luExternalBarcode,
            productScannedCode: masterdata.products.P.gtin,
            expectedQtyToMove: 10,
            expectNextScreen: 'DistributionLinePickFromScreen',
        });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId });
    });

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // DD2 has no pre-allocated move plan, so the app cannot know which HU will serve it — not even
    // though the staging LU the operator just scanned holds plenty of stock for DD2 as well. The
    // operator is therefore asked to scan the source HU, instead of being dropped on the product scan
    // with an assumed-but-unverified HU already applied.
    await test.step('On the auto-advanced DD2: the screen asks for the HU scan (no HU carried forward)', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
        await DistributionLinePickFromScreen.expectProductScanNotReady();
    });

    // Anti-false-positive: "Scan HU" is also what the operator gets when the carry-forward's HU
    // re-resolution merely fails (the thunk's getResolvedHUQR swallows the error and returns null).
    // Assert DD2 genuinely has no steps, so we know the empty-move-plan branch is the one that ran.
    await test.step('Backend: DD2 was started WITHOUT a pre-allocated move plan (no steps)', async () => {
        await DistributionUtils.expectNoPreAllocatedMovePlan({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });
    });
});
