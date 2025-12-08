import { page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { expect } from '@playwright/test';
import { test } from '../../../../playwright.config';

const NAME = 'ScanHUConsolidationTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ScanHUConsolidationTargetScreen');

export const ScanHUConsolidationTargetScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    typeQRCode: async ({ qrCode }) => await step(`${NAME} - Scan QR code`, async () => {
        console.log('Scanning HU QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),
};
