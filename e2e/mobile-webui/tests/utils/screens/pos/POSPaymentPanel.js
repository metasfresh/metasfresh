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

    /**
     * Adds a cash payment for the remaining open amount and completes it by entering the tendered
     * amount. Only the CASH payment method is provisioned by the masterdata API
     * (`CreatePOSTerminalCommand.assertOnlyCashPaymentMethod`), so there is exactly one payment-method
     * button on screen.
     */
    payCash: async ({ tendered }) => await test.step(`${NAME} - Pay cash, tendered ${tendered}`, async () => {
        // PaymentMethodButton.jsx (not touched by this task) has no data-testid; `.payment-method` is
        // unambiguous since only CASH is ever provisioned.
        await page.locator('.payment-method').first().tap();

        const modal = page.getByTestId('pos-cash-payment-modal');
        await modal.waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        // The tendered amount is entered via NumericKeyboard, which forwards real keyboard digits to
        // its onKey handler (useKeyDown listens on `window`) rather than exposing a value we could
        // fill() - dispatch actual key presses instead of tapping the on-screen numpad buttons.
        await page.keyboard.type(`${tendered}`);
        await page.getByTestId('pos-cash-payment-ok-button').tap();
    }),

    expectOrderCompleted: async () => await test.step(`${NAME} - Expect order completed`, async () => {
        // OrderSummary.jsx (not touched by this task) already carries this class - CSS fallback.
        await expect(page.locator('.order-summary-modal')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),
};
