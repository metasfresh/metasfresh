/**
 * Playwright E2E - double-submit of the GRAI "Speichern" button must book the pick only ONCE.
 *
 * Regression: the inline GRAI capture of a GRAIRequired=Y customer sends the pick (qty +
 * GRAIs) from the "Speichern" button. Without an in-flight guard, a second tap while the first
 * `/picking/event` POST is still running fired a second, independent pick POST -> the line was
 * booked twice (over-delivery).
 *
 * The in-flight window is made deterministic with page.route: the FIRST pick event is sent to the
 * server but its response is HELD open; Speichern is then fired a second time while the first request
 * is pending; only then is the response released. Exactly ONE pick event may reach the server and
 * exactly ONE picked HU / M_ShipmentSchedule_QtyPicked may exist.
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickGraiScreen } from '../../utils/screens/picking/PickGraiScreen';

const TU_COUNT = 1;
const QTY_CUS_PER_TU = 4;
const PICK_EVENT_URL_PATTERN = '**/picking/event';

const createMasterdata = async ({ overpickAllowed = true } = {}) => await Backend.createMasterdata({
    language: 'en_US',
    request: {
        login: { user: { language: 'en_US' } },
        mobileConfig: {
            picking: {
                aggregationType: 'product',
                allowPickingAnyCustomer: true,
                createShipmentPolicy: 'CL',
                allowPickingAnyHU: true,
                shipOnCloseLU: false,
                pickTo: ['LU_TU'],
                allowCompletingPartialPickingJob: false,
                // true = over-pick allowed (server would not refuse a second booking);
                // false = over-pick blocked (server refuses the repeat booking)
                showPromptWhenOverPicking: overpickAllowed,
            },
        },
        bpartners: { BP1: { graiRequired: 'Y' } },
        warehouses: { wh: {} },
        pickingSlots: { slot1: {} },
        products: { P1: { prices: [{ price: 1 }] } },
        packingInstructions: {
            PI_MAIN: {
                lu: 'LU_MAIN',
                qtyTUsPerLU: 20,
                tu: 'TU_IFCO',
                product: 'P1',
                qtyCUsPerTU: QTY_CUS_PER_TU,
                graiMapping: true,
            },
        },
        handlingUnits: {
            HU_SOURCE: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_MAIN' },
        },
        salesOrders: {
            SO1: {
                bpartner: 'BP1',
                warehouse: 'wh',
                datePromised: '2025-03-01T00:00:00.000+02:00',
                lines: [{ product: 'P1', qty: TU_COUNT * QTY_CUS_PER_TU, piItemProduct: 'TU_IFCO' }],
            },
        },
    },
});

const runDoubleSubmitScenario = async (masterdata) => {
    const [companyPrefix, assetType] = masterdata.packingInstructions.PI_MAIN.grai.split('.');
    const grai = `${companyPrefix}.${assetType}.000001`;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_MAIN.luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU_SOURCE.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: String(TU_COUNT) });

    await PickGraiScreen.waitForScreen();
    await PickGraiScreen.scanGrai({ graiString: grai });
    await PickGraiScreen.expectCount({ scanned: TU_COUNT, total: TU_COUNT });
    await PickGraiScreen.expectSaveEnabled();

    // Hold the FIRST pick event's response open: the request reaches the server, the answer is withheld.
    const heldPick = await PickGraiScreen.holdNextPickEventResponse();

    await PickGraiScreen.clickSave();
    await heldPick.firstEventReachedServer;
    await PickGraiScreen.clickSaveAgainWhilePickInFlight();

    await heldPick.release();
    await PickingJobLineScreen.waitForScreen();

    expect(heldPick.getPickEventCount(), 'exactly one pick event must be sent').toBe(1);

    await Backend.expect({
        title: 'Exactly one picked HU / one QtyPicked, not two',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ vhu: 'vhu1', tu: 'tu1', lu: 'lu1' }] },
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('GRAI Speichern fired twice while the pick is in flight books the pick only once', async ({ page: _page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Speichern double-submit - in-flight guard');
    await allure.severity('critical');

    await runDoubleSubmitScenario(await createMasterdata());
});

// noinspection JSUnusedLocalSymbols
test('GRAI Speichern fired twice while the pick is in flight books once (over-pick blocked)', async ({ page: _page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F00230: MobileUI Picking');
    await allure.story('GRAI Speichern double-submit - in-flight guard, over-pick blocked');
    await allure.severity('critical');

    await runDoubleSubmitScenario(await createMasterdata({ overpickAllowed: false }));
});
