import { test } from '../../playwright.config';
import { Backend } from '../utils/screens/Backend';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { HUManagerScreen } from '../utils/screens/huManager/HUManagerScreen';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';
import { allure } from 'allure-playwright';


// Mode-engine sysconfigs (new per-mode knobs introduced by the BarcodeScannerModes redesign).
// Keys map 1:1 to AD_SysConfig names (mobileui.frontend.* prefix included).
const modeSysconfigs = ({
    hardwareEnabled,
    cameraEnabled,
    manualEnabled,
    defaultMode,
    hardwareInputMode,
    hardwareInputReadOnly,
} = {}) => ({
    ...(hardwareEnabled != null && { 'mobileui.frontend.barcodeScanner.mode.hardware.enabled': hardwareEnabled }),
    ...(cameraEnabled != null && { 'mobileui.frontend.barcodeScanner.mode.camera.enabled': cameraEnabled }),
    ...(manualEnabled != null && { 'mobileui.frontend.barcodeScanner.mode.manual.enabled': manualEnabled }),
    ...(defaultMode != null && { 'mobileui.frontend.barcodeScanner.defaultMode': defaultMode }),
    ...(hardwareInputMode != null && { 'mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode': hardwareInputMode }),
    ...(hardwareInputReadOnly != null && { 'mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly': hardwareInputReadOnly }),
});

// Minimal masterdata: login only, no HU/orders. Used by the attribute-guard tests,
// which only need the barcode input to render — so they run in seconds.
const createLoginMasterdata = async ({ extraSysconfigs } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: { ...extraSysconfigs },
            login: { user: { language: 'en_US' } },
        },
    });
};

// Full masterdata: login + a handling unit, so scanning its QR code navigates to the HU Manager.
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

// ⚠️ HARDWARE CONTRACT — Honeywell CT60 / Android 11 / Keyboard-wedge mode.
// DO NOT WEAKEN THIS TEST. The five conditions below must ALL hold simultaneously — any
// single regression silently breaks scanning on the real CT60 hardware:
//
//   type=text             — required for Android InputConnection (type=hidden cannot focus)
//   inputmode=none        — soft suppression of the virtual keyboard (Android < 11 honours it)
//   readOnly PRESENT      — HARD suppression of the virtual keyboard on Android 11+ where
//                           inputMode="none" is IGNORED (CT60 / Honeywell Wedge keyboard mode).
//                           This is the load-bearing flag for this device.
//   input-text-offscreen  — the input stays in the DOM (the keystroke hook listens on document)
//                           but is visually hidden. The scan-prompt UI provides the visual anchor.
//   no <video> element    — mode.camera.enabled=N → device camera should never render
//
// Asserts on HU Manager so BarcodeScannerComponent renders from SysConfig (no hardcoded prop) —
// ApplicationsListScreen hardcodes `invisible` (headless hardware variant: off-screen input only,
// no footer, no video), which would short-circuit the sysconfig path and make readOnly +
// camera-absent checks vacuously pass.
//
// IF THIS TEST FAILS after a change to BarcodeScannerComponent.jsx: the CODE broke the contract.
// Fix the code, do NOT relax these expected values. Any change to readOnly / inputMode / type /
// the off-screen class on #input-text MUST be re-validated on a physical Honeywell CT60 (see
// e2e/mobile-webui/CLAUDE.md → "Manual Hardware Test Rule"). Automated tests cannot prove the
// on-device behaviour.
// noinspection JSUnusedLocalSymbols
test('Honeywell CT60 keystroke-wedge mode — input is off-screen + readOnly, no camera', async ({ page }) => {
    await allure.epic('E0295: Frontend MobileUI');
    await allure.feature('F12000: Frontend MobileUI');
    await allure.story('Barcode scanning modes');
    await allure.severity('critical');

    // Sysconfig combo for the keystroke-wedge mode — captured from a live local stack and
    // validated on physical CT60 hardware. Drives the new mode-engine keys so the frontend
    // renders hardware mode with readOnly=Y (hard keyboard suppression for Android 11+).
    const masterdata = await createMasterdataWithHU({
        extraSysconfigs: modeSysconfigs({
            hardwareEnabled: 'Y',
            cameraEnabled: 'N',
            manualEnabled: 'N',
            defaultMode: 'hardware',
            hardwareInputReadOnly: 'Y',
        }),
    });
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();

    await BarcodeScannerComponent.expectAttributes({ type: 'text', inputmode: 'none', readonly: true });
    await BarcodeScannerComponent.expectCssClass({ present: 'input-text-offscreen' });
    await BarcodeScannerComponent.expectCameraModeInactive();
});

