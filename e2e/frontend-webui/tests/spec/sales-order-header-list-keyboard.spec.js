import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getRecordData } from '../utils/WebAPIValidation';
import {
  installDocumentEventRecorder,
  armHeaderReload,
  awaitHeaderReload,
  hasReloadSince,
  pickCustomerByKeyboard,
  expectFocusOn,
  expectFocusHeld,
  describeActiveElement,
} from '../utils/QuickInputFocus';

/**
 * Header keyboard flow requirement test (reproduction of REQUIREMENTS section 3 and TC7): in the sales order
 * header, after the partner Enter the cursor moves on to the address list and, after Enter there, to the contact
 * list; Enter takes the highlighted entry, so partner, address and contact are all filled. Red before the fix.
 */

const ADDRESS_LIST = '#lookup_C_BPartner_Location_ID .input-dropdown-container';
const CONTACT_LIST = '#lookup_AD_User_ID .input-dropdown-container';
const HIGHLIGHTED_OPTION = '.input-dropdown-list .input-dropdown-list-option-key-on';

async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'SOHLK', lastname: 'Test' } },
      bpartners: {
        MULTI: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'Customer',
          locations: {
            LOC1: {
              city: 'Bonn',
              postal: '53111',
              address1: 'A 1',
              countryCode: 'DE',
            },
            LOC2: {
              city: 'Kiel',
              postal: '24103',
              address1: 'B 2',
              countryCode: 'DE',
            },
          },
          contacts: {
            C1: { firstName: 'Anna', lastName: 'One' },
            C2: { firstName: 'Ben', lastName: 'Two' },
          },
        },
      },
    },
  });
}

/**
 * A keyboard user presses Enter on an open list with a highlighted entry; a list that has the cursor but is not
 * open yet ignores Enter. If the list does not open and the cursor is not in it, that is a focus failure and is
 * worded as one. The highlighted-option check is page-wide (the options are portaled out of the lists), so it
 * is only a sign that some list shows a highlighted entry; the `opened` class check is scoped to the list.
 */
