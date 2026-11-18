import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { test } from '../../../../playwright.config';
import { GetQuantityDialog } from './GetQuantityDialog';
import { PickingJobScreen } from './PickingJobScreen';
import { PickingJobLineScreen } from './PickingJobLineScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'PickLineScanScreen';

const containerElement = () => page.locator('#PickLineScanScreen');

export const PickLineScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

    pickHU: async ({ qrCode, qtyEntered, expectQtyEntered, catchWeightQRCode, qtyNotFoundReason, expectGoBackToPickingJob = true } = {}) => await step(`${NAME} - Scan HU and Pick`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(qrCode);

        if (qtyEntered != null || expectQtyEntered != null || catchWeightQRCode != null || qtyNotFoundReason != null) {
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, qtyEntered, catchWeightQRCode, qtyNotFoundReason });
        }

        if (expectGoBackToPickingJob) {
            await PickingJobScreen.waitForScreen();
        } else {
            await PickLineScanScreen.waitForScreen();
        }
    }),

    /** CU quantity entry mode of the line, read from the S5 root attribute `data-cu-qty-entry-mode`. */
    cuQtyEntryMode: async () => await step(`${NAME} - Read CU quantity entry mode`, async () => {
        await PickLineScanScreen.waitForScreen();
        return await containerElement().getAttribute('data-cu-qty-entry-mode');
    }),

    /** Scans one raw CU label on S5 (the label step of the new modes). */
    scanCULabel: async (label) => await step(`${NAME} - Scan CU label`, async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(label);
    }),

    /** Taps "Weitere Kiste" / "Next box": opens the next TU (box) of the line. */
    nextBox: async () => await step(`${NAME} - Next box`, async () => {
        await PickLineScanScreen.waitForScreen();
        await page.getByRole('button', { name: /Weitere Kiste|Next box/ }).tap();
    }),

    /** Opens box 1 before the first booking of a new-mode line (same tap as nextBox, named for the step). */
    openBox: async () => await step(`${NAME} - Open box`, async () => {
        await PickLineScanScreen.waitForScreen();
        await page.getByRole('button', { name: /Weitere Kiste|Next box/ }).tap();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickLineScanScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobLineScreen.waitForScreen();
    }),

};