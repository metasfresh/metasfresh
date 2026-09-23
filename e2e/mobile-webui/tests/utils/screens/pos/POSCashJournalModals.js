import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";

const NAME = 'POSCashJournalModals';
/** @returns {import('@playwright/test').Locator} */
const closingModalElement = () => page.getByTestId('pos-cash-journal-closing-modal');

export const POSCashJournalModals = {
    /** Opens the closing panel from the "close cash journal" header button. */
    openClosing: async () => await test.step(`${NAME} - Open closing panel`, async () => {
        // Header.jsx (not touched by this task) has no data-testid for this button.
        await page.locator('.pos-header .center .pos-header-button').tap();
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
                .toContainText(`${cashIn}`);
        }
        if (cashOut != null) {
            await expect(page.locator('[data-testid="pos-cash-journal-summary-detail-row"][data-detail-type="CASH_OUT"]'))
                .toContainText(`${cashOut}`);
        }
        if (endingBalance != null) {
            await expect(page.getByTestId('pos-cash-journal-summary-booked-amount')).toContainText(`${endingBalance}`);
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
