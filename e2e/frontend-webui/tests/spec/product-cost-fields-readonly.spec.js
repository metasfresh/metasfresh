import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { PRODUCT_COST_M_COST_TAB_ID, PRODUCT_COST_WINDOW_ID, ProductCostPage } from '../utils/pages/ProductCostPage';

/**
 * Product Cost (Produktkosten, window 344) — legacy M_Cost cost fields are READ-ONLY.
 *
 * The migration flips AD_Field.IsReadOnly='Y' for CurrentCostPrice (11350) and FutureCostPrice (11352)
 * on the M_Cost tab (701) of window 344, so a user can no longer edit a product's cost price directly
 * and bypass the audited Kosten Neubewertung path. This is a GLOBAL CORE change (all customers).
 *
 * Asserted on the language-invariant `readonly` flag that the WebUI reads to render the fields read-only
 * (both in the grid and in single-row), plus a UI attempt to edit Current Cost Price that must be refused.
 * That the audited Kosten Neubewertung document remains the working path to change a cost price is covered
 * by cost-revaluation-quickinput.spec.js.
 */

/**
 * The Standard costing cost element of the standard setup. Selected by its name:
 * M_CostElement.Name is master data without translations, so it is the same in every login language.
 */
const STANDARD_COSTING_COST_ELEMENT_NAME = 'Standard Costing';

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  // eslint-disable-next-line no-unused-vars
  test(`Product Cost window 344 CurrentCostPrice + FutureCostPrice are read-only (${label})`, async ({ page }) => {
    test.setTimeout(120000);
    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('Product Cost cost fields locked read-only');
    allure.severity('critical');
    allure.description(`
## F1500: Costing — Product Cost (Produktkosten, window 344) cost fields read-only

A normally-created product carries its default M_Cost rows. On the M_Cost tab of Product Cost
(Produktkosten, window 344), the Current Cost Price and Future Cost Price fields must be read-only for
every costing method, including Standard costing, so a user cannot edit a cost price directly (bypassing
the audited Kosten Neubewertung path). Asserted on the language-invariant \`readonly\` flag the WebUI
renders from, plus a UI attempt to edit Current Cost Price that is refused.
    `);

    const md = await Backend.createMasterdata({
      request: {
        login: { user: { language, firstname: 'PK', lastname: 'ReadOnly' } },
        products: { P: { name: 'PK_PROD', type: 'Item' } },
      },
    });
    const productId = md.products.P.id;

    await LoginPage.goto();
    await LoginPage.login(md.login.user);
    await DashboardPage.expectVisible();

    // The M_Cost rows of the product carry the read-only flag the WebUI renders from.
    const rowsResp = await page.request.get(
      `${WEBAPI_BASE_URL}/window/${PRODUCT_COST_WINDOW_ID}/${productId}/${PRODUCT_COST_M_COST_TAB_ID}`
    );
    expect(rowsResp.status()).toBe(200);
    const rowsBody = await rowsResp.json();
    const rows = rowsBody.result || [];
    expect(rows.length).toBeGreaterThan(0);

    for (const row of rows) {
      const ccp = row.fieldsByName.CurrentCostPrice;
      const fcp = row.fieldsByName.FutureCostPrice;
      // Both cost fields are read-only.
      expect(ccp.readonly).toBe(true);
      expect(fcp.readonly).toBe(true);
    }

    // The column's own read-only logic already locks both fields for every costing method except Standard costing,
    // so the Standard costing row is the one that proves the lockdown.
    const standardCostingRows = rows.filter(
      (row) =>
        row.fieldsByName.M_CostElement_ID && row.fieldsByName.M_CostElement_ID.value.caption === STANDARD_COSTING_COST_ELEMENT_NAME
    );
    expect(standardCostingRows, 'the product has a Standard costing cost row').toHaveLength(1);
    const standardCostingRowId = String(standardCostingRows[0].rowId);

    // UI: the user tries to edit Current Cost Price of the Standard costing row; the cell is read-only and keeps its value.
    await ProductCostPage.open(productId);
    await ProductCostPage.attemptEditCurrentCostPrice(standardCostingRowId, '999');
    allure.attachment('Product Cost (Produktkosten, window 344)', await page.screenshot({ fullPage: true }), 'image/png');

  });
});
