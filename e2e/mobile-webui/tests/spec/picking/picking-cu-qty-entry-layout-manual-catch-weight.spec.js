import { test } from "../../../playwright.config";
import { expectLayoutSound, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, expectInsideScreen } from "../../utils/layout";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { allureMeta, BP_LOCATION_ORDER, createMasterdataRememberingSysconfigs, LAUNCHERS_LIMIT_SYSCONFIGS, MODE_MANUAL, restoreSysconfigsAfterEach, startSalesOrderJob } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Manual mode: catch weight product */

/** Masterdata of picking_catchWeight.spec.js (catch weight product) */
const createCatchWeightMasterdata = async ({ language }) =>
    await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
            mobileConfig: { picking: { cuQtyEntryMode: MODE_MANUAL, aggregationType: 'sales_order', allowPickingAnyCustomer: true, createShipmentPolicy: 'CL', allowPickingAnyHU: true, pickTo: ['LU_TU'], allowCompletingPartialPickingJob: true } },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: {
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }],
                },
            },
            packingInstructions: { PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 } },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', qty: 100, weightNet: 10, lotNo: 'lot1', bestBeforeDate: '2031-11-23' } },
            salesOrders: { SO1: { bpartner: 'BP1', ...BP_LOCATION_ORDER, lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }] } },
        },
    });

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        for (const lang of LAYOUT_LANGUAGES) {
            // noinspection JSUnusedLocalSymbols
            test(`Manual: catch weight QR view and manual catch weight input [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Manual screens');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);
                const masterdata = await createCatchWeightMasterdata({ language: lang });
                await startSalesOrderJob(masterdata);
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
                await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
                await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

                await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
                await GetQuantityDialog.waitForDialog();
                await GetQuantityDialog.expectSwitchToManualInputVisible();
                await layout('Manual catch weight QR view');
                await expectInsideScreen(GetQuantityDialog.switchToManualInputButton(), 'Switch to manual input button', viewport);
                await GetQuantityDialog.clickManual();
                await layout('Manual quantity dialog of a catch weight product (manual input)');
                await expectInsideScreen(GetQuantityDialog.catchWeightField(), 'Catch weight field', viewport);
                await expectInsideScreen(GetQuantityDialog.doneButton(), 'OK button', viewport);
            });
        }
    });
}
