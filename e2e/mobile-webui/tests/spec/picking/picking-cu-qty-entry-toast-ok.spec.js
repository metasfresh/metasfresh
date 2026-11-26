import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, book, createMasterdata, graiTuType, labelOf, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueFormatName } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * The green message "Neues TU geöffnet" / "New TU opened" (shown after "Weiteres TU" and after a GRAI scan) stays visible,
 * but the picker must be able to tap OK of the quantity pop-up at once: the message must not sit on OK, the count field, the target quantity or Weiteres TU.
 */
const VIEWPORTS = [{ width: 393, height: 727 }, { width: 360, height: 640 }];

const SHOTS_DIR = process.env.TOAST_SHOTS_DIR;

/** True when the topmost element at the center of `locator` is inside the toasts: the toast would swallow a tap meant for it */
const isCoveredByToast = (locator) => locator.evaluate((el) => {
    const r = el.getBoundingClientRect();
    return !!document.elementFromPoint(r.x + r.width / 2, r.y + r.height / 2)?.closest('.Toastify');
});

/**
 * While the green message is shown, it covers no interactive or key element of the screen: OK, the count field, the target quantity, Weiteres TU, the footer Back button (when shown);
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
    };
    for (const [label, locator] of Object.entries(keyElements)) {
        await expect(locator, `${label} is shown`).toBeVisible();
        expect.soft(await isCoveredByToast(locator.first()), `${label} is not covered by the green message`).toBe(false);
    }
    // the footer "Back" button, only when it is on screen (the pop-up may stand over it)
    const back = page.locator('#Back-button');
    if (await back.count() > 0 && await back.first().isVisible()) {
        expect.soft(await isCoveredByToast(back.first()), 'footer Back button is not covered by the green message').toBe(false);
    }
    await page.getByTestId('done-button').click({ trial: true, timeout: 1000 });
    await page.locator('#qty-input').click({ trial: true, timeout: 1000 });
    await expect(toast).toBeVisible();
};

/** Several TUs opened in a row: still ONE green message, and it covers nothing of the pop-up: the first row (target quantity, its row label), the count field, OK, Weiteres TU */
const expectOneMessageCoveringNothing = async (page, name) => {
    await expect(page.locator('.Toastify__toast--success')).toHaveCount(1);
    const firstRow = page.locator('tr').filter({ has: page.getByTestId('qty-target') }).first();
    await expect(firstRow, 'first row of the pop-up is shown').toBeVisible();
    expect.soft(await isCoveredByToast(firstRow.locator('td').first()), 'label of the first row is not covered by the green message').toBe(false);
    await expectNothingCoveredWhileMessageShown(page, name);
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

        // noinspection JSUnusedLocalSymbols
        test('"Weiteres TU" three times in a row: one message, nothing covered', async ({ page }) => {
            allureMeta('TU opened message');

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 20 }] });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata }));
            for (let i = 0; i < 3; i++) {
                await PickLineScanScreen.openTU();
            }
            await GetQuantityDialog.typeQtyEntered(5);
            await expectOneMessageCoveringNothing(page, `weiteres-tu-x3-${viewport.width}x${viewport.height}`);
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });

        // noinspection JSUnusedLocalSymbols
        test('three GRAI scans in a row: one message, nothing covered', async ({ page }) => {
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
            await expectOneMessageCoveringNothing(page, `grai-x3-${viewport.width}x${viewport.height}`);
            await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 15 });
        });
    });
}
