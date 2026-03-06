import { test } from "../../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionJobsListScreen } from "./DistributionJobsListScreen";
import { expect } from "@playwright/test";

export const DistributionJobsListFiltersScreen = {
    waitForScreen: async () => await test.step('Wait for distribution jobs list filters screen', async () => {
        await page.locator('#WFLaunchersFiltersScreen').waitFor();
        await DistributionJobsListFiltersScreen.waitLoadComplete();
    }),

    waitLoadComplete: async () => await test.step('Wait for load complete', async () => {
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    filterByFacetId: async ({ facetId, expectHitCount }) => await test.step(`Filter by facet ${facetId}`, async () => {
        const facetButton = page.locator(`[data-testid="${facetId}"]`);
        await facetButton.tap();
        await DistributionJobsListFiltersScreen.waitLoadComplete();

        const showResultsButton = page.locator('#showResults');
        if (expectHitCount != null) {
            await expect(showResultsButton).toHaveAttribute("data-hitcount", String(expectHitCount));
        }

        await showResultsButton.tap();
        await DistributionJobsListScreen.waitForScreen();
    }),

};