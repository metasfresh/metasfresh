import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { generateEAN13 } from '../../utils/ean13';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    allowCompletingPartialPickingJob: true,
                    // The flag under test: when false the "Scan Picking Slot" step is dropped
                    // and the operator can pick without selecting a picking slot.
                    pickingSlotRequired: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            // NOTE: deliberately no pickingSlots — the job must be pickable without one.
            products: {
                "P1": { price: 1, gtin: generateEAN13().ean13 },
            },
            packingInstructions: {
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 11 },
                    ]
                }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Pick without a picking slot when picking slot is not required', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.feature('F00230: MobileUI Picking');
    allure.story('Pick without a picking slot when picking slot is not required');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await test.step("Picking slot step is absent => no slot to scan", async () => {
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectPickingSlotButtonNotVisible();
    });

    await test.step("Pick the line, without a picking slot", async () => {
        // HU1 holds 1000 but the line only needs 11, so picking opens the quantity
        // dialog pre-filled with the to-pick qty (this is NOT a pick-directly case).
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '11',
        });
    });

    await test.step("Complete the job", async () => {
        // No completeJobAutomatically => the operator completes the job explicitly;
        // complete() lands back on the jobs list.
        await PickingJobScreen.complete();
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "11 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLine1' }]
                    },
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '989 PCE' } },
            vhu1: { huStatus: 'E', storages: { P1: '11 PCE' } },
        }
    });
});
