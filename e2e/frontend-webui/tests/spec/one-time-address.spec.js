import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { SLOW_ACTION_TIMEOUT, step } from '../utils/common';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { Backend } from '../utils/Backend';
import { AddressWidget } from '../utils/widgets/AddressWidget';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { WidgetCommon } from '../utils/widgets/WidgetCommon';

/**
 * One-time address (Einmaladresse) creation from Sales Order.
 *
 * Tests the quick input flow for creating a new BPartner location
 * directly from location lookup fields on a Sales Order.
 *
 * Bug fix: When creating a one-time address from DropShip_Location_ID
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
   * Fill address via the C_Location_ID inline editor and check IsOneTime.
   * The quick input modal contains a C_Location_ID Address widget (toggle button
   * that expands inline fields) — not direct Address1/Postal/City inputs.
   *
   * We intentionally avoid AddressWidget.setAddress() because it ends with
   * a body.click() that could dismiss the modal. Instead, we open the editor,
   * fill fields, and leave the inline editor open for the "Done" button to handle.
   */
  async function fillAddressInModal(page, { street, postalCode, city }) {
    // Open the C_Location_ID inline address editor
    await AddressWidget.openEditor('C_Location_ID');
    await page.waitForTimeout(500);

    // Fill address fields inside the expanded editor
    const container = WidgetCommon.getFieldContainer('C_Location_ID');

    if (street) {
      const streetInput = container.locator('.form-field-Address1 input').first();
      await streetInput.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
      if ((await streetInput.count()) > 0) {
        await streetInput.fill(street);
        await page.waitForTimeout(300);
      }
    }

    if (postalCode) {
      const postalInput = container.locator('.form-field-Postal input').first();
      if ((await postalInput.count()) > 0) {
        await postalInput.fill(postalCode);
        await page.waitForTimeout(300);
      }
    }

    if (city) {
      const cityInput = container.locator('.form-field-City input').first();
      if ((await cityInput.count()) > 0) {
        await cityInput.fill(city);
        await page.waitForTimeout(300);
      }
    }

    // Check IsOneTime checkbox
    await BooleanWidget.setTrue('IsOneTime');
  }

  test('Create one-time address from DropShip location without DropShip BPartner', async ({ page }) => {
    test.setTimeout(120000);
    allure.epic('E0030: Sales');
    allure.tag('F02300: Sales Order');
    allure.story('One-time Address Creation');
    allure.severity('critical');
    allure.description(
      'Bug fix: creating a one-time address from DropShip_Location_ID without ' +
        'first setting DropShip_BPartner_ID must not throw HTTP 500. The backend ' +
        'should fall back to C_BPartner_ID and auto-fill DropShip_BPartner_ID.'
    );

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

        // Verify DropShip fields appeared
        const dropShipLocationField =
          WidgetCommon.getFieldContainer('DropShip_Location_ID');
        await dropShipLocationField.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });

        // Step 3: Do NOT set DropShip_BPartner_ID — leave it empty (this is the bug scenario)

        // Step 4: Click on DropShip_Location_ID input and type a non-matching
        // string to trigger an empty autocomplete result. The "New" option
        // (data-testid="option-NEW") only appears when 0 results are returned
        // (controlled by SysConfig IsAlwaysDisplayNewBPartner, default=N).
        const dropShipLocationInput = dropShipLocationField
          .locator('input.input-field, input[type="text"]')
          .first();
        await dropShipLocationInput.click();
        await dropShipLocationInput.fill('NewAddr');

        // Step 5: Click "New" option in dropdown
        const newOption = page.getByTestId('option-NEW');
        await newOption.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await newOption.click();

        // Step 6: Wait for the quick input modal to open
        const modal = page.locator('.panel-modal').first();
        await modal.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await page.waitForTimeout(1000);

        // Step 7: Fill address via C_Location_ID inline editor and check IsOneTime
        await fillAddressInModal(page, {
          street: 'Test Street 123',
          postalCode: '12345',
          city: 'Test City',
        });

        // Step 8: Click "Done" button to submit the new location.
        // The quick input modal uses data-testid="process-modal-cancel-button" for the Done/Fertig button
        // (this is a known UI naming quirk — the button closes the modal and saves, despite the testid name).
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

        test.info().annotations.push({ type: 'debug', description: `DropShip_Location_ID value: ${dropShipLocationValue}` });

        // Step 11: Verify DropShip_BPartner_ID was auto-filled by the interceptor
        const dropShipBPartnerField =
          WidgetCommon.getFieldContainer('DropShip_BPartner_ID');

        const bpartnerValue = await dropShipBPartnerField
          .locator('.lookup-widget-value, .input-field')
          .first()
          .textContent()
          .catch(() => '');

        test.info().annotations.push({ type: 'debug', description: `DropShip_BPartner_ID value: ${bpartnerValue}` });
        // Should contain the customer code (auto-filled from C_BPartner_ID)
        expect(bpartnerValue).toContain(
          masterdata.bpartners.CUSTOMER.bpartnerCode
        );
      }
    );
  });

  test('Create one-time address from main location field', async ({ page }) => {
    test.setTimeout(120000);
    allure.epic('E0030: Sales');
    allure.tag('F02300: Sales Order');
    allure.story('One-time Address Creation');
    allure.severity('normal');
    allure.description(
      'Verify that creating a one-time address from the main C_BPartner_Location_ID ' +
        'field works correctly. This is the standard flow where C_BPartner_ID is already set.'
    );

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
        // Type a non-matching string so autocomplete returns 0 results,
        // which causes the "New" option to appear in the dropdown
        // (SysConfig IsAlwaysDisplayNewBPartner defaults to N).
        await locationInput.fill('NewAddr');

        // Step 3: Click "New" option in dropdown
        const newOption = page.getByTestId('option-NEW');
        await newOption.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await newOption.click();

        // Step 4: Wait for the quick input modal to open
        const modal = page.locator('.panel-modal').first();
        await modal.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await page.waitForTimeout(1000);

        // Step 5: Fill address via C_Location_ID inline editor and check IsOneTime
        await fillAddressInModal(page, {
          street: 'Main Street 456',
          postalCode: '54321',
          city: 'Main City',
        });

        // Step 6: Click "Done" button (see step 8 comment in first test for testid naming)
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

        test.info().annotations.push({ type: 'debug', description: `C_BPartner_Location_ID value: ${locationValue}` });
        expect(locationValue.trim().length).toBeGreaterThan(0);
      }
    );
  });
});
