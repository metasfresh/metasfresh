import { test } from '../../../../playwright.config';
import { FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from '../../common';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickingGraiScanPanel';

/** testId for the scanner input on the pick-target TU screen */
const GRAI_SCAN_TESTID = 'grai-scan-input';

/**
 * Screen object for the live GRAI scanner panel rendered on the
 * pick-target TU screen when {@code graiScanEnabled=true}.
 *
 * The scanner input has {@code data-testid="grai-scan-input"}.
 */
export const PickingGraiScanPanel = {
    /**
     * Wait for the GRAI scanner input to be present, confirming
     * that the feature is active for this BPartner.
     */
    waitForScanner: async () => await test.step(`${NAME} - Wait for GRAI scanner to be present`, async () => {
        await BarcodeScannerComponent.waitToAttach({ testId: GRAI_SCAN_TESTID });
    }),

    /**
     * Assert the GRAI scanner is visible (graiScanEnabled=true).
     */
    expectScannerVisible: async () => await test.step(`${NAME} - Expect GRAI scanner visible`, async () => {
        await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCAN_TESTID, timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Assert the GRAI scanner is NOT present (graiScanEnabled=false).
     */
    expectScannerNotVisible: async () => await test.step(`${NAME} - Expect GRAI scanner NOT present`, async () => {
        await BarcodeScannerComponent.expectNotAttached({ testId: GRAI_SCAN_TESTID, timeout: FAST_ACTION_TIMEOUT });
    }),

    /**
     * Scan a GRAI barcode (in canonical dot-separated or GS1 AI 8003 format).
     * Dispatches keyboard events via BarcodeScannerComponent targeting the GRAI scanner input.
     *
     * Uses `BarcodeScannerComponent.type()` which dispatches at document level and appends Enter.
     * The `useKeyboardBarcodeReader` hook calls `event.preventDefault()` on Enter, preventing
     * the focused input's `handleInputTextKeyPress` from double-invoking validateScannedBarcodeAndForward.
     */
    scanGrai: async ({ graiString }) => await test.step(`${NAME} - Scan GRAI: ${graiString}`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: graiString, testId: GRAI_SCAN_TESTID });
    }),
};
