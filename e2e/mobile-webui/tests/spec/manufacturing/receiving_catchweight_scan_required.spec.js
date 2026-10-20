import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { receiptDayPlusDays } from '../../utils/receiptDay';

// Catch-weight main finished good with a total shelf life, so the receive dialog has a pre-filled
// best-before date that the scanned one must replace.
// With "Finished goods: require catch-weight scan" switched on, the dialog only offers the L+M scan. The
// scan fills weight, lot and best-before date for review; the operator may edit those and confirms with Done.
// Catch weight is received one piece at a time: the quantity is fixed at 1 and cannot be changed, so every
// receipt is one piece carrying its own scanned weight, lot and best-before date - whatever the receive target
// (new TU, new LU, the LU received onto before, or an HU picked by scanning its QR code).
// With the setting off (the default), each scan receives one piece right away.
const L_M_CODE = 'LMQ#1#0.101#08.11.2025#500';
// A second piece from another lot, with its own weight and best-before date.
const L_M_CODE_2 = 'LMQ#1#0.093#15.11.2025#501';
const BEST_BEFORE_DAYS = 70;

const createMasterdata = async ({ isFinishedGoodsCatchWeightScanRequired }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isFinishedGoodsCatchWeightScanRequired },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': {},
                'BOM': {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    bestBeforeDays: BEST_BEFORE_DAYS,
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                'BOM_PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4 },
                // one piece per TU: every receipt of one piece fills one TU
                'BOM_PI_1PCE': { lu: 'LU1', qtyTUsPerLU: 20, tu: 'TU1', product: 'BOM', qtyCUsPerTU: 1 },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

const startJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    return await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
};

const startReceiveToNewTU = async (masterdata) => {
    const { jobId } = await startJob(masterdata);

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });

    return { jobId };
};

// Receives one piece onto the currently selected target: scan, review the scanned values (the quantity is
// fixed at 1), confirm. The operator lands back on the job.
const receiveOneScannedPiece = async ({ qrCode, expectedCatchWeight, expectedLotNo, expectedBestBeforeDate, qtyReceivedAfter }) => {
    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectOnlyCatchWeightScanOffered();
    await GetQuantityDialog.scanCatchWeightQRCodeForReview({ qrCode });
    await GetQuantityDialog.expectQtyEnteredFixedAt('1');
    await GetQuantityDialog.expectCatchWeight(expectedCatchWeight);
    await GetQuantityDialog.expectLotNo(expectedLotNo);
    await GetQuantityDialog.expectBestBeforeDate(expectedBestBeforeDate);
    await GetQuantityDialog.clickDone();
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: qtyReceivedAfter });
};

