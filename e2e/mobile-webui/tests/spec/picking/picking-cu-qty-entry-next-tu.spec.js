import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { expectErrorToast } from "../../utils/common";
import { ErrorToast } from "../../utils/dialogs/ErrorToast";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { PickingGraiScanPanel } from "../../utils/screens/picking/PickingGraiScanPanel";
import { SelectPickTargetTUScreen } from "../../utils/screens/picking/SelectPickTargetTUScreen";
import { PickGraiScreen } from "../../utils/screens/picking/PickGraiScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from "../../utils/screens/huManager/HUManagerScreen";
import { GRAIScreen } from "../../utils/screens/huManager/GRAIScreen";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { allureMeta, book, graiTuType, createMasterdata, restoreSysconfigsAfterEach, GRAI_8003_PREFIX, labelOf, MSG_NO_OPEN_TU, uniqueFormatName, openLine, qtyPickedRow, scanLabel, startJob, uniqueLot } from "../../utils/cuQtyEntryCommon";
import { MESSAGES } from "../../utils/cuQtyEntryMessages";
import { delayRequests, delayResponses, hangRequests } from "../../utils/network";
import { createScanEachMasterdata, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/** CU quantity entry */
const MSG_NO_CAPACITY = MESSAGES.noCapacity.en;
const MSG_GRAI_COUNT = MESSAGES.graiCount.en;

// noinspection JSUnusedLocalSymbols
test('GRAI scan with a TU open (customer GRAI not required): the open TU is closed with its 5, the next TU carries the GRAI', async ({ page }) => {
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await book({ qty: 5, expectedQtyTarget: 10 });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 5, tu: 'tu2' })] }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },   // the closed TU: closed with 5, no GRAI
            tu2: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: grai } },   // the new TU: the scanned GRAI, 5
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAI scan with a slow request (count typed and OK pressed before the crate is opened): the count is booked into the new TU', async ({ page }) => {
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    // the picker scans the next crate and types its count at once; the crate's answer comes later
    const restoreTargetSpeed = await delayRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST', delayMs: 4000 });
    await PickLineScanScreen.rescanGrai(grai);
    await book({ qty: 5, expectedQtyTarget: 10 });
    await restoreTargetSpeed();

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 5, tu: 'tu2' })] }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAI scan with a slow answer (crate opened, its answer late; count typed and OK pressed meanwhile): the open quantity shown stays the booked one', async ({ page }) => {
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    // the server opens the crate at once, its answer reaches the handheld late (it still says 15 open)
    const restoreAnswerSpeed = await delayResponses({ urlPattern: /\/picking\/job\/[^/]+\/target\/tu/, method: 'POST', delayMs: 4000 });
    await PickLineScanScreen.rescanGrai(grai);
    await book({ qty: 5, expectedQtyTarget: 10 });
    await restoreAnswerSpeed();
    await GetQuantityDialog.expectQtyTarget(10);

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 5, tu: 'tu2' })] }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAI of a TU type without capacity for the product is refused; the open TU stays and takes the next booking', async ({ page }) => {
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        // the TU type of this GRAI has a capacity row for P2 only, none for the line's product P1
        products: { P2: { price: 1 } },
        packingInstructions: { GN: { tu: 'TU_GN', product: 'P2', qtyCUsPerTU: 4, graiMapping: true } },
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    await PickLineScanScreen.rescanGraiRefused(masterdata.packingInstructions.GN.grai, MSG_NO_CAPACITY);

    // the TU is unchanged: the next booking goes into it, nothing was booked by the refused scan
    await book({ qty: 3, expectedQtyTarget: 12 });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 3, tu: 'tu1' })] }
                }
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '8 PCE' }, attributes: { GRAI: null } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAI of a TU type without capacity, slow refusal (count typed and OK pressed before it): one error, nothing is booked, the typed count stays for the open TU', async ({ page }) => {
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        // the TU type of this GRAI has a capacity row for P2 only, none for the line's product P1
        products: { P2: { price: 1 } },
        packingInstructions: { GN: { tu: 'TU_GN', product: 'P2', qtyCUsPerTU: 4, graiMapping: true } },
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    const restoreTargetSpeed = await delayRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST', delayMs: 4000 });
    // one error: the crate's refusal; the count waiting for that crate is not booked anywhere
    await expectErrorToast(
        'slow GRAI refusal, OK pressed before it',
        async () => {
            await PickLineScanScreen.rescanGrai(masterdata.packingInstructions.GN.grai);
            await GetQuantityDialog.typeQtyEntered(3);
            await GetQuantityDialog.tapDone();
        },
        ({ textContent }) => expect(textContent).toContain(MSG_NO_CAPACITY)
    );
    await restoreTargetSpeed();
    await GetQuantityDialog.waitForDialog();

    // the refused crate booked nothing; the typed count is still there and OK books it into the open TU
    await GetQuantityDialog.expectQtyTarget(15);
    await GetQuantityDialog.expectQtyEntered(3);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 12 });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 3, tu: 'tu1' })] }
                }
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '8 PCE' }, attributes: { GRAI: null } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('"Next TU" button with a slow request (count typed and OK pressed before the TU is opened): the count is booked into the new TU', async ({ page }) => {
    allureMeta('Next TU button');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    // the button closes the open TU and opens the next one: two slow requests; the picker types the count at once
    const restoreTargetSpeed = await delayRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST', delayMs: 3000 });
    await PickLineScanScreen.tapNextTUWithoutWaiting();
    await book({ qty: 3, expectedQtyTarget: 12 });
    await restoreTargetSpeed();
    await GetQuantityDialog.expectQtyTarget(12);

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 3, tu: 'tu2' })] }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '3 PCE' }, attributes: { GRAI: null } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('GRAI scan whose request hangs (no answer at all): the request times out, one error, nothing is booked, the typed count stays for the open TU', async ({ page }) => {
    test.setTimeout(120000);   // the request timeout of the picking target calls is 20 s
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    const restoreTargetSpeed = await hangRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST' });
    await expectErrorToast(
        'hung GRAI opening, OK pressed meanwhile',
        async () => {
            await PickLineScanScreen.rescanGrai(grai);
            await GetQuantityDialog.typeQtyEntered(3);
            await GetQuantityDialog.tapDone();
            await ErrorToast.waitToPopup(undefined, 40000);   // the request timeout (20 s) ends the hung opening
        },
        ({ textContent }) => expect(textContent).toContain('Connection error')   // what the app shows for a timed-out request
    );
    await restoreTargetSpeed();
    await GetQuantityDialog.waitForDialog();

    // nothing was booked; the typed count is still there and OK books it into the open TU
    await GetQuantityDialog.expectQtyTarget(15);
    await GetQuantityDialog.expectQtyEntered(3);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 12 });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 3, tu: 'tu1' })] }
                }
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '8 PCE' }, attributes: { GRAI: null } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('"Next TU" button whose request hangs (no answer at all): the request times out, one error, nothing is booked, the typed count stays for the open TU', async ({ page }) => {
    test.setTimeout(120000);   // the request timeout of the picking target calls is 20 s
    allureMeta('Next TU button');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    // the button first closes the open TU: that request hangs
    const restoreTargetSpeed = await hangRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST' });
    await expectErrorToast(
        'hung Next TU button, OK pressed meanwhile',
        async () => {
            await PickLineScanScreen.tapNextTUWithoutWaiting();
            await GetQuantityDialog.typeQtyEntered(3);
            await GetQuantityDialog.tapDone();
            await ErrorToast.waitToPopup(undefined, 40000);   // the request timeout (20 s) ends the hung opening
        },
        ({ textContent }) => expect(textContent).toContain('Connection error')
    );
    await restoreTargetSpeed();
    await GetQuantityDialog.waitForDialog();

    // nothing was booked; the open TU is still the one the job shows; the typed count books into it
    await GetQuantityDialog.expectQtyTarget(15);
    await GetQuantityDialog.expectQtyEntered(3);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 12 });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' }), qtyPickedRow({ qty: 3, tu: 'tu1' })] }
                }
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '8 PCE' }, attributes: { GRAI: null } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('count confirmed first (OK pressed, its answer late), crate GRAI scanned meanwhile: the count stays in the previous TU, the new crate stays open and takes the next booking', async ({ page }) => {
    allureMeta('Next TU by GRAI');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5, expectedQtyTarget: 15 });

    // OK is pressed, the server books at once but its answer is late; the next crate is scanned meanwhile
    const restoreEventSpeed = await delayResponses({ urlPattern: /\/picking\/event/, method: 'POST', delayMs: 4000 });
    await GetQuantityDialog.typeQtyEntered(5);
    await GetQuantityDialog.tapDone();
    await PickLineScanScreen.rescanGrai(grai);
    await GetQuantityDialog.expectQtyTarget(10);
    await restoreEventSpeed();
    // the late answer of the booking must not show the previous TU as open again
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await book({ qty: 3, expectedQtyTarget: 7 });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            qtyPickedRow({ qty: 5, tu: 'tu1' }),
                            qtyPickedRow({ qty: 5, tu: 'tu1' }),
                            qtyPickedRow({ qty: 3, tu: 'tu2' }),
                        ]
                    }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '10 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '3 PCE' }, attributes: { GRAI: grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('"Next TU" opens a TU of the line\'s TU type in one tap (no "Gebinde" list); a GRAI scan after it opens one more TU, the button TU keeps no GRAI', async ({ page }) => {
    allureMeta('Next TU button');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 20 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await PickLineScanScreen.expectNextTUButtonInQuantityBlock();   // the button sits next to OK
    await book({ qty: 5, expectedQtyTarget: 15 });

    await test.step('one tap opens the next TU, no TU list', async () => {
        await PickLineScanScreen.nextTU();
        await SelectPickTargetTUScreen.expectNotShown();
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing, not the GRAI type G1
        await GetQuantityDialog.waitForDialog();
        await book({ qty: 3, expectedQtyTarget: 12 });
    });

    await test.step('a GRAI scan opens one more TU with that GRAI', async () => {
        await PickLineScanScreen.rescanGrai(grai);
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
        await book({ qty: 4, expectedQtyTarget: 8 });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            qtyPickedRow({ qty: 5, tu: 'tu1' }),
                            qtyPickedRow({ qty: 3, tu: 'tu2' }),
                            qtyPickedRow({ qty: 4, tu: 'tu3' }),
                        ]
                    }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '5 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '3 PCE' }, attributes: { GRAI: null } },   // the button's TU keeps no GRAI
            tu3: { qtyTUs: 1, storages: { P1: '4 PCE' }, attributes: { GRAI: grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan once: customer GRAI required, no pick-time GRAI screen: first booking without a TU is refused, after "Next TU" it is booked', async ({ page }) => {
    allureMeta('No pick-time GRAI screen');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 10, graiRequired: 'Y' }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 10 });
    await openLine({ index: 1 });

    await test.step('(i) booking with no TU open is refused, no GRAI screen', async () => {
        await scanLabel(labelOf({ masterdata }));
        await GetQuantityDialog.typeQtyEntered(5);
        await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_TU });
        await PickGraiScreen.expectNotShown();
        await Backend.expect({ pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } } });
    });

    await test.step('(ii) "Next TU", then the booking goes into the TU, no GRAI screen', async () => {
        await PickLineScanScreen.nextTU();
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 5 });
        await PickGraiScreen.expectNotShown();
    });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' })] } } } },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '5 PCE' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('mixed job (customer GRAI required + customer not required): the GRAI customer\'s TU opened by GRAI scan, job completes', async ({ page }) => {
    allureMeta('Complete a mixed job');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'Y', qty: 8, graiRequired: 'Y' }, { key: 'N', qty: 4, graiRequired: 'N' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });

    await test.step('line of the GRAI customer: TU opened by GRAI scan on the scan step', async () => {
        await openLine({ index: 1 });
        await PickLineScanScreen.scanGrai(grai);
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
        await scanLabel(labelOf({ masterdata }));
        await book({ qty: 8 });      // line done: back to the job screen
        await PickingJobScreen.waitForScreen();
    });

    await test.step('line of the other customer: TU opened with "Next TU"', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openLine({ index: 2 });
        await scanLabel(labelOf({ masterdata }));
        await PickLineScanScreen.openTU();
        await book({ qty: 4 });
        await PickingJobScreen.waitForScreen();
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 8, tu: 'tu1' }), qtyPickedRow({ qty: 4, tu: 'tu2' })] } }
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

