import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from "../../utils/screens/picking/GetQuantityDialog";

/**
 * Picking profile "Don't create shipment" (DO_NOT_CREATE): once a sales order has nothing left to
 * pick it must NOT appear in the mobileUI picking launcher — there is nothing for the picker to do.
 * "Nothing left to pick" means QtyToDeliver <= 0, regardless of whether a shipment was ever created.
 *
 * - AC1 (the reported bug, me03 29437): pick FULLY + complete the job + NO shipment => launcher empty.
 * - AC2: a PARTIALLY-picked order (qty still open, QtyToDeliver > 0) => stays in the launcher.
 * - AC5: picked qty fully bound to a DRAFT shipment (the previously-merged fix's case) => not listed,
 *        subsumed by the general "nothing left to pick" rule.
 *
 * On the current (merged) code AC1 FAILS (RED): the merged fix only excludes the fully-on-draft case
 * (QtyToDeliver<=0 AND IsPickQtyOnDraftShipment='Y'); a completed, never-shipped order has
 * IsPickQtyOnDraftShipment='N' and therefore still shows. AC2 + AC5 already pass on the merged code.
 */

const createMasterdata = async ({ allowCompletingPartialPickingJob = false } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    // 'NO' == "Don't create shipment" (DO_NOT_CREATE) — masterdata enum for createShipmentPolicy.
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    // 12 PCE == exactly 3 TU (qtyCUsPerTU=4) — picks cleanly to whole TUs.
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
// AC1 — the reported bug. RED on the current merged code (no shipment => IsPickQtyOnDraftShipment='N').
test('DO_NOT_CREATE: fully-picked completed order with NO shipment must NOT appear in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('DO_NOT_CREATE picking — fully-picked completed order leaves the launcher (no shipment)');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const documentNo = masterdata.salesOrders.SO1.documentNo;

    // --- Pick all qty and complete in the mobileUI (mirrors the customer's manual flow exactly). ---
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.complete();
    await PickingJobsListScreen.waitForScreen();

    // createShipmentPolicy='NO' => completing the job creates NO shipment at all. The order is now
    // fully picked (QtyToDeliver=0) with nothing left to pick, so it MUST NOT be listed in the
    // picking launcher — there is nothing for the picker to do.
    await test.step("After completing the fully-picked job (no shipment), the order must NOT be in the picking list", async () => {
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.expectJobButtons([]);
    });
});

// noinspection JSUnusedLocalSymbols
// AC2 — no regression: a partial pick leaves QtyToDeliver > 0, so the order stays in the launcher.
test('DO_NOT_CREATE: a partially-picked order (qty still open) must STAY in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('DO_NOT_CREATE picking — partially-picked order stays in the launcher');
    allure.severity('critical');

    // allowCompletingPartialPickingJob=true so we can complete after picking a strict subset.
    const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: true });
    const documentNo = masterdata.salesOrders.SO1.documentNo;
    const salesOrderId = masterdata.salesOrders.SO1.id;

    // --- Pick a STRICT SUBSET (1 TU = 4 PCE out of the ordered 3 TU = 12 PCE) and complete partially. ---
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    // Pick only 1 TU out of 3; the remaining 2 TU are reported as not-found so the partial job can complete.
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        qtyEntered: 1,
        expectQtyEntered: '3',
        qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '1 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.complete();
    await PickingJobsListScreen.waitForScreen();

    // The order still has 8 PCE open => QtyToDeliver > 0 => there is still qty left to pick, so it
    // MUST remain in the picking launcher.
    await test.step("The partially-picked order must STILL be in the picking list", async () => {
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.expectJobButtons([{ salesOrderId }]);
    });
});

// noinspection JSUnusedLocalSymbols
// AC5 — the previously-merged fix's scenario: picked qty fully on a DRAFT shipment. Subsumed by the
// general "nothing left to pick" rule, so it must also stay out of the launcher (passes on both
// merged and fixed code — a regression guard, not RED).
test('DO_NOT_CREATE: order with picked qty fully on a DRAFT shipment must NOT appear in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('DO_NOT_CREATE picking — fully-on-draft-shipment order leaves the launcher');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const documentNo = masterdata.salesOrders.SO1.documentNo;

    // --- Pick all qty and complete in the mobileUI. ---
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.complete();
    await PickingJobsListScreen.waitForScreen();

    // --- Bind all picked qty to a DRAFT shipment line (quantityType='P', complete=false). ---
    await Backend.createDraftShipmentForOrder({ orderIdentifier: 'SO1' });

    // QtyToDeliver=0 and the picked qty is fully bound to a draft shipment => nothing left to pick =>
    // the order MUST NOT be listed in the picking launcher.
    await test.step("With the picked qty fully on a draft shipment, the order must NOT be in the picking list", async () => {
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.expectJobButtons([]);
    });
});
