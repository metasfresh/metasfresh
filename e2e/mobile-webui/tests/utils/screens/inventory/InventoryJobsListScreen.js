import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { InventoryJobScreen } from './InventoryJobScreen';
import { ApplicationsListScreen } from '../ApplicationsListScreen';

const NAME = 'InventoryJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');

export const InventoryJobsListScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
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

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await InventoryJobsListScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),
};

const locateJobButtons = ({ index } = {}) => {
    return page.locator('.wflauncher-button').nth(index - 1);
};
