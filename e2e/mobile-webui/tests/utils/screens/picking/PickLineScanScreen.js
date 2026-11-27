import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { expect } from '@playwright/test';
import { test } from '../../../../playwright.config';
import { GetQuantityDialog } from './GetQuantityDialog';
import { PickingJobScreen } from './PickingJobScreen';
import { PickingJobLineScreen } from './PickingJobLineScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { waitForNoInFlightXhr } from '../../inFlightXhr';
import { measureButtonRow, removeNextTUIconForReference } from '../../layout';

/** A refusal is read on the handheld */
const expectToastCloseButtonOnScreen = async () => {
    await expect(page.locator('.Toastify__close-button--error').first()).toBeInViewport({ ratio: 1 });
};

const NAME = 'PickLineScanScreen';

const containerElement = () => page.locator('#PickLineScanScreen');
const nextTUButton = () => page.getByTestId('next-tu-button');
const tapNextTU = async () => {
    await PickLineScanScreen.waitForScreen();
    await nextTUButton().tap();
    await waitForNoInFlightXhr(page, { urlPattern: /\/picking\//, timeout: SLOW_ACTION_TIMEOUT });
    await expect.poll(async () => (await nextTUButton().count()) === 0 || await nextTUButton().isEnabled(), { timeout: SLOW_ACTION_TIMEOUT }).toBe(true);
};

export const PickLineScanScreen = {
    /** The Next TU button; for layout checks (see utils/layout.js) */
    nextTUButtonLocator: () => nextTUButton(),
    /** Boxes and font size of the given buttons of this screen (by data-testid) */
    measureButtonRow: async (testIds) => await measureButtonRow(page.locator('body'), testIds),
    /** Reference layout for a check: the barcode icon of the Next TU button removed */
    removeNextTUIconForReference: async () => await removeNextTUIconForReference(page.locator('body')),

    /** The Close button of Scan each; for layout checks */
    scanEachCloseButton: () => page.getByTestId('scan-each-close-button'),

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

    /** Asserts (auto-retrying) the CU quantity entry mode of the line */
    expectCUQtyEntryMode: async (expected) => await step(`${NAME} - Expect CU quantity entry mode '${expected}'`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(containerElement()).toHaveAttribute('data-cu-qty-entry-mode', expected);
    }),

    /** Asserts that the "Weiteres TU" / "Next TU" button is not shown (Manual lines never show it) */
    expectNextTUButtonHidden: async () => await step(`${NAME} - Expect Next TU button hidden`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(nextTUButton()).toHaveCount(0);
    }),

    /**
     * Asserts that the scan screen of a label line (Scan once) shows no "Weiteres TU" / "Next TU" before the first scan.
     * Waits until the line is rendered as a label line (data-cu-label-required), so a button that is merely not drawn yet cannot pass as hidden.
     */
    expectNextTUButtonHiddenOnLabelLine: async () => await step(`${NAME} - Expect Next TU button hidden on the label line's scan screen`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expect(containerElement()).toHaveAttribute('data-cu-label-required', 'true');
        await expect(nextTUButton()).toHaveCount(0);
    }),

    /** Scans one raw CU label on the pick-line scan screen */
    scanCULabel: async (label) => await step(`${NAME} - Scan CU label`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(label);
        await waitForNoInFlightXhr(page, { urlPattern: /\/picking\//, timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Scans a raw CU label on the scan step that must be refused */
    scanCULabelRefused: async (label, expectedText, { whileToastShown, unexpectedText } = {}) => await step(`${NAME} - Scan CU label, expect refusal '${expectedText}'`, async () => {
        await PickLineScanScreen.waitForScreen();
        await expectErrorToast(
            `${NAME} label refusal`,
            async () => await PickLineScanScreen.typeQRCode(label),
            async ({ textContent }) => {
                expect(textContent).toContain(expectedText);
                if (unexpectedText) expect(textContent).not.toContain(unexpectedText);
                await expectToastCloseButtonOnScreen();
                await whileToastShown?.();
            }
        );
        await expect(page.locator('.Toastify__toast--error')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        await GetQuantityDialog.expectNotShown();
    }),

    /** Rescans a raw CU label inside the open quantity block where it must be refused */
    rescanCULabelRefused: async (label, expectedText, { whileToastShown, unexpectedText } = {}) => await step(`${NAME} - Rescan CU label, expect refusal '${expectedText}'`, async () => {
        await expectErrorToast(
            `${NAME} label rescan refusal`,
            async () => await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: label, testId: 'rescanCULabel-input' }),
            async ({ textContent }) => {
                expect(textContent).toContain(expectedText);
                if (unexpectedText) expect(textContent).not.toContain(unexpectedText);
                await expectToastCloseButtonOnScreen();
                await whileToastShown?.();
            }
        );
        await GetQuantityDialog.waitForDialog();
    }),

    /** Taps "Weiteres TU" / "Next TU" and does not wait for the answer (to hold a slow backend answer in a test) */
    tapNextTUWithoutWaiting: async () => await step(`${NAME} - Tap Next TU, do not wait`, async () => {
        await nextTUButton().tap();
    }),

    /** Taps "Weiteres TU" / "Next TU" */
    nextTU: async () => await step(`${NAME} - Next TU`, tapNextTU),

    openTU: async () => await step(`${NAME} - Open TU`, async () => {
        await tapNextTU();
        await expect(page.getByTestId('openTU-caption')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(nextTUButton()).toBeEnabled();
    }),

    /** Asserts the Next TU button is shown */
    expectNextTUButtonVisible: async () => await step(`${NAME} - Expect Next TU button visible`, async () => {
        await expect(nextTUButton()).toBeVisible();
    }),

    scrollNextTUButtonIntoView: async () => await step(`${NAME} - Scroll Next TU button into view`, async () => {
        await nextTUButton().scrollIntoViewIfNeeded();
    }),

    /** First scan screen of a CU label line: its own visible scanner stays (and "Weiteres TU" with its second scanner is absent, see expectNextTUButtonHiddenOnLabelLine) */
    expectFirstScreenScannerVisuals: async () => await step(`${NAME} - Expect the first scan screen's own visible scanner`, async () => {
        await expect(page.getByTestId('scanHUBarcode-input')).toBeAttached();
        await expect(page.locator('.scan-prompt')).toHaveCount(1);
        await expect(page.getByTestId('rescanCULabel-input')).toHaveCount(0);
        await expect(page.locator('input.input-text-offscreen')).toHaveCount(1);
    }),

    /** Quantity block (Scan once): no scanner visuals (icon, "Barcode scannen", "Manuell eingeben"), one hidden scanner; the only barcode icon is the one inside "Weiteres TU" */
    expectQtyBlockHasNoScannerVisuals: async () => await step(`${NAME} - Expect the quantity block has no scanner visuals`, async () => {
        await expect(page.locator('.scan-prompt')).toHaveCount(0);
        await expect(page.locator('.barcode-scanner-footer')).toHaveCount(0);
        await expect(page.getByTestId('barcode-scanner-enter-manually')).toHaveCount(0);
        await expect(page.getByText('Barcode scannen')).toHaveCount(0);
        await expect(page.getByText('Manuell eingeben')).toHaveCount(0);
        await expect(page.getByTestId('scanHUBarcode-input')).toHaveCount(0);
        await expect(page.getByTestId('rescanCULabel-input')).toBeAttached();
        await expect(page.locator('input.input-text-offscreen')).toHaveCount(1);
        // the only barcode icon of the dialog is the one inside "Weiteres TU"
        await expect(GetQuantityDialog.container().locator('[class*="fa-barcode"]')).toHaveCount(1);
        await expect(nextTUButton().locator('[class*="fa-barcode"]')).toHaveCount(1);
        await expect(nextTUButton()).toContainText('Weiteres TU');
    }),

    /** Scans one raw CU label into the quantity block's own scanner (Scan once */
    rescanCULabel: async (label) => await step(`${NAME} - Rescan CU label in the quantity block`, async () => {
        await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: label, testId: 'rescanCULabel-input' });
    }),

    expectOpenTUCaption: async (caption) => await step(`${NAME} - Expect open TU '${caption}'`, async () => {
        await expect(page.getByTestId('openTU-caption')).toContainText(caption, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts that "Weiteres TU" / "Next TU" sits in the quantity block's button row, next to OK */
    expectNextTUButtonInQuantityBlock: async () => await step(`${NAME} - Expect Next TU button in the quantity block`, async () => {
        await expect(GetQuantityDialog.nextTUButton()).toBeVisible();
    }),

    /** Scans a GRAI on the scan step (before the label) */
    scanGrai: async (graiString) => await step(`${NAME} - Scan GRAI ${graiString}`, async () => {
        await PickLineScanScreen.waitForScreen();
        // language-independent and not satisfied by an older toast: mark the success toasts shown now, wait for one that is not marked
        await page.locator('.Toastify__toast--success').evaluateAll((toasts) => toasts.forEach((toast) => toast.setAttribute('data-before-scan', 'true')));
        await PickLineScanScreen.typeQRCode(graiString);
        await expect(page.locator('.Toastify__toast--success:not([data-before-scan])')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Scans a GRAI inside the open quantity block */
    rescanGrai: async (graiString) => await step(`${NAME} - Rescan GRAI ${graiString} in the quantity block`, async () => {
        await BarcodeScannerComponent.typeWhenScannerListens({ scannedCode: graiString, testId: 'rescanCULabel-input' });
    }),

    /** Scans a GRAI inside the open quantity block that must be refused */
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