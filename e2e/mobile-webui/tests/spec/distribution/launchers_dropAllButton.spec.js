import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "workplace1" } },
            mobileConfig: {
                distribution: {
                    orderBys: 'SeqNo',
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: {
                "P1": {},
                "P2": {},
                "P3": {},
            },
            warehouses: {
                "wh1": { locators: { wh1_l1: {} } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: { workplace1: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: qtyToMove },
                "HU2": { product: 'P2', warehouse: 'wh1', qty: qtyToMove },
                "HU3": { product: 'P3', warehouse: 'wh1', qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    seqNo: 10,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                },
                "DD2": {
                    seqNo: 20,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P2", qtyEntered: qtyToMove }],
                },
                "DD3": {
                    seqNo: 30,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P3", qtyEntered: qtyToMove },
                    ],
                },
                "DD4": {
                    seqNo: 40,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Pick multiple HUs (by HU code) and drop them all together in one step (using locator code)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.2');
    allure.story('Drop all button from launchers');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step("Before picking expectations", async () => {
        await DistributionJobsListScreen.expectDropAllButton({ visible: false });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId, alreadyStarted: false },
            { testId: masterdata.distributionOrders.DD2.launcherTestId, alreadyStarted: false },
            { testId: masterdata.distributionOrders.DD3.launcherTestId, alreadyStarted: false },
            { testId: masterdata.distributionOrders.DD4.launcherTestId, alreadyStarted: false },
        ]);
        await Backend.expect({
            title: 'All HUs are in warehouse wh1',
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh1', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh1', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh1', storages: { P3: '100 PCE' } },
            }
        });
    });

    await test.step("Pick job 1", async () => {
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.huId,
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
        await DistributionJobScreen.goBack();

        await DistributionJobsListScreen.expectDropAllButton({ enabled: true });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD2.launcherTestId, alreadyStarted: false },
            { testId: masterdata.distributionOrders.DD3.launcherTestId, alreadyStarted: false },
            { testId: masterdata.distributionOrders.DD4.launcherTestId, alreadyStarted: false },
        ]);
    });
    await test.step("Pick job 2", async () => {
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD2.launcherTestId });
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU2.huId,
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
        await DistributionJobScreen.goBack();

        await DistributionJobsListScreen.expectDropAllButton({ enabled: true });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD2.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD3.launcherTestId, alreadyStarted: false },
            { testId: masterdata.distributionOrders.DD4.launcherTestId, alreadyStarted: false },
        ]);
    });
    await test.step("Pick job 3 partial", async () => {
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD3.launcherTestId });
        await DistributionJobScreen.clickLineButton({ index: 2 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU3.huId,
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
        await DistributionJobScreen.goBack();

        await DistributionJobsListScreen.expectDropAllButton({ enabled: true });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD2.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD3.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD4.launcherTestId, alreadyStarted: false },
        ]);
    });

    await Backend.expect({
        title: 'All HUs are in whInTransit',
        hus: {
            HU1: { huStatus: 'A', warehouse: 'whInTransit', storages: { P1: '100 PCE' } },
            HU2: { huStatus: 'A', warehouse: 'whInTransit', storages: { P2: '100 PCE' } },
            HU3: { huStatus: 'A', warehouse: 'whInTransit', storages: { P3: '100 PCE' } },
        }
    });

    await test.step("Drop everything that was picked to wh2_l1", async () => {
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.dropAll({ dropToQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode });

        await DistributionJobsListScreen.expectDropAllButton({ visible: false });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD3.launcherTestId, alreadyStarted: true },
            { testId: masterdata.distributionOrders.DD4.launcherTestId, alreadyStarted: false },
        ]);
        await Backend.expect({
            title: 'All HUs are in wh2',
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh2', storages: { P3: '100 PCE' } },
            }
        });
    });
});
