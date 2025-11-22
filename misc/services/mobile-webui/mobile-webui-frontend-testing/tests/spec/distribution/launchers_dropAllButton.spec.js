import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
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
                    orderBys: 'SeqNo,Priority,DatePromised',
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
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                },
                "DD2": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P2", qtyEntered: qtyToMove }],
                },
                "DD3": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P3", qtyEntered: qtyToMove }],
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Pick multiple HUs (by HU code) and drop them all together in one step (using locator code)', async ({ page }) => {
    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step("Pick job 1", async () => {
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.huId,
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
        await DistributionJobScreen.goBack();
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
    });
});
