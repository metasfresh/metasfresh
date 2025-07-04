import { test } from "../../../../playwright.config";
import { page } from "../../common";
import { expect } from '@playwright/test';
import { cssEscape } from '../../css';

const NAME = 'PickingSlotScanScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ScanScreen');

export const PickingSlotScanScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    waitForInputFieldToGetEmpty: async () => await test.step(`${NAME} - Wait for input field to get empty`, async () => {
        await expect(page.locator('#input-text')).toHaveValue('');
    }),

    typeQRCode: async (qrCode) => await test.step(`${NAME} - Type QR Code`, async () => {
        await page.type('#input-text', qrCode);
    }),

    expectPickingSlotButtons: async (expectationsArray) => await test.step(`${NAME} - Expect ${expectationsArray.length} Picking Slot buttons`, async () => {
        //
        // First, wait until all expected buttons are attached 
        for (const expectation of expectationsArray) {
            await locatePickingSlotButtons(expectation).waitFor({ state: 'attached' });
        }

        //
        // Check it again to make sure all expected buttons are still there and there is one of each 
        for (const expectation of expectationsArray) {
            await locatePickingSlotButtons(expectation).waitFor({ state: 'attached' });
            await expect(locatePickingSlotButtons(expectation)).toHaveCount(1);
        }

        //
        // Make sure we have the expected number of buttons
        // NOTE: we do this at the end because expect does not wait for the elements to stabilise
        await expect(locatePickingSlotButtons()).toHaveCount(expectationsArray.length);
    }),
};

const locatePickingSlotButtons = ({ index, qrCode, bpartnerLocationId, countHUs } = {}) => {
    let selector = '.pickingSlot-button';
    if (qrCode != null) {
        selector += `[data-testid="${cssEscape(qrCode)}"]`;
    }
    if (bpartnerLocationId != null) {
        selector += `[data-bpartnerlocationid="${bpartnerLocationId}"]`;
    }
    if (countHUs != null) {
        selector += `[data-detail-value1="${countHUs}"]`;
    }

    let locator = page.locator(selector);

    if (index != null) {
        locator = locator.nth(index - 1);
    }

    return locator;
};
