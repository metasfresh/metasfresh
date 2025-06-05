import { page, step } from '../../common';
import { test } from '../../../../playwright.config';
import { GetQuantityDialog } from './GetQuantityDialog';
import { PickingJobScreen } from './PickingJobScreen';

const NAME = 'PickLineScanScreen';

const containerElement = () => page.locator('#PickLineScanScreen');

export const PickLineScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        console.log('Scanning HU QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),

    pickHU: async ({ qrCode, qtyEntered, expectQtyEntered, catchWeightQRCode, qtyNotFoundReason }) => await step(`${NAME} - Scan HU and Pick`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(qrCode);
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, qtyEntered, catchWeightQRCode, qtyNotFoundReason });
        await PickingJobScreen.waitForScreen();
    }),

};