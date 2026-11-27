import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';

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

// release line: the reader buffers the keys without echoing them and marks the scanner's input (visible or invisible scanner)
const PAGE_WITH_NON_ECHO_READER = (inputAttrs) => `
<input id="input-text" ${inputAttrs} data-scan-in-progress="false" />
<script>
    window.__scanned = [];
    window.__keysSeen = 0;
    setTimeout(() => {
        let buffer = '';
        const input = document.getElementById('input-text');
        window.addEventListener('keydown', (event) => {
            window.__keysSeen++;
            if (event.key === 'Enter') {
                if (buffer) window.__scanned.push(buffer);
                buffer = '';
                input.dataset.scanInProgress = 'false';
                return;
            }
            buffer += event.key;
            input.dataset.scanInProgress = 'true';
        });
    }, 300);
</script>
`;

test('a non-echoing reader (invisible scanner, other scanner panel present) gets the scan delivered exactly once', async ({ page }) => {
    await page.setContent('<div class="hardware-mode-panel"></div>' + PAGE_WITH_NON_ECHO_READER('data-testid="rescanCULabel-input"'));

    await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: 'ABCDEFGHIJ12', testId: 'rescanCULabel-input' });

    await expect.poll(() => page.evaluate(() => window.__scanned)).toEqual(['ABCDEFGHIJ12']);
    expect(await page.evaluate(() => window.__keysSeen)).toBe('ABCDEFGHIJ12'.length + 1);
});
