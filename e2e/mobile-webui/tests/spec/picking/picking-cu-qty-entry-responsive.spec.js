import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { page } from "../../utils/common";
import { expectLayoutSound, expectOnScreenWithoutScrolling, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, expectInsideScreen } from "../../utils/layout";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { uniqueGTIN, uniqueLot } from "../../utils/cuLabel";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { allureMeta, labelOf, MODE_MANUAL, MSG_NO_OPEN_BOX, newFormatName, openLine, restoreSysconfigsAfterEach, createMasterdata, scanLabel, startJob } from "../../utils/cuQtyEntryScanOnce";
import { createScanEachMasterdata, MSG_ALREADY_PICKED, openScanEachLine, packLabelOf, scanPack } from "../../utils/cuQtyEntryScanEach";

restoreSysconfigsAfterEach(test);

/**
 * Every screen, dialog and state the CU quantity entry modes add or change is walked through at each size and checked by
 * `expectLayoutSound` (utils/layout.js): nothing sticks out sideways, no text cut off, no overlapping controls, controls big enough to
 * tap, dialog controls on the screen without scrolling, refusal toasts fully on the screen. Screens that scroll (as all screens of the app
 * do) must have their primary controls reachable by that scroll.
 *
 * CU quantity entry on a phone-size screen: every control of the quantity block (Scan once), of the Scan each CU scan screen and of the
 * Manual quantity dialog is inside the visible screen, without scrolling. The Manual dialog keeps its layout of today.
 *
 * The sizes (PHONE_VIEWPORTS, utils/layout.js) are a parameter: one block of tests per size.
 */
const TEXTS = {
    en_US: {
        noOpenBox: MSG_NO_OPEN_BOX,
        alreadyPicked: 'This pack was already picked in this job',
        noPackId: 'The label contains no pack ID',
        formatMismatch: 'does not match the label format',
        pickFromRequired: "Please scan 'Pick From' first.",
        noCapacity: 'has no capacity configured for this product',
        nextBoxBusy: 'A box is just being opened.',
    },
    de_DE: {
        noOpenBox: 'Bitte öffnen Sie zuerst eine Kiste',
        alreadyPicked: 'Diese Packung wurde',
        noPackId: 'Packungs-ID',
        formatMismatch: 'Etikettenformat',
    },
};


/** Like startJob, but the source HU ("Pick from") is not scanned: only the picking slot. */
const startJobWithoutSourceHU = async (masterdata, { qtyToDeliver }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver, productId: masterdata.products.P1.id });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
};


