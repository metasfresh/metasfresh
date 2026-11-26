import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

//
// Mobile-UI: un-packing (Entpacken) a catch-weight product must work correctly, including the
// real-world path where a line is picked, then picked AGAIN (surplus / duplicate), and the
// operator un-packs the surplus.
//
// SCENARIO DRIVEN (the real mobile flow, end-to-end):
//   catch-weight product -> pick the line -> pick it AGAIN (duplicate/surplus) -> un-pack the
//   surplus batch, one 1-CU step at a time (skip scanning a target HU == un-pack to floor).
//
// CONTRACT UNDER TEST:
//   After un-packing the whole surplus batch (6 of the 12 identical 1-CU picks), the line must show
//   the correct piece quantity (6 Stk) AND the correct, un-corrupted catch-weight of the 6 remaining
//   pieces (8.76 kg) — reached with NO error toast/screen during any un-pack (no
//   "QR Code ... is not assigned to HU"). The known failure mode is: an un-pack throws a
//   QR-Code-not-assigned error, and/or the remaining catch-weight comes out wrong. The fix records the
//   leaf CU + its own QR on the picking-job step, so each un-pack restores quantity and catch-weight cleanly.
//
// MODELLING NOTES (kept faithful, deliberately robust):
//   - Each catch-weight QR scan is its own 1-CU pick and creates its own picking-job step, so a
//     6-QR pickHU produces 6 one-CU steps. Two 6-QR picks = 12 identical 1-CU steps (line demand 12).
//     This reproduces the core condition (duplicate catch-weight picks into the same pick-to target,
//     then un-pack the surplus) WITHOUT dragging in the over-pick-prompt branch (an unrelated surface).
//   - Un-packing one step returns exactly 1 CU (1.460 kg) to the floor. To un-pack the whole surplus
//     "batch" of 6 pieces, un-pack 6 steps; all 12 steps are identical, so this lands on 6 Stk / 8.76 kg.
//   - Catch-weight is entered via the LMQ catch-weight QR path (multi-CU aggregate) — the path
//     exercised by the "Leich+Mehl" case in picking_catchWeight.spec.js, and the aggregate-HU
//     shape the defect lives in.
//   - The `step()` wrapper used by the screen objects fails the test on any unexpected error
//     toast/screen, so the "uuups" un-pack failure is caught even if it is transient.
//

const PER_CU_WEIGHT_KG = '1.460';                                  // one heavy catch-weight piece
const CATCH_WEIGHT_QR = `LMQ#1#${PER_CU_WEIGHT_KG}#08.11.2025#500`; // 1 piece @ 1.460 kg, lot 500
const BATCH_QR_CODES = Array(6).fill(CATCH_WEIGHT_QR);             // one batch = 6 pieces = 8.76 kg

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": {
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }]
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 100, weightNet: 10, lotNo: 'lot1', bestBeforeDate: '2031-11-23' }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Un-pack surplus of a duplicate-picked catch-weight line restores qty AND catch-weight', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section
    allure.story('Catch weight picking - un-pack surplus of a duplicate pick');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await test.step("Pick the catch-weight line (batch A = 6 Stk / 8.76 kg)", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            catchWeightQRCode: BATCH_QR_CODES,
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '6 Stk', qtyPickedCatchWeight: '8.76 kg' });
    });

    await test.step("Erroneously pick the SAME line AGAIN (batch B = surplus, 6 Stk / 8.76 kg)", async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            catchWeightQRCode: BATCH_QR_CODES,
        });
        // Both duplicate picks are now aggregated on the line: 12 Stk total, 17.52 kg total.
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '12 Stk', qtyPickedCatchWeight: '17.52 kg' });
    });

    await test.step("Un-pack the whole surplus batch (6 pieces = 6 one-CU steps; skip scanning a target HU == un-pack to floor)", async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        // Each pick created its own 1-CU step (12 total). Un-packing a step removes it and re-indexes
        // the list, so un-pack index 0 six times to return the 6 surplus pieces to the floor. All steps
        // are identical, so this leaves exactly 6 Stk / 8.76 kg regardless of which six are removed.
        // On the OLD (unfixed) code the FIRST un-pack already throws "QR Code not assigned to HU".
        for (let i = 0; i < 6; i++) {
            await PickingJobLineScreen.waitForScreen();
            await PickingJobLineScreen.clickStepButton({ index: 0 });
            await PickingJobStepScreen.unpick();
        }
        await PickingJobLineScreen.goBack();
    });

    // Required end-state after un-packing the whole surplus batch (6 of 12 pieces): correct piece
    // quantity (6 Stk) AND correct, un-corrupted catch-weight (8.76 kg for the 6 remaining pieces),
    // reached WITHOUT any error toast/screen (no QR-Code-not-assigned failure — the surrounding step()
    // wrapper fails on any unexpected error). The bug guarded against throws on the FIRST un-pack and/or
    // corrupts the remaining catch-weight.
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '6 Stk', qtyPickedCatchWeight: '8.76 kg' });
});
