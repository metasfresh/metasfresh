import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { POSScreen } from "../../utils/screens/pos/POSScreen";
import { POSOrderPanel } from "../../utils/screens/pos/POSOrderPanel";
import { POSReturnPanel } from "../../utils/screens/pos/POSReturnPanel";
import { POSCashJournalModals } from "../../utils/screens/pos/POSCashJournalModals";
import { generateEAN13 } from "../../utils/ean13";

// de_DE login: rendered amounts/quantities use comma decimals (see exactTextMatch in posText.js).
//
// The product code is a fresh random one each run (the EAN13_ProductCode lookup requires exactly one
// match, so a fixed code would collide with a still-active row from a previous run) - same reasoning as
// pos_ean13_scan.spec.js. Everything ELSE mirrors the customer's real scale label: prefix 28 (variable
// weight), a 5-digit product code, weight 00482 (= 0,482 kg), 15,50/kg, 7 % tax.
const createMasterdata = () => {
    const code = String(Math.floor(Math.random() * 100000)).padStart(5, '0');
    const { ean13: scaleLabel } = generateEAN13({ prefix: '28', productCode: `${code}00482` });

    return Backend.createMasterdata({
        language: 'de_DE',
        request: {
            login: { user: { language: 'de_DE', firstname: 'Anna', lastname: 'Muster' } },
            products: {
                P1: {
                    name: 'Omas Maultaschen',
                    uom: 'KGM', // stock UOM must be KGM: a KGM price on an EACH-stocked product is rejected.
                    ean13ProductCode: code,
                },
            },
            // The return receives goods into the (single, shared-DB-wide) quality-return warehouse -
            // find-or-create: reuses the one already configured, if any run already created it.
            warehouses: {
                quality: { isQualityReturnWarehouse: true },
            },
            posTerminals: {
                T1: {
                    priceListCurrency: 'EUR',
                    isTaxIncluded: true,
                    // The POS price (and its own 7 % tax rate) lives on the terminal's own price list, not on
                    // the product - the walk-in partner's own pricing/tax is deliberately different, and the
                    // return is always priced+taxed from the till, never the client.
                    products: { P1: { price: 15.50, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight', taxRatePercent: 7 } },
                },
            },
        },
    }).then((masterdata) => ({ ...masterdata, scaleLabel }));
};

const loginAndOpenOrderPanel = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await POSScreen.startFromApplicationsList();
    await POSScreen.selectTerminal({ posTerminalId: masterdata.posTerminals.T1.id });
    await POSScreen.openCashJournal({ openingBalance: 100 });
    await POSOrderPanel.waitForScreen();
};

// noinspection JSUnusedLocalSymbols
test('Partial return of a weighed product pays out cash on the spot', async ({ page }) => {
    // Scan + correct qty + pay out + Backend.expect (backend document assertions) + closing summary.
    test.setTimeout(180_000);

    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000'); // Standalone tag for Tags section;
    allure.story('POS - Partial return of a weighed product, paid out in cash');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await loginAndOpenOrderPanel(masterdata);

    await POSReturnPanel.open();
    await POSReturnPanel.expectPayoutDisabled(); // no lines yet

    // 0,482 kg x 15,50 EUR/kg = 7,471, rounded to 7,47 - the label's own weight, before correction.
    // Till renders sub-1kg catch weight in grams (a separate, known display matter) - match on that part only.
    await POSReturnPanel.scanBarcode(masterdata.scaleLabel);
    await POSReturnPanel.expectLine({ index: 0, productName: masterdata.products.P1.productName, qty: '482 g', amount: '7,47' });

    // The scanned weight differs from what was actually returned - correct it on the keypad.
    await POSReturnPanel.tapLine(0);
    await POSReturnPanel.keyQty({ digits: '0.300' });
    await POSReturnPanel.confirmQty();
    await POSReturnPanel.expectLine({ index: 0, qty: '300 g', amount: '4,65' });
    await POSReturnPanel.expectTotal('4,65');

    await POSReturnPanel.payout();
    const { externalId } = await POSReturnPanel.confirmPayCash();
    await POSReturnPanel.expectSuccess('4,65');

    await Backend.expect({
        pos: {
            externalId,
            posTerminal: 'T1',
            returns: [{ product: 'P1', qty: '0.300 KGM', warehouse: 'quality' }],
            creditMemos: [{ lines: [{ product: 'P1', qty: '0.300 KGM', price: '15.50', taxRate: 7 }] }],
            cashJournalLines: [{ type: 'CASH_INOUT', amount: '-4.65' }],
        },
    });

    await POSReturnPanel.closeSuccess();

    await POSCashJournalModals.openClosing();
    await POSCashJournalModals.expectSummary({ cashOut: '-4,65', cashOutDescription: 'Rücknahme', endingBalance: '95,35' });
});
