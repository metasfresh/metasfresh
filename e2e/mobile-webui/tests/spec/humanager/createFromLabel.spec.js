import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { NewHUPreviewScreen } from '../../utils/screens/huManager/NewHUPreviewScreen';
import { expectErrorToast } from '../../utils/common';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';
import { DISPOSAL_REASON_DAMAGED } from '../../utils/screens/huManager/HUDisposalScreen';
import { uniqueGtin14 } from '../../utils/gtin';
import {
    BRACKETED_LABEL_LOT,
    BRACKETED_LABEL_PRODUCTION_DATE_DISPLAY,
    bracketedLabel,
    FORMAT_BRACKETED_GTIN_PRODDATE_LOT,
    FORMAT_GTIN_LOT_BBD,
    gs1Label,
    gtinLotBbdLabel,
} from '../../utils/huLabels';

// HU Manager: scan a product label that is no existing HU -> "New HU" preview -> OK -> one single-HU
// inventory creates the HU -> a success message naming the HU and the quantity, back on the scan screen (the new HU is not opened;
// the user types the HU number from the message to open it).
//
// Main label format of the tests: the bracketed shape of the real label pictures (see utils/huLabels.js); a test that needs a
// best-before date or another shape says so with its own format (GTIN + lot + best-before, GS1, product code + weight).

// Constant + Product code + Weight + Lot + Production date + Best-before + Ignore
//   e.g. 'XQ' + <4-digit product code> + '012345' (12.345 kg) + 'L2311' + '251101' + '271231' + 'zz'
const FORMAT_PRODUCTCODE_WEIGHT = {
    name: 'my product-code label (HU Manager)',
    parts: [
        { startPosition: 1, endPosition: 2, type: 'CONSTANT', constantValue: 'XQ' },
        { startPosition: 3, endPosition: 6, type: 'PRODUCT_CODE' },
        { startPosition: 7, endPosition: 12, type: 'WEIGHT_KG', decimalPointPosition: 3 },
        { startPosition: 13, endPosition: 17, type: 'LOT' },
        { startPosition: 18, endPosition: 23, type: 'PRODUCTION_DATE', dateFormat: 'yyMMdd' },
        { startPosition: 24, endPosition: 29, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
        { startPosition: 30, endPosition: 31, type: 'IGNORE' },
    ],
};

// products / formats / extra are opt-in: the defaults (one piece product on `gtin`, the bracketed label format) are what the core tests use
const createMasterdata = async ({ gtin, users, workplaces, products, formats, extra } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: users ?? { user: { language: "en_US" } },
            products: products ?? { "P1": { gtin, uom: 'PCE' } },
            warehouses: {
                "wh1": { locators: { wh1_l1: {}, wh1_l2: {} } },
                "wh2": { locators: { wh2_l1: {} } },
            },
            ...(workplaces ? { workplaces } : {}),
            customQRCodeFormats: formats ?? [FORMAT_BRACKETED_GTIN_PRODDATE_LOT],
            ...(extra ?? {}),
        }
    });
};

// AC4: after OK the user is back on the scan screen — a success message naming the HU and the quantity, the scan field ready,
// no HU info screen (the new HU is NOT opened). The message is what tells the user which HU number was created.
const expectBackOnScanScreenAfterOK = async ({ created, qty = '1 PCE', locatorCode }) => await test.step('Expect: back on the scan screen after OK, success message, no HU info', async () => {
    // the HU is booked on the proposed location (the create response's JsonHU.locatorValue)
    if (locatorCode !== undefined) expect(created.locatorValue).toBe(locatorCode);
    await HUManagerScreen.waitForScreen();
    await NewHUPreviewScreen.expectNotDisplayed();
    await ErrorToast.expectSuccessToast({ hasText: [`HU ${created.huValue}`, qty] });
    await BarcodeScannerComponent.expectAttached({});
    await HUManagerScreen.expectNoHUInfo();
});

