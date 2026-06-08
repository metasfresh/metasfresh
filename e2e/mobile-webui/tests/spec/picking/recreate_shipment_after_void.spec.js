import { test } from "../../../playwright.config";
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

// Regression guard for the "can't recreate shipment after void" defect on aggregate-HU shipments.
//
// When a shipment whose allocation is an aggregate VHU (one M_HU representing N transport units) is
// reversed, the HU-snapshot replay re-creates the picked qty one transport unit at a time. Without
// consolidation, that leaves N identical, not-yet-shipped M_ShipmentSchedule_QtyPicked rows on the
// SAME (VHU, TU, LU, QtyLU, QtyTU, QtyPicked) tuple; when the shipment is recreated they all take one
// M_InOutLine_ID and collide on the partial unique index M_ShipmentSchedule_QtyPicked_UI — so the
// shipment can no longer be recreated. The fix consolidates the replay into the single existing row.
//
// This drives the full stack: mobile-pick the whole aggregate, generate a completed shipment from the
// picked qty, reverse it (document-engine Reverse-Correct = the "void"), and assert the reverse left
// exactly ONE active un-shipped QtyPicked row per unique-index tuple (so recreation cannot collide).
// createShipmentPolicy:'NO' keeps the mobile pick from auto-shipping; the shipment is generated via the
// testing API so its M_InOut_ID is known and can be reversed.

const createMasterdata = async () => {
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
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { 'BP1': {} },
            warehouses: { 'wh': {} },
            pickingSlots: { slot1: {} },
            products: { 'P1': { prices: [{ price: 1 }] } },
            packingInstructions: {
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 1 },
            },
            handlingUnits: {
                'HU1': { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                'SO1': {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 6, piItemProduct: 'TU' }]
                }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Reverse an aggregate-HU shipment then recreate it — no duplicate QtyPicked collision', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.story('Recreate shipment after void of an aggregate-HU shipment');
    allure.severity('blocker');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await test.step('Pick the whole aggregate HU (6 TU) and complete', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '6' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '6 TU', qtyPicked: '6 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.closeTargetLU();
        await PickingJobScreen.complete();
    });

    let shipmentId;
    await test.step('Generate a COMPLETED shipment from the picked qty', async () => {
        const ship = await Backend.createMasterdata({
            request: { shipments: { FIRST_SHIPMENT: { salesOrder: 'SO1', quantityType: 'P', complete: true } } },
        });
        shipmentId = ship?.shipments?.FIRST_SHIPMENT?.id;
        if (!shipmentId) {
            throw new Error('Initial shipment was not created:\n' + JSON.stringify(ship, null, 2));
        }
    });

    await test.step('Reverse the completed shipment (the "void") — must not leave duplicate QtyPicked rows', async () => {
        const reversed = await Backend.reverseShipment({ shipmentId });
        expect(reversed?.docStatus, 'Shipment was not reversed (expected docStatus RE):\n' + JSON.stringify(reversed, null, 2)).toBe('RE');

        // GREEN (fix): exactly 1 active un-shipped row survives per tuple, so recreation cannot collide.
        // RED  (no fix): N identical rows survive (here 6) -> guaranteed unique-index collision on recreate.
        const dup = reversed.maxIdenticalUnshippedQtyPickedRowsPerVhuTuple;
        expect(dup, `Expected the reverse to leave exactly ONE active un-shipped QtyPicked row per unique-index tuple, but found ${dup} identical rows — they collide on M_ShipmentSchedule_QtyPicked_UI when the shipment is recreated. Full response:\n` + JSON.stringify(reversed, null, 2)).toBe(1);
    });
});
