import { page } from '../../../common';
import { test } from '../../../../../playwright.config';

const NAME = 'ManufacturingReceiptScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ManufacturingReceiptScanScreen');

export const ManufacturingReceiptScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await page.type('#input-text', qrCode);
    }),
}