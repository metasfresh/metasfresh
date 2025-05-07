import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { expectErrorToast } from '../../utils/common';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {},
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BY_PRODUCT": {},
                "BY_PRODUCT2": {},
                "BOM": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                            { product: 'BY_PRODUCT', qty: -10, percentage: true, componentType: 'BY' },
                            { product: 'BY_PRODUCT2', qty: -10, percentage: true, componentType: 'BY' },
                        ]
                    }
                },
            },
            packingInstructions: {
                "BOM_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
                "BY_PRODUCT_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "BY_PRODUCT", qtyCUsPerTU: 4 },
                "BY_PRODUCT_PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU3", product: "BY_PRODUCT2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 1000 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
                "PP2": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Receive By-Products from 2 manufacturing orders into same HU', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    let byProductsQRCode;
    await test.step("Receive on first manufacturing order", async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        byProductsQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BY_PRODUCT_PI.tuPITestId, numberOfHUs: 1 });

        await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., first by-product
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: byProductsQRCode });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: 10, qtyEntered: 1 });
        await Backend.expect({
            hus: {
                [byProductsQRCode]: {
                    storages: { 'BY_PRODUCT': '1 PCE' },
                }
            }
        });

        await ManufacturingJobScreen.goBack();
    });

    await test.step("Receive on second manufacturing order in the same HU", async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP2.documentNo });

        await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., first by-product
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: byProductsQRCode });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: 10, qtyEntered: 2 });
        await Backend.expect({
            hus: {
                [byProductsQRCode]: {
                    storages: { 'BY_PRODUCT': '3 PCE' },
                }
            }
        });

        await ManufacturingJobScreen.goBack();
    });
});

// noinspection JSUnusedLocalSymbols
test('Fail receiving different By-Products from in same HU', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
    const byProductsQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BY_PRODUCT_PI.tuPITestId, numberOfHUs: 1 });

    await test.step("Receive BY_PRODUCT", async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // BY_PRODUCT
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: byProductsQRCode });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: 10, qtyEntered: 1 });
        await Backend.expect({
            hus: {
                [byProductsQRCode]: {
                    storages: { 'BY_PRODUCT': '1 PCE' },
                }
            }
        });
    });

    await test.step("Receive BY_PRODUCT2 in same HU", async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 3 }); // BY_PRODUCT2
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: byProductsQRCode });
        await expectErrorToast('Receiving different By-Products in the same HU shall not be allowed', async () => {
            await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: 10, qtyEntered: 1 });
        });
        await Backend.expect({
            hus: {
                [byProductsQRCode]: {
                    storages: { 'BY_PRODUCT': '1 PCE' },
                }
            }
        });
    });
});
