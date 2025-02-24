import { test } from '../../../../playwright.config';
import { page } from '../../common';

const NAME = 'DistributionStepDropToScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionStepDropToScreen');

export const DistributionStepDropToScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
        await containerElement().waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        console.log('Scanning QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),
};