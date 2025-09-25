import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { CLEARANCE_STATUS_Quarantined } from '../../utils/screens/huManager/ClearanceDialog';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
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
            generatedHUQRCodes: { g1: { packingInstructions: 'LU', product: 'P1' } }
        }
    });
}

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
test('Move HU', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });

    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    await HUManagerScreen.move({ qrCode: masterdata.warehouses.wh2.locatorQRCode });
});

// noinspection JSUnusedLocalSymbols
test('Move HU using locator code', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });

    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    await HUManagerScreen.move({ qrCode: masterdata.warehouses.wh2.locatorCode });
});

// noinspection JSUnusedLocalSymbols
test('Change Qty', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });

    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });
    await HUManagerScreen.changeQty({ expectQtyEntered: '80', qtyEntered: '100', description: 'test' });
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.expectVisible();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '100 PCE' });
});

// noinspection JSUnusedLocalSymbols
test('Change Clearance Status', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });

    await HUManagerScreen.expectValueMissing({ name: 'clearanceStatus-value' });
    await HUManagerScreen.expectValueMissing({ name: 'clearanceNote-value' });
    await HUManagerScreen.changeClearanceStatus({ status: CLEARANCE_STATUS_Quarantined, note: 'my status note' });
    await HUManagerScreen.expectValue({ name: 'clearanceStatus-value', expectedInternalValue: CLEARANCE_STATUS_Quarantined });
    await HUManagerScreen.expectValue({ name: 'clearanceNote-value', expectedValue: 'my status note' });
});

// TODO case was broken long time ago - scanning an non existing HU does not work
/*
test('Change Locator of a generated HU QR Code', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.generatedHUQRCodes.g1.qrCode });

});
 */