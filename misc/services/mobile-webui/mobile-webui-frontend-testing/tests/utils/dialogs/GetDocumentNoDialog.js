import { test } from "../../../playwright.config";
import { page } from "../common";

export const GetDocumentNoDialog = {
    waitForDialog: async () => await test.step(`Wait for Get DocumentNo Dialog`, async () => {
        await page.locator('.get-documentNo-dialog').waitFor();
    }),

    typeDocumentNo: async (documentNo) => await test.step(`Type Document No ${documentNo}`, async () => {
        await page.type('#documentNo-input', documentNo);
    }),

    clickOKButton: async () => await test.step(`Click OK button`, async () => {
        await page.locator('#OK-button').click();
    }),

    clickCancelButton: async () => await test.step(`Click Cancel button`, async () => {
        await page.locator('#clearText-button').click();
    }),

};