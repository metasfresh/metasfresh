import { test } from "../../../playwright.config";
import { expectLayoutSound, PHONE_VIEWPORTS, expectInsideScreen } from "../../utils/layout";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from "../../utils/screens/distribution/DistributionJobScreen";
import { DistributionLinePickFromScreen } from "../../utils/screens/distribution/DistributionLinePickFromScreen";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { generateEAN13 } from "../../utils/ean13";
import { allureMeta, createMasterdataRememberingSysconfigs, login, restoreSysconfigsAfterEach } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Components shared with the CU quantity entry, in distribution */

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        // noinspection JSUnusedLocalSymbols
        test(`Shared components in distribution: product code scan step, quantity dialog (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);
            const masterdata = await createMasterdataRememberingSysconfigs({
                language: 'en_US',
                request: {
                    login: { user: { language: 'en_US', workplace: 'workplace2' } },
                    mobileConfig: { distribution: { requireScanningProductCode: true } },
                    resources: { plantId: { type: 'PT' } },
                    products: { P1: { gtin: generateEAN13().ean13 } },
                    warehouses: { wh1: {}, wh2: { locators: { wh2_l1: {} } }, whInTransit: { inTransit: true } },
                    workplaces: { workplace2: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
                    handlingUnits: { HU1: { product: 'P1', warehouse: 'wh1', qty: 100000 } },
                    distributionOrders: { DD1: { warehouseFrom: 'wh1', warehouseTo: 'wh2', warehouseInTransit: 'whInTransit', plant: 'plantId', lines: [{ product: 'P1', qtyEntered: 100 }] } },
                },
            });
            await login(masterdata, 'distribution');
            await DistributionJobsListScreen.waitForScreen();
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
            await DistributionJobScreen.waitForScreen();
            await layout('Distribution job screen');
            await BarcodeScannerComponent.type({ scannedCode: masterdata.handlingUnits.HU1.qrCode });
            await DistributionLinePickFromScreen.waitForScreen();
            await DistributionLinePickFromScreen.waitForProductCodeInput();
            await layout('Product code scan step after the HU scan (shared scan component)');
            await DistributionLinePickFromScreen.typeProductCode(masterdata.products.P1.gtin);
            await GetQuantityDialog.waitForDialog();
            await layout('Distribution quantity dialog (shared GetQuantityDialog)');
            await expectInsideScreen(GetQuantityDialog.doneButton(), 'Distribution OK button', viewport);
        });
    });
}
