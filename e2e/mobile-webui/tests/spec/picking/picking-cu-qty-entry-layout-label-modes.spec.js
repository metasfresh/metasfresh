import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { delayRequests } from "../../utils/network";
import { LoadingSpinner, LOADING_SCOPE } from "../../utils/components/LoadingSpinner";
import { expectLayoutSound, expectOnScreenWithoutScrolling, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, expectInsideScreen, DIALOG_SCOPE } from "../../utils/layout";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { uniqueGTIN, uniqueLot } from "../../utils/cuLabel";
import { txt } from "../../utils/cuQtyEntryMessages";
import { allureMeta, labelOf, MODE_MANUAL, uniqueFormatName, openLine, restoreSysconfigsAfterEach, createMasterdata, scanLabel, startJob } from "../../utils/cuQtyEntryCommon";
import { createScanEachMasterdata, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        // noinspection JSUnusedLocalSymbols
        test(`Scan once quantity block fits the screen (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 12 }],
            });
            await startJob(masterdata, { qtyToDeliver: 12 });

            await test.step('Scan once: field, lot, best-before, OK, Cancel and Next TU are on the screen', async () => {
                await openLine({ index: 1 });
                await PickLineScanScreen.openTU();
                await scanLabel(labelOf({ masterdata }));
                const dialog = GetQuantityDialog.container();
                await expectInsideScreen(dialog.locator('#qty-input'), 'Scan once quantity field', viewport);
                await expectInsideScreen(dialog.getByTestId('lotNo'), 'Scan once lot field', viewport);
                await expectInsideScreen(dialog.getByTestId('bestBeforeDate'), 'Scan once best-before field', viewport);
                await expectInsideScreen(dialog.getByTestId('done-button'), 'Scan once OK button', viewport);
                await expectInsideScreen(dialog.getByTestId('cancel-button'), 'Scan once Cancel button', viewport);
                await expectInsideScreen(dialog.getByTestId('next-tu-button'), 'Scan once Next TU button', viewport);
                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();
            });
        });

        // noinspection JSUnusedLocalSymbols
        test(`Manual quantity dialog fits the screen and keeps its layout (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 12 }],
                customers: [{ customer: 'custA', cuQtyEntryMode: MODE_MANUAL }], // the line of the buyer books in Manual
            });
            await startJob(masterdata, { qtyToDeliver: 12 });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await GetQuantityDialog.waitForDialog();

            const dialog = GetQuantityDialog.container();
            await expectInsideScreen(dialog.locator('#qty-input'), 'Manual quantity field', viewport);
            await expectInsideScreen(dialog.getByTestId('done-button'), 'Manual OK button', viewport);
            await expectInsideScreen(dialog.getByTestId('cancel-button'), 'Manual Cancel button', viewport);
            const tableArea = await dialog.locator('.table-container').boundingBox();
            // positions are printed, to compare the layout before and after a change
            console.log('MANUAL-DIALOG-TUS ' + viewport.name + ' ' + JSON.stringify({
                field: await dialog.locator('#qty-input').boundingBox(), ok: await dialog.getByTestId('done-button').boundingBox(),
                cancel: await dialog.getByTestId('cancel-button').boundingBox(), tableArea,
            }));
            expect(tableArea.height, 'Manual table area height is 70vh').toBeCloseTo(viewport.height * 0.7, 0);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Manual quantity dialog shows its labels and values uncut sideways (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            // a packing instruction name that is one long word without a space (like an article code), wider than any phone screen
            const longTU = 'P1_CartonOf4CUsWithALongNameWithoutSpaces';
            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 12, piItemProduct: longTU }],
                customers: [{ customer: 'custA', cuQtyEntryMode: MODE_MANUAL }],
                packingInstructions: { P1_20x4: { lu: 'LU', qtyTUsPerLU: 20, tu: longTU, product: 'P1', qtyCUsPerTU: 4 } },
            });
            await startJob(masterdata, { qtyToDeliver: 12 });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await GetQuantityDialog.waitForDialog();

            const dialog = GetQuantityDialog.container();
            const tableArea = dialog.locator('.table-container');
            const packingName = dialog.locator('#packing-name');
            await expect(packingName, 'the dialog shows the packing instruction name').toBeVisible();
            await expect(packingName, 'the test needs a packing name with a long word without a space').toHaveText(/\S{40,}/);

            // the table area is scrolled sideways when its content is wider than it: the labels then sit off the left edge
            const measured = await tableArea.evaluate((area) => {
                const areaBox = area.getBoundingClientRect();
                const labelLefts = [...area.querySelectorAll('th')].map((th) => Math.round(th.getBoundingClientRect().left - areaBox.left));
                return { scrollLeft: area.scrollLeft, tooWide: area.scrollWidth - area.clientWidth, labelLefts };
            });
            console.log('MANUAL-DIALOG-SIDEWAYS ' + viewport.name + ' ' + JSON.stringify(measured));
            expect.soft(measured.scrollLeft, 'the table area is not scrolled sideways').toBe(0);
            expect.soft(measured.tooWide, 'the table content is not wider than the table area').toBeLessThanOrEqual(1);
            expect.soft(Math.min(...measured.labelLefts), 'every label starts inside the table area').toBeGreaterThanOrEqual(0);
            await expectInsideScreen(dialog.locator('th').first(), 'Manual first label', viewport);
            await expectInsideScreen(packingName, 'Manual packing name', viewport);
        });

        for (const lang of LAYOUT_LANGUAGES) {

            // noinspection JSUnusedLocalSymbols
            test(`Scan once walk-through [${lang}]: every screen and refusal is laid out soundly (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive quantity block');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

                const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }], language: lang });
                await startJob(masterdata, { qtyToDeliver: 12 });
                await layout('Once job screen');

                await openLine({ index: 1 });
                await layout('Once scan screen, no TU open');
                await expectOnScreenWithoutScrolling(PickLineScanScreen.nextTUButtonLocator(), 'Once scan screen: Next TU button', viewport);

                await scanLabel(labelOf({ masterdata }));
                await layout('Once quantity block, no TU open', DIALOG_SCOPE);
                await GetQuantityDialog.typeQtyEntered(5);
                await GetQuantityDialog.clickDoneRefused({
                    expectedError: txt(lang, 'noOpenTU'),
                    whileToastShown: async () => await layout('Once refusal no open TU (quantity block + toast)', DIALOG_SCOPE),
                });

                await PickLineScanScreen.openTU();
                await layout('Once quantity block, TU open', DIALOG_SCOPE);
                await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 7 });
                await layout('Once quantity block after a booking', DIALOG_SCOPE);

                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();
                await layout('Once job screen with a picked line');

                await PickingJobScreen.clickLineButton({ index: 1 });
                await PickingJobLineScreen.waitForScreen();
                await layout('Once line screen with a booked TU');
                await expectReachable(PickingJobLineScreen.stepButton({ index: 0 }), 'booked TU button', viewport);

                await PickingJobLineScreen.clickStepButton({ index: 0 });
                await PickingJobStepScreen.waitForScreen();
                await layout('Once step screen (undo)');
                await expectReachable(PickingJobStepScreen.unpickButton(), 'Unpick button', viewport);
                await PickingJobStepScreen.tapUnpick();
                await layout('Once unpick dialog', DIALOG_SCOPE);
            });

            // noinspection JSUnusedLocalSymbols
            test(`Scan each CU walk-through [${lang}]: every screen and refusal is laid out soundly (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive quantity block');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

                const masterdata = await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders: [{ key: 'A', qty: 3 }], language: lang });
                const lot = uniqueLot();
                const gtin = uniqueGTIN();
                const pack = (packId) => packLabelOf({ masterdata, lot, gtin, packId });
                await startJob(masterdata, { qtyToDeliver: 3 });
                await openScanEachLine({ index: 1, openTU: false });
                await layout('Each scan screen, no TU open');
                await expectOnScreenWithoutScrolling(PickLineScanScreen.nextTUButtonLocator(), 'Each scan screen: Next TU button', viewport);
                await expectOnScreenWithoutScrolling(PickLineScanScreen.scanEachCloseButton(), 'Each scan screen: Close button', viewport);

                await PickLineScanScreen.scanCULabelRefused(pack(1), txt(lang, 'noOpenTU'), {
                    whileToastShown: async () => await layout('Each refusal no open TU (scan screen + toast)'),
                });

                await PickLineScanScreen.openTU();
                await layout('Each scan screen, TU open');
                await scanPack(pack(1));
                await layout('Each scan screen after a CU was booked');
                await PickLineScanScreen.scanCULabelRefused(pack(1), txt(lang, 'alreadyPicked'), {
                    whileToastShown: async () => await layout('Each refusal CU already picked (scan screen + toast)'),
                });
                await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, gtin, packId: null }), txt(lang, 'formatMismatch'), {
                    whileToastShown: async () => await layout('Each refusal label without pack ID (scan screen + toast)'),
                });
                await PickLineScanScreen.scanCULabelRefused('not-a-label-of-this-format', txt(lang, 'formatMismatch'), {
                    whileToastShown: async () => await layout('Each refusal label does not fit the format (scan screen + toast)'),
                });
                await scanPack(pack(2));
                await scanPack(pack(3));
                await PickingJobScreen.waitForScreen();
                await layout('Each job screen after the line is done');
            });
        }

        // noinspection JSUnusedLocalSymbols
        test(`Manual walk-through: line screen, scan step, quantity dialog with reject reason (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }], profileMode: MODE_MANUAL, allowCompletingPartialPickingJob: true });
            await startJob(masterdata, { qtyToDeliver: 12, scanPickFrom: false });
            await layout('Manual job screen');
            await PickingJobScreen.clickLineButton({ index: 1 });
            await PickingJobLineScreen.waitForScreen();
            await layout('Manual line screen');
            await PickingJobLineScreen.clickScanButton();
            await PickLineScanScreen.waitForScreen();
            await layout('Manual scan step (HU scanner)');

            await PickLineScanScreen.goBack();
            await PickingJobLineScreen.goBack();
            await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await GetQuantityDialog.waitForDialog();
            await layout('Manual quantity dialog', DIALOG_SCOPE);
            await GetQuantityDialog.typeQtyEntered(3);
            await layout('Manual quantity dialog, less than open: reject reason asked', DIALOG_SCOPE);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Once, no "Pick from" scanned: line screen, scan step and refusal are laid out soundly (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }] });
            await startJob(masterdata, { qtyToDeliver: 12, scanPickFrom: false });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await PickingJobLineScreen.waitForScreen();
            await layout('Once line screen, no source HU');
            await PickingJobLineScreen.clickScanButton();
            await PickLineScanScreen.waitForScreen();
            await layout('Once scan step, no source HU');
            await scanLabel(labelOf({ masterdata }));
            await GetQuantityDialog.typeQtyEntered(3);
            await GetQuantityDialog.clickDoneRefused({
                expectedError: txt('en_US', 'pickFromRequired'),
                whileToastShown: async () => await layout('Once refusal "scan Pick from first" (quantity block + toast)', DIALOG_SCOPE),
            });
        });

        // noinspection JSUnusedLocalSymbols
        test(`TU by GRAI: TU opened toast, refusal, busy refusal (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const masterdata = await createMasterdata({
                formatName: uniqueFormatName(),
                orders: [{ key: 'A', qty: 20, graiRequired: 'N' }],
                products: { P2: { price: 1 } },
                packingInstructions: {
                    G1: { tu: 'TU_G1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                    GN: { tu: 'TU_GN', product: 'P2', qtyCUsPerTU: 4, graiMapping: true }, // another product: no capacity for P1
                },
            });
            await startJob(masterdata, { qtyToDeliver: 20 });
            await openLine({ index: 1 });
            await PickLineScanScreen.scanGrai(masterdata.packingInstructions.G1.grai);
            await layout('GRAI scan opens a TU: "New TU opened" toast and header "Current"');
            await PickLineScanScreen.scanCULabelRefused(masterdata.packingInstructions.GN.grai, txt('en_US', 'noCapacity'), {
                whileToastShown: async () => await layout('GRAI refusal no capacity (scan screen + toast)'),
            });

            await scanLabel(labelOf({ masterdata }));
            // a TU is being opened (slow answer): a GRAI scanned meanwhile is told to wait
            const restoreTargetSpeed = await delayRequests({ urlPattern: /\/picking\/job\/[^/]+\/target/, method: 'POST', delayMs: 6000 });
            await PickLineScanScreen.tapNextTUWithoutWaiting();
            await PickLineScanScreen.rescanCULabelRefused(masterdata.packingInstructions.G1.grai, txt('en_US', 'nextTUBusy'), {
                whileToastShown: async () => await layout('Once busy refusal "a TU is just being opened" (quantity block + toast)', DIALOG_SCOPE),
            });
            await restoreTargetSpeed();
        });

        // noinspection JSUnusedLocalSymbols
        test(`Spinner while a booking is in flight (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders: [{ key: 'A', qty: 3 }] });
            await startJob(masterdata, { qtyToDeliver: 3 });
            await openScanEachLine({ index: 1 });
            const restoreEventSpeed = await delayRequests({ urlPattern: /\/picking\/event/, delayMs: 4000 });
            await PickLineScanScreen.typeQRCode(packLabelOf({ masterdata, packId: 1 }));
            await LoadingSpinner.waitToShow();
            await expectLayoutSound('Spinner while a booking is in flight', viewport, LOADING_SCOPE);
            await expectInsideScreen(LoadingSpinner.icon(), 'Spinner icon', viewport);
            await restoreEventSpeed();
        });

        // noinspection JSUnusedLocalSymbols
        test(`Scan each CU scan screen: Next TU and Close are visible without scrolling (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders: [{ key: 'A', qty: 3 }] });
            await startJob(masterdata, { qtyToDeliver: 3 });
            await openScanEachLine({ index: 1, openTU: false });

            await expectOnScreenWithoutScrolling(PickLineScanScreen.nextTUButtonLocator(), 'Scan each Next TU button', viewport);
            await expectOnScreenWithoutScrolling(PickLineScanScreen.scanEachCloseButton(), 'Scan each Close button', viewport);
        });
    });
}


