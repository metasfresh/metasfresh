import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'DistributionDropAllToScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionDropAllToScreen');

export const DistributionDropAllToScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),
    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),
};
