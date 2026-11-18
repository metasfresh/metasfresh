/**
 * Mobile UI picking configuration windows — "CU quantity entry" (CUQtyEntryMode) and the
 * scannable code format (C_ScannableCode_Format_ID), on BOTH configuration levels:
 *
 *   - picking profile  (MobileUI_UserProfile_Picking, window 541743 "Mobile UI Kommissionierprofil"):
 *     company default; CUQtyEntryMode is mandatory with default MANUAL.
 *   - per-customer picking job options (MobileUI_UserProfile_Picking_Job, window 541862
 *     "Mobile UI Kommissionieraufgabe Optionen", reached from the profile's customer tab):
 *     both fields optional (empty = the profile's value).
 *
 * Scope of this test, per window:
 *   1. the CUQtyEntryMode and C_ScannableCode_Format_ID fields are visible on the new record
 *      (first check: RED fails here while the AD migration is not applied);
 *   2. set CUQtyEntryMode = "Scan once, enter quantity" (SCAN_ONCE_ENTER_QTY) and a
 *      scannable code format, save, reload, assert both values (single view);
 *   3. assert both values in the grid (list view); for the picking profile the grid lists active
 *      rows only, so this check sets both values on the seed profile and restores them afterwards.
 *
 * The scannable code format is provisioned through the frontend-testing masterdata API
 * (`customQRCodeFormats`), never via the DB.
 */

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
import { ListWidget } from '../utils/widgets/ListWidget';
import { TextWidget } from '../utils/widgets/TextWidget';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';
import { getFieldData, waitForRecordSaved, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

const FIELD_NAME = 'Name';
const FIELD_MODE = 'CUQtyEntryMode';
const FIELD_FORMAT = 'C_ScannableCode_Format_ID';
const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY'; // AD_Ref_List.Value

const WINDOWS = [
  // only ONE active picking profile may exist (unique index IDX_unique_UserProfilePicking):
  // the test record is created inactive, before its first save
  { label: 'picking profile', windowId: 541743, expectedDefaultMode: 'MANUAL', createInactive: true },
  { label: 'customer picking job options', windowId: 541862, expectedDefaultMode: null },
];

async function gotoWindowList(page, windowId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}`);
  await page.locator('.document-list-wrapper, .document-list').waitFor({
    state: 'visible',
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });
  await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.locator('.rotating, .panel-spaced-lg')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.waitForTimeout(500);
}

/**
 * Delete a test record through the WebUI REST API (with the browser session), so that no test
 * record is left behind in the shared DB (the picking profile list is also read by other specs).
 */
async function deleteTestRecord(page, windowId, recordId) {
  try {
    const response = await page.request.delete(`${WEBAPI_BASE_URL}/window/${windowId}/${recordId}`, { timeout: 20000 });
    if (!response.ok()) {
      console.error(`Could not delete test record ${windowId}/${recordId}: HTTP ${response.status()}`);
    }
  } catch (error) {
    console.error(`Could not delete test record ${windowId}/${recordId}: ${error.message}`);
  }
}

async function createNewRecord(page) {
  await page.locator('body').click();
  await page.waitForTimeout(200);
  await page.keyboard.press('Alt+N');
  await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.locator('.rotating, .indicator-pending')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.waitForTimeout(1000);
  return page.url().split('/').pop();
}

function keyOf(fieldData) {
  const v = fieldData.value;
  return typeof v === 'object' && v !== null ? v.key : v;
}

test.describe('Mobile UI picking configuration — CU quantity entry mode and scannable code format', () => {
  test('CUQtyEntryMode and C_ScannableCode_Format_ID on the picking profile and the customer job options', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.story('CU quantity entry mode and scannable code format in the picking configuration windows');
    allure.severity('critical');
    allure.description(`
## CUQtyEntryMode + C_ScannableCode_Format_ID

On the picking profile (company default) and on the per-customer picking job options
(optional, empty = the profile's value): the fields are visible, hold a value after
save + reload, and show in the grid.
    `);

    test.setTimeout(240000);

    const stamp = `${Date.now()}`;
    const formatName = `CU entry test format ${stamp}`;

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: {} },
        // The grid check below needs the one active picking profile. A fresh DB has none until a
        // picking config is written, so write one here instead of relying on an earlier spec.
        mobileConfig: { picking: { pickTo: ['LU_TU'] } },
        customQRCodeFormats: [
          {
            name: formatName,
            parts: [{ startPosition: 1, endPosition: 7, type: 'PRODUCT_CODE' }],
          },
        ],
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const createdRecords = [];
    try {
    for (const { label, windowId, expectedDefaultMode, createInactive } of WINDOWS) {
      await test.step(`${label} (window ${windowId})`, async () => {
        const recordName = `CU entry ${label} ${stamp}`;

        await gotoWindowList(page, windowId);
        const recordId = await createNewRecord(page);
        createdRecords.push({ windowId, recordId });

        // First check: both fields exist on the tab (RED while the migration is not applied).
        await expect(
          WidgetCommon.getFieldContainer(FIELD_MODE),
          `${FIELD_MODE} must be visible on ${label}`,
        ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(
          WidgetCommon.getFieldContainer(FIELD_FORMAT),
          `${FIELD_FORMAT} must be visible on ${label}`,
        ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

        // Default of a NEW record: profile = MANUAL (mandatory), customer = empty (optional).
        const defaultMode = await getFieldData(String(windowId), recordId, FIELD_MODE);
        if (expectedDefaultMode) {
          expect(keyOf(defaultMode), `${label}: default ${FIELD_MODE}`).toBe(expectedDefaultMode);
        } else {
          expect(keyOf(defaultMode) ?? null, `${label}: ${FIELD_MODE} must be empty by default`).toBeNull();
        }

        // The record is first saved when its mandatory Name is set, and an inactive record is
        // read-only afterwards: so set everything else first and the Name last.
        if (createInactive) {
          await BooleanWidget.setFalse('IsActive');
        }
        await ListWidget.setByValue(FIELD_MODE, MODE_SCAN_ONCE);
        await ListWidget.setValue(FIELD_FORMAT, formatName, { exactMatch: true });
        // Setting the Name saves the record. Wait for that PATCH response before reading the save
        // status: the status read must not overtake the save request, which can take seconds.
        const nameSaved = page.waitForResponse(
          (response) => response.request().method() === 'PATCH'
            && response.url().endsWith(`/window/${windowId}/${recordId}`)
            && (response.request().postData() ?? '').includes(`"path":"${FIELD_NAME}"`),
          { timeout: VERY_SLOW_ACTION_TIMEOUT },
        );
        await TextWidget.setValue(FIELD_NAME, recordName);
        await expect(WidgetCommon.getFieldContainer(FIELD_NAME).locator('input').first()).toHaveValue(recordName);
        await WidgetCommon.triggerBlur();
        expect((await nameSaved).ok(), 'the Name PATCH (first save) succeeds').toBe(true);
        await WidgetCommon.waitForSaveComplete();
        await waitForRecordSaved(String(windowId), recordId);

        // Reload (single view) and read the persisted values back.
        await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/${recordId}`);
        await expect(WidgetCommon.getFieldContainer(FIELD_MODE)).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
        const mode = await getFieldData(String(windowId), recordId, FIELD_MODE);
        const format = await getFieldData(String(windowId), recordId, FIELD_FORMAT);
        expect(keyOf(mode)).toBe(MODE_SCAN_ONCE);
        expect(keyOf(format), `${FIELD_FORMAT} must be set`).toBeTruthy();
        expect(format.value.caption).toBe(formatName);
        await expect(WidgetCommon.getFieldContainer(FIELD_FORMAT).locator('input').first()).toHaveValue(new RegExp(formatName));

        // Grid (list view): both columns are shown.
        await gotoWindowList(page, windowId);
        // (grid cells are addressed by their field name, never by a localized column caption)
        const gridCell = (row, fieldName) => row.locator(`[data-cy="cell-${fieldName}"]`);
        if (createInactive) {
          // The grid lists active rows only and only ONE active profile may exist (the test
          // profile is inactive), so the grid check runs on the seed profile: set both values on
          // it, assert them in its grid row, and restore its original values afterwards (also
          // when an assertion fails).
          await page.locator('tbody tr').first().dblclick({ timeout: SLOW_ACTION_TIMEOUT });
          await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
          const seedId = page.url().split('/').pop();
          expect(seedId, 'the seed profile is the only active row').not.toBe(recordId);
          await expect(WidgetCommon.getFieldContainer(FIELD_MODE)).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
          await waitForRecordSaved(String(windowId), seedId);
          // remember the seed's own values: they are restored, whatever an earlier spec left there
          const seedMode = keyOf(await getFieldData(String(windowId), seedId, FIELD_MODE));
          const seedFormatData = await getFieldData(String(windowId), seedId, FIELD_FORMAT);
          const seedFormat = keyOf(seedFormatData) ?? null;
          const seedFormatCaption = seedFormat !== null ? seedFormatData.value.caption : null; // a format name: user data, not UI text
          try {
            await expect(WidgetCommon.getFieldContainer(FIELD_MODE).locator('input').first()).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
            await ListWidget.setByValue(FIELD_MODE, MODE_SCAN_ONCE);
            await ListWidget.setValue(FIELD_FORMAT, formatName, { exactMatch: true });
            await WidgetCommon.triggerBlur();
            await WidgetCommon.waitForSaveComplete();
            await waitForRecordSaved(String(windowId), seedId);
            await gotoWindowList(page, windowId);
            const row = page.locator('tbody tr').first();
            await expect(row, 'grid row of the seed profile').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
            // the saved value's caption: the caption the WebAPI returns in the session language (no hard-coded UI text)
            const seedModeSaved = await getFieldData(String(windowId), seedId, FIELD_MODE);
            expect(keyOf(seedModeSaved)).toBe(MODE_SCAN_ONCE);
            await expect(gridCell(row, FIELD_MODE), 'grid cell CUQtyEntryMode').toContainText(seedModeSaved.value.caption);
            await expect(gridCell(row, FIELD_FORMAT), 'grid cell C_ScannableCode_Format_ID').toContainText(formatName);
          } finally {
            await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/${seedId}`);
            await expect(WidgetCommon.getFieldContainer(FIELD_MODE)).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
            await expect(WidgetCommon.getFieldContainer(FIELD_MODE).locator('input').first()).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
            await ListWidget.setByValue(FIELD_MODE, seedMode);
            if (seedFormat === null) {
              // the original format was empty: clear the lookup with its clear icon (nothing to clear when it was never set)
              const clearIcon = WidgetCommon.getFieldContainer(FIELD_FORMAT).locator('.meta-icon-close-alt');
              if ((await clearIcon.count()) > 0) {
                await clearIcon.click();
              }
            } else {
              await ListWidget.setValue(FIELD_FORMAT, seedFormatCaption, { exactMatch: true });
            }
            await WidgetCommon.triggerBlur();
            await WidgetCommon.waitForSaveComplete();
            await waitForRecordSaved(String(windowId), seedId);
            await expect
              .poll(async () => keyOf(await getFieldData(String(windowId), seedId, FIELD_MODE)), {
                message: 'seed profile mode restored',
                timeout: SLOW_ACTION_TIMEOUT,
              })
              .toBe(seedMode);
            await expect
              .poll(async () => keyOf(await getFieldData(String(windowId), seedId, FIELD_FORMAT)) ?? null, {
                message: 'seed profile format restored',
                timeout: SLOW_ACTION_TIMEOUT,
              })
              .toBe(seedFormat);
          }
        } else {
          const row = page.locator('tr', { hasText: recordName }).first();
          await expect(row, `grid row of ${recordName}`).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
          await expect(gridCell(row, FIELD_MODE), 'grid cell CUQtyEntryMode').toContainText(mode.value.caption); // the saved value's caption, in the session language
          await expect(gridCell(row, FIELD_FORMAT), 'grid cell C_ScannableCode_Format_ID').toContainText(formatName);
        }

        const shot = await page.screenshot({ fullPage: true });
        await allure.attachment(`${label}: grid`, shot, 'image/png');
      });
    }
    } finally {
      for (const { windowId, recordId } of createdRecords.reverse()) {
        await deleteTestRecord(page, windowId, recordId);
      }
    }
  });
});
