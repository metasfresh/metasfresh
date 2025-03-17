import { page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { RawMaterialIssueLineScreen } from './issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from './receipt/MaterialReceiptLineScreen';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { ManufacturingJobsListScreen } from './ManufacturingJobsListScreen';

const NAME = 'ManufacturingJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const ManufacturingJobScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    issueRawProduct: async ({ index, qrCode, expectQtyEntered }) => await test.step(`${NAME} - Issue line ${index}`, async () => {
        await ManufacturingJobScreen.clickIssueButton({ index });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode, expectQtyEntered });
        await RawMaterialIssueLineScreen.goBack();

    }),

    clickIssueButton: async ({ index }) => await test.step(`${NAME} - Click issue button ${index}`, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.getByTestId(`issue-${index}-button`).tap();
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    clickReceiveButton: async ({ index }) => await test.step(`${NAME} - Click receive button ${index}`, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.getByTestId(`receipt-${index}-button`).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    complete: async () => await test.step(`${NAME} - Complete`, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await ManufacturingJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};

