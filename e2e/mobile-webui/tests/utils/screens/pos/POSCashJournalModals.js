import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";

const NAME = 'POSCashJournalModals';
/** @returns {import('@playwright/test').Locator} */
const closingModalElement = () => page.getByTestId('pos-cash-journal-closing-modal');

// Exact-boundary match for a formatted numeric value inside a larger text node (a bare digit via
// toContainText would false-positive: '5' also matches '15,00' or '50,00').
const exactNumberMatch = (value) =>
    new RegExp(`(^|[^0-9.,])${String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}([^0-9.,]|$)`);

export const POSCashJournalModals = {
    /** Opens the closing panel from the "close cash journal" header button. */
    openClosing: async () => await test.step(`${NAME} - Open closing panel`, async () => {
        await page.getByTestId('pos-close-cash-journal-button').tap();
        await closingModalElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the closing summary: the CASH_IN / CASH_OUT payment-detail rows and the booked (ending)
     * cash balance.
     */
    expectSummary: async ({ cashIn, cashOut, endingBalance }) => await test.step(`${NAME} - Expect summary`, async () => {
        await closingModalElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        if (cashIn != null) {
            await expect(page.locator('[data-testid="pos-cash-journal-summary-detail-row"][data-detail-type="CASH_IN"]'))
                .toContainText(exactNumberMatch(cashIn));
        }
        if (cashOut != null) {
            await expect(page.locator('[data-testid="pos-cash-journal-summary-detail-row"][data-detail-type="CASH_OUT"]'))
                .toContainText(exactNumberMatch(cashOut));
        }
        if (endingBalance != null) {
            // Scoped to the CASH summary row - getByTestId alone would strict-mode-violate once a
            // second payment method (e.g. CARD) adds its own booked-amount cell.
            const cashRow = page.locator('[data-testid="pos-cash-journal-summary-row"][data-payment-method="CASH"]');
            await expect(cashRow.getByTestId('pos-cash-journal-summary-booked-amount')).toContainText(exactNumberMatch(endingBalance));
        }
    }),

    close: async () => await test.step(`${NAME} - Close cash journal`, async () => {
        await page.getByTestId('pos-cash-journal-close-button').tap();
        await closingModalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    cancel: async () => await test.step(`${NAME} - Cancel closing`, async () => {
        await page.getByTestId('pos-cash-journal-cancel-button').tap();
        await closingModalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};
