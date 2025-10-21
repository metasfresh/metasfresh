import { test } from '../../../playwright.config';
import { FAST_ACTION_TIMEOUT, page } from '../common';
import { expect } from '@playwright/test';

const NAME = 'BarcodeScannerComponent';

export const BarcodeScannerComponent = {
    type: async (scannedCode) => await test.step(`${NAME} - Type scanned code`, async () => {
        console.log('Scanning scanned code:\n' + scannedCode);

        await page.locator('#input-text').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
        // await page.locator('.barcode-scanner').waitFor({timeout: FAST_ACTION_TIMEOUT});

        // Additional wait to ensure event listeners are attached
        await page.waitForTimeout(200);

        await page.keyboard.type(`${scannedCode}`, { delay: 10 });
    }),

    waitForInputFieldToGetEmpty: async () => await test.step(`${NAME} - Wait for input field to get empty`, async () => {
        await expect(page.locator('#input-text')).toHaveValue('');
    }),
}