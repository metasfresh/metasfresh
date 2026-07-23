import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import {
  FRONTEND_BASE_URL,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from '../utils/common';

/**
 * Cost Revaluation window ("Kosten Neubewertung") — RevaluationSource / CopyFrom_M_CostElement_ID
 * display + mandatory logic. AD_Window_ID = 541568, header tab AD_Tab_ID = 546464.
 *
 * The field `CopyFrom_M_CostElement_ID` carries:
 *   - DisplayLogic   @RevaluationSource@=CopyFromCostElement   → shown ONLY when the source is
 *                                                                 CopyFromCostElement
 *   - MandatoryLogic @RevaluationSource@='CopyFromCostElement' → mandatory in that same state
 * `RevaluationSource` is a List with values `Calculated` (default) and `CopyFromCostElement`.
 *
 * What is asserted (the core toggle):
 *   1. NEW record, RevaluationSource = Calculated (default) → CopyFrom field NOT rendered.
 *      (A DisplayLogic-hidden field is dropped from the DOM entirely — RawWidget renders nothing
 *       when the field's `displayed !== true` — so the assertion is "wrapper absent", not "hidden".)
 *   2. Set RevaluationSource = CopyFromCostElement → CopyFrom field becomes visible AND is marked
 *      mandatory (empty mandatory List renders the language-invariant `.input-mandatory` marker).
 *   3. Set RevaluationSource back to Calculated → CopyFrom field hides again.
 *
 * Language-independence: every selector/assertion uses language-invariant identifiers — the DB
 * ColumnName (`.form-field-<Column>`), the ref-list VALUE as the option key
 * (`[data-testid="option-<Value>"]`, since SelectionDropdown sets data-testid = `option-${key}`),
 * and the `.input-mandatory` structural class. Run in en_US + de_DE to prove it.
 */

const COST_REVALUATION_WINDOW_ID = 541568;

// Language-invariant selectors — DB ColumnName based (RawWidget `.form-field-{ColumnName}` wrapper).
const REVALUATION_SOURCE_WRAPPER = '.form-field-RevaluationSource';
const REVALUATION_SOURCE_DROPDOWN = `${REVALUATION_SOURCE_WRAPPER} .input-dropdown-container`;
const COPY_FROM_WRAPPER = '.form-field-CopyFrom_M_CostElement_ID';
// Empty mandatory List/Lookup renders `.input-mandatory` (RawList / Lookup) — language-invariant.
const COPY_FROM_MANDATORY = `${COPY_FROM_WRAPPER} .input-mandatory`;

// SelectionDropdown option data-testid = `option-${key}`; the List option key is the ref-list
// VALUE, which is language-invariant (the ref-list value doesn't change per language).
const optionByKey = (key) => `.input-dropdown-list-option[data-testid="option-${key}"]`;

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

// Open the RevaluationSource List and pick the option whose ref-list value === `valueKey`.
async function selectRevaluationSource(page, valueKey) {
  await page.locator(REVALUATION_SOURCE_DROPDOWN).click();
  await page
    .locator('.input-dropdown-list')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.locator(optionByKey(valueKey)).first().click();
  await page
    .locator('.input-dropdown-list')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
}

test.describe('Cost Revaluation window — CopyFrom_M_CostElement_ID display/mandatory logic', () => {
  testCases.forEach(({ language, label }) => {
    test(`CopyFrom field toggles with RevaluationSource (${label})`, async ({ page }) => {
      allure.epic('E0226: Costing');
      allure.tag('F1514: Cost Type Moving Average Invoice');
      allure.tag('F1514'); // Standalone tag for Tags section
      allure.story(
        'Cost Revaluation — CopyFrom cost element shows and is mandatory only when source = CopyFromCostElement'
      );
      allure.severity('normal');
      allure.description(`
## Cost Revaluation — display/mandatory logic of CopyFrom_M_CostElement_ID

Verifies, on a NEW Cost Revaluation record (AD_Window_ID = ${COST_REVALUATION_WINDOW_ID}), that
\`CopyFrom_M_CostElement_ID\`:
- is NOT displayed while RevaluationSource = Calculated (default),
- becomes visible AND mandatory when RevaluationSource = CopyFromCostElement,
- hides again when RevaluationSource is set back to Calculated.

Language under test: ${language}.
      `);

      // 1. Provision a login user of the given language.
      const masterdata = await Backend.createMasterdata({
        request: { login: { user: { language } } },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // 2. Open a NEW Cost Revaluation record directly in the detail form.
      await page.goto(`${FRONTEND_BASE_URL}/window/${COST_REVALUATION_WINDOW_ID}/NEW`);
      await page.waitForURL(
        new RegExp(`/window/${COST_REVALUATION_WINDOW_ID}/\\d+`),
        { timeout: VERY_SLOW_ACTION_TIMEOUT }
      );
      const recordId = page.url().split('/').pop().split('?')[0];
      console.log(`[INFO] new M_CostRevaluation record id = ${recordId}`);

      // The RevaluationSource List must be present (defaults to Calculated).
      await page
        .locator(REVALUATION_SOURCE_WRAPPER)
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      // 3. Default state (RevaluationSource = Calculated): CopyFrom field is NOT rendered.
      await test.step('Default (Calculated): CopyFrom field is hidden', async () => {
        // DisplayLogic-hidden fields are removed from the DOM (RawWidget renders nothing), so the
        // wrapper must be absent — not merely not-visible.
        await expect(page.locator(COPY_FROM_WRAPPER)).toHaveCount(0);
      });

      // 4. Set RevaluationSource = CopyFromCostElement → CopyFrom becomes visible AND mandatory.
      await test.step('Set CopyFromCostElement: CopyFrom field shown and mandatory', async () => {
        await selectRevaluationSource(page, 'CopyFromCostElement');

        const copyFrom = page.locator(COPY_FROM_WRAPPER);
        await copyFrom.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(copyFrom).toBeVisible();

        // Empty + mandatory → the language-invariant `.input-mandatory` marker is rendered.
        await expect(page.locator(COPY_FROM_MANDATORY).first()).toBeVisible({
          timeout: SLOW_ACTION_TIMEOUT,
        });
      });

      // 5. Set RevaluationSource back to Calculated → CopyFrom hides again.
      await test.step('Back to Calculated: CopyFrom field hides again', async () => {
        await selectRevaluationSource(page, 'Calculated');
        await expect(page.locator(COPY_FROM_WRAPPER)).toHaveCount(0, {
          timeout: SLOW_ACTION_TIMEOUT,
        });
      });

      console.log(`[PASS] Cost Revaluation CopyFrom display/mandatory toggle verified (${label})`);
    });
  });
});