async function expectListOpen(page, list, description) {
  try {
    await expect(page.locator(list)).toHaveClass(/\bopened\b/, { timeout: SLOW_ACTION_TIMEOUT });
    await expect(page.locator(HIGHLIGHTED_OPTION).first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  } catch (error) {
    const hasCursor = await page
      .locator(list)
      .evaluate((el) => el === document.activeElement, undefined, { timeout: 1000 })
      .catch(() => false);
    if (!hasCursor) {
      throw new Error(
        `${description}: cursor is not in the expected field, it is in ${await describeActiveElement(page)}`
      );
    }
    throw new Error(
      `${description}: list did not open with a highlighted entry (${String(error.message).split('\n')[0]})`
    );
  }
}

/** The last checks before an Enter: cursor in the list, list open with an entry, cursor still in the list. */
async function expectReadyForEnter(page, list, description) {
  await expectFocusOn(page.locator(list), `${description}: cursor in the list`);
  await expectListOpen(page, list, description);
  await expectFocusOn(page.locator(list), `${description}: cursor still in the list before Enter`);
}

/**
 * The cursor is in the list, stays there 1000 ms, and, if the step triggered a header reload, is in the list and
 * stays there after that reload as well (a reload is not always emitted, so it is only awaited when it came).
 * requireReload: the step always saves a header change, so the reload is awaited in any case.
 */
async function expectListThroughReload(page, recorder, mark, doc, list, description, { requireReload = false } = {}) {
  const locator = page.locator(list);
  await expectFocusOn(locator, description);
  await expectFocusHeld(locator, 1000, description);
  // hasReloadSince is read once, after the hold: a reload that comes later is not awaited here (the step's own
  // later checks and the next Enter cover it).
  if (requireReload || hasReloadSince(recorder, mark, doc)) {
    try {
      await awaitHeaderReload(page, recorder, mark, { ...doc, step: description });
    } catch (error) {
      // No reload: report where the cursor is, so the failure reads like the focus failures (and keeps the cause).
      throw new Error(
        `${description}: ${String(error.message).split('\n')[0]}; the cursor is in ${await describeActiveElement(page)}`
      );
    }
    await expectFocusOn(locator, `${description}, after the header reload`);
    await expectFocusHeld(locator, 1000, `${description}, after the header reload`);
  }
}

/** Alt+N, partner by keyboard, address list reached, Enter, contact list reached. Returns the record id. */
async function advanceToContactList(page, recorder, masterdata) {
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  // Setup, not the bug: the new order opens with the cursor in the partner. A miss here is not reported as a focus failure.
  await expect
    .poll(() => page.evaluate(() => !!document.activeElement?.closest('#lookup_C_BPartner_ID')), {
      timeout: SLOW_ACTION_TIMEOUT,
      message: 'setup: new order opened with the cursor in the partner input',
    })
    .toBe(true);
  const mark = armHeaderReload(recorder);
  const recordId = await pickCustomerByKeyboard({ bpartnerCode: masterdata.bpartners.MULTI.bpartnerCode });
  const doc = { windowId: SALES_ORDER_WINDOW_ID, recordId };
  // The partner save always makes the server fill the header, so its reload is awaited.
  await expectListThroughReload(
    page,
    recorder,
    mark,
    doc,
    ADDRESS_LIST,
    'after partner Enter: cursor in the address list',
    {
      requireReload: true,
    }
  );
  await expectReadyForEnter(page, ADDRESS_LIST, 'after partner Enter');

  const addressMark = armHeaderReload(recorder);
  await page.keyboard.press('Enter');
  await expectListThroughReload(
    page,
    recorder,
    addressMark,
    doc,
    CONTACT_LIST,
    'after address Enter: cursor in the contact list'
  );
  await expectReadyForEnter(page, CONTACT_LIST, 'after address Enter');
  return recordId;
}

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Sales order header keyboard flow (${label})`, () => {
    const tag = (story) => {
      allure.epic('E0100: Sales');
      allure.tag('F00100: Sales Order Window');
      allure.tag('F00100');
      allure.tag('F00101: Sales Order Quick Entry');
      allure.tag('F00101');
      allure.tag('F50000: Frontend WebUI');
      allure.tag('F50000');
      allure.story(story);
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
    };

    const start = async (page) => {
      const recorder = installDocumentEventRecorder(page);
      const masterdata = await createMasterdata(language);
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      await Backend.resetWebApiCaches();
      return { recorder, masterdata };
    };

    test(`header keyboard flow: partner, address and contact by Enter (${label})`, async ({ page }) => {
      tag('Header keyboard flow: partner, address and contact by Enter');
      test.setTimeout(240000);
      const { recorder, masterdata } = await start(page);

      const recordId = await advanceToContactList(page, recorder, masterdata);
      await page.keyboard.press('Enter');

      await expect
        .poll(
          async () => {
            const f = (await getRecordData(SALES_ORDER_WINDOW_ID, recordId)).fieldsByName;
            return [f?.C_BPartner_ID?.value?.key, f?.C_BPartner_Location_ID?.value?.key, f?.AD_User_ID?.value?.key].map(
              (k) => (k === undefined || k === null ? 'unset' : 'set')
            );
          },
          {
            timeout: SLOW_ACTION_TIMEOUT,
            message: 'C_BPartner_ID, C_BPartner_Location_ID, AD_User_ID are all set',
          }
        )
        .toEqual(['set', 'set', 'set']);
    });

    test(`header keyboard flow: cursor after the contact Enter (${label})`, async ({ page }) => {
      tag('Header keyboard flow: the cursor rests where it is after the contact Enter');
      test.setTimeout(240000);
      const { recorder, masterdata } = await start(page);

      const recordId = await advanceToContactList(page, recorder, masterdata);
      const mark = armHeaderReload(recorder);
      await page.keyboard.press('Enter');

      // The reload the contact PATCH triggers; no other field is touched (typing would move the cursor itself).
      await awaitHeaderReload(page, recorder, mark, {
        windowId: SALES_ORDER_WINDOW_ID,
        recordId,
        step: 'contact Enter',
      });
      // After the last sub-field the cursor correctly stays in the contact: Lookup.focusNextFormField skips read-only
      // inputs (Lookup.js:164-186), and the contact list's input is read-only, so nothing moves the cursor on.
      const described = await describeActiveElement(page);
      // Mark the element in the DOM so the hold check can target exactly that element.
      await page.evaluate(
        () => document.activeElement && document.activeElement.setAttribute('data-header-active', 'A')
      );
      allure.attachment('Active element after contact reload', described, 'text/plain');
      console.log(`Active element after contact reload (${language}): ${described}`);

      // Wherever the cursor is after the reload (in the contact, as above), the assertion is only that it stays there.
      await expectFocusHeld(
        page.locator('[data-header-active="A"]').first(),
        1000,
        `contact Enter: cursor left ${described}`
      );
    });
  });
});
