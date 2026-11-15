import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { POSInvoiceSettlementModal } from "../../utils/screens/pos/POSInvoiceSettlementModal";
import { POSCashJournalModals } from "../../utils/screens/pos/POSCashJournalModals";
import { SumUpStub } from "../../utils/sumupStub";

// Paying an open sales invoice by card at the till ("Rechnung bezahlen" -> "Kartenzahlung"), against a SumUp stub
// on the stack's WireMock (see sumupStub.js). WireMock state is global, hence serial, and every test removes its stubs.
test.describe.configure({ mode: 'serial' });

test.afterEach(async () => {
    await SumUpStub.removeAll();
});

const TEXT = {
    cash: 'Barzahlung',
    card: 'Kartenzahlung',
    waiting: 'Warte auf Kartenterminal',
    failed: 'Kartenzahlung nicht erfolgreich',
    unknown: 'Ergebnis unbekannt — bitte Kartenterminal prüfen',
    cardPaymentOpen: 'Kartenzahlung offen',
    cashRefusedCardPaymentOpen: 'Für diese Rechnung ist noch eine Kartenzahlung offen',
    releaseConfirmation: 'Ich habe das Kartenterminal geprüft: Die Karte wurde nicht belastet',
    released: 'Freigegeben',
    closeJournal: 'Schließen',
};

const SYSCONFIG_ReleaseAfterSeconds = 'de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds';
const OPENING_BALANCE = 100;

const formatAmount = (amount) => Number(amount).toLocaleString('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 });

/**
 * The invoice mirrors a real reference invoice of this feature's customer: a sales invoice (ARI) in EUR,
 * net prices (IsTaxIncluded=N: the customer's price list is created net), payment term 1000, two lines at the
 * reduced 7 % tax, grand total = open amount = 16,07 EUR, no allocations yet.
 * The line split 10,00 + 5,02 net is DERIVED from that total at 7 % (tax 1,05), not read from the reference invoice:
 * the probe returned no line amounts.
 */
const createMasterdata = ({ paymentMethods }) => Backend.createMasterdata({
    language: 'de_DE',
    request: {
        login: { user: { language: 'de_DE' } },
        // invoiceRule 'I' (Immediate): the invoice must exist and be open; how it became invoiceable is not under test
        bpartners: { C1: { isSoPriceList: true, invoiceRule: 'I' } },
        products: {
            P1: { prices: [{ price: 10.00, taxCategory: 'Reduced' }] },
            P2: { prices: [{ price: 5.02, taxCategory: 'Reduced' }] },
        },
        warehouses: { wh: {} },
        posTerminals: {
            T1: {
                priceListCurrency: 'EUR',
                isTaxIncluded: true,
                paymentMethods,
            },
        },
        salesOrders: {
            SO1: {
                bpartner: 'C1',
                warehouse: 'wh',
                datePromised: '2025-03-01T00:00:00.000+02:00',
                paymentTerm: '1000',
                lines: [
                    { product: 'P1', qty: 1 },
                    { product: 'P2', qty: 1 },
                ],
            },
        },
        invoices: {
            INV1: { salesOrder: 'SO1' },
        },
    },
});

/** Logs in, opens the till with a fresh journal, opens "Rechnung bezahlen" and searches the invoice. */
const openTillAndSearchInvoice = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: OPENING_BALANCE });
    await POSOrderPanel.waitForScreen();

    return await searchInvoice({ masterdata });
};

const searchInvoice = async ({ masterdata }) => {
    await POSInvoiceSettlementModal.open();
    return await POSInvoiceSettlementModal.search({ documentNo: masterdata.invoices.INV1.documentNo });
};

const expectSingleResult = (results, documentNo) => {
    if (results.length !== 1) {
        throw new Error(`Expected exactly one open invoice for documentNo ${documentNo}, got:\n` + JSON.stringify(results, null, 2));
    }
    return results[0];
};

const cardTerminalOf = (masterdata) => ({
    merchantCode: masterdata.posTerminals.T1.sumUpMerchantCode,
    readerId: masterdata.posTerminals.T1.sumUpCardReaderId,
});

/** Retries a backend expectation until it holds: the SumUp poller and the booking run asynchronously. */
const expectEventually = async (backendExpectation, timeout = 60_000) => {
    await expect(async () => {
        await Backend.expect(backendExpectation);
    }).toPass({ timeout, intervals: [2_000] });
};

const setAllureMetadata = (story) => {
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story(story);
    allure.severity('critical');
};

