/**
 * me03#29557 — M_Product.DepositType field in Product window
 *
 * Scope: Verify that the new DepositType field introduced by
 * migration 58023600_sys_me03_29557_M_Product_DepositType.sql is:
 *   1. Visible in the Product main tab (AD_Window_ID=140, AD_Tab_ID=180)
 *   2. Populated via the dropdown (the test runs in de_DE so the displayed
 *      label is the German translation of NRC)
 *   3. Persisted after save + page reload
 *
 * AD_Reference_ID: 542089 — List reference with values NRC / RC
 * Widget type: List (renders as #lookup_DepositType dropdown)
 *
 * Issue: https://github.com/metasfresh/me03/issues/29557
 */

import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { ListWidget } from '../utils/widgets/ListWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { PRODUCT_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid } from '../utils/WebAPIValidation';

// AD_Column.ColumnName for the new deposit-type field
const FIELD_NAME = 'DepositType';

// The UI runs in de_DE so the dropdown shows the German translation of NRC.
// This is the literal UI label the user sees and the test clicks on.
const DEPOSIT_TYPE_NRC_LABEL = 'Einwegpfand';

test.describe('me03#29557 — M_Product.DepositType field in Product window', () => {
  test('DepositType field appears in Product window and persists Einwegpfand selection', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.story('me03#29557 DepositType field visible and persists');
    allure.severity('normal');
    allure.description(`
## me03#29557 — M_Product.DepositType

Verifies that the new DepositType field introduced for the Markant
clearing-center INVOIC integration appears in the Product master data window
and that selecting the NRC value persists after save and page reload.
    `);

    // Create a fresh test user (de_DE so dropdown shows German labels)
    // and a dedicated test product to avoid touching shared seed data.
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE' } },
        products: {
          PROD_DEPOSITTYPE: {
            name: 'Test Deposit Type Product',
          },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate directly to the created test product to avoid row-0 pollution
    const productId = masterdata.products.PROD_DEPOSITTYPE.id;
    await page.goto(`/window/${PRODUCT_WINDOW_ID}/${productId}`);
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
    console.log(`[INFO] Navigated to product detail view: ${page.url()}`);

    // CRITICAL: assert record is valid before modifying — if valid=false changes will not be saved
    await assertRecordIsValid(PRODUCT_WINDOW_ID, productId, 'before setting DepositType');

    // === STEP 1: Verify DepositType field is visible ===
    await test.step('Assert DepositType field is present in the form', async () => {
      // List widgets render as: #lookup_DepositType (the container div)
      // with an <input> inside for display, and a dropdown trigger.
      const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });
      await expect(fieldContainer).toBeVisible();
      console.log(`[INFO] DepositType field container is visible`);
    });

    // === STEP 2: Select "Einwegpfand" from the dropdown ===
    await test.step(`Set ${FIELD_NAME} to "${DEPOSIT_TYPE_NRC_LABEL}"`, async () => {
      await ListWidget.setValue(FIELD_NAME, DEPOSIT_TYPE_NRC_LABEL, { exactMatch: true });
      console.log(`[INFO] Selected "${DEPOSIT_TYPE_NRC_LABEL}" from DepositType dropdown`);
    });

    // ListWidget.setValue already calls triggerBlur + waitForSaveComplete internally — no extra step needed.

    // === STEP 4: Reload page and verify persistence ===
    await test.step('Reload page and assert DepositType still shows Einwegpfand', async () => {
      await page.reload();
      // Wait for the detail view to re-render
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });

      // Wait for the field to be visible again after reload
      const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });

      // Read back the displayed value
      const persistedValue = await ListWidget.getValue(FIELD_NAME);
      console.log(`[INFO] DepositType value after reload: "${persistedValue}"`);

      expect(persistedValue).toBe(DEPOSIT_TYPE_NRC_LABEL);

      // Attach a screenshot of the Product window after reload so reviewers
      // can confirm visually that the DepositType field is wired up and
      // shows the persisted value. The Allure attachment + a written file
      // give us both an in-report artefact and a debuggable local file.
      const screenshotBuffer = await page.screenshot({ fullPage: true });
      await allure.attachment(
          'Product window with DepositType field after save & reload',
          screenshotBuffer,
          'image/png',
      );
    });

    console.log('[PASS] DepositType field visible, selectable, and persists correctly.');
  });
});
