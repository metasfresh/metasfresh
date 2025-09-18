import { page } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { WorkplaceManagerScreen } from '../workplaceManager/WorkplaceManagerScreen';

const NAME = 'ScanAnythingScreen';

/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ApplicationsListScreen');

export const ScanAnythingScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached' });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanWorkplace: async ({ qrCode }) => await test.step(`${NAME} - Scan Workplace QR code`, async () => {
        await ScanAnythingScreen.waitForScreen();
        await page.locator('#input-text').type(`${qrCode}`)
        await WorkplaceManagerScreen.waitForScreen();
    }),
}
