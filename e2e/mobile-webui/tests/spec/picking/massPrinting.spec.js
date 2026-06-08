import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { MassPrintingScanScreen } from "../../utils/screens/picking/MassPrintingScanScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

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
const createMasterdata = async ({ luUnits, selfPackedOrderCount }) => {
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
