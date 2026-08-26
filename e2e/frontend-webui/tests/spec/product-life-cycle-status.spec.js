/**
 * M_Product.ProductLifeCycleStatus (BBS-Status) is NOT rendered in the vanilla Product form
 *
 * Scope: guard the deliberate decision that the life-cycle status field is not offered in the
 * standard Product FORM (AD_Window_ID=140, AD_Tab_ID=180):
 *   1. The field is not rendered in the Product form
 *
 * WHY THIS TEST INVERTED
 * ----------------------
 * It previously asserted the opposite — field visible, selectable, persisted, grid column + filter.
 * Migration 5820370 hides the field on window 140 on purpose: the window already carries an
 * "Auslaufprodukt" (M_Product.Discontinued) checkbox that reads like the same control, and the
 * life-cycle enforcement is wanted by one customer, whose own repo adds the field to the window
 * that customer actually opens. Derivation of the new expectations (per
 * `metasfresh-test-integrity` § "Test Wiring Drift", step 3):
 *   - 5820370 sets AD_UI_Element 652772 IsDisplayed='N' => the form must NOT render the field
 * The observed CI failure matched that derivation exactly (the old step 1 timed out waiting for a
 * field container that is no longer rendered), so the assertion is updated rather than the change.
 *
 * The grid column and the filter parameter are LOGGED, NOT ASSERTED, because a local run against a
 * DB with 5820370 applied showed the grid layout of window 140 STILL lists ProductLifeCycleStatus
 * even with both IsDisplayedGrid='N' and IsDisplayed='N' on the UI element (and still lists it with
 * that element deactivated). So the grid descriptor is not driven by the flags this migration sets,
 * and the filter is column-driven (AD_Column.IsSelectionColumn, migration 5819940, untouched here).
 * Neither is part of this change's contract; asserting either way would be a guess.
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
  test('BBS-Status is not rendered in the vanilla Product form', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.tag('F6000');  // Standalone tag for Tags section
    allure.story('Product life-cycle status (BBS-Status) is not part of the standard Product UI');
    allure.severity('normal');
    allure.description(`
## M_Product.ProductLifeCycleStatus (BBS-Status)

Verifies that the product life-cycle status field is NOT offered in the standard Product
window form. The column and its backend enforcement remain in core; only the form exposure is
removed (migration 5820370).
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

    // === STEP 2: record what the list view still exposes (observational) ===
    await test.step('Record the Product list view grid columns and filter parameters', async () => {
      const layout = await getViewLayout(PRODUCT_WINDOW_ID, 'grid');

      // Both LOGGED, not asserted — see the header. Verified locally: the grid layout still lists
      // the column with 5820370 applied, so neither observation is part of this change's contract.
      const columnNames = getViewLayoutColumnNames(layout);
      console.log(`[INFO] Grid columns of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(columnNames)}`);
      const filterParameterNames = getViewLayoutFilterParameterNames(layout);
      console.log(`[INFO] Filter parameters of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(filterParameterNames)}`);
    });

    console.log('[PASS] ProductLifeCycleStatus is not rendered in the vanilla Product form.');
  });
});
