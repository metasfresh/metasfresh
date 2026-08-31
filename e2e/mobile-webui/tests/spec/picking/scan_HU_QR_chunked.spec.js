import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";

// Real-flow validation for the content-based scan-completion fix.
//
// PRODUCTION SYMPTOM: a long HU global QR code (HU#<v>#{…json…}) does not always reach the browser
// in a single burst — a hardware/wedge scanner can deliver it in chunks spread over several seconds.
// The previous keyboard reader completed a scan on the first inter-keystroke gap >= the debounce, so
// such a chunked code was split into unparseable fragments and picking failed with an intermittent
// "QR-Code nicht erkannt" ("QR code not recognised").
//
// The fix keeps a still-arriving recognised QR code (PARTIAL_SCAN) buffered across the gap and only
// force-completes it once its JSON payload closes (COMPLETE_SCAN). This test drives the REAL mobile
// picking flow and injects exactly that mid-scan gap, asserting the HU is recognised and the pick
// proceeds. It fails on the old gap-based reader (fragmented code => not recognised) and passes with
// the fix. The hook mechanic itself is covered exhaustively by the Jest suite
// (src/hooks/__tests__/useKeyboardBarcodeReader.test.js).

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { price: 1 } },
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
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick by scanning a long HU QR Code that arrives in chunks (mid-scan inter-keystroke gap)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scan HU barcodes');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    // The long HU global QR code (HU#<v>#{…json…}). Split it roughly in half so the gap lands INSIDE
    // the still-open JSON payload (first chunk is a PARTIAL_SCAN); the closing brace only arrives with
    // the second chunk.
    const huQrCode = masterdata.handlingUnits.HU1.qrCode;
    const gapAtIndex = Math.floor(huQrCode.length / 2);
    // Gap >= scanner debounce (barcodeScanner.inputText.debounceMillis, default 300ms) and well below
    // the hook's abandon deadline (>= 3000ms), so the in-flight partial QR is protected, not abandoned.
    const gapMs = 1200;

    await PickingJobScreen.pickHU({
        qrCode: huQrCode,
        isScanDirectly: true,
        gapAtIndex,
        gapMs,
        expectQtyEntered: '3'
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
        }
    });

    await PickingJobScreen.complete();
});
