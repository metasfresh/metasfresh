import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";
import { expectErrorToast } from "../../../utils/common";
import { expect } from '@playwright/test';

// A valid 14-digit GS1 GTIN, made unique per run so the strict GTIN->product resolve
// (ProductDAO.getProductIdByGTINStrictly, AD_Client_ID=METASFRESH) matches EXACTLY one product.
// Same technique as picking_partial_unpack.spec.js / pick_by_ExternalBarcode.spec.js.
let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `9${seq}${ts}`;
};
// The GS1 scannable string for a product GTIN: Application Identifier 01 + the 14-digit GTIN.
const gs1GtinScan = (gtin14) => `01${gtin14}`;

// The order is fully picked onto one LU, then a partial qty is unpacked ("Entpacken") back onto a
// SECOND, already-open target LU that carries a legacy/customer label instead of a metasfresh QR
// code. This mirrors picking_partial_unpack.spec.js's masterdata shape (see its comments for why the
// target LU's starting fill is forced to the PI's full 20 TU x 4 = 80 PCE and cannot be created empty).
const createMasterdata = async ({ packedGtin, targetExternalBarcode } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    // Not exercised by these tests (nothing here completes a partial job), but the header
                    // row is sticky (../../../CLAUDE.md "Debugging Flaky Tests" rule 3) and this spec
                    // already writes the other picking fields on that same row, so set it explicitly too
                    // rather than leave it to whatever a previous spec/run left behind.
                    allowCompletingPartialPickingJob: true,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { gtin: packedGtin, prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                // HU1: the pickable source HU.
                HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
                // HU2: the mandatory target LU the unpicked qty is returned into. Carries the legacy
                // customer barcode under test (undefined when a test does not need it).
                HU2: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI', externalBarcode: targetExternalBarcode },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
            },
        },
    });
};

const loginAndStartJob = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    return { pickingJobId };
};

const pickAll3TUFromHU1 = async ({ masterdata, pickingJobId }) => await test.step('Pick all 3 TU (12 PCE) from HU1', async () => {
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
    // Registers the dynamically-created `lu1` HU in the backend test context so the assertions below
    // can refer to it by identifier (same mechanism used throughout picking_partial_unpack.spec.js).
    await Backend.expect({
        title: 'baseline: 12 PCE packed on lu1',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: '12 PCE', qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] },
                },
            },
        },
        hus: { lu1: { huStatus: 'S', storages: { P1: '12 PCE' } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Unpack into a legacy-labelled target LU — operator scans its printed customer barcode', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Unpack to a target LU identified by a legacy/customer label');
    allure.severity('critical');

    const targetExternalBarcode = `EXT${Date.now()}`;
    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14(), targetExternalBarcode });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });
    await pickAll3TUFromHU1({ masterdata, pickingJobId });

    // The target LU is already open on the floor and carries the customer's own printed barcode
    // (an ExternalBarcode) instead of a metasfresh QR code — the everyday case for a customer who
    // labels their own reusable pallets. The operator scans that label to return the unpicked goods.
    await test.step('Unpack 4 PCE and return them to the target LU by scanning its printed customer barcode', async () => {
        await PickingJobScreen.unpickItem({
            scannedCode: packedScan,
            expectDefaultQty: '12',
            qty: '4',
            targetHUQRCode: masterdata.handlingUnits.HU2.externalBarcode,
        });

        // 2 TU (8 PCE) stays packed on the source LU; the unpicked 4 PCE has moved into the target LU,
        // which starts at its PI-forced 80 PCE (see masterdata comment above) -> 80 + 4 = 84 PCE.
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
        await Backend.expect({
            title: 'legacy-barcode target: 8 PCE stays packed on the source LU, 4 PCE moved into the target LU (80 start + 4 = 84)',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { storages: { P1: '84 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Unpack into a target LU by scanning its plain HU value (no ExternalBarcode)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Unpack to a target LU identified by its plain M_HU.Value');
    allure.severity('critical');

    // This target LU deliberately carries NO ExternalBarcode: the operator has nothing to scan but the
    // HU's own plain value. M_HU.Value is always the M_HU_ID (confirmed by the feature owner, and
    // verified against the stack: 40/40 HUs had value = m_hu_id, none null, none differing), so the
    // value printed on such a label is exactly the huId the masterdata API already returns. No DB
    // read and no masterdata-API change is needed to know what the operator would scan.
    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });
    await pickAll3TUFromHU1({ masterdata, pickingJobId });

    await test.step('Unpack 4 PCE and return them to the target LU by scanning its plain HU value', async () => {
        await PickingJobScreen.unpickItem({
            scannedCode: packedScan,
            expectDefaultQty: '12',
            qty: '4',
            targetHUQRCode: masterdata.handlingUnits.HU2.huId,
        });

        // 2 TU (8 PCE) stays packed on the source LU; the unpicked 4 PCE has moved into the target LU,
        // which starts at its PI-forced 80 PCE (see masterdata comment above) -> 80 + 4 = 84 PCE.
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
        await Backend.expect({
            title: 'plain-HU-value target: 8 PCE stays packed on the source LU, 4 PCE moved into the target LU (80 start + 4 = 84)',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { storages: { P1: '84 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan an unrecognised code at the unpack target — clear message, nothing moves, recovery works', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Unpack target scan rejects an unrecognised code with a clear message');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;
    // A code that matches no known format at all (no metasfresh QR, no GTIN/EAN, no GRAI, no
    // ExternalBarcode/HU value on file) — the case where the system genuinely cannot identify what
    // was scanned.
    const unrecognisedCode = `NOT-A-CODE-${Date.now()}`;

    const { pickingJobId } = await loginAndStartJob({ masterdata });
    await pickAll3TUFromHU1({ masterdata, pickingJobId });

    // Drive the unpick panel up to the target-HU scan stage (product + qty already entered).
    await PickingJobScreen.unpickAdvanceToTargetStage({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4' });

    await test.step('Scan a code the system cannot identify as the unpack target — the operator must see a clear, actionable message', async () => {
        await expectErrorToast('Unrecognised code at unpack target', async () => {
            await PickingJobScreen.scanCodeAtTargetStageNoCommit({ scannedCode: unrecognisedCode });
        }, ({ textContent }) => {
            // The operator must be told their scan wasn't recognised, not handed a generic
            // "please try again / contact support" report with no actionable detail.
            expect(textContent).toContain('QR code not recognized');
        });
        // The panel did not close: the target-HU scanner is still armed so the operator can simply
        // scan the correct target LU next.
        await PickingJobScreen.expectOnTargetScanStage();
    });

    // Nothing moved: the source LU still holds all 12 PCE, the target LU is untouched.
    await Backend.expect({
        title: 'after the unrecognised scan: nothing moved, source LU still holds 12 PCE, target LU untouched',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
            [targetHUQRCode]: { storages: { P1: '80 PCE' } },
        },
    });

    await test.step('Scan the correct target LU — the unpick commits normally and the panel is usable again', async () => {
        await PickingJobScreen.scanTargetHUAndCommit({ qrCode: targetHUQRCode });
    });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
    await Backend.expect({
        title: 'recovery: 8 PCE stays packed on the source LU, 4 PCE moved into the target LU (80 + 4 = 84)',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
            [targetHUQRCode]: { storages: { P1: '84 PCE' } },
        },
    });
});
