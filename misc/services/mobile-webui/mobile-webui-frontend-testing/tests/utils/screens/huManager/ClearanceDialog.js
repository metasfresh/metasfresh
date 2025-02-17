import { test } from '../../../../playwright.config';
import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';

// noinspection JSUnusedGlobalSymbols
export const CLEARANCE_STATUS_Cleared = 'Cleared';
export const CLEARANCE_STATUS_Quarantined = 'Quarantined';
// noinspection JSUnusedGlobalSymbols
export const CLEARANCE_STATUS_TestPending = 'TestPending';
// noinspection JSUnusedGlobalSymbols
export const CLEARANCE_STATUS_Locked = 'Locked';

const NAME = 'ClearanceDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('ClearanceDialog');

export const ClearanceDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait to open`, async () => {
        await containerElement().waitFor();
    }),

    waitForDialogToClose: async () => await test.step(`${NAME} - Wait to close`, async () => {
        await containerElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickClearanceStatus: async (status) => await test.step(`${NAME} - Click on Clearance "${status}"`, async () => {
        await ClearanceDialog.expectVisible();
        await page.getByTestId(`clearanceStatus-${status}`).tap({ timeout: FAST_ACTION_TIMEOUT });
    }),

    typeNote: async (note) => await test.step(`${NAME} - Type note: ${note}`, async () => {
        await ClearanceDialog.expectVisible();
        await page.getByTestId('clearanceNote-input').type(note);
    }),

    clickOK: async () => await test.step(`${NAME} - Click OK`, async () => {
        await ClearanceDialog.expectVisible();
        await page.getByTestId('ok-button').tap();
        await ClearanceDialog.waitForDialogToClose();
    }),
};