import {test} from "../../../../playwright.config";
import {page} from "../../common";
import {expect} from "@playwright/test";

export const GetQuantityDialog = {
    waitForDialog: async () => await test.step(`Wait for Get Quantity Dialog`, async () => {
        await page.locator('.get-qty-dialog').waitFor();
    }),

    expectQtyEntered: async (expected) => await test.step(`Expect QtyEntered to be '${expected}'`, async () => {
        await expect(page.locator('#qty-input')).toHaveValue(expected);
    }),

    clickDone: async () => await test.step(`Press OK`, async () => {
        await page.locator('#confirmDone-button').tap();
    }),

    clickCancel: async () => await test.step(`Press Cancel`, async () => {
        await page.locator('#cancelText-button').tap();
    }),
};