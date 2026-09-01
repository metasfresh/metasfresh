import { page, SLOW_ACTION_TIMEOUT } from "../common";
import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { LoginScreen } from "./LoginScreen";
import { ScanAnythingScreen } from './scanAnything/ScanAnythingScreen';
import { WorkplaceManagerScreen } from './workplaceManager/WorkplaceManagerScreen';
import { BarcodeScannerComponent } from '../components/BarcodeScannerComponent';

const NAME = 'HOME';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ApplicationsListScreen');

export const ApplicationsListScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    // Assert the home menu is NOT displayed — used to prove a workflow start did not bounce the
    // operator back to the root menu.
    expectNotDisplayed: async () => await test.step(`${NAME} - Expect NOT to be displayed`, async () => {
        await expect(containerElement()).toHaveCount(0);
    }),

    expectLogoutButtonReachable: async () => await test.step(`${NAME} - Expect logout button reachable by scrolling`, async () => {
        const logoutButton = page.locator('#logout-button');
        await expect(logoutButton).toBeVisible();
        await logoutButton.scrollIntoViewIfNeeded();
        await expect(logoutButton).toBeInViewport();
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

    scanBarcode: async (barcode) => await test.step(`${NAME} - Scan barcode`, async () => {
        await BarcodeScannerComponent.type(barcode);
    }),

    scanBarcodeViaIME: async (barcode) => await test.step(`${NAME} - Scan barcode via IME`, async () => {
        await BarcodeScannerComponent.typeViaIME(barcode);
    }),
}
