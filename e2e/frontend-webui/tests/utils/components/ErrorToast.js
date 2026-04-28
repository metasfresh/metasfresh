import { getPage } from '../common';

/**
 * Get the error toast locator from the Toastify container.
 * Matches error-level toasts with alert role.
 */
const containerElement = () =>
  getPage().locator('.Toastify div[role="alert"].Toastify__toast-body');

/**
 * Error toast component helper for detecting and interacting with error toasts.
 * Used for automatic error detection during test execution.
 */
export const ErrorToast = {
  /**
   * Wait for an error toast to appear and execute callback.
   * @param {Function} callback - Function to execute when toast appears
   * @param {number} timeout - Timeout in milliseconds
   * @returns {Promise<void>}
   */
  waitToPopup: (callback, timeout) => {
    const toastLocator = containerElement();
    return toastLocator
      .waitFor({ state: 'attached', timeout })
      .then(async () => {
        await callback?.(toastLocator);
      });
  },

  /**
   * Close the error toast popup by clicking the close button.
   */
  closePopup: async () => {
    const page = getPage();
    await page.locator('.Toastify__close-button--error').click();
    await containerElement().waitFor({ state: 'detached' });
  },
};
