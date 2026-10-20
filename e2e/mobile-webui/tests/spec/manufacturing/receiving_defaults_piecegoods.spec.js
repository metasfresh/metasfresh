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

// Piece-goods finished product (no catch weight): the receive dialog pre-fills the best-before date with
// "receipt day + the product's total shelf life (BestBeforeDays)". It pre-fills the lot number only when
// the BOM's lot-number sequence can be shown without consuming it (a purely date-based sequence);
// otherwise the lot field starts empty and the lot is generated at receipt. Both fields stay editable.
//
// The lot number and best-before date are the receive dialog's editable-attribute rows (attr-<code>-field):
// configured as editable attributes of the mobile manufacturing config, and instance attributes of the
// attribute set of the product's category (the receive dialog takes the attribute set from the category).
const BEST_BEFORE_DAYS = 10;
const LOT_NO = 'Lot-Nummer';
const BEST_BEFORE_DATE = 'HU_BestBeforeDate';

const createMasterdata = async ({ bestBeforeDays, lotNoSequence } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            productCategories: { 'mfgCat': { attributeSetName: 'mfgAttrSet' } },
            attributes: {
                'lotNumberAttr': { value: LOT_NO, attributeSetNames: ['mfgAttrSet'] },
                'bestBeforeDateAttr': { value: BEST_BEFORE_DATE, attributeSetNames: ['mfgAttrSet'] },
            },
            mobileConfig: {
                manufacturing: { editableAttributes: [BEST_BEFORE_DATE, LOT_NO] },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': { productCategory: 'mfgCat' },
                'BOM': {
                    productCategory: 'mfgCat',
                    bestBeforeDays,
                    bom: { lines: [{ product: 'COMP1', qty: 1 }], lotNoSequence },
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

// A run can cross midnight between opening the job and confirming the receipt, so the receipt day is
// either of the two days seen before and after the receipt.
const distinct = (values) => [...new Set(values)];

const expectReceivedHU = async ({ bestBeforeDatesIso, expectation }) => {
    const candidates = distinct(bestBeforeDatesIso);
    for (let i = 0; i < candidates.length; i++) {
        try {
            return await Backend.expect(expectation(candidates[i]));
        } catch (error) {
            if (i === candidates.length - 1) {
                throw error;
            }
        }
    }
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
    const defaultBefore = receiptDayPlusDays(BEST_BEFORE_DAYS);
    const { jobId } = await startReceiveFlow(masterdata);

    const editedBestBeforeDate = receiptDayPlusDays(BEST_BEFORE_DAYS + 5);
    const editedLotNo = `LOT-${Date.now()}`;

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectNoScanOffered();
    await GetQuantityDialog.expectEditableAttributeValueOneOf(BEST_BEFORE_DATE, distinct([defaultBefore.displayed, receiptDayPlusDays(BEST_BEFORE_DAYS).displayed]));
    await GetQuantityDialog.expectEditableAttributeValue(LOT_NO, '');
    await GetQuantityDialog.typeEditableAttribute(LOT_NO, editedLotNo);
    await GetQuantityDialog.typeEditableAttributeDate(BEST_BEFORE_DATE, editedBestBeforeDate.displayed);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
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
    const before = receiptDayPlusDays(BEST_BEFORE_DAYS);
    const { jobId } = await startReceiveFlow(masterdata);

    // The operator confirms without touching the fields; the best-before date is set at receipt.
    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectEditableAttributeValueOneOf(BEST_BEFORE_DATE, distinct([before.displayed, receiptDayPlusDays(BEST_BEFORE_DAYS).displayed]));
    await GetQuantityDialog.expectEditableAttributeValue(LOT_NO, '');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
    await ManufacturingJobScreen.waitForScreen();
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '4 Stk', qtyReceived: '4 Stk' });
    const after = receiptDayPlusDays(BEST_BEFORE_DAYS);

    await ManufacturingJobScreen.complete();

    await expectReceivedHU({
        bestBeforeDatesIso: [after.iso, before.iso],
        expectation: (bestBeforeDateIso) => ({
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
                    attributes: { 'HU_BestBeforeDate': bestBeforeDateIso, 'Lot-Nummer': null },
                    tus: [
                        { attributes: { 'HU_BestBeforeDate': bestBeforeDateIso } },
                    ],
                },
            },
        }),
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
    await GetQuantityDialog.expectEditableAttributeVisible(BEST_BEFORE_DATE);
    await GetQuantityDialog.expectEditableAttributeValue(BEST_BEFORE_DATE, '');
    await GetQuantityDialog.expectEditableAttributeValue(LOT_NO, '');
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

// noinspection JSUnusedLocalSymbols
test('Date-based lot-number sequence — the lot is pre-filled and received as shown', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Pre-filled best-before date and lot number on the receive dialog');
    allure.severity('critical');

    // The lot number is the receipt day, formatted as the instance-wide date-sequence setting says.
    const lotPrefix = `D${Date.now()}-`;
    const masterdata = await createMasterdata({
        bestBeforeDays: BEST_BEFORE_DAYS,
        lotNoSequence: { prefix: lotPrefix, dateBased: true },
    });
    const receiptDayBefore = receiptDayPlusDays(0).iso;
    const { jobId } = await startReceiveFlow(masterdata);

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    const shownLotNo = await GetQuantityDialog.expectEditableAttributeValueMatching(LOT_NO, new RegExp(`^${lotPrefix}.+$`));
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
    await ManufacturingJobScreen.waitForScreen();
    const receiptDayAfter = receiptDayPlusDays(0).iso;

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the lot that was shown',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                // The untouched lot is generated at receipt for the receipt day: if the run crossed midnight,
                // that is the next day's lot, which can only be checked by its prefix.
                attributes: { 'Lot-Nummer': receiptDayBefore === receiptDayAfter ? shownLotNo : `startsWith:${lotPrefix}` },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Counter lot-number sequence — the lot is not pre-filled and is generated at receipt', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Pre-filled best-before date and lot number on the receive dialog');
    allure.severity('critical');

    // Showing a counter-based lot would mean consuming a number, so the field stays empty.
    const lotPrefix = `C${Date.now()}-`;
    const masterdata = await createMasterdata({
        bestBeforeDays: BEST_BEFORE_DAYS,
        lotNoSequence: { prefix: lotPrefix, currentNext: 1 },
    });
    const { jobId } = await startReceiveFlow(masterdata);

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectEditableAttributeValue(LOT_NO, '');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
    await ManufacturingJobScreen.waitForScreen();

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the first lot of the sequence',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                attributes: { 'Lot-Nummer': `${lotPrefix}1` },
            },
        },
    });
});
