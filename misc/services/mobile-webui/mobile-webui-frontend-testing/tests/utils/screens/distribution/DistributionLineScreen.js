import { test } from '../../../../playwright.config';
import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page } from '../../common';
import { DistributionLinePickFromScreen } from './DistributionLinePickFromScreen';
import { DistributionStepScreen } from './DistributionStepScreen';
import { expect } from '@playwright/test';
import { DistributionJobScreen } from './DistributionJobScreen';
import { GetQuantityDialog } from '../picking/GetQuantityDialog';

const NAME = 'DistributionLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionLineScreen');

export const DistributionLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanHUToMove: async ({ huQRCode, qtyToMove, expectedQtyToMove }) => await test.step(`${NAME} - Scan QR Code`, async () => {
        await page.getByTestId('scanQRCode-button').tap(); // click Scan QR Code button
        await DistributionLinePickFromScreen.waitForScreen();
        await DistributionLinePickFromScreen.typeQRCode(huQRCode);
        await GetQuantityDialog.fillAndPressDone({ qtyEntered: qtyToMove, expectQtyEntered: expectedQtyToMove });
        await DistributionLineScreen.waitForScreen();
    }),

    clickStepButton: async ({ index }) => await test.step(`${NAME} - Click step ${index}`, async () => {
        await DistributionLineScreen.expectVisible();
        await page.getByTestId(`step-${index}-button`).tap({ timeout: FAST_ACTION_TIMEOUT });
        await DistributionStepScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await DistributionLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionJobScreen.waitForScreen();
    }),

};