import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { createMasterdata } from '../utils/OrderLineHarness';

/**
 * An Address cell of a grid is edited through a popup, like the attribute cell of an order line.
 *
 * Real-life case: in the business partner (B2C) window, Address tab, the user double-clicks the
 * address cell, clicks the address button and types a city into the popup. The popup and the cell
 * editor stay open while the focus is in the popup. After the popup closes (click outside it) the
 * cell stays in edit mode, showing the new address, until the user leaves it (Escape). The new
 * address is saved.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const BPARTNER_B2C_WINDOW_ID = '540354';
const ADDRESS_TAB_ID = 'tab_Window-540354-AD_Tab-540847';
const ADDRESS_COLUMN = 'C_Location_ID';

test.describe('Grid Address cell — editing through the address popup', () => {
  test('The popup stays open while edited; after it closes the cell stays in edit mode until left; the address is saved', async ({
    page,
  }) => {
    allure.epic('E0100: Business Partners');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid — Address cell popup editing');
    allure.severity('normal');
    test.setTimeout(180000);

    const city = `E2ECity${Date.now()}`;
    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const openAddressTab = async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${BPARTNER_B2C_WINDOW_ID}/${masterdata.bpartners.CUSTOMER1.id}`);
      await page.locator(`li.nav-item#${ADDRESS_TAB_ID}`).click({ timeout: SLOW_ACTION_TIMEOUT });
      const addressCell = page.locator(`table tbody tr td[data-cy="cell-${ADDRESS_COLUMN}"]`).first();
      await addressCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      return addressCell;
    };

    const cell = await openAddressTab();
    const cellEditor = cell.locator('.form-group.widgetType-Address');
    const popup = page.locator('.attributes-dropdown');

    await test.step('Open the address popup from the grid cell', async () => {
      await cell.dblclick();
      await cell.locator('.attributes-in-table button').click();
      await popup.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Type a city into the popup: the popup and the cell editor stay open', async () => {
      const cityInput = popup.locator('.form-field-City input');
      await cityInput.click();
      await cityInput.fill(city);
      await page.keyboard.press('Tab');
      await expect(
        cell.locator('.attributes-in-table button'),
        'the focus left the address button for the popup'
      ).not.toBeFocused();

      await expect(popup, 'the popup stays open while the user edits it').toBeVisible();
      await expect(cellEditor, 'the cell editor stays open while the popup is edited').toHaveCount(1);
    });

    await test.step('Close the popup by clicking outside it: the cell stays in edit mode', async () => {
      const addressSaved = page.waitForResponse(
        (response) =>
          response.request().method() === 'PATCH' &&
          response.url().includes(`/window/${BPARTNER_B2C_WINDOW_ID}/`) &&
          (response.request().postData() || '').includes(ADDRESS_COLUMN),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      // a plain field label of the master form: a neutral spot outside the popup and the grid
      await page.locator('.panel-primary .form-field-Value .form-control-label').click();
      await popup.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      await addressSaved;

      await expect(cellEditor, 'the cell editor stays open after the popup closed').toHaveCount(1);
      await expect(cell.locator('.attributes-in-table button')).toContainText(city);
    });

    await test.step('Escape leaves the cell, which shows the new address', async () => {
      await page.keyboard.press('Escape');
      await expect(cellEditor).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(cell).toContainText(city);
    });

    await test.step('After a reload the cell still shows the new address', async () => {
      const reloadedCell = await openAddressTab();
      await expect(reloadedCell).toContainText(city, { timeout: SLOW_ACTION_TIMEOUT });
    });
  });
});
