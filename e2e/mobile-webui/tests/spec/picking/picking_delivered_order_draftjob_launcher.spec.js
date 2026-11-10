import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from "../../utils/screens/picking/GetQuantityDialog";

/**
 * Mobile UI picking launcher: an order whose picking job is still DRAFTED (started in the mobile UI,
 * never completed) but whose shipment schedules have NOTHING LEFT TO PICK (the order was delivered
 * through another path, e.g. back-office "Generate shipments") must NOT show up in the launcher.
 *
 * A Drafted picking job over two fully-delivered shipment schedules (QtyToDeliver=0, QtyPickList=0,
 * Processed=Y) used to keep the order on the launcher forever. The "new orders" launcher path already
 * excludes such an order; the "already started" (drafted job) path had no equivalent guard.
 *
 * - TC1 (the reported bug): drafted job over fully-delivered schedules                       => NOT listed.
 * - TC2: drafted job over a partially-delivered schedule (qty still open)                    => STAYS listed.
 * - TC3: the "new orders" path (no drafted job): fully-picked + completed order              => NOT listed (unregressed).
 * - TC4: drafted job over two schedules, one delivered + one still to pick                   => STAYS listed.
 *
 * How the "drafted job over delivered schedules" state is reached (the way it arises in real life):
 *  1. start the picking job in the mobile UI and pick (the job stays Drafted: it is never completed),
 *  2. deliver the picked qty through the real "Generate shipments" process (Backend.generateShipments),
 *     which completes the shipment and lets the schedules flip to Processed=Y asynchronously,
 *  3. wait until the async work is done, then look at the launcher.
 * Picked qty exists on the job, so the shipment-completion hook (which only aborts jobs with NOTHING
 * picked) leaves the Drafted job alone.
 */

// How often the launcher is re-opened before an order is still called "listed": the schedules'
// Processed/QtyToDeliver flip is asynchronous (shipment schedule recompute), so one early look proves nothing.
const LAUNCHER_SETTLE_ATTEMPTS = 6;

const createMasterdata = async ({ twoLines = true, createShipmentPolicy = 'NO' } = {}) => {
    const lines = [{ product: 'P1', qty: 12, piItemProduct: 'TU1' }];
    if (twoLines) {
        lines.push({ product: 'P2', qty: 12, piItemProduct: 'TU2' });
    }

    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy,
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
                "P2": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI1": { lu: "LU", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI1' },
                "HU2": { product: 'P2', warehouse: 'wh', packingInstructions: 'PI2' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    // 12 PCE == exactly 3 TU (qtyCUsPerTU=4) per line.
                    lines
                }
            },
        }
    });
};

const openPickingLauncherFor = async (documentNo) => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
};

/** Start the job for the order in the mobile UI, pick the requested lines and leave WITHOUT completing it (the job stays Drafted). */
const startJobAndPick = async ({ masterdata, pickP1 = true, pickP2 = false, partialP1Tu = null }) => {
    const documentNo = masterdata.salesOrders.SO1.documentNo;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await openPickingLauncherFor(documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });

    if (pickP1) {
        if (partialP1Tu != null) {
            await PickingJobScreen.pickHU({
                qrCode: masterdata.handlingUnits.HU1.qrCode,
                qtyEntered: partialP1Tu,
                expectQtyEntered: '3',
                qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
            });
            await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: `${partialP1Tu} TU`, qtyPickedCatchWeight: '' });
        } else {
            await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
            await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
        }
    }
    if (pickP2) {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    }

    // Leave the job screen WITHOUT completing: the picking job stays Drafted.
    await PickingJobScreen.goBack();

    return { pickingJobId, documentNo };
};

/** Deliver the picked qty via the real "Generate shipments" process and wait until the async processing is done. */
const deliverPickedQtyBackOffice = async ({ masterdata, pickingJobId, expectedPickedP1, expectedPickedP2, expectP2NothingPicked = false }) => {
    const { newShipmentCount } = await Backend.generateShipments({
        salesOrderId: masterdata.salesOrders.SO1.id,
        quantityType: 'P',
        complete: true,
    });
    if (newShipmentCount < 1) {
        throw new Error(`Setup failed: Generate shipments created no shipment for order ${masterdata.salesOrders.SO1.documentNo}`);
    }

    // Backend.expect waits for the qty-picked records of the shipped qty to be processed.
    const shipmentSchedules = {};
    if (expectedPickedP1) shipmentSchedules.P1 = { qtyPicked: [{ qtyPicked: expectedPickedP1, processed: true }] };
    if (expectedPickedP2) shipmentSchedules.P2 = { qtyPicked: [{ qtyPicked: expectedPickedP2, processed: true }] };
    // every schedule of the job needs an expectation: on the two-line order P2 picked nothing
    if (expectP2NothingPicked) shipmentSchedules.P2 = { qtyPicked: [] };
    await Backend.expect({
        title: 'picked qty was delivered and its records are processed',
        pickings: { [pickingJobId]: { shipmentSchedules } },
    });
};

