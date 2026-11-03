import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { NewHUPreviewScreen } from '../../utils/screens/huManager/NewHUPreviewScreen';
import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../utils/common';
import { DISPOSAL_REASON_DAMAGED } from '../../utils/screens/huManager/HUDisposalScreen';

// HU Manager: scan a product label that is no existing HU -> "New HU" preview -> OK -> one single-HU
// inventory creates the HU -> a success message naming the HU and the quantity, back on the scan screen (the new HU is not opened;
// the user types the HU number from the message to open it).
//
// Label format (custom, no product-code part): GTIN 1-14, LOT 15-19, BEST_BEFORE_DATE 20-25 (yyMMdd).
//   e.g. '02001234567893' + 'L2311' + '271231'
// The product's stored GTIN is the 14-digit form (the customer-data shape: leading 0 + 13-digit EAN).

// Unique per run (the label lookup must find exactly one product; the DB keeps products of earlier runs).
let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `7${seq}${ts}`; // 1 + 2 + 11 = 14 digits
};
const customLabel = (gtin) => `${gtin}L2311271231`;
// GS1: (01) GTIN-14, (17) use-by yyMMdd, (10) lot — the shape of an INVENTED placeholder label (not a real customer label)
const gs1Label = (gtin) => `01${gtin}1727123110L2311`;

const FORMAT_GTIN_LOT_BBD = {
    name: 'my gtin label (HU Manager)',
    parts: [
        { startPosition: 1, endPosition: 14, type: 'GTIN' },
        { startPosition: 15, endPosition: 19, type: 'LOT' },
        { startPosition: 20, endPosition: 25, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
    ],
};

// Fixed-position format of a bracketed label "(01)<GTIN-14>(11)<yyMMdd production date>(10)<6-character lot>" (38 characters; no best-before date).
// Only the shape is the shape of a real customer label; the code values of the test are INVENTED.
const FORMAT_BRACKETED_GTIN_PRODDATE_LOT = {
    name: 'my bracketed label (HU Manager)',
    parts: [
        { startPosition: 1, endPosition: 4, type: 'CONSTANT', constantValue: '(01)' },
        { startPosition: 5, endPosition: 18, type: 'GTIN' },
        { startPosition: 19, endPosition: 22, type: 'CONSTANT', constantValue: '(11)' },
        { startPosition: 23, endPosition: 28, type: 'PRODUCTION_DATE', dateFormat: 'yyMMdd' },
        { startPosition: 29, endPosition: 32, type: 'CONSTANT', constantValue: '(10)' },
        { startPosition: 33, endPosition: 38, type: 'LOT' },
    ],
};
const bracketedLabel = (gtin) => `(01)${gtin}(11)270315(10)481732`;

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

// products / formats / extra are opt-in: the defaults (one piece product on `gtin`, the GTIN+lot+best-before format) are what the core tests use
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
            customQRCodeFormats: formats ?? [FORMAT_GTIN_LOT_BBD],
            ...(extra ?? {}),
        }
    });
};

const openHUManager = async ({ user }) => {
    await LoginScreen.login(user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
};

const scanLabelAndWaitForPreview = async ({ label }) => {
    await HUManagerScreen.scanLabel({ label });
    await NewHUPreviewScreen.waitForScreen();
};

// Presses OK and returns the new HU as the server answered it (same JSON as scanning an existing HU: id + qrCode)
const clickOKAndGetCreatedHU = async () => {
    const [response] = await Promise.all([
        page.waitForResponse((r) => r.url().includes('createFromScannedCode') && r.request().method() === 'POST', { timeout: SLOW_ACTION_TIMEOUT }),
        NewHUPreviewScreen.clickOK(),
    ]);
    const hu = (await response.json()).result; // JsonGetSingleHUResponse { result: JsonHU }
    // displayName = M_HU.Value + the packing instruction in brackets (HUDisplayNameBuilder.build()); the toast shows the display name
    return { huId: hu.id, huQRCode: hu.qrCode?.code, huValue: hu.displayName.split(' ')[0], displayName: hu.displayName, locatorValue: hu.locatorValue };
};

// AC4: after OK the user is back on the scan screen — a success message naming the HU and the quantity, the scan field ready,
// no HU info screen (the new HU is NOT opened). The message is what tells the user which HU number was created.
const expectBackOnScanScreenAfterOK = async ({ created, qty = '1 PCE', locatorCode }) => await test.step('Expect: back on the scan screen after OK, success message, no HU info', async () => {
    // the HU is booked on the proposed location (the create response's JsonHU.locatorValue)
    if (locatorCode !== undefined) expect(created.locatorValue).toBe(locatorCode);
    await HUManagerScreen.waitForScreen();
    await NewHUPreviewScreen.expectNotDisplayed();
    await expect(page.locator('.Toastify__toast--success').filter({ hasText: `HU ${created.huValue}` }).filter({ hasText: qty }).first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await BarcodeScannerComponent.expectAttached({});
    await HUManagerScreen.expectValueMissing({ name: 'huinfo-table' });
    await HUManagerScreen.expectValueMissing({ name: 'qty-value' });
});

// The real user path to the new HU afterwards: the user has no label for the new CU, so he types the HU number from the message
// (exactly M_HU.Value, not the display name) in the scanner's manual mode -> the HU info screen of that HU.
const openCreatedHUByValue = async ({ created, locatorCode, qty = '1 PCE' }) => await test.step('Open the created HU by typing its M_HU.Value', async () => {
    await BarcodeScannerComponent.type(created.huValue);
    await HUManagerScreen.waitForHUInfoPanel();
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: qty });
    await HUManagerScreen.expectValue({ name: 'locator-value', expectedValue: locatorCode });
    // the standard actions of a scanned HU are offered
    await HUManagerScreen.expectButtonVisible({ testId: 'move-button' });
});

