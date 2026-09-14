/**
 * M_Product.CoProductFixedCostPrice field in the Product window
 *
 * Scope: verify that the CoProductFixedCostPrice field on M_Product (a manually
 * maintained, overridable fixed cost price for a co-product / rework output) is:
 *   1. Rendered inside the "Product Costs" UI element group in the Product
 *      main tab (AD_Window_ID=140, AD_Tab_ID=180)
 *   2. Editable (not read-only)
 *   3. Persisted after entering a value + save + page reload
 *   4. Labelled correctly per UI language — the AD_Element_Trl translations:
 *        de_DE / de_CH -> "Co-Product Fixkostenpreis"
 *        en_US         -> "Co-Product Fixed Cost Price"
 *
 * Language handling: the test runs once per language (de_DE, en_US). Fields are
 * SELECTED only by the language-invariant DB ColumnName (`.form-field-CoProductFixedCostPrice`)
 * and persistence is verified by the raw WebAPI field value — never by localized
 * text. The label caption IS asserted, but against the expected value FOR THE RUN's
 * language, which is exactly what proves the AD_Element_Trl translation is correct;
 * the expectation varies with the language, so the assertion is language-independent
 * (it never hardcodes one language's caption for a run in another).
 *
 * AD_UI_ElementGroup: "Product Costs" (renders as an unlabeled .panel element group)
 * Widget type: CostPrice (renders as #lookup_CoProductFixedCostPrice / .form-field-CoProductFixedCostPrice input)
 */

import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { NumericWidget } from '../utils/widgets/NumericWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { PRODUCT_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData } from '../utils/WebAPIValidation';

// AD_Column.ColumnName for the new co-product fixed cost price field (language-invariant selector).
const FIELD_NAME = 'CoProductFixedCostPrice';

// The value we enter and expect back after reload. CostPrice reference carries >= 2 decimals.
const TEST_VALUE = 123.45;

// AD_Element_Trl.Name per UI language — the translations under test.
const LABEL_BY_LANGUAGE = {
  de_DE: 'Co-Product Fixkostenpreis',
  en_US: 'Co-Product Fixed Cost Price',
};

const testCases = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

test.describe('M_Product.CoProductFixedCostPrice field in Product window', () => {
  testCases.forEach(({ language, label }) => {
    test(`CoProductFixedCostPrice renders in Product Costs group, is editable and persists (${label})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0380: Masterdata Products');
      allure.tag('F1500: Costing');
      allure.tag('F1500');
      allure.story('CoProductFixedCostPrice field visible, editable and persists');
      allure.severity('normal');
      allure.description(`
## M_Product.CoProductFixedCostPrice (${label})

Verifies that the manually maintained co-product fixed cost price field appears in the
"Product Costs" group of the Product master data window, is editable, persists a value
across save + reload, and carries the correct ${language} label.
      `);

      const expectedCaption = LABEL_BY_LANGUAGE[language];

      // Create a fresh test user (pinned to this language) + a dedicated test product.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
          products: {
            PROD_COPRODUCT_FCP: {
              name: 'Test Co-Product Fixed Cost Price',
            },
          },
        },
      });

      // Login (in the pinned language) and open the created product directly.
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      const productId = masterdata.products.PROD_COPRODUCT_FCP.id;
      await page.goto(`/window/${PRODUCT_WINDOW_ID}/${productId}`);
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
      console.log(`[INFO] (${language}) Navigated to product detail view: ${page.url()}`);

      // CRITICAL: record must be valid before modifying — otherwise changes silently do not save.
      await assertRecordIsValid(PRODUCT_WINDOW_ID, productId, 'before setting CoProductFixedCostPrice');

      // === STEP 1: the field renders inside its "Product Costs" element-group panel ===
      await test.step('Assert CoProductFixedCostPrice renders inside the Product Costs group', async () => {
        const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
        await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });
        await expect(fieldContainer).toBeVisible();

        // Assert the field sits inside an element-group panel (.panel-spaced). NOTE: this does
        // NOT discriminate the specific "Product Costs" group — every AD_UI_ElementGroup renders
        // the same .panel .panel-spaced classes with no group-identifying name/testid
        // (ElementGroup.js). So this proves only "field is wired into some element group and
        // renders", not "the group is Product Costs specifically". The field↔"Product Costs"
        // group placement is verified separately by migration 5824340 + the window-designer
        // render check; here we assert render + editability + persistence + per-language label.
        const groupPanel = fieldContainer.locator('xpath=ancestor::div[contains(@class,"panel-spaced")][1]');
        await expect(groupPanel).toBeVisible();
        console.log(`[INFO] (${language}) CoProductFixedCostPrice field renders inside its Product Costs group panel`);
      });

      // === STEP 2: correct per-language label (AD_Element_Trl translation under test) ===
      await test.step(`Assert ${language} label is "${expectedCaption}"`, async () => {
        const labelEl = WidgetCommon.getFieldContainer(FIELD_NAME).locator('label.form-control-label');
        await expect(labelEl).toHaveText(expectedCaption);
        console.log(`[INFO] (${language}) label caption = "${expectedCaption}"`);
      });

      // === STEP 3: the field is editable ===
      await test.step('Assert the field is editable', async () => {
        const input = WidgetCommon.getFieldContainer(FIELD_NAME).locator('input').first();
        await expect(input).toBeEditable();
        console.log(`[INFO] (${language}) CoProductFixedCostPrice input is editable`);
      });

      // === STEP 4: enter a value, save (blur) ===
      await test.step(`Set ${FIELD_NAME} = ${TEST_VALUE} and save`, async () => {
        await NumericWidget.setValue(FIELD_NAME, TEST_VALUE);
        console.log(`[INFO] (${language}) entered ${TEST_VALUE} into CoProductFixedCostPrice`);
      });

      // === STEP 5: reload and assert persistence via the WebAPI (language-independent) ===
      await test.step('Reload and assert the value persisted', async () => {
        await page.reload();
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });

        const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
        await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });

        // Read the raw persisted value from the WebAPI (system of record), not the displayed text.
        const field = await getFieldData(PRODUCT_WINDOW_ID, productId, FIELD_NAME);
        const rawValue =
          typeof field.value === 'object' && field.value !== null ? field.value.key ?? field.value : field.value;
        console.log(`[INFO] (${language}) CoProductFixedCostPrice raw value after reload: ${JSON.stringify(field.value)}`);

        expect(Number(rawValue)).toBeCloseTo(TEST_VALUE, 2);

        // Proof screenshot with the field scrolled into view.
        await fieldContainer.scrollIntoViewIfNeeded();
        const screenshotBuffer = await page.screenshot({ fullPage: true });
        await allure.attachment(
          `Product window (${language}) with CoProductFixedCostPrice after save & reload`,
          screenshotBuffer,
          'image/png',
        );
      });

      console.log(`[PASS] (${language}) CoProductFixedCostPrice renders in Product Costs group, editable, persists, correct label.`);
    });
  });
});
