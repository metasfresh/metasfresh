import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, BEST_BEFORE, createMasterdata, labelOf, MODE_MANUAL, MODE_SCAN_ONCE, MSG_NO_OPEN_BOX, newFormatName, openLine, scanLabel, SOURCE_QTY, startJob, uniqueLot } from "../../utils/cuQtyEntryScanOnce";

/**
 * CU quantity entry, Scan once ("one label, type a count"): count, source, box, over-pick, line done (TC5, TC5b, TC5c Scan once part,
 * TC6, TC7, TC9, TC10, TC1c, TC26, TC27 flow).
 *
 * Flow of the app as built: with a source HU scanned ("Kommissionieren aus"), a tap on a line with nothing picked opens its scan screen
 * directly; a label scan opens the quantity block; after OK the picker stays on the line (the block stays open, open quantity drops)
 * until the line is fully booked, then the app goes back to the job screen. Every booking needs an open box (`openBox()`), except TC5c.
 * Source: one pallet of 20 boxes of 4 pieces, so a count of 5 needs pieces from two source boxes.
 */
// noinspection JSUnusedLocalSymbols
test('TC5 / TC27 (i): box opened with "Next box", label scanned, count 5 (needs two source boxes) books ONE CU of 5 in the box', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata, lot }));
    await GetQuantityDialog.expectQtyEntered('');  // the field is empty after the scan
    await GetQuantityDialog.typeQtyEntered(5);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 }); // line open 5

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            // the box holds exactly one CU of 5, with lot and best-before of the label
            tu1: { storages: { P1: '5 PCE' }, cus: [{ qty: '5 PCE', attributes: { 'Lot-Nummer': lot, 'HU_BestBeforeDate': BEST_BEFORE } }] },
            vhu1: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lot, 'HU_BestBeforeDate': BEST_BEFORE } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC5b: Scan once field starts empty (not 0) with the zero-as-initial-value setting; a Manual line still shows 0', async ({ page }) => {
    allureMeta('Scan once count');

    let previousSysconfigs = null;
    try {
        const masterdata = await createMasterdata({
            formatName: newFormatName(),
            orders: [{ key: 'A', qty: 12 }, { key: 'B', qty: 12 }],
            // line 1 (buyer A): profile mode Scan once; line 2 (buyer B): record with Manual
            customers: [{ customer: 'custB', cuQtyEntryMode: MODE_MANUAL }],
            sysconfigs: { 'mobileui.frontend.qtyInput.useZeroAsInitialValue': 'Y' },
        });
        previousSysconfigs = masterdata.previousSysconfigs;
        await startJob(masterdata, { qtyToDeliver: 24 });

        await test.step('Scan once line: field empty after the label scan', async () => {
            await openLine({ index: 1 });
            await PickLineScanScreen.openBox();
            await scanLabel(labelOf({ masterdata }));
            await GetQuantityDialog.expectQtyEntered('');
            await GetQuantityDialog.clickCancel();
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Manual line: field shows 0 as today', async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
            await PickingJobScreen.clickLineButton({ index: 2 });
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.expectQtyEntered(0);
            await GetQuantityDialog.clickCancel();
            await PickingJobScreen.waitForScreen();
        });
    } finally {
        if (previousSysconfigs && Object.keys(previousSysconfigs).length > 0) {
            await Backend.setSysconfigs(previousSysconfigs);
        }
    }
});

