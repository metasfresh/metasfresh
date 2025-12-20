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
                await allure.epic('E0295: Frontend MobileUI');
                await allure.tag('F12000: Frontend MobileUI');
                await allure.story('Login with language selection');
                await allure.severity('critical');
                await allure.parameter('Language', language);
                await allure.parameter('Auth Method', authMethod);

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
});
