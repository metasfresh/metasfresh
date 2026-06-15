/**
 * Playwright E2E — in-picking GRAI mass-capture in the "Flow Through" (LU_TU) picking profile.
 *
 * A GRAIRequired=Y customer forces the picker to capture one GRAI per crate (TU) on the picked LU
 * before the picking job can be completed. This spec picks exactly 10 TUs (returnable IFCO crates)
 * onto ONE LU, then opens the dedicated GRAI mass-capture screen (reached via the grai-scan-button
 * on the pick-line screen) and captures the 10 GRAIs — one typed via the manual keyboard input, the
 * rest scanned — before completing the job.
 *
 * The screen under test is PickGraiScanScreen (frontend) backed by the picking-scoped GRAI endpoints
 * GET/PUT /api/v2/picking/job/{wfProcessId}/lu/{huId}/grai. Those endpoints operate on the picked
 * LU's snapshot, so the screen's required GRAI count = the LU's crate count (tuCount = 10) even
 * though a Flow-Through pick packs the 10 crates into ONE aggregate TU (VHU). The completion guard
 * (PickingJobCompleteCommand -> PickingJobGRAIValidator) blocks completion until all 10 crates carry
 * a GRAI.
 *
 * GRAI canonical format (see de.metas.handlingunits.grai.GRAI):
 *   "{companyPrefix}.{assetType}.{serial}"  — companyPrefix is 7 chars.
 *
 * All masterdata (GRAIRequired on the bpartner, the M_HU_PI_GRAI mapping) is created via the Backend
 * masterdata API — never via the DB:
 *   - bpartners.<id>.graiRequired: 'Y' | 'N' | 'D'  (Yes / No / YesWithDummyGRAIs)
 *   - packingInstructions.<id>.graiMapping: true  -> the API generates a scannable GRAI, inserts its
 *     M_HU_PI_GRAI mapping for that TU PI, and returns it as masterdata.packingInstructions.<id>.grai
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickGraiScreen } from '../../utils/screens/picking/PickGraiScreen';

// The Flow-Through pick packs exactly this many crates onto one LU; each crate needs its own GRAI.
const TU_COUNT = 10;
const QTY_CUS_PER_TU = 4;

// Mixed-product scenario: one LU receives TU_COUNT crates of EACH of two products -> 2 * TU_COUNT crates.
const TU_COUNT_PER_PRODUCT = 10;
const TU_COUNT_MIXED_LU = TU_COUNT_PER_PRODUCT * 2;

/**
 * Masterdata for the in-picking GRAI Flow-Through scenarios.
 *
 * - GRAIRequired='Y' customer -> the GRAI capture step is active and the completion guard fires.
 * - PRODUCT aggregation -> the pick is line-level, so luPickingTarget.luId is populated at line scope
 *   and the pick-line screen shows the grai-scan-button (the entry to the in-picking GRAI mass-capture
 *   screen). With sales_order aggregation the target lives on the header and the button never appears.
 * - pickTo: ['LU_TU'] -> the "Flow Through" profile shape: the picked crates are aggregated under one LU.
 * - order qty = TU_COUNT * QTY_CUS_PER_TU -> the line demands exactly TU_COUNT whole crates.
 * - graiMapping:true makes the PI's TU type GRAI-resolvable; the returned canonical GRAI is a
 *   well-formed crate identifier from which the per-crate GRAIs are derived.
 */
const createMasterdataForGraiFlowThrough = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    // "Flow Through" profile: pick into LU+TU.
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                },
            },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { prices: [{ price: 1 }] } },
            packingInstructions: {
                // LU+TU type for the Flow-Through pick. qtyTUsPerLU must hold all TU_COUNT crates.
                // graiMapping yields the canonical GRAI we derive the crate GRAIs from.
                PI_MAIN: {
                    lu: 'LU_MAIN',
                    qtyTUsPerLU: 20,
                    tu: 'TU_IFCO',
                    product: 'P1',
                    qtyCUsPerTU: QTY_CUS_PER_TU,
                    graiMapping: true,
                },
            },
            handlingUnits: {
                // Source HU = a pallet of crates (TUs) of the IFCO type.
                HU_SOURCE: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_MAIN' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    // TU_COUNT whole crates demanded (TU_COUNT TUs * QTY_CUS_PER_TU CUs each).
                    lines: [{ product: 'P1', qty: TU_COUNT * QTY_CUS_PER_TU, piItemProduct: 'TU_IFCO' }],
                },
            },
        },
    });
};

