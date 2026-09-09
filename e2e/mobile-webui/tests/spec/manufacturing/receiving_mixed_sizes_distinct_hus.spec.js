import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { VIRTUAL_TU_TARGET_TESTID } from '../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';

// =============================================================================================
// Editable-attributes (plant-nursery "size in cm") - receiving mixed plant SIZES into DISTINCT
// handling units so each produced plant HU carries its OWN "Size (cm)" (size) attribute.
//
// The master data mirrors a real plant-nursery configuration: attribute "Size (cm)" is a LIST
// (AttributeValueType='L'), instance-level (IsInstanceAttribute='Y'), not mandatory, with cm list
// values 0/10/15/17/19/21/25, and is a member of the product category's attribute set.
//
// CONFIG: the packing instruction declares the writable M_HU_PI_Attribute slot for "Size (cm)" on the
// system VIRTUAL CU PI version (M_HU_PI_ID=101 - the level every loose CU/VHU sits on) via
// `packingInstructions.PI.cuAttributes: ['TestSizeCm']`, NOT on the TU version. A generic LIST
// attribute submitted at a mobile receive is stamped onto a produced HU ONLY when that HU's own
// M_HU_PI_Version has a matching M_HU_PI_Attribute row - the `hasAttribute` guard in
// HUAttributesBL.updateHUAttributeRecursive0 reads the HU's PI-version slots
// (AbstractHUAttributeStorage.loadAttributeValues), NOT the product's M_AttributeSet. The product
// M_AttributeSet only gates what the mobile UI OFFERS (MaterialReceiptActivityHandler.buildEditableAttributes).
// HUPIAttributesDAO.retrievePIAttributes resolves a version's slots as its OWN direct rows plus the
// TEMPLATE's (M_HU_PI_ID=100): so a slot on the VIRTUAL version (101) reaches every CU/VHU, yet is absent
// from the TU/LU (direct(TU/LU)+template, neither carrying the size) - the size lands on the plant (CU/VHU),
// the container (TU/LU) stays neutral. See JsonPackingInstructionsRequest#cuAttributes Javadoc.
//
// THREE CASES (each receives two different sizes and asserts the DESIRED invariant: distinct plant HUs,
// one per size, never overwritten or merged into a single attribute-less HU):
//   1. Floor      - two loose-CU receives (sizes 15, 21) -> two distinct bare VHUs, each with its size.
//   2. Shared TU  - two receives (sizes 15, 21) into ONE TU -> two distinct CUs of different size in it.
//   3. Shared LU  - receive size 15 into a NEW LU, then receive size 21 into that SAME LU -> the shared
//                   LU holds TWO distinct VHUs of different size, its direct children being TUs
//                   (LU->TU->CU, the shape the mobile mfg receive actually produces - loose-CU-directly-
//                   on-LU is not producible by the receive, a separate documented limitation).
//
// OUTCOME (DB-verified via m_hu_attribute on the local scrambled stack): each produced CU/VHU carries its
// OWN selected size, distinctly, and no two are merged. Because the slot is on the VIRTUAL CU PI (101),
// every CU/VHU is materialised with the "Size (cm)" slot, so the receive stamps the size at the CU
// level: the floor case's two bare VHUs carry 15 and 21; the shared-TU case's two inner CUs carry 15 and
// 21 in the one TU; the shared-LU case's two TUs each hold a CU carrying 15 and 21. The TU/LU themselves
// carry NO size (their PI versions have no such slot), so a mixed-size container is never mislabelled with
// one size and distinct-size CUs never collapse into a single attribute-less HU. All three tests pass.
//
// The slot MUST sit on the VIRTUAL CU PI (101), not the TU PI: a TU-version slot stamps the TU and never
// reaches the inner CU (retrievePIAttributes does NOT push a TU-version slot down to the CU), which is what
// left the CU attribute-less and the two CUs merging. Do NOT move the slot back to the TU (`attributes`) and
// do NOT weaken the CU-level assertions - they encode the customer requirement (mixed sizes = distinct plants).
// =============================================================================================

