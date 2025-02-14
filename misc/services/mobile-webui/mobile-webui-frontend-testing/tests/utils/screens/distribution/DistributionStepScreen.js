import { test } from '../../../../playwright.config';
import { ID_BACK_BUTTON, page } from '../../common';
import { expect } from '@playwright/test';
import { DistributionStepDropToScreen } from './DistributionStepDropToScreen';
import { DistributionLineScreen } from './DistributionLineScreen';
import { PickingJobScreen } from '../picking/PickingJobScreen';

export const DistributionStepScreen = {
    waitForScreen: async () => await test.step(`Wait for Distribution Step Screen`, async () => {
        await page.locator('#DistributionStepScreen').waitFor();
    }),

    expectVisible: async () => await test.step('Expecting Distribution Step Screen displayed', async () => {
        await expect(page.locator('#DistributionStepScreen')).toBeVisible();
    }),

    scanDropToLocator: async ({ dropToLocatorQRCode }) => await test.step(`Scan Drop To Locator`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.getByTestId('scanDropToLocator-button').tap();
        await DistributionStepDropToScreen.waitForScreen();
        await DistributionStepDropToScreen.typeQRCode(dropToLocatorQRCode);
        await DistributionStepScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`Go back`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionLineScreen.waitForScreen();
    }),
};