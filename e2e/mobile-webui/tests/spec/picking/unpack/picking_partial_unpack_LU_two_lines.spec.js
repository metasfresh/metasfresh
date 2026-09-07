import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";

// A valid, unique 14-digit GS1 GTIN per run (strict GTIN->product resolve, AD_Client_ID=METASFRESH).
let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `9${seq}${ts}`;
};
const gs1GtinScan = (gtin14) => `01${gtin14}`;

// TWO order lines (two products, two shipment schedules), each packed as a whole TU (piItemProduct set)
// into its OWN TU, but BOTH TUs are nested under the SAME shared LU (sales_order aggregation sets one
// LU pick target for the whole job). This exercises the mobile "Unpack item" LU/TU-target unpick path
// (deleteByTopLevelHUsAndShipmentScheduleId), as opposed to the bare-TU floor-unpick reduce path covered
// by picking_partial_unpack_TU_floor_two_lines.spec.js (path A).
//
// IMPORTANT — investigated and confirmed via direct DB/HU-code reading + live Backend.expect probes
// (not guessed): unlike path A's bare TU (which is never physically detached — it stays the one shared
// container the whole time), path B's PickingJobUnPickCommand.unpickWholeHUs ALWAYS extracts (detaches)
// the picked-to node itself before HUShipmentScheduleBL.deleteByTopLevelHUsAndShipmentScheduleId /
// resetConsigneeIfNoActivePickedRows ever run. For a whole-TU pick under an LU, the extracted/reset node
// is the product's OWN TU (tu1/tu2) — NEVER the shared LU, which stays entirely out of scope for this
// call site. Confirmed empirically: the shared LU's own C_BPartner_ID is untouched throughout (it never
// clears). On the extracted TU itself, the consignee is RETAINED, not cleared: the deleted picked rows
// net to zero but survive as active rows (pre-existing HU-transaction/allocation machinery), so the
// reset guard's "no active picked rows" precondition never holds, and a deferred re-stamp
// (addQtyPickedAndUpdateHU) re-applies the consignee to the TU regardless. So this spec asserts
// retention on each line's own TU, and that the shared LU's consignee is never touched.
const createMasterdata = async ({ gtinP1, gtinP2 }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    // LU/TU target (not a bare TU): both lines' TUs nest under the SAME shared LU.
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { gtin: gtinP1, prices: [{ price: 1 }] },
                "P2": { gtin: gtinP2, prices: [{ price: 1 }] },
            },
            packingInstructions: {
                // Each product gets its OWN TU packing instruction, but both share the SAME LU name — the
                // two physical TUs (tu1, tu2) end up as siblings nested under the SAME shared LU (lu1).
                "PI1": { lu: "LU", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 200 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 4, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 4, piItemProduct: 'TU2' },
                    ]
                }
            },
        }
    });
};

const loginAndStartJob = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // Set the shared LU pick target ONCE, at job level — both lines pack their own TU into THIS LU.
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    return { pickingJobId };
};

// noinspection JSUnusedLocalSymbols
test('Unpick a whole line fully from its own TU under a shared LU retains that TU\'s consignee and leaves the shared LU untouched, without disturbing the other still-active line', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ gtinP1: uniqueGtin14(), gtinP2: uniqueGtin14() });
    const packedScanP1 = gs1GtinScan(masterdata.products.P1.gtin);
    const packedScanP2 = gs1GtinScan(masterdata.products.P2.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick all 1 TU (4 PCE) of P1 into its own TU under the shared LU', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 TU', qtyPicked: '1 TU' });
    });

    await test.step('Pick all 1 TU (4 PCE) of P2 into its own TU under the SAME shared LU', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '1 TU', qtyPicked: '1 TU' });

        await Backend.expect({
            title: 'after both picks: P1 (tu1) and P2 (tu2) each packed as their own TU, both nested under the SAME shared LU (lu1)',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] },
                        P2: { qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: false, shipmentLineId: '-' }] },
                    }
                }
            },
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '4 PCE', P2: '4 PCE' }, bpartner: 'BP1' },
                tu1: { huStatus: 'S', storages: { P1: '4 PCE' } },
                tu2: { huStatus: 'S', storages: { P2: '4 PCE' } },
            }
        });
    });

    await test.step('Unpick ALL of P1 (its whole TU) to the floor — P1 drops to ZERO while P2 still has active qty nested under the shared LU', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScanP1, expectDefaultQty: '4', qty: '4' });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 TU', qtyPicked: '0 TU' });
        // P2's line button is untouched by the P1 unpick.
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '1 TU', qtyPicked: '1 TU' });

        await Backend.expect({
            title: 'after driving P1 to zero: P2 (tu2, under the same shared LU) is completely unaffected, the shared LU (lu1) keeps its consignee, and P1\'s own TU (tu1) RETAINS its consignee too',
            hus: {
                // Regression guard: unpicking P1 must not bleed into P2's own TU or its share of the LU.
                // The shared LU's consignee stays 'BP1' — this delete/reset call site never targets the
                // LU (it only ever operates on the extracted per-schedule TU), so it cannot be reachable
                // to strip it here.
                lu1: { huStatus: 'S', storages: { P1: '0 PCE', P2: '4 PCE' }, bpartner: 'BP1' },
                tu2: { huStatus: 'S', storages: { P2: '4 PCE' } },
                // tu1 is the node the delete/reset actually targets (the extracted per-schedule TU, not
                // the shared LU): the deleted picked row nets to zero but survives active, so the guard's
                // "no active rows" precondition never holds, and the deferred re-stamp re-applies BP1.
                tu1: { bpartner: 'BP1' },
            }
        });
    });

    await test.step('Unpick ALL of P2 too — both lines empty', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScanP2, expectDefaultQty: '4', qty: '4' });

        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '1 TU', qtyPicked: '0 TU' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 TU', qtyPicked: '0 TU' });

        await Backend.expect({
            title: 'after driving P2 to zero too: P2\'s own TU (tu2) also RETAINS its consignee, and the shared LU (lu1) still has its consignee untouched',
            hus: {
                tu2: { bpartner: 'BP1' },
                lu1: { bpartner: 'BP1' },
            }
        });
    });
});
