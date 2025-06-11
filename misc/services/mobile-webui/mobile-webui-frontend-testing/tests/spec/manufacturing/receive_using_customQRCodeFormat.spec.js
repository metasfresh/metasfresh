import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

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
                "BOM": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "BOM_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
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
            },
            customQRCodeFormats: [
                {
                    name: 'my custom code 1',
                    parts: [
                        { startPosition: 1, endPosition: 1, type: 'CONSTANT', constantValue: 'A' },
                        { startPosition: 2, endPosition: 7, type: 'PRODUCT_CODE' },
                        { startPosition: 8, endPosition: 8, type: 'CONSTANT', constantValue: 'G' },
                        { startPosition: 9, endPosition: 14, type: 'WEIGHT_KG', decimalPointPosition: 3 },
                        { startPosition: 15, endPosition: 15, type: 'CONSTANT', constantValue: 'C' },
                        { startPosition: 16, endPosition: 23, type: 'LOT' },
                        { startPosition: 24, endPosition: 24, type: 'CONSTANT', constantValue: 'E' },
                        { startPosition: 25, endPosition: 30, type: 'IGNORE' },
                    ],
                },
                {
                    name: 'my custom code 2',
                    parts: [
                        { startPosition: 1, endPosition: 4, type: 'PRODUCT_CODE' },
                        { startPosition: 5, endPosition: 10, type: 'WEIGHT_KG' },
                        { startPosition: 11, endPosition: 18, type: 'LOT' },
                        { startPosition: 19, endPosition: 24, type: 'IGNORE' },
                        { startPosition: 25, endPosition: 30, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
                    ],
                }
            ],
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Receive using custom QR Code format', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    const bomQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BOM_PI.tuPITestId, numberOfHUs: 1 });

    await test.step('Receive using custom code 1', async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: bomQRCode });
        // NOTE: the product code in the QR code is not validated because, at the moment, product codes from any type of barcode are not being validated.
        await MaterialReceiptLineScreen.receiveQty({ catchWeightQRCode: 'A593707G000384C05321124E000001', expectGoBackToJob: false });
        await MaterialReceiptLineScreen.goBack();
        await Backend.expect({
            hus: {
                [bomQRCode]: {
                    storages: { 'BOM': '1 PCE' },
                    attributes: { 'WeightNet': '0.384' }
                }
            }
        });
    });

    await test.step('Receive using custom code 2', async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: bomQRCode });
        // NOTE: the product code in the QR code is not validated because, at the moment, product codes from any type of barcode are not being validated.
        await MaterialReceiptLineScreen.receiveQty({ catchWeightQRCode: '100009999900000123250403260410', expectGoBackToJob: false });
        await MaterialReceiptLineScreen.goBack();
        await Backend.expect({
            hus: {
                [bomQRCode]: {
                    storages: { 'BOM': '2 PCE' },
                    attributes: { 'WeightNet': '100.383' } // 0.384 + 99.999
                }
            }
        });
    });

    await ManufacturingJobScreen.goBack();
});
