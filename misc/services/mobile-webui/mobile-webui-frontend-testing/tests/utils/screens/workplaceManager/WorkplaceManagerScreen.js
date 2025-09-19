import { ID_BACK_BUTTON, page } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { ApplicationsListScreen } from '../ApplicationsListScreen';

const NAME = 'Workplace Manager';

/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#app-workplaceManager');

export const WorkplaceManagerScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached' });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickAssignButton: async () => await test.step(`${NAME} - Click Assign button`, async () => {
        await page.getByTestId('assign-button').tap();
        await page.getByTestId('assign-button').waitFor({ state: 'detached' });
        await WorkplaceManagerScreen.waitForScreen();
    }),

    expectHeaderProperty: async ({ caption, value }) => await test.step(`${NAME} - Check header property`, async () => {
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
