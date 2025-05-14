import { test } from '../../../../playwright.config';
import { ID_BACK_BUTTON, page } from '../../common';
import { expect } from '@playwright/test';
import { DistributionStepDropToScreen } from './DistributionStepDropToScreen';
import { DistributionLineScreen } from './DistributionLineScreen';
import { UnpickDialog } from '../../dialogs/UnpickDialog';

const NAME = 'DistributionStepScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionStepScreen');

export const DistributionStepScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanDropToLocator: async ({ dropToLocatorQRCode }) => await test.step(`${NAME} - Scan Drop To Locator`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.getByTestId('scanDropToLocator-button').tap();
        await DistributionStepDropToScreen.waitForScreen();
        await DistributionStepDropToScreen.typeQRCode(dropToLocatorQRCode);
        await DistributionStepScreen.waitForScreen();
    }),

    unpick: async () => await test.step(`${NAME} - Unpick`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.getByTestId('unpick-button').tap();
        await UnpickDialog.waitForDialog();
        await UnpickDialog.clickSkipScanningTargetHUButton();
        await DistributionLineScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionLineScreen.waitForScreen();
    }),
};