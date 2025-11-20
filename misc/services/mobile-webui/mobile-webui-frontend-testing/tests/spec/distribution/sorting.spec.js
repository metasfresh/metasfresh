import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

const createMasterdata = async ({ orderBys, distributionOrders }) => {
    const distributionOrdersEffective = {};
    Object.keys(distributionOrders)
        .forEach(key => distributionOrdersEffective[key] = {
            warehouseFrom: "wh1",
            warehouseTo: "wh2",
            warehouseInTransit: "whInTransit",
            plant: "plantId",
            lines: [{
                product: "P1",
                qtyEntered: distributionOrders[key].qtyEntered ?? 100

            }],
            seqNo: distributionOrders[key].seqNo
        });

    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                distribution: {
                    orderBys,
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            distributionOrders: distributionOrdersEffective,
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Sort by SeqNo', async ({ page }) => {
    const masterdata = await createMasterdata({
        orderBys: 'SeqNo,Priority,DatePromised',
        distributionOrders: {
            "DD3": { seqNo: 30, qtyEntered: 33 },
            "DD2": { seqNo: 20, qtyEntered: 22 },
            "DD1": { seqNo: 10, qtyEntered: 11 },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({
        facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId,
        expectHitCount: 3
    });
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD1.launcherTestId },
        { testId: masterdata.distributionOrders.DD2.launcherTestId },
        { testId: masterdata.distributionOrders.DD3.launcherTestId },
    ]);
});
