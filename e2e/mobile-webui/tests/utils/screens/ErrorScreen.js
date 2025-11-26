import { page } from '../common';
import { test } from '../../../playwright.config';

const NAME = 'ErrorScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.getByTestId('error-screen');

export const ErrorScreen = {
    watchForScreen: (callback) => test.step(`${NAME} - Watching for screen`, async () => {
        return containerElement().waitFor({ timeout: 999_000 }) // wait "forever"
            .then(async () => {
                await callback();
            });
    }),
};
