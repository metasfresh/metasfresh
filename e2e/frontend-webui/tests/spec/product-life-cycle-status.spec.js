/**
 * M_Product.ProductLifeCycleStatus (BBS-Status) field in the Product window
 *
 * Scope: verify that the ProductLifeCycleStatus field on M_Product is:
 *   1. Visible in the Product main tab (AD_Window_ID=140, AD_Tab_ID=180)
 *   2. Selectable from the dropdown by its underlying KEY ("G" = Gesperrt/Blocked)
 *   3. Persisted after save + page reload
 *   4. Exposed by the list view as a grid column AND as a filter parameter
 *   5. Rendered as a grid column in the DOM
 *
 * The test is language-independent: it selects the option by its
 * `data-testid="option-G"` (the dropdown renders the key, not the caption) and
 * verifies persistence by reading the raw field value via the WebAPI rather than
 * the displayed caption.
 *
 * Reference list values: O (OK) / A (Auslauf) / G (Gesperrt) / N (Lieferstopp).
 * Widget type: List (renders as #lookup_ProductLifeCycleStatus dropdown).
 * "G" is chosen because the column default is "O", so selecting "G" proves a
 * changed value is persisted (not just the default).
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
import {
  assertRecordIsValid,
  getFieldData,
  getViewLayout,
  getViewLayoutColumnNames,
  getViewLayoutFilterParameterNames,
} from '../utils/WebAPIValidation';
import { navigateToViewWindow } from '../utils/view-validation/ViewWindowHelper';

// AD_Column.ColumnName for the life-cycle status field
const FIELD_NAME = 'ProductLifeCycleStatus';

// Underlying AD_Ref_List.Value — same in every UI language ("Gesperrt")
const LIFE_CYCLE_STATUS_BLOCKED_VALUE = 'G';

test.describe('M_Product.ProductLifeCycleStatus field in Product window', () => {
  test('BBS-Status field appears in Product window and persists a Gesperrt selection', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0380: Masterdata Products');
    allure.tag('F6000: Maintain Product Data');
    allure.tag('F6000');  // Standalone tag for Tags section
    allure.story('Product life-cycle status (BBS-Status) field visible and persists');
    allure.severity('normal');
    allure.description(`
## M_Product.ProductLifeCycleStatus (BBS-Status)

Verifies that the product life-cycle status field appears in the Product master
data window and that selecting "Gesperrt" (G) persists after save and page reload.
    `);

    // Create a fresh test user + a dedicated test product (no shared seed data).
    // No language is pinned — selection happens by option key and assertion by
    // raw field value, so the test is language-independent.
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

    // CRITICAL: assert record is valid before modifying — if valid=false changes will not be saved
    await assertRecordIsValid(PRODUCT_WINDOW_ID, productId, 'before setting ProductLifeCycleStatus');

    // === STEP 1: Verify the field is visible ===
    await test.step('Assert ProductLifeCycleStatus field is present in the form', async () => {
      const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });
      await expect(fieldContainer).toBeVisible();
      console.log(`[INFO] ProductLifeCycleStatus field container is visible`);
    });

    // === STEP 2: Select the Gesperrt option by its key (data-testid="option-G") ===
    await test.step(`Set ${FIELD_NAME} to key "${LIFE_CYCLE_STATUS_BLOCKED_VALUE}"`, async () => {
      await ListWidget.setByValue(FIELD_NAME, LIFE_CYCLE_STATUS_BLOCKED_VALUE);
      console.log(`[INFO] Selected option key "${LIFE_CYCLE_STATUS_BLOCKED_VALUE}" from ProductLifeCycleStatus dropdown`);
    });

    // === STEP 3: Reload page and verify persistence via WebAPI (language-independent) ===
    await test.step('Reload page and assert ProductLifeCycleStatus raw value is G', async () => {
      await page.reload();
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });

      const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
      await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });

      // Read the raw field value from the WebAPI — this returns the underlying
      // key, e.g. { key: 'G', caption: <localized-caption> } for List widgets.
      const field = await getFieldData(PRODUCT_WINDOW_ID, productId, FIELD_NAME);
      const rawKey = typeof field.value === 'object' && field.value !== null
        ? field.value.key
        : field.value;
      console.log(`[INFO] ProductLifeCycleStatus raw value after reload: ${JSON.stringify(field.value)}`);

      expect(rawKey).toBe(LIFE_CYCLE_STATUS_BLOCKED_VALUE);

      // Scroll the field into the viewport before screenshotting so the proof
      // shot actually shows the rendered field (lazy-mounted form groups).
      await fieldContainer.scrollIntoViewIfNeeded();

      const screenshotBuffer = await page.screenshot({ fullPage: true });
      await allure.attachment(
          'Product window with ProductLifeCycleStatus field after save & reload',
          screenshotBuffer,
          'image/png',
      );
    });

    // === STEP 4: list view must expose the field as a grid column AND as a filter ===
    await test.step('Assert BBS-Status is a grid column and a filter of the Product list view', async () => {
      const layout = await getViewLayout(PRODUCT_WINDOW_ID, 'grid');

      const columnNames = getViewLayoutColumnNames(layout);
      console.log(`[INFO] Grid columns of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(columnNames)}`);
      expect(columnNames, `grid columns of window ${PRODUCT_WINDOW_ID}`).toContain(FIELD_NAME);

      // Filter-bar inclusion is driven by AD_Column.IsSelectionColumn — NOT by
      // AD_UI_Element.IsAllowFiltering, which the backend only honours for the Labels widget
      // (AD_UI_ElementType='L') and which is therefore inert on this plain field ('F').
      // Asserting the descriptor the backend actually builds is what discriminates the two:
      // with IsSelectionColumn='N' the field is absent from filters[] even though
      // IsAllowFiltering='Y', so this assertion fails without migration 5819940.
      const filterParameterNames = getViewLayoutFilterParameterNames(layout);
      console.log(`[INFO] Filter parameters of window ${PRODUCT_WINDOW_ID}: ${JSON.stringify(filterParameterNames)}`);
      expect(filterParameterNames, `filter parameters of window ${PRODUCT_WINDOW_ID}`).toContain(FIELD_NAME);
    });

    // === STEP 5: the grid column also renders in the DOM ===
    await test.step('Assert the BBS-Status grid column renders in the Product list view', async () => {
      await navigateToViewWindow(PRODUCT_WINDOW_ID);

      const header = page.locator(`th[data-testid="column-${FIELD_NAME}"]`);
      await expect(header, `grid column header for ${FIELD_NAME}`).toHaveCount(1);

      const screenshotBuffer = await page.screenshot({ fullPage: true });
      await allure.attachment(
          'Product list view with the BBS-Status grid column',
          screenshotBuffer,
          'image/png',
      );
    });

    console.log('[PASS] ProductLifeCycleStatus field visible, selectable, persists, and is a grid column + filter.');
  });
});
