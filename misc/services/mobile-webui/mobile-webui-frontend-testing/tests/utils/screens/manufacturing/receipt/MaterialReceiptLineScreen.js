import { page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { ReceiptReceiveTargetScreen } from './ReceiptReceiveTargetScreen';

const NAME = 'MaterialReceiptLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#MaterialReceiptLineScreen');

export const MaterialReceiptLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickReceiveTargetButton: async () => await test.step(`${NAME} - Click receive target button`, async () => {
        await page.getByTestId('receive-target-button').tap();
        await ReceiptReceiveTargetScreen.waitForScreen();
    }),
};