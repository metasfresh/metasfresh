import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'MassPrintingScanScreen';

/**
 * Screen object for the mobileUI mass-printing scan-and-pack screen
 * (route `/:applicationId/wf/:wfProcessId/massPrinting/scan`).
 *
 * The screen first renders a barcode scanner (the operator scans an LU QR code);
 * once the backend returns, it swaps to a result view wrapped in `mass-printing-result`
 * with one `mass-printing-product-result` block per packed product.
 */
export const MassPrintingScanScreen = {
    waitForScanner: async () => await step(`${NAME} - Wait for scanner`, async () => {
        await BarcodeScannerComponent.waitToAttach({});
    }),

    scanLU: async ({ qrCode }) => await step(`${NAME} - Scan LU ${qrCode}`, async () => {
        await MassPrintingScanScreen.waitForScanner();
        await BarcodeScannerComponent.type(qrCode);
    }),

    waitForResult: async () => await step(`${NAME} - Wait for result`, async () => {
        await page.getByTestId('mass-printing-result').waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Number of product-result blocks shown. */
    expectProductResultCount: async ({ expectedCount }) => await step(`${NAME} - Expect ${expectedCount} product result(s)`, async () => {
        await expect(page.getByTestId('mass-printing-product-result')).toHaveCount(expectedCount);
    }),

    /**
     * Assert the boxes-packed value of the product-result block at the given index (0-based).
     * The block renders the value as `<caption>: <number>`, so we match the trailing number.
     */
    expectBoxesPacked: async ({ index = 0, expected }) => await step(`${NAME} - Expect boxesPacked='${expected}' (block ${index})`, async () => {
        const field = page.getByTestId('mass-printing-product-result').nth(index).getByTestId('mass-printing-boxes-packed');
        await field.waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
        await expect(field).toContainText(new RegExp(`:\\s*${expected}\\s*$`));
    }),

    /** Assert the units-left-on-LU value (only rendered when > 0; pass 0 to assert it is absent). */
    expectUnitsLeftOnLU: async ({ index = 0, expected }) => await step(`${NAME} - Expect unitsLeftOnLU='${expected}' (block ${index})`, async () => {
        const field = page.getByTestId('mass-printing-product-result').nth(index).getByTestId('mass-printing-units-left');
        if (expected === 0) {
            await field.waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
        } else {
            await field.waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
            await expect(field).toContainText(new RegExp(`:\\s*${expected}\\s*$`));
        }
    }),

    /** Assert the open-demand-remaining value (only rendered when > 0; pass 0 to assert it is absent). */
    expectDemandRemaining: async ({ index = 0, expected }) => await step(`${NAME} - Expect demandRemaining='${expected}' (block ${index})`, async () => {
        const field = page.getByTestId('mass-printing-product-result').nth(index).getByTestId('mass-printing-demand-remaining');
        if (expected === 0) {
            await field.waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
        } else {
            await field.waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
            await expect(field).toContainText(new RegExp(`:\\s*${expected}\\s*$`));
        }
    }),

    clickDone: async () => await step(`${NAME} - Click Done`, async () => {
        await page.getByTestId('mass-printing-done-button').tap();
    }),
};
