import { Backend } from './screens/Backend';

/** AD_Process_ID of the LU label report: core "LU SSCC-Barcode-Etikett" (540412); override via env HU_LABEL_PROCESS_ID when the image uses another label process. */
export const HU_LABEL_PROCESS_ID = Number(process.env.HU_LABEL_PROCESS_ID || 540412);

/**
 * Core "LU QR Code Label" report (AD_Process_ID 584998, migration 5626660_sys_gh12534_Add_ReportForLU_QR_Label.sql): an LU label process
 * WITHOUT an M_HU_Process row, like the customer's own SSCC label process. With such a pallet label, the crates on the pallet are looked up
 * for a crate label of their own. The default label of the job-scan masterdata.
 */
export const LU_LABEL_PROCESS_WITHOUT_HU_PROCESS_ID = 584998;

/**
 * Settings as on the customer's instance: the legacy picking label (fallback for every label without a label config) is not configured
 * (-1 takes the same code path as the inactive sysconfig), and the scanner's live timing / manual input.
 * Only the tests of the customer's setup pass them (`sysconfigs`); {@link cleanUpAfterJobScanTest} writes the previous values back after each test.
 */
export const LIVE_DEFAULT_SYSCONFIGS = {
    'de.metas.ui.web.picking.PickingLabel.AD_Process_ID': '-1',
    'mobileui.frontend.barcodeScanner.inputText.debounceMillis': '1000',
    'mobileui.frontend.barcodeScanner.mode.manual.enabled': 'Y',
};

/** Order date of the sales orders (the value every existing consolidation spec uses; its accounting period is open). */
export const DATE_PROMISED = process.env.E2E_DATE_PROMISED || '2025-03-01T00:00:00.000+02:00';

/** A single-line sales order of masterdata warehouse `wh`, dated {@link DATE_PROMISED}: `tu` is the key of the packing instructions item the line is ordered in. */
export const jobScanSalesOrder = ({ bpartner, product, qty, tu }) => ({
    bpartner,
    warehouse: 'wh',
    datePromised: DATE_PROMISED,
    lines: [{ product, qty, piItemProduct: tu }],
});

/**
 * Masterdata of the job-scan scenarios: partner BP1 (graiRequired 'Y'), two slots, three single-line sales orders:
 * SO1 (P1) -> slot1, SO2 (P2) -> slot1, SO3 (P1) -> slot2. Product aggregation + pickTo:['TU'] so each pick
 * materialises as a top-level GRAI-bearing crate. The LU PI has an SSCC18 attribute; BP1 has its own LU label config, with the customer's
 * label setup: a pallet label process without M_HU_Process, no crate label config, legacy picking label off ({@link LIVE_DEFAULT_SYSCONFIGS}).
 *
 * @param {{language?: string, sysconfigs?: object, pickTo?: string[], pickingProfile?: object, bpartners?: object, pickingSlots?: object, salesOrders?: object, handlingUnits?: object, huLabelConfigs?: object, packingInstructions?: object}} [overrides]
 *   optional replacements of the whole section of the same name (e.g. a second partner, a third slot, a pallet pick); `sysconfigs` and
 *   `pickingProfile` are merged over the defaults (`pickingProfile` = fields of mobileConfig.picking, e.g. the customer's pallet-only profile); `handlingUnits` is added to the two source HUs (e.g. an existing pallet); `language` replaces 'en_US' (user + masterdata); `huLabelConfigs` replaces the default label config of BP1 (e.g. `{}` = none); `packingInstructions` replaces the default PI_P1 / PI_P2 (e.g. PI_P2 with its own LU without SSCC18, so the SSCC label of a closed pallet of that type has no SSCC to print).
 */
export const createJobScanMasterdata = async ({ language = 'en_US', pickTo, pickingProfile, bpartners, pickingSlots, salesOrders, sysconfigs, handlingUnits, huLabelConfigs, packingInstructions } = {}) => {
    const masterdata = await Backend.createMasterdata({
        language,
        request: {
            ...(sysconfigs ? { sysconfigs } : {}),
            login: { user: { language } },
            mobileConfig: {
                picking: {
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: pickTo ?? ['TU'],
                    allowCompletingPartialPickingJob: true,
                    anonymousPickHUsOnTheFly: false, // the picking profile is shared and persistent: a spec that left it on would send the pick line tap straight to the quantity screen
                    ...pickingProfile,
                },
            },
            bpartners: bpartners ?? { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: pickingSlots ?? { slot1: {}, slot2: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
                P2: { prices: [{ price: 1 }] },
            },
            packingInstructions: packingInstructions ?? {
                PI_P1: { lu: 'LU', luSSCC18: true, qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5, graiMapping: true },
            },
            huLabelConfigs: huLabelConfigs ?? {
                lc1: { bpartner: 'BP1', huUnitType: 'LU', sourceDocType: 'PI', process: LU_LABEL_PROCESS_WITHOUT_HU_PROCESS_ID, autoPrint: true },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
                HU2: { product: 'P2', warehouse: 'wh', qty: 100 },
                ...handlingUnits,
            },
            salesOrders: salesOrders ?? {
                SO1: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
                SO2: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
                SO3: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
            },
        },
    });
    // keep the values found before the FIRST masterdata call of the test; the later calls only see this test's own values
    if (sysconfigsToRestore === null) {
        sysconfigsToRestore = masterdata.previousSysconfigs ?? {};
    }
    Object.values(masterdata.pickingSlots ?? {}).forEach((pickingSlot) => pickingSlotIdsToEmpty.add(pickingSlot.id));
    return masterdata;
};

/** Sysconfig values found before the first masterdata call of the running test (null = no call yet). */
let sysconfigsToRestore = null;
/** Picking slots created by the running test's masterdata. */
const pickingSlotIdsToEmpty = new Set();

/**
 * Leaves the database as the test found it, for the next test (and the next spec or run): writes back the sysconfig values found before the
 * test's first masterdata call, and empties the test's picking slots (each non-empty picking slot is offered as an HU consolidation job to
 * every later test). Call it in `test.afterEach` of every spec that uses this masterdata; it also runs when the test failed.
 */
export const cleanUpAfterJobScanTest = async () => {
    const sysconfigs = sysconfigsToRestore;
    sysconfigsToRestore = null;
    const pickingSlotIds = [...pickingSlotIdsToEmpty];
    pickingSlotIdsToEmpty.clear();

    if (sysconfigs && Object.keys(sysconfigs).length > 0) {
        await Backend.setSysconfigs(sysconfigs);
    }
    if (pickingSlotIds.length > 0) {
        await Backend.emptyPickingSlots({ pickingSlotIds });
    }
};

/**
 * Masterdata of the scenarios that pick crates WITHOUT a GRAI: partner BP1 on GRAIRequired 'N' (flip it later with
 * Backend.updateBPartner), one slot, SO1 (P1) and SO2 (P2), both for BP1 (same ship-to = one Verdichtung job).
 */
export const createNoGraiPartnerMasterdata = async ({ language = 'en_US' } = {}) => await createJobScanMasterdata({
    language,
    bpartners: { BP1: { graiRequired: 'N' } },
    pickingSlots: { slot1: {} },
    salesOrders: {
        SO1: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
        SO2: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
    },
});
