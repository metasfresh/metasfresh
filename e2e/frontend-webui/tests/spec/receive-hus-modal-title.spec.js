import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PURCHASE_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * "Receive HUs" modal: its title is the translated "HUs vereinnahmen" / "Receive HUs", not the constant "HU Editor".
 * Only the receive view changes; every other HU editor keeps "HU Editor".
 *
 * Exception to the language-independence rule (e2e/frontend-webui/CLAUDE.md): the translated title text IS the
 * feature under test, so it cannot be replaced by an identifier; the spec therefore runs per language and asserts the
 * exact text.
 *
 * The frontend takes a modal's title from the view layout caption (ViewActions.js), so the title assertion reads
 * `.panel-modal-header-title` of the real modal.
 */

const DE_TITLE = 'HUs vereinnahmen';
const EN_TITLE = 'Receive HUs';
/** AD_Language, expected title. fr_CH has no translation: the base (German) text is shown, without an error. */
const LANGUAGES = [
  ['de_DE', DE_TITLE],
  ['de_CH', DE_TITLE],
  ['en_US', EN_TITLE],
  ['fr_CH', DE_TITLE],
];

const MODAL_TITLE = '.panel-modal-header-title';
/**
 * AD_Window_ID of "Wareneingangsdisposition Logistik" (a window on M_ReceiptSchedule). NOT the constant
 * LOGISTIK_WINDOW_ID (542190, "... inkl. Lieferplanung"), which is a different window.
 */
const LOGISTIK_WINDOW_ID = 541954;
const PURCHASE_ORDER_LINE_TAB_ID = 'AD_Tab-293';

function describeCase(story) {
  allure.epic('E0150: Material Receipt');
  allure.tag('F65010: Material Receipt Candidates');
  allure.tag('F65010');
  allure.story(story);
  allure.severity('normal');
}

async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language } },
      bpartners: { VENDOR1: { isVendor: true, isCustomer: false, isSoPriceList: false } },
      products: {
        Product1: {
          name: `Test Product for Receive HUs modal title ${Date.now()}`,
          type: 'Item',
          prices: [{ price: 10.0, currencyCode: 'EUR' }],
        },
      },
    },
  });
}

