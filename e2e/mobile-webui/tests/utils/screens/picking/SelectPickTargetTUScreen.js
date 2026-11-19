import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";

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

    clickTUButton: async ({ tu }) => await test.step(`${NAME} - Click TU button`, async () => {
        await page.locator('button').filter({ hasText: tu }).tap();
    }),

    /** @returns {Promise<boolean>} true when the target screen offers a button whose text contains the given TU (packing instructions) name */
    hasTUButton: async ({ tu }) => (await page.locator('button').filter({ hasText: tu }).count()) > 0,

    /** Taps the first target button of the screen (used when no button carries the expected TU name). */
    clickFirstTargetButton: async () => await test.step(`${NAME} - Click first target button`, async () => {
        await page.locator('#SelectPickTargetScreen button').first().tap();
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target TU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};