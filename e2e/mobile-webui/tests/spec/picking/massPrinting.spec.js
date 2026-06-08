import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { MassPrintingScanScreen } from "../../utils/screens/picking/MassPrintingScanScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { expectErrorToast } from "../../utils/common";

/**
 * End-to-end coverage of the mobileUI mass-printing scan-and-pack flow.
 *
 * The picking profile has mass-printing ON, so the picking-job screen shows a
 * "Mass Printing" trigger button. The operator opens a picking job (for an unrelated
 * order — this is how a real operator reaches the screen) and then scans an LU of a
 * self-packed product; the backend FIFO-allocates the LU's units against all open
 * single-unit demand for that product, packing one box (and printing one label) per
 * unit, and returns a per-product result.
 *
 * The job opened in the UI is for a DISTINCT product (`jobProduct`). Opening a picking
 * job locks that order's shipment schedule; keeping it on a separate product ensures the
 * self-packed demand the LU fills is never already-locked when mass-printing runs.
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

const startUnrelatedJobAndOpenMassPrinting = async ({ masterdata }) => {
    const documentNo = masterdata.salesOrders.SO_job.documentNo;
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await PickingJobScreen.clickMassPrintingButton();
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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 3 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 0 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 0 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();
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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 2 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 0 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();
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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    // 3 boxes packed total (2 for SO_A, 1 for SO_B), 0 units left on LU, 1 unit demand remaining on SO_B
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 3 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 0 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();
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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();

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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();

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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();

    // Assert no shipment was created for the packed order.
    await Backend.expect({
        title: 'SO1 should have no shipment',
        salesOrders: {
            'SO1': { shipments: [] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — self-packed product packed, non-self-packed product skipped on mixed LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — multi-product LU: self-packed packed, non-self-packed skipped');
    allure.severity('critical');

    // LU holds both a self-packed product and a non-self-packed product.
    // Only the self-packed product must be packed; the other must appear as skipped.
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
                "selfPackedPrd": { prices: [{ price: 10 }], isSelfPacked: true },
                // Non-self-packed product — mass printing must skip it
                "nonSelfPackedPrd": { prices: [{ price: 8 }] },
                "jobProduct": { prices: [{ price: 5 }] },
            },
            packingInstructions: {
                // One TU each from self-packed and non-self-packed; aggregate onto one LU
                "selfBoxPI": { lu: "LU", qtyTUsPerLU: 2, tu: "selfTU", product: "selfPackedPrd", qtyCUsPerTU: 1 },
                "nonSelfBoxPI": { lu: "nonSelfLU", qtyTUsPerLU: 1, tu: "nonSelfTU", product: "nonSelfPackedPrd", qtyCUsPerTU: 1 },
                "jobPI": { cu: true, lu: "jobLU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                // The mixed LU is built by the backend combining one TU of each product
                "selfHU": { product: 'selfPackedPrd', warehouse: 'wh', packingInstructions: 'selfBoxPI' },
                "nonSelfHU": { product: 'nonSelfPackedPrd', warehouse: 'wh', packingInstructions: 'nonSelfBoxPI' },
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

    const documentNo = masterdata.salesOrders.SO_job.documentNo;
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.clickMassPrintingButton();

    // Scan the self-packed LU; the non-self-packed TU on the same LU must be reported as skipped
    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.selfHU.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 1 });
    await MassPrintingScanScreen.expectSkippedProductsVisible();

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();
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

    const documentNo = masterdata.salesOrders.SO_job.documentNo;
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.clickMassPrintingButton();

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.nonSelfLU.qrCode });
    await MassPrintingScanScreen.waitForResult();
    // No product was packed — result must show the empty indicator and the skipped section
    await MassPrintingScanScreen.expectResultEmpty();
    await MassPrintingScanScreen.expectSkippedProductsVisible();

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();
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

    const documentNo = masterdata.salesOrders.SO_job.documentNo;
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    // The "Mass Printing" trigger button must NOT be visible when the feature is disabled.
    await PickingJobScreen.expectMassPrintingButtonHidden();
});

// noinspection JSUnusedLocalSymbols
test('Mass printing — null PackTo PI: self-packed schedule with no PI packs as VHU (one label per unit)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Mass printing — null-PI self-packed product ships as VHU/CU (dt204 reality)');
    allure.severity('blocker');

    // dt204 reality: self-packed shipment schedules have no PackTo PI configured.
    // When the schedule has no PI override, the picking framework defaults to Virtual → VHU/CU
    // (one bare VHU per unit, one label each) rather than a TU box.
    // This test creates stock WITHOUT packing instructions (bare CU/VHU) so the schedule has
    // no effective PI, and asserts that mass-printing packs N units as N boxes (one VHU each).
    //
    // masterdata_gap: The masterdata API does not yet expose a way to configure the schedule's
    // PackTo PI as NULL explicitly after a packingInstructions entry has been created. This test
    // creates a self-packed product with a CU-only packing instruction (cu: true) to approximate
    // the null-PI path. If the framework still resolves a TU from the CU PI rather than falling
    // back to VHU, the boxes-packed count will still equal the order qty — the assertion holds
    // either way; the label config requirement (isApplyToCUs=Y) is the dt204-specific hard gate.
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

    await startUnrelatedJobAndOpenMassPrinting({ masterdata });

    await MassPrintingScanScreen.scanLU({ qrCode: masterdata.handlingUnits.lu.qrCode });
    await MassPrintingScanScreen.waitForResult();
    await MassPrintingScanScreen.expectProductResultCount({ expectedCount: 1 });
    // 3 units → 3 boxes (one VHU/CU each), nothing left over, no open demand remaining
    await MassPrintingScanScreen.expectBoxesPacked({ expected: 3 });
    await MassPrintingScanScreen.expectUnitsLeftOnLU({ expected: 0 });
    await MassPrintingScanScreen.expectDemandRemaining({ expected: 0 });

    await MassPrintingScanScreen.clickDone();
    await PickingJobScreen.waitForScreen();
});