// noinspection JSUnusedLocalSymbols
test('Scan required, new TU: the scan fills the fields for review; one piece is received with the edited weight and lot and the scanned best-before date', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true });
    const { jobId } = await startReceiveToNewTU(masterdata);

    const editedLotNo = `LOT-${Date.now()}`;

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    // Nothing can be confirmed or typed in manually before the scan.
    await GetQuantityDialog.expectOnlyCatchWeightScanOffered();

    await GetQuantityDialog.scanCatchWeightQRCodeForReview({ qrCode: L_M_CODE });
    // One piece at a time: the quantity is 1 and cannot be changed.
    await GetQuantityDialog.expectQtyEnteredFixedAt('1');
    await GetQuantityDialog.expectCatchWeight('0.101');
    await GetQuantityDialog.expectLotNo('500');
    await GetQuantityDialog.expectBestBeforeDate('08.11.2025');

    // The operator corrects the weight and the lot of the piece before confirming.
    await GetQuantityDialog.typeCatchWeight('0.105');
    await GetQuantityDialog.typeLotNo(editedLotNo);
    await GetQuantityDialog.clickDone();
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '1 Stk' });

    await Backend.expect({
        title: 'Produced TU carries one piece with the edited weight and lot and the scanned best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '1 PCE' }],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '1 PCE' },
                attributes: {
                    'WeightNet': '0.105',
                    'Lot-Nummer': editedLotNo,
                    'HU_BestBeforeDate': '2025-11-08',
                },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.105', 'Lot-Nummer': editedLotNo, 'HU_BestBeforeDate': '2025-11-08' } },
                ],
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, new LU then the same LU again: each receipt adds one TU of one piece with its own weight, lot and best-before date', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true });
    const { jobId } = await startJob(masterdata);

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.BOM_PI_1PCE.luPIItemTestId });

    // first piece: creates the LU
    await receiveOneScannedPiece({ qrCode: L_M_CODE, expectedCatchWeight: '0.101', expectedLotNo: '500', expectedBestBeforeDate: '08.11.2025', qtyReceivedAfter: '1 Stk' });

    // second piece: the line keeps the LU just received onto as its target
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_2, expectedCatchWeight: '0.093', expectedLotNo: '501', expectedBestBeforeDate: '15.11.2025', qtyReceivedAfter: '2 Stk' });

    await Backend.expect({
        title: 'Both pieces are on the same LU, each in its own TU with its own scanned weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '1 PCE' },
                    { lu: 'lu1', qty: '1 PCE' },
                ],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '2 PCE' },
                attributes: { 'WeightNet': '0.194' },
                tus: [
                    {
                        storages: { 'BOM': '1 PCE' },
                        attributes: { 'WeightNet': '0.101', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' },
                    },
                    {
                        storages: { 'BOM': '1 PCE' },
                        attributes: { 'WeightNet': '0.093', 'Lot-Nummer': '501', 'HU_BestBeforeDate': '2025-11-15' },
                    },
                ],
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, target picked by scanning an HU QR code: one piece is received into that HU with the scanned weight, lot and best-before date', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true });
    const { jobId } = await startJob(masterdata);

    // a pre-printed label of a one-piece TU
    const tuQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BOM_PI_1PCE.tuPITestId, numberOfHUs: 1 });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: tuQRCode });

    await receiveOneScannedPiece({ qrCode: L_M_CODE, expectedCatchWeight: '0.101', expectedLotNo: '500', expectedBestBeforeDate: '08.11.2025', qtyReceivedAfter: '1 Stk' });

    await Backend.expect({
        title: 'The HU with the scanned label carries one piece with the scanned weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '1 PCE' }],
            },
        },
        hus: {
            [tuQRCode]: {
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '0.101', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.101', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' } },
                ],
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required: codes without lot or with an invalid best-before date are rejected and nothing is received', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true });
    const { jobId } = await startReceiveToNewTU(masterdata);

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.scanCatchWeightQRCodeExpectingError({
        qrCode: 'LMQ#1#0.101#08.11.2025',
        expectedError: 'The scanned QR code contains no lot number!',
    });
    await GetQuantityDialog.scanCatchWeightQRCodeExpectingError({
        qrCode: 'LMQ#1#0.101#00.00.0000#500',
        expectedError: 'The scanned QR code contains an invalid best-before date!',
    });
    await GetQuantityDialog.clickCloseScanner();

    await MaterialReceiptLineScreen.goBack();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '0 Stk' });

    await Backend.expect({
        title: 'Nothing was received',
        manufacturings: {
            [jobId]: { receivedHUs: [] },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan not required (default): the scan receives the piece right away', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('normal');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: false });
    const { jobId } = await startReceiveToNewTU(masterdata);

    const before = receiptDayPlusDays(BEST_BEFORE_DAYS);
    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    // The manual input shows the pre-filled best-before date ...
    await GetQuantityDialog.clickManual();
    await GetQuantityDialog.expectBestBeforeDateOneOf([...new Set([before.displayed, receiptDayPlusDays(BEST_BEFORE_DAYS).displayed])]);
    // ... but the operator scans, and the scan receives the piece with the scanned best-before date.
    await GetQuantityDialog.clickSwitchToQRCodeInput();
    await GetQuantityDialog.scanCatchWeightQRCode({ qrCode: L_M_CODE, stepName: '#1/1' });
    await GetQuantityDialog.clickCloseScanner();
    await MaterialReceiptLineScreen.goBack();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '1 Stk' });

    await Backend.expect({
        title: 'The scanned piece was received with the scanned weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '1 PCE' }],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '1 PCE' },
                attributes: {
                    'WeightNet': '0.101',
                    'Lot-Nummer': '500',
                    'HU_BestBeforeDate': '2025-11-08',
                },
            },
        },
    });

    await ManufacturingJobScreen.complete();
});
