import { test } from '../../playwright.config';
import { ErrorScreen } from './screens/ErrorScreen';
import { ErrorToast } from './dialogs/ErrorToast';

export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3001/mobile';

export const VERY_FAST_ACTION_TIMEOUT = 1000; // 1sec
export const FAST_ACTION_TIMEOUT = 5000; // 5sec
export const SLOW_ACTION_TIMEOUT = 20000; // 20sec
export const VERY_SLOW_ACTION_TIMEOUT = 40000; //40sec
export const ID_BACK_BUTTON = '#Back-button';

export let page = null;

export const setCurrentPage = (currentPage) => {
    page = currentPage;
}

export const step = async (title, func) => await test.step(title, async () => await runAndWatchForErrors(func));

let nextErrorWatcherId = 101;
let currentErrorWatcherId = 0;
const runAndWatchForErrors = async (func) => {
    if (currentErrorWatcherId > 0) {
        // console.log(`Already watching for errors (watcherId=${currentErrorWatcherId}), calling the function directly`);
        return await func();
    }

    const watcherId = ++nextErrorWatcherId;
    currentErrorWatcherId = watcherId;
    // console.log(`Start watching for errors (watcherId=${watcherId})`);
    try {
        return await Promise.race([
            func(),
            ErrorToast.waitToPopup(
                async (toastLocator) => {
                    if (currentErrorWatcherId !== watcherId) {
                        // console.log(`Error toast detected, but the current watcher id (${currentErrorWatcherId}) does not match the current one (${watcherId})`);
                        return;
                    }

                    const textContent = await toastLocator.textContent();
                    // console.log(`Error toast detected (watcherId=${watcherId}): ${textContent}. Throwing error.`)
                    throw new Error('Unexpected error toast detected: ' + textContent);
                },
                999_000
            ),
            ErrorScreen.watchForScreen(async () => {
                throw new Error('Unexpected error screen detected. Usually this is an indicator of development errors. Check console for more info.');
            }),
        ]);
    } finally {
        currentErrorWatcherId = 0;
        // console.log(`Stop watching for errors (watcherId=${watcherId}), set back previous watcher id (0)`);
    }
}

export const expectErrorToastIf = async (condition, title, func, toastValidator) => {
    if (condition) {
        return await expectErrorToast(title, func, toastValidator);
    } else {
        return await func();
    }
};

/**
 * Grace timeout for the toast to appear AFTER func() has returned cleanly.
 *
 * Some flows render the error toast well after the action that triggers it returns:
 * the GRAI scanner debounces ~1500ms before issuing the REST call, and func() in those
 * tests returns almost immediately (the target screen is already in the DOM, so its
 * waitForScreen() resolves at once). A fixed sleep-then-throw raced the toast wait and,
 * under CI load, the sleep could win even though the (correct) toast appeared shortly
 * after — a false "not detected". We instead actively wait for the toast element for this
 * long, so a late-but-correct toast always wins. Must comfortably exceed the GRAI debounce
 * plus CI scheduling jitter.
 */
const TOAST_GRACE_TIMEOUT = SLOW_ACTION_TIMEOUT; // 20s

export const expectErrorToast = async (title, func, toastValidator) => {
    const watcherId = ++nextErrorWatcherId;

    return await test.step(`Expect error: ${title} (watcherId=${watcherId})`, async () => {
        const executeFuncFailOnSuccess = async () => {
            await func();
            // Grace period: func() returned cleanly but a toast may still be pending render
            // (e.g. the GRAI scanner debounces ~1500ms before its REST call, yet func() returns
            // immediately because the target screen is already on-screen). Instead of sleeping a
            // fixed time and then throwing — which raced the toast wait and could lose to a
            // late-but-correct toast under CI load — we actively wait for the toast element to
            // attach. If it appears, this branch resolves (never throwing) and lets the parallel
            // ErrorToast.waitToPopup branch validate + close it; only a genuine timeout (no toast
            // within TOAST_GRACE_TIMEOUT) declares "not detected". The hang-on-error semantic of
            // Promise.race is preserved: if func() never returns (waiting for a screen that won't
            // come because the error fired instead), we never reach here and the toast branch wins.
            try {
                await ErrorToast.waitToPopup(undefined, TOAST_GRACE_TIMEOUT);
            } catch {
                throw new Error(`Expected error toast not detected (watcherId=${watcherId})`);
            }
            // Toast appeared after func() returned: yield to let the sibling waitToPopup branch
            // (which carries the validator + closePopup) win the race and assert on it.
            await new Promise(() => {});
        }

        const prevWatcherId = currentErrorWatcherId;
        currentErrorWatcherId = watcherId;
        // console.log(`Start expecting errors (watcherId=${watcherId})`);
        try {
            await Promise.race([
                executeFuncFailOnSuccess(),
                ErrorToast.waitToPopup(async (toastLocator) => {
                    if (currentErrorWatcherId !== watcherId) {
                        // console.log(`Error toast detected, but the current watcher id (${currentErrorWatcherId}) does not match the current one (${watcherId})`);
                        return;
                    }

                    const textContent = await toastLocator.textContent();
                    console.log(`[ OK ] Expected error toast detected (watcherId=${watcherId}): ${textContent}`)

                    if (toastValidator) {
                        await toastValidator({ textContent, toast: toastLocator });
                    }

                    await ErrorToast.closePopup();
                })
            ]);
        } finally {
            currentErrorWatcherId = prevWatcherId;
            // console.log(`Stop expecting errors (watcherId=${watcherId}), set back previous watcher id (${prevWatcherId})`);
        }
    });
};