// The real user path to the new HU afterwards: the user has no label for the new CU, so he types the HU number from the message
// (exactly M_HU.Value, not the display name) in the scanner's manual mode -> the HU info screen of that HU.
const openCreatedHUByValue = async ({ created, locatorCode, qty = '1 PCE' }) => await test.step('Open the created HU by typing its M_HU.Value', async () => {
    await BarcodeScannerComponent.type(created.huValue);
    await HUManagerScreen.waitForHUInfoPanel();
    await HUManagerScreen.expectHUQty(qty);
    await HUManagerScreen.expectHULocator(locatorCode);
    // the standard actions of a scanned HU are offered
    await HUManagerScreen.expectMoveButtonVisible();
});

const expectCreateCallCount = (counter, expected) => expect(counter.count).toBe(expected);

const countCreateCalls = () => NewHUPreviewScreen.countCreateCalls();

const goBackToApplicationsAndLogout = async () => {
    await HUManagerScreen.goBack();
    await ApplicationsListScreen.logout();
};

// noinspection JSUnusedLocalSymbols
test('custom label (GTIN + lot + best-before) -> preview -> OK -> success message, back on the scan screen; the HU opened by its number, Move works', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin, formats: [FORMAT_GTIN_LOT_BBD] });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: gtinLotBbdLabel(gtin) });
    await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
    await NewHUPreviewScreen.expectLotNumber('L2311');
    await NewHUPreviewScreen.expectOKEnabled();
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    const { huId, huQRCode } = created;
    // AC4: success message naming the HU and the quantity, back on the scan screen, the new HU is not opened
    await expectBackOnScanScreenAfterOK({ created, locatorCode });

    await Backend.expect({
        hus: {
            [huId]: {
                huStatus: 'A',
                storages: { 'P1': '1 PCE' },
                cus: [{ qty: '1 PCE', attributes: { 'Lot-Nummer': 'L2311', 'HU_BestBeforeDate': '2027-12-31' } }],
            },
        }
    });

    // Move works on the new HU (opened by typing the HU number from the message)
    await openCreatedHUByValue({ created, locatorCode });
    await HUManagerScreen.move({ qrCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode });
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode });
    await HUManagerScreen.expectHULocator(masterdata.warehouses.wh2.locators.wh2_l1.code);
});

// noinspection JSUnusedLocalSymbols
test('reference case with an invented GS1 label -> preview -> OK -> back on the scan screen; one completed inventory, one CU as previewed', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    // random unique GTIN (not a real article's) so repeated runs never collide
    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin, formats: [FORMAT_GTIN_LOT_BBD] });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });
    const createCalls = countCreateCalls();

    // scan -> preview, everything asserted on screen
    await HUManagerScreen.scanLabelAndWaitForPreview({ label: gs1Label(gtin) });
    await NewHUPreviewScreen.expectProductContains(masterdata.products.P1.productName);
    await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
    await NewHUPreviewScreen.expectLotNumber('L2311');
    // the best-before date of the label (use-by 31.12.2027) is shown on the preview, exactly (en_US display MM/DD/YYYY)
    await NewHUPreviewScreen.expectBestBeforeDate('12/31/2027');
    await NewHUPreviewScreen.expectOKEnabled();
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
    // the preview books nothing
    expectCreateCallCount(createCalls, 0);

    // OK -> success message + back on the scan screen; the HU opened by its number shows quantity + location as previewed
    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    const { huId, huQRCode } = created;
    await expectBackOnScanScreenAfterOK({ created, locatorCode });
    await openCreatedHUByValue({ created, locatorCode }); // quantity + location as previewed
    expectCreateCallCount(createCalls, 1);

    // backend: one CU with product, quantity, values and location as previewed; one completed inventory
    await Backend.expect({
        hus: {
            [huId]: {
                huStatus: 'A',
                storages: { 'P1': '1 PCE' },
                cus: [{ qty: '1 PCE', attributes: { 'Lot-Nummer': 'L2311', 'HU_BestBeforeDate': '2027-12-31' } }],
            },
        },
        inventories: { [huQRCode]: { isExists: true, docStatus: 'CO', count: 1, qtyCount: '1' } },
        products: { 'P1': { huCount: 1 } },
    });
});

