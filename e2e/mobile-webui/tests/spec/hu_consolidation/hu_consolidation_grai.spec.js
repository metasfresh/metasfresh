/**
 * TL;DR — In HU Consolidation, a worker scans a package's barcode on the Picking Slot screen to
 * add that exact package onto the target pallet. This test proves each scanned package ends up on
 * the target pallet (the pallet holds both packages) and the slot is emptied.
 *
 * Real-life flow:
 *   1. Two packages (different products) are picked into a picking slot, each carrying its barcode.
 *   2. The worker opens HU Consolidation, starts a job and chooses the target pallet.
 *   3. The worker scans each package's barcode on the picking slot. Each scanned package moves
 *      onto the target pallet; once both are scanned, the slot is empty.
 *
 * Companion error-case tests (separate spec, hu_consolidation_grai_cross_slot.spec.js, and below):
 *   - Scan a barcode that matches no package  → "No HU found for this QR code".
 *   - Scan a package that is in a different slot → "The HU is not at the picking slot".
 */

import { test } from '../../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { PickingSlotScreen } from '../../utils/screens/huConsolidation/PickingSlotScreen';
import { expectErrorToast } from '../../utils/common';

/**
 * Creates masterdata for the GRAI-based HU consolidation scenarios.
 *
 * Uses product aggregation + pickTo:['TU'] so each line gets its own line-level TU target
 * and each pick produces a top-level TU (qtyLUs=0) in the picking slot.
 * graiRequired:'Y' on the bpartner enables the GRAI scanner at SelectPickTargetTUScreen.
 * graiMapping:true on PI_P1 and PI_P2 generates unique scannable GRAIs for each TU type.
 * lu+qtyTUsPerLU are kept so HUConsolidationJobScreen.setTargetLU can use PI_P1.luName.
 * createShipmentPolicy:'NO' leaves TUs in the slot after job completion.
 */
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    // PRODUCT aggregation → line-level TU targets → GRAI scanner reachable per line.
                    // With SALES_ORDER aggregation isLineLevelPickTarget=false → no line-level TU button.
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
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
                // lu + qtyTUsPerLU: the response carries luName for setTargetLU in consolidation.
                // During picking, NO LU target is set → each pick materialises as a top-level TU.
                PI_P1: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5, graiMapping: true },
            },
            handlingUnits: {
                // Bare VHUs — the GRAI TU target is set per-line via PickingGraiScanPanel,
                // which materialises the TU with the GRAI attribute at pick time.
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
                HU2: { product: 'P2', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 4, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 5, piItemProduct: 'TU2' },
                    ],
                },
            },
        },
    });
};

/**
 * Picks one GRAI-stamped top-level TU for a single-line picking job into the picking slot.
 *
 * Precondition: the job's PickingJobScreen is showing (after startJob).
 * Flow (product aggregation + pickTo:['TU']):
 *   1. Scan picking slot → stays on PickingJobScreen
 *   2. Click line 1 → PickingJobLineScreen (lineId in URL)
 *   3. Click TU target button → SelectPickTargetTUScreen (PickingGraiScanPanel visible)
 *   4. Scan PI GRAI → navigates back to PickingJobLineScreen (TU target set, GRAI pending)
 *   5. Click scan → PickLineScanScreen → scan VHU QR → GetQuantityDialog → Done
 *   6. PickingJobLineScreen → goBack → PickingJobScreen → Complete (lands on PickingJobsListScreen)
 *
 * No LU target is set during picking. With pickTo:['TU'], each pick materialises as a genuine
 * top-level TU (qtyLUs=0) carrying the scanned GRAI as an HU attribute.
 */
