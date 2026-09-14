import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { getFieldData } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import {
  FRONTEND_BASE_URL,
  FAST_ACTION_TIMEOUT,
  SLOW_ACTION_TIMEOUT,
} from '../utils/common';

// =========================================================================
// Creating a NEW document inside an ALREADY-MOUNTED window must focus that
// window's first focusable field — the same field a fresh page load focuses.
//
// Order entry is keyboard-first: New -> type the customer -> complete -> New
// -> … From the SECOND document of a mount onward, focus used to be lost, so
// the clerk typed into nothing.
//
// Mechanism: first-field auto-focus is armed once per widget INSTANCE, while
// opening another document in a mounted window reconciles the widgets instead
// of remounting them (same route `/window/:windowId/:docId`, only positional
// React keys). `RawLookup` clears its `shouldBeFocused` latch after the first
// real focus, so the latch is already spent when the second document arrives.
//
// TWO CONSTRUCTION RULES every scenario here obeys — both learned from failed
// probe runs:
//   1. Assert the docId ACTUALLY CHANGED before asserting focus. Pressing
//      "New" on an untouched fresh draft can leave the docId unchanged (no new
//      document at all); a focus assertion in that state passes or fails for
//      the wrong reason.
//   2. Never click `body` (nor reuse `SalesOrderPage.clickNew()`, which does
//      exactly that at `tests/utils/pages/SalesOrderPage.js:47`) right before
//      the "New" under test — the click itself moves `document.activeElement`
//      to BODY, i.e. it manufactures the very state the scenario detects.
// =========================================================================

/** The Sales Order window's first focusable element (verified against the running stack). */
const SALES_ORDER_FIRST_FIELD = '#lookup_C_BPartner_ID input.input-field';

/**
 * The contact sub-field of the same composed lookup. Mounting onto an order whose
 * Auftraggeber is already filled focuses THIS one — the widget's own
 * "skip a non-empty input" rule — and that parity with a fresh page load must survive
 * the fix.
 */
const SALES_ORDER_CONTACT_FIELD = '#lookup_AD_User_ID .input-dropdown-container';

/**
 * Human-readable description of `document.activeElement`, used in every focus
 * assertion message so a failure says WHAT held focus instead of only "false".
 */
