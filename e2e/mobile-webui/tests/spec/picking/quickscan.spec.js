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

test.describe('Picking Quick Scan', () => {

    // Quick-scan via keyboard events: scan HU QR code directly from the picking job screen
    // without pressing the "Scan" button. The system auto-resolves which line the HU belongs to.
    // noinspection JSUnusedLocalSymbols
    test('quick-scan HU via keyboard events', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Quick-scan HU from picking job screen (keyboard mode)');
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

    // Tests BarcodeScannerComponent in IME mode (Zebra DataWedge text injection).
    //
    // WHY THIS TEST EXISTS (me03#28834):
    // Zebra MC3300x scanners use DataWedge in IME/InputConnection mode. This means scanned
    // text is injected via Android's InputConnection.commitText(), NOT via keyboard events.
    // For InputConnection to work, the barcode input must be:
    //   - type="text" (NOT type="hidden" — hidden inputs can't receive focus/InputConnection)
    //   - inputMode="none" (suppresses virtual keyboard while keeping InputConnection alive)
    //
    // WHAT THIS TEST VALIDATES:
    // 1. Input element attributes: verifies the hidden scanner input on the picking job screen
    //    has type="text" and inputMode="none". This catches regressions where someone changes
    //    it back to type="hidden" (which would silently break DataWedge IME scanning).
    // 2. Functional flow: simulates IME text injection via typeViaIME() and verifies the
    //    full quick-scan path works (scan → auto-resolve line → pick dialog → pick).
    //
    // LIMITATION: typeViaIME() uses evaluate() to set the input value directly — this bypasses
    // the real Android InputConnection. The attribute assertions in step 1 are what actually
    // guard against the type="hidden" regression. Real InputConnection can only be tested
    // on hardware (manual test on Zebra MC3300x).
    //
    // noinspection JSUnusedLocalSymbols
    test('quick-scan HU via IME text injection', async ({ page: _page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Quick-scan HU from picking job screen (IME/DataWedge mode)');
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

        // Verify the hidden scanner input rendered by BarcodeScannerButton has IME-compatible
        // attributes. This is the critical regression guard — if someone reverts to type="hidden",
        // the typeViaIME() flow below would still pass (it bypasses InputConnection), but this
        // assertion would catch it.
        await test.step('Verify hidden scanner input is IME-compatible (type=text, inputMode=none)', async () => {
            const scannerInput = page.locator('#input-text');
            await scannerInput.waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
            await expect(scannerInput).toHaveAttribute('type', 'text');
            await expect(scannerInput).toHaveAttribute('inputmode', 'none');
        });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU' });

        // Scan HU via IME directly from the picking job screen (quick-scan without pressing "Scan" button)
        await test.step('Pick HU via IME (quick-scan from picking job screen)', async () => {
            await BarcodeScannerComponent.typeViaIME(masterdata.handlingUnits.HU1.qrCode);
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '3' });
            await PickingJobScreen.waitForScreen();
        });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });

        await PickingJobScreen.complete();
    });

});
