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
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID, SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';
import { PdfValidator } from '../utils/PdfValidator';

/**
 * ZUGFeRD e-Invoice E2E test.
 *
 * Drives a full sales-order -> shipment -> invoice flow for a ZUGFeRD recipient
 * and verifies that:
 *   (a) the ARCHIVED invoice PDF contains an embedded factur-x.xml attachment
 *       (= it is a valid ZUGFeRD / PDF/A-3 file), AND
 *   (b) the invoice content is intact (document number, product, quantity present).
 *
 * ARCHIVE STRATEGY
 * ----------------
 * The CII XML is embedded into the PDF at archive time, i.e. when the invoice is
 * COMPLETED (AFTER_COMPLETE interceptor calls ZugferdAssembler.embed()).
 * Alt+P triggers a fresh re-render and does NOT return the archived copy.
 *
 * The archived PDF is fetched via the WebUI attachments endpoint:
 *   GET /rest/api/window/167/{invoiceRecordId}/attachments
 *     -> returns a JSON array; archive entries have id prefix "ARR-"
 *   GET /rest/api/window/167/{invoiceRecordId}/attachments/ARR-{archiveId}
 *     -> streams the raw PDF bytes (application/pdf)
 *
 * MASTERDATA RECIPE
 * -----------------
 * Mirrors the Background of:
 *   backend/de.metas.cucumber/src/test/resources/de/metas/cucumber/features/einvoice/eInvoiceZugferdEmail.feature
 *
 * Key requirements for a BR-DE-conformant ZUGFeRD invoice:
 *   Seller: BPartner with VATaxID, DE postal address (city/postal/address1), default contact,
 *           IBAN bank account — linked as the login-org's org-bpartner via orgSeller.
 *   Buyer:  BPartner with isEInvoiceRecipeint=true, eInvoiceType="Z",
 *           eInvoiceBuyerReference, VATaxID, DE postal address.
 *   Report: IsPdfA3Output=true on de/metas/docs/sales/invoice (enables PDF/A-3 output
 *           so ZugferdAssembler.embed() receives parseable PDF bytes).
 *
 * LANGUAGE INDEPENDENCE
 * ---------------------
 * All selectors use data-testid / data-cy / ColumnName IDs — never localized text.
 * The test runs in de_DE (invoice content is German; the ZUGFeRD check is binary).
 */

test.describe('ZUGFeRD e-Invoice', () => {
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
2. Login, create + complete sales order
3. Shipment via ShipmentSchedule
4. Invoice via InvoiceCandidates + complete
5. Fetch ARCHIVED invoice PDF via /attachments endpoint (not Alt+P re-render)
6. Assert factur-x.xml embedded (ZUGFeRD check)
7. Assert invoice content intact (documentNo / product / qty in PDF text)
    `);

    test.setTimeout(180000); // 3 minutes — ZUGFeRD assembly adds latency

    // === TEST DATA CREATION ===
    // Full BR-DE-conformant seller + ZUGFeRD buyer masterdata.
    // Mirrors the cucumber eInvoiceZugferdEmail.feature Background.
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
    // We poll up to 30 s because the archive write is async post-completion.

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
    // Uses PdfValidator.validate() with a synthetic Download-like wrapper.
    // We create a minimal wrapper that satisfies the interface used by validate().
    console.log('[INFO] Validating invoice content in archived PDF...');
    const downloadProxy = {
      path: async () => {
        // Write buffer to a temp file and return the path
        const tmpPath = path.join(os.tmpdir(), `zugferd-invoice-${Date.now()}.pdf`);
        fs.writeFileSync(tmpPath, archivedPdfBuffer);
        return tmpPath;
      },
      suggestedFilename: async () => `invoice-${invoiceDocNo}.pdf`,
    };

    await PdfValidator.validate(downloadProxy, {
      documentNo: invoiceDocNo,
      productCode: masterdata.products.product.productCode,
      quantity: '1',
      language: 'de_DE',
      checkOverlaps: false, // Layout overlaps not relevant here; ZUGFeRD check is the gate
      checkMargins: false,
    });

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
