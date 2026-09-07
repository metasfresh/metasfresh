import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { DistributionUtils } from '../../utils/screens/distribution/DistributionUtils';
import { createSweepMasterdata, SWEEP_HU_QTY } from '../../utils/sweepDistributionMasterdata';

//
// The "sweep" distribution flow when the operator may serve an order from ANY handling unit — the
// setting every customer runs (MobileUI_UserProfile_DD.IsAllowPickingAnyHU = 'Y'): several handling
// units of the same article stand at one ground locator, and small quantities are picked off the one
// the operator identifies, order after order, as the app auto-advances. With that setting the backend
// builds NO pre-allocated move plan (see DistributionJobCreateCommand), so no source handling unit is
// fixed at job-creation time and the app has no plan to compare the operator's scan against — their
// own choice among the handling units standing there is the only source information that exists.
//
// That must NOT cost the operator a handling-unit re-scan. They physically used one of those handling
// units for the order just picked, and it holds the article the next order asks for, so the app
// carries it forward: the auto-advanced screen goes straight to the article-code scan — exactly as it
// does when a move plan pins the handling unit (sweep_scan_product_after_autoAdvance.spec.js) — and
// scanning the article code ALONE books the pick. Asking for the handling unit again instead is the
// defect this spec guards: the operator's next scan is the article GTIN, which then lands in the
// handling-unit slot and the backend rejects it as the wrong QR-code type.
//
// The fixture comes from the factory shared with sweep_scan_product_after_autoAdvance.spec.js — see
// sweepDistributionMasterdata.js for what the two specs hold constant and why this one needs several
// handling units where the plan-driven sibling needs exactly one. The carry-forward rule itself is
// owned by postDistributionPickFromThunk.js.
//

// Several handling units of the same article at one source locator — the recorded situation, where a
// dozen of one article stand at a single locator. Three is what the assertions need: enough for the
// handling unit the operator identifies to be neither end of the candidates standing there.
const HU_COUNT = 3;

// The handling unit the operator identifies, out of the three. Weighing all three in the end-result
// assertion is what pins which one the picks came off; taking the MIDDLE one is what makes an
// implementation reaching for the first or the last candidate fail instead of passing by coincidence.
const IDENTIFIED_HU = 'HU2';

// DD1 (10) and DD2 (20) are both served from IDENTIFIED_HU: DD1 because the operator identified it,
// DD2 because it is carried across the auto-advance. That is what the closing assertion weighs.
const QTY_PICKED_OFF_IDENTIFIED_HU = 10 + 20;

const createMasterdata = async () =>
    await createSweepMasterdata({
        // THE flag under test.
        allowPickingAnyHU: true,
        handlingUnitCount: HU_COUNT,
        barcodePrefix: 'EXT-SWEEP-ANYHU',
        workplaceKey: 'sweepAnyHUWorkplace',
    });

// noinspection JSUnusedLocalSymbols
test('Sweep (pick-any-HU): after auto-advance, the operator scans only the article code (the handling unit they identified carries forward)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Sweep (pick-any-HU): with no pre-allocated move plan, the operator still scans only the article code on every auto-advanced order');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const identifiedHUBarcode = masterdata.huExternalBarcodes[IDENTIFIED_HU];

    await test.step('Open the Distribution app, start DD1', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD1.jobId });
    });

    await test.step(`Pick DD1 off ${IDENTIFIED_HU} (identify it by its external barcode, scan article P, confirm qty 10) → auto-advance to DD2 pick-from`, async () => {
        await DistributionJobScreen.scanHUToMove({
            huQRCode: identifiedHUBarcode,
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
    // No move plan pins a source handling unit for DD2 — but the operator just identified one for DD1,
    // and it holds plenty of stock for DD2 too. The app must carry it forward: the operator is NOT
    // asked to identify a handling unit again, only to scan the article code.
    await test.step('On the auto-advanced DD2: the screen is ready for the ARTICLE scan (no handling-unit re-scan requested)', async () => {
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    // Scanning ONLY the article code must book the pick — no wrong-QR-type error, no handling-unit
    // barcode. The trailing article-scan assertion covers the SECOND order boundary: the carry-forward
    // has to hold at every auto-advance, not just the first one.
    await test.step('Scan article P for DD2 (confirm qty 20) → no error, auto-advance to DD3 which is again ready for the ARTICLE scan', async () => {
        await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P.gtin);
        await DistributionLinePickFromScreen.fillQuantityDialog({ expectedQtyToMove: 20 });
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD3.jobId });
        await DistributionLinePickFromScreen.expectProductScanReady();
    });

    await test.step(`Backend: DD2's pick landed, and it came off ${IDENTIFIED_HU} — the other handling units at the locator are untouched`, async () => {
        // What DD2 moved is a handling unit split off at pick time, so it exists only from the pick on
        // and can only be named through the QR code DD2's job step reports.
        const pickedHUQRCode = await DistributionUtils.getPickedHUQRCode({
            wfProcessId: `distribution-${masterdata.distributionOrders.DD2.jobId}`,
        });

        // Every handling unit at the source locator, with the qty that must be left on it: the one the
        // operator identified is short exactly what DD1 and DD2 took off it, and every other one still
        // stands there full. This is what pins both picks onto the handling unit the operator chose —
        // with a single handling unit at the locator there would be nothing else they could have come
        // off, and the carry-forward's identity would go unchecked.
        const expectedSourceHUs = {};
        for (let i = 1; i <= HU_COUNT; i++) {
            const huIdentifier = `HU${i}`;
            const qtyLeft = huIdentifier === IDENTIFIED_HU ? SWEEP_HU_QTY - QTY_PICKED_OFF_IDENTIFIED_HU : SWEEP_HU_QTY;
            expectedSourceHUs[huIdentifier] = { huStatus: 'A', locator: 'LZ', storages: { P: `${qtyLeft} PCE` } };
        }

        await Backend.expect({
            title: `DD2 pick landed off ${IDENTIFIED_HU}`,
            hus: {
                [pickedHUQRCode]: { huStatus: 'A', warehouse: 'whInTransit', storages: { P: '20 PCE' } },
                ...expectedSourceHUs,
            },
        });
    });
});
