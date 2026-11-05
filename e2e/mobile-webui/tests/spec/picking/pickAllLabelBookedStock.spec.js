import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { NewHUPreviewScreen } from '../../utils/screens/huManager/NewHUPreviewScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { expectErrorToast } from '../../utils/common';
import { uniqueGtin14 } from '../../utils/gtin';
import { bracketedLabel, BRACKETED_LABEL_LOT, FORMAT_BRACKETED_GTIN_PRODDATE_LOT } from '../../utils/huLabels';

// "Pick all" (profile flag IsAllowQuickPackAll) on stock that was booked in the HU Manager by scanning a product label:
// the pack booked by label is an ordinary Active HU, so Pick all takes the open order-line quantity out of it, without a scan.

const createMasterdata = async ({ gtin, orderQty }) => await Backend.createMasterdata({
    language: "en_US",
    request: {
        login: { user: { language: "en_US", workplace: "workplace1" } },
        mobileConfig: {
            picking: {
                aggregationType: "sales_order",
                createShipmentPolicy: 'CL',
                allowPickingAnyHU: true,
                allowQuickPackAll: true,
                shipOnCloseLU: false,
                pickTo: ['CU'],
                allowCompletingPartialPickingJob: false,
                allowPickingAnyCustomer: true,
            }
        },
        bpartners: { "customer1": {} },
        warehouses: { "wh": {} },
        pickingSlots: { slot1: {} },
        workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
        products: { "P1": { gtin, uom: 'PCE', price: 1 } },
        salesOrders: {
            "SO1": {
                bpartner: 'customer1',
                warehouse: 'wh',
                datePromised: '2025-03-01T00:00:00.000+02:00',
                lines: [{ product: 'P1', qty: orderQty }],
            }
        },
        customQRCodeFormats: [FORMAT_BRACKETED_GTIN_PRODDATE_LOT],
    }
});

/** book one pack of `qty` by scanning the label in the HU Manager; returns the new HU's id */
const bookPackByLabel = async ({ label, qty }) => {
    await HUManagerScreen.scanLabelAndWaitForPreview({ label });
    await NewHUPreviewScreen.setQty(qty);
    const created = await NewHUPreviewScreen.clickOKAndGetCreatedHU();
    await HUManagerScreen.waitForScreen();
    return created.huId;
};

const startPickingJob = async ({ masterdata }) => {
    await HUManagerScreen.goBack();
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    return await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
};

// noinspection JSUnusedLocalSymbols
test('Pick all takes the order quantity (5) out of one pack (10) booked by label; 5 stay in the pack, the picked HU carries the label lot', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230');
    allure.story('Pick All button');
    allure.severity('normal');

    const gtin = uniqueGtin14('8');
    const masterdata = await createMasterdata({ gtin, orderQty: 5 });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });
    const huId = await bookPackByLabel({ label: bracketedLabel(gtin), qty: 10 });
    await Backend.expect({ hus: { [huId]: { huStatus: 'A', storages: { P1: '10 PCE' }, cus: [{ qty: '10 PCE', attributes: { 'Lot-Nummer': BRACKETED_LABEL_LOT } }] } } });

    const { pickingJobId } = await startPickingJob({ masterdata });
    await PickingJobScreen.clickPickAllButton();
    await PickingJobsListScreen.expectJobButtons([]);

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "5 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLineId1' }] },
                },
            },
        },
        hus: {
            [huId]: { huStatus: 'A', storages: { P1: '5 PCE' } },
            vhu1: { huStatus: 'E', storages: { P1: '5 PCE' }, cus: [{ qty: '5 PCE', attributes: { 'Lot-Nummer': BRACKETED_LABEL_LOT } }] },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('KNOWN LIMITATION: line (15) larger than any single pack (2 x 10 booked by label) - Pick all fails, nothing picked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230');
    allure.story('Pick All button');
    allure.severity('normal');

    const gtin = uniqueGtin14('8');
    const masterdata = await createMasterdata({ gtin, orderQty: 15 });
    await HUManagerScreen.openAfterLogin({ user: masterdata.login.user });
    const huId1 = await bookPackByLabel({ label: bracketedLabel(gtin), qty: 10 });
    const huId2 = await bookPackByLabel({ label: bracketedLabel(gtin), qty: 10 });

    await startPickingJob({ masterdata });
    // Known limitation: Pick all fills each line from ONE pack only (the button is offered because the total stock, 20, covers 15).
    // When multi-pack auto-pick is implemented, change this case to expect 15 picked from both packs.
    await PickingJobScreen.expectPickAllButtonVisible();
    let pickAllResponse;
    await expectErrorToast('Pick all with a line larger than any single pack fails', async () => {
        pickAllResponse = await PickingJobScreen.tapPickAllButtonExpectingError();
    }, ({ textContent }) => {
        expect(textContent).toContain('Please try again');
    });
    // the generic toast is shared by every server error: pin the real server rejection
    expect(pickAllResponse.status()).toBe(422); // AdempiereException
    const errorMessage = JSON.stringify(await pickAllResponse.json());
    // the one pack the line is taken from (10) cannot cover the requested 15
    expect(errorMessage).toContain('Cannot fully unload all qty for AllocationRequest');
    expect(errorMessage).toContain('qty=15 Stk');
    expect(errorMessage).toContain('qtyAllocated=10');
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '15 Stk', qtyPicked: '0 Stk' });
    await Backend.expect({ hus: { [huId1]: { storages: { P1: '10 PCE' } }, [huId2]: { storages: { P1: '10 PCE' } } } });
});
