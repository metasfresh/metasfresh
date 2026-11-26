import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { expectErrorToast } from '../../utils/common';
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
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * Whole-HU over-delivery confirmation in mobile UI picking.
 *
 * The operator scans a weight label at the HU-scan step rather than typing a quantity, so the
 * whole scanned HU is picked and its quantity comes from the HU's content. When that content
 * exceeds the remaining order quantity, the same Yes/No confirmation the per-piece CU path
 * raises must appear.
 *
 * This suite has its own spec file because it needs its own BOM/manufacturing masterdata builder, and
 * a spec file should read as "one setup, N tests exercising it" (e2e/CLAUDE.md).
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

const blockTheOverDeliveryAtTheScanStep = async ({ weightLabelQRCode }) =>
    await test.step('Scan the whole HU (3 pieces) against an order of 2 - the scan is rejected', async () => {
        await PickLineScanScreen.waitForScreen();

        // Without the confirmation prompt the operator gets no way to authorise an over-delivery,
        // so the same qtyAboveMax ceiling the per-piece CU path enforces applies here: the scan
        // fails and books nothing, rather than silently booking the whole 3-piece HU.
        await expectErrorToast(
            'Whole HU exceeds the ordered qty and the prompt is disabled',
            async () => {
                await PickLineScanScreen.typeQRCode(weightLabelQRCode);
                // Reached only if the pick went through - a booked pick leaves the scan screen
                // for the job screen. Blocked, the operator stays on the scan screen.
                await PickingJobScreen.waitForScreen();
            },
            ({ textContent }) => {
                expect(textContent).toContain('above max');
            }
        );
    });

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU holding more than ordered - prompt disabled - the pick is blocked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned at the HU-scan step, prompt disabled, over-delivery blocked');
    allure.severity('critical');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: false, orderQtyCUs: 2 });

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

    await blockTheOverDeliveryAtTheScanStep({ weightLabelQRCode });

    await test.step('Verify nothing was picked and the HU is untouched', async () => {
        await PickLineScanScreen.goBack();
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '0 Stk' });

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

//
// ===== Exactly on target: the HU holds exactly the 3 pieces still ordered =====
// The negative control for both guards above: neither the confirmation nor the ceiling may fire when
// the scanned HU does not exceed the order. Run in both profile configurations, because with the
// prompt on and off the whole-TU path takes two different branches.
//

const pickWholeHUAndExpectNoQuestion = async ({ masterdata, huQRCode, weightLabelQRCode }) =>
    await test.step('Scan the whole HU (3 pieces) against an order of 3', async () => {
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        const { pickingJobId } = await PickingJobsListScreen.startJob({
            documentNo: masterdata.salesOrders.SO1.documentNo,
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '0 Stk' });
        await PickingJobScreen.scanPickingSlot({
            qrCode: masterdata.pickingSlots.slot1.qrCode,
            expectNextScreen: 'PickLineScanScreen',
        });

        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCode);

        // Landing back on the job screen with the line fully picked is what proves the scan was
        // neither questioned nor rejected: a confirmation would hold the operator on the scan screen,
        // and a rejected scan would leave the line at 0.
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '3 Stk' });

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

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU holding exactly the ordered qty - prompt enabled - no question asked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned at the HU-scan step, exactly on target, no prompt fires');
    allure.severity('normal');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: true, orderQtyCUs: 3 });

    // 4 digits product code, 1.000 kg, lot 123, produced 2025-04-03, best before 2026-04-10
    const weightLabelQRCode = `${masterdata.products.BOM.productCode}00100000000123250403260410`;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    const huQRCode = await produceHU({ masterdata, weightLabelQRCode, qtyCUs: 3 });
    await ApplicationsListScreen.expectVisible();

    await pickWholeHUAndExpectNoQuestion({ masterdata, huQRCode, weightLabelQRCode });
});

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU holding exactly the ordered qty - prompt disabled - not blocked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned at the HU-scan step, exactly on target, ceiling does not block');
    allure.severity('normal');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: false, orderQtyCUs: 3 });

    // 4 digits product code, 1.000 kg, lot 123, produced 2025-04-03, best before 2026-04-10
    const weightLabelQRCode = `${masterdata.products.BOM.productCode}00100000000123250403260410`;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    const huQRCode = await produceHU({ masterdata, weightLabelQRCode, qtyCUs: 3 });
    await ApplicationsListScreen.expectVisible();

    await pickWholeHUAndExpectNoQuestion({ masterdata, huQRCode, weightLabelQRCode });
});

