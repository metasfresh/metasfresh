import { page, VERY_SLOW_ACTION_TIMEOUT } from '../common';
import { test } from '../../../playwright.config';

const NAME = 'ErrorScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('error-screen');

export const ErrorScreen = {
    watchForScreen: (callback) => test.step(`${NAME} - Watching for screen`, async () => {
        return containerElement().waitFor({ timeout: VERY_SLOW_ACTION_TIMEOUT })
            .then(async () => {
                await callback();
            });
    }),
};
