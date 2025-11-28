import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { GetQuantityDialog } from '../picking/GetQuantityDialog';

const NAME = 'DistributionLinePickFromScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionLinePickFromScreen');

export const DistributionLinePickFromScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
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
};