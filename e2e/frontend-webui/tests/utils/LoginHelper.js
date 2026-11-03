/**
 * Login helper for Playwright E2E tests.
 *
 * Handles the login flow using credentials from Backend.createMasterdata() response.
 */

import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from './common';

/**
 * Login to metasfresh WebUI using credentials from masterdata response.
 *
 * @param {import('@playwright/test').Page} page - Playwright page
 * @param {{username: string, password: string}} user - User credentials from masterdata.login.user
 */
export async function loginWithMasterdataUser(page, user) {
  await page.goto(`${FRONTEND_BASE_URL}`);
  await page.waitForTimeout(3000);
  await page.locator('input, [role="textbox"]').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  const inputs = page.locator('input, [role="textbox"]');
  await inputs.nth(0).fill(user.username);
  await inputs.nth(1).fill(user.password);
  await page.locator('.btn-meta-success').click();

  await page.waitForTimeout(2000);
  if (page.url().includes('/login')) {
    const loginBtn = page.locator('.btn-meta-success');
    if (await loginBtn.isVisible()) { await loginBtn.click(); }
  }
  await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
}
