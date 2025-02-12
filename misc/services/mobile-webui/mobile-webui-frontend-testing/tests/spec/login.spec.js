import {test} from "../../playwright.config";
import {LoginScreen} from "../utils/screens/LoginScreen";
import {ApplicationsListScreen} from "../utils/screens/ApplicationsListScreen";
import {Backend} from "../utils/screens/Backend";
import {expect} from "@playwright/test";

test.describe('Login/Logout', () => {
    // noinspection JSUnusedLocalSymbols
    ['en_US', 'de_DE'].forEach(language => test(`using ${language} language`, async ({page}) => {
        const response = await Backend.createMasterdata({
            request: {
                login: {
                    user: {language},
                },
            }
        });

        const {language: languageActual} = await LoginScreen.login(response.login.user);
        expect(languageActual).toBe(language);

        await ApplicationsListScreen.logout();
    }))
});
