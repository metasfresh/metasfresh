import { test } from '../../playwright.config';
import { ErrorScreen } from './screens/ErrorScreen';
import { ErrorToast } from './dialogs/ErrorToast';

export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3001/mobile';

export const VERY_FAST_ACTION_TIMEOUT = 1000; // 1sec
export const FAST_ACTION_TIMEOUT = 5000; // 5sec
// Window for the keyboard barcode-reader hook to flush a scanned barcode (rateMs default 300ms).
// Used only for the negative dedup assertion (count must NOT change), where there's no new DOM to poll.
export const BARCODE_HOOK_FLUSH_MS = 500;
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

export const step = async (title, func) => await test.step(title, async () => await runAndWatchForErrors(func));

/**
 * Simulate a device / browser Back button press (the hardware Back key on a handheld, or the browser
 * Back). This is a pure NO-OP in the app — useDeviceBackButton absorbs it, so the screen does not change
 * and the operator stays where they are. It explicitly does NOT behave like the on-screen footer Back
 * button. Use this to assert the no-op contract; for real Back navigation use a screen object's
 * goBack() (e.g. PickingJobScreen.goBack() / SelectPickTargetLUScreen.goBack()).
 */
export const pressDeviceBack = async () => await step(`Press device/browser Back button`, async () => {
    await page.goBack({ timeout: SLOW_ACTION_TIMEOUT });
});

// Simulates the operator mashing the hardware/browser Back button rapidly: several back traversals
// dispatched within a single event-loop tick (the worst case — exactly what happens on a busy handheld
// when queued hardware-Back presses are delivered in a burst before the page can react). page.goBack()
// awaits each navigation and so cannot reproduce this; firing window.history.back() synchronously can.
export const mashDeviceBack = async (times = 12) => await step(`Mash device/browser Back ${times}x rapidly`, async () => {
    await page.evaluate((n) => {
        for (let i = 0; i < n; i++) window.history.back();
    }, times);
    await page.waitForTimeout(FAST_ACTION_TIMEOUT);
});

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

