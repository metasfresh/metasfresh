import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MaterialReceiptPage } from '../utils/pages/MaterialReceiptPage';

/**
 * Material Receipt E2E test suite.
 *
 * NOTE: Material Receipt works differently than Purchase Order:
 * - NO batch entry toggle
 * - NO "Create From" action button
 * - NO "Receive All" action button
 * - Tabs use format: tab-AD_Tab-{id}, not tab-{tableName}
 *
 * This test validates basic Material Receipt window functionality and
 * language-independent selectors.
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Material Receipt (${label})`, () => {
    test(`Open and navigate Material Receipt window (${label} UI)`, async ({ page }) => {
      // Create test data with specified language
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
            },
          },
          bpartners: {
            VENDOR1: {
              isVendor: true,
              isCustomer: false,
              isSoPriceList: false, // Purchase price list
            },
          },
        },
      });

      // Login with the user (UI will be in specified language)
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // Navigate to Material Receipt window
      await MaterialReceiptPage.goto();
      await MaterialReceiptPage.clickNew();

      // Select business partner (language-independent selector)
      await MaterialReceiptPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);

      // Navigate to Receipt Line tab (language-independent selector: tab-AD_Tab-297)
      await MaterialReceiptPage.goToReceiptLineTab();

      console.log(`✓ Material Receipt window opened and navigated successfully (${label} UI)`);
    });
  });
});
