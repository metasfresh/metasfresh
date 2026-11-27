import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, BEST_BEFORE, createMasterdata, restoreSysconfigsAfterEach, labelOf, MODE_MANUAL, MODE_SCAN_ONCE, MSG_NO_OPEN_TU, uniqueFormatName, openLine, scanLabel, setLUTarget, SOURCE_QTY, startJob, uniqueLot, unpackFirstBooking } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** CU quantity entry, Scan once ("one label, type a count") */
// noinspection JSUnusedLocalSymbols
test('Scan once: the scan screen before the first label scan has no "Weiteres TU"; the pop-up has it', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await PickLineScanScreen.expectNextTUButtonHiddenOnLabelLine();
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.expectNextTUButtonInQuantityBlock();
    await GetQuantityDialog.clickCancel();
    await PickingJobScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('TU opened with "Next TU", label scanned, count 5 (needs two source TUs) books ONE CU of 5 in the TU', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata, lot }));
    await PickLineScanScreen.openTU();
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
            // the TU holds exactly one CU of 5, with lot and best-before of the label
            tu1: { storages: { P1: '5 PCE' }, cus: [{ qty: '5 PCE', attributes: { 'Lot-Nummer': lot, 'HU_BestBeforeDate': BEST_BEFORE } }] },
            vhu1: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lot, 'HU_BestBeforeDate': BEST_BEFORE } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan once field starts empty (not 0) with the zero-as-initial-value setting; a Manual line still shows 0', async ({ page }) => {
    allureMeta('Scan once count');

    let sysconfigsBeforeTest = null;
    try {
        const masterdata = await createMasterdata({
            formatName: uniqueFormatName(),
            orders: [{ key: 'A', qty: 12 }, { key: 'B', qty: 12 }],
            // line 1 (buyer A): profile mode Scan once; line 2 (buyer B): record with Manual
            customers: [{ customer: 'custB', cuQtyEntryMode: MODE_MANUAL }],
            sysconfigs: { 'mobileui.frontend.qtyInput.useZeroAsInitialValue': 'Y' },
        });
        sysconfigsBeforeTest = masterdata.previousSysconfigs;
        await startJob(masterdata, { qtyToDeliver: 24 });

        await test.step('Scan once line: field empty after the label scan', async () => {
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            await PickLineScanScreen.openTU();
            await GetQuantityDialog.expectQtyEntered('');
            await GetQuantityDialog.clickCancel();
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Manual line: field shows 0', async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
            await PickingJobScreen.clickLineButton({ index: 2 });
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.expectQtyEntered(0);
            await GetQuantityDialog.clickCancel();
            await PickingJobScreen.waitForScreen();
        });
    } finally {
        if (sysconfigsBeforeTest && Object.keys(sysconfigsBeforeTest).length > 0) {
            await Backend.setSysconfigs(sysconfigsBeforeTest);
        }
    }
});

