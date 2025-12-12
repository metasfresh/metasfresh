import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { ShipmentPage } from '../utils/pages/ShipmentPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Sales Order to Shipment Schedule to Shipment to Invoice E2E test suite.
 *
 * Tests the complete sales order to shipment to invoice workflow:
 * 1. Create a sales order with customer and product
 * 2. Complete the sales order
 * 3. Generate and validate sales order PDF (Alt+P)
 * 4. Navigate to Shipment Schedule (Alt+6)
 * 5. Validate the ordered quantity appears in the shipment schedule
 * 6. Create shipment from shipment schedule (M_ShipmentSchedule_EnqueueSelection action)
 * 7. Navigate back to sales order and zoom to shipment (Alt+6)
 * 8. Generate and validate shipment PDF
 * 9. Navigate back to sales order and zoom to invoice candidates (Alt+6)
 * 10. Create invoice from invoice candidates (C_Invoice_Candidate_EnqueueSelectionForInvoicing action)
 * 11. Navigate back to sales order and zoom to invoice (Alt+6)
 * 12. Generate and validate invoice PDF
 *
 * This validates language-independent selectors for:
 * - Sales order creation and completion
 * - PDF generation modal and download
 * - PDF content validation (document number, customer, product, quantity)
 * - Related documents navigation (Alt+6)
 * - Shipment schedule window and quantity field
 * - Quick action invocation on list views
 * - Shipment creation and PDF generation
 * - Invoice candidate creation and processing
 * - Invoice PDF generation and validation
 */

