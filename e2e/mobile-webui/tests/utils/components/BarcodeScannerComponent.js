import { test } from '../../../playwright.config';
import { FAST_ACTION_TIMEOUT, page } from '../common';
import { expect } from '@playwright/test';

const NAME = 'BarcodeScannerComponent';

export const BarcodeScannerComponent = {
    waitToAttach: async ({ testId }) => await test.step(`${NAME} - Wait for input element to attach  (${testId})`, async () => {
        let selector = '#input-text';
        if (testId) {
            selector += `[data-testid="${testId}"]`;
        }

        await page.locator(selector).waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
    }),
    type: async (params) => await test.step(`${NAME} - Type scanned code`, async () => {
        let scannedCode;
        let testId;
        if (typeof params === 'string') {
            scannedCode = params;
            testId = undefined;
        } else if (params && typeof params === 'object') {
            scannedCode = params.scannedCode;
            testId = params.testId;
        } else {
            throw new Error("Invalid argument provided to the 'type' function. Must be a string or an object with { scannedCode }.");
        }

        if (!scannedCode) {
            throw new Error("Invalid scannedCode provided. Must not be empty.");
        }

        console.log('Scanning scanned code:\n' + scannedCode);

        await BarcodeScannerComponent.waitToAttach({ testId });

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
