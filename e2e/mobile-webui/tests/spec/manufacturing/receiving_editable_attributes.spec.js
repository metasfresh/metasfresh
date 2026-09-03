import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// Lot + Best-Before editability on the mobile Produktion receive dialog is ON by default,
// so the dialog offers both inputs without any config override (empty mobileConfig) - this spec
// deliberately exercises that DEFAULT-ON path and adds NO editableAttributes list.
//
// Product attribute set: MaterialReceiptActivityHandler resolves the applicable attribute set from the
// product's CATEGORY (IProductBL#getAttributeSetId(I_M_Product) - "take it from the product category,
// never from the product itself"), NOT from M_Product.M_AttributeSet_ID. So for Lot-Nummer /
// HU_BestBeforeDate to be resolvable on the produced HU, this spec creates its OWN per-run product
// category ('mfgCat') whose attribute set ('mfgAttrSet') carries both as INSTANCE attributes, and points
// every product at that category - independent of the preloaded standard category, so it works on the
// vanilla CI DB (fresh-fixture rule) as well as any local dump. (Earlier this spec relied on the seeded
// Lot/Best-before links on the standard category's set, which differs on CI's vanilla DB - inert there.)
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            // Per-run product category + its attribute set - products below reference it by key, and the
            // attributes below link into 'mfgAttrSet' by name.
            productCategories: { 'mfgCat': { attributeSetName: 'mfgAttrSet' } },
            attributes: {
                // Upserts (by Value) the two pre-existing standard attributes and links them into the
                // per-run attribute set as instance attributes - so the products' category resolves a set
                // that carries Lot / Best-before, independent of the seeded standard category.
                'lotNumberAttr': {
                    value: 'Lot-Nummer',
                    isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSet'],
                },
                'bestBeforeDateAttr': {
                    value: 'HU_BestBeforeDate',
                    isInstanceAttribute: true,
                    attributeSetNames: ['mfgAttrSet'],
                },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': { productCategory: 'mfgCat' },
                'BOM': {
                    productCategory: 'mfgCat',
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
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

const startReceiveFlow = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });

    return { jobId };
};

// noinspection JSUnusedLocalSymbols
test('Receive finished goods entering Lot + Best-Before — produced HU carries both attributes', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Editable Lot / Best-Before on the receive dialog');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata);

    const lotNo = `LOT-${Date.now()}`;

    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '5',
        expectLotNoVisible: true,
        expectBestBeforeDateVisible: true,
        lotNo,
        bestBeforeDate: '23.11.2031',
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the entered Lot and Best-Before',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '5 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '5 PCE' },
                attributes: {
                    'Lot-Nummer': lotNo,
                    'HU_BestBeforeDate': '2031-11-23',
                },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive finished goods leaving Lot + Best-Before empty — no attribute is set', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Editable Lot / Best-Before on the receive dialog');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata);

    // Inputs are offered (default ON) but the operator leaves them empty — the produced HU
    // must NOT get a Lot / Best-Before attribute (default-ON must not fabricate values).
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '5',
        expectLotNoVisible: true,
        expectBestBeforeDateVisible: true,
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU has no Lot / Best-Before when left empty',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '5 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '5 PCE' },
                attributes: {
                    'Lot-Nummer': null,
                    'HU_BestBeforeDate': null,
                },
            },
        },
    });
});
