import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

// Coverage: does "Unpack item" work when the pick is to a TOP-LEVEL CU
// (pickTo: ['CU'], no LU/TU carton) — the pick-to-CU configuration?
// The existing picking_partial_unpack.spec.js only covers pickTo:['LU_TU'] (CU nested in a TU/LU).

let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `9${seq}${ts}`;
};
const gs1GtinScan = (gtin14) => `01${gtin14}`;

const createMasterdata = async ({ packedGtin }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    allowPickingAnyCustomer: true,
                    // The crux: pick to a top-level CU, NOT into a TU/LU carton.
                    pickTo: ['CU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                "P1": { gtin: packedGtin, prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 10 }],
                }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Partial unpack - top-level CU pick (no carton): unpick part of the CU to the floor', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await test.step('Pick 10 PCE into a top-level CU (no LU/TU)', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '10' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '10 Stk' });
        await Backend.expect({
            title: 'after CU pick: 10 PCE in a top-level VHU (tu/lu = -)',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "10 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: { vhu1: { storages: { P1: '10 PCE' } } },
        });
    });

    // THE KEY CHECK: a top-level CU is packed (qtyPicked=10). "Unpack item" must be enabled and reachable,
    // and removing a partial qty of that CU to the floor must actually split the CU (the bug: it was a
    // silent no-op for a top-level CU, leaving the VHU at the full 10).
    await test.step('Unpick 4 PCE of the top-level CU to the floor', async () => {
        await PickingJobScreen.expectUnpickItemButtonEnabled();
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScan, expectDefaultQty: '10', qty: '4' });

        // User-visible result first: the picking line drops to the 6 PCE that stays packed. This re-renders
        // only after the unpick is committed on the backend (websocket-driven), so it also gates the
        // Backend.expect below against reading the pre-unpick state. (Mirrors the LU_TU spec, which asserts
        // the line button before its Backend.expect.)
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '6 Stk' });
        await Backend.expect({
            title: 'after CU unpick: 6 PCE stays packed in the CU, 4 PCE dropped to floor',
            hus: { vhu1: { storages: { P1: '6 PCE' } } },
        });
    });
});
