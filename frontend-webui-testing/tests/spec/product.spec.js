import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

// Product window ID in metasfresh
const PRODUCT_WINDOW_ID = 140;

/**
 * Product window test suite.
 * Tests viewing and interacting with the Product master data window.
 */
test.describe('Product Window', () => {
  test('View Product window', async ({ page }) => {
    // Create test data: user and products
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        products: {
          PROD1: {
            name: 'Test Product 1',
            value: 'TEST-PROD-001',
          },
          PROD2: {
            name: 'Test Product 2',
            value: 'TEST-PROD-002',
          },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate to Product window
    await MasterWindowPage.goto(PRODUCT_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // Wait for table data to load
    await MasterWindowPage.waitForTableData();

    // Verify at least one row is visible
    const rowCount = await MasterWindowPage.getRowCount();
    expect(rowCount).toBeGreaterThan(0);

    console.log(`Product window loaded with ${rowCount} rows`);
  });

  test('Open Product detail view', async ({ page }) => {
    // Create test data: user and a product
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        products: {
          PROD1: {
            name: 'Test Product Detail',
            value: 'TEST-PROD-DETAIL',
          },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // Navigate to Product window
    await MasterWindowPage.goto(PRODUCT_WINDOW_ID);
    await MasterWindowPage.waitForTableData();

    // Click the first row to open detail view
    await MasterWindowPage.clickRow(0);

    // Verify URL changed to detail view format
    const url = page.url();
    expect(url).toMatch(/\/window\/\d+\/\d+/);

    console.log('Successfully opened Product detail view:', url);
  });
});
