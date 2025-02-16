export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3001/mobile';

export const FAST_ACTION_TIMEOUT = 5000; // 5sec
export const SLOW_ACTION_TIMEOUT = 20000; // 20sec
export const VERY_SLOW_ACTION_TIMEOUT = 40000; //40sec
export const ID_BACK_BUTTON = '#Back-button';

export let page = null;

export const setCurrentPage = (currentPage) => {
    page = currentPage;
}

export const runAndWatchForErrors = async (func) => {
    return await Promise.race([
        watchForErrorToastsAndFail(),
        func(),
    ]);
}

const watchForErrorToastsAndFail = () => {
    const toastLocator = page.locator('.Toastify div[role="alert"].Toastify__toast-body');
    return toastLocator.waitFor({ state: 'attached' })
        .then(async () => {
            const textContent = await toastLocator.textContent();
            throw new Error('Error toast detected: ' + textContent);
        });
}