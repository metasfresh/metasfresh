import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from "@playwright/test";
import { POSPaymentPanel } from "./POSPaymentPanel";

const NAME = 'POSOrderPanel';
// POSOrderPanel.jsx (not touched by this task) already carries this class - used as a CSS fallback
// selector instead of adding a data-testid to a file outside this task's scope.
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.pos-order-panel');

export const POSOrderPanel = {
    waitForScreen: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout });
        await page.locator('.loading').waitFor({ state: 'detached', timeout });
    }),

    expectEmpty: async () => await test.step(`${NAME} - Expect empty order panel`, async () => {
        await expect(containerElement()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
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
            await expect(line.getByTestId('pos-order-line-description')).toContainText(`${qty}`);
        }
        if (catchWeight != null) {
            await expect(line.getByTestId('pos-order-line-description')).toContainText(`${catchWeight}`);
        }
        if (amount != null) {
            await expect(line.getByTestId('pos-order-line-amount')).toContainText(`${amount}`);
        }
    }),

    checkout: async () => await test.step(`${NAME} - Checkout`, async () => {
        // CurrentOrderActions.jsx (not touched by this task) already carries this class.
        await page.locator('.current-order-actions .pay-action').tap();
        await POSPaymentPanel.waitForScreen();
    }),
};