//
// ===== Catch weight far above nominal, piece count exactly on target =====
// Only the counted quantity is guarded: a weight-only over-delivery must book silently - no confirmation
// and no ceiling - in either prompt configuration. That is the customer's own exclusion ("due to the
// weight variance it must be possible to overdeliver the catch quantity"), and the exact shape they
// reported: a piece nominally weighing ~2 kg booked at ~600 kg while the piece count stayed on target.
//
// The whole-TU comparison quantity comes from a backend lookup (getScannedHUQRCodeInfo ->
// PickingRestController.getHUInfoByQRCode -> JsonHUInfo.productQty), which reports the stock-UOM piece
// count. Were it ever to report the catch-UOM quantity instead, the HU's 1800 kg would be compared
// against the 3 pieces still to pick: with the prompt on the operator would be questioned, with it off
// the scan would be rejected as above max - so both scenarios below would fail.
//
// The exactly-on-target pair above cannot show that. Its 3 pieces are labelled 1.000 kg each, so the
// HU's catch quantity (3.000 kg) coincides with its piece count (3) and neither guard fires whichever
// of the two the lookup reports.
//

// 4 digits product code, 600.000 kg, lot 123, produced 2025-04-03, best before 2026-04-10.
// Against the product's 0.1 KGM-per-PCE catch-UOM rate, 600.000 kg on a single piece is ~6000x nominal.
const heavyWeightLabelQRCodeFor = (masterdata) => `${masterdata.products.BOM.productCode}60000000000123250403260410`;

// 3 pieces x 600.000 kg. Asserted before picking because it is what makes these two scenarios differ
// from the exactly-on-target pair above - without the oversized weight actually on the HU they would
// silently degrade into copies of it.
const expectHUCarriesTheOversizedWeight = async ({ huQRCode }) =>
    await test.step('Verify the produced HU carries 3 pieces weighing 1800 kg', async () => {
        await Backend.expect({
            hus: {
                [huQRCode]: { huStatus: 'A', storages: { BOM: '3 PCE' }, attributes: { WeightNet: '1800.000' } },
            },
        });
    });

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU far above nominal weight but on target by count - prompt enabled - no question asked', async ({
    page,
}) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU over-delivering catch weight only, no prompt fires');
    allure.severity('normal');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: true, orderQtyCUs: 3 });

    const weightLabelQRCode = heavyWeightLabelQRCodeFor(masterdata);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    const huQRCode = await produceHU({ masterdata, weightLabelQRCode, qtyCUs: 3 });
    await ApplicationsListScreen.expectVisible();

    await expectHUCarriesTheOversizedWeight({ huQRCode });

    await pickWholeHUAndExpectNoQuestion({ masterdata, huQRCode, weightLabelQRCode });
});

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU far above nominal weight but on target by count - prompt disabled - not blocked', async ({
    page,
}) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU over-delivering catch weight only, the ceiling does not block');
    allure.severity('normal');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: false, orderQtyCUs: 3 });

    const weightLabelQRCode = heavyWeightLabelQRCodeFor(masterdata);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    const huQRCode = await produceHU({ masterdata, weightLabelQRCode, qtyCUs: 3 });
    await ApplicationsListScreen.expectVisible();

    await expectHUCarriesTheOversizedWeight({ huQRCode });

    await pickWholeHUAndExpectNoQuestion({ masterdata, huQRCode, weightLabelQRCode });
});

//
// ===== The operator scans again while the confirmed pick is still on its way to the server =====
// Confirming the over-delivery sends the pick and takes the question off the screen. Until the server
// answers, the scan step must not come back: on a handheld over warehouse WiFi the operator sees nothing
// happen and pulls the trigger again, which would otherwise book the same HU a second time.
//

// The scenario is about that in-flight window, so it must not depend on how fast the backend answers:
// against a local server it would be milliseconds. Holding the pick request open until the scenario
// releases it reproduces the warehouse-WiFi delay the operator actually experiences. Only the first pick
// is held - a second one, which is exactly what must not happen, would go straight through and show up
// in the qtyPicked records below.
const PICK_REQUEST_URL_PATTERN = '**/picking/event';

const holdTheFirstPickRequest = async ({ page }) => {
    let releasePickRequest;
    const pickRequestReleased = new Promise((resolve) => (releasePickRequest = resolve));
    let isFirstPickRequest = true;

    await page.route(PICK_REQUEST_URL_PATTERN, async (route) => {
        if (isFirstPickRequest) {
            isFirstPickRequest = false;
            await pickRequestReleased;
        }
        await route.continue();
    });

    return releasePickRequest;
};

