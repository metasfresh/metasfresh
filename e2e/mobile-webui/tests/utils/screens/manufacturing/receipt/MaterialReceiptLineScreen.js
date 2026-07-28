import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
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
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
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

    expectNewTUTargetNotPresent: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Expect New TU target "${tuPIItemProductTestId}" not present`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();
        await ReceiptNewHUScreen.expectTUTargetNotPresent({ tuPIItemProductTestId });
    }),

    selectExistingHUTarget: async ({ huQRCode }) => await test.step(`${NAME} - Select existing HU target`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickExistingHUButton();
        await ManufacturingReceiptScanScreen.typeQRCode(huQRCode);
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    receiveQty: async ({ switchToManualInput, qtyEntered, expectQtyEntered, lotNo, bestBeforeDate, expectLotNoVisible, expectBestBeforeDateVisible, catchWeight, catchWeightQRCode, expectGoBackToJob = true }) => await test.step(`${NAME} - Receive qty ${qtyEntered ? qtyEntered : ''}`, async () => {
        await page.getByTestId('receive-qty-button').tap();

        await GetQuantityDialog.waitForDialog();
        if (expectLotNoVisible != null) {
            if (expectLotNoVisible) {
                await GetQuantityDialog.expectLotNoVisible();
            } else {
                await GetQuantityDialog.expectLotNoNotVisible();
            }
        }
        if (expectBestBeforeDateVisible != null) {
            if (expectBestBeforeDateVisible) {
                await GetQuantityDialog.expectBestBeforeDateVisible();
            } else {
                await GetQuantityDialog.expectBestBeforeDateNotVisible();
            }
        }

        await GetQuantityDialog.fillAndPressDone({ switchToManualInput, expectQtyEntered, qtyEntered, lotNo, bestBeforeDate, catchWeight, catchWeightQRCode });
        // await MaterialReceiptLineScreen.waitForScreen(); // while processing

        // final screen
        if (expectGoBackToJob) {
            await ManufacturingJobScreen.waitForScreen();
        } else {
            await MaterialReceiptLineScreen.waitForScreen();
        }
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

    expectNoGebindeHintVisible: async () => await test.step(`${NAME} - Expect no-Gebinde hint near disabled Produzieren`, async () => {
        await expect(page.getByTestId('receive-no-gebinde-hint')).toBeVisible();
    }),

    expectHeaderProperty:  async ({ caption, value }) => await test.step(`${NAME} - Check header property "${caption}" = "${value}"`, async () => {
        const row = await page.locator(
            `tr:has(th:text-is("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),
};