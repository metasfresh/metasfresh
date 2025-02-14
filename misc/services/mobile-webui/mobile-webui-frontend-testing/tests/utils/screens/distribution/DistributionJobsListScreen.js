import {test} from "../../../../playwright.config";
import {page, SLOW_ACTION_TIMEOUT} from "../../common";
import {DistributionJobScreen} from "./DistributionJobScreen";
import {DistributionJobsListFiltersScreen} from "./DistributionJobsListFiltersScreen";

export const DistributionJobsListScreen = {
    waitForScreen: async () => await test.step('Wait for distribution jobs list screen', async () => {
        await page.locator('#WFLaunchersScreen').waitFor({timeout: SLOW_ACTION_TIMEOUT});
        await page.locator('.loading').waitFor({state: 'detached', timeout: SLOW_ACTION_TIMEOUT});
    }),

    filterByFacetId: async ({
                                facetId,
                                expectHitCount
                            }) => await test.step(`Filter distribution jobs by facet ${facetId}`, async () => {
        await page.locator('#filter-button').tap();
        await DistributionJobsListFiltersScreen.waitForScreen();
        await DistributionJobsListFiltersScreen.filterByFacetId({facetId, expectHitCount});
        await DistributionJobsListScreen.waitForScreen();
    }),

    startJob: async ({launcherTestId}) => {
        return await test.step(`Start distribution job for testId ${launcherTestId}`, async () => {
            await page.getByTestId(launcherTestId).tap();
            await DistributionJobScreen.waitForScreen();
        });
    },
};