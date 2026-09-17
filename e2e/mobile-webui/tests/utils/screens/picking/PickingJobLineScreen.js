import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
import { PickingJobScreen } from "./PickingJobScreen";
import { PickingJobStepScreen } from "./PickingJobStepScreen";
import { GetQuantityDialog } from "./GetQuantityDialog";
import { ManufacturingJobScreen } from "../manufacturing/ManufacturingJobScreen";
import { SelectPickTargetLUScreen } from "./SelectPickTargetLUScreen";
import { SelectPickTargetTUScreen } from "./SelectPickTargetTUScreen";
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

    clickScanButton: async () => await test.step(`${NAME} - Click Scan button`, async () => {
        await page.getByRole('button', { name: 'Scan' }).tap();
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

    // Line-level TU pick target (PRODUCT aggregation): the target is a property of the line,
    // so the buttons live inside the line view (SelectCurrentLUTUButtons is rendered with a lineId).
    setTargetTU: async ({ tu }) => await test.step(`${NAME} - Set line target TU to ${tu}`, async () => {
        await PickingJobLineScreen.clickTUTargetButton();
        await SelectPickTargetTUScreen.clickTUButton({ tu });
        await PickingJobLineScreen.waitForScreen();
    }),

    // Line-level carrier advise (PRODUCT aggregation): the advise button + the resolved carrier
    // product caption are rendered inside the line view by SelectCurrentLUTUButtons (lineId set).
    expectCarrierProductCaption: async ({ caption }) => await test.step(`${NAME} - Expect carrier product caption contains '${caption}'`, async () => {
        const detail = page.getByTestId('carrier-product-caption');
        await detail.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(detail).toContainText(caption);
    }),

    expectAdviseCarrierButtonVisible: async () => await test.step(`${NAME} - Expect advise carrier button visible`, async () => {
        await page.getByTestId('advise-carrier-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickAdviseCarrier: async () => await test.step(`${NAME} - Click advise carrier button`, async () => {
        const button = page.getByTestId('advise-carrier-button');
        await button.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toBeEnabled();
        await button.tap();
        await PickingJobLineScreen.waitForScreen();
    }),

    clickScanQRCodeButton: async () => await test.step(`${NAME} - Click Scan QR Code button`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
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