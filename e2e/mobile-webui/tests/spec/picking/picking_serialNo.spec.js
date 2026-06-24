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
// serialProduct=true  → IsSerialNoPicked=Y AND has the "Serial" attribute set (supports SerialNo)
//                       → mobile picking prompts to scan a serial and persists it on the picked HU.
// serialProduct=false → misconfig: IsSerialNoPicked=Y but NO SerialNo-capable attribute set
//                       → no prompt, no error (settled config-gap behaviour) → picks directly.
const createMasterdata = async ({ serialProduct, orderQty = 1 }) => {
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
                    ...(serialProduct ? { attributeSetName: 'Serial' } : {}),
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

    const masterdata = await createMasterdata({ serialProduct: true, orderQty: 3 });
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

test('Misconfigured serial-no product (no SerialNo attribute set): no prompt, picks directly', async ({ page: _page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Serial number scan when picking');
    allure.severity('normal');

    const masterdata = await createMasterdata({ serialProduct: false });
    await startPickingJob(masterdata);

    await test.step("Pick — qty dialog shows no serial control, pick succeeds with no error", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });
        // dialog still opens for the job-default attributes (BestBeforeDate/LotNo) — but NO serial control,
        // and confirm is not gated by a serial (config gap → silent no-prompt).
        await BarcodeScannerComponent.type(masterdata.products.P1.gtin);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectSerialNoNotVisible();
        await GetQuantityDialog.expectDoneEnabled();
        await GetQuantityDialog.clickDone();
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', waitForColor: 'green' });
    });
});
