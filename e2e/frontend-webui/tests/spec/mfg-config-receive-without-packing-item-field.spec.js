import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Mobile UI Manufacturing — `IsAllowReceiveWithoutPackingItem` field.
 *
 * Verifies that the YesNo field "allow receiving without a packing item" is reachable, can be
 * changed, saved and survives a page reload in BOTH places where it is configured:
 * - window 541788 "MobileUI Manufacturing Configuration" (client-wide default, `MobileUI_MFG_Config`,
 *   rendered as a checkbox in the detail view)
 * - window 108 "User", tab 546679 "Mobile UI Nutzerprofil - Produktion" (per-user override,
 *   `MobileUI_UserProfile_MFG`, a Yes/No/none list rendered in the tab grid)
 *
 * This is the window-design-rules evidence test for the field added by migration 5821230.
 * The client-wide config row is shared, so the original value is read first and restored in a
 * `finally`.
 */

const MFG_CONFIG_WINDOW_ID = 541788;
const USER_WINDOW_ID = 108;
const USER_PROFILE_MFG_TAB_ID = 546679;

// Language-invariant selectors — DB ColumnName based (RawWidget `.form-field-{ColumnName}` wrapper).
const FIELD = 'IsAllowReceiveWithoutPackingItem';
const FIELD_WRAPPER = `.form-field-${FIELD}`;
const CONFIG_CHECKBOX = `${FIELD_WRAPPER} input[type="checkbox"]`;
const PROFILE_CELL = `[data-cy="cell-${FIELD}"]`;
// SelectionDropdown option data-testid = `option-${key}`; the key is the YesNo ref-list value (language-invariant).
const optionByKey = (key) => `.input-dropdown-list-option[data-testid="option-${key}"]`;

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

const allureMetadata = (story, description) => {
  allure.epic('E0160: Manufacturing Execution');
  allure.tag('F8030: MobileUI Manufacturing');
  allure.tag('F8030');
  allure.story(story);
  allure.severity('normal');
  allure.description(description);
};

