import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// Generic mfg editable attributes (ANY configured M_Attribute, not just Lot/Best-before), proven via a
// "size (cm)" LIST attribute: configure + select -> produced HU carries it; a not-configured attribute is
// not shown; a co-product line offers it too; field order follows the config's order.
//
// Non-obvious: the applicable attribute set is resolved from the product CATEGORY
// (IProductBL#getAttributeSetId), NOT from M_Product.M_AttributeSet_ID - so this spec creates a per-run
// category ('mfgCat') + set ('mfgAttrSet') and points every product at it (self-sufficient on vanilla CI).

// One shared masterdata builder for every test in this file (e2e/CLAUDE.md "shared createMasterdata" rule).
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            // Per-run product category + its attribute set - products below reference it by key, and the
            // attributes below link into 'mfgAttrSet' by name.
            productCategories: { 'mfgCat': { attributeSetName: 'mfgAttrSet' } },
            mobileConfig: {
                manufacturing: {
                    // Ordered list of masterdata attribute IDENTIFIERS (the `attributes` map keys below),
                    // so this spec exercises MobileConfigManufacturingCommand.resolveEditableAttributeCodes'
                    // identifier-first resolution (each key resolves to its M_Attribute.Value code).
                    // Order deliberately does NOT match alphabetical/pairing order, so the rendered field
                    // order proves it follows THIS list (SeqNo), not some other ordering.
                    // 'notConfiguredAttr' is deliberately excluded - present on the attribute set
                    // (isInstanceAttribute) but NOT configured as editable.
                    editableAttributes: ['bestBeforeDateAttr', 'sizeAttr', 'lotNumberAttr'],
                },
            },
            attributes: {
                // A LIST attribute - the driving "size (cm)" use case.
                'sizeAttr': {
                    value: 'TestSizeCM',
                    name: 'Size (cm)',
                    attributeValueType: 'LIST',
                    listValues: [
                        { value: 'S', name: 'Small' },
                        { value: 'M', name: 'Medium' },
                        { value: 'L', name: 'Large' },
                    ],
                    attributeSetNames: ['mfgAttrSet'],
                },
                // Present on the attribute set and instance-level, but never added to the mfg config's
                // editableAttributes above - proves a not-configured attribute is not shown.
                'notConfiguredAttr': {
                    value: 'TestNotConfiguredCM',
                    name: 'Not Configured Attr',
                    attributeValueType: 'STRING',
                    attributeSetNames: ['mfgAttrSet'],
                },
                // Upserts (by Value) the two pre-existing standard attributes and (re-)links them into the
                // shared test attribute set. Without this, any spec run AFTER this one that relies on the
                // seeded Lot/Best-before default would find them unconfigured.
                'lotNumberAttr': {
                    value: 'Lot-Nummer',
                    attributeSetNames: ['mfgAttrSet'],
                },
                'bestBeforeDateAttr': {
                    value: 'HU_BestBeforeDate',
                    attributeSetNames: ['mfgAttrSet'],
                },
            },
            warehouses: { 'wh': {} },
            products: {
                // Every product resolves its editable attributes from its product CATEGORY's attribute set
                // (MaterialReceiptActivityHandler -> ProductBL.getAttributeSetId reads M_Product_Category.M_AttributeSet_ID),
                // so each must point at the per-run 'mfgCat' category whose set is 'mfgAttrSet' (the set the
                // attributes above are linked into) - NOT the hardcoded STANDARD category.
                'COMP1': { productCategory: 'mfgCat' },
                // Referenced by BOM_WITH_BYPRODUCT's bom line below - must be created before it
                // (createProducts processes products in the given map order).
                'BY_PRODUCT': { productCategory: 'mfgCat' },
                'BOM': {
                    productCategory: 'mfgCat',
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
                // A second finished good with a by-product line, used only by the co-product test below -
                // kept separate from BOM/PP1 so that test's completion is never gated on a by-product receipt.
                'BOM_WITH_BYPRODUCT': {
                    productCategory: 'mfgCat',
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'BY_PRODUCT', qty: -10, percentage: true, componentType: 'BY' },
                        ],
                    },
                },
            },
            packingInstructions: {
                'PI': {
                    lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4,
                    // Declares the writable M_HU_PI_Attribute slot for sizeAttr on the TU's PI version -
                    // required for the value to actually land on the produced HU's own storage (the apply
                    // path's hasAttribute guard reads the HU's own PI version, not the product's attribute
                    // set - see JsonPackingInstructionsRequest#getAttributes Javadoc).
                    attributes: ['TestSizeCM'],
                },
                'BOM_WITH_BYPRODUCT_PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU3', product: 'BOM_WITH_BYPRODUCT', qtyCUsPerTU: 4 },
                'BY_PRODUCT_PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'BY_PRODUCT', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 4, // exactly one full TU (qtyCUsPerTU: 4) - deterministic single produced TU
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
                'PP2': {
                    warehouse: 'wh',
                    product: 'BOM_WITH_BYPRODUCT',
                    qty: 4,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

const startReceiveFlow = async (masterdata, { documentNo, receiveTargetPIItemTestId }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: receiveTargetPIItemTestId });

    return { jobId };
};

// noinspection JSUnusedLocalSymbols
test('Receive selecting a LIST attribute — produced HU carries it; not-configured attribute not shown; field order follows config', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Generic editable attributes on the receive dialog');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata, {
        documentNo: masterdata.manufacturingOrders.PP1.documentNo,
        receiveTargetPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId,
    });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();

    // Field order follows the config's given order (bestBeforeDateAttr, sizeAttr, lotNumberAttr) - not
    // alphabetical, not the Lot-then-BestBefore pairing - proving SeqNo (the config's order) drives it.
    // Attributes are referenced by their masterdata identifier; the screen object resolves each to its
    // per-run code.
    await GetQuantityDialog.expectEditableAttributesSectionVisible();
    await GetQuantityDialog.expectEditableAttributeVisible('sizeAttr');

    // An attribute on the attribute set but NOT in the mfg config's editableAttributes list must not be
    // offered at all.
    await GetQuantityDialog.expectEditableAttributeNotVisible('notConfiguredAttr');
    await GetQuantityDialog.expectEditableAttributesOrder(['bestBeforeDateAttr', 'sizeAttr', 'lotNumberAttr']);

    // Select the LIST value and complete the receive.
    await GetQuantityDialog.selectEditableAttribute('sizeAttr', 'M');
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '4', qtyEntered: '4' });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the selected LIST attribute value',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '4 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '4 PCE' },
                tus: [
                    {
                        storages: { 'BOM': '4 PCE' },
                        attributes: { [masterdata.attributes.sizeAttr.attributeValue]: 'M' },
                    },
                ],
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('A co-product receive line also offers the configured attribute', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Generic editable attributes on the receive dialog');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    await startReceiveFlow(masterdata, {
        documentNo: masterdata.manufacturingOrders.PP2.documentNo,
        receiveTargetPIItemTestId: masterdata.packingInstructions.BOM_WITH_BYPRODUCT_PI.luPIItemTestId,
    });
    await MaterialReceiptLineScreen.goBack();

    // The by-product line (index 2, per the by-products convention - see receiving_by_products.spec.js)
    // offers the same configured attribute, because BY_PRODUCT is in the same 'mfgCat' category and so
    // resolves the same 'mfgAttrSet' attribute set.
    await ManufacturingJobScreen.clickReceiveButton({ index: 2 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.BY_PRODUCT_PI.luPIItemTestId });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectEditableAttributeVisible('sizeAttr');
});
