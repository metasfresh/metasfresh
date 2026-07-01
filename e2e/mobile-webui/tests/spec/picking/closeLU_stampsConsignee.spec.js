import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * me03 #30763 — the per-BPartner M_HU_Label_Config lookup keys on the closed LU's own
 * C_BPartner_ID + C_BPartner_Location_ID. So the picking consignee must be present on the closed LU
 * (and, via the M_HU updateChildren cascade, on its TU/CU) for the correct SSCC label to auto-print
 * and to re-print later. This spec proves that end state end-to-end for both HU-shape branches.
 *
 * The pick-target scope depends on the picking-profile aggregation type (module rule
 * de.metas.handlingunits.base/.../job/service/CLAUDE.md): PRODUCT is line-level, SALES_ORDER /
 * DELIVERY_LOCATION are header-level. Each HU-shape branch needs its own E2E coverage, so both
 * shapes are exercised below against the running stack (in-memory JUnit does not prove HU flush).
 *
 * On the timing of the stamp in THIS mobile picking flow: the pick-target LU is a new-LU target
 * built from a packing instruction (setTargetLU offers only PI-based targets — PickingJobService
 * getLUAvailableTargets), so it is materialised on the first pick by PackToHUsProducer with
 * packForShipping=true (the picking flow never sets packForShipping=false — that is only the
 * distribution flow, DistributionJobPickFromCommand). packForShipping=true makes
 * setupPackToDestinationCommonOptions stamp the producer's ship-to BPartner+location, so
 * HUBuilder.createHU stamps the freshly-created LU with the line's delivery location already at PICK
 * time. Therefore a genuinely partner-less LU *before close* is NOT reproducible through the mobile
 * picking UI in this harness (the customer's real partner-less LUs originate from a different
 * materialisation path — e.g. loading into a pre-existing partner-less LU — that the mobile UI does
 * not expose as a pick target, and that the frontend-testing masterdata API cannot provision). The
 * close-time PickingJobService.stampConsigneeOnClosedLUs is guarded to only-if-unset, so here it is a
 * safety net that no-ops; what the customer actually observes (and what this test guards) is that the
 * closed LU + its children carry the correct consignee so the label config matches. We therefore
 * assert that true end state, at pick and after close, rather than a partner-less precondition that
 * this flow cannot produce.
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
test('Close-LU: closed LU + cascade carry consignee — header-level (DELIVERY_LOCATION aggregation)', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Closed LU + cascade carry the picking consignee');
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

    // While still open, the picked LU already carries the consignee (stamped at pick time; see header
    // comment) — the label-config key is present on the LU, its TU and its CU, end-to-end.
    await Backend.expect({
        title: 'while open: the picked LU carries the consignee',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'openLU', tu: 'openTU', vhu: 'openVHU' }] }
                }
            }
        },
        hus: {
            openLU: { huStatus: 'S', bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
            openTU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
            openVHU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
        }
    });

    await PickingJobScreen.closeTargetLU();

    // After close-LU the closed LU (and its cascaded TU/CU) carry the consignee (bpartner + delivery
    // location), so the per-BPartner M_HU_Label_Config matches for the close-time auto-print and for a
    // later re-print. This is the customer-facing guarantee of me03 #30763 (AC1/AC2).
    await Backend.expect({
        title: 'after close-LU: the closed LU + cascade carry the consignee',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'closedLU', tu: 'closedTU', vhu: 'closedVHU' }] }
                }
            }
        },
        hus: {
            closedLU: {
                huStatus: 'S',
                bpartner: 'customer1',
                bpartnerLocation: 'customer1_location1',
            },
            closedTU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
            closedVHU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Close-LU: closed LU + cascade carry consignee — line-level (PRODUCT aggregation)', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Closed LU + cascade carry the picking consignee');
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

    // While still open, the picked LU already carries the consignee (stamped at pick time; see header
    // comment) — the label-config key is present on the LU, its TU and its CU, end-to-end.
    await Backend.expect({
        title: 'while open: the picked LU carries the consignee',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'openLU', tu: 'openTU', vhu: 'openVHU' }] }
                }
            }
        },
        hus: {
            openLU: { huStatus: 'S', bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
            openTU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
            openVHU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
        }
    });

    await PickingJobScreen.closeTargetLU();

    // After close-LU the closed LU (and its cascaded TU/CU) carry the consignee (bpartner + delivery
    // location), so the per-BPartner M_HU_Label_Config matches for the close-time auto-print and for a
    // later re-print. This is the customer-facing guarantee of me03 #30763 (AC1/AC2).
    await Backend.expect({
        title: 'after close-LU: the closed LU + cascade carry the consignee',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, lu: 'closedLU', tu: 'closedTU', vhu: 'closedVHU' }] }
                }
            }
        },
        hus: {
            closedLU: {
                huStatus: 'S',
                bpartner: 'customer1',
                bpartnerLocation: 'customer1_location1',
            },
            closedTU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
            closedVHU: { bpartner: 'customer1', bpartnerLocation: 'customer1_location1' },
        }
    });
});
