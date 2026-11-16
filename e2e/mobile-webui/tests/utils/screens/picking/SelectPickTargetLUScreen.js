import { ID_BACK_BUTTON, page, revealForCaptureIfEnabled, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";

const NAME = 'SelectPickTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectPickTargetScreen');

export const SelectPickTargetLUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    goBack: async () => await test.step(`${NAME} - Go back (footer Back button)`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
    }),

    // Asserts the title bar shows the expected screen caption. Used to prove a device/browser Back press
    // does NOT revert it (e.g. to the app-level caption "Picking") — it must stay put.
    expectTitle: async (expected) => await test.step(`${NAME} - Expect title '${expected}'`, async () => {
        await page.locator('.app-caption', { hasText: expected }).waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLUButton: async ({ lu }) => await test.step(`${NAME} - Click LU button`, async () => {
        const luButton = page.locator('button').filter({ hasText: lu });
        // Capture mode only (UAT_CAPTURE): the list may still show the loading spinner on the recording;
        // wait until the offered target is rendered and hold it in view before tapping it.
        await revealForCaptureIfEnabled(luButton);
        await luButton.tap();
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target LU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};