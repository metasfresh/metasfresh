import { test } from "../../../playwright.config";
import { expectLayoutSound, PHONE_VIEWPORTS, saveScreenshot, DIALOG_SCOPE } from "../../utils/layout";
import { HUManagerScreen } from "../../utils/screens/huManager/HUManagerScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { allureMeta, createMasterdataRememberingSysconfigs, login, restoreSysconfigsAfterEach } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Components shared with the CU quantity entry, in the HU Manager */

/** Masterdata of the HU Manager: one LU (HU1); `withAggregatedTU`: HU1 has an aggregated TU with its own QR code, and a second LU (HU2) is the move target */
const createHuManagerMasterdata = async ({ withAggregatedTU = false } = {}) =>
    await createMasterdataRememberingSysconfigs({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            products: { P1: {} },
            warehouses: { wh1: {} },
            packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI', ...(withAggregatedTU ? { qrCodeOfAggregatedTU: true } : {}) },
                ...(withAggregatedTU ? { HU2: { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI' } } : {}),
            },
        },
    });

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        // noinspection JSUnusedLocalSymbols
        test(`HU Manager: scan screen, HU info with action buttons, change quantity dialog (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);
            const masterdata = await createHuManagerMasterdata();
            await login(masterdata, 'huManager');
            await HUManagerScreen.waitForScreen();
            await layout('HU Manager scan screen');
            await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
            await layout('HU Manager HU info and action buttons');
            await HUManagerScreen.openChangeQtyDialog();
            await layout('HU Manager change quantity dialog', DIALOG_SCOPE);
        });

        // noinspection JSUnusedLocalSymbols
        test(`HU Manager move: number of TUs dialog of an aggregated TU (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);
            const masterdata = await createHuManagerMasterdata({ withAggregatedTU: true });
            await login(masterdata, 'huManager');
            await HUManagerScreen.waitForScreen();
            await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
            await HUManagerScreen.openMoveScreen();
            await BarcodeScannerComponent.type(masterdata.handlingUnits.HU2.qrCode);
            await GetQuantityDialog.waitForDialog();
            await layout('HU Manager move: number of TUs dialog', DIALOG_SCOPE);
            await saveScreenshot(`${viewport.width}x${viewport.height}-HU-Manager-move-number-of-TUs-dialog`);
        });
    });
}
