import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { HUConsolidationJobScreen } from './HUConsolidationJobScreen';

const NAME = 'HUConsolidationJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');

export const HUConsolidationJobsListScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    startJob: async ({ customerLocationId }) => await test.step(`${NAME} - Start job`, async () => {
        await locateJobButtons({ customerLocationId }).tap()
        await HUConsolidationJobScreen.waitForScreen();
        return {
            pickingJobId: await HUConsolidationJobScreen.getJobId(),
        }
    }),

    /** Expect no job button for the given ship-to (e.g. after Fertigstellen completed its job). */
    expectJobNotListed: async ({ customerLocationId }) => await test.step(`${NAME} - Expect job of ship-to ${customerLocationId} not listed`, async () => {
        await expect(locateJobButtons({ customerLocationId })).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    }),
};

const locateJobButtons = ({ customerLocationId } = {}) => {
    let selector = '.wflauncher-button';
    if (customerLocationId != null) {
        selector += `[data-bpartnerlocationid="${customerLocationId}"]`;
    }

    return page.locator(selector);
};
