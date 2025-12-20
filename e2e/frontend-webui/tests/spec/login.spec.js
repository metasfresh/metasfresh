import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';

/**
 * Login/Logout test suite for metasfresh web UI.
 * Tests authentication functionality with different languages.
 *
 * Features tested (from Google Sheets):
 * - F14000: Username and Password Auth
 */
test.describe('Login/Logout', () => {
  // Test login with different languages
  ['en_US', 'de_DE'].forEach((language) => {
    test(`Login with ${language} language`, async ({ page }) => {
      // === ALLURE METADATA ===
      await allure.epic('E0193: System Authentication');
      await allure.tag('F14000: Username and Password Auth');
      await allure.story('Login with language selection');
      await allure.severity('critical');
      await allure.parameter('Language', language);
      await allure.tag(language);

      // Create test user via backend API
      const response = await Backend.createMasterdata({
        request: {
          login: {
            user: { language },
          },
        },
      });

      const { username, password } = response.login.user;

      // Navigate to login page
      await LoginPage.goto();

      // Perform login
      await LoginPage.login({ username, password });

      // Verify dashboard is visible
      await DashboardPage.expectVisible();

      // Verify user is logged in
      await LoginPage.expectLoggedIn();

      console.log(
        `Successfully logged in as ${username} with language ${language}`
      );
    });
  });
});
