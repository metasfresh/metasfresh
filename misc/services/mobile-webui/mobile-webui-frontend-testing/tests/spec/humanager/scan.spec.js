import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';

const createMasterdata = async ({ externalBarcode } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            products: { "P1": {} },
            warehouses: { "wh1": {} },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 80, externalBarcode }
            },
            generatedHUQRCodes: { g1: { packingInstructions: 'LU', product: 'P1' } }
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Scan by HU QR Code', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});

// noinspection JSUnusedLocalSymbols
test('Scan by M_HU_ID', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.huId });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});

// noinspection JSUnusedLocalSymbols
test('Scan by ExternalBarcode attribute', async ({ page }) => {
    const externalBarcode = "EXT" + Date.now();
    const masterdata = await createMasterdata({ externalBarcode });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: externalBarcode });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
});
