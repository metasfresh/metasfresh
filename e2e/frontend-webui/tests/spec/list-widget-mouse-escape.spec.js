import { expect } from '@playwright/test';
import { CHROME_LAUNCH_ARGS, test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getRecordData } from '../utils/WebAPIValidation';
import {
  pickCustomerByMouse,
  pickCustomerAndSettle,
  expectFocusOn,
  expectFocusHeld,
  describeActiveElement,
} from '../utils/QuickInputFocus';

/**
 * Pins (must pass on today's code): in the sales order header a mouse click on a list field opens its
 * dropdown, a mouse pick keeps the value, a press on the dropdown scrollbar does not close it, and Escape
 * closes it without changing the value. (Escape blurs the field by design, so the cursor is not asserted.)
 */

// List widgets (select-dropdown) have no `#lookup_<Column>` id (only Lookup widgets do), so the fields are
// addressed by the structural class `form-field-<ColumnName>` of their widget wrapper.

const PAYMENT_RULE = '.form-field-PaymentRule .input-dropdown-container';
const PAYMENT_TERM = '.form-field-C_PaymentTerm_ID .input-dropdown-container';
const OPTION = '.input-dropdown-list-option[data-testid^="option-"]';

/**
 * Mark (data-pin-next="A") the element that Tab moves the cursor to from the element at `selector`: the next one in
 * the browser's sequential focus order (positive tabindex ascending first, then tabindex 0; document order within
 * each), skipping disabled and not rendered elements and those in the same form field (`form-field-<Column>`
 * wrapper) as `selector`. So the marked element is never the body nor part of the field Tab leaves.
 * @param {import('@playwright/test').Page} page
 * @param {string} fieldSelector the field's wrapper, e.g. `.form-field-PaymentRule`
 * @param {string} selector the element inside that field that holds the cursor before Tab
 * @returns {Promise<string>} the marked element's description (`<TAG> in form-field-<Column>`), or `none` when no
 *   element follows in the tab order
 */
async function markNextInTabOrder(page, fieldSelector, selector) {
  return await page.evaluate(([fieldSel, sel]) => {
    const from = document.querySelector(sel);
    const fromField = from.closest(fieldSel) || from;
    const tabbable = Array.from(document.querySelectorAll('*')).filter(
      (e) =>
        e.tabIndex >= 0 &&
        !e.disabled &&
        e.getClientRects().length > 0 &&
        getComputedStyle(e).visibility !== 'hidden' &&
        (e === from || !fromField.contains(e))
    );
    const ordered = [
      ...tabbable.filter((e) => e.tabIndex > 0).sort((a, b) => a.tabIndex - b.tabIndex),
      ...tabbable.filter((e) => e.tabIndex === 0),
    ];
    const next = ordered[ordered.indexOf(from) + 1];
    if (!next) return 'none';
    next.setAttribute('data-pin-next', 'A');
    const field = next.closest('[class*="form-field-"]');
    return `${next.tagName}${field ? ' in ' + (field.className.match(/form-field-[A-Za-z0-9_]+/) || [''])[0] : ''}`;
  }, [fieldSelector, selector]);
}

async function savedKey(recordId, column) {
  const f = (await getRecordData(SALES_ORDER_WINDOW_ID, recordId)).fieldsByName;
  const key = f?.[column]?.value?.key;
  return key === undefined || key === null ? null : String(key);
}

/** The option key from the data-testid of an option element. */
async function optionKey(option) {
  const testId = await option.getAttribute('data-testid');
  return testId.replace(/^option-/, '');
}

