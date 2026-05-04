import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

// Replenishment window ("Wiederauffüllung")
const REPLENISHMENT_WINDOW_ID = 541869;

test.describe('Replenishment window — QtyATP column (gh28902)', () => {
  //
  // Verifies that the new QtyATP column added in gh28902 appears in the grid
  // of the Replenishment window's "Materialbedarf" tab, with the correct
  // German and English labels.
  //
  test('German label "Verfügbare Menge" is shown in the grid', async ({ page }) => {
    // TODO ask dev for correct Allure epic + feature ID before merge
    allure.epic('TODO: confirm with dev');
    allure.tag('TODO: confirm with dev');
    allure.story('gh28902: Show ATP in Replenishment window');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(REPLENISHMENT_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    // The QtyATP column may be off-screen (high SeqNoGrid). Use count() to
    // verify presence regardless of scroll position.
    const qtyAtpColumn = page.locator('th[data-testid="column-QtyATP"]');
    expect(await qtyAtpColumn.count()).toBeGreaterThan(0);

    const headerTexts = await page.locator('th').allTextContents();
    const allHeaderText = headerTexts.join(' | ');
    console.log('Replenishment grid headers (de_DE):', allHeaderText);
    expect(allHeaderText).toContain('Verfügbare Menge');
    expect(allHeaderText).toContain('Lagerbestand');
  });

  test('English label "Available Qty" is shown in the grid', async ({ page }) => {
    // TODO ask dev for correct Allure epic + feature ID before merge
    allure.epic('TODO: confirm with dev');
    allure.tag('TODO: confirm with dev');
    allure.story('gh28902: Show ATP in Replenishment window');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await MasterWindowPage.goto(REPLENISHMENT_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();

    const qtyAtpColumn = page.locator('th[data-testid="column-QtyATP"]');
    expect(await qtyAtpColumn.count()).toBeGreaterThan(0);

    const headerTexts = await page.locator('th').allTextContents();
    const allHeaderText = headerTexts.join(' | ');
    console.log('Replenishment grid headers (en_US):', allHeaderText);
    expect(allHeaderText).toContain('Available Qty');
    expect(allHeaderText).toContain('Onhand Quantity');
  });
});
