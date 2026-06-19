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
// The "packing-table operator" distribution flow.
//
// Scenario: an operator works at a single PACKING TABLE. Distribution orders bring goods
// FROM many ground locators (L1..L10) TO that packing table. The operator starts the first
// offered order, scans the source HU + product, confirms the qty, and — with
// navigateToJobsListAfterPickFromComplete — is carried straight to the NEXT order's pick-from
// screen, without returning to the launcher list, until the orders are done.
//
// Mobile distribution profile:
//   navigateToJobsListAfterPickFromComplete: true  — auto-advance to the next order after pick-from
//   maxLaunchers: 20, maxStartedLaunchers: 3        — launcher list limits
//   allowStartNextJobOnly: true                     — start jobs strictly in offered order
//   requireScanningProductCode: true                — operator must scan the product GTIN
//   completeJobAutomatically: true                  — auto-complete a job once fully moved
//   orderBys: 'Priority, LocatorPriority'           — offer orders sorted by priority, then source-locator priority
//
// Layout: ONE warehouse holding 10 GROUND locators L1..L10 (ascending priorityNo) plus a
// non-ground packingTable. The workplace binds that warehouse with pickFromLocator=packingTable.
// 10 products P1..P10 (distinct GTINs); 10 HUs HU1..HU10 (HUi on Li with Pi, qty 1000, with an
// external barcode so it can be scanned); 10 DD orders DD1..DD10 — DDi moves Pi FROM Li TO the
// packing table, qty = i*10 (DD1=10 … DD10=100), seqNo ascending.
//

const N = 10;

// HUi qty: plenty so each line (max 100) is fully pickable from a single HU.
const HU_QTY = 1000;

// External barcodes for HU1..HU10 — unique per run so they can be scanned.
const huExternalBarcode = (i) => `EXT-30474-${i}-${Date.now()}`;

const createMasterdata = async () => {
    const products = {};
    const warehouseLocators = {
        // The packing table the operator stands at — NON-ground (the workplace pickFromLocator).
        packingTable: { isGroundLocator: false, priorityNo: 999, x: "PT", y: "00", z: "0" },
    };
    const handlingUnits = {};
    const distributionOrders = {};
    const externalBarcodes = {};

    for (let i = 1; i <= N; i++) {
        const Li = `L${i}`;
        const Pi = `P${i}`;
        const HUi = `HU${i}`;
        const ddi = `DD${i}`;
        const ext = huExternalBarcode(i);
        externalBarcodes[i] = ext;

        // P1..P10 — each with its own GTIN.
        products[Pi] = { gtin: generateEAN13().ean13 };

        // L1..L10 — ground locators with ascending priorityNo.
        warehouseLocators[Li] = { isGroundLocator: true, priorityNo: i * 10, x: "A", y: String(i).padStart(2, '0'), z: "0A" };

        // HUi at Li with Pi, qty 1000, external barcode set so it can be scanned.
        handlingUnits[HUi] = { product: Pi, warehouse: "wh", locator: Li, qty: HU_QTY, externalBarcode: ext };

        // DDi: product Pi, pickFrom Li, dropTo packingTable, qty = i*10.
        distributionOrders[ddi] = {
            seqNo: i * 10,
            warehouseFrom: "wh",
            warehouseTo: "wh",
            warehouseInTransit: "whInTransit",
            plant: "plantId",
            lines: [{ product: Pi, qtyEntered: i * 10, locatorFrom: Li, locatorTo: "packingTable" }],
        };
    }

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "packingWorkplace" } },
            mobileConfig: {
                distribution: {
                    navigateToJobsListAfterPickFromComplete: true,
                    completeJobAutomatically: true,
                    requireScanningProductCode: true,
                    allowStartNextJobOnly: true,
                    maxLaunchers: 20,
                    maxStartedLaunchers: 3,
                    orderBys: 'Priority, LocatorPriority',
                    // Show source locator + product + qty in the JOB header (asserted per order).
                    // Qty is a job-level caption token, so the qty-to-move appears in the header on
                    // EVERY order — including auto-advanced orders, where no single line is yet
                    // selected and the line-level header is therefore absent.
                    captionFormat: 'LocatorFrom,LocatorTo,ProductValueAndName,Qty',
                },
            },
            // The workplace is the packing table: a single warehouse, pick-from = packingTable.
            workplaces: { packingWorkplace: { warehouse: 'wh', pickFromLocator: 'packingTable' } },
            resources: { "plantId": { type: "PT" } },
            products,
            warehouses: {
                "wh": { locators: warehouseLocators },
                "whInTransit": { inTransit: true },
            },
            handlingUnits,
            distributionOrders,
        },
    });

    masterdata.externalBarcodes = externalBarcodes;
    return masterdata;
};

