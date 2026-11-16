import { page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { MaterialReceiptLineScreen } from './MaterialReceiptLineScreen';

const NAME = 'ReceiptNewHUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReceiptNewHUScreen');

export const ReceiptNewHUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickLUTarget: async ({ luPIItemTestId }) => await test.step(`${NAME} - Click LU target "${luPIItemTestId}"`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.getByTestId(luPIItemTestId).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    /**
     * Expects the LU target not to be offered, once the list of offered targets is shown (at least one target is displayed),
     * so a not yet rendered list cannot make this pass.
     */
    expectLUTargetNotPresent: async ({ luPIItemTestId }) => await test.step(`${NAME} - Expect LU target "${luPIItemTestId}" not offered`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(containerElement().locator('[data-testid^="luPIItem-"]').first()).toBeVisible();
        await expect(page.getByTestId(luPIItemTestId)).toHaveCount(0);
    }),

    expectLUTargetOffered: async ({ luPIItemTestId }) => await test.step(`${NAME} - Expect LU target "${luPIItemTestId}" offered`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(luPIItemTestId)).toBeVisible();
    }),

    clickTUTarget: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Click TU target "${tuPIItemProductTestId}"`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.getByTestId(tuPIItemProductTestId).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    expectTUTargetNotPresent: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Expect TU target "${tuPIItemProductTestId}" not present`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(tuPIItemProductTestId)).toHaveCount(0);
    }),
};
