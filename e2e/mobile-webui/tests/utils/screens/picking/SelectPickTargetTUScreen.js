import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";

const NAME = 'SelectPickTargetTUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectPickTargetScreen');

export const SelectPickTargetTUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        // Use .first() to handle the brief transition where two #SelectPickTargetScreen
        // elements coexist in the DOM (one for LU, one for TU) during navigation.
        await containerElement().first().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts the TU list screen is not on screen. */
    expectNotShown: async () => await test.step(`${NAME} - Expect screen not shown`, async () => {
        await expect(containerElement()).toHaveCount(0);
    }),

    clickTUButton: async ({ tu }) => await test.step(`${NAME} - Click TU button`, async () => {
        await page.locator('button').filter({ hasText: tu }).tap();
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target TU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};