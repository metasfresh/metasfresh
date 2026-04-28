import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'DistributionStepDropToScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionStepDropToScreen');

export const DistributionStepDropToScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),
};