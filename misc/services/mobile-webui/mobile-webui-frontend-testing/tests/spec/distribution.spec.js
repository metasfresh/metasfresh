import {test} from "../../playwright.config";
import {Backend} from "../utils/screens/Backend";
import {LoginScreen} from "../utils/screens/LoginScreen";
import {ApplicationsListScreen} from "../utils/screens/ApplicationsListScreen";
import {DistributionJobsListScreen} from "../utils/screens/distribution/DistributionJobsListScreen";

const loginAndCreateMasterdata = async () => {
    const response = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: {language: "en_US"},
            },
            products: {
                "P1": {},
            },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": {inTransit: true},
            },
            packingInstructions: {
                "PI": {lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4},
            },
            handlingUnits: {
                "HU1": {product: 'P1', warehouse: 'wh1', qty: 80}
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    lines: [{product: "P1", qtyEntered: 80}],
                }
            },
        }
    });

    return {
        login: response.login.user,
        warehouseFromFacetId: response.distributionOrders.DD1.warehouseFromFacetId,
        launcherTestId: response.distributionOrders.DD1.launcherTestId,
    };
}

// noinspection JSUnusedLocalSymbols
test('Simple distribution test', async ({page}) => {
    const {login, warehouseFromFacetId, launcherTestId} = await loginAndCreateMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({facetId: warehouseFromFacetId, expectHitCount: 1});
    await DistributionJobsListScreen.startJob({launcherTestId});
});
