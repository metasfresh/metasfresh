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

// The card modal's elements, found by their data-testid; each one's text as the spec's de_DE login shows it is asserted too.
const CARD_RESULT_UNKNOWN_TEXT = 'Ergebnis unbekannt — bitte Kartenterminal prüfen';
const CARD_RELEASE_CONFIRMATION_TEXT = 'Ich habe das Kartenterminal geprüft: Die Karte wurde nicht belastet';
const CARD_RESULT_UNKNOWN_TESTID = 'pos-card-payment-unknown-message';
const CARD_RELEASE_CONFIRMATION_TESTID = 'pos-card-payment-release-confirmation-text';
const CARD_RELEASE_BUTTON = { testId: 'pos-card-payment-release-button', text: 'Kartenzahlung prüfen / freigeben' };
const CARD_CONFIRM_BUTTON = { testId: 'pos-card-payment-release-confirm-button', text: 'Bestätigen' };
const CARD_CANCEL_BUTTON = { testId: 'pos-card-payment-cancel-button', text: 'Abbrechen' };
// the card modal's retry button (pos.payment.modal.card.actions.checkout)
const CARD_RETRY_BUTTON = { testId: 'pos-card-payment-checkout-button', text: 'Zur Kasse' };

/** @returns {import('@playwright/test').Locator} */
const buttonElement = ({ testId }) => containerElement().getByTestId(testId);

/** Asserts the button is shown with its exact label. */
const expectButtonShown = async (button) => {
    await expect(buttonElement(button)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(buttonElement(button)).toHaveText(button.text);
};

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
        await expectButtonShown(CARD_RETRY_BUTTON);
        await buttonElement(CARD_RETRY_BUTTON).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await checkoutAnswered;
    }),

    /** The card payment shows that its result is unknown and that the cashier must check the card terminal. */
    expectCardPaymentUnknown: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Expect card payment result unknown`, async () => {
        const message = containerElement().getByTestId(CARD_RESULT_UNKNOWN_TESTID);
        await expect(message).toBeVisible({ timeout });
        await expect(message).toHaveText(CARD_RESULT_UNKNOWN_TEXT);
    }),

    /** An unknown card payment must be neither cancelled nor retried: no "Abbrechen", no retry ("Zur Kasse"). */
    expectNoCancelOrRetry: async () => await test.step(`${NAME} - Expect no cancel or retry`, async () => {
        await expect(buttonElement(CARD_CANCEL_BUTTON)).toHaveCount(0);
        await expect(buttonElement(CARD_RETRY_BUTTON)).toHaveCount(0);
    }),

    /** @param amount the open amount as rendered in the spec's login language, e.g. '0,00' for a de_DE login */
    expectOpenAmount: async (amount) => await test.step(`${NAME} - Expect open amount ${amount}`, async () => {
        await expect(containerElement().locator('.payment-summary-line.openAmt .payment-summary-value'))
            .toContainText(exactTextMatch(amount), { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the sale was not completed: the payment panel is still shown (a positive signal) and the order-completed
     * summary is not. The caller adds the backend side (the payment is unresolved) so this is not a vacuous absence check.
     */
    expectOrderNotCompleted: async () => await test.step(`${NAME} - Expect order not completed`, async () => {
        await expect(containerElement()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.getByTestId('pos-order-summary-modal')).toHaveCount(0);
    }),

    expectCardReleaseEnabled: async ({ isEnabled, timeout = SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect card release ${isEnabled ? 'enabled' : 'disabled'}`, async () => {
        await expectButtonShown(CARD_RELEASE_BUTTON);
        const button = buttonElement(CARD_RELEASE_BUTTON);
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
        await expectButtonShown(CARD_RELEASE_BUTTON);
        await buttonElement(CARD_RELEASE_BUTTON).tap({ timeout: SLOW_ACTION_TIMEOUT });
        const confirmation = containerElement().getByTestId(CARD_RELEASE_CONFIRMATION_TESTID);
        await expect(confirmation).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(confirmation).toHaveText(CARD_RELEASE_CONFIRMATION_TEXT);
        if (confirm) {
            await expectButtonShown(CARD_CONFIRM_BUTTON);
            await buttonElement(CARD_CONFIRM_BUTTON).tap({ timeout: SLOW_ACTION_TIMEOUT });
            await expect(confirmation).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        }
    }),

    /** After a release: the payment shows failed, and the cashier can cancel it ("Abbrechen") or retry ("Zur Kasse"). */
    expectCardPaymentFailedWithCancelAndRetry: async () => await test.step(`${NAME} - Expect card payment failed with cancel and retry`, async () => {
        await expect(containerElement().getByTestId(CARD_RESULT_UNKNOWN_TESTID)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        await expectButtonShown(CARD_CANCEL_BUTTON);
        await expectButtonShown(CARD_RETRY_BUTTON);
    }),
};
