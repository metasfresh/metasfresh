import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import * as fs from 'fs';
import * as os from 'os';
import * as path from 'path';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { ShipmentPage } from '../utils/pages/ShipmentPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { PdfValidator } from '../utils/PdfValidator';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID, SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';

/**
 * Sales Order to Shipment Schedule to Shipment to Invoice E2E test suite.
 *
 * Features tested (from Google Sheets):
 * - F00100: Sales Order
 * - F00105: Sales Order Document (PDF)
 * - F00130: Shipment Schedule
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
            // === ALLURE METADATA ===
            // Feature metadata - IDs for filtering, full names in description
            allure.epic('E0100: Sales');
            allure.tag('F00100: Sales Order');
            allure.tag('F00100');  // Standalone tag for Tags section
            allure.tag('F00105: Sales Order Document');
            allure.tag('F00105');  // Standalone tag for Tags section
            allure.tag('F00130: Shipment Schedule');
            allure.tag('F00130');  // Standalone tag for Tags section
            allure.tag('F00150: Sales Shipment');
            allure.tag('F00150');  // Standalone tag for Tags section
            allure.tag('F00200: Sales Invoice');
            allure.tag('F00200');  // Standalone tag for Tags section
            allure.story('Complete Order-to-Cash: SO → Shipment → Invoice');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.parameter('UI Label', label);
            allure.tag(language);

            allure.description(`
## E0100: Sales

## F00100: Sales Order
## F00105: Sales Order Document
## F00130: Shipment Schedule
## F00150: Sales Shipment
## F00200: Sales Invoice

### Test Scenario
This test validates the complete order-to-cash workflow:

1. **Create Sales Order** - New SO with customer and product line
2. **Complete Order** - Mark as completed to trigger downstream processes
3. **Generate PDF** - Create and validate Sales Order PDF document
4. **Navigate to Shipment Schedule** - Use Alt+6 to open related shipment schedule
5. **Verify Quantity** - Confirm ordered quantity appears in schedule
6. **Create Shipment** - Generate shipment from schedule and validate PDF
7. **Create Invoice** - Generate invoice from candidates and validate PDF

### Business Value
Ensures the complete order-to-cash flow works correctly across UI languages.
            `);

            // Extend timeout for this comprehensive E2E test (Sales Order → Shipment → Invoice)
            test.setTimeout(120000); // 2 minutes

            // Step 1: Create test data with specified language
            // === TEST DATA CREATION ===
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

            // Attach test data summary to Allure report
            allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

            console.log(`[${language}] Master data created:`, {
                customer: masterdata.bpartners.CUSTOMER1.bpartnerCode,
                product: masterdata.products.Product1.productName,
            });

            // === TEST EXECUTION ===

            // Step 1: Login
            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();

            // Step 2: Create Sales Order
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();

            // Select customer - this waits for record to be saved (auto-fill completes)
            const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
            console.log(`[${language}] Sales Order ${recordId} created and saved`);

            // Add order line - this waits for tab to allow new records before proceeding
            const orderLineData = {
                product: masterdata.products.Product1.productCode,
                quantity: '10',
                recordId,
            };
            await SalesOrderPage.addOrderLine(orderLineData);

            // Attach order line details as table
            const orderLinesHtml = `<table border="1"><tr><th>Product</th><th>Quantity</th><th>Unit Price</th><th>Line Total</th></tr><tr><td>${masterdata.products.Product1.productCode}</td><td>10</td><td>50.00 EUR</td><td>500.00 EUR</td></tr></table>`;
            allure.attachment('Order Lines', orderLinesHtml, 'text/html');

            // Step 3: Complete the order
            await SalesOrderPage.complete();

            // Get and verify document number
            const soDocumentNo = await SalesOrderPage.getDocumentNo();
            expect(soDocumentNo).toBeTruthy();
            expect(soDocumentNo.length).toBeGreaterThan(0);

            // Add document number as parameter for easy identification
            // excluded: true prevents this dynamic value from affecting Allure's historyId,
            // so failed retry attempts are properly linked to the successful retry
            allure.parameter('Document No', soDocumentNo, { excluded: true });

            console.log(`[${language}] Sales Order created: ${soDocumentNo}`);

            // Step 4: Generate and validate PDF
            await SalesOrderPage.openPrintModal();

            const download = await SalesOrderPage.downloadPDF();
            console.log(`[${language}] PDF downloaded: ${download.suggestedFilename()}`);

            // Attach PDF to Allure report
            const pdfPath = await download.path();
            const pdfContent = fs.readFileSync(pdfPath);
            allure.attachment('Sales Order PDF', pdfContent, 'application/pdf');

            // Validate PDF content
            await SalesOrderPage.validatePdfContent(download, {
                documentNo: soDocumentNo,
                // customerName: masterdata.bpartners.CUSTOMER1.bpartnerCode,  // TODO: PDF text extraction breaks long strings with line breaks
                productCode: masterdata.products.Product1.productCode,
                quantity: '10',
                language,
            });

            console.log(`[${language}] PDF content validated successfully`);

            await SalesOrderPage.closePrintModal().catch(() => {});

            // Step 5: Navigate to Shipment Schedule
            // This navigates directly to the correct shipment schedule for this SO
            // IMPORTANT: Do NOT navigate to window 500221 directly - it may select wrong schedule!
            await SalesOrderPage.openRelatedShipmentCandidate();
            await ShipmentSchedulePage.expectVisible();

            // Take screenshot of shipment schedule for report
            const screenshotBuffer = await page.screenshot();
            allure.attachment('Shipment Schedule View', screenshotBuffer, 'image/png');

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
            // Default: 5 retries × 2s delay = 10 seconds max wait time
            await SalesOrderPage.openRelatedShipment();
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
            await SalesOrderPage.openRelatedInvoiceCandidate({ retryDelay: 5000 });
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
            // Default: 5 retries × 2s delay = 10 seconds max wait time
            await SalesOrderPage.openRelatedInvoice();
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

            // Attach validation summary
            const validationHtml = `<table border="1">
                <tr><th>Check</th><th>Status</th><th>Value</th></tr>
                <tr><td>Sales Order Created</td><td>PASS</td><td>${soDocumentNo}</td></tr>
                <tr><td>PDF Generated</td><td>PASS</td><td>${download.suggestedFilename()}</td></tr>
                <tr><td>Shipment Created</td><td>PASS</td><td>Yes</td></tr>
                <tr><td>Shipment-PDF Generated</td><td>PASS</td><td>${shipmentDownload.suggestedFilename()}</td></tr>
                <tr><td>Invoice Created</td><td>PASS</td><td>Yes</td></tr>
                <tr><td>Invoice-PDF Generated</td><td>PASS</td><td>${invoiceDownload.suggestedFilename()}</td></tr>
            </table>`;
            allure.attachment('Validation Results', validationHtml, 'text/html');
        });
    });
});

// =============================================================================
// ZUGFeRD e-Invoice variant
//
// NOTE: This spec intentionally defines a second createMasterdata — the ZUGFeRD
// setup requires a BR-DE-conformant seller org-config (orgSeller + VATaxID +
// address + IBAN + default contact) and a ZUGFeRD buyer that are incompatible
// with the plain O2C masterdata above. A separate test file would duplicate the
// entire page-object flow; augmenting here fixes the standalone spec's CI failure
// (missing M_Warehouse_ID / C_BPartner_ID etc.) by reusing the proven flow.
// =============================================================================

test.describe('ZUGFeRD e-Invoice (archived PDF embeds factur-x.xml)', () => {
    test('Completed sales invoice PDF is a valid ZUGFeRD file with intact content', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0340: Invoicing');
        allure.tag('F00751: e-Invoicing Germany');
        allure.tag('F00751');
        allure.story('ZUGFeRD invoice: archived PDF embeds factur-x.xml + intact content');
        allure.severity('critical');
        allure.parameter('Language', 'de_DE');

        allure.description(`
## E0340: Invoicing
## F00751: e-Invoicing Germany

### Test Scenario
Validates that a sales invoice for a ZUGFeRD recipient produces a PDF/A-3 archive
with an embedded factur-x.xml (Factur-X / ZUGFeRD CII XML) and intact invoice content.

### Flow
1. createMasterdata: seller org-config + ZUGFeRD buyer + product/pricing + IsPdfA3Output
2. Login, create + complete sales order (reuses SalesOrderPage page-object flow)
3. Shipment via ShipmentSchedulePage.createShipment()
4. Invoice via InvoiceCandidatePage.createInvoiceForSalesOrder()
5. Fetch ARCHIVED invoice PDF via /attachments endpoint (not Alt+P re-render)
6. Assert factur-x.xml embedded (ZUGFeRD check via PdfValidator.validateZugferdAttachment)
7. Assert invoice content intact (documentNo / product / qty in PDF text)

### Archive strategy
The CII XML is embedded at ARCHIVE time (invoice AFTER_COMPLETE interceptor calls
ZugferdAssembler.embed()). Alt+P triggers a fresh re-render — it does NOT carry the
embedded CII. The archived PDF is fetched via:
  GET /rest/api/window/167/{invoiceRecordId}/attachments
    -> JSON array; archive entries have id prefix "ARR-"
  GET /rest/api/window/167/{invoiceRecordId}/attachments/ARR-{archiveId}
    -> streams the raw PDF bytes (application/pdf)
We poll up to 30s because the archive write is async post-completion.

### Language independence
All selectors use data-testid / data-cy / ColumnName IDs. Test runs in de_DE;
the ZUGFeRD assertion is binary (embedded or not) — language does not affect it.
        `);

        test.setTimeout(180000); // 3 min — ZUGFeRD assembly adds latency

        // === TEST DATA CREATION ===
        // Full BR-DE-conformant seller + ZUGFeRD buyer masterdata.
        // Mirrors the Background of:
        //   backend/de.metas.cucumber/src/test/resources/de/metas/cucumber/features/einvoice/eInvoiceZugferdEmail.feature
        const masterdata = await Backend.createMasterdata({
            request: {
                sysconfigs: {
                    'de.metas.report.jasper.IsMockReportService': 'true',
                },
                adProcessFlags: [
                    {
                        jasperReportSubstring: 'de/metas/docs/sales/invoice',
                        isPdfA3Output: true,
                    },
                ],
                bpartners: {
                    // Seller: BR-DE-conformant org-bpartner for the login org.
                    // CII mapper reads seller from invoice org's org-bpartner
                    // (bPartnerDAO.retrieveOrgBPartner(invoice.AD_Org_ID)).
                    seller: {
                        bpartnerCode: 'ZFDSELLER',
                        name: 'Muster GmbH',
                        vatTaxId: 'DE123456789',
                        isCustomer: false,
                        isSoPriceList: false,
                        locations: {
                            sellerLoc: {
                                gln: '4099999000002',
                                city: 'Berlin',
                                postal: '10115',
                                address1: 'Musterstrasse 1',
                                countryCode: 'DE',
                            },
                        },
                        contacts: {
                            sellerContact: {
                                firstName: 'Max',
                                lastName: 'Mustermann',
                                email: 'max.mustermann@muster.de',
                                isDefaultContact: true,
                            },
                        },
                        bankAccounts: {
                            sellerBank: {
                                iban: 'DE89370400440532013000',
                                currencyCode: 'EUR',
                            },
                        },
                    },
                    // Buyer: ZUGFeRD e-invoice recipient.
                    buyer: {
                        name: 'Kaeufer AG',
                        isCustomer: true,
                        isSoPriceList: true,
                        isEInvoiceRecipeint: true,
                        eInvoiceType: 'Z',
                        eInvoiceBuyerReference: '991-1234512345-06',
                        vatTaxId: 'DE987654321',
                        locations: {
                            buyerLoc: {
                                city: 'Hamburg',
                                postal: '20095',
                                address1: 'Kaeuferweg 5',
                                countryCode: 'DE',
                            },
                        },
                    },
                },
                // Link seller BPartner+location as the login org's org-bpartner.
                // orgIdentifier=null -> OrgId.MAIN (the default login org).
                orgSeller: {
                    bpartnerIdentifier: 'seller',
                    bpartnerLocationIdentifier: 'sellerLoc',
                },
                products: {
                    product: {
                        name: 'Testprodukt',
                        type: 'Item',
                        prices: [
                            {
                                price: 100.0,
                                currencyCode: 'EUR',
                            },
                        ],
                    },
                },
                login: {
                    user: {
                        language: 'de_DE',
                    },
                },
            },
        });

        allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');
        console.log('[INFO] Masterdata created. buyer:', masterdata.bpartners.buyer.bpartnerCode,
            'product:', masterdata.products.product.productCode);

        // === LOGIN ===
        await LoginPage.goto();
        await LoginPage.login(masterdata.login.user);
        await DashboardPage.expectVisible();
        console.log('[INFO] Login successful');

        // === CREATE + COMPLETE SALES ORDER ===
        await SalesOrderPage.goto();
        await SalesOrderPage.clickNew();

        const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.buyer.bpartnerCode);
        console.log('[INFO] Sales Order created, recordId:', recordId);

        await SalesOrderPage.addOrderLine({
            product: masterdata.products.product.productCode,
            quantity: '1',
            recordId,
        });

        await SalesOrderPage.complete();

        const soDocumentNo = await SalesOrderPage.getDocumentNo();
        expect(soDocumentNo).toBeTruthy();
        allure.parameter('SO DocumentNo', soDocumentNo, { excluded: true });
        console.log('[INFO] Sales Order completed:', soDocumentNo);

        await SalesOrderPage.closePrintModal().catch(() => {});

        // === SHIPMENT ===
        await SalesOrderPage.openRelatedShipmentCandidate();
        await ShipmentSchedulePage.expectVisible();
        await ShipmentSchedulePage.createShipment();
        console.log('[INFO] Shipment created');

        // Wait for shipment async processing
        await page.waitForTimeout(5000);

        // === INVOICE CANDIDATES ===
        // Navigate back to SO and zoom to invoice candidates
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(500);

        await SalesOrderPage.openRelatedInvoiceCandidate({ retryDelay: 5000 });
        console.log('[INFO] Navigated to Invoice Candidates');

        await InvoiceCandidatePage.expectVisibleForSalesOrder();
        await InvoiceCandidatePage.createInvoiceForSalesOrder();
        console.log('[INFO] Invoice created from candidates');

        // Wait for invoice async processing
        await page.waitForTimeout(5000);

        // === NAVIGATE TO INVOICE ===
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(500);

        await SalesOrderPage.openRelatedInvoice();
        await InvoicePage.expectVisible();

        const invoiceDocNo = await InvoicePage.getDocumentNo();
        expect(invoiceDocNo).toBeTruthy();
        allure.parameter('Invoice DocumentNo', invoiceDocNo, { excluded: true });
        console.log('[INFO] Invoice visible:', invoiceDocNo);

        await InvoicePage.openDetailView();

        // Extract invoice record ID from URL (needed for attachments endpoint)
        const invoiceUrl = page.url();
        const invoiceRecordId = invoiceUrl.split('/').pop();
        expect(invoiceRecordId).toMatch(/^\d+$/);
        console.log('[INFO] Invoice record ID:', invoiceRecordId);

        // === FETCH ARCHIVED PDF (contains embedded factur-x.xml) ===
        //
        // The CII XML is embedded at ARCHIVE time (invoice AFTER_COMPLETE).
        // Alt+P re-renders a fresh PDF — it does NOT carry the embedded CII.
        // We must fetch the AD_Archive entry created at completion time.
        //
        // WebUI endpoint:
        //   GET /rest/api/window/167/{invoiceRecordId}/attachments
        //   -> JSON array of { id, filename, ... }; archive entries have id = "ARR-{archiveId}"
        //   GET /rest/api/window/167/{invoiceRecordId}/attachments/{id}
        //   -> streams the raw archived PDF bytes
        //
        // We poll up to 30s because the archive write is async post-completion.

        let archivedPdfBuffer = null;
        let archiveEntryId = null;

        const attachmentsUrl = `${WEBAPI_BASE_URL}/window/${SALES_INVOICE_WINDOW_ID}/${invoiceRecordId}/attachments`;
        const pollDeadline = Date.now() + 30000;

        while (!archivedPdfBuffer && Date.now() < pollDeadline) {
            const listResp = await page.request.get(attachmentsUrl);
            if (listResp.ok()) {
                const entries = await listResp.json();
                // Find an archive entry (id starts with "ARR-")
                const archiveEntry = Array.isArray(entries)
                    ? entries.find((e) => e.id && String(e.id).startsWith('ARR-'))
                    : null;

                if (archiveEntry) {
                    archiveEntryId = archiveEntry.id;
                    console.log('[INFO] Found archive entry:', archiveEntryId, 'filename:', archiveEntry.filename);

                    const pdfResp = await page.request.get(`${attachmentsUrl}/${archiveEntryId}`);
                    if (pdfResp.ok()) {
                        const bodyBuffer = await pdfResp.body();
                        archivedPdfBuffer = Buffer.from(bodyBuffer);
                        console.log('[INFO] Archived PDF downloaded:', archivedPdfBuffer.length, 'bytes');
                    } else {
                        console.log('[WARN] Archive entry GET failed:', pdfResp.status());
                    }
                } else {
                    console.log('[INFO] No archive entry yet, retrying...');
                }
            } else {
                console.log('[WARN] Attachments list GET failed:', listResp.status());
            }

            if (!archivedPdfBuffer) {
                await page.waitForTimeout(3000);
            }
        }

        expect(archivedPdfBuffer, 'Expected archived invoice PDF to be available within 30s').not.toBeNull();

        // Attach archived PDF to Allure report
        allure.attachment(`archived-invoice-${invoiceDocNo}.pdf`, archivedPdfBuffer, 'application/pdf');

        // === VALIDATE ZUGFeRD: factur-x.xml embedded ===
        console.log('[INFO] Validating ZUGFeRD attachment in archived PDF...');
        await PdfValidator.validateZugferdAttachment(archivedPdfBuffer);
        console.log('[PASS] ZUGFeRD factur-x.xml attachment present');

        // === VALIDATE INVOICE CONTENT: documentNo / product / qty present ===
        // PdfValidator.validate() expects a Download-like object with path() and suggestedFilename().
        // We create a minimal wrapper that writes the buffer to a temp file and cleans it up after.
        console.log('[INFO] Validating invoice content in archived PDF...');
        let tmpPdfPath = null;
        const downloadProxy = {
            path: async () => {
                tmpPdfPath = path.join(os.tmpdir(), `zugferd-invoice-${Date.now()}.pdf`);
                fs.writeFileSync(tmpPdfPath, archivedPdfBuffer);
                return tmpPdfPath;
            },
            suggestedFilename: async () => `invoice-${invoiceDocNo}.pdf`,
        };

        try {
            await PdfValidator.validate(downloadProxy, {
                documentNo: invoiceDocNo,
                productCode: masterdata.products.product.productCode,
                quantity: '1',
                language: 'de_DE',
                checkOverlaps: false, // Layout overlaps not relevant; ZUGFeRD check is the gate
                checkMargins: false,
            });
        } finally {
            if (tmpPdfPath) {
                fs.unlinkSync(tmpPdfPath);
            }
        }

        console.log('[PASS] Invoice content validated: documentNo, product, quantity present');

        // Summary attachment
        const summaryHtml = `<table border="1">
            <tr><th>Check</th><th>Status</th><th>Value</th></tr>
            <tr><td>Sales Order</td><td>PASS</td><td>${soDocumentNo}</td></tr>
            <tr><td>Shipment created</td><td>PASS</td><td>yes</td></tr>
            <tr><td>Invoice created</td><td>PASS</td><td>${invoiceDocNo}</td></tr>
            <tr><td>Archived PDF fetched</td><td>PASS</td><td>${archivedPdfBuffer.length} bytes (entry ${archiveEntryId})</td></tr>
            <tr><td>factur-x.xml embedded</td><td>PASS</td><td>ZUGFeRD confirmed</td></tr>
            <tr><td>Invoice content intact</td><td>PASS</td><td>documentNo + product + qty in PDF text</td></tr>
        </table>`;
        allure.attachment('Validation Summary', summaryHtml, 'text/html');
    });
});
