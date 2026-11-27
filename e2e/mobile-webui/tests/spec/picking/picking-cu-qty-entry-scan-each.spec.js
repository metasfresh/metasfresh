import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { allureMeta, createMasterdata, MSG_NO_OPEN_TU, uniqueFormatName, qtyPickedRow, restoreSysconfigsAfterEach, setLUTarget, startJob, uniqueLot, unpackFirstBooking } from "../../utils/cuQtyEntryCommon";
import { buildCULabel, uniqueGTIN } from "../../utils/cuLabel";
import { MESSAGES } from "../../utils/cuQtyEntryMessages";
import { createScanEachMasterdata, MODE_SCAN_EACH, MSG_ALREADY_PICKED, MSG_FORMAT_MISMATCH, MSG_NO_PACK_ID, MSG_OVER_PICK, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);
// the English reason of the front-end label parser (parseCustomQRCode.js: "Expected constant '01' but got ..."); the toast must never show it
const PARSER_REASON_EN = "Expected constant";

/** CU quantity entry, Scan each CU ("scan every CU label") */

const masterdataOf = async ({ orders = [{ key: 'A', qty: 3 }], ...rest } = {}) =>
    await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders, ...rest });

// noinspection JSUnusedLocalSymbols
test('scan IDs 1 and 2 book one CU each into the open TU, a rescan of ID 1 is warned, ID 3 books the last CU and finishes the line', async ({ page }) => {
    allureMeta('Scan each CU chain');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 3 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });

    await test.step('first scan books one CU of 1, no quantity block', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
            hus: { tu1: { cus: [{ qty: '1 PCE', attributes: { 'Lot-Nummer': lot, PackId: '1' } }] } },
        });
    });

    await test.step('second scan = a second CU in the same TU', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
            hus: { tu1: { storages: { P1: '2 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE', attributes: { PackId: '2' } }] } },
        });
    });

    await test.step('the same CU again is warned, nothing is booked', async () => {
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_ALREADY_PICKED(1));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
        });
    });

    await test.step('third CU finishes the line (back on the job screen), job completes', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 3 }));
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.complete();
        await Backend.expect({
            pickings: { [pickingJobId]: { docStatus: 'Completed', shipmentSchedules: { P1: { qtyPicked: [
                { qtyPicked: '1 PCE', vhu: '-', tu: 'tu1', processed: true }, { qtyPicked: '1 PCE', vhu: '-', tu: 'tu1', processed: true }, { qtyPicked: '1 PCE', vhu: '-', tu: 'tu1', processed: true },
            ] } } } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('a scan after the line is done books nothing: the picker re-enters the finished line via "HU verpacken", a new CU is refused', async ({ page }) => {
    allureMeta('Scan each CU line done');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 3 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
    await scanPack(packLabelOf({ masterdata, lot, packId: 3 }));
    // the third CU finishes the line: back on the job screen, 3 of 3 picked
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '3 Stk' });

    const threeBooked = [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })];

    await test.step('re-enter the done line via "HU verpacken", scan a fourth CU: refused, nothing is booked', async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.tapPickHUButton();
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 4 }), MSG_OVER_PICK);
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: threeBooked } } } },
            hus: { tu1: { storages: { P1: '3 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }, { qty: '1 PCE' }] } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('a second lot on the same line books a CU with that lot; the first CU keeps its lot', async ({ page }) => {
    allureMeta('Scan each CU lots');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 2 }] });
    const lotA = uniqueLot();
    const lotB = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, lot: lotA, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, lot: lotB, packId: 1 })); // same pack ID, other lot = another CU
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
        hus: { tu1: { cus: [{ attributes: { 'Lot-Nummer': lotA } }, { attributes: { 'Lot-Nummer': lotB } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('the same CU in two jobs: job 1 picked it, job 2 books it without a warning (scope = the current job)', async ({ page }) => {
    allureMeta('Scan each CU two jobs');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 1 }, { key: 'B', qty: 1 }], allowCompletingPartialPickingJob: true }); // job 1 completes with customer B's line unpicked
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
    await Backend.expect({ pickings: { [job2Id]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('"Unpack" of a booked CU, then the same label books again without a warning', async ({ page }) => {
    allureMeta('Scan each CU unpick');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 2 }] });
    const lot = uniqueLot();
    const label = packLabelOf({ masterdata, lot, packId: 2 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(label);
    await unpackFirstBooking();
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickLineScanScreen.waitForScreen();
    await scanPack(label); // booked again, no warning
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('TU opened by GRAI scan: "Unpack" of the TU\'s only CU, then the same label books again into a TU that carries the GRAI', async ({ page }) => {
    allureMeta('Scan each CU unpick, GRAI TU');

    const masterdata = await masterdataOf({
        orders: [{ key: 'A', qty: 2 }],
        packingInstructions: { G1: { tu: 'TU_G1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true } },
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const label = packLabelOf({ masterdata, lot, packId: 2 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanGrai(grai);
    await scanPack(label);
    await unpackFirstBooking(); // the TU's only CU: the TU is emptied
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickLineScanScreen.waitForScreen();
    await scanPack(label); // booked again, no warning, into the still open GRAI TU
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
        hus: { tu1: { storages: { P1: '1 PCE' }, attributes: { GRAI: grai } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('the TU\'s first CU: a rescan is warned; "Unpack" frees only that CU (the other CU stays), then the rescan books', async ({ page }) => {
    allureMeta('Scan each CU: first CU of the TU');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 3 }] });
    const lot = uniqueLot();
    const first = packLabelOf({ masterdata, lot, packId: 1 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });
    await scanPack(first);
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
    await PickLineScanScreen.scanCULabelRefused(first, MSG_ALREADY_PICKED(1));

    await unpackFirstBooking(); // the step list holds the first booking at index 0
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
        hus: { tu1: { storages: { P1: '1 PCE' } } },
    });
    await PickingJobScreen.clickLineButton({ index: 1 });   // the other CU is still picked: the line screen opens
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.tapPickHUButton();            // "HU verpacken": back to the scan screen, the TU is still open
    await PickLineScanScreen.waitForScreen();
    await scanPack(first);
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('lots A, A, B: 3 CUs, the TU has no lot (mixed), PackId on no TU, the first CU keeps lot A', async ({ page }) => {
    allureMeta('Scan each CU mixed lots');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 3 }] });
    const lotA = uniqueLot();
    const lotB = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, lot: lotA, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, lot: lotA, packId: 2 }));
    await scanPack(packLabelOf({ masterdata, lot: lotB, packId: 3 }));
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '3 PCE' }, cus: [{ qty: '1 PCE', attributes: { 'Lot-Nummer': lotA, PackId: '1' } }, { qty: '1 PCE', attributes: { 'Lot-Nummer': lotA, PackId: '2' } }, { qty: '1 PCE', attributes: { 'Lot-Nummer': lotB, PackId: '3' } }], attributes: { 'Lot-Nummer': null, PackId: null } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('buyer line (Scan each CU) with an EMPTY own format inherits the profile format: it has no pack-ID part, so every label is refused with the pack-ID text', async ({ page }) => {
    allureMeta('Scan each CU needs a pack ID');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),                               // the profile's format: WITHOUT a pack-ID part
        orders: [{ key: 'C', qty: 2 }],
        profileMode: MODE_SCAN_EACH,
        customers: [{ customer: 'custC', cuQtyEntryMode: MODE_SCAN_EACH }],   // no own format: the profile's applies
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), MSG_NO_PACK_ID);

    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('buyer B line (Scan each CU, pack-ID format): a label without counter (L0) is refused as a format mismatch; buyer C line (effective format has no pack-ID part): every label is refused with the pack-ID text', async ({ page }) => {
    allureMeta('Scan each CU needs a pack ID');

    const plainFormat = uniqueFormatName();
    const masterdata = await masterdataOf({
        orders: [{ key: 'B', qty: 2 }, { key: 'C', qty: 2 }],
        // buyer C: record with Scan each CU and its own format WITHOUT a pack-ID part
        customers: [{ customer: 'custC', cuQtyEntryMode: MODE_SCAN_EACH, cuLabelFormatName: plainFormat }],
        formats: [{ name: plainFormat, withPackId: false }],
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 4 });

    await test.step('buyer B, label L0 (no counter): refused', async () => {
        await openScanEachLine({ index: 1 });
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: null }), MSG_FORMAT_MISMATCH);
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
test('Scan each CU: a label the format rejects is refused and nothing is booked, not even by the product GTIN; the line goes on with a valid label', async ({ page }) => {
    allureMeta('Scan each CU rejects a label of another form');

    const gtin = uniqueGTIN();
    const masterdata = await masterdataOf({
        orders: [{ key: 'A', qty: 3 }],
        products: { P1: { price: 1, gtin, randomValue: { size: 7, isIncludeDigits: true } } },
    });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });

    const validLabel = buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, packId: 1 });
    await test.step('a label with a "]d2" prefix (a form the format does not read) is refused, nothing is booked', async () => {
        // the refusal toast shows only the localized message; the parser reason (constant "01" finds "]d") goes to the UI trace
        await PickLineScanScreen.scanCULabelRefused(']d2' + validLabel, `${MSG_FORMAT_MISMATCH}. Nothing was booked.`, { unexpectedText: PARSER_REASON_EN });
    });
    await test.step('a bare GTIN is refused, nothing is booked', async () => {
        await PickLineScanScreen.scanCULabelRefused(gtin, MSG_FORMAT_MISMATCH);
    });
    await test.step('a string of another shape is refused, nothing is booked', async () => {
        await PickLineScanScreen.scanCULabelRefused('not-a-label-of-this-format', `${MSG_FORMAT_MISMATCH}. Nothing was booked.`, { unexpectedText: PARSER_REASON_EN });
    });
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await test.step('the valid label of the same CU books one CU: the refusals left the line untouched', async () => {
        await scanPack(validLabel);
    });
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } },
        hus: { tu1: { cus: [{ attributes: { 'Lot-Nummer': lot, PackId: '1' } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: a label scan without an open TU is refused with the open-a-TU text, nothing is booked; after "Next TU" the same scan books', async ({ page }) => {
    allureMeta('Scan each CU needs an open TU');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 2 }] });
    const label = packLabelOf({ masterdata, packId: 1 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanCULabelRefused(label, MSG_NO_OPEN_TU);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });

    await PickLineScanScreen.openTU();
    await scanPack(label);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: customer GRAI required: no pick-time GRAI screen; (i) first scan without a TU is refused, (ii) after "Next TU" it books into the TU', async ({ page }) => {
    allureMeta('Scan each CU GRAI required customer');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 2, graiRequired: 'Y' }] });
    const label = packLabelOf({ masterdata, packId: 1 });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanCULabelRefused(label, MSG_NO_OPEN_TU);
    await PickLineScanScreen.openTU();
    await scanPack(label);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } } });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: TU packing line: first scan refused without a TU; "Next TU", then every picked HU is a CU inside a TU of a TU packing (no loose CU)', async ({ page }) => {
    allureMeta('Scan each CU open TU first');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 3 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, packId: 1 }), MSG_NO_OPEN_TU);
    await PickLineScanScreen.openTU();
    for (const packId of [1, 2, 3]) {
        await scanPack(packLabelOf({ masterdata, packId }));
    }
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [1, 2, 3].map(() => qtyPickedRow({ qty: 1, tu: 'tu1' })) } } } },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '3 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }, { qty: '1 PCE' }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line whose packing is "TU x 4 Stk", TU open: a second CU is booked into the open TU (no "Cannot pick TUs when TU pick target is set!")', async ({ page }) => {
    allureMeta('Scan each CU TU packing');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 2 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, packId: 1 }));
    await scanPack(packLabelOf({ masterdata, packId: 2 }));
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [1, 2].map(() => qtyPickedRow({ qty: 1, tu: 'tu1' })) } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('counters 9 and 10 give pack IDs "9" and "10" through the open-end pack-ID part', async ({ page }) => {
    allureMeta('Scan each CU open-end pack ID');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 2 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, packId: 9 }));
    await scanPack(packLabelOf({ masterdata, packId: 10 }));
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu1' })] } } } }, hus: { tu1: { cus: [{ attributes: { PackId: '9' } }, { attributes: { PackId: '10' } }] } } });
});

