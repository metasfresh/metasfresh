import { test } from '../../playwright.config';
import { Backend } from '../utils/screens/Backend';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { HUManagerScreen } from '../utils/screens/huManager/HUManagerScreen';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';
import { allure } from 'allure-playwright';

const createMasterdata = async ({ extraSysconfigs } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: {
                ...extraSysconfigs,
            },
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
// Mode C2 — Ctrl+V paste
// The useKeyboardBarcodeReader hook intercepts Ctrl+V, reads the clipboard via
// navigator.clipboard.readText(), and calls onReadDone immediately.
// ---------------------------------------------------------------------------
// noinspection JSUnusedLocalSymbols
test('Paste — scan via Ctrl+V clipboard', async ({ page, context }) => {
    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000');
    allure.story('Barcode scanning modes — Ctrl+V paste');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const huBarcode = masterdata.handlingUnits.HU1.qrCode;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Grant clipboard-read permission so navigator.clipboard.readText() resolves.
    await context.grantPermissions(['clipboard-read']);

    // Mock navigator.clipboard.readText to return the HU QR code without needing
    // the OS clipboard. The hook calls readText() asynchronously after the keydown.
    await page.evaluate((barcode) => {
        Object.defineProperty(navigator, 'clipboard', {
            value: {
                readText: () => Promise.resolve(barcode),
            },
            configurable: true,
        });
    }, huBarcode);

    // Wait for the scanner input to be in the DOM before firing the paste event.
    await BarcodeScannerComponent.waitToAttach({});

    // Dispatch Ctrl+V on the window — the hook's keydown listener picks it up.
    await page.evaluate(() => {
        window.dispatchEvent(new KeyboardEvent('keydown', { key: 'v', ctrlKey: true, bubbles: true }));
    });

    // The hook resolves the clipboard Promise asynchronously, then calls
    // validateScannedBarcodeAndForward which navigates to the HU Manager.
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
test('Manual typing — visible editable input submits on Enter', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0295: Frontend MobileUI');
    allure.tag('F12000: Frontend MobileUI');
    allure.tag('F12000');
    allure.story('Barcode scanning modes — manual typing into visible input');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        extraSysconfigs: {
            // Make the input visible so Playwright can locate and fill it.
            'mobileui.frontend.barcodeScanner.showInputText': 'Y',
            // Ensure the input is editable (not inputMode="none") so fill() works.
            'mobileui.frontend.barcodeScanner.isInputTextReadonly': 'N',
        },
    });
    const huBarcode = masterdata.handlingUnits.HU1.qrCode;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // The input is visible and editable in this configuration.
    // fill() sets the value without triggering the onChange debounce.
    // Pressing Enter fires onKeyUp → handleInputTextKeyPress →
    // validateScannedBarcodeAndForward in BarcodeScannerComponent.
    const inputLocator = page.locator('#input-text');
    await inputLocator.fill(huBarcode);
    await page.keyboard.press('Enter');

    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});
