import { test } from "../../../../playwright.config";
import { page } from "../../common";

const NAME = 'PickingSlotScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ScanScreen');

export const PickingSlotScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await page.fill('#input-text', qrCode);
    }),
};