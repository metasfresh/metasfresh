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
// The "sweep" distribution flow when the operator may serve an order from ANY handling unit: small
// quantities are picked off ONE staging LU at a ground locator, order after order, as the app
// auto-advances. Because such an order has no source HU assigned to it up front, the app never assumes
// the next order will be served from the LU just scanned — so on every auto-advanced order the operator
// scans the staging LU again, then the product code.
//
// The fixture is deliberately field-identical to sweep_scan_product_after_autoAdvance.spec.js apart
// from the barcode prefix, the workplace key and allowPickingAnyHU: with that flag off, the very same
// data lands the operator on the PRODUCT scan instead. That contrast is what makes this spec
// discriminating, so keep the two fixtures in sync. The carry-forward rule itself is owned by
// postDistributionPickFromThunk.js.
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
                    // THE flag under test, and sticky — see e2e/mobile-webui/CLAUDE.md § "Debugging
                    // Flaky Tests" rule 3: always set, never inherited.
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
test('Sweep: after auto-advance, the operator scans the staging LU again (it does not carry forward)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Sweep: when an order may be served from any HU, the operator scans the staging LU again on each auto-advanced order, then the product code');
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
    // No HU is assigned to DD2, so the app cannot know which one will serve it — not even though the
    // staging LU the operator just scanned holds plenty of stock for DD2 as well. The operator is
    // therefore asked to scan the source HU, instead of being dropped on the product scan with an
    // assumed-but-unverified HU already applied.
    await test.step('On the auto-advanced DD2: the screen asks for the HU scan', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    // The screen alone cannot show WHY it asks: an app that had simply failed to recognise the LU just
    // picked from would land the operator on exactly the same prompt. The two checks below rule that
    // look-alike out — one on the app's own diagnostics, one on the job the backend actually built.
    await test.step('Diagnostic: the HU scan is not the fallback of a failed HU lookup', async () => {
        await DistributionLinePickFromScreen.expectHUScanNotCausedByFailedHULookup();
    });

    await test.step('Backend: DD2 was started in pick-any-HU mode, without a pre-allocated move plan', async () => {
        await DistributionUtils.expectPickAnyHUJobWithoutMovePlan({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });
    });

    await test.step('Scan the staging LU + product P for DD2 (confirm qty 20) → auto-advance to DD3, asking for the HU again', async () => {
        await DistributionLinePickFromScreen.scanHUToMove({
            huQRCode: masterdata.luExternalBarcode,
            productScannedCode: masterdata.products.P.gtin,
            expectedQtyToMove: 20,
            expectNextScreen: 'DistributionLinePickFromScreen',
        });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Backend: the DD2 pick landed (the moved qty of P is on the split-off HU)', async () => {
        const pickedHUQRCode = await DistributionUtils.getPickedHUQRCode({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });
        await Backend.expect({
            title: 'DD2 pick landed',
            hus: {
                [pickedHUQRCode]: { huStatus: 'A', warehouse: 'whInTransit', storages: { P: '20 PCE' } },
            },
        });
    });
});