// noinspection JSUnusedLocalSymbols
test('Scan once: OK without an open TU is refused, nothing is booked; after "Next TU" the same booking succeeds', async ({ page }) => {
    allureMeta('Scan once needs an open TU');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });

    // no "Next TU", no GRAI scan
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(5);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_TU });

    await GetQuantityDialog.expectQtyTarget(10);  // line still open 10
    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } },
    });

    await test.step('open the TU, same booking succeeds', async () => {
        await PickLineScanScreen.openTU();
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
test('the CUs come from the "Kommissionieren aus" HU; the source pallet drops by the booked count, nothing is created on the fly', async ({ page }) => {
    allureMeta('Scan once source');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }], anonymousPickHUsOnTheFly: false });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
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
            [masterdata.handlingUnits.P1_HU.qrCode]: { huStatus: 'A', storages: { P1: `${SOURCE_QTY - 5} PCE` } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('open TU holding 3, count 5 (needs two source TUs) books ONE CU of 5 into it', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata, lot }));
    await PickLineScanScreen.openTU();

    await test.step('first booking: 3 into the TU (leaves 1 piece in the first source TU)', async () => {
        await GetQuantityDialog.typeQtyEntered(3);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 7 });
    });

    await test.step('second booking: 5 (1 + 4 from two source TUs), no "try again" error', async () => {
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
            tu1: { storages: { P1: '8 PCE' } },
            vhu1: { storages: { P1: '3 PCE' }, attributes: { 'Lot-Nummer': lot } },
            vhu2: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lot } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('a count smaller than the open quantity is not a rejection', async ({ page }) => {
    allureMeta('Scan once count');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.expectNoQtyRejected();   // no reject reason asked
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 6 }); // line open 6

    await GetQuantityDialog.clickCancel();
    await PickingJobScreen.waitForScreen();
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
test('the booking that reaches the open quantity finishes the line and goes back to the job screen', async ({ page }) => {
    allureMeta('Scan once line done');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();

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
test('a count above the open quantity is blocked, on a Manual line and on a Scan once line', async ({ page }) => {
    allureMeta('Over-pick refused');

    const formatName = uniqueFormatName();
    const masterdata = await createMasterdata({
        formatName,
        profileMode: null,
        orders: [{ key: 'A', qty: 12 }, { key: 'B', qty: 12 }],
        customers: [{ customer: 'custB', cuQtyEntryMode: MODE_SCAN_ONCE, cuLabelFormatName: formatName }],
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 24 });

    await test.step('Manual line (3 TUs open): 4 is refused by the validation', async () => {
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
        await scanLabel(labelOf({ masterdata }));
        await PickLineScanScreen.openTU();
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
test('on a line whose packing is a TU packing, a SECOND CU booking into the open TU is accepted', async ({ page }) => {
    allureMeta('Scan once on a TU-packed line');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await GetQuantityDialog.typeQtyEntered(1);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 9 });
    await test.step('second booking into the existing TU: no "Cannot pick TUs when TU pick target is set!"', async () => {
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

// noinspection JSUnusedLocalSymbols
test('Pallet target (Scan once): pallet + new TU: counts 3 and 2 book one CU each into the TU on the pallet; "Next TU" + count 4 (a full TU) books one CU of 4 in a second TU on the same pallet; "Unpack" of the 3 frees only that CU', async ({ page }) => {
    allureMeta('Scan once pallet target');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10 }], pickTo: ['LU_TU', 'TU'] });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });
    await setLUTarget({ lu: masterdata.packingInstructions.P1_20x4.luName });

    await test.step('counts 3 and 2: one CU each, in the same TU, the TU on the pallet', async () => {
        await scanLabel(labelOf({ masterdata, lot }));
        await PickLineScanScreen.openTU();
        await GetQuantityDialog.typeQtyEntered(3);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 7 });
        await GetQuantityDialog.typeQtyEntered(2);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 });
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                { qtyPicked: '3 PCE', vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false },
                                { qtyPicked: '2 PCE', vhu: 'vhu2', tu: 'tu1', lu: 'lu1', processed: false },
                            ]
                        }
                    }
                }
            },
            hus: {
                tu1: { storages: { P1: '5 PCE' } },
                vhu1: { storages: { P1: '3 PCE' }, attributes: { 'Lot-Nummer': lot } },
                vhu2: { storages: { P1: '2 PCE' } },
            },
        });
    });

    await test.step('"Next TU", count 4 (fills the TU of 4): one CU of 4 in a second TU on the same pallet, as without a pallet', async () => {
        await PickLineScanScreen.nextTU();
        await GetQuantityDialog.typeQtyEntered(4);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 1 });
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                { qtyPicked: '3 PCE', vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false },
                                { qtyPicked: '2 PCE', vhu: 'vhu2', tu: 'tu1', lu: 'lu1', processed: false },
                                { qtyPicked: '4 PCE', vhu: 'vhu3', tu: 'tu2', lu: 'lu1', processed: false },
                            ]
                        }
                    }
                }
            },
            hus: {
                tu1: { storages: { P1: '5 PCE' } },
                // the second TU holds exactly one CU of 4 (a real TU, not an aggregated TU)
                tu2: { storages: { P1: '4 PCE' }, cus: [{ qty: '4 PCE', attributes: { 'Lot-Nummer': lot } }] },
                vhu3: { storages: { P1: '4 PCE' } },
            },
        });
    });

    await test.step('"Unpack" of the 3: only that CU is freed, the CU of 2 stays in the TU on the pallet, the TU of 4 stays', async () => {
        await GetQuantityDialog.clickCancel(); // closes the quantity block, back on the line screen
        await unpackFirstBooking({ fromLineScreen: true });
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                { qtyPicked: '2 PCE', vhu: 'vhu2', tu: 'tu1', lu: 'lu1', processed: false },
                                { qtyPicked: '4 PCE', vhu: 'vhu3', tu: 'tu2', lu: 'lu1', processed: false },
                            ]
                        }
                    }
                }
            },
            hus: { tu1: { storages: { P1: '2 PCE' } }, tu2: { storages: { P1: '4 PCE' } } },
        });
    });
});
