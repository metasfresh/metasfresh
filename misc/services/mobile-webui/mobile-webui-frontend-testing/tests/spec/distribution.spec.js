import { test } from "../../playwright.config";
import { Backend } from "../utils/screens/Backend";
import { LoginScreen } from "../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../utils/screens/distribution/DistributionLineScreen';
import { DistributionStepScreen } from '../utils/screens/distribution/DistributionStepScreen';

const createMasterdata = async () => {
    const lineInfo = { product: "P1", qtyEntered: 80 };

    const response = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
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
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 100 }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    lines: [lineInfo],
                }
            },
        }
    });

    return {
        login: response.login.user,
        warehouseFromFacetId: response.distributionOrders.DD1.warehouseFromFacetId,
        dropToLocatorQRCode: response.warehouses.wh2.locatorQRCode,
        launcherTestId: response.distributionOrders.DD1.launcherTestId,
        huQRCode: response.handlingUnits.HU1.qrCode,
        lineQty: lineInfo.qtyEntered,
    };
}

// noinspection JSUnusedLocalSymbols
test('Simple distribution test', async ({ page }) => {
    const { login, warehouseFromFacetId, launcherTestId, huQRCode, lineQty, dropToLocatorQRCode } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode, qtyToMove: `${lineQty}`, expectedQtyToMove: `${lineQty}`});
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
});


// noinspection JSUnusedLocalSymbols
test('Distribution using 2 steps to pick the needed qty.', async ({ page }) => {
    const { login, warehouseFromFacetId, launcherTestId, huQRCode, lineQty, dropToLocatorQRCode } = await createMasterdata();

    const qtyToMovePerStep = lineQty / 2;

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode, qtyToMove: `${qtyToMovePerStep}`, expectedQtyToMove: `${lineQty}` });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.scanHUToMove({ huQRCode, qtyToMove: `${qtyToMovePerStep}`, expectedQtyToMove: `${qtyToMovePerStep}` });
    await DistributionLineScreen.clickStepButton({ index: 2 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
});