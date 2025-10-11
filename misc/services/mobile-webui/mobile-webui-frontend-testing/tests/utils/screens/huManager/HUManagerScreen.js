import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { HUMoveScreen } from './HUMoveScreen';
import { ChangeHUQtyDialog } from './ChangeHUQtyDialog';
import { ClearanceDialog } from './ClearanceDialog';
import { DISPOSAL_REASON_DAMAGED, HUDisposalScreen } from './HUDisposalScreen';
import { HUBulkActionsScreen } from './HUBulkActionsScreen';

const NAME = 'HUManagerScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUManagerScreen');

export const HUManagerScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    waitForHUInfoPanel: async () => await test.step(`${NAME} - Wait for HU Info Panel`, async () => {
        await page.getByTestId('huinfo-table').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    scanHUQRCode: async ({ huQRCode }) => await test.step(`${NAME} - Scan HU QR Code`, async () => {
        await HUManagerScreen.expectVisible();

        console.log('Scanning QR code:\n' + huQRCode);
        await page.type('#input-text', huQRCode);

        await HUManagerScreen.waitForHUInfoPanel();
    }),

    expectValue: async ({ name, expectedValue, expectedInternalValue, missing }) => {
        const stepValue = expectedValue ?? expectedInternalValue ?? (missing ? '*missing*' : undefined);
        return await test.step(`${NAME} - Expect "${name}" to be "${stepValue}"`, async () => {
            if (expectedValue !== undefined) await expect(page.getByTestId(name)).toHaveText(expectedValue);
            if (expectedInternalValue !== undefined) await expect(page.getByTestId(name)).toHaveAttribute('data-internalvalue', expectedInternalValue);
            if (missing) await expect(page.getByTestId(name)).toHaveCount(0);
        });
    },

    expectValueMissing: async ({ name }) => HUManagerScreen.expectValue({ name, missing: true }),

    clickButton: async ({ testId }) => await test.step(`${NAME} - Click "${testId}" button`, async () => {
        await page.getByTestId(testId).tap();
    }),
    
    expectButtonsInOrder: async (testIds) => await test.step(`${NAME} - Expect buttons in "${testIds.join()}" order`, async () => {
        const buttons = page.locator('.section .complete-btn');
        const actualTestIds = await buttons.evaluateAll((elements) =>
            elements.map((el) => el.getAttribute('data-testid'))
        );

        expect(actualTestIds).toEqual(testIds);
    }),
        
    dispose: async ({ reason = DISPOSAL_REASON_DAMAGED } = {}) => await test.step(`${NAME} - Dispose HU`, async () => {
        await HUManagerScreen.clickButton({ testId: 'dispose-button' });
        await HUDisposalScreen.waitForScreen();
        await HUDisposalScreen.dispose({ reason });
    }),

    move: async ({ qrCode }) => await test.step(`${NAME} - Move HU`, async () => {
        await HUManagerScreen.clickButton({ testId: 'move-button' });
        await HUMoveScreen.waitForScreen();
        await HUMoveScreen.move({ qrCode });
    }),

    changeQty: async ({ expectQtyEntered, qtyEntered, description }) => await test.step(`${NAME} - Change Qty`, async () => {
        await HUManagerScreen.clickButton({ testId: 'change-qty-button' });
        await ChangeHUQtyDialog.waitForDialog();
        if (expectQtyEntered != null) await ChangeHUQtyDialog.expectFieldValue({ field: 'qtyEntered', expectedValue: expectQtyEntered });
        if (qtyEntered != null) await ChangeHUQtyDialog.type({ field: 'qtyEntered', value: qtyEntered });
        if (description != null) await ChangeHUQtyDialog.type({ field: 'description', value: description });
        await ChangeHUQtyDialog.clickOK();
    }),

    changeClearanceStatus: async ({ status, note }) => await test.step(`${NAME} - Change Clearance Status`, async () => {
        await HUManagerScreen.clickButton({ testId: 'set-clearance-button' });
        await ClearanceDialog.waitForDialog();
        await ClearanceDialog.clickClearanceStatus(status);
        if (note != null) await ClearanceDialog.typeNote(note);
        await ClearanceDialog.clickOK();
    }),

    bulkActions: async ({ targetLocator }) => await test.step(`${NAME} - Bulk Actions`, async () => {
        await HUManagerScreen.clickButton({ testId: 'bulk-actions-button' });
        await HUBulkActionsScreen.waitForScreen();
        await HUBulkActionsScreen.move({ targetLocator });
    })
};