// noinspection JSUnusedLocalSymbols
test('reference label shape (bracketed, fixed positions) -> preview -> enter quantity -> OK -> lot + production date on the CU, no HU_QRCode value', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    await NewHUPreviewScreen.expectProductContains(masterdata.products.P1.productName);
    await NewHUPreviewScreen.expectLotNumber(BRACKETED_LABEL_LOT);
    await NewHUPreviewScreen.expectProductionDate(BRACKETED_LABEL_PRODUCTION_DATE_DISPLAY);
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    // the label carries no quantity: the user enters it
    await NewHUPreviewScreen.setQty(7);
    await NewHUPreviewScreen.expectQty({ qty: '7', uom: 'PCE' });
    await NewHUPreviewScreen.expectOKEnabled();

    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    const { huId, huQRCode } = created;
    await expectBackOnScanScreenAfterOK({ created, locatorCode, qty: '7 PCE' });

    // one completed inventory; one CU with the entered quantity, the lot and the production date of the label, on the previewed location;
    // the label code itself is NOT stored as the HU_QRCode attribute
    await Backend.expect({
        hus: {
            [huId]: {
                huStatus: 'A',
                storages: { 'P1': '7 PCE' },
                cus: [{
                    qty: '7 PCE',
                    // null = the attribute carries no value (the attribute row exists on every HU; AC27: the label code is not written to it)
                    attributes: { 'Lot-Nummer': BRACKETED_LABEL_LOT, 'ProductionDate': '2027-03-15', 'HU_QRCode': null },
                }],
            },
        },
        inventories: { [huQRCode]: { isExists: true, docStatus: 'CO', count: 1, qtyCount: '7' } },
        products: { 'P1': { huCount: 1 } },
    });
});

// noinspection JSUnusedLocalSymbols
test('GS1 label -> preview -> OK -> CU with lot + best-before (= use-by)', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin, formats: [FORMAT_GTIN_LOT_BBD] });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: gs1Label(gtin) });
    await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    const { huId } = created;
    await expectBackOnScanScreenAfterOK({ created, locatorCode });
    await Backend.expect({
        hus: {
            [huId]: {
                huStatus: 'A',
                storages: { 'P1': '1 PCE' },
                cus: [{ qty: '1 PCE', attributes: { 'Lot-Nummer': 'L2311', 'HU_BestBeforeDate': '2027-12-31' } }],
            },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('preview -> Cancel -> nothing booked', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    const createCalls = countCreateCalls();
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    await NewHUPreviewScreen.clickCancel();

    // back on the scan step: no preview, no HU info, nothing was sent to the create endpoint
    await HUManagerScreen.waitForScreen();
    await NewHUPreviewScreen.expectNotDisplayed();
    await HUManagerScreen.expectNoHUInfo();
    expectCreateCallCount(createCalls, 0);

    // the scan step is ready again: the same label gives the preview again
    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    expectCreateCallCount(createCalls, 0);
});

// noinspection JSUnusedLocalSymbols
test('phone viewport: a long product name wraps instead of clipping the values; OK is visible without scrolling', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const longGtin = uniqueGtin14();
    const shortGtin = uniqueGtin14();
    const longName = 'Invented-article-with-an-extremely-long-unbroken-product-name-ABCDEFGHIJKLMNOPQRSTUVWXYZ-0123456789';
    const masterdata = await createMasterdata({
        gtin: longGtin,
        products: { "P1": { gtin: longGtin, uom: 'PCE', name: longName }, "P2": { gtin: shortGtin, uom: 'PCE' } },
    });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(longGtin) });
    await NewHUPreviewScreen.expectLocatorProposedAndGet();
    await NewHUPreviewScreen.expectValuesNotClipped();
    await NewHUPreviewScreen.clickCancel();
    await HUManagerScreen.waitForScreen();

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(shortGtin) });
    await NewHUPreviewScreen.expectLocatorProposedAndGet();
    await NewHUPreviewScreen.expectValuesNotClipped();
    await NewHUPreviewScreen.expectOKWithoutScrolling();
    await NewHUPreviewScreen.clickCancel();
    await HUManagerScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('location from the user workplace — pick-from location, else the default location of the workplace warehouse', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        gtin,
        users: {
            user1: { language: "en_US", workplace: "workplace1" },
            user2: { language: "en_US", workplace: "workplace2" },
        },
        workplaces: {
            workplace1: { warehouse: 'wh1', pickFromLocator: 'wh1_l2' },
            workplace2: { warehouse: 'wh2' },
        },
    });

    await test.step('(a) workplace with a pick-from location', async () => {
        await HUManagerScreen.openAfterLogin({ user: masterdata.login.user1 });
        await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
        await NewHUPreviewScreen.expectLocator(masterdata.warehouses.wh1.locators.wh1_l2.code);
        await NewHUPreviewScreen.clickCancel();
        await HUManagerScreen.waitForScreen();
        await goBackToApplicationsAndLogout();
    });

    await test.step('(b) workplace without a pick-from location -> default location of its warehouse', async () => {
        await HUManagerScreen.openAfterLogin({ user: masterdata.login.user2 });
        await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
        await NewHUPreviewScreen.expectLocator(masterdata.warehouses.wh2.locators.wh2_l1.code);
        await NewHUPreviewScreen.clickCancel();
    });
});

