import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionJobScreen } from "./DistributionJobScreen";
import { DistributionJobsListFiltersScreen } from "./DistributionJobsListFiltersScreen";
import { ApplicationsListScreen } from '../ApplicationsListScreen';

const NAME = 'DistributionJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');

export const DistributionJobsListScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    filterByFacetId: async ({
                                facetId,
                                expectHitCount
                            }) => await test.step(`${NAME} - Filter by facet "${facetId}"`, async () => {
        await page.locator('#filter-button').tap();
        await DistributionJobsListFiltersScreen.waitForScreen();
        await DistributionJobsListFiltersScreen.filterByFacetId({ facetId, expectHitCount });
        await DistributionJobsListScreen.waitForScreen();
    }),

    startJob: async ({ launcherTestId }) => {
        return await test.step(`${NAME} Start job for testId "${launcherTestId}"`, async () => {
            await page.getByTestId(launcherTestId).tap();
            await DistributionJobScreen.waitForScreen();
        });
    },

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),
};