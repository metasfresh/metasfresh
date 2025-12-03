import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';

/**
 * Sales Order to Shipment Schedule E2E test suite.
 *
 * Tests the sales order to shipment schedule workflow:
 * 1. Create a sales order with customer and product
 * 2. Complete the sales order
 * 3. Navigate to Shipment Schedule (Alt+6)
 * 4. Validate the ordered quantity appears in the shipment schedule
 *
 * This validates language-independent selectors for:
 * - Sales order creation and completion
 * - Related documents navigation (Alt+6)
 * - Shipment schedule window and quantity field
 */

// Test cases for multi-language validation
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Sales Order to Shipment Schedule (${label})`, () => {
    test(`Create SO and validate quantity in shipment schedule (${label} UI)`, async ({ page }) => {
      // Step 1: Create test data with specified language
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
            },
          },
          bpartners: {
            CUSTOMER1: {
              isVendor: false,
              isCustomer: true, // ← Sales customer
              isSoPriceList: true, // ← Sales price list
            },
          },
          products: {
            Product1: {
              name: `Test Product SO (${language})`,
              value: `TEST-SO-${language}-001`,
              type: 'Item',
              prices: [
                {
                  price: 50.0,
                  currencyCode: 'EUR',
                },
              ],
            },
          },
        },
      });

      console.log(`[${language}] Master data created:`, {
        customer: masterdata.bpartners.CUSTOMER1.bpartnerCode,
        product: masterdata.products.Product1.productName,
      });

      // Step 2: Login with the user (UI will be in specified language)
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // Step 3: Create sales order for 10 units
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();

      // Select customer - this waits for record to be saved (auto-fill completes)
      const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
      console.log(`[${language}] Sales Order ${recordId} created and saved`);

      // Add order line - this waits for tab to allow new records before proceeding
      await SalesOrderPage.addOrderLine({
        product: masterdata.products.Product1.productCode,
        quantity: '10',
        recordId,
      });

      await SalesOrderPage.complete();

      // Get and verify document number
      const soDocumentNo = await SalesOrderPage.getDocumentNo();
      expect(soDocumentNo).toBeTruthy();
      expect(soDocumentNo.length).toBeGreaterThan(0);

      console.log(`[${language}] Sales Order created: ${soDocumentNo}`);

      // Step 4: Navigate to shipment schedule via Alt+6
      // This navigates directly to the correct shipment schedule for this SO
      // IMPORTANT: Do NOT navigate to window 500221 directly - it may select wrong schedule!
      await SalesOrderPage.openRelatedShipmentCandidate();

      // Verify shipment schedule is visible
      await ShipmentSchedulePage.expectVisible();

      console.log(`[${language}] Shipment Schedule opened for SO ${soDocumentNo}`);

      // Step 5: Validate ordered quantity
      await ShipmentSchedulePage.expectOrderedQuantity('10');

      console.log(`[${language}] Verified ordered quantity: 10`);

      // The test is now complete
      // We successfully created a sales order and validated the quantity in the shipment schedule
    });
  });
});
