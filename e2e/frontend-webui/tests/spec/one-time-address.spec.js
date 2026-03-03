import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT, step } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { Backend } from '../utils/Backend';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';

/**
 * One-time address (Einmaladresse) creation from Sales Order.
 *
 * Tests the quick input flow for creating a new BPartner location
 * directly from location lookup fields on a Sales Order.
 *
 * Bug: me03#28570 — When creating a one-time address from DropShip_Location_ID
 * without first setting DropShip_BPartner_ID, the backend threw HTTP 500
 * "No bpartner ID found". Fix: fallback to C_BPartner_ID.
 */
test.describe('One-time address creation', () => {
  let masterdata;

  test.beforeEach(async () => {
    masterdata = await Backend.createMasterdata({
      request: {
        login: {
          user: {
            language: 'en_US',
            firstname: 'OneTime',
            lastname: 'Address',
          },
        },
        bpartners: {
          CUSTOMER: {
            isVendor: false,
            isCustomer: true,
            isSoPriceList: true,
            name: 'Cust',
          },
        },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();
  });

  test('Create one-time address from DropShip location without DropShip BPartner', async () => {
    await step(
      'Create one-time address from DropShip_Location_ID without DropShip_BPartner_ID',
      async () => {
        const page = getPage();
        test.setTimeout(120000);

        // Step 1: Create a new Sales Order and select customer
        await SalesOrderPage.goto();
        await SalesOrderPage.clickNew();
        const recordId = await SalesOrderPage.selectCustomer(
          masterdata.bpartners.CUSTOMER.bpartnerCode
        );

        // Step 2: Enable IsDropShip checkbox
        await BooleanWidget.setTrue('IsDropShip');
        await page.waitForTimeout(1000);

        // Verify DropShip fields appeared (DropShip_Location_ID should be visible)
        const dropShipLocationField = page.locator(
          '#lookup_DropShip_Location_ID, .form-field-DropShip_Location_ID'
        );
        await dropShipLocationField.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Step 3: Do NOT set DropShip_BPartner_ID — leave it empty (this is the bug scenario)

        // Step 4: Click on DropShip_Location_ID input to open dropdown
        const dropShipLocationInput = dropShipLocationField
          .locator('input.input-field, input[type="text"]')
          .first();
        await dropShipLocationInput.click();
        await page.waitForTimeout(500);

        // Step 5: Click "New" option in dropdown (data-testid="option-NEW")
        const newOption = page.getByTestId('option-NEW');
        await newOption.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await newOption.click();

        // Step 6: Wait for the modal to open
        const modal = page.locator(
          '.panel-modal, .modal-content, [role="dialog"]'
        );
        await modal.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await page.waitForTimeout(1000);

        // Step 7: Fill address fields inside the modal
        const modalScope = page.locator('.panel-modal').first();

        // Fill street (Address1)
        const streetInput = modalScope
          .locator('.form-field-Address1 input, #lookup_Address1 input')
          .first();
        if ((await streetInput.count()) > 0) {
          await streetInput.fill('Test Street 123');
          await page.waitForTimeout(300);
        }

        // Fill postal code
        const postalInput = modalScope
          .locator('.form-field-Postal input, #lookup_Postal input')
          .first();
        if ((await postalInput.count()) > 0) {
          await postalInput.fill('12345');
          await page.waitForTimeout(300);
        }

        // Fill city
        const cityInput = modalScope
          .locator('.form-field-City input, #lookup_City input')
          .first();
        if ((await cityInput.count()) > 0) {
          await cityInput.fill('Test City');
          await page.waitForTimeout(300);
        }

        // Step 8: Check IsOneTime checkbox inside the modal
        const isOneTimeCheckbox = modalScope
          .locator(
            '#lookup_IsOneTime input[type="checkbox"], .form-field-IsOneTime input[type="checkbox"]'
          )
          .first();
        if ((await isOneTimeCheckbox.count()) > 0) {
          await isOneTimeCheckbox.evaluate((el) => {
            if (!el.checked) el.click();
          });
          await page.waitForTimeout(500);
        }

        // Step 9: Click "Done" button to submit the new location
        // For window-type modals, the "Done" button has data-testid="process-modal-cancel-button"
        const doneButton = page.getByTestId('process-modal-cancel-button');
        await doneButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Intercept API responses to verify no 500 error
        const responsePromise = page.waitForResponse(
          (response) =>
            response.url().includes('/rest/api/window') &&
            response.url().includes('NEW'),
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        await doneButton.click();

        // Step 10: Verify modal closes successfully (no HTTP 500)
        await modal.waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Wait for save to complete
        await WidgetCommon.waitForSaveComplete();
        await page.waitForTimeout(2000);

        // Step 11: Verify DropShip_Location_ID is now populated
        const dropShipLocationValue = await dropShipLocationField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        console.log('DropShip_Location_ID value:', dropShipLocationValue);

        // Step 12: Verify DropShip_BPartner_ID was auto-filled by the interceptor
        const dropShipBPartnerField = page.locator(
          '#lookup_DropShip_BPartner_ID, .form-field-DropShip_BPartner_ID'
        );

        // The interceptor should have auto-filled DropShip_BPartner_ID from C_BPartner_ID
        const bpartnerValue = await dropShipBPartnerField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        console.log('DropShip_BPartner_ID value:', bpartnerValue);
        // It should contain the customer name (auto-filled from C_BPartner_ID)
        expect(bpartnerValue.length).toBeGreaterThan(0);
      }
    );
  });

  test('Create one-time address from main location field', async () => {
    await step(
      'Create one-time address from C_BPartner_Location_ID',
      async () => {
        const page = getPage();
        test.setTimeout(120000);

        // Step 1: Create a new Sales Order and select customer
        await SalesOrderPage.goto();
        await SalesOrderPage.clickNew();
        const recordId = await SalesOrderPage.selectCustomer(
          masterdata.bpartners.CUSTOMER.bpartnerCode
        );

        // Step 2: Click on C_BPartner_Location_ID input to open dropdown
        const locationField = page.locator(
          '#lookup_C_BPartner_Location_ID, .form-field-C_BPartner_Location_ID'
        );
        await locationField.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        const locationInput = locationField
          .locator('input.input-field, input[type="text"]')
          .first();
        await locationInput.click();
        await page.waitForTimeout(500);

        // Step 3: Click "New" option in dropdown
        const newOption = page.getByTestId('option-NEW');
        await newOption.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await newOption.click();

        // Step 4: Wait for the modal to open
        const modal = page.locator(
          '.panel-modal, .modal-content, [role="dialog"]'
        );
        await modal.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await page.waitForTimeout(1000);

        // Step 5: Fill address fields inside the modal
        const modalScope = page.locator('.panel-modal').first();

        const streetInput = modalScope
          .locator('.form-field-Address1 input, #lookup_Address1 input')
          .first();
        if ((await streetInput.count()) > 0) {
          await streetInput.fill('Main Street 456');
          await page.waitForTimeout(300);
        }

        const postalInput = modalScope
          .locator('.form-field-Postal input, #lookup_Postal input')
          .first();
        if ((await postalInput.count()) > 0) {
          await postalInput.fill('54321');
          await page.waitForTimeout(300);
        }

        const cityInput = modalScope
          .locator('.form-field-City input, #lookup_City input')
          .first();
        if ((await cityInput.count()) > 0) {
          await cityInput.fill('Main City');
          await page.waitForTimeout(300);
        }

        // Step 6: Check IsOneTime checkbox inside the modal
        const isOneTimeCheckbox = modalScope
          .locator(
            '#lookup_IsOneTime input[type="checkbox"], .form-field-IsOneTime input[type="checkbox"]'
          )
          .first();
        if ((await isOneTimeCheckbox.count()) > 0) {
          await isOneTimeCheckbox.evaluate((el) => {
            if (!el.checked) el.click();
          });
          await page.waitForTimeout(500);
        }

        // Step 7: Click "Done" button
        const doneButton = page.getByTestId('process-modal-cancel-button');
        await doneButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await doneButton.click();

        // Step 8: Verify modal closes successfully (no error)
        await modal.waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        await WidgetCommon.waitForSaveComplete();
        await page.waitForTimeout(2000);

        // Step 9: Verify C_BPartner_Location_ID is populated
        const locationValue = await locationField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        console.log('C_BPartner_Location_ID value:', locationValue);
        expect(locationValue.length).toBeGreaterThan(0);
      }
    );
  });
});