// One shared masterdata builder for every test in this file (e2e/CLAUDE.md "shared createMasterdata" rule).
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            // Per-run product category + its attribute set - the "Artikel"-style set is resolved from the
            // product CATEGORY (IProductBL#getAttributeSetId), so every product points at this category.
            productCategories: { 'artikelCat': { attributeSetName: 'artikelSet' } },
            mobileConfig: {
                manufacturing: {
                    // "Size (cm)" offered as an editable attribute on the receive dialog...
                    editableAttributes: ['sizeAttr'],
                    // ...and the loose-CU "No Packing Item" (virtual) target must be available for the floor
                    // scenario (test 1) - it is what lets a plant be received without a TU/LU packing.
                    isAllowReceiveWithoutPackingItem: true,
                },
            },
            attributes: {
                // The "Size (cm)" LIST attribute: instance-level (default true, mirrors
                // IsInstanceAttribute='Y'); NO isMandatory (mirrors 'N'). Real cm list values.
                'sizeAttr': {
                    value: 'TestSizeCm',
                    name: 'Size (cm)',
                    attributeValueType: 'LIST',
                    listValues: [
                        { value: '0' },
                        { value: '10' },
                        { value: '15' },
                        { value: '17' },
                        { value: '19' },
                        { value: '21' },
                        { value: '25' },
                    ],
                    attributeSetNames: ['artikelSet'],
                },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': { productCategory: 'artikelCat' },
                'BOM': {
                    productCategory: 'artikelCat',
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                // A lu+tu+qtyCUsPerTU PI for the shared-TU/shared-LU tests. Declares the writable
                // M_HU_PI_Attribute slot for "Size (cm)" on the system VIRTUAL CU PI version
                // (M_HU_PI_ID=101 - the level every loose CU/VHU sits on) via `cuAttributes`, NOT on the
                // TU version. This lands the selected size on each produced CU/VHU's own storage (so mixed
                // sizes coexist as distinct plant HUs, one per size) while leaving the TU/LU a neutral
                // container. The apply path's hasAttribute guard reads the HU's own PI version, and
                // HUPIAttributesDAO.retrievePIAttributes resolves a version's slots as its own direct rows
                // plus the TEMPLATE's (100) - so a slot on the VIRTUAL version (101) reaches every CU/VHU
                // yet is absent from the TU (direct(TU)+template, neither carrying the size). A TU-version
                // slot (the `attributes` field, as in receiving_generic_attributes.spec.js) would instead
                // stamp the TU and never reach the inner CU. See JsonPackingInstructionsRequest#getCuAttributes.
                // The VIRTUAL PI is global, so this one declaration also covers the floor case's bare VHU
                // (received onto the "No Packing Item" virtual target, which has no per-run PI of its own).
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4, cuAttributes: ['TestSizeCm'] },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 20, // generous - two small receives of different sizes fit comfortably
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
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
    return { jobId };
};

