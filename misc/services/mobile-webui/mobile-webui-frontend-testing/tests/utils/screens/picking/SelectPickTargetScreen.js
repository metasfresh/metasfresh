import {page, SLOW_ACTION_TIMEOUT} from "../../common";
import {test} from "../../../../playwright.config";

export const SelectPickTargetScreen = {
    waitForScreen: async () => await test.step(`Wait for Select Target screen`, async () => {
        await page.locator('#SelectPickTargetScreen').waitFor();
        await page.locator('.loading').waitFor({state: 'detached', timeout: SLOW_ACTION_TIMEOUT});
    }),

    clickLUButton: async ({lu}) => await test.step(`Click LU button`, async () => {
        await page.locator('button').filter({hasText: lu}).tap();
    }),

    clickCloseTargetButton: async () => await test.step(`Click Close Target LU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};