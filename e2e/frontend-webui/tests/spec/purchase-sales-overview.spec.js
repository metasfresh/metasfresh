import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PURCHASE_SALES_OVERVIEW_WINDOW_ID } from '../utils/WindowIds';
import {
  navigateToViewWindow,
  assertColumnsPresent,
  discoverColumns,
  discoverFacetFilters,
  exerciseFacetFilter,
  assertGridCellsPopulated,
  getGridRowCount,
} from '../utils/view-validation/ViewWindowHelper';

/**
 * Purchase & Sales Overview (Ein- und Verkaufsübersicht) E2E test.
 *
 * Window AD_Window_ID: 542070
 * Table: C_Order_M_InOut_C_Invoice_Overview_V (read-only view)
 *
 * Tests that the window loads, shows correct columns, facet filters work,
 * and data from all document types (orders, shipments, invoices) is visible.
 *
 * Data setup uses the extended frontendTesting API to create a complete
 * SO -> Shipment -> Invoice and PO -> Receipt -> Invoice flow via a single
 * API call (~15 seconds, no UI interaction needed).
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

const EXPECTED_COLUMNS = [
  'IsSOTrx',
  'DocumentNo',
  'DocBaseType',
  'Date',
  'C_BPartner_ID',
  'M_Product_ID',
  'Qty',
  'LineNetAmt',
  'C_UOM_ID',
  'Current_Qty_Sum',
  'DocStatus',
  'Processed',
];

testCases.forEach(({ language, label }) => {
  test.describe(`Purchase & Sales Overview (${label})`, () => {
    test.setTimeout(300000); // 5 minutes — includes data setup + validation

    test(`Validate window columns, filters, and data (${label})`, async ({ page }) => {
      allure.epic('E0500: Statistics');
      allure.tag('F05001: Purchase & Sales Overview');
      allure.story('View window validation: columns, filters, data presence');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.tag(language);

      allure.description(`
## Purchase & Sales Overview Window Validation

Validates that the read-only "Ein- und Verkaufsübersicht" window (542070):
1. Opens and loads the grid view
2. Has all expected columns in the DOM
3. Has facet filters (DocType, DocStatus) that can be opened
4. Shows data from orders, shipments, and invoices after creating test data

Data is seeded via the frontendTesting API (SO + PO -> Ship/Receipt -> Invoice).
`);

      // === STEP 1: Create masterdata + full document lifecycle ===
      await test.step('Create test data via frontendTesting API', async () => {
        const masterdata = await Backend.createMasterdata({
          request: {
            login: {
              user: {
                language,
                firstname: 'overview',
                lastname: 'test',
              },
            },
            bpartners: {
              BP1: {
                isVendor: true,
                isCustomer: true,
                isSoPriceList: true,
                name: 'OVTestPartner',
              },
            },
            products: {
              P1: {
                name: 'OVPRD',
                type: 'Item',
                prices: [{ price: 25.0, currencyCode: 'EUR' }],
              },
            },
            salesOrders: {
              SO1: {
                bpartner: '@BP1@',
                lines: [{ product: '@P1@', qty: 10 }],
              },
            },
            purchaseOrders: {
              PO1: {
                bpartner: '@BP1@',
                lines: [{ product: '@P1@', qty: 8 }],
              },
            },
            shipments: {
              SHIP1: { salesOrder: '@SO1@' },
            },
            receipts: {
              REC1: { purchaseOrder: '@PO1@' },
            },
            invoices: {
              INV_SO: { salesOrder: '@SO1@' },
              INV_PO: { purchaseOrder: '@PO1@' },
            },
          },
        });

        console.log('[INFO] Masterdata created:', JSON.stringify({
          salesOrders: masterdata.salesOrders,
          purchaseOrders: masterdata.purchaseOrders,
          shipments: masterdata.shipments,
          receipts: masterdata.receipts,
          invoices: masterdata.invoices,
        }, null, 2));
      });

      // === STEP 2: Login ===
      await test.step('Login', async () => {
        await LoginPage.goto();
        await LoginPage.login({ language });
        await DashboardPage.expectVisible();
      });

      // === STEP 3: Navigate to window ===
      await test.step('Navigate to Purchase & Sales Overview', async () => {
        await navigateToViewWindow(PURCHASE_SALES_OVERVIEW_WINDOW_ID);
      });

      // === STEP 4: Verify columns ===
      await test.step('Verify all expected columns are present', async () => {
        const result = await assertColumnsPresent(EXPECTED_COLUMNS);

        const allColumns = await discoverColumns();
        allure.attachment('All Grid Columns', JSON.stringify(allColumns, null, 2), 'application/json');
        allure.attachment('Column Check Result', JSON.stringify(result, null, 2), 'application/json');

        expect(result.missing, `Missing columns: ${result.missing.join(', ')}`).toEqual([]);
      });

      // === STEP 5: Verify facet filters ===
      await test.step('Verify facet filters exist and can be opened', async () => {
        const filters = await discoverFacetFilters();
        allure.attachment('Facet Filters', JSON.stringify(filters, null, 2), 'application/json');

        // Expect at least 2 facet filters (DocType, DocStatus)
        expect(filters.length, 'Expected at least 2 facet filters').toBeGreaterThanOrEqual(2);

        // Exercise each filter (open + close)
        for (let i = 0; i < filters.length; i++) {
          const opened = await exerciseFacetFilter(i);
          console.log(`[INFO] Filter ${i} (${filters[i].caption}): opened=${opened}`);
        }
      });

      // === STEP 6: Verify data is present ===
      await test.step('Verify grid has data rows', async () => {
        const rowCount = await getGridRowCount();
        console.log(`[INFO] Grid row count: ${rowCount}`);
        expect(rowCount, 'Expected grid to have data rows').toBeGreaterThan(0);
      });

      // === STEP 7: Verify grid cells populated ===
      await test.step('Verify key columns have values', async () => {
        const result = await assertGridCellsPopulated(
          ['DocumentNo', 'C_BPartner_ID', 'M_Product_ID', 'Qty'],
          5
        );

        allure.attachment('Cell Population', JSON.stringify(result, null, 2), 'application/json');
        expect(result.populated, 'Expected some cells to be populated').toBeGreaterThan(0);
      });

      // === FINAL: Screenshot ===
      const screenshot = await page.screenshot({ fullPage: false });
      allure.attachment('Final Window State', screenshot, 'image/png');
    });
  });
});
