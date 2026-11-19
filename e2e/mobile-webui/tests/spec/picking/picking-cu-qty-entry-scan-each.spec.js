import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { allureMeta, MSG_NO_OPEN_BOX, newFormatName, qtyPickedRow, restoreSysconfigsAfterEach, startJob, uniqueLot } from "../../utils/cuQtyEntryScanOnce";
import { createScanEachMasterdata, MODE_SCAN_EACH, MSG_ALREADY_PICKED, MSG_NO_PACK_ID, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/**
 * CU quantity entry, Scan each CU ("scan every pack label"): every scan books one CU of 1 into the open box; a pack label needs a
 * pack ID; the same pack twice in one job is warned; "Unpack" frees a CU (TC11 - TC13 chain, TC12b - TC12e, TC25, TC3 cases
 * "B, L0" and "C, L(1)", TC5c Scan each CU part, TC17, TC23 (i) / (ii), TC26, TC30 variant).
 *
 * Needs the pack-ID part type of the label-reader change (merged on soft_panda_hotfix): the label format of these specs has a
 * pack-ID part (`buildCULabelFormat({ withPackId: true })`).
 *
 * Source: one pallet of 20 boxes of 4 pieces (80 pieces). Every case opens box 1 first ("Next box"), except TC5c / TC17 (i) / TC23.
 */

/** Opens the pick-step screen of the only picked HU of line `index` and unpacks it ("Entpacken", no target HU). */
const unpackFirstPick = async ({ lineIndex }) => {
    await PickLineScanScreen.goBack(); // lands on the line screen, which lists the picked HUs
    await PickingJobLineScreen.clickStepButton({ index: 0 });
    await PickingJobStepScreen.unpick();
    await PickingJobLineScreen.goBack(); // the unpick leaves on the line screen
};

const newMasterdata = async ({ orders = [{ key: 'A', qty: 3 }], ...rest } = {}) =>
    await createScanEachMasterdata({ packFormatName: newFormatName(), orders, ...rest });

// noinspection JSUnusedLocalSymbols
test('TC11 -> TC12 -> TC13: scan IDs 1 and 2 book one CU each into the open box, a rescan of ID 1 is warned, ID 3 books the last CU and finishes the line', async ({ page }) => {
    allureMeta('Scan each CU chain');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 3 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });

    await test.step('TC11: first scan books one CU of 1, no quantity block', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' })] } } } },
            hus: { vhu1: { storages: { P1: '1 PCE' }, attributes: { 'Lot-Nummer': lot, PackId: '1' } } },
        });
    });

    await test.step('TC12: second scan = a second CU in the same box', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' })] } } } },
            hus: { tu1: { storages: { P1: '2 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }] }, vhu2: { attributes: { PackId: '2' } } },
        });
    });

    await test.step('TC13: the same pack again is warned, nothing is booked', async () => {
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_ALREADY_PICKED(1));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' })] } } } },
        });
    });

    await test.step('third pack finishes the line (back on the job screen), job completes', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 3 }));
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.complete();
        await Backend.expect({
            // completing the job processes the bookings and puts the boxes on an LU: only CU, box and processed are pinned
            pickings: { [pickingJobId]: { docStatus: 'Completed', shipmentSchedules: { P1: { qtyPicked: [
                { qtyPicked: '1 PCE', vhu: 'vhu1', tu: 'tu1', processed: true }, { qtyPicked: '1 PCE', vhu: 'vhu2', tu: 'tu1', processed: true }, { qtyPicked: '1 PCE', vhu: 'vhu3', tu: 'tu1', processed: true },
            ] } } } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('TC12b: a second lot on the same line books a CU with that lot; the first CU keeps its lot', async ({ page }) => {
    allureMeta('Scan each CU lots');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 2 }] });
    const lotA = uniqueLot();
    const lotB = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, lot: lotA, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, lot: lotB, packId: 1 })); // same pack ID, other lot = another pack
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' })] } } } },
        hus: { vhu1: { attributes: { 'Lot-Nummer': lotA } }, vhu2: { attributes: { 'Lot-Nummer': lotB } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC12c: the same pack in two jobs: job 1 picked it, job 2 books it without a warning (scope = the current job)', async ({ page }) => {
    allureMeta('Scan each CU two jobs');

    // a product job holds every customer who ordered the product: job 1 = both orders; the picker packs customer A's line only
    // and completes; customer B's still open order is offered again and becomes job 2 (same product, same lot, same pack)
    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 1 }, { key: 'B', qty: 1 }] });
    const lot = uniqueLot();
    const label = packLabelOf({ masterdata, lot, packId: 2 });

    const job1 = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(label);
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [job1.pickingJobId]: { docStatus: 'Completed' } } });

    const { pickingJobId: job2Id } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 1, productId: masterdata.products.P1.id });
    await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
    await openScanEachLine({ index: 1 });
    await scanPack(label); // no warning
    await PickingJobScreen.waitForScreen();
    await Backend.expect({ pickings: { [job2Id]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('TC12d: "Unpack" of a booked CU, then the same label books again without a warning', async ({ page }) => {
    allureMeta('Scan each CU unpick');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 2 }] });
    const lot = uniqueLot();
    const label = packLabelOf({ masterdata, lot, packId: 2 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(label);
    await unpackFirstPick({ lineIndex: 1 });
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickLineScanScreen.waitForScreen();
    await scanPack(label); // booked again, no warning
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('TC12d (box opened by GRAI scan): "Unpack" of the box\'s only pack, then the same label books again into a box that carries the GRAI', async ({ page }) => {
    allureMeta('Scan each CU unpick, GRAI box');

    const masterdata = await newMasterdata({
        orders: [{ key: 'A', qty: 2 }],
        packingInstructions: { G1: { tu: 'TU_G1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true } },
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const label = packLabelOf({ masterdata, lot, packId: 2 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanGrai(grai);
    await scanPack(label);
    await unpackFirstPick({ lineIndex: 1 }); // the box's only pack: the box is emptied
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickLineScanScreen.waitForScreen();
    await scanPack(label); // booked again, no warning, into the still open GRAI box
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' })] } } } },
        hus: { tu1: { storages: { P1: '1 PCE' }, attributes: { GRAI: grai } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC12e: the box\'s first pack: a rescan is warned; "Unpack" frees only that CU (the other CU stays), then the rescan books', async ({ page }) => {
    allureMeta('Scan each CU first pack of the box');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 3 }] });
    const lot = uniqueLot();
    const first = packLabelOf({ masterdata, lot, packId: 1 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });
    await scanPack(first);
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
    await PickLineScanScreen.scanCULabelRefused(first, MSG_ALREADY_PICKED(1));

    await unpackFirstPick({ lineIndex: 1 }); // the step list holds the first booking at index 0
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' })] } } } },
        hus: { tu1: { storages: { P1: '1 PCE' } } },
    });
    await PickingJobScreen.clickLineButton({ index: 1 });   // the other CU is still picked: the line screen opens
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.tapPickHUButton();            // "HU verpacken": back to the scan screen, the box is still open
    await PickLineScanScreen.waitForScreen();
    await scanPack(first);
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu3', tu: 'tu1' })] } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC25: lots A, A, B: 3 CUs, the box has no lot (mixed), PackId on no box, the first CU keeps lot A', async ({ page }) => {
    allureMeta('Scan each CU mixed lots');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 3 }] });
    const lotA = uniqueLot();
    const lotB = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, lot: lotA, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, lot: lotA, packId: 2 }));
    await scanPack(packLabelOf({ masterdata, lot: lotB, packId: 3 }));
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu3', tu: 'tu1' })] } } } },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '3 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }, { qty: '1 PCE' }], attributes: { 'Lot-Nummer': null, PackId: null } },
            vhu1: { attributes: { 'Lot-Nummer': lotA, PackId: '1' } },
            vhu2: { attributes: { 'Lot-Nummer': lotA, PackId: '2' } },
            vhu3: { attributes: { 'Lot-Nummer': lotB, PackId: '3' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC3: buyer B line (Scan each CU, pack-ID format): a label without counter (L0) is refused with the pack-ID text; buyer C line (effective format has no pack-ID part): every label is refused with the pack-ID text', async ({ page }) => {
    allureMeta('Scan each CU needs a pack ID');

    const plainFormat = newFormatName();
    const masterdata = await newMasterdata({
        orders: [{ key: 'B', qty: 2 }, { key: 'C', qty: 2 }],
        // buyer C: record with Scan each CU and its own format WITHOUT a pack-ID part
        customers: [{ customer: 'custC', cuQtyEntryMode: MODE_SCAN_EACH, cuLabelFormatName: plainFormat }],
        formats: [{ name: plainFormat, withPackId: false }],
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 4 });

    await test.step('buyer B, label L0 (no counter): refused', async () => {
        await openScanEachLine({ index: 1 });
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: null }), MSG_NO_PACK_ID);
        await PickLineScanScreen.goBack();
        await PickingJobLineScreen.goBack(); // line screen -> job screen
    });

    await test.step('buyer C, label L(1): the effective format has no pack-ID part, refused', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openScanEachLine({ index: 2 });
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), MSG_NO_PACK_ID);
    });

    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('TC5c (Scan each CU): a label scan without an open box is refused with the open-a-box text, nothing is booked; after "Next box" the same scan books', async ({ page }) => {
    allureMeta('Scan each CU needs an open box');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 2 }] });
    const label = packLabelOf({ masterdata, packId: 1 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanCULabelRefused(label, MSG_NO_OPEN_BOX);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await PickLineScanScreen.openBox();
    await scanPack(label);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('TC17 (Scan each CU): customer GRAI required: no pick-time GRAI screen; (i) first scan without a box is refused, (ii) after "Next box" it books into the box', async ({ page }) => {
    allureMeta('Scan each CU GRAI required customer');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 2, graiRequired: 'Y' }] });
    const label = packLabelOf({ masterdata, packId: 1 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanCULabelRefused(label, MSG_NO_OPEN_BOX);
    await PickLineScanScreen.openBox();
    await scanPack(label);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('TC23 (i) / (ii) (Scan each CU): box packing line: first scan refused without a box; "Next box", then every picked HU is a CU inside a TU of a TU packing (no loose CU)', async ({ page }) => {
    allureMeta('Scan each CU open box first');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 3 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1, openBox: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), MSG_NO_OPEN_BOX);
    await PickLineScanScreen.openBox();
    for (const packId of [1, 2, 3]) {
        await scanPack(packLabelOf({ masterdata, packId }));
    }
    // each picked HU = a CU whose box (`tuN`, qtyTUs 1 = a real TU, never the virtual packing) is a TU packing
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [1, 2, 3].map((n) => qtyPickedRow({ qty: 1, vhu: `vhu${n}`, tu: 'tu1' })) } } } },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '3 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }, { qty: '1 PCE' }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC26 (Scan each CU): line whose packing is "TU x 4 Stk", box open: a second CU is booked into the open box (no "Cannot pick TUs when TU pick target is set!")', async ({ page }) => {
    allureMeta('Scan each CU box packing');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 2 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, packId: 2 }));
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [1, 2].map((n) => qtyPickedRow({ qty: 1, vhu: `vhu${n}`, tu: 'tu1' })) } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC30 variant: counters 9 and 10 give pack IDs "9" and "10" through the open-end pack-ID part', async ({ page }) => {
    allureMeta('Scan each CU open-end pack ID');

    const masterdata = await newMasterdata({ orders: [{ key: 'A', qty: 2 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, packId: 9 }));
    await scanPack(packLabelOf({ masterdata, packId: 10 }));
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 1, vhu: 'vhu2', tu: 'tu1' })] } } } }, hus: { vhu1: { attributes: { PackId: '9' } }, vhu2: { attributes: { PackId: '10' } } } });
});