/** "GRAI scannen" in the HU Manager for each given TU (alias of a picked TU, `tuN`) */
const addGraiToTUsInHUManager = async ({ masterdata, tus }) => {
    const serialBase = Date.now();
    await PickingJobScreen.goBack();
    await PickingJobsListScreen.goBack();
    // one HU Manager visit per TU: its scan field is gone while an HU is shown
    for (const [idx, tu] of tus.entries()) {
        const tuQRCode = await Backend.getHUQRCodeByIdentifier({ identifier: tu });
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
test('customer GRAI required, TU opened with "Next TU": "Fertigstellen" is refused; after "GRAI scannen" in the HU Manager the job completes', async ({ page }) => {
    allureMeta('GRAI customer, TU without GRAI');

    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 5, graiRequired: 'Y' }] });
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 5 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await PickLineScanScreen.openTU();
    await book({ qty: 5 });
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                docStatus: 'Drafted',
                shipmentSchedules: { P1: { qtyPicked: [qtyPickedRow({ qty: 5, tu: 'tu1' })] } },
            }
        },
        hus: { tu1: { qtyTUs: 1, storages: { P1: '5 PCE' } } },
    });

    // GRAI on the TU in the HU Manager, then back in the job: it completes
    await addGraiToTUsInHUManager({ masterdata, tus: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

/** Expectation "every picked HU is a CU inside a TU of a TU packing, no loose CU" */
const expectEveryPickedHUIsACUInATU = async ({ pickingJobId, tus, docStatus }) => {
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                ...(docStatus ? { docStatus } : {}),
                shipmentSchedules: {
                    P1: { qtyPicked: tus.map(({ qty }, idx) => qtyPickedRow({ qty, tu: `tu${idx + 1}` })) }
                }
            }
        },
        hus: Object.fromEntries(tus.flatMap(({ qty, grai }, idx) => [
            [`tu${idx + 1}`, { qtyTUs: 1, storages: { P1: `${qty} PCE` }, cus: [{ qty: `${qty} PCE` }], ...(grai !== undefined ? { attributes: { GRAI: grai } } : {}) }],
        ])),
    });
};

