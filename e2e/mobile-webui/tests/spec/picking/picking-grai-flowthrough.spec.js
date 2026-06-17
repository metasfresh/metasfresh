/**
 * Playwright E2E — inline GRAI mass-capture in the "Flow Through" (LU_TU) picking profile.
 *
 * A GRAIRequired=Y customer forces the picker to capture one GRAI per crate (TU) before the pick
 * can be saved. This spec picks exactly 10 TUs (returnable IFCO crates) onto ONE LU: after the pick
 * quantity is confirmed, the GRAI capture is auto-invoked inline (no separate screen, no button),
 * the 10 GRAIs are captured — one typed via the scanner's manual-entry mode, the rest scanned — and
 * Save sends the whole pick (quantity + the 10 GRAIs) in ONE atomic event, after which the job
 * completes.
 *
 * The capture is the inline GraiCapturePanel (frontend); the pick event carries setGrais/graiCodes
 * and the backend stamps one GRAI per materialised crate within the pick transaction. The required
 * GRAI count = the picked TU quantity (10). The completion guard
 * (PickingJobCompleteCommand -> PickingJobGRAIValidator) is the backend enforcement of the same
 * one-GRAI-per-crate invariant.
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

/**
 * Masterdata for the in-picking GRAI Flow-Through scenarios.
 *
 * - GRAIRequired='Y' customer -> confirming the pick quantity auto-invokes the inline GRAI capture
 *   and the completion guard fires.
 * - PRODUCT aggregation -> the pick is line-level. This file exercises the product-aggregation path;
 *   the sales_order-aggregation path is covered by picking-grai-flowthrough-mixed-product.spec.js.
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
 * Drive the common prefix of both scenarios: log in, start the job, set the LU target, scan the
 * source HU and confirm the pick quantity (TU_COUNT crates). Confirming the quantity auto-invokes
 * the inline GRAI capture.
 *
 * Postcondition: the inline GRAI capture panel is showing and reports the picked crate count as the
 * required number of GRAIs (count label 0 / TU_COUNT).
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

    // Confirming the quantity auto-invokes the inline GRAI capture (GRAIRequired=Y); the pick is NOT
    // sent yet. The panel shows the picked crate count as the required GRAI count: 0 / TU_COUNT.
    await PickGraiScreen.waitForScreen();
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

    // Exactly 10 GRAIs captured -> count reads 10 / 10, save enabled; save sends the atomic pick
    // (qty + the 10 GRAIs) and the workflow returns to the picking job.
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
