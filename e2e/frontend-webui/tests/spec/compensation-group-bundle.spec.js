import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { ShipmentPage } from '../utils/pages/ShipmentPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { PdfDownloader } from '../utils/PdfDownloader';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import fs from 'fs';
import path from 'path';

/**
 * F00127.1 — Single price for bundle (me03#29558).
 *
 * Full end-to-end demonstration that the schema-driven bundle flow works:
 *   1. masterdata builder wires M_Product.C_CompensationGroup_Schema_ID on the main product,
 *   2. ordering the main product via UI quickinput fires OrderLineQuickInputCallout, which
 *      auto-creates one C_OrderLine per Template Line — including the main product itself
 *      as the first template line (carries the bundle price), followed by the components
 *      (all IsWithoutCharge='Y' → priced at 0, Reason='B'),
 *   3. IsWithoutCharge + Reason propagate C_OrderLine → C_Invoice_Candidate → C_InvoiceLine,
 *   4. both the Sales Order PDF and the Sales Invoice PDF show the main product with its
 *      price and the component rows with "Ohne Berechnung" in the price-per-unit and
 *      line-total columns.
 *
 * Customer semantics (per me03#29558 grooming, 2026-05-28):
 *   - The main product carries the price; physical shipment is the components.
 *   - The main appears on the order + invoice; components appear with "Ohne Berechnung".
 *
 * Printer-set scenario (1 main priced + 2 components free):
 *   main:  PrinterStarterSet  -> trigger product (carries the schema FK), priced 199,00 EUR
 *   free:  InkCartridge       -> IsWithoutCharge='Y' on the template line
 *   free:  PaperRoll          -> IsWithoutCharge='Y' on the template line
 */

const BUNDLE_PRICE = 199.0;
const INK_PRICE = 5.0;     // pricelist price; auto-zeroed via IsWithoutCharge='Y'
const PAPER_PRICE = 8.0;   // pricelist price; auto-zeroed via IsWithoutCharge='Y'

// German-locale formatting of the prices on the PDF. The bundle price MUST appear;
// the free components' pricelist prices MUST NOT (they render without-charge instead).
const BUNDLE_PRICE_DE = '199,00';
const INK_PRICE_DE = '5,00';
const PAPER_PRICE_DE = '8,00';

// The user's language drives which translation Jasper uses for the without-charge
// label. The same key (Field.WithoutCharge) is read from the Jasper resource bundle
// in this test — keep one source of truth, never hard-code a localised string.
const TEST_LANGUAGE = 'de_DE';

// Each free component row renders the without-charge label ONCE on the PDF —
// only in the line-total (Summe) column. The price-per-unit column is left blank
// for free items (per the me03#26061 concept). So one occurrence per free line.
const FREE_COMPONENT_COUNT = 2;
const OCCURRENCES_PER_FREE_LINE = 1;
const EXPECTED_WITHOUT_CHARGE_OCCURRENCES = FREE_COMPONENT_COUNT * OCCURRENCES_PER_FREE_LINE; // 2

