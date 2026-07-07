import { page, SLOW_ACTION_TIMEOUT } from '../common';

//
// IMPORTANT: DO NOT import playwright.config.js here — it would introduce a circular dependency
// (this file is used by screen objects that are, in turn, reachable from common.js/playwright.config.js).
// Mirror ErrorToast.js, which is intentionally config-free for the same reason.
//

// The success/notification toast is react-toastify's success variant. It carries the
// `Toastify__toast--success` modifier on the toast wrapper, distinguishing it from the error toast
// (`Toastify__toast--error`, matched by ErrorToast.js). We match the success variant so this never
// resolves on an error toast.
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.Toastify .Toastify__toast--success').first();

export const NotificationToast = {
    /**
     * Wait for the success/notification toast to be PAINTED (visible), not merely attached.
     *
     * This is the commit-confirming, user-visible signal for actions that finish with a
     * `toastNotification(...)` in their REST `.then()` handler (see e2e/mobile-webui/CLAUDE.md
     * § "async-commit race" / "Assert what the user SEES"). Awaiting it BEFORE the follow-up
     * screen-transition wait gives the (potentially slow, under CI load) round-trip its OWN timeout
     * budget, instead of cramming "slow commit + navigation + websocket refetch" into the single
     * budget of the next `waitForScreen()` — which is what makes that follow-up wait overshoot and flake.
     *
     * Note: the toast is transient (react-toastify auto-closes it), so we wait for `visible` — which
     * resolves the instant it paints — and never for it to detach.
     */
    waitToPopup: async ({ timeout = SLOW_ACTION_TIMEOUT } = {}) => {
        await containerElement().waitFor({ state: 'visible', timeout });
    },
};
