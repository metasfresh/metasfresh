import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { AllureHelpers } from '../utils/AllureHelpers';

// Product window ID in metasfresh
const PRODUCT_WINDOW_ID = 140;

/**
 * Product window test suite.
 * Tests viewing and interacting with the Product master data window.
 *
 * Features tested (from Google Sheets):
 * - F6000: Maintain Product Data
 */
test.describe('Product Window', () => {
  test('View Product window', async ({ page }) => {
    // === ALLURE METADATA ===
    await AllureHelpers.setFeature({
      id: 'F6000',
      name: 'Maintain Product Data',
      epicId: 'E0380',
      epicName: 'Masterdata Products'
    });
    await AllureHelpers.setStory('View product list');
    await AllureHelpers.setSeverity('normal');

    // Create test data: user and products
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        products: {
          PROD1: {
            name: 'Test Product 1',
          },
          PROD2: {
            name: 'Test Product 2',
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
    // === ALLURE METADATA ===
    await AllureHelpers.setFeature({
      id: 'F6000',
      name: 'Maintain Product Data',
      epicId: 'E0380',
      epicName: 'Masterdata Products'
    });
    await AllureHelpers.setStory('Open product detail view');
    await AllureHelpers.setSeverity('normal');

    // Create test data: user and a product
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        products: {
          PROD1: {
            name: 'Test Product Detail',
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