// noinspection JSUnusedLocalSymbols
test('line with a TU packing "TU x 4 Stk" (customer GRAI required): first booking refused, "Next TU" opens TU 1, a GRAI scan opens the next TU, "Fertigstellen" needs a GRAI on every TU', async ({ page }) => {
    allureMeta('Open TU first, TU packing line');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 8, graiRequired: 'Y' }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 8 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_TU });
    await PickGraiScreen.expectNotShown();
    await GetQuantityDialog.expectQtyTarget(8);

    await PickLineScanScreen.nextTU();
    await SelectPickTargetTUScreen.expectNotShown();   // the line's packing names a TU type: one tap, no list
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 4 });
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await GetQuantityDialog.clickDoneStayOpen();   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', tus: [{ qty: 4, grai: null }, { qty: 4, grai: grai }] });
    await addGraiToTUsInHUManager({ masterdata, tus: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('line with an unlimited-capacity packing that names a TU type: "Next TU" opens no "Gebinde" list, a GRAI scan opens the next TU, "Fertigstellen" needs a GRAI on every TU', async ({ page }) => {
    allureMeta('Open TU first, unlimited capacity packing');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 8, graiRequired: 'Y', piItemProduct: 'TU_INF' }],
        packingInstructions: { INF: { tu: 'TU_INF', product: 'P1' }, ...graiTuType('G1') },
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 8 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_TU });
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.nextTU();
    await SelectPickTargetTUScreen.expectNotShown();
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.INF.tuName);   // the TU type of the line's packing
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 4 });
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.rescanGrai(grai);
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await GetQuantityDialog.clickDoneStayOpen();   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', tus: [{ qty: 4, grai: null }, { qty: 4, grai: grai }] });
    await addGraiToTUsInHUManager({ masterdata, tus: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('line without a TU type: first booking refused, "Next TU" opens the "Gebinde" list (again after a booking, no close screen), a GRAI scan opens the next TU', async ({ page }) => {
    allureMeta('Open TU first, line without a TU type');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 12, graiRequired: 'Y', piItemProduct: null }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const tuName = masterdata.packingInstructions.P1_20x4.tuName;
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await GetQuantityDialog.clickDoneRefused({ expectedError: MSG_NO_OPEN_TU });
    await PickGraiScreen.expectNotShown();

    await test.step('"Next TU" opens the TU list, the picker chooses the TU type', async () => {
        await PickLineScanScreen.nextTU();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        // the choice leads back to the scan screen with the TU open, the scanned label and the entered count kept (as after a GRAI scan): no new label scan
        await PickLineScanScreen.waitForScreen();
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(4);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 8 });
        await PickGraiScreen.expectNotShown();
    });

    await test.step('"Next TU" again: the list opens directly, the first TU closed without a close screen', async () => {
        await PickLineScanScreen.nextTU();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        await PickLineScanScreen.waitForScreen();   // the label of the first TU is kept: no new label scan
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(4);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 4 });
        await PickGraiScreen.expectNotShown();
    });

    await test.step('a GRAI scan opens the next TU', async () => {
        await PickLineScanScreen.rescanGrai(grai);
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
        await GetQuantityDialog.clickDoneStayOpen();
        await PickGraiScreen.expectNotShown();
        await PickingJobScreen.waitForScreen(); // the scan screen was entered from the job screen (next=pickingJob), also after the TU list: the done line leads back there
    });

    await test.step('"Fertigstellen" is refused while TUs without GRAI exist, and completes once every TU has one', async () => {
        await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
        await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', tus: [{ qty: 4, grai: null }, { qty: 4, grai: null }, { qty: 4, grai: grai }] });
        await addGraiToTUsInHUManager({ masterdata, tus: ['tu1', 'tu2'] });
        await PickingJobScreen.complete();
        await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
    });
});

