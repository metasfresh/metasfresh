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

// Capture mode — OFF by default. When the env flag UAT_CAPTURE is set, the run is a deliberate
// recording run for a UAT/documentation video; otherwise this is a no-op and the test runs at
// full speed. The test's normal/CI speed is never affected.
// See skill playwright-video-delivery § "Test speed vs. recording speed".
export const UAT_CAPTURE = !!process.env.UAT_CAPTURE;

// Hold the currently-painted screen on the video recorder long enough for the freshly-entered
// values to be captured as a clear, deliberate freeze (the recorder samples ~25 fps and Playwright
// otherwise fills + confirms within a single frame, so the values are never recorded). NO-OP unless
// UAT_CAPTURE is set — so this can only ever slow a deliberate capture run, never a normal/CI run;
// the value is therefore generous for legibility, not a marginal minimum.
const CAPTURE_HOLD_MS = 500;
export const holdForCaptureIfEnabled = async () => {
    if (!UAT_CAPTURE || page == null) return;
    await page.waitForTimeout(CAPTURE_HOLD_MS);
};

// Bring an element the test is asserting on into the recorded viewport, then hold it there. Playwright's
// toBeVisible() only requires a non-empty bounding box, NOT that the element is inside the viewport — so an
// offered target that renders below the fold satisfies the assertion while appearing in no recorded frame,
// producing a video whose caption claims something its frames never show. NO-OP unless UAT_CAPTURE is set,
// so a normal/CI run neither scrolls nor waits.
export const revealForCaptureIfEnabled = async (locator) => {
    if (!UAT_CAPTURE) return;
    // NOT scrollIntoViewIfNeeded(): it only has to make an element *actionable*, so it does nothing once
    // the browser deems it near enough — which still leaves it under the bottom bar. Measured by rendering
    // this issue's six recordings with that API and reading the frames: 3 of the 6 (receiving_lu_only,
    // receiving_skip_target_step, receiving_no_catchweight) showed no target row at all. Legibility on
    // video needs full centering, so ask for it explicitly.
    await locator.evaluate((el) => el.scrollIntoView({ block: 'center', inline: 'nearest' }));
    await holdForCaptureIfEnabled();
};

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

export const expectErrorToast = async (title, func, toastValidator) => {
    const watcherId = ++nextErrorWatcherId;

    return await test.step(`Expect error: ${title} (watcherId=${watcherId})`, async () => {
        const executeFuncFailOnSuccess = async () => {
            await func();
            // Grace period: if func() returned cleanly but a toast is still pending, give
            // React time to render before declaring "not detected". The original Promise.race
            // could lose against a ~20ms-late toast render under CI load, producing false
            // "not detected" failures. The hang-on-error semantic of Promise.race
            // is preserved: if func() never returns (waiting for a screen that won't come),
            // we never reach this sleep and the toast branch wins as before.
            await new Promise(resolve => setTimeout(resolve, 2000));
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

