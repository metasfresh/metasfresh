import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { generateEAN13 } from '../../utils/ean13';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';

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
                "HU12": { product: 'P2', warehouse: "wh1", qty: qtyToMove },
                "HU21": { product: 'P1', warehouse: "wh1", qty: qtyToMove },
                "HU22": { product: 'P2', warehouse: "wh1", qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    seqNo: 10,
                    warehouseFrom: "wh1", warehouseTo: "wh2", warehouseInTransit: "whInTransit", plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                    ],
                },
                "DD2": {
                    seqNo: 20,
                    warehouseFrom: "wh1", warehouseTo: "wh2", warehouseInTransit: "whInTransit", plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                    ],
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
    allure.story('Navigate to jobs list after pick from complete');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');

    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.expectDropAllButton({ visible: false });

    await test.step(`Job 1`, async () => {
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD1.jobId })

        await test.step('Pick P1 -> expect navigate to job screen since the job is not fully picked', async () => {
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU11.qrCode,
                productScannedCode: masterdata.products.P1.gtin,
                expectedQtyToMove: 100,
                expectNextScreen: 'DistributionJobScreen'
            });
            await DistributionJobScreen.waitForScreen();
        });

        await test.step(`Pick P2 -> expect navigate to next job's pick from screen`, async () => {
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU12.qrCode,
                productScannedCode: masterdata.products.P2.gtin,
                expectedQtyToMove: 100,
                expectNextScreen: 'DistributionLinePickFromScreen'
            });
        });
    });

    await test.step(`Job 2`, async () => {
        await DistributionLinePickFromScreen.expectJobId({ distributionJobId: masterdata.distributionOrders.DD2.jobId })

        await test.step('Pick P1 -> expect navigate to job screen since the job is not fully picked', async () => {
            await DistributionLinePickFromScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU21.qrCode,
                productScannedCode: masterdata.products.P1.gtin,
                expectedQtyToMove: 100,
                expectNextScreen: 'DistributionJobScreen'
            });
            await DistributionJobScreen.waitForScreen();
        });

        await test.step(`Pick P2 -> expect navigate to jobs list screen because there is no other job to start`, async () => {
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU22.qrCode,
                productScannedCode: masterdata.products.P2.gtin,
                expectedQtyToMove: 100,
                expectNextScreen: 'DistributionJobsListScreen'
            });
            await DistributionJobsListScreen.waitForScreen();
        });
    });

    await test.step("Drop everything that was picked to wh2_l1", async () => {
        await DistributionJobsListScreen.expectDropAllButton({ visible: true });
        await DistributionJobsListScreen.dropAll({ dropToQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode });
        await DistributionJobsListScreen.expectJobButtons([]);
        await Backend.expect({
            title: 'All HUs are in wh2',
            hus: {
                HU11: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU12: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                HU21: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU22: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
            }
        });
    });
});