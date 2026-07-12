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

// Same bare-TU (no LU) pick-to-CU structure as picking_partial_unpack_TU_floor.spec.js, but the product is
// catch-weight (a manually-entered actual weight is recorded per pick, invoicing based on that weight
// rather than the piece count). Partially floor-unpicking a catch-weight pick from a bare TU exercises
// HUShipmentScheduleBL.reduceCatchWeightProportionally: the remaining QtyPicked row's catch weight must
// scale down by the same ratio as the piece qty (newCatchWeight = oldCatchWeight * newQty/oldQty), not
// just have its piece qty reduced while the weight is left stale.
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
                "P1": {
                    gtin: packedGtin,
                    uom: 'PCE',
                    // Catch-weight product: KGM is the catch UOM (isCatchUOMForProduct), and invoicing is
                    // based on the actually-entered catch weight rather than the PCE piece count.
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }],
                },
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
    // slot scan; step back up to the job screen (same as picking_partial_unpack_TU_floor.spec.js).
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // Set a bare TU pick target (no LU) — the CU picks are packed into THIS TU.
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.TU_PI.tuName });
    return { pickingJobId };
};

// noinspection JSUnusedLocalSymbols
test('Partial floor-unpick of a catch-weight pick from a bare TU scales the remaining catch weight and the physical HU weight attributes proportionally', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick 4 PCE of P1 into the bare TU target with a manually-entered catch weight (0.44 kg, off the 0.1/PCE default ratio)', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            switchToManualInput: true,
            qtyEntered: '4',
            catchWeight: '0.44',
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '4 Stk' });

        await Backend.expect({
            title: 'after pick: 4 PCE of P1 packed into the pick-to TU (tu1) with catch weight 0.440 KGM',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '4 PCE', catchWeight: '0.440 KGM', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                tu1: {
                    huStatus: 'S',
                    storages: { P1: '4 PCE' },
                    // Physical HU weight attributes on the single picked CU (VHU) under the bare TU.
                    // The manually-entered catch weight (0.44 KGM) is stored as the CU's measured
                    // WeightNet; WeightGross follows it (tare 0, so gross == net). This is the source
                    // of truth the schedule row's QtyDeliveredCatch was derived from at pick time.
                    cus: [{ qty: '4 PCE', attributes: { WeightNet: '0.440', WeightGross: '0.440' } }],
                },
            }
        });
    });

    // Unpick 1 PCE to the floor (partial — a boundary reduce, not a whole-HU delete): 3 PCE stays packed.
    // HUShipmentScheduleBL.reduceQtyPickedForPickToTU must scale the remaining row's catch weight down by
    // the SAME ratio as the piece qty (3/4), via reduceCatchWeightProportionally — NOT leave 0.440 KGM
    // stale on a now-3-PCE row (which would silently over-report the remaining catch weight).
    await test.step('Unpick 1 PCE to the floor — 3 PCE stays packed, the remaining catch weight scales 0.440 -> 0.330 KGM (x 3/4)', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScan, expectDefaultQty: '4', qty: '1' });

        await PickingJobScreen.expectLineButton({ index: 1, qtyPicked: '3 Stk' });

        await Backend.expect({
            title: 'after floor unpick: 3 PCE stays in the TU, remaining catch weight proportionally reduced 0.440 -> 0.330 KGM',
            hus: {
                tu1: {
                    huStatus: 'S',
                    storages: { P1: '3 PCE' },
                    // The remaining CU's physical WeightNet/WeightGross must scale by the SAME 3/4 ratio
                    // as the piece qty (0.440 -> 0.330), NOT stay stale at 0.440. The carved-out 1-PCE
                    // floor CU carries the complementary 0.110 (0.440 x 1/4); 0.330 + 0.110 == 0.440
                    // (conservation). WeightNet is redistributed by RedistributeQtyHUAttributeTransferStrategy
                    // on the VHU-to-VHU split; WeightGross follows via the Net->Gross callout (tare 0).
                    cus: [{ qty: '3 PCE', attributes: { WeightNet: '0.330', WeightGross: '0.330' } }],
                },
            },
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: '3 PCE', catchWeight: '0.330 KGM', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            }
        });
    });
});
