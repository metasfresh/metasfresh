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

      // capture PATCHes to the order-line tab
      const qtyPatches = [];
      page.on('request', (req) => {
        if (
          req.method() === 'PATCH' &&
          req.url().includes('/window/') &&
          (req.postData() || '').includes('QtyEntered')
        ) {
          qtyPatches.push(req.postData());
        }
      });

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

      // reactivate so the line grid becomes editable. Verify reactivation actually
      // completed (Complete action reappears → document back to Drafted) rather than
      // relying on the spinner-detached heuristic alone, which can false-pass before
      // the spinner mounts. Mirrors PurchaseOrderPage.reactivate().
      await test.step('reactivate order', async () => {
        const maxAttempts = 3;
        let reactivated = false;
        for (let attempt = 1; attempt <= maxAttempts && !reactivated; attempt++) {
          if (!(await page.getByTestId('status-RE').isVisible().catch(() => false))) {
            await page.getByTestId('status-button').click();
            await page.waitForTimeout(500);
          }
          const re = page.getByTestId('status-RE');
          const reVisible = await re
            .waitFor({ state: 'visible', timeout: 10000 })
            .then(() => true)
            .catch(() => false);
          if (!reVisible) {
            await page.keyboard.press('Escape').catch(() => {});
            await page.waitForTimeout(1500);
            continue;
          }
          await re.click();
          await page
            .locator('.rotating, .indicator-pending')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
          // confirm the document is completable again (back to Drafted)
          await page.getByTestId('status-button').click();
          await page.waitForTimeout(500);
          reactivated = await page.getByTestId('status-CO').isVisible().catch(() => false);
          await page.keyboard.press('Escape');
          if (!reactivated) await page.waitForTimeout(1500);
        }
        expect(
          reactivated,
          `order did not reactivate to a Drafted (completable) state after ${maxAttempts} attempts`
        ).toBe(true);
      });

      const qtyCell = () =>
        page.locator('table tbody tr').first().locator('[data-cy="cell-QtyEntered"]').first();

      await qtyCell().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // single-click the qty cell, type a new single-digit value, move away.
      // Start from a clean selection state (click a neutral area first) so the cell
      // click is a genuine fresh single-click "type to edit", as a user would do.
      await test.step('single-click edit the quantity', async () => {
        await page
          .locator('.header-breadcrumb, .document-header, body')
          .first()
          .click({ position: { x: 5, y: 5 } })
          .catch(() => {});
        await page.waitForTimeout(300);
        await qtyCell().click();
        await page.waitForTimeout(500);
        await page.keyboard.type('3', { delay: 80 });
        await page.waitForTimeout(500);
        // sanity: the typed value is actually in the cell input before we leave it
        const typed = await qtyCell().locator('input.js-input-field').first().inputValue();
        expect(typed, 'typed value present in the cell input').toBe('3');

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
