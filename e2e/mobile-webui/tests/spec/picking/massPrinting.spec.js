import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { MassPrintingScanScreen } from "../../utils/screens/picking/MassPrintingScanScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

/**
 * End-to-end coverage of the mobileUI mass-printing scan-and-pack flow.
 *
 * With mass-printing enabled in the picking profile, the picking launchers/jobs-list
 * screen shows a "Mass Printing" trigger button — reachable directly, without opening a
 * picking job. The operator taps it and scans an LU of a self-packed product; the backend
 * FIFO-allocates the LU's units against all open single-unit demand for that product,
 * packing one box (and printing one label) per unit, and returns a per-product result.
 */
const createMasterdata = async ({ luUnits, selfPackedOrderCount, createShipmentPolicy = 'NO' }) => {
    const salesOrders = {
        // unrelated order the operator opens to reach the mass-printing button
        "SO_job": {
            bpartner: 'customer',
            warehouse: 'wh',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            lines: [{ product: 'jobProduct', qty: 1 }],
        },
    };
    for (let i = 1; i <= selfPackedOrderCount; i++) {
        salesOrders[`SO${i}`] = {
            bpartner: 'customer',
            warehouse: 'wh',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            lines: [{ product: 'selfPackedPrd', qty: 1 }],
        };
    }

    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    createShipmentPolicy,
                    massPrinting: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "customer": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                // self-packed product: the mass-printing flow only packs self-packed products
                "selfPackedPrd": { prices: [{ price: 10 }], isSelfPacked: true },
                // unrelated product whose picking job the operator opens to reach the button
                "jobProduct": { prices: [{ price: 5 }] },
            },
            packingInstructions: {
                // box PI: 1 CU per TU (= 1 unit = 1 box); the LU aggregates luUnits such boxes
                "boxPI": { lu: "LU", qtyTUsPerLU: luUnits, tu: "TU", product: "selfPackedPrd", qtyCUsPerTU: 1 },
                // CU PI for the unrelated job product so its order is pickable
                "jobPI": { cu: true, lu: "jobLU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                // an LU of `luUnits` single-unit boxes of the self-packed product
                "lu": { product: 'selfPackedPrd', warehouse: 'wh', packingInstructions: 'boxPI' },
                // stock for the unrelated job product
                "jobHU": { product: 'jobProduct', warehouse: 'wh', qty: 100, packingInstructions: 'jobPI' },
            },
            salesOrders,
        }
    });
};

// The mass-printing trigger lives on the picking launchers/jobs-list screen, reachable
// directly from the picking application — without opening any picking job.
const openMassPrintingFromLaunchers = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.clickMassPrintingButton();
};

