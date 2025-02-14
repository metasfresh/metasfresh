import { test } from "../../../../playwright.config";
import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionLineScreen } from './DistributionLineScreen';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';

export const DistributionJobScreen = {
    waitForScreen: async () => await test.step(`Wait for Distribution Job screen`, async () => {
        await page.locator('#WFProcessScreen').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLineButton: async ({ index }) => await test.step(`Click line ${index}`, async () => {
        await page.getByTestId(`line-${index}-button`).tap({ timeout: FAST_ACTION_TIMEOUT });
        await DistributionLineScreen.waitForScreen();
    }),

    complete: async () => await test.step(`Complete Distribution Job`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await DistributionJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};
