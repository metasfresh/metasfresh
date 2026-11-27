import { test } from "../../../playwright.config";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { SuccessToast } from "../../utils/components/SuccessToast";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, book, createMasterdata, graiTuType, labelOf, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueFormatName } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * The green message "Neues TU geöffnet" / "New TU opened" (shown after "Weiteres TU" and after a GRAI scan) stays visible,
 * but the picker must be able to tap OK of the quantity pop-up at once: the message must not sit on OK, the count field, the target quantity or Weiteres TU.
 */
const VIEWPORTS = [{ width: 393, height: 727 }, { width: 360, height: 640 }];

const SHOTS_DIR = process.env.TOAST_SHOTS_DIR;

/** The screenshot file of a check (only when TOAST_SHOTS_DIR is set) */
const shotPath = (name) => SHOTS_DIR ? `${SHOTS_DIR}/${name}.png` : undefined;

for (const viewport of VIEWPORTS) {
    test.describe(`green "TU opened" message does not block OK, ${viewport.width}x${viewport.height}`, () => {
        test.use({ viewport });

        // noinspection JSUnusedLocalSymbols
        test('"Weiteres TU": the message covers nothing and OK is tappable at once', async ({ page: _page }) => {
            const testName = 'weiteres-tu';
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 20 }] });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            await PickLineScanScreen.tapNextTUWithoutWaiting();
            await GetQuantityDialog.typeQtyEntered(5); // OK is enabled once a count is typed
            await SuccessToast.expectNothingCoveredWhileShown({ shotPath: shotPath(`${testName}-${viewport.width}x${viewport.height}`) });
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });

        // noinspection JSUnusedLocalSymbols
        test('GRAI scan in the pop-up: the message covers nothing and OK is tappable at once', async ({ page: _page }) => {
            const testName = 'grai';
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
                packingInstructions: graiTuType('G1'),
            });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            await PickLineScanScreen.openTU();
            await book({ qty: 5, expectedQtyTarget: 15 });
            await SuccessToast.expectCount(0, { timeout: 10000 }); // the message of the first TU is gone

            await PickLineScanScreen.rescanGrai(masterdata.packingInstructions.G1.grai);
            await GetQuantityDialog.typeQtyEntered(5);
            await SuccessToast.expectNothingCoveredWhileShown({ shotPath: shotPath(`${testName}-${viewport.width}x${viewport.height}`) });
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 10 });
        });

        // noinspection JSUnusedLocalSymbols
        test('"Weiteres TU" three times in a row: one message, nothing covered', async ({ page: _page }) => {
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 20 }] });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            for (let i = 0; i < 3; i++) {
                await PickLineScanScreen.openTU();
            }
            await GetQuantityDialog.typeQtyEntered(5);
            await SuccessToast.expectOneCoveringNothing({ shotPath: shotPath(`weiteres-tu-x3-${viewport.width}x${viewport.height}`) });
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });

        // noinspection JSUnusedLocalSymbols
        test('three GRAI scans in a row: one message, nothing covered', async ({ page: _page }) => {
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
                packingInstructions: { ...graiTuType('G1'), ...graiTuType('G2'), ...graiTuType('G3') },
            });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            await PickLineScanScreen.openTU();
            for (const key of ['G1', 'G2', 'G3']) {
                await PickLineScanScreen.rescanGrai(masterdata.packingInstructions[key].grai);
                await PickLineScanScreen.expectOpenTUCaption(masterdata.packingInstructions[key].tuName);
            }
            await GetQuantityDialog.typeQtyEntered(5);
            await SuccessToast.expectOneCoveringNothing({ shotPath: shotPath(`grai-x3-${viewport.width}x${viewport.height}`) });
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });
    });
}