/** Re-open the launcher (like a picker coming back to the app) until the order is gone, or give up. */
const expectOrderNotInLauncher = async ({ documentNo }) => await test.step(`The order ${documentNo} must NOT be in the picking launcher`, async () => {
    let lastError;
    for (let attempt = 1; attempt <= LAUNCHER_SETTLE_ATTEMPTS; attempt++) {
        await PickingJobsListScreen.goBack();
        await openPickingLauncherFor(documentNo);
        try {
            await PickingJobsListScreen.expectNoJobButtons();
            return;
        } catch (e) {
            lastError = e;
        }
    }
    throw lastError;
});

const expectOrderInLauncher = async ({ documentNo, salesOrderId }) => await test.step(`The order ${documentNo} must STILL be in the picking launcher`, async () => {
    await PickingJobsListScreen.goBack();
    await openPickingLauncherFor(documentNo);
    await PickingJobsListScreen.expectJobButtons([{ salesOrderId }]);
});

// noinspection JSUnusedLocalSymbols
test('TC1: drafted picking job over fully-delivered schedules must NOT appear in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('TC1');
    allure.story('Picking launcher - drafted job over delivered order leaves the launcher');
    allure.severity('critical');

    const masterdata = await createMasterdata({ twoLines: true });
    const salesOrderId = masterdata.salesOrders.SO1.id;

    // The order has two lines. Pick BOTH fully, do not complete the job.
    const { pickingJobId, documentNo } = await startJobAndPick({ masterdata, pickP1: true, pickP2: true });

    // Control: while the order is still deliverable, the drafted job's order IS listed (so a later "gone" is meaningful).
    await test.step("Before delivery the drafted job's order is in the picking launcher", async () => {
        await PickingJobsListScreen.goBack();
        await openPickingLauncherFor(documentNo);
        await PickingJobsListScreen.expectJobButtons([{ salesOrderId }]);
    });

    // Deliver the whole order through another path; the drafted job is left behind (picked qty => no auto-abort).
    await deliverPickedQtyBackOffice({ masterdata, pickingJobId, expectedPickedP1: '12 PCE', expectedPickedP2: '12 PCE' });

    // Nothing is left to pick on any schedule of the drafted job => the order must be gone.
    await expectOrderNotInLauncher({ documentNo });
});

// noinspection JSUnusedLocalSymbols
test('TC2: drafted picking job over a partially-delivered schedule must STAY in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('TC2');
    allure.story('Picking launcher - drafted job with qty still open stays in the launcher');
    allure.severity('critical');

    const masterdata = await createMasterdata({ twoLines: false });
    const salesOrderId = masterdata.salesOrders.SO1.id;

    // Pick only 1 of the 3 TU, leave the job drafted, deliver just that 1 TU (4 PCE) => 8 PCE are still to pick.
    const { pickingJobId, documentNo } = await startJobAndPick({ masterdata, pickP1: true, pickP2: false, partialP1Tu: 1 });
    await deliverPickedQtyBackOffice({ masterdata, pickingJobId, expectedPickedP1: '4 PCE' });

    await expectOrderInLauncher({ documentNo, salesOrderId });
});

// noinspection JSUnusedLocalSymbols
test('TC3: new-orders path - fully-picked completed order (no drafted job left) must NOT appear in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('TC3');
    allure.story('Picking launcher - "new orders" path still excludes an order with nothing left to pick');
    allure.severity('critical');

    // Same assertion as picking_DO_NOT_CREATE_shipment_reappearance.spec.js (AC1): pick fully, complete the
    // job, createShipmentPolicy 'NO' => nothing left to pick and no drafted job => not listed.
    const masterdata = await createMasterdata({ twoLines: false, createShipmentPolicy: 'NO' });
    const documentNo = masterdata.salesOrders.SO1.documentNo;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await openPickingLauncherFor(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.complete();
    await PickingJobsListScreen.waitForScreen();

    await expectOrderNotInLauncher({ documentNo });
});

// noinspection JSUnusedLocalSymbols
test('TC4: drafted picking job over two schedules, one delivered and one still to pick, must STAY in the picking launcher', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('TC4');
    allure.story('Picking launcher - drafted job with mixed schedules stays in the launcher');
    allure.severity('critical');

    const masterdata = await createMasterdata({ twoLines: true });
    const salesOrderId = masterdata.salesOrders.SO1.id;

    // Pick only line 1 (P1) fully, leave the job drafted over BOTH schedules, deliver the picked P1 qty:
    // the P1 schedule has nothing left, the P2 schedule still has 12 PCE to pick.
    const { pickingJobId, documentNo } = await startJobAndPick({ masterdata, pickP1: true, pickP2: false });
    await deliverPickedQtyBackOffice({ masterdata, pickingJobId, expectedPickedP1: '12 PCE', expectP2NothingPicked: true });

    await expectOrderInLauncher({ documentNo, salesOrderId });
});
