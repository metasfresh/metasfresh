import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { RawMaterialIssueLineScanScreen } from './RawMaterialIssueLineScanScreen';
import { GetQuantityDialog } from '../../picking/GetQuantityDialog';
import { ManufacturingJobScreen } from '../ManufacturingJobScreen';

const NAME = 'RawMaterialIssueLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#RawMaterialIssueLineScreen');

export const RawMaterialIssueLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanQRCode: async ({ qrCode, expectQtyEntered, expectQtyTarget, expectQtyRemaining, qtyEntered }) => await test.step(`${NAME} - Scan QR code`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        if (expectQtyTarget != null) {
            await GetQuantityDialog.expectUserInfoValue({ captionKey: 'general.QtyToPick_Total', expectedValue: expectQtyTarget });
        }
        if (expectQtyRemaining != null) {
            await GetQuantityDialog.expectUserInfoValue({ captionKey: 'general.QtyToPick', expectedValue: expectQtyRemaining });
        }
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, qtyEntered });
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    expectScanButtonVisible: async ({ visible, noIndicators }) => await test.step(`${NAME} - Expect Scan button visible = ${visible}`, async () => {
        const locator = page.getByTestId('scanQRCode-button');
        if (visible) {
            await expect(locator).toBeVisible();
        } else {
            // Button shall not be present at all when readOnly
            await expect(locator).toHaveCount(0);
        }

        if (noIndicators) {
            await expect(locator.getByTestId('indicator')).toHaveCount(0);
            await expect(locator.getByTestId('indicator2')).toHaveCount(0);
        }
    }),

    scanQRCodeExpectError: async ({ qrCode, qtyEntered }) => await test.step(`${NAME} - Scan QR code (expect error, qty=${qtyEntered})`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        await expectErrorToast(`${NAME} over-issue rejected`, async () => {
            await GetQuantityDialog.fillAndPressDone({ qtyEntered });
            // On success: dialog closes and navigates back to line screen.
            // On error (after fix): dialog stays open → waitToClose hangs → toast wins.
        });
        await GetQuantityDialog.waitForDialog(); // dialog still open — worker was not navigated away
    }),

    retypeQtyAndConfirm: async ({ qtyEntered }) => await test.step(`${NAME} - Retype qty=${qtyEntered} and confirm`, async () => {
        await GetQuantityDialog.fillAndPressDone({ qtyEntered });
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await RawMaterialIssueLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

}
