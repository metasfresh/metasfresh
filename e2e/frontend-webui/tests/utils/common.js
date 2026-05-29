import { test } from '../../playwright.config';
import { ErrorToast } from './components/ErrorToast';

export const FRONTEND_BASE_URL = process.env.FRONTEND_BASE_URL || 'http://localhost:3000';

export const VERY_FAST_ACTION_TIMEOUT = 1000;    // 1 second
export const FAST_ACTION_TIMEOUT = 5000;         // 5 seconds
export const SLOW_ACTION_TIMEOUT = 20000;        // 20 seconds
export const VERY_SLOW_ACTION_TIMEOUT = 40000;   // 40 seconds

export const getPage = () => global.currentPage;

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
          // Only handle error if this watcher is still active
          if (currentErrorWatcherId !== watcherId) {
            return;
          }

          const textContent = await toastLocator.textContent();
          throw new Error('Unexpected error toast detected: ' + textContent);
        },
        999_000 // Very long timeout - we expect the function to complete first
      ),
    ]);
  } finally {
    currentErrorWatcherId = 0;
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
