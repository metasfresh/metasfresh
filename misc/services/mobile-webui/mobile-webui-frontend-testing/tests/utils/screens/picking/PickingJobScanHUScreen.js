import { test } from "../../../../playwright.config";
import { page } from "../../common";
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickingJobScanHUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickProductsScanScreen');

export const PickingJobScanHUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),
};