async function describeActiveElement(page) {
  return await page.evaluate(() => {
    const el = document.activeElement;
    if (!el) return '<none>';
    const id = el.id ? `#${el.id}` : '';
    const cls =
      typeof el.className === 'string' && el.className.trim()
        ? `.${el.className.trim().split(/\s+/).join('.')}`
        : '';
    const owner = !el.id ? el.closest('[id]') : null;
    return `${el.tagName}${id}${cls}${owner ? ` (inside #${owner.id})` : ''}`;
  });
}

/** The document id currently shown in the URL, or null while on a list / NEW placeholder. */
function currentDocId(page) {
  const match = page.url().match(/\/window\/\d+\/(\d+)/);
  return match ? match[1] : null;
}

/**
 * @returns {Promise<{activeId: string, docId: string|null}>} the focused element and the
 * document it belongs to — the pair every scenario reasons about.
 */
async function activeFieldInfo(page) {
  return { activeId: await describeActiveElement(page), docId: currentDocId(page) };
}

/**
 * Wait until the document view has rendered and its pending indicators are gone.
 * Deliberately click-free: any click would move the focus under test.
 */
async function waitForDocumentReady(page) {
  await page
    .locator('.window-wrapper')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page
    .locator('.rotating, .indicator-pending')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
}

/**
 * Press Alt+N in the already-mounted window and prove a genuinely new document was
 * created (construction rule 1). No click of any kind precedes the key press
 * (construction rule 2).
 *
 * @returns {Promise<string>} the new document id
 */
async function pressNewAndExpectNewDocument(page, previousDocId) {
  await page.keyboard.press('Alt+N');

  await page.waitForURL(
    (url) => {
      const match = url.toString().match(/\/window\/\d+\/(\d+)/);
      return !!match && match[1] !== String(previousDocId);
    },
    { timeout: SLOW_ACTION_TIMEOUT }
  );

  const newDocId = currentDocId(page);
  expect(
    newDocId,
    `Alt+N must create a new document, but the URL still shows ${previousDocId}`
  ).not.toBe(String(previousDocId));

  await waitForDocumentReady(page);

  return newDocId;
}

/**
 * Assert that `selector` holds the browser focus. Polls, because focus lands
 * asynchronously after the document data arrives.
 */
async function expectFirstFieldFocused(
  page,
  selector,
  { timeout = FAST_ACTION_TIMEOUT } = {}
) {
  const focused = await page
    .waitForFunction(
      (sel) => {
        const el = document.activeElement;
        return !!el && typeof el.matches === 'function' && el.matches(sel);
      },
      selector,
      { timeout, polling: 100 }
    )
    .then(() => true)
    .catch(() => false);

  const { activeId, docId } = await activeFieldInfo(page);
  console.log(`[focus] docId=${docId} activeElement=${activeId}`);

  expect(
    focused,
    `expected document.activeElement to be "${selector}" on document ${docId}, but it was ${activeId}`
  ).toBe(true);
}

/** Assert that `selector` does NOT hold the browser focus. */
async function expectNotFocused(page, selector) {
  const { activeId } = await activeFieldInfo(page);
  const holdsFocus = await page.evaluate((sel) => {
    const el = document.activeElement;
    return !!el && typeof el.matches === 'function' && el.matches(sel);
  }, selector);

  expect(
    holdsFocus,
    `expected "${selector}" NOT to hold focus, but document.activeElement was ${activeId}`
  ).toBe(false);
}

/** A login-only fixture — for scenarios that need no business data. */
const createLoginFixture = async () =>
  Backend.createMasterdata({ request: { login: { user: { language: 'en_US' } } } });

/** Customer + priced product — the minimum an order needs to be completable. */
const createOrderFixture = async () =>
  Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US' } },
      bpartners: {
        CUSTOMER: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'Customer',
        },
      },
      products: {
        PROD: {
          name: 'PROD',
          type: 'Item',
          prices: [{ price: 17.35, currencyCode: 'EUR' }],
        },
      },
    },
  });

