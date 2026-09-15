import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { getPage, SLOW_ACTION_TIMEOUT } from '../utils/common';

const FRONTEND_BASE_URL =
  process.env.FRONTEND_BASE_URL || 'http://localhost:3000';

/**
 * Column Resize test suite for metasfresh web UI.
 * Tests the ability to resize table columns by dragging column header borders.
 *
 * Features tested:
 * - F50000: Resizable Table Columns
 */
test.describe('Column Resize', () => {
  test.beforeEach(async ({ page }) => {
    // Login directly with default credentials
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    await page.locator('.login-container').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });

    await page.locator('input[name="username"]').fill('metasfresh');
    await page.locator('input[name="password"]').fill('metasfresh');
    await page.locator('.btn-meta-success').click();

    // Handle role selection: if a second login step appears, click through it
    try {
      await page.waitForURL((url) => !url.toString().includes('/login'), {
        timeout: 5000,
      });
    } catch {
      // Role selection step - click the confirm button again
      const confirmBtn = page.locator('.btn-meta-success');
      if (await confirmBtn.isVisible()) {
        await confirmBtn.click();
      }
      await page.waitForURL((url) => !url.toString().includes('/login'), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    }
  });

  test('Drag to resize a column in document list', async ({ page }) => {
    allure.epic('E0200: WebUI Table Features');
    allure.tag('F50000: Resizable Table Columns');
    allure.tag('F50000');
    allure.story('Drag-to-resize column width');
    allure.severity('critical');

    // Navigate to Business Partners list view (window 123)
    await page.goto(`${FRONTEND_BASE_URL}/window/123`);

    // Wait for table to load
    await page.locator('.js-table').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // Wait for at least one row
    await page
      .locator('.js-table tbody tr')
      .first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Find the first column header with a resize handle
    const resizeHandle = page.locator('.column-resize-handle').first();
    await expect(resizeHandle).toBeAttached();

    // Get the parent th element's width
    const th = page.locator('thead th:has(.column-resize-handle)').first();
    const initialBox = await th.boundingBox();
    const initialWidth = initialBox.width;
    console.log(`Initial column width: ${initialWidth}px`);

    // Get the resize handle position
    const handleBox = await resizeHandle.boundingBox();
    const startX = handleBox.x + handleBox.width / 2;
    const startY = handleBox.y + handleBox.height / 2;

    // Drag the resize handle 100px to the right
    const dragDistance = 100;
    await page.mouse.move(startX, startY);
    await page.mouse.down();
    await page.mouse.move(startX + dragDistance, startY, { steps: 10 });
    await page.mouse.up();

    await page.waitForTimeout(300);

    // Verify the column width increased
    const newBox = await th.boundingBox();
    const newWidth = newBox.width;
    console.log(`New column width: ${newWidth}px`);

    expect(newWidth).toBeGreaterThan(initialWidth + 50);
    console.log(
      `Column resized from ${initialWidth}px to ${newWidth}px`
    );
  });

  test('Column width persists after page reload', async ({ page }) => {
    allure.epic('E0200: WebUI Table Features');
    allure.tag('F50000: Resizable Table Columns');
    allure.tag('F50000');
    allure.story('Column width persistence');
    allure.severity('critical');

    // Navigate to Business Partners list view
    await page.goto(`${FRONTEND_BASE_URL}/window/123`);

    // Wait for table
    await page.locator('.js-table').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await page
      .locator('.js-table tbody tr')
      .first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Resize a column
    const resizeHandle = page.locator('.column-resize-handle').first();
    const th = page.locator('thead th:has(.column-resize-handle)').first();

    const handleBox = await resizeHandle.boundingBox();
    const startX = handleBox.x + handleBox.width / 2;
    const startY = handleBox.y + handleBox.height / 2;

    await page.mouse.move(startX, startY);
    await page.mouse.down();
    await page.mouse.move(startX + 80, startY, { steps: 10 });
    await page.mouse.up();

    await page.waitForTimeout(300);

    const resizedBox = await th.boundingBox();
    const resizedWidth = resizedBox.width;
    console.log(`Width after resize: ${resizedWidth}px`);

    // Reload the page
    await page.reload();

    // Wait for table to reload
    await page.locator('.js-table').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await page
      .locator('.js-table tbody tr')
      .first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Check width is preserved (5px tolerance)
    const afterReloadBox = await page
      .locator('thead th:has(.column-resize-handle)')
      .first()
      .boundingBox();
    const afterReloadWidth = afterReloadBox.width;
    console.log(`Width after reload: ${afterReloadWidth}px`);

    expect(Math.abs(afterReloadWidth - resizedWidth)).toBeLessThan(5);
    console.log('Column width persisted across page reload');
  });

  test('Double-click resize handle resets column width', async ({ page }) => {
    allure.epic('E0200: WebUI Table Features');
    allure.tag('F50000: Resizable Table Columns');
    allure.tag('F50000');
    allure.story('Double-click to reset column width');
    allure.severity('normal');

    // Navigate to Business Partners list view
    await page.goto(`${FRONTEND_BASE_URL}/window/123`);

    // Wait for table
    await page.locator('.js-table').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await page
      .locator('.js-table tbody tr')
      .first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Capture original width
    const th = page.locator('thead th:has(.column-resize-handle)').first();
    const originalBox = await th.boundingBox();
    const originalWidth = originalBox.width;
    console.log(`Original width: ${originalWidth}px`);

    // Resize the column
    const resizeHandle = page.locator('.column-resize-handle').first();
    const handleBox = await resizeHandle.boundingBox();
    const startX = handleBox.x + handleBox.width / 2;
    const startY = handleBox.y + handleBox.height / 2;

    await page.mouse.move(startX, startY);
    await page.mouse.down();
    await page.mouse.move(startX + 150, startY, { steps: 10 });
    await page.mouse.up();

    await page.waitForTimeout(300);

    const resizedBox = await th.boundingBox();
    expect(resizedBox.width).toBeGreaterThan(originalWidth);
    console.log(`Resized width: ${resizedBox.width}px`);

    // Double-click the resize handle to reset
    const newHandleBox = await resizeHandle.boundingBox();
    await page.mouse.dblclick(
      newHandleBox.x + newHandleBox.width / 2,
      newHandleBox.y + newHandleBox.height / 2
    );

    await page.waitForTimeout(300);

    // Verify width is back near the original (10px tolerance)
    const resetBox = await th.boundingBox();
    console.log(`Width after reset: ${resetBox.width}px`);

    expect(Math.abs(resetBox.width - originalWidth)).toBeLessThan(10);
    console.log('Column width reset by double-click');
  });
});
