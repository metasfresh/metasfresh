import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { YesNoDialog } from '../../utils/dialogs/YesNoDialog';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';

/**
 * gh#29069: Tests for the overdelivery confirmation prompt in mobile UI picking.
 *
 * When IsShowConfirmationPromptWhenOverPick=Y, entering qty > remaining should show a
 * Yes/No confirmation dialog instead of hard-blocking. When the setting is N (default),
 * entering qty > remaining should show a validation error (existing behavior).
 */

const createMasterdata = async ({ showPromptWhenOverPicking }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                    showPromptWhenOverPicking,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 100 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 10, piItemProduct: 'TU' }],
                },
            },
        },
    });
};

const startPickingJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
};

// noinspection JSUnusedLocalSymbols
test('CU manual entry - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - CU manual entry, confirm Yes');
    allure.severity('critical');

    const masterdata = await createMasterdata({ showPromptWhenOverPicking: true });
    await startPickingJob(masterdata);

    await test.step('Pick with qty > remaining, confirm overdelivery', async () => {
        // Scan the HU
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();

        // Enter qty > remaining (15 > 10)
        await GetQuantityDialog.typeQtyEntered(15);
        await GetQuantityDialog.clickDone();

        // The overdelivery confirmation prompt should appear
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        // Pick should succeed — back to picking job screen
        await PickingJobScreen.waitForScreen();
    });
});

// noinspection JSUnusedLocalSymbols
test('CU manual entry - prompt enabled - decline', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - CU manual entry, decline');
    allure.severity('normal');

    const masterdata = await createMasterdata({ showPromptWhenOverPicking: true });
    await startPickingJob(masterdata);

    await test.step('Pick with qty > remaining, decline, then cancel', async () => {
        // Scan the HU
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();

        // Enter qty > remaining (15 > 10)
        await GetQuantityDialog.typeQtyEntered(15);
        await GetQuantityDialog.clickDone();

        // Decline the overdelivery
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickNoButton();

        // Should return to the GetQuantityDialog (not closed)
        await GetQuantityDialog.waitForDialog();

        // Cancel the dialog to go back
        await GetQuantityDialog.clickCancel();
    });
});

// noinspection JSUnusedLocalSymbols
test('CU manual entry - prompt enabled - qty equal to remaining - no prompt', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - qty equal to remaining, no prompt');
    allure.severity('normal');

    const masterdata = await createMasterdata({ showPromptWhenOverPicking: true });
    await startPickingJob(masterdata);

    await test.step('Pick with qty == remaining, no prompt should appear', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
        });
        await PickingJobScreen.waitForScreen();
    });
});
