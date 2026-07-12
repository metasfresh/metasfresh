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

// Same bare-TU (no LU) pick-to-CU structure as picking_partial_unpack_TU_floor.spec.js, but with TWO
// order lines (two products, two shipment schedules) sharing the SAME bare TU pick target. Verifies
// that unpicking one line's qty to the floor reduces ONLY that line's M_ShipmentSchedule_QtyPicked,
// leaving the other, independently-picked line untouched (no cross-line bleed). Also drives one line
// (P1) fully to ZERO on the shared TU while the other (P2) still has active picked qty — the guarded
// path where the shared bare TU's consignee reset must be skipped because another schedule is still
// active on it (a bare TU is shared across schedules).
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
                    // Bare TU target (no LU): the CUs of BOTH lines are packed directly into the same
                    // top-level TU (sales_order aggregation sets the pick target once, at job level).
                    pickTo: ['TU'],
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
                // The SAME bare-TU pick target ("IFCO") carries a distinct capacity item-product per
                // product, so both order lines' CUs can be packed into the one physical TU.
                "TU_PI_P1": { tu: "IFCO", product: "P1", qtyCUsPerTU: 100 },
                "TU_PI_P2": { tu: "IFCO", product: "P2", qtyCUsPerTU: 100 },
                // A loose source HU per product to pick from.
                "SRC1": { cu: true, lu: "LU1", qtyTUsPerLU: 1 },
                "SRC2": { cu: true, lu: "LU2", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'SRC1' },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'SRC2' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    // Two CU (product) lines => two independent shipment schedules.
                    lines: [
                        { product: 'P1', qty: 4 },
                        { product: 'P2', qty: 4 },
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
    // A CU line (no piItemProduct) auto-navigates into the first line's PickLineScanScreen after the
    // slot scan (even with 2 lines); step back up to the job screen.
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // Set the bare TU pick target (no LU) ONCE, at job level — both lines pack into THIS TU.
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.TU_PI_P1.tuName });
    return { pickingJobId };
};

