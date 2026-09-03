import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { generateEAN13 } from '../../utils/ean13';

// Serial No Picking — enforce serial-number scan when packing serial-no products.
// The IsSerialNoPicked=Y checkbox ALONE drives the prompt: mobile picking prompts to scan a serial
// and persists it on the picked HU. The product's own attribute set is irrelevant — the picked HU's
// ability to store the SerialNo comes from the PI wiring (M_HU_PI_Attribute on the virtual PI),
// not from the product attribute set. So a serial-no product needs no `attributeSetName`.
const createMasterdata = async ({ orderQty = 1 } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // Serial-no scanning is a hardware-scanner task — disable camera mode so the scan
            // view shows only the hardware prompt (no camera toggle), matching the demo UX.
            sysconfigs: {
                "mobileui.frontend.barcodeScanner.mode.camera.enabled": "N",
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": {
                    price: 1,
                    gtin: generateEAN13().ean13,
                    isSerialNoPicked: true,
                },
            },
            packingInstructions: { "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 } },
            handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' } },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: orderQty }]
                }
            },
        }
    });
};

const startPickingJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    return { pickingJobId };
};

test('Serial-no product: scan one serial per picked unit (N of N), deduped, persisted comma-separated on the HU', async ({ page: _page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Serial number scan when picking');
    allure.severity('critical');

    const masterdata = await createMasterdata({ orderQty: 3 });
    const { pickingJobId } = await startPickingJob(masterdata);
    const ts = Date.now();
    const [s1, s2, s3] = [`SN-${ts}-1`, `SN-${ts}-2`, `SN-${ts}-3`];
    const expectedSerials = `${s1},${s2},${s3}`; // SerialNoSet keeps scan/insertion order

    await test.step("Pick qty 3 with one serial per unit", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '0 Stk', color: 'red' });

        // scanning the product opens GetQuantityDialog because the line's readAttributes contains SerialNo
        await BarcodeScannerComponent.type(masterdata.products.P1.gtin);
        await GetQuantityDialog.waitForDialog();

        // need 3 serials (one per unit) → confirm disabled until 3 distinct are scanned
        await GetQuantityDialog.expectSerialNoScanButtonVisible();
        await GetQuantityDialog.expectSerialNoCount({ scanned: 0, total: 3 });
        await GetQuantityDialog.expectDoneDisabled();

        // scan 2 → "2 of 3", still gated
        await GetQuantityDialog.scanSerialNos([s1, s2]);
        await GetQuantityDialog.expectSerialNoCount({ scanned: 2, total: 3 });
        await GetQuantityDialog.expectSerialNoChipCount(2);
        await GetQuantityDialog.expectDoneDisabled();

        // scan a duplicate of s1 → silently deduped, count unchanged, still gated
        await GetQuantityDialog.scanDuplicateSerialNo(s1);
        await GetQuantityDialog.expectSerialNoCount({ scanned: 2, total: 3 });
        await GetQuantityDialog.expectDoneDisabled();

        // scan the 3rd distinct → "3 of 3", confirm enabled
        await GetQuantityDialog.scanSerialNos([s3]);
        await GetQuantityDialog.expectSerialNoCount({ scanned: 3, total: 3 });
        await GetQuantityDialog.expectSerialNoChipCount(3);
        await GetQuantityDialog.expectDoneEnabled();
        await GetQuantityDialog.clickDone();

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '3 Stk', waitForColor: 'green' });

        // the picked HU carries all 3 serials comma-separated (the `pickings` block registers `vhu1`)
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "3 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '997 PCE' } },
                vhu1: { huStatus: 'S', storages: { P1: '3 PCE' }, attributes: { SerialNo: expectedSerials } },
            }
        });
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "3 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLine1' }] }
                }
            }
        },
        hus: {
            vhu1: { huStatus: 'E', storages: { P1: '3 PCE' }, attributes: { SerialNo: expectedSerials } },
        }
    });
});

test('Serial-no product with NO attribute set: checkbox alone still prompts, serial captured and persisted', async ({ page: _page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Serial number scan when picking');
    allure.severity('normal');

    // The product carries IsSerialNoPicked=Y but has NO attribute set (createMasterdata sets no `attributeSetName`).
    // New contract: the checkbox alone enables serial-no picking — the prompt STILL appears and the scanned serial
    // is persisted on the picked HU. The product attribute set is irrelevant; storage comes from the PI wiring.
    const masterdata = await createMasterdata({ orderQty: 1 });
    const { pickingJobId } = await startPickingJob(masterdata);
    const serial = `SN-${Date.now()}`;

    await test.step("Pick qty 1 — serial control IS shown, gated until one serial scanned, then persisted", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });

        await BarcodeScannerComponent.type(masterdata.products.P1.gtin);
        await GetQuantityDialog.waitForDialog();

        // checkbox-alone enables the serial prompt → control visible, confirm gated until 1 serial scanned
        await GetQuantityDialog.expectSerialNoScanButtonVisible();
        await GetQuantityDialog.expectSerialNoCount({ scanned: 0, total: 1 });
        await GetQuantityDialog.expectDoneDisabled();

        await GetQuantityDialog.scanSerialNos([serial]);
        await GetQuantityDialog.expectSerialNoCount({ scanned: 1, total: 1 });
        await GetQuantityDialog.expectSerialNoChipCount(1);
        await GetQuantityDialog.expectDoneEnabled();
        await GetQuantityDialog.clickDone();

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', waitForColor: 'green' });

        // the picked HU carries the scanned serial — proving storage works without a product attribute set
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '999 PCE' } },
                vhu1: { huStatus: 'S', storages: { P1: '1 PCE' }, attributes: { SerialNo: serial } },
            }
        });
    });

    // completion persists the serial through to the shipped HU — the serial is not lost at shipment time.
    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLine1' }] }
                }
            }
        },
        hus: {
            vhu1: { huStatus: 'E', storages: { P1: '1 PCE' }, attributes: { SerialNo: serial } },
        }
    });
});
