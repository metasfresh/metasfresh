import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

// Mobile Distribution launcher split by Workplace.IsPackingPlace.
//
// Setup: one destination warehouse wh1 with four incoming DD orders from wh4.
//   - DD1, DD3 target wh1_l1   — the packing pick-from locator of the packing-place workplace
//   - DD2, DD4 target wh1_0A   — a groundfill (non-packing) locator
// Two workplaces in wh1:
//   - packingWP        IsPackingPlace=Y, pickFromLocator=wh1_l1
//   - replenishmentWP  IsPackingPlace=N (its own pickFromLocator is irrelevant for the N path)
//
// Expected launcher sets:
//   - packingWP (Y)        → only DD orders whose target locator = its pick-from (wh1_l1) → DD1, DD3
//   - replenishmentWP (N)  → only DD orders whose target locator is NOT a packing-place locator
//                            (i.e. NOT IN {wh1_l1}) → the groundfill moves DD2, DD4
const createMasterdata = async ({ distributionOrders }) => {
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
            // login without a workplace; the test scans into each workplace
            login: { user: { language: "en_US", workplace: null } },
            mobileConfig: {
                distribution: {
                    orderBys: 'SeqNo,Priority,DatePromised',
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "wh1": { locators: { wh1_l1: {}, wh1_l2: {}, wh1_0A: {} } },
                "wh4": { locators: { wh4_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                packingWP: { warehouse: 'wh1', pickFromLocator: 'wh1_l1', isPackingPlace: true },
                replenishmentWP: { warehouse: 'wh1', pickFromLocator: 'wh1_l2', isPackingPlace: false },
            },
            distributionOrders: distributionOrdersEffective,
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Distribution launchers split by Workplace.IsPackingPlace (Y vs N)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.feature('F5114: MobileUI Distribution');
    allure.story('Filter distribution by workplace packing-place role');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        distributionOrders: {
            "DD1": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
            "DD2": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_0A" },
            "DD3": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_l1" },
            "DD4": { warehouseFrom: "wh4", locatorFrom: "wh4_l1", warehouseTo: "wh1", locatorTo: "wh1_0A" },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await test.step('Packing-place workplace (IsPackingPlace=Y) is offered only the DD orders targeting its pick-from locator wh1_l1', async () => {
        await ApplicationsListScreen.changeWorkplace({ qrCode: masterdata.workplaces.packingWP.qrCode });
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.packingWP.name });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD1.launcherTestId },
            { testId: masterdata.distributionOrders.DD3.launcherTestId },
        ]);
        await DistributionJobsListScreen.goBack();
    });

    await test.step('Replenishment workplace (IsPackingPlace=N) is offered only the DD orders NOT targeting a packing-place locator (the groundfill wh1_0A moves)', async () => {
        await ApplicationsListScreen.changeWorkplace({ qrCode: masterdata.workplaces.replenishmentWP.qrCode });
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.replenishmentWP.name });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.DD2.launcherTestId },
            { testId: masterdata.distributionOrders.DD4.launcherTestId },
        ]);
    });
});
