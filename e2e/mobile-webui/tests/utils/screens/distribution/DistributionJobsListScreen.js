import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT, VERY_FAST_ACTION_TIMEOUT, holdForCaptureIfEnabled } from "../../common";
import { DistributionJobScreen } from "./DistributionJobScreen";
import { DistributionJobsListFiltersScreen } from "./DistributionJobsListFiltersScreen";
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { expect } from '@playwright/test';
import { expectClasses } from '../../expectations';
import { DistributionJobsDropAllScreen } from './DistributionJobsDropAllScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { JOB_START_ARRIVAL_TIMEOUT, JOB_START_TAP_ATTEMPTS, recoverToLauncherList } from '../jobStartRecovery';

const NAME = 'DistributionJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');
// The job (workflow-process) screen reached after starting a launcher. This id mirrors the private
// containerElement in DistributionJobScreen.js — keep the two in sync if that screen id changes.
/** @returns {import('@playwright/test').Locator} */
const jobScreenElement = () => page.locator('#WFProcessScreen');

export const DistributionJobsListScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
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

    scanTrolley: async ({ scannedCode, expectHeader }) => await test.step(`${NAME} - Scan trolley`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: scannedCode });

        if (expectHeader !== undefined) {
            await DistributionJobsListScreen.expectTrolley({ value: expectHeader });
        }
    }),
    expectTrolley: async ({ value }) => await test.step(`${NAME} - Expect trolley button contains "${value}"`, async () => {
        const trolleyButton = page.getByTestId('scanTrolley-button');
        await expect(trolleyButton).toContainText(value);
    }),

    startJob: async ({ launcherTestId }) => {
        return await test.step(`${NAME} Start job for testId "${launcherTestId}"`, async () => {
            for (let attempt = 1; attempt <= JOB_START_TAP_ATTEMPTS; attempt++) {
                await page.getByTestId(launcherTestId).tap();
                const arrived = await jobScreenElement()
                    .waitFor({ state: 'attached', timeout: JOB_START_ARRIVAL_TIMEOUT })
                    .then(() => true, () => false);
                if (arrived || attempt === JOB_START_TAP_ATTEMPTS) {
                    break;
                }
                if ((await recoverToLauncherList({ applicationId: 'distribution' })) === 'unknown') {
                    break;
                }
            }
            await DistributionJobScreen.waitForScreen();
        });
    },

    expectJobButtons: async (expectationsArray) => await test.step(`${NAME} - Expect ${expectationsArray.length} job buttons`, async () => {
        await waitForExpectedButtonsVisible(expectationsArray);

        //
        // Check it again to make sure all expected buttons are still there and there is one of each
        for (let i = 0; i < expectationsArray.length; i++) {
            const expectation = expectationsArray[i];
            await expectJobButton({
                name: `${i + 1}/${expectationsArray.length}`,
                button: locateJobButtons({ index: i + 1 }),
                expectation
            });
        }

        //
        // Make sure we have the expected number of buttons
        // NOTE: we do this at the end because expect does not wait for the elements to stabilize
        await expect(locateJobButtons()).toHaveCount(expectationsArray.length);

        // Nothing happens here unless a capture run asked for it (UAT_CAPTURE): the offered jobs are
        // the result this screen exists to show, and the checks above can settle faster than the video
        // recorder samples a frame, leaving them off the recording.
        await holdForCaptureIfEnabled();
    }),

    // Order-INDEPENDENT variant of expectJobButtons: use for *filtering* assertions (which
    // launchers are offered), NOT ordering assertions. Each expectation is located by its
    // testId rather than by slot, so a non-deterministic launcher render order cannot flake
    // the check (the rendered launcher order has no id tiebreaker and can swap two launchers
    // that are both present). Still asserts exact membership (each expected
    // testId present exactly once, per-button props match) and exact count (no extras) — only
    // the slot order is relaxed. Every expectation must carry a testId to be locatable.
    expectJobButtonsInAnyOrder: async (expectationsArray) => await test.step(`${NAME} - Expect ${expectationsArray.length} job buttons (any order)`, async () => {
        // Order-independent matching locates each button by its testId, so every expectation
        // must carry one — otherwise locateJobButtons() would fall back to matching ALL buttons
        // and silently skip the identity check. Fail fast rather than degrade to "match anything".
        for (const expectation of expectationsArray) {
            if (expectation.testId == null) {
                throw new Error('expectJobButtonsInAnyOrder: every expectation must carry a testId');
            }
        }

        await waitForExpectedButtonsVisible(expectationsArray);

        //
        // Each expected button exists exactly once and matches its per-button expectations.
        // Located by testId (order-independent), so slot order is irrelevant.
        for (const expectation of expectationsArray) {
            await expectJobButton({
                name: `${expectation.testId}`,
                button: locateJobButtons({ testId: expectation.testId }),
                expectation
            });
        }

        //
        // Make sure we have the expected number of buttons (no unexpected extras).
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

    expectDropAllButton: async ({ enabled, visible }) => await test.step(`${NAME} - Expect Drop All button`, async () => {
        const dropAllButton = dropAllButtonLocator();

        if (visible != null) {
            if (visible) {
                await dropAllButton.waitFor({ state: 'attached', timeout: VERY_FAST_ACTION_TIMEOUT });
            } else {
                await dropAllButton.waitFor({ state: 'detached', timeout: VERY_FAST_ACTION_TIMEOUT });
            }
        }

        if (enabled != null) {
            if (enabled) {
                await expect(dropAllButton).toBeEnabled({ timeout: VERY_FAST_ACTION_TIMEOUT });
            } else {
                await expect(dropAllButton).toBeDisabled({ timeout: VERY_FAST_ACTION_TIMEOUT });
            }
        }
    }),

    dropAll: async ({ dropToQRCode }) => await test.step(`${NAME} - Drop all jobs`, async () => {
        const dropAllButton = dropAllButtonLocator();
        await expect(dropAllButton).toBeEnabled({ timeout: VERY_FAST_ACTION_TIMEOUT });
        await dropAllButton.tap();
        await DistributionJobsDropAllScreen.waitForScreen();
        await DistributionJobsDropAllScreen.dropAll({ dropToQRCode })
    }),

    clickReleaseTrolleyButton: async () => await test.step(`${NAME} - Click 'Release trolley' footer button`, async () => {
        await page.getByTestId('release-trolley-button').tap();
    }),

    expectReleaseTrolleyButtonVisible: async ({ visible }) => await test.step(`${NAME} - Expect release-trolley-button visible=${visible}`, async () => {
        const btn = page.getByTestId('release-trolley-button');
        if (visible) {
            await expect(btn).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
        } else {
            await expect(btn).not.toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
        }
    }),

    expectTrolleyScanScreen: async () => await test.step(`${NAME} - Expect trolley scan screen (no trolley held)`, async () => {
        // After release, the screen returns to the trolley-scan state.
        // The barcode scanner input (#input-text) should be attached, waiting for a trolley scan.
        await page.locator('#input-text').waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};

//
//
//--------------------------------------------------------------------------
//
//

// Wait until every expected launcher button is VISIBLE (painted, spinner gone) — not merely
// attached — so the worker actually SEES the offered job. Shared by expectJobButtons and
// expectJobButtonsInAnyOrder; order-independent (each expectation located on its own).
const waitForExpectedButtonsVisible = async (expectationsArray) => await test.step(`Wait for all expected buttons to be visible`, async () => {
    for (const expectation of expectationsArray) {
        await locateJobButtons(expectation).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }
});

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
    await button.waitFor({ state: 'visible', timeout: VERY_FAST_ACTION_TIMEOUT });
    await expect(button).toHaveCount(1);

    if (expectation.testId != null) {
        await expect(button).toHaveAttribute('data-testid', expectation.testId);
    }

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

//
//
//--------------------------------------------------------------------------
//
//

const dropAllButtonLocator = () => {
    return page.getByTestId('dropAll-button');
};
