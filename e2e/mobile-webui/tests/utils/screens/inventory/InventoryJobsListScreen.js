import { page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { InventoryJobScreen } from './InventoryJobScreen';

const NAME = 'InventoryJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');

export const InventoryJobsListScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    startJob: async ({ index }) => await test.step(`${NAME} - Start job by index ${index - 1}`, async () => {
        await locateJobButtons({ index }).tap()
        await InventoryJobScreen.waitForScreen();
        return {
            jobId: await InventoryJobScreen.getJobId(),
        }
    }),
};

const locateJobButtons = ({ index } = {}) => {
    return page.locator('.wflauncher-button').nth(index - 1);
};