// Each test drives one real-world way a barcode reaches the app, end to end:
// scanning the HU's QR code must navigate to the HU Manager and show the HU quantity.
test.describe('Scan paths', () => {

    // The manual-typing test sets the mode-engine sysconfigs to manual mode to make the input
    // visible + editable. A leaked defaultMode=manual would make every later spec's scanner
    // editable, and BarcodeScannerComponent.type() would then double-insert characters
    // (see e2e/mobile-webui/CLAUDE.md "Barcode Scanner Testing"), breaking unrelated picking scans.
    //
    // No explicit reset is needed here: SysconfigCommand resets the scanner sysconfigs to their
    // mobile defaults at the start of every createMasterdata call, so each test starts from
    // a clean scanner state regardless of what a prior test left behind.

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
            // manual mode: mode.manual.enabled=Y + defaultMode=manual renders a visible editable
            // input so Playwright can locate and fill it. inputMode attribute is absent (keyboard enabled).
            // hardware stays enabled (the handheld's scanner is always present in real deployments).
            extraSysconfigs: modeSysconfigs({
                hardwareEnabled: 'Y',
                cameraEnabled: 'N',
                manualEnabled: 'Y',
                defaultMode: 'manual',
            }),
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();

        // Navigate to HU Manager so the barcode scanner renders from SysConfig (no hardcoded prop).
        // The ApplicationsListScreen hardcodes `invisible`, which would override the
        // SysConfig and make the attribute assertion below meaningless.
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();

        // Regression guard: in manual mode (defaultMode=manual), the visible editable input
        // ([data-testid="manual-entry-input"]) must be present and NOT readOnly — the user must
        // be able to type into it (virtual keyboard suppression must be disabled on that field).
        // NOTE: #input-text (off-screen hardware input) always has inputmode="none" in every mode
        // (DataWedge IME suppression) — do NOT assert inputmode absence on that element here.
        await BarcodeScannerComponent.expectManualEntryInputPresent();
        await BarcodeScannerComponent.expectManualEntryInputNotReadOnly();

        // fill + Enter exercises the manual-typing path: onKeyUp → handleInputTextKeyPress →
        // validateScannedBarcodeAndForward. BarcodeScannerComponent.typeManually() encapsulates
        // the locator so the spec stays free of direct page.locator() calls.
        await BarcodeScannerComponent.typeManually(masterdata.handlingUnits.HU1.qrCode);

        await HUManagerScreen.waitForHUInfoPanel();
        await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    });

});

