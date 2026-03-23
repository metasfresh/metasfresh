import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'GRAIScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#GRAIScreen');

export const GRAIScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    expectGraiCount: async ({ expectedCount }) => await test.step(`${NAME} - Expect GRAI count to be "${expectedCount}"`, async () => {
        const countEl = page.getByTestId('grai-count');
        await expect(countEl).toContainText(`${expectedCount}`);
    }),

    expectGraiChipCount: async ({ expectedCount }) => await test.step(`${NAME} - Expect ${expectedCount} GRAI chip(s)`, async () => {
        const chips = page.getByTestId('grai-chip');
        await expect(chips).toHaveCount(expectedCount);
    }),

    scanGraiBarcode: async ({ barcodeString }) => await test.step(`${NAME} - Scan GRAI barcode`, async () => {
        await GRAIScreen.expectVisible();
        await BarcodeScannerComponent.type(barcodeString);
    }),

    expectGraiChipWithText: async ({ text }) => await test.step(`${NAME} - Expect GRAI chip containing "${text}"`, async () => {
        await expect(page.locator('.grai-chip-text').filter({ hasText: text })).toBeVisible();
    }),

    removeGraiChip: async ({ index = 0 } = {}) => await test.step(`${NAME} - Remove GRAI chip at index ${index}`, async () => {
        const removeButtons = page.getByTestId('grai-chip-remove');
        await removeButtons.nth(index).tap();
    }),
};
