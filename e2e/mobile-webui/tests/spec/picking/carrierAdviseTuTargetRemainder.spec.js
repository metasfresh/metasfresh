import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

// Carrier-advise must stay previewable on a TU pick target even after an earlier TU was packed and
// shipped — i.e. when the current TU target is a fresh TU started for the order remainder. The advise
// is a property of the order's carrier, so the worker must be able to preview it before committing the
// remainder TU, exactly as on the first TU. (Validated against a NO-GATEWAY IsApiCarrierAdvise shipper:
// the carrier product is auto-created and named after the shipper.)

const baseRequest = {
    login: { user: { language: "en_US" } },
    mobileConfig: {
        picking: {
            aggregationType: "sales_order",
            allowPickingAnyCustomer: true,
            createShipmentPolicy: 'CL',
            allowPickingAnyHU: true,
            shipOnCloseLU: false,
            pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
            allowCompletingPartialPickingJob: true,
            // Under-picking one TU (100 of 200) leaves a rejected remainder; allow skipping the
            // rejected reason so the "ignore" option is offered AND pre-selected (Done not gated on
            // a manual Not-Found/Damaged choice) — the un-picked 100 stays to pick into the next TU.
            allowSkippingRejectedReason: true,
        }
    },
    bpartners: { "BP1": {} },
    warehouses: { "wh": {} },
    pickingSlots: { slot1: {} },
    shippers: { "carrier": { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
    products: { "P1": { price: 1 } },
    packingInstructions: {
        "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 20 },
        "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
    },
    handlingUnits: {
        "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
    },
    salesOrders: {
        "SO1": {
            bpartner: 'BP1', warehouse: 'wh', shipper: 'carrier',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            // two full TUs worth (200 CU) so one TU can be packed+shipped and a remainder TU remains
            lines: [{ product: 'P1', qty: 200 }],
        }
    },
};

test('Carrier advise — previewable on the remainder TU after the first TU was shipped', async ({ page }) => {
    allure.epic('E0360: Transport (Extralogistik)');
    allure.feature('F29099: nShift Interface');
    allure.tag('F29099');
    allure.story('Carrier advise stays previewable on a fresh TU target for the order remainder');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });

    // Pack ONE full TU (100 of the 200 ordered), leaving a 100-CU remainder still to pick — so the fresh TU
    // below is a genuine remainder target and the advise must stay previewable on it.
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, qtyEntered: '100' });
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();

    // Ship that first TU, then start a fresh TU for the order remainder (100 CU still to pick).
    await PickingJobScreen.closeTargetTU();
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    // The carrier advise must still be previewable on the remainder TU target.
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();
});
