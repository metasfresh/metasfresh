import { page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { ReceiptNewHUScreen } from './ReceiptNewHUScreen';

const NAME = 'ReceiptReceiveTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReceiptReceiveTargetScreen');

export const ReceiptReceiveTargetScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickNewHUButton: async () => await test.step(`${NAME} - Click new HU button`, async () => {
        await page.getByTestId('new-hu-button').tap();
        await ReceiptNewHUScreen.waitForScreen();
    }),

    clickExistingLUButton: async () => await test.step(`${NAME} - Click existing LU button`, async () => {
        await page.getByTestId('existing-lu-button').tap();
        // TODO waitForScreen 
    }),
};
