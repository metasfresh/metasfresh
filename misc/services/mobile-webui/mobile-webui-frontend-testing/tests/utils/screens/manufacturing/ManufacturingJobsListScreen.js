import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { ManufacturingJobScreen } from './ManufacturingJobScreen';
import { expect } from '@playwright/test';

const NAME = 'ManufacturingJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');

export const ManufacturingJobsListScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    startJob: async ({ documentNo }) => await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
        await page.locator('.wflauncher-button').filter({ hasText: documentNo }).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),
};
