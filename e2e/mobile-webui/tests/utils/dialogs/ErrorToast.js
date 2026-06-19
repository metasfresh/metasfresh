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
        // Wait for the FIRST error toast to attach, then hand it to the caller. The count is
        // enforced separately in closePopup (exactly one), so .first() here only picks the element
        // to validate the text against — it does not condone duplicates.
        const toastLocator = containerElement().first();
        return toastLocator.waitFor({ state: 'attached', ...{ timeout } })
            .then(async () => {
                await callback?.(toastLocator);
            });
    },

    closePopup: async () => {
        // A single failure must surface as EXACTLY ONE error toast. Stacked duplicate toasts are a
        // UI/UX defect (the operator wonders "did it fail twice?", and on the small mobile viewport
        // the upper toast can render off-screen, hiding its close button). The source is fixed so one
        // scan-error renders one toast; enforce that here so any future regression fails loud instead
        // of being silently dismissed.
        //
        // The error toast appears after a debounce-tolerant active wait (see waitToPopup); by the time
        // closePopup runs the toast is already attached and validated, so a stable count check is safe.
        const toasts = containerElement();
        const count = await toasts.count();
        if (count > 1) {
            throw new Error(
                `UI/UX defect: ${count} error toasts stacked — the user must see exactly ONE error`
            );
        }

        const closeButton = page.locator('.Toastify__close-button--error').first();
        await closeButton.scrollIntoViewIfNeeded({ timeout: FAST_ACTION_TIMEOUT });
        await closeButton.tap({ timeout: FAST_ACTION_TIMEOUT });
        await expect(toasts).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    },
}
