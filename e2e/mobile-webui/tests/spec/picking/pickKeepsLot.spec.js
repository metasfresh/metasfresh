import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_IGNORE } from '../../utils/screens/picking/GetQuantityDialog';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';

// Lot and best-before date of the stock must survive a pick when the qty dialog does not show those fields
// (profile: LotNumber = N, BestBeforeDate = N, shipment policy CL).

const LOT_ATTRIBUTE = 'Lot-Nummer';
const BEST_BEFORE_ATTRIBUTE = 'HU_BestBeforeDate';

/**
 * @param readAttributes mobile picking config: attributes the qty dialog shows
 * @param lots stock HUs: `[{ id: 'HU1', qty, lotNo, bestBeforeDate }]`
 * @param orderQty qty of the sales order line
 * @param withLU true: packing instruction PI2 (LU + TU) is created and LU targets are allowed
 */
const createMasterdata = async ({ readAttributes, lots, orderQty, withLU = false }) => {
    const handlingUnits = {};
    for (const { id, qty, lotNo, bestBeforeDate } of lots) {
        handlingUnits[id] = { product: 'P1', warehouse: 'wh', qty, lotNo, bestBeforeDate };
    }

    const packingInstructions = { "PI1": { tu: 'Karton', product: 'P1', qtyCUsPerTU: 10 } };
    if (withLU) {
        packingInstructions["PI2"] = { lu: 'LU1', qtyTUsPerLU: 20, tu: 'Karton2', product: 'P1', qtyCUsPerTU: 10 };
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
                    pickTo: withLU ? ['LU_TU'] : ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    readAttributes,
                    allowCompletingPartialPickingJob: true,
                    allowSkippingRejectedReason: true,
                }
            },
            // the best-before row is the generic case for the best-before test; the lot row mirrors the real configuration
            shipmentScheduleAttributeConfigs: [LOT_ATTRIBUTE, BEST_BEFORE_ATTRIBUTE],
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { prices: [{ price: 1 }] } },
            packingInstructions,
            handlingUnits,
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: orderQty }]
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

/**
 * Asserts the shipment lines of SO1 in either order. The order of M_InOutLine.Line is not stable when two picks of
 * different lots go into one shipment. The expected lines are tried as given, then reversed (inside one report step);
 * each line keeps its own qty + attributes pairing and the line count stays exact, so a lot on the wrong quantity or
 * an extra line still fails.
 */
const expectShipmentLinesInAnyOrder = async (lines) => {
    await Backend.expectAnyOf([
        { salesOrders: { SO1: { shipments: [{ lines }] } } },
        { salesOrders: { SO1: { shipments: [{ lines: [...lines].reverse() }] } } },
    ], 'shipment lines (any order)');
};

const startJob = async (masterdata, { withLU = false } = {}) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    if (withLU) {
        // LU targets only: the job asks for a target after the slot, so no line scan screen to go back from
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    } else {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    }
    return pickingJobId;
};

// noinspection JSUnusedLocalSymbols
test('lot kept: pick 3 of 114 into a new Karton through the dialog', async ({ page }) => {
    allureMeta('Lot kept on pick into a box');

    const masterdata = await createMasterdata({ readAttributes: [], orderQty: 3, lots: [{ id: 'HU1', qty: 114, lotNo: 'L-114' }] });
    const pickingJobId = await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 3 });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] } } } },
        hus: { tu1: { attributes: { [LOT_ATTRIBUTE]: 'L-114' }, cus: [{ qty: '3 PCE', attributes: { [LOT_ATTRIBUTE]: 'L-114' } }] } }
    });

    await PickingJobScreen.complete();
    await Backend.expect({ salesOrders: { SO1: { shipments: [{ lines: [{ product: 'P1', movementQty: 3, attributes: { [LOT_ATTRIBUTE]: 'L-114' } }] }] } } });
});

