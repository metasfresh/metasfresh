import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

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
                    pickWithNewLU: true,
                    allowNewTU: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
            },
            products: {
                "P1": {},
                "P2": {
                    prices: [{ price: 1 }],
                    bom: {
                        lines: [
                            { product: 'P1', qty: 1 },
                        ],
                    },
                },
            },
            productPlannings: {
                "pp1": { product: "P2", warehouse: "wh", pickingOrder: true }
            },
            packingInstructions: {
                "P1x20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
                "P2x25x5": { lu: "LU", qtyTUsPerLU: 25, tu: "P2_5CU", product: "P2", qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'P2x25x5' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P2', qty: 5, piItemProduct: 'P2_5CU' }]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Assemble/Manufacture while picking test', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P2x25x5.luName });
    await PickingJobScreen.clickLineButton({ index: 1 });

    await PickingJobLineScreen.clickManufactureButton();

    await ManufacturingJobScreen.waitForScreen();
    const generatedQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.P2x25x5.tuPITestId });
    await ManufacturingJobScreen.issueRawProduct({
        index: 1,
        expectQtyEntered: 5,
        qrCode: masterdata.handlingUnits.HU1.qrCode,
    })
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: generatedQRCode });
    await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: 5 });
    await ManufacturingJobScreen.goBackToPickingJobLine();

    await PickingJobLineScreen.goBack();
    await PickingJobScreen.pickHU({ qrCode: generatedQRCode });
    await PickingJobScreen.complete();
});
