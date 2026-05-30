import { test } from '../../playwright.config';
import { Backend } from '../utils/screens/Backend';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { HUManagerScreen } from '../utils/screens/huManager/HUManagerScreen';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';
import { allure } from 'allure-playwright';

// ---------------------------------------------------------------------------
// Masterdata helpers
// ---------------------------------------------------------------------------

// Minimal — login only, no HU/orders. Used by attribute-guard tests T1–T4.
// Falls back to full HU masterdata if Backend requires products/warehouses.
const createLoginMasterdata = async ({ extraSysconfigs } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: { ...extraSysconfigs },
            login: { user: { language: 'en_US' } },
        },
    });
};

// Full — login + HU. Used by functional tests T5–T8.
const createMasterdataWithHU = async ({ extraSysconfigs } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: { ...extraSysconfigs },
            login: { user: { language: 'en_US' } },
            products: { P1: {} },
            warehouses: { wh1: {} },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh1', qty: 80 },
            },
        },
    });
};

// ---------------------------------------------------------------------------
// Group 1 — Attribute regression guards (T1–T4)
// These tests detect if the #input-text DOM attributes are accidentally
// reverted to a state that breaks DataWedge IME or causes keyboard popups.
// Login-only masterdata — run in seconds.
// ---------------------------------------------------------------------------

// noinspection JSUnusedLocalSymbols
test('Input attribute: type=text present — never type=hidden', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createLoginMasterdata();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await BarcodeScannerComponent.expectAttributes({ type: 'text' });
});

// noinspection JSUnusedLocalSymbols
test('Input attribute: inputMode=none present — virtual keyboard suppressed by default', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createLoginMasterdata();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // HTML attribute is lowercase 'inputmode' (case-insensitive in HTML, but Playwright matches the attribute name).
    await BarcodeScannerComponent.expectAttributes({ inputmode: 'none' });
});

// noinspection JSUnusedLocalSymbols
test('Input attribute: readOnly absent — DataWedge IME connection must not be broken', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createLoginMasterdata();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // readonly attribute must be absent — DataWedge IME injects via InputConnection,
    // which is blocked when the input is readonly.
    await BarcodeScannerComponent.expectAttributes({ readonly: null });
});

// noinspection JSUnusedLocalSymbols
test('Input CSS: offscreen class present and type=text (not type=hidden) when showInputText=N', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    // showInputText defaults to N — this is the normal operating mode.
    const masterdata = await createLoginMasterdata();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Belt-and-suspenders: input is hidden via CSS class, NOT via type=hidden.
    // type=hidden would break DataWedge IME (InputConnection requires an actual text input).
    await BarcodeScannerComponent.expectCssClass({ present: 'input-text-offscreen' });
    await BarcodeScannerComponent.expectAttributes({ type: 'text' });
});

// ---------------------------------------------------------------------------
// Group 2 — Functional scan paths (T5–T8)
// All require HU masterdata.
// ---------------------------------------------------------------------------

// ---------------------------------------------------------------------------
// Mode A — DataWedge IME (moved from home_screen.spec.js)
// Simulates Zebra DataWedge IME text injection (InputConnection) into #input-text.
// ---------------------------------------------------------------------------
// noinspection JSUnusedLocalSymbols
test('Mode A — DataWedge IME: scan via InputConnection injection (typeViaIME)', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createMasterdataWithHU();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.scanBarcodeViaIME(masterdata.handlingUnits.HU1.qrCode);
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});

// ---------------------------------------------------------------------------
// Mode C1 — Keystroke (keyboard wedge / USB HID scanner)
// Dispatches keydown events on document (same as ApplicationsListScreen.scanBarcode).
// ---------------------------------------------------------------------------
// noinspection JSUnusedLocalSymbols
test('Mode C1 — Keystroke: scan via keyboard events on offscreen input', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createMasterdataWithHU();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // ApplicationsListScreen.scanBarcode() delegates to BarcodeScannerComponent.type(),
    // which dispatches keydown events on document — exactly how a USB/BT keyboard wedge works.
    await ApplicationsListScreen.scanBarcode(masterdata.handlingUnits.HU1.qrCode);
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});

// ---------------------------------------------------------------------------
// Mode C2 — Ctrl+V paste
// The useKeyboardBarcodeReader hook intercepts Ctrl+V, reads the clipboard via
// navigator.clipboard.readText(), and calls onReadDone immediately.
// ---------------------------------------------------------------------------
// noinspection JSUnusedLocalSymbols
test('Mode C2 — Paste: scan via Ctrl+V clipboard', async ({ page, context }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createMasterdataWithHU();
    const huBarcode = masterdata.handlingUnits.HU1.qrCode;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Grant clipboard-read permission so navigator.clipboard.readText() resolves.
    await context.grantPermissions(['clipboard-read']);

    // pasteViaClipboard mocks navigator.clipboard.readText and dispatches Ctrl+V on window.
    // The hook resolves the clipboard Promise asynchronously, then calls
    // validateScannedBarcodeAndForward which navigates to the HU Manager.
    await BarcodeScannerComponent.pasteViaClipboard(huBarcode);
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});

// ---------------------------------------------------------------------------
// Mode C3 — Manual typing into visible editable input
// When barcodeScanner.showInputText=Y and isInputTextReadonly=N the input is
// both visible and editable. Pressing Enter in the input fires onKeyUp which
// calls validateScannedBarcodeAndForward directly.
// ---------------------------------------------------------------------------
// noinspection JSUnusedLocalSymbols
test('Mode C3 — Manual: visible editable input, isInputTextReadonly=N, fill+Enter', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createMasterdataWithHU({
        extraSysconfigs: {
            // Make the input visible so Playwright can locate and fill it.
            'mobileui.frontend.barcodeScanner.showInputText': 'Y',
            // Ensure the input is editable (not inputMode="none") so fill() works.
            'mobileui.frontend.barcodeScanner.isInputTextReadonly': 'N',
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Navigate to HU Manager so the barcode scanner renders from SysConfig (no hardcoded prop).
    // The ApplicationsListScreen hardcodes isShowInputText={false}, which would override the
    // SysConfig and make the attribute assertion below meaningless.
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();

    // Regression guard: when isInputTextReadonly=N, inputmode attribute must be absent
    // (virtual keyboard suppression is disabled to allow manual typing).
    await BarcodeScannerComponent.expectAttributes({ inputmode: null });

    // fill + Enter exercises the manual-typing path: onKeyUp → handleInputTextKeyPress →
    // validateScannedBarcodeAndForward. BarcodeScannerComponent.typeManually() encapsulates
    // the locator so the spec stays free of direct page.locator() calls.
    await BarcodeScannerComponent.typeManually(masterdata.handlingUnits.HU1.qrCode);

    await HUManagerScreen.waitForHUInfoPanel();
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});