test.describe('First-field focus when a new document is created in a mounted window', () => {
  test.beforeEach(() => {
    allure.epic('E0294: Frontend WebUI');
    allure.tag('F50000: Frontend WebUI');
    allure.tag('F50000');
    allure.story('First-field focus on a new document');
    allure.severity('normal');
  });

  // eslint-disable-next-line no-unused-vars
  test('TC0 - fresh page load onto a new sales order focuses the first field', async ({
    page,
  }) => {
    allure.description(`
Baseline the fix must preserve: the mount path already works. Without it, a fix that
re-arms focus on a document change could silently break the first mount.
    `);

    const masterdata = await createLoginFixture();
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // A fresh page load, no previously-mounted window — the path that works today.
    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/new`);
    await page.waitForURL(new RegExp(`/window/${SALES_ORDER_WINDOW_ID}/\\d+`), {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await waitForDocumentReady(page);

    await expectFirstFieldFocused(page, SALES_ORDER_FIRST_FIELD, {
      timeout: SLOW_ACTION_TIMEOUT,
    });
  });

  // eslint-disable-next-line no-unused-vars
  test('TC1 - complete an order, then New in the same window, focuses the first field', async ({
    page,
  }) => {
    allure.description(`
The reported flow: New -> customer -> line -> complete -> New. From the second order of
a mount onward, nothing was focused.
    `);

    const masterdata = await createOrderFixture();
    const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;
    const product = masterdata.products.PROD.productName;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // First order of the mount. `clickNew()` clicks body before Alt+N, which is harmless
    // here: this is not the "New" under test, and the window holds no document yet.
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const firstOrderId = await SalesOrderPage.selectCustomer(customer);

    await SalesOrderPage.openQuickEntryAndSelectProduct({
      product,
      recordId: firstOrderId,
    });
    await SalesOrderPage.submitQuickEntryLine({ quantity: 1 });

    await SalesOrderPage.complete();
    const docStatus = await getFieldData(
      SALES_ORDER_WINDOW_ID,
      firstOrderId,
      'DocStatus'
    );
    expect(
      docStatus.value?.key ?? docStatus.value,
      `order ${firstOrderId} must be completed before the "New" under test`
    ).toBe('CO');

    // The "New" under test — same mounted window, no reload, no preceding click.
    const secondOrderId = await pressNewAndExpectNewDocument(page, firstOrderId);
    console.log(`[TC1] first order ${firstOrderId} -> new order ${secondOrderId}`);

    await expectFirstFieldFocused(page, SALES_ORDER_FIRST_FIELD);
  });

  // eslint-disable-next-line no-unused-vars
  test('TC2 - a second new order without completing the first focuses the first field', async ({
    page,
  }) => {
    allure.description(`
Pins what actually triggers the loss of focus: not completing the document, but that the
first-field widget has already auto-focused once in this mount. Without this scenario a fix
could be built around the completed / read-only state and leave the real trigger open.
    `);

    const masterdata = await createOrderFixture();
    const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // First order of the mount, left in Drafted state — its empty first field means the
    // mount-time focus fires and spends the widget's one-shot arming.
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    await expectFirstFieldFocused(page, SALES_ORDER_FIRST_FIELD, {
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // Pick the customer — deliberately NOT completing the order, but enough to make the
    // draft a real, saved document. An untouched fresh draft is not offered the "New"
    // standard action at all (`GlobalContextShortcuts.js` gates NEW_DOCUMENT on
    // `standardActionsAllowed`), so Alt+N would silently do nothing and the scenario would
    // assert against a document that never changed (construction rule 1).
    const firstOrderId = await SalesOrderPage.selectCustomer(customer);

    // The "New" under test — nothing was completed in between.
    const secondOrderId = await pressNewAndExpectNewDocument(page, firstOrderId);
    console.log(`[TC2] first order ${firstOrderId} -> new order ${secondOrderId}`);

    await expectFirstFieldFocused(page, SALES_ORDER_FIRST_FIELD);
  });

  // eslint-disable-next-line no-unused-vars
  test('TC5 - opening an existing order, then New, keeps fresh-load focus parity', async ({
    page,
  }) => {
    allure.description(`
Highest-regression-risk case: it passes today and relies on the exact widget rule the fix
changes. Mounting onto an order whose Auftraggeber is FILLED must keep focusing the first
EMPTY sub-field of the composed lookup (the contact), never the filled one — and the "New"
that follows must still focus the Auftraggeber.
    `);

    const masterdata = await createOrderFixture();
    const customer = masterdata.bpartners.CUSTOMER.bpartnerCode;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // Produce the "existing order with a filled Auftraggeber" this scenario needs.
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const existingOrderId = await SalesOrderPage.selectCustomer(customer);

    // Re-open it as a FRESH page load: that is the parity reference this scenario pins.
    await page.goto(
      `${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${existingOrderId}`
    );
    await page.waitForURL(
      new RegExp(`/window/${SALES_ORDER_WINDOW_ID}/${existingOrderId}`),
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    await waitForDocumentReady(page);

    await expectFirstFieldFocused(page, SALES_ORDER_CONTACT_FIELD, {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await expectNotFocused(page, SALES_ORDER_FIRST_FIELD);

    // The "New" under test — from a document whose first field was never auto-focused.
    const newOrderId = await pressNewAndExpectNewDocument(page, existingOrderId);
    console.log(`[TC5] existing order ${existingOrderId} -> new order ${newOrderId}`);

    await expectFirstFieldFocused(page, SALES_ORDER_FIRST_FIELD);
  });
});