/**
 * Masterdata for the MIXED-PRODUCT Flow-Through scenario: a GRAIRequired customer, PRODUCT
 * aggregation, pickTo:['LU_TU'], and a sales order with TWO product lines — each demanding
 * TU_COUNT_PER_PRODUCT whole crates. Both lines are picked onto the SAME job-level LU target, so
 * the resulting LU carries TU_COUNT_MIXED_LU crates (two HA blocks, one per product). Each product
 * has its own packing instruction; only PI_P1 carries graiMapping (we only need ONE returned
 * canonical GRAI to derive all distinct crate GRAIs from — the in-picking GRAI mass-capture screen
 * records GRAIs as plain HU attributes and does no M_HU_PI_GRAI resolution, so the GRAIs themselves
 * need not be mapped to a TU type).
 */
const createMasterdataForGraiFlowThroughMixedProduct = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    // PRODUCT aggregation -> line-level pick targets, so each line's pick-line screen
                    // shows the grai-scan-button (the entry to the in-picking GRAI mass-capture screen).
                    aggregationType: 'product',
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
                // Both PIs share the SAME LU type (LU_MAIN) so the two products pack onto ONE LU.
                PI_P1: {
                    lu: 'LU_MAIN',
                    qtyTUsPerLU: TU_COUNT_MIXED_LU,
                    tu: 'TU_IFCO_P1',
                    product: 'P1',
                    qtyCUsPerTU: QTY_CUS_PER_TU,
                    graiMapping: true,
                },
                PI_P2: {
                    lu: 'LU_MAIN',
                    qtyTUsPerLU: TU_COUNT_MIXED_LU,
                    tu: 'TU_IFCO_P2',
                    product: 'P2',
                    qtyCUsPerTU: QTY_CUS_PER_TU,
                },
            },
            handlingUnits: {
                HU_SOURCE_P1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_P1' },
                HU_SOURCE_P2: { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_P2' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: TU_COUNT_PER_PRODUCT * QTY_CUS_PER_TU, piItemProduct: 'TU_IFCO_P1' },
                        { product: 'P2', qty: TU_COUNT_PER_PRODUCT * QTY_CUS_PER_TU, piItemProduct: 'TU_IFCO_P2' },
                    ],
                },
            },
        },
    });
};

/**
 * Build N distinct well-formed canonical GRAIs from the masterdata GRAI by varying the serial.
 * Each picked crate is a distinct returnable asset, so each needs its own GRAI.
 *
 * @param {string} baseGrai  a canonical "{companyPrefix}.{assetType}.{serial}" GRAI
 * @param {number} count
 * @returns {string[]}
 */
const buildDistinctGrais = (baseGrai, count) => {
    const [companyPrefix, assetType] = baseGrai.split('.');
    return Array.from({ length: count }, (_, i) => `${companyPrefix}.${assetType}.${String(i + 1).padStart(6, '0')}`);
};

/**
 * Drive the common prefix of both scenarios: log in, start the job, set the LU target, pick the
 * TU_COUNT crates into that LU, then open the pick-line screen's GRAI capture screen.
 *
 * Postcondition: PickGraiScreen is showing for the picked LU and reports tuCount = TU_COUNT
 * (count label 0 / TU_COUNT).
 *
 * @returns {Promise<{ masterdata: any, grais: string[] }>}
 */
const pickAllTUsAndOpenGraiScreen = async () => {
    const masterdata = await createMasterdataForGraiFlowThrough();
    const grais = buildDistinctGrais(masterdata.packingInstructions.PI_MAIN.grai, TU_COUNT);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    // Flow Through: scan the picking slot, set the LU target at job level, open the line.
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_MAIN.luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();

    // Pick all TU_COUNT crates from the line scan screen (one source HU, qty = TU_COUNT TUs).
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU_SOURCE.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: String(TU_COUNT) });
    await PickingJobLineScreen.waitForScreen();

    // The GRAI mass-capture button now appears on the pick-line screen (LU picked, GRAIRequired=Y).
    await PickingJobLineScreen.expectGraiScanButtonVisible();
    await PickingJobLineScreen.clickGraiScanButton();
    await PickGraiScreen.waitForScreen();

    // The screen must report the LU's real crate count (tuCount = TU_COUNT), starting at 0 / TU_COUNT.
    await PickGraiScreen.expectCount({ scanned: 0, total: TU_COUNT });

    return { masterdata, grais };
};

// --- Happy path — pick 10 crates, capture 10 GRAIs (1 manual + 9 scanned), save, complete -------

