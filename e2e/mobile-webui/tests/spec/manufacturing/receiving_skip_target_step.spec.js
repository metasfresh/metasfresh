import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { ReceiptNewHUScreen } from '../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';

// The manufacturing profile can skip the "new Gebinde vs. scan an existing one" step for the
// finished good, because the operator always receives onto a new pallet. Tapping the receive
// target then goes straight to the packing-instruction list.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                manufacturing: {
                    isSkipFinishedGoodsReceiveTargetStep: true,
                },
            },
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
test('Tapping the receive target goes straight to the packing instructions', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Skip the receive target step');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product

    // The packing-instruction list appears immediately; the new-Gebinde-vs-scan chooser is skipped.
    await MaterialReceiptLineScreen.clickReceiveTargetButtonExpectingNewHUScreen();
    await ReceiptNewHUScreen.expectLUTargetVisible({ luPIItemTestId: masterdata.packingInstructions.BOM_PI.luPIItemTestId });

    // Going back returns to the receive line, so the skipped chooser stays out of reach.
    // (With the step enabled - the default - going back lands on the chooser instead.)
    await ReceiptNewHUScreen.goBackToLineScreen();

    await MaterialReceiptLineScreen.clickReceiveTargetButtonExpectingNewHUScreen();
    await ReceiptNewHUScreen.clickLUTarget({ luPIItemTestId: masterdata.packingInstructions.BOM_PI.luPIItemTestId });

    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: 4,
        expectGoBackToJob: true,
    });
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '100 Stk',
        qtyReceived: '4 Stk',
    });

    await Backend.expect({
        title: "The finished good was received onto the chosen pallet",
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '4 PCE' },
                ]
            }
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
            },
        }
    });

    await ManufacturingJobScreen.complete();
});
