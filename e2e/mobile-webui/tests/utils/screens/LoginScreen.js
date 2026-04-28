import { FRONTEND_BASE_URL, page, SLOW_ACTION_TIMEOUT } from "../common";
import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "./ApplicationsListScreen";
import { expect } from '@playwright/test';

export const AUTH_METHOD_QR_Code = 'QR_Code';
export const AUTH_METHOD_UserPass = 'UserPass';
export const AVAILABLE_AUTH_METHODS = [AUTH_METHOD_QR_Code, AUTH_METHOD_UserPass];

const NAME = 'LoginScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#LoginScreen');

export const LoginScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    login: async ({ username, password, expectDefaultAuthMethod }) => await test.step(`${NAME} - Login with user ${username}`, async () => {
        let authToken = null;
        let language = null;

        page.on('response', async (response) => {
            if (response.url().includes('/api/v2/auth')) { // Match the specific API
                const jsonResponse = await response.json();
                authToken = jsonResponse.token;
                language = jsonResponse.language;
                console.log(`Intercepted authentication token: ${authToken} from login response`, jsonResponse);
            }
        });

        await page.goto(FRONTEND_BASE_URL);
        await LoginScreen.waitForScreen();

        const currentAuthMethod = await getCurrentAuthMethod();
        if (expectDefaultAuthMethod != null) {
            expect(currentAuthMethod).toBe(expectDefaultAuthMethod);
        }
        if (currentAuthMethod !== AUTH_METHOD_UserPass) {
            await page.locator('.alternative-button').click();
        }
        await page.locator('#password-auth').waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        await page.locator('#username').fill(username);
        await page.locator('#current-password').fill(password);
        await page.locator('button[type="submit"]').tap();

        await ApplicationsListScreen.waitForScreen();

        return { authToken, language };
    })
};

const getCurrentAuthMethod = async () => {
    if (await page.locator('#password-auth').isVisible()) {
        return AUTH_METHOD_UserPass;
    } else if (await page.locator('#qr-code-auth').isVisible()) {
        return AUTH_METHOD_QR_Code;
    } else {
        throw "Unknown authentication method";
    }
}