// noinspection JSUnusedLocalSymbols
test('line without a TU type: a GRAI scanned on the "Gebinde" list opens that TU and leads back to the scan screen with the label and the count kept', async ({ page }) => {
    allureMeta('Open TU first, line without a TU type');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 12, graiRequired: 'Y', piItemProduct: null }],
        packingInstructions: graiTuType('G1'),
    });
    const grai = masterdata.packingInstructions.G1.grai;
    await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);
    await PickLineScanScreen.nextTU();
    await SelectPickTargetTUScreen.waitForScreen();
    await PickingGraiScanPanel.scanGrai({ graiString: grai });
    await PickLineScanScreen.waitForScreen();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectQtyEntered(4);
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 8 });
});

// noinspection JSUnusedLocalSymbols
test('line without a TU type: the count kept for the "Gebinde" trip is offered for the first booking only, the next round offers the normal count capped by the open quantity', async ({ page }) => {
    allureMeta('Open TU first, line without a TU type');

    const masterdata = await createMasterdata({
        formatName: uniqueFormatName(),
        orders: [{ key: 'A', qty: 6, graiRequired: 'Y', piItemProduct: null }],
        packingInstructions: graiTuType('G1'),
    });
    const tuName = masterdata.packingInstructions.P1_20x4.tuName;
    await startJob(masterdata, { qtyToDeliver: 6 });
    await openLine({ index: 1 });
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.typeQtyEntered(4);

    await test.step('"Next TU", the picker chooses the TU type, back with the kept count: the first booking takes it', async () => {
        await PickLineScanScreen.nextTU();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        await PickLineScanScreen.waitForScreen();
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(4);
        await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 2 });
    });

    await test.step('the next round offers the last typed count capped by the open quantity (2), not the kept count (4)', async () => {
        await GetQuantityDialog.expectQtyEntered(2);
        await GetQuantityDialog.clickDoneStayOpen();   // the line is done: back to the job screen
        await PickingJobScreen.waitForScreen();
    });
});

