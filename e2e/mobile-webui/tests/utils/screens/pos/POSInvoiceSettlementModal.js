import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { exactTextMatch } from "./posText";

const NAME = 'POSInvoiceSettlementModal';
/** @returns {import('@playwright/test').Locator} */
const modalElement = () => page.getByTestId('pos-invoice-settlement-modal');
/** @returns {import('@playwright/test').Locator} */
const paymentModalElement = () => page.getByTestId('pos-cash-payment-modal');
const rowElement = (index) => modalElement().getByTestId('pos-invoice-settlement-row').nth(index);

export const POSInvoiceSettlementModal = {
    /** Opens the settlement dialog from the header's "Rechnung bezahlen" button. */
    open: async () => await test.step(`${NAME} - Open`, async () => {
        await page.getByTestId('pos-invoice-settlement-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Keys `documentNo` on the on-screen numeric keypad and searches for it. Captures the
     * `GET /pos/invoices` response so the spec can assert against the SERVER's own open amounts
     * rather than re-deriving them.
     * @returns {Promise<Array>} the matching invoices, as returned by the backend.
     */
    search: async ({ documentNo }) => await test.step(`${NAME} - Search ${documentNo}`, async () => {
        const responsePromise = page.waitForResponse(
            (response) => response.request().method() === 'GET' && response.url().includes('/pos/invoices'),
            { timeout: SLOW_ACTION_TIMEOUT }
        );

        for (const digit of `${documentNo}`) {
            await modalElement().getByRole('button', { name: digit, exact: true }).tap();
        }
        await modalElement().getByTestId('pos-invoice-settlement-search-button').tap({ timeout: SLOW_ACTION_TIMEOUT });

        const response = await responsePromise;
        const body = await response.json();
        return body.list ?? [];
    }),

    /**
     * Asserts the result row at `index` (0-based). `openAmt` is the rendered display string, in the
     * spec's login language, e.g. `'107,00 €'` for a `de_DE` login.
     */
    expectResult: async ({ index = 0, documentNo, bpartnerName, openAmt }) => await test.step(`${NAME} - Expect result #${index}`, async () => {
        const row = rowElement(index);
        await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        if (documentNo != null) {
            await expect(row.getByTestId('pos-invoice-settlement-row-documentNo')).toHaveText(documentNo);
        }
        if (bpartnerName != null) {
            await expect(row.getByTestId('pos-invoice-settlement-row-bpartnerName')).toHaveText(bpartnerName);
        }
        if (openAmt != null) {
            await expect(row.getByTestId('pos-invoice-settlement-row-openAmt')).toContainText(exactTextMatch(openAmt));
        }
    }),

    /** Selects the result row at `index` (0-based) and waits for the cash-tendered keypad. */
    selectResult: async ({ index = 0 } = {}) => await test.step(`${NAME} - Select result #${index}`, async () => {
        await rowElement(index).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await paymentModalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Keys `tendered` on the cash-tendered keypad (reused from the sale payment panel's own
     * `CashPaymentDetailsModal`). Does NOT confirm - `CashPaymentDetailsModal` only shows the live
     * change-back amount while this keypad is up (`expectChangeBack` reads it), so a caller that needs
     * to assert it must decompose ({@link module:POSInvoiceSettlementModal.confirmPayment} is the
     * separate confirm step) instead of using an all-in-one method that already navigates past it.
     */
    enterTenderedAmount: async ({ tendered }) => await test.step(`${NAME} - Enter tendered amount ${tendered}`, async () => {
        for (const digit of `${tendered}`) {
            await paymentModalElement().getByRole('button', { name: digit, exact: true }).tap();
        }
    }),

    /** Taps the cash-tendered keypad's OK button and waits for the settlement's success message. */
    confirmPayment: async () => await test.step(`${NAME} - Confirm payment`, async () => {
        await paymentModalElement().getByTestId('pos-cash-payment-ok-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().getByTestId('pos-invoice-settlement-success').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the change-back amount shown on the cash-tendered keypad (the modal's 3rd detail line,
     * after "Betrag" and "Bar erhalten" - `CashPaymentDetailsModal` carries no per-line testid of its
     * own). `changeBack` is the rendered display string, e.g. `'13,00 €'` for a `de_DE` login. MUST be
     * called after {@link module:POSInvoiceSettlementModal.enterTenderedAmount} and before
     * {@link module:POSInvoiceSettlementModal.confirmPayment} - confirming replaces this keypad with the
     * success view, so the detail line is gone by then.
     */
    expectChangeBack: async ({ changeBack }) => await test.step(`${NAME} - Expect change back ${changeBack}`, async () => {
        const changeBackValue = paymentModalElement().locator('.detail-line').nth(2).locator('.detail-value');
        await expect(changeBackValue).toContainText(exactTextMatch(changeBack));
    }),

    /** @param documentNo the settled invoice's document number - the success text reads "Rechnung <no> bezahlt". */
    expectSuccess: async ({ documentNo }) => await test.step(`${NAME} - Expect success ${documentNo}`, async () => {
        await expect(modalElement().getByTestId('pos-invoice-settlement-success')).toContainText(documentNo);
    }),

    close: async () => await test.step(`${NAME} - Close`, async () => {
        await modalElement().getByTestId('pos-invoice-settlement-success-close-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};
