/**
 * M_Product.CoProductCostDistributionPercent field in the Product Costs window
 *
 * Scope: verify that the CoProductCostDistributionPercent field on M_Product (a manually
 * maintained, overridable percentage share of production-cost distribution for a co-product;
 * blank = 0%, i.e. the co-product carries no cost) is:
 *   1. Rendered on the core Product Costs window (AD_Window_ID=344 "Produktkosten"),
 *      main M_Product tab (AD_Tab_ID=700), inside its element-group panel
 *   2. Editable (not read-only)
 *   3. Persisted after entering a value + save (blur) + page reload
 *   4. Labelled correctly per UI language — the AD_Element_Trl translations (AD_Element 585471):
 *        de_DE / de_CH -> "Co-Product Kostenverteilungsanteil"
 *        en_US         -> "Co-Product Cost Distribution Percent"
 *
 * This is the browser-level coverage leg (renders + editable + persists in the desktop WebUI)
 * that the cucumber / window-designer layers do not provide. Replaces the retired
 * product-coproduct-fixed-cost-price.spec.js, which targeted the removed CoProductFixedCostPrice
 * field on window 140 / tab 180.
 *
 * Core-window note: window 344 is a CORE window present on the core `-preloaded` CI image (the
 * field is placed directly on it by the same migration), so this spec runs on core CI — see
 * e2e/frontend-webui/CLAUDE.md "A spec in a CORE PR must target a CORE window".
 *
 * Language handling: the test runs once per language (de_DE, en_US). Fields are SELECTED only by
 * the language-invariant DB ColumnName (`.form-field-CoProductCostDistributionPercent`) and
 * persistence is verified by the raw WebAPI field value — never by localized text. The label
 * caption IS asserted, but against the expected value FOR THE RUN's language, which is exactly
 * what proves the AD_Element_Trl translation is correct; the expectation varies with the language,
 * so the assertion stays language-independent (it never hardcodes one language's caption for a run
 * in another).
 *
 * Widget type: Number (AD_Reference 22) -> renders as .form-field-CoProductCostDistributionPercent
 * with a plain numeric input; driven via NumericWidget.
 */

import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { NumericWidget } from '../utils/widgets/NumericWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { PRODUCT_COST_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData } from '../utils/WebAPIValidation';

// AD_Column.ColumnName for the new co-product cost-distribution field (language-invariant selector).
const FIELD_NAME = 'CoProductCostDistributionPercent';

// The percentage we enter and expect back after reload.
const TEST_VALUE = 37.5;

// AD_Element_Trl.Name per UI language — the translations under test.
const LABEL_BY_LANGUAGE = {
  de_DE: 'Co-Product Kostenverteilungsanteil',
  en_US: 'Co-Product Cost Distribution Percent',
};

const testCases = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

