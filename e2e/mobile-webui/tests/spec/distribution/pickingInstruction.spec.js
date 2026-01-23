import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';

import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                distribution: {
                    captionFormat: 'LocatorFrom,ProductValueAndName,Qty,PickingInstruction',

                }
            },
            warehouses: {
                "whSource": {},
                "whTarget": {},
                "whInTransit": { inTransit: true },
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1, pickingInstruction: "COMP1 picking instructions" },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            // packingInstructions: {
            //     "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
            // },
            // handlingUnits: {
            //     "HU_COMP1": { product: 'COMP1', warehouse: 'whSource', qty: comp1_qty },
            //     "HU_COMP2": { product: 'COMP2', warehouse: 'whSource', qty: comp2_qty },
            // },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'whTarget',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: {
                        "PP1_L1": { product: "COMP1", expectedPickingInstruction: "COMP1 picking instructions" },
                        "PP1_L2": { product: "COMP2", expectedPickingInstruction: "" },
                    }
                }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "whSource",
                    warehouseTo: "whTarget",
                    warehouseInTransit: "whInTransit",
                    forwardPPOrder: "PP1",
                    forwardPPOrderBOMLine: "PP1_L1",
                    lines: [{ product: "COMP1", qtyEntered: 5 }],
                },
                "DD2": {
                    warehouseFrom: "whSource",
                    warehouseTo: "whTarget",
                    warehouseInTransit: "whInTransit",
                    forwardPPOrder: "PP1",
                    forwardPPOrderBOMLine: "PP1_L2",
                    lines: [{ product: "COMP2", qtyEntered: 10 }],
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('PickingInstruction test', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 2 });
    await DistributionJobsListScreen.expectJobButtons([
        {
            testId: masterdata.distributionOrders.DD1.launcherTestId,
            caption: masterdata.warehouses.whSource.locatorCode
                + " | " + masterdata.products.COMP1.productCode + "_" + masterdata.products.COMP1.productName
                + " | 5 Stk"
                + " | COMP1 picking instructions"
        },
        {
            testId: masterdata.distributionOrders.DD2.launcherTestId,
            caption: masterdata.warehouses.whSource.locatorCode
                + " | " + masterdata.products.COMP2.productCode + "_" + masterdata.products.COMP2.productName
                + " | 10 Stk"
        }
    ]);
});
