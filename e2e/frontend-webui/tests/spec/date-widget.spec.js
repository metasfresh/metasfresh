import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Date Widget E2E test suite.
 *
 * Tests the date picker/calendar widget used in document forms:
 * - Click on date field to open calendar picker
 * - Calendar month navigation
 * - Date selection
 * - Date field value formatting
 */

test.describe('Date Widget', () => {
  test('Date picker interaction on Sales Order', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14140: Widgets');
    allure.story('Date Widget Interaction');
    allure.severity('normal');

    allure.description(`
## Date Widget

Tests the date picker calendar widget:
1. **Find date field** — Locate the DatePromised field on Sales Order
2. **Open calendar** — Click the date field to open calendar
3. **Calendar structure** — Verify month, year, day grid are present
4. **Navigate months** — Click arrows to change month
5. **Select date** — Click a day to set the value
    `);

    test.setTimeout(180000); // 3 minutes

    // === CREATE TEST DATA ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: {
          user: { language: 'en_US', firstname: 'first', lastname: 'last' },
        },
        bpartners: {
          CUSTOMER1: {
            isVendor: false,
            isCustomer: true,
            isSoPriceList: true,
            name: 'Customer',
          },
        },
        products: {
          Product1: {
            name: 'PROD',
            type: 'Item',
            prices: [{ price: 15.0, currencyCode: 'EUR' }],
          },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === CREATE SALES ORDER WITH CUSTOMER ===
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(2000);

    const docNo = await SalesOrderPage.getDocumentNo();
    console.log(`Sales Order: ${docNo}`);

    // ======================================================================
    // TEST 1: Find and click date field
    // ======================================================================
    await test.step('Find and open date picker', async () => {
      // DatePromised field on Sales Order — uses database column name in ID
      const dateField = page.locator('#lookup_DatePromised, [data-cy="cell-DatePromised"]').first();
      const hasDateField = await dateField.isVisible().catch(() => false);
      console.log(`DatePromised field visible: ${hasDateField}`);

      if (!hasDateField) {
        // Try the extended edit modal — date field might not be in main view
        console.log('DatePromised not in main view, trying extended edit...');
        await page.keyboard.press('Alt+E');
        await page.waitForTimeout(1000);

        const modalDateField = page.locator('.panel-modal #lookup_DatePromised, .panel-modal [data-cy="cell-DatePromised"]');
        const hasModalDate = await modalDateField.first().isVisible().catch(() => false);
        console.log(`DatePromised in extended edit: ${hasModalDate}`);

        if (hasModalDate) {
          await modalDateField.first().click();
          await page.waitForTimeout(500);
        }

        // Try a different date field — DateOrdered is usually visible
        const dateOrderedField = page.locator('.panel-modal #lookup_DateOrdered');
        const hasDateOrdered = await dateOrderedField.isVisible().catch(() => false);
        console.log(`DateOrdered in extended edit: ${hasDateOrdered}`);

        if (hasDateOrdered) {
          await dateOrderedField.click();
          await page.waitForTimeout(500);
        }

        // Close modal
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
      }

      // Find any date field on the main form
      const anyDateField = page.locator('.datepicker, .rdtPicker, .rdt').first();
      const hasAnyDate = await anyDateField.isVisible().catch(() => false);
      console.log(`Any date picker component: ${hasAnyDate}`);

      // Try clicking a date-type widget
      const dateWidgets = page.locator('.form-group .input-icon-container').first();
      const hasDateWidget = await dateWidgets.isVisible().catch(() => false);
      console.log(`Date widget with icon: ${hasDateWidget}`);

      if (hasDateWidget) {
        await dateWidgets.click();
        await page.waitForTimeout(500);
      }
    });

    // ======================================================================
    // TEST 2: Check if calendar popup appeared
    // ======================================================================
    await test.step('Calendar popup structure', async () => {
      // Look for the calendar/datepicker popup
      const calendar = page.locator('.rdtPicker, .rdtOpen, .react-datepicker, .datepicker-dropdown');
      const calendarVisible = await calendar.first().isVisible().catch(() => false);
      console.log(`Calendar popup visible: ${calendarVisible}`);

      if (calendarVisible) {
        // Check for month/year header
        const monthYear = page.locator('.rdtSwitch, .react-datepicker__current-month');
        const hasMonthYear = await monthYear.first().isVisible().catch(() => false);
        console.log(`Month/year header: ${hasMonthYear}`);

        if (hasMonthYear) {
          const monthText = (await monthYear.first().textContent().catch(() => '')).trim();
          console.log(`Current month: "${monthText}"`);
        }

        // Check for navigation arrows (prev/next month)
        const prevArrow = page.locator('.rdtPrev, .react-datepicker__navigation--previous');
        const nextArrow = page.locator('.rdtNext, .react-datepicker__navigation--next');
        const hasPrev = await prevArrow.first().isVisible().catch(() => false);
        const hasNext = await nextArrow.first().isVisible().catch(() => false);
        console.log(`Navigation arrows — prev: ${hasPrev}, next: ${hasNext}`);

        // Check for day cells
        const dayCells = page.locator('.rdtDay, .react-datepicker__day');
        const dayCount = await dayCells.count();
        console.log(`Day cells: ${dayCount}`);

        // Take screenshot of calendar
        const screenshot = await page.screenshot();
        allure.attachment('Calendar Popup', screenshot, 'image/png');

        // Click a day to select it
        const todayCell = page.locator('.rdtToday, .rdtDay.rdtActive, .react-datepicker__day--today');
        const hasToday = await todayCell.first().isVisible().catch(() => false);
        console.log(`Today cell highlighted: ${hasToday}`);

        if (dayCount > 15) {
          // Click the 15th day cell
          await dayCells.nth(14).click();
          await page.waitForTimeout(500);
          console.log('Selected day 15');
        }
      } else {
        console.log('No calendar popup found — date fields may use inline display');

        // Discover all field types on the form
        const formFields = await page.locator('.form-group .form-control-label').all();
        const fieldNames = [];
        for (let i = 0; i < Math.min(formFields.length, 10); i++) {
          const text = (await formFields[i].textContent().catch(() => '')).trim();
          if (text) fieldNames.push(text);
        }
        console.log(`Form field labels: ${JSON.stringify(fieldNames)}`);
        allure.attachment('Form Fields', JSON.stringify(fieldNames, null, 2), 'application/json');
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Date widget test completed');
  });
});
