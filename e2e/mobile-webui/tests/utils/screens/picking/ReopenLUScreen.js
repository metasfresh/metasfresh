import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";

const NAME = 'ReopenLUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReopenLUScreen');

export const SelectPickTargetLUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};