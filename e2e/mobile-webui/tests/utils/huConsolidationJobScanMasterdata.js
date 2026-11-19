import { Backend } from './screens/Backend';

/** AD_Process_ID of the LU label report: core "LU SSCC-Barcode-Etikett" (540412). */
const HU_LABEL_PROCESS_ID = Number(process.env.HU_LABEL_PROCESS_ID || 540412);
/** Order date of the sales orders (the value every existing consolidation spec uses; its accounting period is open). */
const DATE_PROMISED = process.env.E2E_DATE_PROMISED || '2025-03-01T00:00:00.000+02:00';

/**
 * Masterdata of the job-scan scenarios: partner BP1 (graiRequired 'Y'), two slots, three single-line sales orders:
 * SO1 (P1) -> slot1, SO2 (P2) -> slot1, SO3 (P1) -> slot2. Product aggregation + pickTo:['TU'] so each pick
 * materialises as a top-level GRAI-bearing crate. The LU PI has an SSCC18 attribute; BP1 has its own LU label config.
 */
export const createJobScanMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
                    allowCompletingPartialPickingJob: true,
                },
            },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {}, slot2: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_P1: { lu: 'LU', luSSCC18: true, qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5, graiMapping: true },
            },
            huLabelConfigs: {
                lc1: { bpartner: 'BP1', huUnitType: 'LU', sourceDocType: 'PI', process: HU_LABEL_PROCESS_ID, autoPrint: true },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
                HU2: { product: 'P2', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: { bpartner: 'BP1', warehouse: 'wh', datePromised: DATE_PROMISED, lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
                SO2: { bpartner: 'BP1', warehouse: 'wh', datePromised: DATE_PROMISED, lines: [{ product: 'P2', qty: 5, piItemProduct: 'TU2' }] },
                SO3: { bpartner: 'BP1', warehouse: 'wh', datePromised: DATE_PROMISED, lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
            },
        },
    });
};
