import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page } from "../../common";
import { PickingJobsListScreen } from './PickingJobsListScreen';

const NAME = 'PickingJobsListScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScanBarcodeScreen');

export const PickingJobsListScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    scanQRCode: async (qrCode) => await test.step(`${NAME} - Scan code ${qrCode}`, async () => {
        await page.type('#input-text', qrCode);
        await PickingJobsListScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobsListScreen.waitForScreen();
    }),
};