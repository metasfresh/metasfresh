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
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';

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
// HU1 = LU with 20 TUs × 4 CUs = 80 CUs total.
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
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    return { pickingJobId };
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
                P1: { prices: [{ price: 1 }] },
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
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
        gotoPickingJobScreen: true,
    });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    return { pickingJobId };
};

//
// ===== LU/TU picking mode: scan LU, pick TUs =====
// Order: 12 CUs = 3 TUs (4 CUs per TU). LU has 20 TUs (80 CUs).
//

// noinspection JSUnusedLocalSymbols
test('LU/TU: over-pick TUs - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, pick more TUs than ordered');
    allure.severity('critical');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyCUs: 12 });
    const { pickingJobId } = await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, enter 8 TUs (more than 3 needed), confirm overdelivery', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(3); // UI suggests 3 TUs (12 CUs / 4 per TU)
        await GetQuantityDialog.typeQtyEntered(8);
        await GetQuantityDialog.clickDone();

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '8 TU', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: '32 PCE', qtyTUs: 8, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '48 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/TU: over-pick TUs - prompt enabled - decline', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, decline overdelivery');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyCUs: 12 });
    const { pickingJobId } = await startPickingJob_LU_TU(masterdata);

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

        await PickingJobScreen.waitForScreen();
    });

    await test.step('Verify nothing was picked', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [] },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '80 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/TU: pick exact TU qty - prompt enabled - no prompt', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, exact qty, no prompt fires');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: true, orderQtyCUs: 12 });
    const { pickingJobId } = await startPickingJob_LU_TU(masterdata);

    await test.step('Scan LU, pick exactly 3 TUs — no prompt should appear', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 3,
        });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: '12 PCE', qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '68 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/TU: prompt disabled - regression guard', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/TU mode, prompt disabled, over-entry blocked + exact qty pick');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_TU({ showPromptWhenOverPicking: false, orderQtyCUs: 12 });
    const { pickingJobId } = await startPickingJob_LU_TU(masterdata);

    await test.step('Prompt disabled — enter qty > remaining, Done button blocked, no prompt', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(3);
        await GetQuantityDialog.typeQtyEntered(8);
        await GetQuantityDialog.expectDoneDisabled();
        await GetQuantityDialog.expectQtyValidationError('above max');
        await GetQuantityDialog.clickCancel();

        await PickingJobScreen.waitForScreen();
    });

    await test.step('Prompt disabled — pick exact qty, verify existing behavior unchanged', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 3,
        });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: '12 PCE', qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '68 PCE' } },
            },
        });
    });
});

//
// ===== LU/CU picking mode: scan LU, pick CUs =====
// Order: 10 CUs. HU has 1000 CUs.
//

// noinspection JSUnusedLocalSymbols
test('LU/CU: over-pick CUs - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, pick more CUs than ordered');
    allure.severity('critical');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: true, orderQtyCUs: 10 });
    const { pickingJobId } = await startPickingJob_LU_CU(masterdata);

    await test.step('Scan LU, enter 25 CUs (more than 10 ordered), confirm overdelivery', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.huId);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(10); // UI suggests 10 CUs (remaining)
        await GetQuantityDialog.typeQtyEntered(25);
        await GetQuantityDialog.clickDone();

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '25 Stk', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: '25 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'vhu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '975 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/CU: over-pick CUs - prompt enabled - decline', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, decline overdelivery');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: true, orderQtyCUs: 10 });
    const { pickingJobId } = await startPickingJob_LU_CU(masterdata);

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

        await PickingJobScreen.waitForScreen();
    });

    await test.step('Verify nothing was picked', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [] },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '1000 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/CU: pick exact CU qty - prompt enabled - no prompt', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, exact qty, no prompt fires');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: true, orderQtyCUs: 10 });
    const { pickingJobId } = await startPickingJob_LU_CU(masterdata);

    await test.step('Scan LU, pick exactly 10 CUs — no prompt should appear', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectQtyEntered: 10,
        });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '10 Stk', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: '10 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'vhu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '990 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('LU/CU: prompt disabled - regression guard', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - LU/CU mode, prompt disabled, over-entry blocked + exact qty pick');
    allure.severity('normal');

    const masterdata = await createMasterdata_LU_CU({ showPromptWhenOverPicking: false, orderQtyCUs: 10 });
    const { pickingJobId } = await startPickingJob_LU_CU(masterdata);

    await test.step('Prompt disabled — enter qty > remaining, Done button blocked, no prompt', async () => {
        await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.huId);
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.expectQtyEntered(10);
        await GetQuantityDialog.typeQtyEntered(25);
        await GetQuantityDialog.expectDoneDisabled();
        await GetQuantityDialog.expectQtyValidationError('above max');
        await GetQuantityDialog.clickCancel();

        await PickingJobScreen.waitForScreen();
    });

    await test.step('Prompt disabled — pick exact qty, verify existing behavior unchanged', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectQtyEntered: 10,
        });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '10 Stk', qtyPicked: '10 Stk', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: '10 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'vhu1', lu: 'lu1', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                HU1: { huStatus: 'A', storages: { P1: '990 PCE' } },
            },
        });
    });
});