// Test cases for multi-language validation
const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
    test.describe(`Sales Order to Shipment Schedule (${label})`, () => {
        test(`Create SO and validate quantity in shipment schedule (${label} UI)`, async ({ page }) => {
            // Extend timeout for this comprehensive E2E test (Sales Order → Shipment → Invoice)
            test.setTimeout(120000); // 2 minutes

            // Step 1: Create test data with specified language
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: {
                        user: {
                            language,
                            // login not specified - backend will auto-generate unique user_timestamp
                            firstname: 'first',
                            lastname: 'last'
                        },
                    },
                    bpartners: {
                        CUSTOMER1: {
                            isVendor: false,
                            isCustomer: true, // ← Sales customer
                            isSoPriceList: true, // ← Sales price list
                            name: 'Customer'
                        },
                    },
                    products: {
                        Product1: {
                            name: 'PROD',  // Short base name - backend will append timestamp (max 16 chars total)
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

            // Step 4: Open print modal and generate PDF
            await SalesOrderPage.openPrintModal();

            // Step 4.1: Download PDF
            const download = await SalesOrderPage.downloadPDF();
            console.log(`[${language}] PDF downloaded: ${download.suggestedFilename()}`);

            // Step 4.2: Validate PDF content
            await SalesOrderPage.validatePdfContent(download, {
                documentNo: soDocumentNo,
                // customerName: masterdata.bpartners.CUSTOMER1.bpartnerCode,  // TODO: PDF text extraction breaks long strings with line breaks
                productCode: masterdata.products.Product1.productCode,
                quantity: '10',
                language,
            });

            console.log(`[${language}] PDF content validated successfully`);

            // Step 4.3: Close modal
            await SalesOrderPage.closePrintModal().catch(() => {
            });

            // Step 5: Navigate to shipment schedule via Alt+6
            // This navigates directly to the correct shipment schedule for this SO
            // IMPORTANT: Do NOT navigate to window 500221 directly - it may select wrong schedule!
            await SalesOrderPage.openRelatedShipmentCandidate();

            // Verify shipment schedule is visible
            await ShipmentSchedulePage.expectVisible();

            console.log(`[${language}] Shipment Schedule opened for SO ${soDocumentNo}`);

            // Step 6: Validate ordered quantity
            await ShipmentSchedulePage.expectOrderedQuantity('10');

            console.log(`[${language}] Verified ordered quantity: 10`);

            // Step 7: Create shipment from shipment schedule
            await ShipmentSchedulePage.createShipment();
            console.log(`[${language}] Shipment created from schedule`);

            // Wait for shipment to be fully created and linked (async process)
            await page.waitForTimeout(5000);

            // Step 8: Navigate back to sales order detail view
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page
                .locator('.rotating, .panel-spaced-lg')
                .waitFor({
                    state: 'detached',
                    timeout: SLOW_ACTION_TIMEOUT,
                })
                .catch(() => {});
            await page.waitForTimeout(500);

            console.log(`[${language}] Navigated back to Sales Order ${soDocumentNo}`);

            // Step 9: Zoom to shipment from sales order (Alt+6)
            // Wait longer for shipment to be fully linked (10 seconds)
            await SalesOrderPage.openRelatedShipment(10000);
            console.log(`[${language}] Navigated to Shipment from Sales Order`);

            // Step 10: Verify shipment is visible
            await ShipmentPage.expectVisible();

            // Step 11: Get shipment document number
            const shipmentDocNo = await ShipmentPage.getDocumentNo();
            expect(shipmentDocNo).toBeTruthy();
            expect(shipmentDocNo.length).toBeGreaterThan(0);
            console.log(`[${language}] Shipment created: ${shipmentDocNo}`);

            // Step 12: Open shipment detail view (required for printing from list view)
            await ShipmentPage.openDetailView();

            // Step 13: Generate and validate shipment PDF
            await ShipmentPage.openPrintModal();

            const shipmentDownload = await ShipmentPage.downloadPDF();
            console.log(`[${language}] Shipment PDF downloaded: ${shipmentDownload.suggestedFilename()}`);

            await ShipmentPage.validatePdfContent(shipmentDownload, {
                documentNo: shipmentDocNo,
                productCode: masterdata.products.Product1.productCode,
                quantity: '10',
                language,
            });

            console.log(`[${language}] Shipment PDF content validated successfully`);

            await ShipmentPage.closePrintModal().catch(() => {});

            // Step 14: Navigate back to sales order detail view
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page
                .locator('.rotating, .panel-spaced-lg')
                .waitFor({
                    state: 'detached',
                    timeout: SLOW_ACTION_TIMEOUT,
                })
                .catch(() => {});
            await page.waitForTimeout(500);

            console.log(`[${language}] Navigated back to Sales Order ${soDocumentNo}`);

            // Step 15: Zoom to invoice candidates from sales order (Alt+6)
            // Wait for invoice candidates to be created (5 seconds)
            await SalesOrderPage.openRelatedInvoiceCandidate(5000);
            console.log(`[${language}] Navigated to Invoice Candidates from Sales Order`);

            // Step 16: Verify invoice candidate window is visible
            await InvoiceCandidatePage.expectVisibleForSalesOrder();

            // Step 17: Create invoice from invoice candidates
            await InvoiceCandidatePage.createInvoiceForSalesOrder();
            console.log(`[${language}] Invoice created from invoice candidates`);

            // Wait for invoice to be fully created and linked (async process)
            await page.waitForTimeout(5000);

            // Step 18: Navigate back to sales order detail view
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page
                .locator('.rotating, .panel-spaced-lg')
                .waitFor({
                    state: 'detached',
                    timeout: SLOW_ACTION_TIMEOUT,
                })
                .catch(() => {});
            await page.waitForTimeout(500);

            console.log(`[${language}] Navigated back to Sales Order ${soDocumentNo} for invoice zoom`);

            // Step 19: Zoom to invoice from sales order (Alt+6)
            // Wait for invoice to be fully linked (5 seconds)
            await SalesOrderPage.openRelatedInvoice(5000);
            console.log(`[${language}] Navigated to Invoice from Sales Order`);

            // Step 20: Verify invoice is visible
            await InvoicePage.expectVisible();

            // Step 21: Get invoice document number
            const invoiceDocNo = await InvoicePage.getDocumentNo();
            expect(invoiceDocNo).toBeTruthy();
            expect(invoiceDocNo.length).toBeGreaterThan(0);
            console.log(`[${language}] Invoice created: ${invoiceDocNo}`);

            // Step 22: Open invoice detail view (required for printing from list view)
            await InvoicePage.openDetailView();

            // Step 23: Generate and validate invoice PDF
            await InvoicePage.openPrintModal();

            const invoiceDownload = await InvoicePage.downloadPDF();
            console.log(`[${language}] Invoice PDF downloaded: ${invoiceDownload.suggestedFilename()}`);

            await InvoicePage.validatePdfContent(invoiceDownload, {
                documentNo: invoiceDocNo,
                productCode: masterdata.products.Product1.productCode,
                quantity: '10',
                language,
            });

            console.log(`[${language}] Invoice PDF content validated successfully`);

            await InvoicePage.closePrintModal().catch(() => {});

            // The test is now complete
            // We successfully created a sales order, validated the shipment schedule,
            // created the shipment, validated the shipment PDF,
            // created the invoice, and validated the invoice PDF
        });
    });
});
