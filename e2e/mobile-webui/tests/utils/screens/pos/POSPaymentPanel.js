import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";

const NAME = 'POSPaymentPanel';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-payment-panel');

export const POSPaymentPanel = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    /** Adds a cash payment for the remaining open amount and completes it by entering the tendered amount. */
    payCash: async ({ tendered }) => await test.step(`${NAME} - Pay cash, tendered ${tendered}`, async () => {
        await page.locator('[data-testid="pos-payment-method-button"][data-payment-method="CASH"]').tap();

        const modal = page.getByTestId('pos-cash-payment-modal');
        await modal.waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        // The tendered amount is entered via NumericKeyboard, which forwards real keyboard digits to
        // its onKey handler (useKeyDown listens on `window`) rather than exposing a value we could
        // fill() - dispatch actual key presses instead of tapping the on-screen numpad buttons.
        await page.keyboard.type(`${tendered}`);
        await page.getByTestId('pos-cash-payment-ok-button').tap();
    }),

    expectOrderCompleted: async () => await test.step(`${NAME} - Expect order completed`, async () => {
        await expect(page.getByTestId('pos-order-summary-modal')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),
};
