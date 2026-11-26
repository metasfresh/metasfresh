import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickingJobStepScreen } from '../../utils/screens/picking/PickingJobStepScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_IGNORE } from '../../utils/screens/picking/GetQuantityDialog';

// Shipment lines of a picked box that holds units of several lots: one shipment line per lot
// (shipment policy CL, the lot is a shipment-line aggregation key).

const LOT_ATTRIBUTE = 'Lot-Nummer';

/**
 * @param lots stock HUs: `[{ id: 'HU1', qty, lotNo }]`
 * @param orderLines lines of the sales order: `[{ product: 'P1', qty }]`
 */
const createMasterdata = async ({ lots, orderLines }) => {
    const handlingUnits = {};
    for (const { id, qty, lotNo } of lots) {
        handlingUnits[id] = { product: 'P1', warehouse: 'wh', qty, lotNo };
    }

    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    allowPickingAnyCustomer: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    readAttributes: [],
                    allowCompletingPartialPickingJob: true,
                    allowSkippingRejectedReason: true,
                }
            },
            shipmentScheduleAttributeConfigs: [LOT_ATTRIBUTE],
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { prices: [{ price: 1 }] } },
            packingInstructions: { "PI1": { tu: 'Karton', product: 'P1', qtyCUsPerTU: 10 } },
            handlingUnits,
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: orderLines
                }
            },
        }
    });
};

const allureMeta = (story) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('F17011: Shipment Line Attribute Propagation (ASI)');
    allure.tag('F17011');
    allure.story(story);
    allure.severity('normal');
};

const startJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
};

/** Picks from the order line with the given index (1-based): opens the line, scans the HU, enters the qty, goes back to the job. */
const pickFromLine = async ({ lineIndex, qrCode, qtyEntered }) => {
    await PickingJobScreen.clickLineButton({ index: lineIndex });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanQRCodeButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(qrCode);
    await GetQuantityDialog.fillAndPressDone({ qtyEntered });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
};

// noinspection JSUnusedLocalSymbols
test('mixed-lot box: one shipment line per lot', async ({ page }) => {
    allureMeta('Mixed-lot box ships one line per lot');

    const masterdata = await createMasterdata({
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L1' }, { id: 'HU2', qty: 10, lotNo: 'L2' }, { id: 'HU3', qty: 10, lotNo: 'L3' }],
        // the order asks for more than is picked (2 + 3 + 1): every pick then offers the "qty not found" reason, as in pickKeepsLot.spec.js
        orderLines: [{ product: 'P1', qty: 10 }],
    });
    await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 3, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU3.qrCode, qtyEntered: 1, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });

    await PickingJobScreen.complete();
    await Backend.expect({
        salesOrders: {
            SO1: {
                shipments: [{
                    // the shipment lines follow the pick order (L1, L2, L3), and the test backend compares them in that order
                    lines: [
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L1' } },
                        { product: 'P1', movementQty: 3, attributes: { [LOT_ATTRIBUTE]: 'L2' } },
                        { product: 'P1', movementQty: 1, attributes: { [LOT_ATTRIBUTE]: 'L3' } },
                    ]
                }]
            }
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('box shared by two order lines: each order line gets its own lot', async ({ page }) => {
    allureMeta('Box shared by two order lines ships one line per order line and lot');

    const masterdata = await createMasterdata({
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L1' }, { id: 'HU2', qty: 10, lotNo: 'L2' }],
        orderLines: [{ product: 'P1', qty: 2 }, { product: 'P1', qty: 3 }],
    });
    await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await pickFromLine({ lineIndex: 1, qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2 });
    await pickFromLine({ lineIndex: 2, qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 3 });

    await PickingJobScreen.complete();
    await Backend.expect({
        salesOrders: {
            SO1: {
                shipments: [{
                    lines: [
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L1' } },
                        { product: 'P1', movementQty: 3, attributes: { [LOT_ATTRIBUTE]: 'L2' } },
                    ]
                }]
            }
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('box shared by two order lines, equal quantities: each order line gets its own lot', async ({ page }) => {
    allureMeta('Box shared by two order lines with equal quantities ships one line per order line and lot');

    const masterdata = await createMasterdata({
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L1' }, { id: 'HU2', qty: 10, lotNo: 'L2' }],
        orderLines: [{ product: 'P1', qty: 2 }, { product: 'P1', qty: 2 }],
    });
    await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await pickFromLine({ lineIndex: 1, qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2 });
    await pickFromLine({ lineIndex: 2, qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 2 });

    await PickingJobScreen.complete();
    await Backend.expect({
        salesOrders: {
            SO1: {
                shipments: [{
                    lines: [
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L1' } },
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L2' } },
                    ]
                }]
            }
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('equal quantities of two lots: two shipment lines', async ({ page }) => {
    allureMeta('Equal quantities of two lots in one box ship one line per lot');

    const masterdata = await createMasterdata({
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L1' }, { id: 'HU2', qty: 10, lotNo: 'L2' }],
        orderLines: [{ product: 'P1', qty: 4 }],
    });
    await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 2 }); // the last pick fulfills the order qty, so no "qty not found" reason is asked

    await PickingJobScreen.complete();
    await Backend.expect({
        salesOrders: {
            SO1: {
                shipments: [{
                    lines: [
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L1' } },
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L2' } },
                    ]
                }]
            }
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('same lot twice: one shipment line', async ({ page }) => {
    allureMeta('Two picks of the same lot into one box ship one line');

    const masterdata = await createMasterdata({
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L1' }],
        orderLines: [{ product: 'P1', qty: 5 }],
    });
    await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 3 }); // the last pick fulfills the order qty, so no "qty not found" reason is asked

    await PickingJobScreen.complete();
    await Backend.expect({
        salesOrders: {
            SO1: {
                shipments: [{
                    lines: [
                        { product: 'P1', movementQty: 5, attributes: { [LOT_ATTRIBUTE]: 'L1' } },
                    ]
                }]
            }
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('unpick before completion: only the picked lot is shipped', async ({ page }) => {
    allureMeta('A lot unpicked from a box before completion is not shipped');

    const masterdata = await createMasterdata({
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L1' }, { id: 'HU2', qty: 10, lotNo: 'L2' }],
        orderLines: [{ product: 'P1', qty: 6 }],
    });
    await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 3, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });

    // the picker realizes the 3 units of L2 do not belong into the box: step 0 is the pick of L1, step 1 the pick of L2
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickStepButton({ index: 1 });
    await PickingJobStepScreen.unpick();
    await PickingJobLineScreen.goBack();

    await PickingJobScreen.complete();
    await Backend.expect({
        salesOrders: {
            SO1: {
                shipments: [{
                    lines: [
                        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L1' } },
                    ]
                }]
            }
        }
    });
});
