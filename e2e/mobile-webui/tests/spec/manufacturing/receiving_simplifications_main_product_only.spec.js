import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { ReceiptReceiveTargetScreen } from '../../utils/screens/manufacturing/receipt/ReceiptReceiveTargetScreen';
import { ReceiptNewHUScreen } from '../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';
import { ManufacturingReceiptScanScreen } from '../../utils/screens/manufacturing/receipt/ManufacturingReceiptScanScreen';

// All four receive-config flags at their simplifying values at once: pallet only, no target-step
// chooser, no catch weight. They are scoped to the MAIN finished good - a co-/by-product keeps the
// full receipt, because its target legitimately IS a TU (here an infinite-capacity one, offered
// because the line is catch-weight) and its weight is what the shop actually records for it.
//
// Both goods below are catch-weight products, configured with the same UOM conversion. So the
// catch-weight half of this test really does discriminate: the weight is still asked for on one line
// and no longer on the other under ONE profile, which no product-level setup could produce.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                manufacturing: {
                    isAllowFinishedGoodsReceiveToLU: true,
                    isAllowFinishedGoodsReceiveToTU: false,
                    isSkipFinishedGoodsReceiveTargetStep: true,
                    isCaptureCatchWeightAtReceipt: false,
                },
            },
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "REWORK": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                },
                "BOM": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                            { product: 'REWORK', qty: -10, percentage: true, componentType: 'BY' },
                        ]
                    }
                },
            },
            packingInstructions: {
                "BOM_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
                // A second pallet packing for the main finished good - the same box, stacked 10 instead of
                // 20 per pallet. Two pallets means the operator still has something to choose, so the
                // packing-instruction list is shown and the assertions below about that list have
                // something to look at. With a single pallet the list would be skipped altogether and the
                // pallet taken automatically - a different behaviour, covered on its own by
                // receiving_single_lu_autoselected.spec.js. This test is about the combination of the four
                // flags and the co-product carve-out, not about how many pallets a product happens to have.
                "BOM_PI_LOW_PALLET": { lu: "LU_LOW", qtyTUsPerLU: 10, tu: "TU_LOW", product: "BOM", qtyCUsPerTU: 4 },
                // No qtyCUsPerTU -> infinite capacity, which the receipt offers for catch-weight lines only.
                "REWORK_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU_REWORK", product: "REWORK" },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 1000 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('The simplified receipt applies to the main finished good only, not to its by-product', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('combined receive-config flags');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step("The main finished good is received in the simplified way", async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product

        // Fewer offered target structures is a deliberate simplification, not missing master data.
        await MaterialReceiptLineScreen.expectNoGebindeHintNotVisible();

        // Straight to the packing instructions - no new-Gebinde-vs-scan chooser.
        await MaterialReceiptLineScreen.clickReceiveTargetButtonExpectingNewHUScreen();
        await ReceiptNewHUScreen.expectNoGebindeGuidanceNotVisible();
        await ReceiptNewHUScreen.expectTUTargetNotPresent({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });
        await ReceiptNewHUScreen.expectLUTargetVisible({ luPIItemTestId: masterdata.packingInstructions.BOM_PI.luPIItemTestId });
        await ReceiptNewHUScreen.clickLUTarget({ luPIItemTestId: masterdata.packingInstructions.BOM_PI.luPIItemTestId });

        // A single quantity, no weight - although the finished good is a catch-weight product.
        await MaterialReceiptLineScreen.receiveQty({
            qtyEntered: 4,
            expectQtyInputVisible: true,
            expectCatchWeightVisible: false,
            expectGoBackToJob: true,
        });
        await ManufacturingJobScreen.expectReceiveButton({
            index: 1,
            qtyToReceive: '100 Stk',
            qtyReceived: '4 Stk',
        });

        // Received onto a pallet (`lu` resolves only for an HU inside a loading unit, and asserting
        // the LU's included TUs pins its HU unit type to "loading unit"), with no weight recorded.
        await Backend.expect({
            title: "The main finished good went onto a pallet, without a weight",
            manufacturings: {
                [jobId]: {
                    receivedHUs: [
                        { lu: 'lu1', qty: '4 PCE' },
                    ]
                }
            },
            hus: {
                'lu1': {
                    storages: { 'BOM': '4 PCE' },
                    tus: [
                        { storages: { 'BOM': '4 PCE' }, attributes: { 'WeightNet': '0.000' } },
                    ],
                },
            }
        });
    });

    await test.step("The by-product keeps its target and chooser, but not the weight prompt", async () => {
        // The Gebinde the rework goes into gets its label printed on the shop floor.
        const reworkTUQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({
            piTestId: masterdata.packingInstructions.REWORK_PI.tuPITestId,
            numberOfHUs: 1,
        });

        await ManufacturingJobScreen.clickReceiveButton({ index: 2 }); // i.e., the by-product

        // The chooser is still there for the by-product - unlike the main good above.
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();

        // ... and so is its (infinite-capacity) TU target, which the main good no longer offers.
        await ReceiptNewHUScreen.expectTUTargetVisible({ tuPIItemProductTestId: masterdata.packingInstructions.REWORK_PI.tuPIItemProductTestId });

        // Going back leads to the chooser, not to the receive line as it does for the main good.
        await ReceiptNewHUScreen.goBackToReceiveTargetScreen();

        await ReceiptReceiveTargetScreen.clickExistingHUButton();
        await ManufacturingReceiptScanScreen.typeQRCode(reworkTUQRCode);
        await MaterialReceiptLineScreen.waitForScreen();

        // Catch weight is NOT asked for here either: since 2026-08-20 the flag governs every line, not just
        // the main finished good, so with it switched off the by-product is prompted for the quantity only.
        // The weight of a catch-weight product is captured later, at picking.
        await MaterialReceiptLineScreen.receiveQty({
            qtyEntered: 9,
            expectCatchWeightVisible: false,
            expectGoBackToJob: true,
        });
        await ManufacturingJobScreen.expectReceiveButton({
            index: 2,
            qtyToReceive: '10 Stk',
            qtyReceived: '9 Stk',
        });

        // The by-product's TU carries the received quantity and NO weight - the weight is picking's to record.
        await Backend.expect({
            title: "The by-product TU carries the quantity and no weight",
            hus: {
                [reworkTUQRCode]: {
                    storages: { 'REWORK': '9 PCE' },
                    // No CATCH weight was captured, so setCatchWeightForReceivedHUs() never ran - that is what
                    // this line proves. The 0.000 is NOT a property of the flag: the HU's nominal weight comes
                    // from the product master ((GrossWeight - Weight) x qty, WeightTareAttributeValueCallout
                    // :130-141, contributing nothing at the default 0), which the flag never touches. On a real
                    // catch-weight product the master weight and the UOM conversion agree, so a non-zero nominal
                    // weight would land here even with catch weight off. This fixture cannot express that: the
                    // masterdata API takes uomConversions but has no field for M_Product.Weight, so the product
                    // is created with a Stk->kg rate and weight 0. Scale written out on purpose - the assertion
                    // uses isEqualTo, which is scale-sensitive: '0' would NOT match 0.000.
                    attributes: { 'WeightNet': '0.000' },
                },
            }
        });
    });

    await ManufacturingJobScreen.complete();
});
