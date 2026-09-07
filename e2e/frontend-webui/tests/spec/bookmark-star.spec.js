import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Bookmark Star test suite for metasfresh web UI.
 * Tests that the favorites/bookmark star is always visible in the SubHeader
 * when viewing a window, and that clicking it toggles the bookmark state.
 *
 * PR #22433 removed the hover-only logic: the star was previously only shown
 * on mouse hover or when already bookmarked. Now it is always rendered.
 * The star appears in the SubHeader panel (opened via the actions/more button).
 */
test.describe('Bookmark Star', () => {
  test('Star is always visible and toggles bookmark state', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0193: System Authentication');
    allure.story('Bookmark star always visible in navigation');
    allure.severity('normal');
    allure.description(`
## E0193: System Authentication

### Test Scenario
Verify that the bookmark star is always visible in the SubHeader when viewing
a window, and that clicking the star toggles the bookmark (favorite) state
via PATCH API.
    `);

    // Step 1: Login with default credentials
    await test.step('Login', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/login`);
      await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.locator('input[name="username"]').fill('metasfresh');
      await page.locator('input[name="password"]').fill('metasfresh');
      await page.locator('.btn-meta-success').click();

      // Handle role selection if needed
      await page.waitForTimeout(1000);
      if (page.url().includes('/login')) {
        const sendButton = page.locator('.btn-meta-success');
        if (await sendButton.isVisible()) {
          await sendButton.click();
        }
      }

      // Wait for dashboard
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('.app-content, .dashboard').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    // Step 2: Navigate to a window (Organisation, AD_Window_ID=110)
    await test.step('Navigate to a window', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/110`);
      await page.waitForURL(/\/window\/110/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT });
    });

    // Step 3: Open the SubHeader panel by clicking the actions/more button
    await test.step('Open SubHeader panel', async () => {
      await page.locator('.meta-icon-more').click();
      await page.locator('.subheader-title').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    // Step 4: Assert bookmark star is visible in the SubHeader
    const bookmarkStar = page.locator('.subheader-column .btn-bookmark-icon');

    await test.step('Verify bookmark star is visible', async () => {
      await expect(bookmarkStar).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
      console.log('Bookmark star is visible in SubHeader');
    });

    // Step 5: Click star to toggle bookmark — verify PATCH API call
    await test.step('Click star to toggle bookmark and verify API call', async () => {
      const wasActive = await bookmarkStar.evaluate((el) => el.classList.contains('active'));

      const patchPromise = page.waitForResponse(
        (response) =>
          response.url().includes('/rest/api/menu/node/') &&
          response.request().method() === 'PATCH' &&
          response.status() === 200,
        { timeout: FAST_ACTION_TIMEOUT }
      );

      await bookmarkStar.click();
      await patchPromise;

      if (wasActive) {
        await expect(bookmarkStar).not.toHaveClass(/\bactive\b/, { timeout: FAST_ACTION_TIMEOUT });
        console.log('Star un-bookmarked successfully');
      } else {
        await expect(bookmarkStar).toHaveClass(/\bactive\b/, { timeout: FAST_ACTION_TIMEOUT });
        console.log('Star bookmarked successfully');
      }
    });

    // Step 6: Click again to revert
    await test.step('Click star again to revert and verify API call', async () => {
      const wasActive = await bookmarkStar.evaluate((el) => el.classList.contains('active'));

      const patchPromise = page.waitForResponse(
        (response) =>
          response.url().includes('/rest/api/menu/node/') &&
          response.request().method() === 'PATCH' &&
          response.status() === 200,
        { timeout: FAST_ACTION_TIMEOUT }
      );

      await bookmarkStar.click();
      await patchPromise;

      if (wasActive) {
        await expect(bookmarkStar).not.toHaveClass(/\bactive\b/, { timeout: FAST_ACTION_TIMEOUT });
        console.log('Star reverted to un-bookmarked');
      } else {
        await expect(bookmarkStar).toHaveClass(/\bactive\b/, { timeout: FAST_ACTION_TIMEOUT });
        console.log('Star reverted to bookmarked');
      }
    });
  });
});
