import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { SelectPickTargetTUScreen } from "../../utils/screens/picking/SelectPickTargetTUScreen";
import { PickGraiScreen } from "../../utils/screens/picking/PickGraiScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from "../../utils/screens/huManager/HUManagerScreen";
import { GRAIScreen } from "../../utils/screens/huManager/GRAIScreen";
import { allureMeta, book, createMasterdata, restoreSysconfigsAfterEach, GRAI_8003_PREFIX, labelOf, MSG_NO_OPEN_BOX, newFormatName, openLine, qtyPickedRow, scanLabel, startJob, uniqueLot } from "../../utils/cuQtyEntryScanOnce";
import { createScanEachMasterdata, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/**
 * CU quantity entry: opening the next box, in Scan once and (the three box packing cases at the end) in Scan each CU.
 *
 * The next box comes from a GRAI scan (closes the open box, opens a box of the GRAI's TU type, GRAI on that TU) or from
 * "Weitere Kiste" (one tap, TU type of the line's packing; a line whose packing names no TU type gets the "Gebinde" list).
 * Every GRAI TU type below is a TU packing without a loading unit; every picked HU is a CU inside such a TU
 * (the `qtyTUs` expectation holds only for a real TU), never a loose CU.
 */
const MSG_NO_CAPACITY = 'has no capacity configured for this product';
const MSG_GRAI_COUNT = 'TUs have a GRAI';

const graiTuType = (key, extra = {}) => ({ [key]: { tu: `TU_${key}`, product: 'P1', qtyCUsPerTU: 4, graiMapping: true, ...extra } });

// noinspection JSUnusedLocalSymbols
test('GRAI scan with a box open (customer GRAI not required): old box closed with its 5, new box carries the GRAI', async ({ page }) => {
    allureMeta('Next box by GRAI');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await book({ qty: 5, expectedQtyTarget: 15 });

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
    await book({ qty: 5, expectedQtyTarget: 10 });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 5, vhu: 'vhu2', tu: 'tu2' })] }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },   // the old box: closed with 5, no GRAI
            tu2: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: grai } },   // the new box: the scanned GRAI, 5
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAI of a TU type without capacity for the product is refused; the open box stays and takes the next booking', async ({ page }) => {
    allureMeta('Next box by GRAI');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        // the TU type of this GRAI has a capacity row for P2 only, none for the line's product P1
        products: { P2: { price: 1 } },
        packingInstructions: { GN: { tu: 'TU_GN', product: 'P2', qtyCUsPerTU: 4, graiMapping: true } },
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await book({ qty: 5, expectedQtyTarget: 15 });

    await PickLineScanScreen.rescanGraiRefused(masterdata.packingInstructions.GN.grai, MSG_NO_CAPACITY);

    // the box is unchanged: the next booking goes into it, nothing was booked by the refused scan
    await book({ qty: 3, expectedQtyTarget: 12 });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 3, vhu: 'vhu2', tu: 'tu1' })] }
                }
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '8 PCE' }, attributes: { GRAI: null } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('"Next box" opens a box of the line\'s TU type in one tap (no "Gebinde" list); a GRAI scan after it opens one more box, the button box keeps no GRAI', async ({ page }) => {
    allureMeta('Next box button');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.expectNextBoxButtonInQuantityBlock();   // the button sits next to OK
    await book({ qty: 5, expectedQtyTarget: 15 });

    await test.step('one tap opens the next box, no box list', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.expectNotShown();
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing, not the GRAI type G1
        await GetQuantityDialog.waitForDialog();
        await book({ qty: 3, expectedQtyTarget: 12 });
    });

    await test.step('a GRAI scan opens one more box with that GRAI', async () => {
        await PickLineScanScreen.rescanGrai(grai);
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
        await book({ qty: 4, expectedQtyTarget: 8 });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' }),
                            qtyPickedRow({ qty: 3, vhu: 'vhu2', tu: 'tu2' }),
                            qtyPickedRow({ qty: 4, vhu: 'vhu3', tu: 'tu3' }),
                        ]
                    }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '3 PCE' }, attributes: { GRAI: null } },   // the button's box keeps no GRAI
            tu3: { qtyTUs: 1, storages: { P1: '4 PCE' }, attributes: { GRAI: grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan once: customer GRAI required, no pick-time GRAI screen: first booking without a box is refused, after "Next box" it is booked', async ({ page }) => {
    allureMeta('No pick-time GRAI screen');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10, graiRequired: 'Y' }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });

    await test.step('(i) booking with no box open is refused, no GRAI screen', async () => {
        await scanLabel(labelOf({ masterdata }));
        await GetQuantityDialog.typeQtyEntered(5);
        await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });
        await PickGraiScreen.expectNotShown();
        await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });
    });

    await test.step('(ii) "Next box", then the booking goes into the box, no GRAI screen', async () => {
        await PickLineScanScreen.nextBox();
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 });
        await PickGraiScreen.expectNotShown();
    });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' })] } } } },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '5 PCE' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('mixed job (customer GRAI required + customer not required): the GRAI customer\'s box opened by GRAI scan, job completes', async ({ page }) => {
    allureMeta('Complete a mixed job');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'Y', qty: 8, graiRequired: 'Y' }, { key: 'N', qty: 4, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });

    await test.step('line of the GRAI customer: box opened by GRAI scan on the scan step', async () => {
        await openLine({ index: 1 });
        await PickLineScanScreen.scanGrai(grai);
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
        await scanLabel(labelOf({ masterdata }));
        await book({ qty: 8 });      // line done: back to the job screen
        await PickingJobScreen.waitForScreen();
    });

    await test.step('line of the other customer: box opened with "Next box"', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openLine({ index: 2 });
        await PickLineScanScreen.openBox();
        await scanLabel(labelOf({ masterdata }));
        await book({ qty: 4 });
        await PickingJobScreen.waitForScreen();
    });

    // the GRAI customer's box carries the scanned GRAI (a job that completes even when the GRAI is missing would also pass a bare docStatus check)
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 8, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 4, vhu: 'vhu2', tu: 'tu2' })] } }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '8 PCE' }, attributes: { GRAI: grai } },
            tu2: { qtyTUs: 1, storages: { P1: '4 PCE' }, attributes: { GRAI: null } },
        },
    });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

