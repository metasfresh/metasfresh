import { test } from "../../../../playwright.config";
import { page } from "../../common";
import { GetDocumentNoDialog } from "../../dialogs/GetDocumentNoDialog";

const NAME = 'PickingJobsListFiltersScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersFiltersScreen');

export const PickingJobsListFiltersScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    filterByDocumentNo: async (documentNo) => await test.step(`${NAME} - Filter by documentNo ${documentNo}`, async () => {
        await page.locator('#filterByDocumentNo-button').tap();
        await GetDocumentNoDialog.waitForDialog();
        await GetDocumentNoDialog.typeDocumentNo(documentNo);
        await GetDocumentNoDialog.clickOKButton();
    }),

};