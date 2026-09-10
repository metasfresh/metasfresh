import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// Value-TYPE coverage for the mfg editable-attributes list on the REAL receive dialog: a NUMBER entered ->
// carried, left empty -> stays at default (0); a producer-managed Production Date -> stamped; NUMBER + DATE +
// STRING together -> all three stamped; a MANDATORY attribute left empty -> not enforced, receive completes.
// These supersede the backend cucumber twins (Receive_with_CatchWeight.feature) EXCEPT the multi-TU case
// ("attribute stamped on every produced HU when a line yields MORE THAN ONE TU"), which stays cucumber: the
// mobile receive aggregates the produced qty into a SINGLE HU, so multiple produced TUs are not dialog-reachable.
//
// Non-obvious: the applicable attribute set is resolved from the product CATEGORY (IProductBL#getAttributeSetId),
// NOT from M_Product.M_AttributeSet_ID - so this file creates a per-run category ('mfgCatVT') + set
// ('mfgAttrSetVT') and points every product at it (self-sufficient on vanilla CI).

// Masterdata identifiers (the `attributes` map keys). Specs reference attributes by these identifiers
// everywhere post-response - the screen object and Backend.expect resolve each to its response-reported
// M_Attribute code (masterdata.attributes.<id>.attributeValue), so no per-run literal Value is hardcoded in a
// call site or assertion. The literal codes live only request-side: each attribute's `value` (its definition)
// and the PI writable-slot list, which the backend resolves by literal AttributeCode at request time.
const NUMBER_ATTR = 'numberAttr';
const STRING_ATTR = 'stringAttr';
const DATE_ATTR = 'dateAttr';
const MANDATORY_ATTR = 'mandatoryAttr';
const PRODUCTION_DATE_ATTR = 'productionDateAttr';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            productCategories: { 'mfgCatVT': { attributeSetName: 'mfgAttrSetVT' } },
            mobileConfig: {
                manufacturing: {
                    // Referenced by identifier - the backend resolves each to its per-run M_Attribute code.
                    editableAttributes: [
                        'bestBeforeDateAttr', 'lotNumberAttr',
                        NUMBER_ATTR, STRING_ATTR, DATE_ATTR, PRODUCTION_DATE_ATTR, MANDATORY_ATTR,
                    ],
                },
            },
            attributes: {
                [NUMBER_ATTR]: {
                    value: 'TestWeightGrams', name: 'Test Weight (g)',
                    attributeValueType: 'NUMBER',
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                [STRING_ATTR]: {
                    value: 'TestBatchNote', name: 'Test Batch Note',
                    attributeValueType: 'STRING',
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                [DATE_ATTR]: {
                    value: 'TestInspectionDate', name: 'Test Inspection Date',
                    attributeValueType: 'DATE',
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                // Configured mandatory - the mfg receive must NOT enforce it (v1 attributes are optional).
                [MANDATORY_ATTR]: {
                    value: 'TestMandatoryNote', name: 'Test Mandatory Note',
                    attributeValueType: 'STRING', isMandatory: true,
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                // Production date is a producer-managed standard attribute; upsert (by Value) and link into the set.
                [PRODUCTION_DATE_ATTR]: {
                    value: 'ProductionDate', // AttributeConstants.ProductionDate - producer-managed, no PI slot needed
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                // Re-link the seeded standard attributes so a follower relying on the default pair is not broken.
                'lotNumberAttr': { value: 'Lot-Nummer', attributeSetNames: ['mfgAttrSetVT'] },
                'bestBeforeDateAttr': { value: 'HU_BestBeforeDate', attributeSetNames: ['mfgAttrSetVT'] },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': { productCategory: 'mfgCatVT' },
                'BOM': {
                    productCategory: 'mfgCatVT',
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                // Declare the writable slots for the generic (non producer-managed) attributes - required for the
                // value to land on the produced HU. Lot/Best-before/Production-date go via the producer, no slot needed.
                'PI': {
                    lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4,
                    // Writable M_HU_PI_Attribute slots - the backend resolves these by literal AttributeCode
                    // (request-time), so they must be the M_Attribute Values, matching each attribute's `value`
                    // above. Lot/Best-before/Production-date go via the producer, no slot needed.
                    attributes: ['TestWeightGrams', 'TestBatchNote', 'TestInspectionDate', 'TestMandatoryNote'],
                },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh', product: 'BOM',
                    qty: 4, // one full TU (qtyCUsPerTU 4) - deterministic single produced TU
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

const startReceiveFlow = async (masterdata, { documentNo }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });

    return { jobId };
};

const allureMeta = (story, severity) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story(story);
    allure.severity(severity);
};

// noinspection JSUnusedLocalSymbols
test('Receive entering a NUMBER attribute — produced HU carries the number', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'critical');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectEditableAttributeVisible(NUMBER_ATTR);
    await GetQuantityDialog.typeEditableAttribute(NUMBER_ATTR, '42.5');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the entered NUMBER attribute',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [masterdata.attributes[NUMBER_ATTR].attributeValue]: '42.50' } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive leaving the NUMBER attribute empty — it stays at its default (0), not the typed value', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'normal');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    // Offered (default-optional) but left empty -> no value is submitted, so the produced HU keeps the
    // NUMBER attribute's default (0) rather than any operator-entered number.
    await GetQuantityDialog.expectEditableAttributeVisible(NUMBER_ATTR);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU keeps the NUMBER attribute at its default when left empty',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [masterdata.attributes[NUMBER_ATTR].attributeValue]: '0' } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive entering a Production Date — producer-managed attribute stamped on the produced HU', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'normal');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectEditableAttributeVisible(PRODUCTION_DATE_ATTR);
    await GetQuantityDialog.typeEditableAttributeDate(PRODUCTION_DATE_ATTR, '15.06.2025');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the entered Production Date',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, attributes: { [masterdata.attributes[PRODUCTION_DATE_ATTR].attributeValue]: '2025-06-15' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive filling NUMBER + DATE + STRING attributes together — all three stamped', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'critical');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.typeEditableAttribute(NUMBER_ATTR, '42.5');
    await GetQuantityDialog.typeEditableAttributeDate(DATE_ATTR, '20.08.2025');
    await GetQuantityDialog.typeEditableAttribute(STRING_ATTR, 'Fragile');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries all three attribute value-types entered together',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [masterdata.attributes[NUMBER_ATTR].attributeValue]: '42.50', [masterdata.attributes[DATE_ATTR].attributeValue]: '2025-08-20', [masterdata.attributes[STRING_ATTR].attributeValue]: 'Fragile' } }],
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive with a MANDATORY attribute left empty — the mfg receive does not enforce it and completes', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'normal');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    // The attribute is configured mandatory, but the mfg receive treats v1 attributes as optional:
    // leaving it empty must NOT block the receive (no error), and the HU is produced.
    await GetQuantityDialog.expectEditableAttributeVisible(MANDATORY_ATTR);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Receive completes with the mandatory attribute left empty; HU produced without it',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [masterdata.attributes[MANDATORY_ATTR].attributeValue]: null } }] } },
    });
});
