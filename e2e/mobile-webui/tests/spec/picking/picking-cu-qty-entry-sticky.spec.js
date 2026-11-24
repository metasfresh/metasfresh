import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, BEST_BEFORE, book, createMasterdata, restoreSysconfigsAfterEach, labelOf, uniqueFormatName, openLine, qtyPickedRow, scanLabel, startJob, uniqueLot } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * CU quantity entry, Scan once: the pinned count and the navigation around it (re-entry via "HU verpacken").
 *
 * The field suggests the last typed count, capped by the line's open quantity; confirming books it again on the
 * last scanned lot without a new scan; a label scanned inside the open block replaces product / lot / best-before
 * for the next booking. A new line starts empty, also the next line of the same product.
 */
const BEST_BEFORE_B = '2026-12-01';

// noinspection JSUnusedLocalSymbols
test('the count is pinned per line (12 -> 5, 5, MIN(5, 2) = 2), booked on the last lot without a scan; the next line of the same product starts empty', async ({ page }) => {
    allureMeta('Pinned count');

    // line 1: buyer A, 12; line 2: buyer B, 3 (same product)
    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }, { key: 'B', qty: 3 }] });
    const lotA = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 15 });

    await test.step('line 1, TU 1: scan the label, type 5, OK', async () => {
        await openLine({ index: 1 });
        await PickLineScanScreen.openTU();
        await scanLabel(labelOf({ masterdata, lot: lotA }));
        await GetQuantityDialog.expectQtyEntered('');  // nothing pinned yet
        await book({ qty: 5, expectedQtyTarget: 7 });
    });

    await test.step('TU 2: the suggestion is 5, confirmed without a label scan (open 2 afterwards)', async () => {
        await GetQuantityDialog.expectQtyEntered(5);
        await PickLineScanScreen.nextTU();
        await GetQuantityDialog.expectQtyEntered(5);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });
    });

    await test.step('TU 3: the suggestion is MIN(5, 2) = 2, confirmed, the line is done', async () => {
        await GetQuantityDialog.expectQtyEntered(2);
        await PickLineScanScreen.nextTU();
        await GetQuantityDialog.expectQtyEntered(2);
        await GetQuantityDialog.clickDoneStayOpen();
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '12 Stk' });
    });

    await test.step('next line (same product, other customer): the field starts empty', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openLine({ index: 2 });
        await PickLineScanScreen.openTU();
        await scanLabel(labelOf({ masterdata, lot: lotA }));
        await GetQuantityDialog.expectQtyEntered('');
        await GetQuantityDialog.clickCancel();
        await PickingJobScreen.waitForScreen();
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' }),
                            qtyPickedRow({ qty: 5, vhu: 'vhu2', tu: 'tu2' }),
                            qtyPickedRow({ qty: 2, vhu: 'vhu3', tu: 'tu3' }),
                        ]
                    }
                }
            }
        },
        hus: {
            // TUs 2 and 3 were booked without a scan, on the lot of the one label scanned in TU 1
            vhu1: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lotA } },
            vhu2: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lotA } },
            vhu3: { storages: { P1: '2 PCE' }, attributes: { 'Lot-Nummer': lotA } },
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' } },
            tu2: { qtyTUs: 1, storages: { P1: '5 PCE' } },
            tu3: { qtyTUs: 1, storages: { P1: '2 PCE' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('a label of another lot scanned in the open block replaces the remembered lot; the count stays 5', async ({ page }) => {
    allureMeta('Pinned count, lot change');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }] });
    const lotA = uniqueLot();
    const lotB = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openTU();
    await scanLabel(labelOf({ masterdata, lot: lotA }));
    await book({ qty: 5, expectedQtyTarget: 7 });

    await PickLineScanScreen.nextTU();
    await PickLineScanScreen.rescanCULabel(labelOf({ masterdata, lot: lotB, bestBefore: BEST_BEFORE_B }));
    await GetQuantityDialog.expectQtyEntered(5);   // the suggestion stayed 5
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 5, vhu: 'vhu2', tu: 'tu2' })] }
                }
            }
        },
        hus: {
            // TU 2 holds one CU of 5 with lot B and its best-before; TU 1 keeps lot A
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, cus: [{ qty: '5 PCE', attributes: { 'Lot-Nummer': lotA, 'HU_BestBeforeDate': BEST_BEFORE } }] },
            tu2: { qtyTUs: 1, storages: { P1: '5 PCE' }, cus: [{ qty: '5 PCE', attributes: { 'Lot-Nummer': lotB, 'HU_BestBeforeDate': BEST_BEFORE_B } }] },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('book 6 of 10, close the line short: 4 stay open, no reject reason asked, "Wieder öffnen" reopens it, the job completes', async ({ page }) => {
    allureMeta('Close a line short');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openTU();
    await scanLabel(labelOf({ masterdata }));
    await book({ qty: 6, expectedQtyTarget: 4 });
    await GetQuantityDialog.clickCancel();
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.clickLineButton({ index: 1 });   // something is picked: the line screen opens
    await PickingJobLineScreen.waitForScreen();

    await test.step('"Schließen": the line is closed, nothing is asked', async () => {
        await PickingJobLineScreen.clickCloseButton();
        await PickingJobLineScreen.expectReOpenButtonVisible();
        await GetQuantityDialog.expectNotShown();   // no reject reason / quantity dialog
    });

    await test.step('"Wieder öffnen": the line is open again', async () => {
        await PickingJobLineScreen.clickReOpenButton();
        await PickingJobLineScreen.expectCloseButtonVisible();
    });

    await test.step('closed again, the job completes', async () => {
        await PickingJobLineScreen.clickCloseButton();
        await PickingJobLineScreen.expectReOpenButtonVisible();
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '6 Stk' });   // the open 4 stay open: nothing picked, nothing rejected for them
        await PickingJobScreen.complete();
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                docStatus: 'Completed',
                // closing books nothing: the 6 stay the only booking, the open 4 are not rejected or picked
                shipmentSchedules: { P1: { qtyPicked: [{ qtyPicked: "6 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine1' }] } },
            }
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Re-entry via "HU verpacken": the quantity block reopens with the remembered label and the pinned count, the TU is kept', async ({ page }) => {
    allureMeta('Re-entry keeps the TU');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openTU();
    await scanLabel(labelOf({ masterdata, lot }));
    await book({ qty: 6, expectedQtyTarget: 4 });
    await GetQuantityDialog.clickCancel();
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();

    await PickingJobLineScreen.clickPickHUButton();           // "HU verpacken": the block reopens, no new label scan
    await GetQuantityDialog.expectQtyEntered(4);              // MIN(last typed 6, open 4)
    await GetQuantityDialog.clickDoneStayOpen();              // books 4 into the kept TU: the line is done
    // a finished line honours the setting PickLineScanScreen.gotoPickingJobOnClose (shipped N): the screen was entered from the line screen ("HU verpacken"), so it leads back there
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '10 Stk' });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 6, vhu: 'vhu1', tu: 'tu1' }), qtyPickedRow({ qty: 4, vhu: 'vhu2', tu: 'tu1' })] }
                }
            }
        },
        hus: {
            // both bookings sit in the one TU, on the remembered lot
            tu1: { qtyTUs: 1, storages: { P1: '10 PCE' } },
            vhu1: { storages: { P1: '6 PCE' }, attributes: { 'Lot-Nummer': lot } },
            vhu2: { storages: { P1: '4 PCE' }, attributes: { 'Lot-Nummer': lot } },
        },
    });
});
