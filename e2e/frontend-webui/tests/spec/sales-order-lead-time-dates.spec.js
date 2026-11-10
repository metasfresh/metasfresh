import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT, getPage } from '../utils/common';
import { WEBAPI_BASE_URL, getFieldData } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID, SHIPPER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Dispoliste dates: Sales Order DatePromised / PreparationDate are computed LIVE in the UI from the
 * order date and the route's (M_Shipper) lead time. This is the callout path; the save-time
 * interceptor path is covered by the cucumber scenarios.
 *
 * ---------------------------------------------------------------------------------------------------
 * RUN PRECONDITIONS (authored without being run)
 * ---------------------------------------------------------------------------------------------------
 *  1. Full local app stack: app server (:8282, ServerBoot), webapi (:8080), frontend (:3000), against
 *     a DB that has the M_Shipper.LeadTimeDays migration AND the forward-calc sysconfig migration
 *     applied, built from this branch so that de.metas.tourplanning.callout.C_Order carries the
 *     M_Shipper_ID / lead-time change.
 *  2. Forward calc sysconfig ON: the spec itself sets
 *     `de.metas.tourplanning.LeadTimeForwardCalc.Enabled = Y` through the masterdata request (which
 *     also resets webapi's AD_SysConfig cache) and restores it to 'N' in afterEach.
 *  3. A lead-time route: the spec creates its own M_Shipper with LeadTimeDays = 7 through the Shipper
 *     window REST (no seed dependence).
 *  4. The Sales Order header must show the M_Shipper_ID lookup (`#lookup_M_Shipper_ID`) plus the
 *     DateOrdered / DatePromised / PreparationDate fields (`.form-field-<Column>`). NOT verified
 *     against the layout when authoring - if one is not on the header's main view, fix the locators
 *     (or open Advanced edit) rather than weakening the assertions.
 *  5. Session language en_US (set through the masterdata `login.user.language`): dates are typed and
 *     read as MM/DD/YYYY.
 *  6. de.metas.order.DatePromisedOffsetDays is irrelevant here (the lead-time path supersedes it).
 *
 * Expected values (WORKDAY = Mon-Fri business days), order date Mon 2026-08-10, D = 7:
 *   DatePromised    = workday(2026-08-10, +7)       = 2026-08-19
 *   PreparationDate = workday(DatePromised, -D + 1)  = 2026-08-11
 * Manual edit: DatePromised typed as Fri 2026-08-28 -> PreparationDate = workday(08-28, -6) = 2026-08-20.
 */

const SYSCONFIG_FORWARD_ENABLED = 'de.metas.tourplanning.LeadTimeForwardCalc.Enabled';
const LEAD_TIME_DAYS = 7;

const ORDER_DATE = { year: 2026, month: 8, day: 10 };
const EXPECTED_PROMISED = { year: 2026, month: 8, day: 19 };
const EXPECTED_PREP = { year: 2026, month: 8, day: 11 };
const MANUAL_PROMISED = { year: 2026, month: 8, day: 28 };
const EXPECTED_PREP_AFTER_MANUAL = { year: 2026, month: 8, day: 20 };

const pad = (n) => String(n).padStart(2, '0');
/** The spec pins session language en_US (see fixture), whose date widget format is MM/DD/YYYY. */
const formatEnUS = ({ year, month, day }) => `${pad(month)}/${pad(day)}/${year}`;

/** Language-independent comparison: the widget text must contain year, month and day numbers. */
const expectNumbersMatch = (text, { year, month, day }, what) => {
  const numbers = (text.match(/\d+/g) || []).map(Number);
  expect(numbers, `${what}: input shows "${text}"`).toEqual(expect.arrayContaining([year, month, day]));
};

test.describe('Sales order - lead-time DatePromised / PreparationDate', () => {
  test.afterEach(async () => {
    // Leave the system-level switch at its shipped default so other specs are unaffected.
    await Backend.setSysconfigs({ [SYSCONFIG_FORWARD_ENABLED]: 'N' });
  });

  test('lead-time route + order date compute promised/prep live; a manual promised edit sticks', async ({ page }) => {
    allure.epic('E0159: Manufacturing Planning');
    allure.tag('F8005: Order Checkup');
    allure.tag('F8005'); // Standalone tag for code-only filtering
    allure.story('Dispoliste dates - DatePromised/PreparationDate from the route lead time, live in the Sales Order UI');
    allure.severity('critical');
    allure.description(`
## Sales Order lead-time dates (UI callout)

1. Creates a customer and a carrier shipper with LeadTimeDays = ${LEAD_TIME_DAYS} and switches the forward-calc sysconfig on.
2. New sales order: select customer, select the lead-time shipper, set Order Date 2026-08-10.
3. Asserts DatePromised = 2026-08-19 and PreparationDate = 2026-08-11 are shown live (UI) and persisted (REST).
4. Types a different DatePromised (2026-08-28): asserts it is NOT re-overwritten and PreparationDate recomputes to 2026-08-20.
    `);

    test.setTimeout(240000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        bpartners: {
          CUSTOMER: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Lead-time customer' },
          CARRIER: { isVendor: true, isCustomer: false, isSoPriceList: false, name: 'Lead-time carrier' },
        },
        sysconfigs: { [SYSCONFIG_FORWARD_ENABLED]: 'Y' },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);

    // ---- a route (M_Shipper) with a lead time, created via the Shipper window REST (shares the UI session cookie)
    const patchDocument = async (documentId, changes) => {
      const response = await page.request.patch(`${WEBAPI_BASE_URL}/window/${SHIPPER_WINDOW_ID}/${documentId}`, {
        data: changes,
      });
      if (!response.ok()) {
        throw new Error(`PATCH shipper ${documentId} failed: HTTP ${response.status()} ${await response.text()}`);
      }
      const body = await response.json();
      return (Array.isArray(body) ? body : body.documents || [body])[0];
    };
    const shipperName = `Lead-time route ${Date.now()}`;
    await test.step('Create a shipper (route) with LeadTimeDays', async () => {
      const created = await patchDocument('NEW', []);
      const shipper = await patchDocument(created.id, [
        { op: 'replace', path: 'Name', value: shipperName },
        { op: 'replace', path: 'C_BPartner_ID', value: Number(masterdata.bpartners.CARRIER.id) },
        { op: 'replace', path: 'LeadTimeDays', value: LEAD_TIME_DAYS },
      ]);
      expect(shipper.validStatus && shipper.validStatus.valid, 'shipper is valid').toBe(true);
    });

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER.bpartnerCode);
    const p = getPage();

    const dateInput = (column) => p.locator(`.form-field-${column} input`).first();
    const waitIdle = async () => {
      await p
        .locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
    };
    const typeDate = async (column, date) => {
      const input = dateInput(column);
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await input.click();
      await input.fill(formatEnUS(date));
      await p.keyboard.press('Tab'); // blur => PATCH
      await waitIdle();
    };
    const persistedDate = async (column) => {
      const field = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, column);
      return String(field.value ?? '');
    };

    await test.step('Select the lead-time shipper (route)', async () => {
      // M_Shipper_ID is a Table-Direct reference that the WebUI renders as a List / select-dropdown
      // widget (`.form-field-M_Shipper_ID`, widgetType-List) - its input is readonly, so open the
      // dropdown and click the option; it is NOT a typeable `#lookup_` search widget.
      const shipperField = p.locator('.form-field-M_Shipper_ID');
      await shipperField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await shipperField.locator('.input-dropdown').click();
      const dropdownList = p.locator('.input-dropdown-list');
      await dropdownList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await p.locator('.input-dropdown-list-option').filter({ hasText: shipperName }).first().click();
      await dropdownList.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await waitIdle();
    });

    await test.step('Set Order Date 2026-08-10 => DatePromised / PreparationDate appear live', async () => {
      await typeDate('DateOrdered', ORDER_DATE);

      // Live = shown by the callout response, no save/reload in between.
      // The shipper selection already filled DatePromised from the default order date (today), so
      // "has a value" is not enough: wait until the callout for the typed order date has replaced it.
      const dateNumbers = async (column) => (await dateInput(column).inputValue()).match(/\d+/g)?.map(Number) ?? [];
      await expect
        .poll(async () => dateNumbers('DatePromised'), {
          timeout: SLOW_ACTION_TIMEOUT,
          message: 'DatePromised shown live',
        })
        .toEqual(expect.arrayContaining([EXPECTED_PROMISED.year, EXPECTED_PROMISED.month, EXPECTED_PROMISED.day]));
      expectNumbersMatch(await dateInput('DatePromised').inputValue(), EXPECTED_PROMISED, 'DatePromised (live)');
      await expect
        .poll(async () => dateNumbers('PreparationDate'), {
          timeout: SLOW_ACTION_TIMEOUT,
          message: 'PreparationDate shown live',
        })
        .toEqual(expect.arrayContaining([EXPECTED_PREP.year, EXPECTED_PREP.month, EXPECTED_PREP.day]));
      expectNumbersMatch(await dateInput('PreparationDate').inputValue(), EXPECTED_PREP, 'PreparationDate (live)');

      // ... and what the server holds agrees (calendar part only, timezone-safe).
      expect(await persistedDate('DatePromised')).toContain('2026-08-19');
      expect(await persistedDate('PreparationDate')).toContain('2026-08-11');
    });

    await test.step('Type a different DatePromised => not overwritten, PreparationDate recomputes', async () => {
      await typeDate('DatePromised', MANUAL_PROMISED);

      // Give any (wrongly) re-firing callout the chance to overwrite, then assert it did not.
      await p.waitForTimeout(1500);
      expectNumbersMatch(await dateInput('DatePromised').inputValue(), MANUAL_PROMISED, 'DatePromised (manual)');
      const { year, month, day } = EXPECTED_PREP_AFTER_MANUAL;
      await expect
        .poll(async () => (await dateInput('PreparationDate').inputValue()).match(/\d+/g)?.map(Number) ?? [], {
          timeout: SLOW_ACTION_TIMEOUT,
          message: 'PreparationDate recomputed from the manual DatePromised',
        })
        .toEqual(expect.arrayContaining([year, month, day]));

      expect(await persistedDate('DatePromised')).toContain('2026-08-28');
      expect(await persistedDate('PreparationDate')).toContain('2026-08-20');
    });
  });
});
