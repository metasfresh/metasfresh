import { expect } from '@playwright/test';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../common';

//
// IMPORTANT: DO NOT import playwright.config.js because you will introduce a circular dependency.
// This file is used by common.js which is used by playwright.config.js
//

/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.Toastify div[role="alert"].Toastify__toast-body');

export const ErrorToast = {
    waitToPopup: (callback, timeout) => {
        // Use .first() to handle the case where multiple toasts are stacked
        // (react-toastify can show multiple toasts with the same message).
        const toastLocator = containerElement().first();
        return toastLocator.waitFor({ state: 'attached', ...{ timeout } })
            .then(async () => {
                await callback?.(toastLocator);
            });
    },

    closePopup: async () => {
        // react-toastify can stack multiple toasts (e.g. during a picking screen transition the GRAI
        // scanner is briefly mounted twice, so a single backend error gets rendered as two identical
        // toasts). Closing must therefore dismiss EVERY error toast, not just the first.
        //
        // Two traps handled here:
        //  1. Closing only the first toast then waiting for "no toast at all" hangs, because the
        //     surviving duplicate keeps the toast-body locator matched. We loop until the count is 0.
        //  2. The toasts are bottom-center and stack UPWARD, so on the small mobile viewport the upper
        //     (earlier-in-DOM) toast's close button can be scrolled out of view, and a tap on it fails
        //     with "Element is outside of the viewport". We close the LAST toast first (the lowest,
        //     on-screen one); after it is removed the next one moves on-screen, and so on.
        const closeButtons = page.locator('.Toastify__close-button--error');
        const toasts = containerElement();
        for (let i = 0; i < 10; i++) {
            const remaining = await toasts.count();
            if (remaining === 0) {
                return;
            }
            const lastClose = closeButtons.nth(remaining - 1);
            await lastClose.scrollIntoViewIfNeeded({ timeout: FAST_ACTION_TIMEOUT });
            await lastClose.tap({ timeout: FAST_ACTION_TIMEOUT });
            // Wait for the toast count to drop below the current value before closing the next one,
            // so we don't repeatedly tap a toast that is mid-removal animation.
            await expect(toasts).toHaveCount(remaining - 1, { timeout: SLOW_ACTION_TIMEOUT });
        }
        // Final assert: all error toasts are gone.
        await expect(toasts).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    },
}
