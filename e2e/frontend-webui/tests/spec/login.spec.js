import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';

/**
 * Login/Logout test suite for metasfresh web UI.
 * Tests authentication functionality with different languages.
 */
test.describe('Login/Logout', () => {
  // Test login with different languages
  ['en_US', 'de_DE'].forEach((language) => {
    test(`Login with ${language} language`, async ({ page }) => {
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
