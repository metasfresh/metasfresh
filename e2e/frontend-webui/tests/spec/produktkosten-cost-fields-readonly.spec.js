import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Produktkosten (legacy M_Cost) window 344 — cost fields are READ-ONLY [AC10/AC11].
 *
 * The migration flips AD_Field.IsReadOnly='Y' for CurrentCostPrice (11350) and FutureCostPrice (11352)
 * on the M_Cost tab (701) of window 344, so a user can no longer edit a product's cost price directly
 * and bypass the audited Kosten Neubewertung path. This is a GLOBAL CORE change (all customers).
 *
 * AC10 is asserted on the language-invariant `readonly` flag that the WebUI reads to render the fields
 * read-only (both in the grid and in single-row). AC11 (the audited Kosten Neubewertung document remains
 * the working path to change a cost price) is covered by cost-revaluation-quickinput.spec.js.
 */

const PRODUKTKOSTEN_WINDOW_ID = '344';
const M_COST_TAB_ID = 'AD_Tab-701';

// eslint-disable-next-line no-unused-vars
test('Produktkosten window 344 CurrentCostPrice + FutureCostPrice are read-only (English)', async ({ page }) => {
  test.setTimeout(120000);
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.story('Produktkosten cost fields locked read-only');
  allure.severity('critical');
  allure.description(`
## F1500: Costing — Produktkosten (window 344) cost fields read-only [AC10]

A normally-created product carries its default M_Cost rows. On the Produktkosten window's M_Cost tab,
the Current Cost Price and Future Cost Price fields must be read-only so a user cannot edit a cost
price directly (bypassing the audited Kosten Neubewertung path). Asserted on the language-invariant
\`readonly\` flag the WebUI renders from, plus a UI screenshot of the window.
  `);

  const md = await Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US', firstname: 'PK', lastname: 'ReadOnly' } },
      products: { P: { name: 'PK_PROD', type: 'Item' } },
    },
  });
  const productId = md.products.P.id;

  await LoginPage.goto();
  await LoginPage.login(md.login.user);
  await DashboardPage.expectVisible();

  // The M_Cost rows of the product carry the read-only flag the WebUI renders from.
  const rowsResp = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${PRODUKTKOSTEN_WINDOW_ID}/${productId}/${M_COST_TAB_ID}`
  );
  expect(rowsResp.status()).toBe(200);
  const rowsBody = await rowsResp.json();
  const rows = rowsBody.result || [];
  console.log('[TC7] M_Cost rows=' + rows.length);
  expect(rows.length).toBeGreaterThan(0);

  for (const row of rows) {
    const ccp = row.fieldsByName.CurrentCostPrice;
    const fcp = row.fieldsByName.FutureCostPrice;
    console.log(`[TC7] CurrentCostPrice.readonly=${ccp && ccp.readonly} FutureCostPrice.readonly=${fcp && fcp.readonly}`);
    // AC10: both cost fields are read-only.
    expect(ccp.readonly).toBe(true);
    expect(fcp.readonly).toBe(true);
  }

  // UI evidence: open the Produktkosten window and screenshot the cost grid.
  await page.goto(`${FRONTEND_BASE_URL}/window/${PRODUKTKOSTEN_WINDOW_ID}/${productId}`);
  await page.waitForTimeout(4000);
  await page
    .locator('.table-flex-wrapper table tbody tr')
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  allure.attachment('Produktkosten window', await page.screenshot({ fullPage: true }), 'image/png');

  console.log('[TC7] CurrentCostPrice + FutureCostPrice are read-only on window 344');
});
