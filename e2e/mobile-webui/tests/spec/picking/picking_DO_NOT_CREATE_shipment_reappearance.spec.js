import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from "../../utils/screens/picking/GetQuantityDialog";

/**
 * With picking profile "Don't create shipment" (DO_NOT_CREATE), a fully-picked order whose
 * picked qty is bound to a DRAFT shipment must NOT appear in the mobileUI picking launcher:
 * there is nothing left to pick. It re-appears only if the shipment is reversed (the picked
 * qty becomes unbound again and the order genuinely has to be picked once more).
 *
 * The fix excludes a schedule from the launcher ONLY when QtyToDeliver <= 0 AND
 * IsPickQtyOnDraftShipment='Y' (fully on draft). The second test below guards the inverse:
 * a PARTIAL draft (some picked qty on a draft line, but order qty still open => QtyToDeliver > 0)
 * must STAY visible, because there is still qty left to pick.
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
test('DO_NOT_CREATE: completed order must NOT re-appear after a draft shipment is created', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('DO_NOT_CREATE picking — order must stay gone after draft shipment');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const documentNo = masterdata.salesOrders.SO1.documentNo;

    // --- Pick all qty and complete in the mobileUI ---
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.complete();
    await PickingJobsListScreen.waitForScreen();

    // After completing (createShipmentPolicy='NO' => no shipment created), the picked qty sits in
    // M_ShipmentSchedule_QtyPicked rows with M_InOutLine_ID=NULL, Processed='N'. The order is still
    // legitimately shown in the picking list (it still has to be shipped). We do NOT assert it gone
    // here — that is normal and unchanged by the fix.

    // --- Create a DRAFT shipment from the shipment schedule (quantityType='P', complete=false).
    //     This binds M_ShipmentSchedule_QtyPicked.M_InOutLine_ID with Processed='N' — picked qty
    //     fully assigned to a draft shipment line. ---
    await Backend.createDraftShipmentForOrder({ orderIdentifier: 'SO1' });

    // --- With all picked qty bound to the draft shipment line, the order has nothing left to pick
    //     and must NOT be listed in the mobileUI picking launcher. ---
    await test.step("After the draft shipment, the order must NOT be in the picking list", async () => {
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.expectJobButtons([]);
    });
});

// noinspection JSUnusedLocalSymbols
test('DO_NOT_CREATE: order with a PARTIAL draft shipment (qty still open) must STAY in the picking list', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('DO_NOT_CREATE picking — partial draft shipment keeps the order visible');
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
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo });

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

    // --- Create a DRAFT shipment for the picked subset (quantityType='P' ships the 4 PCE picked).
    //     This binds the picked qty to a draft shipment line (IsPickQtyOnDraftShipment='Y'), but the
    //     order still has 8 PCE open => QtyToDeliver > 0. This is the PARTIAL-draft case. ---
    await Backend.createDraftShipmentForOrder({ orderIdentifier: 'SO1' });

    // --- The order still has open qty to pick, so it MUST remain in the picking launcher.
    //     This is the inverse of the fully-on-draft exclusion guarded by the first test. ---
    await test.step("After the partial draft shipment, the order must STILL be in the picking list", async () => {
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(documentNo);
        await PickingJobsListScreen.expectJobButtons([{ salesOrderId }]);
    });
});
