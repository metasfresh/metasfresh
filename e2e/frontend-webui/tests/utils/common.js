import { test } from '../../playwright.config';
import { ErrorToast } from './components/ErrorToast';

export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3000';

export const VERY_FAST_ACTION_TIMEOUT = 1000;    // 1 second
export const FAST_ACTION_TIMEOUT = 5000;         // 5 seconds
export const SLOW_ACTION_TIMEOUT = 20000;        // 20 seconds
export const VERY_SLOW_ACTION_TIMEOUT = 40000;   // 40 seconds

export const getPage = () => global.currentPage;

/**
 * Collect uncaught page errors and console.error entries from `page` for a hard
 * zero-error assertion at the end of a scenario. Attach BEFORE navigating.
 */
export function collectPageErrors(page) {
  const errors = [];
  page.on('pageerror', (err) => errors.push(`pageerror: ${err.message}`));
  page.on('console', (msg) => {
    if (msg.type() === 'error') errors.push(`console.error: ${msg.text()}`);
  });
  return errors;
}

/**
 * Wrap a test step function with automatic error detection.
 * If an error toast appears during execution, the step will fail.
 */
export const step = async (title, func) =>
  await test.step(title, async () => await runAndWatchForErrors(func));

let currentErrorWatcherId = 0;
let nextErrorWatcherId = 100;

/**
 * Execute a function while watching for error toasts.
 * Uses Promise.race to fail immediately if an error toast appears.
 */
const runAndWatchForErrors = async (func) => {
  // If already watching for errors (nested call), just execute
  if (currentErrorWatcherId > 0) {
    return await func();
  }

  const watcherId = ++nextErrorWatcherId;
  currentErrorWatcherId = watcherId;

  try {
    return await Promise.race([
      func(),
      ErrorToast.waitToPopup(
        async (toastLocator) => {
          // The toast belongs to a more deeply nested watcher, not to us. Do NOT return: returning
          // resolves this branch, which settles the enclosing Promise.race and abandons func(), so
          // every remaining step and assertion of the caller is silently skipped and the test passes
          // vacuously. Hang instead, so only func() or a toast that IS ours can settle the race.
          // See e2e/CLAUDE.md "An ownership guard inside a Promise.race branch must hang, never return".
          if (currentErrorWatcherId !== watcherId) {
            await new Promise(() => {});
          }

          const textContent = await toastLocator.textContent();
          throw new Error('Unexpected error toast detected: ' + textContent);
        },
        999_000 // Very long timeout - we expect the function to complete first
      ),
    ]);
  } finally {
    // Release the slot only if we still own it. Promise.race does not cancel the losing branch, so an
    // abandoned branch can reach this finally long after the winner unwound and would otherwise clear
    // a slot a newer owner had claimed.
    if (currentErrorWatcherId === watcherId) {
      currentErrorWatcherId = 0;
    }
  }
};

/**
 * Execute a function expecting an error toast to appear.
 * Fails if no error toast appears.
 */
export const expectErrorToast = async (title, func) =>
  await test.step(title, async () => {
    try {
      await Promise.race([
        func(),
        ErrorToast.waitToPopup(
          async () => {
            // Error toast appeared as expected
            return;
          },
          VERY_SLOW_ACTION_TIMEOUT
        ),
      ]);
      throw new Error('Expected error toast to appear but it did not');
    } catch (error) {
      if (error.message.includes('Expected error toast')) {
        throw error;
      }
      // Error toast appeared, test passes
    }
  });

/**
 * True only for a deliberate evidence-capture run (`UAT_CAPTURE=1 npx playwright test ...`).
 * Unset in normal and CI runs.
 */
export const isUatCapture = () => !!process.env.UAT_CAPTURE && process.env.UAT_CAPTURE !== '0';

/**
 * Keep the current screen painted for `ms` so the video recorder captures a state the test would
 * otherwise leave within a frame or two (an open result list, an entered value, a tooltip).
 * No-op unless UAT_CAPTURE is set, so normal and CI runs keep their full speed.
 * Call it from page objects only, never from a spec.
 */
export const holdForCaptureIfEnabled = async (ms = 1500) => {
  if (!isUatCapture()) {
    return;
  }
  await getPage().waitForTimeout(ms);
};
