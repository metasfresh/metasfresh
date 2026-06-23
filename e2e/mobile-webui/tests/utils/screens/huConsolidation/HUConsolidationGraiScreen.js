import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'HUConsolidationGraiScreen';

/** Container of the GRAI capture panel rendered by HUConsolidationGraiScreen.jsx */
const containerElement = () => page.locator('.grai-screen');

export const HUConsolidationGraiScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Scan a single GRAI barcode.
     * Uses typeBatch (dispatches Enter after the code) to trigger immediate processing
     * via the keyboard hook's onReadDone path, avoiding the interval-flush delay.
     */
    scanGrai: async ({ graiString }) => await test.step(`${NAME} - Scan GRAI: ${graiString}`, async () => {
        await BarcodeScannerComponent.typeBatch({ codes: [graiString] });
    }),

    /**
     * Expect the GRAI chip count (both assigned + overflow) to match expectedCount.
     * Waits for the chips to be visible so the assertion cannot pass while a loading
     * spinner is still painted.
     */
    expectGraiChipCount: async ({ expectedCount }) => await test.step(`${NAME} - Expect ${expectedCount} GRAI chip(s)`, async () => {
        const chips = page.locator('[data-testid="grai-chip"], [data-testid="grai-chip-extra"]');
        await expect(chips).toHaveCount(expectedCount, { timeout: SLOW_ACTION_TIMEOUT });
        if (expectedCount > 0) {
            await expect(chips.last()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        }
    }),

    /** Assert the send button is enabled (enough GRAIs captured). */
    expectSendEnabled: async () => await test.step(`${NAME} - Expect send button enabled`, async () => {
        await expect(page.getByTestId('grai-send-button')).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Assert the send button is disabled (not enough GRAIs yet). */
    expectSendDisabled: async () => await test.step(`${NAME} - Expect send button disabled`, async () => {
        await expect(page.getByTestId('grai-send-button')).toBeDisabled({ timeout: FAST_ACTION_TIMEOUT });
    }),

    /** Tap the send button and wait for the PUT /grai response to land. */
    clickSend: async () => await test.step(`${NAME} - Click send`, async () => {
        const syncDone = page.waitForResponse(
            (resp) => resp.url().includes('/grai') && resp.request().method() === 'PUT',
            { timeout: SLOW_ACTION_TIMEOUT }
        );
        await page.getByTestId('grai-send-button').tap();
        await syncDone;
    }),
};