/**
 * Rows of the given buttons: box of each (Playwright boundingBox), height and font of its computed style.
 * The barcode icon of "Weitere Kiste" must not change which buttons share a row, so the rows are compared with and without the icon.
 */
const measureRow = async (page, scope, testIds) => {
    const result = {};
    for (const testId of testIds) {
        const button = scope(page).getByTestId(testId);
        await expect(button).toBeVisible();
        const box = await button.boundingBox();
        const font = await button.evaluate((el) => getComputedStyle(el).fontSize);
        result[testId] = { x: box.x, y: box.y, width: box.width, height: box.height, font };
    }
    return result;
};

/** Number of the row (0 = topmost) a button sits in: the buttons with the same y share a row. (The dialog is bottom-anchored, so absolute y shifts with the row count.) */
const rowIndexOf = (boxes, id) => [...new Set(Object.values(boxes).map((b) => Math.round(b.y)))].sort((p, q) => p - q).indexOf(Math.round(boxes[id].y));

const ICON_LAYOUT_VIEWPORTS = [{ width: 360, height: 720 }, { width: 393, height: 727 }];

/**
 * The barcode icon of "Weitere Kiste" (next-tu-button) is a decoration: at every Zebra width the buttons are arranged in the same rows
 * as without it (same y per button), all buttons are equally high, and the font is not shrunk.
 */
