import { page, SLOW_ACTION_TIMEOUT, step } from "../../common";
import { InventoryScanScreen } from './InventoryScanScreen';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';

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

    countHU: async ({ locatorQRCode, huQRCode, expectQtyBooked, qtyCount }) => await step(`${NAME} - Scan HU and Report Counting`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await InventoryScanScreen.waitForScreen();

        await InventoryScanScreen.countHU({ locatorQRCode, huQRCode, expectQtyBooked, qtyCount });
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

};