// noinspection JSUnusedLocalSymbols
test('Paying the reference invoice by card at the till', async ({ page }) => {
    test.setTimeout(180_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const bpartnerName = masterdata.bpartners.C1.bpartnerCode; // C_BPartner.Name defaults to its Value
    const cardTerminal = cardTerminalOf(masterdata);
    const checkout = await SumUpStub.stubCheckout(cardTerminal);

    const result = expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    // The reference invoice's open amount; this also proves the net prices at 7 % reached the invoice.
    expect(Number(result.openAmt)).toBe(16.07);
    const openAmtStr = formatAmount(result.openAmt);

    await POSInvoiceSettlementModal.selectResultForPaymentChoice();
    await POSInvoiceSettlementModal.expectSelectedInvoice({ documentNo: invoice.documentNo, bpartnerName, openAmt: `${openAmtStr} €` });
    await POSInvoiceSettlementModal.expectPaymentMethodsOffered({ isCashOffered: true, isCardOffered: true });

    const settlement = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(settlement.id),
        status: 'PENDING',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.waiting });

    // the customer approves on the reader
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(settlement.id),
        status: 'SUCCESSFUL',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.expectPaidByCard({ documentNo: invoice.documentNo });

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true, allocatedPayments: [{ tenderType: 'C' }] }],
            posTerminal: 'T1',
            cashJournalLines: [{ type: 'CARD_PAY', amount: result.openAmt, cardSettlement: true }],
            cardSettlements: [{ invoice: 'INV1', status: 'SU' }],
        },
    });

    await POSInvoiceSettlementModal.close();

    await POSCashJournalModals.openClosing();
    await POSCashJournalModals.expectPaymentMethodTotal({ paymentMethod: 'CARD', amount: openAmtStr });
    await POSCashJournalModals.expectSummary({ endingBalance: formatAmount(OPENING_BALANCE) });
});

