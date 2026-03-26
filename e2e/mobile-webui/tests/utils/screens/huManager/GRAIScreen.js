import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { YesNoDialog } from '../../dialogs/YesNoDialog';

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

    scanGraiBatch: async ({ graiCodes }) => await test.step(
        `${NAME} - Scan RFID batch of ${graiCodes.length} GRAIs`,
        async () => {
            await GRAIScreen.expectVisible();
            await BarcodeScannerComponent.typeBatch({ codes: graiCodes });
        }
    ),

    removeGraiChip: async ({ index = 0 } = {}) => await test.step(`${NAME} - Remove GRAI chip at index ${index}`, async () => {
        const removeButtons = page.getByTestId('grai-chip-remove');
        await removeButtons.nth(index).tap();
    }),

    expectClearAllButtonVisible: async () => await test.step(`${NAME} - Expect Clear All button visible`, async () => {
        await expect(page.getByTestId('grai-clear-all-button')).toBeVisible();
    }),

    expectClearAllButtonNotVisible: async () => await test.step(`${NAME} - Expect Clear All button not visible`, async () => {
        await expect(page.getByTestId('grai-clear-all-button')).not.toBeVisible();
    }),

    clearAllGrais: async () => await test.step(`${NAME} - Clear all GRAIs (with confirmation)`, async () => {
        await page.getByTestId('grai-clear-all-button').tap();
        await YesNoDialog.clickYesButton();
    }),

    clearAllGraisAndCancel: async () => await test.step(`${NAME} - Click Clear All then cancel`, async () => {
        await page.getByTestId('grai-clear-all-button').tap();
        await YesNoDialog.clickNoButton();
    }),
};
