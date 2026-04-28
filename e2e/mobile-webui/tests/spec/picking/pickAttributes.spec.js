import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { generateEAN13 } from '../../utils/ean13';

const createMasterdata = async ({ readAttributes, qty }) => {
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
                    readAttributes,
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { price: 1, gtin: generateEAN13().ean13 } },
            packingInstructions: { "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 } },
            handlingUnits: { "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' } },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty }]
                }
            },
        }
    })
}
// noinspection JSUnusedLocalSymbols
test('Expect picking directly without dialog', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick attributes behavior');
    allure.severity('normal');

    const masterdata = await createMasterdata({ readAttributes: [], qty: 1 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // NO target to be set => pick to top level CUs

    await test.step("Pick", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });

        await PickingJobScreen.pickHU({
            isScanDirectly: true,
            expectedPickDirectly: true,
            qrCode: masterdata.products.P1.gtin,
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', waitForColor: 'green' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '999 PCE' } },
                vhu1: { huStatus: 'S', storages: { P1: '1 PCE' } },
            }
        });
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLine1' }]
                    },
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '999 PCE' } },
            vhu1: { huStatus: 'E', storages: { P1: '1 PCE' } },
        }
    });
});

test('Expect read qty dialog with only LotNo attribute', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick attributes behavior');
    allure.severity('normal');

    const masterdata = await createMasterdata({ readAttributes: ['LotNo'], qty: 1 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // NO target to be set => pick to top level CUs

    const testLotNo = `LOT-${Date.now()}`;

    await test.step("Pick with LotNo and expect only LotNo in read qty dialog", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });

        // Scan the product - should open GetQuantityDialog because readAttributes contains LotNo
        await BarcodeScannerComponent.type(masterdata.products.P1.gtin);
        await GetQuantityDialog.waitForDialog();

        // Expect only LotNo field is visible, BestBeforeDate is not
        await GetQuantityDialog.expectLotNoVisible();
        await GetQuantityDialog.expectBestBeforeDateNotVisible();

        await GetQuantityDialog.fillAndPressDone({ lotNo: testLotNo });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', waitForColor: 'green' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: false, shipmentLineId: '-' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '999 PCE' } },
                vhu1: { huStatus: 'S', storages: { P1: '1 PCE' }, attributes: { 'Lot-Nummer': testLotNo } },
            }
        });
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLine1' }]
                    },
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '999 PCE' } },
            vhu1: { huStatus: 'E', storages: { P1: '1 PCE' }, attributes: { 'Lot-Nummer': testLotNo } },
        }
    });
});
