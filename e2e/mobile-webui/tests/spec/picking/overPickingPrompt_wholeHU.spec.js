import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { YesNoDialog } from '../../utils/dialogs/YesNoDialog';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';

/**
 * Whole-HU over-delivery confirmation in mobile UI picking.
 *
 * The operator scans a weight label at the HU-scan step rather than typing a quantity, so the
 * whole scanned HU is picked and its quantity comes from the HU's content. When that content
 * exceeds the remaining order quantity, the same Yes/No confirmation the per-piece CU path
 * raises must appear.
 *
 * Split out of overPickingPrompt.spec.js: this suite needs its own BOM/manufacturing masterdata
 * builder, and a spec file should read as "one setup, N tests exercising it" (e2e/CLAUDE.md).
 */
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

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU holding more than ordered - prompt enabled - decline', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned at the HU-scan step, over-delivery declined');
    allure.severity('normal');

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

    await test.step('Scan the whole HU (3 pieces) against an order of 2, decline the over-delivery', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCode);

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickNoButton();

        // Declining returns the operator to the very step the scan came from, ready to scan again.
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.goBack();
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '0 Stk' });
    });

    await test.step('Verify nothing was picked and the HU is untouched', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        BOM: { qtyPicked: [] },
                    },
                },
            },
            hus: {
                [huQRCode]: { huStatus: 'A', storages: { BOM: '3 PCE' } },
            },
        });
    });
});
