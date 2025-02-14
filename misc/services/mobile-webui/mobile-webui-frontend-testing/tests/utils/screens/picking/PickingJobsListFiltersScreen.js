import {test} from "../../../../playwright.config";
import {page} from "../../common";
import {GetDocumentNoDialog} from "../../dialogs/GetDocumentNoDialog";

export const PickingJobsListFiltersScreen = {
    waitForScreen: async () => await test.step('Wait for picking jobs list filters screen', async () => {
        await page.locator('#WFLaunchersFiltersScreen').waitFor();
    }),
    
    filterByDocumentNo: async (documentNo) => await test.step(`Filter by documentNo ${documentNo}`, async () => {
        await page.locator('#filterByDocumentNo-button').tap();
        await GetDocumentNoDialog.waitForDialog();
        await GetDocumentNoDialog.typeDocumentNo(documentNo);
        await GetDocumentNoDialog.clickOKButton();
    }),

};