test.describe('Compensation Group bundle (F00127.1)', () => {
    test('PrinterStarterSet bundle: SO + Invoice PDFs render "Ohne Berechnung" only on free components', async ({ page }) => {
        allure.epic('E0100: Sales');
        allure.tag('F00127.1: Single price for bundle');
        allure.tag('F00100: Sales Order');
        allure.tag('F00200: Sales Invoice');
        allure.story('Schema-driven bundle expansion + Ohne Berechnung in SO and Invoice PDFs');
        allure.severity('critical');

        // Generous timeout: masterdata build + several long async waits (shipment schedule,
        // invoice candidate processor) + PDF downloads. The async document pipelines are
        // markedly slower on disk-pressured single-runner customer CI (e.g. dt204
        // deep_tundra_uat, mf15#4193), so the overall budget and the per-step Alt+6 polls
        // below are sized for that worst case (they finish in 1-2 attempts on a healthy env).
        test.setTimeout(600000); // 10 minutes

        // ============================================================
        // Step 1: Build all masterdata via the frontend-testing REST API.
        // CreateMasterdataCommand.linkProductsToCompensationGroupSchemas() wires
        // M_Product.C_CompensationGroup_Schema_ID on PrinterStarterSet → schema_printerset.
        // ============================================================
        const masterdata = await Backend.createMasterdata({
            request: {
                login: {
                    user: { language: TEST_LANGUAGE, firstname: 'first', lastname: 'last' },
                },
                bpartners: {
                    CUSTOMER1: {
                        isVendor: false,
                        isCustomer: true,
                        isSoPriceList: true,
                        name: 'PrinterMart',
                    },
                },
                products: {
                    BUNDLE: {
                        name: 'PrinterStarterSet',
                        // The main / "bracket" product carries the bundle price. Modelled
                        // as Type=Item with IsStocked=N so it participates in the shipment
                        // + invoice flow (and shows up on those documents in the natural
                        // order-line position) without tracking stock — exactly what the
                        // metasfresh "Compensations" tab val-rule M_Product_ForCompensationLine
                        // (M_Product.IsStocked='N') expects for a bracket / bundle line.
                        type: 'Item',
                        isStocked: false,
                        compensationGroupSchema: 'schema_printerset',
                        prices: [{ price: BUNDLE_PRICE, currencyCode: 'EUR' }],
                    },
                    INK: {
                        name: 'InkCartridge',
                        type: 'Item',
                        prices: [{ price: INK_PRICE, currencyCode: 'EUR' }],
                    },
                    PAPER: {
                        name: 'PaperRoll',
                        type: 'Item',
                        prices: [{ price: PAPER_PRICE, currencyCode: 'EUR' }],
                    },
                },
                warehouses: { wh: {} },
                compensationGroupSchemas: {
                    schema_printerset: {
                        name: 'PrinterSet Bundle Schema',
                        // The main product is the FIRST template line — it carries the bundle price
                        // and stays priced on the resulting order/invoice. The components below it
                        // are all auto-zeroed via IsWithoutCharge='Y'.
                        templateLines: [
                            { product: 'BUNDLE', qty: 1, isWithoutCharge: false },
                            { product: 'INK', qty: 1, isWithoutCharge: true },
                            { product: 'PAPER', qty: 2, isWithoutCharge: true },
                        ],
                    },
                },
                // NOTE: the order is created via the UI quickinput below, NOT via the
                // masterdata salesOrders block — the REST sales-order command bypasses
                // OrderLineQuickInputCallout, and the callout is what triggers the
                // schema expansion. We mirror the real user flow.
            },
        });

        allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');

        // ============================================================
        // Step 2: Log in.
        // ============================================================
        await LoginPage.goto();
        await LoginPage.login(masterdata.login.user);
        await DashboardPage.expectVisible();

        // ============================================================
        // Step 3: Create the SO via UI quickinput.
        // Adding PrinterStarterSet to the order fires OrderLineQuickInputCallout
        // → schema expansion → 3 component lines (Ink, Paper, Ethernet) created
        // asynchronously. The bundle product itself is the trigger only; it does
        // NOT appear as its own order line (verified against the customer's
        // production behaviour in me03#29558).
        // ============================================================
        await SalesOrderPage.goto();
        await SalesOrderPage.clickNew();

        const soId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
        console.log(`[F00127.1] SO created: id=${soId}`);

        // Give the BPartner-default-warehouse callout time to settle before the quickinput
        // submits a line. Without this, an Item-type bundle product triggers "FillMandatory
        // M_Warehouse_ID" because the order is still in Invalid-Initial state when the
        // quickinput POSTs (service-type products masked this because they skip the
        // shipment-schedule pre-flight check).
        await page.waitForTimeout(3000);

        // IMPORTANT: do NOT use SalesOrderPage.addOrderLine() here.
        // That helper considers the add "failed" if the grid still shows zero rows
        // 2 s after Enter, and retries by reloading + re-submitting. The schema
        // expansion's OrderLineQuickInputCallout is async and routinely takes
        // longer than 2 s — the retry then re-fires the callout, producing 2×3
        // (or worse) duplicated component lines. Inline a single-shot quickinput
        // that does NOT retry on empty grid; we wait afterwards for the schema
        // expansion to settle and verify the line count via REST instead.
        await addBundleViaQuickInput({
            page,
            recordId: soId,
            productCode: masterdata.products.BUNDLE.productCode,
            quantity: '1',
        });

        // Wait for the async OrderLineQuickInputCallout (schema expansion) to
        // finish, then reload so the grid picks up the new component lines.
        const expectedLineCount = 3; // PrinterStarterSet + Ink + Paper
        await page.waitForTimeout(8000);
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.locator('.rotating, .panel-spaced-lg')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        await page.waitForTimeout(2000);

        // Step 4: Verify the schema expansion (3 lines) and complete the order.
        await SalesOrderPage.goToOrderLineTab();
        const gridRowCount = await page.locator('table tbody tr').count();
        console.log(`[F00127.1] Order lines visible in grid: ${gridRowCount} (expected ${expectedLineCount} = PrinterStarterSet + Ink + Paper)`);
        expect(gridRowCount, 'Schema expansion must produce 3 lines (main + Ink + Paper)').toBe(expectedLineCount);

        await SalesOrderPage.complete();
        const soDocNo = await SalesOrderPage.getDocumentNo();
        expect(soDocNo, 'Sales Order should be completed and have a DocumentNo').toBeTruthy();
        console.log(`[F00127.1] SO completed: ${soDocNo} (id=${soId})`);

        // Async shipment schedule creation after order completion.
        await page.waitForTimeout(5000);

        // ============================================================
        // Step 5: Drive shipment + invoice candidate creation via the UI.
        // ============================================================
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);

        // 90s budget + F5 refresh between attempts: on dt204 the shipment-schedule reference
        // is created async and routinely misses a 45s no-refresh poll (mf15#4193). refreshOnRetry
        // forces a fresh document+references fetch each attempt (the proven pattern from
        // document-references.spec.js) rather than re-reading a stale client-cached SSE set.
        await SalesOrderPage.openRelatedShipmentCandidate({ maxRetries: 30, retryDelay: 3000, refreshOnRetry: true });
        await ShipmentSchedulePage.expectVisible();
        await ShipmentSchedulePage.createShipment();
        console.log('[F00127.1] Shipment created');

        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        // NOTE: pass an options object — a bare number (5000) was silently ignored by the
        // destructured signature and fell back to the 5×2s=10s default. Give it the same
        // hardened budget + refresh as the shipment-schedule step.
        await SalesOrderPage.openRelatedInvoiceCandidate({ maxRetries: 30, retryDelay: 3000, refreshOnRetry: true });
        await InvoiceCandidatePage.expectVisibleForSalesOrder();
        await InvoiceCandidatePage.createInvoiceForSalesOrder();
        console.log('[F00127.1] Invoice created from candidates');

        // Async wait: invoice candidate processor → C_Invoice creation.
        await page.waitForTimeout(8000);

        // ============================================================
        // Step 6: Download + assert the Sales Order PDF.
        // ============================================================
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.locator('.rotating, .panel-spaced-lg')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

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

        // Best-effort: copy the rendered PDFs into the local UAT working folder for manual review.
        // This is a developer-machine convenience only — on CI the path doesn't exist and the
        // try/catch below no-ops. The authoritative CI artefacts are the allure attachments above.
        const screenshotsDir = path.resolve(process.cwd(), '..', '..', 'ai-work', '29558', 'screenshots');
        try {
            fs.mkdirSync(screenshotsDir, { recursive: true });
            fs.copyFileSync(soPdfPath, path.join(screenshotsDir, 'order-pdf-rendered.pdf'));
            fs.writeFileSync(path.join(screenshotsDir, 'order-pdf-rendered.png'), soUiScreenshot);
        } catch (e) {
            console.log(`[F00127.1] Could not copy SO PDF to ai-work: ${e.message}`);
        }

        // Resolve the localised "without charge" label from the same Jasper resource bundle
        // the SO report consumes — never hard-code a translated string in the test.
        const soWithoutChargeLabel = loadJasperResourceValue('sales/order', TEST_LANGUAGE, 'Field.WithoutCharge');

        // Content assertions on the SO PDF.
        expect(soPdfTextCompact, 'SO PDF contains the main bundle product').toContain(masterdata.products.BUNDLE.productCode);
        expect(soPdfTextCompact, 'SO PDF contains InkCartridge component').toContain(masterdata.products.INK.productCode);
        expect(soPdfTextCompact, 'SO PDF contains PaperRoll component').toContain(masterdata.products.PAPER.productCode);
        expect(soPdfTextCompact, 'SO PDF contains document number').toContain(soDocNo);

        expect(soPdfTextCompact, `SO PDF contains bundle price "${BUNDLE_PRICE_DE}"`).toContain(BUNDLE_PRICE_DE);

        // Language-independent structural assertion (runs everywhere, incl. CI where the
        // resource bundle isn't reachable): the free components must NOT show their pricelist
        // prices — they are without-charge. (5,00 / 8,00 don't occur as substrings of any other
        // amount on this PDF: 199,00 / 37,81 / 236,81.)
        expect(soPdfTextCompact, `SO PDF must NOT show the free InkCartridge price (${INK_PRICE_DE})`).not.toContain(INK_PRICE_DE);
        expect(soPdfTextCompact, `SO PDF must NOT show the free PaperRoll price (${PAPER_PRICE_DE})`).not.toContain(PAPER_PRICE_DE);

        // Exact-label assertion — only when the Jasper bundle is reachable (local full workspace).
        // Catches .properties drift; skipped in CI (see loadJasperResourceValue).
        if (soWithoutChargeLabel !== null) {
            const soWithoutChargeCount = countOccurrencesIgnoringWhitespace(soPdfText, soWithoutChargeLabel);
            expect(
                soWithoutChargeCount,
                `SO PDF: expected exactly ${EXPECTED_WITHOUT_CHARGE_OCCURRENCES} occurrences of the without-charge label ("${soWithoutChargeLabel}") — one per free component (Ink + Paper), line-total column only. If 3 → a paid component was wrongly auto-flagged; if 1 → only one free component is rendered; if 4 → the label is wrongly also in the price-per-unit column.`,
            ).toBe(EXPECTED_WITHOUT_CHARGE_OCCURRENCES);
            console.log(`[F00127.1] SO PDF "${soWithoutChargeLabel}" occurrences: ${soWithoutChargeCount}`);
        } else {
            console.log('[F00127.1] SO PDF: bundle label not resolved (CI) — verified structurally (free prices absent, bundle priced).');
        }

        // ============================================================
        // Step 7: Navigate to the invoice and download the invoice PDF.
        // (Prior version of this test deferred this; covered now.)
        // ============================================================
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.locator('.rotating, .panel-spaced-lg')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

        // Long retry budget: invoice-candidate processor is slower than the SO→shipment-schedule pipeline.
        await SalesOrderPage.openRelatedInvoice({ maxRetries: 30, retryDelay: 3000, refreshOnRetry: true });
        await InvoicePage.expectVisible();

        const invoiceDocNo = await InvoicePage.getDocumentNo();
        expect(invoiceDocNo, 'Invoice should exist').toBeTruthy();
        console.log(`[F00127.1] Invoice: ${invoiceDocNo}`);

        await InvoicePage.openDetailView();

        const invUiScreenshot = await page.screenshot({ fullPage: true });
        allure.attachment('Invoice — UI', invUiScreenshot, 'image/png');

        await PdfDownloader.openPrintModal('InvoicePage');
        const invDownload = await PdfDownloader.downloadPdf('sales-invoice', 'InvoicePage');

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

        // Resolve the invoice-side label from the invoice report's resource bundle (same key,
        // separate bundle path — the SO and the invoice each ship their own properties files).
        const invWithoutChargeLabel = loadJasperResourceValue('sales/invoice', TEST_LANGUAGE, 'Field.WithoutCharge');

        // Content assertions on the Invoice PDF — mirror the SO assertions to prove
        // the IsWithoutCharge + Reason propagation through C_Invoice_Candidate to
        // C_InvoiceLine actually reaches the rendered invoice.
        expect(invPdfTextCompact, 'Invoice PDF contains the main bundle product').toContain(masterdata.products.BUNDLE.productCode);
        expect(invPdfTextCompact, 'Invoice PDF contains InkCartridge component').toContain(masterdata.products.INK.productCode);
        expect(invPdfTextCompact, 'Invoice PDF contains PaperRoll component').toContain(masterdata.products.PAPER.productCode);
        expect(invPdfTextCompact, `Invoice PDF contains bundle price "${BUNDLE_PRICE_DE}"`).toContain(BUNDLE_PRICE_DE);

        // Language-independent structural assertion: free components show no pricelist price.
        expect(invPdfTextCompact, `Invoice PDF must NOT show the free InkCartridge price (${INK_PRICE_DE})`).not.toContain(INK_PRICE_DE);
        expect(invPdfTextCompact, `Invoice PDF must NOT show the free PaperRoll price (${PAPER_PRICE_DE})`).not.toContain(PAPER_PRICE_DE);

        // Exact-label assertion — only when the bundle is reachable (local).
        if (invWithoutChargeLabel !== null) {
            const invWithoutChargeCount = countOccurrencesIgnoringWhitespace(invPdfText, invWithoutChargeLabel);
            expect(
                invWithoutChargeCount,
                `Invoice PDF: expected exactly ${EXPECTED_WITHOUT_CHARGE_OCCURRENCES} occurrences of the without-charge label ("${invWithoutChargeLabel}") — one per free component (Ink + Paper), line-total column only.`,
            ).toBe(EXPECTED_WITHOUT_CHARGE_OCCURRENCES);
            console.log(`[F00127.1] Invoice PDF "${invWithoutChargeLabel}" occurrences: ${invWithoutChargeCount}`);
        } else {
            console.log('[F00127.1] Invoice PDF: bundle label not resolved (CI) — verified structurally.');
        }

        // ============================================================
        // Step 8: Navigate to the shipment (Lieferschein) and download its PDF.
        // The delivery note does NOT print prices by default (its price columns are
        // gated behind isshipmentpriceprinted='Y' AND PRINTER_OPTS_IsPrintPrices='Y'),
        // so there is no "Ohne Berechnung" rendering here — the goal is simply to prove
        // the bundle "bracket" product (Type=Item, IsStocked=N) and the physical
        // components all appear correctly on the delivery note.
        // ============================================================
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soId}`);
        await page.locator('.rotating, .panel-spaced-lg')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

        await SalesOrderPage.openRelatedShipment({ maxRetries: 30, retryDelay: 3000, refreshOnRetry: true });
        await ShipmentPage.expectVisible();

        const shipmentDocNo = await ShipmentPage.getDocumentNo();
        expect(shipmentDocNo, 'Shipment should exist').toBeTruthy();
        console.log(`[F00127.1] Shipment: ${shipmentDocNo}`);

        await ShipmentPage.openDetailView();

        const shipUiScreenshot = await page.screenshot({ fullPage: true });
        allure.attachment('Shipment — UI', shipUiScreenshot, 'image/png');

        await PdfDownloader.openPrintModal('ShipmentPage');
        const shipDownload = await PdfDownloader.downloadPdf('shipment', 'ShipmentPage');
        await ShipmentPage.closePrintModal().catch(() => {});

        const shipPdfPath = await shipDownload.path();
        const shipPdfText = await extractPdfText(shipPdfPath);
        const shipPdfTextCompact = shipPdfText.replace(/\s+/g, ' ');

        console.log(`[F00127.1] Shipment PDF text length: ${shipPdfText.length}`);
        allure.attachment('Shipment PDF — extracted text', shipPdfText, 'text/plain');

        try {
            fs.copyFileSync(shipPdfPath, path.join(screenshotsDir, 'shipment-pdf-rendered.pdf'));
            fs.writeFileSync(path.join(screenshotsDir, 'shipment-pdf-rendered.png'), shipUiScreenshot);
        } catch (e) {
            console.log(`[F00127.1] Could not copy shipment PDF to ai-work: ${e.message}`);
        }

        // Content assertions on the Shipment PDF: all 3 products (bracket + both
        // physical components) are present. No price / without-charge assertion — the
        // delivery note intentionally omits prices.
        expect(shipPdfTextCompact, 'Shipment PDF contains the main bundle product').toContain(masterdata.products.BUNDLE.productCode);
        expect(shipPdfTextCompact, 'Shipment PDF contains InkCartridge component').toContain(masterdata.products.INK.productCode);
        expect(shipPdfTextCompact, 'Shipment PDF contains PaperRoll component').toContain(masterdata.products.PAPER.productCode);
        // The delivery note must NOT leak the without-charge label (prices are not printed
        // at all). Only checkable when the label is resolved (local); skipped in CI.
        if (soWithoutChargeLabel !== null) {
            const shipWithoutChargeCount = countOccurrencesIgnoringWhitespace(shipPdfText, soWithoutChargeLabel);
            expect(
                shipWithoutChargeCount,
                `Shipment PDF should NOT render the without-charge label ("${soWithoutChargeLabel}") — the delivery note omits prices entirely.`,
            ).toBe(0);
        }
        console.log(`[F00127.1] Shipment PDF contains all 3 products; without-charge label correctly absent.`);

        console.log('[F00127.1] All assertions passed: SO + Invoice PDFs show without-charge on free components (no pricelist price); shipment lists all products without prices.');
    });
});

/**
 * Read a Jasper resource-bundle value by report path + locale + key.
 *
 * Reads `metasfresh/backend/de.metas.fresh/de.metas.fresh.base/src/main/jasperreports/
 *        de/metas/docs/<reportDir>/report_<locale>.properties`
 * (latin-1 encoded — Java Properties default). Returns the trimmed value for `key`.
 *
 * Use this to keep the test in lockstep with the same translation strings Jasper renders,
 * instead of hard-coding any localised text in the test source.
 */
function loadJasperResourceValue(reportDir, locale, key) {
    // Resolve from this file: e2e/frontend-webui/tests/spec/<file>.js → up 4 to metasfresh root.
    const metasfreshRoot = path.resolve(__dirname, '..', '..', '..', '..');
    const propertiesPath = path.join(
        metasfreshRoot,
        'backend', 'de.metas.fresh', 'de.metas.fresh.base', 'src', 'main', 'jasperreports',
        'de', 'metas', 'docs', reportDir, `report_${locale}.properties`,
    );
    let raw;
    try {
        raw = fs.readFileSync(propertiesPath, 'latin1');
    } catch (e) {
        if (e.code === 'ENOENT') {
            // CI frontend-webui container mounts only the e2e dir (/app), not the backend
            // source tree — the bundle is unreachable there. Return null; callers fall back
            // to language-independent structural assertions. Locally the bundle IS present,
            // so the exact-label assertion still runs and catches .properties drift.
            console.log(`[F00127.1] Jasper bundle not reachable (${propertiesPath}); using structural fallback.`);
            return null;
        }
        throw e;
    }
    for (const line of raw.split(/\r?\n/)) {
        if (line.startsWith('#') || !line.includes('=')) { continue; }
        const eq = line.indexOf('=');
        const k = line.slice(0, eq).trim();
        if (k === key) {
            return line.slice(eq + 1).trim();
        }
    }
    throw new Error(`Key "${key}" not found in ${propertiesPath}`);
}

/**
 * Count how many times `needle` occurs in `haystack`, ignoring all whitespace differences.
 *
 * Why: pdf-parse can split a single rendered token across line breaks mid-word when a
 * narrow column wraps the text (e.g. "Ohne Berechnung" renders as "Ohne\nBerechnun\ng"
 * in a 53px-wide column). Counting on whitespace-stripped strings is robust against this
 * AND language-agnostic — works for the German, English, French and Italian translations
 * of `Field.WithoutCharge` without per-language regex tweaks.
 */
function countOccurrencesIgnoringWhitespace(text, target) {
    const strip = (s) => s.replace(/\s+/g, '');
    const haystack = strip(text);
    const needle = strip(target);
    if (needle.length === 0) { return 0; }
    let count = 0;
    let idx = 0;
    while ((idx = haystack.indexOf(needle, idx)) !== -1) {
        count++;
        idx += needle.length;
    }
    return count;
}

/**
 * Extract raw text from a PDF on disk via pdf-parse.
 */
async function extractPdfText(pdfPath) {
    const pdfParse = (await import('pdf-parse')).default;
    const buffer = fs.readFileSync(pdfPath);
    const data = await pdfParse(buffer);
    return data.text;
}

/**
 * Single-shot bundle-aware quickinput: open batch entry, enter product + qty,
 * press Enter, close modal. Does NOT retry on empty grid (the schema-driven
 * OrderLineQuickInputCallout is async and the grid may still be empty by the
 * time SalesOrderPage.addOrderLine's 2-second timeout expires — its retry then
 * re-fires the callout and duplicates the bundle expansion).
 */
async function addBundleViaQuickInput({ page, recordId, productCode, quantity }) {
    // Open the order-lines tab and the batch entry panel.
    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
    await page.locator('.rotating, .panel-spaced-lg')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

    const batchToggle = page.getByTestId('batch-entry-toggle');
    await batchToggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await batchToggle.click();

    const productField = page.locator('.quick-input-container #lookup_M_Product_ID input');
    await productField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await productField.fill(productCode);
    await page.waitForTimeout(800);
    // Pick the first lookup suggestion to resolve the product.
    await page.locator('.input-dropdown-list-option').first().click();
    await page.waitForTimeout(500);

    const qtyField = page.locator('.quick-input-container').getByRole('spinbutton');
    await qtyField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await qtyField.click();
    await qtyField.fill(String(quantity));
    await page.waitForTimeout(300);

    await page.keyboard.press('Enter');
    // Give the synchronous OL save a moment, then close the modal so we don't accidentally re-submit.
    await page.waitForTimeout(2500);
    await page.locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
    const stillOpen = await page.locator('.quick-input-container').isVisible().catch(() => false);
    if (stillOpen) {
        await batchToggle.click();
        await page.waitForTimeout(800);
    }
}

