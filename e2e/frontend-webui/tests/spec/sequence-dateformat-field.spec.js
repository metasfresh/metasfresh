import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { getFieldData, waitForRecordSaved } from '../utils/WebAPIValidation';

/**
 * Sequence window (AD_Window_ID = 112, table AD_Sequence) -- the per-sequence "Date format" field
 * (AD_Sequence.DateFormat).
 *
 * The field only makes sense for sequences whose numbers come from the date sequence provider, so
 * it is shown only for an automatic, non-table-ID sequence whose custom provider is
 * DateSequenceProvider (AD_JavaClass 540073). The test creates a sequence through the real UI flow
 * and verifies:
 *   1. the field is HIDDEN while no custom provider is set,
 *   2. it APPEARS once the date sequence provider is selected,
 *   3. it is EDITABLE and the entered format PERSISTS (WebAPI value + after a full page reload).
 */

const SEQUENCE_WINDOW_ID = 112;
const DATE_SEQUENCE_PROVIDER_JAVACLASS_ID = 540073; // AD_JavaClass of de.metas.document.sequenceno.DateSequenceProvider
const DATE_FORMAT = 'yy-DDD';

const dateFormatField = (page) => page.locator('.form-field-DateFormat');

// A Table-reference widget renders its options at document level, each carrying the record id as key.
// SelectionDropdown puts that key under `data-testid` = `option-${key}` on one branch line and
// `data-test-id` = `${key}${caption}` on another, so match both (language-invariant either way).
const DATE_SEQUENCE_PROVIDER_OPTION =
  `.input-dropdown-list .input-dropdown-list-option[data-testid="option-${DATE_SEQUENCE_PROVIDER_JAVACLASS_ID}"], ` +
  `.input-dropdown-list .input-dropdown-list-option[data-test-id^="${DATE_SEQUENCE_PROVIDER_JAVACLASS_ID}"]`;

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

test.describe('Sequence window — per-sequence date format field', () => {
  testCases.forEach(({ language, label }) => {
    test(`Date format is shown only for the date sequence provider, is editable and persists (${label})`, async ({
      page,
    }) => {
      allure.epic('E0160: Manufacturing Execution');
      allure.tag('F8041: Automatic Lot Number');
      allure.tag('F8041');
      allure.story('Per-sequence date format for date-based lot number sequences');
      allure.severity('normal');

      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
        },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      let recordId;
      await test.step('Open a new sequence', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${SEQUENCE_WINDOW_ID}/NEW`);
        await page.waitForURL(new RegExp(`/window/${SEQUENCE_WINDOW_ID}/\\d+`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
        recordId = page.url().split('/').pop().split('?')[0];
        console.log(`[INFO] new AD_Sequence record id = ${recordId}`);
      });

      await test.step('Set name and make it an automatic sequence', async () => {
        const nameInput = page.locator('.form-field-Name input');
        await nameInput.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await nameInput.fill(`DateFormatField_${Date.now()}`);
        await page.keyboard.press('Tab');

        const isAutoSequence = page.locator('.form-field-IsAutoSequence input[type="checkbox"]');
        if (!(await isAutoSequence.isChecked())) {
          await page.locator('.form-field-IsAutoSequence label.input-checkbox').click();
        }
        await expect(isAutoSequence).toBeChecked();
      });

      await test.step('Date format is hidden while no custom provider is set', async () => {
        await expect(page.locator('.form-field-CustomSequenceNoProvider_JavaClass_ID')).toBeVisible({
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await expect(dateFormatField(page)).toHaveCount(0);
      });

      await test.step('Select the date sequence provider', async () => {
        await page
          .locator('.form-field-CustomSequenceNoProvider_JavaClass_ID .input-dropdown-container')
          .first()
          .click();
        await page.locator(DATE_SEQUENCE_PROVIDER_OPTION).first().click();
        await expect(page.locator('.input-dropdown-list')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
      });

      await test.step('Date format appears and takes a format', async () => {
        await expect(dateFormatField(page)).toBeVisible({
          timeout: SLOW_ACTION_TIMEOUT,
        });

        const input = dateFormatField(page).locator('input');
        await expect(input).toBeEditable();
        await input.fill(DATE_FORMAT);
        await page.keyboard.press('Tab');
      });

      await test.step('The format persists', async () => {
        await waitForRecordSaved(SEQUENCE_WINDOW_ID, recordId, {
          maxRetries: 20,
          retryDelayMs: 1000,
        });
        await expect
          .poll(async () => (await getFieldData(SEQUENCE_WINDOW_ID, recordId, 'DateFormat')).value, {
            timeout: SLOW_ACTION_TIMEOUT,
          })
          .toBe(DATE_FORMAT);

        await page.reload();
        await expect(dateFormatField(page).locator('input')).toHaveValue(DATE_FORMAT, {
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });
      });
    });
  });
});
