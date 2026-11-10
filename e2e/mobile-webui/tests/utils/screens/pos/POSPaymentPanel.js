import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { exactTextMatch } from "./posText";

const NAME = 'POSPaymentPanel';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-payment-panel');
// getReceiptPdf retries up to 60x1s (see OrderSummary.jsx / actions/orders.js) - allow for the full
// backend retry budget plus the async invoice/shipment creation it depends on.
const RECEIPT_PDF_TIMEOUT = 70000;
// The card checkout is sent to the reader synchronously; a SumUp that does not answer ends at the client's 60 s read timeout.
const CARD_CHECKOUT_TIMEOUT = 90_000;

const CARD_RESULT_UNKNOWN_TEXT = 'Ergebnis unbekannt — bitte Kartenterminal prüfen';
const CARD_RELEASE_BUTTON_NAME = 'Kartenzahlung prüfen / freigeben';
const CARD_RELEASE_CONFIRMATION_TEXT = 'Ich habe das Kartenterminal geprüft: Die Karte wurde nicht belastet';
const CARD_CONFIRM_BUTTON_NAME = 'Bestätigen';
const CANCEL_BUTTON_NAME = 'Abbrechen';
// the card modal's retry button (pos.payment.modal.card.actions.checkout)
const RETRY_BUTTON_NAME = 'Zur Kasse';

/** @returns {import('@playwright/test').Locator} */
const buttonElement = (name) => containerElement().getByRole('button', { name, exact: true });

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

    /**
     * Adds a card payment for the remaining open amount, then checks it out on the card reader ("Zur Kasse").
     * Returns once the checkout request has been answered (it may take the client's 60 s read timeout).
     */
    payCard: async () => await test.step(`${NAME} - Pay by card`, async () => {
        const paymentSynced = page.waitForResponse((response) =>
            response.request().method() === 'POST' && response.url().endsWith('/api/v2/pos/orders'));
        await page.locator('[data-testid="pos-payment-method-button"][data-payment-method="CARD"]').tap();
        await paymentSynced;

        const checkoutAnswered = page.waitForResponse((response) =>
            response.request().method() === 'POST' && response.url().endsWith('/api/v2/pos/orders/checkoutPayment'),
            { timeout: CARD_CHECKOUT_TIMEOUT });
        await buttonElement(RETRY_BUTTON_NAME).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await checkoutAnswered;
    }),

    /** The card payment shows that its result is unknown and that the cashier must check the card terminal. */
    expectCardPaymentUnknown: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Expect card payment result unknown`, async () => {
        await expect(containerElement().getByText(CARD_RESULT_UNKNOWN_TEXT)).toBeVisible({ timeout });
    }),

    /** An unknown card payment must be neither cancelled nor retried: no "Abbrechen", no retry ("Zur Kasse"). */
    expectNoCancelOrRetry: async () => await test.step(`${NAME} - Expect no cancel or retry`, async () => {
        await expect(buttonElement(CANCEL_BUTTON_NAME)).toHaveCount(0);
        await expect(buttonElement(RETRY_BUTTON_NAME)).toHaveCount(0);
    }),

    /** @param amount the open amount as rendered in the spec's login language, e.g. '0,00' for a de_DE login */
    expectOpenAmount: async (amount) => await test.step(`${NAME} - Expect open amount ${amount}`, async () => {
        await expect(containerElement().locator('.payment-summary-line.openAmt .payment-summary-value'))
            .toContainText(exactTextMatch(amount), { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts the sale was not completed: the order-completed summary is not shown. */
    expectOrderNotCompleted: async () => await test.step(`${NAME} - Expect order not completed`, async () => {
        await expect(page.getByTestId('pos-order-summary-modal')).toHaveCount(0);
    }),

    expectCardReleaseEnabled: async ({ isEnabled, timeout = SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect card release ${isEnabled ? 'enabled' : 'disabled'}`, async () => {
        const button = buttonElement(CARD_RELEASE_BUTTON_NAME);
        await expect(button).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        if (isEnabled) {
            await expect(button).toBeEnabled({ timeout });
        } else {
            await expect(button).toBeDisabled({ timeout });
        }
    }),

    /**
     * Taps **Kartenzahlung prüfen / freigeben**, waits for the "card was not charged" confirmation and,
     * with `confirm`, confirms it.
     */
    releaseCardPayment: async ({ confirm }) => await test.step(`${NAME} - Release card payment, confirm=${confirm}`, async () => {
        await buttonElement(CARD_RELEASE_BUTTON_NAME).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(containerElement().getByText(CARD_RELEASE_CONFIRMATION_TEXT)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        if (confirm) {
            await buttonElement(CARD_CONFIRM_BUTTON_NAME).tap({ timeout: SLOW_ACTION_TIMEOUT });
            await expect(containerElement().getByText(CARD_RELEASE_CONFIRMATION_TEXT)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        }
    }),

    /** After a release: the payment shows failed, and the cashier can cancel it ("Abbrechen") or retry ("Zur Kasse"). */
    expectCardPaymentFailedWithCancelAndRetry: async () => await test.step(`${NAME} - Expect card payment failed with cancel and retry`, async () => {
        await expect(containerElement().getByText(CARD_RESULT_UNKNOWN_TEXT)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        await expect(buttonElement(CANCEL_BUTTON_NAME)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(buttonElement(RETRY_BUTTON_NAME)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),
};
