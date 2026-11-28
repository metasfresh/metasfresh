import { test } from "../../../playwright.config";
import { expectLayoutSound, expectReachable, PHONE_VIEWPORTS, LAYOUT_LANGUAGES, expectInsideScreen } from "../../utils/layout";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickGraiScreen } from "../../utils/screens/picking/PickGraiScreen";
import { allureMeta, BP_LOCATION_ORDER, createMasterdataRememberingSysconfigs, LAUNCHERS_LIMIT_SYSCONFIGS, MODE_MANUAL, restoreSysconfigsAfterEach, startSalesOrderJob } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Manual mode: inline GRAI capture panel */

const createGraiMasterdata = async ({ language }) =>
    await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
            mobileConfig: { picking: { cuQtyEntryMode: MODE_MANUAL, aggregationType: 'sales_order', allowPickingAnyCustomer: true, createShipmentPolicy: 'CL', allowPickingAnyHU: true, shipOnCloseLU: false, pickTo: ['LU_TU'], allowCompletingPartialPickingJob: false } },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { prices: [{ price: 1 }] }, P2: { prices: [{ price: 1 }] } },
            packingInstructions: {
                PI_P1: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU_MAIN', qtyTUsPerLU: 40, tu: 'TU_IFCO_P2', product: 'P2', qtyCUsPerTU: 4 },
            },
            handlingUnits: { HU_SOURCE_P1: { product: 'P1', warehouse: 'wh', qty: 200 }, HU_SOURCE_P2: { product: 'P2', warehouse: 'wh', qty: 200 } },
            salesOrders: { SO1: { bpartner: 'BP1', ...BP_LOCATION_ORDER, lines: [{ product: 'P1', qty: 40, piItemProduct: 'TU_IFCO_P1' }, { product: 'P2', qty: 40, piItemProduct: 'TU_IFCO_P2' }] } },
        },
    });

const buildDistinctGrais = (baseGrai, count) => {
    const [companyPrefix, assetType] = baseGrai.split('.');
    return Array.from({ length: count }, (_, i) => `${companyPrefix}.${assetType}.${String(i + 1).padStart(6, '0')}`);
};

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        for (const lang of LAYOUT_LANGUAGES) {
            // noinspection JSUnusedLocalSymbols
            test(`Manual: inline GRAI capture panel and the "skipped" notice [${lang}] (${viewport.name})`, async ({ page: _page }) => {
                allureMeta('Responsive Manual screens');
                const layout = (label, scope) => expectLayoutSound(`${label} [${lang}]`, viewport, scope);
                const masterdata = await createGraiMasterdata({ language: lang });
                const allGrais = buildDistinctGrais(masterdata.packingInstructions.PI_P1.grai, 20);
                await startSalesOrderJob(masterdata);
                await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
                await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });

                await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU_SOURCE_P1.qrCode, expectQtyEntered: '10', expectNextScreen: 'PickGraiScreen' });
                await layout('Manual GRAI capture panel, nothing captured');
                await PickGraiScreen.scanGraiBatch({ graiStrings: allGrais.slice(0, 10) });
                await PickGraiScreen.expectGraiChipCount({ expectedCount: 10 });
                await expectInsideScreen(PickGraiScreen.panel(), 'GRAI capture panel with 10 chips', { width: viewport.width, height: 100000 });
                await expectReachable(PickGraiScreen.saveButton(), 'GRAI Save button', viewport);
                await PickGraiScreen.clickSave();
                await PickingJobScreen.waitForScreen();
                await layout('Manual job screen after a GRAI pick');

                await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU_SOURCE_P2.qrCode, expectQtyEntered: '10', expectNextScreen: 'PickGraiScreen' });
                await PickGraiScreen.scanGrai({ graiString: allGrais[0] }); // already on the LU: skipped
                await PickGraiScreen.expectSkippedNotice({ count: 1 });
                await layout('Manual GRAI capture panel with the "1 skipped" notice');
            });
        }
    });
}
