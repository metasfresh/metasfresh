import { test } from "../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT } from "../common";

const NAME = 'UnpickDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.unpick-dialog');

export const UnpickDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickSkipScanningTargetHUButton: async () => await test.step(`${NAME} - Click Skip button`, async () => {
        await page.locator('#skip-button').tap();
    }),
};