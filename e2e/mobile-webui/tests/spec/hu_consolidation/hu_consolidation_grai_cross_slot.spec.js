/**
 * TL;DR — In HU Consolidation, scanning a package that belongs to a DIFFERENT picking slot than
 * the one the worker has open must be rejected with a clear error and must leave the open slot
 * unchanged.
 *
 * Real-life flow:
 *   1. Two packages are picked, one into each of two different slots.
 *   2. The worker opens HU Consolidation and opens the first slot.
 *   3. Scanning the barcode of the package that is in the OTHER slot → "The HU is not at the picking
 *      slot"; the open slot is unchanged.
 *   4. Scanning the package that IS in the open slot then consolidates it and completes the job.
 *
 * In its own spec because it needs a different setup (two slots) than hu_consolidation_grai.spec.js.
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
 * Two picking slots and two single-line sales orders (SO1→P1, SO2→P2). Product aggregation +
 * pickTo:['TU'] so each pick materialises as a top-level GRAI-bearing TU. graiRequired:'Y' enables
 * the GRAI scanner during picking; graiMapping:true generates a scannable GRAI per TU PI.
 */
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
                    allowCompletingPartialPickingJob: true,
                },
            },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {}, slot2: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_P1: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5, graiMapping: true },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
                HU2: { product: 'P2', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }],
                },
                SO2: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P2', qty: 5, piItemProduct: 'TU2' }],
                },
            },
        },
    });
};

/**
 * Picks one GRAI-stamped top-level TU for a single-line picking job into the given picking slot.
 *
 * Precondition: the job's PickingJobScreen is showing (after startJob).
 * Scans the picking slot, opens line 1, scans the PI GRAI at the TU-target screen, scans the VHU,
 * confirms qty, then completes the single-line job (landing back on PickingJobsListScreen).
 * With pickTo:['TU'] and no LU target, each pick materialises as a genuine top-level TU carrying
 * the scanned GRAI as an HU attribute.
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

// noinspection JSUnusedLocalSymbols
test('Scan GRAI of TU not in the open slot → LuNotAtPickingSlot error toast', async ({ page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan of TU not in slot yields error');
    await allure.severity('critical');

    const masterdata = await createMasterdata();
    const graiP1 = masterdata.packingInstructions.PI_P1.grai;
    const graiP2 = masterdata.packingInstructions.PI_P2.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Pick SO1 (P1 TU) → slot1 via GRAI scan at SelectPickTargetTUScreen
    await test.step('Pick SO1 into slot1', async () => {
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ index: 1 });
        await pickOneGraiLine({
            slotQrCode: masterdata.pickingSlots.slot1.qrCode,
            grai: masterdata.packingInstructions.PI_P1.grai,
            huQrCode: masterdata.handlingUnits.HU1.qrCode,
        });
        await PickingJobsListScreen.goBack();
    });

    // Pick SO2 (P2 TU) → slot2 via GRAI scan
    await test.step('Pick SO2 into slot2', async () => {
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO2.documentNo);
        await PickingJobsListScreen.startJob({ index: 1 });
        await pickOneGraiLine({
            slotQrCode: masterdata.pickingSlots.slot2.qrCode,
            grai: masterdata.packingInstructions.PI_P2.grai,
            huQrCode: masterdata.handlingUnits.HU2.qrCode,
        });
        await PickingJobsListScreen.goBack();
    });

    await test.step('HU Consolidation — open slot1, scan GRAI of TU in slot2 → error', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

        // Open slot1 (contains the P1 TU with graiP1)
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });

        // Scan graiP2 — its TU is in slot2, NOT in the currently open slot1
        await expectErrorToast('GRAI of TU in slot2 while slot1 is open → LuNotAtPickingSlot error', async () => {
            await PickingSlotScreen.scanGRAI({ graiString: graiP2 });
            await PickingSlotScreen.waitForScreen();
        }, ({ textContent }) => {
            // Backend throws MobileQRCodeMessages.LU_NOT_AT_SLOT (AD_Message
            // de.metas.hu_consolidation.LuNotAtPickingSlot), rendered to the operator as this en_US message.
            // The scanned unit is a TU, so the message is HU-worded (not "LU") — see gh#29852.
            expect(textContent).toContain('The HU is not at the picking slot');
        });

        // Confirm slot1 state is unchanged (graiP1 TU is still there)
        await PickingSlotScreen.goBack();

        // Sanity: now consolidate graiP1 (the correct TU) successfully and complete
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });
        await PickingSlotScreen.scanGRAI({ graiString: graiP1 });
        await PickingSlotScreen.waitNotLoading();
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.complete();
    });
});
