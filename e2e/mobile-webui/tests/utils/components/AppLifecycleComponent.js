import { test } from '../../../playwright.config';
import { page } from '../common';

const NAME = 'AppLifecycleComponent';

export const AppLifecycleComponent = {
    // Simulates the operator returning to an app that stayed open — switching back to the installed
    // PWA, unlocking the device, or re-selecting the browser tab. The browser fires exactly these two
    // events on that transition, and there is no Playwright API that produces them, so they are
    // dispatched directly (same reason BarcodeScannerComponent dispatches raw keyboard events to
    // stand in for a hardware scanner).
    returnToForeground: async () => await test.step(`${NAME} - Operator returns to the app`, async () => {
        await page.evaluate(() => {
            document.dispatchEvent(new Event('visibilitychange'));
            window.dispatchEvent(new Event('focus'));
        });
    }),
};
