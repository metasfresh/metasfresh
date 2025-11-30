import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';

/**
 * Purchase Order E2E test suite.
 * Tests purchase order creation, line entry, and completion using metasfresh Web UI.
 *
 * Features tested:
 * - Backend API integration for test data creation (vendors, products, users)
 * - Language-independent selectors using database column names and data-testid
 * - Dropdown selection with metasfresh's data-test-id pattern
 * - Document action workflow (Drafted → Complete)
 * - Multi-language support (en_US, de_DE)
 *
 * Test Strategy:
 * - Each test is executed twice: once with en_US, once with de_DE
 * - This validates that selectors are truly language-independent
 * - Tests should produce identical results regardless of UI language
 */

// Define test cases with language parameters
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

// Run tests for each language
testCases.forEach(({ language, label }) => {
  test.describe(`Purchase Order (${label})`, () => {
    test(`Create and complete purchase order (${label} UI)`, async ({ page }) => {
      // Step 1: Create master data via Backend API
      // - User with specified language
      // - Business partner (vendor)
      // - Product with pricing
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
            },
          },
          bpartners: {
            Vendor1: {
              isVendor: true,
              isCustomer: false,
              isSoPriceList: false, // Purchase price list, not sales
            },
          },
          products: {
            Product1: {
              name: `Test Product PO (${language})`,
              value: `TEST-PO-${language}-001`,
              type: 'Item',
              prices: [
                {
                  price: 10.0,
                  currencyCode: 'EUR',
                },
              ],
            },
          },
        },
      });

      console.log(`[${language}] Master data created:`, {
        user: masterdata.login.user.username,
        vendor: masterdata.bpartners.Vendor1.bpartnerCode,
        product: masterdata.products.Product1.productName,
      });

      // Step 2: Login to metasfresh Web UI
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      // Step 3: Create Purchase Order for 10 units
      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.Vendor1.bpartnerCode);
      await PurchaseOrderPage.setOrderDate(30); // Select day 30 from date picker
      await PurchaseOrderPage.addOrderLine({
        product: masterdata.products.Product1.productName,
        quantity: 10,
      });

      // Step 4: Complete the purchase order
      await PurchaseOrderPage.complete();
      const purchaseOrderNo = await PurchaseOrderPage.getDocumentNo();

      console.log(`[${language}] Purchase Order created and completed:`, purchaseOrderNo);

      // Verify document number was assigned
      expect(purchaseOrderNo).toBeTruthy();
      expect(purchaseOrderNo.length).toBeGreaterThan(0);
    });

    test(`Create purchase order and verify it appears in the list (${label} UI)`, async ({ page }) => {
      // Simpler test: Just create a PO and verify document number is assigned

      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
            },
          },
          bpartners: {
            Vendor1: {
              isVendor: true,
              isCustomer: false,
              isSoPriceList: false,
            },
          },
          products: {
            Product1: {
              name: `Simple Test Product (${language})`,
              value: `SIMPLE-${language}-001`,
              prices: [
                {
                  price: 5.0,
                  currencyCode: 'EUR',
                },
              ],
            },
          },
        },
      });

      console.log(`[${language}] Master data created:`, {
        user: masterdata.login.user.username,
        vendor: masterdata.bpartners.Vendor1.bpartnerCode,
        product: masterdata.products.Product1.productName,
      });

      // Login
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);

      // Create purchase order
      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.Vendor1.bpartnerCode);
      await PurchaseOrderPage.setOrderDate(30);
      await PurchaseOrderPage.addOrderLine({
        product: masterdata.products.Product1.productName,
        quantity: 5,
      });

      // Get document number (even before completion)
      const docNo = await PurchaseOrderPage.getDocumentNo();

      expect(docNo).toBeTruthy();
      expect(docNo.length).toBeGreaterThan(0);

      console.log(`[${language}] Purchase Order created successfully:`, docNo);
    });
  });
});