test('Whole HU: scan again while the confirmed pick is still in flight - it books exactly once', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned again while the confirmed pick is in flight, booked only once');
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

    const releasePickRequest = await holdTheFirstPickRequest({ page });

    await test.step('Scan the whole HU (3 pieces) against an order of 2 and confirm the over-delivery', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCode);

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        // Confirming sends the pick. Until the server answers, the operator is shown that it is running
        // and gets no scan step back - if the scan step returned here, the next scan would book again.
        await PickLineScanScreen.expectPickInProgress();
    });

    await test.step('Scan the same HU again while the pick is still running - nothing happens', async () => {
        await PickLineScanScreen.typeQRCodeWithoutWaitingForScanTarget(weightLabelQRCode);

        // The scan finds nothing listening, so the screen is unchanged: still the running pick, and no
        // second over-delivery question for the operator to confirm.
        await PickLineScanScreen.expectPickInProgress();
        await YesNoDialog.expectNotVisible();
    });

    await test.step('Let the pick reach the server and verify the line grew by one HU only', async () => {
        releasePickRequest();

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '3 Stk' });

        await page.unroute(PICK_REQUEST_URL_PATTERN);
    });

    await test.step('Verify the HU was booked exactly once', async () => {
        // A single qtyPicked record of 3 pieces. The second scan booking as well would leave two records
        // here (the expectation list is matched against all records of the schedule, size included).
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

//
// ===== A line that already carries a picked quantity =====
// The shape the customer reported: the line is ordered 3 and 3 are already picked, and the operator
// scans one more HU holding a single piece. Nothing about that HU exceeds the order on its own - it is
// the quantity already picked that makes it an over-delivery, so this is what proves the whole-HU path
// compares against the quantity still to pick rather than against the ordered quantity.
//

// noinspection JSUnusedLocalSymbols
test('Whole HU: scan an HU on a line that is already fully picked - prompt enabled - confirm Yes', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - whole HU scanned on a line that already carries a picked qty, over-delivery confirmed');
    allure.severity('critical');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: true, orderQtyCUs: 3 });

    // 4 digits product code, 1.000 kg, lot 123 resp. 124, produced 2025-04-03, best before 2026-04-10.
    // Each HU gets its own label, differing only in the LOT part (positions 11-18): the label is stored on
    // the produced HU as its HU_QRCode attribute, and a scanned label is resolved back to its HU by an exact
    // match on that attribute (PickingJobHUService.getFirstHUIdByQRCodeAttribute), so two HUs sharing one
    // label would be interchangeable candidates for either scan.
    const weightLabelQRCodeHU1 = `${masterdata.products.BOM.productCode}00100000000123250403260410`;
    const weightLabelQRCodeHU2 = `${masterdata.products.BOM.productCode}00100000000124250403260410`;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    const huQRCode1 = await produceHU({ masterdata, weightLabelQRCode: weightLabelQRCodeHU1, qtyCUs: 3 });
    await ApplicationsListScreen.expectVisible();
    const huQRCode2 = await produceHU({ masterdata, weightLabelQRCode: weightLabelQRCodeHU2, qtyCUs: 1 });
    await ApplicationsListScreen.expectVisible();

    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '0 Stk' });
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
    });

    await test.step('Pick the first whole HU (3 pieces), which takes the line to the ordered qty', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCodeHU1);

        // Neither questioned nor rejected - this HU is exactly on target. It is only the setup: what it
        // establishes for the second scan is the non-zero picked quantity the scenario is about.
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '3 Stk' });
    });

    await test.step('Scan a second whole HU (1 piece) onto the fully picked line, confirm the over-delivery', async () => {
        // Back into the scan step the way the operator gets there once a pick has returned them to the
        // job screen: open the line, then its Scan button.
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickScanButton();
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCodeHU2);

        // The one piece this HU holds stays well inside the 3 the line was ordered, so a comparison
        // against the ordered quantity raises no question at all. The question can only appear because
        // the comparison is against what is left to pick, which the first pick took down to 0.
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();

        // Confirming books the pick and returns the operator to the line they scanned from.
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.goBack();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '4 Stk' });
    });

    await test.step('Verify the second HU was picked on top of the first, over-delivering the line by 1', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        BOM: {
                            qtyPicked: [
                                { qtyPicked: '3 PCE', processed: false, shipmentLineId: '-' },
                                { qtyPicked: '1 PCE', processed: false, shipmentLineId: '-' },
                            ],
                        },
                    },
                },
            },
            hus: {
                [huQRCode1]: { huStatus: 'S', storages: { BOM: '3 PCE' } },
                [huQRCode2]: { huStatus: 'S', storages: { BOM: '1 PCE' } },
            },
        });
    });
});

