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
      allure.epic('E0193: System Authentication');
      allure.tag('F14000: Username and Password Auth');
      allure.tag('F14000');  // Standalone tag for Tags section
      allure.story('Login with language selection');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
      allure.description(`
## E0193: System Authentication
## F14000: Username and Password Auth

### Test Scenario
Login with language selection and verify dashboard is visible.
      `);

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
