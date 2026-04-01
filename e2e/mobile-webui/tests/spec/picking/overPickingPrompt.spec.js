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
 * Covers LU/TU picking (scan LU, pick TUs) and LU/CU picking (scan LU, pick CUs).
 */

//
// ----- LU/TU masterdata: 4 CUs per TU, 20 TUs per LU -----
// Order qty is in CUs. qty=12 with 4 CUs/TU = 3 TUs to pick.
//

const createMasterdata_LU_TU = async ({ showPromptWhenOverPicking, orderQtyCUs = 12 }) => {
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
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: orderQtyCUs, piItemProduct: 'TU' }],
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

//
// ----- LU/CU masterdata: CU packing, LU with 1000 CUs -----
// Order qty is in CUs. qty=10, HU has 1000.
//

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
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                    showPromptWhenOverPicking,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { price: 1 },
            },
            packingInstructions: {
                PI1: { tu: 'TU', product: 'P1', qtyCUsPerTU: 100, lu: 'LU', qtyTUsPerLU: 20 },
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
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
};

//
// ===== LU/TU picking mode: scan LU, pick TUs =====
// Order: 12 CUs = 3 TUs (4 CUs per TU). LU has 20 TUs.
//

// noinspection JSUnusedLocalSymbols
test('LU/TU: over-pick TUs - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, pick more TUs than ordered');
    allure.severity('critical');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyCUs: 12 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, enter 8 TUs (more than 3 needed), confirm overdelivery', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(3); // UI suggests 3 TUs (12 CUs / 4 per TU)
        await GetQuantityDialog.typeQtyEntered(8);
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

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyCUs: 12 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, enter 8 TUs, decline overdelivery, cancel dialog', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(3);
        await GetQuantityDialog.typeQtyEntered(8);
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

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyCUs: 12 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, pick exactly 3 TUs — no prompt should appear', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 3,
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

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: false, orderQtyCUs: 12 });
    await startPickingJob_LU_TU(masterdata);

    await test.step('Prompt disabled — pick exact qty, verify existing behavior unchanged', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 3,
        });
        await PickingJobScreen.waitForScreen();
    });
});

//
// ===== LU/CU picking mode: scan LU, pick CUs =====
// Order: 10 CUs. HU has 1000 CUs.
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
        await GetQuantityDialog.expectQtyEntered(10); // UI suggests 10 CUs (remaining)
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
        await GetQuantityDialog.expectQtyEntered(10);
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