// Scrollbars are hidden by default in Playwright's Chromium, so the scrollbar pin turns them on. `test.use` replaces
// the project's launchOptions, so the project's args are passed again.
test.use({
  launchOptions: {
    args: CHROME_LAUNCH_ARGS,
    ignoreDefaultArgs: ['--hide-scrollbars'],
  },
});

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`List widget mouse and Escape (${label})`, () => {
    const tag = (story) => {
      allure.epic('E0100: Sales');
      allure.tag('F50000: Frontend WebUI');
      allure.tag('F50000');
      allure.tag('F00100: Sales Order');
      allure.tag('F00100');
      allure.story(story);
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);
    };

    const start = async (page) => {
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'LWME', lastname: 'Test' } },
          bpartners: { CUST: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' } },
        },
      });
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      await Backend.resetWebApiCaches();
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const bp = masterdata.bpartners.CUST;
      // After the pick the header walks the cursor partner -> address -> contact. What each wait guarantees:
      // - pickCustomerAndSettle: the contact list request of the walk was answered, then 2 animation frames
      //   passed. It does not prove that the cursor already sits in the contact field, and a later header
      //   reload is not waited for.
      // - the waitForFunction below: the cursor reached the contact field (the last hop of the walk).
      // - the hold below: the cursor stays in the contact field for 500 ms, so a second late hop (the contact
      //   list re-taking the cursor on a re-render) would show here and not inside the pin that follows.
      // The race itself (a click during the walk steals the cursor) is an app defect that this pin does not
      // cover; the nearest pins are in quick-input-keyboard-focus.spec.js (the keyboard quick entry cases and the
      // keyboard pick of a partner with two addresses: the cursor must survive the walk into the quick entry). No
      // spec pins a list-field click during the walk: this is not covered.
      const recordId = await pickCustomerAndSettle(() =>
        pickCustomerByMouse({ searchText: bp.bpartnerCode, bpartnerCode: bp.bpartnerCode })
      );
      const inContact = () => page.evaluate(() => !!document.activeElement?.closest('#lookup_AD_User_ID'));
      await page.waitForFunction(() => !!document.activeElement?.closest('#lookup_AD_User_ID'), null, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      for (const deadline = Date.now() + 500; Date.now() < deadline; ) {
        expect(await inContact(), 'the header walk ended: cursor stays in the contact field for 500 ms').toBe(true);
        await page.waitForTimeout(100);
      }
      await expect(page.locator(PAYMENT_RULE)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      return recordId;
    };

    test(`mouse pick in PaymentRule then Tab (${label})`, async ({ page }) => {
      tag('Header list: one click opens, a mouse pick keeps the value, Tab moves on');
      test.setTimeout(240000);
      const recordId = await start(page);

      await page.locator(PAYMENT_RULE).click();
      await expect(page.locator(PAYMENT_RULE), 'one click opens PaymentRule').toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      const option = page.locator(OPTION).nth(1);
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const key = await optionKey(option);
      const pickSaved = page.waitForResponse(
        (r) => r.request().method() === 'PATCH' && new URL(r.url()).pathname.endsWith(`/window/${SALES_ORDER_WINDOW_ID}/${recordId}`),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      pickSaved.catch(() => {}); // awaited below; a timeout fails the test there
      await option.click();
      await expect(page.locator(PAYMENT_RULE), 'dropdown closed after the pick').not.toHaveClass(/opened/);
      // The mouse-down on the option sends the cursor to the body, and PaymentRule takes it back right after the
      // pick. Saving the pick can hide the next field (a cash payment rule hides the payment term), so the next field
      // is known only once the save is answered. Tab is pressed when both have happened, as a user would see them.
      await pickSaved;
      await expectFocusOn(page.locator(PAYMENT_RULE), 'after the pick: cursor back in PaymentRule');
      await page.evaluate(() => new Promise((r) => requestAnimationFrame(() => requestAnimationFrame(r))));
      const expectedNext = await markNextInTabOrder(page, '.form-field-PaymentRule', PAYMENT_RULE);
      console.log(`Next field after PaymentRule in tab order (${language}): ${expectedNext}`);

      await page.keyboard.press('Tab');
      // The next field is neither the body nor part of PaymentRule (see markNextInTabOrder).
      await expectFocusOn(page.locator('[data-pin-next="A"]'), `after Tab: cursor on the next field (${expectedNext})`);
      const described = await describeActiveElement(page);
      console.log(`Active element after Tab (${language}): ${described}`);
      // Mark the element holding the cursor so the hold check targets exactly it.
      await page.evaluate(() => document.activeElement && document.activeElement.setAttribute('data-pin-active', 'A'));
      await expectFocusHeld(page.locator('[data-pin-active="A"]').first(), 1000, `after Tab: cursor left ${described}`);

      await expect
        .poll(() => savedKey(recordId, 'PaymentRule'), { timeout: SLOW_ACTION_TIMEOUT, message: `saved PaymentRule equals ${key}` })
        .toBe(key);
    });

    test(`click-open and scrollbar in C_PaymentTerm_ID (${label})`, async ({ page }) => {
      tag('Header list: one click opens, the scrollbar keeps it open, a mouse pick keeps the value');
      test.setTimeout(240000);
      const recordId = await start(page);

      await page.locator(PAYMENT_TERM).click();
      await expect(page.locator(PAYMENT_TERM), 'one click opens C_PaymentTerm_ID').toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      const list = page.locator('.input-dropdown-list').first();
      await list.locator(OPTION).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const before = await savedKey(recordId, 'C_PaymentTerm_ID');
      const box = await list.boundingBox();
      const x = box.x + box.width - 4;
      const y = box.y + box.height / 2;
      const geo = await list.evaluate((l, [px, py]) => {
        const cs = getComputedStyle(l);
        const hit = document.elementFromPoint(px, py);
        return {
          scrollable: l.scrollHeight > l.clientHeight,
          scrollbarWidth: l.offsetWidth - l.clientWidth - parseFloat(cs.borderLeftWidth) - parseFloat(cs.borderRightWidth),
          hitIsList: hit === l,
        };
      }, [x, y]);
      expect(geo.scrollable, 'the payment term list is scrollable').toBe(true);
      expect(geo.scrollbarWidth, 'the list draws a classic scrollbar').toBeGreaterThan(0);
      expect(geo.hitIsList, 'the press point is on the list scrollbar, not on an option').toBe(true);
      await page.mouse.move(x, y);
      await page.mouse.down();
      await page.mouse.up();
      await expect(page.locator(PAYMENT_TERM), 'dropdown still open after a scrollbar press').toHaveClass(/opened/);
      await expect(list, 'option list still visible after a scrollbar press').toBeVisible();
      expect(await savedKey(recordId, 'C_PaymentTerm_ID'), 'scrollbar press selects nothing').toBe(before);
      // A deferred focus-out handling must not close it later either: hold for 1000 ms, sampled every 100 ms.
      for (const deadline = Date.now() + 1000; Date.now() < deadline; ) {
        const isOpen = await page.locator(PAYMENT_TERM).evaluate((el) => el.classList.contains('opened'));
        expect(isOpen, 'dropdown stays open for 1000 ms after a scrollbar press').toBe(true);
        await page.waitForTimeout(100);
      }
      await expect(list, 'option list still visible 1000 ms after a scrollbar press').toBeVisible();
      expect(await savedKey(recordId, 'C_PaymentTerm_ID'), 'value unchanged 1000 ms after a scrollbar press').toBe(before);

      const option = page.locator(OPTION).nth(1);
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const key = await optionKey(option);
      await option.click();
      await expect
        .poll(() => savedKey(recordId, 'C_PaymentTerm_ID'), {
          timeout: SLOW_ACTION_TIMEOUT,
          message: `saved C_PaymentTerm_ID equals ${key}`,
        })
        .toBe(key);
    });

    test(`Escape closes PaymentRule (${label})`, async ({ page }) => {
      tag('Header list: ArrowDown opens, Escape closes it and keeps the value');
      test.setTimeout(240000);
      const recordId = await start(page);

      const before = await savedKey(recordId, 'PaymentRule');
      // start() has waited for the header walk to end (cursor held in the contact field)
      await page.locator(PAYMENT_RULE).focus();
      await expectFocusOn(page.locator(PAYMENT_RULE), 'PaymentRule focused before ArrowDown');
      await page.keyboard.press('ArrowDown');
      await expect(page.locator(PAYMENT_RULE), 'ArrowDown opens PaymentRule').toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      // the options are loaded and the dropdown (which owns the Escape handler) is rendered
      await page.locator(OPTION).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.keyboard.press('Escape');
      await expect(page.locator(PAYMENT_RULE), 'Escape closes PaymentRule').not.toHaveClass(/opened/, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(page.locator('.input-dropdown-list'), 'no dropdown list after Escape').toHaveCount(0);
      await page.waitForTimeout(1000);
      await expect(page.locator(PAYMENT_RULE), 'still closed 1 s after Escape').not.toHaveClass(/opened/);
      expect(await savedKey(recordId, 'PaymentRule'), 'PaymentRule value unchanged by Escape').toBe(before);
    });
  });
});
