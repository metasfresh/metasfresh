import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { InventoryScanScreen } from './InventoryScanScreen';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { InventoryJobsListScreen } from './InventoryJobsListScreen';

const NAME = 'InventoryJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const InventoryJobScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    getJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/inventory-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    countHU: async ({ locatorQRCode, huQRCode, expectQtyBooked, qtyCount, attributes }) => await step(`${NAME} - Scan HU and Report Counting`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await InventoryScanScreen.waitForScreen();

        await InventoryScanScreen.countHU({ locatorQRCode, huQRCode, expectQtyBooked, qtyCount, attributes });
    }),

    expectLineButton: async ({ productId, locatorId, qtyBooked, qtyCount }) => await test.step(`${NAME} - Expect job button by productId=${productId}, locatorId=${locatorId}`, async () => {
        const lineButton = await page.getByTestId(`line-P${productId}-L${locatorId}-button`);
        const lineButtonDetails = await lineButton.locator('.button-details');

        if (qtyBooked != null) {
            await test.step(`Expect qtyBooked=${qtyBooked}`, async () => {
                const locator = lineButtonDetails.locator('.value1');
                await expect(locator).toBeVisible();
                await expect(locator).toHaveText(`${qtyBooked}`);
            });
        }
        if (qtyCount != null) {
            await test.step(`Expect qtyCount=${qtyCount}`, async () => {
                const locator = lineButtonDetails.locator('.value2');
                await expect(locator).toBeVisible();
                await expect(locator).toHaveText(`${qtyCount}`);
            });
        }
    }),

    complete: async () => await step(`${NAME} - Complete`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await InventoryJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await InventoryJobScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await InventoryJobsListScreen.waitForScreen();
    }),
};
