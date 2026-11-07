import {test} from "../../playwright.config";
import { AVAILABLE_AUTH_METHODS, LoginScreen } from "../utils/screens/LoginScreen";
import {ApplicationsListScreen} from "../utils/screens/ApplicationsListScreen";
import {Backend} from "../utils/screens/Backend";
import {expect} from "@playwright/test";
import { allure } from 'allure-playwright';

test.describe('Login/Logout', () => {
    // noinspection JSUnusedLocalSymbols
    ['en_US', 'de_DE'].forEach(language => {
        AVAILABLE_AUTH_METHODS.forEach(authMethod => {
            // noinspection JSUnusedLocalSymbols
            test(`By user/pass, using ${language} language, ${authMethod} as default auth method`, async ({ page }) => {
                // === ALLURE METADATA ===
                allure.epic('E0295: Frontend MobileUI');
                allure.tag('F12000: Frontend MobileUI');
        allure.tag('F12000');  // Standalone tag for Tags section;
                allure.story('Login with language selection');
                allure.severity('critical');
                allure.parameter('Language', language);
                allure.parameter('Auth Method', authMethod);

                const response = await Backend.createMasterdata({
                    request: {
                        mobileConfig: {
                            defaultAuthMethod: authMethod,
                        },
                        login: {
                            user: { language },
                        },
                    }
                });

                const { language: languageActual } = await LoginScreen.login({
                    ...response.login.user,
                    expectDefaultAuthMethod: authMethod,
                });
                expect(languageActual).toBe(language);

                await ApplicationsListScreen.logout();
            });
        });
    })

    test('Logout button visible on small screen height', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0295: Frontend MobileUI');
        allure.tag('F12000: Frontend MobileUI');
        allure.tag('F12000');
        allure.story('Logout button visible on small screen');
        allure.severity('normal');

        // Simulate Zebra MC3300x screen height (~450px)
        await page.setViewportSize({ width: 412, height: 450 });

        const response = await Backend.createMasterdata({
            request: {
                login: {
                    user: { language: 'en_US' },
                },
            }
        });

        await LoginScreen.login(response.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.expectLogoutButtonReachable();
        await ApplicationsListScreen.logout();
    });
});
