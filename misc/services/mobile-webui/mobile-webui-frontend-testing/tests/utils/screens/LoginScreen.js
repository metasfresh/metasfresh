import {FRONTEND_BASE_URL, page, SLOW_ACTION_TIMEOUT} from "../common";
import {test} from "../../../playwright.config";
import {ApplicationsListScreen} from "./ApplicationsListScreen";

export const LoginScreen = {
    waitForScreen: async () => await test.step(`Wait for Login screen`, async () => {
        await page.locator('#LoginScreen').waitFor({timeout: SLOW_ACTION_TIMEOUT});
        await page.locator('.loading').waitFor({state: 'detached', timeout: SLOW_ACTION_TIMEOUT});
    }),

    login: async ({username, password}) => await test.step(`Login with user ${username}`, async () => {
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
        await switchToUserPasswordPanel();

        await page.locator('#username').fill(username);
        await page.locator('#current-password').fill(password);
        await page.locator('button[type="submit"]').tap();

        await ApplicationsListScreen.waitForScreen();

        return {authToken, language};
    })
};


const switchToUserPasswordPanel = async () => {
    if (await page.locator('#password-auth').isVisible()) {
        // already there
    } else if (await page.locator('#qr-code-auth').isVisible()) {
        await page.locator('.alternative-button').click();
    } else {
        throw "Unknown authentication method";
    }

    page.locator('#password-auth').waitFor({timeout: SLOW_ACTION_TIMEOUT});
}