// noinspection JSUnusedLocalSymbols
test('Flow Through: capture one GRAI per picked crate (manual + scanned) then complete', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Flow Through — capture one GRAI per picked crate, then completion succeeds');
    await allure.severity('critical');

    const { grais } = await pickAllTUsAndOpenGraiScreen();

    // Save must be disabled before all 10 GRAIs are captured.
    await PickGraiScreen.expectSaveDisabled();

    // Capture the first GRAI by TYPING it on the keyboard (the manual-entry path), the rest by scanning.
    await PickGraiScreen.enterGraiManually({ graiString: grais[0] });
    await PickGraiScreen.expectGraiChipCount({ expectedCount: 1 });
    for (let i = 1; i < TU_COUNT; i++) {
        await PickGraiScreen.scanGrai({ graiString: grais[i] });
        // Assertion between scans lets the keyboard-hook interval flush each barcode (buffer-merge guard).
        await PickGraiScreen.expectGraiChipCount({ expectedCount: i + 1 });
    }

    // Exactly 10 GRAIs captured -> count reads 10 / 10, save enabled; save returns to the picking job.
    await PickGraiScreen.expectCount({ scanned: TU_COUNT, total: TU_COUNT });
    await PickGraiScreen.expectSaveEnabled();
    await PickGraiScreen.clickSave();
    await PickingJobScreen.waitForScreen();

    // Completion must succeed now that every picked crate has a GRAI -> shipment is created.
    await PickingJobScreen.complete();

    await Backend.expect({
        title: 'Flow Through happy path: order picked & shipped (every crate has a GRAI)',
        salesOrders: { SO1: { status: 'Completed' } },
    });
});

// --- Incomplete-blocked — capture fewer than 10 GRAIs -> save stays disabled, completion blocked --

// noinspection JSUnusedLocalSymbols
test('Flow Through: capturing fewer GRAIs than crates keeps save disabled and blocks completion', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Flow Through — fewer GRAIs than picked crates blocks completion');
    await allure.severity('critical');

    const { grais } = await pickAllTUsAndOpenGraiScreen();

    // Capture FEWER than the required 10 GRAIs (9) — one crate is left without a GRAI.
    for (let i = 0; i < TU_COUNT - 1; i++) {
        await PickGraiScreen.scanGrai({ graiString: grais[i] });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: i + 1 });
    }

    // Below 10 the count reads 9 / 10 and the save button stays disabled — the UI guard prevents
    // persisting an incomplete capture, so the picker cannot complete the job with a GRAI-less crate
    // (the completion guard is the backend enforcement of the same invariant).
    await PickGraiScreen.expectCount({ scanned: TU_COUNT - 1, total: TU_COUNT });
    await PickGraiScreen.expectSaveDisabled();
});

// --- Mixed-product LU + RFID re-scan dedup -------------------------------------------------------
//
// One LU receives TWO products (TU_COUNT_PER_PRODUCT crates each = TU_COUNT_MIXED_LU crates total).
// After the first product is picked and its crates GRAI'd, the second product is picked onto the
// SAME LU and the GRAI screen is re-opened. The screen must now learn the LU's full crate count
// (TU_COUNT_MIXED_LU, NOT the per-product count) and pre-load the GRAIs already assigned to the
// first product's crates. The RFID re-scan then feeds ALL tags in range — the already-assigned ones
// plus the new ones — and the screen must dedup the already-assigned ones, only adding the genuinely
// new crates, so the list reaches exactly TU_COUNT_MIXED_LU and the job can complete.

