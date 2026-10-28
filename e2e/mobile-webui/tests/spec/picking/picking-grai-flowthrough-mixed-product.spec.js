/**
 * Playwright E2E — mixed-product LU in the GRAI "Flow Through" (LU_TU) picking profile (inline capture).
 *
 * A GRAIRequired=Y customer orders TWO products (10 TUs / IFCO crates each) on one sales order.
 * With sales_order aggregation the whole order is picked onto ONE shared LU, so the LU ends up
 * holding 2 x 10 = 20 crates. Each crate is a distinct returnable asset needing its own GRAI.
 *
 * With the inline atomic capture, each pick captures its OWN crates' GRAIs at pick time (there is no
 * re-openable per-LU GRAI screen anymore):
 *  1. Pick Product1 (10 crates) onto the LU; the inline GRAI capture is auto-invoked for THIS pick
 *     (0 / 10). The 10 GRAIs are read as overlapping RFID-gun bursts (whitespace-separated batches,
 *     like the HU-Manager RFID batch scan) — the deduped merge collapses the overlap back to 10.
 *     Save sends Product1's pick + its 10 GRAIs atomically; the resulting LU then carries exactly
 *     Product1's 10 GRAIs.
 *  2. Pick Product2 (10 crates) onto the SAME LU; the inline capture is auto-invoked for the second
 *     pick (0 / 10, independent of product 1); scan its 10 GRAIs the same way; Save. The shared LU
 *     must now carry BOTH picks' GRAIs (the second pick UNIONS, it does not wipe the first).
 *  3. Complete; verify each product's VHU carries exactly its own 10 GRAIs (20 on the LU in total).
 *
 * Lives in its own file (separate from picking-grai-flowthrough.spec.js) because it uses a different
 * createMasterdata setup — per the convention "Playwright tests that don't share the same
 * createMasterdata belong in separate files" (e2e/mobile-webui/CLAUDE.md § Test Organization).
 *
 * GRAI canonical format (see de.metas.handlingunits.grai.GRAI):
 *   "{companyPrefix}.{assetType}.{serial}"  — companyPrefix is 7 chars.
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
 * Masterdata for the mixed-product Flow-Through scenario: a GRAIRequired customer, sales_order
 * aggregation, pickTo:['LU_TU'], and a 2-line sales order (10 whole crates of each product). Both
 * products share one LU type (LU_MAIN) so sales_order aggregation packs them onto ONE LU. Source HUs
 * are plain bulk stock (the TUs are materialised on the target LU during picking, per the order
 * line's piItemProduct). Only PI_P1 carries graiMapping — one returned canonical GRAI is enough to
 * derive all distinct crate GRAIs from (GRAIs are recorded as plain HU attributes during the atomic
 * pick; no M_HU_PI_GRAI TU-type resolution is involved).
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
                // Both PIs share the SAME LU type so sales_order aggregation packs both onto one LU.
                // qtyTUsPerLU (40) leaves headroom over the 20 crates the order will pack; qtyCUsPerTU
                // is 4, so each line's 10 crates = 10 x 4 = 40 CUs.
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
                        // 10 whole crates of each product (10 TUs x 4 CUs/TU = 40 CUs per line).
                        { product: 'P1', qty: 40, piItemProduct: 'TU_IFCO_P1' },
                        { product: 'P2', qty: 40, piItemProduct: 'TU_IFCO_P2' },
                    ],
                },
            },
        },
    });
};

/**
 * Build N distinct well-formed canonical GRAIs from the masterdata GRAI by varying the serial.
 * Each picked crate is a distinct returnable asset, so each needs its own GRAI.
 */
const buildDistinctGrais = (baseGrai, count) => {
    const [companyPrefix, assetType] = baseGrai.split('.');
    return Array.from({ length: count }, (_, i) => `${companyPrefix}.${assetType}.${String(i + 1).padStart(6, '0')}`);
};