// noinspection JSUnusedLocalSymbols
test('lot kept without a box, partial pick', async ({ page }) => {
    allureMeta('Lot kept on pick without a box');

    const masterdata = await createMasterdata({ readAttributes: [], orderQty: 3, lots: [{ id: 'HU1', qty: 114, lotNo: 'L-114' }] });
    const pickingJobId = await startJob(masterdata);

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 3 });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }] } } } },
        hus: { vhu1: { attributes: { [LOT_ATTRIBUTE]: 'L-114' } } }
    });

    await PickingJobScreen.complete();
    await Backend.expect({ salesOrders: { SO1: { shipments: [{ lines: [{ product: 'P1', movementQty: 3, attributes: { [LOT_ATTRIBUTE]: 'L-114' } }] }] } } });
});

// noinspection JSUnusedLocalSymbols
test('lot kept without a box, whole-HU pick', async ({ page }) => {
    allureMeta('Lot kept on whole-HU pick');

    const masterdata = await createMasterdata({ readAttributes: [], orderQty: 4, lots: [{ id: 'HU1', qty: 4, lotNo: 'L-114' }] });
    await startJob(masterdata);

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 4 });
    await PickingJobScreen.complete();
    await Backend.expect({ salesOrders: { SO1: { shipments: [{ lines: [{ product: 'P1', movementQty: 4, attributes: { [LOT_ATTRIBUTE]: 'L-114' } }] }] } } });
});

// noinspection JSUnusedLocalSymbols
test('best-before kept', async ({ page }) => {
    allureMeta('Best-before kept on pick into a box');

    const masterdata = await createMasterdata({ readAttributes: [], orderQty: 3, lots: [{ id: 'HU1', qty: 114, lotNo: 'L-114', bestBeforeDate: '2027-01-31' }] });
    const pickingJobId = await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 3 });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] } } } },
        hus: { tu1: { cus: [{ qty: '3 PCE', attributes: { [BEST_BEFORE_ATTRIBUTE]: '2027-01-31' } }] } }
    });

    await PickingJobScreen.complete();
    await Backend.expect({ salesOrders: { SO1: { shipments: [{ lines: [{ product: 'P1', movementQty: 3, attributes: { [BEST_BEFORE_ATTRIBUTE]: '2027-01-31' } }] }] } } });
});

// noinspection JSUnusedLocalSymbols
test('box lot follows its CUs', async ({ page }) => {
    allureMeta('Box lot follows the lots of its CUs');

    const masterdata = await createMasterdata({
        readAttributes: [], orderQty: 5,
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L-A' }, { id: 'HU2', qty: 10, lotNo: 'L-B' }]
    });
    const pickingJobId = await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] } } } },
        hus: { tu1: { attributes: { [LOT_ATTRIBUTE]: 'L-A' }, cus: [{ qty: '2 PCE', attributes: { [LOT_ATTRIBUTE]: 'L-A' } }] } }
    });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 3 });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [
            { qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
            { qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
        ] } } } },
        hus: {
            tu1: {
                attributes: { [LOT_ATTRIBUTE]: null },
                cus: [
                    { qty: '2 PCE', attributes: { [LOT_ATTRIBUTE]: 'L-A' } },
                    { qty: '3 PCE', attributes: { [LOT_ATTRIBUTE]: 'L-B' } },
                ]
            },
        }
    });

    await PickingJobScreen.complete();
    await expectShipmentLinesInAnyOrder([
        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L-A' } },
        { product: 'P1', movementQty: 3, attributes: { [LOT_ATTRIBUTE]: 'L-B' } },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('lot field shown, picker clears it', async ({ page }) => {
    allureMeta('A shown lot field that the picker clears still clears the lot');

    const masterdata = await createMasterdata({ readAttributes: ['LotNo'], orderQty: 3, lots: [{ id: 'HU1', qty: 114, lotNo: 'L-114' }] });
    const pickingJobId = await startJob(masterdata);

    await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectLotNoVisible();
    await GetQuantityDialog.expectLotNo('L-114'); // the field starts with the stock's lot, the picker then clears it
    await GetQuantityDialog.fillAndPressDone({ qtyEntered: 3, lotNo: '' });
    await PickingJobScreen.waitForScreen();

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: '3 PCE', qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }] } } } },
        hus: { vhu1: { attributes: { [LOT_ATTRIBUTE]: null } } }
    });
});

