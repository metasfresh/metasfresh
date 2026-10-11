import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { ManufacturingJobScreen } from './ManufacturingJobScreen';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { captionStartsWithField } from '../../launcherCaption';

const NAME = 'ManufacturingJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');
// The caption starts with the documentNo (`<documentNo> | <product> | <qty> | <date>`); match it as that
// leading field, never as a substring - see launcherCaption.js.
/** @returns {import('@playwright/test').Locator} */
const jobButton = ({ documentNo }) => page.locator('.wflauncher-button').filter({ hasText: captionStartsWithField(documentNo) });

export const ManufacturingJobsListScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await ManufacturingJobsListScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),

    // Test-setup check: a launcher whose caption contains `text` anywhere (plain substring match) is listed exactly once.
    expectJobButtonContainingText: async ({ text }) => await test.step(`${NAME} - Expect one job button containing '${text}'`, async () => {
        await expect(page.locator('.wflauncher-button').filter({ hasText: text })).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Test-setup check: how many listed launchers contain `text` anywhere in their caption (plain substring match).
    countJobButtonsContainingText: async ({ text }) => await test.step(`${NAME} - Count job buttons containing '${text}'`, async () => {
        return await page.locator('.wflauncher-button').filter({ hasText: text }).count();
    }),

    startJob: async ({ documentNo }) => await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
        await jobButton({ documentNo }).tap();
        await ManufacturingJobScreen.waitForScreen();
        return {
            jobId: await ManufacturingJobsListScreen.getJobId(),
        }
    }),

    getJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/mfg-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

};
