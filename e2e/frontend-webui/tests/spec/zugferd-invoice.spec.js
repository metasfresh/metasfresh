import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { PdfValidator } from '../utils/PdfValidator';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID, SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';

/**
 * ZUGFeRD e-Invoice E2E test suite.
 *
 * Features tested (from Google Sheets):
 * - F00751: e-Invoicing Germany
 *
 * Validates that a sales invoice for a ZUGFeRD recipient produces a PDF/A-3 archive
 * with an embedded factur-x.xml (Factur-X / ZUGFeRD CII XML) and intact invoice content.
 *
 * Runs in both en_US and de_DE to prove language-independence of all selectors.
 * The ZUGFeRD assertion itself is binary (embedded or not) and language-invariant.
 */

// Test cases for multi-language validation
const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

// Line product name — set in the masterdata request and asserted against the CII line item.
// M_Product.Name is not language-translated, so it appears identically in both en_US and de_DE.
const PRODUCT_NAME = 'Testprodukt';

testCases.forEach(({ language, label }) => {
    test.describe(`ZUGFeRD e-Invoice (${label})`, () => {
        test(`Completed sales invoice PDF is a valid ZUGFeRD file with intact content (${label})`, async ({ page }) => {
            // === ALLURE METADATA ===
            allure.epic('E0340: Invoicing');
            allure.tag('F00751: e-Invoicing Germany');
            allure.tag('F00751');
            allure.story('ZUGFeRD invoice: archived PDF embeds factur-x.xml + intact content');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.parameter('UI Label', label);
            allure.tag(language);

            allure.description(`
## E0340: Invoicing
## F00751: e-Invoicing Germany

### Test Scenario
Validates that a sales invoice for a ZUGFeRD recipient produces a PDF/A-3 archive
with an embedded factur-x.xml (Factur-X / ZUGFeRD CII XML) and intact invoice content.

### Flow
1. createMasterdata: seller org-config + ZUGFeRD buyer + product/pricing + IsPdfA3Output
2. Login (${label}), create + complete sales order (reuses SalesOrderPage page-object flow)
3. Shipment via ShipmentSchedulePage.createShipment()
4. Invoice via InvoiceCandidatePage.createInvoiceForSalesOrder()
5. Fetch ARCHIVED invoice PDF via /attachments endpoint (not Alt+P re-render)
6. Assert factur-x.xml embedded (ZUGFeRD check via PdfValidator.validateZugferdAttachment)
7. Assert invoice content intact (documentNo / product / qty in the embedded CII)

### Archive strategy
The CII XML is embedded at ARCHIVE time (invoice AFTER_COMPLETE interceptor calls
ZugferdAssembler.embed()). Alt+P triggers a fresh re-render — it does NOT carry the
embedded CII. The archived PDF is fetched via:
  GET /rest/api/window/167/{invoiceRecordId}/attachments
    -> JSON array; archive entries have id prefix "ARR_"
  GET /rest/api/window/167/{invoiceRecordId}/attachments/ARR_{archiveId}
    -> streams the raw PDF bytes (application/pdf)
We poll up to 120s because the archive write is async post-completion.

### Language independence
All selectors use data-testid / data-cy / ColumnName IDs. The ZUGFeRD assertion is
binary (embedded or not) — language does not affect it. Test runs in both en_US and
de_DE to prove the full page-object flow is language-independent.
            `);

            test.setTimeout(180000); // 3 min — ZUGFeRD assembly adds latency

            // === TEST DATA CREATION ===
            // Full BR-DE-conformant seller + ZUGFeRD buyer masterdata.
            // Mirrors the Background of:
            //   backend/de.metas.cucumber/src/test/resources/de/metas/cucumber/features/einvoice/eInvoiceZugferdEmail.feature
            const masterdata = await Backend.createMasterdata({
                request: {
                    // NB: do NOT set IsMockReportService here — the frontend-webui stack renders REAL
                    // PDFs (the standard O2C test above validates real invoice-PDF content). The real
                    // report service renders the invoice as PDF/A-3 (driven by adProcessFlags below),
                    // the archive seam embeds the CII, and we validate that real archived PDF. Mocking
                    // would substitute a fixture and make the content assertions vacuous/failing.
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
                            // No fixed bpartnerCode/GLN: this spec runs in en_US + de_DE against a
                            // shared e2e DB, so hardcoded unique values (C_BPartner.Value,
                            // C_BPartner_Location.GLN) collide on the 2nd run. Let the backend
                            // auto-generate a unique Value; GLN is not required for ZUGFeRD EN16931
                            // validity (VATaxID covers BR-CO-26; BT-34 comes from the mailbox).
                            name: 'Muster GmbH',
                            vatTaxId: 'DE136695976',
                            isCustomer: false,
                            // true: the masterdata shares one pricing system across bpartners, and the
                            // first-created one sets its price list's IsSOPriceList — the buyer needs a sales list.
                            isSoPriceList: true,
                            locations: {
                                sellerLoc: {
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
                            vatTaxId: 'DE811569869',
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
                            name: PRODUCT_NAME,
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
                            language,
                        },
                    },
                },
            });

            allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');
            console.log(`[${language}] Masterdata created. buyer:`, masterdata.bpartners.buyer.bpartnerCode,
                'product:', masterdata.products.product.productCode);

            // === LOGIN ===
            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();
            console.log(`[${language}] Login successful`);

            // === CREATE + COMPLETE SALES ORDER ===
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();

            const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.buyer.bpartnerCode);
            console.log(`[${language}] Sales Order created, recordId:`, recordId);

            await SalesOrderPage.addOrderLine({
                product: masterdata.products.product.productCode,
                quantity: '1',
                recordId,
            });

            await SalesOrderPage.complete();

            const soDocumentNo = await SalesOrderPage.getDocumentNo();
            expect(soDocumentNo).toBeTruthy();
            allure.parameter('SO DocumentNo', soDocumentNo, { excluded: true });
            console.log(`[${language}] Sales Order completed:`, soDocumentNo);

            await SalesOrderPage.closePrintModal().catch(() => {});

            // === SHIPMENT ===
            await SalesOrderPage.openRelatedShipmentCandidate();
            await ShipmentSchedulePage.expectVisible();
            await ShipmentSchedulePage.createShipment();
            console.log(`[${language}] Shipment created`);

            // Wait for shipment async processing
            await page.waitForTimeout(5000);

            // === INVOICE CANDIDATES ===
            // Navigate back to SO and zoom to invoice candidates
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.waitForTimeout(500);

            await SalesOrderPage.openRelatedInvoiceCandidate({ retryDelay: 5000 });
            console.log(`[${language}] Navigated to Invoice Candidates`);

            await InvoiceCandidatePage.expectVisibleForSalesOrder();
            await InvoiceCandidatePage.createInvoiceForSalesOrder();
            console.log(`[${language}] Invoice created from candidates`);

            // Wait for invoice async processing
            await page.waitForTimeout(5000);

            // === NAVIGATE TO INVOICE ===
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.waitForTimeout(500);

            // Invoice generation is async (InvoiceCandWorkpackageProcessor): the C_Order→C_Invoice
            // reference link only materialises once the workpackage has produced the invoice, which
            // can land after the related-docs panel is first opened. refreshOnRetry reloads the SO
            // page between attempts so the link is picked up once the async invoice completes.
            await SalesOrderPage.openRelatedInvoice({ refreshOnRetry: true, maxRetries: 10, retryDelay: 3000 });
            await InvoicePage.expectVisible();

            const invoiceDocNo = await InvoicePage.getDocumentNo();
            expect(invoiceDocNo).toBeTruthy();
            allure.parameter('Invoice DocumentNo', invoiceDocNo, { excluded: true });
            console.log(`[${language}] Invoice visible:`, invoiceDocNo);

            await InvoicePage.openDetailView();

            // Extract invoice record ID from URL (needed for attachments endpoint)
            const invoiceUrl = page.url();
            const invoiceRecordId = invoiceUrl.split('/').pop();
            expect(invoiceRecordId).toMatch(/^\d+$/);
            console.log(`[${language}] Invoice record ID:`, invoiceRecordId);

            // === FETCH ARCHIVED PDF (contains embedded factur-x.xml) ===
            //
            // The CII XML is embedded at ARCHIVE time (invoice AFTER_COMPLETE).
            // Alt+P re-renders a fresh PDF — it does NOT carry the embedded CII.
            // We must fetch the AD_Archive entry created at completion time.
            //
            // WebUI endpoint:
            //   GET /rest/api/window/167/{invoiceRecordId}/attachments
            //   -> JSON array of { id, filename, ... }; archive entries have id = "ARR_{archiveId}"
            //   GET /rest/api/window/167/{invoiceRecordId}/attachments/{id}
            //   -> streams the raw archived PDF bytes
            //
            // We poll up to 120s because the archive write is async post-completion: the
            // DocOutbound workpackage renders the invoice (Jasper, cold-compile on first run),
            // embeds the CII (Mustang) and converts to PDF/A-3 — which can exceed 30s.

            let archivedPdfBuffer = null;
            let archiveEntryId = null;

            const attachmentsUrl = `${WEBAPI_BASE_URL}/window/${SALES_INVOICE_WINDOW_ID}/${invoiceRecordId}/attachments`;
            const pollDeadline = Date.now() + 120000;

            while (!archivedPdfBuffer && Date.now() < pollDeadline) {
                const listResp = await page.request.get(attachmentsUrl);
                if (listResp.ok()) {
                    const entries = await listResp.json();
                    // Find an archive entry (id starts with "ARR_" — DocumentAttachments ID_SEPARATOR is "_")
                    const archiveEntry = Array.isArray(entries)
                        ? entries.find((e) => e.id && String(e.id).startsWith('ARR_'))
                        : null;

                    if (archiveEntry) {
                        archiveEntryId = archiveEntry.id;
                        console.log(`[${language}] Found archive entry:`, archiveEntryId, 'filename:', archiveEntry.filename);

                        const pdfResp = await page.request.get(`${attachmentsUrl}/${archiveEntryId}`);
                        if (pdfResp.ok()) {
                            const bodyBuffer = await pdfResp.body();
                            archivedPdfBuffer = Buffer.from(bodyBuffer);
                            console.log(`[${language}] Archived PDF downloaded:`, archivedPdfBuffer.length, 'bytes');
                        } else {
                            console.log(`[${language}] [WARN] Archive entry GET failed:`, pdfResp.status());
                        }
                    } else {
                        console.log(`[${language}] No archive entry yet, retrying...`);
                    }
                } else {
                    console.log(`[${language}] [WARN] Attachments list GET failed:`, listResp.status());
                }

                if (!archivedPdfBuffer) {
                    await page.waitForTimeout(3000);
                }
            }

            expect(archivedPdfBuffer, 'Expected archived invoice PDF to be available within 120s').not.toBeNull();

            // Attach archived PDF to Allure report
            allure.attachment(`archived-invoice-${invoiceDocNo}.pdf`, archivedPdfBuffer, 'application/pdf');

            // === VALIDATE ZUGFeRD: factur-x.xml embedded + carries the invoice content ===
            console.log(`[${language}] Validating ZUGFeRD attachment in archived PDF...`);
            const ciiXml = await PdfValidator.validateZugferdAttachment(archivedPdfBuffer);
            console.log(`[${language}] [PASS] ZUGFeRD factur-x.xml attachment present`);

            // === VALIDATE INVOICE CONTENT against the embedded CII (not the PDF text) ===
            // The PDF/A-3 visual layer is not reliably text-extractable (pdf-parse yields no text),
            // and for an e-invoice the authoritative content is the structured factur-x.xml CII:
            // it carries the invoice number (BT-1), the line product name, and the billed quantity.
            console.log(`[${language}] Validating invoice content in embedded CII...`);
            allure.attachment(`factur-x-${invoiceDocNo}.xml`, ciiXml, 'application/xml');
            expect(ciiXml, 'CII must carry the invoice document number (BT-1)').toContain(invoiceDocNo);
            expect(ciiXml, 'CII must carry the line product name').toContain(PRODUCT_NAME);
            expect(ciiXml, 'CII must carry the billed quantity').toMatch(/BilledQuantity[^>]*>\s*1(\.0+)?\s*</);

            console.log(`[${language}] [PASS] Invoice content validated in CII: documentNo, product, quantity present`);

            // Summary attachment
            const summaryHtml = `<table border="1">
                <tr><th>Check</th><th>Status</th><th>Value</th></tr>
                <tr><td>Sales Order</td><td>PASS</td><td>${soDocumentNo}</td></tr>
                <tr><td>Shipment created</td><td>PASS</td><td>yes</td></tr>
                <tr><td>Invoice created</td><td>PASS</td><td>${invoiceDocNo}</td></tr>
                <tr><td>Archived PDF fetched</td><td>PASS</td><td>${archivedPdfBuffer.length} bytes (entry ${archiveEntryId})</td></tr>
                <tr><td>factur-x.xml embedded</td><td>PASS</td><td>ZUGFeRD confirmed</td></tr>
                <tr><td>Invoice content intact</td><td>PASS</td><td>documentNo + product + qty in embedded CII</td></tr>
            </table>`;
            allure.attachment('Validation Summary', summaryHtml, 'text/html');
        });
    });
});
