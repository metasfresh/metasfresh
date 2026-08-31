import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";

const QTY_CUSTOMER_A = 10;
const QTY_CUSTOMER_B = 5;
const QTY_TOTAL = QTY_CUSTOMER_A + QTY_CUSTOMER_B;

// One ground locator holding exactly the summed demand: the stock-aware allocation then has a single
// source locator to serve the group from, so the group's one order is also the only order.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { mover: { language: "en_US", workplace: "packingWorkplace" } },
            mobileConfig: {
                distribution: {
                    captionFormat: 'LocatorFrom,LocatorTo,ProductValueAndName,Qty',
                },
            },
            bpartners: { customerA: {}, customerB: {} },
            products: { "P1": { price: 1 } },
            shippers: { shipper: {} },
            warehouses: {
                "stockWH": { locators: { stockLocator: { isGroundLocator: true, priorityNo: 10 } } },
                "packingWH": {
                    autoDistributionOrder: true,
                    replenishment: { fromWarehouse: 'stockWH', shipper: 'shipper' },
                    locators: { packingLocator: {} },
                },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                packingWorkplace: { warehouse: 'packingWH', pickFromLocator: 'packingLocator' },
            },
            handlingUnits: {
                "stockHU": { product: 'P1', warehouse: 'stockWH', locator: 'stockLocator', qty: QTY_TOTAL },
            },
            salesOrders: {
                "SO_A": {
                    bpartner: 'customerA',
                    warehouse: 'packingWH',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{
                        product: 'P1',
                        qty: QTY_CUSTOMER_A,
                        schedules: [{ workplace: 'packingWorkplace', qty: QTY_CUSTOMER_A }],
                    }],
                },
                "SO_B": {
                    bpartner: 'customerB',
                    warehouse: 'packingWH',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{
                        product: 'P1',
                        qty: QTY_CUSTOMER_B,
                        schedules: [{ workplace: 'packingWorkplace', qty: QTY_CUSTOMER_B }],
                    }],
                },
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Two sales orders needing the same product are offered as ONE job carrying the summed quantity', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Aggregate replenishment orders by product');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.mover);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step(`Expect one job for ${QTY_CUSTOMER_A} + ${QTY_CUSTOMER_B} = ${QTY_TOTAL} instead of one job per sales order`, async () => {
        await DistributionJobsListScreen.expectHeaderProperty({ caption: 'Workplace', value: masterdata.workplaces.packingWorkplace.name });
        await DistributionJobsListScreen.expectJobButtons([{
            caption: masterdata.warehouses.stockWH.locators.stockLocator.code
                + " | " + masterdata.warehouses.packingWH.locators.packingLocator.code
                + " | " + masterdata.products.P1.productCode + "_" + masterdata.products.P1.productName
                + ` | ${QTY_TOTAL} Stk`
        }]);
    });
});
