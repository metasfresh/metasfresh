import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { generateEAN13 } from '../../utils/ean13';

const createMasterdata = async ({ qtyToMove, externalBarcode }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "workplace2" } },
            mobileConfig: {
                distribution: {
                    requireScanningProductCode: true,
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: {
                "P1": { gtin: generateEAN13().ean13 },
            },
            warehouses: {
                "wh1": {},
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                workplace2: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' },
            },
            handlingUnits: {
                "HU1": { product: "P1", warehouse: "wh1", qty: 100000, externalBarcode }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                }
            },
        }
    });
}

const createMasterdataAndStartJob = async ({ qtyToMove, externalBarcode }) => {
    const masterdata = await createMasterdata({ qtyToMove, externalBarcode });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    return masterdata;
};

// noinspection JSUnusedLocalSymbols
test.describe('Scan directly in job screen, expect scanning the product code too', () => {
    // noinspection JSUnusedLocalSymbols
    test('Scan the HU by QRCode', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0370: Intralogistic (HUs)');
        allure.tag('F5114.3');
        allure.story('Validate GTIN during pick from');
        allure.severity('normal');

        const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100 });

        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            productScannedCode: masterdata.products.P1.gtin,
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
    });

    // noinspection JSUnusedLocalSymbols
    test('Scan the HU by Value/M_HU_ID', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0370: Intralogistic (HUs)');
        allure.tag('F5114.3');
        allure.story('Validate GTIN during pick from');
        allure.severity('normal');

        const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100 });

        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.huId,
            productScannedCode: masterdata.products.P1.gtin,
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
    });

    // noinspection JSUnusedLocalSymbols
    test('Scan the HU by External Attribute', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0370: Intralogistic (HUs)');
        allure.tag('F5114.3');
        allure.story('Validate GTIN during pick from');
        allure.severity('normal');

        const externalBarcode = "EXT" + Date.now();
        const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100, externalBarcode });

        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.scanHUToMove({
            huQRCode: externalBarcode,
            productScannedCode: masterdata.products.P1.gtin,
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
    });

});

// noinspection JSUnusedLocalSymbols
test('Do not ask for picked qty when it is one', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.story('Validate GTIN during pick from');
    allure.severity('normal');

    const masterdata = await createMasterdataAndStartJob({ qtyToMove: 1 });

    await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });
    await DistributionJobScreen.scanHUToMove({
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        productScannedCode: masterdata.products.P1.gtin,
        expectQuantityDialog: false,
    });
    await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', color: 'yellow' });
});
