import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Dashboard/home page object for the metasfresh web UI.
 * Represents the main page shown after successful login.
 */
export class DashboardPage {
  /**
   * Wait for the dashboard page to be visible.
   * Waits for main content container and network idle state.
   */
  static async waitForPage() {
    return await test.step('DashboardPage - Wait for page', async () => {
      const page = getPage();

      // Wait for main content container
      await page
        .locator('.app-content, .dashboard')
        .waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

      // Wait for network to settle
      await page.waitForLoadState('networkidle', {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }

  /**
   * Verify that the dashboard is visible and user is logged in.
   */
  static async expectVisible() {
    return await test.step('DashboardPage - Expect visible', async () => {
      await this.waitForPage();
      const page = getPage();

      // Verify not on login page
      const url = page.url();
      expect(url).not.toContain('/login');

      // Verify dashboard content is visible
      await expect(page.locator('.app-content, .dashboard')).toBeVisible();
    });
  }
}
