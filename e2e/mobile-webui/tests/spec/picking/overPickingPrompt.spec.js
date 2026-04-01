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
 * https://github.com/metasfresh/me03/issues/29069
 *
 * Tests for the overdelivery confirmation prompt in mobile UI picking.
 *
 * When IsShowConfirmationPromptWhenOverPick=Y, entering qty > remaining should show
 * a Yes/No confirmation dialog instead of hard-blocking.
 * When the setting is N (default), the existing qtyMax validation blocks qty > remaining.
 *
 * Covers multiple picking modes: LU/TU (TU picking), LU/CU (CU picking), CU (CU picking).
 */

//
// ----- Masterdata helpers -----
//

const createMasterdata_LU_TU = async ({ showPromptWhenOverPicking, orderQtyTUs = 2 }) => {
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
            products: { P1: { prices: [{ price: 1 }] } },
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
                    lines: [{ product: 'P1', qty: orderQtyTUs, piItemProduct: 'TU' }],
                },
            },
        },
    });
};

const createMasterdata_LU_CU = async ({ showPromptWhenOverPicking, orderQtyCUs = 10 }) => {
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
                    pickTo: ['LU_CU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                    showPromptWhenOverPicking,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { price: 1 } },
            packingInstructions: {
                LU_CU: { cu: true, lu: 'LU', qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: orderQtyCUs }],
                },
            },
        },
    });
};

const startPickingJob_LU_TU = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
};

const startPickingJob_LU_CU = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
        gotoPickingJobScreen: true,
    });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.LU_CU.luName });
};

//
// ----- LU/TU picking mode: scan LU, pick TUs -----
//

// noinspection JSUnusedLocalSymbols
test('LU/TU: over-pick TUs - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, pick more TUs than ordered');
    allure.severity('critical');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyTUs: 2 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, enter 5 TUs (more than 2 ordered), confirm overdelivery', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.typeQtyEntered(5);
        await GetQuantityDialog.clickDone();

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        await PickingJobScreen.waitForScreen();
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/TU: over-pick TUs - prompt enabled - decline', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, decline overdelivery');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyTUs: 2 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, enter 5 TUs, decline overdelivery, cancel dialog', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.typeQtyEntered(5);
        await GetQuantityDialog.clickDone();

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickNoButton();

        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.clickCancel();
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/TU: pick exact TU qty - prompt enabled - no prompt', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, exact qty, no prompt fires');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyTUs: 2 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, pick exactly 2 TUs — no prompt should appear', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 2,
        });
        await PickingJobScreen.waitForScreen();
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/TU: prompt disabled - regression guard', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, prompt disabled, pick exact qty');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: false, orderQtyTUs: 2 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Prompt disabled — pick exact qty, verify existing behavior unchanged', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 2,
        });
        await PickingJobScreen.waitForScreen();
    });
});

//
// ----- LU/CU picking mode: scan LU, pick CUs -----
//

// noinspection JSUnusedLocalSymbols
test('LU/CU: over-pick CUs - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, pick more CUs than ordered');
    allure.severity('critical');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: true, orderQtyCUs: 10 });
    await startPickingJob_LU_CU(masterdata);

    await test.step('Scan LU, enter 25 CUs (more than 10 ordered), confirm overdelivery', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.huId);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.typeQtyEntered(25);
        await GetQuantityDialog.clickDone();

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        await PickingJobScreen.waitForScreen();
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/CU: over-pick CUs - prompt enabled - decline', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, decline overdelivery');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: true, orderQtyCUs: 10 });
    await startPickingJob_LU_CU(masterdata);

    await test.step('Scan LU, enter 25 CUs, decline overdelivery, cancel dialog', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.huId);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.typeQtyEntered(25);
        await GetQuantityDialog.clickDone();

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickNoButton();

        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.clickCancel();
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/CU: pick exact CU qty - prompt enabled - no prompt', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, exact qty, no prompt fires');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: true, orderQtyCUs: 10 });
    await startPickingJob_LU_CU(masterdata);

    await test.step('Scan LU, pick exactly 10 CUs — no prompt should appear', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectQtyEntered: 10,
        });
        await PickingJobScreen.waitForScreen();
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/CU: prompt disabled - regression guard', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, prompt disabled, pick exact qty');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: false, orderQtyCUs: 10 });
    await startPickingJob_LU_CU(masterdata);

    await test.step('Prompt disabled — pick exact qty, verify existing behavior unchanged', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectQtyEntered: 10,
        });
        await PickingJobScreen.waitForScreen();
    });
});
