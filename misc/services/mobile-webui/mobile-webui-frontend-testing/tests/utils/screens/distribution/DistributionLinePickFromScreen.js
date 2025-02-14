import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';

export const DistributionLinePickFromScreen = {
    waitForScreen: async () => await test.step(`Wait for Pick From Scan screen`, async () => {
        await page.locator('#DistributionLinePickFromScreen').waitFor({timeout: SLOW_ACTION_TIMEOUT});
    }),

    typeQRCode: async (qrCode) => await test.step(`Type QR Code`, async () => {
        console.log('Scanning QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),

};