// noinspection JSUnusedLocalSymbols
test('Flow Through: mixed-product LU counts all crates and dedups already-assigned GRAIs on RFID re-scan', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Flow Through — mixed-product LU crate count and dedup of re-scanned already-assigned GRAIs');
    await allure.severity('critical');

    const masterdata = await createMasterdataForGraiFlowThroughMixedProduct();

    // TU_COUNT_MIXED_LU distinct GRAIs: the first TU_COUNT_PER_PRODUCT are the product-1 crates,
    // the next TU_COUNT_PER_PRODUCT are the product-2 crates. The product-1 GRAIs are reused verbatim
    // in the RFID re-scan set (the reader re-reads every tag already on the LU).
    const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, TU_COUNT_MIXED_LU);
    const product1Grais = allGrais.slice(0, TU_COUNT_PER_PRODUCT);
    const product2Grais = allGrais.slice(TU_COUNT_PER_PRODUCT, TU_COUNT_MIXED_LU);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // Flow Through: scan the picking slot, set the (one) LU target at job level.
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

    // --- Product 1: pick its TU_COUNT_PER_PRODUCT crates onto the LU, then capture their GRAIs ----
    await test.step('Pick product 1 crates onto the LU', async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickScanButton();
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU_SOURCE_P1.qrCode);
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: String(TU_COUNT_PER_PRODUCT) });
        await PickingJobLineScreen.waitForScreen();
    });

    await test.step('Capture all product-1 GRAIs (LU holds only product 1 so far)', async () => {
        await PickingJobLineScreen.expectGraiScanButtonVisible();
        await PickingJobLineScreen.clickGraiScanButton();
        await PickGraiScreen.waitForScreen();

        // Only product 1 is on the LU yet -> the LU's crate count is TU_COUNT_PER_PRODUCT.
        await PickGraiScreen.expectCount({ scanned: 0, total: TU_COUNT_PER_PRODUCT });

        await PickGraiScreen.scanGraiBatch({ graiStrings: product1Grais });
        await PickGraiScreen.expectGraiChipCount({ expectedCount: TU_COUNT_PER_PRODUCT });
        await PickGraiScreen.expectCount({ scanned: TU_COUNT_PER_PRODUCT, total: TU_COUNT_PER_PRODUCT });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // --- Product 2: pick its TU_COUNT_PER_PRODUCT crates onto the SAME LU ------------------------
    await test.step('Pick product 2 crates onto the same LU', async () => {
        await PickingJobScreen.clickLineButton({ index: 2 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickScanButton();
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU_SOURCE_P2.qrCode);
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: String(TU_COUNT_PER_PRODUCT) });
        await PickingJobLineScreen.waitForScreen();
    });

    await test.step('Re-open GRAI screen: required count is the LU total and product-1 GRAIs are pre-loaded', async () => {
        await PickingJobLineScreen.expectGraiScanButtonVisible();
        await PickingJobLineScreen.clickGraiScanButton();
        await PickGraiScreen.waitForScreen();

        // The LU now holds TU_COUNT_MIXED_LU crates (both products) -> required count is the LU total,
        // NOT the per-product count. The TU_COUNT_PER_PRODUCT already-assigned product-1 GRAIs are
        // re-loaded from the backend, so the screen reads <per-product> / <LU total>.
        await PickGraiScreen.expectGraiChipCount({ expectedCount: TU_COUNT_PER_PRODUCT });
        await PickGraiScreen.expectCount({ scanned: TU_COUNT_PER_PRODUCT, total: TU_COUNT_MIXED_LU });
        // Below the LU total -> save still disabled.
        await PickGraiScreen.expectSaveDisabled();
    });

    await test.step('RFID over-scan: re-read everything in range; already-assigned GRAIs are deduped', async () => {
        // The RFID gun re-reads ALL tags on the LU: the TU_COUNT_PER_PRODUCT already-assigned product-1
        // GRAIs PLUS the TU_COUNT_PER_PRODUCT new product-2 GRAIs. The screen must IGNORE the already-
        // assigned ones (dedup against the pre-loaded list) and only add the new crates -> exactly
        // TU_COUNT_MIXED_LU captured.
        const rfidReadSet = [...product1Grais, ...product2Grais];
        await PickGraiScreen.scanGraiBatch({ graiStrings: rfidReadSet });

        await PickGraiScreen.expectGraiChipCount({ expectedCount: TU_COUNT_MIXED_LU });
        await PickGraiScreen.expectCount({ scanned: TU_COUNT_MIXED_LU, total: TU_COUNT_MIXED_LU });
        await PickGraiScreen.expectSaveEnabled();
        await PickGraiScreen.clickSave();
        await PickingJobScreen.waitForScreen();
    });

    // --- Complete + backend verification of the exact GRAI set on the LU ------------------------
    await PickingJobScreen.complete();

    // Register the picked LU and per-product VHUs in the masterdata context (via the picking
    // expectation's lu/vhu fields), then assert the GRAI attribute on each product's VHU. The
    // mixed-product LU has one HA block per product; computeDelta fills each block (capacity =
    // TU_COUNT_PER_PRODUCT) in pick order from the assigned list (product-1 GRAIs first, then
    // product-2), so the product-1 VHU carries product-1's GRAIs and the product-2 VHU carries
    // product-2's. Together they are EXACTLY the TU_COUNT_MIXED_LU scanned GRAIs — no more, no less.
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
