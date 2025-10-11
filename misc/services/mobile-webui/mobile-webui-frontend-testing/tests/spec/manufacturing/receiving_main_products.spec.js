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
}

// noinspection JSUnusedLocalSymbols
test('Receive 1 full LU, 1 half LU', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId })
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '100',
    })

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '80 PCE' },
                    { lu: 'lu2', qty: '20 PCE' },
                ]
            }
        },
        hus: {
            'lu1': {
                huStatus: 'A',
                storages: { 'BOM': '80 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 20 }],
            },
            'lu2': {
                huStatus: 'A',
                storages: { 'BOM': '20 PCE' },
                tus: [{ isAggregatedTU: true, qtyTUs: 5 }],
            },
        }
    });
});

