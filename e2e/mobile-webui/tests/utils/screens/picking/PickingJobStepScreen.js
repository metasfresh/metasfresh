import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
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

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobStepScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

    // Whole-step unpick. With no argument the target-HU scan is skipped (the goods drop to loose
    // top-level TUs). Passing { targetHUQRCode } instead scans a target HU, so the unpicked goods
    // are moved onto that scanned HU. No-arg behaviour is preserved for existing callers.
    unpick: async ({ targetHUQRCode } = {}) => await test.step(`${NAME} - Click unpick`, async () => {
        await page.locator(`#unpick-button`).tap();
        await UnpickDialog.waitForDialog();
        if (targetHUQRCode) {
            await UnpickDialog.scanTargetHU(targetHUQRCode);
        } else {
            await UnpickDialog.clickSkipScanningTargetHUButton();
        }
        await PickingJobLineScreen.waitForScreen();
    }),
};