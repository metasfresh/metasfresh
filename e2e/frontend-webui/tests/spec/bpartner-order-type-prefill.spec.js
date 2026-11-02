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
import {
  getFieldData,
  getTabRows,
  getTabLayout,
  getFieldLabelFromLayout,
} from '../utils/WebAPIValidation';
import { BUSINESS_PARTNER_WINDOW_ID, SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Partner order type: the business partner window shows the partner's order type
 * (`C_BPartner.SO_DocTypeTarget_ID`, tab "Kunde"), and a new sales order takes it over as its order type.
 *
 * Language-independent: nothing is asserted against localized text. Order types are compared by their lookup KEY
 * (`C_BPartner.SO_DocTypeTarget_ID`, `C_Order.C_DocTypeTarget_ID`), the document status by its `DocStatus` key
 * (`WP` / `CO`), both read through the WebAPI. The only caption check is AC1's own label: the expectation is the
 * caption the backend serves in the tab layout for the session language, fetched at runtime.
 *
 * Fixture: a prepay partner (order type set through the masterdata API's `orderDocSubType: 'PR'`), a plain
 * partner without an order type, and one priced product.
 *
 * TC1 field is shown on the customer tab with its layout caption, offers exactly the sales order types, can be set, saved and cleared (AC1)
 * TC2 order for the prepay partner: header type pre-filled with the partner's type, completes with status WP (AC2)
 * TC3 the user may switch the pre-filled type to the standard type: order completes normally, status CO (AC3)
 * TC4 partner without order type: header type is the default, as before (AC4)
 */

/** Number of sales order types the field's validation rule offers to the test org (org 001 reads orgs '*' and 001). */
const EXPECTED_PARTNER_TYPE_OPTION_COUNT = 3;

const WAITING_FOR_PAYMENT = 'WP';
const COMPLETED = 'CO';

const PARTNER_FIELD = 'SO_DocTypeTarget_ID';
const ORDER_FIELD = 'C_DocTypeTarget_ID';
const CUSTOMER_TAB = 'AD_Tab-223'; // tab "Customer" (internal name) of the business partner window

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
  const cell = await partnerTypeCell().locator();
  await cell.dblclick();
  // wait for the cell editor (an input inside the cell) instead of a fixed pause
  await cell.locator('input').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
};

/** The lookup key of an order field (e.g. `C_DocTypeTarget_ID`, `DocStatus`) as the WebAPI serves it. */
const orderFieldKey = async (recordId, fieldName) =>
  (await getFieldData(String(SALES_ORDER_WINDOW_ID), recordId, fieldName)).value?.key ?? null;

/** Waits until the order's `DocStatus` has the key `expected`. */
const expectStatus = async (recordId, expected) => {
  await expect
    .poll(() => orderFieldKey(recordId, 'DocStatus'), {
      timeout: SLOW_ACTION_TIMEOUT,
      message: `DocStatus of order ${recordId} is ${expected}`,
    })
    .toBe(expected);
};

/** Waits until the order's order type (`C_DocTypeTarget_ID`) has the lookup key `expected`. */
const expectOrderType = async (recordId, expected, message) => {
  await expect
    .poll(() => orderFieldKey(recordId, ORDER_FIELD), { timeout: SLOW_ACTION_TIMEOUT, message })
    .toBe(expected);
};

/** The partner's order type (`C_BPartner.SO_DocTypeTarget_ID`) lookup key, read from its customer tab row; null if empty. */
const partnerTypeKey = async (partnerId) => {
  const rows = (await getTabRows(String(BUSINESS_PARTNER_WINDOW_ID), partnerId, CUSTOMER_TAB)) ?? [];
  return rows[0]?.fieldsByName?.[PARTNER_FIELD]?.value?.key ?? null;
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
    allure.epic('E0390: Masterdata Partner');
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

    const prepayKey = await partnerTypeKey(masterdata.bpartners.PREPAY.id);
    expect(prepayKey, 'fixture: the prepay partner carries an order type').not.toBeNull();
    expect(await partnerTypeKey(masterdata.bpartners.PLAIN.id), 'fixture: the plain partner has none').toBeNull();

    await BusinessPartnerPage.gotoRecord(masterdata.bpartners.PLAIN.id);
    await BusinessPartnerPage.clickTab(BusinessPartnerPage.TAB_IDS.CUSTOMER);

    // The tab "Kunde" is a grid: the field is a column, identified language-independently by its data-testid.
    const header = getPage().locator(`th[data-testid="column-${PARTNER_FIELD}"]`);
    await expect(header, 'order type column is shown on tab Kunde').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    // AC1 is the label itself: the expectation is the caption the backend serves for the session language (never a literal).
    const caption = getFieldLabelFromLayout(await getTabLayout(String(BUSINESS_PARTNER_WINDOW_ID), CUSTOMER_TAB), PARTNER_FIELD);
    expect(caption, `the tab layout labels ${PARTNER_FIELD}`).toBeTruthy();
    await expect(header).toContainText(caption);

    await openCellEditor();
    const dropdownLoaded = getPage().waitForResponse(
      (resp) => resp.request().method() === 'GET' && resp.url().includes(`/field/${PARTNER_FIELD}/dropdown`),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    await getPage().keyboard.press('ArrowDown');
    const offeredKeys = ((await (await dropdownLoaded).json()).values || []).map((value) => value.key);
    await WidgetCommon.waitForDropdown();
    expect(offeredKeys, 'the dropdown offers exactly the sales order types').toHaveLength(EXPECTED_PARTNER_TYPE_OPTION_COUNT);
    expect(offeredKeys.map(String), 'the prepay type is offered').toContain(String(prepayKey));

    await getPage().locator(`.input-dropdown-list [data-testid="option-${prepayKey}"]`).first().click();
    await getPage().keyboard.press('Tab');
    await reloadKundeTab();
    await expect
      .poll(() => partnerTypeKey(masterdata.bpartners.PLAIN.id), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the chosen order type is saved',
      })
      .toBe(prepayKey);

    // clear it again: the dropdown appends the "no value" entry (key null) last (RawList.js)
    await openCellEditor();
    await getPage().keyboard.press('ArrowDown');
    await WidgetCommon.waitForDropdown();
    await getPage().locator('.input-dropdown-list .input-dropdown-list-option').last().click();
    await getPage().keyboard.press('Tab');
    await reloadKundeTab();
    await expect
      .poll(() => partnerTypeKey(masterdata.bpartners.PLAIN.id), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the order type is cleared',
      })
      .toBeNull();
  });

  // eslint-disable-next-line no-unused-vars
  test('TC2 order for the prepay partner is pre-filled with the prepay type and waits for payment', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    const prepayKey = await partnerTypeKey(masterdata.bpartners.PREPAY.id);
    expect(prepayKey, 'fixture: the prepay partner carries an order type').not.toBeNull();

    const recordId = await newOrderFor(masterdata.bpartners.PREPAY.bpartnerCode);
    await expectOrderType(recordId, prepayKey, "order type is pre-filled with the partner's order type");

    await SalesOrderPage.addOrderLine({ product: masterdata.products.PROD.productName, quantity: 1, recordId });
    await SalesOrderPage.complete();

    await expectStatus(recordId, WAITING_FOR_PAYMENT);
    expect(await orderFieldKey(recordId, ORDER_FIELD), 'completion keeps the order type').toBe(prepayKey);
  });

  // eslint-disable-next-line no-unused-vars
  test('TC3 the pre-filled prepay type can be changed to the standard order type', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    const prepayKey = await partnerTypeKey(masterdata.bpartners.PREPAY.id);
    expect(prepayKey, 'fixture: the prepay partner carries an order type').not.toBeNull();

    // the standard type = the type an order of a partner without order type gets (AC4 default)
    const standardKey = await orderFieldKey(await newOrderFor(masterdata.bpartners.PLAIN.bpartnerCode), ORDER_FIELD);
    expect(standardKey, 'the default order type').not.toBeNull();
    expect(standardKey, 'the default differs from the prepay type').not.toBe(prepayKey);

    const recordId = await newOrderFor(masterdata.bpartners.PREPAY.bpartnerCode);
    await expectOrderType(recordId, prepayKey, "order type is pre-filled with the partner's order type");

    await ListWidget.setByValue(ORDER_FIELD, standardKey);
    await expectOrderType(recordId, standardKey, 'the user switched the order type');

    await SalesOrderPage.addOrderLine({ product: masterdata.products.PROD.productName, quantity: 1, recordId });
    await SalesOrderPage.complete();

    await expectStatus(recordId, COMPLETED);
    expect(await orderFieldKey(recordId, ORDER_FIELD), 'completion keeps the chosen order type').toBe(standardKey);
  });

  // eslint-disable-next-line no-unused-vars
  test('TC4 order for a partner without order type keeps the default order type', async ({ page }) => {
    const masterdata = await createFixture();

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    const prepayKey = await partnerTypeKey(masterdata.bpartners.PREPAY.id);
    expect(prepayKey, 'fixture: the prepay partner carries an order type').not.toBeNull();

    const recordId = await newOrderFor(masterdata.bpartners.PLAIN.bpartnerCode);
    const headerType = await orderFieldKey(recordId, ORDER_FIELD);
    expect(headerType, 'an order type is set').not.toBeNull();
    expect(headerType, 'default order type, not the prepay type').not.toBe(prepayKey);
  });
});
