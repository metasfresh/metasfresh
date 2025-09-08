import { test } from "../../../playwright.config";
import { page } from "../common";

const NAME = 'GetDocumentNoDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.get-documentNo-dialog');

export const GetDocumentNoDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    typeDocumentNo: async (documentNo) => await test.step(`${NAME} - Type Document No ${documentNo}`, async () => {
        await page.type('#documentNo-input', documentNo);
    }),

    clickOKButton: async () => await test.step(`${NAME} - Click OK button`, async () => {
        await page.locator('#OK-button').click();
    }),

    clickCancelButton: async () => await test.step(`${NAME} - Click Cancel button`, async () => {
        await page.locator('#clearText-button').click();
    }),

};