import { test } from '../../../../playwright.config';
import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page } from '../../common';
import { DistributionLinePickFromScreen } from './DistributionLinePickFromScreen';
import { DistributionStepScreen } from './DistributionStepScreen';
import { expect } from '@playwright/test';
import { DistributionJobScreen } from './DistributionJobScreen';

export const DistributionLineScreen = {
    waitForScreen: async () => await test.step(`Wait for Distribution Line Screen`, async () => {
        await page.locator('#DistributionLineScreen').waitFor();
    }),

    expectVisible: async () => await test.step('Expecting Distribution Line Screen displayed', async () => {
        await expect(page.locator('#DistributionLineScreen')).toBeVisible();
    }),

    scanHUToMove: async ({ huQRCode }) => await test.step(`Scan QR Code`, async () => {
        await page.getByTestId('scanQRCode-button').tap(); // click Scan QR Code button
        await DistributionLinePickFromScreen.waitForScreen();
        await DistributionLinePickFromScreen.typeQRCode(huQRCode);
        await DistributionLineScreen.waitForScreen();
    }),

    clickStepButton: async ({ index }) => await test.step(`Click step ${index}`, async () => {
        await DistributionLineScreen.expectVisible();
        await page.getByTestId(`step-${index}-button`).tap({ timeout: FAST_ACTION_TIMEOUT });
        await DistributionStepScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`Go back`, async () => {
        await DistributionLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionJobScreen.waitForScreen();
    }),

};