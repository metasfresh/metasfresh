import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { expect } from '@playwright/test';
import { test } from '../../../../playwright.config';
import { GetQuantityDialog } from './GetQuantityDialog';
import { PickingJobScreen } from './PickingJobScreen';
import { PickingJobLineScreen } from './PickingJobLineScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickLineScanScreen';

const containerElement = () => page.locator('#PickLineScanScreen');
const nextBoxButton = () => page.getByRole('button', { name: /Weitere Kiste|Next box/ });
const tapNextBox = async () => {
    await PickLineScanScreen.waitForScreen();
    await nextBoxButton().tap();
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

    /** Reads the CU quantity entry mode of the line from the screen's `data-cu-qty-entry-mode` attribute (one-shot; assert with expectCUQtyEntryMode). */
    cuQtyEntryMode: async () => await step(`${NAME} - Read CU quantity entry mode`, async () => {
        await PickLineScanScreen.waitForScreen();
        return await containerElement().getAttribute('data-cu-qty-entry-mode');
    }),

    /** Asserts (auto-retrying) the CU quantity entry mode of the line. */
    expectCUQtyEntryMode: async (expected) => await step(`${NAME} - Expect CU quantity entry mode '${expected}'`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(containerElement()).toHaveAttribute('data-cu-qty-entry-mode', expected);
    }),

    /** Asserts that the "Weitere Kiste" / "Next box" button is not shown (Manual lines never show it). */
    expectNextBoxButtonHidden: async () => await step(`${NAME} - Expect Next box button hidden`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(nextBoxButton()).toHaveCount(0);
    }),

    /** Scans one raw CU label on the pick-line scan screen. */
    scanCULabel: async (label) => await step(`${NAME} - Scan CU label`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(label);
    }),

    /** Scans a raw CU label on the scan step that must be refused: error toast with the text, the quantity block does not open. */
    scanCULabelRefused: async (label, expectedText) => await step(`${NAME} - Scan CU label, expect refusal '${expectedText}'`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expectErrorToast(
            `${NAME} label refusal`,
            async () => await PickLineScanScreen.typeQRCode(label),
            ({ textContent }) => expect(textContent).toContain(expectedText)
        );
        await GetQuantityDialog.expectNotShown();
    }),

    /** Rescans a raw CU label inside the open quantity block where it must be refused: error toast with the text, the block stays. */
    rescanCULabelRefused: async (label, expectedText) => await step(`${NAME} - Rescan CU label, expect refusal '${expectedText}'`, async () => {
        await expectErrorToast(
            `${NAME} label rescan refusal`,
            async () => await BarcodeScannerComponent.type({ scannedCode: label, testId: 'rescanCULabel-input' }),
            ({ textContent }) => expect(textContent).toContain(expectedText)
        );
        await GetQuantityDialog.waitForDialog();
    }),

    /** Taps "Weitere Kiste" / "Next box": opens the next TU (box) of the line. */
    nextBox: async () => await step(`${NAME} - Next box`, tapNextBox),

    /**
     * Opens the first box of a line in CU quantity entry mode, before its first booking (same tap as nextBox).
     * Returns when the box is open: the "Current" header entry shows it and the button is usable again.
     */
    openBox: async () => await step(`${NAME} - Open box`, async () => {
        await tapNextBox();
        await expect(page.locator('tr:has(th:has-text("Current"))').first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(nextBoxButton()).toBeEnabled();
    }),

    /** Scans one raw CU label into the quantity block's own scanner (Scan once: a new label replaces the remembered one). */
    rescanCULabel: async (label) => await step(`${NAME} - Rescan CU label in the quantity block`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: label, testId: 'rescanCULabel-input' });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickLineScanScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

};