/** The same three line packings in Scan each CU */
const scanEachMasterdataOf = async ({ orders, packingInstructions }) =>
    await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders, packingInstructions });

// noinspection JSUnusedLocalSymbols
test('Scan each CU: GRAI scan with a slow request, a CU label scanned right after: the CU is booked into the new TU', async ({ page }) => {
    allureMeta('Scan each CU open TU first, TU packing line');

    const masterdata = await scanEachMasterdataOf({ orders: [{ key: 'A', qty: 3, graiRequired: 'N' }], packingInstructions: graiTuType('G1') });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1 });
    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));

    const restoreTargetSpeed = await delayRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST', delayMs: 4000 });
    // both scans end with Enter (the release reader buffers consecutive scans); neither waits for the crate
    await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: grai });
    await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: packLabelOf({ masterdata, lot, packId: 2 }) });
    await restoreTargetSpeed();
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [qtyPickedRow({ qty: 1, tu: 'tu1' }), qtyPickedRow({ qty: 1, tu: 'tu2' })] }
                }
            }
        },
        hus: {
            tu1: { qtyTUs: 1, storages: { P1: '1 PCE' }, attributes: { GRAI: null } },
            tu2: { qtyTUs: 1, storages: { P1: '1 PCE' }, attributes: { GRAI: grai } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line with a TU packing "TU x 4 Stk" (customer GRAI required): first scan refused, "Next TU" opens TU 1, a GRAI scan opens the next TU, "Fertigstellen" needs a GRAI on every TU', async ({ page }) => {
    allureMeta('Scan each CU open TU first, TU packing line');

    const masterdata = await scanEachMasterdataOf({ orders: [{ key: 'A', qty: 2, graiRequired: 'Y' }], packingInstructions: graiTuType('G1') });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_NO_OPEN_TU);
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.nextTU();
    await SelectPickTargetTUScreen.expectNotShown();   // the line's packing names a TU type: one tap, no list
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.P1_20x4.tuName);   // the TU type of the line's packing
    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.scanGrai(grai);
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', tus: [{ qty: 1, grai: null }, { qty: 1, grai: grai }] });
    await addGraiToTUsInHUManager({ masterdata, tus: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line with an unlimited-capacity packing that names a TU type: "Next TU" opens no "Gebinde" list, a GRAI scan opens the next TU, "Fertigstellen" needs a GRAI on every TU', async ({ page }) => {
    allureMeta('Scan each CU open TU first, unlimited capacity packing');

    const masterdata = await scanEachMasterdataOf({
        orders: [{ key: 'A', qty: 2, graiRequired: 'Y', piItemProduct: 'TU_INF' }],
        packingInstructions: { INF: { tu: 'TU_INF', product: 'P1' }, ...graiTuType('G1') },
    });
    const grai = masterdata.packingInstructions.G1.grai;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 2 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_NO_OPEN_TU);
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.nextTU();
    await SelectPickTargetTUScreen.expectNotShown();
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.INF.tuName);   // the TU type of the line's packing
    await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
    await PickGraiScreen.expectNotShown();

    await PickLineScanScreen.scanGrai(grai);
    await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
    await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));   // the line is done: back to the job screen
    await PickGraiScreen.expectNotShown();
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
    await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', tus: [{ qty: 1, grai: null }, { qty: 1, grai: grai }] });
    await addGraiToTUsInHUManager({ masterdata, tus: ['tu1'] });
    await PickingJobScreen.complete();
    await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
});