/**
 * "GRAI scannen" in the HU Manager for each given box (alias of a picked TU, `tuN`): GS1 AI 8003 form (company prefix
 * 7613264, asset type 00309) with a serial of its own per box. Then back to the picking application, in the job,
 * ready to complete.
 */
const addGraiToBoxesInHUManager = async ({ masterdata, boxes }) => {
    const serialBase = Date.now();
    await PickingJobScreen.goBack();
    await PickingJobsListScreen.goBack();
    // one HU Manager visit per box: its scan field is gone while an HU is shown
    for (const [idx, box] of boxes.entries()) {
        const tuQRCode = await Backend.getHUQRCodeByIdentifier({ identifier: box });
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();
        await HUManagerScreen.scanHUQRCode({ huQRCode: tuQRCode });
        await HUManagerScreen.openGRAIScreen();
        await GRAIScreen.scanGraiBarcode({ barcodeString: `${GRAI_8003_PREFIX}${String(serialBase + idx).slice(-11)}` });
        await GRAIScreen.tapSendButton();
        await GRAIScreen.goBack();
        await HUManagerScreen.waitForScreen();
        await HUManagerScreen.goBack();
        await ApplicationsListScreen.expectVisible();
    }
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.startJob({ index: 1, productId: masterdata.products.P1.id });
};

// noinspection JSUnusedLocalSymbols
test('customer GRAI required, box opened with "Next box": "Fertigstellen" is refused as today; after "GRAI scannen" in the HU Manager the job completes', async ({ page }) => {
    allureMeta('GRAI customer, box without GRAI');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 5, graiRequired: 'Y' }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 5 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await book({ qty: 5 });
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                docStatus: 'Drafted',
                shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' })] } },
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '5 PCE' } } },
    });

    // GRAI on the box in the HU Manager, then back in the job: it completes now
    await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

/**
 * Expectation "every picked HU is a CU inside a TU of a TU packing, no loose CU": each booking's CU (`vhuN`) is listed
 * under its box (`tuN`, the `tu` of the qtyPicked row), and every box satisfies `qtyTUs: 1`, which holds only for a real
 * TU (never for a virtual packing), with the CU's quantity as its own storage (one CU per box here).
 */
