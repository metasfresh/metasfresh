import { test } from '../../../../playwright.config';
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { HUMoveScreen } from './HUMoveScreen';
import { ChangeHUQtyDialog } from './ChangeHUQtyDialog';
import { ClearanceDialog } from './ClearanceDialog';
import { DISPOSAL_REASON_DAMAGED, HUDisposalScreen } from './HUDisposalScreen';
import { HUBulkActionsScreen } from './HUBulkActionsScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { LoginScreen } from '../LoginScreen';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { NewHUPreviewScreen } from './NewHUPreviewScreen';

const NAME = 'HUManagerScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUManagerScreen');

export const HUManagerScreen = {
    /** log in, start the HU Manager application from the applications list, wait for its scan screen */
    openAfterLogin: async ({ user }) => await test.step(`${NAME} - Log in and open the application`, async () => {
        await LoginScreen.login(user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();
    }),

    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    /** the back button of the bottom bar (to the applications list, from the scan screen or the HU info panel) */
    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await HUManagerScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
    }),

    waitForHUInfoPanel: async () => await test.step(`${NAME} - Wait for HU Info Panel`, async () => {
        await page.getByTestId('huinfo-table').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    scanHUQRCode: async ({ huQRCode }) => await test.step(`${NAME} - Scan HU QR Code`, async () => {
        await HUManagerScreen.expectVisible();

        await BarcodeScannerComponent.type(huQRCode);

        await HUManagerScreen.waitForHUInfoPanel();
    }),

    /** scans a code that is no existing HU (e.g. a product label) — the HU info panel is NOT awaited */
    scanLabel: async ({ label }) => await test.step(`${NAME} - Scan label`, async () => {
        await HUManagerScreen.expectVisible();
        await BarcodeScannerComponent.type(label);
    }),

    /** scans a product label that is no existing HU and waits for the "New HU" preview */
    scanLabelAndWaitForPreview: async ({ label }) => await test.step(`${NAME} - Scan label and wait for the New HU preview`, async () => {
        await HUManagerScreen.scanLabel({ label });
        await NewHUPreviewScreen.waitForScreen();
    }),

    /** the quantity (with unit, e.g. '1 PCE') on the HU info panel of the opened HU */
    expectHUQty: async (qty) => await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: qty }),

    /** the location (code) on the HU info panel of the opened HU */
    expectHULocator: async (locatorCode) => await HUManagerScreen.expectValue({ name: 'locator-value', expectedValue: locatorCode }),

    /** no HU info panel is shown (no HU is open) */
    expectNoHUInfo: async () => {
        await HUManagerScreen.expectValueMissing({ name: 'huinfo-table' });
        await HUManagerScreen.expectValueMissing({ name: 'qty-value' });
    },

    expectMoveButtonVisible: async () => await HUManagerScreen.expectButtonVisible({ testId: 'move-button' }),

    expectUndoButtonMissing: async () => await HUManagerScreen.expectButtonMissing({ testId: 'undo-button' }),

    /** "Scan again" button of the HU info panel: back to the scan step */
    clickScanAgain: async () => await HUManagerScreen.clickButton({ testId: 'scan-again-button' }),

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
    }),

    expectButtonVisible: async ({ testId }) => await test.step(`${NAME} - Expect "${testId}" button visible`, async () => {
        await expect(page.getByTestId(testId)).toBeVisible();
    }),

    expectButtonMissing: async ({ testId }) => await test.step(`${NAME} - Expect "${testId}" button NOT present`, async () => {
        await expect(page.getByTestId(testId)).toHaveCount(0);
    }),

    openGRAIScreen: async () => await test.step(`${NAME} - Open GRAI Screen`, async () => {
        await page.getByTestId('scan-grai-button').tap();
    }),
};

