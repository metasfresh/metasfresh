import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page } from "../../common";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobStepScreen } from "./PickingJobStepScreen";
import { GetQuantityDialog } from './GetQuantityDialog';
import { ManufacturingJobScreen } from '../manufacturing/ManufacturingJobScreen';

const NAME = 'PickingJobLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickLineScreen');

export const PickingJobLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    clickManufactureButton: async () => await test.step(`${NAME} - Click Manufacture button`, async () => {
        await page.getByTestId(`PickFromManufacturingOrder-button`).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

    clickPickHUButton: async () => await test.step(`${NAME} - Click Pick HU button`, async () => {
        await page.getByTestId(`PickHU-button`).tap();
        await GetQuantityDialog.waitForDialog();
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