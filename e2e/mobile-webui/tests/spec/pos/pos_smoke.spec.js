import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";

// POS specs log in as de_DE (the customer locale; also required for amounts/quantities to render with
// the comma-decimal format that posText.js's exactTextMatch examples and the plan's later POS tasks
// assume - see posText.js).
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'de_DE',
        request: {
            login: { user: { language: 'de_DE' } },
            products: { P1: {} },
            posTerminals: {
                T1: {
                    priceListCurrency: 'EUR',
                    isTaxIncluded: true,
                    products: { P1: { price: 5 } },
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Open cash journal and see an empty order panel', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - Open cash journal');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: 100 });
    await POSOrderPanel.expectEmpty();
});
