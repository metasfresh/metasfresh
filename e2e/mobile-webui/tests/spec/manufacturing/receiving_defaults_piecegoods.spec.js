import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// Piece-goods finished product (no catch weight): the receive dialog pre-fills the best-before date
// with "receipt day + the product's total shelf life (BestBeforeDays)"; both best-before date and
// lot number stay editable. The lot number is only pre-filled when the BOM has a lot-number sequence;
// this BOM has none, so the lot field starts empty.
const BEST_BEFORE_DAYS = 10;

const createMasterdata = async ({ bestBeforeDays } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            warehouses: { 'wh': {} },
            products: {
                'COMP1': {},
                'BOM': {
                    bestBeforeDays,
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 4,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

// The receipt day is "today" in the organisation's time zone; the test organisation has none set, so the
// app server's zone applies, which is Europe/Berlin (see TZ in docker-builds/Dockerfile.backend.app).
const receiptDayPlusDays = (days) => {
    const today = new Date().toLocaleDateString('en-CA', { timeZone: 'Europe/Berlin' }); // YYYY-MM-DD
    const date = new Date(`${today}T00:00:00Z`);
    date.setUTCDate(date.getUTCDate() + days);
    const iso = date.toISOString().substring(0, 10);
    const [year, month, day] = iso.split('-');
    return { iso, displayed: `${day}.${month}.${year}` };
};

const startReceiveFlow = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });

    return { jobId };
};

// noinspection JSUnusedLocalSymbols
test('Best-before date is pre-filled from the shelf life; edited best-before date and lot are received', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Pre-filled best-before date and lot number on the receive dialog');
    allure.severity('critical');

    const masterdata = await createMasterdata({ bestBeforeDays: BEST_BEFORE_DAYS });
    const { jobId } = await startReceiveFlow(masterdata);

    const defaultBestBeforeDate = receiptDayPlusDays(BEST_BEFORE_DAYS);
    const editedBestBeforeDate = receiptDayPlusDays(BEST_BEFORE_DAYS + 5);
    const editedLotNo = `LOT-${Date.now()}`;

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectBestBeforeDate(defaultBestBeforeDate.displayed);
    await GetQuantityDialog.expectLotNo('');
    await GetQuantityDialog.fillAndPressDone({
        expectQtyEntered: '4',
        qtyEntered: '4',
        lotNo: editedLotNo,
        bestBeforeDate: editedBestBeforeDate.displayed,
    });
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '4 Stk', qtyReceived: '4 Stk' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the edited best-before date and lot',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                attributes: {
                    'Lot-Nummer': editedLotNo,
                    'HU_BestBeforeDate': editedBestBeforeDate.iso,
                },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Best-before date and lot left as pre-filled are received as receipt day + shelf life and no lot', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Pre-filled best-before date and lot number on the receive dialog');
    allure.severity('critical');

    const masterdata = await createMasterdata({ bestBeforeDays: BEST_BEFORE_DAYS });
    const { jobId } = await startReceiveFlow(masterdata);

    const defaultBestBeforeDate = receiptDayPlusDays(BEST_BEFORE_DAYS);

    // The operator confirms without touching the fields; the best-before date is set at receipt.
    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectBestBeforeDate(defaultBestBeforeDate.displayed);
    await GetQuantityDialog.expectLotNo('');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '4 Stk', qtyReceived: '4 Stk' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced LU and TU carry receipt day + shelf life as best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                // no lot-number sequence on the BOM: a lot left blank stays blank
                attributes: { 'HU_BestBeforeDate': defaultBestBeforeDate.iso, 'Lot-Nummer': null },
                tus: [
                    { attributes: { 'HU_BestBeforeDate': defaultBestBeforeDate.iso } },
                ],
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Product without shelf life — best-before date is not pre-filled', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Pre-filled best-before date and lot number on the receive dialog');
    allure.severity('normal');

    const masterdata = await createMasterdata({ bestBeforeDays: null });
    const { jobId } = await startReceiveFlow(masterdata);

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectBestBeforeDateVisible();
    await GetQuantityDialog.expectBestBeforeDate('');
    await GetQuantityDialog.expectLotNo('');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
    await ManufacturingJobScreen.waitForScreen();

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU has no best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                attributes: { 'HU_BestBeforeDate': null },
            },
        },
    });
});
