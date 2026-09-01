import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { ReceiptReceiveTargetScreen } from '../../utils/screens/manufacturing/receipt/ReceiptReceiveTargetScreen';
import { ReceiptNewHUScreen, VIRTUAL_TU_TARGET_TESTID } from '../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';

// A finished good in no packing structure has no packing instruction, so without the flag the mobile
// receive screen dead-ends (covered by receiving_no_gebinde_guidance.spec.js). With it on, "No Packing
// Item" is offered — as the WebUI always has — and the goods are received as a bare virtual HU.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                manufacturing: {
                    isAllowReceiveWithoutPackingItem: true,
                },
            },
            warehouses: { "wh": {} },
            products: {
                "COMP1": {},
                "BOM": { bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
            },
            // No packingInstructions for BOM: retrieveTUs returns nothing, leaving only the virtual target.
            handlingUnits: { "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 } },
            manufacturingOrders: {
                "PP1": { warehouse: 'wh', product: 'BOM', qty: 80, datePromised: '2025-03-01T00:00:00.000+02:00' }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A product with no packing instruction is received via "No Packing Item"', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Goods receipt without a packing instruction');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product

    // A target is available, so no "configure the master data" guidance.
    await MaterialReceiptLineScreen.expectNoGebindeHintNotVisible();

    await MaterialReceiptLineScreen.clickReceiveTargetButton();
    await ReceiptReceiveTargetScreen.clickNewHUButton();

    await ReceiptNewHUScreen.expectNoGebindeGuidanceNotVisible();
    await ReceiptNewHUScreen.expectTUTargetVisible({ tuPIItemProductTestId: VIRTUAL_TU_TARGET_TESTID });
    await ReceiptNewHUScreen.clickTUTarget({ tuPIItemProductTestId: VIRTUAL_TU_TARGET_TESTID });

    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: 4,
        expectGoBackToJob: true,
    });
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '80 Stk',
        qtyReceived: '4 Stk',
    });

    // huType 'V' is the point: a bare virtual HU, no TU and no pallet — as the WebUI produces.
    await Backend.expect({
        title: "The finished good was received without any packing structure",
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { hu: 'vhu1', qty: '4 PCE' },
                ]
            }
        },
        hus: {
            'vhu1': {
                huType: 'V',
                storages: { 'BOM': '4 PCE' },
            },
        }
    });

    await ManufacturingJobScreen.complete();
});
