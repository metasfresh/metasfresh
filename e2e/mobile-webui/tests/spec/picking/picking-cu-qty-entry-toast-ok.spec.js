import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, book, createMasterdata, graiTuType, labelOf, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueFormatName } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * The green message "Neues TU geöffnet" / "New TU opened" (shown after "Weiteres TU" and after a GRAI scan) stays visible,
 * but the picker must be able to tap OK of the quantity pop-up at once: the message must not sit on OK or swallow its tap.
 */
const VIEWPORTS = [{ width: 393, height: 727 }, { width: 360, height: 640 }];

/** Passes only while the green message is shown (so the check is not vacuous) and OK receives the tap: a trial click fails when another element is on top of OK */
const expectOKTappableWhileMessageShown = async (page) => {
    await expect(page.locator('.Toastify__toast--success')).toBeVisible();
    await page.getByTestId('done-button').click({ trial: true, timeout: 1000 });
    await expect(page.locator('.Toastify__toast--success')).toBeVisible();
};

for (const viewport of VIEWPORTS) {
    test.describe(`green "TU opened" message does not block OK, ${viewport.width}x${viewport.height}`, () => {
        test.use({ viewport });

        // noinspection JSUnusedLocalSymbols
        test('"Weiteres TU": OK is tappable at once while the message is shown', async ({ page }) => {
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 20 }] });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            await PickLineScanScreen.tapNextTUWithoutWaiting();
            await GetQuantityDialog.typeQtyEntered(5); // OK is enabled once a count is typed
            await expectOKTappableWhileMessageShown(page);
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });

        // noinspection JSUnusedLocalSymbols
        test('GRAI scan in the pop-up: OK is tappable at once while the message is shown', async ({ page }) => {
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
            await expect(page.locator('.Toastify__toast--success')).toHaveCount(0, { timeout: 10000 }); // the message of the first TU is gone

            await PickLineScanScreen.rescanGrai(masterdata.packingInstructions.G1.grai);
            await GetQuantityDialog.typeQtyEntered(5);
            await expectOKTappableWhileMessageShown(page);
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 10 });
        });
    });
}
