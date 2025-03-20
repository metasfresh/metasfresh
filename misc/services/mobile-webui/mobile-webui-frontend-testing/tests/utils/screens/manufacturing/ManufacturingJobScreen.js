import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { RawMaterialIssueLineScreen } from './issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from './receipt/MaterialReceiptLineScreen';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { ManufacturingJobsListScreen } from './ManufacturingJobsListScreen';
import { PickingJobLineScreen } from '../picking/PickingJobLineScreen';
import { GenerateHUQRCodesScreen } from './generateHUQRCodes/GenerateHUQRCodesScreen';

const NAME = 'ManufacturingJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const ManufacturingJobScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    goBackToPickingJobLine: async () => await test.step(`${NAME} - Go back to picking job line`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

    generateSingleHUQRCode: async ({ piTestId, expectNumberOfHUs, expectNumberOfCopies }) => await test.step(`${NAME} - Generate HU QR codes`, async () => {
        await page.getByTestId(`generateHUQRCodes-button`).tap();
        await GenerateHUQRCodesScreen.waitForScreen();
        const generatedQRCodesResult = await GenerateHUQRCodesScreen.print({ piTestId, expectNumberOfHUs: 1, expectNumberOfCopies: 1 });
        expect(generatedQRCodesResult?.qrCodes).toHaveLength(1);
        const generatedQRCode = generatedQRCodesResult.qrCodes[0].code;
        console.log('Generated QR codes:\n' + generatedQRCode);
        return generatedQRCode;
    }),

    issueRawProduct: async ({ index, qrCode, expectQtyEntered }) => await test.step(`${NAME} - Issue line ${index}`, async () => {
        await ManufacturingJobScreen.clickIssueButton({ index });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode, expectQtyEntered });
        await RawMaterialIssueLineScreen.goBack();

    }),

    clickIssueButton: async ({ index }) => await test.step(`${NAME} - Click issue button ${index}`, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.getByTestId(`issue-${index}-button`).tap();
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    clickReceiveButton: async ({ index }) => await test.step(`${NAME} - Click receive button ${index}`, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.getByTestId(`receipt-${index}-button`).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    complete: async () => await test.step(`${NAME} - Complete`, async () => {
        await ManufacturingJobScreen.expectVisible();
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await ManufacturingJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};

