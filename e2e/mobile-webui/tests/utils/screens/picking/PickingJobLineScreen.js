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

    /** "HU verpacken" / "Pack HU" */
    tapPickHUButton: async () => await test.step(`${NAME} - Tap Pick HU button`, async () => {
        await page.getByTestId(`PickHU-button`).tap();
    }),

    clickScanButton: async () => await test.step(`${NAME} - Click Scan button`, async () => {
        await page.getByRole('button', { name: 'Scan' }).tap();
    }),

    /** "Schließen" / "Close" */
    clickCloseButton: async () => await test.step(`${NAME} - Click Close button`, async () => {
        await page.getByTestId('closeText-button').tap();
    }),

    /** "Wieder öffnen" / "Re-open" */
    clickReOpenButton: async () => await test.step(`${NAME} - Click Re-open button`, async () => {
        await page.getByTestId('reOpenText-button').tap();
    }),

    expectCloseButtonVisible: async () => await test.step(`${NAME} - Expect Close button`, async () => {
        await expect(page.getByTestId('closeText-button')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectReOpenButtonVisible: async () => await test.step(`${NAME} - Expect Re-open button`, async () => {
        await expect(page.getByTestId('reOpenText-button')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** The button of a booked step; for layout checks (see utils/layout.js) */
    stepButton: ({ index }) => page.locator(`#step-${index}-button`),

    /** The Re-open button; for layout checks */
    reOpenButton: () => page.getByTestId('reOpenText-button'),

    clickStepButton: async ({ index }) => await test.step(`${NAME} - Click step ${index} button`, async () => {
        await PickingJobLineScreen.stepButton({ index }).tap();
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

    expectHeaderProperty:  async ({ caption, value }) => await test.step(`${NAME} - Check header property`, async () => {
        const row = await page.locator(
          `tr:has(th:has-text("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),
};