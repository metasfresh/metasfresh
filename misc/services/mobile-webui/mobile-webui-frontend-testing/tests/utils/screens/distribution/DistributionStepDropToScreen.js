import { test } from '../../../../playwright.config';
import { page } from '../../common';

export const DistributionStepDropToScreen = {
    waitForScreen: async () => await test.step(`Wait for Distribution Step Drop To Screen`, async () => {
        await page.locator('#DistributionStepDropToScreen').waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`Type QR Code`, async () => {
        console.log('Scanning QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),
};