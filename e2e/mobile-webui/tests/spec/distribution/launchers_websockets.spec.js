import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

const createMasterdata = async ({ workplace }) => {
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
        }
    });
}

const createDistributionOrders = async (distributionOrders) => {
    const distributionOrdersEffective = {};
    Object.keys(distributionOrders)
        .forEach(key => distributionOrdersEffective[key] = {
            seqNo: distributionOrders[key].seqNo,
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

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            distributionOrders: distributionOrdersEffective,
        }
    });

    return masterdata.distributionOrders;

}

// noinspection JSUnusedLocalSymbols
test('Check launchers are refreshed via websockets', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Launcher websocket updates');
    allure.severity('normal');

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

    await test.step('Expect no launchers', async () => {
        await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.workplace1.name });
        await DistributionJobsListScreen.expectJobButtons([]);
    });

    const allDistributionOrders = {};

    await test.step('Create 2 launchers on backend and check they are refreshed', async () => {
        Object.assign(allDistributionOrders, await createDistributionOrders({
            "DD1": { seqNo: 10, warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
            "DD2": { seqNo: 20, warehouseFrom: "wh3", locatorFrom: "wh3_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
        }));

        await DistributionJobsListScreen.expectJobButtons([
            { testId: allDistributionOrders.DD1.launcherTestId },
            { testId: allDistributionOrders.DD2.launcherTestId },
        ]);
    });

    await test.step('Add another launcher in between and check is refreshed', async () => {
        Object.assign(allDistributionOrders, await createDistributionOrders({
            "DD3": { seqNo: 15, warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
        }));

        await DistributionJobsListScreen.expectJobButtons([
            { testId: allDistributionOrders.DD1.launcherTestId },
            { testId: allDistributionOrders.DD3.launcherTestId },
            { testId: allDistributionOrders.DD2.launcherTestId },
        ]);
    });

});
