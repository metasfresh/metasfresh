import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';

// Swiss-German operator (me03#33141): the browser's UI language is de-CH. The context locale alone does not
// change how a number input treats the decimal separator, so Chromium itself is launched with --lang=de-CH.
// NOTE: launchOptions is replaced as a whole, so the base args of playwright.config.js are repeated here.
test.use({
    locale: 'de-CH',
    launchOptions: {
        args: [
            '--lang=de-CH',
            '--no-sandbox',
            '--unsafely-treat-insecure-origin-as-secure=http://app-test:8282',
            '--disable-features=StrictOriginPolicy,HttpsOnlyMode,BlockInsecurePrivateNetworkRequests',
            '--disable-site-isolation-trials',
            '--disable-web-security',
            '--ignore-certificate-errors',
            '--allow-insecure-localhost',
            '--allow-running-insecure-content',
            '--use-fake-ui-for-media-stream',
            '--use-fake-device-for-media-stream',
        ],
    },
});

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
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
            },
            products: {
                "P1": {
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }]
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 100, weightNet: 10, lotNo: 'lot1', bestBeforeDate: '2031-11-23' }
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

// Opens the picking job, scans HU1 and lands in the quantity dialog on manual input.
const startPickingAndOpenManualDialog = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await BarcodeScannerComponent.type({ scannedCode: masterdata.handlingUnits.HU1.qrCode });
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.clickManual();
    return { pickingJobId };
};

const expectCatchWeight1222Committed = async ({ masterdata, pickingJobId }) => {
    // Make-or-break, asserted FIRST: the value committed to the backend (what went over the wire).
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "7 PCE", catchWeight: "1.222 KGM", qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' },
                        ]
                    }
                }
            }
        },
    });
    // on-screen echo, only after the wire value is proven (its formatting depends on the browser locale)
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '7 Stk', qtyPickedCatchWeight: '1.222 kg' });
};

// noinspection JSUnusedLocalSymbols
test('Manual catch weight typed with decimal POINT on a Swiss-German browser', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Catch weight picking - Manual input, de-CH, decimal point');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const { pickingJobId } = await startPickingAndOpenManualDialog(masterdata);

    await GetQuantityDialog.typeQtyEntered('7');
    await GetQuantityDialog.typeCatchWeightKeystrokes('1.222');
    await GetQuantityDialog.expectDoneEnabled();
    await GetQuantityDialog.clickDone();
    await PickingJobScreen.waitForScreen();

    await expectCatchWeight1222Committed({ masterdata, pickingJobId });
});

// noinspection JSUnusedLocalSymbols
test('Manual catch weight typed with decimal COMMA on a Swiss-German browser', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Catch weight picking - Manual input, de-CH, decimal comma');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const { pickingJobId } = await startPickingAndOpenManualDialog(masterdata);

    await GetQuantityDialog.typeQtyEntered('7');
    await GetQuantityDialog.typeCatchWeightKeystrokes('1,222');
    await GetQuantityDialog.expectDoneEnabled();
    await GetQuantityDialog.clickDone();
    await PickingJobScreen.waitForScreen();

    await expectCatchWeight1222Committed({ masterdata, pickingJobId });
});

// noinspection JSUnusedLocalSymbols
test('Emptied catch weight cannot be confirmed on a Swiss-German browser', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Catch weight picking - Manual input, de-CH, empty weight (AC3)');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    await startPickingAndOpenManualDialog(masterdata);

    await GetQuantityDialog.typeQtyEntered('7');
    await GetQuantityDialog.typeCatchWeightKeystrokes('1.222');
    await GetQuantityDialog.clearCatchWeight();
    await GetQuantityDialog.expectDoneDisabled();
});
