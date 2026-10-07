import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

const QTY_TUS_ON_PALLET = 60; // = PI qtyTUsPerLU; the HU is created full (backend rejects an explicit qty together with packingInstructions)
const QTY_TO_PICK = 4;

/**
 * Pallet PI whose only LU->TU item is bound to the customer (no generic item), 1 piece per TU.
 * Stock: one pallet (partner = customer) holding an aggregated TU of 60 x 1 piece.
 *
 * @param tuHasPartner false: the TUs of the stock pallet have NO partner; true: their partner = customer
 */
const createMasterdata = async ({ tuHasPartner }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'], // LU/TU only => an LU target must be selected before picking
                    catchWeightTUPickingEnabled: true,
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "Customer": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "CW Product": {
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }]
                },
            },
            packingInstructions: {
                // "Pallet" -> "TU 1pc"; the LU->TU item is bound to the customer only
                "PI": { lu: "Pallet", qtyTUsPerLU: 60, tu: "TU 1pc", product: "CW Product", qtyCUsPerTU: 1, bpartner: "Customer" },
            },
            handlingUnits: {
                "Stock Pallet": {
                    product: 'CW Product',
                    warehouse: 'wh',
                    packingInstructions: 'PI',
                    bpartner: 'Customer', // partner of the pallet
                    ...(tuHasPartner ? { tuBPartner: 'Customer' } : {}), // omitted => TUs have no partner
                    lotNo: 'lot1',
                    bestBeforeDate: '2031-11-23',
                },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'Customer',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'CW Product', qty: QTY_TO_PICK, piItemProduct: 'TU 1pc' }]
                }
            },
        }
    });
};

const runScenario = async ({ tuHasPartner }) => {
    const masterdata = await createMasterdata({ tuHasPartner });
    const stockPalletQRCode = masterdata.handlingUnits["Stock Pallet"].qrCode;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    // Pick-to structure is LU/TU only => choose "new LU" of the pallet PI.
    // An unexpected error toast/screen fails the step it appears in (see common.js step()).
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await test.step("Pick 1st TU: scan the stock pallet, then a weight label (new picking pallet)", async () => {
        await PickingJobScreen.pickHU({
            qrCode: stockPalletQRCode,
            catchWeightQRCode: ['LMQ#1#3.020#09.11.2031#243'],
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${QTY_TO_PICK} TU`, qtyPicked: '1 TU', qtyPickedCatchWeight: '3.02 kg' });
    });

    await test.step("Pick 2nd TU: scan the stock pallet again, then a 2nd weight label (existing picking pallet)", async () => {
        await PickingJobScreen.pickHU({
            qrCode: stockPalletQRCode,
            catchWeightQRCode: ['LMQ#1#3.358#09.11.2031#243'],
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${QTY_TO_PICK} TU`, qtyPicked: '2 TU', qtyPickedCatchWeight: '6.378 kg' });
    });

    await test.step("Verify: both TUs on ONE picking pallet, the rest stays on the source pallet", async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        "CW Product": {
                            qtyPicked: [
                                // the same alias 'lu1' on both records == one picking pallet
                                { qtyPicked: "1 PCE", catchWeight: "3.020 KGM", qtyTUs: 1, qtyLUs: 1, lu: 'lu1', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "1 PCE", catchWeight: "3.358 KGM", qtyTUs: 1, qtyLUs: 1, lu: 'lu1', processed: false, shipmentLineId: '-' },
                            ]
                        }
                    }
                }
            },
            hus: {
                [stockPalletQRCode]: { huStatus: 'A', tus: [{ isAggregatedTU: true, qtyTUs: QTY_TUS_ON_PALLET - 2 }] },
                // each pick onto the existing picking pallet adds its own aggregated TU
                lu1: { huStatus: 'S', tus: [{ isAggregatedTU: true, qtyTUs: 1 }, { isAggregatedTU: true, qtyTUs: 1 }] },
            }
        });
    });
};

// Variant (a): the TUs of the stock pallet have no partner (partner only on the pallet).
// Before the fix: the 1st pick fails with "... is not configured to be stored into LU ...".
// noinspection JSUnusedLocalSymbols
test('Pick TUs from a pallet with a customer-bound TU item - TU without partner', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs from a pallet - partner-bound pallet TU item - TU without partner');
    allure.severity('critical');

    await runScenario({ tuHasPartner: false });
});

// Variant (b): the TUs of the stock pallet have the customer as partner too.
// Before the fix: the 1st pick is OK, the 2nd fails with ERR_LU_HAS_NO_TU_SUB_PACK_INSTR.
// noinspection JSUnusedLocalSymbols
test('Pick TUs from a pallet with a customer-bound TU item - TU partner = customer', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs from a pallet - partner-bound pallet TU item - TU partner = customer');
    allure.severity('critical');

    await runScenario({ tuHasPartner: true });
});
