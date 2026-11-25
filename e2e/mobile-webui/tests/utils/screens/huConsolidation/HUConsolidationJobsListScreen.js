import { page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
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

    /**
     * Runs `trigger` (the action that leaves the job screen for the job list, e.g. Fertigstellen -> Ja), then expects
     * no job button for the given ship-to.
     *
     * The absence check is only meaningful once the list has been loaded: a list that is still loading has no job button
     * either. `waitForScreen` alone is not enough: the list screen is first rendered without its spinner, and the spinner
     * appears only after the launchers request was issued, so `.loading` can be "detached" before the load even started.
     * So we wait for the launchers query response (registered before `trigger`, so it cannot be missed), then for the
     * spinner to be gone, and only then assert the absence.
     */
    expectJobNotListedAfter: async ({ customerLocationId, trigger }) => await test.step(`${NAME} - Expect job of ship-to ${customerLocationId} not listed (after the list was loaded)`, async () => {
        const launchersLoaded = page.waitForResponse(
            (response) => response.url().includes('/userWorkflows/launchers/query') && response.request().method() === 'POST',
            { timeout: VERY_SLOW_ACTION_TIMEOUT },
        );
        await trigger();
        await launchersLoaded;
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
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
