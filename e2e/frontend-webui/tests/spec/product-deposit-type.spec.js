/**
 * M_Product.DepositType field in the Product window
 *
 * Scope: verify that the DepositType field on M_Product is:
 *   1. Visible in the Product main tab (AD_Window_ID=140, AD_Tab_ID=180)
 *   2. Selectable from the dropdown by its underlying KEY ("NRC")
 *   3. Persisted after save + page reload
 *
 * The test is language-independent: it selects the NRC option by its
 * `data-testid="option-NRC"` (the dropdown renders the key, not the
 * caption, into data-testid) and verifies persistence by reading the
 * raw field value via the WebAPI rather than the displayed caption.
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
import { ListWidget } from '../utils/widgets/ListWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { PRODUCT_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData } from '../utils/WebAPIValidation';

// AD_Column.ColumnName for the new deposit-type field
const FIELD_NAME = 'DepositType';

// Underlying AD_Ref_List.Value — same in every UI language
const DEPOSIT_TYPE_NRC_VALUE = 'NRC';

test.describe('M_Product.DepositType field in Product window', () => {
  test('DepositType field appears in Product window and persists NRC selection', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.story('me03#29557 DepositType field visible and persists');
    allure.severity('normal');
    allure.description(`
## M_Product.DepositType

Verifies that the DepositType field introduced for the Markant clearing-center
INVOIC integration appears in the Product master data window and that selecting
the NRC value persists after save and page reload.
    `);

    // Create a fresh test user + a dedicated test product (no shared seed data).
    // The `login` block is required so that Backend returns `login.user` for
    // LoginPage.login(); the test deliberately does NOT pin a language —
    // selection happens by option key and assertion by raw field value.
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: {} },
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
      const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });
      await expect(fieldContainer).toBeVisible();
      console.log(`[INFO] DepositType field container is visible`);
    });

    // === STEP 2: Select the NRC option by its key (data-testid="option-NRC") ===
    await test.step(`Set ${FIELD_NAME} to key "${DEPOSIT_TYPE_NRC_VALUE}"`, async () => {
      await ListWidget.setByValue(FIELD_NAME, DEPOSIT_TYPE_NRC_VALUE);
      console.log(`[INFO] Selected option key "${DEPOSIT_TYPE_NRC_VALUE}" from DepositType dropdown`);
    });

    // === STEP 3: Reload page and verify persistence via WebAPI (language-independent) ===
    await test.step('Reload page and assert DepositType raw value is NRC', async () => {
      await page.reload();
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });

      const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });

      // Read the raw field value from the WebAPI — this returns the underlying
      // key, e.g. { key: 'NRC', caption: <localized-caption> } for List widgets.
      const field = await getFieldData(PRODUCT_WINDOW_ID, productId, FIELD_NAME);
      const rawKey = typeof field.value === 'object' && field.value !== null
        ? field.value.key
        : field.value;
      console.log(`[INFO] DepositType raw value after reload: ${JSON.stringify(field.value)}`);

      expect(rawKey).toBe(DEPOSIT_TYPE_NRC_VALUE);

      // Scroll the DepositType field into the viewport BEFORE screenshotting.
      // A fullPage screenshot captures everything in the layout, but elements
      // outside the viewport may not have rendered yet (lazy-mounted form
      // groups, virtualised sections), so they appear blank in the capture.
      // Scrolling into view forces the render and gives a useful proof shot.
      await fieldContainer.scrollIntoViewIfNeeded();

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