// noinspection JSUnusedLocalSymbols
test('Unpick to the floor on one of two picking lines reduces that line\'s shipment schedule qty only, the other line stays untouched', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ gtinP1: uniqueGtin14(), gtinP2: uniqueGtin14() });
    const packedScanP1 = gs1GtinScan(masterdata.products.P1.gtin);
    const packedScanP2 = gs1GtinScan(masterdata.products.P2.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick 3 PCE of P1 (line 1) into the shared bare TU', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 3, expectQtyEntered: '4' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '3 Stk' });
    });

    await test.step('Pick 3 PCE of P2 (line 2) into the SAME shared bare TU', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 3, expectQtyEntered: '4' });
        await PickingJobScreen.expectLineButton({ index: 2, qtyPicked: '3 Stk' });

        await Backend.expect({
            title: 'after both picks: 3 PCE of P1 + 3 PCE of P2 packed into the SAME TU (tu1), two independent schedule rows',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                        P2: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                    }
                }
            },
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '3 PCE', P2: '3 PCE' } },
            }
        });
    });

    await test.step('Unpick 2 PCE of P1 to the floor — must reduce ONLY the P1 schedule', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScanP1, expectDefaultQty: '3', qty: '2' });

        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '1 Stk' });
        // P2's line button is untouched by the P1 unpick.
        await PickingJobScreen.expectLineButton({ index: 2, qtyPicked: '3 Stk' });

        await Backend.expect({
            title: 'after unpicking P1: P1 schedule reduced 3->1, P2 schedule UNCHANGED at 3 (no cross-line bleed)',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '1 PCE', P2: '3 PCE' } },
            },
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '1 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                        P2: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                    }
                }
            }
        });
    });

    await test.step('Unpick 2 PCE of P2 to the floor — must reduce ONLY the P2 schedule; P1 stays at its already-reduced qty', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScanP2, expectDefaultQty: '3', qty: '2' });

        await PickingJobScreen.expectLineButton({ index: 2, qtyPicked: '1 Stk' });
        // P1's line button is untouched by the P2 unpick (stays at the qty from the previous step).
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '1 Stk' });

        await Backend.expect({
            title: 'after unpicking P2: P2 schedule reduced 3->1, P1 schedule stays at 1 (no over/under-reduction, no bleed)',
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '1 PCE', P2: '1 PCE' } },
            },
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '1 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                        P2: { qtyPicked: [{ qtyPicked: '1 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                    }
                }
            }
        });
    });

    // The shared bare TU is still carrying BOTH lines' picked qty at this point (P1=1, P2=1). Driving
    // P1's remaining 1 PCE fully to the floor (P1 -> ZERO) is the case the shipment-schedule reset guard
    // protects: on a bare TU shared across two schedules, fully unpicking ONE schedule's rows must NOT
    // reset the TU's consignee (C_BPartner_ID/C_BPartner_Location_ID) while the OTHER schedule (P2) still
    // holds active picked qty on the same TU. Before the guard, reaching zero on P1 unconditionally reset
    // the TU's consignee, corrupting the still-active P2 pick sharing that TU.
    await test.step('Unpick the remaining 1 PCE of P1 to the floor — P1 drops to ZERO while P2 still has active qty on the shared TU', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScanP1, expectDefaultQty: '1', qty: '1' });

        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '0 Stk' });
        // P2's line button (and its underlying schedule, asserted below) must be completely unaffected by
        // P1 reaching zero on the shared TU — this is the guarded cross-line-bleed path.
        await PickingJobScreen.expectLineButton({ index: 2, qtyPicked: '1 Stk' });

        await Backend.expect({
            title: 'after driving P1 to zero on the shared TU: P1 has no QtyPicked rows left, P2 schedule row is UNCHANGED at 1 PCE, and the TU consignee is RETAINED (P2 still active on it)',
            hus: {
                // The shared TU stays active (huStatus 'S') and keeps P2's storage untouched. The
                // production guard (HUShipmentScheduleBL.resetConsigneeIfNoActivePickedRows, backed by
                // IHUShipmentScheduleDAO.hasActiveQtyPickedForTopLevelHU) must RETAIN the TU's consignee
                // (C_BPartner_ID/C_BPartner_Location_ID) here because P2 still has an active picked row on
                // it — asserted directly via the `bpartner` HU-expectation field (not a proxy).
                tu1: { huStatus: 'S', storages: { P1: '0 PCE', P2: '1 PCE' }, bpartner: 'BP1' },
            },
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [] },
                        P2: { qtyPicked: [{ qtyPicked: '1 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] },
                    }
                }
            }
        });
    });

    // Now P2 becomes the LAST schedule holding picked qty on the shared TU. Driving it to zero too must
    // FINALLY reset the TU's consignee (C_BPartner_ID/C_BPartner_Location_ID stripped) — no schedule is
    // left active on it. Asserted directly via `consigneeCleared` (not a proxy).
    await test.step('Unpick the remaining 1 PCE of P2 to the floor — P2 drops to ZERO too, no schedule left active on the shared TU', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScanP2, expectDefaultQty: '1', qty: '1' });

        await PickingJobScreen.expectLineButton({ index: 2, qtyPicked: '0 Stk' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '0 Stk' });

        await Backend.expect({
            title: 'after driving P2 to zero too: both schedules empty (the now-fully-drained TU is destroyed, per framework HU-lifecycle GC), the shared TU consignee is CLEARED (no schedule left active on it)',
            hus: {
                // The TU holds zero storage of both products at this point, so the framework destroys it
                // (huStatus 'D') rather than leaving an empty active shell — unrelated to the consignee
                // guard under test. `consigneeCleared` still asserts the guard's actual outcome directly.
                tu1: { huStatus: 'D', consigneeCleared: true },
            },
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [] },
                        P2: { qtyPicked: [] },
                    }
                }
            }
        });
    });
});
