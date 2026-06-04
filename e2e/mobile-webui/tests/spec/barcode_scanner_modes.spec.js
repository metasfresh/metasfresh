import { test } from '../../playwright.config';
import { Backend } from '../utils/screens/Backend';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { HUManagerScreen } from '../utils/screens/huManager/HUManagerScreen';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';
import { allure } from 'allure-playwright';

const scannerSysconfigs = ({ showInputText, isInputTextReadonly }) => ({
    ...(showInputText != null && { 'mobileui.frontend.barcodeScanner.showInputText': showInputText }),
    ...(isInputTextReadonly != null && { 'mobileui.frontend.barcodeScanner.isInputTextReadonly': isInputTextReadonly }),
});

// Minimal masterdata: login only, no HU/orders. Used by the attribute-guard tests,
// which only need the barcode input to render — so they run in seconds.
const createLoginMasterdata = async ({ extraSysconfigs, showInputText, isInputTextReadonly } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: { ...scannerSysconfigs({ showInputText, isInputTextReadonly }), ...extraSysconfigs },
            login: { user: { language: 'en_US' } },
        },
    });
};

// Full masterdata: login + a handling unit, so scanning its QR code navigates to the HU Manager.
const createMasterdataWithHU = async ({ extraSysconfigs, showInputText, isInputTextReadonly } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: { ...scannerSysconfigs({ showInputText, isInputTextReadonly }), ...extraSysconfigs },
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

// ⚠️ HARDWARE CONTRACT — DO NOT WEAKEN THIS TEST.
// These four #input-text properties are required for the Zebra MC3300x DataWedge IME to inject
// scans WITHOUT popping the virtual keyboard. They must ALL hold simultaneously — any single
// regression (readOnly added back, type changed to hidden, inputMode dropped, input removed from
// the DOM) silently breaks scanning on real hardware. This test guards the regression where
// readOnly was swapped for inputMode="none" plus an unguarded focus(), which broke the device.
//
// IF THIS TEST FAILS after a change to BarcodeScannerComponent.jsx: the CODE broke the contract —
// fix the code, do NOT relax or "update" these expected values to make the test pass. Any change to
// these properties MUST be re-validated on a physical Zebra MC3300x (see e2e/mobile-webui/CLAUDE.md
// → "Manual Hardware Test Rule"); automated tests CANNOT prove the on-device behaviour.
//
//   type=text       — required for Android InputConnection (type=hidden cannot receive focus)
//   inputmode=none  — suppresses the virtual keyboard while keeping InputConnection alive
//   readonly absent — readOnly kills InputConnection; DataWedge injection silently fails
//   CSS-hidden      — input-text-offscreen, NOT removed from the DOM (type=hidden would break IME)
// noinspection JSUnusedLocalSymbols
test('#input-text HTML: type=text, inputMode=none, readOnly absent, CSS-hidden', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    const masterdata = await createLoginMasterdata();
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // type=text: required for Android InputConnection (type=hidden cannot receive focus)
    // inputmode=none: suppresses virtual keyboard while keeping InputConnection alive
    // readonly absent: readOnly kills InputConnection — DataWedge injection silently fails
    // input-text-offscreen: CSS-hidden, NOT removed from DOM (type=hidden would break IME)
    await BarcodeScannerComponent.expectAttributes({ type: 'text', inputmode: 'none', readonly: null });
    await BarcodeScannerComponent.expectCssClass({ present: 'input-text-offscreen' });
});

// Each test drives one real-world way a barcode reaches the app, end to end:
// scanning the HU's QR code must navigate to the HU Manager and show the HU quantity.
test.describe('Scan paths', () => {

    // The manual-typing test flips two GLOBAL barcode-scanner sysconfigs to make the input
    // visible + editable. A leaked isInputTextReadonly='N' would make every later spec's scanner
    // editable, and BarcodeScannerComponent.type() would then double-insert characters
    // (see e2e/mobile-webui/CLAUDE.md "Barcode Scanner Testing"), breaking unrelated picking scans.
    //
    // No explicit reset is needed here: SysconfigCommand resets the scanner sysconfigs to their
    // mobile defaults (showInputText='Y', isInputTextReadonly='Y') at the start of every
    // createMasterdata call, so each test starts from a clean scanner state regardless of what a
    // prior test left behind.

    // noinspection JSUnusedLocalSymbols
    test('DataWedge IME — InputConnection injection forwards barcode', async ({ page }) => {
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

    // noinspection JSUnusedLocalSymbols
    test('hardware scanner keystrokes forward barcode via window-level hook', async ({ page }) => {
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

    // noinspection JSUnusedLocalSymbols
    test('Ctrl+V clipboard paste forwards barcode', async ({ page, context }) => {
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

    // noinspection JSUnusedLocalSymbols
    test('manual typing with visible editable input forwards barcode on Enter', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        const masterdata = await createMasterdataWithHU({
            // showInputText='Y' makes the input visible so Playwright can locate and fill it.
            showInputText: 'Y',
            // isInputTextReadonly='N' makes the input editable (not inputMode="none") so fill() works.
            isInputTextReadonly: 'N',
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

});