// noinspection JSUnusedLocalSymbols
test('location changed with the Change locator button, not remembered; a non-location code in the dialog is refused, Cancel keeps the location', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        gtin,
        users: { user: { language: "en_US", workplace: "workplace1" } },
        workplaces: { workplace1: { warehouse: 'wh1', pickFromLocator: 'wh1_l1' } },
    });
    const wh1_l1 = masterdata.warehouses.wh1.locators.wh1_l1;
    const wh2_l1 = masterdata.warehouses.wh2.locators.wh2_l1;
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    await NewHUPreviewScreen.expectLocator(wh1_l1.code);

    await NewHUPreviewScreen.expectNoScanner();

    // (e) a product label scanned in the dialog -> refused with an error toast, dialog stays open; Cancel keeps the location
    await NewHUPreviewScreen.openChangeLocatorDialog();
    await expectErrorToast('a product label is no location', async () => await NewHUPreviewScreen.scanInChangeLocatorDialog(bracketedLabel(gtin)), ({ textContent }) => {
        expect(textContent).toMatch(/Not found/); // the server's answer of the locator lookup, not any other error (e.g. a 500)
    });
    await NewHUPreviewScreen.cancelChangeLocatorDialog();
    await NewHUPreviewScreen.expectLocator(wh1_l1.code);

    // (c) Change locator + another location QR code -> shown at once; OK books there
    await NewHUPreviewScreen.changeLocator(wh2_l1.qrCode);
    await NewHUPreviewScreen.expectLocator(wh2_l1.code);
    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    await expectBackOnScanScreenAfterOK({ created });
    await openCreatedHUByValue({ created, locatorCode: wh2_l1.code }); // booked where it was scanned

    // (d) the next label shows the workplace location again, not the one scanned before
    await HUManagerScreen.clickScanAgain();
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    await NewHUPreviewScreen.expectLocator(wh1_l1.code);
});

