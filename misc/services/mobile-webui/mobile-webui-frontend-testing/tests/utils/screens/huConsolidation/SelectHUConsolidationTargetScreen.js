import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { test } from '../../../../playwright.config';
import { HUConsolidationJobScreen } from './HUConsolidationJobScreen';

const NAME = 'SelectHUConsolidationTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectHUConsolidationTargetScreen');

export const SelectHUConsolidationTargetScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickLUButton: async ({ lu }) => await step(`${NAME} - Click LU button`, async () => {
        await page.locator('button').filter({ hasText: lu }).tap();
    }),

    clickPrintLabelButton: async () => await step(`${NAME} - Click Print Label button`, async () => {
        await page.locator('#printLabel-button').tap();

        // Wait for the button to be disabled (processing)
        await expect(page.locator('#printLabel-button')).toBeDisabled();

        // Wait for the button to be enabled again
        await page.locator('#printLabel-button').waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.locator('#printLabel-button')).toBeEnabled({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    clickCloseTargetButton: async () => await step(`${NAME} - Click Close Target LU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await SelectHUConsolidationTargetScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),

};
