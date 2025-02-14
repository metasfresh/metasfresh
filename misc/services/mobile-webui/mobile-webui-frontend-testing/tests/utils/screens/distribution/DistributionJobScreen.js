import {test} from "../../../../playwright.config";
import {page, SLOW_ACTION_TIMEOUT} from "../../common";

export const DistributionJobScreen = {
    waitForScreen: async () => await test.step(`Wait for Distribution Job screen`, async () => {
        await page.locator('#WFProcessScreen').waitFor({timeout: SLOW_ACTION_TIMEOUT});
    }),
};