//
// ===== A whole-HU scan that booked nothing must not change what the NEXT pick books =====
// The operator scans the whole-HU label and the pick is stopped before anything is booked - they decline
// the over-delivery question, or the ceiling refuses the scan outright. Still standing at the same scan
// step, they then pick from the line the everyday way: scan the HU's own QR code, and scan a single
// weight label inside the quantity dialog, which asks the line for exactly one piece.
//
// The line ordered 2 and the HU holds 3, so the two possible outcomes are told apart by the booked
// quantity alone: crediting 1 is the piece the operator asked for, crediting 3 is the whole HU of the
// scan that was supposed to have booked nothing.
//
// The operator never leaves the scan step between the two scans - that is the situation being covered.
// Walking back to the job screen and in again would be a different, easier case.
//

const declineTheOverDeliveryAtTheScanStep = async ({ weightLabelQRCode }) =>
    await test.step('Scan the whole HU (3 pieces) against an order of 2 and decline the over-delivery', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(weightLabelQRCode);

        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickNoButton();
    });

const pickOnePieceThroughTheQtyDialog = async ({ huQRCode, weightLabelQRCode }) =>
    await test.step('Scan the HU, then scan one weight label in the qty dialog - a request for one piece', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(huQRCode);

        // Scanning a weight label inside the dialog is the catch-weight pick the operator makes all day:
        // one label is one piece, and its weight is the piece's catch weight.
        await GetQuantityDialog.fillAndPressDone({ catchWeightQRCode: weightLabelQRCode });

        await PickingJobScreen.waitForScreen();
    });

const expectTheLineWasCreditedTheOnePieceRequested = async ({ pickingJobId }) =>
    await test.step('Verify the line was credited the single piece that was asked for', async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '1 Stk' });

        // A single record of one piece. The whole HU going onto the line instead shows up here as 3 PCE.
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        BOM: {
                            qtyPicked: [{ qtyPicked: '1 PCE', processed: false, shipmentLineId: '-' }],
                        },
                    },
                },
            },
        });
    });

// noinspection JSUnusedLocalSymbols
test('Whole HU: after the over-delivery was declined, the next pick books the quantity it asked for', async ({
    page,
}) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - a declined whole-HU scan leaves the next pick booking its own quantity');
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

    await declineTheOverDeliveryAtTheScanStep({ weightLabelQRCode });

    await pickOnePieceThroughTheQtyDialog({ huQRCode, weightLabelQRCode });
    await expectTheLineWasCreditedTheOnePieceRequested({ pickingJobId });
});

// noinspection JSUnusedLocalSymbols
test('Whole HU: after the over-delivery was blocked, the next pick books the quantity it asked for', async ({
    page,
}) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - a blocked whole-HU scan leaves the next pick booking its own quantity');
    allure.severity('critical');

    const masterdata = await createMasterdata_WholeHU({ showPromptWhenOverPicking: false, orderQtyCUs: 2 });

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

    await blockTheOverDeliveryAtTheScanStep({ weightLabelQRCode });

    await pickOnePieceThroughTheQtyDialog({ huQRCode, weightLabelQRCode });
    await expectTheLineWasCreditedTheOnePieceRequested({ pickingJobId });
});

//
// ===== The quantity the dialog offers after a whole-HU scan that booked nothing =====
// Same situation as above, read one step earlier: the operator opens the quantity dialog on the HU and
// switches to typing a quantity by hand. The quantity waiting in the field is the one they will book
// with a single OK, so it has to be this line's own remaining quantity (2) - never the 3 pieces of the
// HU whose scan was declined a moment ago.
//

// noinspection JSUnusedLocalSymbols
test('Whole HU: after the over-delivery was declined, the qty dialog offers the line remaining qty', async ({
    page,
}) => {
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Over-picking prompt - after a declined whole-HU scan the qty dialog offers the line remaining qty');
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
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 Stk', qtyPicked: '0 Stk' });
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
    });

    await declineTheOverDeliveryAtTheScanStep({ weightLabelQRCode });

    await test.step('Open the qty dialog on the HU and read the quantity it offers', async () => {
        await PickLineScanScreen.waitForScreen();
        await PickLineScanScreen.typeQRCode(huQRCode);

        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.clickManual();

        // 2 is what this line still has to be picked. 3 would be the content of the HU from the
        // declined scan, offered here for the operator to book with one OK.
        await GetQuantityDialog.expectQtyEntered('2');
    });
});
