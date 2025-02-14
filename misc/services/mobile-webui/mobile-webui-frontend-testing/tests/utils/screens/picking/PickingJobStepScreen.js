import {test} from "../../../../playwright.config";
import {page} from "../../common";
import {UnpickDialog} from "./UnpickDialog";
import {PickingJobLineScreen} from "./PickingJobLineScreen";

export const PickingJobStepScreen = {
    waitForScreen: async () => await test.step(`Wait for Picking Job Step Screen`, async () => {
        await page.locator('#PickStepScreen').waitFor();
    }),

    unpick: async () => await test.step(`Click unpick`, async () => {
        await page.locator(`#unpick-button`).tap();
        await UnpickDialog.waitForDialog();
        await UnpickDialog.clickSkipButton();
        await PickingJobLineScreen.waitForScreen();
    }),
};