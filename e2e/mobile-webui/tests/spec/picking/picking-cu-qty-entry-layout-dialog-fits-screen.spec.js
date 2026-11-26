import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { expectInsideScreen } from "../../utils/layout";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from "../../utils/screens/distribution/DistributionJobScreen";
import { DistributionLinePickFromScreen } from "../../utils/screens/distribution/DistributionLinePickFromScreen";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { PickLineScanScreen as ScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { createScanEachMasterdata, openScanEachLine, packLabelOf } from "../../utils/cuQtyEntryScanEach";
import { txt } from "../../utils/cuQtyEntryMessages";
import { uniqueGTIN, uniqueLot } from "../../utils/cuLabel";
import { generateEAN13 } from "../../utils/ean13";
import { allureMeta, createMasterdata, createMasterdataRememberingSysconfigs, labelOf, login, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueFormatName } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/**
 * The quantity pop-up (shared `GetQuantityDialog`) is never taller than the screen: when the content does not fit (a short screen,
 * e.g. with the browser bars or the on-screen keyboard shown), it stays within the screen (its table area shrinks, and scrolls inside when its rows need the room), so the OK and
 * Cancel buttons stay on the screen. Pinned in picking and in distribution (another app that uses the same dialog). The same short screen pins the shared error toast.
 */
const SHORT_SCREEN = { width: 360, height: 300, deviceScaleFactor: 3 };

const expectDialogFitsScreen = async (dialog, where, { isTableAreaScrolling }) => {
    const message = dialog.locator('.message');
    await expectInsideScreen(message, `${where}: the whole pop-up`, SHORT_SCREEN);
    await expectInsideScreen(dialog.getByTestId('done-button'), `${where}: OK button`, SHORT_SCREEN);
    await expectInsideScreen(dialog.getByTestId('cancel-button'), `${where}: Cancel button`, SHORT_SCREEN);
    if (isTableAreaScrolling) {
        // the rows are taller than the table area that is left: it scrolls inside, the pop-up does not grow
        const scrolls = await dialog.locator('.table-container').evaluate((area) => area.scrollHeight > area.clientHeight);
        expect(scrolls, `${where}: the table area scrolls inside`).toBe(true);
    }
};

test.describe(`quantity pop-up on a short screen ${SHORT_SCREEN.width}x${SHORT_SCREEN.height}`, () => {
    test.use({ viewport: { width: SHORT_SCREEN.width, height: SHORT_SCREEN.height }, deviceScaleFactor: SHORT_SCREEN.deviceScaleFactor });

    // noinspection JSUnusedLocalSymbols
    test('picking (Scan once, with lot, best-before and Next TU): OK and Cancel stay on the screen', async ({ page: _page }) => {
        allureMeta('Responsive quantity block');

        const masterdata = await createMasterdata({
            formatName: uniqueFormatName(),
            orders: [{ key: 'A', qty: 12 }],
        });
        await startJob(masterdata, { qtyToDeliver: 12 });
        await openLine({ index: 1 });
        await scanLabel(labelOf({ masterdata }));
        await PickLineScanScreen.openTU();

        await expectDialogFitsScreen(GetQuantityDialog.container(), 'picking Scan once', { isTableAreaScrolling: true });
    });

    // noinspection JSUnusedLocalSymbols
    test('distribution: OK and Cancel stay on the screen', async ({ page: _page }) => {
        allureMeta('Responsive Manual screens');

        const masterdata = await createMasterdataRememberingSysconfigs({
            language: 'en_US',
            request: {
                login: { user: { language: 'en_US', workplace: 'workplace2' } },
                mobileConfig: { distribution: { requireScanningProductCode: true } },
                resources: { plantId: { type: 'PT' } },
                products: { P1: { gtin: generateEAN13().ean13 } },
                warehouses: { wh1: {}, wh2: { locators: { wh2_l1: {} } }, whInTransit: { inTransit: true } },
                workplaces: { workplace2: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
                handlingUnits: { HU1: { product: 'P1', warehouse: 'wh1', qty: 100000 } },
                distributionOrders: { DD1: { warehouseFrom: 'wh1', warehouseTo: 'wh2', warehouseInTransit: 'whInTransit', plant: 'plantId', lines: [{ product: 'P1', qtyEntered: 100 }] } },
            },
        });
        await login(masterdata, 'distribution', { isTableAreaScrolling: false });
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.waitForScreen();
        await BarcodeScannerComponent.type({ scannedCode: masterdata.handlingUnits.HU1.qrCode });
        await DistributionLinePickFromScreen.waitForScreen();
        await DistributionLinePickFromScreen.waitForProductCodeInput();
        await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P1.gtin);
        await GetQuantityDialog.waitForDialog();

        await expectDialogFitsScreen(GetQuantityDialog.container(), 'distribution', { isTableAreaScrolling: false });
    });
});

// a screen so short that the toast does not fit under its bottom margin unless it is capped to the screen height
const TINY_SCREEN = { width: 360, height: 120, deviceScaleFactor: 3 };

test.describe(`error toast on a tiny screen ${TINY_SCREEN.width}x${TINY_SCREEN.height}`, () => {
    test.use({ viewport: { width: TINY_SCREEN.width, height: TINY_SCREEN.height }, deviceScaleFactor: TINY_SCREEN.deviceScaleFactor });

        test('error toast (shared by every app): its close button stays on the screen', async ({ page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders: [{ key: 'A', qty: 3 }], language: 'en_US' });
            await startJob(masterdata, { qtyToDeliver: 3 });
            await openScanEachLine({ index: 1, openTU: false });

            await ScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot: uniqueLot(), gtin: uniqueGTIN(), packId: 1 }), txt('en_US', 'noOpenTU'), {
                whileToastShown: async () => {
                    await expectInsideScreen(page.locator('.Toastify__toast--error').first(), 'error toast', TINY_SCREEN);
                    await expectInsideScreen(page.locator('.Toastify__close-button--error').first(), 'error toast close button', TINY_SCREEN);
                },
            });
        });
});
