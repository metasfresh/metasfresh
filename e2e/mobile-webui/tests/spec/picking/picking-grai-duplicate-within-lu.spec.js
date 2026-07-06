/**
 * Playwright E2E — TC20 reproduction: a GRAI reused across two products on ONE loading unit must NOT
 * end up on two crates. This is the RED spec that gates the GRAI single-use-within-an-LU fix.
 *
 * Scenario (GRAI "Flow Through" / LU_TU profile, GRAIRequired customer, sales_order aggregation so
 * both order lines pack onto ONE shared LU — identical setup to picking-grai-flowthrough-mixed-product):
 *  1. Pick Product1 (10 crates) onto the shared LU; inline-capture its 10 GRAIs — the FIRST of which
 *     is the shared GRAI `G`. Save. The LU now carries G on Product1's crate.
 *  2. Pick Product2 (10 crates) onto the SAME LU; inline-capture 10 GRAIs whose first scan REUSES the
 *     same `G` (physically impossible — one crate can't be on the pallet twice) followed by 9 fresh
 *     GRAIs. This mirrors the reference happy-path interaction (10 scans → Save) because that is what
 *     CURRENT code permits: today the capture buffer only dedupes within a single pick, so the reused
 *     `G` is distinct from Product2's 9 fresh codes → 10/10 → Save enabled.
 *  3. Complete; assert the single-use invariant: `G` is on EXACTLY ONE crate/VHU of the LU
 *     (Product1's). Product2's VHU must carry only its own 9 distinct GRAIs, never `G`.
 *
 * WHY this interaction shape (the reuse mechanism is unconfirmed by code trace — reproduce to pin it):
 *   A code trace shows the in-picking Flow-Through path *cannot* silently
 *   double — `computeDelta` distributes from a single shared pool, so re-using `G` for Product2 should
 *   leave `G` on Product1 and under-fill Product2 → `GRAI_COUNT_MISMATCH` at completion, NOT a double.
 *   But the reported symptom ("a GRAI ends up used more than once") points to the in-picking capture
 *   path rather than the LU-union path. This spec drives that exact path to completion so the RED reveals which symptom
 *   current code actually produces (double-assign vs count-mismatch vs other) — the observation that
 *   pins the fix location. It is expected RED on current code; the finalized GREEN interaction (a
 *   "N skipped" non-blocking notice + a replacement scan for the dropped `G`, per REQUIREMENTS AC6/AC7)
 *   is added in the fix task once the mechanism is confirmed and signed off.
 *
 * GRAI canonical format (see de.metas.handlingunits.grai.GRAI): "{companyPrefix}.{assetType}.{serial}".
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickGraiScreen } from '../../utils/screens/picking/PickGraiScreen';

/**
 * Same masterdata as the mixed-product Flow-Through scenario: a GRAIRequired customer, sales_order
 * aggregation, pickTo:['LU_TU'], a 2-line sales order (10 whole crates each), both PIs sharing LU_MAIN
 * so aggregation packs both products onto ONE LU.
 */
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                },
            },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_P1: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P2', product: 'P2', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU_SOURCE_P1: { product: 'P1', warehouse: 'wh', qty: 200 },
                HU_SOURCE_P2: { product: 'P2', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 40, piItemProduct: 'TU_IFCO_P1' },
                        { product: 'P2', qty: 40, piItemProduct: 'TU_IFCO_P2' },
                    ],
                },
            },
        },
    });
};

/** Build N distinct well-formed canonical GRAIs from the masterdata GRAI by varying the serial. */
const buildDistinctGrais = (baseGrai, count) => {
    const [companyPrefix, assetType] = baseGrai.split('.');
    return Array.from({ length: count }, (_, i) => `${companyPrefix}.${assetType}.${String(i + 1).padStart(6, '0')}`);
};

// noinspection JSUnusedLocalSymbols
test('a GRAI reused across two products on one LU must land on at most one crate', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F5230: GRAI on Returnable Assets');
    await allure.story('GRAI single-use within a loading unit — cross-product reuse');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    // 19 distinct GRAIs. Product1 takes serials 1..10; Product2 takes serials 11..19 (9 fresh) PLUS
    // it re-scans serial 1 (the shared `G`). `G` is Product1's crate #1 — physically already on the LU.
    const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, 19);
    const sharedG = allGrais[0];                 // Product1's crate #1 GRAI, reused for Product2
    const product1Grais = allGrais.slice(0, 10); // serials 1..10 (includes sharedG)
    const product2Fresh = allGrais.slice(10, 19); // serials 11..19 — 9 fresh, distinct from Product1
    // What Product2's operator scans: the reused G first, then its own 9 fresh crates (10 scans total).
    const product2ScanList = [sharedG, ...product2Fresh];

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

    // --- Product 1: pick its 10 crates; capture its 10 GRAIs (crate #1 == the shared G) -------------
    await test.step('Pick product 1 crates and capture their 10 GRAIs (crate #1 = shared G)', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode,
            expectQtyEntered: '10',
            expectNextScreen: 'PickGraiScreen',
        });
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });
        await PickGraiScreen.scanGraiBatch({ graiStrings: product1Grais });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // Baseline: after product 1's atomic pick the shared LU carries exactly product 1's 10 GRAIs.
    await Backend.expect({
        title: 'After product 1 pick: the shared LU carries product 1\'s 10 GRAIs (incl. shared G)',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed' }] },
                    P2: {},
                },
            },
        },
        hus: {
            vhuP1: { attributes: { GRAI: product1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });

    // --- Product 2: pick its 10 crates onto the SAME LU; the first scan REUSES G --------------------
    await test.step('Pick product 2 crates onto the same LU; re-scan the shared G among its 10 GRAIs', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P2.qrCode,
            expectQtyEntered: '10',
            expectNextScreen: 'PickGraiScreen',
        });
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });
        // Current code dedupes only within this pick's buffer, so [G, 11..19] are 10 distinct codes
        // → 10 / 10 → Save enabled. (Post-fix this becomes 9/10 + a "1 skipped" notice; the fix task
        // finalizes that interaction. Here we drive the buggy path to completion.)
        await PickGraiScreen.scanGraiBatch({ graiStrings: product2ScanList });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // THE INVARIANT UNDER TEST (RED on current code): G is on Product1's crate ONLY. Product2's VHU
    // must carry only its own 9 fresh GRAIs — never the reused G. If current code assigns G to
    // Product2's crate too, this assertion FAILS (double-assignment). If instead the LU-union drops G
    // and under-fills Product2, completion below fails with GRAI_COUNT_MISMATCH. Either is the RED.
    await Backend.expect({
        title: 'Single-use invariant: G stays on product 1\'s crate; product 2 carries only its 9 fresh GRAIs',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed' }] },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed' }] },
                },
            },
        },
        hus: {
            vhuP1: { attributes: { GRAI: product1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            vhuP2: { attributes: { GRAI: product2Fresh.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });

    await PickingJobScreen.complete();

    await Backend.expect({
        title: 'After completion: G on exactly one crate; each VHU carries only its own GRAIs, processed',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed', processed: true }] },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed', processed: true }] },
                },
            },
        },
        hus: {
            vhuP1: { attributes: { GRAI: product1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            vhuP2: { attributes: { GRAI: product2Fresh.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });
});
