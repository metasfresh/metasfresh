import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../../utils/screens/Backend';
import { LoginScreen } from '../../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../../utils/screens/picking/PickingJobLineScreen';
import { generateEAN13 } from '../../../utils/ean13';

// noinspection JSUnusedLocalSymbols
test('Expect PRODUCT_NO displayed on picking line', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Order based picking - fields configuration');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    anonymousPickHUsOnTheFly: false,
                    readAttributes: [],
                    fields: [
                        { field: 'PRODUCT_NO' },
                    ],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { price: 1, gtin: generateEAN13().ean13 } },
            packingInstructions: { "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 } },
            handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' } },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 1 }]
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });

    await test.step("Expect PRODUCT_NO in line screen header", async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.expectHeaderProperty({ caption: 'Product No', value: masterdata.products.P1.productCode });
        await PickingJobLineScreen.goBack();
    });

    await test.step("Pick product and verify line turns green", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });

        await PickingJobScreen.pickHU({
            isScanDirectly: true,
            expectedPickDirectly: true,
            qrCode: masterdata.products.P1.gtin,
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', waitForColor: 'green' });
    });

    await PickingJobScreen.complete();
});
