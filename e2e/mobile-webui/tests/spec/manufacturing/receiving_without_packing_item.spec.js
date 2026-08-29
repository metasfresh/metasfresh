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

// Plants grown in the ground or in their own container sit in no GS1 packing structure at all, so the
// finished good has NO packing instruction. Without the profile flag below the mobile receive screen
// dead-ends — that case stays covered by receiving_no_gebinde_guidance.spec.js, which pins the flag OFF.
// With the flag on, the "No Packing Item" packing instruction
// is offered — the same choice the WebUI "Empfangen" process has always had — and the goods are
// received as a bare virtual HU.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                manufacturing: {
                    isAllowReceiveWithoutPackingItem: true,
                    // The three below are sticky mobileConfig fields written only when supplied, and sibling
                    // specs in this folder leave the values this scenario cannot run under:
                    // receiving_lu_only leaves TU receiving off (which also hides the virtual target, since it
                    // lives in the TU list), receiving_skip_target_step leaves the chooser skipped, and the
                    // receiving_tu_mode specs leave TU receive mode (whose quantities are counted in TUs -
                    // meaningless for goods in no packing structure). Pin each rather than inherit.
                    isAllowFinishedGoodsReceiveToTU: true,
                    isSkipFinishedGoodsReceiveTargetStep: false,
                    receiveUnitType: 'CU',
                },
            },
            warehouses: { "wh": {} },
            products: {
                "COMP1": {},
                "BOM": { bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
            },
            // Deliberately no packingInstructions for BOM: retrieveTUs returns nothing, so the only
            // target that can be offered is the virtual "No Packing Item" one.
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

    // A target IS available now, so the operator must not be told to go configure master data.
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

    // huType 'V' is the point of the whole scenario: the goods landed in a bare virtual HU, with no
    // TU and no pallet around them — the same result the WebUI "Empfangen" process produces.
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