test('inline quantity on the preview: typed digits stay in the field, emptied field disables OK, quantity + Change locator -> HU on the scanned location with the typed quantity', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        gtin,
        users: { user: { language: "en_US", workplace: "workplace1" } },
        workplaces: { workplace1: { warehouse: 'wh1', pickFromLocator: 'wh1_l1' } },
    });
    const wh1_l1 = masterdata.warehouses.wh1.locators.wh1_l1;
    const wh2_l1 = masterdata.warehouses.wh2.locators.wh2_l1;
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });
    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });

    await test.step('(i) typed digits stay in the quantity field (the preview has no scanner)', async () => {
        await NewHUPreviewScreen.expectNoScanner();
        await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
        await NewHUPreviewScreen.setQty(7);
        await NewHUPreviewScreen.expectQty({ qty: '7', uom: 'PCE' });
        await ErrorToast.expectNoErrorToast();
        await NewHUPreviewScreen.expectLocator(wh1_l1.code);
        await NewHUPreviewScreen.expectOKEnabled();
    });

    await test.step('(ii) quantity field emptied -> OK disabled; typed again -> enabled', async () => {
        await NewHUPreviewScreen.clearQty();
        await NewHUPreviewScreen.expectOKDisabled();
        await NewHUPreviewScreen.setQty(5);
        await NewHUPreviewScreen.expectOKEnabled();
    });

    await test.step('(iii) quantity typed, Change locator, location scanned -> the location changes, the quantity stays', async () => {
        await NewHUPreviewScreen.setQty(3);
        await NewHUPreviewScreen.changeLocator(wh2_l1.qrCode);
        await NewHUPreviewScreen.expectLocator(wh2_l1.code);
        await NewHUPreviewScreen.expectQty({ qty: '3', uom: 'PCE' });
    });

    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    await expectBackOnScanScreenAfterOK({ created, qty: '3 PCE' });
    await Backend.expect({ hus: { [created.huId]: { huStatus: 'A', storages: { 'P1': '3 PCE' } } } });
    await openCreatedHUByValue({ created, locatorCode: wh2_l1.code, qty: '3 PCE' }); // booked on the scanned location, typed quantity
});

// noinspection JSUnusedLocalSymbols
test('double tap on OK books one inventory', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    const createCalls = countCreateCalls();
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
    const created = await NewHUPreviewScreen.doubleClickOKAndGetCreatedHU();
    await expectBackOnScanScreenAfterOK({ created, locatorCode });

    expectCreateCallCount(createCalls, 1);
    await Backend.expect({
        hus: {
            [created.huId]: {
                huStatus: 'A',
                storages: { 'P1': '1 PCE' },
                cus: [{ qty: '1 PCE' }],
            },
        },
        inventories: {
            [created.huQRCode]: { isExists: true, docStatus: 'CO', count: 1 },
        },
    });
});

