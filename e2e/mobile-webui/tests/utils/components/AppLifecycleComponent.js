import { test } from '../../../playwright.config';
import { page } from '../common';

const NAME = 'AppLifecycleComponent';

export const AppLifecycleComponent = {
    // Simulates the operator switching away from an app that stays open and then coming back to it —
    // to the installed PWA and back, locking and unlocking the device, or re-selecting the browser
    // tab. The browser fires `blur` on the way out, then both `visibilitychange` and `focus` on the
    // way back in. There is no Playwright API that produces them, so they are dispatched directly
    // (same reason BarcodeScannerComponent dispatches raw keyboard events to stand in for a hardware
    // scanner). Both return events are sent because a real return fires both, and the app must treat
    // the pair as one return rather than reloading twice.
    leaveAndReturnToForeground: async () => await test.step(`${NAME} - Operator leaves the app and comes back`, async () => {
        await page.evaluate(() => {
            window.dispatchEvent(new Event('blur'));
            document.dispatchEvent(new Event('visibilitychange'));
            window.dispatchEvent(new Event('focus'));
        });
    }),
};
