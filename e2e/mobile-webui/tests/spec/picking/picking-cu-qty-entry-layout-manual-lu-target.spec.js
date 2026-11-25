import { test } from "../../../playwright.config";
import { expectLayoutSound, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, expectInsideScreen } from "../../utils/layout";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { YesNoDialog } from "../../utils/dialogs/YesNoDialog";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { allureMeta, BP_LOCATION_ORDER, createMasterdataRememberingSysconfigs, LAUNCHERS_LIMIT_SYSCONFIGS, MODE_MANUAL, restoreSysconfigsAfterEach, startSalesOrderJob } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Manual mode: LU target dialog and over-pick confirmation */

/** Masterdata of overPickingPrompt.spec.js (LU/TU picking, 4 CUs per TU, 20 TUs per LU) */
const createLuTuMasterdata = async ({ language, orderQtyCUs = 12 }) =>
    await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
            mobileConfig: { picking: { cuQtyEntryMode: MODE_MANUAL, aggregationType: 'sales_order', allowPickingAnyCustomer: true, createShipmentPolicy: 'CL', allowPickingAnyHU: true, pickTo: ['LU_TU'], shipOnCloseLU: false, allowCompletingPartialPickingJob: true, showPromptWhenOverPicking: true } },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { prices: [{ price: 1 }] } },
            packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' } },
            salesOrders: { SO1: { bpartner: 'BP1', ...BP_LOCATION_ORDER, lines: [{ product: 'P1', qty: orderQtyCUs, piItemProduct: 'TU' }] } },
        },
    });

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        for (const lang of LAYOUT_LANGUAGES) {
            // noinspection JSUnusedLocalSymbols
            test(`Manual: LU target dialog with "OK und LU schliessen", over-pick confirmation [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Manual screens');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);
                const masterdata = await createLuTuMasterdata({ language: lang });
                await startSalesOrderJob(masterdata);
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
                await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
                await layout('Manual job screen with pallet target');

                await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
                await GetQuantityDialog.waitForDialog();
                await layout('Manual quantity dialog with OK und LU schliessen (pallet target set)');
                await expectInsideScreen(GetQuantityDialog.doneAndCloseTargetButton(), 'OK und LU schliessen button', viewport);
                await expectInsideScreen(GetQuantityDialog.doneButton(), 'OK button', viewport);

                await GetQuantityDialog.typeQtyEntered(8); // more than the 3 TUs of the line
                await GetQuantityDialog.tapDone();
                await YesNoDialog.waitForDialog();
                await layout('Manual over-pick confirmation (YesNoDialog)');
                await expectInsideScreen(YesNoDialog.yesButton(), 'Over-pick Yes button', viewport);
                await expectInsideScreen(YesNoDialog.noButton(), 'Over-pick No button', viewport);
                await YesNoDialog.clickNoButton();
            });
        }
    });
}