// noinspection JSUnusedLocalSymbols
test('Pallet target (Scan each CU): pallet + new TU: every scan books one CU into the TU on the pallet; "Unpack" of CU 1 frees only that CU', async ({ page }) => {
    allureMeta('Scan each CU pallet target');

    const masterdata = await masterdataOf({ orders: [{ key: 'A', qty: 3 }], pickTo: ['LU_TU', 'TU'] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1, openTU: false });
    await setLUTarget({ lu: masterdata.packingInstructions.P1_20x4.luName });
    await PickLineScanScreen.openTU();

    await test.step('CUs 1 and 2: one CU of 1 each, in the same TU, the TU on the pallet', async () => {
        await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
        await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [1, 2].map((n) => ({ qtyPicked: '1 PCE', vhu: `vhu${n}`, tu: 'tu1', lu: 'lu1', processed: false })) } } } },
            hus: {
                tu1: { storages: { P1: '2 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }] },
                vhu1: { storages: { P1: '1 PCE' }, attributes: { 'Lot-Nummer': lot, PackId: '1' } },
                vhu2: { storages: { P1: '1 PCE' }, attributes: { PackId: '2' } },
            },
        });
    });

    await test.step('"Unpack" of CU 1: only that CU is freed, CU 2 stays in the TU on the pallet', async () => {
        await unpackFirstBooking();
        await Backend.expect({
            pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: '1 PCE', vhu: 'vhu2', tu: 'tu1', lu: 'lu1', processed: false }] } } } },
            hus: { tu1: { storages: { P1: '1 PCE' } } },
        });
    });
});

const MSG_EACH_ONLY_FOR_PIECES = MESSAGES.eachOnlyPieces.en + ". Please have your administrator set the CU quantity entry for this customer to 'Manual' or 'Scan once, enter quantity'.";

// noinspection JSUnusedLocalSymbols
test('a line counted in kg: a CU scan is refused with the "pieces only" message, nothing is booked', async ({ page }) => {
    allureMeta('Scan each CU kg line');

    const masterdata = await masterdataOf({
        orders: [{ key: 'A', qty: 3 }],
        products: { P1: { price: 1, uom: 'KGM', randomValue: { size: 7, isIncludeDigits: true } } },
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });

    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot: uniqueLot(), packId: 1 }), MSG_EACH_ONLY_FOR_PIECES);
    await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });
});
