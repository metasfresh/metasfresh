import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { POSCashWithdrawalModal } from "../../utils/screens/pos/POSCashWithdrawalModal";
import { POSCashJournalModals } from "../../utils/screens/pos/POSCashJournalModals";

let previousSysconfigs = null;

// Restore in an afterEach so it also runs when the test body fails or times out.
test.afterEach(async () => {
    if (previousSysconfigs && Object.keys(previousSysconfigs).length > 0) {
        await Backend.setSysconfigs(previousSysconfigs);
    }
});

// de_DE login: rendered amounts use comma decimals (see exactTextMatch in posText.js).
const createMasterdata = async ({ cashierLastname }) => {
    const masterdata = await Backend.createMasterdata({
        language: 'de_DE',
        request: {
            login: { user: { language: 'de_DE', firstname: 'Kasse', lastname: cashierLastname } },
            posTerminals: {
                T1: {
                    priceListCurrency: 'EUR',
                    isTaxIncluded: true,
                    // Charge names are unique per client: the masterdata returns the actual (per-run) names.
                    cashWithdrawalCategories: ['Reisekosten AN', 'Porto'],
                },
            },
        },
    });

    previousSysconfigs = masterdata.previousSysconfigs;
    return masterdata;
};

const today = () => new Date().toLocaleDateString('de-DE', { day: '2-digit', month: '2-digit', year: 'numeric' });

// noinspection JSUnusedLocalSymbols
test('Cash taken out of the till for an expense is journaled and prints a receipt slip', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - Cash withdrawal with receipt slip');
    allure.severity('critical');

    const cashierLastname = `Withdrawal${Date.now()}`;
    const masterdata = await createMasterdata({ cashierLastname });
    const terminal = masterdata.posTerminals.T1;
    const travelCosts = terminal.cashWithdrawalCategories['Reisekosten AN'];
    const postage = terminal.cashWithdrawalCategories['Porto'];

    await POSCashWithdrawalModal.recordPrintedDocuments();
    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: terminal.id });
    await POSScreen.openCashJournal({ openingBalance: 100 });
    await POSOrderPanel.waitForScreen();

    await POSCashWithdrawalModal.open();
    await POSCashWithdrawalModal.expectCategories({ names: [postage.name, travelCosts.name] });
    await POSCashWithdrawalModal.selectCategory({ name: travelCosts.name });
    await POSCashWithdrawalModal.expectOKDisabled(); // no amount yet
    await POSCashWithdrawalModal.keyAmount({ digits: '12' });
    await POSCashWithdrawalModal.expectAmount({ amount: '12,00 €' });
    await POSCashWithdrawalModal.confirm();

    await POSCashWithdrawalModal.expectSlip({
        terminal: terminal.name,
        date: today(),
        cashier: cashierLastname,
        category: travelCosts.name,
        amount: '12,00 €',
    });
    await POSCashWithdrawalModal.expectSlipPrinted({ texts: [travelCosts.name, '12,00 €', 'Geld erhalten'] });
    await POSCashWithdrawalModal.closeSlip();

    await POSCashJournalModals.openClosing();
    await POSCashJournalModals.expectSummary({ cashOut: '-12,00', cashOutDescription: travelCosts.name, endingBalance: '88,00' });
});
