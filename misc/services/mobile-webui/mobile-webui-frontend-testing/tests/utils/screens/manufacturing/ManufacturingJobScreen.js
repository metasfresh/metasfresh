import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { RawMaterialIssueLineScreen } from './issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from './receipt/MaterialReceiptLineScreen';

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
};

