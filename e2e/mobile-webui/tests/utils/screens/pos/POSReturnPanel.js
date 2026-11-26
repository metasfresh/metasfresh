import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { exactTextMatch } from "./posText";

const NAME = 'POSReturnPanel';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-return-panel');
/** @returns {import('@playwright/test').Locator} */
const qtyModalElement = () => page.getByTestId('pos-return-qty-modal');
/** @returns {import('@playwright/test').Locator} */
const confirmModalElement = () => page.getByTestId('pos-return-confirm-modal');

export const POSReturnPanel = {
    /** Opens the return panel from the header's "Rücknahme" button. */
    open: async () => await test.step(`${NAME} - Open`, async () => {
        await page.getByTestId('pos-return-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await containerElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Types `barcode` into the product search bar and presses Enter, mirroring a scan. */
    scanBarcode: async (barcode) => await test.step(`${NAME} - Scan barcode ${barcode}`, async () => {
        const field = page.getByTestId('pos-product-search-input');
        await field.fill(`${barcode}`);
        await field.press('Enter');
    }),

    /**
     * Asserts the return-cart line at `index` (0-based). `qty` is the exact rendered qty+uom description
     * (e.g. `'482 g'` / `'0,300 kg'`), `amount` the rendered refund amount for that line - both exact
     * rendered display strings in the spec's login language, see {@link exactTextMatch}.
     */
    expectLine: async ({ index, productName, qty, amount }) => await test.step(`${NAME} - Expect line #${index}`, async () => {
        const line = page.getByTestId('pos-return-line').nth(index);
        await line.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        if (productName != null) {
            await expect(line.getByTestId('pos-return-line-product-name')).toHaveText(productName);
        }
        if (qty != null) {
            await expect(line.getByTestId('pos-return-line-description')).toContainText(exactTextMatch(qty));
        }
        if (amount != null) {
            await expect(line.getByTestId('pos-return-line-amount')).toContainText(exactTextMatch(amount));
        }
    }),

    /** Taps the line at `index` to open its {@link POSReturnPanel.keyQty} correction modal. */
    tapLine: async (index) => await test.step(`${NAME} - Tap line #${index}`, async () => {
        await page.getByTestId('pos-return-line').nth(index).tap({ timeout: SLOW_ACTION_TIMEOUT });
        await qtyModalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Keys `digits` (e.g. `'0.300'`) on the qty modal's on-screen keypad, one character at a time (including the decimal point). */
    keyQty: async ({ digits }) => await test.step(`${NAME} - Key qty ${digits}`, async () => {
        for (const digit of `${digits}`) {
            await qtyModalElement().getByRole('button', { name: digit, exact: true }).tap();
        }
    }),

    /** Confirms the qty modal, applying the keyed correction to the line just tapped. */
    confirmQty: async () => await test.step(`${NAME} - Confirm qty`, async () => {
        await qtyModalElement().getByTestId('pos-return-qty-modal-ok-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await qtyModalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** @param amount the rendered total refund, e.g. `'4,65 €'` for a `de_DE` login. */
    expectTotal: async (amount) => await test.step(`${NAME} - Expect total ${amount}`, async () => {
        await expect(containerElement().getByTestId('pos-return-total-amount')).toContainText(exactTextMatch(amount));
    }),

    expectPayoutDisabled: async () => await test.step(`${NAME} - Expect payout disabled`, async () => {
        await expect(containerElement().getByTestId('pos-return-payout-button')).toBeDisabled();
    }),

    /** Opens the "Auszahlen" confirmation. */
    payout: async () => await test.step(`${NAME} - Payout`, async () => {
        await page.getByTestId('pos-return-payout-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await confirmModalElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Confirms "Bar auszahlen" and waits for the success view. Captures the outgoing `/pos/returns` request's
     * `externalId` (the client generates it; the response itself carries no externalId), so the spec can key
     * a `Backend.expect({pos: {externalId, ...}})` call off it.
     * @returns {Promise<{externalId: string}>}
     */
    confirmPayCash: async () => await test.step(`${NAME} - Confirm pay cash`, async () => {
        const requestPromise = page.waitForRequest(
            (request) => request.url().includes('/pos/returns') && request.method() === 'POST',
            { timeout: SLOW_ACTION_TIMEOUT }
        );
        await confirmModalElement().getByTestId('pos-return-confirm-ok-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        const request = await requestPromise;
        const externalId = request.postDataJSON().externalId;

        await confirmModalElement().getByTestId('pos-return-success').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        return { externalId };
    }),

    /** @param amount the rendered refunded amount, e.g. `'4,65 €'` for a `de_DE` login - the success text also names the credit memo (not asserted here, it is unpredictable). */
    expectSuccess: async (amount) => await test.step(`${NAME} - Expect success ${amount}`, async () => {
        await expect(confirmModalElement().getByTestId('pos-return-success')).toContainText(exactTextMatch(amount));
    }),

    closeSuccess: async () => await test.step(`${NAME} - Close success`, async () => {
        await confirmModalElement().getByTestId('pos-return-confirm-close-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await confirmModalElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await containerElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Cancels the return and returns to the normal order panel (no confirmation is in flight). */
    cancel: async () => await test.step(`${NAME} - Cancel`, async () => {
        await page.getByTestId('pos-return-cancel-button').tap({ timeout: SLOW_ACTION_TIMEOUT });
        await containerElement().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
};