// noinspection JSUnusedLocalSymbols
test('Floor: two loose-CU receives of different sizes produce two distinct VHUs, each keeping its own size', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8047: MobileUI Manufacturing Profile');
    allure.tag('F8047');
    allure.story('Receiving mixed sizes into distinct handling units');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const sizeCode = masterdata.attributes.sizeAttr.attributeValue;
    const { jobId } = await startJob(masterdata);

    // --- Receive #1: size 15 cm into a bare virtual HU ("No Packing Item" target) ---
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: VIRTUAL_TU_TARGET_TESTID });
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: '3',
        listAttributes: { sizeAttr: '15' },
        expectGoBackToJob: true,
    });

    // Gate the backend read on a commit-confirming, user-visible signal: the receive button now shows the
    // received qty (the job screen re-fetched the WFProcess AFTER the mfg receive committed). Reading the
    // backend before this can read pre-commit state - the documented async-commit race (CLAUDE.md § "A RED
    // is not a bug until you rule out the test harness").
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '20 Stk', qtyReceived: '3 Stk' });

    // --- The bare VHU carries the selected size 15 ---
    // A bare VHU uses the system VIRTUAL PI (101); with the "Size (cm)" slot declared on that VIRTUAL CU
    // PI version (cuAttributes above), the VHU is materialised with the slot, so the receive stamps the size
    // onto it (rather than dropping it as it would under a slotless config).
    await Backend.expect({
        title: 'Receive #1 - the bare VHU carries the selected size 15',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ hu: 'vhu1', qty: '3 PCE' }],
            },
        },
        hus: {
            'vhu1': {
                huType: 'V',
                storages: { 'BOM': '3 PCE' },
                attributes: { [sizeCode]: '15' },
            },
        },
    });

    // --- Receive #2: size 21 cm into a second bare virtual HU ---
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: VIRTUAL_TU_TARGET_TESTID });
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: '3',
        listAttributes: { sizeAttr: '21' },
        expectGoBackToJob: true,
    });

    // Gate on the commit-confirming, user-visible signal (both receives landed: 3+3=6) before completing
    // and reading the backend - guards the async-commit race.
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '20 Stk', qtyReceived: '6 Stk' });

    await ManufacturingJobScreen.complete();

    // End result: TWO distinct bare VHUs, each carrying its OWN size - neither overwritten nor merged.
    await Backend.expect({
        title: 'Two distinct VHUs, one per size (15 and 21) - neither overwritten',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { hu: 'vhu1', qty: '3 PCE' },
                    { hu: 'vhu2', qty: '3 PCE' },
                ],
            },
        },
        hus: {
            'vhu1': { huType: 'V', storages: { 'BOM': '3 PCE' }, attributes: { [sizeCode]: '15' } },
            'vhu2': { huType: 'V', storages: { 'BOM': '3 PCE' }, attributes: { [sizeCode]: '21' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Shared TU: two receives of different sizes yield two distinct CUs with different size in one TU', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8047: MobileUI Manufacturing Profile');
    allure.tag('F8047');
    allure.story('Receiving mixed sizes into distinct handling units');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const sizeCode = masterdata.attributes.sizeAttr.attributeValue;
    const { jobId } = await startJob(masterdata);

    // --- Receive #1: size 15 cm into a NEW TU ---
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: '2',
        listAttributes: { sizeAttr: '15' },
        expectGoBackToJob: true,
    });

    // Gate on the commit-confirming, user-visible signal (receive button shows the received qty) before
    // reading the backend - guards the async-commit race.
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '20 Stk', qtyReceived: '2 Stk' });

    // Bind the produced TU identifier 'tu1' so we can resolve its QR code and receive INTO it next.
    await Backend.expect({
        title: 'Receive #1 - a new TU holds one CU of size 15',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ tu: 'tu1', qty: '2 PCE' }],
            },
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '2 PCE' },
                cus: [
                    { qty: '2 PCE', attributes: { [sizeCode]: '15' } },
                ],
            },
        },
    });

    const huQRCode = await Backend.getHUQRCodeByIdentifier({ identifier: 'tu1' });

    // --- Receive #2: size 21 cm INTO the same existing TU ---
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode });
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: '2',
        listAttributes: { sizeAttr: '21' },
        expectGoBackToJob: true,
    });

    // Gate on the commit-confirming, user-visible signal (both receives into the shared TU landed: 2+2=4)
    // before completing and reading the backend - guards the async-commit race.
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '20 Stk', qtyReceived: '4 Stk' });

    await ManufacturingJobScreen.complete();

    // End result: the SHARED tu1 holds TWO distinct CUs, one per size (15 and 21) - a distinct-attribute
    // CU is never merged into the other. Each CU sits on the VIRTUAL CU PI (101), which carries the
    // "Size (cm)" slot, so each keeps its own size. (Under a slotless config the sizes would be dropped,
    // both CUs attribute-less, and they would merge into a single 4-PCE CU.)
    await Backend.expect({
        title: 'The shared TU holds two distinct CUs, one carrying size 15 and one size 21',
        hus: {
            'tu1': {
                storages: { 'BOM': '4 PCE' },
                cus: [
                    { qty: '2 PCE', attributes: { [sizeCode]: '15' } },
                    { qty: '2 PCE', attributes: { [sizeCode]: '21' } },
                ],
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Shared LU: two receives of different sizes yield two distinct TUs (each one CU) of different size on one LU', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8047: MobileUI Manufacturing Profile');
    allure.tag('F8047');
    allure.story('Receiving mixed sizes into distinct handling units');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const sizeCode = masterdata.attributes.sizeAttr.attributeValue;
    const { jobId } = await startJob(masterdata);

    // --- Receive #1: size 15 cm into a NEW LU (LU->TU->CU) ---
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: '2',
        listAttributes: { sizeAttr: '15' },
        expectGoBackToJob: true,
    });

    // Gate on the commit-confirming, user-visible signal (receive button shows the received qty) before
    // reading the backend - guards the async-commit race.
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '20 Stk', qtyReceived: '2 Stk' });

    // Bind the produced LU identifier 'lu1' (from the received HU's loading unit) so we can resolve its
    // QR code and receive INTO the same LU next.
    await Backend.expect({
        title: 'Receive #1 - a new LU is produced holding the size-15 receive',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '2 PCE' }],
            },
        },
    });

    const huQRCode = await Backend.getHUQRCodeByIdentifier({ identifier: 'lu1' });

    // --- Receive #2: size 21 cm INTO the same existing LU ---
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode });
    await MaterialReceiptLineScreen.receiveQty({
        qtyEntered: '2',
        listAttributes: { sizeAttr: '21' },
        expectGoBackToJob: true,
    });

    // Gate on the commit-confirming, user-visible signal (both receives into the shared LU landed: 2+2=4)
    // before completing and reading the backend - guards the async-commit race.
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '20 Stk', qtyReceived: '4 Stk' });

    await ManufacturingJobScreen.complete();

    // End result: the SHARED lu1 holds TWO distinct size-bearing CUs, one per size (15 and 21), each inside
    // its OWN transport unit (LU->TU->CU) - a distinct-attribute CU is never merged into the other.
    //
    // The LU's direct children are asserted as [aggregate placeholder, TU, TU], not [TU, TU]: two separate
    // PARTIAL receives (2 of the 4 CUs/TU) into an LU that still has spare TU capacity materialize as TWO
    // concrete TUs AND leave an EMPTY aggregate-TU placeholder on the LU (M_HU_Item ITEMTYPE_HUAggregate, no
    // storage). That placeholder is inherent to the LU/TU aggregation model - present regardless of the size
    // attribute - so getIncludedHUs(lu) returns THREE children. Asserting the empty placeholder slot plus
    // the two concrete TUs (each a LU->TU->CU carrying its own size) keeps the shape faithful to what the
    // receive really produces and green now the size is persistable. With the "Size (cm)" slot on the
    // VIRTUAL CU PI (101), each TU's inner CU carries its own size (15 / 21); under a slotless config the
    // size would be dropped and the per-CU size assertions would fail.
    await Backend.expect({
        title: 'The shared LU holds two distinct size-bearing CUs (15, 21), each in its own TU (LU->TU->CU)',
        hus: {
            'lu1': {
                huType: 'LU',
                storages: { 'BOM': '4 PCE' }, // both 2-PCE receives on one pallet
                tus: [
                    // The empty aggregate-TU placeholder for the LU's remaining (unmaterialized) capacity.
                    // No huType asserted here on purpose: an empty aggregate has no resolvable
                    // M_HU_PI_Version, so a huType check would throw rather than fail cleanly.
                    { isAggregatedTU: true },
                    // Receive #1 -> its own concrete TU, holding one CU of size 15.
                    { huType: 'TU', storages: { 'BOM': '2 PCE' }, cus: [{ qty: '2 PCE', attributes: { [sizeCode]: '15' } }] },
                    // Receive #2 -> its own concrete TU, holding one CU of size 21.
                    { huType: 'TU', storages: { 'BOM': '2 PCE' }, cus: [{ qty: '2 PCE', attributes: { [sizeCode]: '21' } }] },
                ],
            },
        },
    });
});
