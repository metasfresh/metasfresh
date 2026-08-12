import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickGraiScreen';

/** Container of the inline GRAI mass-capture panel (GraiCapturePanel.jsx). */
const containerElement = () => page.locator('.grai-screen');

/**
 * Screen object for the inline GRAI mass-capture (`GraiCapturePanel`). In the "Flow Through"
 * (LU_TU) profile of a GRAIRequired customer, this panel is auto-invoked right after the pick
 * quantity is confirmed (no separate screen, no button to reach it): the operator must capture one
 * GRAI per picked crate (TU) before the pick can be saved. Save sends the whole pick — quantity +
 * the captured GRAIs — in one atomic event; the count's total is the picked TU quantity.
 *
 * GRAIs are added either by scanning (the live {@link BarcodeScannerComponent}, hardware mode) or
 * by typing — typing goes through the scanner component's own manual-entry mode (tap "Enter
 * manually", type into `manual-entry-input`, submit). The save button (`grai-save-button`, label
 * "Save"/"Speichern") is enabled only when exactly N GRAIs are captured.
 */
export const PickGraiScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Scan a GRAI barcode through the live scanner (the offscreen hardware-scan input).
     * Dispatches keyboard events at document level, terminated with Enter (DataWedge behaviour).
     */
    scanGrai: async ({ graiString }) => await test.step(`${NAME} - Scan GRAI: ${graiString}`, async () => {
        await BarcodeScannerComponent.type(graiString);
    }),

    /**
     * Scan a whole batch of GRAIs in one burst — the RFID-gun read pattern, where the reader emits
     * every tag in range back-to-back with an Enter terminator between codes. {@link BarcodeScannerComponent.typeBatch}
     * sends Enter after each code, so the keyboard hook flushes each barcode individually (no buffer
     * merge) and no inter-scan assertion is needed (unlike consecutive {@link scanGrai} calls).
     *
     * The list may legitimately contain GRAIs already captured — the panel's deduped merge
     * (mergeGraiArrays) ignores any GRAI already present, so re-reading already-captured tags does
     * not inflate the count.
     */
    scanGraiBatch: async ({ graiStrings }) => await test.step(`${NAME} - Scan GRAI batch of ${graiStrings.length}`, async () => {
        await BarcodeScannerComponent.typeBatch({ codes: graiStrings });
    }),

    /**
     * Type a GRAI by hand: switch the scanner to manual-entry mode, type the GRAI into the visible
     * manual input and confirm it. After a successful submit the scanner auto-returns to the default
     * (hardware) mode, ready for the next scan.
     */
    enterGraiManually: async ({ graiString }) => await test.step(`${NAME} - Enter GRAI manually: ${graiString}`, async () => {
        await page.getByTestId('barcode-scanner-enter-manually').tap();
        const input = page.getByTestId('manual-entry-input');
        await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await input.fill(graiString);
        await page.getByTestId('manual-entry-submit').tap();
    }),

    /** Assert the total captured GRAI chip count — both assigned ('grai-chip') and overflow
     * ('grai-chip-extra'), so an unexpected over-capture is caught instead of silently passing because
     * the extra chips fall outside a chip-only count. */
    expectGraiChipCount: async ({ expectedCount }) => await test.step(`${NAME} - Expect ${expectedCount} GRAI chip(s)`, async () => {
        const chips = page.locator('[data-testid="grai-chip"], [data-testid="grai-chip-extra"]');
        await expect(chips).toHaveCount(expectedCount, { timeout: SLOW_ACTION_TIMEOUT });
        // Assert the chips are actually painted (not merely attached) so a green count cannot pass
        // while a re-render/spinner is still in flight — per the mobile-webui visible-not-attached rule.
        if (expectedCount > 0) {
            await expect(chips.last()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        }
    }),

    /**
     * Assert the count label reads "<scanned> / <total>" (e.g. 0 / 10) — proving the panel shows the
     * picked crate count (total = the picked TU quantity, N) as the required number of GRAIs.
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

    /** Tap the save ("Save"/"Speichern") button — sends the atomic pick (qty + GRAIs). */
    clickSave: async () => await test.step(`${NAME} - Click save`, async () => {
        await page.getByTestId('grai-save-button').tap();
    }),
};
