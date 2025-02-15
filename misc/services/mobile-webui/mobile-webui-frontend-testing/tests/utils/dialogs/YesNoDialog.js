import { test } from "../../../playwright.config";
import { page } from "../common";

const NAME = 'YesNoDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.yes-no-dialog');

export const YesNoDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    clickYesButton: async () => await test.step(`${NAME} - Click Yes Button`, async () => {
        await page.locator('#yes-button').click();
    }),

    clickNoButton: async () => await test.step(`${NAME} - Click No Button`, async () => {
        await page.locator('#no-button').click();
    }),

};