import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { expect } from '@playwright/test';
import { test } from '../../../../playwright.config';
import { GetQuantityDialog } from './GetQuantityDialog';
import { PickingJobScreen } from './PickingJobScreen';
import { PickingJobLineScreen } from './PickingJobLineScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickLineScanScreen';

const containerElement = () => page.locator('#PickLineScanScreen');

export const PickLineScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

    // The operator scans again although the app has offered no new scan target - what happens on the floor
    // when nothing seems to have moved after the previous scan and the trigger gets pulled once more.
    typeQRCodeWithoutWaitingForScanTarget: async (qrCode) => await test.step(`${NAME} - Type QR Code without waiting for a scan target`, async () => {
        await BarcodeScannerComponent.typeWithoutWaitingForScanTarget(qrCode);
    }),

    // A pick the operator confirmed is on its way to the server: the screen shows the spinner instead of the
    // scan step, so there is nothing on screen for a further scan to land in.
    expectPickInProgress: async () => await test.step(`${NAME} - Expect the confirmed pick to be in progress`, async () => {
        await expect(page.locator('.loading')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await BarcodeScannerComponent.expectNotAttached({ testId: 'scanHUBarcode-input' });
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

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickLineScanScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

};