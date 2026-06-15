/**
 * Playwright E2E — mixed-product LU in the GRAI "Flow Through" (LU_TU) picking profile.
 *
 * A GRAIRequired=Y customer orders TWO products (10 TUs / IFCO crates each) on one sales order.
 * With sales_order aggregation the whole order is picked onto ONE shared LU, so the LU ends up
 * holding 2 x 10 = 20 crates. Each crate is a distinct returnable asset needing its own GRAI.
 *
 * Scenario (the RFID re-scan dedup on a mixed-product LU):
 *  1. Pick Product1 (10 crates) onto the LU; capture its 10 GRAIs (LU holds only Product1 -> 0/10).
 *  2. Pick Product2 (10 crates) onto the SAME LU; re-open the GRAI screen. It must now require the
 *     LU's full crate count (20, NOT the per-product 10) and pre-load the 10 already-assigned
 *     Product1 GRAIs -> 10/20, save disabled.
 *  3. RFID over-scan: the reader re-reads EVERY tag on the LU (the 10 already-assigned Product1 GRAIs
 *     + the 10 new Product2 GRAIs). The screen must dedup the already-assigned ones and only add the
 *     new crates -> exactly 20/20.
 *  4. Complete; verify the LU carries exactly the 20 scanned GRAIs (10 per product VHU).
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
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickGraiScreen } from '../../utils/screens/picking/PickGraiScreen';

const TU_PER_PRODUCT = 10;
const TU_MIXED_LU = TU_PER_PRODUCT * 2;
const QTY_CUS_PER_TU = 4;

/**
 * Masterdata for the mixed-product Flow-Through scenario: a GRAIRequired customer, sales_order
 * aggregation, pickTo:['LU_TU'], and a 2-line sales order (10 whole crates of each product). Both
 * products share one LU type (LU_MAIN) so sales_order aggregation packs them onto ONE LU. Source HUs
 * are plain bulk stock (the TUs are materialised on the target LU during picking, per the order
 * line's piItemProduct). Only PI_P1 carries graiMapping — one returned canonical GRAI is enough to
 * derive all distinct crate GRAIs from (the in-picking GRAI mass-capture screen records GRAIs as
 * plain HU attributes; it does no M_HU_PI_GRAI TU-type resolution).
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
                // qtyTUsPerLU leaves headroom over the 20 crates the order will pack.
                PI_P1: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P1', product: 'P1', qtyCUsPerTU: QTY_CUS_PER_TU, graiMapping: true },
                PI_P2: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P2', product: 'P2', qtyCUsPerTU: QTY_CUS_PER_TU },
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
                        { product: 'P1', qty: TU_PER_PRODUCT * QTY_CUS_PER_TU, piItemProduct: 'TU_IFCO_P1' },
                        { product: 'P2', qty: TU_PER_PRODUCT * QTY_CUS_PER_TU, piItemProduct: 'TU_IFCO_P2' },
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

/** Open the GRAI mass-capture screen for the (shared) picked LU via the given picked line. */
const openGraiScreenViaLine = async ({ lineIndex }) => {
    await PickingJobScreen.clickLineButton({ index: lineIndex });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.expectGraiScanButtonVisible();
    await PickingJobLineScreen.clickGraiScanButton();
    await PickGraiScreen.waitForScreen();
};

// noinspection JSUnusedLocalSymbols
test('Flow Through: mixed-product LU counts all crates and dedups already-assigned GRAIs on RFID re-scan', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Flow Through — mixed-product LU crate count and dedup of re-scanned already-assigned GRAIs');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    // TU_MIXED_LU distinct GRAIs: the first TU_PER_PRODUCT are Product1's crates, the next
    // TU_PER_PRODUCT are Product2's. Product1's GRAIs are reused verbatim in the RFID re-scan set
    // (the reader re-reads every tag already on the LU).
    const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, TU_MIXED_LU);
    const product1Grais = allGrais.slice(0, TU_PER_PRODUCT);
    const product2Grais = allGrais.slice(TU_PER_PRODUCT, TU_MIXED_LU);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // Flow Through: scan the picking slot and set the (one) shared LU target at job level.
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

    // --- Product 1: pick its crates onto the LU (pickHU auto-routes to the P1 line) -------------
    await test.step('Pick product 1 crates, then capture their GRAIs (LU holds only product 1)', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode, expectQtyEntered: String(TU_PER_PRODUCT) });

        await openGraiScreenViaLine({ lineIndex: 1 });
        // Only product 1 is on the LU yet -> required count is TU_PER_PRODUCT.
        await PickGraiScreen.expectCount({ scanned: 0, total: TU_PER_PRODUCT });

        await PickGraiScreen.scanGraiBatch({ graiStrings: product1Grais });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: TU_PER_PRODUCT });
        await PickGraiScreen.expectCount({ scanned: TU_PER_PRODUCT, total: TU_PER_PRODUCT });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // --- Product 2: pick its crates onto the SAME LU --------------------------------------------
    await test.step('Pick product 2 crates onto the same LU', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU_SOURCE_P2.qrCode, expectQtyEntered: String(TU_PER_PRODUCT) });
    });

    await test.step('Re-open GRAI screen: required count is the LU total and product-1 GRAIs are pre-loaded', async () => {
        await openGraiScreenViaLine({ lineIndex: 2 });
        // The LU now holds TU_MIXED_LU crates -> required count is the LU total, NOT the per-product
        // count. The TU_PER_PRODUCT already-assigned product-1 GRAIs are re-loaded from the backend.
        await PickGraiScreen.expectGraiChipCount({ expectedCount: TU_PER_PRODUCT });
        await PickGraiScreen.expectCount({ scanned: TU_PER_PRODUCT, total: TU_MIXED_LU });
        await PickGraiScreen.expectSaveDisabled();
    });

    await test.step('RFID over-scan: re-read everything in range; already-assigned GRAIs are deduped', async () => {
        // The RFID gun re-reads ALL tags on the LU: the already-assigned product-1 GRAIs PLUS the new
        // product-2 GRAIs. The screen must IGNORE the already-assigned ones (dedup against the
        // pre-loaded list) and only add the new crates -> exactly TU_MIXED_LU captured.
        await PickGraiScreen.scanGraiBatch({ graiStrings: [...product1Grais, ...product2Grais] });

        await PickGraiScreen.expectGraiChipCount({ expectedCount: TU_MIXED_LU });
        await PickGraiScreen.expectCount({ scanned: TU_MIXED_LU, total: TU_MIXED_LU });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // --- Complete + verify the exact GRAI set on the LU ----------------------------------------
    await PickingJobScreen.complete();

    // The mixed-product LU has one aggregate VHU per product; computeDelta fills each block in pick
    // order from the assigned list (product-1 GRAIs first, then product-2), so the product-1 VHU
    // carries product-1's GRAIs and the product-2 VHU carries product-2's — together EXACTLY the 20
    // scanned GRAIs, no more, no less.
    await Backend.expect({
        title: 'Mixed-product LU carries exactly the 2x10 scanned GRAIs (per product VHU)',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhuP1', tu: 'tuP1', lu: 'luMixed', processed: true }] },
                    P2: { qtyPicked: [{ vhu: 'vhuP2', tu: 'tuP2', lu: 'luMixed', processed: true }] },
                },
            },
        },
        hus: {
            vhuP1: { attributes: { GRAI: product1Grais.join(',') } },
            vhuP2: { attributes: { GRAI: product2Grais.join(',') } },
        },
    });
});
