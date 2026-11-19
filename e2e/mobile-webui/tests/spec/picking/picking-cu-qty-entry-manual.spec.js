import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND } from "../../utils/screens/picking/GetQuantityDialog";
import { buildCULabelFormat, uniqueProductValue } from "../../utils/cuLabel";

/**
 * Characterization of the Manual mode (AC2): these tests PASS before and after the CU quantity entry change.
 * The mobile config never sends `cuQtyEntryMode` / `cuLabelFormatName`, so the profile stays Manual.
 * A test format with the marker parts is active (created, not linked to the profile) as a precondition; that Manual
 * does not read labels with it is covered by TC1d (jest), not here: no label is scanned in these tests.
 */
const createMasterdata = async ({ pickTo }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CO',
                    allowPickingAnyHU: true,
                    pickTo,
                    allowCompletingPartialPickingJob: true,
                    allowSkippingRejectedReason: true,
                }
            },
            customQRCodeFormats: [buildCULabelFormat({ name: `cuqe-manual-${uniqueProductValue()}` })],
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { price: 1 },
                "P2": { price: 1 },
                "P3": { price: 1 },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
                "PI3": { lu: "LU", qtyTUsPerLU: 20, tu: "TU3", product: "P3", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 200 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 200 },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 40, piItemProduct: 'TU' },   // 10 TU
                        { product: 'P2', qty: 40, piItemProduct: 'TU2' },  // 10 TU
                        { product: 'P3', qty: 40, piItemProduct: 'TU3' },  // 10 TU
                    ]
                }
            },
        }
    });
};

const openJob = async (masterdata, { withLU }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    if (withLU) {
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    } else {
        // without an LU target the slot scan opens the scan screen of line 1: go back to the job screen
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    }
    return { pickingJobId };
};

/** Opens the line on the scan screen (S5) and checks that Manual shows no CU-mode element. */
const openLineScanScreen = async ({ index }) => {
    await PickingJobScreen.clickLineButton({ index });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.expectNextBoxButtonHidden();
};

/** Scans the HU on the scan screen (S5), answers the quantity dialog, and goes back to the job screen (S2). */
const scanAndPick = async ({ qrCode, qtyEntered, expectQtyEntered, qtyNotFoundReason }) => {
    await PickLineScanScreen.typeQRCode(qrCode);
    await GetQuantityDialog.fillAndPressDone({ qtyEntered, expectQtyEntered, qtyNotFoundReason });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
};

// noinspection JSUnusedLocalSymbols
test('TC1a: Manual profile, line of 10 TU is picked as today', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Manual mode unchanged');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickTo: ['LU_TU'] });
    const { pickingJobId } = await openJob(masterdata, { withLU: true });

    await openLineScanScreen({ index: 1 });
    // dialog pre-filled with 10 TU, OK makes 10 TU
    await scanAndPick({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '10' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 TU', qtyPicked: '10 TU' });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "40 PCE", qtyTUs: 10, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    },
                    P2: {},
                    P3: {},
                }
            }
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC1b: Manual profile, partial pick asks a reason, "New TU" behaves as today', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Manual mode unchanged');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickTo: ['TU'] });
    const { pickingJobId } = await openJob(masterdata, { withLU: false });

    await test.step('Line 2: partial pick, 3 of 10 TU, reason "not found"', async () => {
        await openLineScanScreen({ index: 2 });
        await scanAndPick({
            qrCode: masterdata.handlingUnits.HU2.qrCode,
            qtyEntered: 3,
            expectQtyEntered: '10',
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
        });
        // partial with a reason: the line is yellow and the 7 TU not found count as rejected (the next scan pre-fills 0)
        await PickingJobScreen.expectLineButton({ index: 2, color: 'yellow', qtyToPick: '10 TU', qtyPicked: '3 TU' });
        await openLineScanScreen({ index: 2 });
        await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU2.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered('0');
        await GetQuantityDialog.clickCancel();
        await PickingJobLineScreen.goBack(); // Cancel closes the scan screen: back on the line screen
    });

    await test.step('Line 3: "New TU" target, then pick', async () => {
        await PickingJobScreen.setTargetTU({ tu: 'TU3' });
        await openLineScanScreen({ index: 3 });
        await scanAndPick({ qrCode: masterdata.handlingUnits.HU3.qrCode, expectQtyEntered: '10' });
        await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '10 TU', qtyPicked: '10 TU' });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {},
                    P2: {
                        // no box (TU) open: each OK of N TU makes N separate TUs (today's behaviour, F16)
                        qtyPicked: [
                            { qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu2a', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu2b', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu2c', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    },
                    P3: {
                        // "New TU" target set: all 10 TU of the line go into the one new TU (today's behaviour, F12 / F16)
                        qtyPicked: [{ qtyPicked: "40 PCE", qtyTUs: 1, qtyLUs: 0, vhu: '-', tu: 'tu3', lu: '-', processed: false, shipmentLineId: '-' }]
                    },
                }
            }
        },
    });
});
