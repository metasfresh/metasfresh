import { test } from '../../../playwright.config';
import { FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT, page } from '../common';
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
    expectAttached: async ({ testId, timeout = SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect input element attached (${testId})`, async () => {
        let selector = '#input-text';
        if (testId) {
            selector += `[data-testid="${testId}"]`;
        }
        await expect(page.locator(selector)).toBeAttached({ timeout });
    }),
    expectNotAttached: async ({ testId, timeout = FAST_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect input element NOT attached (${testId})`, async () => {
        let selector = '#input-text';
        if (testId) {
            selector += `[data-testid="${testId}"]`;
        }
        await expect(page.locator(selector)).not.toBeAttached({ timeout });
    }),
    // Simulates a hardware/wedge keyboard scan by dispatching keydown/keyup for each character.
    //
    // Optional mid-scan gap (object form only): { scannedCode, testId, gapAtIndex, gapMs }
    //   gapAtIndex - split the code before this character index and insert ONE real wall-clock pause
    //   gapMs      - duration of that pause, in milliseconds
    // The pause is a genuine page.waitForTimeout() (NOT a simulated timestamp): the useKeyboardBarcodeReader
    // hook measures inter-keystroke gaps with Date.now() and runs a setInterval flush timer in the browser,
    // and a single page.evaluate loop dispatches all keystrokes synchronously (zero wall-clock gap) so it
    // CANNOT reproduce a real gap. Splitting the dispatch across two evaluate() calls with an awaited pause
    // between them reproduces the production symptom: a long HU global QR code (HU#<v>#{…json…}) that arrives
    // at the browser in chunks spread over several seconds. Set gapMs >= the scanner debounce
    // (barcodeScanner.inputText.debounceMillis, default 300ms) to exercise the gap path, and keep it well
    // below the hook's abandon deadline (max(3000, rateMs*10) ms) so an in-flight partial QR is not abandoned.
    // Default (no gapAtIndex/gapMs): unchanged single synchronous zero-delay dispatch — existing callers are
    // not affected.
    type: async (params) => await test.step(`${NAME} - Type scanned code`, async () => {
        let scannedCode;
        let testId;
        let gapAtIndex;
        let gapMs;
        if (typeof params === 'string') {
            scannedCode = params;
            testId = undefined;
        } else if (params && typeof params === 'object') {
            scannedCode = params.scannedCode;
            testId = params.testId;
            gapAtIndex = params.gapAtIndex;
            gapMs = params.gapMs;
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
        const dispatchChunk = async (chunk) => {
            await page.evaluate((code) => {
                for (const char of code) {
                    document.dispatchEvent(new KeyboardEvent('keydown', { key: char, bubbles: true }));
                    document.dispatchEvent(new KeyboardEvent('keyup', { key: char, bubbles: true }));
                }
            }, chunk);
        };

        const hasMidScanGap =
            Number.isInteger(gapAtIndex) && gapAtIndex > 0 && gapAtIndex < scannedCode.length && gapMs > 0;
        if (!hasMidScanGap) {
            await dispatchChunk(scannedCode);
        } else {
            await dispatchChunk(scannedCode.substring(0, gapAtIndex));
            await page.waitForTimeout(gapMs);
            await dispatchChunk(scannedCode.substring(gapAtIndex));
        }
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

    waitForInputFieldToGetEmpty: async () => await test.step(`${NAME} - Wait for input field to get empty`, async () => {
        await expect(page.locator('#input-text')).toHaveValue('');
    }),

    // Fills the visible editable input and presses Enter — exercises the manual-typing path
    // (onChange debounce / onKeyUp Enter) in BarcodeScannerComponent. Only valid when
    // the mode-engine is in manual mode (defaultMode=manual, mode.manual.enabled=Y).
    // NOTE: wait on the manual-entry input, NOT on `#input-text`. The hardware off-screen
    // `#input-text` is rendered by HardwareModePanel only when activeMode === HARDWARE; in
    // pure manual mode (`mode.hardware.enabled=N` or `defaultMode=manual`) it is not in the
    // DOM, so `waitToAttach({})` would time out and look like a manual-entry failure.
    typeManually: async (barcode) => await test.step(`${NAME} - Type manually and press Enter`, async () => {
        const input = page.locator('[data-testid="manual-entry-input"]');
        await input.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
        await input.fill(barcode);
        await input.press('Enter');
    }),

    // Asserts DOM attributes on #input-text.
    //   value === null   → attribute must be ABSENT
    //   value === true   → attribute must be PRESENT (any value — use for HTML boolean attrs
    //                      like readonly/disabled where React's rendered value can vary)
    //   other            → attribute must equal that exact string
    // Example: expectAttributes({ type: 'text', inputmode: 'none', readonly: null })
    //          expectAttributes({ type: 'text', inputmode: 'none', readonly: true })   // CT60 mode
    expectAttributes: async (expectedAttrs) => await test.step(`${NAME} - Expect attributes: ${JSON.stringify(expectedAttrs)}`, async () => {
        await BarcodeScannerComponent.waitToAttach({});
        for (const [attr, expectedValue] of Object.entries(expectedAttrs)) {
            if (expectedValue === null) {
                await expect(page.locator('#input-text')).not.toHaveAttribute(attr);
            } else if (expectedValue === true) {
                await expect(page.locator('#input-text')).toHaveAttribute(attr, /.*/);
            } else {
                await expect(page.locator('#input-text')).toHaveAttribute(attr, expectedValue);
            }
        }
    }),

    // Asserts camera mode is NOT active — the CameraModePanel wrapper (`.camera-mode-panel`,
    // rendered iff activeMode===CAMERA) is not mounted. Used by hardware-scanner-only deployments
    // (camera disabled) to guard against a regression that would silently re-enable camera capture.
    // We assert on the always-mounted wrapper rather than the inner <video> — see
    // expectCameraModeActive for why the <video> is unreliable.
    // Uses FAST_ACTION_TIMEOUT (5s): absence-of-element assertions should fail fast — falling back
    // to the 120s global assertion timeout would silently consume the test budget on a genuine regression.
    expectCameraModeInactive: async () => await test.step(`${NAME} - Expect camera mode not active`, async () => {
        await expect(page.locator('.camera-mode-panel')).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    // Simulates Ctrl+V paste: mocks navigator.clipboard.readText to return the barcode,
    // then dispatches a keydown Ctrl+V on window — the useKeyboardBarcodeReader hook
    // intercepts it and calls onReadDone(clipboardText).
    pasteViaClipboard: async (barcode) => await test.step(`${NAME} - Paste via Ctrl+V`, async () => {
        await BarcodeScannerComponent.waitToAttach({});
        await page.evaluate((value) => {
            Object.defineProperty(navigator, 'clipboard', {
                value: { readText: () => Promise.resolve(value) },
                configurable: true,
            });
        }, barcode);
        await page.evaluate(() => {
            window.dispatchEvent(new KeyboardEvent('keydown', { key: 'v', ctrlKey: true, bubbles: true }));
        });
    }),

    expectCssClass: async ({ present, absent }) => await test.step(`${NAME} - Expect CSS class (present=${present}, absent=${absent})`, async () => {
        await BarcodeScannerComponent.waitToAttach({});
        if (present) await expect(page.locator('#input-text')).toHaveClass(new RegExp(`(^|\\s)${present}(\\s|$)`));
        if (absent) await expect(page.locator('#input-text')).not.toHaveClass(new RegExp(`(^|\\s)${absent}(\\s|$)`));
    }),

    // Asserts the footer (.barcode-scanner-footer) is NOT rendered.
    // Used by invisible-mode tests — invisible suppresses the footer entirely.
    expectFooterAbsent: async () => await test.step(`${NAME} - Expect no footer rendered`, async () => {
        await expect(page.locator('.barcode-scanner-footer')).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    // Asserts the manual-entry visible input IS rendered (data-testid="manual-entry-input").
    expectManualEntryInputPresent: async () => await test.step(`${NAME} - Expect manual-entry input present`, async () => {
        await expect(page.getByTestId('manual-entry-input')).toBeAttached({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Asserts the manual-entry visible input is NOT rendered.
    expectManualEntryInputAbsent: async () => await test.step(`${NAME} - Expect manual-entry input absent`, async () => {
        await expect(page.getByTestId('manual-entry-input')).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    // Asserts the manual-entry input does NOT have the readOnly attribute — the user must be able
    // to type into it (keyboard-enabled). Only meaningful when expectManualEntryInputPresent() passes.
    expectManualEntryInputNotReadOnly: async () => await test.step(`${NAME} - Expect manual-entry input not readOnly`, async () => {
        await expect(page.getByTestId('manual-entry-input')).not.toHaveAttribute('readonly', { timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Asserts camera mode IS active by checking the always-mounted CameraModePanel wrapper
    // (`.camera-mode-panel`, rendered iff activeMode===CAMERA).
    // NOT the inner <video>: CameraModePanel renders the <video> only `{!isProcessing && <video/>}`,
    // so it unmounts whenever a barcode is processed. In CI the synthetic fake-media stream
    // (--use-fake-device-for-media-stream) can make ZXing false-positive a 1-D barcode → isProcessing
    // flips → the <video> unmounts mid-assertion (and a getUserMedia error tears the panel down via
    // onCancel). Asserting on the stable wrapper verifies the toggle switched into camera mode without
    // depending on a transient, hardware-feed-dependent element. Real video-feed validation needs
    // physical camera hardware and cannot be deterministic in CI.
    expectCameraModeActive: async () => await test.step(`${NAME} - Expect camera mode active`, async () => {
        await expect(page.locator('.camera-mode-panel')).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Clicks a footer button by its testId (e.g. 'barcode-scanner-toggle-hw-camera',
    // 'barcode-scanner-enter-manually', 'barcode-scanner-back-to-scanner').
    clickFooterButton: async (testId) => await test.step(`${NAME} - Click footer button '${testId}'`, async () => {
        await page.getByTestId(testId).tap();
    }),

    // Asserts a footer button (by testId) IS shown.
    expectFooterButtonPresent: async (testId) => await test.step(`${NAME} - Expect footer button present '${testId}'`, async () => {
        await expect(page.getByTestId(testId)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Asserts a footer button (by testId) is NOT shown.
    expectFooterButtonAbsent: async (testId) => await test.step(`${NAME} - Expect footer button absent '${testId}'`, async () => {
        await expect(page.getByTestId(testId)).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),
}
