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
// The scan does not depend on "Capture catch weight": with it switched off (weight captured later, at picking) the
// operator enters the number of blocks as in a normal receipt and scans the label of the LAST block; its lot and
// best-before date apply to the whole receipt. No weight is shown or received.
const L_M_CODE = 'LMQ#1#0.101#08.11.2025#500';
// A second piece from another lot, with its own weight and best-before date.
const L_M_CODE_2 = 'LMQ#1#0.093#15.11.2025#501';
// A third piece, from yet another lot.
const L_M_CODE_3 = 'LMQ#1#0.097#22.11.2025#502';
// A label whose weight segment is empty: fine when no weight is captured at receipt.
const L_M_CODE_WITHOUT_WEIGHT = 'LMQ#1##15.11.2025#501';
const BEST_BEFORE_DAYS = 70;

// isTUMode: the line is received in TUs (receive unit type TU); the PP order then carries the two-piece TU packing.
// isCaptureCatchWeightAtReceipt: undefined = the default (captured)
const createMasterdata = async ({
    isFinishedGoodsCatchWeightScanRequired,
    isTUMode = false,
    isCaptureCatchWeightAtReceipt,
    isLotNumberEditable,
}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: {
                    isFinishedGoodsCatchWeightScanRequired,
                    isCaptureCatchWeightAtReceipt,
                    isLotNumberEditable,
                    receiveUnitType: isTUMode ? 'TU' : 'CU',
                },
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
                // two pieces per TU: the third receipt in TU mode needs a second TU
                'BOM_PI_2PCE': { lu: 'LU2', qtyTUsPerLU: 20, tu: 'TU2', product: 'BOM', qtyCUsPerTU: 2 },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    piItemProduct: isTUMode ? 'TU2' : undefined,
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

// Weight not captured at receipt: one scan of the last block's label fills lot and best-before date (editable),
// no weight is shown, and the operator enters the number of blocks.
const receiveBlocksWithLastBlockScan = async ({ qrCode, expectedQtyBefore, qty, expectedLotNo, expectedBestBeforeDate, qtyToReceive, qtyReceivedAfter }) => {
    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    // The scan is still mandatory: nothing can be confirmed or typed in manually before it.
    await GetQuantityDialog.expectOnlyCatchWeightScanOffered();
    await GetQuantityDialog.scanCatchWeightQRCodeForReview({ qrCode });
    await GetQuantityDialog.expectCatchWeightNotVisible();
    await GetQuantityDialog.expectLotNo(expectedLotNo);
    await GetQuantityDialog.expectLotNoEditable();
    await GetQuantityDialog.expectBestBeforeDate(expectedBestBeforeDate);
    await GetQuantityDialog.expectQtyEnteredEditable(expectedQtyBefore);
    await GetQuantityDialog.typeQtyEntered(qty);
    await GetQuantityDialog.clickDone();
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive, qtyReceived: qtyReceivedAfter });
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
test('Scan required, TU mode: each receipt adds one piece to the current TU; a full TU is followed by a new one', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isTUMode: true });
    const { jobId } = await startJob(masterdata);

    // Every receipt is one piece, so the line counts pieces, not TUs.
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '0 Stk' });
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI_2PCE.tuPIItemProductTestId });

    // first piece: starts TU 1
    await receiveOneScannedPiece({ qrCode: L_M_CODE, expectedCatchWeight: '0.101', expectedLotNo: '500', expectedBestBeforeDate: '08.11.2025', qtyReceivedAfter: '1 Stk' });
    // second piece: goes into TU 1, which is then full (2 pieces per TU)
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_2, expectedCatchWeight: '0.093', expectedLotNo: '501', expectedBestBeforeDate: '15.11.2025', qtyReceivedAfter: '2 Stk' });
    // third piece: TU 1 is full, so TU 2 is started
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_3, expectedCatchWeight: '0.097', expectedLotNo: '502', expectedBestBeforeDate: '22.11.2025', qtyReceivedAfter: '3 Stk' });

    await Backend.expect({
        title: 'TU 1 holds two pieces and TU 2 one piece, each piece with its own scanned weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '1 PCE' },
                    { tu: 'tu1', qty: '1 PCE' },
                    { tu: 'tu2', qty: '1 PCE' },
                ],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '2 PCE' },
                attributes: { 'WeightNet': '0.194' },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.101', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' } },
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.093', 'Lot-Nummer': '501', 'HU_BestBeforeDate': '2025-11-15' } },
                ],
            },
            'tu2': {
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '0.097', 'Lot-Nummer': '502', 'HU_BestBeforeDate': '2025-11-22' },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.097', 'Lot-Nummer': '502', 'HU_BestBeforeDate': '2025-11-22' } },
                ],
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, TU mode, existing TU picked by its QR code: each piece is added to that TU; once it is full a new TU is started', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isTUMode: true });
    const { jobId } = await startJob(masterdata);

    // the label of a two-piece TU
    const tuQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BOM_PI_2PCE.tuPITestId, numberOfHUs: 1 });

    // first piece: the label's TU is received with it
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: tuQRCode });
    await receiveOneScannedPiece({ qrCode: L_M_CODE, expectedCatchWeight: '0.101', expectedLotNo: '500', expectedBestBeforeDate: '08.11.2025', qtyReceivedAfter: '1 Stk' });

    // second piece: the operator scans the label of that existing TU again; the piece goes into it, which is then full
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: tuQRCode });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_2, expectedCatchWeight: '0.093', expectedLotNo: '501', expectedBestBeforeDate: '15.11.2025', qtyReceivedAfter: '2 Stk' });

    // third piece: the operator scans the full TU's label once more; a new TU is started for the piece
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: tuQRCode });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_3, expectedCatchWeight: '0.097', expectedLotNo: '502', expectedBestBeforeDate: '22.11.2025', qtyReceivedAfter: '3 Stk' });

    await Backend.expect({
        title: 'The labelled TU holds two pieces, the new TU one piece, each piece with its own scanned weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '1 PCE' },
                    { tu: 'tu1', qty: '1 PCE' },
                    { tu: 'tu2', qty: '1 PCE' },
                ],
            },
        },
        hus: {
            [tuQRCode]: {
                storages: { 'BOM': '2 PCE' },
                attributes: { 'WeightNet': '0.194' },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.101', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' } },
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.093', 'Lot-Nummer': '501', 'HU_BestBeforeDate': '2025-11-15' } },
                ],
            },
            // tu1 is the labelled TU: same content
            'tu1': {
                storages: { 'BOM': '2 PCE' },
            },
            'tu2': {
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '0.097', 'Lot-Nummer': '502', 'HU_BestBeforeDate': '2025-11-22' },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '0.097', 'Lot-Nummer': '502', 'HU_BestBeforeDate': '2025-11-22' } },
                ],
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, TU mode, new LU: the pieces fill the LU\'s current TU; a full TU is followed by a new TU on the same LU', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isTUMode: true });
    const { jobId } = await startJob(masterdata);

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.BOM_PI_2PCE.luPIItemTestId });

    // first piece: creates the LU with its first TU
    await receiveOneScannedPiece({ qrCode: L_M_CODE, expectedCatchWeight: '0.101', expectedLotNo: '500', expectedBestBeforeDate: '08.11.2025', qtyReceivedAfter: '1 Stk' });
    // second piece: into that TU on the LU, which is then full
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_2, expectedCatchWeight: '0.093', expectedLotNo: '501', expectedBestBeforeDate: '15.11.2025', qtyReceivedAfter: '2 Stk' });
    // third piece: a new TU on the same LU
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await receiveOneScannedPiece({ qrCode: L_M_CODE_3, expectedCatchWeight: '0.097', expectedLotNo: '502', expectedBestBeforeDate: '22.11.2025', qtyReceivedAfter: '3 Stk' });

    await Backend.expect({
        title: 'One LU with a TU of two pieces and a TU of one piece, each piece with its own scanned weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '1 PCE' },
                    { lu: 'lu1', qty: '1 PCE' },
                    { lu: 'lu1', qty: '1 PCE' },
                ],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '3 PCE' },
                attributes: { 'WeightNet': '0.291' },
                tus: [
                    // the new LU's (empty) aggregate of full TUs: the first piece is a partial TU, so it went into a real TU
                    { isAggregatedTU: true },
                    {
                        storages: { 'BOM': '2 PCE' },
                        cus: [
                            { qty: '1 PCE', attributes: { 'WeightNet': '0.101', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' } },
                            { qty: '1 PCE', attributes: { 'WeightNet': '0.093', 'Lot-Nummer': '501', 'HU_BestBeforeDate': '2025-11-15' } },
                        ],
                    },
                    {
                        storages: { 'BOM': '1 PCE' },
                        cus: [
                            { qty: '1 PCE', attributes: { 'WeightNet': '0.097', 'Lot-Nummer': '502', 'HU_BestBeforeDate': '2025-11-22' } },
                        ],
                    },
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

const lastBlockScanStory = () => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');
};

// noinspection JSUnusedLocalSymbols
test('Scan required, weight not captured at receipt, new TU: 3 blocks received with one scan of the last block; all carry its lot and best-before date', async ({ page }) => {
    lastBlockScanStory();

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isCaptureCatchWeightAtReceipt: false });
    const { jobId } = await startReceiveToNewTU(masterdata);

    await receiveBlocksWithLastBlockScan({
        qrCode: L_M_CODE,
        expectedQtyBefore: '100',
        qty: '3',
        expectedLotNo: '500',
        expectedBestBeforeDate: '08.11.2025',
        qtyToReceive: '100 Stk',
        qtyReceivedAfter: '3 Stk',
    });

    // No weight is captured at receipt: WeightNet stays at zero (it is captured later, at picking).
    await Backend.expect({
        title: 'One TU with the 3 blocks, all with the scanned lot and best-before date and no weight',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '3 PCE' }],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '3 PCE' },
                attributes: { 'WeightNet': '0.000', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' },
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, weight not captured at receipt, new LU: 3 blocks received with one scan of the last block; the LU carries its lot and best-before date', async ({ page }) => {
    lastBlockScanStory();

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isCaptureCatchWeightAtReceipt: false });
    const { jobId } = await startJob(masterdata);
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.BOM_PI_1PCE.luPIItemTestId });

    await receiveBlocksWithLastBlockScan({
        qrCode: L_M_CODE_WITHOUT_WEIGHT,
        expectedQtyBefore: '100',
        qty: '3',
        expectedLotNo: '501',
        expectedBestBeforeDate: '15.11.2025',
        qtyToReceive: '100 Stk',
        qtyReceivedAfter: '3 Stk',
    });

    await Backend.expect({
        title: 'One LU with the 3 blocks, carrying the scanned lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '3 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '3 PCE' },
                attributes: { 'Lot-Nummer': '501', 'HU_BestBeforeDate': '2025-11-15' },
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, weight not captured at receipt, TU mode: 2 TUs received with one scan of the last block; the line counts TUs', async ({ page }) => {
    lastBlockScanStory();

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isTUMode: true, isCaptureCatchWeightAtReceipt: false });
    const { jobId } = await startJob(masterdata);

    // A normal TU receipt: the line counts TUs (100 pieces, 2 per TU).
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '50 TU', qtyReceived: '0 TU' });
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI_2PCE.tuPIItemProductTestId });

    await receiveBlocksWithLastBlockScan({
        qrCode: L_M_CODE,
        expectedQtyBefore: '50',
        qty: '2',
        expectedLotNo: '500',
        expectedBestBeforeDate: '08.11.2025',
        qtyToReceive: '50 TU',
        qtyReceivedAfter: '2 TU',
    });

    await Backend.expect({
        title: '2 TUs of 2 blocks each, all with the scanned lot and best-before date and no weight',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '2 PCE' },
                    { tu: 'tu2', qty: '2 PCE' },
                ],
            },
        },
        hus: {
            'tu1': { storages: { 'BOM': '2 PCE' }, attributes: { 'WeightNet': '0.000', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' } },
            'tu2': { storages: { 'BOM': '2 PCE' }, attributes: { 'WeightNet': '0.000', 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' } },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, weight not captured at receipt, lot number not editable: the scanned lot is shown, editable, and lands on the HU', async ({ page }) => {
    lastBlockScanStory();

    const masterdata = await createMasterdata({
        isFinishedGoodsCatchWeightScanRequired: true,
        isCaptureCatchWeightAtReceipt: false,
        isLotNumberEditable: false,
    });
    const { jobId } = await startReceiveToNewTU(masterdata);

    await receiveBlocksWithLastBlockScan({
        qrCode: L_M_CODE,
        expectedQtyBefore: '100',
        qty: '2',
        expectedLotNo: '500',
        expectedBestBeforeDate: '08.11.2025',
        qtyToReceive: '100 Stk',
        qtyReceivedAfter: '2 Stk',
    });

    await Backend.expect({
        title: 'The TU carries the scanned lot, although the lot number is not editable',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '2 PCE' }],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '2 PCE' },
                attributes: { 'Lot-Nummer': '500', 'HU_BestBeforeDate': '2025-11-08' },
            },
        },
    });

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan required, weight not captured at receipt: codes without lot or with an invalid best-before date are rejected and nothing is received', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Mandatory catch-weight scan on finished goods receipt');
    allure.severity('critical');

    const masterdata = await createMasterdata({ isFinishedGoodsCatchWeightScanRequired: true, isCaptureCatchWeightAtReceipt: false });
    const { jobId } = await startReceiveToNewTU(masterdata);

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectOnlyCatchWeightScanOffered();
    await GetQuantityDialog.scanCatchWeightQRCodeExpectingError({
        qrCode: 'LMQ#1#0.101#08.11.2025',
        expectedError: 'The scanned QR code contains no lot number!',
    });
    await GetQuantityDialog.scanCatchWeightQRCodeExpectingError({
        qrCode: 'LMQ#1##00.00.0000#500',
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
