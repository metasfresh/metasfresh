import {test} from "../../../../playwright.config";
import {page} from "../../common";

export const UnpickDialog = {
    waitForDialog: async () => await test.step(`Wait for Unpick Dialog`, async () => {
        await page.locator('.unpick-dialog').waitFor();
    }),

    clickSkipButton: async () => await test.step(`Click Skip button`, async () => {
        await page.locator('#skip-button').tap();
    }),
};