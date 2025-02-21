import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page } from "../../common";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobStepScreen } from "./PickingJobStepScreen";

const NAME = 'PickingJobLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickLineScreen');

export const PickingJobLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    clickStepButton: async ({ index }) => await test.step(`${NAME} - Click step ${index} button`, async () => {
        await page.locator(`#step-${index}-button`).tap();
        await PickingJobStepScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobScreen.waitForScreen();
    }),
};