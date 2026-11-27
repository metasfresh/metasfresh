import { expect } from '@playwright/test';
import { ID_BACK_BUTTON, page, step } from '../common';
import { GetQuantityDialog } from '../screens/picking/GetQuantityDialog';
import { PickLineScanScreen } from '../screens/picking/PickLineScanScreen';

const NAME = 'SuccessToast';
const SUCCESS_TOASTS = '.Toastify__toast--success';

/** True when the topmost element at the center of `locator` is inside the toasts: the toast would swallow a tap meant for it */
const isCoveredByToast = (locator) => locator.evaluate((el) => {
    const r = el.getBoundingClientRect();
    return !!document.elementFromPoint(r.x + r.width / 2, r.y + r.height / 2)?.closest('.Toastify');
});

/** The non-blocking green message (e.g. "New TU opened"), see also dialogs/ErrorToast.js for the red one */
export const SuccessToast = {
    /** Asserts exactly `expectedCount` green messages are shown (Playwright default timeout, or `timeout`) */
    expectCount: async (expectedCount, { timeout } = {}) => await step(`${NAME} - Expect ${expectedCount} green message(s)`, async () => {
        await expect(page.locator(SUCCESS_TOASTS)).toHaveCount(expectedCount, timeout != null ? { timeout } : undefined);
    }),

    /**
     * While the green message is shown, it covers no interactive or key element of the pop-up: OK, the count field, the target quantity, Weiteres TU, the footer Back button (when shown);
     * a real tap on OK and on the count field succeeds (a trial click fails when another element is on top).
     * `shotPath`: also saves a screenshot after the toast animation (when given).
     */
    expectNothingCoveredWhileShown: async ({ shotPath } = {}) => await step(`${NAME} - Expect the green message covers nothing`, async () => {
        const toast = page.locator(SUCCESS_TOASTS);
        await expect(toast).toBeVisible();
        if (shotPath) await page.waitForTimeout(600); // after the toast animation
        if (shotPath) await page.screenshot({ path: shotPath });

        const keyElements = {
            'OK': GetQuantityDialog.doneButton(),
            'count field': GetQuantityDialog.qtyInputField(),
            'target quantity': GetQuantityDialog.qtyTargetElement(),
            'Weiteres TU': PickLineScanScreen.nextTUButtonLocator(),
        };
        for (const [label, locator] of Object.entries(keyElements)) {
            await expect(locator, `${label} is shown`).toBeVisible();
            expect.soft(await isCoveredByToast(locator.first()), `${label} is not covered by the green message`).toBe(false);
        }
        // the footer "Back" button, only when it is on screen (the pop-up may stand over it)
        const back = page.locator(ID_BACK_BUTTON);
        if (await back.count() > 0 && await back.first().isVisible()) {
            expect.soft(await isCoveredByToast(back.first()), 'footer Back button is not covered by the green message').toBe(false);
        }
        await GetQuantityDialog.doneButton().click({ trial: true, timeout: 1000 });
        await GetQuantityDialog.qtyInputField().click({ trial: true, timeout: 1000 });
        await expect(toast).toBeVisible();
    }),

    /** Several TUs opened in a row: still ONE green message, and it covers nothing of the pop-up: the first row (target quantity, its row label), the count field, OK, Weiteres TU */
    expectOneCoveringNothing: async ({ shotPath } = {}) => await step(`${NAME} - Expect ONE green message covering nothing`, async () => {
        await SuccessToast.expectCount(1);
        const firstRow = GetQuantityDialog.firstRow();
        await expect(firstRow, 'first row of the pop-up is shown').toBeVisible();
        expect.soft(await isCoveredByToast(firstRow.locator('td').first()), 'label of the first row is not covered by the green message').toBe(false);
        await SuccessToast.expectNothingCoveredWhileShown({ shotPath });
    }),
};