const expectEveryPickedHUIsACUInATU = async ({ pickingJobId, boxes, docStatus }) => {
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                ...(docStatus ? { docStatus } : {}),
                shipmentSchedules: {
                    P1: { qtyPicked: boxes.map(({ qty }, idx) => qtyPickedRow({ qty, vhu: `vhu${idx + 1}`, tu: `tu${idx + 1}` })) }
                }
            }
        },
        hus: Object.fromEntries(boxes.flatMap(({ qty, grai }, idx) => [
            [`tu${idx + 1}`, { qtyTUs: 1, storages: { P1: `${qty} PCE` }, cus: [{ qty: `${qty} PCE` }], ...(grai !== undefined ? { attributes: { GRAI: grai } } : {}) }],
            [`vhu${idx + 1}`, { storages: { P1: `${qty} PCE` } }],
        ])),
    });
};

// noinspection JSUnusedLocalSymbols
test('line with a box packing "TU x 4 Stk" (customer GRAI required): first booking refused, "Next box" opens box 1, a GRAI scan opens the next box, "Fertigstellen" needs a GRAI on every box', async ({ page }) => {
    allureMeta('Open box first, box packing line');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 8, graiRequired: 'Y' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 8 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });
    await PickGraiScreen.expectNotShown();
    await GetQuantityDialog.expectQtyTarget(8);

    await PickLineScanScreen.nextBox();
    await SelectPickTargetTUScreen.expectNotShown();   // the line's packing names a TU type: one tap, no list
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 4 });
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
    await GetQuantityDialog.clickDoneStayOpen();   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    // box 1 was opened without GRAI: "Fertigstellen" is refused (customer GRAI required), and completes once every box has a GRAI
    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', boxes: [{ qty: 4, grai: null }, { qty: 4, grai: grai }] });
    await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('line with an unlimited-capacity packing that names a TU type: "Next box" opens no "Gebinde" list, a GRAI scan opens the next box, "Fertigstellen" needs a GRAI on every box', async ({ page }) => {
    allureMeta('Open box first, unlimited capacity packing');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 8, graiRequired: 'Y', piItemProduct: 'TU_INF' }],
        // INF: no qtyCUsPerTU, the capacity row of this TU type is unlimited; G1: the GRAI mapped TU type of the next box
        packingInstructions: { INF: { tu: 'TU_INF', product: 'P1' }, ...graiTuType('G1') },
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 8 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.nextBox();
    await SelectPickTargetTUScreen.expectNotShown();
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.INF.tuName);   // the TU type of the line's packing
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 4 });
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
    await GetQuantityDialog.clickDoneStayOpen();   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    // box 1 was opened without GRAI: "Fertigstellen" is refused (customer GRAI required), and completes once every box has a GRAI
    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', boxes: [{ qty: 4, grai: null }, { qty: 4, grai: grai }] });
    await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('line without a TU type: first booking refused, "Next box" opens the "Gebinde" list (again after a booking, no close screen), a GRAI scan opens the next box', async ({ page }) => {
    allureMeta('Open box first, line without a TU type');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 12, graiRequired: 'Y', piItemProduct: null }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const tuName = masterdata.packingInstructions.P1_20x4.tuName;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });
    await PickGraiScreen.expectNotShown();

    await test.step('"Next box" opens the box list, the picker chooses the TU type', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        // the choice leads back to the line screen with the box open; "Pack HU" goes on to the label scan
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.tapPickHUButton();
        await PickLineScanScreen.waitForScreen();
        await scanLabel(labelOf({ masterdata }));
        await book({ qty: 4, expectedQtyTarget: 8 });
        await PickGraiScreen.expectNotShown();
    });

    await test.step('"Next box" again: the list opens directly, the first box closed without a close screen', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickPickHUButton();   // the remembered label opens the quantity block again
        await book({ qty: 4, expectedQtyTarget: 4 });
        await PickGraiScreen.expectNotShown();
    });

    await test.step('a GRAI scan opens the next box', async () => {
        await PickLineScanScreen.rescanGrai(grai);
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
        await GetQuantityDialog.clickDoneStayOpen();   // the line is done: the setting PickLineScanScreen.gotoPickingJobOnClose (shipped N) leads back to the line screen it was entered from
        await PickGraiScreen.expectNotShown();
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.waitForScreen();
    });

    await test.step('"Fertigstellen" is refused while boxes without GRAI exist, and completes once every box has one', async () => {
        await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
        await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', boxes: [{ qty: 4, grai: null }, { qty: 4, grai: null }, { qty: 4, grai: grai }] });
        await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1', 'tu2'] });
        await PickingJobScreen.complete();
        await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
    });
});