// noinspection JSUnusedLocalSymbols
test('Mass printing — scan LU packs one box per unit for open demand', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — scan LU, one box per unit');
    allure.severity('critical');

    // LU has 3 units; 3 single-unit orders => 3 boxes packed, nothing left over.
    const masterdata = await createMasterdata({ luUnits: 3, selfPackedOrderCount: 3 });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 3 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 0 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 0 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();

    // Policy = DO_NOT_CREATE: confirm no shipment was generated for any of the 3 orders.
    await Backend.expect({
        title: 'No shipments created (DO_NOT_CREATE policy)',
        salesOrders: {
            'SO1': { shipments: [] },
            'SO2': { shipments: [] },
            'SO3': { shipments: [] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — scan LU with leftover units when demand is smaller than LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — scan LU, leftover units on LU');
    allure.severity('normal');

    // LU has 3 units but only 1 single-unit order => 1 box packed, 2 units left on the LU.
    const masterdata = await createMasterdata({ luUnits: 3, selfPackedOrderCount: 1 });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 2 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 0 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();

    // Policy = DO_NOT_CREATE: confirm no shipment was generated for the packed order.
    await Backend.expect({
        title: 'No shipment created (DO_NOT_CREATE policy)',
        salesOrders: { 'SO1': { shipments: [] } }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — FIFO partial fill when LU capacity is smaller than total demand', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — FIFO partial fill, open demand remaining');
    allure.severity('critical');

    // LU has 3 units; two orders each demand 2 units (total demand 4 > 3).
    // FIFO by delivery date: first order fully filled (2 boxes), second partially filled (1 box),
    // 1 unit of open demand stays on the second order.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    // CREATE_AND_COMPLETE so we can assert per-SO how many units were shipped.
                    createShipmentPolicy: 'CREATE_AND_COMPLETE',
                    massPrinting: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "customer": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                "selfPackedPrd": { prices: [{ price: 10 }], isSelfPacked: true },
                "jobProduct": { prices: [{ price: 5 }] },
            },
            packingInstructions: {
                "boxPI": { lu: "LU", qtyTUsPerLU: 3, tu: "TU", product: "selfPackedPrd", qtyCUsPerTU: 1 },
                "jobPI": { cu: true, lu: "jobLU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "lu": { product: 'selfPackedPrd', warehouse: 'wh', packingInstructions: 'boxPI' },
                "jobHU": { product: 'jobProduct', warehouse: 'wh', qty: 100, packingInstructions: 'jobPI' },
            },
            salesOrders: {
                "SO_job": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'jobProduct', qty: 1 }],
                },
                // Earlier delivery date → filled first
                "SO_A": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'selfPackedPrd', qty: 2 }],
                },
                // Later delivery date → partially filled
                "SO_B": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-04-01T00:00:00.000+02:00',
                    lines: [{ product: 'selfPackedPrd', qty: 2 }],
                },
            },
        }
    });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    // 3 boxes packed total (2 for SO_A, 1 for SO_B), 0 units left on LU, 1 unit demand remaining on SO_B
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 3 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 0 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();

    // Per-SO FIFO allocation assertion:
    //   SO_A (earlier delivery date) → fully filled → completed shipment with movementQty=2
    //   SO_B (later delivery date)   → partially filled → completed shipment with movementQty=1
    await Backend.expect({
        title: 'FIFO per-SO shipment assertion: SO_A fully filled, SO_B partially filled',
        salesOrders: {
            'SO_A': { shipments: [{ docStatus: 'CO', movementQty: 2 }] },
            'SO_B': { shipments: [{ docStatus: 'CO', movementQty: 1 }] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — shipment created and completed when policy is CREATE_AND_COMPLETE', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — CREATE_AND_COMPLETE policy creates a completed shipment');
    allure.severity('critical');

    // Profile policy CREATE_AND_COMPLETE: one completed shipment per order after scan.
    const masterdata = await createMasterdata({
        luUnits: 3,
        selfPackedOrderCount: 1,
        createShipmentPolicy: 'CREATE_AND_COMPLETE',
    });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();

    // Assert a completed (CO) shipment was created for the packed order.
    await Backend.expect({
        title: 'SO1 should have a completed shipment',
        salesOrders: {
            'SO1': { shipments: [{ docStatus: 'CO' }] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — shipment created in draft when policy is CREATE_DRAFT', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — CREATE_DRAFT policy creates a draft shipment');
    allure.severity('normal');

    // Profile policy CREATE_DRAFT: one draft shipment per order after scan.
    const masterdata = await createMasterdata({
        luUnits: 3,
        selfPackedOrderCount: 1,
        createShipmentPolicy: 'CREATE_DRAFT',
    });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();

    // Assert a draft (DR) shipment was created for the packed order.
    await Backend.expect({
        title: 'SO1 should have a draft shipment',
        salesOrders: {
            'SO1': { shipments: [{ docStatus: 'DR' }] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — no shipment created when policy is DO_NOT_CREATE', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — DO_NOT_CREATE policy produces no shipment');
    allure.severity('normal');

    // Profile policy DO_NOT_CREATE (the default 'NO'): no shipment is generated.
    const masterdata = await createMasterdata({ luUnits: 3, selfPackedOrderCount: 1 });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();

    // Assert no shipment was created for the packed order.
    await Backend.expect({
        title: 'SO1 should have no shipment',
        salesOrders: {
            'SO1': { shipments: [] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — self-packed product on LU is packed; no skipped-products section shown', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — self-packed-only LU: product packed, no skipped section');
    allure.severity('critical');

    // The masterdata API creates one HU per product and does not yet support aggregating two
    // TUs from different products onto one mixed LU (masterdata_gap: no multi-product-LU API).
    // This test therefore verifies the self-packed path on a self-packed-only LU:
    // 1 box packed, 0 skipped products. The non-self-packed skip path is covered by the
    // "only non-self-packed products" test below (which uses a non-self-packed-only LU).
    const masterdata = await createMasterdata({ luUnits: 1, selfPackedOrderCount: 1 });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });
    // No non-self-packed products on this LU — skipped section must not appear
    await MassPrintingScanScreen.expectNoSkippedProducts();

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — LU with only non-self-packed products shows empty result', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — non-self-packed-only LU yields empty result with skipped products');
    allure.severity('normal');

    // LU holds only a non-self-packed product. The result must have no packed boxes
    // and the skipped section must be visible (informative feedback to operator).
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    createShipmentPolicy: 'NO',
                    massPrinting: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "customer": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                "nonSelfPackedPrd": { prices: [{ price: 8 }] },
                "jobProduct": { prices: [{ price: 5 }] },
            },
            packingInstructions: {
                "nonSelfBoxPI": { lu: "LU", qtyTUsPerLU: 1, tu: "nonSelfTU", product: "nonSelfPackedPrd", qtyCUsPerTU: 1 },
                "jobPI": { cu: true, lu: "jobLU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "nonSelfLU": { product: 'nonSelfPackedPrd', warehouse: 'wh', packingInstructions: 'nonSelfBoxPI' },
                "jobHU": { product: 'jobProduct', warehouse: 'wh', qty: 100, packingInstructions: 'jobPI' },
            },
            salesOrders: {
                "SO_job": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'jobProduct', qty: 1 }],
                },
            },
        }
    });

    await openMassPrintingFromLaunchers({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.nonSelfLU.qrCode });
    await MassPrintingScanScreen.waitForResult();
    // No product was packed — no product-result blocks, but skipped section must be visible.
    // (The empty-result element is only rendered when BOTH productResults and skippedProducts
    //  are empty; here skippedProducts is non-empty so only the skipped section renders.)
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 0 });
    await MassPrintingScanScreen.expectSkippedProductsVisible();

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — button absent when mass-printing is disabled in picking profile', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — off-mode guard: trigger button absent when feature disabled');
    allure.severity('critical');

    // When massPrinting=false the picking-job screen must NOT render the "Mass Printing"
    // trigger button, providing a clean off-mode guard at the UI level.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    createShipmentPolicy: 'NO',
                    massPrinting: false,    // OFF — the trigger button must not appear
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "customer": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                "selfPackedPrd": { prices: [{ price: 10 }], isSelfPacked: true },
                "jobProduct": { prices: [{ price: 5 }] },
            },
            packingInstructions: {
                "boxPI": { lu: "LU", qtyTUsPerLU: 3, tu: "TU", product: "selfPackedPrd", qtyCUsPerTU: 1 },
                "jobPI": { cu: true, lu: "jobLU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "lu": { product: 'selfPackedPrd', warehouse: 'wh', packingInstructions: 'boxPI' },
                "jobHU": { product: 'jobProduct', warehouse: 'wh', qty: 100, packingInstructions: 'jobPI' },
            },
            salesOrders: {
                "SO_job": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'jobProduct', qty: 1 }],
                },
                "SO1": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'selfPackedPrd', qty: 1 }],
                },
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // The "Mass Printing" trigger button must NOT be visible when the feature is disabled.
    await PickingJobsListScreen.expectMassPrintingButtonHidden();
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — null PackTo PI: self-packed schedule with no PI packs as VHU (one label per unit)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — null-PI self-packed product ships as VHU/CU');
    allure.severity('blocker');

    // When a self-packed shipment schedule has no PackTo PI configured, the picking framework
    // defaults to Virtual → VHU/CU (one bare VHU per unit, one label each) rather than a TU box.
    // This test sets up stock with a CU-only packing instruction (no TU wrapping), so the schedule
    // has no effective PackTo PI, then asserts mass-printing packs N units as N VHUs.
    //
    // The VHU assertion below (huType='V' per packed HU) discriminates the VHU path from a TU box —
    // it fails if the framework regresses to producing TU boxes. The label-config requirement
    // (isApplyToCUs=Y) is the deployment-specific gate for the labels to actually print.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    createShipmentPolicy: 'NO',
                    massPrinting: true,
                    pickTo: ['LU_TU', 'LU_CU'],  // allow CU/VHU pick-to as well
                }
            },
            bpartners: { "customer": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                // Self-packed product with no TU packing instruction (CU-only = null-PI equivalent)
                "nullPISelfPackedPrd": { prices: [{ price: 10 }], isSelfPacked: true },
                "jobProduct": { prices: [{ price: 5 }] },
            },
            packingInstructions: {
                // CU-only PI: no TU wrapping → schedule has no effective PackTo PI → VHU path
                "cuOnlyPI": { cu: true, lu: "LU", qtyTUsPerLU: 3 },
                "jobPI": { cu: true, lu: "jobLU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                // 3 bare VHUs/CUs of the self-packed product on a single LU
                "lu": { product: 'nullPISelfPackedPrd', warehouse: 'wh', qty: 3, packingInstructions: 'cuOnlyPI' },
                "jobHU": { product: 'jobProduct', warehouse: 'wh', qty: 100, packingInstructions: 'jobPI' },
            },
            salesOrders: {
                "SO_job": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'jobProduct', qty: 1 }],
                },
                // 3 single-unit orders → 3 VHU packs expected
                "SO1": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'nullPISelfPackedPrd', qty: 1 }],
                },
                "SO2": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-02T00:00:00.000+02:00',
                    lines: [{ product: 'nullPISelfPackedPrd', qty: 1 }],
                },
                "SO3": {
                    bpartner: 'customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-03T00:00:00.000+02:00',
                    lines: [{ product: 'nullPISelfPackedPrd', qty: 1 }],
                },
            },
        }
    });

    await openMassPrintingFromLaunchers({ masterdata });

    // scanLUAndGetResult intercepts the massPrinting/scan REST response so we get packedHUIds.
    const scanResult = await MassPrintingScanScreen.scanLUAndGetResult({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    // 3 units → 3 boxes (one VHU/CU each), nothing left over, no open demand remaining
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 3 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 0 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 0 });

    // VHU assertion: each packed HU must be a Virtual PI (HU_UnitType='V', M_HU_PI_ID=101).
    // This fails if the backend regresses to producing TU boxes (TransportUnit) for null-PI schedules.
    // packedHUIds is exposed by JsonMassPrintingProductResult (extended in this issue).
    const packedHUIds = scanResult?.productResults?.[0]?.packedHUIds ?? [];
    if (packedHUIds.length === 0) {
        throw new Error('scanResult.productResults[0].packedHUIds is empty — cannot assert VHU type. scanResult: ' + JSON.stringify(scanResult));
    }
    const husExpectation = {};
    for (const huId of packedHUIds) {
        husExpectation[String(huId)] = { huType: 'V' };
    }
    await Backend.expect({
        title: 'Packed HUs from null-PI scan must be Virtual PIs (VHUs), not TU boxes',
        hus: husExpectation,
    });

    await MassPrintingScanScreen.clickDone();
    await PickingJobsListScreen.waitForScreen();
});
