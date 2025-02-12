import {page} from "../common";
import {test} from "../../../playwright.config";
import {expect} from "@playwright/test";
import {LoginScreen} from "./LoginScreen";

export const ApplicationsListScreen = {
    waitForScreen: async () => await test.step('Wait for HOME screen', async () => {
        await page.locator('#ApplicationsListScreen').waitFor();
    }),

    expectVisible: async () => await test.step('Expecting HOME screen displayed', async () => {
        expect(page.locator('#ApplicationsListScreen')).toBeVisible();
    }),

    startApplication: async (applicationId) => await test.step('Start application ' + applicationId, async () => {
        await page.locator('#' + applicationId + '-button').tap();
    }),

    logout: async () => await test.step('Logout', async () => {
        await ApplicationsListScreen.expectVisible();
        await page.locator('#logout-button').tap();
        await LoginScreen.waitForScreen();
    }),
}
