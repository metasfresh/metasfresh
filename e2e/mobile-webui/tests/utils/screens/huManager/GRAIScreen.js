import { test } from '../../../../playwright.config';
import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { HUManagerScreen } from './HUManagerScreen';

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

    expectExtraGraiChipCount: async ({ expectedCount }) => await test.step(`${NAME} - Expect ${expectedCount} extra GRAI chip(s)`, async () => {
        const chips = page.getByTestId('grai-chip-extra');
        await expect(chips).toHaveCount(expectedCount);
    }),

    removeExtraGraiChip: async ({ index = 0 } = {}) => await test.step(`${NAME} - Remove extra GRAI chip at index ${index}`, async () => {
        const removeButtons = page.getByTestId('grai-chip-extra-remove');
        await removeButtons.nth(index).tap();
    }),

    expectCountExtraVisible: async ({ expectedExtra }) => await test.step(`${NAME} - Expect extra count shows ${expectedExtra}`, async () => {
        await expect(page.getByTestId('grai-count-extra')).toContainText(`${expectedExtra}`);
    }),

    expectCountExtraNotVisible: async () => await test.step(`${NAME} - Expect extra count not visible`, async () => {
        await expect(page.getByTestId('grai-count-extra')).not.toBeVisible();
    }),

    expectClearAllButtonEnabled: async () => await test.step(`${NAME} - Expect Clear All button enabled`, async () => {
        await expect(page.getByTestId('grai-clear-all-button')).toBeEnabled({ timeout: FAST_ACTION_TIMEOUT });
    }),

    expectClearAllButtonDisabled: async () => await test.step(`${NAME} - Expect Clear All button disabled`, async () => {
        await expect(page.getByTestId('grai-clear-all-button')).toBeDisabled({ timeout: FAST_ACTION_TIMEOUT });
    }),

    clearAllGrais: async () => await test.step(`${NAME} - Clear all GRAIs (with confirmation)`, async () => {
        await page.getByTestId('grai-clear-all-button').tap();
        await YesNoDialog.clickYesButton();
    }),

    clearAllGraisAndCancel: async () => await test.step(`${NAME} - Click Clear All then cancel`, async () => {
        await page.getByTestId('grai-clear-all-button').tap();
        await YesNoDialog.clickNoButton();
    }),

    tapSendButton: async () => await test.step(`${NAME} - Send GRAIs to backend`, async () => {
        await expect(page.getByTestId('grai-send-button')).toBeEnabled();
        const syncDone = page.waitForResponse(
            (resp) => resp.url().includes('/grai') && resp.request().method() === 'PUT',
            { timeout: 5000 }
        );
        await page.getByTestId('grai-send-button').tap();
        await syncDone;
        await expect(page.getByTestId('grai-send-button')).toBeDisabled({timeout: FAST_ACTION_TIMEOUT});
    }),

    expectSendButtonEnabled: async () => await test.step(`${NAME} - Expect Send button enabled`, async () => {
        await expect(page.getByTestId('grai-send-button')).toBeEnabled({timeout: FAST_ACTION_TIMEOUT});
    }),

    expectSendButtonDisabled: async () => await test.step(`${NAME} - Expect Send button disabled`, async () => {
        await expect(page.getByTestId('grai-send-button')).toBeDisabled({timeout: FAST_ACTION_TIMEOUT});
    }),

    tapUndoButton: async () => await test.step(`${NAME} - Tap Undo button (reload from backend)`, async () => {
        await expect(page.getByTestId('grai-undo-button')).toBeEnabled({timeout: FAST_ACTION_TIMEOUT});
        await page.getByTestId('grai-undo-button').tap();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
    }),

    reloadFromBackend: async () => await test.step(`${NAME} - Reload from backend (go back and re-open)`, async () => {
        await GRAIScreen.goBack();
        await HUManagerScreen.waitForScreen();
        await HUManagerScreen.openGRAIScreen();
        await GRAIScreen.expectVisible();
    }),

    expectGraiChipTexts: async ({ expectedTexts }) => await test.step(
        `${NAME} - Expect exact GRAI chip texts (${expectedTexts.length} chips)`,
        async () => {
            const chipTexts = await page.locator('.grai-chip-text').evaluateAll(
                (elements) => elements.map((el) => el.textContent.trim()).sort()
            );
            expect(chipTexts).toEqual([...expectedTexts].sort());
        }
    ),

    /**
     * Start listening for the next PUT /grai backend sync response.
     * Call BEFORE the action that triggers the sync (scan/clear), then await the returned promise
     * after the action to ensure data reached the backend before navigating away.
     */
    pendingBackendSync: () => page.waitForResponse(
        (resp) => resp.url().includes('/grai') && resp.request().method() === 'PUT',
        { timeout: 5000 }
    ),
};