// noinspection JSUnusedLocalSymbols
test('pre-existing LU target: LU lot follows its TUs', async ({ page }) => {
    allureMeta('LU lot follows the lots of its TUs');

    const masterdata = await createMasterdata({
        readAttributes: [], orderQty: 5, withLU: true,
        lots: [{ id: 'HU1', qty: 10, lotNo: 'L-A' }, { id: 'HU2', qty: 10, lotNo: 'L-B' }]
    });
    const pickingJobId = await startJob(masterdata, { withLU: true });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI2.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI2.tuName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: 2, expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    // lu1 is named here by the picking line; a new LU then carries the lot of its only TU
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [
            { qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' },
        ] } } } },
        hus: { lu1: { attributes: { [LOT_ATTRIBUTE]: 'L-A' } } }
    });
    // a new box for the second pick: the LU then holds two TUs with different lots
    await PickingJobScreen.closeTargetTU();
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI2.tuName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, qtyEntered: 3 });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [
            { qtyPicked: '2 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' },
            { qtyPicked: '3 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu2', lu: 'lu1', processed: false, shipmentLineId: '-' },
        ] } } } },
        hus: {
            lu1: { attributes: { [LOT_ATTRIBUTE]: null } },
            tu1: { cus: [{ qty: '2 PCE', attributes: { [LOT_ATTRIBUTE]: 'L-A' } }] },
            tu2: { cus: [{ qty: '3 PCE', attributes: { [LOT_ATTRIBUTE]: 'L-B' } }] },
        }
    });

    await PickingJobScreen.complete();
    await expectShipmentLinesInAnyOrder([
        { product: 'P1', movementQty: 2, attributes: { [LOT_ATTRIBUTE]: 'L-A' } },
        { product: 'P1', movementQty: 3, attributes: { [LOT_ATTRIBUTE]: 'L-B' } },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('one box, five CUs of three lots: every CU keeps its own lot, the box lot stays empty', async ({ page }) => {
    allureMeta('CUs of several lots in one box keep their own lots');

    // two CUs of lot L-A, two of lot L-B, one of lot L-C (quantities differ, so every CU is identifiable by its qty)
    const picks = [
        { hu: 'HU1', qty: 2, lot: 'L-A' },
        { hu: 'HU2', qty: 3, lot: 'L-A' },
        { hu: 'HU3', qty: 4, lot: 'L-B' },
        { hu: 'HU4', qty: 5, lot: 'L-B' },
        { hu: 'HU5', qty: 6, lot: 'L-C' },
    ];
    const masterdata = await createMasterdata({
        readAttributes: [], orderQty: picks.reduce((sum, { qty }) => sum + qty, 0),
        lots: picks.map(({ hu, lot }) => ({ id: hu, qty: 10, lotNo: lot }))
    });
    const pickingJobId = await startJob(masterdata);
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    const qtyPickedRow = ({ qty }) => ({ qtyPicked: `${qty} PCE`, qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' });
    for (let i = 0; i < picks.length; i++) {
        const { hu, qty } = picks[i];
        const isLastPick = i === picks.length - 1; // the last pick fulfills the order qty, so no "qty not found" reason is asked
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits[hu].qrCode,
            qtyEntered: qty,
            ...(isLastPick ? {} : { expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE }),
        });
    }

    // lots of the box's CUs, each with its own lot; the box itself holds several lots, so its own lot is empty
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: picks.map(qtyPickedRow) } } } },
        hus: {
            tu1: {
                attributes: { [LOT_ATTRIBUTE]: null },
                cus: picks.map(({ qty, lot }) => ({ qty: `${qty} PCE`, attributes: { [LOT_ATTRIBUTE]: lot } })),
            },
        }
    });

    await PickingJobScreen.complete();

    // The shipment lines of this box (merged per lot or one per CU) are asserted on the final build, together with the shipment fix; this test covers the picking side only.
});