const expectCreateCallCount = (counter, expected) => expect(counter.count).toBe(expected);

/** counts the POSTs to the given endpoints (url fragments) from now on */
const countPostCalls = (...urlParts) => {
    const counter = { count: 0 };
    page.on('request', (r) => {
        if (r.method() === 'POST' && urlParts.some((urlPart) => r.url().includes(urlPart))) counter.count++;
    });
    return counter;
};
const countCreateCalls = () => countPostCalls('createFromScannedCode');

const goBackToApplicationsAndLogout = async () => {
    await page.locator(ID_BACK_BUTTON).tap();
    await ApplicationsListScreen.logout();
};

// noinspection JSUnusedLocalSymbols
test('custom label (GTIN + lot + best-before) -> preview -> OK -> success message, back on the scan screen; the HU opened by its number, Move works', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
    await NewHUPreviewScreen.expectValue({ name: 'attribute-Lot-Nummer-value', expectedValue: 'L2311' });
    await NewHUPreviewScreen.expectOKEnabled();
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    const created = await clickOKAndGetCreatedHU();
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
    await HUManagerScreen.expectValue({ name: 'locator-value', expectedValue: masterdata.warehouses.wh2.locators.wh2_l1.code });
});

// noinspection JSUnusedLocalSymbols
test('reference case with an invented GS1 label -> preview -> OK -> back on the scan screen; one completed inventory, one CU as previewed', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    // random unique GTIN (not a real article's) so repeated runs never collide
    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await openHUManager({ user: masterdata.login.user });
    const createCalls = countCreateCalls();

    // scan -> preview, everything asserted on screen
    await scanLabelAndWaitForPreview({ label: gs1Label(gtin) });
    await expect(page.getByTestId('product-value')).toContainText(masterdata.products.P1.productName);
    await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
    await NewHUPreviewScreen.expectValue({ name: 'attribute-Lot-Nummer-value', expectedValue: 'L2311' });
    // the best-before date of the label (use-by 31.12.2027) is shown on the preview
    await expect(page.getByTestId('attribute-HU_BestBeforeDate-value')).toContainText('2027');
    await NewHUPreviewScreen.expectOKEnabled();
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
    // the preview books nothing
    expectCreateCallCount(createCalls, 0);

    // OK -> success message + back on the scan screen; the HU opened by its number shows quantity + location as previewed
    const created = await clickOKAndGetCreatedHU();
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
        inventories: { [huQRCode]: { isExists: true, docStatus: 'CO', count: 1 } },
        products: { 'P1': { huStorageCount: 1, inventoryLineCount: 1 } },
    });
});

