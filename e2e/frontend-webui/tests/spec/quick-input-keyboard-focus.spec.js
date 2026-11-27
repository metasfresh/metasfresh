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
  expectFocusHeld,
  describeActiveElement,
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

function tagAllure({ language, packingInstructionsField, story }) {
  allure.epic('E0100: Sales');
  allure.tag('F00101: Sales Order Quick Entry');
  allure.tag('F00101');
  allure.tag('F50000: Frontend WebUI');
  allure.tag('F50000');
  allure.story(story);
  allure.severity('critical');
  allure.parameter('Language', language);
  allure.parameter('EnablePackingInstructionsField', packingInstructionsField);
  allure.tag(language);
  test.setTimeout(240000);
}

/** Masterdata, login and a fresh sales order window (before "New"). Returns the recorder and the masterdata. */
async function loginForSalesOrder({ language, packingInstructionsField, customers }) {
  const recorder = installDocumentEventRecorder(global.currentPage);
  const masterdata = await createMasterdata(language, { packingInstructionsField, customers });
  allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();
  await Backend.resetWebApiCaches();
  return { recorder, masterdata };
}

/**
 * One quick entry case: new order, customer picked by keyboard or mouse, quick entry opened with
 * Alt+Q or the button, optionally closed and reopened with Alt+Q, three lines by keyboard only,
 * order completed, DB end state asserted. pickCustomer 'mouseOfSeveral': two customers share the typed
 * prefix, the first one is picked by mouse from the option list.
 */
