import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { generateEAN13 } from '../../utils/ean13';

const createMasterdata = async ({ captionFormat, qtyEntered, priority }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                distribution: {
                    captionFormat,
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    priority,
                    lines: [{ product: "P1", qtyEntered, }],
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('LocatorFrom,ProductValueAndName,ProductGTIN,Qty,Priority', async ({ page }) => {
    const masterdata = await createMasterdata({
        captionFormat: 'LocatorFrom,ProductValueAndName,ProductGTIN,Qty,Priority',
        qtyEntered: 123,
        priority: '3' // High
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({
        facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId,
        expectHitCount: 1
    });
    await DistributionJobsListScreen.expectJobButtons([{
        testId: masterdata.distributionOrders.DD1.launcherTestId,
        caption: masterdata.warehouses.wh1.locatorCode
            + " | " + masterdata.products.P1.productCode + "_" + masterdata.products.P1.productName
            + " | " + masterdata.products.P1.gtin
            + " | 123 Stk"
            + " | High"
    }]);
});
