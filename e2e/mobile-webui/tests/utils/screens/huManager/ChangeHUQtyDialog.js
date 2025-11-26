import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';

const NAME = 'ChangeHUQtyDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('ChangeHUQtyDialog');

export const ChangeHUQtyDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait to open`, async () => {
        await containerElement().waitFor();
    }),

    waitForDialogToClose: async () => await test.step(`${NAME} - Wait to close`, async () => {
        await containerElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    expectFieldValue: async ({ field, expectedValue }) => await test.step(`${NAME} - Expect "${field}" to have value "${expectedValue}"`, async () => {
        await ChangeHUQtyDialog.expectVisible();
        await expect(page.getByTestId(field)).toHaveValue(expectedValue);
    }),

    type: async ({ field, value }) => await test.step(`${NAME} - Type into ${field}: ${value}`, async () => {
        await ChangeHUQtyDialog.expectVisible();
        await page.getByTestId(field).type(value);
    }),

    clickOK: async () => await test.step(`${NAME} - Click OK`, async () => {
        await ChangeHUQtyDialog.expectVisible();
        await page.getByTestId('ok-button').tap();
        await ChangeHUQtyDialog.waitForDialogToClose();
    }),
};