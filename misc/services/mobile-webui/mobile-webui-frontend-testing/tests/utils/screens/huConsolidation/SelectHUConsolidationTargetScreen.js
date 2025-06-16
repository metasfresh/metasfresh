import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';

const NAME = 'SelectHUConsolidationTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectHUConsolidationTargetScreen');

export const SelectHUConsolidationTargetScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLUButton: async ({ lu }) => await test.step(`${NAME} - Click LU button`, async () => {
        await page.locator('button').filter({ hasText: lu }).tap();
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target LU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};