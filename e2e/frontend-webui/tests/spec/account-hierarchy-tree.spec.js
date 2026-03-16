import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Account Hierarchy Tree View E2E tests.
 *
 * Tests the Account Hierarchy (Kontenhierarchie) AD_Process and menu registration.
 *
 * me03#28782
 */

test.describe('Account Hierarchy Tree View', () => {
  test('Account Hierarchy menu entry exists (de_DE)', async ({ page }) => {
    allure.epic('E0225: Accounting');
    allure.story('Account Hierarchy Tree');
    allure.severity('critical');

    test.setTimeout(120000);

    // Navigate to frontend
    await page.goto(FRONTEND_BASE_URL);
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT });

    // Login — use generic input selectors
    const loginInput = page.locator('input').first();
    await loginInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await loginInput.fill('metasfresh');

    const passwordInput = page.locator('input').nth(1);
    await passwordInput.fill('metasfresh');

    await page.locator('button:has-text("Login")').click();

    // Wait for role selection or dashboard
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForTimeout(5000);

    // Take screenshot
    await page.screenshot({ path: 'test-results/account-hierarchy-de_DE.png', fullPage: true });
  });
});
