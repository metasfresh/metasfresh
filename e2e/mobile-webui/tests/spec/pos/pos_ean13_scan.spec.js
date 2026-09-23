import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";

// de_DE login: rendered amounts/quantities use comma decimals (see exactTextMatch in posText.js).
//
// P1's `value` is deliberately left to the masterdata API's default (a random per-run unique
// string, see CreateProductCommand#generateValue) — it never starts with the scanned EAN13
// prefixes below, so a lookup that (wrongly) matched against M_Product.Value instead of
// M_Product.EAN13_ProductCode could not accidentally pass.
const createMasterdata = ({ ean13ProductCode, pricePerKg }) => {
    return Backend.createMasterdata({
        language: 'de_DE',
        request: {
            login: { user: { language: 'de_DE' } },
            products: {
                P1: {
                    uom: 'KGM', // stock UOM must be KGM: a KGM price on an EACH-stocked product is rejected.
                    ean13ProductCode,
                },
            },
            posTerminals: {
                T1: {
                    priceListCurrency: 'EUR',
                    isTaxIncluded: true,
                    // POS price lives on the terminal's own price list (since T1.1) - never on the product.
                    products: { P1: { price: pricePerKg, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' } },
                },
            },
        },
    });
};

const loginAndOpenOrderPanel = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: 100 });
    await POSOrderPanel.waitForScreen();
};

// noinspection JSUnusedLocalSymbols
test('prefix 28 label resolves via EAN13_ProductCode (AC10)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - Scan a customer variable-weight scale label (EAN13 prefix 28)');
    allure.severity('critical');

    // Real customer scale label: prefix 28 (variable weight), product code 59414, embedded weight 0.482 kg
    // (EAN13Test#happyCase fixture).
    const masterdata = await createMasterdata({ ean13ProductCode: '59414', pricePerKg: 15.50 });

    await loginAndOpenOrderPanel(masterdata);

    await POSOrderPanel.scanBarcode('2859414004825');

    // 0,482 kg x 15,50 EUR/kg = 7,471, rounded to the terminal's price precision (2 decimals) = 7,47.
    await POSOrderPanel.expectLine({
        index: 0,
        productName: masterdata.products.P1.productName,
        catchWeight: '0,482 kg',
        amount: '7,47',
    });
});

// noinspection JSUnusedLocalSymbols
test('prefix 29 label resolves via EAN13_ProductCode (AC11)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - Scan a customer variable-weight scale label (EAN13 prefix 29)');
    allure.severity('critical');

    // Prefix 29 (internal use / variable measure), product code 1234, embedded weight 0.500 kg
    // (EAN13Test#happyCase fixture for InternalUseOrVariableMeasure_prefix29).
    const masterdata = await createMasterdata({ ean13ProductCode: '1234', pricePerKg: 10.00 });

    await loginAndOpenOrderPanel(masterdata);

    await POSOrderPanel.scanBarcode('2912345005009');

    // 0,500 kg x 10,00 EUR/kg = 5,00 (exact, no rounding).
    await POSOrderPanel.expectLine({
        index: 0,
        productName: masterdata.products.P1.productName,
        catchWeight: '0,500 kg',
        amount: '5,00',
    });
});
