import { page, SLOW_ACTION_TIMEOUT } from '../common';

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
        await page.locator('.Toastify__close-button--error').first().tap();
        await containerElement().first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    },
}
