import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { ApplicationsListScreen } from "../ApplicationsListScreen";

const NAME = 'POSScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-screen');

export const POSScreen = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    /** Starts the `pos` application from the applications list (home screen) and waits for the POS screen. */
    startFromApplicationsList: async () => await test.step(`${NAME} - Start from applications list`, async () => {
        await ApplicationsListScreen.startApplication('pos');
        await POSScreen.waitForScreen();
    }),

    /**
     * Selects the POS terminal to work with, by its backend id (`masterdata.posTerminals.<id>.id`).
     *
     * When exactly one POS terminal is configured, `POSTerminalSelectModal` auto-selects it and is
     * never rendered (see the component's `useEffect`) - the cash-journal-open modal is shown directly
     * instead. When more than one terminal is configured, the select modal is shown and the terminal
     * with the matching `data-terminal-id` is tapped.
     */
    selectTerminal: async ({ posTerminalId } = {}) => await test.step(`${NAME} - Select terminal ${posTerminalId ?? '(auto)'}`, async () => {
        const selectModal = page.getByTestId('pos-terminal-select-modal');
        const openJournalModal = page.getByTestId('pos-cash-journal-open-modal');

        // Whichever appears first: the select modal (more than one terminal configured) or the
        // cash-journal-open modal (exactly one terminal - already auto-selected).
        await selectModal.or(openJournalModal).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        if (await selectModal.isVisible()) {
            await selectModal.locator(`[data-testid="pos-terminal-button"][data-terminal-id="${posTerminalId}"]`).tap();
            await selectModal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        }
    }),

    /** Opens the terminal's cash journal for the day with the given opening balance. */
    openCashJournal: async ({ openingBalance }) => await test.step(`${NAME} - Open cash journal with ${openingBalance}`, async () => {
        const modal = page.getByTestId('pos-cash-journal-open-modal');
        await modal.waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        await page.getByTestId('pos-cash-journal-opening-balance-input').fill(`${openingBalance}`);
        await page.getByTestId('pos-cash-journal-open-button').tap();

        await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the drawer's current cash balance. The only place this value is rendered on screen
     * outside the closing summary (see `POSCashJournalModals.expectSummary`) is the opening-balance
     * input of `POSCashJournalOpenModal`, pre-filled from `posTerminal.cashLastBalance` - i.e. the
     * balance carried over from the last closed journal. Only meaningful while that modal is showing
     * (terminal not yet opened for the day).
     */
    expectDrawerBalance: async (amount) => await test.step(`${NAME} - Expect drawer balance ${amount}`, async () => {
        const modal = page.getByTestId('pos-cash-journal-open-modal');
        await modal.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.getByTestId('pos-cash-journal-opening-balance-input')).toHaveValue(`${amount}`);
    }),
};
