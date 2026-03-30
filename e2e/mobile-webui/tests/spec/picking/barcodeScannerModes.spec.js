import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
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

    // Uses Chromium 104 (pre-:has() support) to simulate Android 11 WebView.
    // Download: curl -L -o /tmp/chromium-old.zip "https://www.googleapis.com/download/storage/v1/b/chromium-browser-snapshots/o/Linux_x64%2F1010000%2Fchrome-linux.zip?alt=media"
    // Extract: unzip -qo /tmp/chromium-old.zip -d /tmp/chromium-old
    const OLD_CHROMIUM_PATH = '/tmp/chromium-old/chrome-linux/chrome';

    // noinspection JSUnusedLocalSymbols
    test.describe('Older WebView without CSS :has() support', () => {
        test.skip(() => {
            try { require('fs').accessSync(OLD_CHROMIUM_PATH, require('fs').constants.X_OK); return false; }
            catch { return true; }
        }, `Chromium 104 not found at ${OLD_CHROMIUM_PATH} — see download instructions above`);

        test.use({
            launchOptions: {
                executablePath: OLD_CHROMIUM_PATH,
                args: [
                    '--no-sandbox',
                    '--unsafely-treat-insecure-origin-as-secure=http://app-test:8282',
                    '--disable-features=StrictOriginPolicy,HttpsOnlyMode,BlockInsecurePrivateNetworkRequests',
                    '--disable-site-isolation-trials',
                    '--disable-web-security',
                    '--ignore-certificate-errors',
                    '--allow-insecure-localhost',
                    '--allow-running-insecure-content',
                ],
            },
        });

        // noinspection JSUnusedLocalSymbols
        test('scan input field visible and scanning works', async ({ page }) => {
            allure.epic('E0105: Picking');
            allure.tag('F00230: MobileUI Picking');
            allure.tag('F00230');
            allure.story('Barcode scanner input visible on older WebViews (Android 11)');
            allure.severity('critical');

            const masterdata = await createMasterdata();

            await LoginScreen.login(masterdata.login.user);
            await ApplicationsListScreen.expectVisible();
            await ApplicationsListScreen.startApplication('picking');
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

            // Verify the barcode scanner container has the class-based selector (not relying on :has())
            await BarcodeScannerComponent.expectContainerHasClass('has-video');

            // Verify the input is visible and within the viewport
            await BarcodeScannerComponent.expectInputVisible();

            // Verify scanning still works
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
            await PickingJobScreen.expectPickingSlotButtonGreen();
        });
    });

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

});