const pickOneGraiLine = async ({ slotQrCode, grai, huQrCode }) => {
    await PickingJobScreen.scanPickingSlot({ qrCode: slotQrCode });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
    await PickingGraiScanPanel.scanGrai({ graiString: grai });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(huQrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();
};

/**
 * Picks two GRAI-stamped top-level TUs (one per product) into the same picking slot.
 *
 * Product aggregation splits SO1's two lines (P1, P2) into TWO separate single-line picking
 * jobs. We filter the jobs list by the SO document number once, then pick the two jobs
 * sequentially (each `startJob({index:1})` selects the first remaining job; after the first
 * job completes, only the P2 job remains). Both picks land in slot1.
 *
 * The two jobs are asserted in separate Backend.expect calls with distinct TU aliases
 * (tu1 = P1, tu2 = P2) — the alias context accumulates across calls (in testContext.lastContext),
 * so a shared alias name would collide. Later assertions reference the tu1/tu2 aliases directly.
 */
const pickGraiTUsToPickingSlot = async ({ masterdata }) => await test.step('Pick TUs into picking slot via GRAI', async () => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    // Both product-aggregation jobs carry SO1's document number — filter once, then disambiguate
    // the two product launchers by their qty-to-deliver (P1 line qty=4, P2 line qty=5). A bare
    // index is ambiguous: after job 1 completes its launcher lingers briefly alongside P2's, so
    // index:1 could hit either. qtyToDeliver selects the intended product's launcher deterministically.
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);

    // --- Job 1: product P1 (qtyToDeliver=4) ---
    const { pickingJobId: pickingJobId1 } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 4 });
    await pickOneGraiLine({
        slotQrCode: masterdata.pickingSlots.slot1.qrCode,
        grai: masterdata.packingInstructions.PI_P1.grai,
        huQrCode: masterdata.handlingUnits.HU1.qrCode,
    });
    await Backend.expect({
        title: 'P1 job: top-level TU with GRAI in slot1',
        pickings: {
            [pickingJobId1]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                        ],
                    },
                },
            },
        },
        hus: {
            tu1: { storages: { P1: '4 PCE' }, attributes: { GRAI: masterdata.packingInstructions.PI_P1.grai } },
        },
    });

    // --- Job 2: product P2 (qtyToDeliver=5; the completed P1 launcher may still linger) ---
    const { pickingJobId: pickingJobId2 } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 5 });
    await pickOneGraiLine({
        slotQrCode: masterdata.pickingSlots.slot1.qrCode,
        grai: masterdata.packingInstructions.PI_P2.grai,
        huQrCode: masterdata.handlingUnits.HU2.qrCode,
    });
    await PickingJobsListScreen.goBack();

    // Verify the picking slot now contains both top-level TUs (qtyLUs=0) each carrying its GRAI.
    await Backend.expect({
        title: 'P2 job: both GRAI TUs present in slot1',
        pickings: {
            [pickingJobId2]: {
                shipmentSchedules: {
                    P2: {
                        qtyPicked: [
                            { qtyPicked: '5 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu2', lu: '-', processed: false, shipmentLineId: '-' },
                        ],
                    },
                },
            },
        },
        pickingSlots: {
            [masterdata.pickingSlots.slot1.qrCode]: {
                queue: [
                    { hu: 'tu1' },
                    { hu: 'tu2' },
                ],
            },
        },
        hus: {
            tu2: { storages: { P2: '5 PCE' }, attributes: { GRAI: masterdata.packingInstructions.PI_P2.grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan TU GRAI on PickingSlotScreen → TU consolidated onto target LU', async ({ page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan selects TU for consolidation (happy path)');
    await allure.severity('critical');

    const masterdata = await createMasterdata();
    const graiP1 = masterdata.packingInstructions.PI_P1.grai;
    const graiP2 = masterdata.packingInstructions.PI_P2.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickGraiTUsToPickingSlot({ masterdata });

    // The target LU is created mid-flow (on the first consolidate); its global QR code is captured
    // from the live job below so the end result — both TUs parented under it — can be asserted.
    let targetLUQRCode;

    await test.step('HU Consolidation — scan both TU GRAIs', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });

        // Set a new LU target using the PI for P1 (both TU types fit on any LU in practice)
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });

        // The customer requires GRAI, so the scan affordance is shown.
        await PickingSlotScreen.expectScannerVisible();

        // Scan first TU's GRAI → consolidates tu1 onto the target LU
        await PickingSlotScreen.scanGRAI({ graiString: graiP1 });
        // Assertion between scans: ensures the keyboard hook flushes before the next scan
        // (CLAUDE.md § "Back-to-Back type() calls — Buffer Concatenation")
        await PickingSlotScreen.waitNotLoading();

        // Scan second TU's GRAI → consolidates tu2 onto the target LU
        await PickingSlotScreen.scanGRAI({ graiString: graiP2 });
        await PickingSlotScreen.waitNotLoading();

        // Both TUs are now consolidated; the target is now an existing LU. Capture its QR code
        // so the final assertion can check the LU actually contains both TUs as children.
        targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();

        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.complete();
    });

    // End result: the target LU now contains BOTH scanned TUs as its children (parent→child),
    // each still carrying its product storage and its GRAI — i.e. consolidation actually parented
    // them under the LU, not merely removed them from the slot. The picking slot is empty.
    // NB: the tu1/tu2 keys are the masterdata ALIASES bound in the accumulated backend context by
    // the picking expects; targetLUQRCode is the LU's global QR code captured above — both are
    // valid `hus` matchers (a raw numeric HU id is rejected as an invalid QR code by the assert API).
    await Backend.expect({
        title: 'GRAI-scan consolidation: both TUs parented under target LU, slot emptied',
        hus: {
            // The actual end result: target LU → [tu1, tu2] (creation order), each with its GRAI.
            [targetLUQRCode]: {
                tus: [
                    { storages: { P1: '4 PCE' }, attributes: { GRAI: graiP1 } },
                    { storages: { P2: '5 PCE' }, attributes: { GRAI: graiP2 } },
                ],
            },
            tu1: { storages: { P1: '4 PCE' }, attributes: { GRAI: graiP1 } },
            tu2: { storages: { P2: '5 PCE' }, attributes: { GRAI: graiP2 } },
        },
        pickingSlots: {
            [masterdata.pickingSlots.slot1.qrCode]: { queue: [] },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan unknown GRAI on PickingSlotScreen → HuNotFound error toast', async ({ page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan with unknown GRAI yields error');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    // A valid-format GRAI that has no M_HU_Attribute match in the DB
    const unknownGrai = '9999999.99998.UNKNOWN_SERIAL';

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickGraiTUsToPickingSlot({ masterdata });

    await test.step('HU Consolidation — scan unknown GRAI → error', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });

        await expectErrorToast('Unknown GRAI → HuNotFound error', async () => {
            await PickingSlotScreen.scanGRAI({ graiString: unknownGrai });
            await PickingSlotScreen.waitForScreen();
        }, ({ textContent }) => {
            // Backend throws MobileQRCodeMessages.HU_NOT_FOUND (AD_Message de.metas.mobile.qr.HuNotFound),
            // rendered to the operator as this en_US message.
            expect(textContent).toContain('No HU found for this QR code');
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan a garbage / non-GRAI barcode on PickingSlotScreen → rejected, nothing consolidated', async ({ page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan with a garbage barcode is safely rejected');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    // A free-form, non-GRAI string. (GRAI parsing is lenient, so it is treated as a GRAI that
    // simply matches no HU — the operator sees the same "no HU found" rejection as an unknown GRAI;
    // the point of this case is that arbitrary garbage input is safely rejected and consolidates nothing.)
    const garbageBarcode = 'NOT-A-GRAI-BARCODE-12345';

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickGraiTUsToPickingSlot({ masterdata });

    await test.step('HU Consolidation — scan a garbage barcode → error, nothing consolidated', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });

        await expectErrorToast('Garbage barcode → rejected', async () => {
            await PickingSlotScreen.scanGRAI({ graiString: garbageBarcode });
            await PickingSlotScreen.waitForScreen();
        }, ({ textContent }) => {
            // A non-resolvable scanned code is rejected; nothing is consolidated.
            expect(textContent).toContain('No HU found for this QR code');
        });
    });

    // The garbage scan changed nothing: both picked TUs are still in the slot, none on a target LU.
    await Backend.expect({
        title: 'garbage scan consolidated nothing — both TUs still in slot1',
        pickingSlots: {
            [masterdata.pickingSlots.slot1.qrCode]: { queue: [{ hu: 'tu1' }, { hu: 'tu2' }] },
        },
        hus: {
            tu1: { storages: { P1: '4 PCE' } },
            tu2: { storages: { P2: '5 PCE' } },
        },
    });
});