async function runQuickEntryCase(page, { language, packingInstructionsField, pickCustomer, openBy, shouldReopen = false }) {
  tagAllure({
    language,
    packingInstructionsField,
    story: `Quick entry opened with ${openBy === 'altQ' ? 'Alt+Q' : 'the button'} keeps the cursor, keyboard-only line entry`,
  });

  // mouseOfSeveral: two customers whose code and name share this run-unique prefix
  const severalPrefix = `QIM${Date.now().toString(36).toUpperCase()}`;
  const { recorder, masterdata } = await loginForSalesOrder({
    language,
    packingInstructionsField,
    customers:
      pickCustomer === 'mouseOfSeveral'
        ? {
            CUSTOMER1: { bpartnerCode: `${severalPrefix}A`, name: `${severalPrefix}A` },
            CUSTOMER2: { bpartnerCode: `${severalPrefix}B`, name: `${severalPrefix}B` },
          }
        : { CUSTOMER1: {} },
  });

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
  if (shouldReopen) {
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

  await expectRowsAndCompleted(windowId, recordId);
}

// Customer with two addresses and two contacts: the header walks partner -> address -> contact and can
// leave the address (and contact) dropdown open. Address captions start with the city: Bonn (B), Kiel (K);
// the typed characters of the product code QIKF_... include K, never B.
const MULTI_CUSTOMER = {
  locations: {
    LOC1: { city: 'Bonn', postal: '53111', address1: 'A 1', countryCode: 'DE' },
    LOC2: { city: 'Kiel', postal: '24103', address1: 'B 2', countryCode: 'DE' },
  },
  contacts: {
    C1: { firstName: 'Anna', lastName: 'One' },
    C2: { firstName: 'Ben', lastName: 'Two' },
  },
};
const OPEN_DROPDOWN_TIMEOUT = 5000;

async function waitForOpenDropdown(page, column, timeout = OPEN_DROPDOWN_TIMEOUT) {
  try {
    await page.locator(`#lookup_${column} .input-dropdown-container.opened`).waitFor({ state: 'attached', timeout });
    return true;
  } catch (error) {
    return false;
  }
}

const isDropdownOpen = (page, column) =>
  page
    .locator(`#lookup_${column} .input-dropdown-container.opened`)
    .count()
    .then((n) => n > 0);

/** Login, new order, MULTI customer picked by keyboard. Returns what the pick left open (observed, 5 s each). */
async function startMultiCustomerOrder(page, { language, story }) {
  tagAllure({ language, packingInstructionsField: 'N', story });
  const { recorder, masterdata } = await loginForSalesOrder({
    language,
    packingInstructionsField: 'N',
    customers: { CUSTOMER1: MULTI_CUSTOMER },
  });
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  const recordId = await pickCustomerByKeyboard({ bpartnerCode: masterdata.bpartners.CUSTOMER1.bpartnerCode });
  const isAddressOpen = await waitForOpenDropdown(page, 'C_BPartner_Location_ID');
  console.log(`[INFO] address dropdown opened by itself after keyboard pick: ${isAddressOpen ? 'yes' : 'no'}`);
  return { recorder, masterdata, recordId, isAddressOpen };
}

/** The same open-address-dropdown setup for the Alt+Q-with-open-address-dropdown test and the Tab test: open it with ArrowDown if it did not open by itself. */
async function ensureAddressDropdownOpen(page, isAddressOpen) {
  if (isAddressOpen) return;
  console.log('[WARN] address dropdown did not open after keyboard pick; opening with ArrowDown');
  await page.keyboard.press('ArrowDown');
  await page
    .locator('#lookup_C_BPartner_Location_ID .input-dropdown-container.opened')
    .waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
}

async function readHeaderAddressKey(windowId, recordId) {
  let key;
  await expect
    .poll(
      async () => {
        key = (await getRecordData(windowId, recordId)).fieldsByName?.C_BPartner_Location_ID?.value?.key;
        return key;
      },
      { timeout: SLOW_ACTION_TIMEOUT, message: 'header C_BPartner_Location_ID has a value' },
    )
    .toBeDefined();
  return key;
}

async function expectHeaderAddress(page, windowId, recordId, before) {
  const after = await readHeaderAddressKey(windowId, recordId);
  expect(after, `header address changed: before=${before} after=${after} (${await describeActiveElement(page)})`).toBe(before);
}

async function openQuickEntryByAltQ(page) {
  await page.keyboard.press('Alt+Q');
  const quickInput = page.locator('.quick-input-container');
  await quickInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const productInput = quickInput.locator('#lookup_M_Product_ID input.input-field');
  return { quickInput, productInput };
}

async function expectRowsAndCompleted(windowId, recordId) {
  const rows = await getTabRows(windowId, recordId, ORDER_LINE_TAB_ID);
  expect(rows).toHaveLength(3);
  QTYS.forEach((q, i) => expect(Number(rows[i].fieldsByName?.QtyEntered?.value), `QtyEntered of line ${i + 1}`).toBe(q));
  const record = await getRecordData(windowId, recordId);
  expect(record.fieldsByName?.DocStatus?.value?.key).toBe('CO');
}

const products = (masterdata) => PRODUCT_KEYS.map((k) => masterdata.products[k].productCode);

testCases.forEach(({ language, label }) => {
  test.describe(`Quick entry keyboard focus (${label})`, () => {
    test(`keyboard, no packing-instruction field (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'keyboard',
        openBy: 'altQ',
      });
    });

    test(`keyboard, packing-instruction field on (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'Y',
        pickCustomer: 'keyboard',
        openBy: 'altQ',
      });
    });

    test(`mouse, button, PI off (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'mouse',
        openBy: 'button',
      });
    });

    test(`mouse, button, PI on (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'Y',
        pickCustomer: 'mouse',
        openBy: 'button',
      });
    });

    test(`keyboard, Alt+Q, close and reopen with Alt+Q (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'keyboard',
        openBy: 'altQ',
        shouldReopen: true,
      });
    });

    test(`keyboard partner, button, PI off (${label})`, async ({ page }) => {
      await runQuickEntryCase(page, {
        language,
        packingInstructionsField: 'N',
        pickCustomer: 'keyboard',
        openBy: 'button',
      });
    });

    test(`keyboard partner with two addresses and contacts, take what the header offers, Alt+Q (${label})`, async ({ page }) => {
      const { recorder, masterdata, recordId, isAddressOpen } = await startMultiCustomerOrder(page, {
        language,
        story: 'Quick entry opened with Alt+Q after a keyboard pick of a customer with two addresses and two contacts',
      });
      const windowId = SALES_ORDER_WINDOW_ID;
      if (isAddressOpen) {
        await page.keyboard.press('Enter'); // takes the highlighted address
        const isContactOpen = await waitForOpenDropdown(page, 'AD_User_ID');
        console.log(`[INFO] contact dropdown opened after the address Enter: ${isContactOpen ? 'yes' : 'no'}`);
        if (isContactOpen) await page.keyboard.press('Enter');
      } else {
        const isContactOpen = await isDropdownOpen(page, 'AD_User_ID');
        console.log(`[INFO] contact dropdown open without an address pick: ${isContactOpen ? 'yes' : 'no'}`);
        if (isContactOpen) await page.keyboard.press('Enter');
      }

      const { productInput } = await openQuickEntryByAltQ(page);
      await expectFocusOn(productInput, 'opened: cursor in Produkt');
      await enterLinesKeyboardOnly({
        recorder,
        windowId,
        recordId,
        products: products(masterdata),
        qtys: QTYS,
        packingInstructionsField: 'N',
      });
      await closeQuickInputAndComplete({ recorder, windowId, recordId });
      await expectRowsAndCompleted(windowId, recordId);
    });

    test(`Alt+Q with the address dropdown open, the pick must not change (${label})`, async ({ page }) => {
      const { recorder, masterdata, recordId, isAddressOpen } = await startMultiCustomerOrder(page, {
        language,
        story: 'Alt+Q with the header address dropdown open keeps the address and the cursor in the quick entry',
      });
      const windowId = SALES_ORDER_WINDOW_ID;
      await ensureAddressDropdownOpen(page, isAddressOpen);
      const before = await readHeaderAddressKey(windowId, recordId);
      const beforeCaption = (await getRecordData(windowId, recordId)).fieldsByName?.C_BPartner_Location_ID?.value?.caption;
      expect(beforeCaption, `setup: header address before Alt+Q is not LOC1: ${beforeCaption}`).toContain('Bonn');
      const highlighted = await page
        .locator('.input-dropdown-list .input-dropdown-list-option-key-on')
        .first()
        .innerText({ timeout: SLOW_ACTION_TIMEOUT });
      expect(highlighted, `setup: highlighted option is not LOC1: ${highlighted}`).toContain('Bonn');

      const { productInput } = await openQuickEntryByAltQ(page);
      await expectFocusOn(productInput, 'opened: cursor in Produkt');
      const prods = products(masterdata);
      const common = { recorder, windowId, recordId, packingInstructionsField: 'N' };
      await enterLinesKeyboardOnly({ ...common, products: prods.slice(0, 1), qtys: QTYS.slice(0, 1) });
      await expectHeaderAddress(page, windowId, recordId, before);
      await enterLinesKeyboardOnly({ ...common, products: prods.slice(1), qtys: QTYS.slice(1), rowsBefore: 1 });
      await closeQuickInputAndComplete({ recorder, windowId, recordId });
      await expectHeaderAddress(page, windowId, recordId, before);
      await expectRowsAndCompleted(windowId, recordId);
    });

    test(`Tab after Alt+Q leaves the header, not swallowed (${label})`, async ({ page }) => {
      const { isAddressOpen } = await startMultiCustomerOrder(page, {
        language,
        story: 'A Tab in the quick entry is not swallowed by a header dropdown left open',
      });
      await ensureAddressDropdownOpen(page, isAddressOpen);

      const { productInput } = await openQuickEntryByAltQ(page);
      await expectFocusOn(productInput, 'opened: cursor in Produkt');
      const isAddressDropdownOpen = await isDropdownOpen(page, 'C_BPartner_Location_ID');
      console.log(`[INFO] address dropdown open at Tab: ${isAddressDropdownOpen ? 'yes' : 'no'}`);
      await page.keyboard.press('Tab');

      const where = await page.evaluate(() => {
        const a = document.activeElement;
        const lookup = a && a.closest('[id^="lookup_"]');
        return {
          inContainer: !!(a && a.closest('form.quick-input-container')),
          inHeaderLookup: !!(lookup && !lookup.closest('form.quick-input-container')),
        };
      });
      const isStillInProduct = await productInput.evaluate((el) => el === document.activeElement);
      const landed = await describeActiveElement(page);
      console.log(`[INFO] Tab landed on ${landed}`);
      expect(isStillInProduct, `Tab after Alt+Q: ${landed}`).toBe(false);
      expect(where.inContainer, `Tab after Alt+Q: ${landed}`).toBe(true);
      expect(where.inHeaderLookup, `Tab after Alt+Q: ${landed}`).toBe(false);
      await page.evaluate(() => document.activeElement.setAttribute('data-qikf-tab-landed', 'true'));
      await expectFocusHeld(page.locator('[data-qikf-tab-landed="true"]'), 1000, 'Tab after Alt+Q');
    });

    [
      { packingInstructionsField: 'N', piLabel: 'PI off' },
      { packingInstructionsField: 'Y', piLabel: 'PI on' },
    ].forEach(({ packingInstructionsField, piLabel }) => {
      test(`mouse pick from several, Alt+Q, ${piLabel} (${label})`, async ({ page }) => {
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
