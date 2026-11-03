import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { POSInvoiceSettlementModal } from "../../utils/screens/pos/POSInvoiceSettlementModal";
import { POSCashJournalModals } from "../../utils/screens/pos/POSCashJournalModals";

// de_DE login: rendered amounts use comma decimals (see exactTextMatch in posText.js).
const createMasterdata = () => Backend.createMasterdata({
    language: 'de_DE',
    request: {
        login: { user: { language: 'de_DE' } },
        // invoiceRule: 'I' (Immediate) - the settlement dialog under test doesn't care HOW C1's invoice
        // became invoiceable, only that an open, unpaid invoice exists to settle. Immediate invoicing gets
        // one into existence without also having to build out a delivery/shipment leg that's orthogonal to
        // this feature; the masterdata default (After Delivery, inherited from the "Standard" BP group /
        // system default - neither carries an override) would otherwise leave QtyToInvoice=0 forever, since
        // this scenario never delivers anything.
        bpartners: { C1: { isSoPriceList: true, invoiceRule: 'I' } },
        products: { P1: { price: 100 } },
        warehouses: { wh: {} },
        posTerminals: {
            T1: {
                priceListCurrency: 'EUR',
                isTaxIncluded: true,
            },
        },
        // A real, already-issued open sales invoice, unrelated to the till - the customer holds it from a
        // prior visit and pays it now at the register. Created (and completed) via the ordinary sales-order
        // -> invoice-candidate pipeline, same as every other invoice-bearing spec in this suite.
        salesOrders: {
            SO1: {
                bpartner: 'C1',
                warehouse: 'wh',
                datePromised: '2025-03-01T00:00:00.000+02:00',
                lines: [{ product: 'P1', qty: 1 }],
            },
        },
        invoices: {
            INV1: { salesOrder: 'SO1' },
        },
    },
});

// noinspection JSUnusedLocalSymbols
test('Settling an open sales invoice in cash at the till', async ({ page }) => {
    // Search + select + tender + confirm + Backend.expect (invoice paid, allocated cash payment) + closing summary.
    test.setTimeout(180_000);

    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - Settle an open sales invoice in cash at the till');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const invoice = masterdata.invoices.INV1;
    // C1 was created without an explicit `name`, so C_BPartner.Name defaults to its Value (= bpartnerCode) - see
    // CreateBPartnerCommand#execute.
    const bpartnerName = masterdata.bpartners.C1.bpartnerCode;

    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: 100 });
    await POSOrderPanel.waitForScreen();

    await POSInvoiceSettlementModal.open();
    const results = await POSInvoiceSettlementModal.search({ documentNo: invoice.documentNo });

    // The server's own numbers - asserted against instead of re-derived, per the docs on Backend.expect
    // (a number you didn't measure carries a label, not a predicate).
    if (results.length !== 1) {
        throw new Error(`Expected exactly one open invoice for documentNo ${invoice.documentNo}, got:\n` + JSON.stringify(results, null, 2));
    }
    const openAmt = Number(results[0].openAmt);
    const openAmtStr = openAmt.toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

    await POSInvoiceSettlementModal.expectResult({
        documentNo: invoice.documentNo,
        bpartnerName,
        openAmt: `${openAmtStr} €`,
    });

    await POSInvoiceSettlementModal.selectResult();

    const tendered = 120;
    await POSInvoiceSettlementModal.enterTenderedAmount({ tendered });
    const changeBackStr = (tendered - openAmt).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    await POSInvoiceSettlementModal.expectChangeBack({ changeBack: `${changeBackStr} €` });
    await POSInvoiceSettlementModal.confirmPayment();

    await POSInvoiceSettlementModal.expectSuccess({ documentNo: invoice.documentNo });

    await Backend.expect({
        pos: {
            invoices: [{
                invoice: 'INV1',
                isPaid: true,
                hasAllocatedPayment: true,
                allocatedPayments: [{ tenderType: 'X' }],
            }],
            posTerminal: 'T1',
            cashJournalLines: [{ type: 'CASH_INOUT', amount: results[0].grandTotal }],
        },
    });

    await POSInvoiceSettlementModal.close();

    await POSCashJournalModals.openClosing();
    // The settlement books a manual cash-in (CASH_IN detail row) - distinct from an ordinary sale's
    // CASH_PAYMENTS row.
    await POSCashJournalModals.expectSummary({
        cashIn: openAmtStr,
        endingBalance: (100 + openAmt).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 }),
    });
});
