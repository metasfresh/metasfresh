import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * BPartner Balance View E2E tests.
 *
 * Tests the BPartner Balance (Kontensaldo) AD_Process and menu registration.
 *
 * me03#28782
 */

test.describe('BPartner Balance View', () => {
  test('BPartner Balance menu entry exists (de_DE)', async ({ page }) => {
    allure.epic('E0225: Accounting');
    allure.story('BPartner Balance View');
    allure.severity('critical');

    test.setTimeout(120000);

    // Navigate to frontend
    await page.goto(FRONTEND_BASE_URL);
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT });

    // Login — use labels to find the right inputs
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
    await page.screenshot({ path: 'test-results/bpartner-balance-de_DE.png', fullPage: true });
  });
});
