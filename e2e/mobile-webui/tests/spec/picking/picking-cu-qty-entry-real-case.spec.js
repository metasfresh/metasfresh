import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, book, labelOf, uniqueFormatName, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueLot } from "../../utils/cuQtyEntryCommon";
import { createScanEachMasterdata, MODE_SCAN_EACH, MSG_ALREADY_PICKED, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/** The real case, one mixed product job of two customers, source TUs of 4 */

// noinspection JSUnusedLocalSymbols
test('real case, buyer A 50 CU in 11 TUs (Scan once), buyer B 6 CU in 2 TUs with one duplicate warning (Scan each CU): 13 TUs, 56 CU, job completed', async ({ page }) => {
    // 13 TUs, 17 bookings and 2 completions: about 100 s on a quiet machine, over the default 120 s under load
    test.slow();
    allureMeta('Real case');

    const onceFormat = uniqueFormatName();
    const packFormat = uniqueFormatName();
    const masterdata = await createScanEachMasterdata({
        packFormatName: packFormat,
        profileMode: 'SCAN_ONCE_ENTER_QTY',
        orders: [{ key: 'A', qty: 50 }, { key: 'B', qty: 6, graiRequired: 'Y' }],
        customers: [{ customer: 'custB', cuQtyEntryMode: MODE_SCAN_EACH, cuLabelFormatName: packFormat }],
        formats: [{ name: onceFormat, withPackId: false }],
        packingInstructions: { G1: { tu: 'TU_G1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true } },
    });
    // the mapping is per (company prefix, asset type): the serial tells the TUs apart
    const [companyPrefix, assetType] = masterdata.packingInstructions.G1.grai.split('.');
    const serialBase = Date.now();
    const graiOfTU = (tu) => `${companyPrefix}.${assetType}.${serialBase + tu}`;
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 56 });

    await test.step('buyer A: 50 CU in 11 TUs', async () => {
        await openLine({ index: 1 });
        await PickLineScanScreen.openTU();                 // TU 1
        await scanLabel(labelOf({ masterdata, lot }));
        await book({ qty: 3, expectedQtyTarget: 47 });
        await PickLineScanScreen.rescanCULabel(labelOf({ masterdata, lot }));
        await PickLineScanScreen.nextTU();                  // TU 2
        await book({ qty: 5, expectedQtyTarget: 42 });
        for (let i = 0; i < 8; i++) {                        // TUs 3 - 10, alternating GRAI scan / "Next TU"
            if (i % 2 === 0) {
                await PickLineScanScreen.rescanGrai(graiOfTU(3 + i));
            } else {
                await PickLineScanScreen.nextTU();
            }
            await book({ qty: 5, expectedQtyTarget: 42 - 5 * (i + 1) });
        }
        await PickLineScanScreen.nextTU();                  // TU 11
        await GetQuantityDialog.typeQtyEntered(2);
        await GetQuantityDialog.clickDoneStayOpen();
        await PickingJobScreen.waitForScreen();
    });

    await test.step('buyer B: 6 CU in 2 GRAI TUs, pack ID 3 twice = one warning', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openScanEachLine({ index: 2, openTU: false });
        await PickLineScanScreen.scanGrai(graiOfTU(12));    // TU 12 = buyer B's TU 1
        await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions.G1.tuName);
        for (const packId of [1, 2, 3]) {
            await scanPack(packLabelOf({ masterdata, lot, packId }));
        }
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 3 }), MSG_ALREADY_PICKED(3));   // the one warning
        await PickLineScanScreen.scanGrai(graiOfTU(13));    // TU 13 = buyer B's TU 2
        for (const packId of [4, 5, 6]) {
            await scanPack(packLabelOf({ masterdata, lot, packId }));
        }
        await PickingJobScreen.waitForScreen();
    });

    await PickingJobScreen.complete();

    // one booking = one CU row: buyer A 3, 5, 8 x 5, 2 in TUs 1 - 11; buyer B 6 x 1 in TUs 12, 13 (3 each)
    const row = (qty, tu, cu) => ({ qtyPicked: `${qty} PCE`, vhu: `vhu${cu}`, tu: `tu${tu}`, processed: true });
    const buyerARows = [row(3, 1, 1), row(5, 2, 2), ...[3, 4, 5, 6, 7, 8, 9, 10].map((tu) => row(5, tu, tu)), row(2, 11, 11)];
    const buyerBRows = [12, 12, 12, 13, 13, 13].map((tu, idx) => row(1, tu, 12 + idx));
    const tu = (qty, grai) => ({ storages: { P1: `${qty} PCE` }, ...(grai !== undefined ? { attributes: { GRAI: grai } } : {}) });
    await Backend.expect({
        title: '13 TUs, 56 CU, job completed',
        pickings: { [pickingJobId]: { docStatus: 'Completed', shipmentSchedules: { P1: { qtyPicked: [...buyerARows, ...buyerBRows] } } } },
        hus: {
            tu1: tu(3, null), tu2: tu(5, null),
            tu3: tu(5, graiOfTU(3)), tu4: tu(5, null), tu5: tu(5, graiOfTU(5)), tu6: tu(5, null),
            tu7: tu(5, graiOfTU(7)), tu8: tu(5, null), tu9: tu(5, graiOfTU(9)), tu10: tu(5, null),
            tu11: tu(2, null),
            tu12: tu(3, graiOfTU(12)), tu13: tu(3, graiOfTU(13)),
        },
    });
});