test.describe('Mobile UI Manufacturing — IsAllowReceiveWithoutPackingItem field', () => {
  testCases.forEach(({ language, label }) => {
    test(`MobileUI Manufacturing Configuration window: checkbox renders, toggles, persists (${label})`, async ({
      page,
    }) => {
      allureMetadata(
        'Receive-without-packing-item flag on the manufacturing configuration persists',
        `
## Manufacturing configuration — IsAllowReceiveWithoutPackingItem

Verifies that the checkbox on the MobileUI Manufacturing Configuration window
(AD_Window_ID = ${MFG_CONFIG_WINDOW_ID}) renders, can be toggled, is saved via the standard PATCH and
survives a page reload. The original value is restored at the end.

Language under test: ${language}.
        `
      );

      const masterdata = await Backend.createMasterdata({
        request: { login: { user: { language } } },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      let recordId;
      await test.step('Open the manufacturing configuration record', async () => {
        await MasterWindowPage.goto(MFG_CONFIG_WINDOW_ID);
        await page.locator('.table-row').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await page.locator('.table-row').first().dblclick();
        await page.waitForURL(new RegExp(`/window/${MFG_CONFIG_WINDOW_ID}/\\d+`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
        recordId = page.url().match(new RegExp(`/window/${MFG_CONFIG_WINDOW_ID}/(\\d+)`))[1];
      });

      const checkbox = page.locator(CONFIG_CHECKBOX);
      const toggle = async () => {
        const patchResponse = page.waitForResponse(
          (resp) =>
            resp.url().includes(`/rest/api/window/${MFG_CONFIG_WINDOW_ID}/${recordId}`) &&
            resp.request().method() === 'PATCH',
          { timeout: SLOW_ACTION_TIMEOUT }
        );
        // Click the wrapping label (the tick <div> intercepts pointer events on the raw input).
        await page.locator(`${FIELD_WRAPPER} .input-checkbox`).click();
        expect((await patchResponse).ok()).toBeTruthy();
      };

      await page.locator(FIELD_WRAPPER).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expect(checkbox).toBeVisible();
      const initiallyChecked = await checkbox.isChecked();

      try {
        await test.step('Toggle the checkbox and await the PATCH save', async () => {
          await toggle();
          await expect(checkbox).toBeChecked({ checked: !initiallyChecked });
        });

        await test.step('Reload and assert the toggled value persisted', async () => {
          await page.reload();
          await page.locator(FIELD_WRAPPER).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
          await expect(checkbox).toBeChecked({ checked: !initiallyChecked });
        });
      } finally {
        // The configuration row is shared across scenarios: always put the original value back.
        await test.step('Restore the original value', async () => {
          await page.locator(FIELD_WRAPPER).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
          if ((await checkbox.isChecked()) !== initiallyChecked) {
            await toggle();
          }
          await expect(checkbox).toBeChecked({ checked: initiallyChecked });
        });
      }
    });

    test(`User window, Mobile UI Nutzerprofil - Produktion tab: list renders, changes, persists (${label})`, async ({
      page,
    }) => {
      allureMetadata(
        'Receive-without-packing-item flag on the user manufacturing profile persists',
        `
## User manufacturing profile — IsAllowReceiveWithoutPackingItem

Verifies that the Yes/No list on the "Mobile UI Nutzerprofil - Produktion" tab
(AD_Window_ID = ${USER_WINDOW_ID}, AD_Tab_ID = ${USER_PROFILE_MFG_TAB_ID}) renders, can be changed,
is saved via the standard PATCH and survives a page reload. The test creates its own login user and
profile row, so nothing shared needs restoring.

Language under test: ${language}.
        `
      );

      // The login user is created with an explicit manufacturing profile of its own, so the tab has a row.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
          mobileConfig: { manufacturing: { isAllowReceiveWithoutPackingItem: false } },
        },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      // The login user's AD_User_ID is the user-profile id of its session.
      const session = await (await page.request.get(`${WEBAPI_BASE_URL}/userSession`)).json();
      const userId = session.userProfileId;
      expect(userId, 'AD_User_ID of the login user').toBeTruthy();

      const cell = page.locator(PROFILE_CELL).first();
      const openProfileTab = async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${USER_WINDOW_ID}/${userId}`);
        const tab = page.locator(`[data-testid="tab-AD_Tab-${USER_PROFILE_MFG_TAB_ID}"]`);
        await tab.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await tab.click();
        await cell.waitFor({ state: 'attached', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await cell.scrollIntoViewIfNeeded();
      };
      // Put the cell into edit mode (dblclick), then open its dropdown list.
      const openList = async () => {
        await cell.dblclick();
        await cell.locator('.input-dropdown-container').click();
        await page.locator('.input-dropdown-list').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        // The list first shows a header-only placeholder (no data-testid) while it loads: wait for real options.
        await page.locator('.input-dropdown-list-option[data-testid]').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      };
      const currentKey = async () => {
        // The cell shows the localized caption; map it to the language-invariant list key via the options.
        await openList();
        const selected = await cell.locator('input.js-input-field').inputValue();
        const options = await page
          .locator('.input-dropdown-list-option[data-testid]')
          .evaluateAll((els) => els.map((e) => ({ key: e.getAttribute('data-testid').replace('option-', ''), text: e.textContent })));
        await page.keyboard.press('Escape');
        const match = options.find((o) => o.text === selected);
        return match ? match.key : 'null';
      };
      const selectKey = async (key) => {
        const patchResponse = page.waitForResponse(
          (resp) =>
            resp.url().includes(`/rest/api/window/${USER_WINDOW_ID}/${userId}/AD_Tab-${USER_PROFILE_MFG_TAB_ID}/`) &&
            resp.request().method() === 'PATCH',
          { timeout: SLOW_ACTION_TIMEOUT }
        );
        await openList();
        await page.locator(optionByKey(key)).first().click();
        expect((await patchResponse).ok()).toBeTruthy();
      };

      await test.step('Open the user and its manufacturing profile tab', openProfileTab);
      await expect(cell).toBeVisible();

      const originalKey = 'N'; // set explicitly by the masterdata above
      const changedKey = 'Y';

      await test.step('Change the value and await the PATCH save', async () => {
        expect(await currentKey()).toBe(originalKey);
        await selectKey(changedKey);
      });

      await test.step('Reload and assert the changed value persisted', async () => {
        await openProfileTab();
        expect(await currentKey()).toBe(changedKey);
      });
    });
  });
});
