import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: 'workplace1' } },
            mobileConfig: {
                distribution: {
                    orderBys: 'LocatorPriority',
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                // Source warehouse with two FROM locators of distinct priority.
                // Lower PriorityNo sorts first (ascending) → locHi (10) before locLo (90).
                "whFrom": { locators: { locHi: { priorityNo: 10 }, locLo: { priorityNo: 90 } } },
                // Destination warehouse + locator that matches workplace1.
                "whTo": { locators: { whTo_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                workplace1: { warehouse: 'whTo', pickFromLocator: 'whTo_l1' },
            },
            distributionOrders: {
                // Both drop to the workplace's pick-from locator so both are visible at the workplace.
                // They differ only by their FROM locator's PriorityNo.
                "DD_HI": {
                    seqNo: 1,
                    warehouseFrom: "whFrom",
                    warehouseTo: "whTo",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ locatorFrom: "locHi", locatorTo: "whTo_l1", product: "P1", qtyEntered: 100 }],
                },
                "DD_LO": {
                    seqNo: 2,
                    warehouseFrom: "whFrom",
                    warehouseTo: "whTo",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ locatorFrom: "locLo", locatorTo: "whTo_l1", product: "P1", qtyEntered: 100 }],
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Sort distribution jobs by locator priority', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Sort distribution jobs by locator priority');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    // orderBys=LocatorPriority sorts ascending by the FROM locator's PriorityNo:
    // DD_HI (PriorityNo=10) must appear before DD_LO (PriorityNo=90).
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD_HI.launcherTestId },
        { testId: masterdata.distributionOrders.DD_LO.launcherTestId },
    ]);
});
