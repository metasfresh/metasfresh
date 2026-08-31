import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// Carrier advise on picking, validated against a NO-GATEWAY IsApiCarrierAdvise shipper.
// With no ShipperGateway the carrier product is a fixed local default named after the shipper,
// carried on the picking job (the job-scoped source of truth — PackedHUCarrierAdviseService; the
// carrier is NOT synced to the shipment schedule). The advise is auto-requested on order completion,
// so the resolved carrier product is already shown in picking; the advise button re-runs it for the
// picked HUs. This spec asserts the MOBILE UI: the carrier-product caption renders and the advise
// button gates on IsApiCarrierAdvise across LU / TU / line-view pick targets. No backend
// schedule/order assert — with a no-gateway shipper the carrier product is a constant, so asserting
// its value would be a tautology.

const baseRequest = ({ pickTo, pickingSlotRequired = true }) => ({
    login: { user: { language: "en_US" } },
    mobileConfig: {
        picking: {
            aggregationType: "sales_order",
            allowPickingAnyCustomer: true,
            createShipmentPolicy: 'CL',
            allowPickingAnyHU: true,
            shipOnCloseLU: false,
            pickTo,
            allowCompletingPartialPickingJob: true,
            // "Kommissionierfach erforderlich" (IsPickingSlotRequired). When false, the Scan-Picking-Slot
            // step is dropped, so the line view is reachable without selecting a slot.
            pickingSlotRequired,
        }
    },
    bpartners: { "BP1": {} },
    warehouses: { "wh": {} },
    // Picking slot only when required (the line-view test runs with pickingSlotRequired=false → no slot).
    ...(pickingSlotRequired ? { pickingSlots: { slot1: {} } } : {}),
    shippers: { "carrier": { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
    products: { "P1": { price: 1 } },
    packingInstructions: {
        // LU/TU/CU all reachable: full TU = 100 CU, full LU = 20 TU.
        "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 20 },
        "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
    },
    handlingUnits: {
        "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
    },
    salesOrders: {
        "SO1": {
            bpartner: 'BP1',
            warehouse: 'wh',
            shipper: 'carrier',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            // one full TU worth (100 CU) so we always pick complete TUs
            lines: [{ product: 'P1', qty: 100 }],
        }
    },
});

const startJob = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    return pickingJobId;
};

const allureMeta = (story) => {
    allure.epic('E0360: Transport (Extralogistik)');
    allure.feature('F29099: nShift Interface');
    allure.tag('F29099');
    allure.story(story);
    allure.severity('normal');
};

// noinspection JSUnusedLocalSymbols
test('Carrier advise — pick into LU', async ({ page }) => {
    allureMeta('Carrier advise resolved when picking into an LU');

    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest({ pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'] }) });

    const pickingJobId = await startJob({ masterdata });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });

    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: 100 });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', qtyPickedCatchWeight: '' });

    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();
    await PickingJobScreen.clickAdviseCarrier();
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Carrier advise — pick into TU', async ({ page }) => {
    allureMeta('Carrier advise resolved when picking into a top-level TU');

    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest({ pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'] }) });

    const pickingJobId = await startJob({ masterdata });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });

    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: 100 });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', qtyPickedCatchWeight: '' });

    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();
    await PickingJobScreen.clickAdviseCarrier();
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Carrier advise — resolved and re-advisable from the line view', async ({ page }) => {
    allureMeta('Carrier advise shown and re-advisable from the line view');

    // The carrier-advise controls also render inside the line view: PickLineScreen renders
    // SelectCurrentLUTUButtons with a lineId. On a normal (sales-order) job the operator sets the
    // pick target at job level (which enables the line buttons), switches to the line, picks, and
    // re-advises from there — exercising the line-scoped SelectCurrentLUTUButtons rendering.
    // pickingSlotRequired=false → no picking slot to scan; the line view is reachable directly.
    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest({ pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'], pickingSlotRequired: false }) });

    await startJob({ masterdata });

    // Set the LU pick target at job level — this enables the line buttons (they are disabled without it).
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });

    // Switch to the line view; carrier advise is rendered here by SelectCurrentLUTUButtons (lineId set).
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();

    // Pick the HU from the line: Scan → qty dialog → a full pick returns to the line view.
    await PickingJobLineScreen.clickScanQRCodeButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 100 });
    await PickingJobLineScreen.waitForScreen();

    // The resolved carrier product is shown on the line, and the line-level advise button re-runs it.
    await PickingJobLineScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobLineScreen.expectAdviseCarrierButtonVisible();
    await PickingJobLineScreen.clickAdviseCarrier();
    await PickingJobLineScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });

    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();
});
