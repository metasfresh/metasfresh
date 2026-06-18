import { test } from '../../../../playwright.config';
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { GetQuantityDialog } from '../picking/GetQuantityDialog';
import { DistributionUtils } from './DistributionUtils';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';
import { DistributionJobScreen } from './DistributionJobScreen';
import { expect } from '@playwright/test';

const NAME = 'DistributionLinePickFromScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionLinePickFromScreen');

export const DistributionLinePickFromScreen = {
        waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
            await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        }),

        expectJobId: async ({ distributionJobId }) => await test.step(`${NAME} - Expect jobId=${distributionJobId}`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();
            await DistributionUtils.expectJobId({ distributionJobId });
        }),

        // Assert a row of the (global) job header table that this screen renders after auto-advance.
        // The header is the shared `.view-header` table — same DOM as
        // DistributionJobScreen.expectHeaderProperty — so the same `tr/th/td` selector applies here.
        //
        // Caption is matched EXACTLY (`th:text-is`) so a substring caption (e.g. "Product" vs
        // "Product Value and Name") can't hit the wrong row. `exact` (default true) likewise matches
        // the value EXACTLY (`td:text-is`) so that e.g. locator "L1" is not satisfied by a stale
        // "L10" value — exactly the wrong/leftover-header class of bug this test guards. Pass
        // `exact: false` only when the rendered value legitimately carries trailing content you do
        // not want to pin (e.g. a qty followed by its UOM symbol).
        expectHeaderProperty: async ({ caption, value, exact = true }) => await test.step(`${NAME} - Check header property '${caption}'='${value}'${exact ? '' : ' (substring)'}`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();
            const valueSelector = exact ? `td:text-is("${value}")` : `td:has-text("${value}")`;
            const row = page.locator(`tr:has(th:text-is("${caption}")):has(${valueSelector})`);
            await expect(row).toHaveCount(1);
        }),

        scanHUToMove: async ({ huQRCode, productScannedCode, expectQuantityDialog = true, expectedQtyToMove, expectNextScreen }) => await test.step(`${NAME} - Scan HU to move`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();

            if (huQRCode) {
                await DistributionLinePickFromScreen.typeHUQRCode(huQRCode);
            }

            await DistributionLinePickFromScreen.typeProductCode(productScannedCode);

            if (expectQuantityDialog) {
                await DistributionLinePickFromScreen.fillQuantityDialog({
                    expectedQtyToMove,
                });
            }

            if (!expectNextScreen || expectNextScreen === 'DistributionJobScreen') {
                await DistributionJobScreen.waitForScreen();
            } else if (expectNextScreen === 'DistributionJobsListScreen') {
                await DistributionJobsListScreen.waitForScreen();
            } else if (expectNextScreen === 'DistributionLinePickFromScreen') {
                await DistributionLinePickFromScreen.waitForScreen();
            } else {
                throw new Error(`Invalid expectNextScreen: ${expectNextScreen}`);
            }
        }),

        typeHUQRCode: async (qrCode) => await test.step(`${NAME} - Type HU QR Code`, async () => {
            await BarcodeScannerComponent.type({ scannedCode: qrCode, testId: 'scanHUBarcode-input' });
        }),

        typeProductCode: async (productScannedCode) => await test.step(`${NAME} - Type Product Scanned Code: ${productScannedCode}`, async () => {
            await BarcodeScannerComponent.type({ scannedCode: productScannedCode, testId: 'scanProductCode-input' });
        }),

        goBackToJobScreen: async () => await test.step(`${NAME} - Go back to job screen`, async () => {
            await page.locator(ID_BACK_BUTTON).tap();
            await DistributionJobScreen.waitForScreen();
        }),

        fillQuantityDialog: async ({ qtyToMove, expectedQtyToMove, expectedError }) => await test.step(`${NAME} - Fill Quantity Dialog`, async () => {
            await GetQuantityDialog.fillAndPressDone({
                qtyEntered: qtyToMove,
                expectQtyEntered: expectedQtyToMove,
                expectedError,
            });
        }),
    }
;