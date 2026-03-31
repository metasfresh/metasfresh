import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { page, FAST_ACTION_TIMEOUT } from "../../utils/common";
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { BarcodeScannerComponent } from "../../utils/components/BarcodeScannerComponent";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { PickingSlotScanScreen } from "../../utils/screens/picking/PickingSlotScanScreen";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { prices: [{ price: 1 }] } },
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
    });
};

test.describe('Barcode Scanner Input Modes', () => {

    // noinspection JSUnusedLocalSymbols
    test('scan barcode via keyboard events', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Barcode scanner keyboard input mode');
        allure.severity('critical');

        const masterdata = await createMasterdata();

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU' });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            isScanDirectly: true,
            expectQtyEntered: '3',
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });

        await PickingJobScreen.complete();
    });

    // noinspection JSUnusedLocalSymbols
    test('scan barcode via IME text injection', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Barcode scanner IME input mode (Zebra DataWedge)');
        allure.severity('critical');

        const masterdata = await createMasterdata();

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

        // Scan picking slot via IME
        await test.step('Scan picking slot via IME', async () => {
            await PickingJobScreen.clickPickingSlotButton();
            await PickingSlotScanScreen.waitForScreen();
            await BarcodeScannerComponent.typeViaIME(masterdata.pickingSlots.slot1.qrCode);
            await PickingJobScreen.waitForScreen();
            await PickingJobScreen.expectPickingSlotButtonGreen();
        });

        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU' });

        // Scan HU via IME
        await test.step('Pick HU via IME', async () => {
            await BarcodeScannerComponent.typeViaIME(masterdata.handlingUnits.HU1.qrCode);
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '3' });
            await PickingJobScreen.waitForScreen();
        });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });

        await PickingJobScreen.complete();
    });

    // Regression test for me03#28834: the hidden barcode scanner on the picking job screen
    // must use type="text" (not type="hidden") so that Zebra DataWedge IME mode can establish
    // an InputConnection and inject scanned text. type="hidden" inputs cannot receive focus,
    // causing DataWedge text injection to silently fail.
    // noinspection JSUnusedLocalSymbols
    test('hidden barcode scanner input is IME-compatible (not type=hidden)', async ({ page: _page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Quick-scan hidden input must be type=text for DataWedge IME');
        allure.severity('critical');

        const masterdata = await createMasterdata();

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

        // The BarcodeScannerButton renders a hidden BarcodeScannerComponent with isShowInputText=false.
        // Verify the input element is type="text" (IME-compatible) not type="hidden" (breaks IME).
        await test.step('Verify hidden scanner input is type=text and inputMode=none', async () => {
            const scannerInput = page.locator('#input-text');
            await scannerInput.waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
            await expect(scannerInput).toHaveAttribute('type', 'text');
            await expect(scannerInput).toHaveAttribute('inputmode', 'none');
        });
    });

});
