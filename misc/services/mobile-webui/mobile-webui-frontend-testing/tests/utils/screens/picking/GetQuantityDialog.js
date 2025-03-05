import { test } from "../../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { expect } from "@playwright/test";

const NAME = 'GetQuantityDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.get-qty-dialog');

export const GetQuantityDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    expectQtyEntered: async (expected) => await test.step(`${NAME} - Expect QtyEntered to be '${expected}'`, async () => {
        await expect(page.locator('#qty-input')).toHaveValue(expected);
    }),

    typeQtyEntered: async (qty) => await test.step(`${NAME} - Type QtyEntered '${qty}'`, async () => {
        await page.locator('#qty-input').type(qty);
    }),

    scanCatchWeightQRCode: async ({ qrCode, stepName }) => await test.step(`${NAME} - Scan ${stepName}: ${qrCode}`, async () => {
        const prevQtyTarget = await page.locator('[data-testid="qty-target"]').innerText();

        await page.getByTestId('qrCode-input').type(qrCode);
        await page.locator('[data-testid="qrCode-input"]').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        await page.waitForFunction((prevQtyTarget) => {
            const el = document.querySelector('[data-testid="qty-target"]');
            return el && el.textContent !== prevQtyTarget;
        }, prevQtyTarget);

        await page.waitForFunction(
            () => {
                const el = document.querySelector('[data-testid="qrCode-input"]');
                return el && el.value === '';
            });
    }),

    clickDone: async () => await test.step(`${NAME} - Press OK`, async () => {
        await page.getByTestId('done-button').tap();
    }),

    clickCancel: async () => await test.step(`${NAME} - Press Cancel`, async () => {
        await page.getByTestId('cancel-button').tap();
    }),

    fillAndPressDone: async ({ expectQtyEntered, qtyEntered, catchWeightQRCode }) => await test.step(`${NAME} - Fill dialog`, async () => {
        await GetQuantityDialog.waitForDialog();

        if (expectQtyEntered != null) {
            await GetQuantityDialog.expectQtyEntered(expectQtyEntered);
        }
        if (qtyEntered != null) {
            await GetQuantityDialog.typeQtyEntered(qtyEntered);
        }
        if (catchWeightQRCode != null) {
            const qrCodesArray = Array.isArray(catchWeightQRCode) ? catchWeightQRCode : [catchWeightQRCode];
            const length = qrCodesArray.length;
            for (let idx = 0; idx < length; idx++) {
                const qrCode = qrCodesArray[idx];
                await GetQuantityDialog.scanCatchWeightQRCode({ qrCode, stepName: `#${idx + 1}/${length}` });
            }
        }

        await GetQuantityDialog.clickDone();
    }),
};