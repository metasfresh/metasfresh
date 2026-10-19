import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// Catch-weight main finished good. With "Finished goods: require catch-weight scan" switched on, the
// receive dialog only offers the L+M scan; the scan fills quantity 1, weight, lot and best-before date
// for review, the operator may edit all of them (e.g. receive 3 pieces with their total weight) and confirms with Done. With the setting off (the default),
// each scan receives one piece right away.
const L_M_CODE = 'LMQ#1#0.101#08.11.2025#500';

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
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                'BOM_PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4 },
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

const startReceiveToNewTU = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });

    return { jobId };
};

// noinspection JSUnusedLocalSymbols
test('Scan required: the scan fills the fields for review; the entered quantity and weight, edited lot and scanned best-before date are received', async ({ page }) => {
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
    await GetQuantityDialog.expectQtyEntered('1');
    await GetQuantityDialog.expectCatchWeight('0.101');
    await GetQuantityDialog.expectLotNo('500');
    await GetQuantityDialog.expectBestBeforeDate('08.11.2025');

    // The operator receives as many pieces as they say, with their total weight.
    await GetQuantityDialog.typeQtyEntered('3');
    await GetQuantityDialog.typeCatchWeight('0.350');
    await GetQuantityDialog.replaceLotNo(editedLotNo);
    await GetQuantityDialog.clickDone();
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '3 Stk' });

    await Backend.expect({
        title: 'Produced TU carries the entered quantity and weight, the edited lot and the scanned best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '3 PCE' }],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '3 PCE' },
                attributes: {
                    'WeightNet': '0.350',
                    'Lot-Nummer': editedLotNo,
                    'HU_BestBeforeDate': '2025-11-08',
                },
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
    await startReceiveToNewTU(masterdata);

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

    await MaterialReceiptLineScreen.receiveQty({
        catchWeightQRCode: L_M_CODE,
        expectGoBackToJob: false,
    });
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
