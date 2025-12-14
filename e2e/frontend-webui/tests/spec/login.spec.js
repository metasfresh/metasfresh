import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { AllureHelpers } from '../utils/AllureHelpers';

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
      await AllureHelpers.setFeature({
        id: 'F14000',
        name: 'Username and Password Auth',
        epicId: 'E0193',
        epicName: 'System Authentication'
      });
      await AllureHelpers.setStory('Login with language selection');
      await AllureHelpers.setSeverity('critical');
      await AllureHelpers.addParameter('Language', language);
      await AllureHelpers.addTags(language);

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
