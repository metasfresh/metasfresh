import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

// MobileUI Manufacturing Configuration window ID
const MFG_CONFIG_WINDOW_ID = 541788;

test.describe('MobileUI MFG Config Translations', () => {
  //
  // TC-E1: German field names
  //
  test('German field labels in MobileUI Manufacturing Configuration window', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('AD translation verification');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate to MobileUI Manufacturing Configuration window
    await MasterWindowPage.goto(MFG_CONFIG_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // Check column headers in the grid view for German labels
    const tableHeaders = page.locator('.header-item .header-caption, th');
    const headerTexts = await tableHeaders.allTextContents();
    const allHeaderText = headerTexts.join(' | ');
    console.log('Grid headers (de_DE):', allHeaderText);

    // Verify German translations are present (not English)
    // The exact labels depend on which fields are displayed in grid view
    expect(allHeaderText).toContain('Zuteilung beliebiger HU erlauben');
  });

  //
  // TC-E2: English field names
  //
  test('English field labels in MobileUI Manufacturing Configuration window', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('AD translation verification');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate to MobileUI Manufacturing Configuration window
    await MasterWindowPage.goto(MFG_CONFIG_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // Check column headers in the grid view for English labels
    const tableHeaders = page.locator('.header-item .header-caption, th');
    const headerTexts = await tableHeaders.allTextContents();
    const allHeaderText = headerTexts.join(' | ');
    console.log('Grid headers (en_US):', allHeaderText);

    // Verify English translations are present
    expect(allHeaderText).toContain('Allow Issuing Any HU');
  });
});
