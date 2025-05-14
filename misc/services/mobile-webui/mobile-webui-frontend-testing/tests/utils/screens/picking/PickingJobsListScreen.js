import { page, SLOW_ACTION_TIMEOUT, VERY_FAST_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobsListFiltersScreen } from "./PickingJobsListFiltersScreen";
import { PickingJobsListScanScreen } from './PickingJobsListScanScreen';
import { expect } from '@playwright/test';

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

    filterByQRCode: async (scannedCode) => await test.step(`${NAME} - Filter by scanned code ${scannedCode}`, async () => {
        await PickingJobsListScreen.clickQRCodeFilterButton();
        await PickingJobsListScanScreen.scanQRCode(scannedCode);
    }),

    clearQRCodeFilter: async () => await test.step(`${NAME} - Clear QR code filter`, async () => {
        await PickingJobsListScreen.clickQRCodeFilterButton();
        // NOTE when we navigate to PickingJobsListScanScreen we expect the current QR code filter to be cleared,
        // so we just have to go back
        await PickingJobsListScanScreen.goBack();
    }),

    clickQRCodeFilterButton: async () => await test.step(`${NAME} - Click QR code filter button`, async () => {
        await page.getByTestId('filterByQRCode-button').tap({ timeout: VERY_FAST_ACTION_TIMEOUT });
        await PickingJobsListScanScreen.waitForScreen();
    }),

    startJob: async ({ documentNo, index, qtyToDeliver }) => {
        if (documentNo != null) {
            return await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
                await locateJobButtons({ documentNo }).tap();
                await PickingJobScreen.waitForScreen();
            });
        } else if (index != null) {
            return await test.step(`${NAME} - Start job by index ${index - 1}`, async () => {
                await locateJobButtons({ qtyToDeliver, index }).tap()
                await PickingJobScreen.waitForScreen();
            });
        } else {
            throw "No documentNo or index provided";
        }
    },

    expectJobButtons: async (expectationsArray) => await test.step(`${NAME} - Expect ${expectationsArray.length} job buttons`, async () => {
        //
        // First, wait until all expected buttons are attached 
        for (const expectation of expectationsArray) {
            await locateJobButtons(expectation).waitFor({ state: 'attached' });
        }

        //
        // Check it again to make sure all expected buttons are still there and there is one of each 
        for (const expectation of expectationsArray) {
            await locateJobButtons(expectation).waitFor({ state: 'attached' });
            await expect(locateJobButtons(expectation)).toHaveCount(1);
        }

        //
        // Make sure we have the expected number of buttons
        // NOTE: we do this at the end because expect does not wait for the elements to stabilize
        await expect(locateJobButtons()).toHaveCount(expectationsArray.length);
    }),
};

const locateJobButtons = ({ documentNo, index, qtyToDeliver, productId } = {}) => {
    let selector = '.wflauncher-button';
    if (qtyToDeliver != null) {
        selector += `[data-qtytodeliver="${qtyToDeliver}"]`;
    }
    if (productId != null) {
        selector += `[data-productid="${productId}"]`;
    }

    let locator = page.locator(selector);

    if (documentNo != null) {
        locator = locator.filter({ hasText: documentNo })
    }

    if (index != null) {
        locator = locator.nth(index - 1);
    }

    return locator;
};