// noinspection JSUnusedLocalSymbols
test('A declined card payment can be retried by card', async ({ page }) => {
    test.setTimeout(180_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const cardTerminal = cardTerminalOf(masterdata);
    const checkout = await SumUpStub.stubCheckout(cardTerminal);

    const result = expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.selectResultForPaymentChoice();

    const declined = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(declined.id),
        status: 'FAILED',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.failed });
    await POSInvoiceSettlementModal.expectPaymentMethodsOffered({ isCashOffered: true, isCardOffered: true });

    const retried = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(retried.id),
        status: 'SUCCESSFUL',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.expectPaidByCard({ documentNo: invoice.documentNo });

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true, allocatedPayments: [{ tenderType: 'C' }] }],
            posTerminal: 'T1',
            cashJournalLines: [{ type: 'CARD_PAY', amount: result.openAmt, cardSettlement: true }],
            cardSettlements: [{ invoice: 'INV1', status: 'FA' }, { invoice: 'INV1', status: 'SU' }],
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('After a declined card payment the invoice can be paid in cash', async ({ page }) => {
    test.setTimeout(180_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const cardTerminal = cardTerminalOf(masterdata);
    const checkout = await SumUpStub.stubCheckout(cardTerminal);

    const result = expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.selectResultForPaymentChoice();

    const declined = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(declined.id),
        status: 'FAILED',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.failed });

    await POSInvoiceSettlementModal.payByCash();
    await POSInvoiceSettlementModal.enterTenderedAmount({ tendered: 20 });
    await POSInvoiceSettlementModal.confirmPayment();
    await POSInvoiceSettlementModal.expectSuccess({ documentNo: invoice.documentNo });

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true, allocatedPayments: [{ tenderType: 'X' }] }],
            posTerminal: 'T1',
            cashJournalLines: [{ type: 'CASH_INOUT', amount: result.grandTotal }],
            cardSettlements: [{ invoice: 'INV1', status: 'FA' }],
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('A waiting card payment is shown again when the invoice is reopened, and cash is refused', async ({ page }) => {
    test.setTimeout(180_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const cardTerminal = cardTerminalOf(masterdata);
    const checkout = await SumUpStub.stubCheckout(cardTerminal);

    const result = expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.selectResultForPaymentChoice();

    const settlement = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(settlement.id),
        status: 'PENDING',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.closeDialog();

    expectSingleResult(await searchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.expectResultCardPaymentOpen({ text: TEXT.cardPaymentOpen });
    const checked = await POSInvoiceSettlementModal.selectResultWithOpenCardPayment();
    expect(checked.status).toBe('PE');
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.waiting });
    await POSInvoiceSettlementModal.expectCashRefused({ message: TEXT.cashRefusedCardPaymentOpen });

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: false, hasAllocatedPayment: false }],
            cardSettlements: [{ invoice: 'INV1', status: 'PE' }],
        },
    });

    // the cashier cancels the payment on the reader: the till shows that it did not go through, and cash is offered again
    await SumUpStub.setTransactionStatus({
        clientTransactionId: checkout.clientTransactionIdOf(settlement.id),
        status: 'CANCELLED',
        amount: result.openAmt,
        merchantCode: cardTerminal.merchantCode,
    });
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.failed });
    await POSInvoiceSettlementModal.expectPaymentMethodsOffered({ isCashOffered: true, isCardOffered: true });
    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: false, hasAllocatedPayment: false }],
            cardSettlements: [{ invoice: 'INV1', status: 'CA' }],
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('On a cash-only terminal the invoice goes straight to the cash keypad', async ({ page }) => {
    test.setTimeout(180_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    const masterdata = await createMasterdata({ paymentMethods: ['CASH'] });
    const invoice = masterdata.invoices.INV1;

    expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.selectResult();
    await POSInvoiceSettlementModal.expectNoPaymentChoice();
});

// noinspection JSUnusedLocalSymbols
test('A send that times out shows result unknown, cash is refused, and the payment can be released after the wait', async ({ page }) => {
    test.setTimeout(300_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    // the floor of the wait; restored after the test
    await Backend.setSysconfigs({ [SYSCONFIG_ReleaseAfterSeconds]: '150' });

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const cardTerminal = cardTerminalOf(masterdata);
    // beyond the SumUp client's 60 s read timeout: whether the reader got the checkout is unknown
    await SumUpStub.stubCheckout({ ...cardTerminal, fixedDelayMilliseconds: 65_000 });
    await SumUpStub.stubTerminate(cardTerminal);

    expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.selectResultForPaymentChoice();

    const settlement = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting, responseTimeout: 90_000 });
    expect(settlement.status).toBe('UN');
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.unknown });
    await POSInvoiceSettlementModal.expectCashRefused({ message: TEXT.cashRefusedCardPaymentOpen });

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: false, hasAllocatedPayment: false }],
            cardSettlements: [{ invoice: 'INV1', status: 'UN' }],
        },
    });

    // after the wait the cashier checks the reader and releases the payment as not charged
    await POSInvoiceSettlementModal.expectReleaseEnabled({ isEnabled: true, timeout: 170_000 });
    const releaseResponse = await POSInvoiceSettlementModal.release();
    expect(releaseResponse.confirmationRequired).toBe(true);
    await POSInvoiceSettlementModal.expectReleaseConfirmation({ text: TEXT.releaseConfirmation });
    const confirmed = await POSInvoiceSettlementModal.confirmRelease();
    expect(confirmed.settlement.status).toBe('AB');
    expect(confirmed.settlement.isAbandoned).toBe(true);
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.released });
    await POSInvoiceSettlementModal.expectPaymentMethodsOffered({ isCashOffered: true, isCardOffered: true });
    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: false, hasAllocatedPayment: false }],
            cardSettlements: [{ invoice: 'INV1', status: 'AB' }],
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('A card approval while the journal is closed is booked on the next journal', async ({ page }) => {
    test.setTimeout(240_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const cardTerminal = cardTerminalOf(masterdata);
    const checkout = await SumUpStub.stubCheckout(cardTerminal);

    const result = expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    const openAmtStr = formatAmount(result.openAmt);
    await POSInvoiceSettlementModal.selectResultForPaymentChoice();

    const settlement = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    const clientTransactionId = checkout.clientTransactionIdOf(settlement.id);
    await SumUpStub.setTransactionStatus({ clientTransactionId, status: 'PENDING', amount: result.openAmt, merchantCode: cardTerminal.merchantCode });
    await POSInvoiceSettlementModal.closeDialog();

    // the day is closed while the card payment is still waiting
    await POSCashJournalModals.openClosing();
    await POSCashJournalModals.closeJournal({ closeButtonCaption: TEXT.closeJournal });

    // the customer approves on the reader: with no open journal the booking waits (approved, not yet booked)
    await SumUpStub.setTransactionStatus({ clientTransactionId, status: 'SUCCESSFUL', amount: result.openAmt, merchantCode: cardTerminal.merchantCode });
    await expectEventually({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: false }],
            cardSettlements: [{ invoice: 'INV1', status: 'AP' }],
        },
    });

    // the next journal is opened on the till: the waiting booking completes on it
    await POSScreen.openCashJournal({ openingBalance: OPENING_BALANCE });
    await POSOrderPanel.waitForScreen();
    await expectEventually({
        pos: {
            cardSettlements: [{ invoice: 'INV1', status: 'SU' }],
        },
    });

    await searchInvoice({ masterdata });
    await POSInvoiceSettlementModal.expectNoResults();

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true, allocatedPayments: [{ tenderType: 'C' }] }],
            posTerminal: 'T1',
            cashJournalLines: [{ type: 'CARD_PAY', amount: result.openAmt, cardSettlement: true }],
            cardSettlements: [{ invoice: 'INV1', status: 'SU' }],
        },
    });

    await POSInvoiceSettlementModal.closeDialog();
    await POSCashJournalModals.openClosing();
    await POSCashJournalModals.expectPaymentMethodTotal({ paymentMethod: 'CARD', amount: openAmtStr });
    await POSCashJournalModals.expectSummary({ endingBalance: formatAmount(OPENING_BALANCE) });
});

