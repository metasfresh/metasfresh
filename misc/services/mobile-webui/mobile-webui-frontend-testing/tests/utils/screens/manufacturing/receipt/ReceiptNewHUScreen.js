import { page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { MaterialReceiptLineScreen } from './MaterialReceiptLineScreen';

const NAME = 'ReceiptNewHUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReceiptNewHUScreen');

export const ReceiptNewHUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickLUTarget: async ({ luPIItemTestId }) => await test.step(`${NAME} - Click LU target "${luPIItemTestId}"`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.getByTestId(luPIItemTestId).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),
};
