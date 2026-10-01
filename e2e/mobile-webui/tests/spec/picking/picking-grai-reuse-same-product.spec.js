/**
 * Playwright E2E — a GRAI already assigned to one crate of a loading unit must not be reusable on
 * a LATER pick of the SAME product on that same LU either: the mobile capture panel's LU-wide
 * dedupe mirror applies across separate picks of one product, not only across different products
 * (see `picking-grai-duplicate-within-lu.spec.js` for the cross-product case).
 *
 * Scenario (GRAI "Flow Through" / LU_TU profile, GRAIRequired customer, sales_order aggregation so
 * both order lines pack onto ONE shared LU):
 *  1. Pick the FIRST batch of Product1 (10 of its 20 ordered crates — a deliberate partial pick,
 *     manually entered with a "not found" reason, same mechanism as the standard partial-pick
 *     scenarios) onto the shared LU; inline-capture its 10 GRAIs — the FIRST of which is the
 *     shared GRAI `G`. Save.
 *  2. Pick Product2 (10 crates) onto the SAME LU — an unrelated pick in between; capture its own
 *     10 fresh GRAIs. Save.
 *  3. Pick the SECOND (remaining) batch of Product1 — its last 10 crates — onto the SAME LU.
 *     Re-scan the shared `G` first (physically impossible — one crate can't be on the pallet
 *     twice): the panel does NOT advance the count and shows a non-blocking "1 skipped" notice,
 *     mirroring the server-side LU-wide dedupe. The operator then scans 10 fresh GRAIs (distinct
 *     from both prior batches), reaches 10/10, and saves.
 *  4. Complete; assert the single-use invariant: `G` is on EXACTLY ONE crate/VHU of the LU
 *     (Product1's first batch). Product1's second batch and Product2's VHU never carry `G`.
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
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * A GRAIRequired customer, sales_order aggregation, pickTo:['LU_TU'], both PIs sharing LU_MAIN so
 * aggregation packs everything onto ONE LU. Product1's order-line demand is 20 crates (80 units);
 * ample stock (800 units) backs it, so the operator's FIRST pick is a deliberate manual partial
 * pick (10 of the suggested 20 crates, justified with a "not found" reason) — the same mechanism
 * the standard partial-pick scenarios use (`picking.spec.js` "Partial pick blocked, recover by
 * picking remaining"). The first pick accounts for all 20 suggested crates (10 picked + 10 marked
 * not-found), so the SECOND pick of the same HU prefills 0 and explicitly overrides qtyEntered to
 * recover the remaining 10 crates (same recovery pattern as that precedent). Product2's line (10
 * crates) is fulfilled by a single ordinary pick.
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
                HU_SOURCE_P1: { product: 'P1', warehouse: 'wh', qty: 800 },
                HU_SOURCE_P2: { product: 'P2', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 80, piItemProduct: 'TU_IFCO_P1' },
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
test('a GRAI reused on a later pick of the same product on one LU must land on at most one crate', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F5230: GRAI on Returnable Assets');
    await allure.story('GRAI single-use within a loading unit — same-product re-pick reuse');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    // 30 distinct GRAIs, sliced across the three picks: batch1 (Product1, serials 1..10, incl. shared
    // G at index 0), the Product2 pick in between (serials 11..20), batch2's fresh replacements
    // (serials 21..30). batch2 also re-scans sharedG (deduped, not counted towards its 10).
    const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, 30);
    const batch1Grais = allGrais.slice(0, 10);
    const sharedG = batch1Grais[0];
    const product2Grais = allGrais.slice(10, 20);
    const batch2Fresh = allGrais.slice(20, 30);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

    // --- First batch of product 1: a deliberate manual partial pick (10 of the suggested 20 crates) ---
    await test.step('Pick a first, partial batch of product 1 and capture its 10 GRAIs (crate #1 = shared G)', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode,
            expectQtyEntered: '20',
            qtyEntered: '10',
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
            expectNextScreen: 'PickGraiScreen',
        });
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });
        await PickGraiScreen.scanGraiBatch({ graiStrings: batch1Grais });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // --- Product 2: an unrelated pick in between, onto the same LU, with its own fresh GRAIs --------
    await test.step('Pick product 2 onto the same LU and capture its own fresh GRAIs', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P2.qrCode,
            expectQtyEntered: '10',
            expectNextScreen: 'PickGraiScreen',
        });
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });
        await PickGraiScreen.scanGraiBatch({ graiStrings: product2Grais });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // --- Second (remaining) batch of product 1: re-scanning the shared G (its own earlier crate)
    // is skipped. After the first pick accounted for all 20 suggested crates (10 picked + 10 marked
    // not-found), the dialog now prefills 0, so we explicitly override qtyEntered to recover the last
    // 10 crates — the same recovery pattern as picking.spec.js "Partial pick blocked, recover by
    // picking remaining". --
    await test.step('Pick the remaining batch of product 1 onto the same LU; re-scanning the shared G is skipped, not counted', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode,
            expectQtyEntered: '0',
            qtyEntered: '10',
            expectNextScreen: 'PickGraiScreen',
        });
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });

        // Re-scan the shared G first: the panel mirrors the server-side LU-wide dedupe — the count
        // does NOT advance and a non-blocking "1 skipped" notice appears, even though G was assigned
        // to an EARLIER crate of the SAME product's line, not a different product's.
        await PickGraiScreen.scanGrai({ graiString: sharedG });
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });
        await PickGraiScreen.expectSkippedNotice({ count: 1 });

        // Scan 10 distinct fresh GRAIs — reaches 10/10, Save enabled.
        await PickGraiScreen.scanGraiBatch({ graiStrings: batch2Fresh });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // THE INVARIANT UNDER TEST: G stays on product 1's FIRST-batch crate only. Its second batch and
    // product 2 each carry only their own GRAIs — never the reused G.
    await Backend.expect({
        title: 'Single-use invariant: G stays on product 1\'s first-batch crate; the re-pick and product 2 carry only their own GRAIs',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { vhu: 'vhuP1Batch1', tu: 'tuP1Batch1', lu: 'luMixed' },
                            { vhu: 'vhuP1Batch2', tu: 'tuP1Batch2', lu: 'luMixed' },
                        ],
                    },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed' }] },
                },
            },
        },
        hus: {
            vhuP1Batch1: { attributes: { GRAI: batch1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            vhuP1Batch2: { attributes: { GRAI: batch2Fresh.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            vhuP2: { attributes: { GRAI: product2Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });

    await PickingJobScreen.complete();

    await Backend.expect({
        title: 'After completion: G on exactly one crate; each VHU carries only its own GRAIs, processed',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { vhu: 'vhuP1Batch1', tu: 'tuP1Batch1', lu: 'luMixed', processed: true },
                            { vhu: 'vhuP1Batch2', tu: 'tuP1Batch2', lu: 'luMixed', processed: true },
                        ],
                    },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed', processed: true }] },
                },
            },
        },
        hus: {
            vhuP1Batch1: { attributes: { GRAI: batch1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            vhuP1Batch2: { attributes: { GRAI: batch2Fresh.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            vhuP2: { attributes: { GRAI: product2Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });
});
