import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "product",
                    allowPickingAnyCustomer: false,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    filterByQRCode: false,
                    anonymousPickHUsOnTheFly: false,
                    customers: [{ customer: "customer1" }],
                    fields: [
                        { field: 'CUSTOMER' },
                        { field: 'PRODUCT_NO' },
                        { field: 'PRODUCT_NAME' },
                    ]
                }
            },
            bpartners: {
                "customer1": { gln: 'random' },
            },
            warehouses: {
                "wh": {},
            },
            products: {
                "P1": { price: 1 },
                "P2": { price: 1 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20 },
                        { product: 'P2', qty: 21 },
                    ]
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Caption includes ProductNo', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Product based aggregation');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    await PickingJobsListScreen.expectJobButtons([
        { productId: masterdata.products.P1.id, qtyToDeliver: 20, caption: masterdata.products.P1.productCode + " | " + masterdata.products.P1.productName },
        { productId: masterdata.products.P2.id, qtyToDeliver: 21, caption: masterdata.products.P2.productCode + " | " + masterdata.products.P2.productName },
    ]);

});
