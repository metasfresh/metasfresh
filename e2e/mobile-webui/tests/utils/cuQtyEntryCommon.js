// Shared setup of the CU quantity entry specs (all modes) that drive a product-based picking job: masterdata, job start, line open, label scan
// and booking. The examples in the comments are of Scan once ("one label, type a count").
//
// Flow of the app as built: with a source HU scanned ("Kommissionieren aus"), a tap on a line with nothing picked opens
// its scan screen directly; a label scan opens the quantity block; after OK the picker stays on the line (the block
// stays open, the open quantity drops) until the line is fully booked, then the app goes back to the job screen.
// Every booking needs an open TU (a GRAI scan or "Next TU").
// Source: one pallet of 20 TUs of 4 pieces (80 pieces).
import { allure } from 'allure-playwright';
import { Backend } from './screens/Backend';
import { LoginScreen } from './screens/LoginScreen';
import { ApplicationsListScreen } from './screens/ApplicationsListScreen';
import { PickingJobsListScreen } from './screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from './screens/picking/PickingJobScreen';
import { PickLineScanScreen } from './screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from './screens/picking/GetQuantityDialog';
import { PickingJobLineScreen } from './screens/picking/PickingJobLineScreen';
import { PickingJobStepScreen } from './screens/picking/PickingJobStepScreen';
import { SelectPickTargetLUScreen } from './screens/picking/SelectPickTargetLUScreen';
import { buildCULabel, buildCULabelFormat, defaultBestBefore, uniqueGTIN, uniqueLot, uniqueProductValue } from './cuLabel';

export const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY';
export const MODE_MANUAL = 'MANUAL';
export const BEST_BEFORE = defaultBestBefore();
export const SOURCE_QTY = 80; // 20 TUs x 4 pieces
// GS1 AI 8003 GRAI form (AI + pad + company prefix 7613264 + asset type 00309 + check digit + first serial digit); same public test value as tests/spec/humanager/grai.spec.js
export const GRAI_8003_PREFIX = '8003076132640030951';
export const MSG_NO_OPEN_TU = "Please open a TU first (scan a GRAI or 'Next TU').";

// The launcher list shows at most this many launchers (default 20). Leftovers of other runs on a shared DB (every
// unpicked order of any product is a candidate) would push the launcher of a spec's fresh masterdata out of the list,
// so the specs raise the display limit; this changes nothing the specs assert.
export const LAUNCHERS_LIMIT_SYSCONFIGS = { 'WorkflowRestAPIService.LaunchersLimit': '1000' };

// The sysconfigs a test overwrote: the `previousSysconfigs` field of its masterdata calls (the values before the call). Written
// back after the test by restoreSysconfigsAfterEach, also when the test failed, so the raised limit does not leak into
// later specs and runs on the shared DB (same pattern as barcode_scanner_modes.spec.js).
let sysconfigsToRestore = new Map();

/** Backend.createMasterdata that remembers the sysconfig values it overwrote. */
export const createMasterdataRememberingSysconfigs = async (args) => {
    const masterdata = await Backend.createMasterdata(args);
    for (const [name, value] of Object.entries(masterdata.previousSysconfigs ?? {})) {
        if (!sysconfigsToRestore.has(name)) {
            sysconfigsToRestore.set(name, value); // the first call of the test holds the values before the test
        }
    }
    return masterdata;
};

/** Call once at the top level of a spec file that creates masterdata through this module. */
export const restoreSysconfigsAfterEach = (test) => {
    test.afterEach(async () => {
        const sysconfigs = Object.fromEntries(sysconfigsToRestore);
        sysconfigsToRestore = new Map();
        if (Object.keys(sysconfigs).length > 0) {
            await Backend.setSysconfigs(sysconfigs);
        }
    });
};

export const uniqueFormatName = () => `cuqe-so-${uniqueProductValue()}`;

/**
 * @param {object} p
 * @param {string} p.formatName label format of the profile
 * @param {object[]} p.orders sales orders in the order of the job lines: `{ key, qty, graiRequired?, piItemProduct? }`
 *        (customer `cust<key>` is created per order; `piItemProduct` = TU packing of the line, default 'P1_4CU', null = none)
 * @param {object} [p.customers] customer records of the mobile config
 * @param {string|null} [p.profileMode] profile CU quantity entry mode; null = not sent (Manual)
 * @param {object} [p.packingInstructions] extra packing instructions (e.g. GRAI mapped TU types)
 * @param {object} [p.products] extra products
 * @param {string[]} [p.pickTo]
 * @param {object} [p.sysconfigs] sysconfigs to set for the test (on top of the launcher limit)
 * @param {boolean} [p.anonymousPickHUsOnTheFly]
 * @param {string} [p.language] session language, default en_US
 * @param {boolean} [p.allowCompletingPartialPickingJob] profile flag, always sent (default false) so no spec inherits it from an earlier one; a test that completes a partly picked job passes true
 * @param {{name: string, withPackId?: boolean}[]} [p.formats] formats to create; default = `formatName` without a pack-ID part
 */
