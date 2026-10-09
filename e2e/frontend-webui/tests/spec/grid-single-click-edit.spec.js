import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Grid inline edit — single-click "type to edit" must be saved.
 *
 * Regression guard: editing a numeric grid cell (Quantity/Amount widget) by a single click
 * followed by typing and moving away (Tab) must send the PATCH and persist the value, exactly
 * like the double-click path. Previously such an edit was silently dropped: the widget's focus
 * state was set asynchronously, so the first keystroke was misread as an external value change
 * and the cached baseline was reset, making the on-blur change detection conclude "nothing
 * changed" — no PATCH, value lost.
 */
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe('Grid single-click inline edit', () => {
    test(`single-click + type + Tab persists the value (${label})`, async ({ page }) => {
      allure.epic('E0294: Frontend WebUI');
      allure.tag('F50000: Frontend WebUI');
      allure.tag('F50000');
      allure.story('Single-click inline cell editing');
      allure.severity('critical');

      test.setTimeout(180000);

      // completed SO with one line (qty 5), created server-side
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'first', lastname: 'last' } },
          bpartners: {
            CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
          },
          products: {
            Product1: { name: 'PROD', type: 'Item', prices: [{ price: 15.0, currencyCode: 'EUR' }] },
          },
          warehouses: { wh: {} },
          salesOrders: {
            SO1: {
              bpartner: 'CUSTOMER1',
              warehouse: 'wh',
              datePromised: '2026-03-01T00:00:00.000+01:00',
              lines: [{ product: 'Product1', qty: 5 }],
            },
          },
        },
      });
      const orderId = masterdata.salesOrders.SO1.id;

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // Open the record and wait on a concrete document-header control (not networkidle —
      // metasfresh keeps the network busy with STOMP/KPI polling, so networkidle never settles).
      const openRecord = async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}`);
        await page
          .getByTestId('status-button')
          .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      };

      await openRecord();

      // reactivate so the line grid becomes editable — every wait below is on a state, never on
      // a fixed sleep. The status tag's class follows the document status (`tag-success` = CO),
      // and the action list is fetched when the status dropdown gains focus, so it is only
      // current if the dropdown is (re)opened after the status changed.
      await test.step('reactivate order', async () => {
        const statusButton = page.getByTestId('status-button');
        const statusTag = statusButton.locator('.tag').first();
        // (re)open the status dropdown: Escape only hides the list and keeps focus, and a click
        // on an already-focused button fires no focus event, so blur first to force a fresh
        // focus -> action-list fetch -> open.
        const openStatusDropdown = async () => {
          await statusButton.blur();
          await statusButton.click();
          await expect(statusButton).toHaveClass(/dropdown-status-open/, { timeout: 5000 });
        };

        await expect(statusTag, 'the order starts Completed').toHaveClass(/tag-success/, {
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Trigger Reactivate. A retry only re-opens the dropdown and re-clicks while no click
        // went through (status-RE not offered yet); a click that went through sends the
        // DocAction=RE PATCH, which is awaited below.
        const reactivatePatch = page
          .waitForResponse(
            (resp) =>
              resp.request().method() === 'PATCH' &&
              new URL(resp.url()).pathname.endsWith(`/window/${SALES_ORDER_WINDOW_ID}/${orderId}`) &&
              (resp.request().postData() || '').includes('"DocAction"'),
            { timeout: SLOW_ACTION_TIMEOUT * 2 }
          )
          .catch(() => null);
        await expect(async () => {
          await openStatusDropdown();
          await page.getByTestId('status-RE').click({ timeout: 5000 });
        }, 'the Reactivate action is offered on the Completed order').toPass({
          timeout: SLOW_ACTION_TIMEOUT,
        });
        const reactivateResponse = await reactivatePatch;
        expect(reactivateResponse, 'the Reactivate (DocAction=RE) PATCH was sent').not.toBeNull();
        expect(reactivateResponse.ok(), 'the Reactivate PATCH succeeded').toBe(true);

        // the document left Completed ...
        await expect(statusTag, 'the order left the Completed status').not.toHaveClass(
          /tag-success/,
          { timeout: SLOW_ACTION_TIMEOUT }
        );
        // ... and is completable again (back to Drafted): the Complete action is offered.
        // Each try re-opens the dropdown, so a list fetched mid-transition is never re-read.
        await expect(async () => {
          await openStatusDropdown();
          await expect(page.getByTestId('status-CO')).toBeVisible({ timeout: 5000 });
        }, 'order did not reactivate to a Drafted (completable) state').toPass({
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await page.keyboard.press('Escape');
        await statusButton.blur();
      });

      const qtyCell = () =>
        page.locator('table tbody tr').first().locator('[data-cy="cell-QtyEntered"]').first();

      await qtyCell().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      // the line grid follows the document status: wait until the qty cell is editable
      await expect(qtyCell(), 'the qty cell is editable after reactivation').not.toHaveClass(
        /cell-disabled/,
        { timeout: SLOW_ACTION_TIMEOUT }
      );

      // capture QtyEntered PATCHes — only from the edit below onwards
      const qtyPatches = [];
      const captureQtyPatch = (req) => {
        if (
          req.method() === 'PATCH' &&
          req.url().includes('/window/') &&
          (req.postData() || '').includes('QtyEntered')
        ) {
          qtyPatches.push(req.postData());
        }
      };

      // single-click the qty cell, type a new single-digit value, move away.
      // Start from a clean selection state (click a neutral area first) so the cell
      // click is a genuine fresh single-click "type to edit", as a user would do.
      await test.step('single-click edit the quantity', async () => {
        await page
          .locator('.header-breadcrumb, .document-header, body')
          .first()
          .click({ position: { x: 5, y: 5 } })
          .catch(() => {});
        page.on('request', captureQtyPatch);
        await qtyCell().click();
        // keystrokes go to the focused cell (TableCell onKeyDown); wait for that focus
        await expect(qtyCell(), 'the clicked qty cell has focus').toBeFocused();
        await page.keyboard.type('3', { delay: 80 });
        // sanity: the typed value is actually in the cell input before we leave it
        await expect(
          qtyCell().locator('input.js-input-field').first(),
          'typed value present in the cell input'
        ).toHaveValue('3');

        // Await the actual PATCH round-trip on blur (not a blind sleep). On the buggy path
        // no PATCH is sent, so this resolves null after the timeout and the assertion below fails.
        const patchSettled = page
          .waitForResponse(
            (resp) =>
              resp.request().method() === 'PATCH' &&
              resp.url().includes('/window/') &&
              (resp.request().postData() || '').includes('QtyEntered'),
            { timeout: 10000 }
          )
          .catch(() => null);
        await page.keyboard.press('Tab');
        await patchSettled;
        page.off('request', captureQtyPatch);
      });

      // the edit must have been PATCHed to the server
      expect(
        qtyPatches,
        'a PATCH with the new QtyEntered must be sent on single-click edit'
      ).not.toHaveLength(0);

      // end result: the new quantity must persist across a reload
      await test.step('reload and verify the quantity persisted', async () => {
        await openRecord();
        await qtyCell().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        const cellText = (await qtyCell().innerText()).trim();
        // language-independent numeric check: normalise decimal comma -> dot, parse
        const numeric = parseFloat(cellText.replace(/\s/g, '').replace(',', '.').replace(/[^0-9.]/g, ''));
        expect(Math.round(numeric), `persisted QtyEntered (cell text: "${cellText}")`).toBe(3);
      });
    });
  });
});
