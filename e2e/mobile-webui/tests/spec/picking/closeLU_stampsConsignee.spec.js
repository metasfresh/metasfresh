import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * me03 #30763 — at mobile picking "close LU" the just-closed LU must carry the consignee's
 * C_BPartner_ID + C_BPartner_Location_ID, so the per-BPartner M_HU_Label_Config matches and the
 * SSCC label auto-prints. The LU is materialised WITHOUT a partner during picking; the fix stamps
 * the consignee (per-LU, resolved from the picking job) at close-LU time.
 *
 * The pick-target scope depends on the picking-profile aggregation type (module rule
 * de.metas.handlingunits.base/.../job/service/CLAUDE.md): PRODUCT is line-level, SALES_ORDER /
 * DELIVERY_LOCATION are header-level. Each HU-shape branch needs its own E2E coverage, so both
 * shapes are exercised below against the running stack (in-memory JUnit does not prove HU flush).
 */

const createMasterdata = async ({ aggregationType }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType,
                    allowPickingAnyCustomer: false,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    filterByQRCode: false,
                    anonymousPickHUsOnTheFly: false,
                    customers: [
                        { customer: "customer1" },
                    ],
                }
            },
            bpartners: {
                "customer1": {
                    locations: {
                        customer1_location1: {},
                    }
                },
            },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
            },
            products: {
                "P1": { price: 1 },
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    location: 'customer1_location1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20, piItemProduct: 'P1_4CU' },
                    ]
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Close-LU stamps consignee — header-level (DELIVERY_LOCATION aggregation)', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Close-LU stamps picking consignee on the LU');
    allure.severity('critical');

    const masterdata = await createMasterdata({ aggregationType: 'delivery_location' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, customerLocationId: masterdata.bpartners.customer1.locations.customer1_location1.id });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // header-level: the pick target LU lives on the header (M_Picking_Job.M_LU_HU_ID)
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P1_20x4.luName });

    await test.step('Pick the line entirely', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode, expectQtyEntered: 5 /*TU*/ });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 TU', qtyPicked: '5 TU' });
    });

    // the LU carries NO partner while still open (materialised without one)
    await Backend.expect({
        title: 'before close-LU: the LU has no bpartner yet',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'openLU' }] }
                }
            }
        },
        hus: {
            openLU: { huStatus: 'S', bpartner: '-' },
        }
    });

    await PickingJobScreen.closeTargetLU();

    // after close-LU the fix stamps the consignee (bpartner + delivery location) onto the closed LU
    await Backend.expect({
        title: 'after close-LU: the closed LU carries the consignee',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'closedLU' }] }
                }
            }
        },
        hus: {
            closedLU: {
                huStatus: 'S',
                bpartner: 'customer1',
                bpartnerLocation: 'customer1_location1',
            },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Close-LU stamps consignee — line-level (PRODUCT aggregation)', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Close-LU stamps picking consignee on the LU');
    allure.severity('critical');

    const masterdata = await createMasterdata({ aggregationType: 'product' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 20 });
    await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    // line-level (PRODUCT): the pick target LU lives on the line (M_Picking_Job_Line.Current_PickTo_LU_ID)
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P1_20x4.luName });

    await test.step('Pick the line entirely', async () => {
        // PRODUCT (line-level) has no header "Scan HU" button — the pick-from HU was scanned above,
        // and the qty is entered per-line via the line button (see productBasedPicking/standard.spec.js).
        await PickingJobScreen.clickLineButton({ index: 1 });
        await GetQuantityDialog.waitForDialog();
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 5 /*TU*/ });
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 TU', qtyPicked: '5 TU' });
    });

    await Backend.expect({
        title: 'before close-LU: the LU has no bpartner yet',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'openLU' }] }
                }
            }
        },
        hus: {
            openLU: { huStatus: 'S', bpartner: '-' },
        }
    });

    await PickingJobScreen.closeTargetLU();

    await Backend.expect({
        title: 'after close-LU: the closed LU carries the consignee',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'closedLU' }] }
                }
            }
        },
        hus: {
            closedLU: {
                huStatus: 'S',
                bpartner: 'customer1',
                bpartnerLocation: 'customer1_location1',
            },
        }
    });
});
