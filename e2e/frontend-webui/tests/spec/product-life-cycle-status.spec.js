/**
 * M_Product.ProductLifeCycleStatus (BBS-Status) is deliberately NOT part of the vanilla Product
 * window (AD_Window_ID=140, AD_Tab_ID=180). This spec guards that: the field must be absent from
 * the form, from the grid, and from the filter bar.
 *
 * Migration 5820370 hid it there on purpose -- the window already carries an "Auslaufprodukt"
 * (M_Product.Discontinued) checkbox that reads like the same control, and the enforcement is wanted
 * by one customer, whose own repo puts the field on the window that customer actually opens. 5820780
 * then removed the leftover filter entry. The column, its reference list and every backend guard stay
 * in core, covered by de.metas.cucumber productLifeCycleStatus.feature and ProductBL_assertAllowed_Test
 * / PickHUCommand_LifeCycleStatus_Test.
 *
 * The filter assertion guards AD_Field.IsFilterField (window-scoped), NOT AD_Column.IsSelectionColumn
 * (table-level, still on so the customer's own window keeps its quick filter).
 *
 * Running this locally: reset the SqlViewLayouts and SqlViewBindings caches first, or the app server
 * keeps serving the pre-migration layout. Neither cache is keyed to a table, so no AD_* change
 * invalidates them and an unreset run will contradict the migration.
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

    // === STEP 2: the grid must not list the column, the filter bar must not offer it ===
    await test.step('Assert ProductLifeCycleStatus is absent from the Product grid and filters', async () => {
      const layout = await getViewLayout(PRODUCT_WINDOW_ID, 'grid');

      const columnNames = getViewLayoutColumnNames(layout);
      console.log(`[INFO] Grid columns of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(columnNames)}`);
      expect(columnNames, `${FIELD_NAME} must not be a grid column of window ${PRODUCT_WINDOW_ID}`)
          .not.toContain(FIELD_NAME);

      const filterParameterNames = getViewLayoutFilterParameterNames(layout);
      console.log(`[INFO] Filter parameters of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(filterParameterNames)}`);
      expect(filterParameterNames, `${FIELD_NAME} must not be a filter of window ${PRODUCT_WINDOW_ID}`)
          .not.toContain(FIELD_NAME);
    });

    console.log('[PASS] ProductLifeCycleStatus is not rendered in the vanilla Product form.');
  });
});
