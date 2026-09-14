import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { ReceiptNewHUScreen } from '../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';

// The manufacturing profile can switch off both the "new Gebinde vs. scan an existing one" chooser and
// receiving into a TU, so a pallet is the only kind of target left. When the finished good then has a
// single pallet packing, there is nothing left for the operator to choose - so nothing is asked: the
// pallet is taken and the operator goes straight on to the quantity.
//
// The second test is the control that keeps this honest: with TWO pallet packings under the very same
// profile the list is still shown, so the first test cannot be satisfied by simply never showing it.
const createMasterdata = async ({ packingInstructions }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                manufacturing: {
                    isAllowFinishedGoodsReceiveToLU: true,
                    isAllowFinishedGoodsReceiveToTU: false,
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
            packingInstructions,
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
test('A single pallet is taken without asking, and no target screen is shown at all', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Single pallet needs no choosing');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        packingInstructions: {
            "BOM_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product

    // Offering only one target is a deliberate simplification, not missing master data.
    await MaterialReceiptLineScreen.expectNoGebindeHintNotVisible();

    // Tapping the receive target returns the operator to the receive line with the pallet already set -
    // neither the chooser nor the packing-instruction list is shown.
    await MaterialReceiptLineScreen.clickReceiveTargetButtonExpectingTargetSelected({
        luName: masterdata.packingInstructions.BOM_PI.luName,
    });

    // The line is ready for the quantity right away.
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: 4,
        expectGoBackToJob: true,
    });
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '100 Stk',
        qtyReceived: '4 Stk',
    });

    // `lu` resolves only when the received HU sits inside a loading unit, and asserting the LU's
    // included TUs additionally pins its HU unit type to "loading unit".
    await Backend.expect({
        title: "The finished good was received onto the automatically taken pallet",
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
                tus: [
                    { storages: { 'BOM': '4 PCE' } },
                ],
            },
        }
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Two pallets are still offered as a list to choose from', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Single pallet needs no choosing');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        packingInstructions: {
            // Two pallet types for the same finished good: the same box, stacked 20 resp. 10 per pallet.
            "BOM_PI_TALL_PALLET": { lu: "LU_TALL", qtyTUsPerLU: 20, tu: "TU_TALL", product: "BOM", qtyCUsPerTU: 4 },
            "BOM_PI_LOW_PALLET": { lu: "LU_LOW", qtyTUsPerLU: 10, tu: "TU_LOW", product: "BOM", qtyCUsPerTU: 4 },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product

    // Two pallets means there IS something to choose, so the list appears (the chooser stays skipped).
    await MaterialReceiptLineScreen.clickReceiveTargetButtonExpectingNewHUScreen();
    await ReceiptNewHUScreen.expectLUTargetVisible({ luPIItemTestId: masterdata.packingInstructions.BOM_PI_TALL_PALLET.luPIItemTestId });
    await ReceiptNewHUScreen.expectLUTargetVisible({ luPIItemTestId: masterdata.packingInstructions.BOM_PI_LOW_PALLET.luPIItemTestId });

    // The pallet the operator taps is the one that ends up on the receive line.
    await ReceiptNewHUScreen.clickLUTarget({ luPIItemTestId: masterdata.packingInstructions.BOM_PI_LOW_PALLET.luPIItemTestId });
    await MaterialReceiptLineScreen.expectReceiveTargetButtonNames({ luName: masterdata.packingInstructions.BOM_PI_LOW_PALLET.luName });

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
        title: "The finished good was received onto the pallet the operator chose",
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
                tus: [
                    { storages: { 'BOM': '4 PCE' } },
                ],
            },
        }
    });

    await ManufacturingJobScreen.complete();
});
