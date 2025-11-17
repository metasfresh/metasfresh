import { test } from '../../../playwright.config';
import { FAST_ACTION_TIMEOUT, page } from '../common';
import { expect } from '@playwright/test';

const NAME = 'BarcodeScannerComponent';

export const BarcodeScannerComponent = {
    type: async (scannedCode) => await test.step(`${NAME} - Type scanned code`, async () => {
        console.log('Scanning scanned code:\n' + scannedCode);

        await page.locator('#input-text').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });

        // NOTE page.keyboard.type is very slow, so we have to send the keyboard events directly, 
        // Now a QR code is typed in 30ms instead of 5 seconds.
        // await page.keyboard.type(`${scannedCode}`, { delay: delay != null ? delay : TYPE_DELAY_MILLIS });
        await page.evaluate((code) => {
            for (const char of code) {
                document.dispatchEvent(new KeyboardEvent('keydown', { key: char, bubbles: true }));
                document.dispatchEvent(new KeyboardEvent('keyup', { key: char, bubbles: true }));
            }
        }, scannedCode);
    }),

    waitForInputFieldToGetEmpty: async () => await test.step(`${NAME} - Wait for input field to get empty`, async () => {
        await expect(page.locator('#input-text')).toHaveValue('');
    }),
}
