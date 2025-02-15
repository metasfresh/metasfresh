import { test } from "../../playwright.config";
import { Backend } from "../utils/screens/Backend";
import { LoginScreen } from "../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../utils/screens/huManager/HUManagerScreen';

const createMasterdata = async () => {
    const response = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            products: {
                "P1": {},
            },
            warehouses: {
                "wh1": {},
                "wh2": {},
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 80 }
            },
        }
    });

    return {
        login: response.login.user,
        huQRCode: response.handlingUnits.HU1.qrCode,
        wh2LocatorQRCode: response.warehouses.wh2.locatorQRCode,
    };
}

// noinspection JSUnusedLocalSymbols
test('Simple HU Manager test', async ({ page }) => {
    const { login, huQRCode, wh2LocatorQRCode } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode });
    await HUManagerScreen.expectHUInfoValue({ name: 'qty-value', expectedValue: '80 PCE' });
    await HUManagerScreen.move({ qrCode: wh2LocatorQRCode });
});
