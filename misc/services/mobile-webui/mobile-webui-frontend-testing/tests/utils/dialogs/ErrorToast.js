import { page } from '../common';

//
// IMPORTANT: DO NOT import playwright.config.js because you will introduce a circular dependency.
// This file is used by common.js which is used by playwright.config.js
//

/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('.Toastify div[role="alert"].Toastify__toast-body');

export const ErrorToast = {
    waitToPopup: (callback, timeout) => {
        const toastLocator = containerElement();
        return toastLocator.waitFor({ state: 'attached', ...{ timeout } })
            .then(async () => {
                await callback(toastLocator);
            });
    },

    closePopup: async () => {
        await page.locator('.Toastify__close-button--error').tap();
        await containerElement().waitFor({ state: 'detached' });
    },
}
