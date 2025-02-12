import {test} from "../../../../playwright.config";
import {page} from "../../common";

export const PickingJobScanPickingSlotScreen = {
    waitForScreen: async () => await test.step(`Wait for Picking Job screen`, async () => {
        await page.locator('#ScanScreen').waitFor();
    }),

    typeQRCode: async (qrCode) => await test.step(`Type QR Code`, async () => {
        await page.type('#input-text', qrCode);
    }),
};