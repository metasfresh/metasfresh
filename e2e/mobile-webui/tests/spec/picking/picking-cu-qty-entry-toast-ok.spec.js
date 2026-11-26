import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, book, createMasterdata, graiTuType, labelOf, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueFormatName } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * The green message "Neues TU geöffnet" / "New TU opened" (shown after "Weiteres TU" and after a GRAI scan) stays visible,
 * but the picker must be able to tap OK of the quantity pop-up at once: the message must not sit on OK, the count field, the target quantity or the header.
 */
const VIEWPORTS = [{ width: 393, height: 727 }, { width: 360, height: 640 }];

const SHOTS_DIR = process.env.TOAST_SHOTS_DIR;

/** True when the topmost element at the center of `locator` is inside the toasts: the toast would swallow a tap meant for it */
const isCoveredByToast = (locator) => locator.evaluate((el) => {
    const r = el.getBoundingClientRect();
    return !!document.elementFromPoint(r.x + r.width / 2, r.y + r.height / 2)?.closest('.Toastify');
});

/**
 * While the green message is shown, it covers no interactive or key element of the screen: OK, the count field, the target quantity, the app header;
 * a real tap on OK and on the count field succeeds (a trial click fails when another element is on top).
 * `name`: also saves a screenshot (when TOAST_SHOTS_DIR is set).
 */
const expectNothingCoveredWhileMessageShown = async (page, name) => {
    const toast = page.locator('.Toastify__toast--success');
    await expect(toast).toBeVisible();
    if (SHOTS_DIR) await page.waitForTimeout(600); // after the toast animation
    if (SHOTS_DIR) await page.screenshot({ path: `${SHOTS_DIR}/${name}.png` });

    const keyElements = {
        'OK': page.getByTestId('done-button'),
        'count field': page.locator('#qty-input'),
        'target quantity': page.getByTestId('qty-target'),
        'Weiteres TU': page.getByTestId('next-tu-button'),
        'app header': page.locator('#Back-button'),
    };
    for (const [label, locator] of Object.entries(keyElements)) {
        if (await locator.count() === 0 || !(await locator.first().isVisible())) continue; // the header sits behind the full-screen pop-up
        expect.soft(await isCoveredByToast(locator.first()), `${label} is not covered by the green message`).toBe(false);
    }
    await page.getByTestId('done-button').click({ trial: true, timeout: 1000 });
    await page.locator('#qty-input').click({ trial: true, timeout: 1000 });
    await expect(toast).toBeVisible();
};

for (const viewport of VIEWPORTS) {
    test.describe(`green "TU opened" message does not block OK, ${viewport.width}x${viewport.height}`, () => {
        test.use({ viewport });

        // noinspection JSUnusedLocalSymbols
        test('"Weiteres TU": the message covers nothing and OK is tappable at once', async ({ page }) => {
            const testName = 'weiteres-tu';
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 20 }] });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            await PickLineScanScreen.tapNextTUWithoutWaiting();
            await GetQuantityDialog.typeQtyEntered(5); // OK is enabled once a count is typed
            await expectNothingCoveredWhileMessageShown(page, `${testName}-${viewport.width}x${viewport.height}`);
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });

        // noinspection JSUnusedLocalSymbols
        test('GRAI scan in the pop-up: the message covers nothing and OK is tappable at once', async ({ page }) => {
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
            await expect(page.locator('.Toastify__toast--success')).toHaveCount(0, { timeout: 10000 }); // the message of the first TU is gone

            await PickLineScanScreen.rescanGrai(masterdata.packingInstructions.G1.grai);
            await GetQuantityDialog.typeQtyEntered(5);
            await expectNothingCoveredWhileMessageShown(page, `${testName}-${viewport.width}x${viewport.height}`);
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 10 });
        });
    });
}
