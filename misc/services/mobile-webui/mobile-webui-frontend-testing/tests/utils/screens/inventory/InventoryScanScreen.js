import { test } from "../../../../playwright.config";
import { page, step } from "../../common";
import { expect } from '@playwright/test';
import { InventoryJobScreen } from './InventoryJobScreen';

const NAME = 'InventoryScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator(`#${NAME}`);

export const InventoryScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    waitForPanel: async (panel) => await test.step(`${NAME} - Wait for panel '${panel}'`, async () => {
        await page.locator(`.panel-${panel}`).waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        console.log('Scanning HU QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),

    countHU: async ({ locatorQRCode, huQRCode, expectQtyBooked, qtyCount, attributes, expectedAttributes }) => await step(`${NAME} - Scan HU and Report Counting`, async () => {
        await InventoryScanScreen.waitForPanel('ScanLocator');
        await InventoryScanScreen.typeQRCode(locatorQRCode);

        await InventoryScanScreen.waitForPanel('ScanHU');
        await InventoryScanScreen.typeQRCode(huQRCode);

        await InventoryScanScreen.waitForPanel('FillData');

        //
        // Expectations
        if (expectQtyBooked != null) {
            await expect(page.getByTestId('qty-booked')).toHaveText(expectQtyBooked);
        }
        if (expectedAttributes != null) {
            for (const [attribute, value] of Object.entries(expectedAttributes)) {
                await expect(page.getByTestId(`attr-${attribute}-field`)).toHaveText(`${value}`);
            }
        }

        //
        // Fill fields
        if (qtyCount != null) {
            await page.getByTestId('qty-count').type(`${qtyCount}`);
        }
        if (attributes != null) {
            for (const [attribute, value] of Object.entries(attributes)) {
                await page.getByTestId(`attr-${attribute}-field`).type(`${value}`);
            }
        }

        await page.getByTestId('ok-button').click();
        await InventoryJobScreen.waitForScreen();
    }),

};