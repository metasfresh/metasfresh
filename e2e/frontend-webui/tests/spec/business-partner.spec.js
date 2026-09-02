import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

// Business Partner window ID in metasfresh
const BUSINESS_PARTNER_WINDOW_ID = 123;

/**
 * Business Partner window test suite.
 * Tests viewing and interacting with the Business Partner master data window.
 *
 * Features tested (from Google Sheets):
 * - F00900: Business Partner
 */
test.describe('Business Partner Window', () => {
  test('View Business Partner window', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0390: Masterdata Partner');
    allure.tag('F00900: Business Partner');
    allure.tag('F00900');  // Standalone tag for Tags section
    allure.story('View business partner list');
    allure.severity('normal');
    allure.description(`
## E0390: Masterdata Partner
## F00900: Business Partner

### Test Scenario
View business partner list in master data window.
    `);

    // Create test data: user and business partners
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        bpartners: {
          BP1: { name: 'Test Partner 1' },
          BP2: { name: 'Test Partner 2' },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate to Business Partner window
    await MasterWindowPage.goto(BUSINESS_PARTNER_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // Wait for table data to load
    await MasterWindowPage.waitForTableData();

    // Verify at least one row is visible
    const rowCount = await MasterWindowPage.getRowCount();
    expect(rowCount).toBeGreaterThan(0);

    console.log(`Business Partner window loaded with ${rowCount} rows`);
  });

  test('Open Business Partner detail view', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0390: Masterdata Partner');
    allure.tag('F00900: Business Partner');
    allure.tag('F00900');  // Standalone tag for Tags section
    allure.story('Open business partner detail view');
    allure.severity('normal');
    allure.description(`
## E0390: Masterdata Partner
## F00900: Business Partner

### Test Scenario
Open business partner detail view from list.
    `);

    // Create test data: user and a business partner
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        bpartners: {
          BP1: { name: 'Test Partner Detail' },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // Navigate to Business Partner window
    await MasterWindowPage.goto(BUSINESS_PARTNER_WINDOW_ID);
    await MasterWindowPage.waitForTableData();

    // Click the first row to open detail view
    await MasterWindowPage.clickRow(0);

    // Verify URL changed to detail view format
    const url = page.url();
    expect(url).toMatch(/\/window\/\d+\/\d+/);

    console.log('Successfully opened Business Partner detail view:', url);
  });
});
