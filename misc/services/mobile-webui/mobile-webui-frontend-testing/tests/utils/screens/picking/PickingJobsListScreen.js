import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobsListFiltersScreen } from "./PickingJobsListFiltersScreen";

const NAME = 'PickingJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');

export const PickingJobsListScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    filterByDocumentNo: async (documentNo) => await test.step(`${NAME} - Filter by documentNo ${documentNo}`, async () => {
        await page.locator('#filter-button').tap();
        await PickingJobsListFiltersScreen.waitForScreen();
        await PickingJobsListFiltersScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.waitForScreen();
    }),

    startJob: async ({ documentNo }) => await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
        await page.locator('.wflauncher-button').filter({ hasText: documentNo }).tap();
        await PickingJobScreen.waitForScreen();
    }),
};
