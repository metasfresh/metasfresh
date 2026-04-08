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
            // Terminate with Enter, matching real hardware scanner behavior (DataWedge default).
            // Without this, rapid consecutive scans can merge buffers in useKeyboardBarcodeReader
            // because the rate-drop flush (300ms) hasn't fired yet.
            document.dispatchEvent(new KeyboardEvent('keydown', { key: 'Enter', bubbles: true }));
            document.dispatchEvent(new KeyboardEvent('keyup', { key: 'Enter', bubbles: true }));
        }, scannedCode);
    }),

    typeViaIME: async (params) => await test.step(`${NAME} - Type scanned code via IME`, async () => {
        let scannedCode;
        let testId;
        if (typeof params === 'string') {
            scannedCode = params;
            testId = undefined;
        } else if (params && typeof params === 'object') {
            scannedCode = params.scannedCode;
            testId = params.testId;
        } else {
            throw new Error("Invalid argument provided to the 'typeViaIME' function. Must be a string or an object with { scannedCode }.");
        }

        if (!scannedCode) {
            throw new Error("Invalid scannedCode provided. Must not be empty.");
        }

        console.log('Scanning scanned code via IME:\n' + scannedCode);

        await BarcodeScannerComponent.waitToAttach({ testId });

        let selector = '#input-text';
        if (testId) {
            selector += `[data-testid="${testId}"]`;
        }

        // Simulate DataWedge IME text injection: sets value + fires input/change events, no keydown events.
        // Uses evaluate() because the input may be type="hidden" (when isShowInputText=false),
        // and Playwright's fill() requires visible elements. DataWedge injects via InputConnection
        // regardless of visibility.
        await page.evaluate(({ selector, value }) => {
            const el = document.querySelector(selector);
            const nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;
            nativeInputValueSetter.call(el, value);
            el.dispatchEvent(new Event('input', { bubbles: true }));
            el.dispatchEvent(new Event('change', { bubbles: true }));
        }, { selector, value: scannedCode });
        // DataWedge appends Enter after text injection to trigger submission
        await page.evaluate((selector) => {
            const el = document.querySelector(selector);
            el.dispatchEvent(new KeyboardEvent('keyup', { key: 'Enter', bubbles: true }));
        }, selector);
    }),

    typeBatch: async ({ codes, testId }) => await test.step(
        `${NAME} - Type batch of ${codes.length} scanned codes`,
        async () => {
            if (!Array.isArray(codes) || codes.length === 0) {
                throw new Error("Invalid codes provided. Must be a non-empty array.");
            }

            console.log(`Scanning batch of ${codes.length} codes:\n` + codes.join('\n'));

            await BarcodeScannerComponent.waitToAttach({ testId });

            // Mirrors DataWedge RFID keystroke output: all tags typed rapidly
            // with Enter separator between them (default DataWedge config).
            // Same dispatch pattern as type(), but with Enter after each code.
            await page.evaluate((codesArr) => {
                for (const code of codesArr) {
                    for (const char of code) {
                        document.dispatchEvent(new KeyboardEvent('keydown', { key: char, bubbles: true }));
                        document.dispatchEvent(new KeyboardEvent('keyup', { key: char, bubbles: true }));
                    }
                    document.dispatchEvent(new KeyboardEvent('keydown', { key: 'Enter', bubbles: true }));
                    document.dispatchEvent(new KeyboardEvent('keyup', { key: 'Enter', bubbles: true }));
                }
            }, codes);
        }
    ),

    waitForInputFieldToGetEmpty: async () => await test.step(`${NAME} - Wait for input field to get empty`, async () => {
        await expect(page.locator('#input-text')).toHaveValue('');
    }),
}
