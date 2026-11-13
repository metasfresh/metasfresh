import { holdForCaptureIfEnabled, page, revealForCaptureIfEnabled, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { exactTextMatch } from "./posText";

const NAME = 'POSInvoiceSettlementModal';
/** @returns {import('@playwright/test').Locator} */
const modalElement = () => page.getByTestId('pos-invoice-settlement-modal');
/** @returns {import('@playwright/test').Locator} */
const paymentModalElement = () => page.getByTestId('pos-cash-payment-modal');
const rowElement = (index) => modalElement().getByTestId('pos-invoice-settlement-row').nth(index);
/** The selected invoice with its payment choice: shown on a card terminal, and for an invoice with an unfinished card payment. */
const paymentChoiceElement = () => modalElement().getByTestId('pos-invoice-settlement-payment-choice');
const cardButton = () => paymentChoiceElement().getByTestId('pos-invoice-settlement-card-button');
const cashButton = () => paymentChoiceElement().getByTestId('pos-invoice-settlement-cash-button');
const cardStatusElement = () => modalElement().getByTestId('pos-card-settlement-status');
const releaseButton = () => modalElement().getByTestId('pos-card-settlement-release-button');
const releaseConfirmationElement = () => modalElement().getByTestId('pos-card-settlement-release-confirmation');
const cashRefusedMessageElement = () => paymentChoiceElement().getByTestId('pos-invoice-settlement-cash-refused-message');

/**
 * Asserts that the element is visible AND entirely inside the viewport, i.e. the cashier sees it without scrolling.
 * A plain visibility check passes for an element clipped below the fold of the dialog body.
 */
const expectEntirelyOnScreen = async (locator, { timeout = SLOW_ACTION_TIMEOUT } = {}) => {
    await expect(locator).toBeVisible({ timeout });
    await expect(locator).toBeInViewport({ ratio: 1, timeout });
};

/**
 * Waits for the response of a card settlement POST: `action` `null` = the start (`POST /pos/invoices/cardSettlements`),
 * else `POST /pos/invoices/cardSettlements/{id}/<action>`.
 */
const waitForCardSettlementPost = ({ action, timeout }) => {
    const pathPattern = action
        ? new RegExp(`/pos/invoices/cardSettlements/[^/]+/${action}$`)
        : /\/pos\/invoices\/cardSettlements$/;
    return page.waitForResponse(
        (response) => response.request().method() === 'POST' && pathPattern.test(new URL(response.url()).pathname),
        { timeout }
    );
};

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
        // capture runs only: the next step closes the dialog at once, so hold the paid state for the recorder
        await revealForCaptureIfEnabled(modalElement().getByTestId('pos-invoice-settlement-success'));
    }),

    close: async () => await test.step(`${NAME} - Close`, async () => {
        await modalElement().getByTestId('pos-invoice-settlement-success-close-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Selects the result row at `index` (0-based) on a card terminal and waits for the payment choice
     * (selected invoice + **Barzahlung** / **Kartenzahlung**).
     */
    selectResultForPaymentChoice: async ({ index = 0 } = {}) => await test.step(`${NAME} - Select result #${index} for payment choice`, async () => {
        await rowElement(index).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await paymentChoiceElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Selects the result row at `index` (0-based) of an invoice with an unfinished card payment: the till asks
     * SumUp for its current result once (`POST …/check`) and shows that payment.
     * @returns {Promise<object>} the checked card settlement, as returned by the backend
     */
    selectResultWithOpenCardPayment: async ({ index = 0 } = {}) => await test.step(`${NAME} - Select result #${index} with an open card payment`, async () => {
        const checkResponsePromise = waitForCardSettlementPost({ action: 'check', timeout: SLOW_ACTION_TIMEOUT });
        await rowElement(index).tap({ timeout: SLOW_ACTION_TIMEOUT });
        const settlement = await (await checkResponsePromise).json();
        await expectEntirelyOnScreen(cardStatusElement());
        return settlement;
    }),

    /** Asserts that the result row at `index` (0-based) shows that a card payment of the invoice is open. */
    expectResultCardPaymentOpen: async ({ index = 0, text }) => await test.step(`${NAME} - Expect result #${index} with an open card payment`, async () => {
        await expect(rowElement(index).getByTestId('pos-invoice-settlement-row-cardSettlement')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(rowElement(index).getByTestId('pos-invoice-settlement-row-cardSettlement')).toContainText(text);
    }),

    /** Asserts that the search found no open invoice. */
    expectNoResults: async () => await test.step(`${NAME} - Expect no results`, async () => {
        await expect(modalElement().getByTestId('pos-invoice-settlement-no-results')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(modalElement().getByTestId('pos-invoice-settlement-row')).toHaveCount(0);
    }),

    /** Asserts the selected invoice shown with the payment choice. `openAmt` is the rendered display string, e.g. `'16,07 €'`. */
    expectSelectedInvoice: async ({ documentNo, bpartnerName, openAmt }) => await test.step(`${NAME} - Expect selected invoice ${documentNo}`, async () => {
        const choice = paymentChoiceElement();
        await choice.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        if (documentNo != null) {
            await expectEntirelyOnScreen(choice.getByTestId('pos-invoice-settlement-selected-documentNo'));
            await expect(choice.getByTestId('pos-invoice-settlement-selected-documentNo')).toHaveText(documentNo);
        }
        if (bpartnerName != null) {
            // a long name is truncated with an ellipsis on screen; the element still holds the whole name
            await expectEntirelyOnScreen(choice.getByTestId('pos-invoice-settlement-selected-bpartnerName'));
            await expect(choice.getByTestId('pos-invoice-settlement-selected-bpartnerName')).toHaveText(bpartnerName);
        }
        if (openAmt != null) {
            await expectEntirelyOnScreen(choice.getByTestId('pos-invoice-settlement-selected-openAmt'));
            await expect(choice.getByTestId('pos-invoice-settlement-selected-openAmt')).toContainText(exactTextMatch(openAmt));
        }
    }),

    /** Asserts which payment methods are offered, entirely on screen and enabled: `isCashOffered`, `isCardOffered`. */
    expectPaymentMethodsOffered: async ({ isCashOffered, isCardOffered }) => await test.step(`${NAME} - Expect payment methods cash=${isCashOffered} card=${isCardOffered}`, async () => {
        await paymentChoiceElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        if (isCashOffered != null) {
            if (isCashOffered) {
                await expectEntirelyOnScreen(cashButton());
                await expect(cashButton()).toBeEnabled();
            } else {
                await expect(cashButton()).toBeDisabled();
            }
        }
        if (isCardOffered != null) {
            if (isCardOffered) {
                await expectEntirelyOnScreen(cardButton());
                await expect(cardButton()).toBeEnabled();
            } else {
                await expect(cardButton()).toHaveCount(0);
            }
        }
    }),

    /** Asserts that no payment choice is shown: on a cash-only terminal the selected invoice goes straight to the cash keypad. */
    expectNoPaymentChoice: async () => await test.step(`${NAME} - Expect no payment choice`, async () => {
        await paymentModalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(paymentChoiceElement()).toHaveCount(0);
        await expect(page.getByTestId('pos-invoice-settlement-card-button')).toHaveCount(0);
    }),

    /** Asserts that **Barzahlung** is disabled and the till says why (`message`, e.g. a card payment is still open), both on screen. */
    expectCashRefused: async ({ message }) => await test.step(`${NAME} - Expect cash refused`, async () => {
        await expectEntirelyOnScreen(cashButton());
        await expect(cashButton()).toBeDisabled({ timeout: SLOW_ACTION_TIMEOUT });
        await expectEntirelyOnScreen(cashRefusedMessageElement());
        await expect(cashRefusedMessageElement()).toContainText(message);
    }),

    /**
     * Taps **Kartenzahlung**. While the reader has not answered, the till shows `waitingText`; then the till's start
     * request returns.
     * @param responseTimeout how long the send to the reader may take
     * @returns {Promise<object>} the started card settlement, as returned by the backend
     */
    payByCard: async ({ waitingText, responseTimeout = SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Pay by card`, async () => {
        const startResponsePromise = waitForCardSettlementPost({ action: null, timeout: responseTimeout });
        await cardButton().tap({ timeout: SLOW_ACTION_TIMEOUT });
        await expectEntirelyOnScreen(cardStatusElement());
        await expect(cardStatusElement()).toContainText(waitingText, { timeout: SLOW_ACTION_TIMEOUT });
        const startResponse = await startResponsePromise;
        if (!startResponse.ok()) {
            throw new Error(`Starting the card payment failed: HTTP ${startResponse.status()} ${await startResponse.text()}`);
        }
        return await startResponse.json();
    }),

    /** Taps **Barzahlung** and waits for the cash-tendered keypad. */
    payByCash: async () => await test.step(`${NAME} - Pay by cash`, async () => {
        await cashButton().tap({ timeout: SLOW_ACTION_TIMEOUT });
        await paymentModalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Asserts the card payment's state as the till shows it, entirely on screen. The till polls the payment, so the
     * expected text may take a few seconds to appear (`timeout`).
     */
    expectCardPaymentStatus: async ({ text, timeout = VERY_SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect card payment status "${text}"`, async () => {
        await expect(cardStatusElement()).toContainText(text, { timeout });
        await expectEntirelyOnScreen(cardStatusElement());
        // capture runs only: the next step replaces this state at once, so hold it for the recorder
        await holdForCaptureIfEnabled();
    }),

    /** Waits until the till shows the invoice as paid (`Rechnung <no> bezahlt`); the till polls the card payment. */
    expectPaidByCard: async ({ documentNo, timeout = VERY_SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect paid by card ${documentNo}`, async () => {
        await expect(modalElement().getByTestId('pos-invoice-settlement-success')).toBeVisible({ timeout });
        await expect(modalElement().getByTestId('pos-invoice-settlement-success')).toContainText(documentNo);
        // capture runs only: the next step closes the dialog at once, so hold the paid state for the recorder
        await revealForCaptureIfEnabled(modalElement().getByTestId('pos-invoice-settlement-success'));
    }),

    /** Asserts whether **Kartenzahlung prüfen / freigeben** can be tapped (it is on screen either way); waits up to `timeout`. */
    expectReleaseEnabled: async ({ isEnabled, timeout = SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect release ${isEnabled ? 'enabled' : 'disabled'}`, async () => {
        await expectEntirelyOnScreen(releaseButton());
        if (isEnabled) {
            await expect(releaseButton()).toBeEnabled({ timeout });
        } else {
            await expect(releaseButton()).toBeDisabled({ timeout });
        }
    }),

    /**
     * Taps **Kartenzahlung prüfen / freigeben**.
     * @returns {Promise<object>} the release response: `{ settlement, confirmationRequired, refusedMessage }`
     */
    release: async () => await test.step(`${NAME} - Check / release card payment`, async () => {
        const releaseResponsePromise = waitForCardSettlementPost({ action: 'release', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await releaseButton().tap({ timeout: SLOW_ACTION_TIMEOUT });
        return await (await releaseResponsePromise).json();
    }),

    /** Asserts the confirmation the till asks for before releasing a payment whose result is still unknown, with its buttons on screen. */
    expectReleaseConfirmation: async ({ text }) => await test.step(`${NAME} - Expect release confirmation`, async () => {
        await expectEntirelyOnScreen(releaseConfirmationElement());
        await expect(releaseConfirmationElement()).toContainText(text);
        await expectEntirelyOnScreen(releaseConfirmationElement().getByTestId('pos-card-settlement-release-confirm-button'));
        await expectEntirelyOnScreen(releaseConfirmationElement().getByTestId('pos-card-settlement-release-cancel-button'));
    }),

    /**
     * Confirms that the card was not charged.
     * @returns {Promise<object>} the release response: `{ settlement, confirmationRequired, refusedMessage }`
     */
    confirmRelease: async () => await test.step(`${NAME} - Confirm card not charged`, async () => {
        const releaseResponsePromise = waitForCardSettlementPost({ action: 'release', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await releaseConfirmationElement().getByTestId('pos-card-settlement-release-confirm-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        const response = await (await releaseResponsePromise).json();
        await releaseConfirmationElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        return response;
    }),

    /**
     * Closes the dialog with its header close button, on any step but the success message: e.g. while a card payment
     * is still waiting, or from the search results.
     */
    closeDialog: async () => await test.step(`${NAME} - Close dialog`, async () => {
        await modalElement().getByTestId('pos-invoice-settlement-header-close-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await modalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};
