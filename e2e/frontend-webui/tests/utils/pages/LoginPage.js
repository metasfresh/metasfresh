import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Login page object for the metasfresh web UI.
 * Handles navigation to login, authentication, and verification.
 */
export class LoginPage {
  /**
   * Navigate to the login page.
   */
  static async goto() {
    return await test.step('LoginPage - Navigate to login', async () => {
      const page = getPage();
      await page.goto(`${FRONTEND_BASE_URL}/login`);
      await this.waitForPage();
    });
  }

  /**
   * Wait for the login page to be visible.
   */
  static async waitForPage() {
    return await test.step('LoginPage - Wait for page', async () => {
      const page = getPage();
      await page.locator('.login-container').waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }

  /**
   * Perform login with username and password.
   * @param {Object} credentials
   * @param {string} credentials.username - Username
   * @param {string} credentials.password - Password
   * @returns {Promise<Object>} Login response body
   */
  static async login({ username, password }) {
    return await test.step(`LoginPage - Login as ${username}`, async () => {
      const page = getPage();

      await this.waitForPage();

      // Fill username
      const usernameInput = page.locator('input[name="username"]');
      await usernameInput.waitFor({ state: 'visible' });
      await usernameInput.fill(username);

      // Fill password
      const passwordInput = page.locator('input[name="password"]');
      await passwordInput.fill(password);

      // Wait for login response
      const responsePromise = page.waitForResponse(
        (response) => response.url().includes('/login/authenticate')
      );

      // Click the Login button
      // Note: The button is NOT type="submit", it's a regular button with class "btn-meta-success"
      const submitButton = page.locator('.btn-meta-success');
      await submitButton.click();

      const response = await responsePromise;
      const responseBody = await response.json();

      console.log('Login response:', responseBody);

      // Wait for redirect away from login page
      // Note: url parameter is a URL object, need to convert to string
      await page.waitForURL((url) => !url.toString().includes('/login'), {
        timeout: SLOW_ACTION_TIMEOUT,
      });

      return responseBody;
    });
  }

  /**
   * Verify that the user is logged in (not on login page).
   */
  static async expectLoggedIn() {
    return await test.step('LoginPage - Expect logged in', async () => {
      const page = getPage();
      const url = page.url();
      expect(url).not.toContain('/login');
    });
  }
}
