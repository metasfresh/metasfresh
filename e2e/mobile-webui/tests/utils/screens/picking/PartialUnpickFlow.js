import { test } from "../../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from "../../common";
import { expect } from "@playwright/test";
import { BarcodeScannerComponent } from "../../components/BarcodeScannerComponent";
import { GetQuantityDialog } from "./GetQuantityDialog";
import { PickingJobStepScreen } from "./PickingJobStepScreen";

const NAME = 'PartialUnpickFlow';

/**
 * Screen object for the partial-unpick ("Remove item") flow that lives on a packed picking STEP
 * (rendered by PartialUnpickFlow.jsx + UnpickProductScanDialog.jsx + UnpickDialog.jsx).
 *
 * Flow: tap "Remove item" (remove-item-button) -> scan the product GTIN into the
 * unpick-product-scanner -> the qty dialog (the shared GetQuantityDialog, wrapped in
 * data-testid="unpick-qty-dialog") opens with default = packed qty -> enter the qty to remove
 * and press Done -> a MANDATORY target-HU scan (unpick-target-hu-scanner, no Skip button) ->
 * commit. On success the flow closes and returns to the step screen.
 *
 * Selector notes (verified against the real components):
 *   - The scanners are the BarcodeScannerComponent's #input-text[data-testid=...] (use
 *     BarcodeScannerComponent helpers, NOT raw .unpick-dialog locators).
 *   - The qty dialog IS the shared GetQuantityDialog (.get-qty-dialog / #qty-input / done-button),
 *     so GetQuantityDialog methods apply.
 */
export const PartialUnpickFlow = {
    // Opens the partial-unpick flow from the step screen and waits for the product scanner.
    clickRemoveItem: async () => await test.step(`${NAME} - Click "Remove item"`, async () => {
        await page.getByTestId('remove-item-button').tap();
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-product-scanner', timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Scans the product GTIN into the unpick product scanner; the backend resolves it to the
    // packed product and opens the qty dialog (default qty = packed qty).
    //
    // Robust to the scanner mount-race: the unpick-product-scanner is a freshly mounted dialog, and
    // its document-level keydown listener (useKeyboardBarcodeReader) arms one tick AFTER the input
    // element attaches. A scan dispatched in that window is silently dropped (no resolve request, so
    // the qty dialog never opens). A real operator simply scans again when nothing registers, so we
    // re-scan if the qty dialog has not opened shortly after the first scan. This is real-life scan
    // behaviour, not a recording aid.
    scanProduct: async ({ scannedCode }) => await test.step(`${NAME} - Scan product GTIN '${scannedCode}'`, async () => {
        const qtyDialog = page.locator('.get-qty-dialog');
        const maxScanAttempts = 3;
        for (let attempt = 1; attempt <= maxScanAttempts; attempt++) {
            await BarcodeScannerComponent.type({ scannedCode, testId: 'unpick-product-scanner' });
            try {
                await qtyDialog.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
                return;
            } catch (e) {
                if (attempt === maxScanAttempts) {
                    throw e;
                }
                // Scan dropped during the mount-race — the scanner is still on the product-scan stage.
                // Confirm it's still mounted, then scan again.
                await BarcodeScannerComponent.expectAttached({ testId: 'unpick-product-scanner', timeout: SLOW_ACTION_TIMEOUT });
            }
        }
    }),

    // Asserts the default qty offered in the qty dialog equals the currently-packed qty.
    expectDefaultQtyToRemove: async ({ qty }) => await test.step(`${NAME} - Expect default qty '${qty}'`, async () => {
        await GetQuantityDialog.expectQtyEntered(qty);
    }),

    // Enters the qty to remove in the (reused) GetQuantityDialog and presses Done -> moves to the
    // mandatory target-HU scan stage. We wait for the target-HU scanner to attach (the qty dialog
    // has closed and the UnpickDialog target scanner has mounted).
    enterQtyToRemove: async ({ qty }) => await test.step(`${NAME} - Enter qty to remove '${qty}'`, async () => {
        await GetQuantityDialog.typeQtyEntered(qty);
        await GetQuantityDialog.clickDone({});
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-target-hu-scanner', timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Scans the mandatory destination HU (no Skip available in this flow) and commits the
    // partial unpick; on success the flow closes and returns to the step screen.
    scanTargetHUAndCommit: async ({ qrCode }) => await test.step(`${NAME} - Scan target HU and commit`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: qrCode, testId: 'unpick-target-hu-scanner' });
        await PickingJobStepScreen.waitForScreen();
    }),

    // Full happy path: remove `qty` of the product scanned via `scannedCode` into the HU `targetHUQRCode`.
    removeItem: async ({ scannedCode, qty, targetHUQRCode, expectDefaultQty }) => await test.step(`${NAME} - Remove ${qty} of '${scannedCode}' into target HU`, async () => {
        await PartialUnpickFlow.clickRemoveItem();
        await PartialUnpickFlow.scanProduct({ scannedCode });
        if (expectDefaultQty != null) {
            await PartialUnpickFlow.expectDefaultQtyToRemove({ qty: expectDefaultQty });
        }
        await PartialUnpickFlow.enterQtyToRemove({ qty });
        await PartialUnpickFlow.scanTargetHUAndCommit({ qrCode: targetHUQRCode });
    }),

    // Negative path (AC2): scans a code into the product scanner WITHOUT expecting the qty dialog.
    // The backend rejects a code that does not resolve to a product packed in this job; the error
    // is surfaced through the BarcodeScannerComponent's single toast (the "one clear message"
    // surface, per AC2). Wrap the call site in expectErrorToast(...) to assert that toast.
    scanProductCode: async ({ scannedCode }) => await test.step(`${NAME} - Scan product code '${scannedCode}' (no advance expected)`, async () => {
        await BarcodeScannerComponent.type({ scannedCode, testId: 'unpick-product-scanner' });
    }),

    // Asserts the flow is still on the product-scan stage (scanner attached, no qty dialog).
    expectOnProductScanStage: async () => await test.step(`${NAME} - Expect still on product-scan stage`, async () => {
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-product-scanner', timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.locator('.get-qty-dialog')).toHaveCount(0);
    }),

    // Closes the flow via the dialog's Close button (returns to the step screen).
    close: async () => await test.step(`${NAME} - Close`, async () => {
        await page.locator('.unpick-dialog .buttons button.is-danger').tap();
        await PickingJobStepScreen.waitForScreen();
    }),
};
