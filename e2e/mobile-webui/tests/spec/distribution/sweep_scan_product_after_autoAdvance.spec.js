import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { DistributionUtils } from '../../utils/screens/distribution/DistributionUtils';
import { createSweepMasterdata } from '../../utils/sweepDistributionMasterdata';

//
// The "sweep" distribution flow: an operator scans ONE staging LU (a large HU sitting at a ground
// locator) ONCE, then repeatedly scans the product code as the app auto-advances order→order,
// picking small quantities of the SAME product off that SAME LU for each order — never re-scanning
// the HU. This mirrors packingTable_navigateToNextOrder.spec.js's auto-advance setup, but with a
// single staging LU/product shared by all orders (instead of one dedicated HU per order), because
// that is what exercises the auto-advance carrying the scanned HU forward to the next order.
//
// The fixture is built by the factory shared with sweep_scan_HU_after_autoAdvance_anyHU.spec.js, so
// the two scenarios hold the whole distribution profile and layout constant — see
// sweepDistributionMasterdata.js for that profile, and for why this scenario stands ONE handling unit
// at the source locator while the pick-any-HU one stands several.
//

const createMasterdata = async () =>
    await createSweepMasterdata({
        // This scenario's whole premise is a pre-allocated move plan carrying a FIXED source HU per
        // step (see DistributionJobCreateCommand), which the backend only builds when this is false.
        allowPickingAnyHU: false,
        barcodePrefix: 'EXT-SWEEP',
        workplaceKey: 'sweepWorkplace',
    });

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
            huQRCode: masterdata.huExternalBarcodes.HU1,
            productScannedCode: masterdata.products.P.gtin,
            expectedQtyToMove: 10,
            expectNextScreen: 'DistributionLinePickFromScreen',
        });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId });
    });

    // *** REGRESSION GUARD ***
    // The staging LU was already scanned for DD1 and holds plenty of stock for DD2 too, so the
    // operator does not need to re-scan it — the auto-advanced screen goes straight to the
    // product-code scan. The thunk carries the just-picked HU's QR code forward only when the next
    // order's pre-allocated move plan draws from that SAME physical HU (as here) — see
    // postDistributionPickFromThunk.js. Without that carry-forward, the operator's next scan (the
    // product GTIN) would be misread as an HU barcode, and this assertion would fail.
    await test.step('On the auto-advanced DD2: the screen is ready for the PRODUCT scan (no HU re-scan requested)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await test.step('Scan product P for DD2 (confirm qty 20) → no error, auto-advance to DD3 pick-from', async () => {
        await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P.gtin);
        await DistributionLinePickFromScreen.fillQuantityDialog({ expectedQtyToMove: 20 });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
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