const expectIconKeepsTheRows = async (page, { scope, testIds, label }) => {
    const withIcon = {};
    for (const viewport of ICON_LAYOUT_VIEWPORTS) {
        await page.setViewportSize(viewport);
        withIcon[viewport.width] = await measureRow(page, scope, testIds);
        const heights = testIds.map((id) => withIcon[viewport.width][id].height);
        expect.soft(new Set(heights).size, `${label} ${viewport.width}: all buttons equally high ${JSON.stringify(heights)}`).toBe(1);
        const next = withIcon[viewport.width]['next-tu-button'];
        expect.soft(next.x + next.width, `${label} ${viewport.width}: Weitere Kiste inside the screen`).toBeLessThanOrEqual(viewport.width);
    }
    // reference: the same buttons without the icon (icon span removed, icon class of the button removed for this check only)
    await scope(page).getByTestId('next-tu-button').evaluate((el) => {
        el.querySelector('.dialog-button-icon')?.remove();
        el.classList.remove('has-dialog-icon');
    });
    for (const viewport of ICON_LAYOUT_VIEWPORTS) {
        await page.setViewportSize(viewport);
        const without = await measureRow(page, scope, testIds);
        for (const id of testIds) {
            const a = withIcon[viewport.width][id];
            const b = without[id];
            expect.soft(rowIndexOf(withIcon[viewport.width], id), `${label} ${viewport.width}: ${id} row number with the icon vs without`).toBe(rowIndexOf(without, id));
            expect.soft(a.height, `${label} ${viewport.width}: ${id} height with the icon vs without`).toBe(b.height);
            expect.soft(a.font, `${label} ${viewport.width}: ${id} font size with the icon vs without`).toBe(b.font);
        }
    }
};

