import { page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../../components/BarcodeScannerComponent';

const NAME = 'RawMaterialIssueLineScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#RawMaterialIssueLineScanScreen');

export const RawMaterialIssueLineScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

};