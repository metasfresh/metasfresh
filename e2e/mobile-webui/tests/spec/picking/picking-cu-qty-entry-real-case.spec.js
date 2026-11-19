import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, book, GRAI_8003_PREFIX, labelOf, newFormatName, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueLot } from "../../utils/cuQtyEntryScanOnce";
import { createScanEachMasterdata, MODE_SCAN_EACH, MSG_ALREADY_PICKED, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/**
 * TC20, the real case (AC17), one mixed product job of two customers, source boxes of 4:
 *  - buyer A (`C_BPartner.GRAIRequired` N, profile Scan once + format without pack-ID part): 50 CU = 3, 5, 8 x 5 (alternating
 *    GRAI scan / "Next box"), 2; box 1 opened by "Next box"; 11 boxes, 50 CU.
 *  - buyer B (GRAIRequired Y, record Scan each CU + format with pack-ID part): 6 CU, pack ID 3 scanned twice (one warning); 2 boxes.
 * Expected: 13 boxes, 56 CU, 1 warning, `M_Picking_Job.DocStatus` CO.
 *
 * Needs the pack-ID part type of the label-reader change (merged on soft_panda_hotfix).
 */

// noinspection JSUnusedLocalSymbols
test('TC20: real case, buyer A 50 CU in 11 boxes (Scan once), buyer B 6 CU in 2 boxes with one duplicate warning (Scan each CU): 13 boxes, 56 CU, job completed', async ({ page }) => {
    allureMeta('Real case');

    const onceFormat = newFormatName();
    const packFormat = newFormatName();
    // profile = Scan once + format without pack-ID part (buyer A, no record); buyer B = record Scan each CU + the pack-ID format
    const masterdata = await createScanEachMasterdata({
        packFormatName: packFormat,
        profileMode: 'SCAN_ONCE_ENTER_QTY',
        orders: [{ key: 'A', qty: 50 }, { key: 'B', qty: 6, graiRequired: 'Y' }],
        customers: [{ customer: 'custB', cuQtyEntryMode: MODE_SCAN_EACH, cuLabelFormatName: packFormat }],
        formats: [{ name: onceFormat, withPackId: false }],
    });
    const lot = uniqueLot();
    const { pickingJobId } = await startJob(masterdata, { qtyToDeliver: 56 });

    await test.step('buyer A: 50 CU in 11 boxes', async () => {
        await openLine({ index: 1 });
        await PickLineScanScreen.openBox();                 // box 1
        await scanLabel(labelOf({ masterdata, lot }));
        await book({ qty: 3, expectedQtyTarget: 47 });
        await PickLineScanScreen.rescanCULabel(labelOf({ masterdata, lot }));
        await PickLineScanScreen.nextBox();                  // box 2
        await book({ qty: 5, expectedQtyTarget: 42 });
        for (let i = 0; i < 8; i++) {                        // boxes 3 - 10, alternating GRAI scan / "Next box"
            if (i % 2 === 0) {
                await PickLineScanScreen.rescanGrai(`${GRAI_8003_PREFIX}${i}`);
            } else {
                await PickLineScanScreen.nextBox();
            }
            await book({ qty: 5, expectedQtyTarget: 42 - 5 * (i + 1) });
        }
        await PickLineScanScreen.nextBox();                  // box 11
        await GetQuantityDialog.typeQtyEntered(2);
        await GetQuantityDialog.clickDoneStayOpen();
        await PickingJobScreen.waitForScreen();
    });

    await test.step('buyer B: 6 CU, pack ID 3 twice = one warning', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
        await openScanEachLine({ index: 2 });
        for (const packId of [1, 2, 3]) {
            await scanPack(packLabelOf({ masterdata, lot, packId }));
        }
        await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, packId: 3 }), MSG_ALREADY_PICKED(3));   // the one warning
        await PickLineScanScreen.nextBox();                  // box 2 of buyer B
        for (const packId of [4, 5, 6]) {
            await scanPack(packLabelOf({ masterdata, lot, packId }));
        }
        await PickingJobScreen.waitForScreen();
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        title: 'TC20: 13 boxes, 56 CU, job completed',
        pickings: { [pickingJobId]: { docStatus: 'Completed' } },
    });
});
