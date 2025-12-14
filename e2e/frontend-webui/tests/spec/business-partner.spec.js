import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { AllureHelpers } from '../../../common/AllureHelpers';

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
    await AllureHelpers.setFeature({
      id: 'F00900',
      name: 'Business Partner',
      epicId: 'E0390',
      epicName: 'Masterdata Partner'
    });
    await AllureHelpers.setStory('View business partner list');
    await AllureHelpers.setSeverity('normal');

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
    await AllureHelpers.setFeature({
      id: 'F00900',
      name: 'Business Partner',
      epicId: 'E0390',
      epicName: 'Masterdata Partner'
    });
    await AllureHelpers.setStory('Open business partner detail view');
    await AllureHelpers.setSeverity('normal');

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
