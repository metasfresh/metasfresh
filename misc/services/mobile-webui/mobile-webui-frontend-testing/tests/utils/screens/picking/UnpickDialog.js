import { test } from "../../../../playwright.config";
import { page } from "../../common";

const NAME = 'UnpickDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.unpick-dialog');

export const UnpickDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    clickSkipScanningTargetHUButton: async () => await test.step(`${NAME} - Click Skip button`, async () => {
        await page.locator('#skip-button').tap();
    }),
};