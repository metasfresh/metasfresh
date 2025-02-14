import {test} from "../../../../playwright.config";
import {page} from "../../common";

export const PickingJobScanHUScreen = {
    waitForScreen: async () => await test.step(`Wait for Picking Job Scan HU Screen`, async () => {
        await page.locator('#PickProductsScanScreen').waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`Type QR Code`, async () => {
        console.log('Scanning HU QR code:\n' + qrCode);
        await page.type('#input-text', qrCode);
    }),
};