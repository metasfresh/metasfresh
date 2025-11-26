import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

const createMasterdata = async ({ workplace, distributionOrders }) => {
    const distributionOrdersEffective = {};
    let seqNo = 1;
    Object.keys(distributionOrders)
        .forEach(key => distributionOrdersEffective[key] = {
            seqNo: seqNo++,
            warehouseFrom: distributionOrders[key].warehouseFrom,
            warehouseTo: distributionOrders[key].warehouseTo,
            warehouseInTransit: "whInTransit",
            plant: "plantId",
            lines: [{
                locatorFrom: distributionOrders[key].locatorFrom,
                locatorTo: distributionOrders[key].locatorTo,
                product: "P1",
                qtyEntered: 100,
            }],
        });

    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace } },
            mobileConfig: {
                distribution: {
                    orderBys: 'SeqNo,Priority,DatePromised',
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "wh1": { locators: { wh1_l1: {}, wh1_l2: {} } },
                "wh2": { locators: { wh2_l1: {}, wh2_l2: {} } },
                "wh3": { locators: { wh3_l1: {}, wh3_l2: {} } },
                "wh4": { locators: { wh4_l1: {}, wh4_l2: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                workplace1: { warehouse: 'wh1', pickFromLocator: 'wh1_l1' },
                workplace2: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' },
                workplace3: { warehouse: 'wh3', pickFromLocator: 'wh3_l1' },
                workplace4: { warehouse: 'wh4', pickFromLocator: 'wh4_l1' },
            },
            distributionOrders: distributionOrdersEffective,
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Show all jobs when no current workplace', async ({ page }) => {
    const masterdata = await createMasterdata({
        workplace: null,
        distributionOrders: {
            "DD1": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
            "DD2": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l2" },
            "DD3": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh2_l1" },
            "DD4": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh2_l2" },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await DistributionJobsListScreen.filterByFacetId({
        facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, // i.e. wh4
        expectHitCount: 4
    });
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD1.launcherTestId },
        { testId: masterdata.distributionOrders.DD2.launcherTestId },
        { testId: masterdata.distributionOrders.DD3.launcherTestId },
        { testId: masterdata.distributionOrders.DD4.launcherTestId },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('Show only jobs suitable for workplace1', async ({ page }) => {
    const masterdata = await createMasterdata({
        workplace: 'workplace1',
        distributionOrders: {
            "DD1": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
            "DD2": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l2" },
            "DD3": { warehouseFrom: "wh3", locatorFrom: "wh3_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
            "DD4": { warehouseFrom: "wh3", locatorFrom: "wh3_l1", warehouseTo: "wh1", locatorTo: "wh2_l2" },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step('Expect only jobs that have locatorTo=wh1_l1 to be available', async () => {
        await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.workplace1.name });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId },
            { testId: masterdata.distributionOrders.DD3.launcherTestId },
        ]);
    });

    await test.step('Filtering by warehouseFrom=wh4 shall narrow the results', async () => {
        // Filtering by wh4 (the only available from warehouse) shall give the same results
        await DistributionJobsListScreen.filterByFacetId({
            facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, // i.e. wh4
            expectHitCount: 1
        });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId },
        ]);
    });
});
