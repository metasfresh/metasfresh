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
   * Handles role selection when multiple roles are available.
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
      let responseBody = await response.json();

      console.log('Login response:', responseBody);

      // Handle role selection if login is not complete
      // When loginComplete=false and roles are provided, user must select a role
      if (responseBody.loginComplete === false && responseBody.roles && responseBody.roles.length > 0) {
        console.log('Role selection required, clicking Send button...');

        // Wait for role selection UI to be visible
        await page.waitForTimeout(500);

        // The role is usually pre-selected, just need to click Send
        const sendButton = page.locator('.btn-meta-success');
        await sendButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        // Wait for the second authenticate response
        const roleResponsePromise = page.waitForResponse(
          (response) => response.url().includes('/login/authenticate')
        );

        await sendButton.click();

        const roleResponse = await roleResponsePromise;
        responseBody = await roleResponse.json();

        console.log('Role selection response:', responseBody);
      }

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
