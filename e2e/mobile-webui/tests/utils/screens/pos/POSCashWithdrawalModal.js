import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";

const NAME = 'POSCashWithdrawalModal';
/** @returns {import('@playwright/test').Locator} */
const modalElement = () => page.getByTestId('pos-cash-withdrawal-modal');
/** @returns {import('@playwright/test').Locator} */
const slipElement = () => page.getByTestId('pos-cash-withdrawal-slip');
const categoryButton = (name) => modalElement().locator(`[data-testid="pos-cash-withdrawal-category-button"][data-category-name="${name}"]`);

export const POSCashWithdrawalModal = {
    /**
     * Records every document the till sends to the printer. The slip is printed from a hidden iframe
     * (print-js), whose `print()` would open the browser's print dialog; the replacement stores the printed
     * document's text on the top window instead. Must be called before the first navigation (it is a `page.addInitScript`).
     */
    recordPrintedDocuments: async () => await test.step(`${NAME} - Record printed documents`, async () => {
        await page.addInitScript(() => {
            window.print = () => {
                const printed = window.top.__posPrintedDocuments || (window.top.__posPrintedDocuments = []);
                printed.push(document.body ? document.body.innerText : '');
            };
        });
    }),

    /** Opens the withdrawal dialog from the header's "Entnahme" button. */
    open: async () => await test.step(`${NAME} - Open`, async () => {
        await page.getByTestId('pos-cash-withdrawal-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts the offered categories, by charge name, in the given order. */
    expectCategories: async ({ names }) => await test.step(`${NAME} - Expect categories ${names.join(', ')}`, async () => {
        await expect(modalElement().getByTestId('pos-cash-withdrawal-category-button')).toHaveText(names, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    selectCategory: async ({ name }) => await test.step(`${NAME} - Select category ${name}`, async () => {
        await categoryButton(name).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(categoryButton(name)).toHaveAttribute('data-selected', 'true');
    }),

    /** Keys the amount on the on-screen numpad, as a cashier does on a touch till. */
    keyAmount: async ({ digits }) => await test.step(`${NAME} - Key amount ${digits}`, async () => {
        for (const digit of `${digits}`) {
            await modalElement().getByRole('button', { name: digit, exact: true }).tap();
        }
    }),

    /** @param amount the amount as displayed, in the spec's login language, e.g. `'12,00 €'` for `de_DE` */
    expectAmount: async ({ amount }) => await test.step(`${NAME} - Expect amount ${amount}`, async () => {
        await expect(modalElement().getByTestId('pos-cash-withdrawal-amount')).toHaveText(amount);
    }),

    expectOKDisabled: async () => await test.step(`${NAME} - Expect OK disabled`, async () => {
        await expect(modalElement().getByTestId('pos-cash-withdrawal-ok-button')).toBeDisabled();
    }),

    /** Confirms the withdrawal and waits for its slip. */
    confirm: async () => await test.step(`${NAME} - Confirm`, async () => {
        await modalElement().getByTestId('pos-cash-withdrawal-ok-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await slipElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the slip preview. Every value is the rendered display string in the spec's login language;
     * `date` and `cashier` may be a part of the shown value (e.g. the date without the time). The slip ends with "Geld erhalten" above the recipient's signature line.
     */
    expectSlip: async ({ terminal, date, cashier, category, amount }) => await test.step(`${NAME} - Expect slip`, async () => {
        await expect(slipElement().getByTestId('pos-cash-withdrawal-slip-terminal')).toHaveText(terminal);
        await expect(slipElement().getByTestId('pos-cash-withdrawal-slip-date')).toContainText(date);
        await expect(slipElement().getByTestId('pos-cash-withdrawal-slip-cashier')).toContainText(cashier);
        await expect(slipElement().getByTestId('pos-cash-withdrawal-slip-category')).toHaveText(category);
        await expect(slipElement().getByTestId('pos-cash-withdrawal-slip-amount')).toHaveText(amount);
        await expect(slipElement().getByTestId('pos-cash-withdrawal-slip-received')).toHaveText('Geld erhalten');
        await expect(slipElement().locator('[data-testid="pos-cash-withdrawal-slip-received"] + [data-testid="pos-cash-withdrawal-slip-signature"]')).toBeVisible();
    }),

    /** Asserts that the slip was sent to the printer (needs {@link POSCashWithdrawalModal.recordPrintedDocuments}) and that the printed slip shows the given texts. */
    expectSlipPrinted: async ({ texts }) => await test.step(`${NAME} - Expect slip printed`, async () => {
        await expect.poll(async () => page.evaluate(() => (window.__posPrintedDocuments || []).length), { timeout: SLOW_ACTION_TIMEOUT })
            .toBeGreaterThan(0);
        const printed = await page.evaluate(() => window.__posPrintedDocuments[window.__posPrintedDocuments.length - 1]);
        for (const text of texts) {
            expect(printed).toContain(text);
        }
    }),

    closeSlip: async () => await test.step(`${NAME} - Close slip`, async () => {
        await modalElement().getByTestId('pos-cash-withdrawal-slip-close-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};