// noinspection JSUnusedLocalSymbols
test('A waiting card payment is released after the wait and the invoice is paid in cash', async ({ page }) => {
    test.setTimeout(240_000);
    setAllureMetadata('POS - Pay an open sales invoice by card at the till');

    // the floor of the wait; restored after the test
    await Backend.setSysconfigs({ [SYSCONFIG_ReleaseAfterSeconds]: '150' });

    const masterdata = await createMasterdata({ paymentMethods: ['CASH', 'CARD'] });
    const invoice = masterdata.invoices.INV1;
    const cardTerminal = cardTerminalOf(masterdata);
    const checkout = await SumUpStub.stubCheckout(cardTerminal);
    await SumUpStub.stubTerminate(cardTerminal);

    const result = expectSingleResult(await openTillAndSearchInvoice({ masterdata }), invoice.documentNo);
    await POSInvoiceSettlementModal.selectResultForPaymentChoice();

    const settlement = await POSInvoiceSettlementModal.payByCard({ waitingText: TEXT.waiting });
    const clientTransactionId = checkout.clientTransactionIdOf(settlement.id);
    await SumUpStub.setTransactionStatus({ clientTransactionId, status: 'PENDING', amount: result.openAmt, merchantCode: cardTerminal.merchantCode });

    await POSInvoiceSettlementModal.expectReleaseEnabled({ isEnabled: false });
    await POSInvoiceSettlementModal.expectReleaseEnabled({ isEnabled: true, timeout: 170_000 });
    if (Date.now() < Date.parse(settlement.releasableAt)) {
        throw new Error(`Release was enabled before ${settlement.releasableAt}`);
    }

    // SumUp still says pending: the cashier must confirm that the card was not charged
    const releaseResponse = await POSInvoiceSettlementModal.release();
    expect(releaseResponse.confirmationRequired).toBe(true);
    await POSInvoiceSettlementModal.expectReleaseConfirmation({ text: TEXT.releaseConfirmation });
    const confirmed = await POSInvoiceSettlementModal.confirmRelease();
    // the expectations API checks the status only; the abandoned flag is read from the release response
    expect(confirmed.settlement.status).toBe('AB');
    expect(confirmed.settlement.isAbandoned).toBe(true);
    await POSInvoiceSettlementModal.expectCardPaymentStatus({ text: TEXT.released });
    // the terminated checkout ends as cancelled at SumUp; the released payment stays released
    const cancelledAtSumUp = await SumUpStub.setTransactionStatus({ clientTransactionId, status: 'CANCELLED', amount: result.openAmt, merchantCode: cardTerminal.merchantCode });

    await POSInvoiceSettlementModal.payByCash();
    await POSInvoiceSettlementModal.enterTenderedAmount({ tendered: 20 });
    await POSInvoiceSettlementModal.confirmPayment();
    await POSInvoiceSettlementModal.expectSuccess({ documentNo: invoice.documentNo });

    await Backend.expect({
        pos: {
            invoices: [{ invoice: 'INV1', isPaid: true, hasAllocatedPayment: true, allocatedPayments: [{ tenderType: 'X' }] }],
            posTerminal: 'T1',
            cashJournalLines: [{ type: 'CASH_INOUT', amount: result.grandTotal }],
            cardSettlements: [{ invoice: 'INV1', status: 'AB' }],
        },
    });
    await cancelledAtSumUp.waitForLookup();
});
