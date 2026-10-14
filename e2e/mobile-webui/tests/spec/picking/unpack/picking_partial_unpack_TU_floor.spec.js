import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";

// A valid, unique 14-digit GS1 GTIN per run (strict GTIN->product resolve, AD_Client_ID=METASFRESH).
// (Same rationale as picking_partial_unpack.spec.js: high-order per-call counter so back-to-back GTINs
// differ by 2 high-order digits, never a single trailing digit.)
let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `9${seq}${ts}`;
};
const gs1GtinScan = (gtin14) => `01${gtin14}`;

// Pick-to-CU INTO a bare TU (no LU) — a reusable-transport-crate structure: the line is a CU pick (line
// has NO piItemProduct), the operator sets a bare-TU pick target, and each pick packs a CU INTO that TU.
// This is the structure the skip-to-floor unpick bug was specific to (a CU packed into a real TU with a
// NULL/skip target left the CU Picked+nested instead of detaching it as an active floor HU). The prior
// floor test (picking_partial_unpack.spec.js) used a TU-under-LU structure with an LU target, so it did
// not exercise this path.
const createMasterdata = async ({ packedGtin }) => {
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
                    // Bare TU target (no LU): the CU is packed directly into a top-level TU.
                    pickTo: ['TU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { gtin: packedGtin, prices: [{ price: 1 }] },
            },
            packingInstructions: {
                // The bare-TU pick target the CU is packed into (IFCO-like), capacity 100 CUs/TU, no LU.
                "TU_PI": { tu: "IFCO", product: "P1", qtyCUsPerTU: 100 },
                // A loose source HU holding the product to pick from.
                "SRC": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'SRC' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    // No piItemProduct => the line is a CU (product) pick; the CU is packed into the TU target.
                    lines: [{ product: 'P1', qty: 4 }]
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
    // A CU line (no piItemProduct) auto-navigates into the single line's PickLineScanScreen after the
    // slot scan; step back up to the job screen (same as pick_by_EAN13.spec.js's "LU/CU -> top level TU").
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // Set a bare TU pick target (no LU) — the CU picks are packed into THIS TU.
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.TU_PI.tuName });
    return { pickingJobId };
};

// noinspection JSUnusedLocalSymbols
test('Partial unpack to the floor from a pick-to-CU-into-TU package — repeatable, no orphaned/stuck HU in the TU', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick 4 PCE of P1 into the bare TU target', async () => {
        // CU pick: scan the source HU and enter the product qty; the picked CUs are packed into the TU.
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 4, expectQtyEntered: '4' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '4 Stk' });
        await Backend.expect({
            title: 'after pick: 4 PCE of P1 packed into the pick-to TU (tu1)',
            // A CU-into-bare-TU pick records the destination TU (not the leaf CU) on
            // M_ShipmentSchedule_QtyPicked — VHU_ID stays NULL (the long-standing top-level-TU pick
            // behavior; cf. pick_by_EAN13.spec.js "LU/CU -> top level TU" which also asserts vhu:'-').
            // The leaf CU is recorded on the picking-job step (which drives unpick), not here. Binding
            // `tu1` here is what makes it resolvable in `hus:` below.
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                // The materialised pick-to TU carries the picked CU content and is in Picked state.
                tu1: { huStatus: 'S', storages: { P1: '4 PCE' } },
            }
        });
    });

    // Round 1: unpick 1 PCE to the floor (skip the target-HU scan). The removed CU must physically detach
    // from the TU as an ACTIVE re-pickable floor HU (AC3/AC4a) — NOT stay Picked inside the TU (the bug).
    await test.step('Round 1 — unpick 1 PCE to the floor, then re-pick', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScan, expectDefaultQty: '4', qty: '1' });

        // Packed qty decremented (3 PCE stays in the TU) and the qty-to-pick reappears (re-pickable).
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '3 Stk' });
        await Backend.expect({
            title: 'round 1 after floor unpick: 3 PCE stays in the TU; unpicked 1 PCE dropped to the floor as an active standalone CU, no orphan left Picked in the TU',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '3 PCE' } },
            },
            // The shipment schedule's picked qty must follow the unpick: 4 -> 3 PCE. The unpick must
            // reduce/delete the M_ShipmentSchedule_QtyPicked recorded for this pick — the known bug is that
            // the HU detaches correctly but this row is NOT reduced (stays 4 PCE), over-reporting picked qty.
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            }
        });

        // Re-pick the removed 1 PCE back into the package (the reversible cycle): the line returns to 4.
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 1, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '4 Stk' });
        await Backend.expect({
            title: 'round 1 after re-pick: back to 4 PCE packed in the TU',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '4 PCE' } },
            }
        });
    });

    // Round 2: repeat the unpick->re-pick loop to prove it is repeatable with NO accumulating orphan CU
    // inside the TU (the exact reported symptom: the crate retained one Picked CU per round).
    await test.step('Round 2 — unpick 1 PCE to the floor, then re-pick', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScan, expectDefaultQty: '4', qty: '1' });

        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '3 Stk' });
        await Backend.expect({
            title: 'round 2 after floor unpick: 3 PCE stays in the TU; no accumulating orphan Picked CU in the TU',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '3 PCE' } },
            }
        });

        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 1, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '4 Stk' });
        await Backend.expect({
            title: 'round 2 after re-pick: back to 4 PCE packed in the TU',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '4 PCE' } },
            }
        });
    });
});
