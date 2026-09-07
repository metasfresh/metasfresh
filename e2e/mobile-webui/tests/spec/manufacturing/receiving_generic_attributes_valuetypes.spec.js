import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// Value-TYPE coverage for the mfg editable-attributes list: the generic case (receiving_generic_attributes.spec.js)
// proves a LIST attribute end to end; this file proves the remaining input widgets and apply behaviours on the
// REAL receive dialog, so the backend cucumber twins (Receive_with_CatchWeight.feature) are no longer needed:
//   - a NUMBER attribute entered -> produced HU carries the number; left empty -> stays at its default (0);
//   - a Production Date (producer-managed) entered -> stamped on the produced HU;
//   - NUMBER + DATE + STRING filled together in one receive -> all three stamped;
//   - a MANDATORY attribute left empty -> the mfg receive does NOT enforce it, the receive still completes.
// (The "attribute stamped on every produced HU when a line yields MORE THAN ONE TU" case stays a cucumber
//  scenario: the mobile receive aggregates the produced qty into a SINGLE HU, so multiple produced TUs are
//  not reachable through the dialog - only via the backend receipt event.)
//
// Product attribute set: MaterialReceiptActivityHandler resolves the applicable attribute set from the product's
// CATEGORY (IProductBL#getAttributeSetId), NOT from M_Product.M_AttributeSet_ID. So this file creates its OWN
// per-run product category ('mfgCatVT') whose set ('mfgAttrSetVT') carries the attributes, and points every product
// there - independent of the preloaded standard category, so it works on the vanilla CI DB (fresh-fixture rule).
//
// mobileConfig.manufacturing.editableAttributes is a GLOBAL, REPLACE-on-write list; the list below re-includes the
// seeded Lot-Nummer / HU_BestBeforeDate so a spec that relies on the default pair (receiving_editable_attributes.spec.js)
// is not left broken if it runs after this one (e2e/mobile-webui/CLAUDE.md "sticky mobileConfig fields").

// Generic (non producer-managed) attribute codes must be declared on the TU's PI version (the writable
// M_HU_PI_Attribute slot) for the value to land on the produced HU's own storage - see the PI 'attributes' below.
const NUMBER_CODE = 'TestWeightGrams';
const STRING_CODE = 'TestBatchNote';
const DATE_CODE = 'TestInspectionDate';
const MANDATORY_CODE = 'TestMandatoryNote';
const PRODUCTION_DATE_CODE = 'ProductionDate'; // AttributeConstants.ProductionDate - producer-managed, no PI slot needed

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            productCategories: { 'mfgCatVT': { attributeSetName: 'mfgAttrSetVT' } },
            mobileConfig: {
                manufacturing: {
                    editableAttributes: [
                        'HU_BestBeforeDate', 'Lot-Nummer',
                        NUMBER_CODE, STRING_CODE, DATE_CODE, PRODUCTION_DATE_CODE, MANDATORY_CODE,
                    ],
                },
            },
            attributes: {
                'numberAttr': {
                    value: NUMBER_CODE, name: 'Test Weight (g)',
                    attributeValueType: 'NUMBER', isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                'stringAttr': {
                    value: STRING_CODE, name: 'Test Batch Note',
                    attributeValueType: 'STRING', isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                'dateAttr': {
                    value: DATE_CODE, name: 'Test Inspection Date',
                    attributeValueType: 'DATE', isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                // Configured mandatory - the mfg receive must NOT enforce it (v1 attributes are optional).
                'mandatoryAttr': {
                    value: MANDATORY_CODE, name: 'Test Mandatory Note',
                    attributeValueType: 'STRING', isInstanceAttribute: true, isMandatory: true,
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                // Production date is a producer-managed standard attribute; upsert (by Value) and link into the set.
                'productionDateAttr': {
                    value: PRODUCTION_DATE_CODE, isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSetVT'],
                },
                // Re-link the seeded standard attributes so a follower relying on the default pair is not broken.
                'lotNumberAttr': { value: 'Lot-Nummer', isInstanceAttribute: true, attributeSetNames: ['mfgAttrSetVT'] },
                'bestBeforeDateAttr': { value: 'HU_BestBeforeDate', isInstanceAttribute: true, attributeSetNames: ['mfgAttrSetVT'] },
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
                    attributes: [NUMBER_CODE, STRING_CODE, DATE_CODE, MANDATORY_CODE],
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
    await GetQuantityDialog.expectEditableAttributeVisible(NUMBER_CODE);
    await GetQuantityDialog.typeEditableAttribute(NUMBER_CODE, '42.5');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the entered NUMBER attribute',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [NUMBER_CODE]: '42.50' } }] } },
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
    await GetQuantityDialog.expectEditableAttributeVisible(NUMBER_CODE);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU keeps the NUMBER attribute at its default when left empty',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [NUMBER_CODE]: '0' } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive entering a Production Date — producer-managed attribute stamped on the produced HU', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'normal');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectEditableAttributeVisible(PRODUCTION_DATE_CODE);
    await GetQuantityDialog.typeEditableAttributeDate(PRODUCTION_DATE_CODE, '15.06.2025');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the entered Production Date',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, attributes: { [PRODUCTION_DATE_CODE]: '2025-06-15' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive filling NUMBER + DATE + STRING attributes together — all three stamped', async ({ page }) => {
    allureMeta('Generic editable attributes on the receive dialog', 'critical');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, { documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.typeEditableAttribute(NUMBER_CODE, '42.5');
    await GetQuantityDialog.typeEditableAttributeDate(DATE_CODE, '20.08.2025');
    await GetQuantityDialog.typeEditableAttribute(STRING_CODE, 'Fragile');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries all three attribute value-types entered together',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [NUMBER_CODE]: '42.50', [DATE_CODE]: '2025-08-20', [STRING_CODE]: 'Fragile' } }],
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
    await GetQuantityDialog.expectEditableAttributeVisible(MANDATORY_CODE);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Receive completes with the mandatory attribute left empty; HU produced without it',
        manufacturings: { [jobId]: { receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }] } },
        hus: { 'lu1': { storages: { 'BOM': '4 PCE' }, tus: [{ storages: { 'BOM': '4 PCE' }, attributes: { [MANDATORY_CODE]: null } }] } },
    });
});
