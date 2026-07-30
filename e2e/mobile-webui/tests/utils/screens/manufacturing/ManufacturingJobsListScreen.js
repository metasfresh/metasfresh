import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { ManufacturingJobScreen } from './ManufacturingJobScreen';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';

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

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await ManufacturingJobsListScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),

    startJob: async ({ documentNo }) => await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
        await page.locator('.wflauncher-button').filter({ hasText: documentNo }).tap();
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

    expectHeaderProperty: async ({ caption, value }) => await test.step(`${NAME} - Check header property '${caption}'='${value}'`, async () => {
        const row = await page.locator(
            `tr:has(th:has-text("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),

    // The operator's current workstation as THIS screen displays it (GET /api/v2/workstation).
    expectCurrentWorkstation: async (name) => await test.step(`${NAME} - Expect header workstation = '${name}'`, async () => {
        await ManufacturingJobsListScreen.expectHeaderProperty({ caption: 'Workstation', value: name });
    }),

    // The operator's current ACTIVE workplace as THIS screen displays it (GET /api/v2/workplace).
    expectCurrentWorkplace: async (name) => await test.step(`${NAME} - Expect header workplace = '${name}'`, async () => {
        await ManufacturingJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: name });
    }),

};