//
// ===== Whole-HU picking: the operator scans a weight label at the HU-scan step =====
// The label is not a unique HU QR code, so the whole scanned HU is picked and its quantity is
// decided from the HU's content — the operator never types a quantity.
// Order: 2 CUs. The scanned HU holds 3 CUs, so picking it whole exceeds the order by 1.
//

const createMasterdata_WholeHU = async ({ showPromptWhenOverPicking, orderQtyCUs = 2 }) => {
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
                    pickTo: ['TU'],
                    allowCompletingPartialPickingJob: true,
                    showLastPickedBestBeforeDateForLines: false,
                    showPromptWhenOverPicking,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                COMP1: {},
                COMP2: {},
                BOM: {
                    randomValue: { size: 4, isIncludeDigits: true },
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.1, isCatchUOMForProduct: true }],
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ],
                    },
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }],
                },
            },
            packingInstructions: {
                BOM_PI: { tu: 'TU', product: 'BOM', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU_COMP1: { product: 'COMP1', warehouse: 'wh', qty: 1000 },
                HU_COMP2: { product: 'COMP2', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                PP1: {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'BOM', qty: orderQtyCUs, piItemProduct: 'TU' }],
                },
            },
            customQRCodeFormats: [
                {
                    name: 'weight label',
                    parts: [
                        { startPosition: 1, endPosition: 4, type: 'PRODUCT_CODE' },
                        { startPosition: 5, endPosition: 10, type: 'WEIGHT_KG' },
                        { startPosition: 11, endPosition: 18, type: 'LOT' },
                        { startPosition: 19, endPosition: 24, type: 'PRODUCTION_DATE', dateFormat: 'yyMMdd' },
                        { startPosition: 25, endPosition: 30, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
                    ],
                },
            ],
        },
    });
};

// Produces one HU carrying `weightLabelQRCode`, filled with `qtyCUs` pieces.
const produceHU = async ({ masterdata, weightLabelQRCode, qtyCUs }) =>
    await test.step(`Produce one HU of ${qtyCUs} pieces, labelled ${weightLabelQRCode}`, async () => {
        await ApplicationsListScreen.startApplication('mfg');
        await ManufacturingJobsListScreen.waitForScreen();
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        const huQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({
            piTestId: masterdata.packingInstructions.BOM_PI.tuPITestId,
            numberOfHUs: 1,
        });

        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode });
        await MaterialReceiptLineScreen.receiveQty({
            catchWeightQRCode: Array(qtyCUs).fill(weightLabelQRCode),
            expectGoBackToJob: false,
        });
        await MaterialReceiptLineScreen.goBack();

        await ManufacturingJobScreen.goBack();
        await ManufacturingJobsListScreen.goBack();

        return huQRCode;
    });

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU holding more than ordered - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned at the HU-scan step, over-delivery confirmed');
    allure.severity('critical');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: true, orderQtyCUs: 2 });

    // 4 digits product code, 1.000 kg, lot 123, produced 2025-04-03, best before 2026-04-10
    const weightLabelQRCode = `${masterdata.products.BOM.productCode}00100000000123250403260410`;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    const huQRCode = await produceHU({ masterdata, weightLabelQRCode, qtyCUs: 3 });

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '0 Stk' });
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
    });

    await test.step('Scan the whole HU (3 pieces) against an order of 2, confirm the over-delivery', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCode);

        // The same over-delivery question the operator gets when typing a too-large quantity.
        // It can only appear if the comparison uses a quantity larger than the 2 pieces
        // remaining: neither the 0 the client sends today for a whole-HU pick nor the single
        // handling unit being scanned would exceed it — only the HU's 3 pieces do.
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '3 Stk' });
    });

    await test.step('Verify the whole HU was picked, i.e. the quantity the operator confirmed', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        BOM: {
                            qtyPicked: [{ qtyPicked: '3 PCE', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
            hus: {
                [huQRCode]: { huStatus: 'S', storages: { BOM: '3 PCE' } },
            },
        });
    });
});
