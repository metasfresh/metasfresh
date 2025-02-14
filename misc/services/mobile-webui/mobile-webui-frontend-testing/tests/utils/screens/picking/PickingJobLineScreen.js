import {test} from "../../../../playwright.config";
import {ID_BACK_BUTTON, page} from "../../common";
import {PickingJobScreen} from "./PickingJobScreen";
import {PickingJobStepScreen} from "./PickingJobStepScreen";

export const PickingJobLineScreen = {
    waitForScreen: async () => await test.step(`Wait for Picking Job Line Screen`, async () => {
        await page.locator('#PickLineScreen').waitFor();
    }),

    clickStepButton: async ({index}) => await test.step(`Click step ${index} button`, async () => {
        await page.locator(`#step-${index}-button`).tap();
        await PickingJobStepScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobScreen.waitForScreen();
    }),
};