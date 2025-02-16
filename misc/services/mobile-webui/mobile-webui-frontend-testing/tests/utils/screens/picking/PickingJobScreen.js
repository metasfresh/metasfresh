import { page, runAndWatchForErrors, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { SelectPickTargetLUScreen } from "./SelectPickTargetLUScreen";
import { PickingJobLineScreen } from "./PickingJobLineScreen";
import { PickingJobScanHUScreen } from "./PickingJobScanHUScreen";
import { PickingSlotScanScreen } from "./PickingSlotScanScreen";
import { GetQuantityDialog } from "./GetQuantityDialog";
import { YesNoDialog } from "../../dialogs/YesNoDialog";
import { PickingJobsListScreen } from "./PickingJobsListScreen";
import { SelectPickTargetTUScreen } from './SelectPickTargetTUScreen';

const NAME = 'PickingJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const PickingJobScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    scanPickingSlot: async ({ qrCode }) => await test.step(`${NAME} - Scan picking slot ${qrCode}`, async () => {
        await page.locator('#scan-activity-A1-button').tap();
        await PickingSlotScanScreen.waitForScreen();
        await PickingSlotScanScreen.typeQRCode(qrCode);
        await PickingJobScreen.waitForScreen();
    }),

    clickLUTargetButton: async () => await test.step(`${NAME} - Click LU target button`, async () => {
        await page.locator('#targetLU-button').tap();
    }),
    setTargetLU: async ({ lu }) => await test.step(`${NAME} - Set target LU to ${lu}`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetLUScreen.waitForScreen();
        await SelectPickTargetLUScreen.clickLUButton({ lu });
        await PickingJobScreen.waitForScreen();
    }),
    closeTargetLU: async () => await test.step(`${NAME} - Close target LU`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetLUScreen.clickCloseTargetButton();
        await PickingJobScreen.waitForScreen();
    }),

    clickTUTargetButton: async () => await test.step(`${NAME} - Click TU target button`, async () => {
        await page.locator('#targetTU-button').tap();
    }),
    setTargetTU: async ({ tu }) => await test.step(`${NAME} - Set target TU to ${tu}`, async () => {
        await PickingJobScreen.clickTUTargetButton();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu });
        await PickingJobScreen.waitForScreen();
    }),
    closeTargetTU: async () => await test.step(`${NAME} - Close target TU`, async () => {
        await PickingJobScreen.clickTUTargetButton();
        await SelectPickTargetTUScreen.clickCloseTargetButton();
        await PickingJobScreen.waitForScreen();
    }),

    pickHU: async ({ qrCode, expectQtyEntered, catchWeightQRCode }) => await test.step(`${NAME} - Scan HU and Pick`, async () => {
        await page.locator('#scanQRCode-button').tap(); // click Scan QR Code button
        await PickingJobScanHUScreen.waitForScreen();
        await PickingJobScanHUScreen.typeQRCode(qrCode);
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, catchWeightQRCode });
        await PickingJobScreen.waitForScreen();
    }),

    clickLineButton: async ({ index }) => await test.step(`${NAME} - Click line ${index}`, async () => {
        await page.locator(`#line-0-${index}-button`).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

    abort: async () => await test.step(`${NAME} - Abort`, async () => {
        await page.locator('#abort-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await PickingJobsListScreen.waitForScreen();
    }),

    complete: async () => await test.step(`${NAME} - Complete`, async () => {
        await runAndWatchForErrors(async () => {
            await page.locator('#last-confirm-button').tap();
            await YesNoDialog.waitForDialog();
            await YesNoDialog.clickYesButton();
            await PickingJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
        })
    }),
};