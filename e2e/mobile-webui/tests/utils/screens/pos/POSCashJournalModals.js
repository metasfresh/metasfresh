import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { exactTextMatch } from "./posText";

const NAME = 'POSCashJournalModals';
/** @returns {import('@playwright/test').Locator} */
const closingModalElement = () => page.getByTestId('pos-cash-journal-closing-modal');

const detailRow = (detailType) =>
    page.locator(`[data-testid="pos-cash-journal-summary-detail-row"][data-detail-type="${detailType}"]`);

export const POSCashJournalModals = {
    /** Opens the closing panel from the "close cash journal" header button. */
    openClosing: async () => await test.step(`${NAME} - Open closing panel`, async () => {
        await page.getByTestId('pos-close-cash-journal-button').tap();
        await closingModalElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the closing summary. `cashPayments` is the CASH_PAYMENTS detail row - the running total
     * of cash-sale payments (`JsonCashJournalSummary.java`: `CASH_PAYMENT` lines, e.g. checkout via
     * `POSPaymentPanel.payCash`). `cashIn` / `cashOut` are the CASH_IN / CASH_OUT detail rows - manual
     * cash-in/cash-out movements (`CASH_IN_OUT` lines), a different source than a sale. `endingBalance`
     * is the booked (ending) cash balance. Every value is the exact rendered display string, in the
     * spec's login language - e.g. `'5,00'` for a `de_DE` login - see {@link exactTextMatch}.
     */
    expectSummary: async ({ cashPayments, cashIn, cashOut, endingBalance }) => await test.step(`${NAME} - Expect summary`, async () => {
        await closingModalElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        if (cashPayments != null) {
            await expect(detailRow('CASH_PAYMENTS')).toContainText(exactTextMatch(cashPayments));
        }
        if (cashIn != null) {
            await expect(detailRow('CASH_IN')).toContainText(exactTextMatch(cashIn));
        }
        if (cashOut != null) {
            await expect(detailRow('CASH_OUT')).toContainText(exactTextMatch(cashOut));
        }
        if (endingBalance != null) {
            // Scoped to the CASH summary row - getByTestId alone would strict-mode-violate once a
            // second payment method (e.g. CARD) adds its own booked-amount cell.
            const cashRow = page.locator('[data-testid="pos-cash-journal-summary-row"][data-payment-method="CASH"]');
            await expect(cashRow.getByTestId('pos-cash-journal-summary-booked-amount')).toContainText(exactTextMatch(endingBalance));
        }
    }),
};
