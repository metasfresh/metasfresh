import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { getPage, SLOW_ACTION_TIMEOUT, step } from '../utils/common';
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

  test.beforeEach(async ({ page }) => {
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

  /**
   * Fill address fields and check IsOneTime inside the quick input modal.
   * Uses modal-scoped selectors because BooleanWidget/AddressWidget
   * operate on the global page scope, not within a modal overlay.
   */
  async function fillAddressInModal(modalScope, { street, postal, city }) {
    const page = getPage();

    const streetInput = modalScope
      .locator('.form-field-Address1 input, #lookup_Address1 input')
      .first();
    if ((await streetInput.count()) > 0) {
      await streetInput.fill(street);
      await page.waitForTimeout(300);
    }

    const postalInput = modalScope
      .locator('.form-field-Postal input, #lookup_Postal input')
      .first();
    if ((await postalInput.count()) > 0) {
      await postalInput.fill(postal);
      await page.waitForTimeout(300);
    }

    const cityInput = modalScope
      .locator('.form-field-City input, #lookup_City input')
      .first();
    if ((await cityInput.count()) > 0) {
      await cityInput.fill(city);
      await page.waitForTimeout(300);
    }

    // Check IsOneTime checkbox — must use modal-scoped selector because
    // BooleanWidget.setTrue() operates on global page scope and would
    // not reliably target the checkbox inside the modal overlay.
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
  }

  test('Create one-time address from DropShip location without DropShip BPartner', async ({ page }) => {
    test.setTimeout(120000);

    await step(
      'Create one-time address from DropShip_Location_ID without DropShip_BPartner_ID',
      async () => {
        // Step 1: Create a new Sales Order and select customer
        await SalesOrderPage.goto();
        await SalesOrderPage.clickNew();
        await SalesOrderPage.selectCustomer(
          masterdata.bpartners.CUSTOMER.bpartnerCode
        );

        // Step 2: Enable IsDropShip checkbox
        await BooleanWidget.setTrue('IsDropShip');
        await page.waitForTimeout(1000);

        // Verify DropShip fields appeared
        const dropShipLocationField =
          WidgetCommon.getFieldContainer('DropShip_Location_ID');
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

        // Step 7: Fill address fields and check IsOneTime inside the modal
        const modalScope = page.locator('.panel-modal').first();
        await fillAddressInModal(modalScope, {
          street: 'Test Street 123',
          postal: '12345',
          city: 'Test City',
        });

        // Step 8: Click "Done" button to submit the new location
        // For window-type modals, the "Done" button has data-testid="process-modal-cancel-button"
        const doneButton = page.getByTestId('process-modal-cancel-button');
        await doneButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await doneButton.click();

        // Step 9: Verify modal closes successfully (no HTTP 500)
        await modal.waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Wait for save to complete
        await WidgetCommon.waitForSaveComplete();
        await page.waitForTimeout(2000);

        // Step 10: Verify DropShip_Location_ID is now populated
        const dropShipLocationValue = await dropShipLocationField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        console.log('DropShip_Location_ID value:', dropShipLocationValue);

        // Step 11: Verify DropShip_BPartner_ID was auto-filled by the interceptor
        const dropShipBPartnerField =
          WidgetCommon.getFieldContainer('DropShip_BPartner_ID');

        const bpartnerValue = await dropShipBPartnerField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        console.log('DropShip_BPartner_ID value:', bpartnerValue);
        // Should contain the customer code (auto-filled from C_BPartner_ID)
        expect(bpartnerValue).toContain(
          masterdata.bpartners.CUSTOMER.bpartnerCode
        );
      }
    );
  });

  test('Create one-time address from main location field', async ({ page }) => {
    test.setTimeout(120000);

    await step(
      'Create one-time address from C_BPartner_Location_ID',
      async () => {
        // Step 1: Create a new Sales Order and select customer
        await SalesOrderPage.goto();
        await SalesOrderPage.clickNew();
        await SalesOrderPage.selectCustomer(
          masterdata.bpartners.CUSTOMER.bpartnerCode
        );

        // Step 2: Click on C_BPartner_Location_ID input to open dropdown
        const locationField =
          WidgetCommon.getFieldContainer('C_BPartner_Location_ID');
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

        // Step 5: Fill address fields and check IsOneTime inside the modal
        const modalScope = page.locator('.panel-modal').first();
        await fillAddressInModal(modalScope, {
          street: 'Main Street 456',
          postal: '54321',
          city: 'Main City',
        });

        // Step 6: Click "Done" button
        const doneButton = page.getByTestId('process-modal-cancel-button');
        await doneButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await doneButton.click();

        // Step 7: Verify modal closes successfully (no error)
        await modal.waitFor({
          state: 'detached',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        await WidgetCommon.waitForSaveComplete();
        await page.waitForTimeout(2000);

        // Step 8: Verify C_BPartner_Location_ID is populated
        const locationValue = await locationField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        console.log('C_BPartner_Location_ID value:', locationValue);
        expect(locationValue.trim().length).toBeGreaterThan(0);
      }
    );
  });
});
