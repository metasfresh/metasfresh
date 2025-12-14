import {test} from "../../playwright.config";
import { AVAILABLE_AUTH_METHODS, LoginScreen } from "../utils/screens/LoginScreen";
import {ApplicationsListScreen} from "../utils/screens/ApplicationsListScreen";
import {Backend} from "../utils/screens/Backend";
import {expect} from "@playwright/test";
import { AllureHelpers } from '../../../common/AllureHelpers';

test.describe('Login/Logout', () => {
    // noinspection JSUnusedLocalSymbols
    ['en_US', 'de_DE'].forEach(language => {
        AVAILABLE_AUTH_METHODS.forEach(authMethod => {
            // noinspection JSUnusedLocalSymbols
            test(`By user/pass, using ${language} language, ${authMethod} as default auth method`, async ({ page }) => {
                // === ALLURE METADATA ===
                await AllureHelpers.setFeature({
                    id: 'F12000',
                    name: 'Frontend MobileUI',
                    epicId: 'E0295',
                    epicName: 'Frontend MobileUI'
                });
                await AllureHelpers.setStory('Login with language selection');
                await AllureHelpers.setSeverity('critical');
                await AllureHelpers.addParameter('Language', language);
                await AllureHelpers.addParameter('Auth Method', authMethod);

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
