import { expect } from '@playwright/test';
import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../common';

//
// IMPORTANT: DO NOT import playwright.config.js because you will introduce a circular dependency.
// This file is used by common.js which is used by playwright.config.js
//

/**
 * @returns {import('@playwright/test').Locator}
 * Scope to ERROR toasts only (`.Toastify__toast--error`) — this helper backs the global
 * unexpected-error watcher (common.js) and the expect-an-error helpers, so it must match errors
 * (`toastError` → `toast.error` → `--error`) and NOT non-blocking success/info notices
 * (`toastNotification` → `toast.success` → `--success`, e.g. the GRAI "N skipped" notice).
 * react-toastify renders the type modifier on the toast container and `role="alert"` on the body;
 * without the `--error` ancestor constraint a lingering success toast was misdetected as an error.
 */
const containerElement = () => page.locator('.Toastify .Toastify__toast--error div[role="alert"].Toastify__toast-body');

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
        // tap() itself scrolls into view and waits until the button is visible, stable (same box in two consecutive animation
        // frames) and enabled, so a separate scrollIntoViewIfNeeded(FAST) only added a second 5 s stability wait.
        // The toast's enter animation is long over here (waitToPopup + text validation take longer; measured: no running
        // animation, constant button box). The flake (TC23, 1 of 3): "element is not stable" over the whole 5 s with only 2
        // attempts, i.e. the page produced almost no frames while the host was overloaded (load average ~20, other Playwright
        // runs on the same machine). The stability check needs frames, so its budget must be the machine-load budget (SLOW), not 5 s.
        // Actionability stays fully enforced; nothing is skipped (force is not used).
        await closeButton.tap({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(toasts).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        // The message body is gone before react-toastify has released the toast id: it keeps the id (and the toast element) until
        // its exit animation ends, about 1 s later, and ignores a new toast with the same id meanwhile. Wait for the element,
        // so that the next scan's message (same scanner toast id) is not swallowed.
        await expect(page.locator('.Toastify__toast--error')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    },

    // Assert exactly N non-blocking success/info toasts (`.Toastify__toast--success`) are shown — one
    // skipped scan must surface once, never as stacked duplicates (the "user must see exactly ONE" rule).
    expectSuccessToastCount: async (expectedCount) => {
        const successToasts = page.locator('.Toastify__toast--success');
        if (expectedCount > 0) {
            await expect(successToasts.first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        }
        await expect(successToasts).toHaveCount(expectedCount, { timeout: SLOW_ACTION_TIMEOUT });
    },

    // Assert no blocking (red) error toast is shown.
    expectNoErrorToast: async () => {
        await expect(page.locator('.Toastify__toast--error')).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    },
}
