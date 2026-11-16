import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// LU packing instruction "LU" holds IFCO-like TUs:
// * generic LU item: 30 TUs per LU
// * LU item bound to product "XY": 40 TUs per LU
// Product "OTHER" has no product-specific LU item and shares the same LU and TU packing instructions.
// Each TU holds 4 pieces.
// Only the manufacturing order of the given product is created: the mobile job list is capped (~20 orders).
const createMasterdata = async ({ orderProduct }) => {
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
                "XY": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
                "OTHER": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "PI": {
                    lu: "LU", qtyTUsPerLU: 30, tu: "TU", product: "XY", qtyCUsPerTU: 4,
                    additionalLUItems: [{ product: "XY", qtyTUsPerLU: 40 }],
                },
                // Same LU and TU packing instructions (reused by identifier) for another product, without a product-specific LU item.
                // Note: this creates a second Material item in the same TU packing instruction version, which the receipt does not mind.
                "PI_OTHER": { tu: "TU", product: "OTHER", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 2000 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 2000 },
            },
            manufacturingOrders: {
                "PP": {
                    warehouse: 'wh',
                    product: orderProduct,
                    qty: orderProduct === 'XY' ? 320 : 360, // 80 TUs of XY, 90 TUs of OTHER
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Product with its own LU item: only the product-specific LU target is offered, LUs hold 40 TUs', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.story('Receive main products to LUs');
    allure.severity('normal');

    const masterdata = await createMasterdata({ orderProduct: 'XY' });
    const genericLUTestId = masterdata.packingInstructions.PI.luPIItemTestId;
    const xyLUTestId = masterdata.packingInstructions.PI.additionalLUPIItemTestIds[0];

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({
        luPIItemTestId: xyLUTestId,
        expectNotOfferedLUPIItemTestIds: [genericLUTestId],
    });
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '320',
        qtyEntered: '320',
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '160 PCE' },
                    { lu: 'lu2', qty: '160 PCE' },
                ]
            }
        },
        hus: {
            'lu1': {
                huStatus: 'A',
                storages: { 'XY': '160 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 40 }],
            },
            'lu2': {
                huStatus: 'A',
                storages: { 'XY': '160 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 40 }],
            },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Product without its own LU item: generic LU target is offered, LUs hold 30 TUs', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.story('Receive main products to LUs');
    allure.severity('normal');

    const masterdata = await createMasterdata({ orderProduct: 'OTHER' });
    const genericLUTestId = masterdata.packingInstructions.PI.luPIItemTestId;
    const xyLUTestId = masterdata.packingInstructions.PI.additionalLUPIItemTestIds[0];

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({
        luPIItemTestId: genericLUTestId,
        expectNotOfferedLUPIItemTestIds: [xyLUTestId],
    });
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '360',
        qtyEntered: '360',
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '120 PCE' },
                    { lu: 'lu2', qty: '120 PCE' },
                    { lu: 'lu3', qty: '120 PCE' },
                ]
            }
        },
        hus: {
            'lu1': {
                huStatus: 'A',
                storages: { 'OTHER': '120 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 30 }],
            },
            'lu2': {
                huStatus: 'A',
                storages: { 'OTHER': '120 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 30 }],
            },
            'lu3': {
                huStatus: 'A',
                storages: { 'OTHER': '120 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 30 }],
            },
        }
    });
});
