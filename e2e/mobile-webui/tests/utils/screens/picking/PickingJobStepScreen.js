import { test } from "../../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { UnpickDialog } from "../../dialogs/UnpickDialog";
import { PickingJobLineScreen } from "./PickingJobLineScreen";

const NAME = 'PickingJobStepScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickStepScreen');

export const PickingJobStepScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    unpick: async () => await test.step(`${NAME} - Click unpick`, async () => {
        await page.locator(`#unpick-button`).tap();
        await UnpickDialog.waitForDialog();
        await UnpickDialog.clickSkipScanningTargetHUButton();
        await PickingJobLineScreen.waitForScreen();
    }),
};