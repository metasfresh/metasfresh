import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { POSPaymentPanel } from "../../utils/screens/pos/POSPaymentPanel";
import { POSCashJournalModals } from "../../utils/screens/pos/POSCashJournalModals";

let previousSysconfigs = null;

// Restore in an afterEach so it also runs when the test body fails or times out.
test.afterEach(async () => {
    if (previousSysconfigs && Object.keys(previousSysconfigs).length > 0) {
        await Backend.setSysconfigs(previousSysconfigs);
    }
});

// de_DE login: rendered amounts/quantities use comma decimals (see exactTextMatch in posText.js).
const createMasterdata = async () => {
    const masterdata = await Backend.createMasterdata({
        language: 'de_DE',
        request: {
            // The order summary shows the receipt PDF, which is the POS order's archive written by the
            // document-outbound work package. Cucumber runs switch that processor off via this sysconfig
            // and leave it on a shared DB, so set it explicitly rather than inherit it.
            sysconfigs: { SKIP_WP_PROCESSOR_FOR_AUTOMATION: 'N' },
            login: { user: { language: 'de_DE' } },
            products: { P1: {} },
            posTerminals: {
                T1: {
                    priceListCurrency: 'EUR',
                    isTaxIncluded: true,
                    products: { P1: { price: 2.50 } },
                },
            },
        },
    });

    previousSysconfigs = masterdata.previousSysconfigs;
    return masterdata;
};

// noinspection JSUnusedLocalSymbols
test('An ordinary cash sale completes and journals', async ({ page }) => {
    // Completing the order triggers async invoice+shipment creation, then a Jasper receipt PDF
    // fetch (retried up to 60x1s) before the summary panel can be closed - above the 120s default.
    test.setTimeout(180_000);

    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - An ordinary cash sale completes and journals');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: 100 });
    await POSOrderPanel.waitForScreen();

    await POSOrderPanel.scanBarcode(masterdata.products.P1.productCode);
    await POSOrderPanel.expectLine({ index: 0, productName: masterdata.products.P1.productName, amount: '2,50' });

    await POSOrderPanel.checkout();
    await POSPaymentPanel.payCash({ tendered: 10 });
    await POSPaymentPanel.expectOrderCompleted();
    // Checkout creates the sales order, its invoice (paid by the cash payment) and its shipment asynchronously.
    await Backend.expect({
        posOrders: {
            T1: { cashier: 'user', salesOrderDocStatus: 'CL', invoiceDocStatus: 'CO', invoicePaid: true, shipmentDocStatus: 'CO' },
        },
    });
    await POSPaymentPanel.closeOrderSummary();

    await POSCashJournalModals.openClosing();
    // Cash sales book under CASH_PAYMENTS; CASH_IN is only for manual cash deposits.
    await POSCashJournalModals.expectSummary({ cashPayments: '2,50', endingBalance: '102,50' });
});
