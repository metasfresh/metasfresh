import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

const createMasterdata = async () => {
    const response = await Backend.createMasterdata({
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
                    allowNewTU: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: {
                "wh": {},
            },
            products: {
                "P1": {
                    valuePrefix: '00027', // important for EAN13 barcodes
                    gtin: '97311876341811',
                    ean13ProductCode: '4888',
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }]
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 100 }
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
        tuPIName: response.packingInstructions.PI.tuName,
    };
}

// noinspection JSUnusedLocalSymbols
test('Leich+Mehl', async ({ page }) => {
    const { login, pickingSlotQRCode, documentNo, huQRCode, luPIName, tuPIName } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: pickingSlotQRCode });
    await PickingJobScreen.setTargetLU({ lu: luPIName });
    await PickingJobScreen.setTargetTU({ tu: tuPIName });
    await PickingJobScreen.pickHU({
        qrCode: huQRCode,
        catchWeightQRCode: [
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
        ],
    });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('GS1', async ({ page }) => {
    const { login, pickingSlotQRCode, documentNo, huQRCode, luPIName, tuPIName } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: pickingSlotQRCode });
    await PickingJobScreen.setTargetLU({ lu: luPIName });
    await PickingJobScreen.setTargetTU({ tu: tuPIName });
    await PickingJobScreen.pickHU({
        qrCode: huQRCode,
        catchWeightQRCode: [
            '019731187634181131030075201527080910501',
        ],
    });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('EAN13', async ({ page }) => {
    const { login, pickingSlotQRCode, documentNo, huQRCode, luPIName, tuPIName } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: pickingSlotQRCode });
    await PickingJobScreen.setTargetLU({ lu: luPIName });
    await PickingJobScreen.setTargetTU({ tu: tuPIName });
    await PickingJobScreen.pickHU({
        qrCode: huQRCode,
        catchWeightQRCode: [
            '2800027002616',
        ],
    });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('EAN13WithUPC', async ({ page }) => {
    const { login, pickingSlotQRCode, documentNo, huQRCode, luPIName, tuPIName } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: pickingSlotQRCode });
    await PickingJobScreen.setTargetLU({ lu: luPIName });
    await PickingJobScreen.setTargetTU({ tu: tuPIName });
    await PickingJobScreen.pickHU({
        qrCode: huQRCode,
        catchWeightQRCode: [
            '2948882005745',
        ],
    });
    await PickingJobScreen.complete();
});
