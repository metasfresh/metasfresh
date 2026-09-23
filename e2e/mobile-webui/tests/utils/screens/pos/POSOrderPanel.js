import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { POSPaymentPanel } from "./POSPaymentPanel";

const NAME = 'POSOrderPanel';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('pos-order-panel');

// Exact-boundary match for a formatted numeric value inside a larger text node (a bare digit via
// toContainText would false-positive: '5' also matches '15,00' or '50,00').
const exactNumberMatch = (value) =>
    new RegExp(`(^|[^0-9.,])${String(value).replace(/[.*+?^${}()|[\]\\]/g, '\\$&')}([^0-9.,]|$)`);

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

    /** Asserts the order line at `index` (0-based) matches the given field values. */
    expectLine: async ({ index, productName, qty, catchWeight, amount }) => await test.step(`${NAME} - Expect line #${index}`, async () => {
        const line = page.getByTestId('pos-order-line').nth(index);
        await line.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        if (productName != null) {
            await expect(line.getByTestId('pos-order-line-product-name')).toHaveText(productName);
        }
        if (qty != null) {
            await expect(line.getByTestId('pos-order-line-description')).toContainText(exactNumberMatch(qty));
        }
        if (catchWeight != null) {
            await expect(line.getByTestId('pos-order-line-description')).toContainText(exactNumberMatch(catchWeight));
        }
        if (amount != null) {
            await expect(line.getByTestId('pos-order-line-amount')).toContainText(exactNumberMatch(amount));
        }
    }),

    checkout: async () => await test.step(`${NAME} - Checkout`, async () => {
        await page.getByTestId('pos-order-checkout-button').tap();
        await POSPaymentPanel.waitForScreen();
    }),
};
