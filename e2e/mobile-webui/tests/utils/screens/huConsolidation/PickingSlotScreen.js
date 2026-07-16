import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { HUConsolidationJobScreen } from './HUConsolidationJobScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { expect } from '@playwright/test';

/** data-testid of the GRAI scanner input on PickingSlotScreen (see PickingSlotScreen.jsx) */
const GRAI_SCANNER_TESTID = 'grai-scanner';

const NAME = 'PickingSlotScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickingSlotScreen');

export const PickingSlotScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await PickingSlotScreen.waitNotLoading();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    waitLoading: async () => await test.step(`${NAME} - Wait for screen to start loading`, async () => {
        await page.locator('.loading').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    waitNotLoading: async () => await test.step(`${NAME} - Wait for screen not loading`, async () => {
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingSlotScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    clickConsolidateAllButton: async () => await test.step(`${NAME} - Click Consolidate All button`, async () => {
        await page.getByTestId('consolidateAll-button').tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    clickConsolidateHUButton: async ({ huId }) => await test.step(`${NAME} - Click Consolidate huId=${huId} button`, async () => {
        if (!huId) throw Error("huId not provided");

        const button = page.getByTestId(`consolidate-${huId}-button`);
        await button.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT })
        await button.tap();
        await PickingSlotScreen.waitNotLoading();
    }),

    /**
     * Scan a TU's GRAI barcode on the PickingSlotScreen.
     *
     * The screen renders a BarcodeScannerComponent with data-testid="grai-scanner".
     * On a successful scan the backend resolves the HU by GRAI attribute, consolidates it onto
     * the target LU, and refreshes the slot content — the screen stays on PickingSlotScreen.
     * On error (HuNotFound, LuNotAtPickingSlot) the backend returns 4xx → error toast.
     *
     * @param {string} graiString - Canonical GRAI string ("{companyPrefix}.{assetType}.{serial}")
     */
    scanGRAI: async ({ graiString }) => await test.step(`${NAME} - Scan GRAI: ${graiString}`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: graiString, testId: GRAI_SCANNER_TESTID });
    }),

    /** The GRAI scanner is present — shown only when the consolidation customer requires GRAI. */
    expectScannerVisible: async () => await test.step(`${NAME} - Expect GRAI scanner present`, async () => {
        await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCANNER_TESTID });
    }),

    /** The GRAI scanner is absent — the customer does not require GRAI. */
    expectScannerNotVisible: async () => await test.step(`${NAME} - Expect GRAI scanner absent`, async () => {
        await BarcodeScannerComponent.expectNotAttached({ testId: GRAI_SCANNER_TESTID });
    }),
};
