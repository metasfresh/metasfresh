import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { generateEAN13 } from "../../utils/ean13";

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
                    // The POS price lives on the terminal's own price list, not on the product.
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

    // A fresh random product code each run - a fixed code would collide with the row a previous
    // run left active (EAN13_ProductCode lookup requires exactly one match) and go RED on re-run.
    // The customer's literal label (2859414004825, product code 59414) is pinned separately by a
    // ProductBL unit test; this spec only needs a barcode with the same structure: prefix 28
    // (variable weight), a 5-digit product code, and the embedded weight 00482 (= 0,482 kg).
    const code28 = String(Math.floor(Math.random() * 100000)).padStart(5, '0');
    const { ean13: label28 } = generateEAN13({ prefix: '28', productCode: `${code28}00482` });
    const masterdata = await createMasterdata({ ean13ProductCode: code28, pricePerKg: 15.50 });

    await loginAndOpenOrderPanel(masterdata);

    await POSOrderPanel.scanBarcode(label28);

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

    // A fresh random product code each run - see the prefix-28 test above for why. Barcode
    // structure: prefix 29 (internal use / variable measure), a 4-digit product code, and the
    // embedded weight 00500 (= 0,500 kg).
    const code29 = String(Math.floor(Math.random() * 10000)).padStart(4, '0');
    const { ean13: label29 } = generateEAN13({ prefix: '29', productCode: `${code29}000500` });
    const masterdata = await createMasterdata({ ean13ProductCode: code29, pricePerKg: 10.00 });

    await loginAndOpenOrderPanel(masterdata);

    await POSOrderPanel.scanBarcode(label29);

    // 0,500 kg x 10,00 EUR/kg = 5,00 (exact, no rounding).
    await POSOrderPanel.expectLine({
        index: 0,
        productName: masterdata.products.P1.productName,
        catchWeight: '0,500 kg',
        amount: '5,00',
    });
});