/*
 * The same three line packings in Scan each CU: every pack scan books one CU of 1 into the open box, so each box below holds one CU of 1.
 * The last pack of a line finishes it and the app leaves the scan screen.
 */
const newScanEachMasterdata = async ({ orders, packingInstructions }) =>
    await createScanEachMasterdata({ packFormatName: newFormatName(), orders, packingInstructions });

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line with a box packing "TU x 4 Stk" (customer GRAI required): first scan refused, "Next box" opens box 1, a GRAI scan opens the next box, "Fertigstellen" needs a GRAI on every box', async ({ page }) => {
    allureMeta('Scan each CU open box first, box packing line');

    const masterdata = await newScanEachMasterdata({ orders: [{ key: 'A', qty: 2, graiRequired: 'Y' }], packingInstructions: graiTuType('G1') });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_NO_OPEN_BOX);
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.nextBox();
    await SelectPickTargetTUScreen.expectNotShown();   // the line's packing names a TU type: one tap, no list
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing
    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.scanGrai(grai);
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    // box 1 was opened without GRAI: "Fertigstellen" is refused (customer GRAI required), and completes once every box has a GRAI
    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', boxes: [{ qty: 1, grai: null }, { qty: 1, grai: grai }] });
    await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line with an unlimited-capacity packing that names a TU type: "Next box" opens no "Gebinde" list, a GRAI scan opens the next box, "Fertigstellen" needs a GRAI on every box', async ({ page }) => {
    allureMeta('Scan each CU open box first, unlimited capacity packing');

    const masterdata = await newScanEachMasterdata({
        orders: [{ key: 'A', qty: 2, graiRequired: 'Y', piItemProduct: 'TU_INF' }],
        // INF: no qtyCUsPerTU, the capacity row of this TU type is unlimited; G1: the GRAI mapped TU type of the next box
        packingInstructions: { INF: { tu: 'TU_INF', product: 'P1' }, ...graiTuType('G1') },
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_NO_OPEN_BOX);
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.nextBox();
    await SelectPickTargetTUScreen.expectNotShown();
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.INF.tuName);   // the TU type of the line's packing
    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.scanGrai(grai);
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    // box 1 was opened without GRAI: "Fertigstellen" is refused (customer GRAI required), and completes once every box has a GRAI
    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', boxes: [{ qty: 1, grai: null }, { qty: 1, grai: grai }] });
    await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line without a TU type: first scan refused, "Next box" opens the "Gebinde" list (again after a booking), a GRAI scan opens the next box, "Fertigstellen" needs a GRAI on every box', async ({ page }) => {
    allureMeta('Scan each CU open box first, line without a TU type');

    const masterdata = await newScanEachMasterdata({ orders: [{ key: 'A', qty: 3, graiRequired: 'Y', piItemProduct: null }], packingInstructions: graiTuType('G1') });
    const grai = masterdata.packingInstructions.G1.grai;
    const tuName = masterdata.packingInstructions.P1_20x4.tuName;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_NO_OPEN_BOX);
    await PickGraiScreen.expectNotShown();

    await test.step('"Next box" opens the box list, the picker chooses the TU type and scans the first pack', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        // the choice leads back to the line screen with the box open; "Pack HU" goes on to the scan screen
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.tapPickHUButton();
        await PickLineScanScreen.waitForScreen();
        await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
        await PickGraiScreen.expectNotShown();
    });

    await test.step('"Next box" again: the list opens directly, the picker chooses the TU type and scans the second pack', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.tapPickHUButton();
        await PickLineScanScreen.waitForScreen();
        await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
        await PickGraiScreen.expectNotShown();
    });

    await test.step('a GRAI scan opens the next box, the third pack finishes the line', async () => {
        await PickLineScanScreen.scanGrai(grai);
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
        await scanPack(packLabelOf({ masterdata, lot, packId: 3 }));
        await PickGraiScreen.expectNotShown();
        await PickingJobLineScreen.waitForScreen();   // entered from the line screen: the done line leads back there
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.waitForScreen();
    });

    await test.step('"Fertigstellen" is refused while boxes without GRAI exist, and completes once every box has one', async () => {
        await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
        await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', boxes: [{ qty: 1, grai: null }, { qty: 1, grai: null }, { qty: 1, grai: grai }] });
        await addGraiToBoxesInHUManager({ masterdata, boxes: ['tu1', 'tu2'] });
        await PickingJobScreen.complete();
        await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
    });
});
