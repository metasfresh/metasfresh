import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { YesNoDialog } from '../../utils/dialogs/YesNoDialog';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';

/**
 * Tests for the shelf-life (MHD / RLZ) warning dialog in mobile UI picking.
 *
 * When the mobile picking profile's IsWarnShelfLifeUndercut=Y and the picked HU's HU_BestBeforeDate
 * would undercut the customer's guaranteed shelf life, the backend throws RLZ_TooShort.
 * The mobile frontend shows a Yes/No confirmation dialog with the server message.
 * - Yes: confirms the pick, pick completes.
 * - No: aborts the pick (pick skipped toast).
 *
 * When the flag is off, or the shelf life is sufficient, the pick completes directly
 * without any dialog.
 *
 * Delivery date = C_Order.DatePromised (propagated to M_ShipmentSchedule.DeliveryDate_Effective).
 * Undercut condition: bestBeforeDate < deliveryDate + guaranteedDays
 *   where guaranteedDays = C_BPartner_Product.ShelfLifeMinDays (> 0) or M_Product.GuaranteeDaysMin.
 *
 * Fixture dates (UTC-safe, all in the future so the scheduler creates fresh schedules):
 *   datePromised = 2030-01-01  →  deliveryDate = 2030-01-01
 *   guaranteedDays = 30        →  threshold = 2030-01-31
 *   SHORT bestBeforeDate = 2030-01-15  (< 2030-01-31 → undercut)
 *   SUFFICIENT bestBeforeDate = 2030-03-01 (> 2030-01-31 → ok)
 */

//
// ----- Shared fixture dates -----
//
const DATE_PROMISED = '2030-01-01T00:00:00.000+01:00';
const GUARANTEED_DAYS = 30; // C_BPartner_Product.ShelfLifeMinDays
const BEST_BEFORE_SHORT = '2030-01-15'; // undercuts threshold (< 2030-01-31)
const BEST_BEFORE_SUFFICIENT = '2030-03-01'; // sufficient (> 2030-01-31)
const ORDER_QTY = 10;

//
// ----- Masterdata builder -----
// One LU containing ORDER_QTY CUs of P1 (LU/CU pick via the quantity dialog).
// warnShelfLifeUndercut controls the mobile picking-profile flag; the logged-in user is assigned to WP1.
// bestBeforeDate sets HU_BestBeforeDate on the HU.
//
const createMasterdata = async ({ warnShelfLifeUndercut, bestBeforeDate }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'WP1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                    warnShelfLifeUndercut,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            workplaces: { WP1: { warehouse: 'wh' } },
            pickingSlots: { slot1: {} },
            products: {
                P1: {
                    prices: [{ price: 1 }],
                    guaranteeDaysMin: GUARANTEED_DAYS,
                },
            },
            packingInstructions: {
                PI1: { tu: 'TU', product: 'P1', qtyCUsPerTU: 100, lu: 'LU', qtyTUsPerLU: 20 },
                LU_CU: { cu: true, lu: 'LU', qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: ORDER_QTY, packingInstructions: 'LU_CU', bestBeforeDate },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: DATE_PROMISED,
                    lines: [{ product: 'P1', qty: ORDER_QTY }],
                },
            },
        },
    });
};

const startPickingJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
        gotoPickingJobScreen: true,
    });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    return { pickingJobId };
};

//
// ===== Scenario (a): flag ON + short shelf life → RLZ dialog → confirm Yes → pick completes =====
//

// noinspection JSUnusedLocalSymbols
test('RLZ warning: flag ON, short shelf life → confirm Yes → pick completes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('MHD-Kontrolle — RLZ shelf-life warning dialog: confirm Yes');
    allure.severity('critical');

    const masterdata = await createMasterdata({ warnShelfLifeUndercut: true, bestBeforeDate: BEST_BEFORE_SHORT });
    const { pickingJobId } = await startPickingJob(masterdata);

    await test.step('Scan HU with undercut shelf life — RLZ dialog appears, confirm Yes — pick completes', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.huId);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(ORDER_QTY);
        // Pressing Done triggers the backend RLZ_TooShort error; the qty dialog stays open
        // underneath while the RLZ confirmation dialog appears on top.
        await GetQuantityDialog.clickDoneExpectingFollowupDialog();

        // Backend threw RLZ_TooShort → frontend shows the "Restlaufzeit unterschreitet …" confirmation dialog
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        // After confirmation the qty dialog closes, the pick completes and we return to the job screen
        await GetQuantityDialog.waitToClose();
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${ORDER_QTY} Stk`, qtyPicked: `${ORDER_QTY} Stk`, qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: `${ORDER_QTY} PCE`, qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'vhu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
        });
    });
});

//
// ===== Scenario (a2): flag ON + short shelf life → RLZ dialog → decline No → pick aborted =====
//

// noinspection JSUnusedLocalSymbols
test('RLZ warning: flag ON, short shelf life → decline No → pick aborted', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('MHD-Kontrolle — RLZ shelf-life warning dialog: decline No');
    allure.severity('normal');

    const masterdata = await createMasterdata({ warnShelfLifeUndercut: true, bestBeforeDate: BEST_BEFORE_SHORT });
    const { pickingJobId } = await startPickingJob(masterdata);

    await test.step('Scan HU with undercut shelf life — RLZ dialog appears, decline No — pick aborted', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.huId);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(ORDER_QTY);
        await GetQuantityDialog.clickDoneExpectingFollowupDialog();

        // Backend threw RLZ_TooShort → frontend shows the "Restlaufzeit unterschreitet …" confirmation dialog
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickNoButton();

        // Declining aborts the pick — the operator returns to the picking job screen, nothing picked.
        await PickingJobScreen.waitForScreen();
    });

    await test.step('Verify nothing was picked', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [] },
                    },
                },
            },
        });
    });
});

//
// ===== Scenario (b): flag ON + sufficient shelf life → no dialog, pick completes directly =====
//

// noinspection JSUnusedLocalSymbols
test('RLZ warning: flag ON, sufficient shelf life → no dialog, pick completes directly', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('MHD-Kontrolle — no RLZ dialog when shelf life is sufficient');
    allure.severity('normal');

    const masterdata = await createMasterdata({ warnShelfLifeUndercut: true, bestBeforeDate: BEST_BEFORE_SUFFICIENT });
    const { pickingJobId } = await startPickingJob(masterdata);

    await test.step('Scan HU with sufficient shelf life — no RLZ dialog, pick completes', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectQtyEntered: ORDER_QTY,
        });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${ORDER_QTY} Stk`, qtyPicked: `${ORDER_QTY} Stk`, qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: `${ORDER_QTY} PCE`, qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'vhu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
        });
    });
});

//
// ===== Scenario (c): flag OFF + short shelf life → no dialog, pick completes directly =====
//

// noinspection JSUnusedLocalSymbols
test('RLZ warning: flag OFF, short shelf life → no dialog, pick completes directly', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('MHD-Kontrolle — no RLZ dialog when IsWarnShelfLifeUndercut is off');
    allure.severity('normal');

    const masterdata = await createMasterdata({ warnShelfLifeUndercut: false, bestBeforeDate: BEST_BEFORE_SHORT });
    const { pickingJobId } = await startPickingJob(masterdata);

    await test.step('Scan HU with undercut shelf life but flag OFF — no dialog, pick completes', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectQtyEntered: ORDER_QTY,
        });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${ORDER_QTY} Stk`, qtyPicked: `${ORDER_QTY} Stk`, qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: `${ORDER_QTY} PCE`, qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'vhu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
        });
    });
});
