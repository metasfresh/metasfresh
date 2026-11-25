import { test } from "../../../playwright.config";
import { expectLayoutSound, PHONE_VIEWPORTS } from "../../utils/layout";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { allureMeta, createMasterdata, labelOf, login, MODE_MANUAL, openLine, restoreSysconfigsAfterEach, scanLabel, startJob, uniqueFormatName } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Manual mode by profile default or customer record; keyboard reachability of Manual and Scan once */

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        // noinspection JSUnusedLocalSymbols
        test(`Manual by profile default, empty customer record, customer record Manual: job and line screens are sound (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 4 }, { key: 'B', qty: 4 }, { key: 'C', qty: 4 }],
                profileMode: MODE_MANUAL,
                customers: [{ customer: 'custB' }, { customer: 'custC', cuQtyEntryMode: MODE_MANUAL }],
            });
            await login(masterdata);
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 12, productId: masterdata.products.P1.id });
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
            await layout('Manual job screen, three customers (profile default / empty record / record Manual)');
            for (const index of [1, 2, 3]) {
                await PickingJobScreen.clickLineButton({ index });
                await PickingJobLineScreen.waitForScreen();
                await layout(`Manual line screen of customer ${index}`);
                await PickingJobLineScreen.clickScanButton();
                await PickLineScanScreen.waitForScreen();
                await PickLineScanScreen.expectCUQtyEntryMode(MODE_MANUAL);
                await layout(`Manual scan step of customer ${index}`);
                await PickLineScanScreen.goBack();
                await PickingJobLineScreen.goBack();
                await PickingJobScreen.waitForScreen();
            }
        });

        // noinspection JSUnusedLocalSymbols
        test(`Manual: on-screen keyboard open (screen height cut to 55 %), quantity field and OK stay reachable (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const manual = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }], profileMode: MODE_MANUAL });
            await startJob(manual, { qtyToDeliver: 12 });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.expectReachableWithKeyboard('Manual quantity dialog', viewport);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Scan once: on-screen keyboard open (screen height cut to 55 %), quantity field and OK stay reachable (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const once = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }] });
            await startJob(once, { qtyToDeliver: 12 });
            await openLine({ index: 1 });
            await scanLabel(labelOf({ masterdata: once }));
            await GetQuantityDialog.expectReachableWithKeyboard('Scan once quantity block', viewport);
        });
    });
}
