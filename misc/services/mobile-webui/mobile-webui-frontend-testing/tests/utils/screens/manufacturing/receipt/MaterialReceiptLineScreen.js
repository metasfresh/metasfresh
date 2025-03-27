import { ID_BACK_BUTTON, page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { ReceiptReceiveTargetScreen } from './ReceiptReceiveTargetScreen';
import { GetQuantityDialog } from '../../picking/GetQuantityDialog';
import { ReceiptNewHUScreen } from './ReceiptNewHUScreen';
import { ManufacturingJobScreen } from '../ManufacturingJobScreen';
import { ManufacturingReceiptScanScreen } from './ManufacturingReceiptScanScreen';

const NAME = 'MaterialReceiptLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#MaterialReceiptLineScreen');

export const MaterialReceiptLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached' });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickReceiveTargetButton: async () => await test.step(`${NAME} - Click receive target button`, async () => {
        await page.getByTestId('receive-target-button').tap();
        await ReceiptReceiveTargetScreen.waitForScreen();
    }),

    selectNewLUTarget: async ({ luPIItemTestId }) => await test.step(`${NAME} - Select New LU target "${luPIItemTestId}"`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();
        await ReceiptNewHUScreen.clickLUTarget({ luPIItemTestId });
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    selectNewTUTarget: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Select New TU target "${tuPIItemProductTestId}"`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();
        await ReceiptNewHUScreen.clickTUTarget({ tuPIItemProductTestId });
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    selectExistingHUTarget: async ({ huQRCode }) => await test.step(`${NAME} - Select existing HU target`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickExistingHUButton();
        await ManufacturingReceiptScanScreen.typeQRCode(huQRCode);
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    receiveQty: async ({ qtyEntered, expectQtyEntered }) => await test.step(`${NAME} - Receive qty ${qtyEntered ? qtyEntered : ''}`, async () => {
        await page.getByTestId('receive-qty-button').tap();

        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, qtyEntered });
        // await MaterialReceiptLineScreen.waitForScreen(); // while processing
        await ManufacturingJobScreen.waitForScreen(); // final screen
    }),

    receiveQtyWithQRCode: async ({ catchWeightQRCode }) => await test.step(`${NAME} - Receive via QrCode`, async () => {
        await page.getByTestId('receive-qty-button').tap();

        await GetQuantityDialog.fillAndPressDone({ catchWeightQRCode });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await MaterialReceiptLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),
};