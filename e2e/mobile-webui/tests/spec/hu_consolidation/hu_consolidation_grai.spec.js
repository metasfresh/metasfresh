/**
 * Playwright E2E — HU Consolidation GRAI gate + scan flow.
 *
 * When the ship-to BPartner has GRAIRequired=Y, the HU Consolidation job exposes a
 * "GRAI scannen" action button after a target LU is set.  The Complete ("Fertigstellen")
 * button is disabled while the required GRAI count on the target LU is unfilled.  Once the
 * operator navigates to the GRAI capture screen, scans the required GRAIs, and sends them,
 * the gate clears and Complete succeeds.
 *
 * Two scenarios:
 *   1. GRAIRequired=Y  → Complete blocked before GRAI scan → scan GRAIs → Complete succeeds.
 *   2. GRAIRequired=No → no "GRAI scannen" button, Complete proceeds without a GRAI step.
 *
 * All masterdata is provisioned via Backend.createMasterdata (the /api/v2/frontendTesting
 * endpoint).  GRAIRequired is set via bpartners.<id>.graiRequired.  The packing instructions
 * use graiMapping:true so the API generates a unique, scannable GRAI and returns it as
 * masterdata.packingInstructions.<id>.grai — no DB access, no hardcoded customer data.
 *
 * GRAI canonical format: "{companyPrefix}.{assetType}.{serial}" — 7-char companyPrefix.
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { HUConsolidationGraiScreen } from '../../utils/screens/huConsolidation/HUConsolidationGraiScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
// ─── Masterdata helpers ───────────────────────────────────────────────────────

/**
 * Creates masterdata for the GRAIRequired=Y consolidation scenario.
 *
 * Key decisions:
 * - BP1 has graiRequired:'Y' → the backend marks the consolidation job with graiScanEnabled=true,
 *   which shows the "GRAI scannen" button and gates the Complete button.
 * - graiMapping:true on PI_P1 makes the API generate a unique, scannable GRAI for that TU PI
 *   and return it as masterdata.packingInstructions.PI_P1.grai.
 * - The picking config matches the existing consolidation spec so the pick→slot→consolidate
 *   flow is identical; only the bpartner differs.
 */
const createMasterdataGraiRequired = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
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
                // graiMapping: true → unique GRAI generated + M_HU_PI_GRAI mapping inserted.
                // The returned GRAI is accessible at masterdata.packingInstructions.PI_P1.grai.
                PI_P1: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_P1' },
                HU2: { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_P2' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 12, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 15, piItemProduct: 'TU2' },
                    ],
                },
            },
        },
    });
};

/**
 * Creates masterdata for the GRAIRequired=No scenario — identical structure but
 * graiRequired:'N' so the job has graiScanEnabled=false and no gate applies.
 */
const createMasterdataNoGrai = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
                    allowCompletingPartialPickingJob: false,
                },
            },
            bpartners: { BP_NOGRAI: { graiRequired: 'N' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_P1: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4 },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_P1' },
                HU2: { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_P2' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP_NOGRAI',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 12, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 15, piItemProduct: 'TU2' },
                    ],
                },
            },
        },
    });
};

// ─── Shared sub-flow: pick TUs into the picking slot ─────────────────────────

/**
 * Picks HU1 and HU2 into slot1 using the picking application, then navigates
 * back to the launcher so the consolidation application can be started next.
 *
 * Mirrors the pickHUsToPickingSlot helper in hu_consolidation.spec.js.
 */
const pickHUsToPickingSlot = async ({ masterdata }) => await test.step('Pick HUs into picking slot', async () => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen' });
    await PickLineScanScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3', expectGoBackToPickingJob: false });
    await PickLineScanScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.complete();
    await PickingJobsListScreen.goBack();
});

// ─── Tests ────────────────────────────────────────────────────────────────────

// noinspection JSUnusedLocalSymbols
test('GRAIRequired=Y — Complete blocked until GRAIs scanned, then succeeds', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation GRAI — complete gate: blocked before scan, enabled after scan');
    await allure.severity('critical');

    const masterdata = await createMasterdataGraiRequired();
    // The generated GRAI for the TU PI that has graiMapping:true.
    const grai = masterdata.packingInstructions.PI_P1.grai;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickHUsToPickingSlot({ masterdata });

    await test.step('Open HU Consolidation job', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.waitForScreen();
    });

    await test.step('Set target LU — GRAI scan button appears', async () => {
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        // After the LU target is set with graiScanEnabled=true, the "GRAI scannen" button must be visible.
        await HUConsolidationJobScreen.expectGraiScanButtonVisible();
    });

    await test.step('Consolidate all TUs', async () => {
        await HUConsolidationJobScreen.consolidateAll({ pickingSlotId: masterdata.pickingSlots.slot1.id });
    });

    await test.step('Complete is blocked — GRAI required but not yet scanned', async () => {
        // The Complete/Fertigstellen button (#last-confirm-button) must be disabled:
        // isConfirmGraiReady() returns false when graiScanEnabled=true and
        // graiAssignedCount < graiExpectedCount on the current target.
        await HUConsolidationJobScreen.expectCompleteDisabled();
    });

    await test.step('Navigate to GRAI scan screen and scan the required GRAIs', async () => {
        await HUConsolidationJobScreen.clickGraiScanButton();
        await HUConsolidationGraiScreen.waitForScreen();
        // The send button is disabled with 0 GRAIs captured.
        await HUConsolidationGraiScreen.expectSendDisabled();

        // Scan the GRAI returned by the masterdata API.  One GRAI is the minimum required
        // (graiExpectedCount = number of consolidated LUs, here 1).
        await HUConsolidationGraiScreen.scanGrai({ graiString: grai });
        // Wait for the chip to appear so we know the scan was processed before asserting the button.
        await HUConsolidationGraiScreen.expectGraiChipCount({ expectedCount: 1 });
        await HUConsolidationGraiScreen.expectSendEnabled();

        // Send the GRAIs to the backend and navigate back.
        await HUConsolidationGraiScreen.clickSend();
    });

    await test.step('Return to job screen — Complete is now enabled', async () => {
        await HUConsolidationJobScreen.waitForScreen();
        // graiAssignedCount has been updated (= graiExpectedCount) → isGraiReady() is true.
        await HUConsolidationJobScreen.expectCompleteEnabled();
    });

    await test.step('Complete the consolidation job', async () => {
        await HUConsolidationJobScreen.complete();
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAIRequired=No — no GRAI step, Complete proceeds directly', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation GRAI — no GRAI step when customer has GRAIRequired=No');
    await allure.severity('normal');

    const masterdata = await createMasterdataNoGrai();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickHUsToPickingSlot({ masterdata });

    await test.step('Open HU Consolidation job and set target LU', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP_NOGRAI.bpartnerLocationId });
        await HUConsolidationJobScreen.waitForScreen();
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    });

    await test.step('No GRAI scan button shown (graiScanEnabled=false)', async () => {
        // The "GRAI scannen" button must NOT be present — graiScanEnabled=false for GRAIRequired=No.
        await HUConsolidationJobScreen.expectGraiScanButtonNotVisible();
    });

    await test.step('Consolidate all TUs and complete without a GRAI step', async () => {
        await HUConsolidationJobScreen.consolidateAll({ pickingSlotId: masterdata.pickingSlots.slot1.id });
        // Complete must be enabled immediately (no GRAI gate).
        await HUConsolidationJobScreen.expectCompleteEnabled();
        await HUConsolidationJobScreen.complete();
    });
});
