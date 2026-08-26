/**
 * M_Product.ProductLifeCycleStatus (BBS-Status) is NOT exposed in the vanilla Product window
 *
 * Scope: guard the deliberate decision that the life-cycle status field is not part of the
 * standard Product UI (AD_Window_ID=140, AD_Tab_ID=180):
 *   1. The field is not rendered in the Product form
 *   2. The field is not a grid column of the Product list view
 *
 * WHY THIS TEST INVERTED
 * ----------------------
 * It previously asserted the opposite — field visible, selectable, persisted, grid column + filter.
 * Migration 5820370 hides the field on window 140 on purpose: the window already carries an
 * "Auslaufprodukt" (M_Product.Discontinued) checkbox that reads like the same control, and the
 * life-cycle enforcement is wanted by one customer, whose own repo adds the field to the window
 * that customer actually opens. Derivation of the new expectations (per
 * `metasfresh-test-integrity` § "Test Wiring Drift", step 3):
 *   - 5820370 sets AD_UI_Element 652772 IsDisplayed='N'   => the form must NOT render the field
 *   - 5820370 sets AD_UI_Element 652772 IsDisplayedGrid='N' => it must NOT be a grid column
 * The observed CI failure matched that derivation exactly (the old step 1 timed out waiting for a
 * field container that is no longer rendered), so the assertions are updated rather than the change.
 *
 * The filter parameter is deliberately NOT asserted either way. Filter-bar inclusion is driven by
 * AD_Column.IsSelectionColumn (migration 5819940), which is table-level and untouched here, so
 * whether the filter survives a hidden field is not part of this change's contract.
 *
 * WHERE THE REMAINING BEHAVIOUR IS COVERED
 * ----------------------------------------
 * The column, its reference list and every backend guard stay in core, and are exercised by
 * `de.metas.cucumber` productLifeCycleStatus.feature (order-line block + the completion re-checks)
 * and by ProductBL_assertAllowed_Test / PickHUCommand_LifeCycleStatus_Test. The field's visibility
 * in the customer's own Product window cannot be tested here — that window does not exist on a core
 * DB — so no automated core check covers it.
 */

import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { PRODUCT_WINDOW_ID } from '../utils/WindowIds';
import {
  assertRecordIsValid,
  getViewLayout,
  getViewLayoutColumnNames,
  getViewLayoutFilterParameterNames,
} from '../utils/WebAPIValidation';

// AD_Column.ColumnName for the life-cycle status field
const FIELD_NAME = 'ProductLifeCycleStatus';

test.describe('M_Product.ProductLifeCycleStatus field in Product window', () => {
  test('BBS-Status is not exposed in the vanilla Product window', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.tag('F6000');  // Standalone tag for Tags section
    allure.story('Product life-cycle status (BBS-Status) is not part of the standard Product UI');
    allure.severity('normal');
    allure.description(`
## M_Product.ProductLifeCycleStatus (BBS-Status)

Verifies that the product life-cycle status field is NOT offered in the standard Product
window — neither in the form nor as a grid column. The column and its backend enforcement
remain in core; only the vanilla UI exposure is removed (migration 5820370).
    `);

    // Create a fresh test user + a dedicated test product (no shared seed data).
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: {} },
        products: {
          PROD_LIFECYCLE: {
            name: 'Test Life Cycle Status Product',
          },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate directly to the created test product to avoid row-0 pollution
    const productId = masterdata.products.PROD_LIFECYCLE.id;
    await page.goto(`/window/${PRODUCT_WINDOW_ID}/${productId}`);
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
    console.log(`[INFO] Navigated to product detail view: ${page.url()}`);

    // The record must load cleanly — otherwise "field absent" would prove nothing.
    await assertRecordIsValid(PRODUCT_WINDOW_ID, productId, 'Product record loaded');

    // === STEP 1: the form must not render the field ===
    await test.step('Assert ProductLifeCycleStatus is absent from the Product form', async () => {
      // A control the window DOES render, to prove the form itself is up before asserting an absence.
      const referenceField = WidgetCommon.getFieldContainer('Name');
      await referenceField.waitFor({ state: 'visible', timeout: 30000 });

      // Built explicitly rather than via WidgetCommon.getFieldContainer, which appends .first():
      // an absence check must count EVERY match, not just the first.
      const fieldLocator = page.locator(`#lookup_${FIELD_NAME}, .form-field-${FIELD_NAME}`);
      await expect(fieldLocator, `${FIELD_NAME} must not be rendered on window ${PRODUCT_WINDOW_ID}`)
          .toHaveCount(0);
      console.log(`[INFO] ${FIELD_NAME} is not rendered in the Product form, as intended`);

      const screenshotBuffer = await page.screenshot({ fullPage: true });
      await allure.attachment(
          'Product window without the BBS-Status field',
          screenshotBuffer,
          'image/png',
      );
    });

    // === STEP 2: the list view must not offer it as a grid column ===
    await test.step('Assert BBS-Status is not a grid column of the Product list view', async () => {
      const layout = await getViewLayout(PRODUCT_WINDOW_ID, 'grid');

      const columnNames = getViewLayoutColumnNames(layout);
      console.log(`[INFO] Grid columns of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(columnNames)}`);
      expect(columnNames, `grid columns of window ${PRODUCT_WINDOW_ID}`).not.toContain(FIELD_NAME);

      // Logged, not asserted — see the header: filter inclusion is column-driven
      // (AD_Column.IsSelectionColumn) and is not part of this change's contract.
      const filterParameterNames = getViewLayoutFilterParameterNames(layout);
      console.log(`[INFO] Filter parameters of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(filterParameterNames)}`);
    });

    console.log('[PASS] ProductLifeCycleStatus is not exposed in the vanilla Product window.');
  });
});
