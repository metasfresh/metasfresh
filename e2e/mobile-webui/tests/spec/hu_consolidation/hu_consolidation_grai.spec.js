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
 *
 * Source TUs are placed directly into the picking slot (bypassing the mobile picking app)
 * via the pickingSlot field on the handlingUnit request.  This means no GRAI is stamped on
 * the TUs during picking — exactly the "cross-dock" scenario that the consolidation GRAI gate
 * is designed to catch.
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { HUConsolidationGraiScreen } from '../../utils/screens/huConsolidation/HUConsolidationGraiScreen';
// ─── Masterdata helpers ───────────────────────────────────────────────────────

/**
 * Creates masterdata for the GRAIRequired=Y consolidation scenario.
 *
 * Key decisions:
 * - BP1 has graiRequired:'Y' → the backend marks the consolidation job with graiScanEnabled=true,
 *   which shows the "GRAI scannen" button and gates the Complete button.
 * - graiMapping:true on PI_P1 makes the API generate a unique, scannable GRAI for that TU PI
 *   and return it as masterdata.packingInstructions.PI_P1.grai.
 * - PI_P1 (with lu:'LU') is used only as the consolidation target LU (setTargetLU).
 * - Source TUs (PI_TU1/PI_TU2, no lu) are placed directly into slot1 via pickingSlot —
 *   bypassing the mobile picking app so no GRAI is stamped on them during picking.
 * - The picking slot is pre-reserved for BP1 (bpartnerLocation:'BP1') so the consolidation
 *   launcher can find a job for BP1 from the non-empty slot.
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
            pickingSlots: { slot1: { bpartnerLocation: 'BP1' } },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                // PI_P1: target LU used for setTargetLU + graiMapping for the GRAI to scan.
                PI_P1: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                // TU-only PIs for source HUs placed directly into the picking slot.
                PI_TU1: { tu: 'TU1', product: 'P1', qtyCUsPerTU: 4 },
                PI_TU2: { tu: 'TU2', product: 'P2', qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                // pickingSlot:'slot1' places the TU directly into the slot queue without the
                // mobile picking app, so no GRAI is stamped — triggering the consolidation gate.
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_TU1', pickingSlot: 'slot1' },
                HU2: { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_TU2', pickingSlot: 'slot1' },
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
            pickingSlots: { slot1: { bpartnerLocation: 'BP_NOGRAI' } },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_P1: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4 },
                PI_TU1: { tu: 'TU1', product: 'P1', qtyCUsPerTU: 4 },
                PI_TU2: { tu: 'TU2', product: 'P2', qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_TU1', pickingSlot: 'slot1' },
                HU2: { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_TU2', pickingSlot: 'slot1' },
            },
        },
    });
};

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

    await test.step('Open HU Consolidation job', async () => {
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

    await test.step('Open HU Consolidation job and set target LU', async () => {
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
