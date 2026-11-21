import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';

/**
 * BarcodeScannerComponent.type against a minimal page (no app server): the scanner input is in the DOM before
 * the scanner listens.
 *
 * In the app, the scanner input (`#input-text`) is rendered by React's commit, but the window `keydown` listener
 * of useKeyboardBarcodeReader is added later, in a useEffect. A scan sent between the two is lost: no listener
 * sees the keys. CI trace of receiving_main_products_catchweight.spec.js ("TO a new LU, scanning 2 x GTIN codes",
 * 2026-11-21): the keys were sent 4 ms before the console line "Enabled keyboard barcode reader"; the dialog never
 * got the scan and the test timed out.
 *
 * The page below mirrors that order: input first, listener 300 ms later. The listener mirrors the reader: it
 * collects the keys, shows them in the input (as onReadInProgress does), and reports the code on Enter.
 */
const PAGE_WITH_LATE_LISTENER = `
<input id="input-text" data-testid="qrCode-input" />
<script>
    window.__scanned = [];
    window.__keysSeen = 0;
    setTimeout(() => {
        let buffer = '';
        window.addEventListener('keydown', (event) => {
            window.__keysSeen++;
            if (event.key === 'Enter') {
                if (buffer) window.__scanned.push(buffer);
                buffer = '';
                document.getElementById('input-text').value = '';
                return;
            }
            buffer += event.key;
            document.getElementById('input-text').value = buffer;
        });
        window.__listening = true;
    }, 300);
</script>
`;

test('a scan sent while the input exists but the reader does not listen yet is delivered once the reader listens', async ({ page }) => {
    await page.setContent(PAGE_WITH_LATE_LISTENER);

    await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: '019121234559370931030008321004481124', testId: 'qrCode-input' });

    // exactly one scan, and the listener saw the code's keys + Enter exactly once (no key sent twice)
    await expect.poll(() => page.evaluate(() => window.__scanned)).toEqual(['019121234559370931030008321004481124']);
    expect(await page.evaluate(() => window.__keysSeen)).toBe('019121234559370931030008321004481124'.length + 1);
});

test('a scanner that never listens fails the scan instead of passing silently', async ({ page }) => {
    await page.setContent('<input id="input-text" data-testid="qrCode-input" />');

    await expect(BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: 'ABCDEFGHIJ12', testId: 'qrCode-input' }))
        .rejects.toThrow('did not listen');
});

test('a scan sent to a listening reader is delivered exactly once', async ({ page }) => {
    await page.setContent(PAGE_WITH_LATE_LISTENER);
    await page.waitForFunction(() => window.__listening === true);

    await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: 'ABCDEFGHIJ12', testId: 'qrCode-input' });
    await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: 'KLMNOPQRST34', testId: 'qrCode-input' });

    await expect.poll(() => page.evaluate(() => window.__scanned)).toEqual(['ABCDEFGHIJ12', 'KLMNOPQRST34']);
});
