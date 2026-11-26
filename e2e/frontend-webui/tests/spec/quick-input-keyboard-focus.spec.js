import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getRecordData, getTabRows } from '../utils/WebAPIValidation';
import {
  installDocumentEventRecorder,
  pickCustomerByKeyboard,
  pickCustomerByMouse,
  pickCustomerAndSettle,
  enterLinesKeyboardOnly,
  closeQuickInputAndComplete,
  expectFocusOn,
} from '../utils/QuickInputFocus';

/**
 * Sales Order quick entry (batch entry) opened with Alt+Q: the cursor must stay in the quick
 * entry while lines are entered by keyboard only (article, Enter, quantity, Enter, ...).
 * A focus check only counts once the order header reload triggered by the step has happened.
 */

const ORDER_LINE_TAB_ID = 'AD_Tab-187';
const PRODUCT_KEYS = ['P1', 'P2', 'P3'];
const QTYS = [7, 8, 9];

async function createMasterdata(language, { packingInstructionsField, customers = { CUSTOMER1: {} } } = {}) {
  const products = {};
  PRODUCT_KEYS.forEach((key) => {
    products[key] = {
      name: `QIKF${key}`,
      valuePrefix: 'QIKF',
      type: 'Item',
      prices: [{ price: 10.0, currencyCode: 'EUR' }],
    };
  });
  const bpartners = {};
  Object.entries(customers).forEach(([key, extra]) => {
    bpartners[key] = {
      isVendor: false,
      isCustomer: true,
      isSoPriceList: true,
      name: 'Customer',
      ...extra,
    };
  });
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'QIKF', lastname: 'Test' } },
      bpartners,
      products,
      sysconfigs: {
        'webui.quickinput.EnablePackingInstructionsField': packingInstructionsField,
      },
    },
  });
}

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

/**
 * One quick entry case: new order, customer picked by keyboard or mouse, quick entry opened with
 * Alt+Q or the button, optionally closed and reopened with Alt+Q, three lines by keyboard only,
 * order completed, DB end state asserted. pickCustomer 'mouseOfSeveral': two customers share the typed
 * prefix, the first one is picked by mouse from the option list.
 */
async function runQuickEntryCase(page, { language, packingInstructionsField, pickCustomer, openBy, reopen = false }) {
  allure.epic('E0100: Sales');
  allure.tag('F00101: Sales Order Quick Entry');
  allure.tag('F00101');
  allure.tag('F50000: Frontend WebUI');
  allure.tag('F50000');
  allure.story(`Quick entry opened with ${openBy === 'altQ' ? 'Alt+Q' : 'the button'} keeps the cursor, keyboard-only line entry`);
  allure.severity('critical');
  allure.parameter('Language', language);
  allure.parameter('EnablePackingInstructionsField', packingInstructionsField);
  allure.tag(language);
  test.setTimeout(240000);

  const recorder = installDocumentEventRecorder(page);
  // mouseOfSeveral: two customers whose code and name share this run-unique prefix
  const severalPrefix = `QIM${Date.now().toString(36).toUpperCase()}`;
  const masterdata = await createMasterdata(language, {
    packingInstructionsField,
    customers:
      pickCustomer === 'mouseOfSeveral'
        ? {
            CUSTOMER1: { bpartnerCode: `${severalPrefix}A`, name: `${severalPrefix}A` },
            CUSTOMER2: { bpartnerCode: `${severalPrefix}B`, name: `${severalPrefix}B` },
          }
        : { CUSTOMER1: {} },
  });
  allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();
  await Backend.resetWebApiCaches();

  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  const bpartnerCode = masterdata.bpartners.CUSTOMER1.bpartnerCode;
  const pick = () => {
    if (pickCustomer === 'keyboard') return pickCustomerByKeyboard({ bpartnerCode });
    if (pickCustomer === 'mouseOfSeveral') return pickCustomerByMouse({ searchText: severalPrefix, bpartnerCode, minOptions: 2 });
    return pickCustomerByMouse({ searchText: bpartnerCode, bpartnerCode });
  };
  // The button must not be clicked while the header still walks partner -> address -> contact.
  const recordId = openBy === 'button' ? await pickCustomerAndSettle(pick) : await pick();
  const windowId = SALES_ORDER_WINDOW_ID;

  const toggle = page.getByTestId('batch-entry-toggle');
  await toggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  if (openBy === 'altQ') {
    await page.keyboard.press('Alt+Q');
  } else {
    await toggle.click();
  }
  const quickInput = page.locator('.quick-input-container');
  await quickInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const productInput = quickInput.locator('#lookup_M_Product_ID input.input-field');
  await expectFocusOn(productInput, openBy === 'altQ' ? 'opened: cursor in Produkt' : 'opened by button: cursor in Produkt');
  if (reopen) {
    await page.keyboard.press('Alt+Q');
    await quickInput.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
    await page.keyboard.press('Alt+Q');
    await quickInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await expectFocusOn(productInput, 'reopened with Alt+Q: cursor in Produkt');
  }

  await enterLinesKeyboardOnly({
    recorder,
    windowId,
    recordId,
    products: PRODUCT_KEYS.map((k) => masterdata.products[k].productCode),
    qtys: QTYS,
    packingInstructionsField,
  });
  await closeQuickInputAndComplete({ recorder, windowId, recordId });

  const rows = await getTabRows(windowId, recordId, ORDER_LINE_TAB_ID);
  expect(rows).toHaveLength(3);
  QTYS.forEach((q, i) => {
    const v = rows[i].fieldsByName?.QtyEntered?.value;
    expect(v, `QtyEntered of line ${i + 1}`).toBeDefined();
    expect(Number(v)).toBe(q);
  });
  const record = await getRecordData(windowId, recordId);
  expect(record.fieldsByName?.DocStatus?.value?.key).toBe('CO');
}

testCases.forEach(({ language, label }) => {
  test.describe(`Quick entry keyboard focus (${label})`, () => {
    test(`TC1 keyboard, no packing-instruction field (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'keyboard',
        openBy: 'altQ',
      });
    });

    test(`TC2 keyboard, packing-instruction field on (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'Y',
        pickCustomer: 'keyboard',
        openBy: 'altQ',
      });
    });

    test(`TC3 mouse, button, PI off (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'mouse',
        openBy: 'button',
      });
    });

    test(`TC4 mouse, button, PI on (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'Y',
        pickCustomer: 'mouse',
        openBy: 'button',
      });
    });

    test(`TC9 keyboard, Alt+Q, close and reopen with Alt+Q (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'keyboard',
        openBy: 'altQ',
        reopen: true,
      });
    });

    test(`TC12 keyboard partner, button, PI off (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'keyboard',
        openBy: 'button',
      });
    });

    [
      { packingInstructionsField: 'N', piLabel: 'PI off' },
      { packingInstructionsField: 'Y', piLabel: 'PI on' },
    ].forEach(({ packingInstructionsField, piLabel }) => {
      test(`TC10 mouse pick from several, Alt+Q, ${piLabel} (${label})`, async ({ page }) => {
        await runQuickEntryCase(page, {
          language,
          packingInstructionsField,
          pickCustomer: 'mouseOfSeveral',
          openBy: 'altQ',
        });
      });
    });
  });
});
