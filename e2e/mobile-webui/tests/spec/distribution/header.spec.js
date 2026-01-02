import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { generateEAN13 } from '../../utils/ean13';

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "workplace1" } },
            mobileConfig: {
                distribution: {
                    navigateToJobsListAfterPickFromComplete: true,
                }
            },
            workplaces: { workplace1: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
            resources: { "plantId": { type: "PT" } },
            products: {
                "P1": { gtin: generateEAN13().ean13 },
                "P2": { gtin: generateEAN13().ean13 },
            },
            warehouses: {
                "wh1": { locators: { wh1_l1: {} } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                "HU11": { product: 'P1', warehouse: "wh1", qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    seqNo: 10,
                    warehouseFrom: "wh1", warehouseTo: "wh2", warehouseInTransit: "whInTransit", plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                },
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
    allure.story('Distribution header display');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');

    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.expectDropAllButton({ visible: false });

    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.expectHeaderProperty({ caption: 'From Locator', value: 'wh1_l1' })
    await DistributionJobScreen.expectHeaderProperty({ caption: 'Locator to', value: 'wh2_l1' })
});