// Per-mode attribute contract — guards the HTML attributes emitted by the new mode engine.
//
// These tests assert the DOM output of useBarcodeScannerModes + the hardware input knobs:
//   mode.hardware.input.inputMode  → the `inputMode` HTML attribute on #input-text
//   mode.hardware.input.readOnly   → the `readOnly` HTML attribute on #input-text (absent=N, present=Y)
//
// Navigation: HU Manager is used (not ApplicationsListScreen) because ApplicationsListScreen
// hardcodes `invisible=true`, which short-circuits the sysconfig path and would make
// mode-specific attribute/footer assertions vacuously pass.
test.describe('Modes', () => {

    // ⚠️ HARDWARE CONTRACT — mode-engine driven.
    // Off-screen hardware input default contract: inputmode=none, readonly absent, CSS-hidden.
    // Mirrors the top-level DataWedge guard above but exercises the NEW mode-engine path
    // (mode.hardware.enabled=Y, mode.hardware.input.readOnly=N default).
    // noinspection JSUnusedLocalSymbols
    test('hardware mode — off-screen input: inputmode=none, readonly absent, CSS-hidden', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        // hardware.enabled=Y (default), readOnly=N (default) — explicit to document the contract.
        const masterdata = await createMasterdataWithHU({
            extraSysconfigs: modeSysconfigs({
                hardwareEnabled: 'Y',
                cameraEnabled: 'N',
                manualEnabled: 'N',
                hardwareInputReadOnly: 'N',
            }),
        });
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();

        // Off-screen input HTML contract for DataWedge IME deployments:
        //   type=text       — focusable, keeps InputConnection alive
        //   inputmode=none  — suppresses virtual keyboard (soft hint)
        //   readonly absent — readOnly kills InputConnection; must be absent for DataWedge IME
        //   CSS-hidden      — input-text-offscreen keeps it in DOM but invisible
        await BarcodeScannerComponent.expectAttributes({ type: 'text', inputmode: 'none', readonly: null });
        await BarcodeScannerComponent.expectCssClass({ present: 'input-text-offscreen' });
    });

    // ⚠️ HARDWARE CONTRACT — mode-engine driven, readOnly=Y.
    // Mirrors the CT60 keystroke-wedge guard above via the new mode-engine readOnly knob.
    // noinspection JSUnusedLocalSymbols
    test('hardware mode — off-screen input readOnly=Y: inputmode=none, readonly present', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        const masterdata = await createMasterdataWithHU({
            extraSysconfigs: modeSysconfigs({
                hardwareEnabled: 'Y',
                cameraEnabled: 'N',
                manualEnabled: 'N',
                hardwareInputReadOnly: 'Y',
            }),
        });
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();

        // readonly PRESENT — hard suppression for Android 11 firmware that ignores inputMode="none".
        await BarcodeScannerComponent.expectAttributes({ type: 'text', inputmode: 'none', readonly: true });
        await BarcodeScannerComponent.expectCssClass({ present: 'input-text-offscreen' });
        await BarcodeScannerComponent.expectCameraModeInactive();
    });

    // invisible=true (ApplicationsListScreen / BarcodeScannerButton callers):
    //   • off-screen #input-text IS present (keyboard listener needs it in DOM)
    //   • NO <video> element
    //   • NO footer (.barcode-scanner-footer)
    // noinspection JSUnusedLocalSymbols
    test('invisible mode — off-screen input present, no video, no footer', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        // ApplicationsListScreen always renders BarcodeScannerComponent with invisible=true,
        // so no HU or extra sysconfig setup is needed — just login.
        const masterdata = await createLoginMasterdata();
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();

        // invisible=true → useBarcodeScannerModes returns hardware-only, no camera, no manual.
        // The component renders ONLY the off-screen #input-text and the keyboard listener.
        await BarcodeScannerComponent.expectAttached({});
        await BarcodeScannerComponent.expectCameraModeInactive();
        await BarcodeScannerComponent.expectFooterAbsent();
    });

    // manual mode: mode.manual.enabled=Y + defaultMode=manual, hardware always enabled →
    //   • visible editable input (data-testid="manual-entry-input") IS rendered
    //   • off-screen #input-text IS still present (keyboard listener stays mounted)
    //   • manual-entry-input has no readOnly attribute (keyboard must be enabled)
    //   • the "Use hardware scanner" button IS shown (hardware is an enabled scanner mode to
    //     return to) — this mirrors the real deployment, where the handheld's hardware scanner
    //     is always present, so manual mode always offers a way back to it.
    // noinspection JSUnusedLocalSymbols
    test('manual mode — visible editable input rendered, off-screen input present, no readOnly', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        const masterdata = await createLoginMasterdata({
            extraSysconfigs: modeSysconfigs({
                hardwareEnabled: 'Y',
                cameraEnabled: 'N',
                manualEnabled: 'Y',
                defaultMode: 'manual',
            }),
        });
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();

        // Off-screen input stays mounted in MANUAL mode (DataWedge IME InputConnection preserved).
        await BarcodeScannerComponent.expectAttached({});
        // Visible editable input is rendered in manual mode.
        await BarcodeScannerComponent.expectManualEntryInputPresent();
        // The manual-entry input must NOT be readOnly — the user must be able to type.
        await BarcodeScannerComponent.expectManualEntryInputNotReadOnly();
        await BarcodeScannerComponent.expectCameraModeInactive();
        // Hardware scanner is enabled → manual mode offers the "Use hardware scanner" button
        // (testId barcode-scanner-back-to-scanner) so the operator can return to the scanner.
        await BarcodeScannerComponent.expectButtonPresent('barcode-scanner-back-to-scanner');
    });

    // camera toggle: hardware + camera both enabled →
    //   • footer toggle button present
    //   • clicking toggle → camera mode becomes active (camera panel shown)
    // noinspection JSUnusedLocalSymbols
    test('camera toggle — clicking hw/camera toggle activates camera mode', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        // Must run BEFORE login so the getUserMedia stub is installed before the app's first camera
        // request. Stabilises this camera-toggle test against a flaky panel teardown — mechanism in
        // BarcodeScannerComponent.stubCameraStream.
        await BarcodeScannerComponent.stubCameraStream();

        const masterdata = await createLoginMasterdata({
            extraSysconfigs: modeSysconfigs({
                hardwareEnabled: 'Y',
                cameraEnabled: 'Y',
                manualEnabled: 'N',
                defaultMode: 'hardware',
            }),
        });
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();

        // Starting in hardware mode — camera mode not active.
        await BarcodeScannerComponent.expectCameraModeInactive();
        // Toggle to camera mode via footer button.
        await BarcodeScannerComponent.clickFooterButton('barcode-scanner-toggle-hw-camera');
        // After toggle, camera mode must be active (the camera panel is shown).
        await BarcodeScannerComponent.expectCameraModeActive();
    });

    // THE canonical hardware-handheld deployment: hardware scanner is the default, manual typing is
    // an available fallback, the device camera is OFF, and the off-screen input is readOnly
    // (keyboard-suppress for firmware that ignores inputMode=none). The footer must therefore offer
    // the manual-entry fallback but NOT a camera toggle, and the manual fallback must actually
    // forward a typed barcode.
    test('hardware default + manual fallback, camera off — footer shows manual, hides camera toggle', async ({ page }) => {
        await allure.epic('E0295: Frontend MobileUI');
        await allure.feature('F12000: Frontend MobileUI');
        await allure.story('Barcode scanning modes');
        await allure.severity('critical');

        const masterdata = await createMasterdataWithHU({
            extraSysconfigs: modeSysconfigs({
                hardwareEnabled: 'Y',
                cameraEnabled: 'N',
                manualEnabled: 'Y',
                defaultMode: 'hardware',
                hardwareInputMode: 'none',
                hardwareInputReadOnly: 'Y',
            }),
        });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huManager');
        await HUManagerScreen.waitForScreen();

        // Boots in hardware mode: off-screen scan input present, camera mode not active.
        await BarcodeScannerComponent.expectAttached({});
        await BarcodeScannerComponent.expectCameraModeInactive();

        // Footer contract for this deployment: manual-entry fallback shown, camera toggle hidden
        // (camera toggle needs BOTH hardware and camera enabled).
        await BarcodeScannerComponent.expectButtonPresent('barcode-scanner-enter-manually');
        await BarcodeScannerComponent.expectButtonAbsent('barcode-scanner-toggle-hw-camera');

        // Manual fallback works: tap "enter manually" → visible editable input → type + Enter forwards.
        await BarcodeScannerComponent.clickFooterButton('barcode-scanner-enter-manually');
        await BarcodeScannerComponent.expectManualEntryInputPresent();
        await BarcodeScannerComponent.expectManualEntryInputNotReadOnly();
        await BarcodeScannerComponent.typeManually(masterdata.handlingUnits.HU1.qrCode);

        await HUManagerScreen.waitForHUInfoPanel();
        await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    });

});
