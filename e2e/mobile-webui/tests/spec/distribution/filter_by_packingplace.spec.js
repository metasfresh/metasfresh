import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

// Mobile Distribution launcher split by Workplace.IsPackingPlace, modelled on a realistic
// two-warehouse topology:
//   - "packing" warehouse holds the packing stations (locators packing_station_1 / _2).
//   - "storage" warehouse holds reserve + ground pick faces (reserve1/ground1, reserve2/ground2).
//
// Workplaces (all created every run; the logged-in user is assigned to one of them per test):
//   - packing_station_1  IsPackingPlace=Y, warehouse=packing, pick-from=packing_station_1
//   - packing_station_2  IsPackingPlace=Y, warehouse=packing, pick-from=packing_station_2
//   - fork_lift_1        IsPackingPlace=N, warehouse=storage, no pick-from
//
// DD orders:
//   - DD1  storage/ground1   → packing/packing_station_1   (bring-to-packing)
//   - DD2  storage/ground2   → packing/packing_station_2   (bring-to-packing)
//   - DD3  storage/reserve1  → storage/ground1             (groundfill, intra-storage)
//   - DD4  storage/reserve2  → storage/ground2             (groundfill, intra-storage)
//
// Expected launchers per workplace:
//   - packing_station_1 (Y) → DD1 only (warehouse=packing + pick-from locator=packing_station_1)
//   - packing_station_2 (Y) → DD2 only
//   - fork_lift_1 (N)       → DD3, DD4 (warehouse=storage; no packing-place locator lives in storage,
//                             so nothing is excluded; the bring-to-packing DD1/DD2 are already filtered
//                             out by warehouseTo=packing)
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
                "packing": { locators: { packing_station_1: {}, packing_station_2: {} } },
                "storage": { locators: { ground1: {}, reserve1: {}, ground2: {}, reserve2: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                wpPacking1: { warehouse: 'packing', pickFromLocator: 'packing_station_1', isPackingPlace: true },
                wpPacking2: { warehouse: 'packing', pickFromLocator: 'packing_station_2', isPackingPlace: true },
                wpForkLift: { warehouse: 'storage', isPackingPlace: false },
            },
            distributionOrders: distributionOrdersEffective,
        }
    });
}

const DISTRIBUTION_ORDERS = {
    "DD1": { warehouseFrom: "storage", locatorFrom: "ground1", warehouseTo: "packing", locatorTo: "packing_station_1" },
    "DD2": { warehouseFrom: "storage", locatorFrom: "ground2", warehouseTo: "packing", locatorTo: "packing_station_2" },
    "DD3": { warehouseFrom: "storage", locatorFrom: "reserve1", warehouseTo: "storage", locatorTo: "ground1" },
    "DD4": { warehouseFrom: "storage", locatorFrom: "reserve2", warehouseTo: "storage", locatorTo: "ground2" },
};

const openDistributionLaunchers = async (user) => {
    await LoginScreen.login(user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
};

// noinspection JSUnusedLocalSymbols
test('packing_station_1 (IsPackingPlace=Y) is offered only its own bring-to-packing order DD1', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.feature('F5114: MobileUI Distribution');
    allure.story('Filter distribution by workplace packing-place role');
    allure.severity('normal');

    const masterdata = await createMasterdata({ workplace: 'wpPacking1', distributionOrders: DISTRIBUTION_ORDERS });
    await openDistributionLaunchers(masterdata.login.user);

    await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.wpPacking1.name });
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD1.launcherTestId },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('packing_station_2 (IsPackingPlace=Y) is offered only its own bring-to-packing order DD2', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.feature('F5114: MobileUI Distribution');
    allure.story('Filter distribution by workplace packing-place role');
    allure.severity('normal');

    const masterdata = await createMasterdata({ workplace: 'wpPacking2', distributionOrders: DISTRIBUTION_ORDERS });
    await openDistributionLaunchers(masterdata.login.user);

    await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.wpPacking2.name });
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD2.launcherTestId },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('fork_lift_1 (IsPackingPlace=N) is offered the intra-storage groundfill orders DD3, DD4', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.feature('F5114: MobileUI Distribution');
    allure.story('Filter distribution by workplace packing-place role');
    allure.severity('normal');

    const masterdata = await createMasterdata({ workplace: 'wpForkLift', distributionOrders: DISTRIBUTION_ORDERS });
    await openDistributionLaunchers(masterdata.login.user);

    await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.wpForkLift.name });
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD3.launcherTestId },
        { testId: masterdata.distributionOrders.DD4.launcherTestId },
    ]);
});