// noinspection JSUnusedLocalSymbols
test('Flow Through: each pick onto a mixed-product LU captures its own crate GRAIs (RFID re-read deduped)', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Flow Through — per-pick inline GRAI capture on a shared mixed-product LU');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    // 20 distinct GRAIs: the first 10 are Product1's crates, the next 10 are Product2's.
    const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, 20);
    const product1Grais = allGrais.slice(0, 10);
    const product2Grais = allGrais.slice(10, 20);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // Flow Through: scan the picking slot and set the (one) shared LU target at job level.
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

    // --- Product 1: pick its 10 crates; the inline capture is auto-invoked for THIS pick ---------
    await test.step('Pick product 1 crates and capture their GRAIs inline (overlapping RFID bursts, deduped)', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode,
            expectQtyEntered: '10',
            expectNextScreen: 'PickGraiScreen',
        });
        // This pick is 10 crates -> required count is 10, independent of any later pick.
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });

        // Capture the 10 GRAIs as two OVERLAPPING RFID-gun bursts (mirrors the HU-Manager RFID batch
        // scan): a whitespace/Enter-separated batch each, the second re-reading crates 4-7 of the
        // first. The deduped merge collapses the overlap so the count lands on exactly 10.
        await PickGraiScreen.scanGraiBatch({ graiStrings: product1Grais.slice(0, 7) });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 7 });
        await PickGraiScreen.scanGraiBatch({ graiStrings: product1Grais.slice(3, 10) });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });

        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // After product 1's atomic pick, the shared LU must carry EXACTLY product 1's 10 GRAIs.
    await Backend.expect({
        title: 'After product 1 pick: the shared LU carries product 1\'s 10 GRAIs',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed' }] },
                    P2: {}, // P2's schedule exists but is not yet picked
                },
            },
        },
        hus: {
            // Picked-target VHU also carries the consignee (BP1's single default ship-to → BP1_singleBPLocationI).
            vhuP1: { attributes: { GRAI: product1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });

    // --- Product 2: pick its 10 crates onto the SAME LU; a fresh inline capture for the 2nd pick --
    await test.step('Pick product 2 crates onto the same LU and capture their GRAIs inline', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU_SOURCE_P2.qrCode,
            expectQtyEntered: '10',
            expectNextScreen: 'PickGraiScreen',
        });
        // The second pick's capture starts fresh at 0 / 10 (it is not the LU's running total).
        await PickGraiScreen.expectCount({ scanned: 0, total: 10 });

        await PickGraiScreen.scanGraiBatch({ graiStrings: product2Grais.slice(0, 7) });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 7 });
        await PickGraiScreen.scanGraiBatch({ graiStrings: product2Grais.slice(3, 10) });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });

        await PickGraiScreen.expectCount({ scanned: 10, total: 10 });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // After product 2's atomic pick onto the SAME LU, the shared LU must carry BOTH picks' GRAIs:
    // the second pick UNIONS with the first, it does NOT wipe product 1's GRAIs (each product is its
    // own aggregate VHU; product-1's VHU keeps product-1's GRAIs, product-2's VHU carries product-2's).
    await Backend.expect({
        title: 'After product 2 pick: the shared LU carries BOTH products\' GRAIs (second pick unions, not wipes)',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed' }] },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed' }] },
                },
            },
        },
        hus: {
            // Picked-target VHU also carries the consignee (BP1's single default ship-to → BP1_singleBPLocationI).
            vhuP1: { attributes: { GRAI: product1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            // Picked-target VHU also carries the consignee (BP1's single default ship-to → BP1_singleBPLocationI).
            vhuP2: { attributes: { GRAI: product2Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });

    // --- Complete + verify the final picked/shipped state -----------------------------------------
    await PickingJobScreen.complete();

    await Backend.expect({
        title: 'Mixed-product LU carries exactly the 2x10 scanned GRAIs (per product VHU), picked & processed',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed', processed: true }] },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed', processed: true }] },
                },
            },
        },
        hus: {
            // Picked-target VHU also carries the consignee (BP1's single default ship-to → BP1_singleBPLocationI).
            vhuP1: { attributes: { GRAI: product1Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
            // Picked-target VHU also carries the consignee (BP1's single default ship-to → BP1_singleBPLocationI).
            vhuP2: { attributes: { GRAI: product2Grais.join(',') }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        },
    });
});
