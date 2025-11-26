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

    scanQRCode: async ({ qrCode, expectQtyEntered }) => await test.step(`${NAME} - Scan QR code`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        await GetQuantityDialog.fillAndPressDone({expectQtyEntered});
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await RawMaterialIssueLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

}