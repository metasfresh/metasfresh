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

// Same bare-TU (no LU) pick-to-CU structure as picking_partial_unpack_TU_floor.spec.js, but the packed
// qty is built from TWO SEPARATE pick actions into the SAME target TU (two M_ShipmentSchedule_QtyPicked
// rows for one product on one TU, per PickingJobPickCommand — each pick into a bare TU records its own
// row). This lets an unpick-to-floor qty CROSS both picks in a single unpick action.
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
                // The bare-TU pick target the CUs are packed into (IFCO-like), capacity 100 CUs/TU, no LU.
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
    // slot scan; step back up to the job screen.
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // Set a bare TU pick target (no LU) — all pick actions below pack into THIS TU.
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.TU_PI.tuName });
    return { pickingJobId };
};

// noinspection JSUnusedLocalSymbols
test('Unpick to the floor spanning two separate picks into the same bare TU reduces the shipment schedule qty across both picks', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('First pick: 2 PCE of P1 into the bare TU target', async () => {
        // The dialog's default offered qty is the full remaining-to-pick (4); we type 2 (a partial pick).
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyEntered: '4' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '2 Stk' });
        await Backend.expect({
            title: 'after 1st pick: 2 PCE of P1 packed into the pick-to TU (tu1), one QtyPicked row',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '2 PCE' } },
            }
        });
    });

    await test.step('Second pick: another 2 PCE of P1 into the SAME bare TU (a second, independent pick step)', async () => {
        // The remaining-to-pick default is now 2 (4 target - 2 already picked); type 2 again.
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyEntered: '2' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '4 Stk' });
        await Backend.expect({
            title: 'after 2nd pick: 4 PCE of P1 packed into tu1, TWO QtyPicked rows of 2 PCE each (oldest first)',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                { qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                                { qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                            ]
                        }
                    }
                }
            },
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '4 PCE' } },
            }
        });
    });

    await test.step('Unpick 3 PCE to the floor — crosses BOTH picks (2+2=4 packed, unpick 3 leaves 1)', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScan, expectDefaultQty: '4', qty: '3' });

        // The TU keeps the remainder (1 PCE); the qty-to-pick reappears (re-pickable).
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '1 Stk' });
        await Backend.expect({
            title: 'after spanning floor unpick: 1 PCE stays in the TU (4-3), the newest pick row fully consumed and deleted, the older row reduced to 1 — no orphan row, no orphan CU',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '1 PCE' } },
            },
            // Newest-first reduce: the 2nd (newest) pick's row is fully consumed and deleted; the 1st
            // (older) pick's row is partially reduced by the remaining 1 unit (2 -> 1). Exactly ONE
            // QtyPicked row remains, holding the full packed-minus-unpicked qty.
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '1 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            }
        });
    });
});
