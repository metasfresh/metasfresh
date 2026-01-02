import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { DistributionStepScreen } from '../../utils/screens/distribution/DistributionStepScreen';

const createMasterdata = async ({ externalBarcode } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                distribution: {
                    // mobileConfig->distribution entry is important to make sure we get the default distribution config
                }
            },
            resources: {
                "plantId": { type: "PT" },
            },
            products: {
                "P1": {},
            },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 100, externalBarcode }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: 100 }],
                }
            },
        }
    });
};

const expectHU = async ({ warehouse }) => {
    await Backend.expect({
        hus: {
            HU1: { warehouse, huStatus: 'A', storages: { P1: '100 PCE' } },
        }
    });
};

// noinspection JSUnusedLocalSymbols
const standardTest = async ({ masterdata, huBarcodeToScan }) => {
    await expectHU({ warehouse: 'wh1' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({
        huQRCode: huBarcodeToScan,
        qtyToMove: '100',
        expectedQtyToMove: '100'
    });
    await expectHU({ warehouse: 'whInTransit' });

    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
    await expectHU({ warehouse: 'wh2' });

    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
};

// noinspection JSUnusedLocalSymbols
test('Scan by HU QR Code', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    await standardTest({ masterdata, huBarcodeToScan: masterdata.handlingUnits.HU1.qrCode });
});

// noinspection JSUnusedLocalSymbols
test('Scan by M_HU_ID', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    await standardTest({ masterdata, huBarcodeToScan: masterdata.handlingUnits.HU1.huId });
});

// noinspection JSUnusedLocalSymbols
test('Scan by ExternalBarcode', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const externalBarcode = "EXT" + Date.now();
    const masterdata = await createMasterdata({ externalBarcode });
    await standardTest({ masterdata, huBarcodeToScan: externalBarcode });
});
