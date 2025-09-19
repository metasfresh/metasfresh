import { page } from "../common";
import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { LoginScreen } from "./LoginScreen";
import { ScanAnythingScreen } from './scanAnything/ScanAnythingScreen';
import { WorkplaceManagerScreen } from './workplaceManager/WorkplaceManagerScreen';

const NAME = 'HOME';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ApplicationsListScreen');

export const ApplicationsListScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached' });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    startApplication: async (applicationId) => await test.step(`${NAME} - Start application ${applicationId}`, async () => {
        await page.locator('#' + applicationId + '-button').tap();
    }),
    startPickingApplication: async () => await ApplicationsListScreen.startApplication('picking'),
    startScanAnythingApplication: async () => await ApplicationsListScreen.startApplication('scanAnything'),

    logout: async () => await test.step(`${NAME} - Logout`, async () => {
        await ApplicationsListScreen.expectVisible();
        await page.locator('#logout-button').tap();
        await LoginScreen.waitForScreen();
    }),

    changeWorkplace: async ({ qrCode }) => await test.step(`${NAME} - Change workplace to ${qrCode}`, async () => {
        await ApplicationsListScreen.startScanAnythingApplication();
        await ScanAnythingScreen.scanWorkplace({ qrCode });
        await WorkplaceManagerScreen.clickAssignButton();
        await WorkplaceManagerScreen.goBack();
        await ApplicationsListScreen.waitForScreen();
    }),

}
