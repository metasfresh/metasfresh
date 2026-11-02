import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { BusinessPartnerPage } from '../utils/pages/BusinessPartnerPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ListWidget } from '../utils/widgets/ListWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { getPage, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Partner order type: the business partner window shows the partner's order type
 * (`C_BPartner.SO_DocTypeTarget_ID`, tab "Kunde"), and a new sales order takes it over as its order type.
 *
 * Expected texts are German: the spec logs in as a `de_DE` user and asserts the labels the customer sees
 * (`Auftragsart`, `Vorauskasseauftrag`, `Standardauftrag`, `Warten auf Zahlung`).
 *
 * Fixture: a prepay partner (order type set through the masterdata API's `orderDocSubType: 'PR'`), a plain
 * partner without an order type, and one priced product.
 *
 * TC1 field is shown on tab "Kunde", offers exactly the sales order types, can be set, saved and cleared (AC1)
 * TC2 order for the prepay partner: header type pre-filled "Vorauskasseauftrag", completes as "Warten auf Zahlung" (AC2)
 * TC3 the user may switch the pre-filled type to "Standardauftrag": order completes normally (AC3)
 * TC4 partner without order type: header type is the default, as before (AC4)
 */

const PREPAY_TYPE = 'Vorauskasseauftrag';
const STANDARD_TYPE = 'Standardauftrag';
const WAITING_FOR_PAYMENT = 'Warten auf Zahlung';
const COMPLETED = 'Fertiggestellt';
const FIELD_LABEL = 'Auftragsart';
/** The dropdown's entry for "no value" (de_DE). */
const EMPTY_OPTION = 'leer';

/** The sales order types the field's validation rule offers to the test org (org 001 reads orgs '*' and 001). */
const EXPECTED_PARTNER_TYPE_OPTIONS = ['Auftrag auf Kommission', STANDARD_TYPE, PREPAY_TYPE].sort();

const PARTNER_FIELD = 'SO_DocTypeTarget_ID';
const ORDER_FIELD = 'C_DocTypeTarget_ID';

const createFixture = async () =>
  Backend.createMasterdata({
    request: {
      login: { user: { language: 'de_DE' } },
      bpartners: {
        PREPAY: { isCustomer: true, isSoPriceList: true, orderDocSubType: 'PR' },
        PLAIN: { isCustomer: true, isSoPriceList: true },
      },
      products: {
        PROD: { name: 'PROD', type: 'Item', prices: [{ price: 10.0, currencyCode: 'EUR' }] },
      },
    },
  });

/** The grid cell of the order type column in the (single) row of tab "Kunde", found by the header's position. */
const partnerTypeCell = () => {
  const page = getPage();
  const headers = page.locator('thead th');
  return {
    async locator() {
      await page.locator(`th[data-testid="column-${PARTNER_FIELD}"]`).waitFor({ timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('tbody tr').first().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
      const count = await headers.count();
      for (let i = 0; i < count; i++) {
        if ((await headers.nth(i).getAttribute('data-testid')) === `column-${PARTNER_FIELD}`) {
          return page.locator('tbody tr').first().locator('td').nth(i);
        }
      }
      throw new Error(`column ${PARTNER_FIELD} not found in the grid header`);
    },
  };
};

/** Reloads the page (proves what was stored) and opens tab "Kunde" again. */
const reloadKundeTab = async () => {
  await getPage().reload();
  await getPage()
    .locator(`li.nav-item#${BusinessPartnerPage.TAB_IDS.CUSTOMER}`)
    .waitFor({ timeout: SLOW_ACTION_TIMEOUT });
  await BusinessPartnerPage.clickTab(BusinessPartnerPage.TAB_IDS.CUSTOMER);
};

const openCellEditor = async () => {
  await (await partnerTypeCell().locator()).dblclick();
  await getPage().waitForTimeout(500);
};

/** The text the document status button of the open document shows (e.g. "Warten auf Zahlung"). */
const statusText = async () => (await getPage().getByTestId('status-button').innerText()).trim();

/** Waits until the status button shows `expected`; returns the shown text. */
const expectStatus = async (expected) => {
  await expect
    .poll(statusText, { timeout: SLOW_ACTION_TIMEOUT, message: `document status shows "${expected}"` })
    .toContain(expected);
};

/** New sales order for `partnerCode`: returns the record id once the partner was chosen and saved. */
const newOrderFor = async (partnerCode) => {
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  return SalesOrderPage.selectCustomer(partnerCode);
};

test.describe('Partner order type', () => {
  test.beforeEach(() => {
    allure.epic('E0100: Sales');
    allure.tag('F00100: Sales Order Window');
    allure.tag('F00100');
    allure.tag('F00900: Business Partner');
    allure.tag('F00900');
  });

  // `page` is destructured but not referenced: the page objects read the page the custom fixture in
  // playwright.config.js publishes as `global.currentPage`, and Playwright only runs that fixture for a
  // test that requests `page`.
  // eslint-disable-next-line no-unused-vars
  test('TC1 partner window shows the order type on tab Kunde and offers the sales order types', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    await BusinessPartnerPage.gotoRecord(masterdata.bpartners.PLAIN.id);
    await BusinessPartnerPage.clickTab(BusinessPartnerPage.TAB_IDS.CUSTOMER);

    // The tab "Kunde" is a grid: the field is a column, identified language-independently by its data-testid.
    const header = getPage().locator(`th[data-testid="column-${PARTNER_FIELD}"]`);
    await expect(header, 'order type column is shown on tab Kunde').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(header).toContainText(FIELD_LABEL);

    await openCellEditor();
    await getPage().keyboard.press('ArrowDown');
    await WidgetCommon.waitForDropdown();
    const options = (await getPage().locator('.input-dropdown-list .input-dropdown-list-option').allTextContents())
      .map((option) => option.trim())
      .filter((option) => option !== EMPTY_OPTION)
      .sort();
    expect(options, 'the dropdown offers exactly the sales order types').toEqual(EXPECTED_PARTNER_TYPE_OPTIONS);

    await getPage().locator('.input-dropdown-list-option').getByText(PREPAY_TYPE, { exact: true }).first().click();
    await getPage().keyboard.press('Tab');
    await reloadKundeTab();
    await expect(await partnerTypeCell().locator(), 'the chosen order type is saved').toHaveText(PREPAY_TYPE, {
      timeout: SLOW_ACTION_TIMEOUT,
    });

    await openCellEditor();
    await getPage().keyboard.press('ArrowDown');
    await WidgetCommon.waitForDropdown();
    await getPage().locator('.input-dropdown-list-option').getByText(EMPTY_OPTION, { exact: true }).first().click();
    await getPage().keyboard.press('Tab');
    await reloadKundeTab();
    await expect(await partnerTypeCell().locator(), 'the order type is cleared').toHaveText('', { timeout: SLOW_ACTION_TIMEOUT });
  });

  // eslint-disable-next-line no-unused-vars
  test('TC2 order for the prepay partner is pre-filled with the prepay type and waits for payment', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    const recordId = await newOrderFor(masterdata.bpartners.PREPAY.bpartnerCode);
    await expect(WidgetCommon.getFieldContainer(ORDER_FIELD).locator('input').first()).toHaveValue(PREPAY_TYPE, {
      timeout: SLOW_ACTION_TIMEOUT,
    });

    await SalesOrderPage.addOrderLine({ product: masterdata.products.PROD.productName, quantity: 1, recordId });
    await SalesOrderPage.complete();

    await expectStatus(WAITING_FOR_PAYMENT);
  });

  // eslint-disable-next-line no-unused-vars
  test('TC3 the pre-filled prepay type can be changed to the standard order type', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    const recordId = await newOrderFor(masterdata.bpartners.PREPAY.bpartnerCode);
    await expect(WidgetCommon.getFieldContainer(ORDER_FIELD).locator('input').first()).toHaveValue(PREPAY_TYPE, {
      timeout: SLOW_ACTION_TIMEOUT,
    });

    await ListWidget.setValue(ORDER_FIELD, STANDARD_TYPE, { exactMatch: true });
    await expect(WidgetCommon.getFieldContainer(ORDER_FIELD).locator('input').first()).toHaveValue(STANDARD_TYPE);

    await SalesOrderPage.addOrderLine({ product: masterdata.products.PROD.productName, quantity: 1, recordId });
    await SalesOrderPage.complete();

    await expectStatus(COMPLETED);
    await expect(WidgetCommon.getFieldContainer(ORDER_FIELD).locator('input').first()).toHaveValue(STANDARD_TYPE);
  });

  // eslint-disable-next-line no-unused-vars
  test('TC4 order for a partner without order type keeps the default order type', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    await newOrderFor(masterdata.bpartners.PLAIN.bpartnerCode);

    const headerType = await ListWidget.getValue(ORDER_FIELD);
    expect(headerType, 'default order type, not the prepay type').toBe(STANDARD_TYPE);
  });
});
