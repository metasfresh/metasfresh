import { test } from "../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT } from "../common";
import { expect } from '@playwright/test';

const NAME = 'YesNoDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.yes-no-dialog');

export const YesNoDialog = {
    /** The Yes button; for layout checks (see utils/layout.js) */
    yesButton: () => page.locator('#yes-button'),

    /** The No button; for layout checks */
    noButton: () => page.locator('#no-button'),

    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect dialog to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    expectNotVisible: async () => await test.step(`${NAME} - Expect dialog NOT to be displayed`, async () => {
        await expect(containerElement()).not.toBeVisible();
    }),
    expectPromptContains: async (text) => await test.step(`${NAME} - Expect prompt to contain '${text}'`, async () => {
        await expect(containerElement()).toContainText(text);
    }),

    clickYesButton: async () => await test.step(`${NAME} - Click Yes Button`, async () => {
        await YesNoDialog.expectVisible();
        await YesNoDialog.yesButton().click();
    }),

    clickNoButton: async () => await test.step(`${NAME} - Click No Button`, async () => {
        await YesNoDialog.expectVisible();
        await YesNoDialog.noButton().click();
    }),

};