// noinspection JSUnusedLocalSymbols
test('Packing-table operator: orders sorted by priority then locator priority and auto-advance order→order', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Packing-table operator auto-advances from order to order, picking from ground locators by priority');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await test.step('Open the Distribution app as the packing-table operator', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
    });

    await test.step('The packing table offers the orders sorted by priority then source-locator priority (DD1..DD10)', async () => {
        await DistributionJobsListScreen.expectJobButtons(
            Array.from({ length: N }, (_, idx) => ({
                testId: masterdata.distributionOrders[`DD${idx + 1}`].launcherTestId,
            }))
        );
    });

    // Assert THIS order's full job header. The header carries the job-level caption-format fields
    // (From Locator, Product Value and Name, Quantity) for the CURRENT order. This is the regression
    // guard: the bug left these showing the PREVIOUS order's values (or undefined) after the
    // operator auto-advanced order→order — so the header is asserted on EVERY order, on whichever
    // screen renders it (the job screen for the first order, the pick-from screen for the rest).
    //
    // From Locator + Product Value and Name: exact value match — catches a stale/leftover header
    // and avoids the L1 ⊂ L10 substring trap. Quantity (server-rendered "<qty> <uomSymbol>"):
    // substring on THIS order's qty number (i*10), robust to the UOM symbol while still proving the
    // qty advanced with the order.
    const assertJobHeader = async (screen, i) => {
        // exact:true for locator + product on BOTH screens (the job screen's method defaults to
        // substring, so it is passed explicitly here) — so the leftover/stale-header guard and the
        // L1 ⊂ L10 protection hold uniformly on every order.
        await screen.expectHeaderProperty({ caption: 'From Locator', value: `L${i}`, exact: true });
        await screen.expectHeaderProperty({
            caption: 'Product Value and Name',
            value: masterdata.products[`P${i}`].productCode + "_" + masterdata.products[`P${i}`].productName,
            exact: true,
        });
        await screen.expectHeaderProperty({ caption: 'Quantity', value: `${i * 10}`, exact: false });
    };
    const assertHeaderOnJobScreen = (i) => assertJobHeader(DistributionJobScreen, i);
    const assertHeaderOnPickFromScreen = (i) => assertJobHeader(DistributionLinePickFromScreen, i);

    await test.step('Start the first offered order → it is DD1 (product P1, from locator L1, qty 10)', async () => {
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD1.jobId });
        await assertHeaderOnJobScreen(1);
    });

    // Pick DD1 on the job screen, then auto-advance DD2→DD3→…→DD10, each landing directly on the
    // next order's pick-from screen. After EACH advance, assert the new order's full header.
    await test.step('Pick DD1 (scan HU1 + product P1, confirm qty 10) → auto-advance to DD2 pick-from', async () => {
        await DistributionJobScreen.scanHUToMove({
            huQRCode: masterdata.externalBarcodes[1],
            productScannedCode: masterdata.products.P1.gtin,
            expectedQtyToMove: 10,
            expectNextScreen: 'DistributionLinePickFromScreen',
        });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId });
        await assertHeaderOnPickFromScreen(2);
    });

    for (let i = 2; i < N; i++) {
        const next = i + 1;
        await test.step(`Pick DD${i} (scan HU${i} + product P${i}, confirm qty ${i * 10}) → auto-advance to DD${next} pick-from (assert DD${next} header)`, async () => {
            await DistributionLinePickFromScreen.scanHUToMove({
                huQRCode: masterdata.externalBarcodes[i],
                productScannedCode: masterdata.products[`P${i}`].gtin,
                expectedQtyToMove: i * 10,
                expectNextScreen: 'DistributionLinePickFromScreen',
            });
            await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders[`DD${next}`].jobId });
            await assertHeaderOnPickFromScreen(next);
        });
    }

    await test.step(`Pick DD${N} (scan HU${N} + product P${N}, confirm qty ${N * 10}) → no more orders, return to jobs list`, async () => {
        await DistributionLinePickFromScreen.scanHUToMove({
            huQRCode: masterdata.externalBarcodes[N],
            productScannedCode: masterdata.products[`P${N}`].gtin,
            expectedQtyToMove: N * 10,
            expectNextScreen: 'DistributionJobsListScreen',
        });
        await DistributionJobsListScreen.waitForScreen();
    });
});
