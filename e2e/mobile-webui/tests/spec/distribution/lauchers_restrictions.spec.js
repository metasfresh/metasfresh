import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { generateEAN13 } from '../../utils/ean13';
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';

const createMasterdata = async ({ maxLaunchers, maxStartedLaunchers, allowStartNextJobOnly, generateLaunchersCount }) => {
    let distributionOrders = {};
    for (let i = 1; i <= generateLaunchersCount; i++) {
        distributionOrders['DD' + i] = {
            warehouseFrom: "wh1",
            warehouseTo: "wh2",
            warehouseInTransit: "whInTransit",
            plant: "plantId",
            lines: [{ product: "P1", qtyEntered: 100 }],
            seqNo: i
        };
    }

    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                distribution: {
                    orderBys: 'SeqNo,Priority,DatePromised',
                    maxLaunchers,
                    maxStartedLaunchers,
                    allowStartNextJobOnly,
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            distributionOrders,
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('No restrictions', async ({ page }) => {
    const masterdata = await createMasterdata({
        maxLaunchers: 20,
        maxStartedLaunchers: 0,
        allowStartNextJobOnly: false,
        generateLaunchersCount: 3
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
        { testId: masterdata.distributionOrders.DD1.launcherTestId, disabled: false },
        { testId: masterdata.distributionOrders.DD2.launcherTestId, disabled: false },
        { testId: masterdata.distributionOrders.DD3.launcherTestId, disabled: false },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('Allow starting next job only', async ({ page }) => {
    const masterdata = await createMasterdata({
        maxLaunchers: 20,
        maxStartedLaunchers: 0,
        allowStartNextJobOnly: true,
        generateLaunchersCount: 3
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
        { testId: masterdata.distributionOrders.DD1.launcherTestId, disabled: false, alreadyStarted: false },
        { testId: masterdata.distributionOrders.DD2.launcherTestId, disabled: true, alreadyStarted: false },
        { testId: masterdata.distributionOrders.DD3.launcherTestId, disabled: true, alreadyStarted: false },
    ]);

    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.goBack();
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD1.launcherTestId, disabled: false, alreadyStarted: true },
        { testId: masterdata.distributionOrders.DD2.launcherTestId, disabled: false, alreadyStarted: false },
        { testId: masterdata.distributionOrders.DD3.launcherTestId, disabled: true, alreadyStarted: false },
    ]);

    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.abort();
    await DistributionJobsListScreen.expectJobButtons([
        { testId: masterdata.distributionOrders.DD1.launcherTestId, disabled: false, alreadyStarted: false },
        { testId: masterdata.distributionOrders.DD2.launcherTestId, disabled: true, alreadyStarted: false },
        { testId: masterdata.distributionOrders.DD3.launcherTestId, disabled: true, alreadyStarted: false },
    ]);

});
