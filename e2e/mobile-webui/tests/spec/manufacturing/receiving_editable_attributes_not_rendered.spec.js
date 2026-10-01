import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

// When the editable-attributes section must NOT render (or must exclude an attribute), proven on the REAL dialog -
// replacing the backend response-builder unit tests (MaterialReceiptActivityHandler.buildEditableAttributes):
//   - a product whose CATEGORY has NO attribute set -> no editable-attributes section at all;
//   - a product-level (isInstanceAttribute=false) attribute, even when configured editable, is EXCLUDED
//     (the dialog only offers instance-level attributes of the product's set).
//
// MaterialReceiptActivityHandler resolves the set from the product CATEGORY; buildEditableAttributes returns empty
// when the category has no set, and reads only the INSTANCE attributes of that set. Both products below get their
// own per-run category so the file is independent of the preloaded standard category (fresh-fixture rule).
//
// The seeded Lot-Nummer / HU_BestBeforeDate are re-included in the global editableAttributes list so a follower
// relying on the default pair (receiving_editable_attributes.spec.js) is not left broken (sticky mobileConfig).

const VISIBLE_CODE = 'TestVisibleInstanceAttr';      // instance-level, configured -> shown
const PRODUCT_LEVEL_CODE = 'TestProductLevelAttr';   // product-level (non-instance), configured -> excluded

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            productCategories: {
                'mfgCatWithSet': { attributeSetName: 'mfgAttrSetNR' },
                'mfgCatNoSet': {}, // no attribute set -> its products offer no editable attributes at all
            },
            mobileConfig: {
                manufacturing: {
                    // Both codes are configured editable; the product-level one must still be excluded by the
                    // instance-only resolver. Default pair re-included to protect followers.
                    editableAttributes: ['HU_BestBeforeDate', 'Lot-Nummer', VISIBLE_CODE, PRODUCT_LEVEL_CODE],
                },
            },
            attributes: {
                'visibleAttr': {
                    value: VISIBLE_CODE, name: 'Test Visible Instance Attr',
                    attributeValueType: 'STRING', isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSetNR'],
                },
                'productLevelAttr': {
                    value: PRODUCT_LEVEL_CODE, name: 'Test Product Level Attr',
                    attributeValueType: 'STRING', isInstanceAttribute: false,
                    attributeSetNames: ['mfgAttrSetNR'],
                },
                'lotNumberAttr': { value: 'Lot-Nummer', isInstanceAttribute: true, attributeSetNames: ['mfgAttrSetNR'] },
                'bestBeforeDateAttr': { value: 'HU_BestBeforeDate', isInstanceAttribute: true, attributeSetNames: ['mfgAttrSetNR'] },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': { productCategory: 'mfgCatWithSet' },
                'BOM_WITHSET': {
                    productCategory: 'mfgCatWithSet',
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
                'BOM_NOSET': {
                    productCategory: 'mfgCatNoSet',
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                'PI_WITHSET': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM_WITHSET', qtyCUsPerTU: 4 },
                'PI_NOSET': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'BOM_NOSET', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                'PP_WITHSET': { warehouse: 'wh', product: 'BOM_WITHSET', qty: 4, datePromised: '2025-03-01T00:00:00.000+02:00' },
                'PP_NOSET': { warehouse: 'wh', product: 'BOM_NOSET', qty: 4, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
};

const startReceiveFlow = async (masterdata, { documentNo, luPIItemTestId }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId });
};

const allureMeta = (severity) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Generic editable attributes on the receive dialog');
    allure.severity(severity);
};

// noinspection JSUnusedLocalSymbols
test('A product whose category has no attribute set — the receive dialog shows no editable-attributes section', async ({ page }) => {
    allureMeta('normal');

    const masterdata = await createMasterdata();
    await startReceiveFlow(masterdata, {
        documentNo: masterdata.manufacturingOrders.PP_NOSET.documentNo,
        luPIItemTestId: masterdata.packingInstructions.PI_NOSET.luPIItemTestId,
    });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    // No attribute set on the product's category -> nothing editable, not even the configured attributes.
    await GetQuantityDialog.expectEditableAttributesSectionNotVisible();
});

// noinspection JSUnusedLocalSymbols
test('A configured product-level (non-instance) attribute is excluded — only instance attributes are offered', async ({ page }) => {
    allureMeta('normal');

    const masterdata = await createMasterdata();
    await startReceiveFlow(masterdata, {
        documentNo: masterdata.manufacturingOrders.PP_WITHSET.documentNo,
        luPIItemTestId: masterdata.packingInstructions.PI_WITHSET.luPIItemTestId,
    });

    await page.getByTestId('receive-qty-button').tap();
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectEditableAttributesSectionVisible();
    await GetQuantityDialog.expectEditableAttributeVisible(VISIBLE_CODE);
    // Configured editable, but product-level (isInstanceAttribute=false) -> must NOT be offered.
    await GetQuantityDialog.expectEditableAttributeNotVisible(PRODUCT_LEVEL_CODE);
});
