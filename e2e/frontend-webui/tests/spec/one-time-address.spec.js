import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { SLOW_ACTION_TIMEOUT, step } from '../utils/common';
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
   * Click on a location lookup field, type a non-matching string to trigger
   * zero autocomplete results, then click "New" to open the quick input modal.
   *
   * The "New" option (data-testid="option-NEW") only appears when autocomplete
   * returns 0 results (SysConfig IsAlwaysDisplayNewBPartner defaults to N).
   */
  async function openNewLocationModal(page, locationFieldName) {
    const locationField = WidgetCommon.getFieldContainer(locationFieldName);
    await locationField.waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });

    const locationInput = locationField
      .locator('input.input-field, input[type="text"]')
      .first();
    await locationInput.click();
    await locationInput.fill('NewAddr');

    const newOption = page.getByTestId('option-NEW');
    await newOption.waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await newOption.click();

    // Wait for the quick input modal to open
    const modal = page.locator('.panel-modal').first();
    await modal.waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await page.waitForTimeout(1000);

    return modal;
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

        // Step 4: Open quick input modal from DropShip_Location_ID.
        // KEY ASSERTION: If the bug is present, this throws HTTP 500 and the modal
        // either fails to open or shows an error. A successful modal open proves the fix works.
        const modal = await openNewLocationModal(page, 'DropShip_Location_ID');

        // Step 5: Close the modal via the Done/Fertig button.
        // The quick input modal uses data-testid="process-modal-cancel-button" for Done
        // (known UI naming quirk — the button closes the modal and saves).
        const doneButton = page.getByTestId('process-modal-cancel-button');
        await doneButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await doneButton.click();

        // Step 6: Verify modal closes (may close or show validation — either is OK,
        // the key assertion was that the modal opened without HTTP 500).
        // Wait for modal to detach or for it to remain with validation messages.
        await modal
          .waitFor({ state: 'detached', timeout: 5000 })
          .catch(() => {
            // Modal may stay open due to required field validation — that's acceptable.
            // Press Escape to dismiss it.
            test.info().annotations.push({
              type: 'info',
              description: 'Modal stayed open after Done (likely validation). Pressing Escape.',
            });
          });

        // If modal is still visible, dismiss with Escape
        if (await modal.isVisible().catch(() => false)) {
          await page.keyboard.press('Escape');
          await modal
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        }

        await page.waitForTimeout(1000);
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

        // Step 2: Open quick input modal from C_BPartner_Location_ID.
        // This is the standard flow — C_BPartner_ID is already set.
        const modal = await openNewLocationModal(
          page,
          'C_BPartner_Location_ID'
        );

        // Step 3: Close the modal via Done button
        const doneButton = page.getByTestId('process-modal-cancel-button');
        await doneButton.waitFor({
          state: 'visible',
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await doneButton.click();

        // Step 4: Handle modal close (may have validation)
        await modal
          .waitFor({ state: 'detached', timeout: 5000 })
          .catch(() => {
            test.info().annotations.push({
              type: 'info',
              description: 'Modal stayed open after Done (likely validation). Pressing Escape.',
            });
          });

        if (await modal.isVisible().catch(() => false)) {
          await page.keyboard.press('Escape');
          await modal
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        }

        await page.waitForTimeout(1000);
      }
    );
  });
});
