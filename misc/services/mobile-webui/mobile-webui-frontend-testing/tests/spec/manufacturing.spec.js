import { Backend } from '../utils/screens/Backend';
import { test } from '../../playwright.config';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

const createMasterdata = async ({ finishedProductUOMConfigs = {} }) => {
    const response = await Backend.createMasterdata({
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
                    ...finishedProductUOMConfigs,
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    })

    return {
        login: response.login.user,
        // tuPIItemProductTestId: response.packingInstructions.PI.tuPIItemProductTestId,
        luPIItemTestId: response.packingInstructions.PI.luPIItemTestId,
        documentNo: response.manufacturingOrders.PP1.documentNo,
        comp1_huQRCode: response.handlingUnits.HU_COMP1.qrCode,
        comp2_huQRCode: response.handlingUnits.HU_COMP2.qrCode,
    };
}

// noinspection JSUnusedLocalSymbols
test('Simple manufacturing test', async ({ page }) => {
    const {
        login,
        // tuPIItemProductTestId,
        luPIItemTestId,
        documentNo,
        comp1_huQRCode,
        comp2_huQRCode
    } = await createMasterdata({});

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp1_huQRCode, expectQtyEntered: '5' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickIssueButton({ index: 2 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp2_huQRCode, expectQtyEntered: '10' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId })
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '1',
    })

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Receive with catch weight', async ({ page }) => {
    const finishedProductUOMConfigs = {
        uom: "PCE",
        uomConversions: [
            { from: "PCE", to: "KGM", multiplyRate: 4, isCatchUOMForProduct: true }
        ],
    };
    const {
        login,
        luPIItemTestId,
        documentNo,
        comp1_huQRCode,
        comp2_huQRCode
    } = await createMasterdata({ finishedProductUOMConfigs });

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp1_huQRCode, expectQtyEntered: '5' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickIssueButton({ index: 2 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp2_huQRCode, expectQtyEntered: '10' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId });

    await MaterialReceiptLineScreen.receiveQtyWithQRCode({
        catchWeightQRCode: [
            '019121234559370931030008321004481124',
        ],
    });
    await MaterialReceiptLineScreen.expectVisible();
    await MaterialReceiptLineScreen.receiveQtyWithQRCode({
        catchWeightQRCode: [
            '019121234559370931030008321004481124',
        ],
    });
    await MaterialReceiptLineScreen.goBack();

    await ManufacturingJobScreen.complete();
});
