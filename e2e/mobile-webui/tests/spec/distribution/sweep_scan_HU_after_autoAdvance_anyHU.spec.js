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
// The "sweep" distribution flow when the operator may serve an order from ANY handling unit — the
// setting every customer runs (MobileUI_UserProfile_DD.IsAllowPickingAnyHU = 'Y'): small quantities
// are picked off ONE staging LU at a ground locator, order after order, as the app auto-advances.
// With that setting the backend builds NO pre-allocated move plan (see DistributionJobCreateCommand),
// so no source HU is fixed at job-creation time and the app has no plan to compare the operator's
// scan against.
//
// That must NOT cost the operator a handling-unit re-scan. They physically used the SAME staging LU
// for the order just picked, and nothing contradicts that choice, so the app carries that HU forward:
// the auto-advanced screen goes straight to the product-code scan — exactly as it does when a move
// plan pins the HU (sweep_scan_product_after_autoAdvance.spec.js) — and scanning the product code
// ALONE books the pick onto the HU split off for that order. Asking for the handling unit again
// instead is the defect this spec guards: the operator's next scan is the product GTIN, which then
// lands in the HU slot and the backend rejects it as the wrong QR-code type.
//
// The fixture comes from the factory shared with sweep_scan_product_after_autoAdvance.spec.js, so the
// two specs cover the two sides of allowPickingAnyHU with everything else held constant — see
// sweepDistributionMasterdata.js. The carry-forward rule itself is owned by
// postDistributionPickFromThunk.js.
//

const createMasterdata = async () =>
    await createSweepMasterdata({
        // THE flag under test.
        allowPickingAnyHU: true,
        barcodePrefix: 'EXT-SWEEP-ANYHU',
        workplaceKey: 'sweepAnyHUWorkplace',
    });

// noinspection JSUnusedLocalSymbols
test('Sweep (pick-any-HU): after auto-advance, the operator scans only the product code (the staging LU carries forward)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Sweep (pick-any-HU): with no pre-allocated move plan, the operator still scans only the product code on every auto-advanced order');
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

    // PRECONDITION, asserted before the target assertion so a fixture/config drift fails HERE and is
    // never mistaken for the behaviour under test: DD2 really is a pick-any-HU job with no
    // pre-allocated move plan. It says nothing about which path the app then took.
    await test.step('Backend: DD2 was started in pick-any-HU mode, without a pre-allocated move plan', async () => {
        await DistributionUtils.expectPickAnyHUJobWithoutMovePlan({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });
    });

    // *** THE ASSERTION THIS SPEC EXISTS FOR ***
    // No move plan pins a source HU for DD2 — but the operator just scanned the staging LU for DD1,
    // and that same LU holds plenty of stock for DD2 too. The app must carry it forward: the operator
    // is NOT asked to re-scan the handling unit, only the product code.
    await test.step('On the auto-advanced DD2: the screen is ready for the PRODUCT scan (no HU re-scan requested)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    // Scanning ONLY the product code must book the pick — no wrong-QR-type error, no HU barcode.
    // The trailing product-scan assertion covers the SECOND order boundary: the carry-forward has to
    // hold at every auto-advance, not just the first one.
    await test.step('Scan product P for DD2 (confirm qty 20) → no error, auto-advance to DD3 which is again ready for the PRODUCT scan', async () => {
        await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P.gtin);
        await DistributionLinePickFromScreen.fillQuantityDialog({ expectedQtyToMove: 20 });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
        await DistributionLinePickFromScreen.expectProductScanReady();
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