test.describe('M_Product.CoProductCostDistributionPercent field in Product Costs window', () => {
  testCases.forEach(({ language, label }) => {
    test(`CoProductCostDistributionPercent renders on Product Costs window, is editable and persists (${label})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0380: Masterdata Products');
      allure.tag('F1500: Costing');
      allure.tag('F1500');
      allure.story('CoProductCostDistributionPercent field visible, editable and persists');
      allure.severity('normal');
      allure.description(`
## M_Product.CoProductCostDistributionPercent (${label})

Verifies that the manually maintained co-product cost-distribution percentage field appears on the
Product Costs master-data window (344 / tab 700), is editable, persists a value across save + reload,
and carries the correct ${language} label.
      `);

      const expectedCaption = LABEL_BY_LANGUAGE[language];

      // Create a fresh test user (pinned to this language) + a dedicated test product.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
          products: {
            PROD_COPRODUCT_DISTPCT: {
              name: 'Test Co-Product Cost Distribution Percent',
            },
          },
        },
      });

      // Login (in the pinned language) and open the created product on the Product Costs window.
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      const productId = masterdata.products.PROD_COPRODUCT_DISTPCT.id;
      await page.goto(`/window/${PRODUCT_COST_WINDOW_ID}/${productId}`);
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });
      console.log(`[INFO] (${language}) Navigated to Product Costs detail view: ${page.url()}`);

      // CRITICAL: record must be valid before modifying — otherwise changes silently do not save.
      await assertRecordIsValid(PRODUCT_COST_WINDOW_ID, productId, 'before setting CoProductCostDistributionPercent');

      // === STEP 1: the field renders inside its element-group panel ===
      await test.step('Assert CoProductCostDistributionPercent renders inside its element-group panel', async () => {
        const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
        await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });
        await expect(fieldContainer).toBeVisible();

        // Assert the field sits inside an element-group panel (.panel-spaced). NOTE: this does NOT
        // discriminate the specific "costing" group — every AD_UI_ElementGroup renders the same
        // .panel .panel-spaced classes with no group-identifying name/testid (ElementGroup.js). So
        // this proves only "field is wired into some element group and renders", not "the group is
        // 'costing' specifically". The field<->group placement is verified separately by the
        // migration + the window-designer render check; here we assert render + editability +
        // persistence + per-language label.
        const groupPanel = fieldContainer.locator('xpath=ancestor::div[contains(@class,"panel-spaced")][1]');
        await expect(groupPanel).toBeVisible();
        console.log(`[INFO] (${language}) CoProductCostDistributionPercent field renders inside its element-group panel`);
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
        console.log(`[INFO] (${language}) CoProductCostDistributionPercent input is editable`);
      });

      // === STEP 3b: negative control — a pre-existing tab field stays read-only ===
      // The migration's invariant is two-sided: making tab 700 editable must NOT make its other
      // fields editable (that is the whole point of "Option C"). Assert that a displayed pre-existing
      // field (Description) still renders but is disabled, proving CoProductCostDistributionPercent is
      // the ONLY field that became editable on this otherwise read-only cost-selector tab.
      await test.step('Assert a pre-existing tab field (Description) stays read-only', async () => {
        const readonlyInput = WidgetCommon.getFieldContainer('Description').locator('input, textarea').first();
        await readonlyInput.waitFor({ state: 'visible', timeout: 30000 });
        await expect(readonlyInput).toBeDisabled();
        console.log(`[INFO] (${language}) pre-existing Description field is read-only (disabled) as expected`);
      });

      // === STEP 4: enter a percentage value, save (blur) ===
      await test.step(`Set ${FIELD_NAME} = ${TEST_VALUE} and save`, async () => {
        await NumericWidget.setValue(FIELD_NAME, TEST_VALUE);
        console.log(`[INFO] (${language}) entered ${TEST_VALUE} into CoProductCostDistributionPercent`);
      });

      // === STEP 5: reload and assert persistence via the WebAPI (language-independent) ===
      await test.step('Reload and assert the value persisted', async () => {
        await page.reload();
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: 30000 });

        const fieldContainer = WidgetCommon.getFieldContainer(FIELD_NAME);
        await fieldContainer.waitFor({ state: 'visible', timeout: 30000 });

        // Read the raw persisted value from the WebAPI (system of record), not the displayed text.
        const field = await getFieldData(PRODUCT_COST_WINDOW_ID, productId, FIELD_NAME);
        const rawValue =
          typeof field.value === 'object' && field.value !== null ? field.value.key ?? field.value : field.value;
        console.log(`[INFO] (${language}) CoProductCostDistributionPercent raw value after reload: ${JSON.stringify(field.value)}`);

        expect(Number(rawValue)).toBeCloseTo(TEST_VALUE, 2);

        // Proof screenshot with the field scrolled into view.
        await fieldContainer.scrollIntoViewIfNeeded();
        const screenshotBuffer = await page.screenshot({ fullPage: true });
        await allure.attachment(
          `Product Costs window (${language}) with CoProductCostDistributionPercent after save & reload`,
          screenshotBuffer,
          'image/png',
        );
      });

      console.log(`[PASS] (${language}) CoProductCostDistributionPercent renders on Product Costs window, editable, persists, correct label.`);
    });
  });
});
