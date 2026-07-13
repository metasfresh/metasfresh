import { ID_BACK_BUTTON, page, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'WorkplaceManagerScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WorkplaceManagerScreen');

export const WorkplaceManagerScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached' });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanWorkplace: async (qrCode) => await test.step(`${NAME} - Scan workplace QR '${qrCode}'`, async () => {
        // Scanning into a just-mounted home screen can race the barcode reader's keydown listener,
        // which attaches in a post-render effect slightly after its input is in the DOM — a scan fired
        // in that window is silently dropped and never routes. Retry the scan until the workplace
        // screen actually opens (a genuinely non-routing scan still fails once the outer timeout elapses).
        await expect(async () => {
            await BarcodeScannerComponent.type(qrCode);
            await containerElement().waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
        }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
        await WorkplaceManagerScreen.waitForScreen();
    }),

    clickAssignButton: async () => await test.step(`${NAME} - Click Assign button`, async () => {
        await page.getByTestId('assign-button').tap();
        await page.getByTestId('assign-button').waitFor({ state: 'detached' });
        await WorkplaceManagerScreen.waitForScreen();
    }),

    expectHeaderProperty: async ({ caption, value }) => await test.step(`${NAME} - Check header property '${caption}'='${value}'`, async () => {
        const row = await page.locator(
            `tr:has(th:has-text("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await WorkplaceManagerScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),
}
