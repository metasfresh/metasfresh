import { test } from "../../../playwright.config";
import { page } from "../common";

export const YesNoDialog = {
    waitForDialog: async () => await test.step(`Wait for Confirm Dialog`, async () => {
        await page.locator('.yes-no-dialog').waitFor();
    }),

    clickYesButton: async () => await test.step(`Click Yes Button`, async () => {
        await page.locator('#yes-button').click();
    }),

    clickNoButton: async () => await test.step(`Click No Button`, async () => {
        await page.locator('#no-button').click();
    }),

};