for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        // noinspection JSUnusedLocalSymbols
        test(`Scan once quantity block fits the screen (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createMasterdata({
                formatName: newFormatName(),
                orders: [{ key: 'A', qty: 12 }],
            });
            await startJob(masterdata, { qtyToDeliver: 12 });

            await test.step('Scan once: field, lot, best-before, OK, Cancel and Next box are on the screen', async () => {
                await openLine({ index: 1 });
                await PickLineScanScreen.openBox();
                await scanLabel(labelOf({ masterdata }));
                const dialog = page.locator('.get-qty-dialog');
                await expectInsideScreen(dialog.locator('#qty-input'), 'Scan once quantity field', viewport);
                await expectInsideScreen(dialog.getByTestId('lotNo'), 'Scan once lot field', viewport);
                await expectInsideScreen(dialog.getByTestId('bestBeforeDate'), 'Scan once best-before field', viewport);
                await expectInsideScreen(dialog.getByTestId('done-button'), 'Scan once OK button', viewport);
                await expectInsideScreen(dialog.getByTestId('cancel-button'), 'Scan once Cancel button', viewport);
                await expectInsideScreen(dialog.getByTestId('next-box-button'), 'Scan once Next box button', viewport);
                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();
            });
        });

        // noinspection JSUnusedLocalSymbols
        test(`Manual quantity dialog fits the screen and keeps its layout (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createMasterdata({
                formatName: newFormatName(),
                orders: [{ key: 'A', qty: 12 }],
                customers: [{ customer: 'custA', cuQtyEntryMode: MODE_MANUAL }], // the line of the buyer books in Manual
            });
            await startJob(masterdata, { qtyToDeliver: 12 });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await GetQuantityDialog.waitForDialog();

            const dialog = page.locator('.get-qty-dialog');
            await expectInsideScreen(dialog.locator('#qty-input'), 'Manual quantity field', viewport);
            await expectInsideScreen(dialog.getByTestId('done-button'), 'Manual OK button', viewport);
            await expectInsideScreen(dialog.getByTestId('cancel-button'), 'Manual Cancel button', viewport);
            const tableArea = await dialog.locator('.table-container').boundingBox();
            // positions are printed, to compare the layout before and after a change
            console.log('MANUAL-DIALOG-BOXES ' + viewport.name + ' ' + JSON.stringify({
                field: await dialog.locator('#qty-input').boundingBox(), ok: await dialog.getByTestId('done-button').boundingBox(),
                cancel: await dialog.getByTestId('cancel-button').boundingBox(), tableArea,
            }));
            expect(tableArea.height, 'Manual table area height is 70vh as before the change').toBeCloseTo(viewport.height * 0.7, 0);
        });

        for (const lang of LAYOUT_LANGUAGES) {
            const T = TEXTS[lang];

            // noinspection JSUnusedLocalSymbols
            test(`Scan once walk-through [${lang}]: every screen and refusal is laid out soundly (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive quantity block');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

                const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 12 }], language: lang });
                await startJob(masterdata, { qtyToDeliver: 12 });
                await layout('Once job screen');

                await openLine({ index: 1 });
                await layout('Once scan screen, no box open');
                await expectOnScreenWithoutScrolling(page.getByTestId('next-box-button'), 'Once scan screen: Next box button', viewport);

                await scanLabel(labelOf({ masterdata }));
                await layout('Once quantity block, no box open');
                await GetQuantityDialog.typeQtyEntered(5);
                await GetQuantityDialog.clickDoneRefused({
                    expectedError: T.noOpenBox,
                    whileToastShown: async () => await layout('Once refusal no open box (quantity block + toast)'),
                });

                await PickLineScanScreen.openBox();
                await layout('Once quantity block, box open');
                await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget: 7 });
                await layout('Once quantity block after a booking');

                await GetQuantityDialog.clickCancel();
                await PickingJobScreen.waitForScreen();
                await layout('Once job screen with a picked line');

                await PickingJobScreen.clickLineButton({ index: 1 });
                await PickingJobLineScreen.waitForScreen();
                await layout('Once line screen with a booked box');
                await expectReachable(page.locator('#step-0-button'), 'booked box button', viewport);

                await PickingJobLineScreen.clickStepButton({ index: 0 });
                await PickingJobStepScreen.waitForScreen();
                await layout('Once step screen (undo)');
                await expectReachable(page.locator('#unpick-button'), 'Unpick button', viewport);
                await page.locator('#unpick-button').tap();
                await layout('Once unpick dialog');
            });

            // noinspection JSUnusedLocalSymbols
            test(`Scan each CU walk-through [${lang}]: every screen and refusal is laid out soundly (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive quantity block');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);

                const masterdata = await createScanEachMasterdata({ packFormatName: newFormatName(), orders: [{ key: 'A', qty: 3 }], language: lang });
                const lot = uniqueLot();
                const gtin = uniqueGTIN();
                const pack = (packId) => packLabelOf({ masterdata, lot, gtin, packId });
                await startJob(masterdata, { qtyToDeliver: 3 });
                await openScanEachLine({ index: 1, openBox: false });
                await layout('Each scan screen, no box open');
                await expectOnScreenWithoutScrolling(page.getByTestId('next-box-button'), 'Each scan screen: Next box button', viewport);
                await expectOnScreenWithoutScrolling(page.getByTestId('scan-each-close-button'), 'Each scan screen: Close button', viewport);

                await PickLineScanScreen.scanCULabelRefused(pack(1), T.noOpenBox, {
                    whileToastShown: async () => await layout('Each refusal no open box (scan screen + toast)'),
                });

                await PickLineScanScreen.openBox();
                await layout('Each scan screen, box open');
                await scanPack(pack(1));
                await layout('Each scan screen after a pack was booked');
                await PickLineScanScreen.scanCULabelRefused(pack(1), T.alreadyPicked, {
                    whileToastShown: async () => await layout('Each refusal pack already picked (scan screen + toast)'),
                });
                await PickLineScanScreen.scanCULabelRefused(packLabelOf({ masterdata, lot, gtin, packId: null }), T.noPackId, {
                    whileToastShown: async () => await layout('Each refusal label without pack ID (scan screen + toast)'),
                });
                await PickLineScanScreen.scanCULabelRefused('not-a-label-of-this-format', T.formatMismatch, {
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

            const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 12 }], profileMode: MODE_MANUAL, allowCompletingPartialPickingJob: true });
            await startJobWithoutSourceHU(masterdata, { qtyToDeliver: 12 });
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
            await layout('Manual quantity dialog');
            await GetQuantityDialog.typeQtyEntered(3);
            await layout('Manual quantity dialog, less than open: reject reason asked');
        });

        // noinspection JSUnusedLocalSymbols
        test(`Once, no "Pick from" scanned: line screen, scan step and refusal are laid out soundly (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const masterdata = await createMasterdata({ formatName: newFormatName(), orders: [{ key: 'A', qty: 12 }] });
            await startJobWithoutSourceHU(masterdata, { qtyToDeliver: 12 });
            await PickingJobScreen.clickLineButton({ index: 1 });
            await PickingJobLineScreen.waitForScreen();
            await layout('Once line screen, no source HU');
            await PickingJobLineScreen.clickScanButton();
            await PickLineScanScreen.waitForScreen();
            await layout('Once scan step, no source HU');
            await scanLabel(labelOf({ masterdata }));
            await GetQuantityDialog.typeQtyEntered(3);
            await GetQuantityDialog.clickDoneRefused({
                expectedError: TEXTS.en_US.pickFromRequired,
                whileToastShown: async () => await layout('Once refusal "scan Pick from first" (quantity block + toast)'),
            });
        });

        // noinspection JSUnusedLocalSymbols
        test(`Box by GRAI: box opened toast, refusal, busy refusal (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const masterdata = await createMasterdata({
                formatName: newFormatName(),
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
            await layout('GRAI scan opens a box: "New box opened" toast and header "Current"');
            await PickLineScanScreen.scanCULabelRefused(masterdata.packingInstructions.GN.grai, TEXTS.en_US.noCapacity, {
                whileToastShown: async () => await layout('GRAI refusal no capacity (scan screen + toast)'),
            });

            await scanLabel(labelOf({ masterdata }));
            // a box is being opened (slow answer): a GRAI scanned meanwhile is told to wait
            await page.route(/\/picking\/job\/[^/]+\/target/, async (route) => {
                if (route.request().method() === 'POST') await new Promise((resolve) => setTimeout(resolve, 6000));
                await route.continue();
            });
            await page.getByTestId('next-box-button').tap();
            await PickLineScanScreen.rescanCULabelRefused(masterdata.packingInstructions.G1.grai, TEXTS.en_US.nextBoxBusy, {
                whileToastShown: async () => await layout('Once busy refusal "a box is just being opened" (quantity block + toast)'),
            });
            await page.unroute(/\/picking\/job\/[^/]+\/target/);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Spinner while a booking is in flight (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createScanEachMasterdata({ packFormatName: newFormatName(), orders: [{ key: 'A', qty: 3 }] });
            await startJob(masterdata, { qtyToDeliver: 3 });
            await openScanEachLine({ index: 1 });
            await page.route(/\/picking\/event/, async (route) => {
                await new Promise((resolve) => setTimeout(resolve, 4000));
                await route.continue();
            });
            await PickLineScanScreen.typeQRCode(packLabelOf({ masterdata, packId: 1 }));
            await page.locator('.loading').first().waitFor({ state: 'visible' });
            await expectLayoutSound('Spinner while a booking is in flight', viewport, '.loading');
            await expectInsideScreen(page.locator('.loading-icon').first(), 'Spinner icon', viewport);
            await page.unroute(/\/picking\/event/);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Scan each CU scan screen: Next box and Close are visible without scrolling (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive quantity block');

            const masterdata = await createScanEachMasterdata({ packFormatName: newFormatName(), orders: [{ key: 'A', qty: 3 }] });
            await startJob(masterdata, { qtyToDeliver: 3 });
            await openScanEachLine({ index: 1, openBox: false });

            await expectOnScreenWithoutScrolling(page.getByTestId('next-box-button'), 'Scan each Next box button', viewport);
            await expectOnScreenWithoutScrolling(page.getByTestId('scan-each-close-button'), 'Scan each Close button', viewport);
        });
    });
}
