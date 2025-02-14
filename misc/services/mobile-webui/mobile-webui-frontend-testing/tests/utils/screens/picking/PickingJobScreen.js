import {page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT} from "../../common";
import {test} from "../../../../playwright.config";
import {SelectPickTargetScreen} from "./SelectPickTargetScreen";
import {PickingJobLineScreen} from "./PickingJobLineScreen";
import {PickingJobScanHUScreen} from "./PickingJobScanHUScreen";
import {PickingJobScanPickingSlotScreen} from "./PickingJobScanPickingSlotScreen";
import {GetQuantityDialog} from "./GetQuantityDialog";
import {YesNoDialog} from "../../dialogs/YesNoDialog";
import {PickingJobsListScreen} from "./PickingJobsListScreen";

export const PickingJobScreen = {
    waitForScreen: async () => await test.step(`Wait for Picking Job screen`, async () => {
        await page.locator('#WFProcessScreen').waitFor({timeout: SLOW_ACTION_TIMEOUT});
    }),

    scanPickingSlot: async ({qrCode}) => await test.step(`Scan picking slot ${qrCode}`, async () => {
        await page.locator('#scan-activity-A10-button').tap();
        await PickingJobScanPickingSlotScreen.waitForScreen();
        await PickingJobScanPickingSlotScreen.typeQRCode(qrCode);
        await PickingJobScreen.waitForScreen();
    }),

    clickLUTargetButton: async () => await test.step(`Click LU target button`, async () => {
        await page.locator('#targetLU-button').tap();
    }),
    setTargetLU: async ({lu}) => await test.step(`Set target LU to ${lu}`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetScreen.waitForScreen();
        await SelectPickTargetScreen.clickLUButton({lu});
        await PickingJobScreen.waitForScreen();
    }),
    closeTargetLU: async () => await test.step(`Close target LU`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetScreen.clickCloseTargetButton();
        await PickingJobScreen.waitForScreen();
    }),

    pickHU: async ({qrCode, expectQtyEntered}) => await test.step(`Scan HU and Pick`, async () => {
        await page.locator('#scanQRCode-button').tap(); // click Scan QR Code button
        await PickingJobScanHUScreen.waitForScreen();
        await PickingJobScanHUScreen.typeQRCode(qrCode);
        await test.step('GetQuantityDialog', async () => {
            await GetQuantityDialog.waitForDialog();
            if (expectQtyEntered != null) {
                await GetQuantityDialog.expectQtyEntered(expectQtyEntered);
            }
            await GetQuantityDialog.clickDone();
            await PickingJobScreen.waitForScreen();
        });
    }),

    clickLineButton: async ({index}) => await test.step(`Click line ${index}`, async () => {
        await page.locator(`#line-0-${index}-button`).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

    abort: async () => await test.step(`Abort Picking Job`, async () => {
        await page.locator('#abort-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await PickingJobsListScreen.waitForScreen();
    }),

    complete: async () => await test.step(`Complete Picking Job`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await PickingJobsListScreen.waitForScreen({timeout: VERY_SLOW_ACTION_TIMEOUT});
    }),
};