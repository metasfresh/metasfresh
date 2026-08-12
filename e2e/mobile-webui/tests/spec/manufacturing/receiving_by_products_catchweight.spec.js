import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

const createMasterdata = async ({ byProductInfinite = false } = {}) => {
    const byProductPI = byProductInfinite
        ? { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "BY_PRODUCT" }
        : { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "BY_PRODUCT", qtyCUsPerTU: 4 };

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
                "BY_PRODUCT": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                },
                "BOM": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                            { product: 'BY_PRODUCT', qty: -10, percentage: true, componentType: 'BY' },
                        ]
                    }
                },
            },
            packingInstructions: {
                "BOM_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
                "BY_PRODUCT_PI": byProductPI,
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
test('Receive several by-products using manual input to a new TU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
        allure.tag('F8030');  // Standalone tag for Tags section;
    allure.story('Receive by-products with catch weight');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., first by-product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BY_PRODUCT_PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({
        switchToManualInput: true,
        qtyEntered: 9,
        catchWeight: 0.987,
        expectGoBackToJob: true
    });
    await ManufacturingJobScreen.expectReceiveButton({
        index: 2,
        qtyToReceive: '10 Stk',
        qtyReceived: '9 Stk',
        //catchWeight: '987 g', // TODO figure out why this is not displayed!?
    })

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Receive several by-products using L+M codes to a new TU', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., first by-product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BY_PRODUCT_PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({
        catchWeightQRCode: [
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500'
        ],
        expectGoBackToJob: false
    });
    await MaterialReceiptLineScreen.goBack();

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Receive a by-product into an infinite-capacity TU using catch weight', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Infinite-capacity TU is selectable for catch-weight by-products');
    allure.severity('normal');

    const masterdata = await createMasterdata({ byProductInfinite: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., first by-product
    // The infinite-capacity TU (no qtyCUsPerTU on the PI) appears here only because the line is catch-weight.
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BY_PRODUCT_PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({
        switchToManualInput: true,
        qtyEntered: 9,
        catchWeight: 0.987,
        expectGoBackToJob: true,
    });

    await ManufacturingJobScreen.complete();
});

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
        await MaterialReceiptLineScreen.receiveQty({ catchWeightQRCode: 'LMQ#1#0.101#08.11.2025#500', expectGoBackToJob: false });
        await MaterialReceiptLineScreen.goBack();
        await Backend.expect({
            hus: {
                [byProductsQRCode]: {
                    storages: { 'BY_PRODUCT': '1 PCE' },
                    attributes: { 'WeightNet': '0.101' }
                }
            }
        });

        await ManufacturingJobScreen.complete();
        // await ManufacturingJobScreen.goBack();
    });

    await test.step("Receive on second manufacturing order in the same HU", async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP2.documentNo });

        await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., first by-product
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: byProductsQRCode });
        await MaterialReceiptLineScreen.receiveQty({ catchWeightQRCode: 'LMQ#1#0.030#09.11.2025#501', expectGoBackToJob: false });
        await MaterialReceiptLineScreen.goBack();
        await Backend.expect({
            hus: {
                [byProductsQRCode]: {
                    storages: { 'BY_PRODUCT': '2 PCE' },
                    attributes: { 'WeightNet': '0.131' }
                }
            }
        });

        await ManufacturingJobScreen.complete();
        // await ManufacturingJobScreen.goBack();
    });
});
