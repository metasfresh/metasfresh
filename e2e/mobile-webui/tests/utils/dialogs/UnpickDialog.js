import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { page, SLOW_ACTION_TIMEOUT } from "../common";
import { BarcodeScannerComponent } from "../components/BarcodeScannerComponent";

const NAME = 'UnpickDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.unpick-dialog');

export const UnpickDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickSkipScanningTargetHUButton: async () => await test.step(`${NAME} - Click Skip button`, async () => {
        await page.locator('#skip-button').tap();
    }),

    // The dialog is still open and its scanner still armed — asserted `visible`, not merely attached,
    // because the point is that the operator can see the panel and scan again after a rejected code.
    expectDialogVisible: async () => await test.step(`${NAME} - Expect dialog to be displayed`, async () => {
        await expect(containerElement()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Scans the target HU's label — any format the shared HUQRCodesService bridge accepts (a
    // metasfresh global QR code, a legacy ExternalBarcode, or the plain M_HU.Value).
    scanTargetHU: async (targetCode) => await test.step(`${NAME} - Scan target HU '${targetCode}'`, async () => {
        await BarcodeScannerComponent.type(targetCode);
    }),
};