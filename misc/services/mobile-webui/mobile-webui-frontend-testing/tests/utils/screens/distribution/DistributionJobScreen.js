import { test } from "../../../../playwright.config";
import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionLineScreen } from './DistributionLineScreen';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';

const NAME = 'DistributionJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const DistributionJobScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLineButton: async ({ index }) => await test.step(`${NAME} - Click line ${index}`, async () => {
        await page.getByTestId(`line-${index}-button`).tap({ timeout: FAST_ACTION_TIMEOUT });
        await DistributionLineScreen.waitForScreen();
    }),

    complete: async () => await test.step(`${NAME} - Complete`, async () => {
        await clickCompleteButton();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await DistributionJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};

const clickCompleteButton = async () => await test.step(`${NAME} - Click Complete button`, async () => {
    await page.locator('#last-confirm-button').tap();
});
