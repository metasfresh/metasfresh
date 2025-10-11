import { test } from "../../../../playwright.config";
import { page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { expect } from "@playwright/test";

const NAME = 'GetQuantityDialog';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.get-qty-dialog');

export const QTY_NOT_FOUND_REASON_NOT_FOUND = 'N';
// noinspection JSUnusedGlobalSymbols
export const QTY_NOT_FOUND_REASON_DAMAGED = 'D';
export const QTY_NOT_FOUND_REASON_IGNORE = 'IgnoreReason';

export const GetQuantityDialog = {
    waitForDialog: async () => await test.step(`${NAME} - Wait for dialog`, async () => {
        await containerElement().waitFor();
    }),

    waitToClose: async () => await test.step(`${NAME} - Wait to close`, async () => {
        await containerElement().waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    expectQtyEntered: async (expected) => await test.step(`${NAME} - Expect QtyEntered to be '${expected}'`, async () => {
        await expect(page.locator('#qty-input')).toHaveValue(`${expected}`);
    }),

    typeQtyEntered: async (qty) => await test.step(`${NAME} - Type QtyEntered '${qty}'`, async () => {
        await page.locator('#qty-input').type(`${qty}`);
    }),

    typeCatchWeight: async (qty) => await test.step(`${NAME} - Type CatchWeight '${qty}'`, async () => {
        // Replace `.` with locale-appropriate decimal, e.g., `,` for some regions
        const correctedQty = `${qty}`.replace('.', (1.1).toLocaleString().substring(1, 2));

        const field = page.locator('#catch-weight');
        await clickAndType(field, correctedQty);
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

    clickQtyNotFoundReason: async ({ reason }) => await test.step(`${NAME} - Click qty not found reason '${reason}'`, async () => {
        await page.getByTestId(`qty-reason-radio-${reason}`).tap();
    }),

    expectQtyNotFoundReason: async ({ reason }) => await test.step(`${NAME} - Expect qty not found reason '${reason}'`, async () => {
        const radioButton = page.getByTestId(`qty-reason-radio-${reason}`);
        await expect(radioButton).toBeChecked();
    }),

    clickDone: async () => await test.step(`${NAME} - Press OK`, async () => {
        await page.getByTestId('done-button').tap();
        await GetQuantityDialog.expectComponentsDisabled();
        await GetQuantityDialog.waitToClose();
    }),

    clickCancel: async () => await test.step(`${NAME} - Press Cancel`, async () => {
        await page.getByTestId('cancel-button').tap();
        await GetQuantityDialog.expectComponentsDisabled();
        await GetQuantityDialog.waitToClose();
    }),

    clickManual: async () => await test.step(`${NAME} - Press Manual`, async () => {
        await page.getByTestId('switchToManualInput-button').tap();
        await page.locator('#qty-input').waitFor(); // atm that's the only indicator that we switched to manual input
    }),

    expectComponentsDisabled: async () => await test.step(`${NAME} - Expect fields and buttons disabled`, async () => {
        await expectMissingOrDisabled(page.locator('#qty-input'));
        // await expectMissingOrDisabled(page.getByTestId('bestBeforeDate'));
        // await expectMissingOrDisabled(page.getByTestId('lotNo'));
        await expectMissingOrDisabled(page.getByTestId('done-button'));
        await expectMissingOrDisabled(page.getByTestId('cancel-button'));
        await expectMissingOrDisabled(page.getByTestId('confirmDoneAndCloseTarget-button'));
    }),

    fillAndPressDone: async ({ switchToManualInput, expectQtyEntered, qtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason }) => await test.step(`${NAME} - Fill dialog`, async () => {
        await GetQuantityDialog.waitForDialog();

        // run this first!
        if (switchToManualInput) {
            await GetQuantityDialog.clickManual();
        }

        if (expectQtyEntered != null) {
            await GetQuantityDialog.expectQtyEntered(expectQtyEntered);
        }
        if (qtyEntered != null) {
            await GetQuantityDialog.typeQtyEntered(qtyEntered);
        }
        if (catchWeight != null) {
            await GetQuantityDialog.typeCatchWeight(catchWeight);
        }
        if (catchWeightQRCode != null) {
            const qrCodesArray = Array.isArray(catchWeightQRCode) ? catchWeightQRCode : [catchWeightQRCode];
            const length = qrCodesArray.length;
            for (let idx = 0; idx < length; idx++) {
                const qrCode = qrCodesArray[idx];
                await GetQuantityDialog.scanCatchWeightQRCode({ qrCode, stepName: `#${idx + 1}/${length}` });
            }
        }
        if (expectQtyNotFoundReason != null) {
            await GetQuantityDialog.expectQtyNotFoundReason({ reason: expectQtyNotFoundReason });
        }
        if (qtyNotFoundReason != null) {
            await GetQuantityDialog.clickQtyNotFoundReason({ reason: qtyNotFoundReason });
        }

        await GetQuantityDialog.clickDone();
    }),
};

//
//
//
//
//

const expectMissingOrDisabled = async (locator) => {
    if (await locator.count() > 0) {
        await expect(locator).toBeDisabled();
    }
};

const clickAndType = async (field, value) => {
    // Tap the field before, to gain focus so our code will react and select all text
    await field.tap();
    // ... so when typing, we will actually override the current value
    await field.type(value);

    // cannot check in case of qtys, because we set "0,789" but we get "0.789"
    // const enteredValue = await field.inputValue();
    // if (enteredValue !== value) {
    //     throw new Error(`Expected value '${value}', but got '${enteredValue}'`);
    // }
}