test.describe('Receive HUs modal - title', () => {
  test.setTimeout(240000);

  for (const [language, expectedTitle] of LANGUAGES) {
    test(`TC2-${language}: the receive modal opened from the receipt candidates is titled "${expectedTitle}"`, async ({
      page,
    }) => {
      describeCase(`Receive HUs modal title ${language}`);
      const masterdata = await createMasterdata(language);

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
      await PurchaseOrderPage.goToOrderLineTab();
      await PurchaseOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: '5' });
      await PurchaseOrderPage.complete();
      await PurchaseOrderPage.openRelatedReceiptCandidate();
      await ReceiptCandidatesPage.expectQuickActionsVisible();
      await ReceiptCandidatesPage.executeDefaultQuickAction();
      await ReceiptCandidatesPage.waitForPlanningHURows();

      await expect(page.locator(MODAL_TITLE).first()).toHaveText(expectedTitle, { timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  for (const [language, expectedTitle] of [
    ['de_DE', DE_TITLE],
    ['en_US', EN_TITLE],
  ]) {
    test(`TC2-Logistik-${language}: the receive modal opened from "Wareneingangsdisposition Logistik" is titled "${expectedTitle}"`, async ({
      page,
      request,
    }) => {
      describeCase(`Receive HUs modal title from Logistik window ${language}`);
      const masterdata = await createMasterdata(language);
      const REST = WEBAPI_BASE_URL;
      const productId = masterdata.products.Product1.id;
      const vendorId = masterdata.bpartners.VENDOR1.id;

      const firstDocument = (body) => (Array.isArray(body) ? body : body.documents || [body])[0];
      const patchDocument = async (windowId, documentId, tabId, rowId, changes) => {
        const path = tabId
          ? `${REST}/window/${windowId}/${documentId}/${tabId}/${rowId}`
          : `${REST}/window/${windowId}/${documentId}`;
        const response = await request.patch(path, { data: changes });
        if (!response.ok()) {
          throw new Error(`PATCH ${path} failed: HTTP ${response.status()} ${await response.text()}`);
        }
        return firstDocument(await response.json());
      };

      // REST setup session on the `request` fixture (not page.request: that would log the browser in)
      const sessionBody = await (await request.get(`${REST}/userSession`)).json().catch(() => ({}));
      if (!sessionBody.loggedIn) {
        const authBody = await (
          await request.post(`${REST}/login/authenticate`, { data: { username: 'metasfresh', password: 'metasfresh' } })
        ).json();
        if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
          await request.post(`${REST}/login/loginComplete`, { data: authBody.roles[0] });
        }
      }

      const purchaseOrderId = (await patchDocument(PURCHASE_ORDER_WINDOW_ID, 'NEW', null, null, [])).id;
      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, null, null, [
        { op: 'replace', path: 'C_BPartner_ID', value: Number(vendorId) },
      ]);
      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, PURCHASE_ORDER_LINE_TAB_ID, 'NEW', [
        { op: 'replace', path: 'M_Product_ID', value: Number(productId) },
        { op: 'replace', path: 'QtyEntered', value: 5 },
      ]);
      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, null, null, [
        { op: 'replace', path: 'DocAction', value: 'CO' },
      ]);

      // the receipt-schedule row is created asynchronously: poll the window's own view for this order
      const ownRows = async () => {
        const view = await (
          await request.post(`${REST}/documentView/${LOGISTIK_WINDOW_ID}`, {
            data: { windowId: String(LOGISTIK_WINDOW_ID), viewType: 'grid' },
          })
        ).json();
        const rowsPage = await (
          await request.get(
            `${REST}/documentView/${LOGISTIK_WINDOW_ID}/${view.viewId}` +
              `?firstRow=0&pageLength=500&orderBy=-M_ReceiptSchedule_ID`,
          )
        ).json();
        return (rowsPage.result || []).filter(
          (row) => String(row.fieldsByName?.M_Product_ID?.value?.key) === String(productId),
        );
      };
      await expect.poll(async () => (await ownRows()).length, { timeout: 90000, intervals: [1000, 2000] }).toBe(1);
      const rowIds = (await ownRows()).map((row) => Number(row.id));

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // a view scoped to the own row, created in the browser's session (see receipt_disposition_delivery_planning_actions.spec.js)
      const viewId = await page.evaluate(
        async ({ rest, windowId, ids }) => {
          const created = await fetch(`${rest}/documentView/${windowId}`, {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
            body: JSON.stringify({ windowId: String(windowId), viewType: 'grid', filterOnlyIds: ids }),
          });
          if (!created.ok) {
            throw new Error(`view scoped to the own rows failed: HTTP ${created.status}`);
          }
          return (await created.json()).viewId;
        },
        { rest: REST, windowId: LOGISTIK_WINDOW_ID, ids: rowIds },
      );
      await page.goto(`${FRONTEND_BASE_URL}/window/${LOGISTIK_WINDOW_ID}?viewId=${viewId}`);

      const row = page.locator('table tbody tr').first();
      await row.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      // the grid auto-selects its first row and a click on a selected row deselects it
      if (!/row-selected/.test((await row.getAttribute('class')) || '')) {
        await row.click();
      }
      await expect(row).toHaveClass(/row-selected/);

      await ReceiptCandidatesPage.executeDefaultQuickAction();
      await expect(page.locator(MODAL_TITLE).first()).toHaveText(expectedTitle, { timeout: SLOW_ACTION_TIMEOUT });
    });
  }

  test('TC2-default: the default HU editor keeps the caption "HU Editor"', async ({ page }) => {
    describeCase('Default HU editor caption unchanged');
    const masterdata = await createMasterdata('en_US');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // No other MODAL HU editor is reachable without heavy setup, and the frontend uses the layout caption only for
    // modals, so the default HU editor (window 540189) is asserted on its layout caption as the web API serves it.
    const layout = await page.evaluate(async (rest) => {
      const response = await fetch(`${rest}/documentView/540189/layout?viewType=grid`, {
        credentials: 'include',
        headers: { Accept: 'application/json' },
      });
      return { ok: response.ok, body: await response.json() };
    }, WEBAPI_BASE_URL);
    expect(layout.ok, 'HU editor layout request').toBe(true);
    expect(layout.body.caption).toBe('HU Editor');
  });
});
