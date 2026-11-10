import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { POSPaymentPanel } from "../../utils/screens/pos/POSPaymentPanel";
import { SumUpStub } from "../../utils/sumupStub";

// Paying a till sale by card, against a SumUp stub on the stack's WireMock (see sumupStub.js).
// WireMock state is global, hence serial, and every test removes its stubs.
test.describe.configure({ mode: 'serial' });

test.afterEach(async () => {
    await SumUpStub.removeAll();
});

const SYSCONFIG_ReleaseAfterSeconds = 'de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds';
const MESSAGE_ReleasedAsNotCharged = 'de.metas.pos.POSPayment.ReleasedAsNotCharged';
const OPENING_BALANCE = 100;

/**
 * The sale data of the cash sale spec (pos_cash_sale.spec.js): de_DE, EUR gross price list (isTaxIncluded), P1 at 2,50;
 * plus the payment methods CASH and CARD, as in pos_invoice_card_settlement.spec.js.
 */
const createMasterdata = () => Backend.createMasterdata({
    language: 'de_DE',
    request: {
        // The order summary shows the receipt PDF, which is the POS order's archive written by the
        // document-outbound work package; cucumber runs switch that processor off on a shared DB.
        sysconfigs: { SKIP_WP_PROCESSOR_FOR_AUTOMATION: 'N' },
        login: { user: { language: 'de_DE' } },
        products: { P1: {} },
        posTerminals: {
            T1: {
                priceListCurrency: 'EUR',
                isTaxIncluded: true,
                paymentMethods: ['CASH', 'CARD'],
                products: { P1: { price: 2.50 } },
            },
        },
    },
});

const cardTerminalOf = (masterdata) => ({
    merchantCode: masterdata.posTerminals.T1.sumUpMerchantCode,
    readerId: masterdata.posTerminals.T1.sumUpCardReaderId,
});

/** Logs in, opens the till with a fresh journal, scans P1 and goes to the payment panel. */
const openTillAndCheckoutP1 = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: OPENING_BALANCE });
    await POSOrderPanel.waitForScreen();

    await POSOrderPanel.scanBarcode(masterdata.products.P1.productCode);
    await POSOrderPanel.expectLine({ index: 0, productName: masterdata.products.P1.productName, amount: '2,50' });
    await POSOrderPanel.checkout();
};

const setAllureMetadata = (story) => {
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story(story);
    allure.severity('critical');
};

// noinspection JSUnusedLocalSymbols
test('An approved card sale completes', async ({ page }) => {
    test.setTimeout(180_000);
    setAllureMetadata('POS - A card sale');

    const masterdata = await createMasterdata();
    const cardTerminal = cardTerminalOf(masterdata);
    const { clientTransactionId } = await SumUpStub.stubSaleCheckout(cardTerminal);

    await openTillAndCheckoutP1({ masterdata });
    await POSPaymentPanel.payCard();

    // the customer approves on the reader
    const approval = await SumUpStub.setTransactionStatus({
        clientTransactionId,
        status: 'SUCCESSFUL',
        amount: 2.50,
        merchantCode: cardTerminal.merchantCode,
    });
    await approval.waitForLookup();

    await POSPaymentPanel.expectOrderCompleted();
    await Backend.expect({
        pos: {
            posTerminal: 'T1',
            cardPayments: [{ status: 'SUCCESSFUL', issueMessages: [] }],
        },
    });
});

// TC24: the card sale's send to the reader times out, so whether the reader got the checkout is unknown.
// noinspection JSUnusedLocalSymbols
test('A card sale whose send times out shows result unknown and can be released after the wait', async ({ page }) => {
    test.setTimeout(300_000);
    setAllureMetadata('POS - A card sale');

    // the floor of the wait; restored after the test
    await Backend.setSysconfigs({ [SYSCONFIG_ReleaseAfterSeconds]: '150' });

    const masterdata = await createMasterdata();
    const cardTerminal = cardTerminalOf(masterdata);
    // beyond the SumUp client's 60 s read timeout: whether the reader got the checkout is unknown
    await SumUpStub.stubSaleCheckout({ ...cardTerminal, fixedDelayMilliseconds: 65_000 });
    await SumUpStub.stubTerminate(cardTerminal);

    await openTillAndCheckoutP1({ masterdata });
    await POSPaymentPanel.payCard();

    // the result is unknown: nothing to cancel or retry, no other payment is due, the sale is not completed
    await POSPaymentPanel.expectCardPaymentUnknown();
    await POSPaymentPanel.expectNoCancelOrRetry();
    await POSPaymentPanel.expectOpenAmount('0,00');
    await POSPaymentPanel.expectOrderNotCompleted();
    await POSPaymentPanel.expectCardReleaseEnabled({ isEnabled: false });

    // once the payment is 150 s old, the cashier checks the reader and releases the payment as not charged
    await POSPaymentPanel.expectCardReleaseEnabled({ isEnabled: true, timeout: 170_000 });
    await POSPaymentPanel.releaseCardPayment({ confirm: true });

    await POSPaymentPanel.expectCardPaymentFailedWithCancelAndRetry();
    await Backend.expect({
        pos: {
            posTerminal: 'T1',
            cardPayments: [{ status: 'FAILED', issueMessages: [MESSAGE_ReleasedAsNotCharged] }],
        },
    });
});
