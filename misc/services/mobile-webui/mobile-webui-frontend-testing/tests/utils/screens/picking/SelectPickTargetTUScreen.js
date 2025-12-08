import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";

const NAME = 'SelectPickTargetTUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectPickTargetScreen');

export const SelectPickTargetTUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickTUButton: async ({ tu }) => await test.step(`${NAME} - Click TU button`, async () => {
        await page.locator('button').filter({ hasText: tu }).tap();
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target TU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};