import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { generateEAN13 } from '../../utils/ean13';

//
// The "sweep" distribution flow: an operator scans ONE staging LU (a large HU sitting at a ground
// locator) ONCE, then repeatedly scans the product code as the app auto-advances order→order,
// picking small quantities of the SAME product off that SAME LU for each order — never re-scanning
// the HU. This mirrors packingTable_navigateToNextOrder.spec.js's auto-advance setup, but with a
// single staging LU/product shared by all orders (instead of one dedicated HU per order), because
// that is what exercises the auto-advance carrying the scanned HU forward to the next order.
//
// Mobile distribution profile (same as the mirror spec):
//   navigateToJobsListAfterPickFromComplete: true — auto-advance to the next order after pick-from
//   requireScanningProductCode: true              — operator must scan the product GTIN
//   completeJobAutomatically: true                — auto-complete a job once fully moved
//   allowStartNextJobOnly: true                   — start jobs strictly in offered order
//   orderBys: 'Priority, LocatorPriority'         — offer orders sorted by priority, then source-locator priority
//

const N = 3;

// Plenty of qty on the staging LU so every DD order below is a small, partial pick off it.
const LU_QTY = 1000;

const createMasterdata = async () => {
    const luExternalBarcode = `EXT-SWEEP-${Date.now()}`;

    const distributionOrders = {};
    for (let i = 1; i <= N; i++) {
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
            login: { user: { language: "en_US", workplace: "sweepWorkplace" } },
            mobileConfig: {
                distribution: {
                    navigateToJobsListAfterPickFromComplete: true,
                    completeJobAutomatically: true,
                    requireScanningProductCode: true,
                    allowStartNextJobOnly: true,
                    orderBys: 'Priority, LocatorPriority',
                    // Job-level caption (asserted nowhere here, but kept consistent with the mirror
                    // spec's convention so the launcher/job header renders a meaningful caption).
                    captionFormat: 'LocatorFrom,LocatorTo,ProductValueAndName,Qty',
                },
            },
            // The workplace is the packing table: a single warehouse, pick-from = packingTable.
            workplaces: { sweepWorkplace: { warehouse: 'wh', pickFromLocator: 'packingTable' } },
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
test('Sweep: after auto-advance, the operator scans only the product code (the staging LU carries forward)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Sweep: scanning ONE staging LU once, the operator scans only the product code on every auto-advanced order');
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

    // *** THE RED ASSERTION ***
    // The staging LU was already scanned for DD1 and holds plenty of stock for DD2 too, so the
    // operator should not need to re-scan it — the auto-advanced screen should go straight to the
    // product-code scan. Today the app's auto-advance drops the scanned LU, leaving the screen in
    // "Scan HU" state instead: the operator's next scan (the product GTIN) is then misread as an HU
    // barcode, and this assertion fails.
    await test.step('On the auto-advanced DD2: the screen is ready for the PRODUCT scan (no HU re-scan requested)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await test.step('Scan product P for DD2 (confirm qty 20) → no error, auto-advance to DD3 pick-from', async () => {
        await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P.gtin);
        await DistributionLinePickFromScreen.fillQuantityDialog({ expectedQtyToMove: 20 });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
    });

    await test.step('Backend: the DD2 pick landed (the moved qty of P is on the split-off HU)', async () => {
        const pickedHUQRCode = await Backend.getDistributionPickedHUQRCode({
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
