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
// The default pair: the editable-attribute list is global, other specs rely on it.
const DEFAULT_EDITABLE_ATTRIBUTES = [LOT_NO, BEST_BEFORE_DATE];

// editableAttributes: the global editable-attribute list of the mobile manufacturing config, e.g. [BEST_BEFORE_DATE] for a lot number that is not editable
const createMasterdata = async ({ bestBeforeDays, lotNoSequence, editableAttributes = DEFAULT_EDITABLE_ATTRIBUTES } = {}) => {
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
                manufacturing: { editableAttributes },
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

// A test that narrows the global editable-attribute list puts the default pair back, also if it fails part-way.
let isEditableAttributesChanged = false;
test.afterEach(async () => {
    if (isEditableAttributesChanged) {
        await Backend.createMasterdata({
            language: 'en_US',
            request: { mobileConfig: { manufacturing: { editableAttributes: DEFAULT_EDITABLE_ATTRIBUTES } } },
        });
        isEditableAttributesChanged = false;
    }
});

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

// The receipt day as a date-only lot number with the date format yy-DDD (two-digit calendar year, day of the year).
const toLotNoYYDDD = (iso) => {
    const date = new Date(`${iso}T00:00:00Z`);
    const dayOfYear = Math.round((date - Date.UTC(date.getUTCFullYear(), 0, 1)) / 86400000) + 1;
    return `${iso.substring(2, 4)}-${String(dayOfYear).padStart(3, '0')}`;
};

// noinspection JSUnusedLocalSymbols
test('Lot number not editable, date-only lot sequence (own format yy-DDD): the lot is shown read-only and generated at receipt; the best-before date stays editable', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Pre-filled best-before date and lot number on the receive dialog');
    allure.severity('critical');

    // The lot sequence has its own date format, independent of the instance-wide date-sequence setting.
    // The lot number is not editable: it is not one of the editable attributes.
    isEditableAttributesChanged = true;
    const masterdata = await createMasterdata({
        bestBeforeDays: BEST_BEFORE_DAYS,
        lotNoSequence: { dateBased: true, dateFormat: 'yy-DDD' },
        editableAttributes: [BEST_BEFORE_DATE],
    });
    const receiptDayBefore = receiptDayPlusDays(0).iso;
    const { jobId } = await startReceiveFlow(masterdata);
    const editedBestBeforeDate = receiptDayPlusDays(BEST_BEFORE_DAYS + 5);

    await MaterialReceiptLineScreen.clickReceiveQtyButton();
    await GetQuantityDialog.expectNoScanOffered();
    const shownLotNo = await GetQuantityDialog.expectLotNoMatching(/^\d{2}-\d{3}$/);
    await GetQuantityDialog.expectLotNoDisabled();
    await GetQuantityDialog.expectEditableAttributeNotPresent(LOT_NO);
    await GetQuantityDialog.typeEditableAttributeDate(BEST_BEFORE_DATE, editedBestBeforeDate.displayed);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });
    await ManufacturingJobScreen.waitForScreen();
    const receiptDayAfter = receiptDayPlusDays(0).iso;

    await ManufacturingJobScreen.complete();

    // The shown lot is the receipt day as yy-DDD; the receipt generates the same lot (unless the run crossed midnight).
    const expectedLotNos = distinct([toLotNoYYDDD(receiptDayBefore), toLotNoYYDDD(receiptDayAfter)]);
    if (!expectedLotNos.includes(shownLotNo)) {
        throw new Error(`Shown lot ${shownLotNo} is not the receipt day as yy-DDD: ${expectedLotNos}`);
    }
    await Backend.expect({
        title: 'Produced HU carries the generated lot that was shown and the edited best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                attributes: {
                    'Lot-Nummer': receiptDayBefore === receiptDayAfter ? shownLotNo : toLotNoYYDDD(receiptDayAfter),
                    'HU_BestBeforeDate': editedBestBeforeDate.iso,
                },
            },
        },
    });
});
