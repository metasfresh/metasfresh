import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { expect } from '@playwright/test';
import { test } from '../../../../playwright.config';
import { GetQuantityDialog } from './GetQuantityDialog';
import { PickingJobScreen } from './PickingJobScreen';
import { PickingJobLineScreen } from './PickingJobLineScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { waitForNoInFlightXhr } from '../../inFlightXhr';

/** A refusal is read on the handheld: the whole error toast, its close button included, fits on the screen (a long message must not push it off). */
const expectToastCloseButtonOnScreen = async () => {
    await expect(page.locator('.Toastify__close-button--error').first()).toBeInViewport({ ratio: 1 });
};

const NAME = 'PickLineScanScreen';

const containerElement = () => page.locator('#PickLineScanScreen');
const nextTUButton = () => page.getByTestId('next-tu-button');
const tapNextTU = async () => {
    await PickLineScanScreen.waitForScreen();
    await nextTUButton().tap();
    // the tap's request has been answered and rendered (a poll right after the tap could still see the button enabled, before the tap took effect)
    await waitForNoInFlightXhr(page, { urlPattern: /\/picking\//, timeout: SLOW_ACTION_TIMEOUT });
    // the button is disabled while the TU is being closed and opened; a booking before that is done is refused (no open TU).
    // Done = the button is usable again, or the screen was left for the TU list.
    await expect.poll(async () => (await nextTUButton().count()) === 0 || await nextTUButton().isEnabled(), { timeout: SLOW_ACTION_TIMEOUT }).toBe(true);
};

export const PickLineScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

    pickHU: async ({ qrCode, qtyEntered, expectQtyEntered, catchWeightQRCode, qtyNotFoundReason, expectGoBackToPickingJob = true } = {}) => await step(`${NAME} - Scan HU and Pick`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(qrCode);

        if (qtyEntered != null || expectQtyEntered != null || catchWeightQRCode != null || qtyNotFoundReason != null) {
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, qtyEntered, catchWeightQRCode, qtyNotFoundReason });
        }

        if (expectGoBackToPickingJob) {
            await PickingJobScreen.waitForScreen();
        } else {
            await PickLineScanScreen.waitForScreen();
        }
    }),

    /** Asserts (auto-retrying) the CU quantity entry mode of the line. */
    expectCUQtyEntryMode: async (expected) => await step(`${NAME} - Expect CU quantity entry mode '${expected}'`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(containerElement()).toHaveAttribute('data-cu-qty-entry-mode', expected);
    }),

    /** Asserts that the "Weitere Kiste" / "Next box" button is not shown (Manual lines never show it). */
    expectNextTUButtonHidden: async () => await step(`${NAME} - Expect Next box button hidden`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(nextTUButton()).toHaveCount(0);
    }),

    /** Scans one raw CU label on the pick-line scan screen. */
    scanCULabel: async (label) => await step(`${NAME} - Scan CU label`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(label);
        // Scan each CU books at once: the booking's request has been answered before the next step looks at the backend
        await waitForNoInFlightXhr(page, { urlPattern: /\/picking\//, timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Scans a raw CU label on the scan step that must be refused: error toast with the text, the quantity block does not open. */
    scanCULabelRefused: async (label, expectedText, { whileToastShown } = {}) => await step(`${NAME} - Scan CU label, expect refusal '${expectedText}'`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expectErrorToast(
            `${NAME} label refusal`,
            async () => await PickLineScanScreen.typeQRCode(label),
            async ({ textContent }) => {
                expect(textContent).toContain(expectedText);
                await expectToastCloseButtonOnScreen();
                await whileToastShown?.();
            }
        );
        // the closed toast is still fading out for a moment: the next step's error watcher would see it as a new error
        await expect(page.locator('.Toastify__toast--error')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        await GetQuantityDialog.expectNotShown();
    }),

    /** Rescans a raw CU label inside the open quantity block where it must be refused: error toast with the text, the block stays. */
    rescanCULabelRefused: async (label, expectedText, { whileToastShown } = {}) => await step(`${NAME} - Rescan CU label, expect refusal '${expectedText}'`, async () => {
        await expectErrorToast(
            `${NAME} label rescan refusal`,
            async () => await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: label, testId: 'rescanCULabel-input' }),
            async ({ textContent }) => {
                expect(textContent).toContain(expectedText);
                await expectToastCloseButtonOnScreen();
                await whileToastShown?.();
            }
        );
        await GetQuantityDialog.waitForDialog();
    }),

    /** Taps "Weitere Kiste" / "Next box": opens the next TU of the line. */
    nextTU: async () => await step(`${NAME} - Next box`, tapNextTU),

    /**
     * Opens the first TU of a line in CU quantity entry mode, before its first booking (same tap as nextTU).
     * Returns when the TU is open: the "Current" header entry shows it and the button is usable again.
     */
    openTU: async () => await step(`${NAME} - Open TU`, async () => {
        await tapNextTU();
        await expect(page.locator('tr:has(th:has-text("Current"))').first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(nextTUButton()).toBeEnabled();
    }),

    /** Scans one raw CU label into the quantity block's own scanner (Scan once: a new label replaces the remembered one). */
    rescanCULabel: async (label) => await step(`${NAME} - Rescan CU label in the quantity block`, async () => {
        await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: label, testId: 'rescanCULabel-input' });
    }),

    /** Asserts that the open TU shown in the header ("Current") carries the given caption (the TU type name of a GRAI TU). */
    expectOpenTUCaption: async (caption) => await step(`${NAME} - Expect open TU '${caption}'`, async () => {
        await expect(page.locator('tr:has(th:has-text("Current"))').first()).toContainText(caption, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts that "Weitere Kiste" / "Next box" sits in the quantity block's button row, next to OK. */
    expectNextTUButtonInQuantityBlock: async () => await step(`${NAME} - Expect Next box button in the quantity block`, async () => {
        await expect(page.locator('.get-qty-dialog').getByTestId('next-tu-button')).toBeVisible();
    }),

    /** Scans a GRAI on the scan step (before the label): opens a new TU. */
    scanGrai: async (graiString) => await step(`${NAME} - Scan GRAI ${graiString}`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(graiString);
        // the TU is open once the app confirms it with the GRAI ("TU ... opened"); a label scanned before that has no open TU
        await expect(page.locator('.Toastify__toast--success', { hasText: graiString })).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Scans a GRAI inside the open quantity block: opens a new TU, the block stays. */
    rescanGrai: async (graiString) => await step(`${NAME} - Rescan GRAI ${graiString} in the quantity block`, async () => {
        await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: graiString, testId: 'rescanCULabel-input' });
    }),

    /** Scans a GRAI inside the open quantity block that must be refused: error toast with the text, the block stays. */
    rescanGraiRefused: async (graiString, expectedText) => await step(`${NAME} - Rescan GRAI, expect refusal '${expectedText}'`, async () => {
        await expectErrorToast(
            `${NAME} GRAI refusal`,
            async () => await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: graiString, testId: 'rescanCULabel-input' }),
            ({ textContent }) => expect(textContent).toContain(expectedText)
        );
        await GetQuantityDialog.waitForDialog();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickLineScanScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

};