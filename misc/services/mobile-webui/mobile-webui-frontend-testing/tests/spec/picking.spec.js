import { test } from "../../playwright.config";
import { ApplicationsListScreen } from "../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../utils/screens/picking/PickingJobsListScreen";
import { PickingJobLineScreen } from "../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../utils/screens/picking/PickingJobScreen";
import { Backend } from "../utils/screens/Backend";
import { LoginScreen } from "../utils/screens/LoginScreen";

const loginAndCreateMasterdata = async () => {
    const response = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: {
                "wh": {},
            },
            products: {
                "P1": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 80 }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    })

    const { pickingSlotQRCode } = await Backend.getFreePickingSlot({
        bpartnerCode: response.bpartners.BP1.bpartnerCode
    });

    return {
        login: response.login.user,
        pickingSlotQRCode,
        documentNo: response.salesOrders.SO1.documentNo,
        huQRCode: response.handlingUnits.HU1.qrCode,
        luPIName: response.packingInstructions.PI.luName,
    };
}

// noinspection JSUnusedLocalSymbols
test('Simple picking test', async ({ page }) => {
    const { login, pickingSlotQRCode, documentNo, huQRCode, luPIName } = await loginAndCreateMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: pickingSlotQRCode });
    await PickingJobScreen.setTargetLU({ lu: luPIName });
    await PickingJobScreen.pickHU({ qrCode: huQRCode, expectQtyEntered: '3' });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Pick - unpick', async ({ page }) => {
    const { login, pickingSlotQRCode, documentNo, huQRCode, luPIName } = await loginAndCreateMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: pickingSlotQRCode });
    await PickingJobScreen.setTargetLU({ lu: luPIName });
    await PickingJobScreen.pickHU({ qrCode: huQRCode, expectQtyEntered: '3' });

    await PickingJobScreen.clickLineButton({ index: 0 });
    await PickingJobLineScreen.clickStepButton({ index: 0 });
    await PickingJobStepScreen.unpick();
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();

    await PickingJobScreen.closeTargetLU();

    await PickingJobScreen.abort();
});