export const createMasterdata = async ({
                                           formatName,
                                           orders,
                                           customers = [],
                                           profileMode = MODE_SCAN_ONCE,
                                           packingInstructions = {},
                                           products = {},
                                           pickTo = ['TU'],
                                           sysconfigs = {},
                                           anonymousPickHUsOnTheFly = false,
                                           allowCompletingPartialPickingJob = false,
                                           language = 'en_US',
                                           formats = [{ name: formatName, withPackId: false }],
                                       }) => {
    const bpartners = {};
    const salesOrders = {};
    orders.forEach(({ key, qty, graiRequired, piItemProduct = 'P1_4CU' }) => {
        bpartners[`cust${key}`] = graiRequired ? { graiRequired } : {};
        salesOrders[`SO_${key}`] = {
            bpartner: `cust${key}`,
            warehouse: 'wh',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            lines: [{ product: 'P1', qty, ...(piItemProduct ? { piItemProduct } : {}) }]
        };
    });
    const slots = {};
    orders.forEach((_, idx) => { slots[`slot${idx + 1}`] = {}; });

    return await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: { ...LAUNCHERS_LIMIT_SYSCONFIGS, ...sysconfigs },
            login: { user: { language } },
            mobileConfig: {
                picking: {
                    aggregationType: "product",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    anonymousPickHUsOnTheFly,
                    showPromptWhenOverPicking: false,
                    allowCompletingPartialPickingJob,
                    pickTo,
                    ...(profileMode ? { cuQtyEntryMode: profileMode } : {}),
                    cuLabelFormatName: formatName,
                    customers,
                }
            },
            customQRCodeFormats: formats.map(({ name, withPackId = false }) => buildCULabelFormat({ name, withPackId })),
            bpartners,
            warehouses: { "wh": {} },
            pickingSlots: slots,
            products: {
                // product value of 7 characters, as on the real pack label
                "P1": { price: 1, randomValue: { size: 7, isIncludeDigits: true } },
                ...products,
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
                ...packingInstructions,
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
            },
            salesOrders,
        }
    });
};

/** Logs in, starts the product job of P1 (launcher pinned by product), scans the source HU ("Kommissionieren aus") and the picking slot of line 1. */
export const startJob = async (masterdata, { qtyToDeliver }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver, productId: masterdata.products.P1.id });
    await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    return { pickingJobId };
};

/** Opens the scan screen of a Scan once line with nothing picked yet (source HU known: the tap goes there directly) and checks its mode. */
export const openLine = async ({ index }) => {
    await PickingJobScreen.clickLineButton({ index });
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
};

/**
 * Sets a pallet (LU) target of type `lu` on the line, before its first booking (needs `pickTo` with 'LU_TU'): from the line's scan
 * screen back to the line screen, pallet target, then "HU verpacken" / "Pack HU" back to the scan screen.
 */
export const setPalletTarget = async ({ lu }) => {
    await PickLineScanScreen.goBack();
    await PickingJobLineScreen.clickLUTargetButton();
    await SelectPickTargetLUScreen.clickLUButton({ lu });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.tapPickHUButton();
    await PickLineScanScreen.waitForScreen();
};

/**
 * Opens the pick-step screen of the first booking of the open line and unpacks it ("Entpacken", no target HU); ends on the job screen.
 * Starts on the line's scan screen, or on the line screen with `fromLineScreen` (e.g. after Cancel of the quantity block).
 */
export const unpackFirstBooking = async ({ fromLineScreen = false } = {}) => {
    if (fromLineScreen) {
        await PickingJobLineScreen.waitForScreen();
    } else {
        await PickLineScanScreen.goBack(); // lands on the line screen, which lists the picked HUs
    }
    await PickingJobLineScreen.clickStepButton({ index: 0 });
    await PickingJobStepScreen.unpick();
    await PickingJobLineScreen.goBack(); // the unpick leaves on the line screen
};

/** Scan once: label scan, the quantity block opens. */
export const scanLabel = async (label) => {
    await PickLineScanScreen.scanCULabel(label);
    await GetQuantityDialog.waitForDialog();
};

export const labelOf = ({ masterdata, gtin = uniqueGTIN(), lot = uniqueLot(), packId = 1, bestBefore = BEST_BEFORE }) =>
    buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, bestBefore, packId });

/** Types the count and presses OK: the picker stays on the line, `expectedQtyTarget` = open quantity afterwards. */
export const book = async ({ qty, expectedQtyTarget }) => {
    await GetQuantityDialog.typeQtyEntered(qty);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget });
};

export const allureMeta = (story) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story(story);
    allure.severity('normal');
};

/** One `qtyPicked` expectation row of a booking that is not processed yet (a CU `vhu` inside the TU `tu`, no LU). */
export const qtyPickedRow = ({ qty, vhu, tu }) => ({ qtyPicked: `${qty} PCE`, qtyTUs: 1, qtyLUs: 0, vhu, tu, lu: '-', processed: false, shipmentLineId: '-' });

export { uniqueLot };
