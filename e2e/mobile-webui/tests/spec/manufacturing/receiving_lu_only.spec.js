import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { ReceiptReceiveTargetScreen } from '../../utils/screens/manufacturing/receipt/ReceiptReceiveTargetScreen';
import { ReceiptNewHUScreen } from '../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';

// The manufacturing profile can switch off receiving the finished good into a TU, so the
// operator is only ever offered a pallet (LU). The finished good below has BOTH an LU and a TU
// packing configured, so a TU target would be offered were it not for the profile setting.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                manufacturing: {
                    isAllowFinishedGoodsReceiveToTU: false,
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
test('Only pallets are offered as receive target when receiving into a TU is switched off', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Receive main product to a pallet only');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product

    // Offering fewer target structures is a deliberate simplification, not missing master data:
    // the operator must not be told that no receiving Gebinde is available.
    await MaterialReceiptLineScreen.expectNoGebindeHintNotVisible();

    await MaterialReceiptLineScreen.clickReceiveTargetButton();
    await ReceiptReceiveTargetScreen.clickNewHUButton();

    await ReceiptNewHUScreen.expectNoGebindeGuidanceNotVisible();
    await ReceiptNewHUScreen.expectTUTargetNotPresent({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });
    await ReceiptNewHUScreen.expectLUTargetVisible({ luPIItemTestId: masterdata.packingInstructions.BOM_PI.luPIItemTestId });

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

    // `lu` resolves only when the received HU sits inside a loading unit, and asserting the LU's
    // included TUs additionally pins its HU unit type to "loading unit".
    await Backend.expect({
        title: "The finished good was received onto a pallet",
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
