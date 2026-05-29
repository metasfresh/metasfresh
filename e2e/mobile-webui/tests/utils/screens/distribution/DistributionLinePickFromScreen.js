import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { GetQuantityDialog } from '../picking/GetQuantityDialog';
import { DistributionUtils } from './DistributionUtils';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';
import { DistributionJobScreen } from './DistributionJobScreen';

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

        fillQuantityDialog: async ({ qtyToMove, expectedQtyToMove, expectedError }) => await test.step(`${NAME} - Fill Quantity Dialog`, async () => {
            await GetQuantityDialog.fillAndPressDone({
                qtyEntered: qtyToMove,
                expectQtyEntered: expectedQtyToMove,
                expectedError,
            });
        }),
    }
;