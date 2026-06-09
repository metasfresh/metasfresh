import { test } from "../../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';

const NAME = 'DistributionJobsDropAllScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionJobsDropAllScreen');

export const DistributionJobsDropAllScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    dropAll: async ({ dropToQRCode }) => await test.step(`${NAME} - Drop all to scanned locator`, async () => {
        await BarcodeScannerComponent.type(dropToQRCode);
        await DistributionJobsListScreen.waitForScreen();
    }),
};
