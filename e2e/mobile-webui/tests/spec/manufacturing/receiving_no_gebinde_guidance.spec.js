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

// me03/30334 — when the manufacturing goods-receipt step cannot offer a receiving Gebinde,
// the "Neues Gebinde" screen must show a clear, actionable guidance message instead of an
// empty target area. Verified trigger (observed 2026-06-15): the receive query
// (retrieveTUs) returns no usable TU packing for the product — here, the finished product
// has NO TU packing configured at all, so no receiving target can be offered.
// Assertions key off a stable data-testid (language-independent), never the DE/EN text.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // The guidance only appears while receiving without a packing instruction is switched off, so this
            // scenario states that precondition rather than leaning on the default.
            mobileConfig: { manufacturing: { receiveUnitType: 'TU', isAllowReceiveWithoutPackingItem: false } },
            warehouses: { "wh": {} },
            products: {
                "COMP1": {},
                "BOM": { bom: { lines: [{ product: 'COMP1', qty: 1 }] } },
            },
            // No packingInstructions for BOM -> retrieveTUs returns nothing -> no receiving target.
            handlingUnits: { "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 } },
            manufacturingOrders: {
                "PP1": { warehouse: 'wh', product: 'BOM', qty: 80, datePromised: '2025-03-01T00:00:00.000+02:00' }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('No receiving Gebinde -> guidance message instead of empty target area', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8030: MobileUI Manufacturing');
    allure.story('Goods receipt — no receiving Gebinde available');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });

    // Surface 2 (AC2): the receive-line screen shows WHY the quantity action is unavailable,
    // instead of a silently-disabled "Produzieren" button.
    await MaterialReceiptLineScreen.expectNoGebindeHintVisible();

    await MaterialReceiptLineScreen.clickReceiveTargetButton();
    await ReceiptReceiveTargetScreen.clickNewHUButton();

    // Precondition: this really is the dead-end — no receiving target is offered.
    await ReceiptNewHUScreen.expectNoTargetsOffered();

    // Surface 1 (AC1): the operator must see an actionable guidance message
    // (language-independent testId), not a blank target area. (RED until the fix adds it.)
    await ReceiptNewHUScreen.expectNoGebindeGuidanceVisible();
});
