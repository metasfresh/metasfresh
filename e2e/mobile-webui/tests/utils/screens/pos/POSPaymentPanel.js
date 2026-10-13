import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";

const NAME = 'POSPaymentPanel';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-payment-panel');
// getReceiptPdf retries up to 60x1s (see OrderSummary.jsx / actions/orders.js) - allow for the full
// backend retry budget plus the async invoice/shipment creation it depends on.
const RECEIPT_PDF_TIMEOUT = 70000;

export const POSPaymentPanel = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    /** Adds a cash payment for the remaining open amount and completes it by entering the tendered amount. */
    payCash: async ({ tendered }) => await test.step(`${NAME} - Pay cash, tendered ${tendered}`, async () => {
        // Adding the payment is applied locally first and then synced to the backend (POST /pos/orders);
        // wait for that sync to land before entering the amount, so the input is not raced by it.
        const paymentSynced = page.waitForResponse((response) =>
            response.request().method() === 'POST' && response.url().endsWith('/api/v2/pos/orders'));
        await page.locator('[data-testid="pos-payment-method-button"][data-payment-method="CASH"]').tap();

        const modal = page.getByTestId('pos-cash-payment-modal');
        await modal.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await paymentSynced;

        // Enter the tendered amount on the on-screen numpad, as a cashier does on a touch till. (Physical
        // key presses are only picked up once NumericKeyboard's window keydown listener is attached in
        // an effect after the modal first paints, so typing right after the modal appears can be lost.)
        for (const digit of `${tendered}`) {
            await modal.getByRole('button', { name: digit, exact: true }).tap();
        }
        await page.getByTestId('pos-cash-payment-ok-button').tap();
    }),

    expectOrderCompleted: async () => await test.step(`${NAME} - Expect order completed`, async () => {
        await expect(page.getByTestId('pos-order-summary-modal')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Closes the order-completed summary panel (the "Schließen" button - the panel has no
     * `data-testid` button, so it's matched by its de_DE caption, matching the spec's de_DE login).
     * Transitions the order to Closed, so the header's "close cash journal" button is reachable again.
     */
    closeOrderSummary: async () => await test.step(`${NAME} - Close order summary`, async () => {
        const modal = page.getByTestId('pos-order-summary-modal');
        // The panel fetches the receipt PDF in the background (retried up to 60x1s) and shows a
        // full-screen spinner (the shared `.loading` overlay) until it settles - the operator can't
        // see/tap Close until then either, so wait for it exactly like every other screen's spinner.
        await page.locator('.loading').waitFor({ state: 'detached', timeout: RECEIPT_PDF_TIMEOUT });
        await modal.getByRole('button', { name: 'Schließen' }).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};
