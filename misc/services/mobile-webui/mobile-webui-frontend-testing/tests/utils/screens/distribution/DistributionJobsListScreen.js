import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionJobScreen } from "./DistributionJobScreen";
import { DistributionJobsListFiltersScreen } from "./DistributionJobsListFiltersScreen";
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { expect } from '@playwright/test';
import { expectClasses } from '../../expectations';

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

    expectJobButtons: async (expectationsArray) => await test.step(`${NAME} - Expect ${expectationsArray.length} job buttons`, async () => {
        await test.step(`Wait for all expected buttons to be attached`, async () => {
            for (const expectation of expectationsArray) {
                await locateJobButtons(expectation).waitFor({ state: 'attached' });
            }
        });

        //
        // Check it again to make sure all expected buttons are still there and there is one of each
        for (let i = 0; i < expectationsArray.length; i++) {
            const expectation = expectationsArray[i];
            await expectJobButton({
                name: `${i + 1}/${expectationsArray.length}`,
                button: locateJobButtons(expectation),
                expectation
            });
        }

        //
        // Make sure we have the expected number of buttons
        // NOTE: we do this at the end because expect does not wait for the elements to stabilize
        await expect(locateJobButtons()).toHaveCount(expectationsArray.length);
    }),

    expectHeaderProperty: async ({ caption, value }) => await test.step(`${NAME} - Check header property '${caption}'='${value}'`, async () => {
        const row = await page.locator(
            `tr:has(th:has-text("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),
};

const locateJobButtons = ({ index, testId } = {}) => {
    let selector = '.wflauncher-button';
    if (testId != null) {
        selector += `[data-testid="${testId}"]`;
    }

    let locator = page.locator(selector);

    if (index != null) {
        locator = locator.nth(index - 1);
    }

    return locator;
};

const expectJobButton = async ({ name, button, expectation }) => await test.step(`Expect job button ${name}`, async () => {
    await button.waitFor({ state: 'attached' });
    await expect(button).toHaveCount(1);

    if (expectation.caption != null) {
        await expect(button).toHaveText(expectation.caption);
    }

    if (expectation.disabled != null) {
        if (expectation.disabled) {
            await expect(button).toBeDisabled();
        } else {
            await expect(button).toBeEnabled();
        }
    }

    if (expectation.alreadyStarted != null) {
        const indicatorLocator = button.locator(`[data-testid="indicator2"]`);
        if (expectation.alreadyStarted) {
            await expectClasses({ locator: indicatorLocator, expectedClasses: 'fa-lock indicator-box' });
        } else {
            await expect(indicatorLocator).toHaveCount(0);
        }
    }
});
