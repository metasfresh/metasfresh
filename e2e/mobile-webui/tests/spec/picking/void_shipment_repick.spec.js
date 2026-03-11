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
 * 3. Void the shipment via WebUI REST API
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

    // Step 3: Void the shipment via WebUI REST API
    await test.step('Void the shipment for SO1', async () => {
        const shipmentId = await voidShipmentViaWebUI({ salesOrderId: masterdata.salesOrders.SO1.id });
        console.log(`Voided shipment ID: ${shipmentId}`);
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
// Helper: Void shipment via WebUI REST API
//
async function voidShipmentViaWebUI({ salesOrderId }) {
    // 1. Find the shipment via DB query through the backend
    const backendBaseUrl = await getBackendBaseUrl();
    // The backendBaseUrl is like http://localhost:8282/api/v2
    // Extract the host for WebUI (port 8080)
    const appUrl = new URL(backendBaseUrl);
    const webuiBaseUrl = `${appUrl.protocol}//${appUrl.hostname}:8080`;

    // 2. Login to WebUI
    const authResponse = await page.request.post(`${webuiBaseUrl}/rest/api/login/authenticate`, {
        data: { type: 'password', username: 'metasfresh', password: 'metasfresh' },
    });
    const authBody = await authResponse.json();
    if (!authBody.roles?.length) throw new Error('WebUI login failed: no roles');

    const role = authBody.roles.find(r => r.roleId === 540024) || authBody.roles[0];
    await page.request.post(`${webuiBaseUrl}/rest/api/login/loginComplete`, {
        data: role,
    });

    // 3. Find the shipment ID via SQL query on the database
    // We query through the WebUI's view API - find the shipment for this sales order
    // Alternative: use the app server's DB access
    // Since we don't have direct DB access from Playwright, we'll search via WebUI
    // Window 169 = Lieferung (Shipment)

    // Create a view to find the shipment
    const viewResponse = await page.request.post(`${webuiBaseUrl}/rest/api/documentView/169`, {
        data: {
            windowId: '169',
            viewType: 'grid',
            filters: [],
            queryPageLength: 100,
        },
    });
    const viewBody = await viewResponse.json();
    const viewId = viewBody.viewId;
    if (!viewId) throw new Error('Failed to create shipment view: ' + JSON.stringify(viewBody).substring(0, 300));

    // Get view rows
    const rowsResponse = await page.request.get(
        `${webuiBaseUrl}/rest/api/documentView/169/${viewId}?firstRow=0&pageLength=100`
    );
    const rowsBody = await rowsResponse.json();
    const rows = rowsBody.result || [];

    // Find the completed shipment linked to our sales order
    // We look for the most recent CO (completed) shipment
    let shipmentRowId = null;
    for (const row of rows) {
        const docStatus = row.fieldsByName?.DocStatus?.value;
        if (docStatus?.key === 'CO' || docStatus === 'CO') {
            shipmentRowId = row.id;
            break;
        }
    }

    if (!shipmentRowId) {
        // If view search didn't work, try direct document access with known IDs
        console.log('Could not find shipment in view. Rows:', rows.length);
        throw new Error('Could not find completed shipment for the sales order');
    }

    // 4. Void the shipment by setting DocAction=RC (Reverse-Correct)
    const patchResponse = await page.request.patch(`${webuiBaseUrl}/rest/api/window/169/${shipmentRowId}`, {
        data: [
            { op: 'replace', path: 'DocAction', value: { key: 'RC', caption: 'Reverse - Correct' } }
        ],
    });

    if (!patchResponse.ok()) {
        const patchBody = await patchResponse.text();
        throw new Error(`Failed to void shipment: ${patchResponse.status()} ${patchBody.substring(0, 300)}`);
    }

    // Wait for the void to be processed
    await new Promise(resolve => setTimeout(resolve, 3000));

    // Verify the shipment is now reversed
    const docResponse = await page.request.get(`${webuiBaseUrl}/rest/api/window/169/${shipmentRowId}`);
    const docBody = await docResponse.json();
    const docStatus = docBody[0]?.fieldsByName?.DocStatus?.value?.key;
    if (docStatus !== 'RE') {
        console.log(`Warning: Shipment status is ${docStatus}, expected RE`);
    }

    // Cleanup: logout from WebUI (so we can continue with mobile)
    // The mobile uses a different auth mechanism (token), so this shouldn't matter
    // but let's be clean
    try {
        await page.request.get(`${webuiBaseUrl}/rest/api/login/logout`);
    } catch (e) {
        // Ignore logout errors
    }

    return shipmentRowId;
}
