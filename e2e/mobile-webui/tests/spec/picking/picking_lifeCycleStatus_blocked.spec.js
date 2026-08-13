import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

/**
 * Product life-cycle status (BBS-Status) blocks mobile picking.
 *
 * A product that is "Gesperrt" (blocked, status G) must not be pickable. The block is TEMPORAL and
 * that is the whole point of the scenario: a product with status G can never reach a picking job
 * through the normal path, because order-line creation (SELL) is already blocked for a G product.
 * So the realistic flow is — sell + create the picking job while the product is still OK, THEN flip
 * it to G, THEN have the picker try to pick it:
 *
 *   1. product created OK  →  sold on SO1 (completes, shipment schedule appears)  →  picking job ready
 *   2. product flipped to G (second masterdata request — `productLifeCycleStatuses`)
 *   3. picker opens the job, scans the HU, presses OK  →  backend rejects the pick (PICK not allowed)
 *      →  error toast, nothing is picked.
 */

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
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                // created OK (default null status is fully permissive) so the sale below goes through
                "P1": { price: 1 },
            },
            packingInstructions: {
                "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 20 },
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
    })
}

// noinspection JSUnusedLocalSymbols
test('Gesperrt product cannot be picked', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section
    allure.story('Product life-cycle status (BBS-Status) blocks picking');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    // The product was sold while OK; now it gets blocked in the ERP (status G = Gesperrt).
    await test.step("Block the product (flip P1 to Gesperrt)", async () => {
        await Backend.createMasterdata({ request: { productLifeCycleStatuses: { P1: 'G' } } });
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });

    await test.step("Attempt to pick the blocked product -> rejected", async () => {
        // Assert on the language-independent error code (the backend appends it to the toast, and it
        // never gets translated) rather than the localized message text — same handle the order-line
        // cucumber test keys on. The visible message reads e.g. "Product <x> is in status G - action
        // not allowed." (en_US) / "Produkt <x> ist im Status G - Aktion nicht erlaubt." (de_DE).
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.huId,
            expectedError: 'M_Product_BBSStatus_ActionBlocked',
        });
    });

    await test.step("Nothing was picked", async () => {
        // Assert the real end result via the backend (authoritative + immune to the still-visible
        // rejection toast): the source HU is untouched — still Active with its full quantity, nothing
        // moved onto a picked HU, and the shipment schedule has no picked qty.
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [] }
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '1000 PCE' } },
            }
        });
    });
});