// noinspection JSUnusedLocalSymbols
test('reference label shape (bracketed, fixed positions) -> preview -> enter quantity -> OK -> lot + production date on the CU, no HU_QRCode value', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin, formats: [FORMAT_BRACKETED_GTIN_PRODDATE_LOT] });
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: bracketedLabel(gtin) });
    await expect(page.getByTestId('product-value')).toContainText(masterdata.products.P1.productName);
    await NewHUPreviewScreen.expectValue({ name: 'attribute-Lot-Nummer-value', expectedValue: '481732' });
    await expect(page.getByTestId('attribute-ProductionDate-value')).toContainText('2027');
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    // the label carries no quantity: the user enters it
    await NewHUPreviewScreen.setQty(7);
    await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '7 PCE' });
    await NewHUPreviewScreen.expectOKEnabled();

    const created = await clickOKAndGetCreatedHU();
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
                    attributes: { 'Lot-Nummer': '481732', 'ProductionDate': '2027-03-15', 'HU_QRCode': null },
                }],
            },
        },
        inventories: { [huQRCode]: { isExists: true, docStatus: 'CO', count: 1, qtyCount: '7' } },
        products: { 'P1': { huStorageCount: 1, inventoryLineCount: 1 } },
    });
});

// noinspection JSUnusedLocalSymbols
test('GS1 label -> preview -> OK -> CU with lot + best-before (= use-by)', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('critical');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: gs1Label(gtin) });
    await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    const created = await clickOKAndGetCreatedHU();
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
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    await NewHUPreviewScreen.clickCancel();

    // back on the scan step: no preview, no HU info, nothing was sent to the create endpoint
    await HUManagerScreen.waitForScreen();
    await NewHUPreviewScreen.expectNotDisplayed();
    await HUManagerScreen.expectValueMissing({ name: 'qty-value' });
    expectCreateCallCount(createCalls, 0);

    // the scan step is ready again: the same label gives the preview again
    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    expectCreateCallCount(createCalls, 0);
});

// noinspection JSUnusedLocalSymbols
test('phone viewport: a long product name wraps instead of clipping the values; OK is above the scanner and visible without scrolling', async ({ page }) => {
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
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(longGtin) });
    await NewHUPreviewScreen.expectLocatorProposedAndGet();
    await NewHUPreviewScreen.expectValuesNotClipped();
    await NewHUPreviewScreen.clickCancel();
    await HUManagerScreen.waitForScreen();

    await scanLabelAndWaitForPreview({ label: customLabel(shortGtin) });
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
        await openHUManager({ user: masterdata.login.user1 });
        await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
        await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: masterdata.warehouses.wh1.locators.wh1_l2.code });
        await NewHUPreviewScreen.clickCancel();
        await HUManagerScreen.waitForScreen();
        await goBackToApplicationsAndLogout();
    });

    await test.step('(b) workplace without a pick-from location -> default location of its warehouse', async () => {
        await openHUManager({ user: masterdata.login.user2 });
        await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
        await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: masterdata.warehouses.wh2.locators.wh2_l1.code });
        await NewHUPreviewScreen.clickCancel();
    });
});

