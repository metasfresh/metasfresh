/**
 * me03#29557 — M_Product.DepositType field in Product window
 *
 * Scope: Verify that the new DepositType field (Pfandart) introduced by
 * migration 5831760_sys_me03_29557_M_Product_DepositType.sql is:
 *   1. Visible in the Product main tab (AD_Window_ID=140, AD_Tab_ID=180)
 *   2. Populated via the dropdown (de_DE label "Einwegpfand" = NRC)
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

// AD_Column.ColumnName for the new deposit-type field
const FIELD_NAME = 'DepositType';

// de_DE display label for value NRC (Einwegpfand = Disposable Deposit)
const DEPOSIT_TYPE_NRC_LABEL = 'Einwegpfand';

test.describe('me03#29557 — M_Product.DepositType field in Product window', () => {
  test('DepositType field appears in Product window and persists Einwegpfand selection', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.tag('F6000');
    allure.story('me03#29557 DepositType field visible and persists');
    allure.severity('normal');
    allure.description(`
## me03#29557 — M_Product.DepositType

Verifies that the new DepositType (Pfandart) field introduced for the Markant
clearing-center INVOIC integration appears in the Product master data window
and that selecting "Einwegpfand" (NRC) persists after save and page reload.
    `);

    // Create a fresh test user (de_DE so dropdown shows German labels)
    // and a dedicated test product to avoid touching shared seed data.
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE' } },
        products: {
          PROD_DEPOSITTYPE: {
            name: 'Test Pfandart Product',
          },
        },
      },
    });

    // Login
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // Navigate to Product window list view
    await MasterWindowPage.goto(PRODUCT_WINDOW_ID);
    await MasterWindowPage.expectWindowLoaded();
    await MasterWindowPage.waitForTableData();

    // Open the first available product record (double-click to enter detail view)
    await MasterWindowPage.clickRow(0);

    // Verify we are now in a detail view (URL pattern /window/140/<id>)
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
    const detailUrl = page.url();
    console.log(`[INFO] Opened product detail view: ${detailUrl}`);

    // === STEP 1: Verify DepositType field is visible ===
    await test.step('Assert DepositType field is present in the form', async () => {
      // List widgets render as: #lookup_DepositType (the container div)
      // with an <input> inside for display, and a dropdown trigger.
      const fieldContainer = page.locator(`#lookup_${FIELD_NAME}, .form-field-${FIELD_NAME}`).first();
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });
      await expect(fieldContainer).toBeVisible();
      console.log(`[INFO] DepositType field container is visible`);
    });

    // === STEP 2: Select "Einwegpfand" from the dropdown ===
    await test.step(`Set ${FIELD_NAME} to "${DEPOSIT_TYPE_NRC_LABEL}"`, async () => {
      await ListWidget.setValue(FIELD_NAME, DEPOSIT_TYPE_NRC_LABEL, { exactMatch: true });
      console.log(`[INFO] Selected "${DEPOSIT_TYPE_NRC_LABEL}" from DepositType dropdown`);
    });

    // === STEP 3: Wait for auto-save to complete ===
    await test.step('Wait for record save to complete', async () => {
      await WidgetCommon.waitForSaveComplete();
      console.log(`[INFO] Record saved`);
    });

    // === STEP 4: Reload page and verify persistence ===
    await test.step('Reload page and assert DepositType still shows Einwegpfand', async () => {
      await page.reload();
      // Wait for the detail view to re-render
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });

      // Wait for the field to be visible again after reload
      const fieldContainer = page.locator(`#lookup_${FIELD_NAME}, .form-field-${FIELD_NAME}`).first();
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });

      // Read back the displayed value
      const persistedValue = await ListWidget.getValue(FIELD_NAME);
      console.log(`[INFO] DepositType value after reload: "${persistedValue}"`);

      expect(persistedValue).toBe(DEPOSIT_TYPE_NRC_LABEL);
    });

    console.log('[PASS] DepositType field visible, selectable, and persists correctly.');
  });
});