// noinspection JSUnusedLocalSymbols
test('The barcode icon of "Weitere Kiste" keeps the button rows of the pop-up (next to OK and Abbrechen) at 360 and 393 px', async ({ page }) => {
    allureMeta('Responsive quantity block');
    const masterdata = await createMasterdata({ formatName: uniqueFormatName(), orders: [{ key: 'A', qty: 12 }], language: 'de_DE' });
    await startJob(masterdata, { qtyToDeliver: 12 });
    await openLine({ index: 1 });
    await PickLineScanScreen.openTU();
    await scanLabel(labelOf({ masterdata }));
    await GetQuantityDialog.waitForDialog();
    await expectIconKeepsTheRows(page, {
        scope: (p) => p.locator('.get-qty-dialog'),
        testIds: ['done-button', 'cancel-button', 'next-tu-button'],
        label: 'pop-up',
    });
});

// noinspection JSUnusedLocalSymbols
test('The barcode icon of "Weitere Kiste" keeps the button rows of the first scan screen (next to Schließen) at 360 and 393 px', async ({ page }) => {
    allureMeta('Responsive quantity block');
    const masterdata = await createScanEachMasterdata({ packFormatName: uniqueFormatName(), orders: [{ key: 'A', qty: 3 }], language: 'de_DE' });
    await startJob(masterdata, { qtyToDeliver: 3 });
    await openScanEachLine({ index: 1, openTU: false });
    await expectIconKeepsTheRows(page, {
        scope: (p) => p.locator('body'),
        testIds: ['next-tu-button', 'scan-each-close-button'],
        label: 'first-scan screen',
    });
});
