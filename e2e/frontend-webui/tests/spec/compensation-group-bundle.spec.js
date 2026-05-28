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
import { PdfDownloader } from '../utils/PdfDownloader';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Compensation Group bundle end-to-end E2E test.
 *
 * Feature: F00127.1 - Single price for bundle (me03#29558)
 *
 * Verifies that a bundle SKU triggered via M_Product.C_CompensationGroup_Schema_ID
 * expands into:
 *   - the bundle line (priced)
 *   - a "free" component template line (IsWithoutCharge='Y', PriceActual=0)
 *   - a priced component template line (IsWithoutCharge='N')
 *
 * The IsWithoutCharge flag + Reason='B' propagate from C_OrderLine → C_Invoice_Candidate
 * → C_InvoiceLine, and the sales-order + sales-invoice PDFs render
 * "Ohne Berechnung" / "no charge" in the free component's price area only.
 */

const ETHERNET_PRICE = 19.5;
const BUNDLE_PRICE = 199.0;

test.describe('Compensation Group bundle (F00127.1)', () => {
    test('Bundle SKU expands into priced + free components; PDFs render Ohne Berechnung correctly', async ({ page }) => {
        allure.epic('E0100: Sales');
        allure.tag('F00127.1: Single price for bundle');
        allure.tag('F00100: Sales Order');
        allure.tag('F00200: Sales Invoice');
        allure.story('Bundle expansion with mixed priced + free components');
        allure.severity('critical');

        // Generous timeout: this is a full O2C flow + 2 PDF downloads
        test.setTimeout(240000); // 4 minutes

        // ============================================================
        // Step 1: Create masterdata via Backend (incl. SO + Shipment + Invoice)
        // ============================================================
        // The compensation_group masterdata builder is the new surface from T11c-1.
        // The bundle product carries the schema pointer; the schema's template lines
        // declare which components are auto-flagged IsWithoutCharge='Y' on expansion.
        const masterdata = await Backend.createMasterdata({
            request: {
                login: {
                    user: {
                        language: 'de_DE',
                        firstname: 'first',
                        lastname: 'last',
                    },
                },
                bpartners: {
                    CUSTOMER1: {
                        isVendor: false,
                        isCustomer: true,
                        isSoPriceList: true,
                        name: 'Bundle Customer',
                    },
                },
                products: {
                    BUNDLE: {
                        name: 'PrinterSet Bundle',
                        type: 'Item',
                        compensationGroupSchema: 'schema_printerset',
                        prices: [{ price: BUNDLE_PRICE, currencyCode: 'EUR' }],
                    },
                    INK: {
                        name: 'Ink Cartridge (free)',
                        type: 'Item',
                        prices: [{ price: 5.0, currencyCode: 'EUR' }],
                    },
                    ETHERNET: {
                        name: 'Ethernet Adapter (priced)',
                        type: 'Item',
                        prices: [{ price: ETHERNET_PRICE, currencyCode: 'EUR' }],
                    },
                },
                warehouses: { wh: {} },
                compensationGroupSchemas: {
                    schema_printerset: {
                        name: 'Printer Set Schema',
                        templateLines: [
                            { product: 'INK', qty: 1, isWithoutCharge: true },
                            { product: 'ETHERNET', qty: 1, isWithoutCharge: false },
                        ],
                    },
                },
                salesOrders: {
                    SO1: {
                        bpartner: 'CUSTOMER1',
                        warehouse: 'wh',
                        datePromised: '2026-06-15T00:00:00.000+02:00',
                        lines: [{ product: 'BUNDLE', qty: 1 }],
                    },
                },
                // Shipment + invoice creation is done via the UI below; the
                // masterdata `shipments` command requires HU/picking infra not set
                // up here, so we mirror the invoice-reversal.spec.js UI flow.
            },
        });

        allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');

        const soId = masterdata.salesOrders.SO1.id;
        const soDocNo = masterdata.salesOrders.SO1.documentNo;
        expect(soDocNo, 'Sales Order should be created').toBeTruthy();
        console.log(`[F00127.1] SO=${soDocNo} (id=${soId})`);

        // ============================================================
        // Step 2: Log in
        // ============================================================
        await LoginPage.goto();
        await LoginPage.login(masterdata.login.user);
        await DashboardPage.expectVisible();

        // ============================================================
        // Step 2b: Drive shipment + invoice creation via the UI (same as
        // invoice-reversal.spec.js — masterdata's shipment command needs
        // HU/picking setup we don't have).
        // ============================================================
        // Wait for async shipment-schedule creation after order completion.
        await page.waitForTimeout(5000);

        // Navigate to the completed SO
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(500);

        await SalesOrderPage.openRelatedShipmentCandidate({ maxRetries: 15, retryDelay: 3000 });
        await ShipmentSchedulePage.expectVisible();
        await ShipmentSchedulePage.createShipment();
        console.log(`[F00127.1] Shipment created`);

        await page.waitForTimeout(5000);

        // Back to SO -> create invoice from invoice candidates
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(500);

        await SalesOrderPage.openRelatedInvoiceCandidate(5000);
        await InvoiceCandidatePage.expectVisibleForSalesOrder();
        await InvoiceCandidatePage.createInvoiceForSalesOrder();
        console.log(`[F00127.1] Invoice created from candidates`);

        await page.waitForTimeout(5000);

        // ============================================================
        // Step 3: Navigate to SO and download the order PDF
        // ============================================================
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.locator('.rotating, .panel-spaced-lg')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        await page.waitForTimeout(500);

        // Take a UI screenshot before printing (rendered SO header in UI)
        const soUiScreenshot = await page.screenshot({ fullPage: true });
        allure.attachment('Sales Order — UI', soUiScreenshot, 'image/png');

        await PdfDownloader.openPrintModal('SalesOrderPage');
        const soDownload = await PdfDownloader.downloadPdf('sales-order', 'SalesOrderPage');
        await SalesOrderPage.closePrintModal().catch(() => {});

        const soPdfPath = await soDownload.path();
        const soPdfText = await extractPdfText(soPdfPath);
        const soPdfTextCompact = soPdfText.replace(/\s+/g, ' ');

        console.log(`[F00127.1] SO PDF text length: ${soPdfText.length}`);
        allure.attachment('Sales Order PDF — extracted text', soPdfText, 'text/plain');

        // Save the order PDF as the "rendered" artefact (it IS the rendered PDF)
        const fs = require('fs');
        const path = require('path');
        const screenshotsDir = path.resolve(process.cwd(), '..', '..', 'ai-work', '29558', 'screenshots');
        try {
            fs.mkdirSync(screenshotsDir, { recursive: true });
            fs.copyFileSync(soPdfPath, path.join(screenshotsDir, 'order-pdf-rendered.pdf'));
            fs.writeFileSync(path.join(screenshotsDir, 'order-pdf-rendered.png'), soUiScreenshot);
        } catch (e) {
            console.log(`[F00127.1] Could not copy PDF to ai-work: ${e.message}`);
        }

        // ============================================================
        // Step 4: Assert order PDF text content
        // ============================================================
        // The bundle product, ink product, and ethernet product names should all appear.
        expect(soPdfTextCompact, 'SO PDF contains bundle product').toContain(masterdata.products.BUNDLE.productCode);
        expect(soPdfTextCompact, 'SO PDF contains ink product').toContain(masterdata.products.INK.productCode);
        expect(soPdfTextCompact, 'SO PDF contains ethernet product').toContain(masterdata.products.ETHERNET.productCode);
        expect(soPdfTextCompact, 'SO PDF contains document number').toContain(soDocNo);

        // The "Ohne Berechnung" string MUST appear (rendered on the free ink component row).
        expect(soPdfTextCompact, 'SO PDF renders "Ohne Berechnung"').toContain('Ohne Berechnung');

        // Locale assertion: count occurrences. Exactly ONE "Ohne Berechnung" (ink only,
        // not ethernet). If the regex were to find > 1, the ethernet row would be wrongly
        // flagged.
        const soOhneBerechnungCount = (soPdfText.match(/Ohne\s*Berechnung/g) || []).length;
        expect(soOhneBerechnungCount, 'SO PDF: exactly one "Ohne Berechnung" (ink only)').toBe(1);
        console.log(`[F00127.1] SO "Ohne Berechnung" occurrences: ${soOhneBerechnungCount}`);

        // Ethernet price (formatted with German thousands/decimal "19,50") should appear.
        // German locale: comma decimal separator.
        const ethernetPriceDe = '19,50';
        expect(soPdfTextCompact, `SO PDF contains ethernet price "${ethernetPriceDe}"`).toContain(ethernetPriceDe);

        // ============================================================
        // Step 5: Navigate to the invoice (via SO → related Invoice, Alt+6)
        // and download invoice PDF
        // ============================================================
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.waitForTimeout(500);

        await SalesOrderPage.openRelatedInvoice();
        await InvoicePage.expectVisible();
        const invDocNo = await InvoicePage.getDocumentNo();
        expect(invDocNo, 'Invoice should be visible from SO').toBeTruthy();
        console.log(`[F00127.1] Invoice: ${invDocNo}`);

        await InvoicePage.openDetailView();
        await page.waitForTimeout(500);

        const invUiScreenshot = await page.screenshot({ fullPage: true });
        allure.attachment('Invoice — UI', invUiScreenshot, 'image/png');

        await PdfDownloader.openPrintModal('InvoicePage');
        const invDownload = await PdfDownloader.downloadPdf('invoice', 'InvoicePage');
        await page.keyboard.press('Escape').catch(() => {});

        const invPdfPath = await invDownload.path();
        const invPdfText = await extractPdfText(invPdfPath);
        const invPdfTextCompact = invPdfText.replace(/\s+/g, ' ');

        console.log(`[F00127.1] Invoice PDF text length: ${invPdfText.length}`);
        allure.attachment('Invoice PDF — extracted text', invPdfText, 'text/plain');

        try {
            fs.copyFileSync(invPdfPath, path.join(screenshotsDir, 'invoice-pdf-rendered.pdf'));
            fs.writeFileSync(path.join(screenshotsDir, 'invoice-pdf-rendered.png'), invUiScreenshot);
        } catch (e) {
            console.log(`[F00127.1] Could not copy invoice PDF to ai-work: ${e.message}`);
        }

        // ============================================================
        // Step 6: Assert invoice PDF text content
        // ============================================================
        expect(invPdfTextCompact, 'Invoice PDF contains invoice number').toContain(invDocNo);
        expect(invPdfTextCompact, 'Invoice PDF contains bundle product').toContain(masterdata.products.BUNDLE.productCode);
        expect(invPdfTextCompact, 'Invoice PDF contains ink product').toContain(masterdata.products.INK.productCode);
        expect(invPdfTextCompact, 'Invoice PDF contains ethernet product').toContain(masterdata.products.ETHERNET.productCode);

        // Free component row renders the localized "no charge" indicator.
        expect(invPdfTextCompact, 'Invoice PDF renders "Ohne Berechnung"').toContain('Ohne Berechnung');

        // Exactly one occurrence — the ink row, NOT the ethernet row.
        const invOhneBerechnungCount = (invPdfText.match(/Ohne\s*Berechnung/g) || []).length;
        expect(invOhneBerechnungCount, 'Invoice PDF: exactly one "Ohne Berechnung" (ink only)').toBe(1);
        console.log(`[F00127.1] Invoice "Ohne Berechnung" occurrences: ${invOhneBerechnungCount}`);

        // Ethernet priced row carries its price.
        expect(invPdfTextCompact, `Invoice PDF contains ethernet price "${ethernetPriceDe}"`).toContain(ethernetPriceDe);

        // ============================================================
        // Summary
        // ============================================================
        const summaryHtml = `<table border="1">
            <tr><th>Check</th><th>Status</th><th>Value</th></tr>
            <tr><td>SO created (auto-completed via masterdata)</td><td>PASS</td><td>${soDocNo}</td></tr>
            <tr><td>Invoice created via masterdata</td><td>PASS</td><td>${invDocNo}</td></tr>
            <tr><td>SO PDF: bundle/ink/ethernet products present</td><td>PASS</td><td>3 products</td></tr>
            <tr><td>SO PDF: exactly ONE "Ohne Berechnung"</td><td>PASS</td><td>ink row only</td></tr>
            <tr><td>SO PDF: ethernet price ${ethernetPriceDe} EUR rendered</td><td>PASS</td><td>priced component visible</td></tr>
            <tr><td>Invoice PDF: bundle/ink/ethernet products present</td><td>PASS</td><td>3 products</td></tr>
            <tr><td>Invoice PDF: exactly ONE "Ohne Berechnung"</td><td>PASS</td><td>propagation OL → IC → IL</td></tr>
            <tr><td>Invoice PDF: ethernet price ${ethernetPriceDe} EUR rendered</td><td>PASS</td><td>priced component visible</td></tr>
        </table>`;
        allure.attachment('Validation Results', summaryHtml, 'text/html');

        console.log('[F00127.1] Bundle e2e (SO + Invoice + PDFs) PASS');
    });
});

/**
 * Extract raw text from a PDF on disk via pdf-parse.
 */
async function extractPdfText(pdfPath) {
    const fs = require('fs');
    const pdfParse = require('pdf-parse');
    const buffer = fs.readFileSync(pdfPath);
    const data = await pdfParse(buffer);
    return data.text;
}
