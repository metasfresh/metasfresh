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
import { book, createMasterdata, labelOf, MSG_NO_OPEN_BOX, newFormatName, openLine, scanLabel, startJob } from "../../utils/cuQtyEntryScanOnce";

/**
 * CU quantity entry, Scan once: opening the next box (TC14, TC14b, TC15, TC16, TC17 Scan once part, TC18, TC19, TC23).
 *
 * The next box comes from a GRAI scan (closes the open box, opens a box of the GRAI's TU type, GRAI on that TU) or from
 * "Weitere Kiste" (one tap, TU type of the line's packing; a line whose packing names no TU type gets the "Gebinde" list).
 * Every GRAI TU type below is a TU packing without a loading unit; every picked HU is a CU inside such a TU
 * (the `qtyTUs` expectation holds only for a real TU), never a loose CU.
 */
const MSG_NO_CAPACITY = 'has no capacity configured for this product';
const GRAI_8003_PREFIX = '8003076132640030951';
const MSG_GRAI_COUNT = 'TUs have a GRAI';

const graiTuType = (key, extra = {}) => ({ [key]: { tu: `TU_${key}`, product: 'P1', qtyCUsPerTU: 4, graiMapping: true, ...extra } });

const allureMeta = (story) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story(story);
    allure.severity('normal');
};

const qtyPickedRow = ({ qty, vhu, tu }) => ({ qtyPicked: `${qty} PCE`, qtyTUs: 1, qtyLUs: 0, vhu, tu, lu: '-', processed: false, shipmentLineId: '-' });

// noinspection JSUnusedLocalSymbols
test('TC14: GRAI scan with a box open (customer GRAI not required): old box closed with its 5, new box carries the GRAI', async ({ page }) => {
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
test('TC14b: GRAI of a TU type without capacity for the product is refused; the open box stays and takes the next booking', async ({ page }) => {
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
test('TC15 / TC16: "Next box" opens a box of the line\'s TU type in one tap (no "Gebinde" list); a GRAI scan after it opens one more box, the button box keeps no GRAI', async ({ page }) => {
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

    await test.step('TC15: one tap opens the next box, no box list', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.expectNotShown();
        await GetQuantityDialog.waitForDialog();
        await book({ qty: 3, expectedQtyTarget: 12 });
    });

    await test.step('TC16: a GRAI scan opens one more box with that GRAI', async () => {
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
test('TC17 (Scan once): customer GRAI required, no pick-time GRAI screen: first booking without a box is refused, after "Next box" it is booked', async ({ page }) => {
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
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 });
        await PickGraiScreen.expectNotShown();
    });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' })] } } } },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '5 PCE' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC18: mixed job (customer GRAI required + customer not required): the GRAI customer\'s box opened by GRAI scan, job completes', async ({ page }) => {
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

    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('TC19: customer GRAI required, box opened with "Next box": "Fertigstellen" is refused as today; after "GRAI scannen" in the HU Manager the job completes', async ({ page }) => {
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

    // GRAI on the box in the HU Manager: GS1 AI 8003 form (company prefix 7613264, asset type 00309) with a serial of its own
    const tuQRCode = await Backend.getHUQRCodeByIdentifier({ identifier: 'tu1' });
    const graiBarcode = `${GRAI_8003_PREFIX}${String(Date.now()).slice(-11)}`;
    await PickingJobScreen.goBack();
    await PickingJobsListScreen.goBack();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: tuQRCode });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.scanGraiBarcode({ barcodeString: graiBarcode });
    await GRAIScreen.tapSendButton();

    // back in the job: it completes now
    await GRAIScreen.goBack();
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.goBack();
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.startJob({ index: 1, productId: masterdata.products.P1.id });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

/**
 * TC23 expectation "every picked HU is a CU inside a TU of a TU packing, no loose CU": each booking's CU (`vhuN`) is listed
 * under its box (`tuN`, the `tu` of the qtyPicked row), and every box satisfies `qtyTUs: 1`, which holds only for a real
 * TU (never for a virtual packing), with the CU's quantity as its own storage (one CU per box here).
 */
const expectEveryPickedHUIsACUInATU = async ({ pickingJobId, boxes }) => {
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
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
test('TC23 (i): line with a box packing "TU x 4 Stk" (customer GRAI required): first booking refused, "Next box" opens box 1, a GRAI scan opens the next box', async ({ page }) => {
    allureMeta('Open box first, box packing line');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 12, graiRequired: 'Y' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });
    await GetQuantityDialog.expectQtyTarget(12);

    await PickLineScanScreen.nextBox();
    await SelectPickTargetTUScreen.expectNotShown();   // the line's packing names a TU type: one tap, no list
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 8 });

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 4 });

    await expectEveryPickedHUIsACUInATU({ pickingJobId, boxes: [{ qty: 4, grai: null }, { qty: 4, grai: grai }] });
});

// noinspection JSUnusedLocalSymbols
test('TC23 (ii): line with an unlimited-capacity packing that names a TU type: "Next box" opens no "Gebinde" list', async ({ page }) => {
    allureMeta('Open box first, unlimited capacity packing');

    const masterdata = await createMasterdata({
        formatName: newFormatName(),
        orders: [{ key: 'A', qty: 12, graiRequired: 'Y', piItemProduct: 'TU_INF' }],
        // no qtyCUsPerTU: the capacity row of this TU type is unlimited
        packingInstructions: { INF: { tu: 'TU_INF', product: 'P1' } },
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });

    await PickLineScanScreen.nextBox();
    await SelectPickTargetTUScreen.expectNotShown();
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 8 });

    await expectEveryPickedHUIsACUInATU({ pickingJobId, boxes: [{ qty: 4 }] });
});

// noinspection JSUnusedLocalSymbols
test('TC23 (iii): line without a TU type: first booking refused, "Next box" opens the "Gebinde" list (again after a booking, no close screen), a GRAI scan opens the next box', async ({ page }) => {
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
    });

    await test.step('"Next box" again: the list opens directly, the first box closed without a close screen', async () => {
        await PickLineScanScreen.nextBox();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickPickHUButton();   // the remembered label opens the quantity block again
        await book({ qty: 4, expectedQtyTarget: 4 });
    });

    await test.step('a GRAI scan opens the next box', async () => {
        await PickLineScanScreen.rescanGrai(grai);
        await PickLineScanScreen.expectOpenBoxCaption(masterdata.packingInstructions.G1.tuName);
        await GetQuantityDialog.clickDoneStayOpen();
    });

    await expectEveryPickedHUIsACUInATU({ pickingJobId, boxes: [{ qty: 4, grai: null }, { qty: 4, grai: null }, { qty: 4, grai: grai }] });
});
