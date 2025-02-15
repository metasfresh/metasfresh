import { test } from "../../../../playwright.config";
import { page } from "../../common";
import { expect } from "@playwright/test";

const NAME = 'GetQuantityDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.get-qty-dialog');

export const GetQuantityDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    expectQtyEntered: async (expected) => await test.step(`${NAME} - Expect QtyEntered to be '${expected}'`, async () => {
        await expect(page.locator('#qty-input')).toHaveValue(expected);
    }),

    clickDone: async () => await test.step(`${NAME} - Press OK`, async () => {
        await page.locator('#confirmDone-button').tap();
    }),

    clickCancel: async () => await test.step(`${NAME} - Press Cancel`, async () => {
        await page.locator('#cancelText-button').tap();
    }),
};