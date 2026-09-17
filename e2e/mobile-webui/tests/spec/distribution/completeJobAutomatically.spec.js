import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { allure } from 'allure-playwright';

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "workplace1" } },
            mobileConfig: {
                distribution: {
                    completeJobAutomatically: true,
                }
            },
            workplaces: { workplace1: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "wh1": { locators: { wh1_l1: {} } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: "wh1", qty: qtyToMove }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1", warehouseTo: "wh2", warehouseInTransit: "whInTransit", plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Happy case', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Auto-complete distribution job');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.dropAllTo({ 
        dropToLocatorQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode,
        expectNextScreen: 'DistributionJobsListScreen',
    });
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.expectJobButtons([]);
});