// noinspection JSUnusedLocalSymbols
test('location scanned on the preview (no button), not remembered; a product label on the preview is refused', async ({ page }) => {
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
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: wh1_l1.code });

    // (e) a product label scanned on the preview -> refused with an error toast, preview unchanged
    await NewHUPreviewScreen.scan(customLabel(gtin));
    await expect(page.locator('.Toastify__toast--error').filter({ hasText: 'Scan a locator or press OK' }).first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await NewHUPreviewScreen.waitForScreen();
    await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: wh1_l1.code });

    // (c) another location QR code -> shown at once, no button; OK books there
    await NewHUPreviewScreen.scan(wh2_l1.qrCode);
    await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: wh2_l1.code });
    const created = await clickOKAndGetCreatedHU();
    await expectBackOnScanScreenAfterOK({ created });
    await openCreatedHUByValue({ created, locatorCode: wh2_l1.code }); // booked where it was scanned

    // (d) the next label shows the workplace location again, not the one scanned before
    await HUManagerScreen.clickButton({ testId: 'scan-again-button' });
    await HUManagerScreen.waitForScreen();
    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: wh1_l1.code });
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
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
    const created = page.waitForResponse((r) => r.url().includes('createFromScannedCode') && r.request().method() === 'POST', { timeout: SLOW_ACTION_TIMEOUT });
    await NewHUPreviewScreen.okButton().dblclick();
    const hu = (await (await created).json()).result; // JsonGetSingleHUResponse { result: JsonHU }
    await expectBackOnScanScreenAfterOK({ created: { huValue: hu.displayName.split(' ')[0], locatorValue: hu.locatorValue }, locatorCode });

    expectCreateCallCount(createCalls, 1);
    await Backend.expect({
        hus: {
            [hu.id]: {
                huStatus: 'A',
                storages: { 'P1': '1 PCE' },
                cus: [{ qty: '1 PCE' }],
            },
        },
        inventories: {
            [hu.qrCode.code]: { isExists: true, docStatus: 'CO', count: 1 },
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
    await openHUManager({ user: masterdata.login.user });

    const label = `XQ${masterdata.products.P1.productCode}012345L2311251101271231zz`;
    await scanLabelAndWaitForPreview({ label });
    await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
    await NewHUPreviewScreen.expectValue({ name: 'weight-value', expectedValue: '12.345 KGM' });
    await NewHUPreviewScreen.expectValue({ name: 'attribute-Lot-Nummer-value', expectedValue: 'L2311' });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();

    const created = await clickOKAndGetCreatedHU();
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
    await openHUManager({ user: masterdata.login.user });

    await test.step('product stocked in kg: quantity = label weight', async () => {
        await scanLabelAndWaitForPreview({ label: `01${gtinKg}3103012345` });
        await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '12.345 KGM' });
        const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
        const created = await clickOKAndGetCreatedHU();
        const { huId } = created;
        await expectBackOnScanScreenAfterOK({ created, qty: '12.345 KGM', locatorCode });
        await Backend.expect({ hus: { [huId]: { huStatus: 'A', storages: { 'P1': '12.345 KGM' } } } });
    });

    await test.step('piece product: quantity 1, weight shown as not used and not stored', async () => {
        await scanLabelAndWaitForPreview({ label: `01${gtinPce}3103012345` });
        await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
        await NewHUPreviewScreen.expectValue({ name: 'weight-value', expectedValue: 'The weight on the label is not used for this product' });
        const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
        const created = await clickOKAndGetCreatedHU();
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
    await openHUManager({ user: masterdata.login.user });

    await expectRejection({ title: 'label not recognized', label: 'hello', expectedText: /Label not recognized/ });
    await expectRejection({ title: 'GTIN of no product', label: customLabel(unknownGtin), expectedText: new RegExp(`No product found for GTIN ${unknownGtin}`) });
    await expectRejection({ title: 'GS1 label without GTIN', label: '10L2311', expectedText: /does not identify a product/ });
    await expectRejection({ title: 'mistyped HU number reads as GS1 lot', label: `10${Date.now()}`, expectedText: /does not identify a product/ });
    await expectRejection({ title: 'unassigned HU QR code', label: masterdata.generatedHUQRCodes.g1.qrCode, expectedText: /Not a product label/ });
    await expectRejection({ title: 'locator (shelf) QR code', label: masterdata.warehouses.wh1.locators.wh1_l1.qrCode, expectedText: /invalid/i });
    await expectRejection({ title: 'LM code', label: 'LMQ#1#25.5#31.12.2025#L2311', expectedText: /invalid/i });
    await expectRejection({ title: 'pick-on-the-fly code', label: 'PICK_ON_THE_FLY', expectedText: /Not a product label/ });

    await test.step('quantity 0 -> OK disabled', async () => {
        await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
        await NewHUPreviewScreen.setQty(0);
        await NewHUPreviewScreen.expectOKDisabled();
        await NewHUPreviewScreen.clickCancel();
    });

    // nothing was booked by any of the above
    await Backend.expect({ products: { 'P1': { huStorageCount: 0, inventoryLineCount: 0 } } });
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
    await openHUManager({ user: masterdata.login.user });

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
    await Backend.expect({ products: { 'P1': { huStorageCount: 0, inventoryLineCount: 0 } } });
});

// noinspection JSUnusedLocalSymbols
test('the created HU opened by its number has no Undo; standard Dispose flags the HU, the HU stays on hand', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - create HU from a scanned label');
    allure.severity('normal');

    const gtin = uniqueGtin14();
    const masterdata = await createMasterdata({ gtin });
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    const locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
    const created = await clickOKAndGetCreatedHU();
    const { huId, huQRCode } = created;
    await expectBackOnScanScreenAfterOK({ created, locatorCode });
    // the user has no label for the new CU: he types the HU number from the success message in the scanner's manual mode
    await openCreatedHUByValue({ created, locatorCode });
    await HUManagerScreen.expectButtonMissing({ testId: 'undo-button' });

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
    await openHUManager({ user: masterdata.login.user });

    let locatorCode;
    const created = [];
    for (let i = 1; i <= 2; i++) {
        await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
        await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
        // the user has no workplace -> the organisation's default location; the second preview must look like the first
        if (i === 1) locatorCode = await NewHUPreviewScreen.expectLocatorProposedAndGet();
        await NewHUPreviewScreen.expectValue({ name: 'locator-value', expectedValue: locatorCode });
        await NewHUPreviewScreen.expectValue({ name: 'attribute-Lot-Nummer-value', expectedValue: 'L2311' });
        // no note or warning of any kind (the success toast of the previous HU is no note)
        await expect(page.locator('.Toastify__toast:not(.Toastify__toast--success)')).toHaveCount(0);
        if (i === 2) await expect(page.locator('.Toastify__toast--success').filter({ hasNotText: `HU ${created[0].huValue}` })).toHaveCount(0); // the only toast left is the previous HU's success message
        created.push(await clickOKAndGetCreatedHU());
        await expectBackOnScanScreenAfterOK({ created: created[i - 1], locatorCode }); // straight back on the scan screen for the next label
    }

    expect(created[0].huId).not.toBe(created[1].huId);
    await Backend.expect({
        hus: {
            [created[0].huId]: { huStatus: 'A', storages: { 'P1': '1 PCE' } },
            [created[1].huId]: { huStatus: 'A', storages: { 'P1': '1 PCE' } },
        },
        inventories: {
            [created[0].huQRCode]: { isExists: true, docStatus: 'CO', count: 1 },
            [created[1].huQRCode]: { isExists: true, docStatus: 'CO', count: 1 },
        },
        products: { 'P1': { huStorageCount: 2, inventoryLineCount: 2 } },
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
    await openHUManager({ user: masterdata.login.user });

    const p1 = masterdata.products.P1.productCode;
    const p2 = masterdata.products.P2.productCode;
    await expectRejection({
        title: 'barcode fits several products',
        label: customLabel(gtin),
        expectedText: new RegExp(`Barcode ${gtin} fits articles .*${p1}.*${p2}|Barcode ${gtin} fits articles .*${p2}.*${p1}`),
    });

    // the scan field is ready again: the next scan is processed
    await expectRejection({ title: 'next scan processed', label: 'hello', expectedText: /Label not recognized/ });

    await Backend.expect({
        products: {
            'P1': { huStorageCount: 0, inventoryLineCount: 0 },
            'P2': { huStorageCount: 0, inventoryLineCount: 0 },
        },
    });
});

// A role whose HU Manager access does NOT allow all actions (Mobile_Application_Access.IsAllowAllActions = N).
// includedRoles: [] — the default WebUI role would add its allow-all HU Manager access (permissions of included roles are merged).
const restrictedHUManagerRole = (grantedActions) => ({
    "restricted": {
        includedRoles: [],
        mobileApplicationAccess: [{ application: 'huManager', allowAllActions: false, ...(grantedActions ? { grantedActions } : {}) }],
    },
});

const countProposeAndCreateCalls = () => countPostCalls('proposeFromScannedCode', 'createFromScannedCode');

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
    await openHUManager({ user: masterdata.login.user });

    // today's not-found error: the HU lookup answers 404 (positive proof the HU lookup itself works for this role) and the app shows
    // its generic error toast ("Please try again ... (Trace: ...)": the app shows the raw axios text only when the
    // mobileui.frontend.showAllErrorMessages SysConfig is Y), never the label messages of the new feature
    const lookupPromise = page.waitForResponse((r) => r.url().includes('/hu-manager/byQRCode') && r.request().method() === 'POST', { timeout: SLOW_ACTION_TIMEOUT });
    await expectErrorToast('product label without createHU', async () => {
        await HUManagerScreen.scanLabel({ label: customLabel(gtin) });
    }, ({ textContent }) => {
        expect(textContent).toMatch(/Please try again/);
        expect(textContent).not.toMatch(/Label not recognized|No product found|does not identify a product|Not a product label/);
    });
    expect((await lookupPromise).status()).toBe(404);
    await NewHUPreviewScreen.expectNotDisplayed();
    await HUManagerScreen.waitForScreen();
    expect(calls.count).toBe(0); // neither propose nor create was even called: the app gate (HUManagerScreen.jsx) holds

    await Backend.expect({ products: { 'P1': { huStorageCount: 0, inventoryLineCount: 0 } } });
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
    await openHUManager({ user: masterdata.login.user });

    await scanLabelAndWaitForPreview({ label: customLabel(gtin) });
    await NewHUPreviewScreen.expectValue({ name: 'qty-value', expectedValue: '1 PCE' });
    await NewHUPreviewScreen.clickCancel();

    await HUManagerScreen.waitForScreen();
    expectCreateCallCount(createCalls, 0);
    await Backend.expect({ products: { 'P1': { huStorageCount: 0, inventoryLineCount: 0 } } });
});
