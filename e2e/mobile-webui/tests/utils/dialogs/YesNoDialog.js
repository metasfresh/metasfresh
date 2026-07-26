import { test } from "../../../playwright.config";
import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT } from "../common";
import { expect } from '@playwright/test';

const NAME = 'YesNoDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.yes-no-dialog');
/** The question the operator is asked before confirming. @returns {import('@playwright/test').Locator} */
const questionElement = () => containerElement().locator('strong');

export const YesNoDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect dialog to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    expectQuestion: async ({ contains }) => await test.step(`${NAME} - Expect the question to say '${contains}'`, async () => {
        await expect(questionElement()).toContainText(contains, { timeout: FAST_ACTION_TIMEOUT });
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