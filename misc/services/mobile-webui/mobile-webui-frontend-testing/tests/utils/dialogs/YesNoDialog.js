import { test } from "../../../playwright.config";
import { page } from "../common";
import { expect } from '@playwright/test';

const NAME = 'YesNoDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.yes-no-dialog');

export const YesNoDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect dialog to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickYesButton: async () => await test.step(`${NAME} - Click Yes Button`, async () => {
        await YesNoDialog.expectVisible();
        await page.locator('#yes-button').click();
    }),

    clickNoButton: async () => await test.step(`${NAME} - Click No Button`, async () => {
        await YesNoDialog.expectVisible();
        await page.locator('#no-button').click();
    }),

};