// noinspection JSUnusedLocalSymbols
test('TC5c (Scan once): OK without an open box is refused, nothing is booked; after "Next box" the same booking succeeds', async ({ page }) => {
    allureMeta('Scan once needs an open box');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });

    // no "Next box", no GRAI scan
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(5);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_BOX });

    await GetQuantityDialog.expectQtyTarget(10);  // line still open 10
    // 'no pick-time GRAI screen' is not asserted: no customer of this data requires a GRAI, so that screen cannot appear anyway
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } },
    });

    await test.step('open the box, same booking succeeds', async () => {
        await PickLineScanScreen.openBox();
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                }
            }
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC6: the packs come from the "Kommissionieren aus" HU; the source pallet drops by the booked count, nothing is created on the fly', async ({ page }) => {
    allureMeta('Scan once source');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }], anonymousPickHUsOnTheFly: false });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(5);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                }
            }
        },
        hus: {
            // the source pallet is lowered by exactly the booked 5; with `anonymousPickHUsOnTheFly` N no other HU can have supplied them
            [masterdata.handlingUnits.P1_HU.qrCode]: { huStatus: 'A', storages: { P1: `${SOURCE_QTY - 5} PCE` } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC7 / TC27 (ii): open box holding 3, count 5 (needs two source boxes) books ONE CU of 5 into it', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata, lot }));

    await test.step('first booking: 3 into the box (leaves 1 piece in the first source box)', async () => {
        await GetQuantityDialog.typeQtyEntered(3);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 7 });
    });

    await test.step('second booking: 5 (1 + 4 from two source boxes), no "try again" error', async () => {
        await GetQuantityDialog.typeQtyEntered(5);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "3 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu2', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    }
                }
            }
        },
        hus: {
            // the open box holds 8 = one CU of 3 and ONE CU of 5. No requirement orders the CUs inside a box, and the
            // backend's `cus` assertion sorts by M_HU_ID (the merged CU of 5 can carry the lower id), so each CU is
            // asserted by its own content through its picking alias; 3 + 5 = the box total leaves no room for a third CU
            tu1: { storages: { P1: '8 PCE' } },
            vhu1: { storages: { P1: '3 PCE' }, attributes: { 'Lot-Nummer': lot } },
            vhu2: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lot } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC9: a count smaller than the open quantity is not a rejection', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.expectNoQtyRejected();   // no reject reason asked
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 6 }); // line open 6

    await GetQuantityDialog.clickCancel();
    await PickingJobScreen.waitForScreen();
    // the line button shows the picked quantity only (4); that nothing was rejected is proven above:
    // no reject field in the dialog and the open quantity dropped to 6 (a booked rejection would have dropped it to 0)
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '4 Stk' });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }] }
                }
            }
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC10: the booking that reaches the open quantity finishes the line and goes back to the job screen', async ({ page }) => {
    allureMeta('Scan once line done');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));

    await GetQuantityDialog.typeQtyEntered(8);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });  // booked 8: the picker stays on the line

    await GetQuantityDialog.typeQtyEntered(2);
    await GetQuantityDialog.clickDoneStayOpen();                          // booked 2: line done
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '10 Stk' });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "8 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "2 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu2', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    }
                }
            }
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC1c: a count above the open quantity is blocked as today, on a Manual line and on a Scan once line', async ({ page }) => {
    allureMeta('Over-pick unchanged');

    // profile Manual (mode not sent) with a Scan once customer record; both lines order 12 (Manual line counts 3 boxes of 4)
    const formatName = newFormatName();
    const masterdata = await createMasterdata({
        formatName,
        profileMode: null,
        orders: [{ key: 'A', qty: 12 }, { key: 'B', qty: 12 }],
        customers: [{ customer: 'custB', cuQtyEntryMode: MODE_SCAN_ONCE, cuLabelFormatName: formatName }],
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 24 });

    await test.step('Manual line (3 boxes open): 4 is refused by the validation', async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.typeQtyEntered(4);
        await GetQuantityDialog.expectDoneDisabled();
        await GetQuantityDialog.expectQtyValidationError('above max');
        await GetQuantityDialog.clickCancel();
        await PickingJobScreen.waitForScreen();
    });

    await test.step('Scan once line (12 open): 13 is refused by the same validation', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openLine({ index: 2 });
        await PickLineScanScreen.openBox();
        await scanLabel(labelOf({ masterdata }));
        await GetQuantityDialog.typeQtyEntered(13);
        await GetQuantityDialog.expectDoneDisabled();
        await GetQuantityDialog.expectQtyValidationError('above max');
        await GetQuantityDialog.clickCancel();
        await PickingJobScreen.waitForScreen();
    });

    // nothing extra booked on either line
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC26: on a line whose packing is a box packing, a SECOND CU booking into the open box is accepted', async ({ page }) => {
    allureMeta('Scan once on a box-packed line');

    const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openBox();
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(1);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 9 });
    // the first booking turns the new box into a real box (TU); only a SECOND booking into it reaches the
    // rule "Cannot pick TUs when TU pick target is set!" (PickingJobPickCommand: TU picking unit + existing TU target)
    await test.step('second booking into the now existing box: no "Cannot pick TUs when TU pick target is set!"', async () => {
        await GetQuantityDialog.typeQtyEntered(1);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 8 });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "1 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "1 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu2', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    }
                }
            }
        },
        hus: { tu1: { storages: { P1: '2 PCE' }, cus: [{ qty: '1 PCE' }, { qty: '1 PCE' }] } },
    });
});
