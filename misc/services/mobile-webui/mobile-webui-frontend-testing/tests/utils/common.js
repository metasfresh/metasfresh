import { test } from '../../playwright.config';
import { ErrorScreen } from './screens/ErrorScreen';
import { ErrorToast } from './dialogs/ErrorToast';

export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3001/mobile';

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

export const expectErrorToast = async (title, func) => {
    const watcherId = ++nextErrorWatcherId;

    return await test.step(`Expect error: ${title} (watcherId=${watcherId})`, async () => {
        const executeFuncFailOnSuccess = async () => {
            await func();
            throw new Error(`Expected error toast not detected (watcherId=${watcherId})`);
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
                    await ErrorToast.closePopup();
                })
            ]);
        } finally {
            currentErrorWatcherId = prevWatcherId;
            // console.log(`Stop expecting errors (watcherId=${watcherId}), set back previous watcher id (${prevWatcherId})`);
        }
    });
};

