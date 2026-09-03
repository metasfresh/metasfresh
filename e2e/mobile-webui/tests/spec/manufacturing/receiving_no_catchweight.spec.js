import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// The manufacturing profile can stop the production receipt from asking for the catch weight,
// for shops that weigh the goods later (at picking) instead. The finished good below IS a
// catch-weight product - its weight is simply not captured here.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                manufacturing: {
                    isCaptureCatchWeightAtReceipt: false,
                },
            },
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
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('A catch-weight product is received with the quantity only, without a weight', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Receive without capturing the catch weight');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });

    // The operator types the same single quantity a product without catch weight uses. Note that no
    // `switchToManualInput` is needed: when the weight IS captured the dialog opens on the weight
    // QR-code reader (see receiving_main_products_catchweight.spec.js), and here it does not.
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: 4,
        expectQtyInputVisible: true,
        expectCatchWeightVisible: false,
        expectGoBackToJob: true,
    });
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '100 Stk',
        qtyReceived: '4 Stk',
    });

    // The receipt booked the quantity and recorded no weight on the received Gebinde. Every HU carries
    // the WeightNet attribute, so "no weight captured" is that attribute still sitting at zero -
    // under the default profile the very same receipt writes the scanned/typed weight into it
    // (receiving_main_products_catchweight.spec.js asserts '0.303' on its TU).
    await Backend.expect({
        title: "The received TU carries the quantity but no weight",
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '4 PCE' },
                ]
            }
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '4 PCE' },
                attributes: { 'WeightNet': '0.000' },
            },
        }
    });

    await ManufacturingJobScreen.complete();
});