// noinspection JSUnusedLocalSymbols
test('Scan each CU: line without a TU type: first scan refused, "Next TU" opens the "Gebinde" list (again after a booking), a GRAI scan opens the next TU, "Fertigstellen" needs a GRAI on every TU', async ({ page }) => {
    allureMeta('Scan each CU open TU first, line without a TU type');

    const masterdata = await scanEachMasterdataOf({ orders: [{ key: 'A', qty: 3, graiRequired: 'Y', piItemProduct: null }], packingInstructions: graiTuType('G1') });
    const grai = masterdata.packingInstructions.G1.grai;
    const tuName = masterdata.packingInstructions.P1_20x4.tuName;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1, openTU: false });
    await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 1 }), MSG_NO_OPEN_TU);
    await PickGraiScreen.expectNotShown();

    await test.step('"Next TU" opens the TU list, the picker chooses the TU type and scans the first CU', async () => {
        await PickLineScanScreen.nextTU();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        // the choice leads back to the line screen with the TU open; "Pack HU" goes on to the scan screen
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.tapPickHUButton();
        await PickLineScanScreen.waitForScreen();
        await scanPack(packLabelOf({ masterdata, lot, packId: 1 }));
        await PickGraiScreen.expectNotShown();
    });

    await test.step('"Next TU" again: the list opens directly, the picker chooses the TU type and scans the second CU', async () => {
        await PickLineScanScreen.nextTU();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.tapPickHUButton();
        await PickLineScanScreen.waitForScreen();
        await scanPack(packLabelOf({ masterdata, lot, packId: 2 }));
        await PickGraiScreen.expectNotShown();
    });

    await test.step('a GRAI scan opens the next TU, the third CU finishes the line', async () => {
        await PickLineScanScreen.scanGrai(grai);
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
        await scanPack(packLabelOf({ masterdata, lot, packId: 3 }));
        await PickGraiScreen.expectNotShown();
        await PickingJobLineScreen.waitForScreen();   // entered from the line screen: the done line leads back there
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.waitForScreen();
    });

    await test.step('"Fertigstellen" is refused while TUs without GRAI exist, and completes once every TU has one', async () => {
        await PickingJobScreen.completeRefused({ expectedError: MSG_GRAI_COUNT });
        await expectEveryPickedHUIsACUInATU({ pickingJobId, docStatus: 'Drafted', tus: [{ qty: 1, grai: null }, { qty: 1, grai: null }, { qty: 1, grai: grai }] });
        await addGraiToTUsInHUManager({ masterdata, tus: ['tu1', 'tu2'] });
        await PickingJobScreen.complete();
        await Backend.expect({ pickings: { [pickingJobId]: { docStatus: 'Completed' } } });
    });
});