const expectRejection = async ({ title, label, expectedText }) => await test.step(`Rejection: ${title}`, async () => {
    await expectErrorToast(title, async () => {
        await HUManagerScreen.scanLabel({ label });
    }, ({ textContent }) => {
        expect(textContent).toMatch(expectedText);
    });
    // exactly one message, no preview, scan step ready again
    await NewHUPreviewScreen.expectNotDisplayed();
    await HUManagerScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('catch-weight product, label with constant + product code + weight + lot + production date + best-before + ignore', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const masterdata = await createMasterdata({
        products: {
            "P1": {
                randomValue: { size: 4, isIncludeDigits: true },
                uom: 'PCE',
                uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
            },
        },
        formats: [FORMAT_PRODUCTCODE_WEIGHT],
    });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    const label = `XQ${masterdata.products.P1.productCode}012345L2311251101271231zz`;
    await HUManagerScreen.scanLabelAndWaitForPreview({ label });
    await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
    await NewHUPreviewScreen.expectWeight('12.345 KGM');
    await NewHUPreviewScreen.expectLotNumber('L2311');
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    const { huId, huQRCode } = created;
    await expectBackOnScanScreenAfterOK({ created, locatorCode });
    // The weight proof: WeightNet is 12.345 on the HU and the CU (not the nominal 0.1 of the PCE->KGM rate, not 0), and the
    // catch-weight inventory completed. The lot, production date and best-before date arrive through the inventory line's
    // attributes (inventory ASI) and are inherited by the CU when the inventory completes. The weight is set on the CU
    // (PackedHUWeightNetUpdater runs in its own HU context).
    await Backend.expect({
        hus: {
            [huId]: {
                huStatus: 'A',
                storages: { 'P1': '1 PCE' },
                attributes: { 'WeightNet': '12.345' },
                cus: [{ qty: '1 PCE', attributes: { 'WeightNet': '12.345', 'Lot-Nummer': 'L2311', 'ProductionDate': '2025-11-01', 'HU_BestBeforeDate': '2027-12-31' } }],
            },
        },
        inventories: {
            [huQRCode]: {
                docStatus: 'CO',
                // the AD_Message `...createFromLabel.InventoryDescription` in the E2E user's language (en_US, see createMasterdata)
                description: 'HU Manager: created from scanned label',
                count: 1,
                qtyCount: '1',
                lineAttributes: { 'Lot-Nummer': 'L2311', 'ProductionDate': '2025-11-01', 'HU_BestBeforeDate': '2027-12-31' },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('GS1 label with weight (310n) — kg product: quantity = weight; piece product: quantity 1, weight "not used"', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtinKg = uniqueGtin14();
    const gtinPce = uniqueGtin14();
    const masterdata = await createMasterdata({
        products: {
            "P1": { gtin: gtinKg, uom: 'KGM' },
            "P2": { gtin: gtinPce, uom: 'PCE' },
        },
    });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await test.step('product stocked in kg: quantity = label weight', async () => {
        await HUManagerScreen.scanLabelAndWaitForPreview({ label: `01${gtinKg}3103012345` });
        await NewHUPreviewScreen.expectQty({ qty: '12.345', uom: 'KGM' });
        const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
        const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
        const { huId } = created;
        await expectBackOnScanScreenAfterOK({ created, qty: '12.345 KGM', locatorCode });
        await Backend.expect({ hus: { [huId]: { huStatus: 'A', storages: { 'P1': '12.345 KGM' } } } });
    });

    await test.step('piece product: quantity 1, weight shown as not used and not stored', async () => {
        await HUManagerScreen.scanLabelAndWaitForPreview({ label: `01${gtinPce}3103012345` });
        await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
        await NewHUPreviewScreen.expectWeightNotUsed();
        const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
        const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
        const { huId } = created;
        await expectBackOnScanScreenAfterOK({ created, locatorCode });
        await Backend.expect({ hus: { [huId]: { huStatus: 'A', storages: { 'P2': '1 PCE' }, attributes: { 'WeightNet': '0.000' } } } }); // the HU carries the default WeightNet 0 (a piece product / this HU profile has the attribute row; not assumed for every product) — NOT the label's 12.345
    });
});

// noinspection JSUnusedLocalSymbols
test('rejections show one message each, book nothing; quantity 0 disables OK', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const unknownGtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        gtin,
        extra: {
            packingInstructions: { "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 } },
            generatedHUQRCodes: { g1: { packingInstructions: 'LU', product: 'P1' } },
        },
    });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await expectRejection({ title: 'label not recognized', label: 'hello', expectedText: /Label not recognized/ });
    await expectRejection({ title: 'GTIN of no product', label: bracketedLabel(unknownGtin), expectedText: new RegExp(`No product found for GTIN ${unknownGtin}`) });
    await expectRejection({ title: 'GS1 label without GTIN', label: '10L2311', expectedText: /does not identify a product/ });
    await expectRejection({ title: 'mistyped HU number reads as GS1 lot', label: `10${Date.now()}`, expectedText: /does not identify a product/ });
    await expectRejection({ title: 'unassigned HU QR code', label: masterdata.generatedHUQRCodes.g1.qrCode, expectedText: /Not a product label/ });
    await expectRejection({ title: 'locator (shelf) QR code', label: masterdata.warehouses.wh1.locators.wh1_l1.qrCode, expectedText: /invalid/i });
    await expectRejection({ title: 'LM code', label: 'LMQ#1#25.5#31.12.2025#L2311', expectedText: /invalid/i });
    await expectRejection({ title: 'pick-on-the-fly code', label: 'PICK_ON_THE_FLY', expectedText: /Not a product label/ });

    await test.step('quantity 0 -> OK disabled', async () => {
        await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
        await NewHUPreviewScreen.setQty(0);
        await NewHUPreviewScreen.expectOKDisabled();
        await NewHUPreviewScreen.clickCancel();
    });

    // nothing was booked by any of the above
    await Backend.expect({ products: { 'P1': { huCount: 0 } } });
});

// noinspection JSUnusedLocalSymbols
test('a label whose constant part does not match is not recognized', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        products: { "P1": { randomValue: { size: 4, isIncludeDigits: true }, uom: 'PCE' } },
        formats: [FORMAT_PRODUCTCODE_WEIGHT],
    });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await expectRejection({
        title: 'constant part mismatch',
        label: `ZZ${masterdata.products.P1.productCode}012345L2311251101271231zz`,
        expectedText: /Label not recognized/,
    });
    // the product-code part matches the format, but no product has this code: "product not found", not "label not recognized"
    await expectRejection({
        title: 'product code of no product',
        label: 'XQZZZZ012345L2311251101271231zz',
        expectedText: /No product found for product code ZZZZ/,
    });
    await Backend.expect({ products: { 'P1': { huCount: 0 } } });
});

// noinspection JSUnusedLocalSymbols
test('the created HU opened by its number has no Undo; standard Dispose flags the HU, the HU stays on hand', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    const { huId, huQRCode } = created;
    await expectBackOnScanScreenAfterOK({ created, locatorCode });
    // the user has no label for the new CU: he types the HU number from the success message in the scanner's manual mode
    await openCreatedHUByValue({ created, locatorCode });
    await HUManagerScreen.expectUndoButtonMissing();

    await HUManagerScreen.dispose({ reason: DISPOSAL_REASON_DAMAGED });

    // Dispose is only a "please remove" flag: one candidate for the whole HU, the HU itself unchanged
    await Backend.expect({
        inventoryCandidates: { [huQRCode]: { count: 1, qtyToDispose: '1', isWholeHU: true } },
        hus: { [huId]: { huStatus: 'A', storages: { 'P1': '1 PCE' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('the same label twice -> two previews alike, two HUs, two completed inventories, no note', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    let locatorCode;
    const created = [];
    for (let i = 1; i <= 2; i++) {
        await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
        await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
        // the user has no workplace -> the organisation's default location; the second preview must look like the first
        if (i === 1) locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
        await NewHUPreviewScreen.expectLocator(locatorCode);
        await NewHUPreviewScreen.expectLotNumber(BRACKETED_LABEL_LOT);
        // no note or warning of any kind (the success toast of the previous HU is no note)
        await ErrorToast.expectNoToastOtherThanSuccess();
        if (i === 2) await ErrorToast.expectNoSuccessToastWithoutText(`HU ${created[0].huValue}`); // the only toast left is the previous HU's success message
        created.push(await NewHUPreviewScreen.clickOKAndGetCreatedHU());
        await expectBackOnScanScreenAfterOK({ created: created[i - 1], locatorCode }); // straight back on the scan screen for the next label
    }

    expect(created[0].huId).not.toBe(created[1].huId);
    await Backend.expect({
        hus: {
            [created[0].huId]: { huStatus: 'A', storages: { 'P1': '1 PCE' } },
            [created[1].huId]: { huStatus: 'A', storages: { 'P1': '1 PCE' } },
        },
        inventories: {
            [created[0].huQRCode]: { isExists: true, docStatus: 'CO', count: 1, qtyCount: '1' },
            [created[1].huQRCode]: { isExists: true, docStatus: 'CO', count: 1, qtyCount: '1' },
        },
        products: { 'P1': { huCount: 2 } },
    });
});

// noinspection JSUnusedLocalSymbols
test('one barcode on two products -> one message naming both, no preview, nothing booked', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        products: {
            "P1": { gtin, uom: 'PCE' },
            "P2": { gtin, uom: 'PCE' },
        },
    });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    const p1 = masterdata.products.P1.productCode;
    const p2 = masterdata.products.P2.productCode;
    await expectRejection({
        title: 'barcode fits several products',
        label: bracketedLabel(gtin),
        expectedText: new RegExp(`Barcode ${gtin} fits articles .*${p1}.*${p2}|Barcode ${gtin} fits articles .*${p2}.*${p1}`),
    });

    // the scan field is ready again: the next scan is processed
    await expectRejection({ title: 'next scan processed', label: 'hello', expectedText: /Label not recognized/ });

    await Backend.expect({
        products: {
            'P1': { huCount: 0 },
            'P2': { huCount: 0 },
        },
    });
});

// A role whose HU Manager access does NOT allow all actions (Mobile_Application_Access.IsAllowAllActions = N).
// includedRoles: [] — the default WebUI role would add its allow-all HU Manager access (permissions of included roles are merged).
const restrictedHUManagerRole = (grantedActions) => ({
    "restricted": {
        includedRoles: [],
        mobileApplicationAccess: [{ application: 'huManager', isAllowAllActions: false, ...(grantedActions ? { grantedActions } : {}) }],
    },
});

const countProposeAndCreateCalls = () => NewHUPreviewScreen.countProposeAndCreateCalls();

// noinspection JSUnusedLocalSymbols
test('role without allow-all and without the createHU grant -> product label gives today\'s not found, no preview, nothing booked', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        gtin,
        users: { user: { language: 'en_US', role: 'restricted' } },
        extra: { roles: restrictedHUManagerRole() },
    });
    const calls = countProposeAndCreateCalls();
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    // today's not-found error: the HU lookup answers 404 (positive proof the HU lookup itself works for this role) and the app shows
    // its generic error toast ("Please try again ... (Trace: ...)": the app shows the raw axios text only when the
    // mobileui.frontend.showAllErrorMessages SysConfig is Y), never the label messages of the new feature
    const huLookup = HUManagerScreen.watchHULookup();
    await expectErrorToast('product label without createHU', async () => {
        await HUManagerScreen.scanLabel({ label: bracketedLabel(gtin) });
    }, ({ textContent }) => {
        expect(textContent).toMatch(/Please try again/);
        expect(textContent).not.toMatch(/Label not recognized|No product found|does not identify a product|Not a product label/);
    });
    expect(await huLookup.getStatus()).toBe(404);
    await NewHUPreviewScreen.expectNotDisplayed();
    await HUManagerScreen.waitForScreen();
    expect(calls.count).toBe(0); // neither propose nor create was even called: the app gate (HUManagerScreen.jsx) holds

    await Backend.expect({ products: { 'P1': { huCount: 0 } } });
});

// noinspection JSUnusedLocalSymbols
test('role without allow-all, only createHU granted -> product label opens the preview; Cancel books nothing', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({
        gtin,
        users: { user: { language: 'en_US', role: 'restricted' } },
        extra: { roles: restrictedHUManagerRole(['createHU']) },
    });
    const createCalls = countCreateCalls();
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });

    await HUManagerScreen.scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    await NewHUPreviewScreen.expectQty({ qty: '1', uom: 'PCE' });
    await NewHUPreviewScreen.clickCancel();

    await HUManagerScreen.waitForScreen();
    expectCreateCallCount(createCalls, 0);
    await Backend.expect({ products: { 'P1': { huCount: 0 } } });
});
