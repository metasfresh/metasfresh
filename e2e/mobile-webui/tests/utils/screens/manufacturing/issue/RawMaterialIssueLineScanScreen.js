import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../../components/BarcodeScannerComponent';
import { RawMaterialIssueLineScreen } from './RawMaterialIssueLineScreen';

const NAME = 'RawMaterialIssueLineScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#RawMaterialIssueLineScanScreen');

export const RawMaterialIssueLineScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await RawMaterialIssueLineScanScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

};