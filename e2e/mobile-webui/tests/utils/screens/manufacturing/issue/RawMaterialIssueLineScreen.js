import { ID_BACK_BUTTON, page } from '../../../common';
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
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickScanButton: async () => await test.step(`${NAME} - Click Scan button`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
    }),

    scanQRCode: async ({ qrCode, expectQtyEntered }) => await test.step(`${NAME} - Scan QR code`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered });
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

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await RawMaterialIssueLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

}