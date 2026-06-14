import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickGraiScreen';

/** Container of the in-picking GRAI mass-capture screen (PickGraiScanScreen.jsx). */
const containerElement = () => page.locator('.grai-screen');

/**
 * Screen object for the in-picking GRAI mass-capture screen (`PickGraiScanScreen`), reached from
 * the pick-line screen via the {@code grai-scan-button}. It is shown for a GRAIRequired customer
 * once an LU has been picked (Flow Through / LU_TU profile).
 *
 * The screen captures one GRAI per picked TU (N = tuCount). GRAIs are added either by scanning
 * (the live {@link BarcodeScannerComponent} on this screen) or by typing them into the manual
 * input ({@code grai-manual-input} + {@code grai-manual-submit}). The save button
 * ({@code grai-save-button}, label "Save"/"Speichern") is enabled only when exactly N GRAIs are
 * captured; on save the app returns to the picking job screen.
 */
export const PickGraiScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Scan a GRAI barcode through the screen's live scanner (the offscreen hardware-scan input).
     * Dispatches keyboard events at document level, terminated with Enter (DataWedge behaviour).
     */
    scanGrai: async ({ graiString }) => await test.step(`${NAME} - Scan GRAI: ${graiString}`, async () => {
        await BarcodeScannerComponent.type(graiString);
    }),

    /** Type a GRAI into the manual-entry input and confirm it with the Add button. */
    enterGraiManually: async ({ graiString }) => await test.step(`${NAME} - Enter GRAI manually: ${graiString}`, async () => {
        const input = page.getByTestId('grai-manual-input');
        await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await input.fill(graiString);
        await page.getByTestId('grai-manual-submit').tap();
    }),

    /**
     * Wait until the screen has loaded the expected GRAI count (tuCount > 0) from the backend and
     * return it. The count label reads "<scanned> / <total> GRAIs scanned"; total is the tuCount.
     */
    waitForTuCountLoaded: async () => await test.step(`${NAME} - Wait for tuCount to load`, async () => {
        const label = page.getByTestId('grai-count');
        await expect(label).toContainText(/\/\s*[1-9]\d*\s/, { timeout: SLOW_ACTION_TIMEOUT });
        const text = await label.textContent();
        const match = text.match(/\/\s*(\d+)/);
        return match ? parseInt(match[1], 10) : 0;
    }),

    /** Assert the captured/assigned GRAI chip count (the N-of-tuCount progress). */
    expectGraiChipCount: async ({ expectedCount }) => await test.step(`${NAME} - Expect ${expectedCount} GRAI chip(s)`, async () => {
        await expect(page.getByTestId('grai-chip')).toHaveCount(expectedCount, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Assert the count label reads "<scanned> / <total>" (e.g. 0 / 10) — proving the screen learned
     * the LU's real crate count from the backend (tuCount = N, not 1).
     */
    expectCount: async ({ scanned, total }) => await test.step(`${NAME} - Expect count ${scanned} / ${total}`, async () => {
        await expect(page.getByTestId('grai-count')).toContainText(
            new RegExp(`\\b${scanned}\\s*/\\s*${total}\\b`),
            { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Assert the save button is enabled (exactly N GRAIs captured). */
    expectSaveEnabled: async () => await test.step(`${NAME} - Expect save enabled`, async () => {
        await expect(page.getByTestId('grai-save-button')).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Assert the save button is disabled (fewer/more than N GRAIs captured). */
    expectSaveDisabled: async () => await test.step(`${NAME} - Expect save disabled`, async () => {
        await expect(page.getByTestId('grai-save-button')).toBeDisabled({ timeout: FAST_ACTION_TIMEOUT });
    }),

    /** Tap the save ("Save"/"Speichern") button. */
    clickSave: async () => await test.step(`${NAME} - Click save`, async () => {
        await page.getByTestId('grai-save-button').tap();
    }),
};
