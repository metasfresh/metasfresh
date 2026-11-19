import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobStepScreen } from "./PickingJobStepScreen";
import { GetQuantityDialog } from "./GetQuantityDialog";
import { ManufacturingJobScreen } from "../manufacturing/ManufacturingJobScreen";
import { SelectPickTargetLUScreen } from "./SelectPickTargetLUScreen";
import { expect } from "@playwright/test";

const NAME = "PickingJobLineScreen";
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickLineScreen');

export const PickingJobLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickManufactureButton: async () => await test.step(`${NAME} - Click Manufacture button`, async () => {
        await page.getByTestId(`PickFromManufacturingOrder-button`).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

    clickPickHUButton: async () => await test.step(`${NAME} - Click Pick HU button`, async () => {
        await page.getByTestId(`PickHU-button`).tap();
        await GetQuantityDialog.waitForDialog();
    }),

    /** "HU verpacken" / "Pack HU": opens the line's scan screen (the quantity block opens by itself only when a label is remembered). */
    tapPickHUButton: async () => await test.step(`${NAME} - Tap Pick HU button`, async () => {
        await page.getByTestId(`PickHU-button`).tap();
    }),

    clickScanButton: async () => await test.step(`${NAME} - Click Scan button`, async () => {
        await page.getByRole('button', { name: 'Scan' }).tap();
    }),

    /** "Schließen" / "Close": closes the line short. */
    clickCloseButton: async () => await test.step(`${NAME} - Click Close button`, async () => {
        await page.getByTestId('closeText-button').tap();
    }),

    /** "Wieder öffnen" / "Re-open". */
    clickReOpenButton: async () => await test.step(`${NAME} - Click Re-open button`, async () => {
        await page.getByTestId('reOpenText-button').tap();
    }),

    expectCloseButtonVisible: async () => await test.step(`${NAME} - Expect Close button`, async () => {
        await expect(page.getByTestId('closeText-button')).toBeVisible();
    }),

    expectReOpenButtonVisible: async () => await test.step(`${NAME} - Expect Re-open button`, async () => {
        await expect(page.getByTestId('reOpenText-button')).toBeVisible();
    }),

    clickStepButton: async ({ index }) => await test.step(`${NAME} - Click step ${index} button`, async () => {
        await page.locator(`#step-${index}-button`).tap();
        await PickingJobStepScreen.waitForScreen();
    }),

    clickLUTargetButton: async () => await test.step(`${NAME} - Click LU target button`, async () => {
        await page.getByTestId('targetLU-button').tap();
        await SelectPickTargetLUScreen.waitForScreen();
    }),

    clickTUTargetButton: async () => await test.step(`${NAME} - Click TU target button`, async () => {
        await page.getByTestId('targetTU-button').tap();
        // Use first() to avoid strict-mode errors — a previous SelectPickTargetScreen
        // (from LU target selection) may still be transitioning out of the DOM when the
        // new one (for TU) is already mounted.
        await page.locator('#SelectPickTargetScreen').first().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobLineScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobScreen.waitForScreen();
    }),

    /**
     * After the booking that finished a line: waits for the job screen, or, when the app shows the line screen instead,
     * goes back from it. AC8 says the job screen; the app as built stays on the line screen when the quantity block was
     * entered through "Pack HU" (the "Re-entry via Pack HU" spec pins the AC). Specs about something else than that
     * navigation use this to carry on from either screen.
     */
    leaveToJobScreenAfterLineDone: async () => await test.step(`${NAME} - Leave to the job screen after the line is done`, async () => {
        await page.locator('#WFProcessScreen, #PickLineScreen').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        if (await containerElement().isVisible()) {
            await PickingJobLineScreen.goBack();
        }
        await PickingJobScreen.waitForScreen();
    }),

    expectHeaderProperty:  async ({ caption, value }) => await test.step(`${NAME} - Check header property`, async () => {
        const row = await page.locator(
          `tr:has(th:has-text("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),
};