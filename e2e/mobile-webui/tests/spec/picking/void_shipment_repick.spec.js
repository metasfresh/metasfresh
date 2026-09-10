import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend, getBackendBaseUrl } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { page } from "../../utils/common";

/**
 * Regression test for me03#28458:
 * After voiding a shipment, the same HUs should be pickable again.
 *
 * Flow:
 * 1. Create 2 sales orders + 1 HU
 * 2. Pick HU for SO1, complete (generates shipment via createShipmentPolicy=CL)
 * 3. Reverse-correct the shipment via frontendTesting API
 * 4. Pick the same HU for SO2
 */
test('Void shipment and re-pick same HUs for different order', async ({ page: _page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Void shipment and re-pick HUs');
    allure.severity('critical');

    // Step 1: Create masterdata with 2 sales orders
    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { prices: [{ price: 1 }] } },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1', warehouse: 'wh',
                    datePromised: '2026-03-15T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                },
                "SO2": {
                    bpartner: 'BP1', warehouse: 'wh',
                    datePromised: '2026-03-15T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    });

    // Step 2: Login and pick for SO1
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    let pickingJobId1;
    await test.step('Pick for SO1', async () => {
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        const startResult = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        pickingJobId1 = startResult.pickingJobId;

        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            isScanDirectly: true,
            expectQtyEntered: '3'
        });

        await PickingJobScreen.complete();
    });

    // Verify shipment was created
    await test.step('Verify shipment created for SO1', async () => {
        await Backend.expect({
            title: "SO1 shipment created",
            pickings: {
                [pickingJobId1]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ processed: true, shipmentLineId: 'shipmentLineId1' }]
                        }
                    }
                }
            }
        });
    });

    // Step 3: Reverse-correct the shipment via frontendTesting API
    await test.step('Void the shipment for SO1', async () => {
        const result = await reverseShipment({ salesOrderId: masterdata.salesOrders.SO1.id });
        console.log(`Reversed shipment: id=${result.shipmentId}, docNo=${result.documentNo}, status=${result.docStatus}`);
    });

    // Step 4: Pick the same HU for SO2
    await test.step('Pick same HU for SO2 (after void)', async () => {
        // Navigate back to home screen from PickingJobsListScreen
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO2.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO2.documentNo });

        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

        // This is the critical step - scanning the SAME HU that was used for the voided shipment
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            isScanDirectly: true,
            expectQtyEntered: '3'
        });

        await PickingJobScreen.complete();
    });
});

//
// Helper: Reverse-correct a shipment via the frontendTesting API (app server, port 8282)
//
async function reverseShipment({ salesOrderId }) {
    const backendBaseUrl = await getBackendBaseUrl();
    // backendBaseUrl is like http://localhost:8282/api/v2 or http://app-test:8282/api/v2
    const response = await page.request.post(`${backendBaseUrl}/frontendTesting/reverseShipment`, {
        data: { salesOrderId },
    });

    if (!response.ok()) {
        const body = await response.text();
        throw new Error(`reverseShipment failed: ${response.status()} ${body.substring(0, 300)}`);
    }

    return await response.json();
}
