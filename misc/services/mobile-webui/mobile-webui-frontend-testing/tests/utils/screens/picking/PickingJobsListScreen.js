import {page, SLOW_ACTION_TIMEOUT} from "../../common";
import {test} from "../../../../playwright.config";
import {PickingJobScreen} from "./PickingJobScreen";
import {PickingJobsListFiltersScreen} from "./PickingJobsListFiltersScreen";

export const PickingJobsListScreen = {
    waitForScreen: async ({timeout: timeoutParam}={}) => await test.step('Wait for picking jobs list screen', async () => {
        const timeout = timeoutParam != null ? timeoutParam : SLOW_ACTION_TIMEOUT;
        await page.locator('#WFLaunchersScreen').waitFor({timeout});
        await page.locator('.loading').waitFor({state: 'detached', timeout});
    }),

    filterByDocumentNo: async (documentNo) => await test.step(`Filter picking jobs by documentNo ${documentNo}`, async () => {
        await page.locator('#filter-button').tap();
        await PickingJobsListFiltersScreen.waitForScreen();
        await PickingJobsListFiltersScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.waitForScreen();
    }),

    startJob: async ({documentNo}) => await test.step(`Start picking job for documentNo ${documentNo}`, async () => {
        await page.locator('.wflauncher-button').filter({hasText: documentNo}).tap();
        await PickingJobScreen.waitForScreen();
    }),
};
