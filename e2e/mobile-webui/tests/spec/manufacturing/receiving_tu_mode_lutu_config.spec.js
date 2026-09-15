import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
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
            mobileConfig: {
                manufacturing: {
                    receiveUnitType: 'TU',
                },
            },
            bpartners: {
                "BP1": {},
            },
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    prices: [{ price: 1 }],
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
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 200 },
            },
            // No sales order — the PP_Order uses lutuConfigurationTU to set LUTU configuration directly
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 80,
                    lutuConfigurationTU: 'TU',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Receive in TU mode via LUTU configuration to a new TU target', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Receive main products in TU mode via LUTU configuration');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    // In TU mode, the receive button should show quantities in TUs:
    // 80 CUs / 4 CUs per TU = 20 TUs
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '20 TU',
        qtyReceived: '0 TU',
    });

    // Receive 5 TUs to a new TU target
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.expectHeaderProperty({ caption: 'Qty to Receive Target', value: '20 TU' });
    await MaterialReceiptLineScreen.expectHeaderProperty({ caption: 'Qty to Receive', value: '20 TU' });
    await MaterialReceiptLineScreen.expectHeaderProperty({ caption: 'Qty Received', value: '0 TU' });
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '20',
        qtyEntered: '5',
    });

    // Verify partial receive in TU mode
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '20 TU',
        qtyReceived: '5 TU',
    });

    await ManufacturingJobScreen.complete();

    // Backend validation: 5 TUs × 4 CUs/TU = 20 CUs
    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '4 PCE' },
                    { tu: 'tu2', qty: '4 PCE' },
                    { tu: 'tu3', qty: '4 PCE' },
                    { tu: 'tu4', qty: '4 PCE' },
                    { tu: 'tu5', qty: '4 PCE' },
                ]
            }
        },
    });
});
