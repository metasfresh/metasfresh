import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { POSPaymentPanel } from "./POSPaymentPanel";
import { exactTextMatch } from "./posText";

const NAME = 'POSOrderPanel';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-order-panel');

export const POSOrderPanel = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    expectEmpty: async () => await test.step(`${NAME} - Expect empty order panel`, async () => {
        // Settle on the painted screen first, so an empty order list is not read before it has loaded.
        await POSOrderPanel.waitForScreen();
        await expect(page.getByTestId('pos-order-line')).toHaveCount(0);
    }),

    /** Types `barcode` into the product search bar and presses Enter, mirroring a scan. */
    scanBarcode: async (barcode) => await test.step(`${NAME} - Scan barcode ${barcode}`, async () => {
        const field = page.getByTestId('pos-product-search-input');
        await field.fill(`${barcode}`);
        await field.press('Enter');
    }),

    /**
     * Asserts the order line at `index` (0-based) matches the given field values. `qty`, `catchWeight`
     * and `amount` are the exact rendered display strings, in the spec's login language - e.g. `'5,00'`
     * / `'0,482 kg'` for a `de_DE` login - see {@link exactTextMatch}.
     */
    expectLine: async ({ index, productName, qty, catchWeight, amount }) => await test.step(`${NAME} - Expect line #${index}`, async () => {
        const line = page.getByTestId('pos-order-line').nth(index);
        await line.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        if (productName != null) {
            await expect(line.getByTestId('pos-order-line-product-name')).toHaveText(productName);
        }
        if (qty != null) {
            await expect(line.getByTestId('pos-order-line-description')).toContainText(exactTextMatch(qty));
        }
        if (catchWeight != null) {
            await expect(line.getByTestId('pos-order-line-description')).toContainText(exactTextMatch(catchWeight));
        }
        if (amount != null) {
            await expect(line.getByTestId('pos-order-line-amount')).toContainText(exactTextMatch(amount));
        }
    }),

    checkout: async () => await test.step(`${NAME} - Checkout`, async () => {
        await page.getByTestId('pos-order-checkout-button').tap();
        await POSPaymentPanel.waitForScreen();
    }),
};
